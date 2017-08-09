package com.neusoft.sample.Model;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by AstroBoy on 2016/9/13.
 */
public class Post_area_rankList {

    public static String getStringCha(String city,String grade,String month,String year) throws IOException {
        final OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("year",year)
                .addFormDataPart("nianji",grade)
                .addFormDataPart("month",month)
                .addFormDataPart("city",city)
                .build();

        Request request = new Request.Builder()
                .url(Constant.post_longhubang)
                .tag("")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        return response.body().string();
    }
}
