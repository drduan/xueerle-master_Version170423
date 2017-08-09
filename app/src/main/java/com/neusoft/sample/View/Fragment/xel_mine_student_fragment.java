package com.neusoft.sample.View.Fragment;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ncapdevi.sample.R;
import com.neusoft.sample.App;
import com.neusoft.sample.Ctrl.Db_UserService;
import com.neusoft.sample.Ctrl.wenchengcheng.CheckForUpdateService;
import com.neusoft.sample.Ctrl.wenchengcheng.RoundImageView;
import com.neusoft.sample.GreenDao.User;
import com.neusoft.sample.Model.Constant;
import com.neusoft.sample.Model.MsharedPrefrence;
import com.neusoft.sample.View.users.BuyCourseActivity;
import com.neusoft.sample.View.xel_mine.MyHomeWork.MyHomeWork;
import com.neusoft.sample.View.xel_mine.Xel_mine_bibeigushicimulu;
import com.neusoft.sample.View.xel_mine.Xel_mine_feedback;
import com.neusoft.sample.View.xel_mine.Xel_mine_notify.Xel_mine_notify_main;
import com.neusoft.sample.View.xel_mine.Xel_mine_ranklist;
import com.neusoft.sample.View.xel_mine.Xel_mine_techangfudao;
import com.neusoft.sample.View.xel_mine.Xel_mine_tuozhangushicimulu;
import com.neusoft.sample.View.xel_mine.Xel_mine_youxiuzuowenshangxi;
import com.neusoft.sample.View.xel_mine.xel_mine_studentmypage;
import com.neusoft.sample.util.CheckProductionVersion;
import com.neusoft.sample.util.ContextHolder;
import com.neusoft.sample.util.ExitApplicationAction;
import com.neusoft.sample.util.PostUserInfoInServlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static com.neusoft.sample.View.BaseFragment.ARGS_INSTANCE;

