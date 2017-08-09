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
import com.neusoft.sample.Ctrl.Db_XTCSJGService;
import com.neusoft.sample.Ctrl.Db_XTCTJLService;
import com.neusoft.sample.Ctrl.Db_XTDCTMService;
import com.neusoft.sample.Model.Constant;
import com.neusoft.sample.Model.MsharedPrefrence;
import com.neusoft.sample.Model.aosai_zu_Entity;
import com.neusoft.sample.View.BaseActivity;
import com.neusoft.sample.util.ContextHolder;
import com.neusoft.sample.util.QuestionnumberSwitchTitle;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangyujie on 16/7/9.
 */
public class Aosai_Error_question_report extends BaseActivity {

    @BindView(R.id.back_home)
    Button back_home;
    @BindView(R.id.again_do)
    Button again_do;
    @BindView(R.id.titles1)
    TextView titles1;

    ListView list_report;
    List<Aosai_Question> errorSubject_twoList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xel_error_question_report);
        ButterKnife.bind(this);
        list_report = (ListView) findViewById(R.id.list_report);
        errorSubject_twoList = getIntent().getParcelableArrayListExtra("response");
        Collections.sort(errorSubject_twoList);
        //删除数据库中原有的数据
        Db_XTDCTMService.getInstance(ContextHolder.getContext()).deleteAllNote();
        Db_XTCTJLService.getInstance(ContextHolder.getContext()).deleteAllNote();
        Db_XTCSJGService.getInstance(ContextHolder.getContext()).deleteAllNote();
        Log.d("ErrorSubject_two", "s" + JSON.toJSONString(errorSubject_twoList));
        if (!errorSubject_twoList.isEmpty()) {
            titles1.setText(QuestionnumberSwitchTitle.GetErrortitle(errorSubject_twoList.get(0).getItemNo()));
        }
        RwArrayAdapter adapter = new RwArrayAdapter(this, errorSubject_twoList);
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
                MsharedPrefrence.SetisDoGain(Aosai_Error_question_report.this);
                Log.d("我要重新开始做了", "successfully");
                List<aosai_zu_Entity> list =  new ArrayList<aosai_zu_Entity>();
                for (Aosai_Question aosai_question : errorSubject_twoList) {
                    aosai_zu_Entity aosaiZuEntity=new aosai_zu_Entity();
                    aosaiZuEntity.setXiangXiJiexi(aosai_question.getXiangXiJiexi());
                    aosaiZuEntity.setUsedStemPDF(aosai_question.isUsedStemPDF());
                    aosaiZuEntity.setUsedAnaSumPDF(aosai_question.isUsedAnaSumPDF());
                    aosaiZuEntity.setSummary(aosai_question.getSummary());
                    aosaiZuEntity.setChoiceA(aosai_question.getChoiceA());
                    aosaiZuEntity.setChoiceB(aosai_question.getChoiceB());
                    aosaiZuEntity.setChoiceC(aosai_question.getChoiceC());
                    aosaiZuEntity.setChoiceD(aosai_question.getChoiceD());
                    aosaiZuEntity.setItemNo(aosai_question.getItemNo());
                    aosaiZuEntity.setRightAnswer(aosai_question.getRightAnswer());
                    aosaiZuEntity.setStem(aosai_question.getStem());
                    list.add(aosaiZuEntity);


                }

