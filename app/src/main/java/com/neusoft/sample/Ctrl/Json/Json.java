package com.neusoft.sample.Ctrl.Json;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/6/29.
 */
public class Json {

    public static void GetJson(String url) {
        // 发起请求
//TODO null
        JsonObjectRequest request = new JsonObjectRequest(StringUtil.preUrl(url), null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {

            }
        });
        // 请求加上Tag,用于取消请求
    }
}
