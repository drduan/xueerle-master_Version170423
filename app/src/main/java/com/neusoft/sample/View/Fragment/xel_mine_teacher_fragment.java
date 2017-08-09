package com.neusoft.sample.View.Fragment;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ncapdevi.sample.R;
import com.neusoft.sample.Ctrl.Db_TeacherService;
import com.neusoft.sample.Ctrl.wenchengcheng.CheckForUpdateService;
import com.neusoft.sample.Ctrl.wenchengcheng.RoundImageView;
import com.neusoft.sample.GreenDao.teacher;
import com.neusoft.sample.Model.MsharedPrefrence;
import com.neusoft.sample.View.users.BuyCourseActivity;
import com.neusoft.sample.View.xel_mine.MyHomeWork.MyWorkActivity;
import com.neusoft.sample.View.xel_mine.Xel_mine_bibeigushicimulu;
import com.neusoft.sample.View.xel_mine.Xel_mine_feedback;
import com.neusoft.sample.View.xel_mine.Xel_mine_notify.Xel_mine_notify_main;
import com.neusoft.sample.View.xel_mine.Xel_mine_ranklist;
import com.neusoft.sample.View.xel_mine.Xel_mine_teachermypage;
import com.neusoft.sample.View.xel_mine.Xel_mine_techangfudao;
import com.neusoft.sample.View.xel_mine.Xel_mine_tuozhangushicimulu;
import com.neusoft.sample.View.xel_mine.Xel_mine_youxiuzuowenshangxi;
import com.neusoft.sample.util.CheckProductionVersion;
import com.neusoft.sample.util.ExitApplicationAction;

import java.util.List;

import static com.neusoft.sample.View.BaseFragment.ARGS_INSTANCE;

public class xel_mine_teacher_fragment extends Fragment implements View.OnClickListener{

    public static final String TAG = "teacher_fragment";
    private LinearLayout header_ly,coach_ly,notify_ly,rankList_ly,courseBuy_ly,necessaryPoem_ly,
            expandPoem,passageReview_ly,bookReview_ly,bookRecommend_ly,publishWork_ly,feedback_ly,
            personalSetting_ly,checkForUpdate_ly,exit_ly;
    private TextView userName_tv,motto_tv,version_tv;
    private RoundImageView userAvatar_iv;
    public static String cUserName,cUserIconUrl,cUserIconUri,cMotto,cPhone;

