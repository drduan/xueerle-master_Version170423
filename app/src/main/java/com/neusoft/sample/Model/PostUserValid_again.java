package com.neusoft.sample.Model;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.neusoft.sample.App;
import com.neusoft.sample.Ctrl.Db_UserService;
import com.neusoft.sample.Ctrl.Json.StringUtil;
import com.neusoft.sample.Ctrl.Json.VolleyUtil;
import com.neusoft.sample.GreenDao.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/29.
 */
public class PostUserValid_again {
    static String Server_id;
    static String success;

    /**
     * 这里未做后台发回错误数据时相应操作
     * @param context
     * @param phone
     * @param target
     * @param paramMap
     * @param dialog
     */
    public static void Linkage(final Context context, final String phone, String target, final Map<String, String> paramMap, final ProgressDialog dialog) {


        Response.Listener<String> listener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                /**
                 * 返回的数据是存取到本地数据库
                 */

                Log.d("Post", response);
                Log.d("SUCESS","*********************************");

                try {

                    JSONObject object = new JSONObject(response);
                    success = (String) object.get("success");
                    if (success.equals("200")) {
                        JSONObject jsonObject= (JSONObject) object.get("data");
                        String class_number= (String) jsonObject.get("class_number");
                        String user_id=(String) jsonObject.get("user_id");
                        String shengName=(String) jsonObject.get("shengName");
                        String shiName=(String) jsonObject.get("shiName");
                        String quName=(String) jsonObject.get("quName");
                        String schoolName=(String) jsonObject.get("schoolName");
                        List<User> userList = Db_UserService.getInstance(context).loadAllNote();
                        for (User user : userList) {
                            if (user.getPhone().equals(phone)) {
                                user.setServer_id(user_id);
                                Log.d("省份值", class_number.substring(0, 2));
                                user.setProvince(shengName);
                                user.setProvince_nub(class_number.substring(0, 2));
                                Log.d("城市值", class_number.substring(0, 4));
                                user.setCity(shiName);
                                user.setCity_nub(class_number.substring(0, 4));

                                Log.d("地区值", class_number.substring(0, 6));
                                user.setRegion(quName);
                                user.setRegion_nub(class_number.substring(0, 6));
                                Log.d("学校值", class_number.substring(0, 8));
                                user.setSchool(schoolName);
                                user.setSchool_nub(class_number.substring(0, 8));
                                Log.d("年级值", class_number.substring(0, 10));
                                user.setGrade(class_number.substring(0, 10));
                                user.setGrade_nub(class_number.substring(0, 10));
                                Log.d("班级值", class_number.substring(0, 12));
                                user.setClasses(class_number.substring(0, 12));
                                user.setClasses_nub(class_number.substring(0, 12));

                                Db_UserService.getInstance(context).deleteAllNote();
                                Db_UserService.getInstance(context).saveNote(user);
                                App.GetSharePrefrence(context, phone);
                                Map<String, String> paramMaps =new HashMap<>();
                                List<User> list2 = Db_UserService.getInstance(context).loadAllNote();//user表的list
                                for (User users1 : list2) {

                                    if (users1.getPhone().equals(phone)) {//手机存在
                                        paramMaps.put("user_id", users1.getServer_id());
                                    }
                                }
                                Log.d("Test_", "走到了");


                                MsharedPrefrence.SetUserisFirstLoading(context,phone,"0");

                                return;

                            }
                        }
                    } else {
                       //TODO 不是合法用户，重置用户名密码角色
                        /**
                         * 可能会出错
                         */
                    dialog.dismiss();
                        Toast.makeText(context,"当前用户没有注册！！",Toast.LENGTH_SHORT).show();
                        Log.d("PostUserValid","当前用户没有注册");
                        List<User> userList = Db_UserService.getInstance(context).loadAllNote();
                        for (User user : userList) {
                            if (user.getPhone().equals(phone)) {

                                Db_UserService.getInstance(context).deleteNote(user);
                                return;
                            }
                        }

                    }






                } catch (JSONException e) {
                    e.printStackTrace();
                }





            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                Log.d("Post_user", "请求失败" + arg0);
                dialog.dismiss();
                Toast.makeText(context,"网络请求失败！",Toast.LENGTH_SHORT).show();
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
        //setRetryPolicy自定义请求超时，重试失败的请求
        request.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 1, 1.0f));
        // 请求加上Tag,用于取消请求
        request.setTag(context);

        VolleyUtil.getQueue(context).add(request);
    }


}
