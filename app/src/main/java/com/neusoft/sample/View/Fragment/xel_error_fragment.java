package com.neusoft.sample.View.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ncapdevi.sample.R;
import com.neusoft.sample.App;
import com.neusoft.sample.Ctrl.Db_TextOneStructureService;
import com.neusoft.sample.Ctrl.Db_UserService;
import com.neusoft.sample.GreenDao.ErrorSubjectNub;
import com.neusoft.sample.GreenDao.ErrorSubjectNubDao;
import com.neusoft.sample.GreenDao.TextOneStructure;
import com.neusoft.sample.GreenDao.TextTwoStructure;
import com.neusoft.sample.GreenDao.User;
import com.neusoft.sample.Model.Constant;
import com.neusoft.sample.Model.ErrorSubject_two;
import com.neusoft.sample.Model.FindDB_kejiezu;
import com.neusoft.sample.Model.Post_error_question;
import com.neusoft.sample.View.Adapter.Cuoti_spinner_item_Adapter;
import com.neusoft.sample.View.AoSaiTest.Aosai_Error_question_report;
import com.neusoft.sample.View.AoSaiTest.Aosai_Question;
import com.neusoft.sample.View.BaseFragment;
import com.neusoft.sample.View.xel_course.Vman.Error_question_report;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wangyujie on 3/26/16.
 */
public class xel_error_fragment extends BaseFragment {
    private Button btn_leftbutton;
    View v;
    Handler handler;
    Handler handlern;
    String response = null;//返回的第一次数据
    //做题做错的数
    int mathnub;
    int chinesenub;
    int englishnub;
    int kousuan_errornub;
    int aosainub;
    int rijiyuelei_nub;
    int recite_nub;
    int yufa_nub;
    int xingainian_nub;


    //************************
    TextView math;
    TextView yuwen;
    TextView english;
    LinearLayout lay_math;
    LinearLayout lay_yuwen;
    LinearLayout lay_english;
    TextView mathno;
    TextView englishno;
    TextView aosaierrorn;
    TextView chineseno;
    TextView kousuan_errorn;

    TextView recite_no;
    TextView yufa_no;
    TextView xingainian_no;
    TextView rijiyuelei_no;
    View math_line;
    View chinese_line;
    View english_line;


    //**************Spinner************************************
    Spinner error_test_kousuan_ke;
    Spinner error_test_kousuan_jie;
    Spinner error_test_aosai_ke;
    Spinner error_test_aosan_jie;

    Spinner error_test_rijiyue_ke;
    Spinner error_test_rijiyue_jie;
    Spinner error_test_reciteword_ke;
    Spinner error_test_reciteword_jie;

    Spinner error_test__yufa_ke;
    Spinner error_test_xingainian_ke;

    LinearLayout xinggainian_container;

    //**********************一级结构数据源和二级结构数据源******************************
    List<SpannableStringBuilder> error_test_kousuan_ke_list = new ArrayList<>();
    List<String> error_test_kousuan_jie_list = new ArrayList<>();
    List<SpannableStringBuilder> error_test_aosai_ke_list = new ArrayList<>();
    List<String> error_test_aosan_jie_list = new ArrayList<>();
    List<SpannableStringBuilder> error_test_rijiyue_ke_list = new ArrayList<>();
    List<String> error_test_rijiyue_jie_list = new ArrayList<>();
    List<SpannableStringBuilder> error_test_reciteword_ke_list = new ArrayList<>();
    List<String> error_test_reciteword_jie_list = new ArrayList<>();
    List<SpannableStringBuilder> error_test__yufa_ke_list = new ArrayList<>();
    List<SpannableStringBuilder> error_test_xingainian_ke_list = new ArrayList<>();

    //****************************************************


