package com.neusoft.sample.View.xel_specialtopic.yangkangkang;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.ncapdevi.sample.R;
import com.neusoft.sample.App;
import com.neusoft.sample.Ctrl.yangkangkang.XiaoXueTuoZhanGuShiCi;
import com.neusoft.sample.Ctrl.yangkangkang.shici_gridview_bean;
import com.neusoft.sample.Ctrl.yangkangkang.shici_title_gridview_bean;
import com.neusoft.sample.Model.Consant_stringg;
import com.neusoft.sample.Model.Constant;
import com.neusoft.sample.Model.Stitching;
import com.neusoft.sample.View.Adapter.yangka.shici_GridviewAdapter;
import com.neusoft.sample.View.AlwaysMarqueeTextView;
import com.neusoft.sample.View.Mp3.MusicPlayService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class xel_specialt_maozedong_shici extends AppCompatActivity {
    Typeface tf,tttf;
    private TextView tv_stu_title,tv_stu_autor,tv_stu_shang,tv_stu_pinyin,tv_title;
    private AlwaysMarqueeTextView tv_shici_Navigation;
    private GridView lv_stu_shici;
    private ImageView im_voice,img_shangxi;
    private ImageButton ima_left;
    private LinearLayout ln_shangxi;
    String web_yuanwen;
    String web_shangxi;
    /////////////////////////////////////////数据存储
    //这是   一个存储  接受到的  实体
    List<XiaoXueTuoZhanGuShiCi> accept;
    String shi_title="";
    String shi_titles="";
    String shi_autor="";
    String shi_autors="";
    String shi_content="";
    String hou_2= "";
    //音频的引用
    private MusicPlayService mService;
    private App application;
    private GridView Ggv_title_shi;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xel_specialt_shici);
        Initview();
        Inti_jieshou();

    }
    //添加题目和作者
    private void Inti_add() {
        tv_shici_Navigation.setText("专题>语文>毛泽东诗词>第"+hou_2+"首");
    }
    //////添加向  listview   中添加东西
    private void Ini_add_list() {
        List<shici_gridview_bean> list_listview = new ArrayList<>();
        List<shici_title_gridview_bean> list_listview_ = new ArrayList<>();
        Collections.sort(accept);
        for(int i = 0;i<accept.size();i++){
            //获取每一行的  最后两位
            String int_ = accept.get(i).getItemNo();
           // 赏析的取出

            if(accept.get(i).getAppreciate().equals("\"")) {
            }
            else {
                String shangxi = Constant.getString(accept.get(0).getAppreciate());
                if(shangxi.length()<4){
                    ln_shangxi.setVisibility(View.GONE);
                }
                tv_stu_shang.setText(shangxi);
            }
            if(accept.get(i).getWriter().equals("\"\"")){
            }else {
                shi_autors = accept.get(i).getWriter();
            }
            if(int_.length() ==8){

                String hou_1 = int_.substring(int_.length()-1,int_.length());
                if(hou_1.equals("1")){
                    String pin1,pin2,pin3,pin4,pin5,pin6,pin7,pin8,pin9,pin10,pin11,pin12;
                    String w1,w2,w3,w4,w5,w6,w7,w8,w9,w10,w11,w12;

                    w1 = Consant_stringg.getString(accept.get(i).getWord1());
                    w2 = Consant_stringg.getString(accept.get(i).getWord2());
                    w3 = Consant_stringg.getString(accept.get(i).getWord3());
                    w4 = Consant_stringg.getString(accept.get(i).getWord4());
                    w5 = Consant_stringg.getString(accept.get(i).getWord5());
                    w6 = Consant_stringg.getString(accept.get(i).getWord6());
                    w7 = Consant_stringg.getString(accept.get(i).getWord7());
                    w8 = Consant_stringg.getString(accept.get(i).getWord8());
                    w9 = Consant_stringg.getString(accept.get(i).getWord9());
                    w10 = Consant_stringg.getString(accept.get(i).getWord10());
                    w11= Consant_stringg.getString(accept.get(i).getWord11());
                    w12 = Consant_stringg.getString(accept.get(i).getWord12());
                    String word = w1+w2+w3+w4+w5+w6+w7+w8+w9+w10+w11+w12;
                    word = word.replaceAll(" ","");

                    tv_title.setText(word);
                    continue;
                }else if(hou_1.equals("2")){
                    shi_autor = accept.get(i).getWord1() + accept.get(i).getWord2() + accept.get(i).getWord3() + accept.get(i).getWord4() + accept.get(i).getWord5() + accept.get(i).getWord6() + accept.get(i).getWord7() + accept.get(i).getWord8() + accept.get(i).getWord9() + accept.get(i).getWord10() + accept.get(i).getWord11() + accept.get(i).getWord12();
                    shi_autors = shi_autor.replaceAll("\"", "");
                    continue;
                }
            }
            String pin1,pin2,pin3,pin4,pin5,pin6,pin7,pin8,pin9,pin10,pin11,pin12;
            String w1,w2,w3,w4,w5,w6,w7,w8,w9,w10,w11,w12;
            pin1 = Constant.getString(accept.get(i).getPin1());
            pin2 = Constant.getString(accept.get(i).getPin2());
            pin3 = Constant.getString(accept.get(i).getPin3());
            pin4 = Constant.getString(accept.get(i).getPin4());
            pin5 = Constant.getString(accept.get(i).getPin5());
            pin6 = Constant.getString(accept.get(i).getPin6());
            pin7 = Constant.getString(accept.get(i).getPin7());
            pin8 = Constant.getString(accept.get(i).getPin8());
            pin9 = Constant.getString(accept.get(i).getPin9());
            pin10 = Constant.getString(accept.get(i).getPin10());
            pin11 = Constant.getString(accept.get(i).getPin11());
            pin12 = Constant.getString(accept.get(i).getPin12());
            w1 = Consant_stringg.getString(accept.get(i).getWord1());
            w2 = Consant_stringg.getString(accept.get(i).getWord2());
            w3 = Consant_stringg.getString(accept.get(i).getWord3());
            w4 = Consant_stringg.getString(accept.get(i).getWord4());
            w5 = Consant_stringg.getString(accept.get(i).getWord5());
            w6 = Consant_stringg.getString(accept.get(i).getWord6());
            w7 = Consant_stringg.getString(accept.get(i).getWord7());
            w8 = Consant_stringg.getString(accept.get(i).getWord8());
            w9 = Consant_stringg.getString(accept.get(i).getWord9());
            w10 = Consant_stringg.getString(accept.get(i).getWord10());
            w11= Consant_stringg.getString(accept.get(i).getWord11());
            w12 = Consant_stringg.getString(accept.get(i).getWord12());
            list_listview.add(new shici_gridview_bean(1,
                    pin1,pin2,pin3,pin4,pin5,pin6,pin7,pin8,pin9,pin10,pin11,pin12,
                    w1,w2,w3,w4,w5,w6,w7,w8,w9,w10,w11,w12
            ));
            lv_stu_shici.setAdapter(new shici_GridviewAdapter(this,list_listview));
        }
        Inti_add();
    }
    private void Initview() {
        tf =  Typeface.createFromAsset(getAssets(), "fonts/youyuan.ttf" );
        tttf =  Typeface.createFromAsset(getAssets(), "ttf.ttf" );
        //初始化控件
        application = (App) getApplication();
        mService = application.getmService();
        tv_stu_autor = (TextView) findViewById(R.id.shici_autor);
        tv_stu_autor.setTypeface(tttf);
        tv_stu_shang = (TextView) findViewById(R.id.shici_shangxi);
        tv_stu_shang.setTypeface(tttf);
        lv_stu_shici = (GridView) findViewById(R.id.shici_listview);
        im_voice = (ImageView) findViewById(R.id.shi_voice);
        img_shangxi = (ImageView) findViewById(R.id.shi_voice_shangxi);
        ima_left = (ImageButton) findViewById(R.id.left);
        tv_shici_Navigation = (AlwaysMarqueeTextView) findViewById(R.id.shici_Navigation);
        tv_shici_Navigation.setTypeface(tf);
        Ggv_title_shi = (GridView) findViewById(R.id.gv_title_shi);
        ln_shangxi = (LinearLayout) findViewById(R.id.lin_jiexi);
        tv_title = (TextView) findViewById(R.id.title_word);
        tv_title.setTypeface(tttf);
        ima_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mService.onDestroy();
                finish();
            }
        });



        tttf =  Typeface.createFromAsset(getAssets(), "ttf.ttf" );
        tv_stu_autor.setTypeface(tttf);
        tv_title.setTypeface(tttf);
        tv_title.getPaint().setFakeBoldText(true);
    }
    //接受服务器的传值  病将它  解析到   list  里边
    private void Inti_jieshou() {
        Intent intent_jeishou =getIntent();
        String jieshou =intent_jeishou.getStringExtra("shici");
        try {
            JSONObject json = new JSONObject(jieshou);
            accept = JSON.parseArray((String) json.get("data"), XiaoXueTuoZhanGuShiCi.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String [] web_lujing = Stitching.get_shiroute();
        web_yuanwen = web_lujing[0];
        web_shangxi = web_lujing[1];
        hou_2 = web_lujing[3];
        INit_bofang();//播放音频路径
        Ini_add_list();
    }

    private void INit_bofang() {
        im_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(xel_specialt_maozedong_shici.this, "正在加载音频..........请等待", Toast.LENGTH_LONG).show();

                mService.onDestroy();
                mService.playMusics(web_yuanwen);
            }
        });
        img_shangxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(xel_specialt_maozedong_shici.this, "正在加载音频..........请等待", Toast.LENGTH_LONG).show();
                mService.onDestroy();

                mService.playMusics(web_shangxi);
            }
        });
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
