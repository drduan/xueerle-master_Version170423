package com.neusoft.sample.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ncapdevi.sample.R;
import com.neusoft.sample.App;
import com.neusoft.sample.Ctrl.Db_ErrorSubjectNub;
import com.neusoft.sample.Ctrl.Db_StudyGoodItemService;
import com.neusoft.sample.Ctrl.wenchengcheng.ExitApplication;
import com.neusoft.sample.Ctrl.wenchengcheng.verifyStoragePermissions;
import com.neusoft.sample.GreenDao.ErrorSubjectNub;
import com.neusoft.sample.GreenDao.StudyGoodItem;
import com.neusoft.sample.Model.Constant;
import com.neusoft.sample.Model.MsharedPrefrence;
import com.neusoft.sample.Model.Post_error_question;
import com.neusoft.sample.View.Fragment.xel_course_fragment;
import com.neusoft.sample.View.Fragment.xel_error_fragment;
import com.neusoft.sample.View.Fragment.xel_mine_parent_fragment;
import com.neusoft.sample.View.Fragment.xel_mine_student_fragment;
import com.neusoft.sample.View.Fragment.xel_mine_teacher_fragment;
import com.neusoft.sample.View.Fragment.xel_specialtopic_fragment;
import com.neusoft.sample.View.Fragment.xel_teacher_cuoti_fragment;
import com.neusoft.sample.util.PostUserInfoInServlet;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BottomTabSwitcherActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener{

    private static String TAG = "BottomTabSwitcherActivity";
    private ArrayList<Fragment> fragments;
    private static boolean isExit = false;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    protected synchronized void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApplication.getInstance().addActivity(this);
        setDarkStatusIcon(true);
        setContentView(R.layout.xel_bottom_tab_switcher);
        ProgressDialog dialog = ProgressDialog.show(this, "加载资源中。。。。。。", " ");
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);

        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.xel_kecheng_nopress_36dp, "课程").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.mipmap.xel_wrong_nopress_36dp, "错题").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.mipmap.xel_zhuanti_nopress_36dp, "专题").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.mipmap.xel_wo_nopress_36dp, "我的").setActiveColorResource(R.color.colorPrimary))
                .setFirstSelectedPosition(0)
                .initialise();

        fragments = getFragments();
        setDefaultFragment();
        bottomNavigationBar.setTabSelectedListener(this);
        dialog.dismiss();
        verifyStoragePermissions.verifyStoragePermissions(this);//检测用户是否打开存储权限


        final HashMap<String, String> map = new HashMap<>();
        String user_id = App.newInstance().GetSharePrefrence_kejiezu(this);
        map.put("user_id", user_id);//传递的值
        List<StudyGoodItem> studyGoodItemList = Db_StudyGoodItemService.getInstance(this).loadAllNote();

        List<HashMap<String, String>> stringList = new ArrayList<>();
        for (StudyGoodItem studyGoodItem : studyGoodItemList) {
            HashMap<String, String> map1 = new HashMap<>();
            if (!studyGoodItem.getBookNo().isEmpty()) {
                map1.put("tableName", studyGoodItem.getBookNo());
                stringList.add(map1);
            }
        }
        String JiaocaiSBNo = JSON.toJSONString(stringList);
        map.put("JiaocaiSBNo", JiaocaiSBNo);//TODO 更改其传递的值
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String response = Post_error_question.getStringCha(Constant.post_ErrorQuestion, map);
                    JSONObject jsonObject = new JSONObject(response);
                    List<ErrorSubjectNub> errorSubjectNubList = JSON.parseArray(jsonObject.get("data").toString(), ErrorSubjectNub.class);
                    List<ErrorSubjectNub> errorSubjectNubListnew = Db_ErrorSubjectNub.getInstance(BottomTabSwitcherActivity.this).loadAllNote();
                    if (errorSubjectNubList.size() != errorSubjectNubListnew.size()) {
                        Db_ErrorSubjectNub.getInstance(BottomTabSwitcherActivity.this).saveNoteLists(errorSubjectNubList);
                        Log.d("ErrorSubjectNub", "插入成功s！！！" + errorSubjectNubListnew.size());
                    }
                    Log.d("ErrorSubjectNub", "插入成功！！！" + errorSubjectNubListnew.size());

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.layFrame, xel_course_fragment.newInstance(0));
        transaction.commit();
    }

    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        String role[] = MsharedPrefrence.Getphonewsd(BottomTabSwitcherActivity.this);
        switch (role[2]) {

            case "0"://老师角色
                fragments.add(xel_course_fragment.newInstance(0));
                fragments.add(xel_teacher_cuoti_fragment.newInstance(1));
                fragments.add(xel_specialtopic_fragment.newInstance(2));
                fragments.add(xel_mine_teacher_fragment.newInstance(3));
                new PostUserInfoInServlet().Ini_Post_userInfo();
                break;
            case "1"://学生角色
                fragments.add(xel_course_fragment.newInstance(0));
                fragments.add(xel_error_fragment.newInstance(1));
                fragments.add(xel_specialtopic_fragment.newInstance(2));
                fragments.add(xel_mine_student_fragment.newInstance(3));
                new PostUserInfoInServlet().Ini_Post_userInfo();
                break;
            case "2"://家长角色
                fragments.add(xel_course_fragment.newInstance(0));
                fragments.add(xel_error_fragment.newInstance(1));
                fragments.add(xel_specialtopic_fragment.newInstance(2));
                fragments.add(xel_mine_parent_fragment.newInstance(3));
                break;

        }
        return fragments;
    }

    /**
     * Called when a tab enters the selected state.
     *
     * @param position The position of the tab that was selected
     */
    @Override
    public void onTabSelected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                String role[] = MsharedPrefrence.Getphonewsd(BottomTabSwitcherActivity.this);
                Log.d("@@","position"+position);
                if (fragment.isAdded()) {
                    switch (position) {
                        case 0:
                            break;
                        case 1:
                            Log.d("@@","position"+position);
                            final HashMap<String, String> map = new HashMap<>();
                            String user_id = App.newInstance().GetSharePrefrence_kejiezu(this);
                            map.put("user_id", user_id);//传递的值
                            List<StudyGoodItem> studyGoodItemList = Db_StudyGoodItemService.getInstance(this).loadAllNote();

                            List<HashMap<String, String>> stringList = new ArrayList<>();
                            for (StudyGoodItem studyGoodItem : studyGoodItemList) {
                                HashMap<String, String> map1 = new HashMap<>();
                                if (!studyGoodItem.getBookNo().isEmpty()) {
                                    map1.put("tableName", studyGoodItem.getBookNo());
                                    stringList.add(map1);
                                }
                            }
                            String JiaocaiSBNo = JSON.toJSONString(stringList);
                            map.put("JiaocaiSBNo", JiaocaiSBNo);//TODO 更改其传递的值
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        String response = Post_error_question.getStringCha(Constant.post_ErrorQuestion, map);
                                        JSONObject jsonObject = new JSONObject(response);
                                        List<ErrorSubjectNub> errorSubjectNubList = JSON.parseArray(jsonObject.get("data").toString(), ErrorSubjectNub.class);
                                        List<ErrorSubjectNub> errorSubjectNubListnew = Db_ErrorSubjectNub.getInstance(BottomTabSwitcherActivity.this).loadAllNote();
                                        if (errorSubjectNubList.size() != errorSubjectNubListnew.size()) {
                                            Db_ErrorSubjectNub.getInstance(BottomTabSwitcherActivity.this).saveNoteLists(errorSubjectNubList);
                                            Log.d("ErrorSubjectNub", "插入成功s！！！" + errorSubjectNubListnew.size());
                                        }
                                        Log.d("ErrorSubjectNub", "插入成功！！！" + errorSubjectNubListnew.size());

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();

                            break;
                        case 2:
                            break;
                        case 3:
                            if (role[2].equals("1")||role[2].equals("2")) new PostUserInfoInServlet().Ini_Post_userInfo();
                            break;
                    }
                    ft.replace(R.id.layFrame, fragment);
                } else {
                    switch (position) {
                        case 0:
                            break;
                        case 1:
                            final HashMap<String, String> map = new HashMap<>();
                            final List<ErrorSubjectNub> errorSubjectNubListnew = Db_ErrorSubjectNub.getInstance(BottomTabSwitcherActivity.this).loadAllNote();
                            String user_id = App.newInstance().GetSharePrefrence_kejiezu(this);
                            map.put("user_id", user_id);//传递的值

                            List<StudyGoodItem> studyGoodItemList = Db_StudyGoodItemService.getInstance(this).loadAllNote();

                            List<HashMap<String, String>> stringList = new ArrayList<>();
                            for (StudyGoodItem studyGoodItem : studyGoodItemList) {
                                HashMap<String, String> map1 = new HashMap<>();
                                if (!studyGoodItem.getBookNo().isEmpty()) {
                                    map1.put("tableName", studyGoodItem.getBookNo());
                                    stringList.add(map1);
                                }
                            }
                            final String JiaocaiSBNo = JSON.toJSONString(stringList);
                            Log.d("JiaocaiSBNo",JiaocaiSBNo+"--");
                            map.put("JiaocaiSBNo", JiaocaiSBNo);//TODO 更改其传递的值
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        String response = Post_error_question.getStringCha(Constant.post_ErrorQuestion, map);
                                        Log.d("responsepp",response+"--");
                                        JSONObject jsonObject = new JSONObject(response);
                                        List<ErrorSubjectNub> errorSubjectNubList = JSON.parseArray(jsonObject.get("data").toString(), ErrorSubjectNub.class);
                                        Log.d("数据库中的数据和后台请求的数据",errorSubjectNubList.size() +"--");
                                        if (errorSubjectNubList.size() != errorSubjectNubListnew.size()) {
                                            Db_ErrorSubjectNub.getInstance(BottomTabSwitcherActivity.this).saveNoteLists(errorSubjectNubList);
                                        }

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();

                            break;
                        case 2:
                            break;
                        case 3:
                            if (role[2].equals("1")||role[2].equals("2")) new PostUserInfoInServlet().Ini_Post_userInfo();
                            break;

                    }
                    ft.add(R.id.layFrame, fragment);
                }
                    ft.commitAllowingStateLoss();
                }

            }
        }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * Called when a tab exits the selected state.
     *
     * @param position The position of the tab that was unselected
     */
    @Override
    public void onTabUnselected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                ft.remove(fragment);
                ft.commitAllowingStateLoss();
            }
        }
    }

    /**
     * Called when a tab that is already selected is chosen again by the user. Some applications
     * may use this action to return to the top level of a category.
     *
     * @param position The position of the tab that was reselected.
     */
    @Override
    public void onTabReselected(int position) {
        String role[] = MsharedPrefrence.Getphonewsd(BottomTabSwitcherActivity.this);
        if (position==1){
            final HashMap<String, String> map = new HashMap<>();
            final List<ErrorSubjectNub> errorSubjectNubListnew = Db_ErrorSubjectNub.getInstance(BottomTabSwitcherActivity.this).loadAllNote();

            String user_id = App.newInstance().GetSharePrefrence_kejiezu(this);
            map.put("user_id", user_id);//传递的值

            List<StudyGoodItem> studyGoodItemList = Db_StudyGoodItemService.getInstance(this).loadAllNote();

            List<HashMap<String, String>> stringList = new ArrayList<>();
            for (StudyGoodItem studyGoodItem : studyGoodItemList) {
                HashMap<String, String> map1 = new HashMap<>();
                if (!studyGoodItem.getBookNo().isEmpty()) {
                    map1.put("tableName", studyGoodItem.getBookNo());
                    stringList.add(map1);
                }
            }
            final String JiaocaiSBNo = JSON.toJSONString(stringList);
            Log.d("JiaocaiSBNosss",JiaocaiSBNo+"--");
            map.put("JiaocaiSBNo", JiaocaiSBNo);//TODO 更改其传递的值
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String response = Post_error_question.getStringCha(Constant.post_ErrorQuestion, map);
                        Log.d("responsepp",response+"--");
                        JSONObject jsonObject = new JSONObject(response);
                        List<ErrorSubjectNub> errorSubjectNubList = JSON.parseArray(jsonObject.get("data").toString(), ErrorSubjectNub.class);
                        if (errorSubjectNubList.size() != errorSubjectNubListnew.size()) {
                            Db_ErrorSubjectNub.getInstance(BottomTabSwitcherActivity.this).saveNoteLists(errorSubjectNubList);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }else if (position==3){
            if (role[2].equals("1")||role[2].equals("2")) new PostUserInfoInServlet().Ini_Post_userInfo();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void setDarkStatusIcon(boolean bDark) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            View decorView = getWindow().getDecorView();
            if(decorView != null){
                int vis = decorView.getSystemUiVisibility();
                if(bDark){
                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else{
                    vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                decorView.setSystemUiVisibility(vis);
            }
        }
    }
}
