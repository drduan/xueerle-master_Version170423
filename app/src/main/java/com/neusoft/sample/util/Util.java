package com.neusoft.sample.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;

/**
 * Created by duanxudong on 16/7/10.
 */
public class Util {

    double height ,width;
    public double[] getBitMapSize(Context context, Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return null;
        }
        else {
            height = bitmap.getRowBytes() * bitmap.getHeight();
             width = bitmap.getRowBytes() * bitmap.getWidth();
        }
        return new double[]{height, width};

    }

}
