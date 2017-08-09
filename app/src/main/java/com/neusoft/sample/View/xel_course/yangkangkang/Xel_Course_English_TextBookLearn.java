package com.neusoft.sample.View.xel_course.yangkangkang;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.ncapdevi.sample.R;
import com.neusoft.sample.App;
import com.neusoft.sample.Ctrl.yangkangkang.TingXieAnswer;
import com.neusoft.sample.Ctrl.yangkangkang.TingXieAnswer_item;
import com.neusoft.sample.Model.Consant_stringg;
import com.neusoft.sample.Model.Constant;
import com.neusoft.sample.Model.Post_learn_rijiyuelei;
import com.neusoft.sample.Model.Stitching;
import com.neusoft.sample.View.Adapter.yangka.TingxieAdapter_eng;
import com.neusoft.sample.View.Adapter.yangka.TingxieAdapter_eng_3;
import com.neusoft.sample.View.AlwaysMarqueeTextView;
import com.neusoft.sample.View.BaseActivity;
import com.neusoft.sample.View.Mp3.MusicPlayService;
import com.neusoft.sample.View.xel_course.Xel_Course_English_Textbooklearn_wordlearn;
import com.neusoft.sample.View.xel_mine.MyHomeWork.get_tingxie;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Xel_Course_English_TextBookLearn extends BaseActivity implements View.OnClickListener {
    private ImageButton img_back;
    private AlwaysMarqueeTextView eng_learn_text;
    private Button U1;
    private Button U2;
    private Button english_slow_btn;
    private Button english_z_btn;
    private Button chinese_slow_btn;
    private Button chinese_z_btn;
    private Button wordlearn_btn,stop;
    Handler handler = new Handler();
    Boolean bool;
    int eng_chi = 0;
    private TextView tv_title;
    /*
    * 定义拼接音频图片webview的内容
    * */
    String ke_id = "";//教材编号
    String ke_name = "";//教材的名称
    String ke_zhang = "";
    String num = "";
    String num_beidanci = "";//背单词的请求的id
    WebView webView_ui;
    //假的音频文件地址   假的内容
    String url_ui1_yuyin="";//英语原文UI1语音
    String url_ui2_yuyin="";//英语原文UI2语音
    String url_ui1_web = "";//英语原文webwiew1
    String url_ui2_web = "";//英语原文webwiew2
    String url_ui="";
    String url_eng_man = "";
    String url_eng_zhong = "";
    String url_zh_man = "";
    String url_zh_zhong = "";
    /*听写答案 */
    LinearLayout lin_eng_tngxie,lin_eng_zhong;//听写答案的显示与隐藏的布局
    ListView list_eng_tingxie;//显示答案的listview
    HashMap<String, String> map;//向服务器请求听写答案的map
    String urll;//请求听写答案的接口
    Boolean succ = false;//判断是否请求成功听写答案

    List<TingXieAnswer> tingxie ;//承接听写答案的实体
    
    //播放音音频private App application;
    private App application;
    private TextView  tv_curcentTime, tv_allTime,tv_eng_congtent;
    private SeekBar seekBar1;// 播放进度条
    private MusicPlayService mService;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xel_course_english_textbooklearn);
        bool = Consant_stringg.is_internet(this);
        application = (App) getApplication();
        mService = application.getmService();
        img_back = (ImageButton) findViewById(R.id.img_back);
        eng_learn_text = (AlwaysMarqueeTextView) findViewById(R.id.course_eng_learn);
        eng_learn_text.setText("课程>英语>课本学习>"+ke_name);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mService.onDestroy();
                handler.removeCallbacks(updateThread);
                finish();
            }
        });
        Init_accept();
        initView();
        setListener();
    }

    /*
    * 解析接受过来的的教材编号  和名称*/
    private void Init_accept() {
        Intent  acc_intent= getIntent();
         num = acc_intent.getStringExtra("ke_id");
        num_beidanci = num+"00";
        String name = acc_intent.getStringExtra("ke_name");
        ke_id = num.substring(0,5);
        ke_zhang = num.substring(num.length()-2,num.length());
        ke_name = name;
        eng_learn_text.setText("课程>英语>课本学习>"+ke_name);
        INi_pinjie();
    }
    /*
    * 拼接资源文件路径
    * */
    private void INi_pinjie() {
        String [] getRoute = Stitching.getRoute(num);
        url_ui1_web = getRoute[0];//英文UI1web
        url_ui2_web = getRoute[1];//英文UI2web
        url_ui1_yuyin = getRoute[2];//英文UI1语音
        url_ui2_yuyin =getRoute[3];//英文UI2语音
        Log.d("@@","路径"+url_ui1_yuyin+"2"+url_ui2_yuyin);
        url_eng_man =getRoute[4];//英文慢速
        url_eng_zhong = getRoute[5];//英文中速
        url_zh_man =getRoute[6];//中文慢速
        url_zh_zhong = getRoute[7];//中文中速
    }
    private void initView() {
        map = new HashMap<>();//初始化   用于保存请听写答案的 map
        U1 = (Button) findViewById(R.id.U1);
        U1.setOnClickListener(this);
        U2 = (Button) findViewById(R.id.U2);
        U2.setOnClickListener(this);
       // tv_eng_congtent = (TextView) findViewById(R.id.tv_eng_content);
        english_slow_btn = (Button) findViewById(R.id.english_slow_btn);
        english_slow_btn.setOnClickListener(this);
        english_z_btn = (Button) findViewById(R.id.english_z_btn);
        english_z_btn.setOnClickListener(this);
        chinese_slow_btn = (Button) findViewById(R.id.chinese_slow_btn);
        chinese_slow_btn.setOnClickListener(this);
        chinese_z_btn = (Button) findViewById(R.id.chinese_z_btn);
        chinese_z_btn.setOnClickListener(this);
        wordlearn_btn = (Button) findViewById(R.id.wordlearn_btn);
        wordlearn_btn.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.eng_title);
        tv_title.setText(ke_name);
        webView_ui = (WebView) findViewById(R.id.ui_web);
        stop = (Button) findViewById(R.id.stop_eng_btn);
        //音频初始化
        seekBar1 = (SeekBar) findViewById(R.id.seekBar1_eng);
        tv_curcentTime = (TextView) findViewById(R.id.tv_curcentTime_eng_);
        tv_allTime = (TextView) findViewById(R.id.tv_allTime_eng);
        /*听写初始化*/
        lin_eng_tngxie = (LinearLayout) findViewById(R.id.eng_tingxie);
        lin_eng_zhong = (LinearLayout) findViewById(R.id.eng_tingxie_zhong);
        list_eng_tingxie = (ListView) findViewById(R.id.eng_listview);