    public static xel_error_fragment newInstance(int instance) {
        Log.d("@@", "positionerror");
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        xel_error_fragment fragment = new xel_error_fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        initEnvent();
        final List<TextOneStructure> Get_ke = Db_TextOneStructureService.getInstance(getContext()).loadAllNote();
        Collections.sort(Get_ke);
        error_test_kousuan_ke_list.clear();
        error_test_aosai_ke_list.clear();
        error_test_rijiyue_ke_list.clear();
        error_test_reciteword_ke_list.clear();
        error_test__yufa_ke_list.clear();
        error_test_xingainian_ke_list.clear();
        //新概念显示不同年级
        List<User> userList = Db_UserService.getInstance(getContext()).loadAllNote();
        String Grade = userList.get(0).getGrade_nub();

        Log.d("Grade", "Grade" + Grade);
        if (Grade.substring(8, 10).equals("01") || Grade.substring(8, 10).equals("02")) {
            xinggainian_container.setVisibility(View.GONE);
        } else {
            xinggainian_container.setVisibility(View.VISIBLE);
        }
        for (TextOneStructure textOneStructure : Get_ke) {
            int op = 0;
            op = (int) App.getDaoSession(getContext()).queryBuilder(ErrorSubjectNub.class).where(ErrorSubjectNubDao.Properties.Examination_number.like(textOneStructure.getChapterNo().substring(0, 7) + "%")).distinct().count();

            //口算题卡一级数据源
            if ("1".equals(textOneStructure.getChapterNo().substring(0, 1))) {

                if (op != 0) {
                    String items = textOneStructure.getChapterSequenceName() + "  " + textOneStructure.getChapterName() + "  &" + op;
                    SpannableStringBuilder style_1 = new SpannableStringBuilder(items);
                    int start = items.indexOf("  &");
                    style_1.setSpan(new ForegroundColorSpan(Color.RED), start, items.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                    error_test_kousuan_ke_list.add(style_1);
                } else {
                    String items = textOneStructure.getChapterSequenceName() + "  " + textOneStructure.getChapterName();
                    SpannableStringBuilder style_1 = new SpannableStringBuilder(items);
                    error_test_kousuan_ke_list.add(style_1);
                }


            }
            //奥赛测试一级数据源
            else if ("3".equals(textOneStructure.getChapterNo().substring(0, 1))) {

                if (op != 0) {
                    String items = textOneStructure.getChapterSequenceName() + "  " + textOneStructure.getChapterName() + "  &" + op;
                    SpannableStringBuilder style_1 = new SpannableStringBuilder(items);
                    int start = items.indexOf("  &");
                    style_1.setSpan(new ForegroundColorSpan(Color.RED), start, items.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                    error_test_aosai_ke_list.add(style_1);
                } else {

                    String items = textOneStructure.getChapterSequenceName() + "  " + textOneStructure.getChapterName();
                    SpannableStringBuilder style_1 = new SpannableStringBuilder(items);
                    error_test_aosai_ke_list.add(style_1);
                }


            }
            //英语背单词测试一级数据源
            else if ("5".equals(textOneStructure.getChapterNo().substring(0, 1))) {

                String items;
                if (op != 0) {
                    if (!textOneStructure.getChapterName().equals("\"\""))
                        items = textOneStructure.getChapterSequenceName() + "  " + textOneStructure.getChapterName() + "  &" + op;
                    else
                        items = textOneStructure.getChapterSequenceName() + "  &" + op;
                    SpannableStringBuilder style_1 = new SpannableStringBuilder(items);
                    int start = items.indexOf("  &");
                    style_1.setSpan(new ForegroundColorSpan(Color.RED), start, items.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                    error_test_reciteword_ke_list.add(style_1);
                } else {

                    if (!textOneStructure.getChapterName().equals("\"\""))
                        items = textOneStructure.getChapterSequenceName() + "  " + textOneStructure.getChapterName();
                    else
                        items = textOneStructure.getChapterSequenceName();
                    SpannableStringBuilder style_1 = new SpannableStringBuilder(items);
                    error_test_reciteword_ke_list.add(style_1);
                }


            }
            //新概念单词测试一级数据源
            else if ("7".equals(textOneStructure.getChapterNo().substring(0, 1))) {


                if (op != 0) {
                    String items = textOneStructure.getChapterSequenceName() + "  " + textOneStructure.getChapterName() + "  &" + op;
                    SpannableStringBuilder style_1 = new SpannableStringBuilder(items);
                    int start = items.indexOf("  &");
                    style_1.setSpan(new ForegroundColorSpan(Color.RED), start, items.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                    error_test_xingainian_ke_list.add(style_1);
                } else {

                    String items = textOneStructure.getChapterSequenceName() + "  " + textOneStructure.getChapterName();
                    SpannableStringBuilder style_1 = new SpannableStringBuilder(items);
                    error_test_xingainian_ke_list.add(style_1);
                }

            }
            //语法学习一级数据源
            else if ("8".equals(textOneStructure.getChapterNo().substring(0, 1))) {


                if (op != 0) {
                    String items = textOneStructure.getChapterSequenceName() + "  " + textOneStructure.getChapterName() + "  &" + op;
                    SpannableStringBuilder style_1 = new SpannableStringBuilder(items);
                    int start = items.indexOf("  &");
                    style_1.setSpan(new ForegroundColorSpan(Color.RED), start, items.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                    error_test__yufa_ke_list.add(style_1);
                } else {

                    String items = textOneStructure.getChapterSequenceName() + "  " + textOneStructure.getChapterName();
                    SpannableStringBuilder style_1 = new SpannableStringBuilder(items);
                    error_test__yufa_ke_list.add(style_1);
                }


            }
            //日积月累一级数据源
            else if ("C".equalsIgnoreCase(textOneStructure.getChapterNo().substring(0, 1))) {


                if (op != 0) {
                    String items = textOneStructure.getChapterSequenceName() + "  " + textOneStructure.getChapterName() + "  &" + op;
                    SpannableStringBuilder style_1 = new SpannableStringBuilder(items);
                    int start = items.indexOf("  &");
                    style_1.setSpan(new ForegroundColorSpan(Color.RED), start, items.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                    error_test_rijiyue_ke_list.add(style_1);
                } else {

                    String items = textOneStructure.getChapterSequenceName() + "  " + textOneStructure.getChapterName();
                    SpannableStringBuilder style_1 = new SpannableStringBuilder(items);
                    error_test_rijiyue_ke_list.add(style_1);
                }
            }
        }


        do_error_test_kousuan(v, error_test_kousuan_ke_list);
        do_error_test_aosai(v, error_test_aosai_ke_list);
        do_error_test_rijiyue(v, error_test_rijiyue_ke_list);
        do_error_test_reciteword(v, error_test_reciteword_ke_list);
        do_error_test__yufa(v, error_test__yufa_ke_list);
        do_error_test_xingainian(v, error_test_xingainian_ke_list);


    }





    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.cuoti_layout, null);
        initSpinner(v);
        initView(v);
        Viewswitch();
        return v;
    }

    private void do_error_test_xingainian(View v, List<SpannableStringBuilder> error_test_xingainian_ke_list) {
        final String[] sectionNo = {""};
        final String[] nub_zu = {""};

        final List<ErrorSubject_two> xtdctmLister = new ArrayList<>();

        //获取存在sharePrefrence当前的用户的标识
        String user_id = App.newInstance().GetSharePrefrence_kejiezu(getContext());
        final List<TextOneStructure> Get_ke = FindDB_kejiezu.Get_ke(getContext(), user_id, "7");

        Collections.sort(Get_ke);

        Cuoti_spinner_item_Adapter ke_adapter = new Cuoti_spinner_item_Adapter(getContext(), error_test_xingainian_ke_list);
        error_test_xingainian_ke.setAdapter(ke_adapter);
        error_test_xingainian_ke.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                xtdctmLister.clear();
                String chapterNo = Get_ke.get(position).getChapterNo();
                final List<TextTwoStructure> Get_jie = FindDB_kejiezu.Get_jie(getContext(), chapterNo);
                Collections.sort(Get_jie);
                List<ErrorSubjectNub> xtdctmList = App.getDaoSession(getContext()).queryBuilder(ErrorSubjectNub.class).where(ErrorSubjectNubDao.Properties.Examination_number.like(chapterNo + "%")).distinct().list();
                Log.d("xtdctmListtop", "s" + JSON.toJSONString(xtdctmList));

                for (ErrorSubjectNub xtdctm : xtdctmList) {
                    if (chapterNo.equals(xtdctm.getExamination_number().substring(0, 7))) {
                        ErrorSubject_two two = new ErrorSubject_two();
                        two.setItemNo(xtdctm.getExamination_number());
                        two.setTwoJieGou(xtdctm.getExamination_number().substring(0, 9));

                        xtdctmLister.add(two);

                    }
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        LinearLayout kousuan_error_selectbutton = (LinearLayout) v.findViewById(R.id.xingainian_go);
        kousuan_error_selectbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("二次接收", "p");
                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        try {

                            final HashMap<String, String> mapp = new HashMap<>();

                            String itemNo = JSON.toJSONString(xtdctmLister);

                            Log.d("itemNO&pp", "s" + itemNo);
                            mapp.put("No", itemNo);

                            String responseup = Post_error_question.getStringCha(Constant.post_ErrorQuestion_one_speical, mapp);

//                            responsess第一个接口的数据源
                            List<ErrorSubjectNub> xtdctmList = App.getDaoSession(getContext()).queryBuilder(ErrorSubjectNub.class).where(ErrorSubjectNubDao.Properties.Examination_number.like("7%")).distinct().list();
                            Log.d("reooo", JSON.toJSONString(xtdctmList));
//                           responseup第二个接口的数据源


                            JSONObject jsonObject1 = new JSONObject(responseup);
//                            Map<String, String> maptop = (Map<String, String>) JSON.parse(responseup);
                            List<ErrorSubject_two> errorSubject_twoList = JSON.parseArray(jsonObject1.get("data").toString(), ErrorSubject_two.class);

                            for (ErrorSubject_two two : errorSubject_twoList) {
                                for (ErrorSubjectNub xtdctmjl : xtdctmList) {
                                    if (two.getItemNo().equals(xtdctmjl.getExamination_number())) {
                                        two.setAnswer(xtdctmjl.getAnswer());
                                    }
                                }

                            }

                            Log.d("二次接收", "p" + responseup);
                            if (!errorSubject_twoList.isEmpty()) {
                                Intent intent = new Intent(getContext(), Error_question_report.class);
                                intent.putParcelableArrayListExtra("response", (ArrayList<? extends Parcelable>) errorSubject_twoList);
                                startActivity(intent);
                            } else {
                                Looper.prepare();
                                Toast.makeText(getContext(), "当前选择的节无错题", Toast.LENGTH_SHORT).show();
                                Looper.loop();

                            }
                            xtdctmList.clear();
                            errorSubject_twoList.clear();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        /****************************************************/
    }

    private void do_error_test__yufa(View v, List<SpannableStringBuilder> error_test__yufa_ke_list) {


        final List<ErrorSubject_two> xtdctmLister = new ArrayList<>();
        xtdctmLister.clear();
        //获取存在sharePrefrence当前的用户的标识
        String user_id = App.newInstance().GetSharePrefrence_kejiezu(getContext());
        final List<TextOneStructure> Get_ke = FindDB_kejiezu.Get_ke(getContext(), user_id, "8");
        Collections.sort(Get_ke);

        Cuoti_spinner_item_Adapter ke_adapter = new Cuoti_spinner_item_Adapter(getContext(), error_test__yufa_ke_list);
        error_test__yufa_ke.setAdapter(ke_adapter);
        error_test__yufa_ke.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String chapterNo = Get_ke.get(position).getChapterNo() + "00";

                JSONObject jsonObject = null;

                xtdctmLister.clear();
                List<ErrorSubjectNub> xtdctmList = App.getDaoSession(getContext()).queryBuilder(ErrorSubjectNub.class).where(ErrorSubjectNubDao.Properties.Examination_number.like(chapterNo + "%")).distinct().list();
                Log.d("xtdctmListtop", "s" + JSON.toJSONString(xtdctmList));
                for (ErrorSubjectNub xtdctm : xtdctmList) {
                    if (chapterNo.equals(xtdctm.getExamination_number().substring(0, 9))) {
                        ErrorSubject_two two = new ErrorSubject_two();
                        two.setItemNo(xtdctm.getExamination_number());
                        two.setTwoJieGou(chapterNo);
                        xtdctmLister.add(two);

                    }
                }
                Log.d("@@@ps", JSON.toJSONString(xtdctmLister));


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


//        yufa_test_go
        LinearLayout kousuan_error_selectbutton = (LinearLayout) v.findViewById(R.id.yufa_test_go);
        kousuan_error_selectbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("二次接收ppp", "p");
                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        try {

                            final HashMap<String, String> mapp = new HashMap<>();

                            String itemNo = JSON.toJSONString(xtdctmLister);
//                            xtdctmLister.clear();
                            mapp.put("No", itemNo);
                            Log.d("itemNo", "s" + itemNo);
                            String responseup = Post_error_question.getStringCha(Constant.post_ErrorQuestion_two, mapp);

//                            responsess第一个接口的数据源
                            List<ErrorSubjectNub> xtdctmList = App.getDaoSession(getContext()).queryBuilder(ErrorSubjectNub.class).where(ErrorSubjectNubDao.Properties.Examination_number.like("8%")).distinct().list();
                            Log.d("reooo", JSON.toJSONString(xtdctmList));
//                           responseup第二个接口的数据源


                            JSONObject jsonObject1 = new JSONObject(responseup);
//                            Map<String, String> maptop = (Map<String, String>) JSON.parse(responseup);
                            List<ErrorSubject_two> errorSubject_twoList = JSON.parseArray(jsonObject1.get("data").toString(), ErrorSubject_two.class);

                            for (ErrorSubject_two two : errorSubject_twoList) {
                                for (ErrorSubjectNub xtdctmjl : xtdctmList) {
                                    if (two.getItemNo().equals(xtdctmjl.getExamination_number())) {
                                        two.setAnswer(xtdctmjl.getAnswer());
                                    }
                                }

                            }

                            Log.d("二次接收", "p" + responseup);

                            if (!errorSubject_twoList.isEmpty()) {
                                Intent intent = new Intent(getContext(), Error_question_report.class);
                                intent.putParcelableArrayListExtra("response", (ArrayList<? extends Parcelable>) errorSubject_twoList);
                                startActivity(intent);
                            } else {
                                Looper.prepare();
                                Toast.makeText(getContext(), "当前选择的节无错题", Toast.LENGTH_SHORT).show();
                                Looper.loop();

                            }
                            xtdctmList.clear();
                            errorSubject_twoList.clear();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });


    }


    private void do_error_test_reciteword(View v, List<SpannableStringBuilder> error_test_reciteword_ke_list) {
        final String[] sectionNo = {""};
        final String[] nub_zu = {""};

        final List<ErrorSubject_two> xtdctmLister = new ArrayList<>();

        //获取存在sharePrefrence当前的用户的标识
        String user_id = App.newInstance().GetSharePrefrence_kejiezu(getContext());
        final List<TextOneStructure> Get_ke = FindDB_kejiezu.Get_ke(getContext(), user_id, "5");

        Collections.sort(Get_ke);

        Cuoti_spinner_item_Adapter ke_adapter = new Cuoti_spinner_item_Adapter(getContext(), error_test_reciteword_ke_list);
        error_test_reciteword_ke.setAdapter(ke_adapter);
        error_test_reciteword_ke.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                xtdctmLister.clear();
                String chapterNo = Get_ke.get(position).getChapterNo();
                final List<TextTwoStructure> Get_jie = FindDB_kejiezu.Get_jie(getContext(), chapterNo);
                Collections.sort(Get_jie);
                List<ErrorSubjectNub> xtdctmList = App.getDaoSession(getContext()).queryBuilder(ErrorSubjectNub.class).where(ErrorSubjectNubDao.Properties.Examination_number.like(chapterNo + "%")).distinct().list();
                Log.d("xtdctmListtop", "s" + JSON.toJSONString(xtdctmList));

                for (ErrorSubjectNub xtdctm : xtdctmList) {
                    if (chapterNo.equals(xtdctm.getExamination_number().substring(0, 7))) {
                        ErrorSubject_two two = new ErrorSubject_two();
                        two.setItemNo(xtdctm.getExamination_number());
                        two.setTwoJieGou(xtdctm.getExamination_number().substring(0, 9));
                        xtdctmLister.add(two);

                    }
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        LinearLayout kousuan_error_selectbutton = (LinearLayout) v.findViewById(R.id.recite_word_go);
        kousuan_error_selectbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("二次接收", "p");
                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        try {

                            final HashMap<String, String> mapp = new HashMap<>();

                            String itemNo = JSON.toJSONString(xtdctmLister);

                            Log.d("itemNO&pp", "s" + itemNo);
                            mapp.put("No", itemNo);

                            String responseup = Post_error_question.getStringCha(Constant.post_ErrorQuestion_one_speical, mapp);

//                            responsess第一个接口的数据源
                            List<ErrorSubjectNub> xtdctmList = App.getDaoSession(getContext()).queryBuilder(ErrorSubjectNub.class).where(ErrorSubjectNubDao.Properties.Examination_number.like("5%")).distinct().list();
                            Log.d("reooo", JSON.toJSONString(xtdctmList));
//                           responseup第二个接口的数据源


                            JSONObject jsonObject1 = new JSONObject(responseup);
//                            Map<String, String> maptop = (Map<String, String>) JSON.parse(responseup);
                            List<ErrorSubject_two> errorSubject_twoList = JSON.parseArray(jsonObject1.get("data").toString(), ErrorSubject_two.class);

                            for (ErrorSubject_two two : errorSubject_twoList) {
                                for (ErrorSubjectNub xtdctmjl : xtdctmList) {
                                    if (two.getItemNo().equals(xtdctmjl.getExamination_number())) {
                                        two.setAnswer(xtdctmjl.getAnswer());
                                    }
                                }

                            }

                            Log.d("二次接收", "p" + responseup);
                            if (!errorSubject_twoList.isEmpty()) {
                                Intent intent = new Intent(getContext(), Error_question_report.class);
                                intent.putParcelableArrayListExtra("response", (ArrayList<? extends Parcelable>) errorSubject_twoList);
                                startActivity(intent);
                            } else {
                                Looper.prepare();
                                Toast.makeText(getContext(), "当前选择的节无错题", Toast.LENGTH_SHORT).show();
                                Looper.loop();

                            }
                            xtdctmList.clear();
                            errorSubject_twoList.clear();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });


    }

    private void do_error_test_rijiyue(View v, List<SpannableStringBuilder> error_test_rijiyue_ke_list) {
        final String[] sectionNo = {""};

        final List<ErrorSubject_two> xtdctmLister = new ArrayList<>();

        //获取存在sharePrefrence当前的用户的标识
        String user_id = App.newInstance().GetSharePrefrence_kejiezu(getContext());
        final List<TextOneStructure> Get_ke = FindDB_kejiezu.Get_ke(getContext(), user_id, "C");
        Collections.sort(Get_ke);

        Cuoti_spinner_item_Adapter ke_adapter = new Cuoti_spinner_item_Adapter(getContext(), error_test_rijiyue_ke_list);
        error_test_rijiyue_ke.setAdapter(ke_adapter);
        error_test_rijiyue_ke.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<SpannableStringBuilder> jie_list = new ArrayList<>();
                String chapterNo = Get_ke.get(position).getChapterNo();
                final List<TextTwoStructure> Get_jie = FindDB_kejiezu.Get_jie(getContext(), chapterNo);
                Collections.sort(Get_jie);

//                    jie_list.add(textTwoStructure.getSectionSequenceName() + textTwoStructure.getSectionName());

                JSONObject jsonObject = null;

                xtdctmLister.clear();
                List<ErrorSubjectNub> xtdctmList = App.getDaoSession(getContext()).queryBuilder(ErrorSubjectNub.class).where(ErrorSubjectNubDao.Properties.Examination_number.like(chapterNo + "%")).distinct().list();
                Log.d("xtdctmListtop", "s" + JSON.toJSONString(xtdctmList));

                for (ErrorSubjectNub xtdctm : xtdctmList) {
                    if (chapterNo.equals(xtdctm.getExamination_number().substring(0, 7))) {
                        ErrorSubject_two two = new ErrorSubject_two();
                        two.setItemNo(xtdctm.getExamination_number());
                        two.setTwoJieGou(xtdctm.getExamination_number().substring(0, 9));
                        xtdctmLister.add(two);

                    }
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

///
        LinearLayout kousuan_error_selectbutton = (LinearLayout) v.findViewById(R.id.chengyujielong_go);
        kousuan_error_selectbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("二次接收", "p");
                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        try {

                            final HashMap<String, String> mapp = new HashMap<>();

                            String itemNo = JSON.toJSONString(xtdctmLister);
//                            xtdctmLister.clear();
                            mapp.put("No", itemNo);

                            String responseup = Post_error_question.getStringCha(Constant.post_ErrorQuestion_one_speical, mapp);

//                            responsess第一个接口的数据源
                            List<ErrorSubjectNub> xtdctmList = App.getDaoSession(getContext()).queryBuilder(ErrorSubjectNub.class).where(ErrorSubjectNubDao.Properties.Examination_number.like("C%")).distinct().list();
                            Log.d("reooo", JSON.toJSONString(xtdctmList));
//                           responseup第二个接口的数据源


                            JSONObject jsonObject1 = new JSONObject(responseup);
//                            Map<String, String> maptop = (Map<String, String>) JSON.parse(responseup);
                            List<ErrorSubject_two> errorSubject_twoList = JSON.parseArray(jsonObject1.get("data").toString(), ErrorSubject_two.class);

                            for (ErrorSubject_two two : errorSubject_twoList) {
                                for (ErrorSubjectNub xtdctmjl : xtdctmList) {
                                    if (two.getItemNo().equals(xtdctmjl.getExamination_number())) {
                                        two.setAnswer(xtdctmjl.getAnswer());
                                    }
                                }

                            }

                            Log.d("二次接收", "p" + responseup);

                            if (!errorSubject_twoList.isEmpty()) {
                                Intent intent = new Intent(getContext(), Error_question_report.class);
                                intent.putParcelableArrayListExtra("response", (ArrayList<? extends Parcelable>) errorSubject_twoList);
                                startActivity(intent);
                            } else {
                                Looper.prepare();
                                Toast.makeText(getContext(), "当前选择的节无错题", Toast.LENGTH_SHORT).show();
                                Looper.loop();

                            }
                            xtdctmList.clear();
                            errorSubject_twoList.clear();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });


    }

    private void do_error_test_aosai(View v, List<SpannableStringBuilder> error_test_aosai_ke_list) {
        final String[] sectionNo = {""};
        final List<ErrorSubject_two> xtdctmLister = new ArrayList<>();
        xtdctmLister.clear();
        //获取存在sharePrefrence当前的用户的标识
        String user_id = App.newInstance().GetSharePrefrence_kejiezu(getContext());
        final List<TextOneStructure> Get_ke = FindDB_kejiezu.Get_ke(getContext(), user_id, "3");
        Collections.sort(Get_ke);

        Cuoti_spinner_item_Adapter ke_adapter = new Cuoti_spinner_item_Adapter(getContext(), error_test_aosai_ke_list);
        error_test_aosai_ke.setAdapter(ke_adapter);
        error_test_aosai_ke.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<SpannableStringBuilder> jie_list = new ArrayList<>();
                String chapterNo = Get_ke.get(position).getChapterNo();
                final List<TextTwoStructure> Get_jie = FindDB_kejiezu.Get_jie(getContext(), chapterNo);
                Collections.sort(Get_jie);
                jie_list.clear();

                for (TextTwoStructure textTwoStructure : Get_jie) {
                    int op = 0;
                    op = (int) App.getDaoSession(getContext()).queryBuilder(ErrorSubjectNub.class).where(ErrorSubjectNubDao.Properties.Examination_number.like(textTwoStructure.getSectionNo() + "%")).distinct().count();
                    if (op != 0) {
                        String items = textTwoStructure.getSectionSequenceName() + "  " + textTwoStructure.getSectionName() + "  &" + op;
                        SpannableStringBuilder style_1 = new SpannableStringBuilder(items);
                        int start = items.indexOf("  &");
                        style_1.setSpan(new ForegroundColorSpan(Color.RED), start, items.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                        jie_list.add(style_1);
                    } else {

                        String items = textTwoStructure.getSectionSequenceName() + "  " + textTwoStructure.getSectionName();
                        SpannableStringBuilder style_1 = new SpannableStringBuilder(items);
                        jie_list.add(style_1);
                    }
                }


                Cuoti_spinner_item_Adapter jie_adapter = new Cuoti_spinner_item_Adapter(getContext(), jie_list);
                error_test_aosan_jie.setAdapter(jie_adapter);
                error_test_aosan_jie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        sectionNo[0] = Get_jie.get(position).getSectionNo();
                        JSONObject jsonObject = null;

                        xtdctmLister.clear();


                        List<ErrorSubjectNub> xtdctmList = App.getDaoSession(getContext()).queryBuilder(ErrorSubjectNub.class).where(ErrorSubjectNubDao.Properties.Examination_number.like(sectionNo[0] + "%")).distinct().list();
                        Log.d("xtdctmListtop", "s" + JSON.toJSONString(xtdctmList));
                        for (ErrorSubjectNub xtdctm : xtdctmList) {
                            if (sectionNo[0].equals(xtdctm.getExamination_number().substring(0, 9))) {
                                ErrorSubject_two two = new ErrorSubject_two();
                                two.setItemNo(xtdctm.getExamination_number());
                                two.setTwoJieGou(sectionNo[0]);
                                xtdctmLister.add(two);
                            }
                        }
                        Log.d("@@@p", JSON.toJSONString(xtdctmLister));


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        haoti_jie_select1


        LinearLayout kousuan_error_selectbutton = (LinearLayout) v.findViewById(R.id.haoti_jie_select1);
        kousuan_error_selectbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("二次接收", "p");
                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        try {

                            final HashMap<String, String> mapp = new HashMap<>();

                            String itemNo = JSON.toJSONString(xtdctmLister);
//                            xtdctmLister.clear();
                            mapp.put("No", itemNo);

                            String responseup = Post_error_question.getStringCha(Constant.post_ErrorQuestion_two, mapp);

//                            responsess第一个接口的数据源
                            List<ErrorSubjectNub> xtdctmList = App.getDaoSession(getContext()).queryBuilder(ErrorSubjectNub.class).where(ErrorSubjectNubDao.Properties.Examination_number.like("3%")).distinct().list();
                            Log.d("reooo", JSON.toJSONString(xtdctmList));
//                           responseup第二个接口的数据源


                            JSONObject jsonObject1 = new JSONObject(responseup);
//                            Map<String, String> maptop = (Map<String, String>) JSON.parse(responseup);

                            List<Aosai_Question> errorSubject_twoList = new Gson().fromJson(jsonObject1.get("data").toString(), new TypeToken<List<Aosai_Question>>() {
                            }.getType());

                            for (Aosai_Question two : errorSubject_twoList) {
                                for (ErrorSubjectNub xtdctmjl : xtdctmList) {
                                    if (two.getItemNo().equals(xtdctmjl.getExamination_number())) {
                                        two.setMyChoice(xtdctmjl.getAnswer());
                                    }
                                }

                            }

                            Log.d("二次接收", "p" + responseup);

                            if (!errorSubject_twoList.isEmpty()) {
                                Intent intent = new Intent(getContext(), Aosai_Error_question_report.class);
                                intent.putParcelableArrayListExtra("response", (ArrayList<? extends Parcelable>) errorSubject_twoList);
                                startActivity(intent);
                            } else {
                                Looper.prepare();
                                Toast.makeText(getContext(), "当前选择的节无错题", Toast.LENGTH_SHORT).show();
                                Looper.loop();

                            }
                            xtdctmList.clear();
                            errorSubject_twoList.clear();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });


    }

    private void do_error_test_kousuan(View v, List<SpannableStringBuilder> error_test_kousuan_ke_list) {
        final String[] sectionNo = {""};
        final List<ErrorSubject_two> xtdctmLister = new ArrayList<>();
        xtdctmLister.clear();
        //获取存在sharePrefrence当前的用户的标识
        String user_id = App.newInstance().GetSharePrefrence_kejiezu(getContext());
        final List<TextOneStructure> Get_ke = FindDB_kejiezu.Get_ke(getContext(), user_id, "1");
        Collections.sort(Get_ke);

        Cuoti_spinner_item_Adapter ke_adapter = new Cuoti_spinner_item_Adapter(getContext(), error_test_kousuan_ke_list);
        error_test_kousuan_ke.setAdapter(ke_adapter);
        error_test_kousuan_ke.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<SpannableStringBuilder> jie_list = new ArrayList<>();
                String chapterNo = Get_ke.get(position).getChapterNo();
                final List<TextTwoStructure> Get_jie = FindDB_kejiezu.Get_jie(getContext(), chapterNo);
                Collections.sort(Get_jie);
                for (TextTwoStructure textTwoStructure : Get_jie) {
                    int op = 0;
                    op = (int) App.getDaoSession(getContext()).queryBuilder(ErrorSubjectNub.class).where(ErrorSubjectNubDao.Properties.Examination_number.like(textTwoStructure.getSectionNo() + "%")).distinct().count();
                    if (op != 0) {
                        String items = textTwoStructure.getSectionSequenceName() + "  " + textTwoStructure.getSectionName() + "  &" + op;
                        SpannableStringBuilder style_1 = new SpannableStringBuilder(items);
                        int start = items.indexOf("  &");
                        style_1.setSpan(new ForegroundColorSpan(Color.RED), start, items.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                        jie_list.add(style_1);
                    } else {
                        String items = textTwoStructure.getSectionSequenceName() + "  " + textTwoStructure.getSectionName();
                        SpannableStringBuilder style_1 = new SpannableStringBuilder(items);
                        jie_list.add(style_1);
                    }
                }


                Cuoti_spinner_item_Adapter jie_adapter = new Cuoti_spinner_item_Adapter(getContext(), jie_list);
                error_test_kousuan_jie.setAdapter(jie_adapter);
                error_test_kousuan_jie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        sectionNo[0] = Get_jie.get(position).getSectionNo();
                        Log.d("sectionNo", sectionNo[0]);
                        JSONObject jsonObject = null;

                        xtdctmLister.clear();

                        List<ErrorSubjectNub> xtdctmList = App.getDaoSession(getContext()).queryBuilder(ErrorSubjectNub.class).where(ErrorSubjectNubDao.Properties.Examination_number.like(sectionNo[0] + "%")).distinct().list();
                        for (ErrorSubjectNub xtdctm : xtdctmList) {
                            if (sectionNo[0].equals(xtdctm.getExamination_number().substring(0, 9))) {
                                ErrorSubject_two two = new ErrorSubject_two();
                                two.setItemNo(xtdctm.getExamination_number());
                                two.setTwoJieGou(sectionNo[0]);
                                xtdctmLister.add(two);
                            }
                        }
                        Log.d("@@@p", JSON.toJSONString(xtdctmLister));


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        LinearLayout kousuan_error_selectbutton = (LinearLayout) v.findViewById(R.id.kousuan_error_selectbutton);

        kousuan_error_selectbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        try {

                            final HashMap<String, String> mapp = new HashMap<>();
                            String itemNo = JSON.toJSONString(xtdctmLister);
                            System.out.println("list反映" + itemNo);
//                            xtdctmLister.clear();


                            mapp.put("No", itemNo);

                            String responseup = Post_error_question.getStringCha(Constant.post_ErrorQuestion_two, mapp);

//                            responsess第一个接口的数据源

                            List<ErrorSubjectNub> xtdctmList = App.getDaoSession(getContext()).queryBuilder(ErrorSubjectNub.class).where(ErrorSubjectNubDao.Properties.Examination_number.like("1%")).distinct().list();
                            Log.d("reooo", JSON.toJSONString(xtdctmList));
//                           responseup第二个接口的数据源
                            JSONObject jsonObject1 = new JSONObject(responseup);
//                            Map<String, String> maptop = (Map<String, String>) JSON.parse(responseup);
                            List<ErrorSubject_two> errorSubject_twoList = JSON.parseArray(jsonObject1.get("data").toString(), ErrorSubject_two.class);

                            for (ErrorSubject_two two : errorSubject_twoList) {
                                for (ErrorSubjectNub xtdctmjl : xtdctmList) {
                                    if (two.getItemNo().equals(xtdctmjl.getExamination_number())) {
                                        two.setAnswer(xtdctmjl.getAnswer());
                                    }
                                }

                            }

                            Log.d("二次接收", "p" + xtdctmList);

                            if (!errorSubject_twoList.isEmpty()) {
                                Intent intent = new Intent(getContext(), Error_question_report.class);
                                intent.putParcelableArrayListExtra("response", (ArrayList<? extends Parcelable>) errorSubject_twoList);
                                startActivity(intent);
                            } else {
                                Looper.prepare();
                                Toast.makeText(getContext(), "当前选择的节无错题", Toast.LENGTH_SHORT).show();
                                Looper.loop();

                            }
                            xtdctmList.clear();
                            errorSubject_twoList.clear();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    private void initSpinner(View v) {
        error_test_kousuan_ke = (Spinner) v.findViewById(R.id.kousuan_error_ke_select);
        error_test_kousuan_jie = (Spinner) v.findViewById(R.id.kousuan_error_jie_select);
        error_test_aosai_ke = (Spinner) v.findViewById(R.id.aosai_error_zhang_select);
        error_test_aosan_jie = (Spinner) v.findViewById(R.id.aosai_error_jie_select);
        error_test_rijiyue_ke = (Spinner) v.findViewById(R.id.error_test_rijiyue_ke);
        error_test_rijiyue_jie = (Spinner) v.findViewById(R.id.error_test_rijiyue_jie);
        error_test_reciteword_ke = (Spinner) v.findViewById(R.id.error_test_reciteword_ke);
        error_test_reciteword_jie = (Spinner) v.findViewById(R.id.error_test_reciteword_jie);
        error_test__yufa_ke = (Spinner) v.findViewById(R.id.errortestyufake);
//      error_test_yufa_jie = (Spinner) v.findViewById(R.id.errortestyufajie);
        error_test_xingainian_ke = (Spinner) v.findViewById(R.id.error_test_xingainian_ke);
    }

    private void Viewswitch() {
        lay_math.setVisibility(View.VISIBLE);
        lay_english.setVisibility(View.GONE);
        lay_yuwen.setVisibility(View.GONE);
        math.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                math.setTextColor(getResources().getColor(R.color.zhuti));
//                yuwen.setTextColor(Color.BLACK);
//                english.setTextColor(Color.BLACK);
                math_line.setVisibility(View.VISIBLE);
                chinese_line.setVisibility(View.GONE);
                english_line.setVisibility(View.GONE);

                lay_english.setVisibility(View.GONE);
                lay_math.setVisibility(View.VISIBLE);
                lay_yuwen.setVisibility(View.GONE);
            }
        });
        yuwen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                math.setTextColor(Color.BLACK);
//                yuwen.setTextColor(getResources().getColor(R.color.zhuti));
//                english.setTextColor(Color.BLACK);

                math_line.setVisibility(View.GONE);
                chinese_line.setVisibility(View.VISIBLE);
                english_line.setVisibility(View.GONE);

                lay_english.setVisibility(View.GONE);
                lay_math.setVisibility(View.GONE);
                lay_yuwen.setVisibility(View.VISIBLE);

            }
        });

        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                math.setTextColor(Color.BLACK);
//                yuwen.setTextColor(Color.BLACK);
//                english.setTextColor(getResources().getColor(R.color.zhuti));

                math_line.setVisibility(View.GONE);
                chinese_line.setVisibility(View.GONE);
                english_line.setVisibility(View.VISIBLE);

                lay_english.setVisibility(View.VISIBLE);
                lay_math.setVisibility(View.GONE);
                lay_yuwen.setVisibility(View.GONE);

            }
        });
    }

    private void initEnvent() {


        new AsyncTask<Void, Void, int[]>() {

            @Override
            protected int[] doInBackground(Void... params) {
                kousuan_errornub = (int) App.getDaoSession(getContext()).queryBuilder(ErrorSubjectNub.class).where(ErrorSubjectNubDao.Properties.Examination_number.like("1%")).distinct().count();
                aosainub = (int) App.getDaoSession(getContext()).queryBuilder(ErrorSubjectNub.class).where(ErrorSubjectNubDao.Properties.Examination_number.like("3%")).distinct().count();
                rijiyuelei_nub = (int) App.getDaoSession(getContext()).queryBuilder(ErrorSubjectNub.class).where(ErrorSubjectNubDao.Properties.Examination_number.like("C%")).distinct().count();
                recite_nub = (int) App.getDaoSession(getContext()).queryBuilder(ErrorSubjectNub.class).where(ErrorSubjectNubDao.Properties.Examination_number.like("5%")).distinct().count();
                yufa_nub = (int) App.getDaoSession(getContext()).queryBuilder(ErrorSubjectNub.class).where(ErrorSubjectNubDao.Properties.Examination_number.like("8%")).distinct().count();
                xingainian_nub = (int) App.getDaoSession(getContext()).queryBuilder(ErrorSubjectNub.class).where(ErrorSubjectNubDao.Properties.Examination_number.like("7%")).distinct().count();
                mathnub = kousuan_errornub + aosainub;
                chinesenub = rijiyuelei_nub;
                englishnub = recite_nub + yufa_nub + xingainian_nub;
                int[] strings = new int[10];
                strings[0] = kousuan_errornub;
                strings[1] = aosainub;
                strings[2] = rijiyuelei_nub;
                strings[3] = recite_nub;
                strings[4] = yufa_nub;
                strings[5] = xingainian_nub;
                strings[6] = mathnub;
                strings[7] = chinesenub;
                strings[8] = englishnub;
                Log.d("数量内容",JSON.toJSONString(strings)+"==");
                return strings;
            }

            @Override
            protected void onPostExecute(int[] strings) {
                super.onPostExecute(strings);

                    if (strings[6] == 0) {
                        mathno.setVisibility(View.INVISIBLE);
                    }
                    if (strings[7] == 0) {
                        chineseno.setVisibility(View.INVISIBLE);
                    }

                    if (strings[8] == 0) {
                        englishno.setVisibility(View.INVISIBLE);
                    }

                    if (strings[0] == 0) {
                        kousuan_errorn.setVisibility(View.INVISIBLE);
                    }

                    if (strings[1] == 0) {
                        aosaierrorn.setVisibility(View.INVISIBLE);
                    }

                    if ( strings[3] == 0) {
                        recite_no.setVisibility(View.INVISIBLE);
                    }
                    if (strings[4] == 0) {
                        yufa_no.setVisibility(View.INVISIBLE);
                    }

                    if (strings[5] == 0) {
                        xingainian_no.setVisibility(View.INVISIBLE);
                    }

                    if (strings[2] == 0) {
                        rijiyuelei_no.setVisibility(View.INVISIBLE);
                    }
                    //语数外的错题数量
                    mathno.setText(String.valueOf(strings[6]));
                    chineseno.setText(String.valueOf(strings[7]));
                    englishno.setText(String.valueOf(strings[8]));
                    //口算题卡的错题数
                    kousuan_errorn.setText(String.valueOf(strings[0]));
                    //奥赛的数量
                    aosaierrorn.setText(String.valueOf(strings[1] ));
                    recite_no.setText(String.valueOf(strings[3]));
                    yufa_no.setText(String.valueOf(strings[4]));
                    xingainian_no.setText(String.valueOf(strings[5]));
                    rijiyuelei_no.setText(String.valueOf(strings[2]));


            }
        }.execute();


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        error_test_kousuan_ke_list.clear();

        error_test_aosai_ke_list.clear();

        error_test_rijiyue_ke_list.clear();

        error_test_reciteword_ke_list.clear();

        error_test__yufa_ke_list.clear();
        error_test_xingainian_ke_list.clear();
    }

    private void initView(View v) {
        xinggainian_container = (LinearLayout) v.findViewById(R.id.xinggainian_container);
        math = (TextView) v.findViewById(R.id.cuoti_math);
        yuwen = (TextView) v.findViewById(R.id.cuoti_chinese);
        english = (TextView) v.findViewById(R.id.cuoti_english);
        lay_math = (LinearLayout) v.findViewById(R.id.lay_cuoti_math);
        lay_yuwen = (LinearLayout) v.findViewById(R.id.lay_cuoti_chinese);
        lay_english = (LinearLayout) v.findViewById(R.id.lay_cuoti_english);
        mathno = (TextView) v.findViewById(R.id.mathno);
        chineseno = (TextView) v.findViewById(R.id.chineseno);
        englishno = (TextView) v.findViewById(R.id.englishno);
        kousuan_errorn = (TextView) v.findViewById(R.id.kousuan_errornub);
        aosaierrorn = (TextView) v.findViewById(R.id.aosaierrornub);

        math_line = v.findViewById(R.id.math_line);
        chinese_line = v.findViewById(R.id.chinese_line);
        english_line = v.findViewById(R.id.english_line);
        math_line.setVisibility(View.VISIBLE);
        chinese_line.setVisibility(View.GONE);
        english_line.setVisibility(View.GONE);
        recite_no = (TextView) v.findViewById(R.id.recite_no);
        yufa_no = (TextView) v.findViewById(R.id.yufa_no);
        xingainian_no = (TextView) v.findViewById(R.id.xingainian_no);
        rijiyuelei_no = (TextView) v.findViewById(R.id.rijiyuelei_nub);


    }


}
