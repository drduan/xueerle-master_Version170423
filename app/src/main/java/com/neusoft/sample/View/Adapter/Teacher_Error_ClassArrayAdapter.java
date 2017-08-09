package com.neusoft.sample.View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ncapdevi.sample.R;
import com.neusoft.sample.Model.Teacher_Error_Class_Entity;

import java.util.List;

/**
 * Created by Administrator on 2016/12/24.
 */

public class Teacher_Error_ClassArrayAdapter extends ArrayAdapter {
    private Context context;
    private List<Teacher_Error_Class_Entity> teacher_error_class_entities;
    private   ViewHolder holder;

    public Teacher_Error_ClassArrayAdapter(Context context, List<Teacher_Error_Class_Entity> teacher_error_class_entities) {
        super(context,R.layout.xel_spiner_custom_view);
//        R.layout.xel_spiner_custom_view,
        this.context=context;
        this.teacher_error_class_entities=teacher_error_class_entities;
    }



    @Override
    public int getCount() {
        return teacher_error_class_entities.size();
    }

    @Override
    public Object getItem(int position) {
        return teacher_error_class_entities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null)
        {
//            R.layout.xel_spiner_custom_view

            convertView= LayoutInflater.from(context).inflate(R.layout.xel_spiner_custom_view,null);
           holder=new ViewHolder();


            holder.textView= (TextView) convertView.findViewById(R.id.register_sponer_tv_id);
            convertView.setTag(convertView);

        }else {
            convertView= (View) convertView.getTag();
        }

        holder.textView.setText(""+teacher_error_class_entities.get(position).getClassname());

        return convertView;
    }

    class ViewHolder{
        TextView textView;

    }


}
