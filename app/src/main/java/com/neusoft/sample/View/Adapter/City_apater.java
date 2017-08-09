package com.neusoft.sample.View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ncapdevi.sample.R;

import java.util.List;

/**
 * Created by Administrator on 2016/6/28.
 */
public class City_apater extends BaseAdapter {
    Context context;
    List list;
   LayoutInflater inflater;
    public City_apater(Context context, List list) {
        this.context = context;
        this.list = list;
        inflater=LayoutInflater.from(context);

    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String data= (String) list.get(position);
        View view =inflater.inflate(R.layout.xel_register_spiner_cutsomadapter_view,null);
        TextView tv= (TextView) view.findViewById(R.id.register_sponer_tv_id);
        tv.setText(data);
//去除点击的数据
        return view;
    }
}
