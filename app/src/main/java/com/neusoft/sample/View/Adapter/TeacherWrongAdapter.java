package com.neusoft.sample.View.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ncapdevi.sample.R;
import com.neusoft.sample.Model.TeacherWrong_Entity;
import com.neusoft.sample.View.xel_error.TeacherWrongactivity;

import java.util.List;

/**
 * Created by Administrator on 2016/12/24.
 */
public class TeacherWrongAdapter extends BaseAdapter {

    private TeacherWrongactivity teacherWrongactivity;
    private List<TeacherWrong_Entity.DataBean> data;

    public TeacherWrongAdapter(TeacherWrongactivity teacherWrongactivity, List<TeacherWrong_Entity.DataBean> data) {
        this.teacherWrongactivity = teacherWrongactivity;
        this.data = data;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      HoderView hoderView = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(teacherWrongactivity).inflate(R.layout.teacherwrongadapteritemlayout, null,true);

            hoderView = new HoderView();
            hoderView.teacher_nub = (TextView) convertView.findViewById(R.id.teacher_nub);
            hoderView.teacher_content = (TextView) convertView.findViewById(R.id.teacher_content);

            hoderView.teacher_pepolesum = (TextView) convertView.findViewById(R.id.teacher_pepolesum);
            convertView.setTag(hoderView);
        }else {
            hoderView= (HoderView) convertView.getTag();
        }
        String questionnub=data.get(position).getTest_group_number();
        hoderView.teacher_nub.setText(""+questionnub.substring(8,10)+"--"+questionnub.substring(10));
        hoderView.teacher_content.setText(data.get(position).getStem()+"");

        hoderView.teacher_pepolesum.setText("["+data.get(position).getCount()+"]");

        return convertView;
    }

   private final class HoderView {
        TextView teacher_nub;
        TextView teacher_content;
        TextView teacher_pepolesum;

    }
}
