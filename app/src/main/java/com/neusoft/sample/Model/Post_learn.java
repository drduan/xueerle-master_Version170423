package com.neusoft.sample.Model;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.neusoft.sample.Ctrl.Json.StringUtil;
import com.neusoft.sample.Ctrl.Json.VolleyUtil;
import com.neusoft.sample.View.users.Xel_Users_Register;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/10.
 */
public class Post_learn {
    public static void Linkage(final Context context, String target, final Map<String, String> paramMap) {
        //取消之前的  序列请求
        VolleyUtil.getQueue(context).cancelAll(Xel_Users_Register.TAG);
        final HashMap<String,String> map = new HashMap<String,String>();
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                /**
                 * 返回的数据是存取到本地数据库
                 */
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray ct = (JSONArray) jsonObject.get("Ur1");
                    String ct_str = ct.toString();
                    JSONArray ct1 = (JSONArray) jsonObject.get("Ur1");
                    String ct1_str = ct.toString();
                    JSONArray ct2 = (JSONArray) jsonObject.get("Ur1");
                    String ct2_str = ct.toString();
                    map.put("uri",ct_str);
                    map.put("ur1",ct1_str);
                    map.put("ur2",ct2_str);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
            }
        };

        final StringRequest request = new StringRequest(Request.Method.POST, StringUtil.preUrl(target.toString().trim()), listener, errorListener) {

            //重写getParams设置post请求的参数
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> paramMaps;
                paramMaps = paramMap;
                return paramMaps;
            }
        };
        // 请求加上Tag,用于取消请求
        request.setTag(context);
        VolleyUtil.getQueue(context).add(request);
    }
}
