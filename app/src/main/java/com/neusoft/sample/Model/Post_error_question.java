package com.neusoft.sample.Model;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/7/10.
 */
public class Post_error_question {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static String getStringCha(String url, Map<String, String> paramMap) throws IOException {
        HttpParams params = new HttpParams();
        Set<String> set = paramMap.keySet();
        for (Iterator<String> i = set.iterator(); i.hasNext(); ) {
            String key = (String) i.next();
            params.put(key, paramMap.get(key));
        }
        Response response = OkGo
                .post(url)
                .tag("")
                .params(params)
                .execute();
        Log.d("@@","@"+response.body().toString());
        return response.body().string();
    }
}