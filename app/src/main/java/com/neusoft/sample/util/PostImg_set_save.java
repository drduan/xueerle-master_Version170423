package com.neusoft.sample.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by AstroBoy on 2017/1/16.
 */

public class PostImg_set_save {
    private final String TAG = "PostImg_set_save";
    private Handler bitmapHandler = new Handler();
    private Bitmap bitmap;

    private synchronized String PostImg_set_save(final String postUrl, final ImageView imageView,final String imgName) {
        final String[] SavePath = {""};
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(postUrl); //path图片的网络地址

                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setConnectTimeout(5000);
                    if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                        bitmap = BitmapFactory.decodeStream(httpURLConnection.getInputStream());
                        bitmapHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(bitmap);
                                File appDir = new File(Environment.getExternalStorageDirectory(), "Xel_Resources");
                                if (!appDir.exists()) {
                                    appDir.mkdir();
                                }
                                String fileName = imgName;
                                Log.d(TAG, "fileName" + fileName);
                                File file = new File(appDir, fileName);
                                SavePath[0] = appDir + "/" + fileName + ".jpg";
                                Integer i = SavePath[0].length();
                                SavePath[0] = SavePath[0].substring(1, i - 4);
                                Log.d(TAG, "裁剪完后的Uri" + SavePath[0]);
                                /*MsharedPrefrence.SetUserTempImgDir(ContextHolder.getContext(), phoneNum, savePath);
                                String[] imgDir = MsharedPrefrence.GetUserUserTempImgDir(ContextHolder.getContext());
                                Log.d(TAG, "ImgDir(SP):" + imgDir[1]);
                                Log.d(TAG, "Astro");*/
                                try {
                                    FileOutputStream fos = new FileOutputStream(file);
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                                    fos.flush();
                                    fos.close();
                                    Log.d(TAG, "已将图片保存至本地");
                                    Log.d(TAG, "本地图片路径" + SavePath[0]);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    //Toast.makeText(getContext(),"未将图片保存至本地",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Looper.prepare();
                        //Toast.makeText(getContext(),"未将图片保存至本地",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(TAG, "网络图片获取失败");
                    bitmap = null;
                }
            }
        }).start();
        return "1";
    }
}
