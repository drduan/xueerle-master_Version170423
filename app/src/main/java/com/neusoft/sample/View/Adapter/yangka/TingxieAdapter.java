package com.neusoft.sample.View.Adapter.yangka;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ncapdevi.sample.R;
import com.neusoft.sample.Ctrl.yangkangkang.TingXieAnswer_item;

import java.util.List;

/**
 * Created by 杨康 on 2016/7/15.
 */
public class TingxieAdapter extends BaseAdapter {
    private int selectedPosition;
    private List<TingXieAnswer_item> mlist;//用于保存传递进来的   数据
    //数据源与适配器关联

    private LayoutInflater mInflater;
    public TingxieAdapter(Context context, List<TingXieAnswer_item> list) {
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
        holder = new ViewHolder();
        View v = mInflater.inflate(R.layout.item_tingxie_listview,null);
        holder.tv1 = (TextView) v.findViewById(R.id.ting1);
        holder.tv2 = (TextView) v.findViewById(R.id.ting2);
        holder.tv3 = (TextView) v.findViewById(R.id.ting3); 
        holder.tv4 = (TextView) v.findViewById(R.id.ting4);
          v.setTag(holder);
        holder = (ViewHolder) v.getTag();
        TingXieAnswer_item bean = mlist.get(position);
          String []dd = bean.aa;
        Log.d("@@","d"+dd);

        holder.tv1.getPaint().setTypeface(Typeface.MONOSPACE);
        holder.tv2.getPaint().setTypeface(Typeface.MONOSPACE);
        holder.tv3.getPaint().setTypeface(Typeface.SERIF);
        holder.tv4.getPaint().setTypeface(Typeface.MONOSPACE);
        holder.tv1.setText(dd[0]);
        holder.tv2.setText(dd[1]);
        holder.tv3.setText(dd[2]);
        holder.tv4.setText(dd[3]);
       return v;
    }
    public class ViewHolder {
        public  TextView tv1;
        public  TextView tv2;
        public  TextView tv3; 
        public  TextView tv4;
        
    }
}
