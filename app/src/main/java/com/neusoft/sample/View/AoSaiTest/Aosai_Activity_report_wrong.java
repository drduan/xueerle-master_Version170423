package com.neusoft.sample.View.AoSaiTest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ncapdevi.sample.R;
import com.neusoft.sample.Ctrl.Db_XTCTJLService;
import com.neusoft.sample.GreenDao.XTCTJL;
import com.neusoft.sample.Model.Constant;
import com.neusoft.sample.Model.MsharedPrefrence;
import com.neusoft.sample.Model.aosai_zu_Entity;
import com.neusoft.sample.View.BaseActivity;
import com.neusoft.sample.util.QuestionnumberSwitchTitle;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by duanxudong on 16/7/9.
 */
public class Aosai_Activity_report_wrong extends BaseActivity {

    @BindView(R.id.back_home)
    Button back_home;
    @BindView(R.id.again_do)
    Button again_do;
    @BindView(R.id.cuotireport)
    TextView cuotireport;
    @BindView(R.id.titles1)
    TextView titles1;
    ListView list_report;
    List<XTCTJL> xtctjlList;
    List<aosai_zu_Entity> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xel_report_cuotibaogaodasn);
        ButterKnife.bind(this);
        list_report = (ListView) findViewById(R.id.list_report);
        list = getIntent().getParcelableArrayListExtra("list");
        Collections.sort(list);
        xtctjlList = Db_XTCTJLService.getInstance(this).loadAllNote();
        Log.d("xtctjlList", "i" + JSON.toJSONString(xtctjlList));
        Collections.sort(xtctjlList);
        if ("doagain".equals(MsharedPrefrence.GetisDoGain(Aosai_Activity_report_wrong.this))) {
            cuotireport.setText(QuestionnumberSwitchTitle.Getzhang(xtctjlList.get(0).getXtdctm_id()) + "错题重做报告单");
        } else {
            cuotireport.setText(QuestionnumberSwitchTitle.Getzhang(xtctjlList.get(0).getXtdctm_id()) + "错题报告单");
        }
        titles1.setText(QuestionnumberSwitchTitle.Gettitle(xtctjlList.get(0).getXtdctm_id()));
        RwArrayAdapter adapter = new RwArrayAdapter(this, xtctjlList, list);
        adapter.notifyDataSetChanged();
        list_report.setAdapter(adapter);
        back_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        again_do.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //加载试题
                MsharedPrefrence.SetisDoGain(Aosai_Activity_report_wrong.this);
                Log.d("我要重新开始做了", "successfully");
                Log.d("wrong_report", "1" + JSON.toJSONString(list));
                startActivity(new Intent(Aosai_Activity_report_wrong.this, Aosai_DoEaxm.class).putExtra("error_q", "1").putExtra("nub_zu", xtctjlList.get(0).getXtdctm_id()).putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) list));
                finish();
            }
        });
    }

    private class RwArrayAdapter extends BaseAdapter {
        Context context;
        List<XTCTJL> xtctjlList;
        List<aosai_zu_Entity> list;

        public RwArrayAdapter(Context activity_report_wrong, List<XTCTJL> xtctjlList, List<aosai_zu_Entity> list) {
            this.xtctjlList = xtctjlList;
            context = activity_report_wrong;
            this.list = list;
        }

        @Override
        public int getCount() {
            return xtctjlList.size();
        }

        @Override
        public Object getItem(int position) {
            return xtctjlList.get(position);
        }

        @Override
        public int getItemViewType(int position) {

            for (aosai_zu_Entity aosai_zu_entity : list) {
                if (xtctjlList.get(position).getXtdctm_id().equals(aosai_zu_entity.getItemNo())) {
                    if (aosai_zu_entity.getUsedStemPDF()) {
                        return 100;//pdf
                    } else {
                        return 200;//不是pdf
                    }
                }
            }


            return 1;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public synchronized View getView(final int position, View convertView, ViewGroup parent) {
            View view = null;
            int type = getItemViewType(position);
            final XTCTJL xtctjl = xtctjlList.get(position);
            if (type == 200) {
                view = LayoutInflater.from(context).inflate(R.layout.xel_listview_custom_view_for_aosai, null);

                //获取对象中的变量的变量名称
                //利用Java反射，通过用户选择的ABCD获取其实体的属性名称，进而获取其属性的内容
                String Mychoice = "";

                for (aosai_zu_Entity test : list) {
                    if (test.getItemNo().equals(xtctjl.getXtdctm_id())) {
                        String name = Constant.getName(test, xtctjl.getAnswer());
                        Field field_mychioce = null;
                        try {
                            field_mychioce = test.getClass().getDeclaredField(name);
                            field_mychioce.setAccessible(true);
                            Mychoice = (String) field_mychioce.get(test);
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
                TextView textView = (TextView) view.findViewById(R.id.q_nub);
                TextView textView1 = (TextView) view.findViewById(R.id.q_title);
                TextView textView2 = (TextView) view.findViewById(R.id.q_context);
                TextView textView3 = (TextView) view.findViewById(R.id.q_mcontext);
                Button not_pdf_btn_question = (Button) view.findViewById(R.id.not_pdf_btn_question);

                textView.setText(xtctjl.getXtdctm_id().substring(11));
                not_pdf_btn_question.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(Aosai_Activity_report_wrong.this, Question_pdf_loader.class);
                        intent.putExtra("question_nub", "" + xtctjl.getXtdctm_id());
                        intent.putExtra("aosai_zu_Entity",list.get(position));
                        startActivity(intent);
                    }
                });
                //textView2.setText(Mychoice);

                //利用Java反射，通过用户选择的ABCD获取其实体的属性名称，进而获取其属性的内容
                for (aosai_zu_Entity calculationTest : list) {
                    if (calculationTest.getItemNo().equals(xtctjl.getXtdctm_id())) {
                        String ll = calculationTest.getStem() + "【" + Mychoice + "】";
                        SpannableStringBuilder style = new SpannableStringBuilder(ll);
                        int start = ll.indexOf("【");
                        int end = ll.indexOf("】");
                        style.setSpan(new ForegroundColorSpan(Color.RED), start + 1, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                        textView1.setText(style);

                        String rightAnswer = "";
                        String name = Constant.getName(calculationTest, calculationTest.getRightAnswer());
                        try {
                            Field field_right = calculationTest.getClass().getDeclaredField(name);
                            field_right.setAccessible(true);
                            rightAnswer = (String) field_right.get(calculationTest);
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        textView3.setText(rightAnswer);
                        break;

                    }
                }
            }
            if (type == 100) {
                view = LayoutInflater.from(context).inflate(R.layout.xel_listview_custom_view_notpdf, null);
                TextView question_nub = (TextView) view.findViewById(R.id.q_nub);
                Button pdf_btn_question = (Button) view.findViewById(R.id.pdf_btn_question);
                TextView q_nub_answer = (TextView) view.findViewById(R.id.q_nub_answer);
                question_nub.setText(xtctjl.getXtdctm_id().substring(11));
                pdf_btn_question.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(Aosai_Activity_report_wrong.this, Question_pdf_loader.class);
                        intent.putExtra("question_nub", "" + xtctjl.getXtdctm_id());

                        startActivity(intent);
                    }
                });

                for (aosai_zu_Entity calculationTest : list) {
                    if (calculationTest.getItemNo().equals(xtctjl.getXtdctm_id())) {
                        String ll = "【" + xtctjl.getAnswer() + "】";
                        SpannableStringBuilder style = new SpannableStringBuilder(ll);
                        int start = ll.indexOf("【");
                        int end = ll.indexOf("】");
                        style.setSpan(new ForegroundColorSpan(Color.RED), start + 1, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                        q_nub_answer.setText(style);

                    }
                }


            }


            return view;
        }
    }

    public void fanhui(View view) {
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        xtctjlList.clear();

    }
}
