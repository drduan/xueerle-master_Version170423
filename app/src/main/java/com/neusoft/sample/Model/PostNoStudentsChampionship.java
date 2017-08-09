package com.neusoft.sample.Model;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.neusoft.sample.Ctrl.Db_XTCSGJService;
import com.neusoft.sample.Ctrl.Json.StringUtil;
import com.neusoft.sample.Ctrl.Json.VolleyUtil;
import com.neusoft.sample.GreenDao.XTCSGJ;
import com.neusoft.sample.util.ContextHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangyujie on 2016/7/12.
 */
public class PostNoStudentsChampionship {
     public static int back;

    public static void PostLink(final Context context, String target, final HashMap<String, String> hmap){

        VolleyUtil.getQueue(context);
        StringRequest  request=new StringRequest(Request.Method.POST, StringUtil.preUrl(target.toString().trim()),
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("Post", response);
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    back = (int) object.get("success");
                    Db_XTCSGJService.getInstance(ContextHolder.getContext()).deleteAllNote();
                    if (back==200)
                    {

                        JSONObject jsonObject= (JSONObject) object.get("data");
                        String test_group_number= (String) jsonObject.get("test_group_number");
                        String user_id= (String) jsonObject.get("user_id");
                        int time= (int) jsonObject.get("time");

//                        String tableName= (String) jsonObject.get("tableName");
                        XTCSGJ xtcsgj=new XTCSGJ();
//                        xtcsgj.setTableName(tableName);
                        xtcsgj.setTest_group_number(test_group_number);
                        xtcsgj.setUser_id(user_id);
                        xtcsgj.setTime(time);
                        Db_XTCSGJService.getInstance(ContextHolder.getContext()).saveNote(xtcsgj);


                       Log.d("上传整组测试结果（不含错题列表）","上传数据成功:200");
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
                return hmap;
            }
        };
        VolleyUtil.getQueue(context).add(request);


    }

}
