package com.neusoft.sample.View.Adapter.yangka;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ncapdevi.sample.R;
import com.neusoft.sample.Ctrl.yangkangkang.guoxue_listview_bean;

import java.util.List;

/**
 * Created by 杨康 on 2016/7/15.
 */
public class Guoxue_ListviewAdapter extends BaseAdapter {
    String fontPath = "ttf.ttf";
    Context mBase;
    private List<guoxue_listview_bean> mlist;//用于保存传递进来的   数据
    //数据源与适配器关联
    private LayoutInflater mInflater;
    public Guoxue_ListviewAdapter(Context context, List<guoxue_listview_bean> list) {
            mlist=list;
        mBase = context;
            mInflater = LayoutInflater.from(context);//引入context   是为了初始化 inflater
    }
    @Override
    public int getCount() {
        return mlist.size();//返回gridview  数据量
    }
    @Override
    public Object getItem(int position) {
        return mlist.get(position);//从mlist   中取出索引对应的数据项
    }

    @Override
    public long getItemId(int position) {
        return position;//对应的索引项
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {//返回每一项的显示内容
        View v = mInflater.inflate(R.layout.guoxue_gridview_item,null);
        TextView pin1 = (TextView) v.findViewById(R.id.guoxue_pin1);
        TextView pin2 = (TextView) v.findViewById(R.id.guoxue_pin2);
        TextView pin3 = (TextView) v.findViewById(R.id.guoxue_pin3);
        TextView pin4 = (TextView) v.findViewById(R.id.guoxue_pin4);
        TextView pin5 = (TextView) v.findViewById(R.id.guoxue_pin5);
        TextView pin6 = (TextView) v.findViewById(R.id.guoxue_pin6);
        TextView pin7 = (TextView) v.findViewById(R.id.guoxue_pin7);
        TextView pin8 = (TextView) v.findViewById(R.id.guoxue_pin8);
        TextView pin9 = (TextView) v.findViewById(R.id.guoxue_pin9);
        TextView pin10 = (TextView) v.findViewById(R.id.guoxue_pin10);
        TextView pin11 = (TextView) v.findViewById(R.id.guoxue_pin11);
        TextView pin12 = (TextView) v.findViewById(R.id.guoxue_pin12);
        TextView pin13 = (TextView) v.findViewById(R.id.guoxue_pin13);

        TextView zi1 = (TextView) v.findViewById(R.id.guoxue_text1);
        TextView zi2 = (TextView) v.findViewById(R.id.guoxue_text2);
        TextView zi3 = (TextView) v.findViewById(R.id.guoxue_text3);
        TextView zi4 = (TextView) v.findViewById(R.id.guoxue_text4);
        TextView zi5 = (TextView) v.findViewById(R.id.guoxue_text5);
        TextView zi6 = (TextView) v.findViewById(R.id.guoxue_text6);
        TextView zi7 = (TextView) v.findViewById(R.id.guoxue_text7);
        TextView zi8 = (TextView) v.findViewById(R.id.guoxue_text8);
        TextView zi9 = (TextView) v.findViewById(R.id.guoxue_text9);
        TextView zi10 = (TextView) v.findViewById(R.id.guoxue_text10);
        TextView zi11 = (TextView) v.findViewById(R.id.guoxue_text11);
        TextView zi12 = (TextView) v.findViewById(R.id.guoxue_text12);
        TextView zi13 = (TextView) v.findViewById(R.id.guoxue_text13);
        Typeface tf = Typeface.createFromAsset(mBase.getAssets(),fontPath);

        guoxue_listview_bean bean = mlist.get(position);
        pin1.setText(bean.p1);
        pin2.setText(bean.p2);    pin3.setText(bean.p3);pin4.setText(bean.p4);
        pin5.setText(bean.p5);  pin6.setText(bean.p6);  pin7.setText(bean.p7); 
        pin8.setText(bean.p8);  pin9.setText(bean.p9);  pin10.setText(bean.p10); 
        pin11.setText(bean.p11);  pin12.setText(bean.p12);  
        pin13.setText(bean.p13);  //pin14.setText(bean.p14);  pin15.setText(bean.p15);  pin16.setText(bean.p16);  pin17.setText(bean.p17);  pin18.setText(bean.p18);  pin19.setText(bean.p19);  pin20.setText(bean.p20);  pin21.setText(bean.p21);  pin22.setText(bean.p22);  pin23.setText(bean.p23);  pin24.setText(bean.p24);  pin25.setText(bean.p25);  pin26.setText(bean.p26);
        zi1.setTypeface(tf);zi2.setTypeface(tf);zi3.setTypeface(tf);zi4.setTypeface(tf);zi5.setTypeface(tf);zi6.setTypeface(tf);zi7.setTypeface(tf);zi8.setTypeface(tf);zi9.setTypeface(tf);zi10.setTypeface(tf);zi11.setTypeface(tf);zi12.setTypeface(tf);zi13.setTypeface(tf);
        zi1.setText(bean.w1); zi2.setText(bean.w2); zi3.setText(bean.w3);
        zi4.setText(bean.w4); zi5.setText(bean.w5); zi6.setText(bean.w6); 
        zi7.setText(bean.w7); zi8.setText(bean.w8); zi9.setText(bean.w9); zi10.setText(bean.w10); zi11.setText(bean.w11); 
        zi12.setText(bean.w12); zi13.setText(bean.w13); //zi14.setText(bean.w14); zi15.setText(bean.w15); zi16.setText(bean.w16); zi17.setText(bean.w17); zi18.setText(bean.w18); zi19.setText(bean.w19); zi20.setText(bean.w20); zi21.setText(bean.w21); zi22.setText(bean.w22); zi23.setText(bean.w23); zi24.setText(bean.w24); zi25.setText(bean.w25); zi26.setText(bean.w26);
       return v;
    }
    
}
