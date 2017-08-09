package com.neusoft.sample.View.xel_mine;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.ncapdevi.sample.R;
import com.neusoft.sample.Ctrl.Db_UserService;
import com.neusoft.sample.Ctrl.wenchengcheng.ExitApplication;
import com.neusoft.sample.Ctrl.wenchengcheng.RoundImageView;
import com.neusoft.sample.Ctrl.wenchengcheng.verifyCameraPermission;
import com.neusoft.sample.Ctrl.wenchengcheng.verifyStoragePermissions;
import com.neusoft.sample.GreenDao.User;
import com.neusoft.sample.Model.Constant;
import com.neusoft.sample.Model.MsharedPrefrence;
import com.neusoft.sample.Model.Post_UserInfo;
import com.neusoft.sample.Model.Post_learn_rijiyuelei;
import com.neusoft.sample.Model.mutils.ToastUtil;
import com.neusoft.sample.View.BaseActivity;
import com.neusoft.sample.View.Fragment.xel_mine_fragment;
import com.neusoft.sample.View.Fragment.xel_mine_parent_fragment;
import com.neusoft.sample.View.Fragment.xel_mine_student_fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class xel_mine_studentmypage extends BaseActivity {
    public User cUserInfo = xel_mine_fragment.tUserInfo;
    LinearLayout userIcon_tag, motto_tag, userID_tag, gender_tag, QQID_tag, mail_tag, weixinID_tag, recipient_tag, addressDetail_tag, phoneNum_tag, zipCode_tag;
    TextView tv_motto, tv_userID, tv_userName, tv_gender, tv_qqNum, tv_mailUrl, tv_weixinNum, tv_recipient, tv_address, tv_phone, tv_zipcode;
    ImageButton student_back;
    RoundImageView img_userIcon;
    public static String cUserID, cUserIconUrl,cUserIconUri, cMotto, cMailUrl, cWeiXinNum, cRecipient, cAddress, cQQNum, cPhone, cZipcode, cUserGender;
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
    public static final String TAG = "xel_mine_studentmypage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xel_mine_studentmypage);
        ExitApplication.getInstance().addActivity(this);
        verifyStoragePermissions.verifyStoragePermissions(this);
        verifyCameraPermission.verifyCameraPermissions(this);
        phone = MsharedPrefrence.Getphonewsd(this);
        phoneNum = phone[0];
        init();
        Log.d(TAG, String.valueOf(cUserInfo));
        student_back = (ImageButton) findViewById(R.id.student_back);
        student_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
                onResume();
            }
        });
        onLayoutClickListener();
        Ini_view_image();
        setViewContent();
    }

    private void init() {
        img_userIcon = (RoundImageView) findViewById(R.id.stu_img_userIcon);
        tv_userID = (TextView) findViewById(R.id.stu_tv_userID);
        tv_gender = (TextView) findViewById(R.id.stu_tv_gender);
        tv_qqNum = (TextView) findViewById(R.id.stu_tv_qqNum);
        tv_mailUrl = (TextView) findViewById(R.id.stu_tv_mailUrl);
        tv_weixinNum = (TextView) findViewById(R.id.stu_tv_weixinNum);
        tv_motto = (TextView) findViewById(R.id.stu_tv_motto);
        tv_recipient = (TextView) findViewById(R.id.stu_tv_recipient);
        tv_address = (TextView) findViewById(R.id.stu_tv_address);
        tv_phone = (TextView) findViewById(R.id.stu_tv_phone);
        tv_zipcode = (TextView) findViewById(R.id.stu_tv_zipcode);

        userIcon_tag = (LinearLayout) findViewById(R.id.stu_userIcon_tag);
        motto_tag = (LinearLayout) findViewById(R.id.stu_motto_tag);
        userID_tag = (LinearLayout) findViewById(R.id.stu_userID_tag);
        gender_tag = (LinearLayout) findViewById(R.id.stu_gender_tag);
        QQID_tag = (LinearLayout) findViewById(R.id.stu_QQID_tag);
        mail_tag = (LinearLayout) findViewById(R.id.stu_mail_tag);
        weixinID_tag = (LinearLayout) findViewById(R.id.stu_weinxinID_tag);
        recipient_tag = (LinearLayout) findViewById(R.id.stu_recipient_tag);

        addressDetail_tag = (LinearLayout) findViewById(R.id.stu_addressDetail_tag);
        phoneNum_tag = (LinearLayout) findViewById(R.id.stu_phoneNum_tag);
        zipCode_tag = (LinearLayout) findViewById(R.id.stu_zipCode_tag);

        uploadDialog = new ProgressDialog(this);
        uploadDialog.setTitle("更新信息");
        uploadDialog.setMessage("正在上传用户信息，请稍后~");
    }

    private void Ini_view_image() {
        String Uri_img = filePath;
        String[] img_info = MsharedPrefrence.GetUserUserTempImgDir(this);

        if (img_info[0] != null && img_info[1].length()>10){
            Log.d(TAG,"Ini_view_image"+img_info[1]+"用户有记录，并有头像路径:"+img_info[1]+"  "+img_info[0]);
            Bitmap bm = BitmapFactory.decodeFile(img_info[1]);
            img_userIcon.setImageBitmap(bm);
        }
        else{
            Log.d(TAG,"Ini_view_image"+"用户有记录，但无头像路径:"+img_info[1]+"  "+img_info[0]);
        }
        filePath = null;
    }

    private void setViewContent() {
        if (queryCase==0 || queryCase==1) {
            Drawable drawable = this.getResources().getDrawable(R.drawable.mylose);
            String[] ImgUri = MsharedPrefrence.GetUserUserTempImgDir(this);
            cUserUniqueID = cUserInfo.getServer_id();//NullPointer报空
            cMotto = cUserInfo.getMotto();
            cUserID = xel_mine_fragment.sPhone;
            Log.d(TAG, "cUserGender==" + cUserInfo.getGender());
            cUserGender = cUserInfo.getGender();
            cUserIconUri = ImgUri[1];
            Log.d(TAG,"ImgUri"+ImgUri[1]);
            cQQNum = String.valueOf(cUserInfo.getQq_Number());
            cMailUrl = cUserInfo.getEmail();
            cWeiXinNum = cUserInfo.getWeixin_number();
            cRecipient = cUserInfo.getRecipient();
            cAddress = cUserInfo.getAddress();
            cPhone = cUserInfo.getPhone();

            cZipcode = cUserInfo.getZip_code();
            if (cUserIconUri != null && cUserIconUri.length()>10) {
                Log.d("SP中存取的路径", cUserIconUri);
                Bitmap bm = BitmapFactory.decodeFile(cUserIconUri);
                img_userIcon.setImageBitmap(bm);
            } else {
                img_userIcon.setImageDrawable(drawable);
            }//1

            if (cMotto != null) {
                tv_motto.setText(cMotto);
            } else {
                tv_motto.setText("无");
            }//2

            if (cUserID != null) {
                tv_userID.setText(String.valueOf(cUserID));
            } else {
                tv_userID.setText("无");
            }//3

            if (cUserGender != null) {
                Log.d(TAG, "cGender" + cUserGender);
                if (cUserGender.equals("1")) {
                    tv_gender.setText("女");
                } else if (cUserGender.equals("0")) {
                    tv_gender.setText("男");
                }
            } else {
                tv_gender.setText("无");
            }//4

            if (cQQNum.equals("null")) {
                tv_qqNum.setText("无");
            } else {
                tv_qqNum.setText(cQQNum);
            }//5

            if (cMailUrl != null) {
                tv_mailUrl.setText(cMailUrl);
            } else {
                tv_mailUrl.setText("无");
            }//6

            if (cWeiXinNum != null) {
                tv_weixinNum.setText(cWeiXinNum);
            } else {
                tv_weixinNum.setText("无");
            }//7

            if (cRecipient != null) {
                tv_recipient.setText(cRecipient);
            } else {
                tv_recipient.setText("无");
            }//8

            if (cAddress != null) {
                tv_address.setText(cAddress);
            } else {
                tv_address.setText("无");
            }//9

            if (cPhone == null) {
                tv_phone.setText("无");
            } else {
                tv_phone.setText(cPhone);
            }//10

            if (cZipcode == null) {
                tv_zipcode.setText("无");
            } else {
                tv_zipcode.setText(cZipcode);
            }//11
        }
    }

    private void onLayoutClickListener() {
        MyLayoutListener listener = new MyLayoutListener();
        userIcon_tag.setTag(0);
        userIcon_tag.setOnClickListener(listener);
        motto_tag.setTag(1);
        motto_tag.setOnClickListener(listener);
        userID_tag.setTag(2);
        userID_tag.setOnClickListener(listener);
        gender_tag.setTag(3);
        gender_tag.setOnClickListener(listener);
        QQID_tag.setTag(4);
        QQID_tag.setOnClickListener(listener);
        mail_tag.setTag(5);
        mail_tag.setOnClickListener(listener);
        weixinID_tag.setTag(6);
        weixinID_tag.setOnClickListener(listener);
        recipient_tag.setTag(7);
        recipient_tag.setOnClickListener(listener);
        addressDetail_tag.setTag(8);
        addressDetail_tag.setOnClickListener(listener);
        phoneNum_tag.setTag(9);
        phoneNum_tag.setOnClickListener(listener);
        zipCode_tag.setTag(10);
        zipCode_tag.setOnClickListener(listener);
    }

    public void setUpload_state(int upload_state) {
        this.upload_state = upload_state;
    }

    public int getUpload_state() {
        return upload_state;
    }

    private class MyLayoutListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            flag = (int) v.getTag();
            switch (flag) {
                case 0:
                    UserIcon_popDialogDisplay();
                    break;
                case 1:
                    Info_popDialogDisplay(1);
                    break;
                case 3:
                    Gender_popDialogDisplay();
                    break;
                case 4:
                    Info_popDialogDisplay(4);
                    break;
                case 5:
                    Info_popDialogDisplay(5);
                    break;
                case 6:
                    Info_popDialogDisplay(6);
                    break;
                case 7:
                    Info_popDialogDisplay(7);
                    break;
                case 8:
                    Info_popDialogDisplay(8);
                    break;
                case 9:
                    Info_popDialogDisplay(9);
                    break;
                case 10:
                    Info_popDialogDisplay(10);
                    break;
            }
        }
    }

    private void Info_popDialogDisplay(int i) {
        popDialog_Info = new Xel_mine_modifyInfo_popWindow(xel_mine_studentmypage.this, itemsOnClick_UserInfo, i);
        //显示窗口
        popDialog_Info.showAtLocation(xel_mine_studentmypage.this.findViewById(R.id.student_layout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置

    }

    private void UserIcon_popDialogDisplay() {
        popDialog_UserIcon = new Xel_mine_modifyUserIcon_popWindow(xel_mine_studentmypage.this, itemsOnClick_UserIcon);
        //显示窗口
        popDialog_UserIcon.showAtLocation(xel_mine_studentmypage.this.findViewById(R.id.student_layout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
    }

    private void Gender_popDialogDisplay() {
        popDialog_Gender = new Xel_mine_modifyGender_popWindow(xel_mine_studentmypage.this, itemsOnClick_UserGender);
        //显示窗口
        popDialog_Gender.showAtLocation(xel_mine_studentmypage.this.findViewById(R.id.student_layout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
    }

    private View.OnClickListener itemsOnClick_UserInfo = new View.OnClickListener(){

            @Override
            public void onClick(View v) {
            popDialog_Info.dismiss();
            switch (v.getId()) {
                case R.id.modify_edit_confirm:
                    selectFlag = Xel_mine_modifyInfo_popWindow.flag;
                    TextView tv = (TextView) Xel_mine_modifyInfo_popWindow.popView.findViewById(R.id.modify_edit);
                    String textContent = null;
                    if (tv != null) {
                        textContent = tv.getText().toString();
                        tv.setText("");
                    }else {
                        Log.d(TAG, "代码有异常");
                    }
                    Log.d(TAG, "用户选择的FLAG:" + selectFlag + "," + "用户输入的信息:" + textContent);
                    getTagToChnageText(selectFlag, textContent);
                    break;
                case R.id.modify_edit_cancel:
                    flag = 0;
                    break;
            }

        }
    };

    private View.OnClickListener itemsOnClick_UserGender = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            popDialog_Gender.dismiss();
            switch (v.getId()) {
                case R.id.modify_gender_confirm:
                    getTagToChangeGender(Xel_mine_modifyGender_popWindow.gender);
                    break;
                case R.id.modify_gender_cancel:
                    popDialog_Gender.dismiss();
            }

        }
    };

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick_UserIcon = new View.OnClickListener() {
        public void onClick(View v) {

            popDialog_UserIcon.dismiss();

            switch (v.getId()) {
                case R.id.item_popupwindows_Photo:
                    getPicFromPhoto();
                    break;
                case R.id.item_popupwindows_camera:
                    getPicFromCamera();
                    break;
                default:
                    break;
            }
        }
    };


    private void getTagToChangeGender(String genderContent) {
        final Map<String, String> map1 = new HashMap<>();
        queryCase = 1;
        cUserInfo.setGender(genderContent);
        String genderJson = JSON.toJSONString(cUserInfo).replace("server_id", "user_id").replace("qq_Number", "qq_number");
        map1.put("information", genderJson);
        Log.d(TAG, genderJson);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    recode = Post_learn_rijiyuelei.getStringCha(Constant.post_user_info, map1);
                    Message msg = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("response", recode);
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        getHandlerMSG();
    }

    private void getTagToChnageText(int selectFlag, String textContent) {
        final Map<String, String> map = new HashMap<>();
        queryCase = 1;
        switch (selectFlag) {
            case 1:
                cUserInfo.setMotto(textContent);
                Log.d(TAG, JSON.toJSONString(cUserInfo));
                contentThread(cUserInfo);
                break;
            case 4:
                cUserInfo.setQq_Number(textContent);
                Log.d(TAG, JSON.toJSONString(cUserInfo));
                contentThread(cUserInfo);
                break;
            case 5:
                cUserInfo.setEmail(textContent);
                Log.d(TAG, JSON.toJSONString(cUserInfo));
                contentThread(cUserInfo);
                break;
            case 6:
                cUserInfo.setWeixin_number(textContent);
                Log.d(TAG, JSON.toJSONString(cUserInfo));
                contentThread(cUserInfo);
                break;
            case 7:
                cUserInfo.setRecipient(textContent);
                Log.d(TAG, JSON.toJSONString(cUserInfo));
                contentThread(cUserInfo);
                break;
            case 8:
                cUserInfo.setAddress(String.valueOf(textContent));
                Log.d(TAG, JSON.toJSONString(cUserInfo));
                contentThread(cUserInfo);
                break;
            case 9:
                cUserInfo.setPhone(textContent);
                Log.d(TAG, JSON.toJSONString(cUserInfo));
                contentThread(cUserInfo);
                break;
            case 10:
                cUserInfo.setZip_code(textContent);
                Log.d(TAG, JSON.toJSONString(cUserInfo));
                contentThread(cUserInfo);
                break;
            default:
                ToastUtil.show(xel_mine_studentmypage.this, "用户未输入信息");
                break;
        }
    }

    private void contentThread(User cUserInfo){
        final Map<String, String> map = new HashMap<>();
        Rson = JSON.toJSONString(cUserInfo).replace("server_id", "user_id").replace("qq_Number", "qq_number");
        map.put("information", Rson);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    recode = Post_learn_rijiyuelei.getStringCha(Constant.post_user_info, map);
                    Message msg = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("response", recode);
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        getHandlerMSG();
    }

    public void saveImage(Bitmap bmp) {
        //SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd,hh:mm:ss");
        //String date = sDateFormat.format(new java.util.Date());

        File appDir = new File(Environment.getExternalStorageDirectory(), "Xel_Resources");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        fileName = cUserUniqueID+".jpg";
        File file = new File(appDir, fileName);
        final String fileDir = appDir+"/"+fileName;
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            Integer i = fileDir.length();
            String uri = fileDir.substring(1,i);
            MsharedPrefrence.SetUserTempImgDir(this,phoneNum,uri);
            Log.d(TAG,"MSharePreferenceImgDir"+"phone:"+phoneNum+"  "+"Uri:"+uri);
            //转换为JPEG格式,质量为100
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d(TAG, "FilePath:" + fileDir + "UniqueID:" + cUserUniqueID);
                    recode = Post_UserInfo.getStringCha(Constant.post_image, fileDir, cUserUniqueID);//向服务器提交用户头像上传请求
                    queryCase = 2;  //说明用户选择了头像上传
                    Log.d(TAG,"Post Img result:"+recode);//服务器端返回的结果 100 失败 200 成功
                    Message msg = new Message();//消息处理机制
                    Bundle bundle = new Bundle();
                    bundle.putString("response",recode);
                    Log.d("用户请求上传图片返回的结果", String.valueOf(bundle));
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        getHandlerMSG();//调用消息处理结果方法

    }

    private synchronized void getHandlerMSG() {
        final ProgressDialog dialog = ProgressDialog.show(xel_mine_studentmypage.this, "上传中...", "正在更改个人信息,请稍后！");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String response = msg.getData().getString("response");
                Log.d(TAG, "response==" + response);
                try {
                    Log.d("SSS", "yangkang");
                    json = new JSONObject(response);
                    success = json.getString("success");
                    if (success.equals("200")) {
                        Log.d(TAG, "在Toast之前");
                        ToastUtil.show(xel_mine_studentmypage.this, "更改成功("+queryCase+")");
                        Log.d(TAG, "已走过Toast");
                        switch (queryCase){
                            case 1:
                                Db_UserService.getInstance(xel_mine_studentmypage.this).saveNote(cUserInfo);
                                setViewContent();
                                break;
                            case 2:
                                String uri = Constant.selectUserMessage+"/"+fileName;
                                Log.d(TAG,"cUserInfoUserIcon(queryCase=2):"+uri);
                                cUserInfo.setUser_icon_url(uri);
                                Db_UserService.getInstance(xel_mine_studentmypage.this).saveNote(cUserInfo);
                                Log.d(TAG,"存好的cUserInfo(queryCase=2):"+String.valueOf(cUserInfo));
                                Ini_view_image();
                                setUpload_state(1);
                                //setViewContent();
                                break;
                        }
                        Log.d(TAG, "cUserInfo存好的信息" + cUserInfo);
                        dialog.dismiss();


                    } else {

                        ToastUtil.show(xel_mine_studentmypage.this, "更改失败,请联网重试");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        MsharedPrefrence.SetUserInfo(this,cUserInfo.getUser_icon_url(),phoneNum,cUserInfo.getMotto(),cUserInfo.getGender(),cUserInfo.getQq_Number(),cUserInfo.getWeixin_number(),cUserInfo.getEmail(),cUserInfo.getRecipient(),cUserInfo.getAddress(),cUserInfo.getZip_code());
        recode = null;
        json = null;
        success = null;
    }

    public void getPicFromPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);//Intent.Action_Pick调取系统相册
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        Log.d("yann", "getPicFromPhoto");
        startActivityForResult(intent, PHOTO_REQUEST);
    }


    public void getPicFromCamera() {
        Log.d("yann", "getPicFromCamera");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 下面这句指定调用相机拍照后的照片存储的路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                Environment.getExternalStorageDirectory(), "test.jpg")));
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("yann", "requestCode" + requestCode);
        switch (requestCode) {
            case CAMERA_REQUEST:
                switch (resultCode) {
                    case -1:// -1表示拍照成功
                        File file = new File(Environment.getExternalStorageDirectory()
                                + "/test.jpg");
                        if (file.exists()) {
                            Log.d("yann", "Uri.fromFile(file)===" + Uri.fromFile(file));
                            photoClip(Uri.fromFile(file));
                        }else {
                            Log.d(TAG,"!file.exists()");
                        }
                        break;
                    default:
                        Log.d(TAG,"RESULTCODE:"+resultCode);
                        break;
                }
                break;
            case PHOTO_REQUEST:
                if (data != null) {
                    Log.d("yann", "data.getData()===" + data.getData());
                    photoClip(data.getData());
                } else {
                    //Log.d("yann", "wochucuole" + data.getData());
                    Log.d("yann", "wochucuole");
                }
                break;
            case PHOTO_CLIP:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    Log.d("yann", "onActivityResult:PHOTO_CLIP" + data.getData());
                    if (extras != null) {
                        Bitmap photo = extras.getParcelable("data");
                        Log.d(TAG, "saveBitmap");
                        saveImage(photo);
                    }
                }
                break;
            default:
                Log.d(TAG,"REQUESTCODE:"+requestCode);
                break;
        }
    }

    private void photoClip(Uri uri) {
        // 调用系统中自带的图片剪裁
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        muri = uri;
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        //intent.putExtra("circlecrop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 256);
        intent.putExtra("return-data", true);
        //getImageToView(intent);
        startActivityForResult(intent, PHOTO_CLIP);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        switch (MsharedPrefrence.Getphonewsd(this)[2]){
            case "1":
                xel_mine_student_fragment.newInstance(3);
                break;
            case "2":
                xel_mine_parent_fragment.newInstance(3);
                break;
            default:
                break;
        }

    }

    /*private void getImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Log.d("yann", "getImageToView");
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(photo);
            String picName = cUserInfo.getServer_id() + "userIcon.png";
            File saveImgFile = new File(Environment.getExternalStorageDirectory(), "storage/emulated/0/" + picName);
            if (saveImgFile.exists()) {
                Log.d("BitmapIcon Path", saveImgFile.getPath());
            }
            img_userIcon.setImageDrawable(drawable);
        }
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
