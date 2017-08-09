package com.neusoft.sample.View.xel_mine.Xel_mine_notify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ncapdevi.sample.R;
import com.neusoft.sample.Ctrl.Db_Notification;
import com.neusoft.sample.Ctrl.wenchengcheng.ExitApplication;
import com.neusoft.sample.GreenDao.Notification;
import com.neusoft.sample.Model.Constant;
import com.neusoft.sample.View.BaseActivity;

import java.util.List;

public class xel_mine_notify_detail extends BaseActivity {
    TextView title, publisher, content, titleNo, publishTime;
    ImageButton notify_detail_back;
    String queryID;
    List<Notification> info;
    Context context;
    String uniID;
    String readState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xel_mine_notify_info);
        ExitApplication.getInstance().addActivity(this);
        init();
        initView();
        queryNotifyDetail();
    }

    private void init() {
        notify_detail_back = (ImageButton) findViewById(R.id.notify_detail_back);
        title = (TextView) findViewById(R.id.notify_detail_title);
        publisher = (TextView) findViewById(R.id.notify_detail_publisher);
        content = (TextView) findViewById(R.id.notify_detail_content);
        titleNo = (TextView) findViewById(R.id.notify_detail_titleNo);
        publishTime = (TextView) findViewById(R.id.notify_detail_publishTime);
    }

    private void initView() {
        notify_detail_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void queryNotifyDetail() {
        Intent intent = getIntent();
        queryID = String.valueOf(intent.getExtras().get("NotificationID"));
        queryDatabase();
        for (Notification q : info) {
            if (q.getNotification_id().equals(queryID)) {
                title.setText(isNullConvert(q.getTitle()));
                publisher.setText("发布方:" + isNullConvert(q.getNotify_publisher()));
                content.setText(isNullConvert(q.getContent()));
                titleNo.setText("通知编号:" + isNullConvert(q.getTitleNo()));
                publishTime.setText("发布时间:" + isNullConvert(Constant.getyymmddhhmmss(q.getNotify_time())));
                if (q.getIs_read().equals("0")) {
                    q.setIs_read("1");
                    readState = "0";
                    uniID = q.getUnique_id();
                    Db_Notification.getInstance(xel_mine_notify_detail.this).saveNote(q);
                    Log.d("detail:read", JSON.toJSONString(q));
                }else {
                    readState = "1";
                }
            }
        }
        queryDatabase();
        for (Notification p : info) {
            if (p.getUnique_id().equals(uniID)) {
                Log.d("改过的列表项:", JSON.toJSONString(p));
            }
        }

    }

    private void queryDatabase() {
        info = Db_Notification.getInstance(context).loadAllNote();
    }

    private String isNullConvert(String content) {
        String inValidContent = "无内容";
        if (content == null) {
            return inValidContent;
        } else {
            return content;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent in = new Intent();
        in.putExtra("requestCode","0");

        in.putExtra("isReadQuery",readState);
    }
}
