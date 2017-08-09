package com.neusoft.sample.View.xel_mine.MyHomeWork;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ncapdevi.sample.R;

import java.util.List;

/**
 * Created by Administrator on 2016/12/16.
 */
public class LookUpAdapter extends BaseAdapter{
    Context context;
    List<String> it;
    private TextView bianhao;

    private TextView contextid;
    public LookUpAdapter(Context context, List<String> it) {
        this.context=context;
        this.it=it;
    }

    @Override
    public int getCount() {
        return it.size();
    }

    @Override
    public Object getItem(int position) {
        return it.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView==null)
        {
            convertView= LayoutInflater.from(context).inflate(R.layout.lookupadapter_customview,null);
            bianhao = (TextView) convertView.findViewById(R.id.bianhao);
            contextid= (TextView) convertView.findViewById(R.id.contextid);
            convertView.setTag(convertView);
        }else {

            convertView= (View) convertView.getTag();
        }
        contextid.setText(it.get(position));

        return convertView;
    }
    public void setDate( List<String> it){
        this.it=it;

    }
}