//                startActivity(new Intent(Aosai_Error_question_report.this, Aosai_DoEaxm.class).putExtra("error_q", "1").putExtra("nub_zu",xtctjlList.get(0).getXtdctm_id()).putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) list));
                startActivity(new Intent(Aosai_Error_question_report.this, Aosai_DoEaxmDoagain.class).putParcelableArrayListExtra("response", (ArrayList<? extends Parcelable>) list).putExtra("nub_zu", errorSubject_twoList.get(0).getItemNo()));
            }
        });


    }
    public  void doagain(View view){
        finish();

    }

    private class RwArrayAdapter extends BaseAdapter {
        Context context;
        List<Aosai_Question> xtctjlList;

        public RwArrayAdapter(Context activity_report_wrong, List<Aosai_Question> xtctjlList) {
            this.xtctjlList = xtctjlList;
            context = activity_report_wrong;

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
            for (Aosai_Question aosai_zu_entity : xtctjlList) {
                if (xtctjlList.get(position).getItemNo().equals(aosai_zu_entity.getItemNo()))
                {
                    if (aosai_zu_entity.isUsedAnaSumPDF())
                    {
                        return 100;//pdf
                    }
                    else
                    {
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
        public View getView(int position, View convertView, ViewGroup parent) {
            final Aosai_Question xtctjl = xtctjlList.get(position);
            View view = null;
            int type = getItemViewType(position);
            Log.d("这个是错题：", JSON.toJSONString(xtctjlList));
            Log.d("这个是错题的回答：", xtctjl.getMyChoice());
            if (type == 200) {
                view = LayoutInflater.from(context).inflate(R.layout.xel_listview_custom_view, null);
                TextView textView = (TextView) view.findViewById(R.id.q_nub);
                TextView textView1 = (TextView) view.findViewById(R.id.q_title);
                TextView textView2 = (TextView) view.findViewById(R.id.q_context);
                TextView textView3 = (TextView) view.findViewById(R.id.q_mcontext);

                //获取对象中的变量的变量名称
                //利用Java反射，通过用户选择的ABCD获取其实体的属性名称，进而获取其属性的内容
                String Mychoice = "";
//            List<CalculationTest> testList = Db_CaculationTest.getInstance(Error_question_report.this).loadAllNote();
                for (Aosai_Question test : xtctjlList) {
                    if (test.getItemNo().equals(xtctjl.getItemNo())) {
                        String name = Constant.getName(test, xtctjl.getMyChoice());
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


                String jie;
                String ke;
                if (Integer.valueOf(xtctjl.getItemNo().substring(11)) < 10) {
                    ke = "0" + xtctjl.getItemNo().substring(11);
                } else {
                    ke = xtctjl.getItemNo().substring(11);
                }
                textView.setText(xtctjl.getItemNo().substring(9, 11) + "-" + ke);
//            List<CalculationTest> caculationTests = Db_CaculationTest.getInstance(ContextHolder.getContext()).loadAllNote();
                //利用Java反射，通过用户选择的ABCD获取其实体的属性名称，进而获取其属性的内容
                for (Aosai_Question test : xtctjlList) {
                    if (test.getItemNo().equals(xtctjl.getItemNo())) {
                        String ll = test.getStem() + "【" + Mychoice + "】";
                        SpannableStringBuilder style = new SpannableStringBuilder(ll);
                        int start = ll.indexOf("【");
                        int end = ll.indexOf("】");
                        style.setSpan(new ForegroundColorSpan(Color.RED), start + 1, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                        textView1.setText(style);
                        String rightAnswer = "";
                        String name = Constant.getName(test, test.getRightAnswer());
                        try {
                            Field field_right = test.getClass().getDeclaredField(name);
                            field_right.setAccessible(true);
                            rightAnswer = (String) field_right.get(test);
                            textView3.setText(rightAnswer);
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }

                        break;

                    }
                }

            }
            if (type == 100) {

                view = LayoutInflater.from(context).inflate(R.layout.xel_listview_custom_view_notpdf, null);
                TextView question_nub = (TextView) view.findViewById(R.id.q_nub);
                Button pdf_btn_question = (Button) view.findViewById(R.id.pdf_btn_question);
                TextView q_nub_answer = (TextView) view.findViewById(R.id.q_nub_answer);
                String ke;
                if (Integer.valueOf(xtctjl.getItemNo().substring(11)) < 10) {
                    ke =  0+xtctjl.getItemNo().substring(11);
                } else {
                ke = xtctjl.getItemNo().substring(11);
            }
//                textView.setText(xtctjl.getItemNo().substring(9, 11) + "-" + ke);
                question_nub.setText(xtctjl.getItemNo().substring(9, 11) + "-" + ke);
                pdf_btn_question.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent=new Intent(Aosai_Error_question_report.this,Question_pdf_loader.class);
                        intent.putExtra("question_nub",""+xtctjl.getItemNo());

                        startActivity(intent);
                    }
                });

                for (Aosai_Question calculationTest : xtctjlList) {
                    if (calculationTest.getItemNo().equals(xtctjl.getItemNo())) {
                        String ll = "【" +  xtctjl.getMyChoice() + "】";
                        Log.d("ll","p"+ll);
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


}
