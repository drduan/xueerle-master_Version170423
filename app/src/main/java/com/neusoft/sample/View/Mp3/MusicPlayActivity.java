package com.neusoft.sample.View.Mp3;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ncapdevi.sample.R;
import com.neusoft.sample.App;
import com.neusoft.sample.View.Mp3.bean.Mp3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MusicPlayActivity extends AppCompatActivity {





    private ListView listView;
    private TextView tv_newPlaylist;
    private SimpleAdapter adapter;
    boolean isReturePlaylist;
    private int type = -1;
    private ArrayList<Mp3> songs;// 歌曲集合
    public static final int SONGS_LIST = 2;//适配器加载的数据是歌曲列表
    private App application;
    private Button mPauseImageButton;
    private TextView  tv_curcentTime, tv_allTime;
    private SeekBar seekBar1;// 播放进度条
    private MusicPlayService mService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_play);
         application = (App) getApplication();
        mService = application.getmService();
        initView();
        setListener();
        initListener();
    }
    private void initView() {
        tv_newPlaylist = (TextView) this.findViewById(R.id.tv_newPlaylist);
        listView = (ListView) this.findViewById(R.id.play_listview);
        ////////main上
        mPauseImageButton = (Button) findViewById(R.id.PauseImageButton);
        tv_curcentTime = (TextView) findViewById(R.id.tv_curcentTime);
        tv_allTime = (TextView) findViewById(R.id.tv_allTime);
        seekBar1 = (SeekBar) findViewById(R.id.seekBar1);
        // 启动
        handler.post(updateThread);
    }

    Handler handler = new Handler();
    Runnable updateThread = new Runnable() {
        public void run() {
            // 获得歌曲的长度并设置成播放进度条的最大值
            seekBar1.setMax(mService.getDuration());
            // 获得歌曲现在播放位置并设置成播放进度条的值
            seekBar1.setProgress(mService.getCurrent());
            tv_curcentTime.setText(formatTime(mService.getCurrent()));
            tv_allTime.setText(formatTime(mService.getDuration()));
            // 每次延迟100毫秒再启动线程
            handler.postDelayed(updateThread, 100);
        }
    };

    private void setListener() {
        // 暂停or开始
        mPauseImageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mService.pausePlay();
                if (mService.isPlay()) {
                   //改变  暂停按钮的  样式  和显示的字

                } else {
                    //改变  暂停按钮的  样式  和显示的字
                }
            }
        });


        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // fromUser判断是用户改变的滑块的值
                if (fromUser == true) {
                    mService.movePlay(progress);
                }
            }
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    /**
     * 格式化时间，将其变成00:00的形式
     */
    public String formatTime(int time) {
        int secondSum = time / 1000;
        int minute = secondSum / 60;
        int second = secondSum % 60;

        String result = "";
        if (minute < 10)
            result = "0";
        result = result + minute + ":";
        if (second < 10)
            result = result + "0";
        result = result + second;

        return result;
    }







    ///////////////////////////////////////////////////////////////////
    public void initListener() {
        //列出所有歌曲
        tv_newPlaylist.setVisibility(View.VISIBLE);
        //  通过一个   工具类   扫描 媒体库  获取到手机中的音乐
        songs = MusicUtils.getAllSongs(MusicPlayActivity.this);

        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < songs.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", songs.get(i).getSqlId());
            map.put("songName", songs.get(i).getName());
            if (songs.get(i).getSingerName().equals("<unknown>")) {
                map.put("singerName", "----");
            } else {
                map.put("singerName", songs.get(i).getSingerName());
            }
            listItems.add(map);
        }
        adapter = new SimpleAdapter(MusicPlayActivity.this, listItems, R.layout.itemmusic_activity, new String[]{"id", "songName", "singerName"}, new int[]{R.id.tv_id,
                R.id.tv_songName, R.id.tv_singerName});
        type = SONGS_LIST;
        listView.setAdapter(adapter);
        //点击  之后  获取 到  歌曲   然后跳转  到 播放页面
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                if (null == mService) {
                    mService = application.getmService();
                }
                mService.setCurrentListItme(position);
                mService.setSongs(songs);
                mService.playMusic(songs.get(position).getUrl());

            }
        });
    }











}
