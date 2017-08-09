package com.neusoft.sample.Model;

import android.content.Context;
import android.content.SharedPreferences;

import static com.neusoft.sample.util.ContextHolder.getContext;

/**
 * Created by Administrator on 2016/7/10.
 */
public class MsharedPrefrence {
    static String lujing;
//是否重做
public static void SetisDoGain(Context context) {
    SharedPreferences.Editor editor = context.getSharedPreferences("do_gain_flag", getContext().MODE_PRIVATE).edit();
    editor.putString("flag", "doagain").commit();
}

    public static void SetvoidDoGain(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences("do_gain_flag", getContext().MODE_PRIVATE).edit();
        editor.putString("flag", "").commit();
    }

    public static String GetisDoGain(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("do_gain_flag", getContext().MODE_PRIVATE);
        String flag = preferences.getString("flag", "");
        return flag;
    }
    //记住密码
    public static String[] Getphonewsd(Context context) {
        String[] strings=new String[4];
        getContext();
        SharedPreferences preferences=context.getSharedPreferences("phonewsd", getContext().MODE_PRIVATE);
        String phone=preferences.getString("phone", "");
        String wsd=preferences.getString("wsd", "");
        String role=preferences.getString("role", "");
        strings[0]=phone;
        strings[1]=wsd;
        strings[2]=role;
        return strings;
    }
    public static  void  Setphonewsd(Context context,String phone,String wsd,String role)
    {
        SharedPreferences.Editor editor= context.getSharedPreferences("phonewsd", getContext().MODE_PRIVATE).edit();
        editor.putString("phone",phone);
        editor.putString("wsd",wsd);
        editor.putString("role",role);
        
        editor.commit();
    }
    public static String[] GetUserInfo(Context context){
        String[] strings=new String[12];
        SharedPreferences preferences=context.getSharedPreferences("userInfo", getContext().MODE_PRIVATE);
        String user_icon_url=preferences.getString("user_icon_url", "");
        String phone=preferences.getString("phone", "");
        String motto=preferences.getString("motto", "");
        String gender=preferences.getString("gender", "");
        String qq_number=preferences.getString("qq_number", "");
        String weixin_number=preferences.getString("weixin_number", "");
        String email=preferences.getString("email", "");
        String recipient=preferences.getString("recipient", "");
        String address=preferences.getString("address", "");
        String zip_code=preferences.getString("zip_code", "");
        strings[0]=user_icon_url;
        strings[1]=phone;
        strings[2]=motto;
        strings[3]=gender;
        strings[4]=qq_number;
        strings[5]=weixin_number;
        strings[6]=email;
        strings[7]=recipient;
        strings[8]=recipient;
        strings[9]=address;
        strings[10]=zip_code;
        
        return strings;
    }
    public static  void  SetUserInfo(Context context,String user_icon_url,String phone,String motto,String gender,String qq_number,String weixin_number,String email,String recipient,String address,String zip_code)
    {
        SharedPreferences.Editor editor= context.getSharedPreferences("userInfo", getContext().MODE_PRIVATE).edit();
        editor.putString("user_icon_url",user_icon_url);
        editor.putString("phone",phone);
        editor.putString("motto",motto);
        editor.putString("gender",gender);
        editor.putString("qq_number",qq_number);
        editor.putString("weixin_number",weixin_number);
        editor.putString("email",email);
        editor.putString("recipient",recipient);
        editor.putString("address",address);
        editor.putString("zip_code",zip_code);
        editor.commit();
    }
    
    
    public static String[] GetUserisFirstLoading(Context context){
        String[] strings = new String[3];
        SharedPreferences preferences=context.getSharedPreferences("UserFirstLoading", getContext().MODE_PRIVATE);
        String mphone = preferences.getString("phone","");
        String first = preferences.getString("first","");
        strings[0] =mphone;
        strings[1] =first;
        return strings;
    }

    public static  void  SetUserisFirstLoading(Context context,String mphone,String first){
        SharedPreferences.Editor editor= context.getSharedPreferences("UserFirstLoading", getContext().MODE_PRIVATE).edit();
        editor.putString("phone",mphone);
        editor.putString("first",first);
        editor.commit();
    }

    public static String[] GetUserUserTempImgDir(Context context){
        String[] strings = new String[2];
        SharedPreferences preferences=context.getSharedPreferences("UserFirstLoading", getContext().MODE_PRIVATE);
        String mphone = preferences.getString("phone","");
        String imgDir = preferences.getString("first","");
        strings[0] =mphone;
        strings[1] =imgDir;
        return strings;
    }

    public static void SetUserTempImgDir(Context context,String mphone,String imgDir){
        SharedPreferences.Editor editor= context.getSharedPreferences("UserFirstLoading", getContext().MODE_PRIVATE).edit();
        editor.putString("phone",mphone);
        editor.putString("first",imgDir);
        editor.commit();
        lujing = imgDir;
    }


    public static String getLujing() {

        return lujing;
    }
}








