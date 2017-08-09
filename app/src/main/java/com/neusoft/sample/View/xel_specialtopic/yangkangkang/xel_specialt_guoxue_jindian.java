package com.neusoft.sample.View.xel_specialtopic.yangkangkang;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.ncapdevi.sample.R;
import com.neusoft.sample.App;
import com.neusoft.sample.Ctrl.yangkangkang.GuoXueJingDian;
import com.neusoft.sample.Ctrl.yangkangkang.guoxue_listview_bean;
import com.neusoft.sample.Model.Stitching;
import com.neusoft.sample.View.Adapter.yangka.Guoxue_ListviewAdapter;
import com.neusoft.sample.View.AlwaysMarqueeTextView;
import com.neusoft.sample.View.Mp3.MusicPlayService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class xel_specialt_guoxue_jindian extends AppCompatActivity {
    Typeface tf,tttf;
    ///基本控件
    private TextView tv_timu;
    private AlwaysMarqueeTextView tv_titele;//题目
    private ListView lv_content;//内容
    private com.neusoft.sample.Model.mygridview gridView;
    private ImageView iv_voice,iv_voice_yiwen;
    private ImageButton im_left;
    Boolean isFirst = true;
    List<GuoXueJingDian> accept;
    String yuanwen = "";//原文MP3
    String yiwen = "";//译文Mp3
    //音频的引用
    private MusicPlayService mService;
    private App application;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xel_specialt_guoxuejingdian);
        INi_view();
        Inti_jieshou();
        Ini_fang();
        //访问路径，音频，图片
    }
    private void Ini_fang() {
        iv_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(xel_specialt_guoxue_jindian.this, "正在加载音频......", Toast.LENGTH_SHORT).show();
                mService.onDestroy();
                Log.d("@@",yuanwen);
                mService.playMusics(yuanwen);
            }
        });
        iv_voice_yiwen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(xel_specialt_guoxue_jindian.this, "正在加载音频......", Toast.LENGTH_SHORT).show();

                mService.onDestroy();
                Log.d("@@",yuanwen);
                mService.playMusics(yiwen);
            }
        });
    }

    //接受服务器的传值  病将它  解析到   list  里边
    private void Inti_jieshou() {
        Intent intent_jeishou =getIntent();
        String num = intent_jeishou.getStringExtra("num");
        String name = intent_jeishou.getStringExtra("name");
      tv_timu.setText(name);
        
        String jieshou =intent_jeishou.getStringExtra("jieshou");
        try {
            JSONObject json = new JSONObject(jieshou);

            accept = JSON.parseArray((String) json.get("data"), GuoXueJingDian.class);
            Ini_add_list();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String [] guoxue = Stitching.get_shiroute();
        yuanwen = guoxue[0];
        //Log.d("guoxue_jingdian__","音频路径[原文:"+yuanwen+"译文:"+yiwen+"]");
        yiwen = guoxue[1];
        tv_titele.setText("专题>语文>国学经典>第"+guoxue[2]+"课");
    }
    //////////////////////////////////获取的值添加到 list中、、、、、、、、、、、、、、、、、、、、、、、、、、、、
    private void Ini_add_list() {
        List<guoxue_listview_bean> guoxue_list_listview = new ArrayList<>();
        Collections.sort(accept);
        int widthIndex = 0;
        int height = 0;
        ArrayList<String> setHan = new ArrayList<>();
        ArrayList<String> setPin = new ArrayList<>();
        for(int i = 0;i<accept.size();i++){
            //判断段首
            if (accept.get(i).isFirst() && i != 0) {
                //之前的构造

                for (int x = 0;x<13;x++) {
                    setHan.add("    ");
                    setPin.add("    ");
                }

                
                guoxue_list_listview.add(new guoxue_listview_bean(
                        setPin.get(0),setPin.get(1),setPin.get(2),setPin.get(3),setPin.get(4),setPin.get(5),setPin.get(6),setPin.get(7),setPin.get(8),
                        setPin.get(9),setPin.get(10),setPin.get(11),setPin.get(12),
                        setHan.get(0),setHan.get(1),setHan.get(2),setHan.get(3),setHan.get(4),setHan.get(5),setHan.get(6),setHan.get(7),setHan.get(8)
                        ,setHan.get(9),setHan.get(10),setHan.get(11),setHan.get(12)
                ));
                lv_content.setAdapter(new Guoxue_ListviewAdapter(this,guoxue_list_listview));
                setHan.clear();
                setPin.clear();
                widthIndex=0;
              //  height++;

                isFirst = true;
            }else if(i!=0){
                isFirst = false;
            }
            
            //获取所有的数组
            String[] hans = accept.get(i).getWord();
            String[] ping = accept.get(i).getPin();
            
            for (int j = 0;j<28;j++) {

                if (j < 2) {

                    if (isFirst ==true) {

                        setHan.add("    ");
                        setPin.add("    ");
                        widthIndex++;
                    }
                    continue;
                }
                
                if (hans[j-2].equals("\"")) {
                    if (i==accept.size()-1) {
                        //这是最后一段话的最后组
                        for (int x = 0;x<13;x++) {
                            setHan.add("    ");
                            setPin.add("    ");
                        }


                        guoxue_list_listview.add(new guoxue_listview_bean(
                                setPin.get(0),setPin.get(1),setPin.get(2),setPin.get(3),setPin.get(4),setPin.get(5),setPin.get(6),setPin.get(7),setPin.get(8),
                                setPin.get(9),setPin.get(10),setPin.get(11),setPin.get(12),
                                setHan.get(0),setHan.get(1),setHan.get(2),setHan.get(3),setHan.get(4),setHan.get(5),setHan.get(6),setHan.get(7),setHan.get(8)
                                ,setHan.get(9),setHan.get(10),setHan.get(11),setHan.get(12)
                        ));
                        lv_content.setAdapter(new Guoxue_ListviewAdapter(this,guoxue_list_listview));
                    }
                    break;
                }
               if(ping[j-2].equals("\"")){
                   ping[j-2] = " ";
               }
                setHan.add(hans[j-2]);
                setPin.add(ping[j-2]);
                widthIndex++;

                if (widthIndex % 13 == 0 && widthIndex != 0) {
                    widthIndex = 0;
                    height++;
                    
                    guoxue_list_listview.add(new guoxue_listview_bean( setPin.get(0),setPin.get(1),setPin.get(2),setPin.get(3),setPin.get(4),setPin.get(5),setPin.get(6),setPin.get(7),setPin.get(8),
                            setPin.get(9),setPin.get(10),setPin.get(11),setPin.get(12),
                            setHan.get(0),setHan.get(1),setHan.get(2),setHan.get(3),setHan.get(4),setHan.get(5),setHan.get(6),setHan.get(7),setHan.get(8)
                            ,setHan.get(9),setHan.get(10),setHan.get(11),setHan.get(12)));
                    lv_content.setAdapter(new Guoxue_ListviewAdapter(this,guoxue_list_listview));
                    setHan.clear();
                    setPin.clear();
                }
            }
 
            
        }
    }
    ////////////////////////////////////初始化控件的/////////////////////////////////////
    private void INi_view() {
        tttf =  Typeface.createFromAsset(getAssets(), "ttf.ttf" );
        tf =  Typeface.createFromAsset(getAssets(), "fonts/youyuan.ttf" );
        //初始化控件
        application = (App) getApplication();
        mService = application.getmService();
        tv_timu = (TextView) findViewById(R.id.guo_title);
        tv_timu.setTypeface(tttf);
        lv_content = (ListView) findViewById(R.id.guoxue_listview);
        iv_voice = (ImageView) findViewById(R.id.guo_voice);
        iv_voice_yiwen = (ImageView) findViewById(R.id.guo_voice_yiwen);
        im_left = (ImageButton) findViewById(R.id.guo_left);
        tv_titele = (AlwaysMarqueeTextView) findViewById(R.id.title_guoxue);
        tv_titele.setTypeface(tf);
        im_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mService.onDestroy();
                finish();
            }
        });


        tttf =  Typeface.createFromAsset(getAssets(), "ttf.ttf" );

        tv_timu.setTypeface(tttf);
        tv_timu.getPaint().setFakeBoldText(true);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mService.onDestroy();
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
