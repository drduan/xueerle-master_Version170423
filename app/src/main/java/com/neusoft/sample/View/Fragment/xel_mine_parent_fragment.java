package com.neusoft.sample.View.Fragment;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.ncapdevi.sample.R;
import com.neusoft.sample.App;
import com.neusoft.sample.Ctrl.Db_UserService;
import com.neusoft.sample.Ctrl.wenchengcheng.CheckForUpdateService;
import com.neusoft.sample.Ctrl.wenchengcheng.RoundImageView;
import com.neusoft.sample.GreenDao.User;
import com.neusoft.sample.Model.Constant;
import com.neusoft.sample.Model.MsharedPrefrence;
import com.neusoft.sample.Model.Post_Pushwork;
import com.neusoft.sample.Model.Post_learn_rijiyuelei;
import com.neusoft.sample.View.xel_mine.MyHomeWork.MyHomeWork;
import com.neusoft.sample.View.xel_mine.MyHomeWork.get_tingxie;
import com.neusoft.sample.View.xel_mine.Xel_mine_bibeigushicimulu;
import com.neusoft.sample.View.xel_mine.Xel_mine_feedback;
import com.neusoft.sample.View.xel_mine.Xel_mine_learntrack.Xel_mine_learntrack;
import com.neusoft.sample.View.xel_mine.Xel_mine_notify.Xel_mine_notify_main;
import com.neusoft.sample.View.xel_mine.Xel_mine_ranklist;
import com.neusoft.sample.View.xel_mine.Xel_mine_techangfudao;
import com.neusoft.sample.View.xel_mine.Xel_mine_tuozhangushicimulu;
import com.neusoft.sample.View.xel_mine.Xel_mine_youxiuzuowenshangxi;
import com.neusoft.sample.View.xel_mine.xel_mine_studentmypage;
import com.neusoft.sample.util.CheckProductionVersion;
import com.neusoft.sample.util.ContextHolder;
import com.neusoft.sample.util.ExitApplicationAction;
import com.neusoft.sample.util.SetNetworkImgToImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static com.neusoft.sample.View.BaseFragment.ARGS_INSTANCE;

