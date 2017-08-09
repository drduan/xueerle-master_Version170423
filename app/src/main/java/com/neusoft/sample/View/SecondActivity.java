package com.neusoft.sample.View;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.ncapdevi.sample.R;
import com.neusoft.sample.Ctrl.Db_XTCSJGService;
import com.neusoft.sample.Ctrl.Db_XTCTJLService;
import com.neusoft.sample.Ctrl.Db_XTDCTMService;
import com.neusoft.sample.Model.MsharedPrefrence;
import com.neusoft.sample.View.xel_course.DoEaxm;
import com.neusoft.sample.View.xel_course.Xel_Course_Math_RefinedClass;
import com.neusoft.sample.util.ContextHolder;


/**
 * Created by duanxudong on 16/5/29.
 * Version 1
 */

public class SecondActivity extends BaseActivity {
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
            int position1= intent.getIntExtra("kousuan_go", 1);
            String nub_zu=intent.getStringExtra("nub_zu");
            zh_1 = intent.getStringExtra("zh_1");

            System.out.println("挑战开始挑战组为:"+nub_zu);
            int position2= intent.getIntExtra("haoti_go",1);
            if (position1==3){
                //删除数据库中原有的数据
                Db_XTDCTMService.getInstance(ContextHolder.getContext()).deleteAllNote();
                Db_XTCTJLService.getInstance(ContextHolder.getContext()).deleteAllNote();
                Db_XTCSJGService.getInstance(ContextHolder.getContext()).deleteAllNote();
                MsharedPrefrence.SetvoidDoGain(ContextHolder.getContext());
                System.out.println("每次清数据"+   Db_XTDCTMService.getInstance(ContextHolder.getContext()).loadAllNote().toString());
                Intent intent1 = new Intent();
                intent1.putExtra("nub_zu",nub_zu);
                intent1.putExtra("zh_1",zh_1);
                Log.d("@@@","seconey组号"+nub_zu);
                intent1.setClass(SecondActivity.this,DoEaxm.class);
                startActivity(intent1);
            }
            else if (position2==2){
                Intent intent1 = new Intent();
                intent1.setClass(SecondActivity.this,Xel_Course_Math_RefinedClass.class);
                startActivity(intent1);
            }
            finish();
            // TODO: 16/5/29

        };
    };
    private Handler handler=new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case LOG:
                    iv.setImageDrawable(getResources().getDrawable(R.drawable.two));
                    //	iv.setBackgroundColor(R.drawable.two);
                    break;
                case LOGIN:
                    iv.setImageDrawable(getResources().getDrawable(R.drawable.one));
                    //	iv.setBackgroundColor(R.drawable.one);
                    break;
                default:
                    break;
            }
        };
    };
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
