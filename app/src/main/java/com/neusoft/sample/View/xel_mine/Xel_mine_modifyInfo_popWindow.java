package com.neusoft.sample.View.xel_mine;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ncapdevi.sample.R;

/**
 * Created by AstroBoy on 2016/8/12.
 */
public class Xel_mine_modifyInfo_popWindow extends PopupWindow {

    private Button modify_edit_confirm, modify_edit_cancel;
    public static View popView;
    public int CODE;
    static int flag = 0;

    public Xel_mine_modifyInfo_popWindow(Activity context, View.OnClickListener itemsOnClick_UserInfo, int i) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        flag = i;
        Log.d("ModifyInfo", String.valueOf(i));
        popView = inflater.inflate(R.layout.xel_mine_modify_userinfo_dialog, null);
        modify_edit_confirm = (Button) popView.findViewById(R.id.modify_edit_confirm);
        modify_edit_cancel = (Button) popView.findViewById(R.id.modify_edit_cancel);
        setWindowTitle(flag);
        //取消按钮
        modify_edit_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //销毁弹出框
                dismiss();
            }
        });
        //设置按钮监听
        modify_edit_cancel.setOnClickListener(itemsOnClick_UserInfo);
        modify_edit_confirm.setOnClickListener(itemsOnClick_UserInfo);

        //设置SelectPicPopupWindow的View
        this.setContentView(popView);
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
        popView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = popView.findViewById(R.id.info_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                        flag = 0;
                    }
                }
                return true;
            }
        });
    }

    private void setWindowTitle(int flag) {
        TextView modify_text;
        modify_text = (TextView) popView.findViewById(R.id.modify_text);
        int i = 0;
        i = flag;
        switch (i) {
            case 1:
                modify_text.setText("设置签名");
                break;
            case 4:
                modify_text.setText("设置QQ号");
                break;
            case 5:
                modify_text.setText("设置邮箱");
                break;
            case 6:
                modify_text.setText("设置微信号");
                break;
            case 7:
                modify_text.setText("设置昵称");
                break;
            case 8:
                modify_text.setText("设置收货地址");
                break;
            case 9:
                modify_text.setText("设置收货手机号");
                break;
            case 10:
                modify_text.setText("设置邮政编码");
            default:
                Log.d("modifyInfo setText", String.valueOf(i));
        }
    }
}
