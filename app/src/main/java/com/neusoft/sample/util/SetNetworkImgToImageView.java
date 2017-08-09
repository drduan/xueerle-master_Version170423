package com.neusoft.sample.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ncapdevi.sample.R;

/**
 * Created by AstroBoy on 2017/1/11.
 */

public class SetNetworkImgToImageView {

    public void SetNetworkImgToImageView(Context context, ImageView imageView, String url) throws Exception{

        Glide.with(context)
                .load(url)//
//                .placeholder(R.drawable.usericon_default)//
//                .error(R.drawable.usericon_default)//
//                .diskCacheStrategy(DiskCacheStrategy.ALL)//
                .into(imageView);

    }
}
