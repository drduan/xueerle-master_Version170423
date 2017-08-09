package com.neusoft.sample.util;

/**
 * Created by wangyujie on 2016/7/20.
 */
public class QuestionnumberSwitchTitle {

    public static String Gettitle(String nub) {

        StringBuffer stringBuffer = new StringBuffer();
        if (nub.substring(0, 1).equals("1") || nub.substring(0, 1).equals("5")) {
            String a = "课程";
            stringBuffer.append("" + a + ">");
        } else {
            String a = "专题";
            stringBuffer.append("" + a + ">");
        }

        if (nub.substring(0, 1).equals("1") || nub.substring(0, 1).equals("3") || nub.substring(0, 1).equals("4")) {
            stringBuffer.append("数学");
        } else if (nub.substring(0, 1).equals("5") || nub.substring(0, 1).equals("7") || nub.substring(0, 1).equals("8")) {
            stringBuffer.append("英语");
        } else {
            stringBuffer.append("语文");
        }
        if (nub.substring(0, 1).equals("1")) {
            stringBuffer.append(">口算题卡");
        } else if (nub.substring(0, 1).equals("3")) {
            stringBuffer.append(">奥赛学习");
        } else if (nub.substring(0, 1).equals("4")) {
            stringBuffer.append(">数独学习");
        } else if (nub.substring(0, 1).equals("5")) {
            stringBuffer.append(">背单词");
        } else if (nub.substring(0, 1).equals("7")) {
            stringBuffer.append(">新概念背单词");
        } else if (nub.substring(0, 1).equals("8")) {
            stringBuffer.append(">英语语法测试");
        } else if (nub.substring(0, 1).equalsIgnoreCase("C")) {
            stringBuffer.append(">日积月累");
        }
        if(nub.substring(0, 1).equals("5")||nub.substring(0, 1).equals("8")){
            stringBuffer.append(">第" + nub.substring(5, 7) + "章" + ">第" + nub.substring(9, 11) + "组");
        }else if (nub.substring(0, 1).equals("1")||nub.substring(0, 1).equals("3")){
            stringBuffer.append(">第" + nub.substring(5, 7) + "章" + ">第" + nub.substring(7, 9) + "节"+ ">第" + nub.substring(9, 11) + "组");
        }else {
            stringBuffer.append(">第" + nub.substring(5, 7) + "章" + ">第" + nub.substring(7, 9) + "节");
        }

        //14051010111
        String title = stringBuffer.toString();
        return title;
    }



    public static String GetSudytitle(String nub) {

        StringBuffer stringBuffer = new StringBuffer();
        if (nub.substring(0, 1).equals("1") || nub.substring(0, 1).equals("5")) {
            String a = "课程";
            stringBuffer.append("" + a + ">");
        } else {
            String a = "专题";
            stringBuffer.append("" + a + ">");
        }

        if (nub.substring(0, 1).equals("1") || nub.substring(0, 1).equals("3") || nub.substring(0, 1).equals("4")) {
            stringBuffer.append("数学");
        } else if (nub.substring(0, 1).equals("5") || nub.substring(0, 1).equals("7") || nub.substring(0, 1).equals("8")) {
            stringBuffer.append("英语");
        } else {
            stringBuffer.append("语文");
        }
        if (nub.substring(0, 1).equals("1")) {
            stringBuffer.append(">口算题卡");
        } else if (nub.substring(0, 1).equals("3")) {
            stringBuffer.append(">奥赛学习");
        } else if (nub.substring(0, 1).equals("4")) {
            stringBuffer.append(">数独学习");
        } else if (nub.substring(0, 1).equals("5")) {
            stringBuffer.append(">背单词");
        } else if (nub.substring(0, 1).equals("7")) {
            stringBuffer.append(">新概念课本学习");
        } else if (nub.substring(0, 1).equals("8")) {
            stringBuffer.append(">英语语法测试");
        } else if (nub.substring(0, 1).equalsIgnoreCase("C")) {
            stringBuffer.append(">日积月累");
        }
        if(nub.substring(0, 1).equals("5")||nub.substring(0, 1).equals("8")){
            stringBuffer.append(">第" + nub.substring(5, 7) + "章" + ">第" + nub.substring(9, 11) + "组");
        }else if (nub.substring(0, 1).equals("1")||nub.substring(0, 1).equals("3")){
            stringBuffer.append(">第" + nub.substring(5, 7) + "章" + ">第" + nub.substring(7, 9) + "节");
        }else {
            stringBuffer.append(">第" + nub.substring(5, 7) + "章" + ">第" + nub.substring(7, 9) + "节");
        }

        //14051010111
        String title = stringBuffer.toString();
        return title;
    }


