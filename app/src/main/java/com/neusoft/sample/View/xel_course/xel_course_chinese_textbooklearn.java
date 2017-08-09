package com.neusoft.sample.View.xel_course;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.ncapdevi.sample.R;
import com.neusoft.sample.App;
import com.neusoft.sample.Ctrl.yangkangkang.TingXieAnswer;
import com.neusoft.sample.Ctrl.yangkangkang.TingXieAnswer_item;
import com.neusoft.sample.Model.Constant;
import com.neusoft.sample.Model.Post_learn_rijiyuelei;
import com.neusoft.sample.Model.Stitching;
import com.neusoft.sample.View.Adapter.yangka.TingxieAdapter;
import com.neusoft.sample.View.AlwaysMarqueeTextView;
import com.neusoft.sample.View.BaseActivity;
import com.neusoft.sample.View.Mp3.MusicPlayService;
import com.neusoft.sample.View.xel_course.miaohong.HanZiShowActivity;
import com.neusoft.sample.View.xel_mine.MyHomeWork.get_tingxie;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
public class xel_course_chinese_textbooklearn extends BaseActivity implements View.OnClickListener {
    //定义接受传递过来的值
    String num_hou2 ="";
    String teach_num = "";//教材编号
    String class_name  = "";//课名称
    String teach_num7 = "";//截取教材编号的前7位
    String chi_kewen = "";//语文课文语音
    String chi_slow_zi = "";//生字听写慢速
    String chi_speed_zi = "";//生字听写中速
    String chi_slow_ci = "";//词组听写慢速
    String chi_speed_ci = "";//词组听写中速
    String url_ui_web= "";//语文课本内容webcview
    String url_ui="";
    String acc_nmu;
    List<TingXieAnswer>  tingxie ;//承接听写答案的实体
    //播放音音频private App application;
    private App application;
    private TextView tv_curcentTime, tv_allTime;
    private SeekBar seekBar1;// 播放进度条
    private MusicPlayService mService;
    //定义初始化控件用于
    Button btn_stop_chi;
    Button btn_red_chi;
    Button btn_ting_man;
    Button btn_ting_zhong;
    Button btn_ci_ting_man;
    Button btn_ci_ting_zhong;
    Button btn_zi_learn;
    TextView tv_ke_chi;
    AlwaysMarqueeTextView tv_chi_title;
    private WebView web_Ui;//课本html   文件
    private ImageView img_chi;//课本  html   但是目前先用  图片代替
    private RequestQueue mRequestQueue;//vollay   初始化定义
    
