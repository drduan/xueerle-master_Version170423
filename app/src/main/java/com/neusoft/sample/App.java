package com.neusoft.sample;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.IBinder;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.ncapdevi.sample.R;
import com.neusoft.sample.Ctrl.CustomViewWithTypefaceSupport;
import com.neusoft.sample.Ctrl.Db_UserService;
import com.neusoft.sample.Ctrl.TextField;
import com.neusoft.sample.GreenDao.DaoMaster;
import com.neusoft.sample.GreenDao.DaoSession;
import com.neusoft.sample.GreenDao.User;
import com.neusoft.sample.View.Mp3.MusicPlayService;
import com.neusoft.sample.util.ContextHolder;

import java.util.List;

import im.fir.sdk.FIR;
import io.realm.Realm;
import okhttp3.OkHttpClient;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


/**
 * Created by Administrator on 2016/6/27.
 */
public class App extends Application {
    private static App mInstance;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;
    public static RequestQueue queue;
    private SQLiteDatabase db;


    public static Typeface texttypeface = null;


    public static App newInstance(){
        if (mInstance == null)
            mInstance = new App();
        return mInstance;
    }



//    yiui
    MusicPlayService mService;
    private ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder service) {
            mService = ((MusicPlayService.LocalBinder) service).getService();//用绑定方法启动service，就是从这里绑定并得到service，然后就可以操作service了

            mService.setContext(getApplicationContext());
        }
        public void onServiceDisconnected(ComponentName arg0) {
        }
    };


    //
    @Override
    public void onCreate() {
        super.onCreate();

        texttypeface = Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/youyuan.ttf");
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/youyuan.ttf")
                .setFontAttrId(R.attr.fontPath)
                .addCustomViewWithSetTypeface(CustomViewWithTypefaceSupport.class)
                .addCustomStyle(TextField.class, R.attr.textFieldStyle)
                .build());
        //Mp3
        Intent intent = new Intent(this, MusicPlayService.class);
        startService(intent);System.out.println("intent?"+(null == intent));
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        FIR.init(this);
        //
    //在任意位置获取context
        ContextHolder.init(this);
        queue = Volley.newRequestQueue(getApplicationContext());

        HttpHeaders headers = new HttpHeaders();
        headers.put("commonHeaderKey1", "commonHeaderValue1");    //所有的 header 都 不支持 中文
        headers.put("commonHeaderKey2", "commonHeaderValue2");
        HttpParams params = new HttpParams();
        params.put("commonParamsKey1", "commonParamsValue1");     //所有的 params 都 支持 中文
        params.put("commonParamsKey2", "这里支持中文参数");
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //必须调用初始化
        //.init(this);
        //以下都不是必须的，根据需要自行选择
        OkGo.getInstance().init(this)
                .setOkHttpClient(builder.build())
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3)    ;                           //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
                //.addCommonHeaders(headers)                      //全局公共头
                //.addCommonParams(params);                       //全局公共参数

//        AlipayClient alipayClient = new DefaultAlipayClient(gateway,app_id,private_key,"json",charset,alipay_public_key,sign_type);
        OkGo.getInstance().init(this);
//        Realm.init(this);

    }
    public static RequestQueue getRequestQueue(){
        return queue;
    }
    /**
     * 取得DaoMaster
     *
     * @param context
     * @return
     */
    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context,"Xel.db", null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    /**
     * 取得DaoSession
     *
     * @param context
     * @return
     */
    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }
    //
    public MusicPlayService getmService() {
        return mService;
    }
    public  static void GetSharePrefrence(Context context,String phone) {
        SharedPreferences.Editor editor = context.getSharedPreferences("User_ir", Context.MODE_PRIVATE).edit();
        List<User> list= Db_UserService.getInstance(context).loadAllNote();
        for (User user:list)
        {
            if (phone.equals(user.getPhone()))
            {
                editor.putString("user_id",user.getServer_id());
                editor.putString("role", user.getRole());
                editor.putString("username", user.getPhone());
                editor.commit();
                break;
            }
        }
    }

    public String GetSharePrefrence_kejiezu(Context context) {
        SharedPreferences sharedPreferences=context.getSharedPreferences("User_ir", Context.MODE_PRIVATE);
        String user_id=sharedPreferences.getString("user_id", "");

        return user_id;
    }

    public static String GetSharePrefrence_Phone(Context context) {
        SharedPreferences sharedPreferences=context.getSharedPreferences("User_ir", Context.MODE_PRIVATE);
        String user_id=sharedPreferences.getString("username", "");

        return user_id;
    }

    public static String GetSharePrefrence_role(Context context) {
        SharedPreferences sharedPreferences=context.getSharedPreferences("User_ir", Context.MODE_PRIVATE);
        String role=sharedPreferences.getString("role", "");

        return role;
    }

    public SQLiteDatabase getDb() {
        return db;
    }
    
}