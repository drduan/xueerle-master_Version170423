package com.neusoft.sample.View.xel_mine;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.ncapdevi.sample.R;
import com.neusoft.sample.Model.Constant;
import com.neusoft.sample.Model.MsharedPrefrence;
import com.neusoft.sample.Model.Post_Feedback;
import com.neusoft.sample.Model.mutils.ToastUtil;
import com.neusoft.sample.View.BaseActivity;
import com.neusoft.sample.View.xel_mine.ImagePickerView.GlideImageLoader;
import com.neusoft.sample.View.xel_mine.ImagePickerView.ImagePickerAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Xel_mine_feedback extends BaseActivity implements ImagePickerAdapter.OnRecyclerViewItemClickListener {
    ImageButton back;
    LinearLayout feedback_Upload;
    EditText feedback_text;

    Handler handler;
    Timer timer;
    MyTimerTask task;
    String success;
    String[] phone = null;
    String phoneNum = null;
    JSONObject json = null;
    final Map<ArrayList<ImageItem>, String> map = new HashMap<>();
    private Integer requestCode = 100;

    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;

    String content = null;

    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 1;               //允许选择图片最大数

    private static final int TIME_LIMIT = 10000;
    private static final int SUCCESS = 1;
    private static final int TIME_OUT = 0;

    private String TAG = "Xel_mine_feedback";
    private String post_feedback_img_url = Constant.post_feedback_img_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xel_mine_feedback);
        phone = MsharedPrefrence.Getphonewsd(this);
        back = (ImageButton) findViewById(R.id.feedback_back);
        feedback_Upload = (LinearLayout) findViewById(R.id.upload_feedback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        feedback_Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayDialogThread();
            }
        });
        //最好放到 Application oncreate执行
        initImagePicker();
        initWidget();
    }

    private void displayDialogThread() {
        /*ProgressDialog dialog = ProgressDialog.show(Xel_mine_feedback.this, "上传中...", "正在将令状上诉至衙门，请稍后！");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);*/
        final AlertDialog.Builder builder = new AlertDialog.Builder(Xel_mine_feedback.this);
        Boolean existImg = selImageList.isEmpty();
        feedback_text = (EditText) findViewById(R.id.feedback_text);
        content = feedback_text.getText().toString().trim();
        if (content.equals("") || content.length() < 20) {
            builder.setTitle("内容不符合要求")
                    .setMessage("返回重新输入")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            feedback_text.setText("");
                            dialog.dismiss();
                        }
                    }).show();
        } else {
            post_feedback_to_server();
        }
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(false);                      //显示拍照按钮
        imagePicker.setCrop(false);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }

    private void initWidget() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                //打开选择,本次允许选择的数量
                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                Intent intent = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT);
                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                selImageList.addAll(images);
                adapter.setImages(selImageList);
                if (selImageList.isEmpty()) {
                    setImageItemsRequest(100);
                } else {
                    setImageItemsRequest(200);
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                setImageItemsRequest(200);
                selImageList.clear();
                selImageList.addAll(images);
                adapter.setImages(selImageList);
                if (selImageList.isEmpty()) {
                    setImageItemsRequest(100);
                } else {
                    setImageItemsRequest(200);
                }
            }
        }
    }

    public Integer getImageItemsRequest() {
        return requestCode;
    }

    public void setImageItemsRequest(Integer requestCode) {
        this.requestCode = requestCode;
    }

    private void post_feedback_to_server() {

        //timer = new Timer(true);
        Integer req = getImageItemsRequest();
        Log.d(TAG, "ArrayList<ImageItem>" + selImageList);
        Log.d(TAG,"content:"+content);
        if (req == 100) {
            Log.d(TAG, "req:"+req+"  phoneNum:"+phoneNum+"  content:"+content+"filePath:"+"null");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //checkTimeOut();
                        String result = Post_Feedback.getStringChaNonImg(Constant.post_feedback_img_url, "", content, phone[0]);
                        Message msg = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("response", result);
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    } catch (IOException e) {

                        e.printStackTrace();

                        onFailureDialog(String.valueOf(e));
                    }
                    //handler.sendEmptyMessage(10000);
                }
            }).start();

        } else if (req == 200) {
            /*Integer i = selImageList.size();
            Integer j = 0;
            final ArrayList<String> filePaths = null;
            for (j = 0;j < i;j++){
                if (filePaths != null) {
                    filePaths.set(j,selImageList.get(j).path);
                }
            }*/
            final String filePath = selImageList.get(0).path;
            Log.d(TAG, "req:"+req+"  phoneNum:"+phoneNum+"  content:"+content+"filePath:"+String.valueOf(filePath));
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        checkTimeOut();
                        Log.d(TAG, "FilePath:" + filePath + "PhoneNum:" + phoneNum);
                        String result = Post_Feedback.getStringCha(Constant.post_feedback_img_url, filePath, content, phone[0]);
                        Log.d(TAG, "Post Img result:" + result);
                        Message msg = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("response", result);
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    } catch (IOException e) {
                        e.printStackTrace();

                        onFailureDialog(String.valueOf(e));
                    }
                    //handler.sendEmptyMessage(10000);
                }
            }).start();
        }

        getMsgHandler();
    }

    private void getMsgHandler(){
        final ProgressDialog dialog = ProgressDialog.show(Xel_mine_feedback.this, "请稍后...", "正在将令状上报给衙门！");
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
                    if (response != null && !response.equals(""))
                    {
                        json = new JSONObject(response);
                        success = json.getString("success");
                        if (success.equals("200")) {
                            Log.d(TAG, "在Toast之前");
                            Log.d(TAG, "Server Response:" + success);
                            ToastUtil.show(Xel_mine_feedback.this, "成功");
                            Log.d(TAG, "已走过Toast");
                            dialog.dismiss();
                            onSuccessDialog();
                        } else if (success.equals("500")) {
                            dialog.dismiss();
                            Log.d(TAG, "Server Response:" + success);
                            ToastUtil.show(Xel_mine_feedback.this, "反馈信息失败,请保持网络畅通再试哦~");
                            onFailureDialog(success);
                        } else {
                            dialog.dismiss();
                            Log.d(TAG, "Server Response:" + success);
                            ToastUtil.show(Xel_mine_feedback.this, "反馈信息失败,请保持网络畅通再试哦~");

                            onFailureDialog(success);
                        }
                    }else {
                        Log.d(TAG,"onResume");
                    }

                } catch (JSONException e1) {
                    dialog.dismiss();
                    String err = String.valueOf(e1);
                    ToastUtil.show(Xel_mine_feedback.this, "反馈信息失败,请保持网络畅通再试哦~ (" + err + ")");
                    onFailureDialog(String.valueOf(e1));
                    e1.printStackTrace();
                }
            }
        };

    }
    
    private void onTimeOutDialog(){
        AlertDialog dialog = new AlertDialog
                .Builder(Xel_mine_feedback.this)
                .setTitle("上传超时")
                .setMessage("确定将回退至帮助与反馈界面")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNeutralButton("重试", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        post_feedback_to_server();
                    }
                }).show();
    }

    private void onFailureDialog(String err) {
        AlertDialog dialog = new AlertDialog
                .Builder(Xel_mine_feedback.this)
                .setTitle("上传失败")
                .setMessage("确定将回退至帮助与反馈界面"+"(错误:"+err+")")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onResume();
                        dialog.dismiss();
                    }
                }).show();
    }

    class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            handler.sendEmptyMessage(10000);
        }

    }

    private void checkTimeOut(){
        try{
            timer = new Timer();
            task = new MyTimerTask();
            timer.schedule(task, 10000);
        }catch(Exception e){
            Log.e("timer", e.getMessage());
        }
    }

    private void onSuccessDialog() {
        new AlertDialog
                .Builder(Xel_mine_feedback.this)
                .setTitle("上传成功")
                .setMessage("确定将回退至我的界面")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        onBackPressed();
                    }
                }).show();

    }
}
