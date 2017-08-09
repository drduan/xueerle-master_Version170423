package com.neusoft.sample.Model;


import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.neusoft.sample.App;
import com.neusoft.sample.Ctrl.Db_XTCSJGService;
import com.neusoft.sample.Ctrl.Db_XTCTJLService;
import com.neusoft.sample.Ctrl.Db_XTDCTMService;
import com.neusoft.sample.GreenDao.UserDoSubjectInfo;
import com.neusoft.sample.GreenDao.XTCSJG;
import com.neusoft.sample.GreenDao.XTCTJL;
import com.neusoft.sample.GreenDao.XTDCTM;
import com.neusoft.sample.util.ContextHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;

/**
 * Created by wangyujie on 2016/7/7.
 */
public class QuestionGroup extends Subject {
    private List<Question> questions;
    private List<XTDCTM> xtdctmList = new ArrayList<>();
    private List<XTCTJL> xtctjlList = new ArrayList<>();
    //发送错题的数据
    private List<XTDCTM> errorxtdctmList = new ArrayList<>();
    private List<XTCTJL> errorxtctjlList = new ArrayList<>();


    private int theCurrentId;
    private HashMap<String, Object> map = new HashMap<>();

    private int num_right;
    boolean isStu = false;
    public static int recLen = 10;
    private Timer timer;

