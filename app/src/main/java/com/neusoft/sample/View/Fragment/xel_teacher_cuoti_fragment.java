package com.neusoft.sample.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
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
import com.ncapdevi.sample.R;
import com.neusoft.sample.Ctrl.Db_TeacherService;
import com.neusoft.sample.Ctrl.wenchengcheng.ExitApplication;
import com.neusoft.sample.GreenDao.TextOneStructure;
import com.neusoft.sample.GreenDao.TextTwoStructure;
import com.neusoft.sample.GreenDao.teacher;
import com.neusoft.sample.Model.Consant_stringg;
import com.neusoft.sample.Model.FindDB_kejiezu;
import com.neusoft.sample.View.BaseFragment;
import com.neusoft.sample.View.xel_error.TeacherWrongactivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 老师端错题创建人 ：逗比康
 * 完成人：逗比人呵呵
 */
public class xel_teacher_cuoti_fragment extends BaseFragment {
    Boolean bool;//检测网络连接
    //数学
    private Spinner tea_math_ban, tea_math_zhang, tea_math_jie;
    private Spinner aosai_error_ban, aosai_error_zhang, aosai_error_jie;
    //英语
    private Spinner teacher_english_recite_ban, teacher_english_recite_zhang, teacher_english_recite_jie;
    private Spinner teacher_english_xingainian_ban, teacher_english_xingainian_zhang, teacher_english_xingainian_jie;
    private Spinner teacher_english_yufa_ban,teacher_english_yufa_zhang;
    //语文
    private Spinner rijihuelei_error_ke_select, rijihuelei_err_select, rijihuelei_error_jie_select;


    private List<String> teacher_error_class_entities;
    private List<String> teacher_error_classnub_entities;


    //每个spinner的班都会被赋值到 String Classnub中
    String Classnub;

    //开始查看错题按钮
   LinearLayout kousuan_start_btn,aosai_start_btn,recite_start_btn,xingainian_start_btn,rijiyuelei_start_btn,yufa_start_btn;

    public static xel_teacher_cuoti_fragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        xel_teacher_cuoti_fragment fragment = new xel_teacher_cuoti_fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ExitApplication.getInstance().addActivity(getActivity());
        /*检测网络连接*/
        bool = Consant_stringg.is_internet(getActivity());
        if (!bool) {
            Toast.makeText(getActivity(), "请检查您的网络设置", Toast.LENGTH_SHORT).show();
        }
        View v = inflater.inflate(R.layout.cuoti_teacher, null);
        initSwitchView(v); //英语语文数学的视图转换
        initSpinner(v);
        initLinerlayout(v);
        initClassEnvent();
        initNextSpinnerEnvent();


