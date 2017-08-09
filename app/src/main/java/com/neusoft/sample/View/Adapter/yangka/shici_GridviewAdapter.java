package com.neusoft.sample.View.Adapter.yangka;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ncapdevi.sample.R;
import com.neusoft.sample.Ctrl.yangkangkang.shici_gridview_bean;

import java.util.List;

/**
 * Created by 杨康 on 2016/7/15.
 */
public class shici_GridviewAdapter extends BaseAdapter {
    String fontPath = "ttf.ttf";
    Context mBase;
    private int selectedPosition;
    private List<shici_gridview_bean> mlist;//用于保存传递进来的   数据
    //数据源与适配器关联
    private LayoutInflater mInflater;
    public shici_GridviewAdapter(Context context, List<shici_gridview_bean> list) {
            mlist=list;
            mBase=context;
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
   //这句是把listview的点击position,传递过来
    public void clearSelection(int position) {
        selectedPosition = position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {//返回每一项的显示内容
        View v = mInflater.inflate(R.layout.activity_flash, null);
        ViewHolder holder;
        holder = new ViewHolder();
        shici_gridview_bean bean = mlist.get(position);
        if(bean.num==5) {
            v = mInflater.inflate(R.layout.shici_gridview_5_item, null);
        }
        if(bean.num == 1){
            v = mInflater.inflate(R.layout.mao_gridview_item, null);
        }
        if(bean.num ==3 ){
            v = mInflater.inflate(R.layout.stu_shici_item, null);
        }
        if(bean.num == 7 ){
            v = mInflater.inflate(R.layout.shici_gridview_7_item, null);
        }
        holder.tv_pin1_1 = (TextView) v.findViewById(R.id.mao1_pin1);
        holder.tv_pin1_2 = (TextView) v.findViewById(R.id.mao1_pin2);
        holder.tv_pin1_3 = (TextView) v.findViewById(R.id.mao1_pin3);
        holder.tv_pin1_4 = (TextView) v.findViewById(R.id.mao1_pin4);
        holder.tv_pin1_5 = (TextView) v.findViewById(R.id.mao1_pin5);
        holder.tv_pin1_6 = (TextView) v.findViewById(R.id.mao1_pin6);
        holder.tv_pin2_1 = (TextView) v.findViewById(R.id.mao_pin1);
        holder.tv_pin2_2 = (TextView) v.findViewById(R.id.mao_pin2);
        holder.tv_pin2_3 = (TextView) v.findViewById(R.id.mao_pin3);
        holder.tv_pin2_4 = (TextView) v.findViewById(R.id.mao_pin4);
        holder.tv_pin2_5 = (TextView) v.findViewById(R.id.mao_pin5);
        holder.tv_pin2_6 = (TextView) v.findViewById(R.id.mao_pin6);
        holder.tv_zi1_1 = (TextView) v.findViewById(R.id.mao1_zi1);
        holder.tv_zi1_2 = (TextView) v.findViewById(R.id.mao1_zi2);
        holder.tv_zi1_3 = (TextView) v.findViewById(R.id.mao1_zi3);
        holder.tv_zi1_4 = (TextView) v.findViewById(R.id.mao1_zi4);
        holder.tv_zi1_5 = (TextView) v.findViewById(R.id.mao1_zi5);
        holder.tv_zi1_6 = (TextView) v.findViewById(R.id.mao1_zi6);
        holder.tv_zi2_1 = (TextView) v.findViewById(R.id.mao_zi1);
        holder.tv_zi2_2 = (TextView) v.findViewById(R.id.mao_zi2);
        holder.tv_zi2_3 = (TextView) v.findViewById(R.id.mao_zi3);
        holder.tv_zi2_4 = (TextView) v.findViewById(R.id.mao_zi4);
        holder.tv_zi2_5 = (TextView) v.findViewById(R.id.mao_zi5);
        holder.tv_zi2_6 = (TextView) v.findViewById(R.id.mao_zi6);
        v.setTag(holder);
        holder = (ViewHolder) v.getTag();
        holder.tv_pin1_1.setText(bean.pin1_1);
        holder.tv_pin1_2.setText(bean.pin1_2);
        holder.tv_pin1_3.setText(bean.pin1_3);
        holder.tv_pin1_4.setText(bean.pin1_4);
        holder.tv_pin1_5.setText(bean.pin1_5);
        holder.tv_pin1_6.setText(bean.pin1_6);
        holder.tv_pin2_1.setText(bean.pin2_1);
        holder.tv_pin2_2.setText(bean.pin2_2);
        holder.tv_pin2_3.setText(bean.pin2_3);
        holder.tv_pin2_4.setText(bean.pin2_4);
        holder.tv_pin2_5.setText(bean.pin2_5);
        holder.tv_pin2_6.setText(bean.pin2_6);
        Typeface tf = Typeface.createFromAsset(mBase.getAssets(),fontPath);
        holder.tv_zi1_1.setTypeface(tf);
        holder.tv_zi1_2.setTypeface(tf);
        holder.tv_zi1_3.setTypeface(tf);
        holder.tv_zi1_4.setTypeface(tf);
        holder.tv_zi1_5.setTypeface(tf);
        holder.tv_zi1_6.setTypeface(tf);
        holder.tv_zi2_1.setTypeface(tf);
        holder.tv_zi2_2.setTypeface(tf);
        holder.tv_zi2_3.setTypeface(tf);
        holder.tv_zi2_4.setTypeface(tf);
        holder.tv_zi2_5.setTypeface(tf);
        holder.tv_zi2_6.setTypeface(tf);


        holder.tv_zi1_1.setText(bean.zi1_1);
        holder.tv_zi1_2.setText(bean.zi1_2);
        holder.tv_zi1_3.setText(bean.zi1_3);
        holder.tv_zi1_4.setText(bean.zi1_4);
        holder.tv_zi1_5.setText(bean.zi1_5);
        holder.tv_zi1_6.setText(bean.zi1_6);
        holder.tv_zi2_1.setText(bean.zi2_1);
        holder.tv_zi2_2.setText(bean.zi2_2);
        holder.tv_zi2_3.setText(bean.zi2_3);
        holder.tv_zi2_4.setText(bean.zi2_4);
        holder.tv_zi2_5.setText(bean.zi2_5);
        holder.tv_zi2_6.setText(bean.zi2_6);
       return v;
    }
    public class ViewHolder {
        public  TextView tv_pin1_1;
        public  TextView tv_pin1_2;
        public  TextView tv_pin1_3;
        public  TextView tv_pin1_4;
        public  TextView tv_pin1_5;
        public  TextView tv_pin1_6;
        public  TextView tv_pin2_1;
        public  TextView tv_pin2_2;
        public  TextView tv_pin2_3;
        public  TextView tv_pin2_4;
        public  TextView tv_pin2_5;
        public  TextView tv_pin2_6;
        public  TextView tv_zi1_1;
        public  TextView tv_zi1_2;
        public  TextView tv_zi1_3;
        public  TextView tv_zi1_4;
        public  TextView tv_zi1_5;
        public  TextView tv_zi1_6;
        public  TextView tv_zi2_1;
        public  TextView tv_zi2_2;
        public  TextView tv_zi2_3;
        public  TextView tv_zi2_4;
        public  TextView tv_zi2_5;
        public  TextView tv_zi2_6;
    }




}
