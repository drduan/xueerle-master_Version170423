package com.neusoft.sample.Model;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.neusoft.sample.Ctrl.Db_StudyGoodInfoService;
import com.neusoft.sample.Ctrl.Db_StudyGoodItemService;
import com.neusoft.sample.Ctrl.Db_StudyGoodTermService;
import com.neusoft.sample.Ctrl.Db_TextOneStructureService;
import com.neusoft.sample.Ctrl.Db_TextTwoStructureService;
import com.neusoft.sample.Ctrl.Db_UserService;
import com.neusoft.sample.Ctrl.Json.StringUtil;
import com.neusoft.sample.Ctrl.Json.VolleyUtil;
import com.neusoft.sample.GreenDao.StudyGoodInfo;
import com.neusoft.sample.GreenDao.StudyGoodItem;
import com.neusoft.sample.GreenDao.StudyGoodTerm;
import com.neusoft.sample.GreenDao.TextOneStructure;
import com.neusoft.sample.GreenDao.TextTwoStructure;
import com.neusoft.sample.GreenDao.User;
import com.neusoft.sample.View.users.Xel_Users_Register;
import com.neusoft.sample.View.users.xel_user_login;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/29.
 */
public class PostUser {
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

        VolleyUtil.getQueue(context).cancelAll(Xel_Users_Register.TAG);
        Response.Listener<String> listener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                /**
                 * 返回的数据是存取到本地数据库
                 */

                Log.d("Post", response);


                try {

                    JSONObject object = new JSONObject(response);
                    success = (String) object.get("success");
                    if (success.equals("200")) {




                        Server_id = (String) object.get("user_id");
                        Map<String, Object> map = (Map<String, Object>) JSON.parse(response);
                        //***********************************************************************************************************************************
                        //sgtlist是关联jclist与sglist的中间
                        String sgtlist = object.get("Sgtlist").toString();
                        //根据查
                        List<StudyGoodItem> studyGoodItemList = JSON.parseArray(sgtlist, StudyGoodItem.class);
                        Db_StudyGoodItemService.getInstance(context).saveNoteLists(studyGoodItemList);

                        //***********************************************************************************************************************************
                        //教材的存储数据库
                        String Jclist = map.get("Jclist").toString();  //教材
                        Log.d("POST","教材的数据"+Jclist);
                        List<StudyGoodTerm> studyGoodTermList = JSON.parseArray(Jclist, StudyGoodTerm.class);
                        for (StudyGoodTerm term : studyGoodTermList) {

                            if (term!=null) {
                                Db_StudyGoodTermService.getInstance(context).saveNote(term);
                            }
                        }

                        //***********************************************************************************************************************************
                        //学习商品的存储数据库
                        String Sglist = map.get("Sglist").toString(); //学习商品列表
                        List<StudyGoodInfo> studyGoodInfoList = JSON.parseArray(Sglist, StudyGoodInfo.class);

                        for (StudyGoodInfo info : studyGoodInfoList) {
                            if (info!=null) {
                                List<User> list = Db_UserService.getInstance(context).loadAllNote();
                                for (User user : list) {

                                    if (user.getPhone().equals(phone)) {
                                        user.setProductNo(info.getProductNo());
                                        Db_UserService.getInstance(context).saveNote(user);
                                    }
                                }
                                Db_StudyGoodInfoService.getInstance(context).saveNote(info);
                            }
                        }
                        Log.d("POST","IS SUCESSFULLY");
                        //***********************************************************************************************************************************
                        //教材一级结构的存储数据库
                        String JcOneNumber = map.get("JcOneNumber").toString();
                        int JcOneNum = Integer.parseInt(JcOneNumber);//获取当前的一级结构有多少个
                        for (int i = 0; i < JcOneNum-1; i++) {
                            int j = i + 1;
                            String one = "JcOnelist" + j;
                            String JcOneNumbers  = map.get(one).toString();
                            List<TextOneStructure> textOneStructureList = JSON.parseArray(JcOneNumbers, TextOneStructure.class);

                                Db_TextOneStructureService.getInstance(context).saveNoteLists(textOneStructureList);

             }

                        //***********************************************************************************************************************************
                        //教材二级结构的存储数据库

                        String JcTwoNumber = map.get("JcTwoNumber").toString();
                        int JcTwoNum = Integer.parseInt(JcTwoNumber);//获取当前的一级结构有多少个
                        for (int i = 0; i < JcTwoNum; i++) {
                            int j = i + 1;
                            String one = "JcTwolist" + j;
                            Log.d("POST", "教材二级结构的数据" + one);
                            if (map.get(one)!=null) {
                                String JcOneNumbers  = map.get(one).toString();
                                Log.d("POST", "满足教材二级结构的数据" + JcOneNumbers);
                                List<TextTwoStructure> textTwoStructureslist = JSON.parseArray(JcOneNumbers, TextTwoStructure.class);
                                Log.d("POST", "教材二级结构的数据list" + textTwoStructureslist.toString());

                                    Db_TextTwoStructureService.getInstance(context).saveNoteLists(textTwoStructureslist);

                            }
                        }


                        List<User> userList = Db_UserService.getInstance(context).loadAllNote();
                        for (User user : userList) {
                            if (user.getPhone().equals(phone)) {
                                user.setServer_id(Server_id);
                                user.setProductNo(studyGoodInfoList.get(0).getProductNo());
                                Db_UserService.getInstance(context).saveNote(user);
                               break;
                            }
                        }

                        context.startActivity(new Intent().setClass(context, xel_user_login.class));
                        dialog.dismiss();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(context, ""+object.get("error"), Toast.LENGTH_LONG).show();


                        Log.e("PostUser_onRepsonse", "接收异常，返回值不为200");
                        Db_UserService.getInstance(context).deleteAllNote();


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
                Toast.makeText(context, "注册失败，请查看网络连接状态！", Toast.LENGTH_LONG).show();
                Log.e("PostUser_onRepsonse", "接收异常，返回值不为200");
                Db_UserService.getInstance(context).deleteAllNote();

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
