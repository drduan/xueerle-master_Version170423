package com.neusoft.sample.View.Adapter.yangka;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ncapdevi.sample.R;
import com.neusoft.sample.Ctrl.yangkangkang.listview_bean;

import java.util.List;

/**
 * Created by 杨康 on 2016/7/15.
 */
public class ListviewAdapter extends BaseAdapter {
    private List<listview_bean> mlist;//用于保存传递进来的   数据
    //数据源与适配器关联
    private LayoutInflater mInflater;
    public ListviewAdapter(Context context, List<listview_bean> list) {
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
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {//返回每一项的显示内容
        View v = mInflater.inflate(R.layout.listview_item,null);
        TextView tv_pinyin = (TextView) v.findViewById(R.id.shi_pinyin);
        TextView tv_shici = (TextView) v.findViewById(R.id.shi_shici);
        listview_bean bean = mlist.get(position);
        tv_pinyin.setText(bean.pinyin);
        tv_shici.setText(bean.shici);
       return v;
    }
}
