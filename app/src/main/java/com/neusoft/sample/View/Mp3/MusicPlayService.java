package com.neusoft.sample.View.Mp3;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.neusoft.sample.View.Mp3.bean.Mp3;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;


public class MusicPlayService extends Service {
	public static int aa = 0;
	private final IBinder mBinder = new LocalBinder();
	private Context context; 
    /* MediaPlayer对象 */  
	public static MediaPlayer  mMediaPlayer = null;
    private int currentTime = 0;//歌曲播放进度
	private int currentListItme = -1;//当前播放第几首歌
	private List<Mp3> songs;//要播放的歌曲集合
    
	@Override
	public void onCreate() {
		super.onCreate();
		if (mMediaPlayer == null) {
			mMediaPlayer = new MediaPlayer();
		}
	}
    
	/**
	 *得到当前播放进度 
	 */
	public int getCurrent() {
		if (mMediaPlayer.isPlaying()) {
			return mMediaPlayer.getCurrentPosition();
		} else {
			return currentTime;
		}
	}
	
	/**
	 *	跳到输入的进度 
	 */
	public void movePlay(int progress) {
		if(mMediaPlayer == null){
			mMediaPlayer =new MediaPlayer();
		}
		mMediaPlayer.seekTo(progress);
		currentTime = progress;
	}
	public void playUrl(final String url) {
		if(mMediaPlayer == null){

		}else {
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
		mMediaPlayer =new MediaPlayer();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					/* 重置MediaPlayer */
					mMediaPlayer.reset();
			/* 设置要播放的文件的路径 */

					mMediaPlayer.setDataSource(url);
					// mMediaPlayer = MediaPlayer.create(this,
					// R.drawable.bbb);播放资源文件中的歌曲
			/* 准备播放 */
					mMediaPlayer.prepareAsync();
			/* 开始播放 */
					mMediaPlayer.start();
					mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
						public void onCompletion(MediaPlayer arg0) {
							// 播放完成一首之后进行下一首
							mMediaPlayer.release();
							mMediaPlayer = null;

						}
					});
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}}).start();
	}



	/**
	 *	根据歌曲存储路径播放歌曲 
	 */
	public void playMusic(String path) {
		final  String nn = path;
		if(mMediaPlayer == null){

		}else {
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
		mMediaPlayer =new MediaPlayer();
		
		try {
			/* 重置MediaPlayer */
			mMediaPlayer.reset();
			/* 设置要播放的文件的路径 */
			mMediaPlayer.setDataSource(nn);
			// mMediaPlayer = MediaPlayer.create(this,
			// R.drawable.bbb);播放资源文件中的歌曲
			/* 准备播放 */
			mMediaPlayer.prepare();
			/* 开始播放 */
			mMediaPlayer.start();
			mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
				public void onCompletion(MediaPlayer arg0) {
					mMediaPlayer.release();
					mMediaPlayer = null;
				}
			});
		} catch (IOException e) {
		}
		
	}
	
	public static int getAa(){
		return aa;
	}
	
	/*
	* 如果没有进度条的话  调用这个   速度会明显提高*/
	public void playMusics(String path) {
		if(mMediaPlayer == null){

		}else {
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
		mMediaPlayer = new MediaPlayer();
		final String nn = path;

		try{
//			Log.d("musicPlayer","playPath"+new String(nn.getBytes("UTF8")));

			// TODO: 2016/12/11

		}catch (Exception e)
		{

		}



		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
			/* 重置MediaPlayer */
					mMediaPlayer.reset();
			/* 设置要播放的文件的路径 */

					mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

					//  byte []  bytes = nn.getBytes();
//                    nn = new String(bytes,"gb2312");


					int x = nn.lastIndexOf("/");
//                    nn.substring(x,nn.length());

					String temp =nn.substring(0,x+1)+ URLEncoder.encode(nn.substring(x+1,nn.length()), "utf-8");
					Log.d("musicPlayer","playPath"+temp);
					mMediaPlayer.setDataSource(context, Uri.parse(temp));

					// R.drawable.bbb);播放资源文件中的歌曲
			/* 准备播放 */
					mMediaPlayer.prepare();
			/* 开始播放 */
					mMediaPlayer.start();
					mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
						public void onCompletion(MediaPlayer arg0) {
							// 播放完成一首之后进行下一首
							mMediaPlayer.release();
							mMediaPlayer = null;

						}
					});
				} catch (IOException e) {
					Log.d("musicPlayer","playException"+e);
				}
			}
		}).start();
	}
	

	
	/**
	 *	歌曲是否真在播放 
	 */
	public boolean isPlay() {
		return mMediaPlayer.isPlaying();
	}
	
	/**
	 *	暂停或开始播放歌曲 
	 */
	public void pausePlay() {

		if (mMediaPlayer.isPlaying()) {
			currentTime = mMediaPlayer.getCurrentPosition();
			mMediaPlayer.pause();
		} else {

			mMediaPlayer.start();
		}
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return mBinder;
	}

	public String getSongName(){
		return songs.get(currentListItme).getName();
	}
	
	public String getSingerName(){
		return songs.get(currentListItme).getSingerName();
	}
	
	/**
	 *	自定义绑定Service类，通过这里的getService得到Service，之后就可调用Service这里的方法了
	 */
	public class LocalBinder extends Binder {
		public MusicPlayService getService() {
			return MusicPlayService.this;
		}
	}
	
	public void setContext(Context context) {
		this.context = context;
	}

	public MediaPlayer getmMediaPlayer() {
		return mMediaPlayer;
	}

	public void setmMediaPlayer(MediaPlayer mMediaPlayer) {
		this.mMediaPlayer = mMediaPlayer;
	}

	public int getCurrentListItme() {
		return currentListItme;
	}

	public void setCurrentListItme(int currentListItme) {
		this.currentListItme = currentListItme;
	}

	public int getDuration() {

		if(mMediaPlayer ==null){
			mMediaPlayer = new MediaPlayer();
		}
		
		return mMediaPlayer.getDuration();
	}

	public List<Mp3> getSongs() {
		return songs;
	}

	public void setSongs(List<Mp3> songs) {
		this.songs = songs;
	}
	
    @Override
    public void onStart(Intent intent, int startId) {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i("Service", "Received start id " + startId + ": " + intent);
      /**   如果服务进程在它启动后(从onStartCommand()返回后)被kill掉, 那么让他呆在启动状态但不取传给它的intent.
        	随后系统会重写创建service，因为在启动时，会在创建新的service时保证运行onStartCommand
       		如果没有任何开始指令发送给service，那将得到null的intent，因此必须检查它.
         	该方式可用在开始和在运行中任意时刻停止的情况，例如一个service执行音乐后台的重放 **/
        return START_STICKY;
    }

    @Override
    public void onDestroy() {

		if(mMediaPlayer ==null){

		}else {
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
		mMediaPlayer = new MediaPlayer();

    }
}
