package com.neusoft.sample.Model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by 杨康 on 2016/7/23.
 */

public class Consant_stringg {
   

    static String num_2;//后两位编号
    static String chengyu;//传递过来的成语
    static String url_pdf;//成语辨析路径名称
    static int aa;

   

    public static  String downlowd_out = "http://122.156.218.189:8080/Xrl/resources/xrlRes/2/45/bishun/";
    static int a = 0;
    public static String getString(String ff){
        ff =ff.replaceAll("\"","");
        if(ff.equals("，")||ff.equals("。")||ff.equals(".")){
            return ff;
        }else if(ff.contains(" ")){
            return ff ;
        }
        else  {
         ff = "    "+ff;
        }
        return ff ;
    }
    public static int is_contain_num(String chiose){
        if(chiose.contains("0")||chiose.contains("1")||chiose.contains("2")||chiose.contains("3")
                ||chiose.contains("4")||chiose.contains("5")||chiose.contains("6")||chiose.contains("7")
                ||chiose.contains("8")||chiose.contains("9")){
//            if(!chiose.contains("a")||)
            a = 1;
        }
        return a;
    }
    ///////////////////////判断网络是否链接//////////////////////////////////////////
    public static boolean is_internet(Context context){
        if (context != null){
            ConnectivityManager mconnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mconnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null){
                return  mNetworkInfo.isAvailable();
            }
        }
        return  false;
    }
    public static String yanzhengma(){
        String str = "";
        str += (int)(Math.random()*9+1);
        for(int i = 0; i < 5; i++){
            str += (int)(Math.random()*10);
        }
        return str;
    }

    public static void set_bianxi(String u1,String u2,String u3,int a){
        num_2 = u1;
        chengyu = u2;
        url_pdf = u3;
        aa = a;
    }

    public static String getNum_2() {
        return num_2;
    }

    public static String getChengyu() {
        return chengyu;
    }

    public static String getUrl_pdf() {
        return url_pdf;
    }
    public static int getA() {
        return aa;
    }
}
