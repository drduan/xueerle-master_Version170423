package com.neusoft.sample.Model;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by AstroBoy on 2016/8/29.
 */

public class Post_Feedback {

    //private static final String IMGUR_CLIENT_ID = "...";
    public static final MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpg");
    public static String getStringCha(String url, String filePaths, String content,String phone) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .build();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("img",filePaths, RequestBody.create(MEDIA_TYPE_JPG, new File(filePaths)))
                .addFormDataPart("content",content)
                .addFormDataPart("phone",phone)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .tag("")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        //System.out.println(response.body().string());
        return response.body().string();
    }

    public static String getStringChaNonImg(String url, String filePaths, String content,String phone) throws IOException {
        final OkHttpClient client = new OkHttpClient();
        
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("img",filePaths)
                .addFormDataPart("content",content)
                .addFormDataPart("phone",phone)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .tag("")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        //System.out.println(response.body().string());
        return response.body().string();
    }
}
