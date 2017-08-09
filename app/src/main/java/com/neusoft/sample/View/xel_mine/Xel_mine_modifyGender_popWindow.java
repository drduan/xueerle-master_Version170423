package com.neusoft.sample.View.xel_mine;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioButton;

import com.ncapdevi.sample.R;

/**
 * Created by AstroBoy on 2016/8/12.
 */
public class Xel_mine_modifyGender_popWindow extends PopupWindow {
    private Button modify_gender_confirm, modify_gender_cancel;
    public static View popView1;
    public int CODE;
    static String gender = null;
    public Xel_mine_modifyGender_popWindow(Activity context, View.OnClickListener itemsOnClick_UserGender) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popView1 = inflater.inflate(R.layout.xel_mine_modify_usergender_dialog, null);
        modify_gender_confirm = (Button) popView1.findViewById(R.id.modify_gender_confirm);
        modify_gender_cancel = (Button) popView1.findViewById(R.id.modify_gender_cancel);
        //取消按钮
        modify_gender_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //销毁弹出框
                dismiss();
            }
        });
        //设置按钮监听
        modify_gender_confirm.setOnClickListener(itemsOnClick_UserGender);
        getGenderContent();
        //设置SelectPicPopupWindow的View
        this.setContentView(popView1);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimBottom);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        popView1.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                int height = popView1.findViewById(R.id.gender_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    private void getGenderContent() {
        RadioButton male,female;
        male = (RadioButton) popView1.findViewById(R.id.radio_male);
        female = (RadioButton) popView1.findViewById(R.id.radio_female);
        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "0";
            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "1";
            }
        });
    }
    


}

