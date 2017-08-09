package com.neusoft.sample.View.xel_course;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ncapdevi.sample.R;
import com.neusoft.sample.Ctrl.Db_TextOneStructureService;
import com.neusoft.sample.Ctrl.Db_TextTwoStructureService;
import com.neusoft.sample.Ctrl.Db_XTCSJGService;
import com.neusoft.sample.GreenDao.TextOneStructure;
import com.neusoft.sample.GreenDao.TextTwoStructure;
import com.neusoft.sample.GreenDao.XTCSJG;
import com.neusoft.sample.Model.MsharedPrefrence;
import com.neusoft.sample.View.BaseActivity;
import com.neusoft.sample.View.BottomTabSwitcherActivity;
import com.neusoft.sample.util.QuestionnumberSwitchTitle;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by duanxudong on 16/7/9.
 */
public class ActivityGradeResult extends BaseActivity {
    TextView right_worng1,right_worng2;
    String time_fen,time_shi;
    @BindView(R.id.grade)
    TextView grade;
    @BindView(R.id.chapter)
    TextView chapter;
    @BindView(R.id.right_wrong)
    TextView right_wrong;
    @BindView(R.id.do_datetime)
    TextView do_datetime;
    @BindView(R.id.chengjibiao_time)
    TextView chengjibiao_time;
    @BindView(R.id.lists_report)
    TextView lists_report;
    @BindView(R.id.titlenofull)
    TextView titlenofull;
    @BindView(R.id.do_question)
    Button do_agin;
    @BindView(R.id.do_wrong)
    Button do_wrong;
    @BindView(R.id.no_full_zhang)
    TextView tv_full_zhang;
    @BindView(R.id.no_full_jie)
    TextView tv_full_jie;
    @BindView(R.id.no_full_zu)
    TextView tv_full_zu;
    String group_number;

    TextView item_resource;
    TextView tongji;
    TextView yongshi;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xel_report_nofull_mark);
        right_worng1 = (TextView) findViewById(R.id.right_wrongop);
        right_worng2 = (TextView) findViewById(R.id.right_wrongopp);
        item_resource= (TextView) findViewById(R.id.item_resource);
        tongji= (TextView) findViewById(R.id.tongji);
        yongshi= (TextView) findViewById(R.id.yongshi);
        item_resource.getPaint().setFakeBoldText(true);
        tongji.getPaint().setFakeBoldText(true);
        yongshi.getPaint().setFakeBoldText(true);

        ButterKnife.bind(this);
        String info = getIntent().getStringExtra("info");
        XTCSJG xtcsjg = new Gson().fromJson(info, XTCSJG.class);
        double g =xtcsjg.getScore().doubleValue();
        //完整测试结果存放到本地
        Db_XTCSJGService.getInstance(this).saveNote(xtcsjg);
        Log.i("@@@", g + "");
        DecimalFormat decimalFormat = new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p = decimalFormat.format(g);//format 返回的是字符串
        Log.d("@@@","fenshu"+p);
        String p_ = p.replaceAll(".00"," ");
        Log.d("@@","0分分数"+p_);
        int P=p.indexOf(".");
        if (P==0)
        {
            grade.setText("0");
        }
        else {
            grade.setText(""+p.substring(0,P));
        }
        if (grade.getText().toString().equals("100"))
        {
            do_wrong.setVisibility(View.INVISIBLE);
        }
        group_number= xtcsjg.getTest_group_number();

            titlenofull.setText(QuestionnumberSwitchTitle.Gettitle(xtcsjg.getTest_group_number()));


        lists_report.setText(QuestionnumberSwitchTitle.Getzhang(xtcsjg.getTest_group_number())+"成绩报告单");
        if ("doagain".equals(MsharedPrefrence.GetisDoGain(this))) {
            do_wrong.setVisibility(View.GONE);
            lists_report.setText(QuestionnumberSwitchTitle.Getzhang(xtcsjg.getTest_group_number())+"错题重做报告单");
        }


        /*
        * 获取章节组列表
        * */


        List<TextOneStructure> textOneStructureList= Db_TextOneStructureService.getInstance(this).loadAllNote();
        for (TextOneStructure textOneStructure : textOneStructureList) {
            if (group_number.substring(0, 7).equals(textOneStructure.getChapterNo()))
            {
                if(!"5".equals(group_number.substring(0,1))) {
                    tv_full_zhang.setText("" + "第" + group_number.substring(5, 7) + "章 " + "   " + textOneStructure.getChapterName());
                }
                else {

                    tv_full_zhang.setText("" + "第" + group_number.substring(5, 7) + "章 " + "   " + textOneStructure.getChapterSequenceName());

                }
                break;
            }
        }
        List<TextTwoStructure> textTwoStructureList= Db_TextTwoStructureService.getInstance(this).loadAllNote();
        for (TextTwoStructure textTwoStructure : textTwoStructureList) {
            if (group_number.substring(0, 9).equals(textTwoStructure.getSectionNo()))
            {
                if(!"5".equals(group_number.substring(0,1))) {
                    tv_full_jie.setText("" + "第" + group_number.substring(7, 9) + "节 " + "   " + textTwoStructure.getSectionName());
                    tv_full_jie.setSingleLine();
                    tv_full_jie.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
                }
                else {
                    tv_full_jie.setVisibility(View.GONE);
                }
                break;
            }
        }
        tv_full_zu.setText(""+"第"+group_number.substring(9,11)+"组");
       // chapter.setText(group_number.substring(5,7)+"章——"+group_number.substring(7,9)+"节——"+group_number.substring(9,11)+"组");
        right_wrong.setText(String.valueOf((xtcsjg.getNumber_error().intValue()+xtcsjg.getNumber_success().intValue())));
        right_worng1.setText(String.valueOf(xtcsjg.getNumber_error().intValue()));
        right_worng2.setText(String.valueOf(xtcsjg.getNumber_success().intValue()));
        do_datetime.setText(""+xtcsjg.getJg_datetime());
        if((xtcsjg.getDuration() / 1000) % 60<10){
             time_fen= "0"+((xtcsjg.getDuration() / 1000) % 60);
        }else {
            time_fen = String.valueOf(((xtcsjg.getDuration() / 1000) % 60));
        }
        if((xtcsjg.getDuration() / 60000) % 60<10){
            time_shi =  "0"+((xtcsjg.getDuration() / 60000) % 60);
        }
        chengjibiao_time.setText("" + time_shi + "：" + time_fen+ "：" + xtcsjg.getDuration() % 1000+"");
        Initview_taozhuan();
    }
    private void Initview_taozhuan() {
        do_agin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        do_wrong.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent_doagin = new Intent(ActivityGradeResult.this, Activity_report_wrong.class);
            startActivity(intent_doagin);
        }
    });
}
    public void break_1(View view){
        finish();
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {

        super.onStop();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
