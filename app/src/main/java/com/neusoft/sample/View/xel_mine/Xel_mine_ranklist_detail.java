package com.neusoft.sample.View.xel_mine;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.ncapdevi.sample.R;
import com.neusoft.sample.Ctrl.Db_RankListService;
import com.neusoft.sample.Ctrl.wenchengcheng.ExitApplication;
import com.neusoft.sample.Ctrl.wenchengcheng.rankListInfoAdapter;
import com.neusoft.sample.GreenDao.Ranklist;
import com.neusoft.sample.View.AlwaysMarqueeTextView;
import com.neusoft.sample.View.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Xel_mine_ranklist_detail extends BaseActivity {
    AlwaysMarqueeTextView rankListInfo_title;
    ImageButton rankListInfo_back;
    ListView rankListItem_lv;
    String city,year,month,grade,queryCode,title;
    public rankListInfoAdapter listInfoAdapter;
    public List<Map<Object,String>> listInfo = new ArrayList<Map<Object,String>>();// 定义显示的内容包装
    private static final String TAG = "RANKDETAIL";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xel_mine_ranklist_detail);
        ExitApplication.getInstance().addActivity(this);
        init();
        initData();
        initView();
    }

    private void init() {

        rankListInfo_back = (ImageButton) findViewById(R.id.rankListInfo_back);
        rankListInfo_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        rankListInfo_title = (AlwaysMarqueeTextView) findViewById(R.id.rankListInfo_title);
        rankListItem_lv = (ListView) findViewById(R.id.rankListItems_lv);
        rankListItem_lv.setDividerHeight(0);
    }

    private void initData() {
        Intent intent = getIntent();
        city = intent.getStringExtra("city");
        year = intent.getStringExtra("year");
        month = intent.getStringExtra("month");
        grade = intent.getStringExtra("grade");
        queryCode = String.valueOf(intent.getExtras().get("queryCode"));
        Log.i(TAG,"queryCode:"+ queryCode + " " + "city:" + city + " " + "gradeNub:" + grade + " " + "monthNub:" + month + " " + "year:" + year);
        switch (grade){
            case "01" : grade = "一"; break;
            case "02" : grade = "二"; break;
            case "03" : grade = "三"; break;
            case "04" : grade = "四"; break;
            case "05" : grade = "五"; break;
            case "06" : grade = "六"; break;
 /*           case "07" : grade = "初一"; break;
            case "08" : grade = "初二"; break;
            case "09" : grade = "初三"; break;
            case "10" : grade = "高一"; break;
            case "11" : grade = "高二"; break;
            case "12" : grade = "高三"; break;*/
            default: grade = "--"; break;
        }

        List<Ranklist> queryList = Db_RankListService.getInstance(Xel_mine_ranklist_detail.this).loadAllNote();
        for (Ranklist ranklist : queryList){
            if (ranklist.getUnique_id().equals(queryCode)){
                String class_nub = ranklist.getClass_number();
                int i = class_nub.length();
                HashMap<Object, String> map = new HashMap<Object, String>();
                map.put("name",ranklist.getPhone());
                map.put("school",ranklist.getSchoolName());
                //map.put("grade",class_nub.substring(i-4,i-2));
                map.put("class",class_nub.substring(i-1,i));
                map.put("points", String.valueOf(ranklist.getIntegral_number()));
                Log.d(TAG,"map内的值:"+map);
                listInfo.add(map);
            }
        }
        listInfoAdapter = new rankListInfoAdapter(Xel_mine_ranklist_detail.this, listInfo);
        rankListItem_lv.setAdapter(listInfoAdapter);
    }

    private void initView() {
        title = city + "市" + year + "年" + month + "月" + grade + "年级" + "排行榜";
        rankListInfo_title.setText(title);
        Typeface tf = Typeface.createFromAsset(getAssets(), "ttf.ttf");
        rankListInfo_title.setTypeface(tf);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
