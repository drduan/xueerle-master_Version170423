package com.neusoft.sample.Model;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Post_UserInfo {

    public static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpg");
    
    public static String getStringCha(String url, String uploadPath,String keyID) throws IOException {
        final OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("user_id",keyID)
                .addFormDataPart("img",keyID+".jpg",
                        RequestBody.create(MEDIA_TYPE_PNG, new File(uploadPath)))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .tag("")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        return response.body().string(); 
    }
}
