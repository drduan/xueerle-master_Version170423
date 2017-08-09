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
import com.neusoft.sample.Ctrl.yangkangkang.list_view_rijiyuelei_bean;

import java.util.List;

/**
 * Created by 杨康 on 2016/7/15.
 */
public class rijiyulei_GridviewAdapter extends BaseAdapter {
    String fontPath = "ttf.ttf";
    Context mBase;
    private int selectedPosition;
    private List<list_view_rijiyuelei_bean> mlist;//用于保存传递进来的   数据
    //数据源与适配器关联

    private LayoutInflater mInflater;
    public rijiyulei_GridviewAdapter(Context context, List<list_view_rijiyuelei_bean> list) {
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
        ViewHolder holder;
        holder = new ViewHolder();
        View v = mInflater.inflate(R.layout.item_rejiuyele_listview,null);
        holder.tv_chengyu = (TextView) v.findViewById(R.id.riji_body_all);
        holder.tv_num = (TextView) v.findViewById(R.id.rijiyuele);
        v.setTag(holder);
        holder = (ViewHolder) v.getTag();
        list_view_rijiyuelei_bean bean = mlist.get(position);
        Log.d("@@","changdu"+bean.Item_num.length());
        String item = null;
        if(bean.Item_num.length()==10){
          item = bean.Item_num.substring( bean.Item_num.length()-1,bean.Item_num.length());}
        else {
          item = bean.Item_num.substring( bean.Item_num.length()-2,bean.Item_num.length());
        }
        int a = bean.Item_rijiyuelei.indexOf("—");
        Typeface tf = Typeface.createFromAsset(mBase.getAssets(),fontPath);
        if(!(a==-1)){
            Log.d("@@","indexe"+a);
            String qian = bean.Item_rijiyuelei.substring(0,a);
            String hou =bean.Item_rijiyuelei.substring(a,bean.Item_rijiyuelei.length());
            hou = hou.replaceAll("—","");
            holder.tv_chengyu.setTypeface(tf);
            holder.tv_chengyu.setText(item+"."+qian+"\n"+hou);
        }else {
            holder.tv_chengyu.setTypeface(tf);
            holder.tv_chengyu.setText(item+"."+bean.Item_rijiyuelei); 
        }
        holder.tv_num.setTypeface(tf);
        holder.tv_num.setText(item+".");
        holder.tv_chengyu.setTypeface(tf);
        holder.tv_chengyu.setTextSize(18);
        holder.tv_num.setTextSize(18);
       return v;
    }
    public class ViewHolder {
        public  TextView tv_chengyu;
        public  TextView tv_num;
    }
}
