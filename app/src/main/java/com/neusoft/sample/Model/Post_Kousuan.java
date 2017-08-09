package com.neusoft.sample.Model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.neusoft.sample.Ctrl.Db_CaculationTest;
import com.neusoft.sample.Ctrl.Json.StringUtil;
import com.neusoft.sample.Ctrl.Json.VolleyUtil;
import com.neusoft.sample.GreenDao.CalculationTest;
import com.neusoft.sample.View.users.Xel_Users_Register;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/10.
 */
public class Post_Kousuan {
    static String success;

    public static void Linkage(final Context context, String target, final Map<String, String> paramMap) {

        VolleyUtil.getQueue(context).cancelAll(Xel_Users_Register.TAG);
        Response.Listener<String> listener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                /**
                 * 返回的数据是存取到本地数据库
                 */
                Log.d("Post", response);

//                    JSONObject jsonObject = new JSONObject(response);
                    Map<String, Object> map = (Map<String, Object>) JSON.parse(response);

                    success= (String) map.get("success");
                    if (success.equals("200")) {
                        String ct =  map.get("data").toString();
                        Log.d("OO", ct.toString());
                        List<CalculationTest> list = JSON.parseArray(ct, CalculationTest.class);
                        Log.d("OO12", JSON.toJSONString(list));
                        Db_CaculationTest.getInstance(context).saveNoteLists(list);
                    }
                    else {
                        Log.d("success", "查询不到结果");

                    }


            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                Log.d("Post_user", "请求失败" + arg0);
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