        Log.d("@@", "positioneteacheerrror");
        return v;
    }

    private void initLinerlayout(View v) {
        kousuan_start_btn= (LinearLayout) v.findViewById(R.id.kousuan_start_btn);
        aosai_start_btn= (LinearLayout) v.findViewById(R.id.aosai_start_btn);
        recite_start_btn= (LinearLayout) v.findViewById(R.id.recite_start_btn);
        xingainian_start_btn= (LinearLayout) v.findViewById(R.id.xingainian_start_btn);
        rijiyuelei_start_btn= (LinearLayout) v.findViewById(R.id.rijiyuelei_start_btn);
        yufa_start_btn= (LinearLayout) v.findViewById(R.id.yufa_start_btn);
    }

    private void initNextSpinnerEnvent() {

        List<teacher> teacherList=Db_TeacherService.getInstance(getContext()).loadAllNote();
        if ((teacherList.get(0).getSubjectName()).contains("数学"))
        {
            //数学
            Maths();
        }
        else if ((teacherList.get(0).getSubjectName()).contains("语文"))
        {
            //语文
            Chiese();
        }
        else if ((teacherList.get(0).getSubjectName()).contains("英语"))
        {
            //英语
            English();
        }




    }

    private void English() {
        //背单词
        Recitewords();
        //新概念

        Xingainian();
        //语法测试
        yufaceshi();

    }

    private void yufaceshi() {

        final List<String> ke_list = new ArrayList<>();
        final String[] sectionNo = new String[1];

        final List<TextOneStructure> Get_ke = FindDB_kejiezu.Get_ke(getContext(), "", "8");
        Log.d("user_id_t", JSON.toJSONString(Get_ke));
        Collections.sort(Get_ke);
        for (TextOneStructure textOneStructure : Get_ke) {
            ke_list.add("  " + textOneStructure.getChapterSequenceName() +"  " + textOneStructure.getChapterName());
        }
        ArrayAdapter<String> ke_adapter = new ArrayAdapter<String>(getContext(), R.layout.xel_spiner_custom_view, ke_list);
        teacher_english_yufa_zhang.setAdapter(ke_adapter);
        teacher_english_yufa_zhang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String chapterNo = Get_ke.get(position).getChapterNo();
                sectionNo[0]=chapterNo+"00";

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        yufa_start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(), TeacherWrongactivity.class);
                intent.putExtra("jieNo",sectionNo[0] );
                intent.putExtra("class_number",Classnub);
                startActivity(intent);
            }
        });
    }

    private void Xingainian() {

        final List<String> ke_list = new ArrayList<>();
        final String[] sectionNo = new String[1];

        final List<TextOneStructure> Get_ke = FindDB_kejiezu.Get_ke(getContext(), "", "5");
        Log.d("user_id_t", JSON.toJSONString(Get_ke));
        Collections.sort(Get_ke);
        for (TextOneStructure textOneStructure : Get_ke) {
            ke_list.add("  " + textOneStructure.getChapterSequenceName() +"  " + textOneStructure.getChapterName());
        }
        ArrayAdapter<String> ke_adapter = new ArrayAdapter<String>(getContext(), R.layout.xel_spiner_custom_view, ke_list);
        teacher_english_xingainian_zhang.setAdapter(ke_adapter);

        teacher_english_xingainian_zhang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String chapterNo = Get_ke.get(position).getChapterNo();
                sectionNo[0]=chapterNo+"00";

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        xingainian_start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(), TeacherWrongactivity.class);
                intent.putExtra("jieNo",sectionNo[0] );
                intent.putExtra("class_number",Classnub);
                startActivity(intent);
            }
        });


    }

    private void Recitewords() {

        final List<String> ke_list = new ArrayList<>();
        final String[] sectionNo = new String[1];

        final List<TextOneStructure> Get_ke = FindDB_kejiezu.Get_ke(getContext(), "", "5");
        Log.d("user_id_t", JSON.toJSONString(Get_ke));
        Collections.sort(Get_ke);
        for (TextOneStructure textOneStructure : Get_ke) {
            ke_list.add("  " + textOneStructure.getChapterSequenceName() +"  " + textOneStructure.getChapterName());
        }
        ArrayAdapter<String> ke_adapter = new ArrayAdapter<String>(getContext(), R.layout.xel_spiner_custom_view, ke_list);
        teacher_english_recite_zhang.setAdapter(ke_adapter);

        teacher_english_recite_zhang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String chapterNo = Get_ke.get(position).getChapterNo();
                sectionNo[0]=chapterNo+"00";

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        recite_start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(), TeacherWrongactivity.class);
                intent.putExtra("jieNo",sectionNo[0] );
                intent.putExtra("class_number",Classnub);
                startActivity(intent);
            }
        });

    }

    private void Chiese() {
        // 日积月累


        final List<String> ke_list = new ArrayList<>();
        final String[] sectionNo = new String[1];

        final List<TextOneStructure> Get_ke = FindDB_kejiezu.Get_ke(getContext(), "", "C");
        Log.d("user_id_t", JSON.toJSONString(Get_ke));
        Collections.sort(Get_ke);
        for (TextOneStructure textOneStructure : Get_ke) {
            ke_list.add("  " + textOneStructure.getChapterSequenceName() +"  " + textOneStructure.getChapterName());
        }
        ArrayAdapter<String> ke_adapter = new ArrayAdapter<String>(getContext(), R.layout.xel_spiner_custom_view, ke_list);
        rijihuelei_err_select.setAdapter(ke_adapter);

        rijihuelei_err_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String chapterNo = Get_ke.get(position).getChapterNo();
                sectionNo[0]=chapterNo+"00";

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        rijiyuelei_start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(), TeacherWrongactivity.class);
                intent.putExtra("jieNo",sectionNo[0] );
                intent.putExtra("class_number",Classnub);
                startActivity(intent);
            }
        });



    }

    private void Maths() {
        //口算题卡

        Kousuan();

        //奥赛学习
        Aosai();

    }

    private void Aosai() {

        final List<String> ke_list = new ArrayList<>();
        final String[] sectionNo = new String[1];

        final List<TextOneStructure> Get_ke = FindDB_kejiezu.Get_ke(getContext(), "", "3");
        Log.d("user_id_t", JSON.toJSONString(Get_ke));
        Collections.sort(Get_ke);
        for (TextOneStructure textOneStructure : Get_ke) {
            ke_list.add("  " + textOneStructure.getChapterSequenceName() +"  " + textOneStructure.getChapterName());
        }
        ArrayAdapter<String> ke_adapter = new ArrayAdapter<String>(getContext(), R.layout.xel_spiner_custom_view, ke_list);
        aosai_error_zhang.setAdapter(ke_adapter);

        aosai_error_zhang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<String> jie_list = new ArrayList<>();
                String chapterNo = Get_ke.get(position).getChapterNo();
                final List<TextTwoStructure> Get_jie = FindDB_kejiezu.Get_jie(getContext(), chapterNo);
                Collections.sort(Get_jie);
                Log.d("textTwoStructure_M", Get_jie.toString());
                for (TextTwoStructure textTwoStructure : Get_jie) {
                    jie_list.add( "  " +textTwoStructure.getSectionSequenceName() + "  " + textTwoStructure.getSectionName());
                }
                ArrayAdapter<String> jie_adapter = new ArrayAdapter<String>(getContext(), R.layout.xel_spiner_custom_view, jie_list);
                aosai_error_jie.setAdapter(jie_adapter);
                aosai_error_jie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(final AdapterView<?> parent, final View view, int position, long id) {
                        final List<String> zu_list = new ArrayList<>();
                        zu_list.clear();
                        sectionNo[0] = Get_jie.get(position).getSectionNo();
                        final HashMap<String, String> map = new HashMap<>();
                        map.put("No", sectionNo[0]);


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


        aosai_start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(), TeacherWrongactivity.class);
                intent.putExtra("jieNo",sectionNo[0] );
                intent.putExtra("class_number",Classnub);
                startActivity(intent);








            }
        });

