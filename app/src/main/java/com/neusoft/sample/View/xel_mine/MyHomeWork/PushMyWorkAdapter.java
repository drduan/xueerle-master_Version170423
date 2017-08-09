package com.neusoft.sample.View.xel_mine.MyHomeWork;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ncapdevi.sample.R;

import java.util.List;

/**
 * Created by 杨康 on 2016/12/13.
 * 输入内容后添加添加调用adapt  添加到listview
 */

public class PushMyWorkAdapter extends BaseAdapter {
    private Context context;
    private List<PushIWorkListEntity> data;
    private Edit_or_Delete edit_or_delete;

    public PushMyWorkAdapter(Context context,List<PushIWorkListEntity> data){
        this.context=context;
        this.data=data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setEdit_or_delete(Edit_or_Delete edit_or_delete){
        this.edit_or_delete=edit_or_delete;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView=LayoutInflater.from(context).inflate(R.layout.pushmywork_custom_item, null);
            viewHolder=new ViewHolder();
            viewHolder.edit_push= (ImageView) convertView.findViewById(R.id.edit_push);
            viewHolder.indexname= (TextView) convertView.findViewById(R.id.indexname);
            viewHolder.textView=(TextView) convertView.findViewById(R.id.content_push);
            viewHolder.delete_push= (ImageView) convertView.findViewById(R.id.delete_push);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(data.get(position).getWorkname());
        viewHolder.indexname.setText(data.get(position).getItemno()+" ");
        edit_or_delete.dothis(viewHolder.edit_push,position,viewHolder.delete_push);

        return convertView;
    }
    class ViewHolder {

        TextView textView;
        TextView indexname;
        ImageView edit_push;
        ImageView delete_push;
    }

   public interface Edit_or_Delete{
        public void dothis(ImageView edit_push, int position, ImageView delete_push);
    }

}
