package com.neusoft.sample.View.xel_specialtopic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.ncapdevi.sample.R;
import com.neusoft.sample.View.BaseActivity;

public class xel_specialtopic_math_aosai_text extends BaseActivity {
    private Toolbar toolbar;
    Button btn_jiexi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xel_specialtopic_math_aosai_ceshiti);
        toolbar = (Toolbar) findViewById(R.id.toolbar_in_aosai_learn);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setTitle("专题->数学->奥赛->奥赛学习->第一章");
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
            initView();
    }
    public void break_1(View view){
        finish();
    }
    public void initView() {
        btn_jiexi = (Button) findViewById(R.id.jiexi__);
        btn_jiexi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(xel_specialtopic_math_aosai_text.this,Xel_SpecialtOpic_Math_Aosai_fenxi.class);
                startActivity(intent);
            }
        });
    }
}
