package com.neusoft.sample.Ctrl.wenchengcheng;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.ncapdevi.sample.R;
import com.neusoft.sample.Model.Constant;
import com.neusoft.sample.util.ContextHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import im.fir.sdk.FIR;
import im.fir.sdk.VersionCheckCallback;

public class CheckForUpdateService extends Service{
    private String originJson = "";
    private CheckForUpdate checkUpdate;
    public static Map<Object, String> content = new HashMap<>();
    private static String TAG = "UpdateUtil";
    private String firToken = "359710993ea3491621b90593b36c7f87";
    public Context context;
    public String fileName;

    public Map<Object, String> CheckForUpdateService(Context context1) {
        this.context = context1;
        FIR.checkForUpdateInFIR(firToken, new VersionCheckCallback() {
            @Override
            public void onSuccess(String versionJson) {
                Log.i("fir", "check from fir.im success! " + "\n" + versionJson);
                originJson = versionJson;
                try {
                    JSONObject json = new JSONObject(originJson);
                    checkUpdate = JSON.parseObject(String.valueOf(json),
                            CheckForUpdate.class);
                    JSONObject byteJson = new JSONObject(checkUpdate.getBinary());
                    String charByte = byteJson.get("fsize").toString();
                    Log.d(TAG, "charByte = " + charByte);
                    String name = checkUpdate.getName();
                    Float size = (Float.parseFloat(charByte) / 1024f / 1024f);
                    String time = Constant.getyymmddhhmmss(checkUpdate.getUpdated_at());
                    String version = checkUpdate.getVersionShort();
                    String log = checkUpdate.getChangelog();
                    String url = checkUpdate.getDirect_install_url();
                    content.put("AppName", name);
                    content.put("FileSize", String.valueOf(size).substring(0, 5) + "MB");
                    content.put("UpdateTime", time);
                    content.put("ShortVersion", version);
                    content.put("ChangeLog", log);
                    content.put("DownloadUrl", url);
                    Log.d(TAG, "map = " + content);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String version1 = content.get("ShortVersion");
                String version2 = Constant.getAppVersionName(context);
                String[] str1 = version1.split("\\.");
                String[] str2 = version2.split("\\.");
                Log.d(TAG, "VER1:" + version1 + "VER2:" + version2);
                Log.d(TAG, "VER1LEN:" + str1.length + "VER2LEN:" + str2.length);
                if (Integer.parseInt(str1[0]) > Integer.parseInt(str2[0])) {
                    content.put("canUpdate", "yes");
                } else {
                    if (Integer.parseInt(str1[1]) > Integer.parseInt(str2[1])) {
                        content.put("canUpdate", "yes");
                    } else {
                        if (Integer.parseInt(str1[2]) > Integer.parseInt(str2[2])) {
                            content.put("canUpdate", "yes");
                        } else {
                            content.put("canUpdate", "no");
                        }
                    }
                }
                Log.d(TAG,"CANUPDATE?"+content.get("canUpdate"));
                if (content.get("canUpdate").contains("y")&&content.get("DownloadUrl")!=null){
                    fileName = "eXueHui_v"+version1+Constant.getSystemTime();
                    //Toast.makeText(ContextHolder.getContext(),"有新版本!版本号:"+version1,Toast.LENGTH_SHORT).show();
                    new AlertDialog.Builder(context)
                            .setTitle("有更新版本(TEST)")
                            .setIcon(R.drawable.history)
                            .setMessage("版本号:"+version1)

                            .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    new DownloadManagerUtil().DownloadManager(content.get("DownloadUrl"),fileName);
                                }
                            })
                            .setNegativeButton("不用了", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setCancelable(true)
                            .create()
                            .show();

                }else{
                    Toast.makeText(ContextHolder.getContext(),"当前为最新版本~",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFail(Exception exception) {
                Log.i("fir", "check fir.im fail! " + "\n" + exception.getMessage());
                Toast.makeText(context, "检测更新失败,请联网重试", Toast.LENGTH_SHORT).show();
                content.put("canUpdate", "fail");

            }

            @Override
            public void onStart() {

                Toast.makeText(context, "正在检测更新...", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFinish() {

                //Toast.makeText(context, "获取完成", Toast.LENGTH_SHORT).show();

            }
        });
        return content;
    }


    /**
     * Return the communication channel to the service.  May return null if
     * clients can not bind to the service.  The returned
     * {@link IBinder} is usually for a complex interface
     * that has been <a href="{@docRoot}guide/components/aidl.html">described using
     * aidl</a>.
     * <p>
     * <p><em>Note that unlike other application components, calls on to the
     * IBinder interface returned here may not happen on the main thread
     * of the process</em>.  More information about the main thread can be found in
     * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html">Processes and
     * Threads</a>.</p>
     *
     * @param intent The Intent that was used to bind to this service,
     *               as given to {@link Context#bindService
     *               Context.bindService}.  Note that any extras that were included with
     *               the Intent at that point will <em>not</em> be seen here.
     * @return Return an IBinder through which clients can call on to the
     * service.
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
