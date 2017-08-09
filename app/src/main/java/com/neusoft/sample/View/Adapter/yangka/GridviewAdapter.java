package com.neusoft.sample.View.Adapter.yangka;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ncapdevi.sample.R;
import com.neusoft.sample.Ctrl.yangkangkang.gridview_bean;

import java.util.List;

/**
 * Created by 杨康 on 2016/7/15.
 */
public class GridviewAdapter extends BaseAdapter {
    String fontPath = "ttf.ttf";
    Context mBase;
    private int selectedPosition;
    private List<gridview_bean> mlist;//用于保存传递进来的   数据
    //数据源与适配器关联

    private LayoutInflater mInflater;
    public GridviewAdapter(Context context,List<gridview_bean> list) {
        mBase =context;
            mlist=list;
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
        ViewHolder holder;
        Typeface tf = Typeface.createFromAsset(mBase.getAssets(),fontPath);
        holder = new ViewHolder();
        View v = mInflater.inflate(R.layout.gridview_item,null);
        holder.tv_chengyu = (TextView) v.findViewById(R.id.body_all);
        v.setTag(holder);
        holder = (ViewHolder) v.getTag();
        gridview_bean bean = mlist.get(position);
        holder.tv_chengyu.setText(bean.Itemchengyu);
        if(selectedPosition==position){
            holder.tv_chengyu.setTypeface(tf);
            holder.tv_chengyu.setTextColor(Color.parseColor("#fb0404"));
            holder.tv_chengyu.setTextSize(22);
        }else{
            holder.tv_chengyu.setTypeface(tf);
            holder.tv_chengyu.setTextColor(Color.parseColor("#1f1f1f"));
            holder.tv_chengyu.setTextSize(18);
        }
       return v;
    }
    public class ViewHolder {
        public  TextView tv_chengyu;
    }
}
