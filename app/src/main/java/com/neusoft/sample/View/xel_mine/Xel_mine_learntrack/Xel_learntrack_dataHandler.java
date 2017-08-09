package com.neusoft.sample.View.xel_mine.Xel_mine_learntrack;

import android.util.Log;

import com.neusoft.sample.Ctrl.Db_TextTwoStructureService;
import com.neusoft.sample.GreenDao.TextTwoStructure;
import com.neusoft.sample.GreenDao.User;
import com.neusoft.sample.Model.Constant;
import com.neusoft.sample.View.Fragment.xel_mine_fragment;
import com.neusoft.sample.util.ContextHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by AstroBoy on 2016/12/8.
 */

public class Xel_learntrack_dataHandler {

    public List<Map<Object, String>> getNowSemesterDateList() {

        List<Map<Object, String>> termItem = new ArrayList<>();// 定义显示的内容包装

        int year = Constant.getNowDate().getYear() + 1900;
        int month = Constant.getNowDate().getMonth() + 1;
        String termCode = getTermCode();

        switch (termCode) {
            case "1": {
                //Next term
                int minMonth = 9;
                int currentMonth = 0;
                for (currentMonth = minMonth; currentMonth <= month; currentMonth++) {
                    HashMap<Object, String> map = new HashMap<>();
                    map.put("year", String.valueOf(year));
                    map.put("month", String.valueOf(currentMonth));
                    String tempMonth = String.valueOf(currentMonth);
                    if (tempMonth.length()==1) {
                        tempMonth = "0" + tempMonth;
                    }
                    map.put("yyyy-mm",year+"-"+tempMonth);
                    termItem.add(map);
                }
                if (month >= 1 && month <= 2) {
                    for (currentMonth = 1; currentMonth <= month; currentMonth++) {
                        HashMap<Object, String> map = new HashMap<Object, String>();
                        map.put("year", String.valueOf(year));
                        map.put("month", String.valueOf(currentMonth));
                        String tempMonth = String.valueOf(currentMonth);
                        if (tempMonth.length()==1) {
                            tempMonth = "0" + tempMonth;
                        }
                        map.put("yyyy-mm",year+"-"+tempMonth);
                        termItem.add(map);
                    }
                }
                break;
            }
            case "2": {
                //Last term
                int minMonth = 3;
                int currentMonth = 0;
                for (currentMonth = minMonth; currentMonth <= month; currentMonth++) {
                    HashMap<Object, String> map = new HashMap<Object, String>();
                    map.put("year", String.valueOf(year));
                    map.put("month", String.valueOf(currentMonth));
                    String tempMonth = String.valueOf(currentMonth);
                    if (tempMonth.length()==1) {
                        tempMonth = "0" + tempMonth;
                    }
                    map.put("yyyy-mm",year+"-"+tempMonth);
                    termItem.add(map);
                }
                break;
            }
            default:
                HashMap<Object, String> map = new HashMap<Object, String>();
                map.put("year", "获取列表失败");
                break;
        }
        Log.d("getNowSemesterDateList","return"+termItem);
        return termItem;
    }

    public List<Map<Object, String>> getSuitableSubjectList(String subject) {
        User tUserInfo = xel_mine_fragment.tUserInfo;
        List<Map<Object, String>> subjectItem = new ArrayList<Map<Object, String>>();// 定义显示的内容包装
        String calCard = "";
        String aosai = "";
        String riji = "";
        String danci = "";
        String yufa = "";
        String xingainian = "";
        List<TextTwoStructure> twoStructureList = Db_TextTwoStructureService.getInstance(ContextHolder.getContext()).loadAllNote();

        for (TextTwoStructure no : twoStructureList){
            String trueNo = "";
            trueNo = no.getSectionNo().substring(0,5);
            if (trueNo.substring(0,1).equals("1"))
                calCard = trueNo;
            if (trueNo.substring(0,1).equals("3"))
                aosai = trueNo;
            if (trueNo.substring(0,1).equals("C"))
                riji = trueNo;
            if (trueNo.substring(0,1).equals("5"))
                danci = trueNo;
            if (trueNo.substring(0,1).equals("8"))
                yufa = trueNo;
            if (trueNo.substring(0,1).equals("7"))
                xingainian = trueNo;

        }

        HashMap<Object, String> map;
        String originGrade = tUserInfo.getGrade();
        int month = Constant.getNowDate().getMonth();
        String termCode = getTermCode();
        int grade = Integer.parseInt(originGrade.substring(originGrade.length() - 1, originGrade.length()));
        switch (subject) {
            case "math":
                map = new HashMap<Object, String>();
                map.put("name","口算题卡");
                map.put("code",calCard);
                subjectItem.add(map);
                map = new HashMap<Object, String>();
                map.put("name","奥赛测试");
                map.put("code",aosai);
                subjectItem.add(map);
                break;
            case "chinese":
                map = new HashMap<Object, String>();
                map.put("name","日积月累");
                map.put("code",riji);
                subjectItem.add(map);
                break;
            case "english":
                map = new HashMap<Object, String>();
                map.put("name","背单词");
                map.put("code",danci);
                subjectItem.add(map);
                map = new HashMap<Object, String>();
                map.put("name","语法测试");
                map.put("code",yufa);
                subjectItem.add(map);
                if (grade>=3){
                    map = new HashMap<Object, String>();
                    map.put("name","新概念背单词");
                    map.put("code",xingainian);
                    subjectItem.add(map);
                }
                break;
        }

        return subjectItem;
    }

    private String getTermCode(){
        //判断上下学期,1为上学期,2为下学期
        int month = Constant.getNowDate().getMonth()+1;
        String termCode = "";
        if (month >= 2 && month <= 8)
            termCode = "2";
        else{
            termCode = "1";
        }
        Log.d("getTermCode","month"+month+"termCode"+termCode);
        return termCode;
    }

}
