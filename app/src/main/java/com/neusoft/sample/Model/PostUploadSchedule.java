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
import com.neusoft.sample.Ctrl.Db_StudyGoodTermService;
import com.neusoft.sample.Ctrl.Db_TextOneStructureService;
import com.neusoft.sample.Ctrl.Db_TextTwoStructureService;
import com.neusoft.sample.Ctrl.Json.StringUtil;
import com.neusoft.sample.Ctrl.Json.VolleyUtil;
import com.neusoft.sample.GreenDao.StudyGoodTerm;
import com.neusoft.sample.GreenDao.TextOneStructure;
import com.neusoft.sample.GreenDao.TextTwoStructure;
import com.neusoft.sample.View.BottomTabSwitcherActivity;
import com.neusoft.sample.View.users.Xel_Users_Register;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/29.
 */
public class PostUploadSchedule {
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
                    if (!success.equals("200")) {
                        //数据库中现在不是最新的需要对数据进行替换
                        //*****************************************************************************************
                        Db_StudyGoodTermService.getInstance(context).deleteAllNote();
                        Db_TextOneStructureService.getInstance(context).deleteAllNote();
                        Db_TextTwoStructureService.getInstance(context).deleteAllNote();
                        Log.d("教材表现有的数据",Db_StudyGoodTermService.getInstance(context).loadAllNote().toString());
                        Log.d("一级结构表现有的数据",Db_TextOneStructureService.getInstance(context).loadAllNote().toString());
                        Log.d("二级结构表现有的数据",Db_TextOneStructureService.getInstance(context).loadAllNote().toString());






                        //*****************************************************************************************

                        Map<String, Object> map = (Map<String, Object>) JSON.parse(response);
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
                        //教材一级结构的存储数据库
                        String JcOneNumber = map.get("JcOneNumber").toString();
                        int JcOneNum = Integer.parseInt(JcOneNumber);//获取当前的一级结构有多少个
                        for (int i = 0; i < JcOneNum-1; i++) {
                            int j = i + 1;
                            String one = "JcOnelist" + j;
                            String JcOneNumbers  = map.get(one).toString();
                            List<TextOneStructure> textOneStructureList = JSON.parseArray(JcOneNumbers, TextOneStructure.class);

                            for (TextOneStructure structure : textOneStructureList) {
                                Db_TextOneStructureService.getInstance(context).saveNote(structure);
                            }

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
                                for (TextTwoStructure structure : textTwoStructureslist) {
                                    Db_TextTwoStructureService.getInstance(context).saveNote(structure);
                                }
                            }
                        }


                        context.startActivity(new Intent().setClass(context, BottomTabSwitcherActivity.class));


                    } else {
                        Log.d("Post_UploadSchedule", "教材是全新的");
                       context.startActivity(new Intent(context, BottomTabSwitcherActivity.class));
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

        // 请求加上Tag,用于取消请求
        request.setTag(context);

        VolleyUtil.getQueue(context).add(request);
    }


}
