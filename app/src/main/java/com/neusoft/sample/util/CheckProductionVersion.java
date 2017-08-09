package com.neusoft.sample.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.neusoft.sample.Model.Constant;

/**
 * Created by AstroBoy on 2017/1/11.
 */

public class CheckProductionVersion {

    Context context;

    public String CheckProductionVersion(Context context) throws PackageManager.NameNotFoundException {
        this.context = context;
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);

        String version = packInfo.versionName;
        String versiontv = Constant.getAppVersionName(context);

        return versiontv;
    }



}
