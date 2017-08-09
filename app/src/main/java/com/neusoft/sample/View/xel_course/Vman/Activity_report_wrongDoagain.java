package com.neusoft.sample.View.xel_course.Vman;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.neusoft.sample.Model.ErrorSubject_two;
import com.neusoft.sample.Model.MsharedPrefrence;
import com.neusoft.sample.View.AlwaysMarqueeTextView;
import com.neusoft.sample.View.BaseActivity;
import com.neusoft.sample.View.BottomTabSwitcherActivity;
import com.neusoft.sample.View.xel_course.DoEaxm;
import com.neusoft.sample.util.QuestionnumberSwitchTitle;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangyujie on 16/7/29.
 */
public class Activity_report_wrongDoagain extends BaseActivity {

    @BindView(R.id.back_home)
    Button back_home;
    @BindView(R.id.again_do)
    Button again_do;
    @BindView(R.id.cuotireport)
    TextView cuotireport;


    @BindView(R.id.titles1)
    AlwaysMarqueeTextView titles1;

    ListView list_report;
    List<XTCTJL> xtctjlList;
    List<ErrorSubject_two> errorSubject_twoListe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xel_report_cuotibaogaodasn);
        ButterKnife.bind(this);
        list_report= (ListView) findViewById(R.id.list_report);
        errorSubject_twoListe=  getIntent().getParcelableArrayListExtra("response");
        if ("doagain".equals(MsharedPrefrence.GetisDoGain(Activity_report_wrongDoagain.this))) {
            cuotireport.setText("错题重做报告单");
        }
        xtctjlList= Db_XTCTJLService.getInstance(this).loadAllNote();
        Collections.sort(xtctjlList);
        titles1.setText(QuestionnumberSwitchTitle.GettitleDoagin(xtctjlList.get(0).getXtdctm_id()));

        RwArrayAdapter adapter=new RwArrayAdapter(this,xtctjlList);
        adapter.notifyDataSetChanged();
        list_report.setAdapter(adapter);

        back_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_report_wrongDoagain.this,BottomTabSwitcherActivity.class));
                finish();


            }
        });
        again_do.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //加载试题
                MsharedPrefrence.SetisDoGain(Activity_report_wrongDoagain.this);

               Log.d("我要重新开始做了","successfully");
                startActivity(new Intent(Activity_report_wrongDoagain.this, DoEaxm.class).putExtra("error_q", "1").putExtra("nub_zu",xtctjlList.get(0).getXtdctm_id()));
            }
        });


    }

    private class RwArrayAdapter extends BaseAdapter{
        Context context;
        List<XTCTJL> xtctjlList;
        public RwArrayAdapter(Context activity_report_wrong,List<XTCTJL> xtctjlList) {
            this.xtctjlList=xtctjlList;
            context=activity_report_wrong;

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
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public synchronized View getView(int position, View convertView, ViewGroup parent) {
            XTCTJL xtctjl=xtctjlList.get(position);
            Log.d("这个是错题：", JSON.toJSONString(xtctjlList));
            Log.d("这个是错题的回答：", xtctjl.getAnswer());
            View view= LayoutInflater.from(context).inflate(R.layout.xel_listview_custom_view, null);
            //获取对象中的变量的变量名称
            //利用Java反射，通过用户选择的ABCD获取其实体的属性名称，进而获取其属性的内容
            String Mychoice="";
//            List<CalculationTest> testList=Db_CaculationTest.getInstance(Activity_report_wrongDoagain.this).loadAllNote();
            for (ErrorSubject_two test:errorSubject_twoListe) {
                if (test.getItemNo().equals(xtctjl.getXtdctm_id())) {
                    String name = Constant.getName(test, xtctjl.getAnswer());
                    Field field_mychioce= null;
                    try {
                        field_mychioce = test.getClass().getDeclaredField(name);
                        field_mychioce.setAccessible(true);
                        Mychoice= (String) field_mychioce.get(test);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                    break;
                }
            }

            TextView textView= (TextView) view.findViewById(R.id.q_nub);
            TextView textView1= (TextView) view.findViewById(R.id.q_title);
            TextView textView2= (TextView) view.findViewById(R.id.q_context);
            TextView textView3= (TextView) view.findViewById(R.id.q_mcontext);
            textView.setText(xtctjl.getXtdctm_id().substring(11) );
            textView2.setText(Mychoice);
//            List<CalculationTest> caculationTests= Db_CaculationTest.getInstance(ContextHolder.getContext()).loadAllNote();
            //利用Java反射，通过用户选择的ABCD获取其实体的属性名称，进而获取其属性的内容
            for (ErrorSubject_two calculationTest:errorSubject_twoListe)
            {
                if (calculationTest.getItemNo().equals(xtctjl.getXtdctm_id()))
                {

                    textView1.setText(calculationTest.getStem());
                    String rightAnswer="";
                    String name = Constant.getName(calculationTest, calculationTest.getRightAnswer());
                    try {
                        Field field_right=calculationTest.getClass().getDeclaredField(name);
                        field_right.setAccessible(true);
                        rightAnswer= (String) field_right.get(calculationTest);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    textView3.setText(rightAnswer);
                    break;

                }
            }


            return view;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        xtctjlList.clear();
        finish();
    }
}