public class xel_mine_parent_fragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    public static final String TAG = "parent_fragment";
    private LinearLayout header_ly,coach_ly,notify_ly,rankList_ly,courseBuy_ly,learnTrack_ly,necessaryPoem_ly,
            expandPoem,passageReview_ly,bookReview_ly,bookRecommend_ly,checkHomeWork_ly,feedback_ly,
            personalSetting_ly,checkForUpdate_ly,exit_ly;
    private TextView userName_tv,motto_tv,version_tv;
    private RoundImageView userAvatar_iv;
    private ToggleButton recitePermission_tg;
    public static String cUserName,cUserIconUrl,cUserIconUri,cMotto,cPhone;
    private  String  power;

    private  Handler handlerpermission;

    public static xel_mine_parent_fragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        xel_mine_parent_fragment fragment = new xel_mine_parent_fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        queryUserInfo();
    }

    @Override
    public void onResume() {
        super.onResume();
        queryUserInfo();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.xel_mine_parent_fragment, container, false);
        handlePermission();


        initView(v);

        initData();

        return v;
    }

    private void handlePermission() {
        final Thread thread;
        final String[] powersi = new String[1];
        String aa =  null;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                get_tingxie get_tingxie = new get_tingxie();
                HashMap hashMap = new HashMap();
                hashMap.put("user_id", App.newInstance().GetSharePrefrence_kejiezu(getContext()));
                try {
                    Log.d("@@", "user_id" +  App.newInstance().GetSharePrefrence_kejiezu(getContext()));
                    String responsee = Post_learn_rijiyuelei.getStringCha(Constant.findpower, hashMap);
                    Log.d("@@", "respose" + responsee);
                    JSONObject jsonObjec = null;
                    jsonObjec = new JSONObject(responsee);
                    String power = null;
                    power = jsonObjec.getString("data");
                    Message message=new Message();
                    Bundle bundle=new Bundle();
                    bundle.putString("power",power);
                    message.setData(bundle);
                    handlerpermission.sendMessage(message);

                } catch (IOException e) {
                    Log.d("@@","catch1"+e);
                    e.printStackTrace();
                } catch (JSONException e) {
                    Log.d("@@","catch2");
                    e.printStackTrace();
                }
            }
        };
        thread=new Thread(runnable);
        thread.start();
        handlerpermission=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                powersi[0] =msg.getData().getString("power");
                if("0".equals(powersi[0])) {
                    System.out.println("00000");
                    recitePermission_tg.setChecked(true);
                }else {
                    System.out.println("11111");
                    recitePermission_tg.setChecked(false);
                }
                thread.interrupt();
            }
        };

    }

    private void initData() {
        queryUserInfo();

    }

    private void initView(View v) {

        header_ly = (LinearLayout) v.findViewById(R.id.mine_parent_header_ly);
        coach_ly = (LinearLayout) v.findViewById(R.id.mine_parent_coach_ly);
        notify_ly = (LinearLayout) v.findViewById(R.id.mine_parent_notify_ly);
        rankList_ly = (LinearLayout) v.findViewById(R.id.mine_parent_rankList_ly);
        checkHomeWork_ly = (LinearLayout) v.findViewById(R.id.mine_parent_checkHomeWork_ly);
        learnTrack_ly = (LinearLayout) v.findViewById(R.id.mine_parent_learnTrack_ly);
        courseBuy_ly = (LinearLayout) v.findViewById(R.id.mine_parent_courseBuy_ly);
        necessaryPoem_ly = (LinearLayout) v.findViewById(R.id.mine_parent_necessaryPoem_ly);
        expandPoem = (LinearLayout) v.findViewById(R.id.mine_parent_expandPoem_ly);
        passageReview_ly = (LinearLayout) v.findViewById(R.id.mine_parent_passageReview_ly);
        bookReview_ly = (LinearLayout) v.findViewById(R.id.mine_parent_bookReview_ly);
        bookRecommend_ly = (LinearLayout) v.findViewById(R.id.mine_parent_bookRecommend_ly);
        feedback_ly = (LinearLayout) v.findViewById(R.id.mine_parent_feedback_ly);
        personalSetting_ly = (LinearLayout) v.findViewById(R.id.mine_parent_personalSetting_ly);
        checkForUpdate_ly = (LinearLayout) v.findViewById(R.id.mine_parent_checkForUpdate_ly);
        exit_ly = (LinearLayout) v.findViewById(R.id.mine_parent_exit_ly);

        userName_tv = (TextView) v.findViewById(R.id.mine_parent_username_tv);
        motto_tv = (TextView) v.findViewById(R.id.mine_parent_motto_tv);
        version_tv = (TextView) v.findViewById(R.id.mine_parent_version_tv);
        userAvatar_iv = (RoundImageView) v.findViewById(R.id.mine_parent_avatar_iv);
        recitePermission_tg = (ToggleButton) v.findViewById(R.id.mine_parent_recitePermission_tg);

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

        recitePermission_tg.setOnCheckedChangeListener(this);

    }

    private void queryUserInfo() {
        String Query_ID = App.newInstance().GetSharePrefrence_kejiezu(ContextHolder.getContext());//获取当前登陆用户的ID
        List<User> users = Db_UserService.getInstance(getActivity()).loadAllNote();
        User singleUser = null;

        for (User user : users) {
            if (user.getServer_id().equals(Query_ID)) {
                singleUser = user;
            }
        }

        if (singleUser != null) {
            cUserName = singleUser.getRecipient();
            cUserIconUrl = singleUser.getUser_icon_url();
            cMotto = singleUser.getMotto();
            try {
                new SetNetworkImgToImageView().SetNetworkImgToImageView(getActivity(),userAvatar_iv, Constant.selectUserMessage + "/" +cUserIconUrl);
                Log.d(TAG,"Img Url:"+Constant.selectUserMessage + "/" + cUserIconUrl);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG,"AvatarException:"+e);
            }
        }

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
            case R.id.mine_parent_header_ly:
                startActivity(new Intent(getContext(), xel_mine_studentmypage.class));
                break;
            case R.id.mine_parent_coach_ly:
                startActivity(new Intent(getContext(), Xel_mine_techangfudao.class));
                break;
            case R.id.mine_parent_notify_ly:
                Intent intent_notify = new Intent(getActivity(), Xel_mine_notify_main.class);
                intent_notify.putExtra("requestCode", "1");
                intent_notify.putExtra("isReadQuery", "1");
                startActivity(intent_notify);
                break;
            case R.id.mine_parent_rankList_ly:
                startActivity(new Intent(getContext(), Xel_mine_ranklist.class));
                break;
            case R.id.mine_parent_courseBuy_ly:
                Toast.makeText(getContext(),"功能未开放,尽情期待!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.mine_parent_checkHomeWork_ly:
                startActivity(new Intent(getContext(), MyHomeWork.class));
                break;
            case R.id.mine_parent_learnTrack_ly:
                startActivity(new Intent(getContext(), Xel_mine_learntrack.class));
                break;
            case R.id.mine_parent_necessaryPoem_ly:
                startActivity(new Intent(getContext(), Xel_mine_bibeigushicimulu.class));
                break;
            case R.id.mine_parent_expandPoem_ly:
                startActivity(new Intent(getContext(), Xel_mine_tuozhangushicimulu.class));
                break;
            case R.id.mine_parent_passageReview_ly:
                Intent passageReviewIntent = new Intent(getActivity(), Xel_mine_youxiuzuowenshangxi.class);
                passageReviewIntent.setFlags(1);
                startActivity(passageReviewIntent);
                break;
            case R.id.mine_parent_bookReview_ly:
                Intent bookReviewIntent = new Intent(getActivity(), Xel_mine_youxiuzuowenshangxi.class);
                bookReviewIntent.setFlags(2);
                startActivity(bookReviewIntent);
                break;
            case R.id.mine_parent_bookRecommend_ly:
                Intent bookRecommendIntent = new Intent(getActivity(), Xel_mine_youxiuzuowenshangxi.class);
                bookRecommendIntent.setFlags(0);
                startActivity(bookRecommendIntent);
                break;
            case R.id.mine_parent_feedback_ly:
                startActivity(new Intent(getContext(), Xel_mine_feedback.class));
                break;
            case R.id.mine_parent_personalSetting_ly:
                startActivity(new Intent(getContext(), xel_mine_studentmypage.class));
                break;
            case R.id.mine_parent_checkForUpdate_ly:
                new CheckForUpdateService().CheckForUpdateService(getContext());
                break;
            case R.id.mine_parent_exit_ly:
                new ExitApplicationAction(getContext(),getActivity());
                break;
        }

    }

    /**
     * Called when the checked state of a compound button has changed.
     *
     * @param buttonView The compound button view whose state has changed.
     * @param isChecked  The new checked state of buttonView.
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        final HashMap hashMap=new HashMap();
        //0 表示开 1表示关
        if (isChecked==true)
        {

            System.out.println("你老子已经把它开了，你可以看单词列表了");
            hashMap.clear();
            hashMap.put("power","0");
            String phone= MsharedPrefrence.Getphonewsd(getContext())[0];
            hashMap.put("phone",phone);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String respones= Post_Pushwork.getStringCha(Constant.editpower,hashMap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }else {
            System.out.println("你老子已经把它关了，你不能看单词列表了");
            hashMap.clear();
            hashMap.put("power","1");
            String phone= MsharedPrefrence.Getphonewsd(getContext())[0];
            hashMap.put("phone",phone);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String respones= Post_Pushwork.getStringCha(Constant.editpower,hashMap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
