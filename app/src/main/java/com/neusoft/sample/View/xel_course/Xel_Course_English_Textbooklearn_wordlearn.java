package com.neusoft.sample.View.xel_course;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.ncapdevi.sample.R;
import com.neusoft.sample.App;
import com.neusoft.sample.Ctrl.yangkangkang.EnglishJinYiCi;
import com.neusoft.sample.Ctrl.yangkangkang.EnglishWordStudy_bean;
import com.neusoft.sample.Model.Consant_stringg;
import com.neusoft.sample.Model.Stitching;
import com.neusoft.sample.View.AlwaysMarqueeTextView;
import com.neusoft.sample.View.BaseActivity;
import com.neusoft.sample.View.Mp3.MusicPlayService;
import com.neusoft.sample.View.xel_course.yangkangkang.xel_idom_bianxi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/6/27.
 */
public class Xel_Course_English_Textbooklearn_wordlearn extends BaseActivity {
    String jinyici;
    String fanyici;
    EnglishJinYiCi eng_list;
    //定义控件
    // tv_word_body_yin代表的是英语的单词主体  tv_word_body_yin代表的是音标  tv_ke_cixing 代表的课本里的词性
    // tv_tuo_cixing 代表的拓展里的词性  tv_wo1代表的单词++里的第一个单词   tv_wo1_shi第一个单词的解释
    //tv_ci 代表的是短语1
    private TextView tv_word_body,tv_word_body_yin,tv_ke_cixing,tv_ke_ciyi,tv_ke_liju,tv_ke_liju_shi,
            tv_tuo_cixing,tv_tuo_ci,tv_tuo_liju,tv_tuo_liju_shi,tv_wo1,tv_wo2,
             tv_wo3,tv_ci1,tv_ci2,tv_ci3,tv_ci4,tv_ke_ci,
            tv_ci5,tv_ci6,tv_ci7,tv_ci8,tv_zhangzu,tv_jin,tv_fan;
    private AlwaysMarqueeTextView tv_title;
    //imgv_ke_tu  这是课本里有图片 img_left_1上一个单词 img_right_1下一个单词 img_voice音频spi_eng_word 单词的下拉列表
    private ImageView imgv_ke_tu,imgv_tuo_tu,img_left_1,img_right_1,img_voice;
    private Spinner spi_eng_word;
    Bitmap bitmap;///缓存网络图片
    private  String ke_id = "";//课本教材id
    private  String ke_id_zhang = "";//英语背单词后两位章
    private  String url_tu="";//图片路径
    private  String url_yin="";//音频路径
    String png = ".png";
    String ke_name = "";//章节名称
    String word = "";//获取下来的主单词
    String word_cixing_ = "";//获取下来的主单词词性
    //定义lindliayout  用于设置它的隐藏于显示
    private LinearLayout lin_tuozhan,lin_wo,lin_duanyu,lin_jin,lin_fan;
    //定义存储单词的实体
    List<EnglishWordStudy_bean> accept;
    //音频的引用
    private MusicPlayService mService;
    private App application;
    //定义上下单词的控制数量
    int word_num = 0;
    Button btn_bianxi,btn_bianxi1;
    ImageButton left_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xel_course_english_textbooklearn_wordlearn);
        IntView();//初始化定义
        Inivi_accept();
        //显示内容
        Ini_acceept_id();//接受传递过来的id
        Ini_view_num();
    }
    /*
    * 接收传递过来的id*/
    private void Ini_acceept_id() {
        Intent accept_id = getIntent();
        ke_id = accept_id.getStringExtra("ke_id");
        ke_name = accept_id.getStringExtra("ke_name");
        Log.d("@@@","单词id"+ke_id);
        ke_id_zhang = ke_id.substring(ke_id.length()-2,ke_id.length());
        tv_zhangzu.setText(ke_name);
        tv_title.setText("课程>英语>单词学习>"+ke_name);
    }
    /*
    * 拼接图片路径*/
    private void Ini_pinjie() {
        String tupian1 = "";
        if(word_cixing_ == null || word_cixing_.equals("") || word_cixing_== "") {
            Log.d("@@","打印空值"+word_cixing_);
        }else {
            tupian1 = (word_cixing_.substring(0, word_cixing_.length() - 1)) + "1";
            String tupian2 =( word_cixing_.substring(0,word_cixing_.length()-1))+"2";
        }
        String url[] = Stitching.get_word_learn(ke_id);
        url_tu = url[0]+word+"%20"+tupian1+png;
       // url_tu = url[0]+"04.jpg";
        url_yin = url[1]+accept.get(word_num).getSound()+".mp3";

        Log.d("@@","音频路径"+url_yin+"图"+url_tu);
    }

    public void english_break_1(View view){
        finish();
    }
    //接受穿过来的  值  并且将它解析以后存入实体中
    private void Inivi_accept() {
        Intent intent_accept = getIntent();
        String zhi = intent_accept.getStringExtra("zhi");
        try {
            JSONObject json = new JSONObject(zhi);
            String success =  json.getString("success");
            if(success.equals("200")) {
                accept = JSON.parseArray((String) json.get("data"), EnglishWordStudy_bean.class);
            } //排序
            else{
                finish();
                Toast.makeText(this, "当前目录无资源！", Toast.LENGTH_SHORT).show();
            }
            Collections.sort(accept);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    ///////////////////////用于写显示的逻辑和算法////////////////////////////
    private void Ini_view_num() {
        Ini_xianshi();
        //上下单词按钮
        img_left_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(word_num ==0){
                    Toast.makeText(Xel_Course_English_Textbooklearn_wordlearn.this, "这是本组第一个单词", Toast.LENGTH_SHORT).show();
                }
                else {
                    word_num--;
                    Ini_xianshi();
                }
            }
        });
        img_right_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(word_num ==(accept.size()-1)){
                    Toast.makeText(Xel_Course_English_Textbooklearn_wordlearn.this, "已经是本组最后一个单词", Toast.LENGTH_SHORT).show();
                }
                else {

                    word_num++;
                    Ini_xianshi();
                }
            }
        });
        ///////////////////////下面是spinner   中的内容  /////////////////////////////////////
        List<String> spinner_list_eng = new ArrayList<>();
        for(int i =0;i<accept.size();i++){
            spinner_list_eng.add(accept.get(i).getWord());
        }
        ArrayAdapter eng_adapter =
                new ArrayAdapter(Xel_Course_English_Textbooklearn_wordlearn.this,R.layout.xel_spiner_custom_view,spinner_list_eng);
        spi_eng_word.setAdapter(eng_adapter);
        //为  spinner   列表 加点击事件
        spi_eng_word.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
             String num_word = accept.get(position).getItemNo();
                int num_word_ = Integer.parseInt((num_word.substring(num_word.length() - 2)));
                word_num = num_word_-1;
                Ini_xianshi();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        ///////////////////////下面是spinner   中的内容  /////////////////////////////////////
    }
    ////////////下面是展示在xml文件中的    settext///////////////////////////////////////////
    private void Ini_xianshi() {
        mService.onDestroy();
       Boolean wordPicture1 = accept.get(word_num).isWordPicture1();
        Log.d("@@@",""+wordPicture1);
        //主单词
        word = accept.get(word_num).getWord();
        //音标
        String word_sound;
        word_sound =accept.get(word_num).getSoundmark();
        //课本主单词释义词性
        String word_cixing;
        word_cixing = accept.get(word_num).getKb_paraphrase();
        word_cixing_ = word_cixing;
        Log.d("@@","打印词性"+word_cixing);
        if(!(word_cixing == null)) {
            word_cixing = word_cixing.replaceAll("1", ".");
            word_cixing = word_cixing.replaceAll("2", ".");
            word_cixing = word_cixing.replaceAll("0", ".");
            word_cixing = word_cixing.replaceAll("3", ".");
            word_cixing = word_cixing.replaceAll("4", ".");
        }
        Log.d("@@","课本单词释义"+word_cixing_);
        //课本单词释义
        String word_shi;
        word_shi = accept.get(word_num).getTranslate();
        //课本例句，例句译文
        String ke_liju,ke_liju_shi;
        Ini_pinjie();//拼接路径
        
        ke_liju = accept.get(word_num).getTextbook_eg();
        ke_liju_shi = accept.get(word_num).getTranslation();
        //拓展类
        String tuo_cixing,tuo_shiyi,tuo_liju,tuo_liju_shi;
        tuo_cixing = (accept.get(word_num).getTz_paraphrase());
        tuo_shiyi = (accept.get(word_num).getTuozhanshiyi());
        tuo_liju = (accept.get(word_num).getTuozhanshiyi_eg());
        tuo_liju_shi = (accept.get(word_num).getTuozhan_translate());
        //单词++
        String wo1,wo2,wo3;
        wo1=(accept.get(word_num).getWord1());
        wo2=(accept.get(word_num).getWord2());
        wo3 = (accept.get(word_num).getWord3());
        //英语短语学习
        String c1, c2, c3, c4, c5,c6,c7,c8,c9;
        c1 = (accept.get(word_num).getPhraseStudy1());
        c2 = (accept.get(word_num).getPhraseStudy2());
        c3 = (accept.get(word_num).getPhraseStudy3());
        c4 = (accept.get(word_num).getPhraseStudy4());
        c5 = (accept.get(word_num).getPhraseStudy5());
        c6 = (accept.get(word_num).getPhraseStudy6());
        c7 = (accept.get(word_num).getPhraseStudy7());
        c8 = (accept.get(word_num).getPhraseStudy8());
        c9 = (accept.get(word_num).getPhraseStudy9());
        //近义词反义词

        //判断是否显示   单词++和   短语学习的lindlayout 是否显示//////////
        if(wo1==null&&wo2==null&&wo3==null){
            lin_wo.setVisibility(View.GONE);
        }
        else {
            lin_wo.setVisibility(View.VISIBLE);
        }
        if (accept.get(word_num).getTuozhan_translate()==null){
            lin_tuozhan.setVisibility(View.GONE);
        }
        else {
            lin_tuozhan.setVisibility(View.VISIBLE);
        }
        if(accept.get(word_num).getPhraseStudy1()==null){
            lin_duanyu.setVisibility(View.GONE);
        }else {
            lin_duanyu.setVisibility(View.VISIBLE);
        }
        if(!accept.get(word_num).isWordPicture1()){
            imgv_ke_tu.setVisibility(View.GONE);
        }else {
            imgv_ke_tu.setVisibility(View.VISIBLE);
            Toast.makeText(this, "小乐正在加载图片请稍等！", Toast.LENGTH_SHORT).show();
        }
        //////////接下来是展示层//////////////
        //课本类
        tv_word_body.setText(word);
        tv_word_body_yin.setText(word_sound);
        tv_ke_cixing.getPaint().setTypeface(Typeface.DEFAULT);
        tv_ke_cixing.setText(word_cixing);
        tv_ke_ci.setText(word);
        tv_ke_ciyi.setText(word_shi);
        tv_ke_liju.getPaint().setTypeface(Typeface.DEFAULT);
        tv_ke_liju.setText(ke_liju);
        tv_ke_liju_shi.setText(ke_liju_shi);
        //  拓展类
            tv_tuo_ci.setText(tuo_shiyi);tv_tuo_cixing.setText(tuo_cixing);tv_tuo_liju.setText(tuo_liju);tv_tuo_liju_shi.setText(tuo_liju_shi);
        //单词++
            tv_wo1.setText(wo1);tv_wo2.setText(wo2);tv_wo3.setText(wo3);
        //短语学习类
            tv_ci1.setText(c1);tv_ci2.setText(c2);tv_ci3.setText(c3);tv_ci4.setText(c4);tv_ci5.setText(c5);tv_ci6.setText(c6);tv_ci7.setText(c7);
       //近义词 
        if(accept.get(word_num).isPDF()){
            lin_jin.setVisibility(View.VISIBLE);

            tv_jin.getPaint().setTypeface(Typeface.DEFAULT);
            tv_jin.setText(accept.get(word_num).getSynonym());
            btn_bianxi1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mService.onDestroy();
                    Intent intent = new Intent(Xel_Course_English_Textbooklearn_wordlearn.this,xel_idom_bianxi.class);
                    Consant_stringg.set_bianxi(null,word,accept.get(word_num).getPdfName(),0);
                    startActivity(intent);
                }
            });  
        }else {
            lin_jin.setVisibility(View.GONE);
        }
        /*语音*/
        img_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("@@","aa"+accept.get(word_num));
                Toast.makeText(Xel_Course_English_Textbooklearn_wordlearn.this, "小乐正在为您加载音频，请稍等！", Toast.LENGTH_SHORT).show();
                mService.playMusics(url_yin);
                /*accept.get(word_num).getSound();*/
               /* if(!accept.get(word_num).getSound().equals("")) {

                }
                else {
                    Toast.makeText(Xel_Course_English_Textbooklearn_wordlearn.this, "此单词没有语音", Toast.LENGTH_SHORT).show();
                }*/
            }
        });
        /*
        * 加载网络图片的线程处理
        * */
        new Thread(new Runnable() {
            @Override
            public void run() {
               try {
                    Log.d("@@","图片路径"+url_tu);
                    URL url = new URL(url_tu);//path图片的网络地址
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                        bitmap  = BitmapFactory.decodeStream(httpURLConnection.getInputStream());
                        imgv_ke_tu.post(runnableUi);
                        Log.d("@@","if"+url_tu);
                    }else{
                        Log.d("@@","else"+url_tu);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                   Log.d("@@","catch"+url_tu);
                }
            }
        }).start();
    }
    /*
    * 用于异步更新网络图片的操作*/
    Runnable   runnableUi=new  Runnable() {
        @Override
        public void run() {
            Log.d("@@","rundable径"+url_tu);
            imgv_ke_tu.setImageBitmap(bitmap);//加载到ImageView上
        }
    };

    ///控件初始化
    @TargetApi(Build.VERSION_CODES.M)
    private void IntView() {
       // 初始化音频播放器
        application = (App) getApplication();
        mService = application.getmService();
        tv_title = (AlwaysMarqueeTextView) findViewById(R.id.eng_word_learn_navtitle);
        //控件初始化
        tv_word_body = (TextView) findViewById(R.id.eng_word);

        /*字体变黑体加错*/
        tv_word_body.getPaint().setFakeBoldText(true);
        tv_word_body.getPaint().setTypeface(Typeface.SANS_SERIF);

        tv_word_body_yin = (TextView) findViewById(R.id.eng_word_yin);

        tv_word_body_yin.getPaint().setTypeface(Typeface.SANS_SERIF);
        tv_ke_cixing = (TextView) findViewById(R.id.ke_cixing);
        tv_ke_ci = (TextView) findViewById(R.id.ke_ci);
        tv_ke_ciyi = (TextView) findViewById(R.id.ke_ciyi);
        tv_ke_liju = (TextView) findViewById(R.id.ke_liju);
        tv_ke_liju_shi = (TextView) findViewById(R.id.ke_liju_shi);
        tv_tuo_cixing = (TextView) findViewById(R.id.tuo_cixing);
        tv_tuo_ci = (TextView) findViewById(R.id.tuo_ci);
        tv_tuo_liju = (TextView) findViewById(R.id.tuo_liju);
        tv_tuo_liju_shi = (TextView) findViewById(R.id.tuo_liju_shi);
        tv_wo1 = (TextView) findViewById(R.id.eng_wo1);
        tv_wo2 = (TextView) findViewById(R.id.eng_wo2);
        tv_wo3 = (TextView) findViewById(R.id.eng_wo3);
        tv_ci2 = (TextView) findViewById(R.id.eng_ci2);
        tv_ci3 = (TextView) findViewById(R.id.eng_ci3);
        tv_ci4 = (TextView) findViewById(R.id.eng_ci4);
        tv_ci5 = (TextView) findViewById(R.id.eng_ci5);
        tv_ci6 = (TextView) findViewById(R.id.eng_ci6);
        tv_ci7 = (TextView) findViewById(R.id.eng_ci7);
        tv_ci1 = (TextView) findViewById(R.id.eng_ci1);
        tv_zhangzu = (TextView) findViewById(R.id.eng_word_learn_zhangzu);
        tv_zhangzu.getPaint().setFakeBoldText(true);
        tv_zhangzu.getPaint().setTypeface(Typeface.SANS_SERIF);
        img_left_1 = (ImageView) findViewById(R.id.left_1);
        img_right_1 = (ImageView) findViewById(R.id.right_1);
        img_voice = (ImageView) findViewById(R.id.eng_word_voice);
        imgv_ke_tu = (ImageView) findViewById(R.id.ke_eng_tu);//网络加载的图片
        imgv_tuo_tu = (ImageView) findViewById(R.id.tuo_eng_tu);
        spi_eng_word = (Spinner) findViewById(R.id.eng_spi);
        tv_jin = (TextView) findViewById(R.id.eng_jin1);
        lin_jin = (LinearLayout) findViewById(R.id.eng_jin);


        left_btn = (ImageButton) findViewById(R.id.leftbutton);
        btn_bianxi1 = (Button) findViewById(R.id.jin);
        //用于初始化  显示的布局并且将它设置为不可见
        lin_tuozhan = (LinearLayout) findViewById(R.id.eng_tuozhan);
        lin_tuozhan.setVisibility(View.INVISIBLE);
        lin_wo = (LinearLayout) findViewById(R.id.eng_wo);
        lin_wo.setVisibility(View.INVISIBLE);
        lin_duanyu = (LinearLayout) findViewById(R.id.eng_duanyu);
        lin_duanyu.setVisibility(View.INVISIBLE);
        
        
        left_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mService.onDestroy();
                finish();
            }
        });
    }
    /**
     * 重写onKeyDown方法可以拦截系统默认的处理
     */
    @Override
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
