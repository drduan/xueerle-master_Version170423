package com.neusoft.sample.View.CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.TextView;

/**
 * Created by wangyujie on 2016/10/7.
 */
public class CustomMyFraction extends TextView {


    public CustomMyFraction(Context context) {
        super(context);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }



}
