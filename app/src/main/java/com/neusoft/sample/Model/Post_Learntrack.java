package com.neusoft.sample.Model;


import com.lzy.okgo.OkGo;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Response;

public class Post_Learntrack {

    public static final MediaType MEDIA_TYPE_PNG = MediaType.parse("application/json; charset=utf-8");
    
    public static String getStringCha(String url, String jiaocaiNo,String time,String uid) throws IOException {
        /*final OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("jiaocaiNo",jiaocaiNo)
                .addFormDataPart("time",time)
                .addFormDataPart("user",uid)
                .build();*/
        Response response = OkGo
                .get(url)
                .params("jiaocaiNo",jiaocaiNo)
                .params("time",time)
                .params("user",uid)
                .tag("")
                .execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        return response.body().string();
    }
}
