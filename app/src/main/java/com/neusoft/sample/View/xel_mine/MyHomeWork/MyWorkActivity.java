package com.neusoft.sample.View.xel_mine.MyHomeWork;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.ncapdevi.sample.R;
import com.neusoft.sample.Ctrl.Db_TeacherService;
import com.neusoft.sample.GreenDao.teacher;
import com.neusoft.sample.Model.Constant;
import com.neusoft.sample.Model.Post_learn_rijiyuelei;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
/*
* 老师发布作业*/
public class MyWorkActivity extends AppCompatActivity {
    private ImageView back_in_mywork;
    private ImageView addworkitem;
    private TextView datatime;
    private LinearLayout classaddcontainer;
    private ListView workitemlist;              //发布作业列表
    private Button rewrite;
    private Button seend;                        //发布按钮
    private String data_submit;                         //需要提交的日期
    private int year;                            //年月日
    private int month;                          //年月日
    private int day;                            //年月日
    private EditText pushcontext;              //输入框作业内容
    private List<PushIWorkListEntity> strings; //发布作业的数组
    private PushMyWorkAdapter adapter;         // 输入内容后添加添加调用adapt  添加到listview
    private ImageView lookup;                  //查看作业详情按钮
    String subject = null;                     //科目
    String class_num = null;                   //班级编号
    String teacher_name = null;               //老师姓名
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_work);
        initView();
        data();//日期操作
        work();//对作业的操作
        initcommonEvent();//返回按钮
        addClassname();  //动态添加 班级列表
    }

    /*作业操作*/
    private void work() {
        workitemlist.setAdapter(adapter);
        lookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyWorkActivity.this,ParentLookUpWork.class);
                startActivity(intent);
            }
        });
        addworkitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( !"".equals(pushcontext.getText().toString())) {
                    PushIWorkListEntity pushIWorkListEntity = new PushIWorkListEntity();
                    pushIWorkListEntity.setWorkname(pushcontext.getText().toString());
                    pushIWorkListEntity.setItemno(strings.size() + 1);
                    strings.add(pushIWorkListEntity);
                    adapter.notifyDataSetChanged();
                    pushcontext.setText("");
                    pushcontext.setHint("");
                }
            }
        });
        seend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final HashMap<String, String> map = new HashMap();
                //上传信息
                String list = "";
                for (int i = 0; i < classaddcontainer.getChildCount(); i++) {
                    CheckBox item = (CheckBox) classaddcontainer.getChildAt(i);
                    if (item.isChecked()) {
                        Log.d("@@", "changdu" + i + "h" + classaddcontainer.getChildCount());
                        String text = item.getText().toString();
                        String cla = get_class_num(text);
                        if (i == (classaddcontainer.getChildCount() - 1)) {
                            list = list + cla;
                        } else {
                            list = list + cla + "-";
                        }
                    }
                }
                if (data_submit.equals("")) {
                    data_submit = get_today_time1();
                }
                String homework = JSON.toJSONString(strings);
                Log.d("@@", "内容" + list + homework + subject + data_submit + homework + teacher_name);
                map.put("classno", list);
                map.put("subject", subject);
                map.put("date1", data_submit);
                map.put("homework", homework);
                map.put("jobperson", teacher_name);
                map.put("date2", get_today_time());
                Log.d("获取复选框的中内容：", JSON.toJSONString(list));
                if (list.equals("")) {
                    Toast.makeText(MyWorkActivity.this, "请选择班级", Toast.LENGTH_SHORT).show();
                } else {
                    if (strings.size() < 1) {
                        Toast.makeText(MyWorkActivity.this, "请将作业内容添加到作业列表中", Toast.LENGTH_SHORT).show();
                    } else {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    String success = Post_learn_rijiyuelei.getStringCha(Constant.add_work, map);
                                    JSONObject jsonObjec = null;
                                    jsonObjec = new JSONObject(success);
                                    String power = null;
                                    power = jsonObjec.getString("data");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                        Toast.makeText(MyWorkActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    /*将选中的班级  截取  传给后台的 班级编号*/
    private String get_class_num(String text) {
        String num = "";
            num =class_num+ "0" + text.substring(0, 1) +  "0" + text.substring(text.length()-2, text.length()-1);
        Log.d("@@","班级编号" + num);

        return  num;
    }


    /*日期*/
    private void data() {
        adapter.setEdit_or_delete(new PushMyWorkAdapter.Edit_or_Delete() {
            @Override
            public void dothis(final ImageView edit_push, final int position, ImageView delete_push) {
                edit_push.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(MyWorkActivity.this,"我要修改"+position,Toast.LENGTH_SHORT).show();
                    }
                });
                delete_push.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(MyWorkActivity.this,"我要删除"+position,Toast.LENGTH_SHORT).show();
                        strings.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    /*更改日期*/
    private void alterDate() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        String date = sDateFormat.format(new Date());
        datatime.setText(date);
        datatime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar d = Calendar.getInstance(Locale.CHINA);
                Date myDate = new Date();
                d.setTime(myDate);
                year = d.get(Calendar.YEAR);
                month = d.get(Calendar.MONTH);
                day = d.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(MyWorkActivity.this, Datelistener, year, month, day);
                dpd.show();
            }
        });


    }

