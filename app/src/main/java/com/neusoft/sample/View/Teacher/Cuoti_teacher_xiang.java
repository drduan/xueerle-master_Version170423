package com.neusoft.sample.View.Teacher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.ncapdevi.sample.R;

/*
   创建人：  杨康   2016/12/21、
   错题详情页面逻辑
* */
public class Cuoti_teacher_xiang extends AppCompatActivity {
    private ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xel_cuoti_teacher_xiang);
        InitView();
    }
    /*初始化控件*/
    private void InitView() {
            list = (ListView) findViewById(R.id.list);
    }
}
