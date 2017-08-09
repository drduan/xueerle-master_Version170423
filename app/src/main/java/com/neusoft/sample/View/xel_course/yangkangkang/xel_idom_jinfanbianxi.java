package com.neusoft.sample.View.xel_course.yangkangkang;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ncapdevi.sample.R;
import com.neusoft.sample.Ctrl.yangkangkang.ChengYuJinYiCi;
import com.neusoft.sample.View.BaseActivity;

public class xel_idom_jinfanbianxi extends BaseActivity {
    private ImageView img_left; //返回按钮
    TextView tv_jinyici;//四个近义词
    TextView tv1;//辨析1
    TextView tv2;//辨析2
    TextView tv3;//辨析3
    TextView tv4;//辨析4
    TextView ttv1;//例句1内容
    TextView ttv2;//例句2内容
    TextView ttv3;//例句3内容
    TextView ttv4;//例句4内容
    String bian1,bian2,bian3,bian4;//四个辨析的内容
    String li1,li2,li3,li4;//四个例句的内容
    String jinyici;//四个近义词
    int er,san,si;//定义int  类型的  数值为了截取辨析里边的四个内容模块；
    LinearLayout lin1,lin2,lin3,lin4;//定义包裹四个例句的linlayout
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xel__course___chinese_chengyubianxi);
        InitView();//初始化
        Iint_doneirong();//处理接受过来的内容
        Iinit_update_view();//更新界面
    }
    /*处理接受过来的内容*/
    private void Iint_doneirong() {
        //处理接受过来的内容
        String []jinyici_shuzu = ChengYuJinYiCi.get_jinyici();
        Log.d("@@","传递过来的数组"+jinyici_shuzu);
        jinyici = jinyici_shuzu[0] +" "+jinyici_shuzu[1] +" "+jinyici_shuzu[2]+" "+jinyici_shuzu[3];//四个近义词
        er = jinyici_shuzu[4].indexOf("②");
        san =  jinyici_shuzu[4].indexOf("③");
        si = jinyici_shuzu[4].indexOf("④");//取得字符串中2，3处的下表；
        Log.d("@@","san"+san+jinyici);
        Log.d("@@","辨析"+jinyici_shuzu[4]);
        /* 为了防止数组越界在这加一层判断*/
            bian1 = jinyici_shuzu[4].substring(3,er);//辨析1
            if((er!=-1) ){
            bian2 = jinyici_shuzu[4].substring(er,san);}//辨析2
            if(san!=-1){Log.d("@@","san"+san);
                if(si!= -1){
            bian3 = jinyici_shuzu[4].substring(san,si);}
                else {
                    bian3 = jinyici_shuzu[4].substring(san,jinyici_shuzu[4].length());
                }
                Log.d("@@","san"+bian3);}//辨析3
            if(si!=-1){
                Log.d("@@","si"+si);
            bian4 = jinyici_shuzu[4].substring(si,jinyici_shuzu[4].length());
                Log.d("@@","si"+bian4);}//辨析4
        li1 = jinyici_shuzu[5].replaceAll("【例句1】","");//例句一
        li2 = jinyici_shuzu[6].replaceAll("【例句2】","");//例句二
        li3 = jinyici_shuzu[7].replaceAll("【例句3】","");//例句三
        li4 = jinyici_shuzu[8].replaceAll("【例句4】","");//例句四
        Log.d("@@"," dayin例句"+li1+li4);
    }
    /*更新界面*/
    private void Iinit_update_view() {
        tv_jinyici.setText(jinyici);//四个近义词
        tv1.setText(bian1);
        /*对四个辨析界面显示的逻辑判断*/
        if(er != -1){
        tv2.setText(bian2);}
        else {
            tv2.setVisibility(View.GONE);
        }
        if(san != -1){
        tv3.setText(bian3);}
        else {
            tv3.setVisibility(View.GONE);
        }
        if(si != -1){
        tv4.setText(bian4);}
        else {
            tv4.setVisibility(View.GONE);
        }
        /*对四个例句的逻辑判断*/
        if(li1.length()<3){
            lin1.setVisibility(View.GONE);
        }else {
            ttv1.setText(li1);
        }
        if(li2.length()<3){
            lin2.setVisibility(View.GONE);
        }else {
            ttv2.setText(li2);
        }
        if(li3.length()<3){
            lin3.setVisibility(View.GONE);
        }else {
            ttv3.setText(li3);
        }
        if(li4.length()<3){
            lin4.setVisibility(View.GONE);
        }else {
            ttv4.setText(li4);
        }
        
    }
    /*初始化*/
    private void InitView() {
        img_left = (ImageView) findViewById(R.id.rankList_back_idom_bianxi);
        tv_jinyici = (TextView) findViewById(R.id.kstktitle_idom_bianxi);
        tv1 = (TextView) findViewById(R.id.yi);
        tv2 = (TextView) findViewById(R.id.ersansi);
        tv3 = (TextView) findViewById(R.id.sansi);
        tv4 = (TextView) findViewById(R.id.si);
        ttv1 = (TextView) findViewById(R.id.lijuyineirong);
        ttv2 = (TextView) findViewById(R.id.lijuerneirong);
        ttv3 = (TextView) findViewById(R.id.lijusanneirong);
        ttv4 = (TextView) findViewById(R.id.lijusineirong);
        lin1 = (LinearLayout) findViewById(R.id.liju1);
        lin2 = (LinearLayout) findViewById(R.id.lijuer);
        lin3 = (LinearLayout) findViewById(R.id.lijusan);
        lin4 = (LinearLayout) findViewById(R.id.lijusi);
        img_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
