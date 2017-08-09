package com.neusoft.sample.View.xel_specialtopic.yangkangkang;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ncapdevi.sample.R;
import com.neusoft.sample.Ctrl.yangkangkang.RiJiYueLeiStudy;
import com.neusoft.sample.Ctrl.yangkangkang.list_view_rijiyuelei_bean;
import com.neusoft.sample.Model.Stitching;
import com.neusoft.sample.View.Adapter.yangka.rijiyulei_GridviewAdapter;
import com.neusoft.sample.View.AlwaysMarqueeTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by 杨康 on 2016/7/16.
 */
public class xel_specialt_chinese_rijiyuelei extends AppCompatActivity {
    Typeface tf ;
        private Toolbar toolbar;
        private ListView listView;
    private ImageButton imageView ;
    private TextView mtv_title;
    private AlwaysMarqueeTextView title;
    List<RiJiYueLeiStudy> riJiYueLeiStudies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xel_course_chinese_rijiyuelei);
        tf =  Typeface.createFromAsset(getAssets(), "fonts/youyuan.ttf" );
        INi_add_listview();
        Toolebar();
    }
    private void Toolebar() {
    }
    private void initData() {
        List<list_view_rijiyuelei_bean> list_listview_ri = new ArrayList<>();
        Collections.sort(riJiYueLeiStudies);
        for(int i = 0;i<riJiYueLeiStudies.size();i++){

            list_listview_ri.add(new list_view_rijiyuelei_bean(
                    riJiYueLeiStudies.get(i).getContent(),
                    riJiYueLeiStudies.get(i).getItemNo()
                   
            ));
        }
        String   get_stiting[] = Stitching.get_rijiyuelei();
        Intent intent = getIntent();
        String hao = intent.getStringExtra("hao");
        String name =  intent.getStringExtra("name");
        String hao2  = hao.substring(hao.length()-4,hao.length()-2);
        String hao1 = hao.substring(hao.length()-2,hao.length());
        listView= (ListView) findViewById(R.id.rijiyuelei_learn_listview);
        mtv_title = (TextView) findViewById(R.id.title_rijiyuelei);
        title = (AlwaysMarqueeTextView) findViewById(R.id.rijiyuelei_Navigation);
        imageView = (ImageButton) findViewById(R.id.left_rijiyuelei);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mtv_title.setTypeface(tf);
        mtv_title.setText("第"+hao1+"节 "+name);
        title.setTypeface(tf);
        title.setText("专题>语文>日积月累>第"+hao2+"章>第"+hao1+"节");//>第"+hao1+"节
        listView.setAdapter(new rijiyulei_GridviewAdapter(this, list_listview_ri));
    }
    //接受服务器上的数据解析  然后放在  listview  中
    private void INi_add_listview() {
        Intent intent_riji = getIntent();
        String rijiyuelei = intent_riji.getStringExtra("riji");
        try {
            JSONObject jsonObject=new JSONObject(rijiyuelei);
            riJiYueLeiStudies= JSON.parseArray((String) jsonObject.get("data"),RiJiYueLeiStudy.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initData();
    }
}
