package com.neusoft.sample.Model;


import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.neusoft.sample.Ctrl.yangkangkang.NewWords;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 杨康 on 2016/8/18.
 */

public class pdf_http {
    static String zhi_ = "";
    static List<NewWords> accept;
    static String lujing  = "";
    public static Handler handler;
    public  static void   pdfhttp (String urll){
        lujing  = Environment.getExternalStorageDirectory().getAbsolutePath()+"/com.xel.www/";
        File file=new File(lujing);
        if (!file.exists())
        {
            file.mkdirs();
        }
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Log.d("@@","url"+urll);

        Request request = new Request.Builder().url(urll).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("@@", "onFailure");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                Log.d("@@",lujing+"s");
                try {
                    is = response.body().byteStream();
                    File file = new File(lujing, "pdf.pdf");
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    fos.flush();
                    Log.d("@@", "文件下载成功");
                    Message message=new Message();
                    Bundle bundle=new Bundle();
                    bundle.putInt("passkey",1);
                    message.setData(bundle);
                    handler.sendMessage(message);
                } catch (Exception e) {
                    Log.d("@@", "文件下载失败");
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
            }

        });
    }
    public static String getlujing(){
        Log.d("@@","lujing "+lujing);
        return lujing;
    }
}