public class xel_mine_student_fragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "student_fragment";
    private LinearLayout header_ly, coach_ly, notify_ly, rankList_ly, courseBuy_ly, necessaryPoem_ly,
            expandPoem, passageReview_ly, bookReview_ly, bookRecommend_ly, checkHomeWork_ly, feedback_ly,
            personalSetting_ly, checkForUpdate_ly, exit_ly;
    private TextView userName_tv, motto_tv, version_tv;
    private RoundImageView userAvatar_iv;
    public static String cUserName, cUserIconUrl, cUserIconUri, cMotto, cPhone;
    private Handler bitmapHandler;
    private Bitmap bitmap;

    public static xel_mine_student_fragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        xel_mine_student_fragment fragment = new xel_mine_student_fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateUserInfo();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUserInfo();
    }

    private void updateUserInfo() {
        new PostUserInfoInServlet();
        queryUserInfo();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.xel_mine_student_fragment, container, false);

        initView(v);
        initData();

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

    private void initData() {
        queryUserInfo();

    }

    private void initView(View v) {

        header_ly = (LinearLayout) v.findViewById(R.id.mine_student_header_ly);
        coach_ly = (LinearLayout) v.findViewById(R.id.mine_student_coach_ly);
        notify_ly = (LinearLayout) v.findViewById(R.id.mine_student_notify_ly);
        rankList_ly = (LinearLayout) v.findViewById(R.id.mine_student_rankList_ly);
        checkHomeWork_ly = (LinearLayout) v.findViewById(R.id.mine_student_checkHomeWork_ly);
        courseBuy_ly = (LinearLayout) v.findViewById(R.id.mine_student_courseBuy_ly);
        necessaryPoem_ly = (LinearLayout) v.findViewById(R.id.mine_student_necessaryPoem_ly);
        expandPoem = (LinearLayout) v.findViewById(R.id.mine_student_expandPoem_ly);
        passageReview_ly = (LinearLayout) v.findViewById(R.id.mine_student_passageReview_ly);
        bookReview_ly = (LinearLayout) v.findViewById(R.id.mine_student_bookReview_ly);
        bookRecommend_ly = (LinearLayout) v.findViewById(R.id.mine_student_bookRecommend_ly);
        feedback_ly = (LinearLayout) v.findViewById(R.id.mine_student_feedback_ly);
        personalSetting_ly = (LinearLayout) v.findViewById(R.id.mine_student_personalSetting_ly);
        checkForUpdate_ly = (LinearLayout) v.findViewById(R.id.mine_student_checkForUpdate_ly);
        exit_ly = (LinearLayout) v.findViewById(R.id.mine_student_exit_ly);

        userName_tv = (TextView) v.findViewById(R.id.mine_student_username_tv);
        motto_tv = (TextView) v.findViewById(R.id.mine_student_motto_tv);
        version_tv = (TextView) v.findViewById(R.id.mine_student_version_tv);
        userAvatar_iv = (RoundImageView) v.findViewById(R.id.mine_student_avatar_iv);

        header_ly.setOnClickListener(this);
        coach_ly.setOnClickListener(this);
        notify_ly.setOnClickListener(this);
        rankList_ly.setOnClickListener(this);
        courseBuy_ly.setOnClickListener(this);
        checkHomeWork_ly.setOnClickListener(this);
        necessaryPoem_ly.setOnClickListener(this);
        expandPoem.setOnClickListener(this);
        passageReview_ly.setOnClickListener(this);
        bookReview_ly.setOnClickListener(this);
        bookRecommend_ly.setOnClickListener(this);
        feedback_ly.setOnClickListener(this);
        personalSetting_ly.setOnClickListener(this);
        checkForUpdate_ly.setOnClickListener(this);
        exit_ly.setOnClickListener(this);

    }

    private void queryUserInfo() {
        String Query_ID = App.newInstance().GetSharePrefrence_kejiezu(ContextHolder.getContext());//获取当前登陆用户的ID
        List<User> users = Db_UserService.getInstance(getActivity()).loadAllNote();
        User singleUser = null;

        for (User user : users) {
            if (user.getServer_id().equals(Query_ID)) {
                singleUser = user;
                Log.d(TAG,"用户信息:"+singleUser.toString()+"账号:"+MsharedPrefrence.Getphonewsd(getContext())[0]);
            }
        }

        if (singleUser != null) {
            cUserName = singleUser.getRecipient();
            cUserIconUrl = singleUser.getUser_icon_url();
            cMotto = singleUser.getMotto();
            if (cUserIconUrl!=null && cUserIconUrl.length()>1){
                Log.d(TAG,"用户有头像:"+cUserIconUrl+"账号:"+MsharedPrefrence.Getphonewsd(getContext())[0]);
                PostImg_set_save(Constant.selectUserMessage + "/" + cUserIconUrl,userAvatar_iv,cUserIconUrl,MsharedPrefrence.Getphonewsd(getContext())[0]);
            }else {
                userAvatar_iv.setImageResource(R.drawable.usericon_default);
                Log.d(TAG,"成功设置默认头像");
            }

        }

        cPhone = MsharedPrefrence.Getphonewsd(getActivity())[0];
        if (cUserName != null) userName_tv.setText(cUserName);
        else if (cPhone != null) userName_tv.setText(cPhone);

        if (cMotto != null) motto_tv.setText(cMotto);
        else motto_tv.setText("无");

        try {
            version_tv.setText("v" + new CheckProductionVersion().CheckProductionVersion(getActivity()));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            version_tv.setText("unknown");
            Log.d(TAG, "检测版本更新失败:" + e);
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.mine_student_header_ly:
                startActivity(new Intent(getContext(), xel_mine_studentmypage.class));
                break;
            case R.id.mine_student_coach_ly:
                startActivity(new Intent(getContext(), Xel_mine_techangfudao.class));
                break;
            case R.id.mine_student_notify_ly:
                Intent intent_notify = new Intent(getActivity(), Xel_mine_notify_main.class);
                intent_notify.putExtra("requestCode", "1");
                intent_notify.putExtra("isReadQuery", "1");
                startActivity(intent_notify);
                break;
            case R.id.mine_student_rankList_ly:
                startActivity(new Intent(getContext(), Xel_mine_ranklist.class));
                break;
            case R.id.mine_student_courseBuy_ly:
                //传递编号
                //Toast.makeText(getContext(), "功能未开放,尽情期待!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), BuyCourseActivity.class));
                break;
            case R.id.mine_student_checkHomeWork_ly:
                startActivity(new Intent(getContext(), MyHomeWork.class));
                break;
            case R.id.mine_student_necessaryPoem_ly:
                startActivity(new Intent(getContext(), Xel_mine_bibeigushicimulu.class));
                break;
            case R.id.mine_student_expandPoem_ly:
                startActivity(new Intent(getContext(), Xel_mine_tuozhangushicimulu.class));
                break;
            case R.id.mine_student_passageReview_ly:
                Intent passageReviewIntent = new Intent(getActivity(), Xel_mine_youxiuzuowenshangxi.class);
                passageReviewIntent.setFlags(1);
                startActivity(passageReviewIntent);
                break;
            case R.id.mine_student_bookReview_ly:
                Intent bookReviewIntent = new Intent(getActivity(), Xel_mine_youxiuzuowenshangxi.class);
                bookReviewIntent.setFlags(2);
                startActivity(bookReviewIntent);
                break;
            case R.id.mine_student_bookRecommend_ly:
                Intent bookRecommendIntent = new Intent(getActivity(), Xel_mine_youxiuzuowenshangxi.class);
                bookRecommendIntent.setFlags(0);
                startActivity(bookRecommendIntent);
                break;
            case R.id.mine_student_feedback_ly:
                startActivity(new Intent(getContext(), Xel_mine_feedback.class));
                break;
            case R.id.mine_student_personalSetting_ly:
                startActivity(new Intent(getContext(), xel_mine_studentmypage.class));
                break;
            case R.id.mine_student_checkForUpdate_ly:
                new CheckForUpdateService().CheckForUpdateService(getContext());
                break;
            case R.id.mine_student_exit_ly:
                new ExitApplicationAction(getContext(), getActivity());
                break;
        }
    }

    private void PostImg_set_save(final String postUrl, final ImageView imageView, final String imgName, final String phoneNum) {
        final String[] SavePath = {""};
        bitmapHandler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(postUrl); //path图片的网络地址

                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setConnectTimeout(5000);
                    if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                        bitmap = BitmapFactory.decodeStream(httpURLConnection.getInputStream());
                        bitmapHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                File appDir = new File(Environment.getExternalStorageDirectory(), "Xel_Resources");
                                if (!appDir.exists()) {
                                    appDir.mkdir();
                                }
                                String fileName = imgName;
                                Log.d(TAG, "fileName" + fileName);
                                File file = new File(appDir, fileName);
                                SavePath[0] = appDir + "/" + fileName + ".jpg";
                                Integer i = SavePath[0].length();
                                SavePath[0] = SavePath[0].substring(1, i - 4);
                                Log.d(TAG, "裁剪完后的Uri" + SavePath[0]);
                                MsharedPrefrence.SetUserTempImgDir(ContextHolder.getContext(), phoneNum, SavePath[0]);
                                String[] imgDir = MsharedPrefrence.GetUserUserTempImgDir(ContextHolder.getContext());
                                Log.d(TAG, "ImgDir(SP):" + imgDir[1]);

                                Log.d(TAG, "Astro");
                                try {
                                    FileOutputStream fos = new FileOutputStream(file);
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                                    fos.flush();
                                    fos.close();

                                    Uri uri = Uri.fromFile(new File(SavePath[0]));
                                    imageView.setImageURI(uri);

                                    Log.d(TAG, "已将图片保存至本地");
                                    Log.d(TAG, "本地图片路径" + SavePath[0]);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    //Toast.makeText(getContext(),"未将图片保存至本地",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Looper.prepare();
                        //Toast.makeText(getContext(),"未将图片保存至本地",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(TAG, "网络图片获取失败");
                    bitmap = null;
                }
            }
        }).start();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);

        switch (requestCode){

        }
    }
}