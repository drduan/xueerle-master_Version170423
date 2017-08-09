package com.neusoft.sample.View.xel_course.yangkangkang;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.ncapdevi.sample.R;
import com.neusoft.sample.App;
import com.neusoft.sample.Ctrl.yangkangkang.ChengYuJieLong;
import com.neusoft.sample.Ctrl.yangkangkang.ChengYuJieLongList;
import com.neusoft.sample.Ctrl.yangkangkang.ChengYuJinYiCi;
import com.neusoft.sample.Ctrl.yangkangkang.gridview_bean;
import com.neusoft.sample.Model.Constant;
import com.neusoft.sample.Model.Post_learn_rijiyuelei;
import com.neusoft.sample.Model.Stitching;
import com.neusoft.sample.View.Adapter.yangka.GridviewAdapter;
import com.neusoft.sample.View.AlwaysMarqueeTextView;
import com.neusoft.sample.View.Mp3.MusicPlayService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

import static android.view.View.GONE;

/**
 * Created by Administrator on 2016/6/27.
 */
public class Xel_Course_Chinese_IdiomaGame extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {
    GridviewAdapter gvadapter;
    Typeface tf;
    private ImageView voice_chengyu;
    private Handler handler;
    private int aaa = 0;
    private int bbb = 2;
    
    int indext = 0;
   Boolean isjin = false;
    String ht_in_zi = "";
    String ht_in = "";
    //向gridview 中添加 东西的定义
    //  d定义存储gridview数据的   list
    List<gridview_bean> list_gridview;
    String chengyu_juti;
    String jinyici = "";//近义词和反义词
    //gridview定义
    GridView GV_chengyu;
    String shuju = "";
    Button btn_fam;
    Button btn_jinyici = null;
    String num_2;
    
