package com.neusoft.sample.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ncapdevi.sample.R;
import com.neusoft.sample.App;
import com.neusoft.sample.Ctrl.Db_CaculationTest;
import com.neusoft.sample.Ctrl.Db_TeacherService;
import com.neusoft.sample.Ctrl.Db_UserService;
import com.neusoft.sample.Ctrl.wenchengcheng.ExitApplication;
import com.neusoft.sample.GreenDao.CalculationTest;
import com.neusoft.sample.GreenDao.TextOneStructure;
import com.neusoft.sample.GreenDao.TextTwoStructure;
import com.neusoft.sample.GreenDao.User;
import com.neusoft.sample.Model.Consant_stringg;
import com.neusoft.sample.Model.Constant;
import com.neusoft.sample.Model.FindDB_kejiezu;
import com.neusoft.sample.Model.Post_Kousuan;
import com.neusoft.sample.Model.Post_er;
import com.neusoft.sample.Model.Post_learn_rijiyuelei;
import com.neusoft.sample.Model.Stitching;
import com.neusoft.sample.Model.aosai_zu_Entity;
import com.neusoft.sample.View.BaseFragment;
import com.neusoft.sample.View.SecondActivity;
import com.neusoft.sample.View.aosai__SecondActivity;
import com.neusoft.sample.View.xel_specialtopic.Xel_SpecialtOpic_Math_Aosai_Learn;
import com.neusoft.sample.View.xel_specialtopic.Xel_SpecialtOpic_englishyufa_learn;
import com.neusoft.sample.View.xel_specialtopic.xel_math_shudu.Game_4;
import com.neusoft.sample.View.xel_specialtopic.xel_math_shudu.Game_6;
import com.neusoft.sample.View.xel_specialtopic.xel_math_shudu.Game_9;
import com.neusoft.sample.View.xel_specialtopic.xel_specialtopic_math_aosai_text;
import com.neusoft.sample.View.xel_specialtopic.yangkangkang.xel_specialt_chinese_rijiyuelei;
import com.neusoft.sample.View.xel_specialtopic.yangkangkang.xel_specialt_english_textbooklearn;
import com.neusoft.sample.View.xel_specialtopic.yangkangkang.xel_specialt_guoxue_jindian;
import com.neusoft.sample.View.xel_specialtopic.yangkangkang.xel_specialt_kewai_shici;
import com.neusoft.sample.View.xel_specialtopic.yangkangkang.xel_specialt_maozedong_shici;
import com.neusoft.sample.View.xel_specialtopic.yangkangkang.xel_specialt_math_shudu_learn;
import com.neusoft.sample.View.xel_specialtopic.yangkangkang.xel_specialt_stu_shici;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by niccapdevila on 3/26/16.
 */
public class xel_specialtopic_fragment extends BaseFragment {
    String guoxue_name = "";
    String rejiyuelei = "";
//    String a ="";
    private LinearLayout btn_specialt_math_aosai_ceshi,btn_specialt_chinese_rijiyuelei,btn_specialt_math_aosai_rijiyueleiceshi,
        btn_specialt_math_aosai_rijiyueleilearn,english_yufatest,english_learn_specialt,
        Imagbtn_kewai_shici,Imagbtn_stu_shici,Imagebtn_maozedong_shi,Image_guoxue_jingdian,Image_shudu_learn;
//    下面是    数独测试方面的   spinner   的联动   初始化
     private Spinner spe_test_1 = null;  //四六宫
    private Spinner spe_test_2 = null;     //初中高 级
    private Spinner spe_test_3 = null;    //题数
    ArrayAdapter<String> adapter_shudu_1 = null;  //四六宫适配器
    ArrayAdapter<String> adapter_shudu_2 = null;    //初中高 级适配器
    ArrayAdapter<String> adapter_shudu_3 = null;    //题数适配器
    static int provincePosition  = 3;
    String positions;
    String rijiyuelei_ceshi = "";
    String rijiyuelei_ceshi_zu="";
    String name = "";
    int vi = 1;
    //////////////////检查网络的定义、、、、、、、、、///////////////
    Handler handler_aosai;
    Handler handler_er_xingainian;


    ArrayAdapter zu_adapter_er;
    String nub_zu;

    Handler handler_riji;
    Handler handler_er_yufa;

    LinearLayout xinggainian_visibility;

    private  Boolean bool;
    //宫 选项值
    private String[] string_spe_test_1 = new String[] {"四宫","六宫","九宫"};//,四六公    分级
    //难度 选项值

