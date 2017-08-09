package com.neusoft.sample.View.xel_course;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ncapdevi.sample.R;
import com.neusoft.sample.View.BaseActivity;

public class xel_activity_noo extends BaseActivity {

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xel_noo);
        imageView  = (ImageView) findViewById(R.id.nnn);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
