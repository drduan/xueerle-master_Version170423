package com.neusoft.sample.Model;

import android.content.Context;
import android.util.Log;

import com.neusoft.sample.Ctrl.Db_UserService;
import com.neusoft.sample.GreenDao.User;

import java.util.List;

/**
 * Created by 杨康 on 2016/8/9.
 */
public class Stitching {
    static Context context;
    public static String ht_ip =  "http://122.156.218.189:8080/Xrl/resources/xrlRes/";//服务器地址
    public static String ip_chengyu = ht_ip+"2/99/idiom/";//成语接龙
    static String Chapter = "";//教材编号
    static String qian_5 = "";//教材编号前5位
    static String hou_2 = "";//教材编号后两位
    static String hou_1 = "";//教材编号后一位
    static String hou_4_2 = "";//教材编号后4--2
    static String t = "/";
    static String html = ".html";
    static String mp3 = ".mp3";
    static String png = ".png";
    static String [] web_http ={} ;
    static String name = "";//章节名称
    static String grade;//年级



    /*传递章节编号目录的教材编号*/
    public  static void post_Chapter(String Chapter_){
        Chapter = Chapter_;
        Log.d("@@","  cc  "+Chapter);
        qian_5 = Chapter.substring(0,5);
        hou_2 = Chapter.substring(Chapter.length()-2,Chapter.length());
        hou_1 = Chapter.substring(Chapter.length()-1,Chapter.length());
        hou_4_2= Chapter.substring(Chapter.length()-4,Chapter.length()-2);
        
        
//        判断年级
        List<User> users = Db_UserService.getInstance(context).loadAllNote();
        for(User user:users){
          grade =  user.getGrade_nub().substring(user.getGrade_nub().length());
            Log.d("@@","grade"+grade);
        }
    }
    /*章名*/
    public static void post_name(String ne){
        name = ne;
    }
    /*判断的是此账属于几年级*/
    
    
    /*
    * 拼接路径*/
    public  static  String [] getRoute(String Chapter){
        
         hou_4_2 =  Chapter.substring(Chapter.length()-4,Chapter.length()-2);
        Log.d("@@","jiaocai"+Chapter+"gg"+hou_4_2);
         qian_5 = Chapter.substring(0,5);
         hou_2 = Chapter.substring(Chapter.length()-2,Chapter.length());
         hou_1 = Chapter.substring(Chapter.length()-1,Chapter.length());
        if(Chapter.substring(0,1).equals("9")){
           // String  url_ui_web = ht_ip+"2/41/"+qian_5+t+"41"+Chapter+html;//课文原文web
            String  url_ui_web = ht_ip+"0/fengmian/"+hou_4_2+png;
            String chi_kewen   = ht_ip+"2/42/"+qian_5+t+"42"+Chapter+mp3;//课文原文语音
            String chi_slow_zi = ht_ip+"2/43/"+qian_5+t+"43"+Chapter+"0"+mp3;//生字听写慢速
            String chi_speed_zi= ht_ip+"2/43/"+qian_5+t+"43"+Chapter+"1"+mp3;//生字听写中速
            String chi_slow_ci = ht_ip+"2/44/"+qian_5+t+"44"+Chapter+"0"+mp3;//词组听写慢速
            String chi_speed_ci = ht_ip+"2/44/"+qian_5+t+"44"+Chapter+"1"+mp3;//词组听写中速
            web_http = new String[]{url_ui_web, chi_kewen, chi_slow_zi,chi_speed_zi,chi_slow_ci,chi_speed_ci};
        }
        if(Chapter.substring(0,1).equals("5")){
            String url_ui1_web = ht_ip+"3/61/"+qian_5+t+"61"+Chapter+"01"+html;
            String url_ui2_web = ht_ip+"3/61/"+qian_5+t+"61"+Chapter+"02"+html;
            String url_ui1_yuyin = ht_ip+"3/"+"62/"+qian_5+t+"62"+Chapter+"01"+mp3;
            String url_ui2_yuyin = ht_ip+"3/"+"62/"+qian_5+t+"62"+Chapter+"02"+mp3;
            String url_eng_man = ht_ip+"3/"+"63/"+qian_5+"/"+"63"+Chapter+"0"+mp3;//英文慢速
            String url_eng_zhong = ht_ip+"3/"+"63/"+qian_5+"/"+"63"+Chapter+"1"+mp3;//英文中速
            String url_zh_man = Constant.ip+"3/"+"64/"+qian_5+"/"+"64"+Chapter+"0"+mp3;//中文慢速
            String url_zh_zhong = Constant.ip+"3/"+"64/"+qian_5+"/"+"64"+Chapter+"1"+mp3;//中文中速
            web_http = new String[]{url_ui1_web, url_ui2_web, url_ui1_yuyin,url_ui2_yuyin,url_eng_man,url_eng_zhong,url_zh_man,url_zh_zhong};
        }
        if(Chapter.substring(0,1).equals("7")){
            /*新概念*/
            String url_web = ht_ip+"3/81/"+qian_5+t+"81"+Chapter+html;//web
            String url_web_mp3 = ht_ip+"3/"+"82/"+qian_5+t+"82"+Chapter+mp3;//原文语音
            String url_eng_yuyin = ht_ip+"3/"+"83/"+qian_5+t+"83"+Chapter+mp3;//英文听写
            String url_zhong_yuyin= ht_ip+"3/"+"84/"+qian_5+"/"+"84"+Chapter+mp3;//中文慢速
            web_http = new String[]{url_web,url_web_mp3,url_eng_yuyin,url_zhong_yuyin};
        }
        if(Chapter.substring(0,1).equals("4")){
           String ip_shudu="http://122.156.218.189:8080/Xrl/resources/xrlRes/1/23/47811/";//数独
           String ip_shudu_ = ip_shudu+hou_2+t+"23"+Chapter+html;
            web_http = new String[]{ip_shudu_};
        }
        return web_http;
    }
public  static  String [] get_shiroute(){

    if(Chapter.substring(0,1).equals("D")||Chapter.substring(0,1).equals("E")){
        String ip_shici = ht_ip+"2/46/sound/";
        String shi1 = ip_shici+"46"+Chapter+"1"+mp3;
        String shi2 = ip_shici+"46"+Chapter+"2"+mp3;
        web_http = new String[]{shi1,shi2,"D",hou_4_2,hou_2};
    }
    if(Chapter.substring(0,1).equals("G")){
        String ip_shici = ht_ip+"2/46/sound/";
        String shi1 = ip_shici+"46"+Chapter+"001"+mp3;
        String shi2 = ip_shici+"46"+Chapter+"002"+mp3;
        web_http = new String[]{shi1,shi2,"G",hou_2};
    }
    if(Chapter.substring(0,1).equals("F")){
        String ip_di = ht_ip+"2/49/"+qian_5+"/";
        String dizigui1 = ip_di+"49"+qian_5+"第"+hou_1+"章"+name+"原文"+mp3;
        //F7811   F国学经典的判断 7出版社    1年级  1 上学期
        String dizigui2 = ip_di+"49"+qian_5+"第"+hou_1+"章"+name+"译文"+mp3;
        web_http = new String[]{dizigui1,dizigui2,hou_2};
    }
    return web_http;
}
    public static String get_url_shengzi (String zi){
        String shengzi_mp3 = ht_ip+"2/45/"+qian_5+t+hou_2+t+zi+mp3;
        return shengzi_mp3;
    }
    public static String[] get_rijiyuelei(){
        web_http = new String[]{qian_5,hou_2,hou_4_2};
        return null;
    }
    public static String[] get_word_learn(String id){
        String ii = id.substring(0,1);
        String pic,mp3;
        if(ii.equals("7")) {
             pic = ht_ip+"3"+t+"99"+t+"pic"+t;
             mp3 = ht_ip+"3"+t+"85"+t+qian_5+t+hou_2+t;
        }else {
          pic = ht_ip+"3"+t+"99"+t+"pic"+t;
           mp3 = ht_ip+"3"+t+"65"+t+qian_5+t+hou_2+t;}
        
        web_http = new String[]{pic,mp3};
        return web_http;
    }
    
    
}