    private LinearLayout lin_tingxie,lin_tingxe_cizu;//听写的布局
    private ListView list_tingxie;//显示听写答案
    HashMap<String, String> map;//向服务器请求听写答案的map
    String urll;//请求听写答案的接口
    Boolean succ=false;//判断是否请求成功听写答案
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xel_course_chinese_textbooklearn);
       
        //初始化播放音频的  工具类
        application = (App) getApplication();
        mService = application.getmService();
        initView();
        IN_accept();
        allbtn_defaultColor();
        setListener();

    }
    /////////////////////////////////接受穿过来的  教材ID////////////////////////
    private void IN_accept() {
        Intent accept_mum = getIntent();
         acc_nmu =  accept_mum.getStringExtra("mum");
        String acc_name = accept_mum.getStringExtra("mum_name");
        class_name = acc_name;
        teach_num = acc_nmu.substring(0,5);//截取教材id
        teach_num7 = acc_nmu;
        num_hou2 = acc_nmu.substring(acc_nmu.length()-2,acc_nmu.length());
        //////////////////拼接音频路径/////////////////////////////////////////////
        /*
        * 标题的显示
        * */
        tv_chi_title.setText("课程>语文>课本学习>"+"第"+num_hou2+"课  "+class_name);
        tv_ke_chi.setText("第"+num_hou2+"课  "+class_name);
         Ini_yuyin();
    }
    /*
    * 拼接语音路径*/
    private void Ini_yuyin() {
        String [] getroute = Stitching.getRoute(teach_num7);
        url_ui_web = getroute[0];
        chi_kewen = getroute[1];
        chi_slow_zi = getroute[2];
        chi_speed_zi = getroute[3];
        chi_slow_ci =getroute[4];
        chi_speed_ci = getroute[5];
    }
    /*
    * 初始化控件*/
    public void break_1(View view){
        handler.removeCallbacks(updateThread);
        mService.onDestroy();
        finish();
    }
    //初始化控件
    private void initView() {
        mRequestQueue = Volley.newRequestQueue(this);//图片加载 用vollay   框架  初始化
        map = new HashMap<>();//初始化   用于保存请听写答案的 map
        //音频控制按钮初始化
        seekBar1 = (SeekBar) findViewById(R.id.seekBar1_chi);
        tv_curcentTime = (TextView) findViewById(R.id.tv_curcentTime_chi);
        tv_allTime = (TextView) findViewById(R.id.tv_allTime_chi);
        //初始化按钮
        btn_stop_chi= (Button) findViewById(R.id.stop_chi_btn);
        btn_red_chi = (Button) findViewById(R.id.read_chi);
        btn_ting_man = (Button) findViewById(R.id.zi_ting_man);
        btn_ting_zhong = (Button) findViewById(R.id.zi_ting_zhong);
        btn_ci_ting_zhong= (Button) findViewById(R.id.ci_ting_zhong);
        btn_ci_ting_man= (Button) findViewById(R.id.ci_ting_man);
        btn_zi_learn = (Button) findViewById(R.id.zi_learn___);
        tv_chi_title = (AlwaysMarqueeTextView) findViewById(R.id.chi_title);
        tv_ke_chi = (TextView) findViewById(R.id.tv_ke_chi);
        lin_tingxie = (LinearLayout) findViewById(R.id.chinese_tingxie);
        lin_tingxe_cizu = (LinearLayout) findViewById(R.id.chinese_tingxie_cizu);
        list_tingxie= (ListView) findViewById(R.id.chinesee_listview);
        web_Ui = (WebView) findViewById(R.id.chi_web);

        img_chi = (ImageView) findViewById(R.id.chi_web_img);//图片加载
        //设置按钮初始按钮颜色
        btn_stop_chi.setOnClickListener(this);
        btn_ting_man.setOnClickListener(this);
        btn_ting_zhong.setOnClickListener(this);
        btn_ci_ting_man.setOnClickListener(this);
        btn_ci_ting_zhong.setOnClickListener(this);
        btn_red_chi.setOnClickListener(this);
        btn_zi_learn.setOnClickListener(this);
        //btn_zi_learn.setOnClickListener(this);
        // 启动线程用于控制   进度条  播放时间  总时间  等 。。。。。
       
    }

    //用于控制电机前的颜色的额
    private void allbtn_defaultColor() {
        //点击后变色
        Drawable drawable= ContextCompat.getDrawable(this, R.drawable.red_btn_textbooklearn);
       // btn_stop_chi.setBackground(drawable);
        btn_ci_ting_man.setBackground(drawable);
        btn_ci_ting_zhong.setBackground(drawable);
       // btn_zi_learn.setBackground(drawable);
        btn_red_chi.setBackground(drawable);
        btn_ting_man.setBackground(drawable);
        btn_ting_zhong.setBackground(drawable);
    }
    @Override
    public void onClick(View v) {
        //调用按钮初始颜色
        allbtn_defaultColor();
        //定义按钮点今后的颜色
        Drawable drawable_check= ContextCompat.getDrawable(this, R.drawable.red_btn_textbooklearn_change);
        switch (v.getId()){
            case R.id.read_chi:
                list_tingxie.setVisibility(View.GONE);
                lin_tingxie.setVisibility(View.GONE);
                lin_tingxe_cizu.setVisibility(View.GONE);
                web_Ui.setVisibility(View.GONE);
                img_chi.setVisibility(View.VISIBLE);
                Toast.makeText(xel_course_chinese_textbooklearn.this, "正在加载音频,请等待........", Toast.LENGTH_SHORT).show();
                btn_red_chi.setBackground(drawable_check);
                handler.removeCallbacks(updateThread);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mService.playMusic(chi_kewen);
                        handler.post(updateThread);
                    }
                }).start();
                /*请求图片*/
               Log.d("@@","图片路径"+url_ui_web);
                ImageRequest imageRequest = new ImageRequest(url_ui_web, new Response.Listener<Bitmap>() {
                 @Override
                 public void onResponse(Bitmap bitmap) {
                Log.d("@@","请求成功");
                img_chi.setImageBitmap(bitmap);
                     }
                    }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                  @Override
                  public void onErrorResponse(VolleyError volleyError) {
                 Log.d("@@","请求失败");
                  }
                    });
                mRequestQueue.add(imageRequest);
                 /*请求图片*/
                if(mService.getmMediaPlayer() == null){
                    handler.removeCallbacks(updateThread);
                }
                break;
            case R.id.zi_ting_man:
                list_tingxie.setVisibility(View.GONE);
                lin_tingxie.setVisibility(View.VISIBLE);
                lin_tingxe_cizu.setVisibility(View.GONE);
                img_chi.setVisibility(View.GONE);
                btn_ting_man.setBackground(drawable_check);
                Toast.makeText(xel_course_chinese_textbooklearn.this, "正在加载音频,请等待........", Toast.LENGTH_SHORT).show();
                       handler.removeCallbacks(updateThread);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mService.playMusic(chi_slow_zi);
                        handler.post(updateThread);
                    }
                }).start();
                get_tingxie.get_power(xel_course_chinese_textbooklearn.this);
                map.put("No",teach_num7+"0");//向map  赋值
                urll = Constant.post_chinese_shengzi;//赋值请求生子听写答案接口路径
                Init_post_tingxie();//向后台请求听写答案
                break;
            case R.id.zi_ting_zhong:
                list_tingxie.setVisibility(View.GONE);
                lin_tingxie.setVisibility(View.VISIBLE);
                lin_tingxe_cizu.setVisibility(View.GONE);
                img_chi.setVisibility(View.GONE);
                btn_ting_zhong.setBackground(drawable_check);
                Toast.makeText(xel_course_chinese_textbooklearn.this, "正在加载音频,请等待........", Toast.LENGTH_SHORT).show();
                handler.removeCallbacks(updateThread);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mService.playMusic(chi_speed_zi);
                        handler.post(updateThread);  
                    }
                }).start();
                get_tingxie.get_power(xel_course_chinese_textbooklearn.this);
                map.put("No",teach_num7+"1");//向map  赋值
                urll = Constant.post_chinese_shengzi;//赋值请求生子听写答案接口路径
                Init_post_tingxie();//向后台请求听写答案
                break;
            case R.id.ci_ting_man:
                list_tingxie.setVisibility(View.GONE);
                img_chi.setVisibility(View.GONE);
                lin_tingxie.setVisibility(View.GONE);
                lin_tingxe_cizu.setVisibility(View.VISIBLE);
                btn_ci_ting_man.setBackground(drawable_check);
                Toast.makeText(xel_course_chinese_textbooklearn.this, "正在加载音频,请等待........", Toast.LENGTH_SHORT).show();
                       handler.removeCallbacks(updateThread);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mService.playMusic(chi_slow_ci);
                        handler.post(updateThread);
                    }
                }).start();
                get_tingxie.get_power(xel_course_chinese_textbooklearn.this);
                map .put("No",teach_num7+"0");//向map  赋值
                urll = Constant.post_chinese_cizu;//赋值请求词组听写答案接口路径
                Init_post_tingxie();//向后台请求听写答案     
                break;
            case R.id.ci_ting_zhong:
                list_tingxie.setVisibility(View.GONE);
                lin_tingxie.setVisibility(View.GONE);
                lin_tingxe_cizu.setVisibility(View.VISIBLE);
                img_chi.setVisibility(View.GONE);
                btn_ci_ting_zhong.setBackground(drawable_check);
                Toast.makeText(xel_course_chinese_textbooklearn.this, "正在加载音频,请等待........", Toast.LENGTH_SHORT).show();
                       handler.removeCallbacks(updateThread);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mService.playMusic(chi_speed_ci);
                        handler.post(updateThread);
                    }
                }).start();
                map .put("No",teach_num7+"1");//向map  赋值
                urll = Constant.post_chinese_cizu;//赋值请求词组听写答案接口路径
                get_tingxie.get_power(xel_course_chinese_textbooklearn.this);
                Init_post_tingxie();//向后台请求听写答案    
                break;
            case R.id.zi_learn___:
                handler.removeCallbacks(updateThread);
                final HashMap<String, String> map = new HashMap<>();
                map.put("No",acc_nmu );
                btn_zi_learn.setBackground(drawable_check);
                mService.onDestroy();
                Stitching.post_Chapter(acc_nmu);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String zhi = Post_learn_rijiyuelei.getStringCha(Constant.post_Learn_rijiyuelei, map);
                                Intent intent = new Intent(xel_course_chinese_textbooklearn.this, HanZiShowActivity.class);
                                intent.putExtra("zhi",zhi);
                                intent.putExtra("hou_2",num_hou2);
                                startActivity(intent);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }}
                    }).start();
                break;
        }
    }
    /*
    * 向后台请求听写答案
    * */
    public void Init_post_tingxie() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
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
    //   用于控制音频的控件
    Handler handler = new Handler();
     Runnable updateThread = new Runnable() {
        public void run() {
            // 获得歌曲的长度并设置成播放进度条的最大值
            if(MusicPlayService.mMediaPlayer == null){
                handler.removeCallbacks(updateThread);
            }else {
                seekBar1.setMax(mService.getDuration());
                seekBar1.setProgress(mService.getCurrent());
                seekBar1.setSecondaryProgress(MusicPlayService.getAa());
                tv_curcentTime.setText(formatTime(mService.getCurrent()));
                if (mService.getDuration()!= 0)
                    tv_allTime.setText(formatTime(mService.getDuration()));
            }
            /*调用显示 听写答案的 方法*/
            
            if((mService.getDuration()-mService.getCurrent())<5){
                //判断当  播放时间 == 总时间的时候
                Ini_update_tingxieview();/*调用显示 听写答案的 方法*/
            }
            // 获得歌曲现在播放位置并设置成播放进度条的值
          
            // 每次延迟100毫秒再启动线程
            handler.postDelayed(updateThread,1);
        }
     };
    /*更新听写页面的   view*/
    private void Ini_update_tingxieview() {
        if(succ) {
            succ = false;
            Log.d("听写的a内容",get_tingxie.a+" -");
            if (!("1".equals((get_tingxie.a)))) {
                Log.d("@@", " 播放完毕");
                List<TingXieAnswer_item> list_item = new ArrayList<>();
                Collections.sort(tingxie);
                list_tingxie.setVisibility(View.VISIBLE);
                for (int i = 0; i < tingxie.size(); i++) {
                    //循环list
                    String[] bb = tingxie.get(i).get_tingxie();
                    list_item.add(new TingXieAnswer_item(bb));
                }
                list_tingxie.setAdapter(new TingxieAdapter(this, list_item));
            } else {
                Toast.makeText(xel_course_chinese_textbooklearn.this, "当前答案不允许查看", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void setListener() {
        // 暂停or开始
        btn_stop_chi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mService.pausePlay();
                if (mService.isPlay()) {
                    //改变  暂停按钮的  样式和显示的字
                    btn_stop_chi.setText("暂停");
                } else {
                    //改变  暂停按钮的  样式  和显示的字
                    btn_stop_chi.setText("继续");
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
    public static String formatTime(int time) {
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
        }else if(keyCode == KeyEvent.KEYCODE_HOME){
            mService.onDestroy();
        }
        return super.onKeyDown(keyCode, event);
    }
}