//


    }

    private void Kousuan() {
        final List<String> ke_list = new ArrayList<>();
        final String[] sectionNo = new String[1];

        final List<TextOneStructure> Get_ke = FindDB_kejiezu.Get_ke(getContext(), "", "1");
        Log.d("user_id_t", JSON.toJSONString(Get_ke));
        Collections.sort(Get_ke);
        for (TextOneStructure textOneStructure : Get_ke) {
                ke_list.add("  " + textOneStructure.getChapterSequenceName() +"  " + textOneStructure.getChapterName());
        }
        ArrayAdapter<String> ke_adapter = new ArrayAdapter<String>(getContext(), R.layout.xel_spiner_custom_view, ke_list);
        tea_math_zhang.setAdapter(ke_adapter);

        tea_math_zhang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<String> jie_list = new ArrayList<>();
                String chapterNo = Get_ke.get(position).getChapterNo();
                final List<TextTwoStructure> Get_jie = FindDB_kejiezu.Get_jie(getContext(), chapterNo);
                Collections.sort(Get_jie);
                Log.d("textTwoStructure_M", Get_jie.toString());
                for (TextTwoStructure textTwoStructure : Get_jie) {
                    jie_list.add( "  " +textTwoStructure.getSectionSequenceName() + "  " + textTwoStructure.getSectionName());
                }
                ArrayAdapter<String> jie_adapter = new ArrayAdapter<String>(getContext(), R.layout.xel_spiner_custom_view, jie_list);
                tea_math_jie.setAdapter(jie_adapter);
                tea_math_jie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(final AdapterView<?> parent, final View view, int position, long id) {
                        final List<String> zu_list = new ArrayList<>();
                        zu_list.clear();
                        sectionNo[0] = Get_jie.get(position).getSectionNo();
                        final HashMap<String, String> map = new HashMap<>();
                        map.put("No", sectionNo[0]);


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


        kousuan_start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(), TeacherWrongactivity.class);
                intent.putExtra("jieNo",sectionNo[0] );
                intent.putExtra("class_number",Classnub);
                startActivity(intent);








            }
        });

