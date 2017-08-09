package com.neusoft.sample.View.xel_course.Vman;

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
import com.neusoft.sample.Model.ErrorSubject_two;
import com.neusoft.sample.Model.MsharedPrefrence;
import com.neusoft.sample.View.AlwaysMarqueeTextView;
import com.neusoft.sample.View.BaseActivity;
import com.neusoft.sample.View.BottomTabSwitcherActivity;
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
public class Error_question_report extends BaseActivity {

    @BindView(R.id.back_home)
    Button back_home;
    @BindView(R.id.again_do)
    Button again_do;
    @BindView(R.id.titles1)
    AlwaysMarqueeTextView titles1;

    ListView list_report;
    List<ErrorSubject_two> errorSubject_twoList;


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
            titles1.setText(QuestionnumberSwitchTitle.GettitleDoagin(errorSubject_twoList.get(0).getItemNo()));
        }
        RwArrayAdapter adapter = new RwArrayAdapter(this, errorSubject_twoList);
        adapter.notifyDataSetChanged();
        list_report.setAdapter(adapter);
        back_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Error_question_report.this, BottomTabSwitcherActivity.class));

            }
        });
        again_do.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //加载试题
                MsharedPrefrence.SetisDoGain(Error_question_report.this);
                Log.d("我要重新开始做了", "successfully");
                startActivity(new Intent(Error_question_report.this, DoEaxmDoagain.class).putParcelableArrayListExtra("response", (ArrayList<? extends Parcelable>) errorSubject_twoList).putExtra("nub_zu", errorSubject_twoList.get(0).getItemNo()));
            }
        });


    }
    public  void doagain(View view){
        finish();

    }

    private class RwArrayAdapter extends BaseAdapter {
        Context context;
        List<ErrorSubject_two> xtctjlList;

        public RwArrayAdapter(Context activity_report_wrong, List<ErrorSubject_two> xtctjlList) {
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
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ErrorSubject_two xtctjl = xtctjlList.get(position);
            Log.d("这个是错题：", JSON.toJSONString(xtctjlList));
            View view = LayoutInflater.from(context).inflate(R.layout.xel_listview_custom_view, null);
            TextView textView = (TextView) view.findViewById(R.id.q_nub);
            TextView textView1 = (TextView) view.findViewById(R.id.q_title);
            TextView textView2 = (TextView) view.findViewById(R.id.q_context);
            TextView textView3 = (TextView) view.findViewById(R.id.q_mcontext);

            //获取对象中的变量的变量名称
            //利用Java反射，通过用户选择的ABCD获取其实体的属性名称，进而获取其属性的内容
            String Mychoice = "";
//            List<CalculationTest> testList = Db_CaculationTest.getInstance(Error_question_report.this).loadAllNote();
            for (ErrorSubject_two test : xtctjlList) {
                if (test.getItemNo().equals(xtctjl.getItemNo())) {
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


            String jie;
            String ke;
            if (Integer.valueOf(xtctjl.getItemNo().substring(11))<10)
            {
                ke="0"+xtctjl.getItemNo().substring(11);
            }
            else {
                ke =xtctjl.getItemNo().substring(11);
            }

            textView.setText(xtctjl.getItemNo().substring(9,11)+"-"+ke);

//            List<CalculationTest> caculationTests = Db_CaculationTest.getInstance(ContextHolder.getContext()).loadAllNote();
            //利用Java反射，通过用户选择的ABCD获取其实体的属性名称，进而获取其属性的内容
            for (ErrorSubject_two test : xtctjlList) {
                if (test.getItemNo().equals(xtctjl.getItemNo())) {
                    String ll =  test.getStem()+ "【"+Mychoice+"】";
                    SpannableStringBuilder style=new SpannableStringBuilder(ll);
                    int start = ll.indexOf("【");
                    int end   = ll.indexOf("】");
                    style.setSpan(new ForegroundColorSpan(Color.RED),start+1,end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
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


            return view;
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        errorSubject_twoList.clear();
        finish();

    }
}