/*日期转换*/
    private DatePickerDialog.OnDateSetListener Datelistener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int myyear, int monthOfYear, int dayOfMonth) {
            year = myyear;
            month = monthOfYear;
            day = dayOfMonth;
            data_submit = year+"-"+ (month + 1)+"-"+day;
            updateDate();
        }

        private void updateDate() {
            //查询所选的内容 提交 年级号等
            data_submit = year+"-"+ (month + 1)+"-"+day;
            datatime.setText(year + "年" + (month + 1) + "月" + day + "日");
        }
    };
    /*修改日期*/
    private void initcommonEvent() {
        back_in_mywork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        alterDate();
    }
    /*动态添加班级*/
    private void addClassname() {
        ArrayList<String> arrayList_class = new ArrayList<String>();
        List<teacher> teacher = Db_TeacherService.getInstance(MyWorkActivity.this).loadAllNote();
        for (com.neusoft.sample.GreenDao.teacher a : teacher) {
            arrayList_class.add(a.getClassName1());
            arrayList_class.add(a.getClassName2());
            arrayList_class.add(a.getClassName3());
            arrayList_class.add(a.getClassName4());
            subject = a.getSubjectName();
            class_num = a.getSchoolNum();
            teacher_name = a.getTeacherName();
        }
        for(int i =0; i<arrayList_class.size();i++){
            if(arrayList_class.get(i)!=null) {
                String a = arrayList_class.get(i);
                CheckBox test1 = new CheckBox(this);
                test1.setText(a);
                test1.setTextSize(18);
                classaddcontainer.addView(test1);
            }
        }
    }
/*初始化控件*/
    private void initView() {
        data_submit = "";
        strings = new ArrayList<>();
        adapter = new PushMyWorkAdapter(this, strings);
        back_in_mywork = (ImageView) findViewById(R.id.back_in_mywork);
        addworkitem = (ImageView) findViewById(R.id.addworkitem);
        datatime = (TextView) findViewById(R.id.datatime);
        classaddcontainer = (LinearLayout) findViewById(R.id.classaddcontainer);
        workitemlist = (ListView) findViewById(R.id.workitemlist);
        rewrite = (Button) findViewById(R.id.rewrite);
        seend = (Button) findViewById(R.id.seend);
        pushcontext = (EditText) findViewById(R.id.pushcontext);
        lookup= (ImageView) findViewById(R.id.lookup);
    }
 /*获取当前时间   */
    private  String get_today_time(){
        SimpleDateFormat    formatter    =   new    SimpleDateFormat    ("yyyy-MM-dd    HH:mm:ss     ");

        Date    curDate    =   new    Date(System.currentTimeMillis());//获取当前时间
        String    time    =    formatter.format(curDate);
        Log.d("@@","系统时间"+time);
        return  time;
    }
    /*获取当天时间   时间  没有时分秒   */
    private  String get_today_time1(){
        SimpleDateFormat    formatter    =   new    SimpleDateFormat    ("yyyy-MM-dd");
        Date    curDate    =   new    Date(System.currentTimeMillis());//获取当前时间
        String    time    =    formatter.format(curDate);
        Log.d("@@","系统时间"+time);
        return  time;
    }

}
