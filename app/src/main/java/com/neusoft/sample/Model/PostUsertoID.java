package com.neusoft.sample.Model;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
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
import com.neusoft.sample.View.BottomTabSwitcherActivity;
import com.neusoft.sample.View.users.xel_user_login;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/29.
 */
public class PostUsertoID {
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

        VolleyUtil.getQueue(context).cancelAll(xel_user_login.TAG);
        VolleyUtil.getQueue(context).start();
        //  取消之前的网络请求
        Response.Listener<String> listener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("Test_2","走到了");
                Log.d("上传用户的ID,返回信息",response);
                try {

                    ///解析  第一层
                    if (response!=null) {
                        JSONObject object = new JSONObject(response);
                        success = (String) object.get("success");
                        if (success.equals("200")) {

                            //删除上次用户的全部内容
//
//                        Db_StudyGoodTermService.getInstance(context).deleteAllNote();
//                        Db_StudyGoodItemService.getInstance(context).deleteAllNote();;
//                        Db_StudyGoodInfoService.getInstance(context).deleteAllNote();
//                        Db_TextOneStructureService.getInstance(context).deleteAllNote();
//                        Db_TextTwoStructureService.getInstance(context).deleteAllNote();


//                        Server_id = (String) object.get("user_id");
                            //  快速  将  字符串转换成   hasmap
                            Map<String, Object> map = (Map<String, Object>) JSON.parse(response);
                            //***********************************************************************************************************************************
                            //sgtlist是关联jclist与sglist的中间

                            //   第二层解析
                            String sgtlist = object.get("Sgtlist").toString();
                            //根据查


                            List<StudyGoodItem> studyGoodItemList = JSON.parseArray(sgtlist, StudyGoodItem.class);
                            Db_StudyGoodItemService.getInstance(context).saveNoteLists(studyGoodItemList);

                            //***********************************************************************************************************************************
                            //教材的存储数据库
                            String Jclist = map.get("Jclist").toString();  //教材
                            Log.d("POST", "教材的数据" + Jclist);
                            List<StudyGoodTerm> studyGoodTermList = JSON.parseArray(Jclist, StudyGoodTerm.class);
                            for (StudyGoodTerm term : studyGoodTermList) {

                                if (term != null) {
                                    // 在这里 写入 教材是否免费 字段
                                    HttpParams params = new HttpParams();
                                    params.put("user_id","");
                                    params.put("good_no","");
                                    OkGo.<String>post(Constant.ht_ip+"")
                                            .tag(this)//
                                            .cacheKey("cacheKey")
                                            .params(params)
                                            .execute( new StringCallback(){
                                                @Override
                                                public void onSuccess(com.lzy.okgo.model.Response<String> response) {

                                                }

                                                @Override
                                                public void onError(com.lzy.okgo.model.Response<String> response) {
                                                    super.onError(response);
                                                }
                                            });
                                    Db_StudyGoodTermService.getInstance(context).saveNote(term);
                                }
                            }

                            //***********************************************************************************************************************************
                            //学习商品的存储数据库
                            String Sglist = map.get("Sglist").toString(); //学习商品列表
                            List<StudyGoodInfo> studyGoodInfoList = JSON.parseArray(Sglist, StudyGoodInfo.class);

                            for (StudyGoodInfo info : studyGoodInfoList) {
                                if (info != null) {
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
                            Log.d("POST", "IS SUCESSFULLY");
                            //***********************************************************************************************************************************
                            //教材一级结构的存储数据库
                            String JcOneNumber = map.get("JcOneNumber").toString();
                            int JcOneNum = Integer.parseInt(JcOneNumber);//获取当前的一级结构有多少个
                            for (int i = 0; i < JcOneNum - 1; i++) {
                                int j = i + 1;
                                String one = "JcOnelist" + j;
                                String JcOneNumbers = map.get(one).toString();
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
                                if (map.get(one) != null) {
                                    String JcOneNumbers = map.get(one).toString();
                                    List<TextTwoStructure> textTwoStructureslist = JSON.parseArray(JcOneNumbers, TextTwoStructure.class);

                                        Db_TextTwoStructureService.getInstance(context).saveNoteLists(textTwoStructureslist);

                                }
                            }


                            context.startActivity(new Intent().setClass(context, BottomTabSwitcherActivity.class));
                            MsharedPrefrence.SetUserisFirstLoading(context,phone,"0");

                        } else {
                            dialog.dismiss();
                            Toast.makeText(context, "请求失败，请查看网络连接状态！", Toast.LENGTH_LONG).show();
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
                Log.d("Test_1","走到了");
                return paramMaps;
            }


            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {

                if (response.headers == null)
                {
                    // cant just set a new empty map because the member is final.
                    response = new NetworkResponse(
                            response.statusCode,
                            response.data,
                            Collections.<String, String>emptyMap(), // this is the important line, set an empty but non-null map.
                            response.notModified,
                            response.networkTimeMs);


                }

                return super.parseNetworkResponse(response);

            }
        };

        // 请求加上Tag,用于取消请求
        request.setTag(context);

        VolleyUtil.getQueue(context).add(request);
    }


}
