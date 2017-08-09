package com.neusoft.sample.View.xel_mine.MyHomeWork;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.ncapdevi.sample.R;
import com.neusoft.sample.Ctrl.Db_TeacherService;
import com.neusoft.sample.GreenDao.teacher;
import com.neusoft.sample.Model.Constant;
import com.neusoft.sample.Model.Get_TeacherFindTeacherWrong;

import org.json.JSONArray;
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
* 老师查看发布完 的作业*/
public class ParentLookUpWork extends AppCompatActivity{
    private ListView lookupworklist;
    private RadioGroup classaddcontainer;
    private List<String> it;
    private LookUpAdapter lookUpAdapter;
    private ImageView back_in_mywork;
    HomeWork homework;
    Button btn_submit;
    String data_submit = "";
    private TextView datatime;                 //选择的日期
    String subject = null;                     //科目
    String class_num = null;                   //班级编号
    String class_num1 = null;
    String teacher_name = null;               //老师姓名
    private int year;                            //年月日
    private int month;                          //年月日
    private int day;                            //年月日
    ArrayList<String> arrayList_class = new ArrayList<String>();

    Handler handler;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("查看已发布作业");
        setContentView(R.layout.activity_parent_look_up_work);
        Init_view();
        initcommonEvent();
        addClassname();
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();

            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
    private void addClassname() {


        List<teacher> teacher = Db_TeacherService.getInstance(ParentLookUpWork.this).loadAllNote();
        for (com.neusoft.sample.GreenDao.teacher a : teacher) {
            arrayList_class.add(a.getClassName1());
            arrayList_class.add(a.getClassName2());
            arrayList_class.add(a.getClassName3());
            arrayList_class.add(a.getClassName4());
            subject = a.getSubjectName();
            class_num = a.getSchoolNum();
            teacher_name = a.getTeacherName();
        }
        Log.d("@@","打印数组的长度  "+arrayList_class.size());
        for (int i = 0; i < arrayList_class.size(); i++) {
            String a = arrayList_class.get(i);
            RadioButton test1 = new RadioButton(this);
            test1.setText(a);
            test1.setTextSize(18);
            classaddcontainer.addView(test1,i);

        }
        final int dd = arrayList_class.size();
        System.out.println("-----------------------------------------------");

        classaddcontainer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            int  checkedId = 0;
            @Override
            public void onCheckedChanged(RadioGroup group,int  checkedId   ) {
                Log.d("@@","check "+(checkedId)+" 编号 "+group.getCheckedRadioButtonId()+" 班级名字 "
                        +group.getChildAt(checkedId));
                if(checkedId>=arrayList_class.size()){
                    checkedId = checkedId -1;
                    Log.d("@@#","dd"+checkedId/arrayList_class.size());
                    if(checkedId/arrayList_class.size()!=0){
                        checkedId = checkedId-(checkedId/arrayList_class.size())*arrayList_class.size();
                    }


                    RadioButton rabt;

                        rabt = (RadioButton) classaddcontainer.getChildAt(checkedId);


                    Log.d("@@","class"+rabt.getText().toString());
                    class_num1 = get_class_num(rabt.getText().toString());
                    Log.d("@@","class"+class_num1);
                }else {
                    Log.d("@@#","dd"+checkedId/arrayList_class.size());
                    if(checkedId/arrayList_class.size()!=0){
                        checkedId = checkedId-(checkedId/arrayList_class.size())*arrayList_class.size();
                    }
                    RadioButton rabt;
                    if(checkedId == (arrayList_class.size())){
                        Log.d("@@","进入最后一个" +  checkedId);
                        rabt = (RadioButton) classaddcontainer.getChildAt(checkedId);
                    }else{
                        Log.d("@@","未进入最后一个" +  checkedId);
                        rabt = (RadioButton) classaddcontainer.getChildAt(checkedId-1);
                    }

                    Log.d("@@","class"+rabt.getText().toString());
                    class_num1 = get_class_num(rabt.getText().toString());
                    Log.d("@@","class"+class_num1);
                }

            }
        });
        System.out.println("-----------------------------------------------");
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
                DatePickerDialog dpd = new DatePickerDialog(ParentLookUpWork.this, Datelistener, year, month, day);
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
            data_submit = year + "-" + ( month+1) + "-" + day;
            updateDate();
        }

        private void updateDate() {
            //查询所选的内容 提交 年级号等
            data_submit = year + "-" +( month+1) + "-" + day;
            datatime.setText(year + "年" + (month + 1) + "月" + day + "日");
        }
    };

    /*修改日期*/
    private void initcommonEvent() {
        back_in_mywork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                arrayList_class.clear();

            }
        });
        alterDate();
    }
    /*初始化控件*/
    private void Init_view() {



        homework = new HomeWork();
        classaddcontainer = (RadioGroup) findViewById(R.id.classaddcontainer1);

      /*  classaddcontainer.setOnCheckedChangeListener(this);  //  匿名内部类实现点击事件*/

        btn_submit = (Button) findViewById(R.id.btn_check);
        back_in_mywork = (ImageView) findViewById(R.id.back_in_mywork1);
        datatime = (TextView) findViewById(R.id.datatime1);
        lookupworklist = (ListView) findViewById(R.id.lookupworklist1);
    }

    /*向后台请求*/
    private void submit() {

        final HashMap<String, String> map = new HashMap();
        if (data_submit.equals("")) {
            data_submit = get_today_time1();
        }
        map.put("classno", class_num1);
        map.put("date1", data_submit);
        map.put("subject", subject);
        Log.d("@@","老师发布的作业"+class_num1+data_submit+subject);
        if (class_num.equals("")) {
            Toast.makeText(this, "请选择班级", Toast.LENGTH_SHORT).show();
        } else {
            handleMsg();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String success = null;
                    try {
                        success = Get_TeacherFindTeacherWrong.getStringCha(Constant.check_work_teacher, map);
                        JSONObject json = new JSONObject(success);
                        String data = json.getString("data");
                        String successs = json.getString("success");
                        if(successs.equals("200")) {
                            JSONObject json1 = new JSONObject(data);
                            Log.d("@@", "ho1m" + "data" + data + "work");
                            String workk = json1.getString("homework");
                            //JSONArray work =JSON.parseArray(workk);
                            String aa = " [{\"itemno\":1,\"workname\":\"game\"},{\"itemno\":2,\"workname\":\"老老实实\"}]";
                            JSONArray ja = new JSONArray(workk);
                        /*Iterator<Object> it = work.iterator();
                        List<String> key = new ArrayList<String>();*/
                            List<String> value = new ArrayList<String>();
                            for (int i = 0; i < ja.length(); i++) {
                                JSONObject jo = (JSONObject) ja.get(i);
                                String work_name = jo.getString("workname");
                                value.add(work_name);
                            }
                            Message msg = new Message();
                            msg.obj = value;
                            handler.sendMessage(msg);
                        }else {
                            List<String> value = new ArrayList<String>();
                            Message msg = new Message();
                            msg.obj = value;
                            value.add("此班今日无作业");
                            handler.sendMessage(msg);
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

    private void handleMsg() {
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                ArrayList<String> aa= (ArrayList<String>) msg.obj;
                lookUpAdapter  = new LookUpAdapter(ParentLookUpWork.this,aa );
                lookupworklist.setAdapter(lookUpAdapter);
            }
        };

    }

    /*将选中的班级  截取  传给后台的 班级编号*/
    private String get_class_num(String text) {
        String num = "";
        Log.d("@@","班级编号yi "+text);
        num = class_num + "0" + text.substring(0, 1) + "0" + text.substring(text.length() - 2, text.length() - 1);
        Log.d("@@", "班级编号er " + num);
        return num;
    }

    /*获取当天时间   时间  没有时分秒   */
    private String get_today_time1() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String time = formatter.format(curDate);
        Log.d("@@", "系统时间" + time);
        return time;
    }

    @Override
    protected void onPause() {
        super.onPause();
        ParentLookUpWork.this.finish();

    }


   /* *//*关于radiogroup  匿名内部类实现点击事件的方法
    * *//*
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        Log.d("@@#","dd"+checkedId/4);
        if(checkedId/4!=0){
            checkedId = checkedId-(checkedId/4)*4;
        }
        Log.d("@@","check "+(checkedId)+" 编号 "+group.getCheckedRadioButtonId()+" 班级名字 "
                +group.getChildAt(checkedId));
        *//*int aa = 1;
        Log.d("@@","默认的check2"+(checkedId-1));
        if(checkedId  > 5){
            aa = 0;
        }else {
            aa = (checkedId-1);
        }*//*
        RadioButton rabt = (RadioButton) classaddcontainer.getChildAt(checkedId);
        Log.d("@@","class"+rabt.getText().toString());
        class_num1 = get_class_num(rabt.getText().toString());
        Log.d("@@","class"+class_num1);
    }*/
}
