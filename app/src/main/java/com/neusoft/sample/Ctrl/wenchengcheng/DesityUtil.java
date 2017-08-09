package com.neusoft.sample.Ctrl.wenchengcheng;

import android.content.Context;

/**
 * Created by AstroBoy on 2016/9/11.
 */
public class DesityUtil {

    public static int dip2px(Context context,float dpValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue*scale + 0.5f);
    }

    public static int px2dip(Context context,float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue*scale + 0.5f);
    }

}
