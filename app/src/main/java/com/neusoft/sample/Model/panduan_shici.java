package com.neusoft.sample.Model;

import android.util.Log;

import com.neusoft.sample.Ctrl.yangkangkang.XiaoXueTuoZhanGuShiCi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 杨康 on 2016/8/25.
 */

public class panduan_shici {
    static  int  aaaa = 0;
    static int int___ = 0;
    static String string = "";
   static ArrayList<Integer> arrayList = new ArrayList<>(); 
    public static void word (String string__){
        string =  string__ ;
    }
    public static int numm (){
        int aa   = string.indexOf(",");
        return aa;
    }
    public static  int  JudgmentPoetry(List<XiaoXueTuoZhanGuShiCi> list) {
        int flag = 0;
        int index = 0;
        for (int i = 3; i < list.size(); i++) {
            Log.d("@@","循环"+flag);
            String words = list.get(i).getWord1() + list.get(i).getWord2() + list.get(i).getWord3() + list.get(i).getWord4() + list.get(i).getWord5() + list.get(i).getWord6() + list.get(i).getWord7() + list.get(i).getWord8() + list.get(i).getWord9() + list.get(i).getWord9() + list.get(i).getWord10() + list.get(i).getWord11() + list.get(i).getWord12();
            if (words.contains("。")) {
                flag = words.indexOf("。");
               
                
                arrayList.add(flag);
            }
            else if (words.contains(":")) {
                flag = words.indexOf(":");
                arrayList.add(flag);
            }
            else  if (words.contains("，")) {
                flag = words.indexOf("，");
                arrayList.add(flag);
            }
            else if (words.contains("！")) {
                flag = words.indexOf("！");
                arrayList.add(flag);
                
            }
            else  if (words.contains("？")) {
                flag = words.indexOf("？");
                arrayList.add(flag);
               
            }
        }
        aaaa =   Content();
        
        return aaaa;
    }
    private static int  Content() {
        for (int i = 0;i<arrayList.size()-1;i++){
            if(arrayList.get(i) == arrayList.get(i+1)){
                int___ = arrayList.get(i);
            }
            else {
                int___ = 1;
            }
        }
        if(int___==1){
        }else {
            if(int___<5){
                int___ = 5;
            }
            
        }
        if(int___ == 6){
            int___ = 7;
        }
        return int___;
    }
}