    public static String GetAosaititle(String nub) {

        StringBuffer stringBuffer = new StringBuffer();
        if (nub.substring(0, 1).equals("1") || nub.substring(0, 1).equals("5")) {
            String a = "课程";
            stringBuffer.append("" + a + ">");
        } else {
            String a = "专题";
            stringBuffer.append("" + a + ">");
        }

        if (nub.substring(0, 1).equals("1") || nub.substring(0, 1).equals("3") || nub.substring(0, 1).equals("4")) {
            stringBuffer.append("数学");
        } else if (nub.substring(0, 1).equals("5") || nub.substring(0, 1).equals("7") || nub.substring(0, 1).equals("8")) {
            stringBuffer.append("英语");
        } else {
            stringBuffer.append("语文");
        }
        if (nub.substring(0, 1).equals("1")) {
            stringBuffer.append(">口算题卡");
        } else if (nub.substring(0, 1).equals("3")) {
            stringBuffer.append(">奥赛测试");
        } else if (nub.substring(0, 1).equals("4")) {
            stringBuffer.append(">数独测试");
        } else if (nub.substring(0, 1).equals("5")) {
            stringBuffer.append(">背单词");
        } else if (nub.substring(0, 1).equals("7")) {
            stringBuffer.append(">新概念背单词");
        } else if (nub.substring(0, 1).equals("8")) {
            stringBuffer.append(">英语语法测试");
        } else if (nub.substring(0, 1).equalsIgnoreCase("C")) {
            stringBuffer.append(">日积月累");
        }
        if(nub.substring(0, 1).equals("5")||nub.substring(0, 1).equals("8")){
            stringBuffer.append(">第" + nub.substring(5, 7) + "章" + ">第" + nub.substring(9, 11) + "组");
        }else {
            stringBuffer.append(">第" + nub.substring(5, 7) + "章" + ">第" + nub.substring(7, 9) + "节");
        }
        String title = stringBuffer.toString();
        return title;
    }


    public static String GettitleDoagin(String nub) {

        StringBuffer stringBuffer = new StringBuffer();

            stringBuffer.append("错题"  + ">");


        if (nub.substring(0, 1).equals("1") || nub.substring(0, 1).equals("3") || nub.substring(0, 1).equals("4")) {
            stringBuffer.append("数学");
        } else if (nub.substring(0, 1).equals("5") || nub.substring(0, 1).equals("7") || nub.substring(0, 1).equals("8")) {
            stringBuffer.append("英语");
        } else {
            stringBuffer.append("语文");
        }
        if (nub.substring(0, 1).equals("1")) {
            stringBuffer.append(">口算题卡");
        } else if (nub.substring(0, 1).equals("3")) {
            stringBuffer.append(">奥赛测试");
        } else if (nub.substring(0, 1).equals("4")) {
            stringBuffer.append(">数独测试");
        } else if (nub.substring(0, 1).equals("5")) {
            stringBuffer.append(">背单词");
        } else if (nub.substring(0, 1).equals("7")) {
            stringBuffer.append(">新概念背单词");
        } else if (nub.substring(0, 1).equals("8")) {
            stringBuffer.append(">英语语法测试");
        } else if (nub.substring(0, 1).equalsIgnoreCase("C")) {
            stringBuffer.append(">日积月累");
        }
        if(nub.substring(0, 1).equals("5")||nub.substring(0, 1).equals("8")){
            stringBuffer.append(">第" + nub.substring(5, 7) + "章" + ">第" + nub.substring(9, 11) + "组");
        }else {
            stringBuffer.append(">第" + nub.substring(5, 7) + "章" + ">第" + nub.substring(7, 9) + "节");
        }
        String title = stringBuffer.toString();
        return title;
    }




