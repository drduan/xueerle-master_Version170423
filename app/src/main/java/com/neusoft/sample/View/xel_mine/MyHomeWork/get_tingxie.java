package com.neusoft.sample.View.xel_mine.MyHomeWork;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.neusoft.sample.App;
import com.neusoft.sample.Model.Constant;
import com.neusoft.sample.Model.Post_learn_rijiyuelei;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;


/**
 * Created by 杨康 on 2016/12/22.
 * 获取听写权限
 */
public class get_tingxie {

    Context context;
    private static   Handler handler_power  = null;
    public static String a ;
    private String power;
    private static String powersi;
    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }
    public static Handler get_power(final Context context) {


        final Thread thread;
        String aa =  null;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                get_tingxie get_tingxie = new get_tingxie();
                HashMap hashMap = new HashMap();
                hashMap.put("user_id", App.newInstance().GetSharePrefrence_kejiezu(context));
                try {
                    Log.d("@@", "user_id" +  App.newInstance().GetSharePrefrence_kejiezu(context));
                    String responsee = Post_learn_rijiyuelei.getStringCha(Constant.findpower, hashMap);
                    Log.d("@@", "respose" + responsee);
                    JSONObject jsonObjec = null;
                    jsonObjec = new JSONObject(responsee);
                    String power = null;
                    power = jsonObjec.getString("data");
                    a = power;
                    Message message=new Message();
                    Bundle bundle=new Bundle();
                    bundle.putString("power",a);
                    message.setData(bundle);
                    handler_power.sendMessage(message);
                    Log.d("@@", "powera" + a);
                } catch (IOException e) {
                    Log.d("@@","catch1"+e);
                    e.printStackTrace();
                } catch (JSONException e) {
                    Log.d("@@","catch2");
                    e.printStackTrace();
                }
            }
        };
        thread=new Thread(runnable);
        thread.start();
        handler_power=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                powersi=msg.getData().getString("power");
            }
        };
        System.out.println("powerapowera"+powersi);
        return  handler_power;
    }
}
