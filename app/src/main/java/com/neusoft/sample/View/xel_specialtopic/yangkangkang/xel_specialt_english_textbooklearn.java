package com.neusoft.sample.View.xel_specialtopic.yangkangkang;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
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
import com.neusoft.sample.Model.Constant;
import com.neusoft.sample.Model.Post_learn_rijiyuelei;
import com.neusoft.sample.Model.Stitching;
import com.neusoft.sample.View.Adapter.yangka.TingxieAdapter_eng;
import com.neusoft.sample.View.Adapter.yangka.TingxieAdapter_eng_3;
import com.neusoft.sample.View.AlwaysMarqueeTextView;
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

public class xel_specialt_english_textbooklearn extends AppCompatActivity implements View.OnClickListener {
    //定义接受传递过来的值
    String teach_num = "";//教材编号
    String class_name  = "";//课名称
    String teach_num7 = "";//截取教材编号的前7位
    String eng_kewen = "";//新概念课文语音
    String eng_eng_lis = "";//新概念英语听写
    String chi_eng_lis = "";//新概念中文听写
    String url_ui_web= "";//新概念课本内容webcview
    String num_beidanci = "";//请求背单词编号
    //播放音音频private App application;
    private App application;//绑定后台开启sercvice
    private MusicPlayService mService;//播放音频工具类
    ////////////////////////////////
    //定义进度条
    private TextView tv_curcentTime, tv_allTime;//播放时间和总时间

    private SeekBar seekBar1;// 播放进度条
    int eng_chi = 0;

/////////////
    String acc_nmu;
    //定义初始化控件用于
    ImageButton btn_back;//返回按钮
    AlwaysMarqueeTextView tv_title_spe;//导行栏
    TextView tv_title;
    Button stop;//暂停按钮
    Button btn_red_eng;//课文原文
    Button btn_chi_ting;//中文听写
    Button btn_eng_ting;//英语听写
    Button btn_worn_learn;//单词学习
    /*听写答案 */
    LinearLayout lin_eng_tngxie,lin_eng_zhong;//听写答案的显示与隐藏的布局
    ListView list_eng_tingxie;//显示答案的listview
    HashMap<String, String> map;//向服务器请求听写答案的map
    String urll;//请求听写答案的接口
    Boolean succ=false;//判断是否请求成功听写答案
    List<TingXieAnswer> tingxie ;//承接听写答案的实体
    
    Boolean bool;//判断网络是否有网络
    private WebView web_Ui;//web
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xel_specialt_english_textbook);
        ////////////////////////
        initView();//初始化各种控件
        IN_accept();//处理接受的值
        INI_lujing();//处理传递过来的拼接路径
        setListener();
    }