    private List<Map<String, Object>> dataList;
    AlwaysMarqueeTextView tv_idi_title;
    private TextView tv_idio_num,
            tv_body, tv_body_1, body_pinyin, tv_explain, tv_yujian, tv_liju, tv_jinyici, tv_fanyici;
    private LinearLayout ln_yujian, ln_yujian_line, ln_jinyi, ln_fanyi;
    //定义存储成语的实体
    ChengYuJieLongList accept;
    ChengYuJinYiCi Jinyici;
    //定义存储  每一个成语里面的具体的数据
    ChengYuJieLong accept_1;
    //音频的引用
    private MusicPlayService mService;
    private App application;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xel_course_chinese_idiomgame);
        //创建属于主线程的handler
        Iniview();
        Inivi_accept();
        Ini_add_body();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    //接受穿过来的  值  并且将它解析以后存入实体中
    private void Inivi_accept() {
        Intent intent_accept = getIntent();
        String zhi = intent_accept.getStringExtra("zhi");
        String num_ = intent_accept.getStringExtra("num");
         num_2 = num_.substring(num_.length() - 2, num_.length());
        tv_idi_title.setText("课程>语文>成语接龙>第" + num_2 + "章");
        tv_idio_num.setText("第" + num_2 + "章  ");
        JSONObject json = null;
        try {
            json = new JSONObject(zhi);
            String success = json.getString("success");
            if(success.equals("200")){
                accept = JSON.parseObject((String) json.get("data"), ChengYuJieLongList.class);
           aaa = 0;
            }else {
                aaa = 1;
                finish();
                Toast.makeText(this, "目前没有资源", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Ini_post_idio();
    }

    //返回按钮
    public void break_1(View view) {
        mService.onDestroy();
        finish();
    }

    //向gridview  中添加   成语
    private void Ini_add_body() {
        list_gridview = new ArrayList<>();
        if(aaa ==0) {
            list_gridview.add(new gridview_bean(
                    accept.getCheng1()
            ));
            tv_body.setText(accept.getCheng1());
            list_gridview.add(new gridview_bean(
                    accept.getCheng2()
            ));
            list_gridview.add(new gridview_bean(
                    accept.getCheng3()
            ));
            list_gridview.add(new gridview_bean(
                    accept.getCheng4()
            ));
            list_gridview.add(new gridview_bean(
                    accept.getCheng5()
            ));
            list_gridview.add(new gridview_bean(
                    accept.getCheng6()
            ));
            list_gridview.add(new gridview_bean(
                    accept.getCheng7()
            ));
            list_gridview.add(new gridview_bean(
                    accept.getCheng8()
            ));
            list_gridview.add(new gridview_bean(
                    accept.getCheng9()
            ));
            list_gridview.add(new gridview_bean(
                    accept.getCheng10()
            ));
        }
        GV_chengyu = (GridView) findViewById(R.id.gv_chengyu);
        gvadapter = new GridviewAdapter(this, list_gridview);
        GV_chengyu.setAdapter(gvadapter);
        GV_chengyu.setOnItemClickListener(this);
        Ini_post_idio();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        gvadapter.clearSelection(position);
        gvadapter.notifyDataSetChanged();
        //////////
        if (!(position == indext)) {
            indext = position;
        }
        shuju = list_gridview.get(indext).Itemchengyu;
        ht_in_zi = Stitching.ip_chengyu + shuju + "释义.mp3";
        //mService.onDestroy(); 
        Toast.makeText(Xel_Course_Chinese_IdiomaGame.this, "正在加载音频，请等待！", Toast.LENGTH_SHORT).show();
        mService.playMusics(ht_in_zi);
        /*
        * f发送网络请求
        * */
        Ini_post_idio();
    }

    /*
    * 发送网络请求获取到具体的成语内容*/
    private void Ini_post_idio() {
        if (shuju.equals("")) {
            if(aaa ==0) {
                shuju = accept.getCheng1();
            }
        }
        final HashMap<String, String> map = new HashMap<>();
        map.put("No", shuju);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    chengyu_juti = Post_learn_rijiyuelei.getStringCha(Constant.post_Learn_chengyujielong_zhi, map);
                    Init_chengyu_();
                    tv_body.post(runnableUi);
                    tv_body_1.post(runnableUi);
                    tv_liju.post(runnableUi);
                    tv_yujian.post(runnableUi);
                    tv_explain.post(runnableUi);
                    body_pinyin.post(runnableUi);
                  
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
           
        }
    private void INI_getjin() {
        /*获取近义词反义词*/
        try {
            /*解析近义词反义词 并且  将他 存进实体中*/
            JSONObject json1 = new JSONObject(jinyici);
            Log.d("@@n", "打印json" + json1);
            Boolean aa = json1.has("jinYiCi1");
            Boolean aaa = json1.isNull("jinYiCi1");
            Log.d("@@", "aa" + aa + "aaa" + aaa);
            String success = json1.getString("success");
            Log.d("@@","success"+success);
            if(success.equals("200")){
                Jinyici = JSON.parseObject((String) json1.get("data"), ChengYuJinYiCi.class);
           }
        } catch (JSONException e) {
          
            Log.d("@@", "catch");
            e.printStackTrace();
        }
    }
    //////将获取到的数据放入实体中/////////////////////////
    private void Init_chengyu_() {
        try {
            JSONObject json = new JSONObject(chengyu_juti);
            if(json.getString("success").equals("200")) {
                accept_1 = JSON.parseObject((String) json.get("data"), ChengYuJieLong.class);
                bbb = 3;
                Log.d("@@","ispdf"+accept_1.isPDF());
                if(accept_1.isPDF()) {
                    isjin = true;
                    final HashMap<String, String> map1 = new HashMap<>();
                    map1.put("No", accept_1.getPdfName());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                    /*向后台请求近义词反义词数据*/
                                jinyici = Post_learn_rijiyuelei.getStringCha(Constant.ip_post_chegnyu_jinyici, map1);
                                Log.d("@@", "打印jinyici" + jinyici);
                                INI_getjin();
                                tv_jinyici.post(runnableUi_jin);
                                tv_fanyici.post(runnableUi_jin);
                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.d("@@", "jinyicicatch");
                            }
                        }
                    }).start();
                }else {
                    tv_jinyici.post(runnableUi_jin);
                    tv_fanyici.post(runnableUi_jin);
                }
            }else {
                bbb = 2;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    // 构建Runnable对象，在runnable中更新界面
    Runnable runnableUi = new Runnable() {
        @Override
        public void run() {
            //更新界面
            if(bbb == 3) {
                tv_body_1.setText(accept_1.getItme());
                SpannableStringBuilder style_shiyi = new SpannableStringBuilder(accept_1.getShiYi());
                style_shiyi.setSpan(new ForegroundColorSpan(Color.RED), 1, 3, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                tv_explain.setText(style_shiyi);
                SpannableStringBuilder style_liju = new SpannableStringBuilder(accept_1.getLiJu());
                style_liju.setSpan(new ForegroundColorSpan(Color.RED), 1, 3, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                tv_liju.setText(style_liju);
                body_pinyin.getPaint().setTypeface(Typeface.DEFAULT);
                body_pinyin.setText(accept_1.getPinYin());
                if (accept_1.getYuJian().equals("\"\"")) {

                } else {
                    SpannableStringBuilder style_jyujian = new SpannableStringBuilder(accept_1.getYuJian());
                    if (accept_1.getYuJian().length() < 3) {
                        ln_yujian.setVisibility(GONE);
                        ln_yujian_line.setVisibility(GONE);
                    } else {
                        ln_yujian.setVisibility(View.VISIBLE);
                        ln_yujian_line.setVisibility(View.VISIBLE);
                        int start = accept_1.getYuJian().indexOf("【") + 1;
                        int end = accept_1.getYuJian().indexOf("】");
                        style_jyujian.setSpan(new ForegroundColorSpan(Color.RED), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                        tv_yujian.setText(style_jyujian);
                    }
                }
               
            }
        }

    };
    /*近义词反义词的判断显示问题*/
    Runnable runnableUi_jin = new Runnable() {
        @Override
        public void run() {
            Log.d("@@", "isiisis" + accept_1.isPDF());
            if (accept_1.isPDF() == false) {
                Log.d("@@","jinru隐藏近义词");
                ln_jinyi.setVisibility(GONE);
                ln_fanyi.setVisibility(GONE);
            } else {
                Log.d("@@", "打印" + (Jinyici.getJinYiCi1() + Jinyici.getJinYiCi2() + Jinyici.getJinYiCi3()));
                ln_jinyi.setVisibility(View.VISIBLE);
                String jinyici = ("【近义词】  " + Jinyici.getJinYiCi1() + "  " + Jinyici.getJinYiCi2() + "  " + Jinyici.getJinYiCi3() + "  " + Jinyici.getJinYiCi4());
                Log.d("@@", "进入了近义词" + jinyici);
                jinyici =  jinyici.replaceAll(shuju,"");
                SpannableStringBuilder style = new SpannableStringBuilder(jinyici);
                style.setSpan(new ForegroundColorSpan(Color.RED), 1, 4, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                tv_jinyici.setText(style);
                btn_jinyici.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mService.onDestroy();
                        Intent intentt = new Intent(Xel_Course_Chinese_IdiomaGame.this, xel_idom_jinfanbianxi.class);
                        startActivity(intentt);
                    }
                });
                Log.d("@@", "打印反义词" + Jinyici.getFanYiCi1() + Jinyici.getFanYiCi2());
                if ((Jinyici.getFanYiCi1() + Jinyici.getFanYiCi2()).length() < 3) {
                    ln_fanyi.setVisibility(GONE);
                } else {
                    ln_fanyi.setVisibility(View.VISIBLE);
                    String fanyici = ("【反义词】 " + Jinyici.getFanYiCi1() + "  " + Jinyici.getFanYiCi2());

                    SpannableStringBuilder style_ = new SpannableStringBuilder(fanyici);
                    style_.setSpan(new ForegroundColorSpan(Color.RED), 1, 4, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                    tv_fanyici.setText(style_);
                }
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chengyu_voice_ima:
                //////线程
                Toast.makeText(Xel_Course_Chinese_IdiomaGame.this, "正在加载音频,请稍后......", Toast.LENGTH_SHORT).show();
                String web_chengyu = Stitching.ip_chengyu + accept.getCheng1() + "接龙.mp3";
                Log.d("@@", "成语接龙龙头" + web_chengyu);
                mService.playMusics(web_chengyu);
                break;
           

        }
    }
    //////////////////////////初始化控件//////////////////////////初始化控件
    private void Iniview() {
        tf =  Typeface.createFromAsset(getAssets(), "ttf.ttf" );
        application = (App) getApplication();
        mService = application.getmService();
        voice_chengyu = (ImageView) findViewById(R.id.chengyu_voice_ima);
        voice_chengyu.setOnClickListener(this);
        tv_body = (TextView) findViewById(R.id.tv_body_chegnyu);
        tv_body_1 = (TextView) findViewById(R.id.body_1);
        body_pinyin = (TextView) findViewById(R.id.pinyin);
        tv_explain = (TextView) findViewById(R.id.explain);
        tv_yujian = (TextView) findViewById(R.id.yujian);
        tv_liju = (TextView) findViewById(R.id.liju);
        tv_jinyici = (TextView) findViewById(R.id.jinyici);
        tv_fanyici = (TextView) findViewById(R.id.fanyici);
        tv_idio_num = (TextView) findViewById(R.id.tv_num_idio);
        tv_idi_title = (AlwaysMarqueeTextView) findViewById(R.id.idioma_navtitle);
        ln_yujian = (LinearLayout) findViewById(R.id.ln_yujian);
        ln_yujian_line = (LinearLayout) findViewById(R.id.yujian_line);
        ln_jinyi = (LinearLayout) findViewById(R.id.Lin_jinyici);
        ln_fanyi = (LinearLayout) findViewById(R.id.Lin_fanyici);
        btn_jinyici = (Button) findViewById(R.id.pdf_jinyici);
        btn_jinyici.setOnClickListener(this);



                tv_body.setTypeface(tf); tv_body_1.setTypeface(tf);

        body_pinyin.setTypeface(tf); tv_explain.setTypeface(tf); tv_yujian.setTypeface(tf);
        tv_liju.setTypeface(tf); tv_jinyici.setTypeface(tf); tv_fanyici.setTypeface(tf);
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

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Xel_Course_Chinese_IdiomaGame Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