// 启动
        
    }

    private void allbtn_defaultColor() {
//        Drawable drawable = getDrawable(R.drawable.red_btn_textbooklearn);
        Drawable drawable= ContextCompat.getDrawable(this, R.drawable.red_btn_textbooklearn);
        U1.setBackground(drawable);
        U2.setBackground(drawable);
        chinese_slow_btn.setBackground(drawable);
        chinese_z_btn.setBackground(drawable);
        english_slow_btn.setBackground(drawable);
        english_z_btn.setBackground(drawable);
        wordlearn_btn.setBackground(drawable);
    }
    /**
     * u1,u2,慢速英语等的点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        allbtn_defaultColor();
        Drawable drawable= ContextCompat.getDrawable(this, R.drawable.red_btn_textbooklearn_change);
        switch (v.getId()) {
            case R.id.U1:
                webView_ui.setVisibility(View.VISIBLE);
                lin_eng_tngxie.setVisibility(View.GONE);
                lin_eng_zhong.setVisibility(View.GONE);
                list_eng_tingxie.setVisibility(View.GONE);
                Toast.makeText(Xel_Course_English_TextBookLearn.this, "正在加载音频，请等待.........", Toast.LENGTH_SHORT).show();
                U1.setBackground(drawable);
                handler.removeCallbacks(updateThread);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mService.onDestroy();
                        mService.playMusic(url_ui1_yuyin);
                        handler.post(updateThread);
                       
                        
                    }
                }).start();
                Log.d("@@","web"+url_ui);
                url_ui = url_ui1_web;
                webView_ui.loadUrl(url_ui);
                webView_ui.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
               
                break;
            case R.id.U2:
                webView_ui.setVisibility(View.VISIBLE);
                lin_eng_tngxie.setVisibility(View.GONE);
                lin_eng_zhong.setVisibility(View.GONE);
                list_eng_tingxie.setVisibility(View.GONE);
                Toast.makeText(Xel_Course_English_TextBookLearn.this, "正在加载音频，请等待.........", Toast.LENGTH_SHORT).show();
                U2.setBackground(drawable);
                handler.removeCallbacks(updateThread);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mService.onDestroy();
                        mService.playMusic(url_ui2_yuyin);
                        handler.post(updateThread);
                    }
                }).start();
                url_ui = url_ui2_web;
                webView_ui.loadUrl(url_ui);
                webView_ui.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
              
                break;
            case R.id.english_slow_btn:
                webView_ui.setVisibility(View.GONE);
                lin_eng_tngxie.setVisibility(View.VISIBLE);
                lin_eng_zhong.setVisibility(View.GONE);
                list_eng_tingxie.setVisibility(View.GONE);
                Toast.makeText(Xel_Course_English_TextBookLearn.this, "正在加载音频，请等待.........", Toast.LENGTH_SHORT).show();
                english_slow_btn.setBackground(drawable);
                handler.removeCallbacks(updateThread);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mService.onDestroy();
                        mService.playMusic(url_eng_man);
                        handler.post(updateThread);
                    }
                }).start();
                map.put("No",num+"0");//向map  赋值
                urll = Constant.post_english_danci;//赋值请求生子听写答案接口路径
                Init_post_tingxie(1);//向后台请求听写答案
                eng_chi = 0;
                break;
            case R.id.english_z_btn:
                webView_ui.setVisibility(View.GONE);
                lin_eng_tngxie.setVisibility(View.VISIBLE);
                lin_eng_zhong.setVisibility(View.GONE);
                list_eng_tingxie.setVisibility(View.GONE);
                english_z_btn.setBackground(drawable);
                Toast.makeText(Xel_Course_English_TextBookLearn.this, "正在加载音频，请等待.........", Toast.LENGTH_SHORT).show();
                handler.removeCallbacks(updateThread);
             new Thread(new Runnable() {
                 @Override
                 public void run() {
                     mService.onDestroy();
                     mService.playMusic(url_eng_zhong);
                     handler.post(updateThread);
                 }
             }).start();
                map.put("No",num+"1");//向map  赋值
                urll = Constant.post_english_danci;//赋值请求生子听写答案接口路径
                eng_chi = 0;
                Init_post_tingxie(1);//向后台请求听写答案
//                
                break;
            case R.id.chinese_slow_btn:
                webView_ui.setVisibility(View.GONE);
                lin_eng_tngxie.setVisibility(View.GONE);
                lin_eng_zhong.setVisibility(View.VISIBLE);
                list_eng_tingxie.setVisibility(View.GONE);
                chinese_slow_btn.setBackground(drawable);
                handler.removeCallbacks(updateThread);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mService.onDestroy();
                        mService.playMusic(url_zh_man);
                        handler.post(updateThread);
                    }
                }).start();
                map.put("No",num+"0");//向map  赋值
                urll = Constant.post_english_zhongwen;//赋值请求生子听写答案接口路径
                eng_chi = 1;
                Init_post_tingxie(2);//向后台请求听写答案
                Toast.makeText(Xel_Course_English_TextBookLearn.this, "正在加载音频，请等待.........", Toast.LENGTH_SHORT).show();
                break;
            case R.id.chinese_z_btn:
                webView_ui.setVisibility(View.GONE);
                lin_eng_tngxie.setVisibility(View.GONE);
                lin_eng_zhong.setVisibility(View.VISIBLE);
                list_eng_tingxie.setVisibility(View.GONE);
                chinese_z_btn.setBackground(drawable);
                handler.removeCallbacks(updateThread);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mService.onDestroy();
                        mService.playMusic(url_zh_zhong);
                        handler.post(updateThread);
                    }
                }).start();
                map.put("No",num+"1");//向map  赋值
                urll = Constant.post_english_zhongwen;//赋值请求生子听写答案接口路径
                eng_chi = 1;
                Init_post_tingxie(2);//向后台请求听写答案
                Toast.makeText(Xel_Course_English_TextBookLearn.this, "正在加载音频，请等待.........", Toast.LENGTH_SHORT).show();
                break;
           case R.id.wordlearn_btn:
                Stitching.post_Chapter(num);
                handler.removeCallbacks(updateThread);
                mService.onDestroy();
                wordlearn_btn.setBackground(drawable);
                if(bool == false){
                    Toast.makeText(Xel_Course_English_TextBookLearn.this, "请检查您的网络设置", Toast.LENGTH_SHORT).show();
                }else {
                    final HashMap<String, String> map = new HashMap<>();
                    map.put("No", num_beidanci);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String zhi = Post_learn_rijiyuelei.getStringCha(Constant.post_Learn_rijiyuelei, map);
                                Intent intent_eng_word = new Intent(Xel_Course_English_TextBookLearn.this, Xel_Course_English_Textbooklearn_wordlearn.class);
                                intent_eng_word.putExtra("ke_id",ke_id);
                                intent_eng_word.putExtra("zhi", zhi);
                                intent_eng_word.putExtra("ke_name",ke_name);
                                startActivity(intent_eng_word);
                            } catch (IOException e) {
                            }
                        }
                    }).start();
                }
                break;
                //TODO
                /**
                 * 做页面跳转
                 * 到单词学习
                 * Intent
                 * or
                 * do something
                 */
        }
    }
    
    Runnable updateThread = new Runnable() {
        public void run() {
            // 获得歌曲的长度并设置成播放进度条的最大值
            seekBar1.setMax(mService.getDuration());
            // 获得歌曲现在播放位置并设置成播放进度条的值
            seekBar1.setProgress(mService.getCurrent());
            tv_curcentTime.setText(formatTime(mService.getCurrent()));
            tv_allTime.setText(formatTime(mService.getDuration()));
            // 每次延迟100毫秒再启动线程
            /*调用显示 听写答案的 方法*/
            if((mService.getDuration()-mService.getCurrent())<50){
                //判断当  播放时间 == 总时间的时候
                Ini_update_tingxieview();/*调用显示 听写答案的 方法*/
            }
            handler.postDelayed(updateThread, 100);
        }
    };
   
    private void setListener() {
        // 暂停or开始
        stop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mService.pausePlay();
                if (mService.isPlay()) {
                    //改变  暂停按钮的  样式  和显示的字
                    stop.setText("暂停");

                } else {
                    //改变  暂停按钮的  样式  和显示的字
                    stop.setText("继续");
                }
            }
        });
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // fromUser判断是用户改变的滑块的值
                if (fromUser == true) {
                    mService.movePlay(progress);
                }
            }
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
    /**
     * 格式化时间，将其变成00:00的形式
     */
    public String formatTime(int time) {
        int secondSum = time / 1000;
        int minute = secondSum / 60;
        int second = secondSum % 60;

        String result = "";
        if (minute < 10)
            result = "0";
        result = result + minute + ":";
        if (second < 10)
            result = result + "0";
        result = result + second;
        return result;
    }
    /**
     * 重写onKeyDown方法可以拦截系统默认的处理
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            handler.removeCallbacks(updateThread);
            mService.onDestroy();
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    /*
   * 向后台请求听写答案
   * */
    public void Init_post_tingxie(int a) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("@@","map"+map+urll);
                    String get_tingxie = Post_learn_rijiyuelei.getStringCha(urll,map);
                    JSONObject json = new JSONObject(get_tingxie);
                    if(json.getString("success").equals("200")){
                        succ = true;
                        tingxie = JSON.parseArray(String.valueOf(json.get("data")),TingXieAnswer.class);
                       Log.d("@@","json"+json);
                    }else {
                        succ = false;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    /*更新听写页面的   view*/
    private void Ini_update_tingxieview() {
        if(succ) {
            succ = false;
            if (!((get_tingxie.a).equals("1"))) {
                Log.d("@@", " 播放完毕");
                List<TingXieAnswer_item> list_item = new ArrayList<>();
                Collections.sort(tingxie);
                list_eng_tingxie.setVisibility(View.VISIBLE);
                for (int i = 0; i < tingxie.size(); i++) {
                    //循环list
                    String[] bb = tingxie.get(i).get_tingxie();
                    list_item.add(new TingXieAnswer_item(bb));
                }
                if(eng_chi == 0) {
                    list_eng_tingxie.setAdapter(new TingxieAdapter_eng(this, list_item));
                }else {
                    list_eng_tingxie.setAdapter(new TingxieAdapter_eng_3(this, list_item));
                }
            } else {
                Toast.makeText(Xel_Course_English_TextBookLearn.this, "当前答案不允许查看", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