//

    }

    private void initClassEnvent() {
        final List<teacher> teacherList = Db_TeacherService.getInstance(getContext()).loadAllNote();
        //班级list
        teacher_error_class_entities = new ArrayList<>();
        teacher_error_classnub_entities=new ArrayList<>();
        teacher_error_class_entities.add("  " +teacherList.get(0).getClassName1());
        teacher_error_class_entities.add("  " +teacherList.get(0).getClassName2());
        teacher_error_class_entities.add("  " +teacherList.get(0).getClassName3());
        teacher_error_class_entities.add("  " +teacherList.get(0).getClassName4());
        teacher_error_classnub_entities.add(teacherList.get(0).getClassNum1());
        teacher_error_classnub_entities.add(teacherList.get(0).getClassNum2());
        teacher_error_classnub_entities.add(teacherList.get(0).getClassNum3());
        teacher_error_classnub_entities.add(teacherList.get(0).getClassNum4());



        ArrayAdapter<String> ban_adapter = new ArrayAdapter<String>(getContext(), R.layout.xel_spiner_custom_view, teacher_error_class_entities);

//        Teacher_Error_ClassArrayAdapter ban_adapter = new Teacher_Error_ClassArrayAdapter(getContext(), teacher_error_class_entities);

        tea_math_ban.setAdapter(ban_adapter);
        tea_math_ban.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Classnub = teacherList.get(0).getSchoolNum()+teacher_error_classnub_entities.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        aosai_error_ban.setAdapter(ban_adapter);
        aosai_error_ban.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Classnub = teacherList.get(0).getSchoolNum()+teacher_error_classnub_entities.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        teacher_english_recite_ban.setAdapter(ban_adapter);
        teacher_english_recite_ban.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Classnub = teacherList.get(0).getSchoolNum()+teacher_error_classnub_entities.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        teacher_english_xingainian_ban.setAdapter(ban_adapter);
        teacher_english_xingainian_ban.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Classnub = teacherList.get(0).getSchoolNum()+teacher_error_classnub_entities.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        rijihuelei_error_ke_select.setAdapter(ban_adapter);
        rijihuelei_error_ke_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Classnub = teacherList.get(0).getSchoolNum()+teacher_error_classnub_entities.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        teacher_english_yufa_ban.setAdapter(ban_adapter);
        teacher_english_yufa_ban.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Classnub = teacherList.get(0).getSchoolNum()+teacher_error_classnub_entities.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void initSpinner(View v) {
        tea_math_ban = (Spinner) v.findViewById(R.id.tea_math_ban);
        tea_math_zhang = (Spinner) v.findViewById(R.id.tea_math_zhang);
        tea_math_jie = (Spinner) v.findViewById(R.id.tea_math_jie);
        aosai_error_ban = (Spinner) v.findViewById(R.id.aosai_error_ban);
        aosai_error_zhang = (Spinner) v.findViewById(R.id.aosai_error_zhang);
        aosai_error_jie = (Spinner) v.findViewById(R.id.aosai_error_jie);
        teacher_english_recite_ban = (Spinner) v.findViewById(R.id.teacher_english_recite_ban);
        teacher_english_recite_zhang = (Spinner) v.findViewById(R.id.teacher_english_recite_zhang);
//        teacher_english_recite_jie = (Spinner) v.findViewById(R.id.teacher_english_recite_jie);
        teacher_english_xingainian_ban = (Spinner) v.findViewById(R.id.teacher_english_xingainian_ban);
        teacher_english_xingainian_zhang = (Spinner) v.findViewById(R.id.teacher_english_xingainian_zhang);
//        teacher_english_xingainian_jie = (Spinner) v.findViewById(R.id.teacher_english_xingainian_jie);
        rijihuelei_error_ke_select = (Spinner) v.findViewById(R.id.rijihuelei_error_ke_select);
        rijihuelei_err_select = (Spinner) v.findViewById(R.id.rijihuelei_err_select);
//        rijihuelei_error_jie_select = (Spinner) v.findViewById(R.id.rijihuelei_error_jie_select);
        teacher_english_yufa_ban= (Spinner) v.findViewById(R.id.teacher_english_yufa_ban);
        teacher_english_yufa_zhang= (Spinner) v.findViewById(R.id.teacher_english_yufa_zhang);
    }

    private void initSwitchView(View v) {
        final View math_line = v.findViewById(R.id.math_line);
        final View chinese_line = v.findViewById(R.id.chinese_line);
        final View english_line = v.findViewById(R.id.english_line);
        final TextView math = (TextView) v.findViewById(R.id.cuoti_math_teacher);
        final TextView yuwen = (TextView) v.findViewById(R.id.cuoti_chinese_teacher);
        final TextView english = (TextView) v.findViewById(R.id.cuoti_english_teacher);
        final LinearLayout lay_math = (LinearLayout) v.findViewById(R.id.teacher_math);
        final LinearLayout lay_yuwen = (LinearLayout) v.findViewById(R.id.teacher_chinese);
        final LinearLayout lay_english = (LinearLayout) v.findViewById(R.id.teacher_english);
       final LinearLayout xingainian_visible= (LinearLayout) v.findViewById(R.id.xingainian_visible);



        List<teacher> teacherList=Db_TeacherService.getInstance(getContext()).loadAllNote();
        int classe= Integer.parseInt(teacherList.get(0).getClassNum1().substring(1,2));
        if (classe>=3)
        {xingainian_visible.setVisibility(View.VISIBLE);

        }
        else {
            xingainian_visible.setVisibility(View.GONE);
        }
        if ((teacherList.get(0).getSubjectName()).contains("数学"))
        {
            yuwen.setText("数学");
            lay_english.setVisibility(View.GONE);
            lay_math.setVisibility(View.VISIBLE);
            lay_yuwen.setVisibility(View.GONE);

        }
        else if ((teacherList.get(0).getSubjectName()).contains("语文"))
        {
            yuwen.setText("语文");

            lay_english.setVisibility(View.GONE);
            lay_math.setVisibility(View.GONE);
            lay_yuwen.setVisibility(View.VISIBLE);

        }
        else if ((teacherList.get(0).getSubjectName()).contains("英语"))
        {
            yuwen.setText("英语");
            lay_english.setVisibility(View.VISIBLE);
            lay_math.setVisibility(View.GONE);
            lay_yuwen.setVisibility(View.GONE);

        }
    }
}