    public static xel_mine_teacher_fragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        xel_mine_teacher_fragment fragment = new xel_mine_teacher_fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.xel_mine_teacher_fragment, container, false);

        initView(v);
        initData();

        return v;
    }

    private void initData() {
        queryUserInfo();

    }

    private void initView(View v) {

        header_ly = (LinearLayout) v.findViewById(R.id.mine_teacher_header_ly);
        coach_ly = (LinearLayout) v.findViewById(R.id.mine_teacher_coach_ly);
        notify_ly = (LinearLayout) v.findViewById(R.id.mine_teacher_notify_ly);
        rankList_ly = (LinearLayout) v.findViewById(R.id.mine_teacher_rankList_ly);
        courseBuy_ly = (LinearLayout) v.findViewById(R.id.mine_teacher_courseBuy_ly);
        necessaryPoem_ly = (LinearLayout) v.findViewById(R.id.mine_teacher_necessaryPoem_ly);
        expandPoem = (LinearLayout) v.findViewById(R.id.mine_teacher_expandPoem_ly);
        passageReview_ly = (LinearLayout) v.findViewById(R.id.mine_teacher_passageReview_ly);
        bookReview_ly = (LinearLayout) v.findViewById(R.id.mine_teacher_bookReview_ly);
        bookRecommend_ly = (LinearLayout) v.findViewById(R.id.mine_teacher_bookRecommend_ly);
        publishWork_ly = (LinearLayout) v.findViewById(R.id.mine_teacher_publishHomeWork_ly);
        feedback_ly = (LinearLayout) v.findViewById(R.id.mine_teacher_feedback_ly);
        personalSetting_ly = (LinearLayout) v.findViewById(R.id.mine_teacher_personalSetting_ly);
        checkForUpdate_ly = (LinearLayout) v.findViewById(R.id.mine_teacher_checkForUpdate_ly);
        exit_ly = (LinearLayout) v.findViewById(R.id.mine_teacher_exit_ly);

        userName_tv = (TextView) v.findViewById(R.id.mine_teacher_username_tv);
        motto_tv = (TextView) v.findViewById(R.id.mine_teacher_motto_tv);
        version_tv = (TextView) v.findViewById(R.id.mine_teacher_version_tv);
        userAvatar_iv = (RoundImageView) v.findViewById(R.id.mine_teacher_avatar_iv);

        header_ly.setOnClickListener(this);
        coach_ly.setOnClickListener(this);
        notify_ly.setOnClickListener(this);
        rankList_ly.setOnClickListener(this);
        courseBuy_ly.setOnClickListener(this);
        necessaryPoem_ly.setOnClickListener(this);
        expandPoem.setOnClickListener(this);
        passageReview_ly.setOnClickListener(this);
        bookReview_ly.setOnClickListener(this);
        bookRecommend_ly.setOnClickListener(this);
        publishWork_ly.setOnClickListener(this);
        feedback_ly.setOnClickListener(this);
        personalSetting_ly.setOnClickListener(this);
        checkForUpdate_ly.setOnClickListener(this);
        exit_ly.setOnClickListener(this);

    }

    private void queryUserInfo() {
        List<teacher> teachers = Db_TeacherService.getInstance(getActivity()).loadAllNote();
        teacher singleTeacher = teachers.get(0);

        cUserName = singleTeacher.getNickName();
        cUserIconUrl = singleTeacher.getTeacher_icon_url();
        cMotto = singleTeacher.getMotto();
        cPhone = MsharedPrefrence.Getphonewsd(getActivity())[0];
        if (cUserName!=null) userName_tv.setText(cUserName);
        else if (cPhone!=null) userName_tv.setText(cPhone);

        if (cMotto!=null) motto_tv.setText(cMotto);
        else motto_tv.setText("无");

        try {
            version_tv.setText("v"+new CheckProductionVersion().CheckProductionVersion(getActivity()));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            version_tv.setText("unknown");
            Log.d(TAG,"检测版本更新失败:"+e);
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.mine_teacher_header_ly:
                startActivity(new Intent(getContext(), Xel_mine_teachermypage.class));
                break;
            case R.id.mine_teacher_coach_ly:
                startActivity(new Intent(getContext(), Xel_mine_techangfudao.class));
                break;
            case R.id.mine_teacher_notify_ly:
                Intent intent_notify = new Intent(getActivity(), Xel_mine_notify_main.class);
                intent_notify.putExtra("requestCode", "1");
                intent_notify.putExtra("isReadQuery", "1");
                startActivity(intent_notify);
                break;
            case R.id.mine_teacher_rankList_ly:
                startActivity(new Intent(getContext(), Xel_mine_ranklist.class));
                break;
            case R.id.mine_teacher_courseBuy_ly:
//                Toast.makeText(getContext(),"功能未开放,尽情期待!",Toast.LENGTH_SHORT).show();
//                2017-05-16
                startActivity(new Intent(getContext(), BuyCourseActivity.class));
                break;

            case R.id.mine_teacher_necessaryPoem_ly:
                startActivity(new Intent(getContext(), Xel_mine_bibeigushicimulu.class));
                break;
            case R.id.mine_teacher_expandPoem_ly:
                startActivity(new Intent(getContext(), Xel_mine_tuozhangushicimulu.class));
                break;
            case R.id.mine_teacher_passageReview_ly:
                Intent passageReviewIntent = new Intent(getActivity(), Xel_mine_youxiuzuowenshangxi.class);
                passageReviewIntent.setFlags(1);
                startActivity(passageReviewIntent);
                break;
            case R.id.mine_teacher_bookReview_ly:
                Intent bookReviewIntent = new Intent(getActivity(), Xel_mine_youxiuzuowenshangxi.class);
                bookReviewIntent.setFlags(2);
                startActivity(bookReviewIntent);
                break;
            case R.id.mine_teacher_bookRecommend_ly:
                Intent bookRecommendIntent = new Intent(getActivity(), Xel_mine_youxiuzuowenshangxi.class);
                bookRecommendIntent.setFlags(0);
                startActivity(bookRecommendIntent);
                break;
            case R.id.mine_teacher_publishHomeWork_ly:
                startActivity(new Intent(getContext(), MyWorkActivity.class));
                break;
            case R.id.mine_teacher_feedback_ly:
                startActivity(new Intent(getContext(), Xel_mine_feedback.class));
                break;
            case R.id.mine_teacher_personalSetting_ly:
                startActivity(new Intent(getContext(), Xel_mine_teachermypage.class));
                break;
            case R.id.mine_teacher_checkForUpdate_ly:
                new CheckForUpdateService().CheckForUpdateService(getContext());
                break;
            case R.id.mine_teacher_exit_ly:
                new ExitApplicationAction(getContext(),getActivity());
                break;
        }

    }
}
