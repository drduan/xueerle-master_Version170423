package com.neusoft.sample.Model;

/**
 * Created by AstroBoy on 2016/1
 */

public class fenshu_panduan {
    static String qian;
    static String hou;
    static String yunsuanfu;
    static String other;

    public static String [] get_fenshu (String p){
        String []fen ; //返回给  activity 的 数组
        if(p.contains("$")){
            String  jia1 = p.substring(1,p.indexOf("="));
            String  bb = p.substring(p.indexOf("="),p.length());//截取等号后面的所有内容
            /*例： $3/7 = ?/2*/
            if(jia1.contains("/")) {
                qian = p.substring(p.indexOf("$") + 1, p.indexOf("/")) + "\n" + "—" + "\n" + p.substring(p.indexOf("/") + 1, p.indexOf("="));  //截取  =前面的分数
            }else {
                qian = jia1;
            }
            yunsuanfu = "=";
            String aa = p.substring(p.indexOf("="),p.length());//截取等号后面的所有内容
            if(bb.contains("/")) {
                hou = aa.substring(1, aa.indexOf("/")) + "\n" + "—" + "\n" + aa.substring(aa.indexOf("/") + 1, aa.length());//截取  =后面的分数
            }else {
                hou = bb;
            }
            fen = new String[]{qian,yunsuanfu,hou,"3"};//3代表的是  界面 需要三个activity
            return fen;
        }else if(p.contains("~")){
            qian = p.substring(0,p.indexOf("/")) +"\n"+"—"+"\n"+ p.substring(p.indexOf("/")+1,p.indexOf("~"));
            other = p.substring(p.indexOf("~")+1,p.length());
             /*例： 3/7~ 这是一串汉子*/   /*例： 3/~ = ?  */
            fen = new String[]{qian,other,"0","2"};
            return fen;

        }else if(p.contains("#")){
             /*例： $3/7 + ?/2*/   /*例： $3/7 - ?/2*/  /*例： $3/7 x ?/2*/
            int aa ;
            if(p.contains("+")){
                aa =  p.indexOf("+");
            }else if(p.contains("-")){
                aa = p.indexOf("-");
            }else if(p.contains("x")){
                aa = p.indexOf("x");
            }else {
                aa = p.indexOf("÷");
            }/*获取  加减乘除的   下表*/
            String  jia1 = p.substring(1,aa);
            String  bb = p.substring(aa,p.length());//截取等号后面的所有内容
            if(jia1.contains("/")){
                qian = p.substring(1,p.indexOf("/")) +"\n" + "—"+"\n"+p.substring(p.indexOf("/")+1,aa);
            }else {
                qian = jia1;
            }
            yunsuanfu  = p.substring(aa,aa+1);
            if(bb.contains("/")){
                hou = bb.substring(1,bb.indexOf("/")) +"\n" + "—"+"\n"+ bb.substring(bb.indexOf("/")+1,bb.length());
            }else {
                hou = bb.substring(1,bb.length());
            }
            fen = new String[]{qian,yunsuanfu,hou,"3"};
            return fen;
        }else {
//           /*  例：  7/4.,3/5  比较这辆个书的大小*/
            qian = p.substring(0,p.indexOf("/"))+"\n" + "—"+"\n"+p.substring(p.indexOf("/")+1,p.indexOf(","));
            yunsuanfu = "和";
            String bb = p.substring(p.indexOf(","),p.length());//截取等号后面的所有内容
            hou = bb.substring(1,bb.indexOf("/"))+"\n" + "—"+"\n"+bb.substring(bb.indexOf("/")+1,bb.indexOf(" "));
            other  = bb.substring(bb.indexOf(" "),bb.length());
            fen = new String[]{qian,yunsuanfu,hou,other};
            return fen;
        }


    }

}
