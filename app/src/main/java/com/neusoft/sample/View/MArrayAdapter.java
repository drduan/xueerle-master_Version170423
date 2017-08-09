package com.neusoft.sample.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ncapdevi.sample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/28.
 */
public class MArrayAdapter extends BaseAdapter{
    Context context;
    ArrayList spiner_province_list;

    LayoutInflater inflater;
    private List<List> list_city;
    private List list_p;
    public MArrayAdapter(Context context, ArrayList spiner_province_list){
        this.context=context;
        this.spiner_province_list=spiner_province_list;

        inflater=LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return spiner_province_list.size();
    }

    @Override
    public Object getItem(int position) {
        return spiner_province_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        String province= (String) spiner_province_list.get(position);

        View view=inflater.inflate(R.layout.support_simple_spinner_dropdown_item,null);
        TextView textView= (TextView) view.findViewById(R.id.register_sponer_tv_id);
        textView.setText(province);
//去除点击的数据
        return view;
    }



}
