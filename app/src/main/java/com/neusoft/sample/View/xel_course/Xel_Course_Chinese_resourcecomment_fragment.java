package com.neusoft.sample.View.xel_course;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ncapdevi.sample.R;

/**
 * Created by Administrator on 2016/6/25.
 */
public class Xel_Course_Chinese_resourcecomment_fragment extends Fragment {
    //TODO
    /**
     * 做一些网络解析工作
     * 首先从服务器中获取数据
     * 其次是解析数据
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.xel_course_chinese_resourcecomment_fragment,null);
        return view;
    }
}
