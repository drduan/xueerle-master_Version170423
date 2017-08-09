package com.neusoft.sample.View.xel_error;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.ncapdevi.sample.R;
import com.neusoft.sample.Model.Constant;
import com.neusoft.sample.Model.Get_TeacherFindTeacherWrong;
import com.neusoft.sample.Model.TeacherWrong_Entity;
import com.neusoft.sample.View.Adapter.TeacherWrongAdapter;
import com.neusoft.sample.View.AlwaysMarqueeTextView;
import com.neusoft.sample.View.BaseActivity;
import com.neusoft.sample.util.QuestionnumberSwitchTitle;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class TeacherWrongactivity extends BaseActivity {
   private ListView error_teacher_list;
    private AlwaysMarqueeTextView titles_error_teacher;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_wrongactivity);
        final String jieNo=getIntent().getStringExtra("jieNo");
        final String class_number=getIntent().getStringExtra("class_number");


        new Thread(new Runnable() {
            @Override
            public void run() {

                HashMap hashMap=new HashMap();
                hashMap.put("jieNo",jieNo );
                hashMap.put("class_number",class_number);

                try {
                    String result= Get_TeacherFindTeacherWrong.getStringCha(Constant.teacherfindTeacherWrong,hashMap);
                    JSONObject jsonObject=new JSONObject(result);

                    int success=jsonObject.getInt("success");
                    Log.d("success",":"+success);
                    if (success==200) {
                        TeacherWrong_Entity wrong_entity = new Gson().fromJson(result, TeacherWrong_Entity.class);
                        System.out.println("wrong_entity" + JSON.toJSONString(wrong_entity));
                        List<TeacherWrong_Entity.DataBean> dataBeanList = wrong_entity.getData();
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("dataBeanList", (ArrayList<? extends Parcelable>) dataBeanList);
                        message.setData(bundle);
                        handler.sendMessage(message);
                    }
                    else {
                        Log.d("error",":"+jsonObject.getString("data"));
                        Looper.prepare();
                        Toast.makeText(TeacherWrongactivity.this,jsonObject.getString("data")+"",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }).start();

        final String sectionNo=jieNo;

        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg!=null)
                {
                    List<TeacherWrong_Entity.DataBean> dataBeanList=msg.getData().getParcelableArrayList("dataBeanList");
                    titles_error_teacher= (AlwaysMarqueeTextView) findViewById(R.id.titles_error_teacher);
                    titles_error_teacher.setText(QuestionnumberSwitchTitle.GetErrorteachertitle(sectionNo));
                    Comparator<TeacherWrong_Entity.DataBean>  comparator=new Comparator<TeacherWrong_Entity.DataBean>() {
                        @Override
                        public int compare(TeacherWrong_Entity.DataBean lhs, TeacherWrong_Entity.DataBean rhs) {
//                            int lhsnub= Integer.parseInt(lhs.getExamination_number().substring(8));
//                            int rhsnub= Integer.parseInt(rhs.getExamination_number().substring(8));
                            int lhsnub= Integer.parseInt(lhs.getCount());
                            int rhsnub= Integer.parseInt(rhs.getCount());
                            return rhsnub-lhsnub;
                        }
                    };
                    Collections.sort(dataBeanList,comparator);
                    TeacherWrongAdapter teacherWrongAdapter=new TeacherWrongAdapter(TeacherWrongactivity.this,dataBeanList);
                    error_teacher_list= (ListView) findViewById(R.id.error_teacher_list);
                    error_teacher_list.setAdapter(teacherWrongAdapter);


                    Thread.interrupted();
                }

            }
        };







    }
    public void fanhui(View view){
        finish();
    }
}
