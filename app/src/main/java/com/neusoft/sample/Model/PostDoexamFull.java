package com.neusoft.sample.Model;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.neusoft.sample.Ctrl.Json.StringUtil;
import com.neusoft.sample.Ctrl.Json.VolleyUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Administrator on 2016/7/12.
 */
public class PostDoexamFull {
     public static String back="";
    public static void PostLink(final Context context, String target, final Map<String, String> paramMap){
        VolleyUtil.getQueue(context);
        StringRequest  request=new StringRequest(Request.Method.POST, StringUtil.preUrl(target.toString().trim()),
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("Post", response);
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    back = (String) object.get("success");
                    if (back.equals("200"))
                    {
                       Log.d("上传整组测试结果（含错题列表）","上传数据成功:200");
                    }
                    else {
                     Log.d("上传整组测试结果（含错题列表）","上传数据失败100");
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("onError",error.toString());

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map=paramMap;
                Log.d("success","do success");
                return map;
            }
        };
        VolleyUtil.getQueue(context).add(request);


    }

}
