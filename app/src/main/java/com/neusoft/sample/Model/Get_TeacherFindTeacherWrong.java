package com.neusoft.sample.Model;



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
public class Get_TeacherFindTeacherWrong {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static String getStringCha(String url, Map<String, String> paramMap) throws IOException {
        //这是okttp向服务器端传值的转换写法
        HttpParams params = new HttpParams();
        Set<String> set = paramMap.keySet();
        for (Iterator<String> i = set.iterator(); i.hasNext(); ) {
            String key = (String) i.next();
            params.put(key, paramMap.get(key));
        }
        Response response = OkGo
                .get(url)
                .tag("")
                //将转化后的  取出的hashmap的数据通过  okttp  的传送方式传送的服务器
                .params(params)
                .execute();
        //将从服务器获取到的值   作为返回值  直接返回   然后在activity处进行解析与使用
        return response.body().string();
    }
}