    private int num_wrong;


    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(this);
        }
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        // model 直接通知 observer
        this.questions = questions;
        theCurrentId = 0;
        this.notifyObservers();
    }

    public int getTheCurrentId() {
        return theCurrentId;
    }

    private void report() {

        for (Question question : questions) {
            if (question.judege()) {
                num_right++;

                String questionsNub = question.getItem_no();//题号
                XTDCTM xtdctm = new XTDCTM(null, null, questionsNub, 1, questionsNub.substring(0, 11), null, null);
                xtdctm.setUser_id(App.newInstance().GetSharePrefrence_kejiezu(ContextHolder.getContext()));
                errorxtdctmList.add(xtdctm);
                XTCTJL xtctjl = new XTCTJL(null, null, question.getMyChoice(), Constant.getNowDate(), questionsNub, null);
                errorxtctjlList.add(xtctjl);

            } else {
                num_wrong++;
                //存储错的题目到数据库中
                String questionsNub = question.getItem_no();//题号
                XTDCTM xtdctm = new XTDCTM(null, null, questionsNub, 2, questionsNub.substring(0, 11), null, null);
                xtdctm.setUser_id(App.newInstance().GetSharePrefrence_kejiezu(ContextHolder.getContext()));
                xtdctmList.add(xtdctm);
                errorxtdctmList.add(xtdctm);
                XTCTJL xtctjl = new XTCTJL(null, null, question.getMyChoice(), Constant.getNowDate(), questionsNub, null);
                xtctjlList.add(xtctjl);
                errorxtctjlList.add(xtctjl);
            }
        }


        Log.d("其问题的大小", xtdctmList.size() + "");
//存数据前清除数据库中原有数据
        Db_XTDCTMService.getInstance(ContextHolder.getContext()).saveNoteLists(xtdctmList);
        Db_XTCTJLService.getInstance(ContextHolder.getContext()).saveNoteLists(xtctjlList);
        xtdctmList.clear();
        xtctjlList.clear();


        //*****************************************************************************************
        /**
         * 这个是不是重新做的错题
         */

        Log.d("GetisDoGain", MsharedPrefrence.GetisDoGain(ContextHolder.getContext()) + "ii");


        if (!"doagain".equals(MsharedPrefrence.GetisDoGain(ContextHolder.getContext()))) {
            Log.d("doagain no", "is successfully");

            System.out.println("第一次做题走了");

            System.out.println("第一次做题走了" + "这个是正确数-----" + num_right + "题目数------" + questions.size());
            if ((int) num_right != questions.size()) {//不满分


                System.out.println("第一次做题走了--我不满分");


                if (App.GetSharePrefrence_role(ContextHolder.getContext()).equals("1")) {

                    System.out.println("第一次做题走了--我不满分且我是学生");
                    //是学生
                    double f = 100.0d / questions.size();
                    Log.i("@@@", f + "" + num_right);
                    //XTCSJG 设置数据
                    XTCSJG xtcsjg = new XTCSJG();
                    String id = questions.get(0).getItem_no().substring(0, 11);
                    xtcsjg.setTest_group_number(id);
                    xtcsjg.setScore((int) (num_right * f));
                    xtcsjg.setNumber_error(num_wrong);
                    xtcsjg.setNumber_success(num_right);

                    xtcsjg.setDuration(recLen);
                    Date curDate = new Date(System.currentTimeMillis());
                    String dates = Constant.getyymmdd(curDate);
                    xtcsjg.setJg_datetime(Constant.strToDate(dates));
                    xtcsjg.setUser_id(App.newInstance().GetSharePrefrence_kejiezu(ContextHolder.getContext()));
                    //XTCSJG存到数据库中
                    Db_XTCSJGService.getInstance(ContextHolder.getContext()).saveNote(xtcsjg);

                    Log.d("xtcsjg", xtcsjg.toString());
                    //加到消息队列
                    map.put("xtcsjg", xtcsjg);
                    map.put("errorxtdctmList", errorxtdctmList);
                    map.put("errorxtctjlList", errorxtctjlList);


                    String mapstring = JSON.toJSONString(map);
                    EventBus.getDefault().post(mapstring);
                    //*****************************************************************
                } else {//不是学生


                    System.out.println("第一次做题走了--我不满分且我是不是学生");
                    double f = 100.0d / questions.size();
                    Log.i("@@@", f + "" + num_right);
                    XTCSJG xtcsjg = new XTCSJG();
                    String id = questions.get(0).getItem_no().substring(0, 11);
                    xtcsjg.setTest_group_number(id);
                    xtcsjg.setScore((int) (num_right * f));
                    xtcsjg.setNumber_error(questions.size() - num_right);
                    xtcsjg.setNumber_success(num_right);
                    xtcsjg.setDuration(recLen);
                    Date curDate = new Date(System.currentTimeMillis());
                    String dates = Constant.getyymmdd(curDate);
                    xtcsjg.setJg_datetime(Constant.strToDate(dates));
                    Db_XTCSJGService.getInstance(ContextHolder.getContext()).saveNote(xtcsjg);
                    Log.d("xtcsjg", xtcsjg.toString());
                    //加到消息队列
                    map.put("xtcsjg", xtcsjg);
                    String mapstring = JSON.toJSONString(map);
                    EventBus.getDefault().post(mapstring);
                }



            } else {//满分************************************************************************************

                System.out.println("第一次做题走了--我满分");
                if (App.GetSharePrefrence_role(ContextHolder.getContext()).equals("1")) {//是学生
                    System.out.println("第一次做题走了--我满分且我是是学生");
                    //是学生
                    double f = 100.0d / questions.size();
                    Log.i("@@@", f + "" + num_right);
                    //XTCSJG 设置数据
                    XTCSJG xtcsjg = new XTCSJG();
                    String id = questions.get(0).getItem_no().substring(0, 11);
                    xtcsjg.setTest_group_number(id);
                    xtcsjg.setScore((int) (num_right * f));
                    xtcsjg.setNumber_error(num_wrong);
                    xtcsjg.setNumber_success(num_right);
                    xtcsjg.setDuration(recLen);
                    Date curDate = new Date(System.currentTimeMillis());
                    String dates = Constant.getyymmdd(curDate);
                    xtcsjg.setJg_datetime(Constant.strToDate(dates));
                    xtcsjg.setUser_id(App.newInstance().GetSharePrefrence_kejiezu(ContextHolder.getContext()));
                    //XTCSJG存到数据库中
                    Db_XTCSJGService.getInstance(ContextHolder.getContext()).saveNote(xtcsjg);
                    Log.d("xtcsjg", xtcsjg.toString());
                    //加到消息队列
                    map.put("xtcsjg", xtcsjg);
                    String mapstring = JSON.toJSONString(map);
                    EventBus.getDefault().post(mapstring);
                } else //不是学生
                {

                    System.out.println("第一次做题走了--我满分且我是不是学生");
                    double f = 100.0d / questions.size();
                    Log.i("@@@", f + "" + num_right);
                    XTCSJG xtcsjg = new XTCSJG();
                    String id = questions.get(0).getItem_no().substring(0, 11);
                    xtcsjg.setTest_group_number(id);
                    xtcsjg.setScore((int) (num_right * f));
                    xtcsjg.setNumber_error(questions.size() - num_right);
                    xtcsjg.setNumber_success(num_right);
                    xtcsjg.setDuration(recLen);
                    Date curDate = new Date(System.currentTimeMillis());
                    String dates = Constant.getyymmdd(curDate);
                    xtcsjg.setJg_datetime(Constant.strToDate(dates));
                    Db_XTCSJGService.getInstance(ContextHolder.getContext()).saveNote(xtcsjg);
                    Log.d("xtcsjg", xtcsjg.toString());
                    //加到消息队列
                    map.put("xtcsjg", xtcsjg);
                    String mapstring = JSON.toJSONString(map);
                    EventBus.getDefault().post(mapstring);
                    HashMap<String, String> hmap = new HashMap<>();
                    String zuNo = questions.get(0).getItem_no().substring(0, 11);
                    String JiaocaiSBNo = questions.get(0).getItem_no().substring(0, 5);
                    hmap.put("zuNo", zuNo);
                    hmap.put("JiaocaiSBNo", JiaocaiSBNo);
                    PostNoStudentsChampionship.PostLink(ContextHolder.getContext(), Constant.PostNotStudentsChampionship, hmap);
                }

            }
        }
        //*****************************************************************************************
        /**
         * 重新做的错题
         */
        else {

            System.out.println("重新做的错题--走了");
            Log.d("doagain", "is successfully");
//            if (num_right != questions.size()-1) {


            if (App.GetSharePrefrence_role(ContextHolder.getContext()).equals("1")) {

                System.out.println("重新做的错题--走了我是学生");
                //是学生
                double f = 100.0d / questions.size();
                Log.i("@@@", f + "" + num_right);
                //XTCSJG 设置数据
                XTCSJG xtcsjg = new XTCSJG();
                String id = questions.get(0).getItem_no().substring(0, 11);
                xtcsjg.setTest_group_number(id);
                xtcsjg.setScore((int) (num_right * f));
                xtcsjg.setNumber_error(num_wrong);
                xtcsjg.setNumber_success(num_right);

                xtcsjg.setDuration(recLen);
                Date curDate = new Date(System.currentTimeMillis());
                String dates = Constant.getyymmdd(curDate);
                xtcsjg.setJg_datetime(Constant.strToDate(dates));
                xtcsjg.setUser_id(App.newInstance().GetSharePrefrence_kejiezu(ContextHolder.getContext()));
                //XTCSJG存到数据库中
                Db_XTCSJGService.getInstance(ContextHolder.getContext()).saveNote(xtcsjg);

                Log.d("xtcsjg", xtcsjg.toString());
                //加到消息队列
                map.put("xtcsjg", xtcsjg);
//                map.put("errorxtdctmList", errorxtdctmList);
//                map.put("errorxtctjlList", errorxtctjlList);
                String mapstring = JSON.toJSONString(map);
                EventBus.getDefault().post(mapstring);

                List<XTDCTM> xtdctmList2 = errorxtdctmList;
                List<XTCTJL> xtctjlList2 = errorxtctjlList;

                List<UserDoSubjectInfo> userDoSubjectInfoList = new ArrayList<>();
                for (int i = 0; i < xtdctmList2.size(); i++) {
                    UserDoSubjectInfo userDoSubjectInfo = new UserDoSubjectInfo();
                    userDoSubjectInfo.setXtdctm_id(xtdctmList2.get(i).getXtdctm_id());
                    userDoSubjectInfo.setExamination_number(xtdctmList2.get(i).getExamination_number());
                    userDoSubjectInfo.setTest_group_number(xtdctmList2.get(i).getExamination_number().substring(0, 11));
                    userDoSubjectInfo.setIsmastered(xtdctmList2.get(i).getIsmastered());
                    userDoSubjectInfo.setUser_id(xtdctmList2.get(i).getUser_id());
                    userDoSubjectInfo.setXtcsjl_id(xtctjlList2.get(i).getXtcsjl_id());
                    userDoSubjectInfo.setAnswer(xtctjlList2.get(i).getAnswer());
                    userDoSubjectInfo.setJl_datetime(xtctjlList2.get(i).getJl_datetime());
                    userDoSubjectInfoList.add(userDoSubjectInfo);
                }
                HashMap<String, String> hmap = new HashMap<>();
                String JiaocaiSBNo = questions.get(0).getItem_no().substring(0, 5);
                hmap.put("JiaocaiSBNo", JiaocaiSBNo);
//                        String CSJG = JSON.toJSONString(xtcsjg);

                hmap.put("wrongTopicResults", JSON.toJSONString(userDoSubjectInfoList));

                PostDoexamFull.PostLink(ContextHolder.getContext(), Constant.post_Error_subject_again, hmap);
            } else {//不是学生
                System.out.println("重新做的错题--走了我不是学生");
                double f = 100.0d / questions.size();
                Log.i("@@@", f + "" + num_right);
                XTCSJG xtcsjg = new XTCSJG();
                String id = questions.get(0).getItem_no().substring(0, 11);
                xtcsjg.setTest_group_number(id);
                xtcsjg.setScore((int) (num_right * f));
                xtcsjg.setNumber_error(questions.size() - num_right);
                xtcsjg.setNumber_success(num_right);
                xtcsjg.setDuration(recLen);
                Date curDate = new Date(System.currentTimeMillis());
                String dates = Constant.getyymmdd(curDate);
                xtcsjg.setJg_datetime(Constant.strToDate(dates));
                Db_XTCSJGService.getInstance(ContextHolder.getContext()).saveNote(xtcsjg);
                Log.d("xtcsjg", xtcsjg.toString());
                //加到消息队列
                map.put("xtcsjg", xtcsjg);
                String mapstring = JSON.toJSONString(map);
                EventBus.getDefault().post(mapstring);
            }
            //TODO 接下来的事情


//
//            } else {//满分
//
//            }

        }


        System.out.println("================");
        System.out.println("正确数：" + num_right);
        System.out.println("错误数：" + num_wrong);
        System.out.println("================");
        recLen = 0;
        num_right = 0;
        num_wrong = 0;
        errorxtdctmList.clear();
        errorxtctjlList.clear();
    }

    public void submit(String myChoice) {
        if (theCurrentId == this.questions.size() - 1) {

            this.questions.get(theCurrentId).setMyChoice(myChoice);

            report();
            Log.i("@@", "num_right" + num_right + "num_wrong" + num_wrong);
            theCurrentId++;

        } else if (theCurrentId < this.questions.size() - 1){

            this.questions.get(theCurrentId).setMyChoice(myChoice);
            theCurrentId++;
            this.notifyObservers();
        }
    }

}