    public static String GetErrortitle(String nub) {

        StringBuffer stringBuffer = new StringBuffer();

            stringBuffer.append("错题" + ">");


        if (nub.substring(0, 1).equals("1") || nub.substring(0, 1).equals("3") || nub.substring(0, 1).equals("4")) {
            stringBuffer.append("数学");
        } else if (nub.substring(0, 1).equals("5") || nub.substring(0, 1).equals("7") || nub.substring(0, 1).equals("8")) {
            stringBuffer.append("英语");
        } else {
            stringBuffer.append("语文");
        }
        if (nub.substring(0, 1).equals("1")) {
            stringBuffer.append(">口算题卡");
        } else if (nub.substring(0, 1).equals("3")) {
            stringBuffer.append(">奥赛测试");
        } else if (nub.substring(0, 1).equals("4")) {
            stringBuffer.append(">数独测试");
        } else if (nub.substring(0, 1).equals("5")) {
            stringBuffer.append(">背单词");
        } else if (nub.substring(0, 1).equals("7")) {
            stringBuffer.append(">新概念背单词");
        } else if (nub.substring(0, 1).equals("8")) {
            stringBuffer.append(">英语语法测试");
        } else if (nub.substring(0, 1).equalsIgnoreCase("C")) {
            stringBuffer.append(">日积月累");
        }
        if(nub.substring(0, 1).equals("5")){
            stringBuffer.append(">第" + nub.substring(5, 7) + "章" + ">第" + nub.substring(7, 9) + "节");
        }else {
            stringBuffer.append(">第" + nub.substring(5, 7) + "章" + ">第" + nub.substring(7, 9) + "节");
        }
        String title = stringBuffer.toString();
        return title;
    }


    public static String GetErrorteachertitle(String nub) {

        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append("错题" + ">");


        if (nub.substring(0, 1).equals("1") || nub.substring(0, 1).equals("3") || nub.substring(0, 1).equals("4")) {
            stringBuffer.append("数学");
        } else if (nub.substring(0, 1).equals("5") || nub.substring(0, 1).equals("7") || nub.substring(0, 1).equals("8")) {
            stringBuffer.append("英语");
        } else {
            stringBuffer.append("语文");
        }
        if (nub.substring(0, 1).equals("1")) {
            stringBuffer.append(">口算题卡");
        } else if (nub.substring(0, 1).equals("3")) {
            stringBuffer.append(">奥赛测试");
        } else if (nub.substring(0, 1).equals("4")) {
            stringBuffer.append(">数独测试");
        } else if (nub.substring(0, 1).equals("5")) {
            stringBuffer.append(">背单词");
        } else if (nub.substring(0, 1).equals("7")) {
            stringBuffer.append(">新概念背单词");
        } else if (nub.substring(0, 1).equals("8")) {
            stringBuffer.append(">英语语法测试");
        } else if (nub.substring(0, 1).equalsIgnoreCase("C")) {
            stringBuffer.append(">日积月累");
        }
        if(nub.substring(0, 1).equals("5")||nub.substring(0, 1).equals("8")){
            stringBuffer.append(">第" + nub.substring(5, 7) + "章" );
        }else {
            stringBuffer.append(">第" + nub.substring(5, 7) + "章" + ">第" + nub.substring(7, 9) + "节");
        }
        String title = stringBuffer.toString();
        return title;
    }



    public static String Getzhang(String nub) {
        StringBuffer stringBuffer = new StringBuffer();
        if (nub.substring(0, 1).equals("1")) {
            stringBuffer.append("口算题卡");
        } else if (nub.substring(0, 1).equals("3")) {
            stringBuffer.append("奥赛学习");
        } else if (nub.substring(0, 1).equals("4")) {
            stringBuffer.append("数独学习");
        } else if (nub.substring(0, 1).equals("5")) {
            stringBuffer.append("背单词");
        } else if (nub.substring(0, 1).equals("7")) {
            stringBuffer.append("新概念背单词");
        } else if (nub.substring(0, 1).equals("8")) {
            stringBuffer.append("英语语法测试");
        } else if (nub.substring(0, 1).equalsIgnoreCase("C")) {
            stringBuffer.append("日积月累");
        }
String title=stringBuffer.toString();

        return title;
    }







}
