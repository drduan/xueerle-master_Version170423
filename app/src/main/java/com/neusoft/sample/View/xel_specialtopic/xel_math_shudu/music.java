package com.neusoft.sample.View.xel_specialtopic.xel_math_shudu;

import android.content.Context;
import android.media.MediaPlayer;

public class music {
	private static MediaPlayer mp=null;
	private static MediaPlayer mmsound=null;
	/*
	 * 控制音乐播放
	 * 
	 * */
	public static  void paly(Context context,int resources){
		stop(context);//播放音乐之前  停止   上下文多媒体
		if(Game_6.getBackMusic(context)){
			mp=MediaPlayer.create(context, resources);
			mp.setLooping(true);
			mp.start();
		}
	}
	public static void stop(Context context) {
		if(mp!=null){
			mp.stop();
			mp.release();
			mp=null;
		}

	}
	public static void palySound(Context context,int resource){
		stop(context);
		if(Game_6.getSoundSet(context)){
			mmsound=MediaPlayer.create(context, resource);
			mmsound.start();
		}		
	}
}
