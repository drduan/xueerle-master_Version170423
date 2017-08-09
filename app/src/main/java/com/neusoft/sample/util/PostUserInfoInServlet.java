package com.neusoft.sample.util;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.neusoft.sample.App;
import com.neusoft.sample.Ctrl.Db_UserService;
import com.neusoft.sample.Ctrl.wenchengcheng.user_get;
import com.neusoft.sample.GreenDao.User;
import com.neusoft.sample.Model.Constant;
import com.neusoft.sample.Model.MsharedPrefrence;
import com.neusoft.sample.Model.Post_learn_rijiyuelei;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static com.neusoft.sample.Model.MsharedPrefrence.Getphonewsd;
import static com.neusoft.sample.View.Fragment.xel_mine_fragment.cUserInfo;
import static com.neusoft.sample.View.Fragment.xel_mine_fragment.sPhone;
import static com.neusoft.sample.View.Fragment.xel_mine_fragment.tUserInfo;

/**
 * Created by AstroBoy on 2017/1/11.
 */

public class PostUserInfoInServlet {
    private final static String TAG = "PostUserInfoInServlet";
    private user_get userss;
    private Handler handler = new Handler();
    private String user_icon_url, phone, mobile, motto, gender, qq_number, weixin_number, email, recipient, address, zip_code;
    private String postUrl = null;
    private String Query_ID = null;

    public PostUserInfoInServlet() {
        Ini_Post_userInfo();
    }

    public void Ini_Post_userInfo() {
        Query_ID = App.newInstance().GetSharePrefrence_kejiezu(ContextHolder.getContext());//获取当前登陆用户的ID
        final HashMap<String, String> map = new HashMap<>();
        map.put("user_id", Query_ID);
        Log.d(TAG, map.toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("@@", "dayinmap" + map);
                String getInfo;
                try {
                    getInfo = Post_learn_rijiyuelei.getStringCha(Constant.post_get_user, map);
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("response", getInfo);
                    message.setData(bundle);
                    handler.sendMessage(message);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Ini_Post_userInfo_Handler();
    }

    public void Ini_Post_userInfo_Handler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String response = msg.getData().getString("response");
                try {
                    JSONObject json = new JSONObject(response);
                    Log.d(TAG, "Json内容" + json);
                    JSONObject data = json.getJSONObject("data");
                    Log.d("@@", "请求信息成功 data" + data);
                    //setUserInfo();//在数据库中查询当前用户以在存储网络数据
                    userss = JSON.parseObject(String.valueOf(data),
                            user_get.class);
                    Log.d("@@", "请求信息成功" + data);
                    if (userss.getUser_icon_url() != null) {
                        postUrl = Constant.selectUserMessage + "/" + userss.getUser_icon_url();
                    }
                    user_icon_url = userss.getUser_icon_url();
                    phone = String.valueOf(userss.getPhone());
                    mobile = String.valueOf(userss.getPhone());
                    motto = userss.getMotto();
                    gender = userss.getGender();
                    qq_number = userss.getQq_number();
                    weixin_number = userss.getWeixin_number();
                    email = userss.getEmail();
                    recipient = userss.getRecipient();
                    address = userss.getAddress();
                    zip_code = String.valueOf(userss.getZip_code());

                    String[] sharePerf = Getphonewsd(ContextHolder.getContext());
                    sPhone = sharePerf[0];
                    List<User> userInfo = Db_UserService.getInstance(ContextHolder.getContext()).loadAllNote();
                    Log.d(TAG, "list.size()===" + userInfo.size());
                    //int state = 100;
                    Log.d(TAG, "PerPhone" + phone);
                    for (User user : userInfo) {
                        if (user.getServer_id().equals(Query_ID)) {
                            Log.d(TAG, "存之前的cUserInfo:" + user);
                            cUserInfo = user;
                            cUserInfo.setUser_icon_url(user_icon_url);
                            cUserInfo.setPhone(phone);
                            cUserInfo.setMobile(mobile);
                            cUserInfo.setMotto(motto);
                            cUserInfo.setGender(gender);
                            cUserInfo.setQq_Number(qq_number);
                            cUserInfo.setWeixin_number(weixin_number);
                            cUserInfo.setEmail(email);
                            cUserInfo.setRecipient(recipient);
                            cUserInfo.setAddress(address);
                            cUserInfo.setZip_code(zip_code);
                            Db_UserService.getInstance(ContextHolder.getContext()).saveNote(cUserInfo);
                            getUserInfo(cUserInfo);
                            Log.d(TAG, "存好的cUserInfo:" + cUserInfo);
                        }
                    }

                    MsharedPrefrence.SetUserInfo(ContextHolder.getContext(), postUrl, phone, motto, gender, qq_number, weixin_number, email, recipient, address, zip_code);
                    //Toast.makeText(getContext(), "用户信息同步成功!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "已经将网络请求好的数据保存至本地数据库");

                } catch (JSONException e) {
                    String err = String.valueOf(e);
                    Toast.makeText(ContextHolder.getContext(), "未成功同步个人信息,错误" + err, Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    public User getUserInfo(User cUserInfo) {
        tUserInfo = cUserInfo;
        return tUserInfo;
    }

}
