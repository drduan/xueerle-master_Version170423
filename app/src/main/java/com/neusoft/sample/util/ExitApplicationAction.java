package com.neusoft.sample.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.neusoft.sample.Ctrl.wenchengcheng.ActionSheetDialog;
import com.neusoft.sample.Ctrl.wenchengcheng.ExitApplication;
import com.neusoft.sample.View.users.xel_user_login;

/**
 * Created by AstroBoy on 2017/1/11.
 */

public class ExitApplicationAction {


    public ExitApplicationAction(final Context thisContext, final Activity thisActivity) {
        new ActionSheetDialog(thisContext)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("退出登陆", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        ExitApplication.getInstance().Login();
                        JumpToLogin(thisActivity);
                    }
                })
                .addSheetItem("退出易学而乐", ActionSheetDialog.SheetItemColor.Red, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        ExitApplication.getInstance().exit();
                    }
                })
                .show();
    }

    private void JumpToLogin(Activity thisActivity) {
        Intent intent = new Intent(thisActivity, xel_user_login.class);
        thisActivity.startActivity(intent);
    }
}
