package com.neusoft.sample.View;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ncapdevi.sample.R;
import com.neusoft.sample.Ctrl.Db_XTCSJGService;
import com.neusoft.sample.Ctrl.Db_XTCTJLService;
import com.neusoft.sample.Ctrl.Db_XTDCTMService;
import com.neusoft.sample.Model.Constant;
import com.neusoft.sample.Model.MsharedPrefrence;
import com.neusoft.sample.Model.Post_er;
import com.neusoft.sample.Model.aosai_zu_Entity;
import com.neusoft.sample.View.AoSaiTest.Aosai_DoEaxm;
import com.neusoft.sample.util.ContextHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by duanxudong on 16/5/29.
 * Version 1
 */

public class aosai__SecondActivity extends BaseActivity {
    ti tt;
    private final int LOG=1;
    private final int LOGIN=2;
    private ImageView iv;
    String zh_1,zh_2,zh_3,zh_4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        show();
    }
    public void initView(){
        setContentView(R.layout.second_main);
        iv = (ImageView) findViewById(R.id.imageView1);
    }
    public void show(){
      tt= new ti();
        tt.start();
    }


    class ti extends Thread{
        public void run() {
            SystemClock.sleep(2000);
            Intent intent=getIntent();
            Db_XTDCTMService.getInstance(ContextHolder.getContext()).deleteAllNote();
            Db_XTCTJLService.getInstance(ContextHolder.getContext()).deleteAllNote();
            Db_XTCSJGService.getInstance(ContextHolder.getContext()).deleteAllNote();

            String nub_zu=intent.getStringExtra("nub_zu");
            HashMap hashMap=new HashMap();
            hashMap.put("No", nub_zu.substring(0,9));
            String response = null;
            try {
                response = Post_er.getStringCha(Constant.post_Kousuan, hashMap);
                Map<String, Object> maps = (Map<String, Object>) JSON.parse(response);
                String ct = maps.get("data").toString();
                Log.d("OO", ct.toString());
                List<aosai_zu_Entity> list =  new Gson().fromJson(ct, new TypeToken<List<aosai_zu_Entity>>(){}.getType());
                Collections.sort(list);
                System.out.println("挑战开始挑战组为:"+nub_zu);
                System.out.println("挑战开始挑战内容为:"+JSON.toJSONString(list));
                Intent intent1 = new Intent();
                intent1.putExtra("nub_zu",nub_zu);

                MsharedPrefrence.SetvoidDoGain(aosai__SecondActivity.this);

                intent1.putParcelableArrayListExtra("Entity", (ArrayList<? extends Parcelable>) list);
                Log.d("@@@","seconey组号"+nub_zu);
                intent1.setClass(aosai__SecondActivity.this,Aosai_DoEaxm.class);
                startActivity(intent1);

                finish();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // TODO: 16/5/29

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
