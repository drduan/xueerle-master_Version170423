package com.neusoft.sample.View.xel_mine;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ncapdevi.sample.R;
import com.neusoft.sample.Ctrl.Db_UserService;
import com.neusoft.sample.Ctrl.wenchengcheng.ExitApplication;
import com.neusoft.sample.Ctrl.wenchengcheng.rankListItemAdapter;
import com.neusoft.sample.GreenDao.User;
import com.neusoft.sample.Model.Constant;
import com.neusoft.sample.View.BaseActivity;
import com.neusoft.sample.View.Fragment.xel_mine_fragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Xel_mine_ranklist extends BaseActivity {
    public LinearLayout rankListLayout;
    public ImageButton rankList_back;
    public TextView title;
    public ListView timeList;
    public ListView dateAdder = null;
    public User tUserInfo = null;

    public static final String TAG = "XEL_MINE_RANKLIST";
    public static Date netWorkTime;
    public List<Map<Object,String>> listItem = new ArrayList<Map<Object,String>>();// 定义显示的内容包装
    public rankListItemAdapter listItemAdapter;
    int month;
    int year;
    Date date;

    @Override
    protected synchronized void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApplication.getInstance().addActivity(this);
        setContentView(R.layout.xel_mine_ranklist);
        init();
        initData();

    }

    private void init() {
        rankList_back = (ImageButton) findViewById(R.id.rankList_back);
        //rankList_ScrollView = (ScrollView) findViewById(R.id.rankList_ScrollView);
        timeList = (ListView) findViewById(R.id.rankList_ListView);
        rankListLayout = (LinearLayout) findViewById(R.id.rankList_layout);
        rankList_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        title = (TextView) findViewById(R.id.rankList_Title);
    }

    private void initData() {
        tUserInfo = xel_mine_fragment.tUserInfo;

        String a = tUserInfo.getCity();
        String b = tUserInfo.getCity_nub();
        String c = tUserInfo.getGrade();
        String d = tUserInfo.getGrade_nub();

        String grade = c.substring(c.length() - 1, c.length());
        Typeface tf = Typeface.createFromAsset(getAssets(), "ttf.ttf");
        title.setTypeface(tf);
        title.setText(a + "市" + grade + "年级 " + "排行榜");
        List<User> user = Db_UserService.getInstance(this).loadAllNote();
        dateAdder = (ListView) findViewById(R.id.rankList_ListView);
        dateAdder.setDividerHeight(1);

        Log.d("User_data", "i" + JSON.toJSONString(tUserInfo));
        netWorkTime = Constant.getNowDate();
        month = netWorkTime.getMonth();
        year = netWorkTime.getYear() + 1900;
       // Log.d(TAG, "CITY:" + a + "CITY_NUB" + b + "GRADE:" + c + "GRADE_NUB:" + d);
        Log.d(TAG, "month:" + netWorkTime.getMonth());
        addListItem(month, year);
    }

    private void addListItem(int month, int year) {
        int i;
        /*int height = DesityUtil.dip2px(this, month * 55);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) dateAdder.getLayoutParams();
        params.height = height;
        dateAdder.setLayoutParams(params);*/
        //dateAdder.setFocusable(true);

        //rankList_ScrollView.smoothScrollTo(0, 20);
        for (i = 1; i <= month; i++) {
            HashMap<Object, String> map = new HashMap<Object, String>();
            map.put("subTitle", year + "年" + i + "月");
            Log.d(TAG,"map:"+map+"month:"+i);
            listItem.add(map);
        }
        listItemAdapter = new rankListItemAdapter(Xel_mine_ranklist.this, listItem);
        dateAdder.setAdapter(listItemAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void finish() {
        super.finish();
    }
}