    private String[][] string_spe_test_2 = new String[][]
            {
                    { "入门","初级", "中级", "高级","骨灰" },
                    { "入门","初级", "中级", "高级","骨灰" },
                    { "入门","初级", "中级", "高级","骨灰" },
                    { "入门","初级", "中级", "高级","骨灰" },
                    { "入门","初级", "中级", "高级","骨灰" }
            };
    private String[][][] string_spe_test_3 = new String[][][]
            {
                    {
                            {"第一题","第一题", "第一题", "第一题","第一题"},
                            {"第一题","第一题", "第一题", "第一题","第一题"},
                            {"第一题","第一题", "第一题", "第一题","第一题"},
                            {"第一题","第一题", "第一题", "第一题","第一题"},
                            {"第一题","第一题", "第一题", "第一题","第一题"},
                            {"第一题","第一题", "第一题", "第一题","第一题"},
                            {"第一题","第一题", "第一题", "第一题","第一题"},
                            {"第一题","第一题", "第一题", "第一题","第一题"},
                            {"第一题","第一题", "第一题", "第一题","第一题"},{"第一题","第一题", "第一题", "第一题","第一题"},
                            {"第一题","第一题", "第一题", "第一题","第一题"},
                            {"第一题","第一题", "第一题", "第一题","第一题"},
                            {"第一题","第一题", "第一题", "第一题","第一题"},
                            {"第一题","第一题", "第一题", "第一题","第一题"},
                            {"第一题","第一题", "第一题", "第一题","第一题"},
                            { "入门","初级", "中级", "高级","骨灰" },
                            { "入门","初级", "中级", "高级","骨灰" },
                            { "入门","初级", "中级", "高级","骨灰" },
                            { "入门","初级", "中级", "高级","骨灰" }
                    },



                    {
                            {"第一题","第一题", "第一题", "第一题","第一题"},
                            {"第一题","第一题", "第一题", "第一题","第一题"},
                            {"第一题","第一题", "第一题", "第一题","第一题"},
                            {"第一题","第一题", "第一题", "第一题","第一题"},
                            {"第一题","第一题", "第一题", "第一题","第一题"},
                            {"第一题","第一题", "第一题", "第一题","第一题"},
                            {"第一题","第一题", "第一题", "第一题","第一题"},
                            {"第一题","第一题", "第一题", "第一题","第一题"},
                            {"第一题","第一题", "第一题", "第一题","第一题"},{"第一题","第一题", "第一题", "第一题","第一题"},
                            {"第一题","第一题", "第一题", "第一题","第一题"},
                            {"第一题","第一题", "第一题", "第一题","第一题"},
                            {"第一题","第一题", "第一题", "第一题","第一题"},
                            {"第一题","第一题", "第一题", "第一题","第一题"},
                            {"第一题","第一题", "第一题", "第一题","第一题"},
                            { "入门","初级", "中级", "高级","骨灰" },
                            { "入门","初级", "中级", "高级","骨灰" },
                            { "入门","初级", "中级", "高级","骨灰" },
                            { "入门","初级", "中级", "高级","骨灰" }
                    },
                    {
                            {"第一题","第一题", "第一题", "第一题","第一题"},
                            {"第一题","第一题", "第一题", "第一题","第一题"},
                            {"第一题","第一题", "第一题", "第一题","第一题"},
                            {"第一题","第一题", "第一题", "第一题","第一题"},
                            {"第一题","第一题", "第一题", "第一题","第一题"},
                            {"第一题","第一题", "第一题", "第一题","第一题"},
                            {"第一题","第一题", "第一题", "第一题","第一题"},
                            {"第一题","第一题", "第一题", "第一题","第一题"},
                            {"第一题","第一题", "第一题", "第一题","第一题"},{"第一题","第一题", "第一题", "第一题","第一题"},
                            {"第一题","第一题", "第一题", "第一题","第一题"},
                            {"第一题","第一题", "第一题", "第一题","第一题"},
                            {"第一题","第一题", "第一题", "第一题","第一题"},
                            {"第一题","第一题", "第一题", "第一题","第一题"},
                            {"第一题","第一题", "第一题", "第一题","第一题"},
                            { "入门","初级", "中级", "高级","骨灰" },
                            { "入门","初级", "中级", "高级","骨灰" },
                            { "入门","初级", "中级", "高级","骨灰" },
                            { "入门","初级", "中级", "高级","骨灰" }
                    }
            };
    //    上面是    数独测试方面的   spinner   的联动   初始化   //县级选项值
   public static xel_specialtopic_fragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        xel_specialtopic_fragment fragment = new xel_specialtopic_fragment();
        fragment.setArguments(args);
        return fragment;
    }

     /*public static xel_specialtopic_fragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        xel_specialtopic_fragment fragment = new xel_specialtopic_fragment();
        fragment.setArguments(args);
        return fragment;
    }*/
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ExitApplication.getInstance().addActivity(getActivity());
        bool  = Consant_stringg.is_internet(getActivity());
        if (!bool) {
            Toast.makeText(getActivity(), "请检查您的网络设置", Toast.LENGTH_SHORT).show();
        }

        View v = inflater.inflate(R.layout.xel_specialtopic,null);
        xinggainian_visibility= (LinearLayout) v.findViewById(R.id.xinggainian_visibility);
        btn_specialt_math_aosai_rijiyueleilearn= (LinearLayout) v.findViewById(R.id.rijiyueleilearn);
        btn_specialt_math_aosai_rijiyueleiceshi = (LinearLayout) v.findViewById(R.id.rijiyueleiceshi);
        btn_specialt_math_aosai_ceshi = (LinearLayout) v.findViewById(R.id.specialtopic_math_aosai_ceshi);
        btn_specialt_math_aosai_ceshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itent_aosaiceshi = new Intent(getActivity(),xel_specialtopic_math_aosai_text.class);
                startActivity(itent_aosaiceshi);
            }
        });



        //新概念显示不同年级
        String Grade="";
        List<User> userList=Db_UserService.getInstance(getContext()).loadAllNote();
        if (!userList.isEmpty()) {
             Grade = userList.get(0).getGrade_nub();
            Log.d("Grade","Grade"+Grade);
            if (Grade.substring(8,10).equals("01")||Grade.substring(8,10).equals("02"))
            {
                xinggainian_visibility.setVisibility(View.GONE);
            }else {
                xinggainian_visibility.setVisibility(View.VISIBLE);
            }
        }else {
            Grade = Db_TeacherService.getInstance(getContext()).loadAllNote().get(0).getClassNum1();
            if (Grade.substring(0,2).equals("01")||Grade.substring(0,2).equals("02"))
            {
                xinggainian_visibility.setVisibility(View.GONE);
            }else {
                xinggainian_visibility.setVisibility(View.VISIBLE);
            }
        }


        aosai_study(v); //奥赛学习
        aosai_test(v); //奥赛测试
        shudu_study(v); //数独学习
        shudu_test(v); //数独测试
        rijiyuelei_study(v); //日积月累学习
        rijiyuelei_test(v); //日积月累测试
        xiaoxue_gushici(v); //小学古诗词  //error
        kewai_gushici(v); //课外古诗词
        maozedong_shici(v); //毛泽东诗词
        guoxue(v); //国学  //error
        English_yufa_study(v); //英语语法学习
        English_yufa_test(v); //英语语法测试
        xinggainian_study(v); //新概念学习
        xinggainian_test(v); //新概念测试
        initSwitchView(v); //英语语文数学的视图转换
        rijiyuelei(v);////日积月累分类跳转
        return  v;
    }
    /*
    * 日积月累的跳转接口*/
    private void rijiyuelei(View v) {
        final Spinner spi_zu = (Spinner) v.findViewById(R.id.rijis_zu);
        Log.d("@@","vi"+vi);
        btn_specialt_math_aosai_rijiyueleilearn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!bool) {
                        Toast.makeText(getActivity(), "请检查您的网络设置", Toast.LENGTH_SHORT).show();
                    } else {
                        final HashMap<String, String> map = new HashMap<>();
                        map.put("No", rijiyuelei_ceshi);
                        Stitching.post_Chapter(rijiyuelei_ceshi);
                        Log.d("@@@", "线程外");
                        new Thread(new Runnable() {
                            // String a;
                            @Override

                            public void run() {
                                Log.d("@@@", "线程内");
                                try {
                                    Log.d("@@@", "线程内try 外");
                                    String a = Post_learn_rijiyuelei.getStringCha(Constant.post_Learn_rijiyuelei, map);
                                    Log.d("@@@", "打印接受到的返回值try里边" + a);
                                    Intent intent_rijiyuelei = new Intent(getActivity(), xel_specialt_chinese_rijiyuelei.class);
                                    intent_rijiyuelei.putExtra("riji", a);
                                    intent_rijiyuelei.putExtra("hao",rijiyuelei_ceshi);
                                    intent_rijiyuelei.putExtra("name",rejiyuelei);
                                    startActivity(intent_rijiyuelei);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                }
            });
        btn_specialt_math_aosai_rijiyueleiceshi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!bool) {
                        Toast.makeText(getActivity(), "请检查您的网络设置", Toast.LENGTH_SHORT).show();
                    } else {
                        if (rijiyuelei_ceshi_zu!=null&&!"".equals(rijiyuelei_ceshi_zu)) {
                            Intent intent = new Intent();
                            intent.setClass(getActivity(), SecondActivity.class);
                            intent.putExtra("kousuan_go", 3);
                            intent.putExtra("nub_zu", rijiyuelei_ceshi + rijiyuelei_ceshi_zu);
                            Log.d("@@@@", "组号" + rijiyuelei_ceshi + rijiyuelei_ceshi_zu);
                            startActivity(intent);
                        }
                    }
                }
            });

    }


    private void initSwitchView(View v) {
        final View math_line=v.findViewById(R.id.math_line);
        final  View chinese_line=v.findViewById(R.id.chinese_line);
        final  View english_line=v.findViewById(R.id.english_line);
        final TextView math = (TextView) v.findViewById(R.id.math);
        final TextView yuwen = (TextView) v.findViewById(R.id.chinese);
        final TextView english = (TextView) v.findViewById(R.id.english);
        final LinearLayout lay_math = (LinearLayout) v.findViewById(R.id.zhuanti_math);
        final LinearLayout lay_yuwen = (LinearLayout) v.findViewById(R.id.zhuanti_chinese);
        final LinearLayout lay_english= (LinearLayout) v.findViewById(R.id.zhuanti_english);
        lay_math.setVisibility(View.VISIBLE);
        lay_english.setVisibility(View.GONE);
        lay_yuwen.setVisibility(View.GONE);

        math_line.setVisibility(View.VISIBLE);
        chinese_line.setVisibility(View.GONE);
        english_line.setVisibility(View.GONE);



        math.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                math_line.setVisibility(View.GONE);
                chinese_line.setVisibility(View.GONE);
                english_line.setVisibility(View.VISIBLE);

                lay_english.setVisibility(View.VISIBLE);
                lay_math.setVisibility(View.GONE);
                lay_yuwen.setVisibility(View.GONE);
            }
        });
    }
    private void xinggainian_test(View v) {
        final String[] sectionNo = {""};
        final String[] nub_zu = {""};
        final String[] chapterNo = new String[1];
        final ArrayList list_data_zu = new ArrayList();
        final List<String> ke_list = new ArrayList<>();
        final Spinner ke_spinner = (Spinner) v.findViewById(R.id.xingainian_test_ke);
        final Spinner zu_spinner = (Spinner) v.findViewById(R.id.xingainian_test_jie);
        //获取存在sharePrefrence当前的用户的标识
        String user_id = App.newInstance().GetSharePrefrence_kejiezu(getContext());
        Log.d("user_id", user_id);

        final List<TextOneStructure> Get_ke = FindDB_kejiezu.Get_ke(getContext(), user_id, "7");
        Log.d("user_id_t", Get_ke.toString());
        Collections.sort(Get_ke);
        for (TextOneStructure textOneStructure : Get_ke) {
            if (!textOneStructure.getChapterName().equals("\"\""))
                ke_list.add(" " +textOneStructure.getChapterSequenceName() + " " + textOneStructure.getChapterName());
            else ke_list.add(" " +textOneStructure.getChapterSequenceName());
        }
        ArrayAdapter ke_adapter = new ArrayAdapter(getContext(), R.layout.xel_spiner_custom_view, ke_list);
        ke_spinner.setAdapter(ke_adapter);
        ke_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<String> jie_list = new ArrayList<>();
                chapterNo[0] = Get_ke.get(position).getChapterNo();
                //节节节节节节节节节**************************************************************************8
                final List<String> zu_list = new ArrayList<>();

                final HashMap<String, String> map = new HashMap<>();
                map.put("No", chapterNo[0] +"00");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String response = Post_er.getStringCha(Constant.post_Kousuan, map);
                            Log.d("背单词测试","i"+response);
                            Map<String, Object> map = (Map<String, Object>) JSON.parse(response);

                            String success = (String) map.get("success");
                            if (success.equals("200")) {
                                String ct = map.get("data").toString();

                                List<CalculationTest> list = JSON.parseArray(ct, CalculationTest.class);
                                Db_CaculationTest.getInstance(getContext()).saveNoteLists(list);
                                Message message = new Message();
                                Bundle bundle = new Bundle();
                                bundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) list);
                                message.setData(bundle);
                                message.what = 1;
                                handler_er_xingainian.sendMessage(message);
                            } else {
                                Log.d("success", "查询不到结果");
                                Message message = Message.obtain();
                                message.what = 2;
                                handler_er_xingainian.sendMessage(message);
                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();

                handler_er_xingainian = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        if (msg.what == 1) {
                            zu_spinner.setVisibility(View.VISIBLE);
                            List<CalculationTest> calculationTests = msg.getData().getParcelableArrayList("list");
                            if (!calculationTests.isEmpty()) {
                                Log.d("口算题卡", "题目下载成功---正在加载组" + JSON.toJSONString(calculationTests));
                                //获得有多少组
                                int t = 0;
                                HashMap<String, String> hashMap = new HashMap();
                                for (CalculationTest test : calculationTests) {

                                    hashMap.put(test.getItemNo().substring(0, 11) + "", "");

//
                                }

                                Set<Map.Entry<String, String>> keyEntrySet = hashMap.entrySet();

                                list_data_zu.clear();
                                for (Map.Entry<String, String> entry : keyEntrySet) {
                                    String key = entry.getKey().substring(9, 11);
                                    list_data_zu.add(key);
                                }
                                Collections.sort(list_data_zu);
                                //组的数据源
                                for (int i = 0; i < list_data_zu.size(); i++) {
                                    zu_list.add(" " +"第" + list_data_zu.get(i) + "组");
                                }

                            }
                            zu_adapter_er = new ArrayAdapter(getContext(), R.layout.xel_spiner_custom_view, zu_list);
                            zu_adapter_er.notifyDataSetChanged();
                            zu_spinner.setAdapter(zu_adapter_er);

                        }
                        if (msg.what == 2) {
                            zu_spinner.setVisibility(View.GONE);
                        }
                    }
                };


                zu_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                        if (position < 9) {
                            nub_zu[0] = "0" + (position + 1);
                        } else nub_zu[0] = String.valueOf(position);

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
        LinearLayout button4 = (LinearLayout) v.findViewById(R.id.xingainian_test_btn_go);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (zu_spinner.getVisibility() == View.VISIBLE) {
                    final HashMap<String, String> map = new HashMap<>();

                    map.put("No", sectionNo[0]);
                    Post_Kousuan.Linkage(getContext(), Constant.post_Kousuan, map);
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), SecondActivity.class);
                    intent.putExtra("kousuan_go", 3);
                    intent.putExtra("nub_zu", chapterNo[0] +"00" + nub_zu[0]);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "当前无资源", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private void xinggainian_study(View v) {


        final List<String> ke_learn_list = new ArrayList<>();
        final String[] ke_id = new String[1];
        final String[] ke_name = new String[1];
        final Spinner ke_learn_spinner = (Spinner) v.findViewById(R.id.spinner_xingainian);
        //获取存在sharePrefrence当前的用户的标识
        String user_id = App.newInstance().GetSharePrefrence_kejiezu(getContext());
        Log.d("user_id", user_id);
        final List<TextOneStructure> Get_ke = FindDB_kejiezu.Get_ke(getContext(), user_id, "7");
        Log.d("user_id_t", Get_ke.toString());
        Collections.sort(Get_ke);
        for (TextOneStructure textOneStructure : Get_ke) {
            if (!textOneStructure.getChapterName().equals("\"\""))
                ke_learn_list.add(" " +textOneStructure.getChapterSequenceName() + textOneStructure.getChapterName());
            else
                ke_learn_list.add(" " +textOneStructure.getChapterSequenceName());
        }
        ArrayAdapter ke_learn_adapter = new ArrayAdapter(getContext(), R.layout.xel_spiner_custom_view, ke_learn_list);
        ke_learn_spinner.setAdapter(ke_learn_adapter);
        ke_learn_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ke_id[0] = Get_ke.get(position).getChapterNo();
                ke_name[0] = Get_ke.get(position).getChapterName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        english_learn_specialt = (LinearLayout) v.findViewById(R.id.img_eng_btn_spe);
        english_learn_specialt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bool == false) {
                    Toast.makeText(getActivity(), "请检查您的网络设置", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getContext(),xel_specialt_english_textbooklearn.class);
                    Log.d("@@"," 11"+ ke_name[0]+ke_id[0]);
                    intent.putExtra("ke_id", ke_id[0]);
                    intent.putExtra("ke_name", ke_name[0]);
                    startActivity(intent);
                }
            }
        });
    }
    private void English_yufa_test(View v) {
        final String[] chapterNo = new String[1];
        final String[] nub_zu = {""};
        final ArrayList list_data_zu = new ArrayList();
        final List<String> ke_list = new ArrayList<>();
        final Spinner ke_spinner = (Spinner) v.findViewById(R.id.yufacesi_spinner_ke);
        final Spinner zu_spinner = (Spinner) v.findViewById(R.id.yufacesi_spinner_zu);
        //获取存在sharePrefrence当前的用户的标识
        String user_id = App.newInstance().GetSharePrefrence_kejiezu(getContext());
        Log.d("user_id", user_id);
        //
        //        //章
        final List<TextOneStructure> Get_ke = FindDB_kejiezu.Get_ke(getContext(), user_id, "8");
        Log.d("user_id_t", Get_ke.toString());
        Collections.sort(Get_ke);
        for (TextOneStructure textOneStructure : Get_ke) {
            ke_list.add(" " +textOneStructure.getChapterSequenceName() + textOneStructure.getChapterName());
        }
        ArrayAdapter ke_adapter = new ArrayAdapter(getContext(), R.layout.xel_spiner_custom_view, ke_list);
        ke_spinner.setAdapter(ke_adapter);
        ke_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chapterNo[0] = Get_ke.get(position).getChapterNo();
                final List<String> zu_list = new ArrayList<>();
                Log.d("sectionNo", chapterNo[0] + "00");

                final HashMap<String, String> map = new HashMap<>();
                map.put("No", chapterNo[0] + "00");
                //                        Db_CaculationTest.getInstance(getContext()).deleteAllNote();
                Post_Kousuan.Linkage(getContext(), Constant.post_Kousuan, map);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String response=Post_er.getStringCha(Constant.post_Kousuan, map);
                            Map<String, Object> map = (Map<String, Object>) JSON.parse(response);
                            String success = (String) map.get("success");
                            if (success.equals("200")) {
                                String ct = map.get("data").toString();
                                List<CalculationTest> list = JSON.parseArray(ct, CalculationTest.class);
                                Db_CaculationTest.getInstance(getContext()).saveNoteLists(list);

                                Message message=new Message();
                                message.what=1;
                                Bundle bundle = new Bundle();
                                bundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) list);
                                message.setData(bundle);
                                handler_er_yufa.sendMessage(message);


                            } else {
                                Log.d("success", "查询不到结果");
                                Message message=Message.obtain();
                                message.what=2;
                                handler_er_yufa.sendMessage(message);
                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                handler_er_yufa=new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        if (msg.what==1) {
                            zu_spinner.setVisibility(View.VISIBLE);
                            List<CalculationTest> calculationTests = msg.getData().getParcelableArrayList("list");
                            if (!calculationTests.isEmpty()) {
                                Log.d("口算题卡", "题目下载成功---正在加载组" + JSON.toJSONString(calculationTests));
                                //获得有多少组
                                int t = 0;
                                HashMap<String, String> hashMap = new HashMap();
                                for (CalculationTest test : calculationTests) {

                                    hashMap.put(test.getItemNo().substring(0, 11) + "", "");

//
                                }

                                Set<Map.Entry<String, String>> keyEntrySet = hashMap.entrySet();

                                list_data_zu.clear();
                                for (Map.Entry<String, String> entry : keyEntrySet) {
                                    String key = entry.getKey().substring(9, 11);
                                    list_data_zu.add(key);
                                }
                                Collections.sort(list_data_zu);
                                //组的数据源
                                for (int i = 0; i < list_data_zu.size(); i++) {
                                    zu_list.add(" " +"第" + list_data_zu.get(i) + "组");
                                }

                            }
                            zu_adapter_er = new ArrayAdapter(getContext(), R.layout.xel_spiner_custom_view, zu_list);
                            zu_adapter_er.notifyDataSetChanged();
                            zu_spinner.setAdapter(zu_adapter_er);
                        }
                        else if (msg.what==2)
                        {
                            zu_spinner.setVisibility(View.GONE);
                        }


                    }
                };


                zu_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                        if (position < 9) {
                            nub_zu[0] = "0" + (position + 1);
                        } else nub_zu[0] = String.valueOf(position);

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


        english_yufatest = (LinearLayout) v.findViewById(R.id.english_yufatest);
        english_yufatest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final HashMap<String, String> map = new HashMap<>();
