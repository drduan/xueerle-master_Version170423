package com.neusoft.sample.Model;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by 杨康 on 2016/5/23.
 */
public class mygridview extends GridView {

        public mygridview(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public mygridview(Context context) {
            super(context);
        }

        public mygridview(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        @Override
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

            int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                    MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);
        }

}
