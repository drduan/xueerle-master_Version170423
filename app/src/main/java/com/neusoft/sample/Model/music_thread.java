package com.neusoft.sample.Model;

import com.neusoft.sample.View.Mp3.MusicPlayService;

/**
 * Created by 杨康 on 2016/8/25.
 */

 public  class music_thread extends Thread {
    MusicPlayService musicPlayService;
    static  String url  ="";
    public static void urll (String string){
        url = string;
    }
    public void run(String urll){
        musicPlayService.playMusic(urll);
    }


}
