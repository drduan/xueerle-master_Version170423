package com.neusoft.sample.View.xel_specialtopic.yangkangkang;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.ncapdevi.sample.R;
import com.neusoft.sample.Model.Stitching;
import com.neusoft.sample.View.AlwaysMarqueeTextView;
import com.neusoft.sample.View.BaseActivity;

public class xel_specialt_math_shudu_learn extends BaseActivity implements View.OnClickListener{
    private Button btn_break;
    private AlwaysMarqueeTextView tv_title;
    private WebView web_zhu;
    String web_ip = "";//音频路径g
    String lujing= "";//章节编号
    String zhang = "";//章编号
    String name = "";//节名字
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xel_specialt_math_shudu_learn);
        Iniview();//初始化值
        Iniview_acept();//处理传递过来的章节编号
        Ini_pinjie();//拼接路径
        Init_web();//获取webview
    }

    private void Ini_pinjie() {
        String [] getroute = Stitching.getRoute(lujing);
        web_ip = getroute[0];
        Log.d("@@","数独"+web_ip);

    }

    private void Init_web() {
        web_zhu.loadUrl(web_ip);
        web_zhu.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    private void Iniview_acept() {
        Intent accept = getIntent();
        lujing =  accept.getStringExtra("shduzhi");
        zhang = lujing.substring(lujing.length()-4,lujing.length()-2);
        name = accept.getStringExtra("name");
        tv_title.setText("专题>数学>数独学习>"+"第"+zhang+"章  "+name);
    }
    private void Iniview() {
        btn_break = (Button) findViewById(R.id.shudu_leftbutton);
        tv_title = (AlwaysMarqueeTextView) findViewById(R.id.shudu_learn_navtitle);
        btn_break.setOnClickListener(this);
        tv_title.setOnClickListener(this);
        web_zhu = (WebView) findViewById(R.id.web_shudu);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.shudu_leftbutton:
                finish();
                break;
            case R.id.shudu_learn_navtitle:
                break;

        }
    }
}