/*处理传递过来的拼接路径*/
    private void INI_lujing() {
        String  []lujing = Stitching.getRoute(acc_nmu);
        url_ui_web = lujing[0];
        eng_kewen = lujing[1];
        eng_eng_lis = lujing[2];
        chi_eng_lis = lujing[3];
        Log.d("@@","打印路径新概念"+url_ui_web+eng_kewen+eng_eng_lis+chi_eng_lis);
    }

    /////////////////////////////////接受穿过来的  教材ID////////////////////////
    private void IN_accept() {
        Intent accept_mum = getIntent();
         acc_nmu =  accept_mum.getStringExtra("ke_id");
        Log.d("@@","编号"+acc_nmu);
        num_beidanci = acc_nmu+"00";
        Stitching.post_Chapter(acc_nmu);//将教材编号传递过去进行路径拼接
        String acc_name = accept_mum.getStringExtra("ke_name");
        Log.d("@@","编号"+acc_name);
        class_name = acc_name;
        teach_num = acc_nmu.substring(0,5);//截取教材id
        teach_num7 = acc_nmu.substring(0,acc_nmu.length()-2);
        String hou_2 = acc_nmu.substring(acc_nmu.length()-2,acc_nmu.length());
        Log.d("@@@","截取的前五位"+teach_num);
        tv_title_spe.setText("专题>英语>新概念>"+""+hou_2+"章");
        tv_title.setText(class_name);
       
        //////////////////拼接音频路径//////////////////////////////
    }
    /*
    * 初始化控件*/
    public void break_1(View view){
        finish();
    }
    //初始化控件
    private void initView() {
        map = new HashMap<>();//初始化   用于保存请听写答案的 map
        //初始化播放音频的  工具类
        application = (App) getApplication();
        mService = application.getmService();
        //音频控制按钮初始化
        seekBar1 = (SeekBar) findViewById(R.id.seekBar1_eng_spe);
        tv_curcentTime = (TextView) findViewById(R.id.tv_curcentTime_eng_spe);
        tv_allTime = (TextView) findViewById(R.id.tv_allTime_eng_spe);
        //初始化按钮
        stop= (Button) findViewById(R.id.stop_eng_spe_btn);
        btn_red_eng = (Button) findViewById(R.id.read_eng_spe);
        btn_eng_ting = (Button) findViewById(R.id.eng_ting_spe);
        btn_chi_ting = (Button) findViewById(R.id.zhong_ting_spe);
        btn_worn_learn = (Button) findViewById(R.id.word_learn_spe);
        web_Ui = (WebView) findViewById(R.id.eng_spe_web);
        btn_back = (ImageButton) findViewById(R.id.spe_back_1);
        tv_title_spe = (AlwaysMarqueeTextView) findViewById(R.id.title_spe);
        tv_title = (TextView) findViewById(R.id.tv_ke_eng_spe);
         /*听写初始化*/
        lin_eng_tngxie = (LinearLayout) findViewById(R.id.spe_eng_tingxie);
        lin_eng_zhong = (LinearLayout) findViewById(R.id.spe_eng_tingxie_zhong);
        list_eng_tingxie = (ListView) findViewById(R.id.spe_eng_listview);
        //设置按钮初始按钮颜色
        btn_red_eng.setOnClickListener(this);
        btn_eng_ting.setOnClickListener(this);
        btn_chi_ting.setOnClickListener(this);
        btn_worn_learn.setOnClickListener(this);
        btn_back.setOnClickListener(this);

    }
    //用于控制电机前的颜色的额
    private void allbtn_defaultColor() {
        //点击后变色
        Drawable drawable= ContextCompat.getDrawable(this, R.drawable.red_btn_textbooklearn);
        btn_red_eng.setBackground(drawable);
        btn_eng_ting.setBackground(drawable);
        btn_chi_ting.setBackground(drawable);
        btn_worn_learn.setBackground(drawable);
    }
    @Override
    public void onClick(View v) {
        //调用按钮初始颜色
        allbtn_defaultColor();
        //定义按钮点今后的颜色
        Drawable drawable_check= ContextCompat.getDrawable(this, R.drawable.red_btn_textbooklearn_change);
        switch (v.getId()){
            case R.id.read_eng_spe:
                web_Ui.setVisibility(View.VISIBLE);
                lin_eng_tngxie.setVisibility(View.GONE);
                lin_eng_zhong.setVisibility(View.GONE);
                list_eng_tingxie.setVisibility(View.GONE);
                btn_red_eng.setBackground(drawable_check);
                Toast.makeText(xel_specialt_english_textbooklearn.this, "正在加载音频,请等待........", Toast.LENGTH_SHORT).show();
                handler.removeCallbacks(updateThread);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mService.onDestroy();
                        mService.playMusic(eng_kewen);
                        handler.post(updateThread);
                    }
                }).start();
                web_Ui.loadUrl(url_ui_web);
                web_Ui.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
                break;
            case R.id.eng_ting_spe:
                web_Ui.setVisibility(View.GONE);
                lin_eng_tngxie.setVisibility(View.VISIBLE);
                lin_eng_zhong.setVisibility(View.GONE);
                list_eng_tingxie.setVisibility(View.GONE);
                btn_eng_ting.setBackground(drawable_check);
                Toast.makeText(xel_specialt_english_textbooklearn.this, "正在加载音频,请等待........", Toast.LENGTH_SHORT).show();
                handler.removeCallbacks(updateThread);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mService.onDestroy();
                        mService.playMusic(eng_eng_lis);
                        handler.post(updateThread);
                    }
                }).start();
                map.put("No",acc_nmu+"0");//向map  赋值
                urll = Constant.post_xingainain_yingyu;//赋值请求生子听写答案接口路径
                eng_chi = 0;
                Init_post_tingxie();//向后台请求听写答案

                break;
            case R.id.zhong_ting_spe:
                web_Ui.setVisibility(View.GONE);
                lin_eng_tngxie.setVisibility(View.GONE);
                lin_eng_zhong.setVisibility(View.VISIBLE);
                list_eng_tingxie.setVisibility(View.GONE);
                btn_chi_ting.setBackground(drawable_check);
                Toast.makeText(xel_specialt_english_textbooklearn.this, "正在加载音频,请等待........", Toast.LENGTH_SHORT).show();
                handler.removeCallbacks(updateThread);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mService.onDestroy();
                        mService.playMusic(chi_eng_lis);
                        handler.post(updateThread);
                    }
                }).start();
                map.put("No",acc_nmu+"0");//向map  赋值
                urll = Constant.post_xingainain_zhongwen;//赋值请求生子听写答案接口路径
                eng_chi = 1;
                Init_post_tingxie();//向后台请求听写答案
                break;
            case R.id.word_learn_spe:
                btn_worn_learn.setBackground(drawable_check);
                Stitching.post_Chapter(acc_nmu);
                handler.removeCallbacks(updateThread);
                mService.onDestroy();
                    Toast.makeText(this, "请检查您的网络设置", Toast.LENGTH_SHORT).show();
                    final HashMap<String, String> map = new HashMap<>();
                    map.put("No", num_beidanci);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String zhi = Post_learn_rijiyuelei.getStringCha(Constant.post_Learn_rijiyuelei, map);
                                JSONObject json = new JSONObject(zhi);
                                String success = json.getString("success");
                                Log.d("@@","success"+success);
                                if(success.equals("200")) {
                                    Intent intent_eng_word = new Intent(xel_specialt_english_textbooklearn.this, Xel_Course_English_Textbooklearn_wordlearn.class);
                                    intent_eng_word.putExtra("ke_id", acc_nmu);
                                    intent_eng_word.putExtra("ke_name",class_name);
                                    intent_eng_word.putExtra("zhi", zhi);
                                    startActivity(intent_eng_word);
                                }
                                else {
                                    Toast.makeText(xel_specialt_english_textbooklearn.this, "当前无资源", Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException e) {
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                
                break;
            case R.id.spe_back_1:
                mService.onDestroy();
                handler.removeCallbacks(updateThread);

                finish();
                break;
        }
    }

    //   用于控制音频的控件
    Handler handler = new Handler();
    Runnable updateThread = new Runnable() {
        public void run() {
            // 获得歌曲的长度并设置成播放进度条的最大值
            seekBar1.setMax(mService.getDuration());
            // 获得歌曲现在播放位置并设置成播放进度条的值
            seekBar1.setProgress(mService.getCurrent());

            tv_curcentTime.setText(formatTime(mService.getCurrent()));
            tv_allTime.setText(formatTime(mService.getDuration()));
            if((mService.getDuration()-mService.getCurrent())<50){
                //判断当  播放时间 == 总时间的时候
                Ini_update_tingxieview();/*调用显示 听写答案的 方法*/
            }
            // 每次延迟100毫秒再启动线程
            handler.postDelayed(updateThread, 100);
        }
    };
    private void setListener() {
        // 暂停or开始
        stop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mService.pausePlay();
                if (mService.isPlay()) {
                    //改变  暂停按钮的  样式和显示的字
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
    
    /*
  * 向后台请求听写答案
  * */
    public void Init_post_tingxie() {
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
                if (eng_chi == 0) {
                    list_eng_tingxie.setAdapter(new TingxieAdapter_eng(this, list_item));
                } else {
                    list_eng_tingxie.setAdapter(new TingxieAdapter_eng_3(this, list_item));
                }
            }else {
                Toast.makeText(this, "当前答案不允许查看", Toast.LENGTH_SHORT).show();
            }
        }else {

        }





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
        }else if(keyCode == KeyEvent.KEYCODE_HOME){
            mService.onDestroy();
        }
        return super.onKeyDown(keyCode, event);
    }
}