//                map.put("No", "C78110106");
                map.put("No", chapterNo[0] + "00" + nub_zu[0]);
                if (zu_spinner.getVisibility()==View.VISIBLE&&nub_zu[0]!=null&&!"".equals(nub_zu[0])) {
                    Post_Kousuan.Linkage(getContext(), Constant.post_Kousuan, map);
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), SecondActivity.class);
                    intent.putExtra("kousuan_go", 3);
                    intent.putExtra("nub_zu", chapterNo[0] + "00" + nub_zu[0]);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getContext(),"当前无资源",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void English_yufa_study(View v) {
        final String[] sectionNo = {""};
        final String[] nub_zu = {""};
        final List<String> ke_list = new ArrayList<>();
        final Spinner ke_spinner = (Spinner) v.findViewById(R.id.yufa_spinner_ke);
        //获取存在sharePrefrence当前的用户的标识
        String user_id = App.newInstance().GetSharePrefrence_kejiezu(getContext());
        Log.d("user_id", user_id);
        final List<TextOneStructure> Get_ke = FindDB_kejiezu.Get_ke(getContext(), user_id, "8");
        Log.d("user_id_t", Get_ke.toString());
        Collections.sort(Get_ke);
        for (TextOneStructure textOneStructure : Get_ke) {
            ke_list.add(" " +textOneStructure.getChapterSequenceName() + textOneStructure.getChapterName());
        }
        ArrayAdapter ke_adapter = new ArrayAdapter(getContext(), R.layout.xel_spiner_custom_view, ke_list);
        ke_spinner.setAdapter(ke_adapter);
        ke_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<String> jie_list = new ArrayList<>();
                String chapterNo = Get_ke.get(position).getChapterNo();
                sectionNo[0] = chapterNo;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final LinearLayout imageButton = (LinearLayout) v.findViewById(R.id.english_yufa_learn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final HashMap hashMap=new HashMap();
               hashMap.put("No",sectionNo[0]);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final Intent intent = new Intent(getActivity(), Xel_SpecialtOpic_englishyufa_learn.class);
                        String response=Post_learn_rijiyuelei.getStringCha(Constant.yufaxuexi,hashMap);
//                            Map<String, Object> map = (Map<String, Object>) JSON.parse(response);
//                           String pdfname = (String) map.get("EnglishYuFa");
                            Log.d("@@","dayinjson"+response);
                            JSONObject jsonObject = new JSONObject(response);
                            String pdfname = jsonObject.getString("success");
                            intent.putExtra("yufa", sectionNo[0]);
                            Log.d("@@","success"+pdfname);
                            intent.putExtra("pdfname",response);
                            startActivity(intent);
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
     //TODO error 数据库中有错
    private void guoxue(View v) {
        final String[] chapterNo = new String[1];

        final List<String> ke_list = new ArrayList<>();
        final Spinner ke_spinner = (Spinner) v.findViewById(R.id.guoxue_ke);
        //获取存在sharePrefrence当前的用户的标识
        String user_id = App.newInstance().GetSharePrefrence_kejiezu(getContext());
        Log.d("user_id", user_id);
        final List<TextOneStructure> Get_ke = FindDB_kejiezu.Get_ke(getContext(), user_id, "F");
        Log.d("KO", "IS SUCCESSFULLY************************");
        Collections.sort(Get_ke);
        for (TextOneStructure textOneStructure : Get_ke) {
            ke_list.add(" " +textOneStructure.getChapterSequenceName() + textOneStructure.getChapterName());
        }
        ArrayAdapter ke_adapter = new ArrayAdapter(getContext(), R.layout.xel_spiner_custom_view, ke_list);
        ke_spinner.setAdapter(ke_adapter);
        ke_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<String> jie_list = new ArrayList<>();
                chapterNo[0] = Get_ke.get(position).getChapterNo();
                 guoxue_name  = Get_ke.get(position).getChapterName();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        /**
         * 这是专题语文国学经典界面跳转的
         * 点击事件
         */
        Image_guoxue_jingdian = (LinearLayout) v.findViewById(R.id.guoxue_jingdian);
        Image_guoxue_jingdian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bool) {
                    Toast.makeText(getActivity(), "请检查您的网络设置", Toast.LENGTH_SHORT).show();
                } else {
                    final HashMap<String, String> map = new HashMap<>();
                    map.put("No", chapterNo[0]);
                    Stitching.post_Chapter(chapterNo[0]);
                    Stitching.post_name(guoxue_name);
                    new Thread(new Runnable() {
                        // String a;
                        @Override

                        public void run() {
                            Log.d("@@@", "线程内");
                            try {
                                Log.d("@@@", "线程内try 外");
                                String a = Post_learn_rijiyuelei.getStringCha(Constant.post_Learn_rijiyuelei, map);
                                Log.d("@@@", "打印接受到的返回值try里边" + a);
                                JSONObject json = new JSONObject(a);
                                String success = json.getString("success");
                                if(success.equals("100")){
                                    Looper.prepare();
                                    Toast.makeText(getContext(), "当前没有资源", Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }else {
                                    Intent intent_guoxue = new Intent(getActivity(), xel_specialt_guoxue_jindian.class);
                                    intent_guoxue.putExtra("jieshou", a);
                                    intent_guoxue.putExtra("name", guoxue_name);
                                    intent_guoxue.putExtra("num", chapterNo[0]);
                                    startActivity(intent_guoxue);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });



    }

    private void maozedong_shici(View v) {
        final String[] chapterNo = new String[1];
        final String[] sectionNo = {""};
        final String[] nub_zu = {""};
        final List<String> ke_list = new ArrayList<>();
        final Spinner ke_spinner = (Spinner) v.findViewById(R.id.maozedong_shici_ke);
        //获取存在sharePrefrence当前的用户的标识
        String user_id = App.newInstance().GetSharePrefrence_kejiezu(getContext());
        Log.d("user_id", user_id);
        final List<TextOneStructure> Get_ke = FindDB_kejiezu.Get_ke(getContext(), user_id, "G");
        Log.d("user_id_t", Get_ke.toString());
        Collections.sort(Get_ke);
        for (TextOneStructure textOneStructure : Get_ke) {
            ke_list.add(" " +textOneStructure.getChapterSequenceName() + textOneStructure.getChapterName());
        }
        ArrayAdapter ke_adapter = new ArrayAdapter(getContext(), R.layout.xel_spiner_custom_view, ke_list);
        ke_spinner.setAdapter(ke_adapter);
        ke_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<String> jie_list = new ArrayList<>();
              chapterNo[0] = Get_ke.get(position).getChapterNo();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        /**
         * 这是专题语文毛泽东诗词界面跳转的
         * 点击事件
         */
        Imagebtn_maozedong_shi = (LinearLayout) v.findViewById(R.id.maozedong_shici);
        Imagebtn_maozedong_shi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bool) {
                    Toast.makeText(getActivity(), "请检查您的网络设置", Toast.LENGTH_SHORT).show();
                } else {
                    final HashMap<String, String> map = new HashMap<>();
                    map.put("No", chapterNo[0]);
                    Stitching.post_Chapter(chapterNo[0]);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String shici = Post_learn_rijiyuelei.getStringCha(Constant.post_Learn_rijiyuelei, map);
                                Log.d("@@@@", "打印接受到的返回值try里边" + shici);
                                JSONObject json = new JSONObject(shici);
                                String success = json.getString("success");
                                if(success.equals("100")){
                                    Looper.prepare();
                                    Toast.makeText(getContext(), "当前没有资源", Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }else {
                                    Intent intent_stu_shi = new Intent(getActivity(), xel_specialt_maozedong_shici.class);
                                    intent_stu_shi.putExtra("shici", shici);
                                    startActivity(intent_stu_shi);
                                }
                            } catch (IOException e) {
                                Log.d("@@@@", "try catch  报错 检查");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });
    }

    private void kewai_gushici(View v) {
        final String[] sectionNo = {""};
        final String[] nub_zu = {""};
        final List<String> ke_list = new ArrayList<>();
        final Spinner ke_spinner = (Spinner) v.findViewById(R.id.kewai_gushici_ke);
        final Spinner jie_spinner = (Spinner) v.findViewById(R.id.kewai_gushici_jie);
        //获取存在sharePrefrence当前的用户的标识
        String user_id = App.newInstance().GetSharePrefrence_kejiezu(getContext());
        Log.d("user_id", user_id);
        final List<TextOneStructure> Get_ke = FindDB_kejiezu.Get_ke(getContext(), user_id, "E");
        Log.d("user_id_t", Get_ke.toString());
        Collections.sort(Get_ke);
        for (TextOneStructure textOneStructure : Get_ke) {
            ke_list.add(" " +textOneStructure.getChapterSequenceName() + textOneStructure.getChapterName());
        }
        ArrayAdapter ke_adapter = new ArrayAdapter(getContext(), R.layout.xel_spiner_custom_view, ke_list);
        ke_spinner.setAdapter(ke_adapter);
        ke_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<String> jie_list = new ArrayList<>();
                String chapterNo = Get_ke.get(position).getChapterNo();
                final List<TextTwoStructure> Get_jie = FindDB_kejiezu.Get_jie(getContext(), chapterNo);
                Collections.sort(Get_jie);
                Log.d("textTwoStructure_M", Get_jie.toString());
                for (TextTwoStructure textTwoStructure : Get_jie) {
                    jie_list.add(" " +textTwoStructure.getSectionSequenceName() + textTwoStructure.getSectionName());
                }
                ArrayAdapter jie_adapter = new ArrayAdapter(getContext(), R.layout.xel_spiner_custom_view, jie_list);
                jie_spinner.setAdapter(jie_adapter);
                jie_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        sectionNo[0] = Get_jie.get(position).getSectionNo();
                        Log.d("sectionNo", sectionNo[0]);
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
        /**
         * 这是专题语文课外拓展古诗词界面跳转的
         * 点击事件
         */
        Imagbtn_kewai_shici = (LinearLayout) v.findViewById(R.id.kewai_shici);
        Imagbtn_kewai_shici.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bool) {
                    Toast.makeText(getActivity(), "请检查您的网络设置", Toast.LENGTH_SHORT).show();
                } else {
                    final HashMap<String, String> map = new HashMap<>();
                    map.put("No", sectionNo[0]);
                    Stitching.post_Chapter(sectionNo[0]);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String shici = Post_learn_rijiyuelei.getStringCha(Constant.post_Learn_rijiyuelei, map);
                                Log.d("@@@", "打印接受到的返回值try里边" + shici);
                                JSONObject json = new JSONObject(shici);
                                String success = json.getString("success");
                                if(success.equals("100")){
                                    Looper.prepare();
                                    Toast.makeText(getContext(), "当前没有资源", Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }else {
                                    Intent intent_stu_shi = new Intent(getActivity(), xel_specialt_kewai_shici.class);
                                    intent_stu_shi.putExtra("shici", shici);
                                    intent_stu_shi.putExtra("num", sectionNo[0]);
                                    startActivity(intent_stu_shi);
                                }
                            } catch (IOException e) {
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });
    }
    private void xiaoxue_gushici(View v) {
        final String[] sectionNo = {""};
        final List<String> ke_list = new ArrayList<>();
        final Spinner ke_spinner = (Spinner) v.findViewById(R.id.xiaoxue_gushici_ke);
        final Spinner jie_spinner = (Spinner) v.findViewById(R.id.xiaoxue_gushici_jie);
        //获取存在sharePrefrence当前的用户的标识
        String user_id = App.newInstance().GetSharePrefrence_kejiezu(getContext());
        Log.d("user_id", user_id);
        final List<TextOneStructure> Get_ke = FindDB_kejiezu.Get_ke(getContext(), user_id, "D");
        Log.d("user_id_t", Get_ke.toString());
        Collections.sort(Get_ke);
        for (TextOneStructure textOneStructure : Get_ke) {
            ke_list.add(" " +textOneStructure.getChapterSequenceName() + textOneStructure.getChapterName());
        }
        ArrayAdapter ke_adapter = new ArrayAdapter(getContext(), R.layout.xel_spiner_custom_view, ke_list);
        ke_spinner.setAdapter(ke_adapter);
        ke_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<String> jie_list = new ArrayList<>();
                String chapterNo = Get_ke.get(position).getChapterNo();
                final List<TextTwoStructure> Get_jie = FindDB_kejiezu.Get_jie(getContext(), chapterNo);
                Collections.sort(Get_jie);
                Log.d("textTwoStructure_M", Get_jie.toString());
                for (TextTwoStructure textTwoStructure : Get_jie) {
                    jie_list.add(" " +textTwoStructure.getSectionSequenceName() + textTwoStructure.getSectionName());
                }
                ArrayAdapter jie_adapter = new ArrayAdapter(getContext(), R.layout.xel_spiner_custom_view, jie_list);
                jie_spinner.setAdapter(jie_adapter);
                jie_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        sectionNo[0] = Get_jie.get(position).getSectionNo();
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
/**
 * 这是专题语文小学生古诗词界面跳转的
 * 点击事件
 */
        Imagbtn_stu_shici = (LinearLayout) v.findViewById(R.id.specialt_stu_shici);
        Imagbtn_stu_shici.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bool) {
                    Toast.makeText(getActivity(), "请检查您的网络设置", Toast.LENGTH_SHORT).show();
                } else {
                    final HashMap<String, String> map = new HashMap<>();
                    map.put("No", sectionNo[0]);
                    Stitching.post_Chapter(sectionNo[0]);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String shici = Post_learn_rijiyuelei.getStringCha(Constant.post_Learn_rijiyuelei, map);
                                Log.d("@@@", "打印接受到的返回值try里边" + shici);
                                JSONObject json = new JSONObject(shici);
                                String success = json.getString("success");
                                if(success.equals("100")){
                                    Looper.prepare();
                                    Toast.makeText(getContext(), "当前没有资源", Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }else {
                                    Intent intent_stu_shi = new Intent(getActivity(), xel_specialt_stu_shici.class);
                                    intent_stu_shi.putExtra("shici", shici);
                                    startActivity(intent_stu_shi);
                                }
                            } catch (IOException e) {
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });
    }
    private void rijiyuelei_test(View v) {
        /**
         * 测试
         * 这是专题语文日积月测试界面跳转的
         * 点击事件
         *   /**
         * Spinner的数据源**/
        final String[] sectionNo = {""};
        final String[] nub_zu = {""};
        final List<String> ke_list = new ArrayList<>();
        final ArrayList list_data_zu = new ArrayList();
        final Spinner ke_spinner = (Spinner) v.findViewById(R.id.rijis_ke);
        final Spinner jie_spinner = (Spinner) v.findViewById(R.id.rijis_jie);
        final Spinner zu_spinner = (Spinner) v.findViewById(R.id.rijis_zu);
        //获取存在sharePrefrence当前的用户的标识
        String user_id = App.newInstance().GetSharePrefrence_kejiezu(getContext());
        Log.d("user_id", user_id);
        //
        //        //章
        final List<TextOneStructure> Get_ke = FindDB_kejiezu.Get_ke(getContext(), user_id, "C");
        Log.d("user_id_t", Get_ke.toString());
        Collections.sort(Get_ke);
        for (TextOneStructure textOneStructure : Get_ke) {
            ke_list.add(" " +textOneStructure.getChapterSequenceName() + textOneStructure.getChapterName());
        }
        ArrayAdapter ke_adapter = new ArrayAdapter(getContext(), R.layout.xel_spiner_custom_view, ke_list);
        ke_spinner.setAdapter(ke_adapter);
        ke_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<String> jie_list = new ArrayList<>();
                String chapterNo = Get_ke.get(position).getChapterNo();
                //节节节节节节节节节**************************************************************************8
                final List<TextTwoStructure> Get_jie = FindDB_kejiezu.Get_jie(getContext(), chapterNo);
                Collections.sort(Get_jie);
                Log.d("@@textTwoStructure_M", Get_jie.toString());
                for (TextTwoStructure textTwoStructure : Get_jie) {
                    jie_list.add(" " +textTwoStructure.getSectionSequenceName() + textTwoStructure.getSectionName());
                }
                ArrayAdapter jie_adapter = new ArrayAdapter(getContext(), R.layout.xel_spiner_custom_view, jie_list);
                jie_spinner.setAdapter(jie_adapter);
                jie_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        final List<String> zu_list = new ArrayList<>();
                        sectionNo[0] = Get_jie.get(position).getSectionNo();
                        String hou_2 = sectionNo[0].substring(sectionNo[0].length() - 1, sectionNo[0].length());
                        rijiyuelei_ceshi = sectionNo[0];
                        rejiyuelei = Get_jie.get(position).getSectionName();
                        if (hou_2.equals("6")) {
                            zu_spinner.setVisibility(View.VISIBLE);
                            btn_specialt_math_aosai_rijiyueleilearn.setVisibility(View.GONE);
                            btn_specialt_math_aosai_rijiyueleiceshi.setVisibility(View.VISIBLE);
                        } else {
                            btn_specialt_math_aosai_rijiyueleilearn.setVisibility(View.VISIBLE);
                            btn_specialt_math_aosai_rijiyueleiceshi.setVisibility(View.GONE);
                            zu_spinner.setVisibility(View.GONE);
                        }

                        final HashMap<String, String> map = new HashMap<>();
                        map.put("No", sectionNo[0]);
                        //                        Db_CaculationTest.getInstance(getContext()).deleteAllNote();

//                        Post_Kousuan.Linkage(getContext(), Constant.post_Kousuan, map);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    String response= Post_er.getStringCha(Constant.post_Kousuan, map);

                                    Map<String, Object> map = (Map<String, Object>) JSON.parse(response);

                                    String  success= (String) map.get("success");
                                    if (success.equals("200")) {
                                        String ct =  map.get("data").toString();
                                        Log.d("OO", ct.toString());
                                        List<CalculationTest> list = JSON.parseArray(ct, CalculationTest.class);
                                        Log.d("OO12", JSON.toJSONString(list));
                                        Db_CaculationTest.getInstance(getContext()).saveNoteLists(list);

                                        Log.d("口算题卡","题目下载成功---正在加载组");
                                        Message message=new Message();
                                        Bundle bundle = new Bundle();
                                        bundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) list);
                                        message.setData(bundle);
                                        message.what=1;

                                        handler_riji.sendMessage(message);

                                    }
                                    else {
                                        Log.d("success", "查询不到结果");
                                        Message message = Message.obtain();
                                        message.what = 2;
                                        handler_riji.sendMessage(message);
                                    }




                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();


                        handler_riji=new Handler(){
                            @Override
                            public void handleMessage(Message msg) {
                                super.handleMessage(msg);
                                if (msg.what==1) {
                                    List<CalculationTest> calculationTests = msg.getData().getParcelableArrayList("list");
                                    if (!calculationTests.isEmpty()) {
                                        Log.d("口算题卡", "题目下载成功---正在加载组" + JSON.toJSONString(calculationTests));
                                        //获得有多少组
                                        int t = 0;
                                        HashMap<String, String> hashMap = new HashMap();
                                        for (CalculationTest test : calculationTests) {

                                            hashMap.put(test.getItemNo().substring(0, 11) + "", "");

//
                                        }

                                        Set<Map.Entry<String, String>> keyEntrySet = hashMap.entrySet();

                                        list_data_zu.clear();
                                        for (Map.Entry<String, String> entry : keyEntrySet) {
                                            String key = entry.getKey().substring(9, 11);
                                            list_data_zu.add(key);
                                        }
                                        Collections.sort(list_data_zu);
                                        //组的数据源
                                        for (int i = 0; i < list_data_zu.size(); i++) {
                                            zu_list.add(" " +"第" + list_data_zu.get(i) + "组");
                                        }

                                    }
                                    zu_adapter_er = new ArrayAdapter(getContext(), R.layout.xel_spiner_custom_view, zu_list);
                                    zu_adapter_er.notifyDataSetChanged();
                                    zu_spinner.setAdapter(zu_adapter_er);
                                }
                            }
                        };


                        zu_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position < 9) {
                                    nub_zu[0] = "0" + (position + 1);
                                    rijiyuelei_ceshi_zu = nub_zu[0];
                                } else nub_zu[0] = String.valueOf(position);
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }


    private void rijiyuelei_study(View v) {
        final String[] sectionNo = {""};
        final String[] nub_zu = {""};
        final List<String> ke_list = new ArrayList<>();
        final Spinner ke_spinner = (Spinner) v.findViewById(R.id.riji_ke);
        final Spinner jie_spinner = (Spinner) v.findViewById(R.id.riji_jie);
        //获取存在sharePrefrence当前的用户的标识
        String user_id = App.newInstance().GetSharePrefrence_kejiezu(getContext());
        Log.d("user_id", user_id);
        final List<TextOneStructure> Get_ke = FindDB_kejiezu.Get_ke(getContext(), user_id, "C");
        Log.d("user_id_t", Get_ke.toString());
        Collections.sort(Get_ke);
        for (TextOneStructure textOneStructure : Get_ke) {
            ke_list.add(" " +textOneStructure.getChapterSequenceName() + textOneStructure.getChapterName());
        }
        ArrayAdapter ke_adapter = new ArrayAdapter(getContext(), R.layout.xel_spiner_custom_view, ke_list);
        ke_spinner.setAdapter(ke_adapter);
        ke_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<String> jie_list = new ArrayList<>();
                String chapterNo = Get_ke.get(position).getChapterNo();
                final List<TextTwoStructure> Get_jie = FindDB_kejiezu.Get_jie(getContext(), chapterNo);
                Collections.sort(Get_jie);
                Log.d("textTwoStructure_M", Get_jie.toString());
                for (TextTwoStructure textTwoStructure : Get_jie) {
                    jie_list.add(" " +textTwoStructure.getSectionSequenceName() + textTwoStructure.getSectionName());
                }
                ArrayAdapter jie_adapter = new ArrayAdapter(getContext(), R.layout.xel_spiner_custom_view, jie_list);
                jie_spinner.setAdapter(jie_adapter);
                jie_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        sectionNo[0] = Get_jie.get(position).getSectionNo();
                        Log.d("sectionNo", sectionNo[0]);
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




        /**
         * 这是专题语文日积月累界面跳转的
         * 点击事件
         */
//        btn_specialt_chinese_rijiyuelei = (LinearLayout) v.findViewById(R.id.rijiyuelei);
//        btn_specialt_chinese_rijiyuelei.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!bool) {
//                    Toast.makeText(getActivity(), "请检查您的网络设置", Toast.LENGTH_SHORT).show();
//                } else {
//                    final HashMap<String, String> map = new HashMap<>();
//                    map.put("No", sectionNo[0]);
//                    Log.d("@@@", "线程外");
//                    new Thread(new Runnable() {
//                        // String a;
//                        @Override
//
//                        public void run() {
//                            Log.d("@@@", "线程内");
//                            try {
//                                Log.d("@@@", "线程内try 外");
//                                String a = Post_learn_rijiyuelei.getStringCha(Constant.post_Learn_rijiyuelei, map);
//                                Log.d("@@@", "打印接受到的返回值try里边" + a);
//                                JSONObject json = new JSONObject(a);
//                                String success = json.getString("success");
//                                if(success.equals("100")){
//                                    Looper.prepare();
//                                    Toast.makeText(getContext(), "当前没有资源", Toast.LENGTH_SHORT).show();
//                                    Looper.loop();
//                                }else {
//                                    Intent intent_rijiyuelei = new Intent(getActivity(), xel_specialt_chinese_rijiyuelei.class);
//                                    intent_rijiyuelei.putExtra("riji", a);
//                                    startActivity(intent_rijiyuelei);
//                                }
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }).start();
//                }
//            }
//        });

    }

    private void shudu_test(View v) {
        /**
         * 这是数独的三级联动    跳到  数独  等操作  初始化
         * 点击事件
         *///初始化
        spe_test_1 = (Spinner)v.findViewById(R.id.speci_math_shudu_test_1);
        spe_test_2 =(Spinner) v.findViewById(R.id.speci_math_shudu_test_2);
        spe_test_3 = (Spinner)v.findViewById(R.id.speci_math_shudu_test_3);

//绑定适配器和值
        //四六宫
        adapter_shudu_1 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,string_spe_test_1);
        spe_test_1.setAdapter(adapter_shudu_1);
        spe_test_1.setSelection(0,true);  //设置默认选中项，此处为默认选中第4个值
        //初高级   选择
        adapter_shudu_2 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,string_spe_test_2[0]);
        spe_test_2.setAdapter(adapter_shudu_2);
        spe_test_2.setSelection(1,true);
        //题号   选择
        adapter_shudu_3 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,string_spe_test_3[0][0]);
        spe_test_3.setAdapter(adapter_shudu_3);
        spe_test_3.setSelection(1, true);

        //四六宫 下拉框监听
        spe_test_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // 表示选项被改变的时候触发此方法，主要实现办法：动态改变   初高 级适配器的绑定值
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                //position为当前省级选中的值的序号
                positions = string_spe_test_1[position];
                //{"四宫","六宫","九宫"};//

                //将初高 级适配器的值改变为adapter_shudu_2[position]中的值
                adapter_shudu_2 = new ArrayAdapter<String>(
                        getActivity(), android.R.layout.simple_spinner_item, string_spe_test_2[position]);
                // 设置二级下拉列表的选项内容适配器
                spe_test_2.setAdapter(adapter_shudu_2);
                provincePosition = position;    //记录当前省级序号，留给下面修改初高级 适配器时用
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                spe_test_2.getSelectedItem().toString();
            }

        });
        //初高级 下拉监听
        spe_test_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
                adapter_shudu_3 = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_spinner_item, string_spe_test_3[provincePosition][position]);
                spe_test_3.setAdapter(adapter_shudu_3); //可能有错position
            }


            public void onNothingSelected(AdapterView<?> arg0) {
                spe_test_3.getSelectedItem().toString();
            }
        });


        /**
         * 这是专题数学数独界面跳转的
         * 点击事件
         */
        LinearLayout xel_shudu = (LinearLayout) v.findViewById(R.id.xel_specialtopic_math_shuzu);
        xel_shudu.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {


                if ("四宫".equals(positions)) {
                    Intent xel_intent_shudu = new Intent(getActivity(),Game_4.class);
                    startActivity(xel_intent_shudu);
                } else if ("六宫".equals(positions)) {
                    Intent xel_intent_shudu = new Intent(getActivity(),Game_6.class);
                    startActivity(xel_intent_shudu);
                }
                else if ("九宫".equals(positions))
                {
                    Intent xel_intent_shudu = new Intent(getActivity(),Game_9.class);
                    startActivity(xel_intent_shudu);
                }

            }
        });

    }

    private void shudu_study(View v) {

        final String[] sectionNo = {""};
        final String[] nub_zu = {""};
        final List<String> ke_list = new ArrayList<>();
        final Spinner ke_spinner = (Spinner) v.findViewById(R.id.shudu_study_ke);
        final Spinner jie_spinner = (Spinner) v.findViewById(R.id.shudu_study_jie);
        //获取存在sharePrefrence当前的用户的标识
        String user_id = App.newInstance().GetSharePrefrence_kejiezu(getContext());
        Log.d("user_id", user_id);
        final List<TextOneStructure> Get_ke = FindDB_kejiezu.Get_ke(getContext(), user_id, "4");
        Log.d("user_id_t", Get_ke.toString());
        Collections.sort(Get_ke);
        for (TextOneStructure textOneStructure : Get_ke) {
            ke_list.add(" " +textOneStructure.getChapterSequenceName() + textOneStructure.getChapterName());
        }
        ArrayAdapter ke_adapter = new ArrayAdapter(getContext(), R.layout.xel_spiner_custom_view, ke_list);
        ke_spinner.setAdapter(ke_adapter);
        ke_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<String> jie_list = new ArrayList<>();
                String chapterNo = Get_ke.get(position).getChapterNo();
                final List<TextTwoStructure> Get_jie = FindDB_kejiezu.Get_jie(getContext(), chapterNo);

                Collections.sort(Get_jie);
                Log.d("textTwoStructure_M", Get_jie.toString());
                for (TextTwoStructure textTwoStructure : Get_jie) {
                    jie_list.add(" " +textTwoStructure.getSectionSequenceName() + textTwoStructure.getSectionName());
                }
                ArrayAdapter jie_adapter = new ArrayAdapter(getContext(), R.layout.xel_spiner_custom_view, jie_list);
                jie_spinner.setAdapter(jie_adapter);
                jie_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        sectionNo[0] = Get_jie.get(position).getSectionNo();
                       name = Get_jie.get(position).getSectionName();

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
        /*
        * 数独学习点击跳转*/


        Image_shudu_learn = (LinearLayout) v.findViewById(R.id.ima_shudu_learn);
        Image_shudu_learn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten_shudu_learn = new Intent(getActivity(),xel_specialt_math_shudu_learn.class);
                Log.d("@@@@","数独学习章节编号"+sectionNo[0]);
                inten_shudu_learn.putExtra("shduzhi",sectionNo[0]);
                inten_shudu_learn.putExtra("name",name);
                startActivity(inten_shudu_learn);

            }
        });
    }

    private void aosai_test(View v) {
        final String[] sectionNo = {""};


        final ArrayList list_data_zu = new ArrayList();
        final List<String> ke_list = new ArrayList<>();
        final Spinner ke_spinner = (Spinner) v.findViewById(R.id.math_aosaiceshi_ke);
        final Spinner jie_spinner = (Spinner) v.findViewById(R.id.math_aosaiceshi_jie);
        final Spinner zu_spinner = (Spinner) v.findViewById(R.id.math_aosaiceshi_zu);
        //获取存在sharePrefrence当前的用户的标识
        String user_id = App.newInstance().GetSharePrefrence_kejiezu(getContext());
        Log.d("user_id", user_id);
        //
        //        //章
        final List<TextOneStructure> Get_ke = FindDB_kejiezu.Get_ke(getContext(), user_id, "3");
        Log.d("user_id_t", Get_ke.toString());
        Collections.sort(Get_ke);
        for (TextOneStructure textOneStructure : Get_ke) {
            ke_list.add(" " +textOneStructure.getChapterSequenceName() + textOneStructure.getChapterName());
        }
        ArrayAdapter ke_adapter = new ArrayAdapter(getContext(), R.layout.xel_spiner_custom_view, ke_list);
        ke_spinner.setAdapter(ke_adapter);
        ke_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<String> jie_list = new ArrayList<>();
                String chapterNo = Get_ke.get(position).getChapterNo();
                //节节节节节节节节节**************************************************************************8
                final List<TextTwoStructure> Get_jie = FindDB_kejiezu.Get_jie(getContext(), chapterNo);
                Collections.sort(Get_jie);
                Log.d("textTwoStructure_M", Get_jie.toString());
                for (TextTwoStructure textTwoStructure : Get_jie) {
                    jie_list.add(" " +textTwoStructure.getSectionSequenceName() + textTwoStructure.getSectionName());
                }
                ArrayAdapter jie_adapter = new ArrayAdapter(getContext(), R.layout.xel_spiner_custom_view, jie_list);
                jie_spinner.setAdapter(jie_adapter);
                jie_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        final List<String> zu_list = new ArrayList<>();
                        sectionNo[0] = Get_jie.get(position).getSectionNo();
                        Log.d("sectionNo", sectionNo[0]);

                        final HashMap<String, String> map = new HashMap<>();
                        map.put("No", sectionNo[0]);
                        //                        Db_CaculationTest.getInstance(getContext()).deleteAllNote();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    String response = Post_er.getStringCha(Constant.post_Kousuan, map);

                                    Map<String, Object> maps = (Map<String, Object>) JSON.parse(response);

                                    String success = (String) maps.get("success");
                                    if (success.equals("200")) {
                                        String ct = maps.get("data").toString();
                                        Log.d("OO", ct.toString());
                                        List<aosai_zu_Entity> list =  new Gson().fromJson(ct, new TypeToken<List<aosai_zu_Entity>>(){}.getType());
                                        Log.d("OO@", list.toString());
                                        Message message=new Message();
                                        Bundle bundle=new Bundle();
                                        bundle.putParcelableArrayList("key", (ArrayList<? extends Parcelable>) list);
                                        message.setData(bundle);

                                        message.what=1;
                                        handler_aosai.sendMessage(message);

                                    }else {
                                        Message message = Message.obtain();
                                        message.what = 2;
                                        handler_aosai.sendMessage(message);
                                    }


                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                            }
                        }).start();
                        handler_aosai=new Handler(){
                            @Override
                            public void handleMessage(Message msg) {
                                super.handleMessage(msg);

                                if (msg.what==1) {
                                    ArrayList<aosai_zu_Entity> list= msg.getData().getParcelableArrayList("key");

                                    Log.d("奥赛测试的组数据","o"+JSON.toJSONString(list));
                                    if (!list.isEmpty()) {
                                        Log.d("口算题卡", "题目下载成功---正在加载组" + JSON.toJSONString(list));
                                        //获得有多少组
                                        zu_spinner.setVisibility(View.VISIBLE);
                                        int t = 0;
                                        HashMap<String, String> hashMap = new HashMap();
                                        for (aosai_zu_Entity test : list) {
                                            hashMap.put(test.getItemNo().substring(0, 11) + "", "");

//
                                        }

                                        Set<Map.Entry<String, String>> keyEntrySet = hashMap.entrySet();

                                        list_data_zu.clear();
                                        for (Map.Entry<String, String> entry : keyEntrySet) {
                                            String key = entry.getKey().substring(9, 11);
                                            list_data_zu.add(key);
                                        }
                                        Collections.sort(list_data_zu);
                                        //组的数据源
                                        for (int i = 0; i < list_data_zu.size(); i++) {
                                            zu_list.add(" " +"第" + list_data_zu.get(i) + "组");
                                        }
                                        zu_adapter_er = new ArrayAdapter(getContext(), R.layout.xel_spiner_custom_view, zu_list);
                                        zu_adapter_er.notifyDataSetChanged();
                                        zu_spinner.setAdapter(zu_adapter_er);

                                    } else {
                                        zu_spinner.setVisibility(View.GONE);
                                    }
                                }
                                if (msg.what == 2) {
                                    zu_spinner.setVisibility(View.GONE);
                                }
                                Log.d("口算题卡", "题目下载成功---正在加载组");

                            }
                        };




                        zu_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (!list_data_zu.isEmpty())
                                    nub_zu = (String) list_data_zu.get(position);

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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_specialt_math_aosai_ceshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final HashMap<String, String> map = new HashMap<>();
                map.put("No", sectionNo[0]);
                //                        Db_CaculationTest.getInstance(getContext()).deleteAllNote();
                if (zu_spinner.getVisibility()==View.VISIBLE&&nub_zu!=null&&!"".equals(nub_zu)) {
                    Intent itent_aosaiceshi = new Intent(getActivity(), aosai__SecondActivity.class);
                    itent_aosaiceshi.putExtra("nub_zu", sectionNo[0] + nub_zu);
                    startActivity(itent_aosaiceshi);
                }
                else {
                    Toast.makeText(getContext(),"当前无资源",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private void aosai_study(View v) {

        final String[] sectionNo = {""};
        final String[] nub_zu = {""};
        final List<String> ke_list = new ArrayList<>();
        final Spinner ke_spinner = (Spinner) v.findViewById(R.id.aosai_study_ke);
        final Spinner jie_spinner = (Spinner) v.findViewById(R.id.aosai_study_jie);
        //获取存在sharePrefrence当前的用户的标识
        String user_id = App.newInstance().GetSharePrefrence_kejiezu(getContext());
        Log.d("user_id", user_id);
        final List<TextOneStructure> Get_ke = FindDB_kejiezu.Get_ke(getContext(), user_id, "3");
        Log.d("user_id_t", Get_ke.toString());
        Collections.sort(Get_ke);
        for (TextOneStructure textOneStructure : Get_ke) {
            ke_list.add(" " +textOneStructure.getChapterSequenceName() + textOneStructure.getChapterName());
        }
        ArrayAdapter ke_adapter = new ArrayAdapter(getContext(), R.layout.xel_spiner_custom_view, ke_list);
        ke_spinner.setAdapter(ke_adapter);
        ke_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<String> jie_list = new ArrayList<>();
                String chapterNo = Get_ke.get(position).getChapterNo();
                final List<TextTwoStructure> Get_jie = FindDB_kejiezu.Get_jie(getContext(), chapterNo);
                Collections.sort(Get_jie);
                Log.d("textTwoStructure_M", Get_jie.toString());
                for (TextTwoStructure textTwoStructure : Get_jie) {
                    jie_list.add(" " +textTwoStructure.getSectionSequenceName() + textTwoStructure.getSectionName());
                }
                ArrayAdapter jie_adapter = new ArrayAdapter(getContext(), R.layout.xel_spiner_custom_view, jie_list);
                jie_spinner.setAdapter(jie_adapter);
                jie_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        sectionNo[0] = Get_jie.get(position).getSectionNo();
                        Log.d("sectionNo", sectionNo[0]);
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
//TODO  sectionNo[0]
        /**
         * 这是奥数学习和奥数测试的test
         * 点击事件
         */
        LinearLayout imageButton= (LinearLayout) v.findViewById(R.id.specialtopic_math_aosai_xuexi);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), Xel_SpecialtOpic_Math_Aosai_Learn.class);
                intent.putExtra("aosainub", sectionNo[0]);
                startActivity(intent);
            }
        });
    }
}
