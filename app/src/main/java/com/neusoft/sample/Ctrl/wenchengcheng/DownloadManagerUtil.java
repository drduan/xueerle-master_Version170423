package com.neusoft.sample.Ctrl.wenchengcheng;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.neusoft.sample.View.BaseActivity;
import com.neusoft.sample.util.ContextHolder;

import java.io.File;

/**
 * Created by AstroBoy on 6/10/28.
 */

public class DownloadManagerUtil extends BaseActivity {
    private DownloadManager mDownloadManager;
    private long mDownloadReference;
    private String url;
    private String uri;
    private String fileName = "";
    private File mFile;

    public void DownloadManager(String downloadUrl,String fileName) {
        this.url = downloadUrl;
        this.fileName = fileName;
        mDownloadManager = (DownloadManager) ContextHolder.getContext().getSystemService(DOWNLOAD_SERVICE);

        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);

        DownloadCompleteReceiver downloadReceiver = new DownloadCompleteReceiver();
        ContextHolder.getContext().registerReceiver(downloadReceiver, filter);
        //compareVersion();
        Uri u = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(u);
        // request.setNotificationVisibility(Request.VISIBILITY_HIDDEN);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        // 设置下载标题，改标题会在通知中显示
        request.setTitle("下载应用");
        // 设置下载描述, 该描述会在通知中显示
        request.setDescription("正在下载应用");
        // 指定图像文件下载到本地的目录和文件名
        mFile = new File(Environment.getExternalStorageDirectory()+"/Xel_Resources/",fileName+".apk");
        request.setDestinationUri(Uri.fromFile(mFile));

        // 将下载任务加入队列，并返回下载任务的引用
        mDownloadReference = mDownloadManager.enqueue(request);
        request.setTitle("正在下载新版应用...");
    }

    private class DownloadCompleteReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(
                    DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (mDownloadReference == referenceId) {

                    Log.d("DownloadManager",mFile.getName());
                    Toast.makeText(ContextHolder.getContext(),"应用下载完成，请安装",Toast.LENGTH_SHORT).show();
                    Intent in = new Intent();
                    in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    in.setAction(Intent.ACTION_VIEW);
                    in.setDataAndType(Uri.fromFile(mFile),"application/vnd.android.package-archive");
                    ContextHolder.getContext().startActivity(in);
                    //new PkgSilentInstall().silentInstall(mFile.getName());
                }
            }
        }

    }
}

