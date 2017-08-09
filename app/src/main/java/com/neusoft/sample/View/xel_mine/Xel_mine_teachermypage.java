package com.neusoft.sample.View.xel_mine;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.ncapdevi.sample.R;
import com.neusoft.sample.Ctrl.Db_TeacherService;
import com.neusoft.sample.Ctrl.wenchengcheng.Class_bean;
import com.neusoft.sample.Ctrl.wenchengcheng.ExitApplication;
import com.neusoft.sample.Ctrl.wenchengcheng.RoundImageView;
import com.neusoft.sample.Ctrl.wenchengcheng.verifyCameraPermission;
import com.neusoft.sample.Ctrl.wenchengcheng.verifyStoragePermissions;
import com.neusoft.sample.GreenDao.User;
import com.neusoft.sample.GreenDao.teacher;
import com.neusoft.sample.Model.MsharedPrefrence;
import com.neusoft.sample.View.BaseActivity;
import com.neusoft.sample.View.Fragment.xel_mine_fragment;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Xel_mine_teachermypage extends BaseActivity implements View.OnClickListener{

    public User cUserInfo = xel_mine_fragment.tUserInfo;
    LinearLayout userIcon_tag, motto_tag, userName_tag, subject_tag, gender_tag, QQID_tag, mail_tag, weixinID_tag, nickName_tag,realName_tag,score_tag,school_tag,grade_tag;
    TextView tv_motto, tv_userName, tv_gender, tv_subject, tv_qqNum, tv_mailUrl, tv_weixinNum, tv_nickName,tv_realName,tv_score,tv_school,tv_grade;
    ImageButton teacher_back;
    RoundImageView img_userIcon;
    ListView grade_tag_lv;
    List<Class_bean> grade_list = new ArrayList<>();
    List<Map<String, String>> contentList = new ArrayList<Map<String, String>>();
    public static String cUserName,cUserIconUrl,cUserIconUri,cMotto,cMailUrl,cWeiXinNum,cNickname,cQQNum,cUserGender,cScore,cSubject,cSchool,cGrade;
    public static String cUserUniqueID;
    public static int flag;
    private ImagePicker imagePicker;
    public int selectFlag;
    ProgressDialog uploadDialog;
    static String recode = "";
    Handler handler;
    String success;
    String[] phone=null;
    JSONObject json = null;
    String Rson = null;
    int queryCase = 0;// 0 无修改状态 / 1 用户修改文字信息 / 2 用户修改个人头像
    private String filePath = null;
    private int upload_state = 0;
    private static final int PHOTO_REQUEST = 1;
    private static final int CAMERA_REQUEST = 2;
    private static final int PHOTO_CLIP = 3;
    private ArrayList<ImageItem> images;
    private String phoneNum = null;
    private String fileName = null;
    private Uri muri;
    /* 头像名称 */
    Xel_mine_modifyInfo_popWindow popDialog_Info;
    Xel_mine_modifyUserIcon_popWindow popDialog_UserIcon;
    Xel_mine_modifyGender_popWindow popDialog_Gender;
    public static final String TAG = "xel_mine_teachermypage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xel_mine_teachermypage);
        ExitApplication.getInstance().addActivity(this);
        verifyStoragePermissions.verifyStoragePermissions(this);
        verifyCameraPermission.verifyCameraPermissions(this);
        phone = MsharedPrefrence.Getphonewsd(this);
        phoneNum = phone[0];
        initData();
        init();

        Log.d(TAG, String.valueOf(cUserInfo));
        teacher_back = (ImageButton) findViewById(R.id.teacher_back);
        teacher_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
                onResume();
            }
        });
        Ini_view_image();
        setViewContent();
    }

    private void initData() {

        List<teacher> teachers = Db_TeacherService.getInstance(Xel_mine_teachermypage.this).loadAllNote();
        teacher singleTeacher = teachers.get(0);

        cUserName = singleTeacher.getTeacherName();
        cUserIconUrl = singleTeacher.getTeacher_icon_url();
        cMailUrl = singleTeacher.getEmail();
        cMotto = singleTeacher.getMotto();
        cWeiXinNum = singleTeacher.getWeChatNum();
        cNickname = singleTeacher.getNickName();
        cQQNum = singleTeacher.getQqNum();
        cUserGender = singleTeacher.getSex();
        cSchool = singleTeacher.getSchoolName();
        cSubject = singleTeacher.getSubjectName();
        cScore = singleTeacher.getUserIntegral();

        Class_bean c;
        if (singleTeacher.getClassNum1()!=null) {
            c = new Class_bean();
            c.setClassName(singleTeacher.getClassName1());
            c.setClassNum(singleTeacher.getClassNum1());
            c.setPeopleBuy(singleTeacher.getPeopleBuy1());
            c.setPeopleRegister(singleTeacher.getPeopleRegister1());
            c.setPeopleNum(singleTeacher.getPeopleNum1());
            grade_list.add(c);
        }
        if (singleTeacher.getClassNum2()!=null){
            c = new Class_bean();
            c.setClassName(singleTeacher.getClassName2());
            c.setClassNum(singleTeacher.getClassNum2());
            c.setPeopleBuy(singleTeacher.getPeopleBuy2());
            c.setPeopleRegister(singleTeacher.getPeopleRegister2());
            c.setPeopleNum(singleTeacher.getPeopleNum2());
            grade_list.add(c);
        }
        if (singleTeacher.getClassNum3()!=null){
            c = new Class_bean();
            c.setClassName(singleTeacher.getClassName3());
            c.setClassNum(singleTeacher.getClassNum3());
            c.setPeopleBuy(singleTeacher.getPeopleBuy3());
            c.setPeopleRegister(singleTeacher.getPeopleRegister3());
            c.setPeopleNum(singleTeacher.getPeopleNum3());
            grade_list.add(c);
        }
        if (singleTeacher.getClassNum4()!=null){
            c = new Class_bean();
            c.setClassName(singleTeacher.getClassName4());
            c.setClassNum(singleTeacher.getClassNum4());
            c.setPeopleBuy(singleTeacher.getPeopleBuy4());
            c.setPeopleRegister(singleTeacher.getPeopleRegister4());
            c.setPeopleNum(singleTeacher.getPeopleNum4());
            grade_list.add(c);
        }
        contentList = setClassDetail(grade_list);
    }

    private void init() {
        img_userIcon = (RoundImageView) findViewById(R.id.tea_img_userIcon);
        tv_userName = (TextView) findViewById(R.id.tea_tv_userID);
        tv_gender = (TextView) findViewById(R.id.tea_tv_gender);
        tv_subject = (TextView) findViewById(R.id.tea_tv_subject);
        tv_qqNum = (TextView) findViewById(R.id.tea_tv_qqNum);
        tv_mailUrl = (TextView) findViewById(R.id.tea_tv_mailUrl);
        tv_weixinNum = (TextView) findViewById(R.id.tea_tv_weixinNum);
        tv_motto = (TextView) findViewById(R.id.tea_tv_motto);
        tv_nickName = (TextView) findViewById(R.id.tea_tv_nickName);
        tv_realName = (TextView) findViewById(R.id.tea_tv_realName);
        tv_score = (TextView) findViewById(R.id.tea_tv_score);
        tv_school = (TextView) findViewById(R.id.tea_tv_school);
        tv_grade = (TextView) findViewById(R.id.tea_tv_grade);
        grade_tag_lv = (ListView) findViewById(R.id.tea_lv_grade);

        userIcon_tag = (LinearLayout) findViewById(R.id.tea_userIcon_tag);
        motto_tag = (LinearLayout) findViewById(R.id.tea_motto_tag);
        userName_tag = (LinearLayout) findViewById(R.id.tea_userID_tag);
        subject_tag = (LinearLayout) findViewById(R.id.tea_subject_tag);
        gender_tag = (LinearLayout) findViewById(R.id.tea_gender_tag);
        QQID_tag = (LinearLayout) findViewById(R.id.tea_QQID_tag);
        mail_tag = (LinearLayout) findViewById(R.id.tea_mail_tag);
        weixinID_tag = (LinearLayout) findViewById(R.id.tea_weinxinID_tag);
        nickName_tag = (LinearLayout) findViewById(R.id.tea_nickName_tag);
        realName_tag = (LinearLayout) findViewById(R.id.tea_realName_tag);
        score_tag = (LinearLayout) findViewById(R.id.tea_score_tag);
        school_tag = (LinearLayout) findViewById(R.id.tea_school_tag);
        grade_tag = (LinearLayout) findViewById(R.id.tea_grade_tag);

        tv_userName.setText(isNullConvert(cUserName));
        tv_gender.setText(isNullConvert(cUserGender));
        tv_qqNum.setText(isNullConvert(cQQNum));
        tv_subject.setText(isNullConvert(cSubject));
        tv_mailUrl.setText(isNullConvert(cMailUrl));
        tv_weixinNum.setText(isNullConvert(cWeiXinNum));
        tv_motto.setText(isNullConvert(cMotto));
        tv_nickName.setText(isNullConvert(cNickname));
        tv_realName.setText(isNullConvert(phoneNum));
        tv_score.setText(isNullConvert(cScore));
        tv_school.setText(isNullConvert(cSchool));
        if (contentList.size()!=0){
            StringBuffer sb = new StringBuffer();
            for (int i=0;i<contentList.size();i++){
                sb.append(contentList.get(i).get("content"));
                sb.append("\n");
            }
                tv_grade.setText(sb);
        }
        //SimpleAdapter adapter = new SimpleAdapter(this,contentList,R.layout.xel_mine_teachermypage_list,new String[]{"content"},new int[]{R.id.class_detail});
        //grade_tag_lv.setAdapter(adapter);
        //setHeight(adapter);
        //grade_tag_lv.getMeasuredHeight();
        Log.d("xel_tea", String.valueOf(grade_tag_lv.getHeight())+"content:"+contentList);
        uploadDialog = new ProgressDialog(this);
        uploadDialog.setTitle("更新信息");
        uploadDialog.setMessage("正在上传用户信息，请稍后~");
    }

    private void setViewContent() {

    }

    private void Ini_view_image() {
    }

    private String isNullConvert(String content) {
        String inValidContent = "无";
        if (content == null) {
            return inValidContent;
        } else {
            return content;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.tea_userIcon_tag:
                break;

            case R.id.tea_motto_tag:
                break;

            case R.id.tea_userID_tag:
                break;

            case R.id.tea_gender_tag:
                break;

            case R.id.tea_QQID_tag:
                break;

            case R.id.tea_mail_tag:
                break;

            case R.id.tea_weinxinID_tag:
                break;

            case R.id.tea_nickName_tag:
                break;

            case R.id.tea_realName_tag:
                break;

            case R.id.tea_score_tag:
                break;

            case R.id.tea_school_tag:
                break;

            case R.id.tea_tv_grade:
                break;

        }
    }

    private List<Map<String, String>> setClassDetail(List<Class_bean> grade_list) {
        Map<String,String> detail;
        List<Map<String, String>> content = new ArrayList<Map<String, String>>();
        for (Class_bean single:grade_list){
            detail = new HashMap<>();
            detail.put("content",single.getClassName()+"("+single.getPeopleRegister()+"/"+single.getPeopleNum()+")");
            content.add(detail);
        }
        return content;
    }
    /*public void setHeight(SimpleAdapter comAdapter){
        int listViewHeight = 0;
        int adaptCount = comAdapter.getCount();
        //for(int i=0;i<adaptCount;i++){
        if (adaptCount!=0) {
            View temp = comAdapter.getView(1, null, grade_tag_lv);
            temp.measure(0, 0);
            listViewHeight = temp.getMeasuredHeight()*adaptCount*2;
            //}
        }
        ViewGroup.LayoutParams layoutParams = this.grade_tag_lv.getLayoutParams();
        layoutParams.height = listViewHeight;
        grade_tag_lv.setLayoutParams(layoutParams);
        grade_tag_lv.setDividerHeight(0);
        }*/


}
