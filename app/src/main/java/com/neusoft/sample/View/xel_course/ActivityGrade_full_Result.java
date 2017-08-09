package com.neusoft.sample.View.xel_course;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.ncapdevi.sample.R;
import com.neusoft.sample.App;
import com.neusoft.sample.Ctrl.Db_CaculationTest;
import com.neusoft.sample.Ctrl.Db_TextOneStructureService;
import com.neusoft.sample.Ctrl.Db_TextTwoStructureService;
import com.neusoft.sample.Ctrl.Db_XTCSGJService;
import com.neusoft.sample.GreenDao.CalculationTest;
import com.neusoft.sample.GreenDao.TextOneStructure;
import com.neusoft.sample.GreenDao.TextTwoStructure;
import com.neusoft.sample.GreenDao.XTCSGJ;
import com.neusoft.sample.GreenDao.XTCSJG;
import com.neusoft.sample.View.AlwaysMarqueeTextView;
import com.neusoft.sample.View.BaseActivity;
import com.neusoft.sample.View.SecondActivity;
import com.neusoft.sample.util.ContextHolder;
import com.neusoft.sample.util.QuestionnumberSwitchTitle;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Created by duanxudong on 16/7/9.
 */
public class ActivityGrade_full_Result extends BaseActivity {
    String time_fen,time_shi;
    String zhang,jie,zu;
    String nub_zu;
    @BindView(R.id.left__)
    ImageButton left__;
    @BindView(R.id.chatime_full)
    TextView chatime_full;
    @BindView(R.id.right_full)
    TextView right_full;
    @BindView(R.id.success_full)
    TextView success_full;
    @BindView(R.id.time_full)
    TextView time_full;
    @BindView(R.id.titlefull)
    AlwaysMarqueeTextView titlefull;
    @BindView(R.id.tv_cha)
    TextView tv_cha;
    @BindView(R.id.tv_cha_miao)
    TextView tv_cha_miao;
    @BindView(R.id.againchallenge)
    Button againchallenge;
    @BindView(R.id.nextquestion)
    Button nextquestion;
    String nextzu = "";
    @BindView(R.id.full_zhang)
    TextView tv_full_zhang;
    @BindView(R.id.full_jie)
    TextView tv_full_jie;
    @BindView(R.id.full_zu)
    TextView tv_full_zu;
    @BindView(R.id.repot_full)
    TextView tv_full_report;
    TextView item_resource;
    TextView tongji;
    TextView yongshi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xel_report_full_mark);
        ButterKnife.bind(this);
        item_resource= (TextView) findViewById(R.id.item_resourcefull);
        tongji= (TextView) findViewById(R.id.tongjifull);
        yongshi= (TextView) findViewById(R.id.yongshifull);
        item_resource.getPaint().setFakeBoldText(true);
        tongji.getPaint().setFakeBoldText(true);
        yongshi.getPaint().setFakeBoldText(true);
        if (tv_cha.getVisibility()==View.VISIBLE)
        tv_cha.getPaint().setFakeBoldText(true);


        left__.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String info = getIntent().getStringExtra("info");
        XTCSJG xtcsjg = new Gson().fromJson(info, XTCSJG.class);
        final String group_number = xtcsjg.getTest_group_number();
        if(QuestionnumberSwitchTitle.Gettitle(xtcsjg.getTest_group_number()).contains("背单词")){
            titlefull.setText(QuestionnumberSwitchTitle.Gettitle(xtcsjg.getTest_group_number())
                    +"");
        }else {
            titlefull.setText(QuestionnumberSwitchTitle.Gettitle(xtcsjg.getTest_group_number())
                 );
        }
        if (App.GetSharePrefrence_role(ContextHolder.getContext()).equals("1")) {
            againchallenge.setVisibility(View.VISIBLE);
        }
        /*
        * 下一组*/
        String  nub_zu_ = xtcsjg.getTest_group_number();
        String nu =nub_zu_.substring(nub_zu_.length()-1,nub_zu_.length());
        int nu_2 = Integer.parseInt(nu)+1;
        String nu_22 = String.valueOf(nu_2);
        String nu_0 = nub_zu_.substring(0,nub_zu_.length()-1);
        nub_zu = nu_0+nu_22;
           /*
        * 下一组*
        /*
        * 获取章节列表
        * */
        tv_full_report.setText(QuestionnumberSwitchTitle.Getzhang(xtcsjg.getTest_group_number())+"成绩报告单");
        List<TextOneStructure> textOneStructureList= Db_TextOneStructureService.getInstance(this).loadAllNote();
        for (TextOneStructure textOneStructure : textOneStructureList) {
            if (group_number.substring(0, 7).equals(textOneStructure.getChapterNo()))
            {
                tv_full_zhang.setText(""+"第"+ group_number.substring(5, 7)+"章 "+"   "+textOneStructure.getChapterName());
                tv_full_jie.setSingleLine();
                tv_full_jie.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
            }
        }
        List<TextTwoStructure> textTwoStructureList= Db_TextTwoStructureService.getInstance(this).loadAllNote();
        for (TextTwoStructure textTwoStructure : textTwoStructureList) {
            if (group_number.substring(0, 9).equals(textTwoStructure.getSectionNo()))
            {
                tv_full_jie.setText(""+"第"+ group_number.substring(7, 9)+"节 "+"   "+textTwoStructure.getSectionName());
            }
        }
       // tv_full_jie.setText(""+"第"+ group_number.substring(7, 9)+"节 ");
        tv_full_zu.setText(""+"第"+group_number.substring(9, 11)+"组 ");
        right_full.setText("" + (xtcsjg.getNumber_error().intValue() + xtcsjg.getNumber_success().intValue()) +
                "");
        success_full.setText("" + "" + xtcsjg.getNumber_success().intValue());
        if((xtcsjg.getDuration() / 1000) % 60<10){
            time_fen= "0"+(xtcsjg.getDuration() / 1000) % 60;
        }
        else {
            time_fen=""+(xtcsjg.getDuration() / 1000) % 60;
        }
        if((xtcsjg.getDuration() / 60000) % 60<10){
            time_shi =  "0"+((xtcsjg.getDuration() / 60000) % 60);
        }
        time_full.setText("" + time_shi + "：" + time_fen+ "：" + xtcsjg.getDuration() % 1000+"");
        List<XTCSGJ> xtcsgjList = Db_XTCSGJService.getInstance(this).loadAllNote();
        Log.d("返回的冠军的记录",JSON.toJSONString(xtcsgjList));

        Log.d("返回的我的记录",JSON.toJSONString(xtcsjg));

        String GJ = "";
        int time = 0;
        String IsDoSubject = "";

           if (!xtcsgjList.isEmpty()) {
               GJ = xtcsgjList.get(0).getIsGJ();

               Log.d("是否是冠军--", GJ);
               time = xtcsgjList.get(0).getTime();
               IsDoSubject = xtcsgjList.get(0).getIsDoSubject();
           }

        System.out.print(GJ+"满分----冠军");
        System.out.print(time+"满分----时间");
        System.out.print("返回数据满分的"+ JSON.toJSONString(xtcsgjList));
        if ((GJ.equals("yes"))) {
            tv_cha.setVisibility(View.GONE);
            tv_cha_miao.setVisibility(View.GONE);
            chatime_full.setText("Congratulation，您成为本组本城市的第一名！！！");
        }
        if((GJ.equals("no"))){

                Log.d("@@","时间"+(xtcsjg.getDuration() - time));
                chatime_full.setText("" + String.valueOf((xtcsjg.getDuration() - time) / 1000) + "");

        }
        List<CalculationTest> calculationTests = Db_CaculationTest.getInstance(this).loadAllNote();
        //获得有多少组
        for (CalculationTest test : calculationTests) {
            Log.d("满分成绩", group_number + "pp" + test.getItemNo() + "s---" + JSON.toJSONString(test));
            if (!test.getItemNo().equals("")) {
                if (group_number.substring(0, 9).equals(test.getItemNo().substring(0, 9))){
                    if (Integer.valueOf(group_number.substring(9, 11)).intValue() < Integer.valueOf(test.getItemNo().substring(9, 11)).intValue()) {
                        nextzu = test.getItemNo();
                        System.out.println("挑战开始挑战组为:" + nextzu);
                    }
                }
            }
        }
        if (!"".equals(nextzu)) {
            nextquestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ActivityGrade_full_Result.this, SecondActivity.class);
                    intent.putExtra("kousuan_go", 3);
                    intent.putExtra("nub_zu", nub_zu);
                    startActivity(intent);
                    finish();
                }
            });
        } else {
            Toast.makeText(this, "恭喜您！本节题目已经全部做完！", Toast.LENGTH_SHORT).show();
            nextquestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ActivityGrade_full_Result.this, "恭喜您！本节题目已经全部做完！", Toast.LENGTH_SHORT).show();
                }
            });
        }
        if ("no".equals(IsDoSubject)) {
            againchallenge.setVisibility(View.GONE);
        } else {

            againchallenge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ActivityGrade_full_Result.this, DoEaxm.class).putExtra("nub_zu",group_number));
                    finish();
                }
            });
        }
    }
    public void break_1(View view) {
        finish();
    }
}
