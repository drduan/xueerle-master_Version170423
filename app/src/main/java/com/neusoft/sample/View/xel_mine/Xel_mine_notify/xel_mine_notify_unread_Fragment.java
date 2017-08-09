package com.neusoft.sample.View.xel_mine.Xel_mine_notify;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.ncapdevi.sample.R;
import com.neusoft.sample.App;
import com.neusoft.sample.Ctrl.Db_Notification;
import com.neusoft.sample.Ctrl.wenchengcheng.ExitApplication;
import com.neusoft.sample.Ctrl.wenchengcheng.NotifyRecyclerView;
import com.neusoft.sample.Ctrl.wenchengcheng.SystemNotification;
import com.neusoft.sample.GreenDao.Notification;
import com.neusoft.sample.Model.Consant_stringg;
import com.neusoft.sample.Model.Constant;
import com.neusoft.sample.Model.Get_notification;
import com.neusoft.sample.Model.mutils.ToastUtil;
import com.neusoft.sample.util.ContextHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class xel_mine_notify_unread_Fragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final int REFRESH_COMPLETE = 0X110;
    public SwipeRefreshLayout unreadSwipeLayout;
    public RecyclerView unreadRecycleView;
    public ListView unreadListView;
    private String userID;
    private String info = "";
    private static final String TAG = "UNREAD_FRAG";
    public List<SystemNotification> Servletnotification = new ArrayList<>();
    public List<Notification> notifications  = new ArrayList<>();;
    public List<Notification> updated = new ArrayList<>();
    public List<Map<Object,String>> listInfo = new ArrayList<Map<Object,String>>();// 定义显示的内容包装
    public NotifyRecyclerView listInfoAdapter;
    public List<Notification> querynotify = new ArrayList<>();
    protected Handler mHandler = new Handler();


    @Override
    public void onResume() {
        super.onResume();
        Intent in = getActivity().getIntent();
        String req = String.valueOf(in.getExtras().get("requestCode"));
        if (req.equals("1")) {
            queryDatabase();
            Log.d(TAG, "目前已存表的通知" + JSON.toJSONString(querynotify));
            queryNotificationsList();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.xel_mine_notify_unread, container, false);
        ExitApplication.getInstance().addActivity(getActivity());
        Boolean bool = Consant_stringg.is_internet(getActivity());
        if (!bool) {
            Toast.makeText(getActivity(), "请检查您的网络设置!", Toast.LENGTH_SHORT).show();
        }
        //init(v);
        //unreadRecycleView = (RecyclerView) v.findViewById(R.id.unread_notify_recyclerView);
        userID = App.newInstance().GetSharePrefrence_kejiezu(getContext());
        unreadListView = (ListView) v.findViewById(R.id.unread_notify_listview);
        unreadListView.setDividerHeight(0);
        unreadSwipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.unread_notify_swipeRefresh);
        unreadSwipeLayout.setColorSchemeResources(R.color.colorPrimary);
        unreadSwipeLayout.setOnRefreshListener(xel_mine_notify_unread_Fragment.this);
        unreadSwipeLayout.post(new Runnable() {
            @Override
            public void run() {
                unreadSwipeLayout.setRefreshing(true);
            }
        });
        onRefresh();
        return v;
    }

    private void queryDatabase() {
        querynotify = Db_Notification.getInstance(getContext()).loadAllNote();
        Log.d(TAG,info+ JSON.toJSONString(querynotify));
    }

    public void queryNotificationsList() {
        info = "将有那些列表项进入query:";
        queryDatabase();
        listInfo.clear();
        int i = 0;
        for (Notification c : querynotify){
            if (c.getNotify_check_id().equals(userID)&&c.getIs_read().equals("0")) {
                i++;
                Log.d(TAG,"符合未读要求的项目:"+ JSON.toJSONString(c));
                HashMap<Object, String> map = new HashMap<Object, String>();
                map.put("id", c.getNotification_id());
                map.put("hh:mm", c.getNotify_time().getHours() + ":" + c.getNotify_time().getMinutes());
                String yymmdd = Constant.getyy_mm_dd(c.getNotify_time());
                map.put("yy-mm-dd", yymmdd);
                map.put("title", c.getTitle());
                map.put("content", c.getContent());
                Log.d(TAG, "map内的值:" + map);
                listInfo.add(map);
                Log.d(TAG,"LISTINFO的值:"+listInfo);
            }
        }
        Log.d(TAG,"共有"+i+"进入了列表");
        int layoutres = R.layout.xel_mine_notify_item;
        listInfoAdapter = new NotifyRecyclerView(getContext(),listInfo,layoutres);
        listInfoAdapter.notifyDataSetChanged();
        unreadListView.setAdapter(listInfoAdapter);
        querynotify.clear();

    }

    private void Ini_Post_Notification() {
        onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String getNotification;
                try {
                    getNotification = Get_notification.getStringCha(Constant.post_notification);
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("response", getNotification);
                    message.setData(bundle);
                    mHandler.sendMessageDelayed(message,3000);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Ini_Post_Notification_Handler();
    }

    private void Ini_Post_Notification_Handler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String response = msg.getData().getString("response");
                try {
                    JSONObject json = new JSONObject(response);
                    Log.d(TAG, "Json内容" + json);
                   
                    JSONArray data = json.getJSONArray("data");
                    String success = json.getString("success");
                    if (success.equals("200")){
                        Log.d("@@", "请求信息成功 data" + data);

                        Servletnotification = JSON.parseArray(String.valueOf(data),
                                SystemNotification.class);
                        Log.d(TAG,"Servletnotification"+Servletnotification.get(1).getSend_time());

                        for (SystemNotification servletnotification : Servletnotification){
                            Notification notification = new Notification();
                            notification.setNotification_id(servletnotification.getNotification_id());
                            notification.setTitleNo(servletnotification.getTitlerNo());
                            notification.setTitle(servletnotification.getTitle());
                            notification.setContent(servletnotification.getChapterSequence());
                            notification.setNotify_publisher(servletnotification.getChapterSequenceName());
                            Log.d(TAG,"origin time:"+servletnotification.getSend_time());
                            notification.setNotify_time(string2Date(servletnotification.getSend_time()));
                            notification.setNotify_check_id(userID);
                            notification.setIs_read("0");
                            notification.setUnique_id(servletnotification.getNotification_id()+userID);
                            notifications.add(notification);
                        }
                        Collections.sort(notifications);
                        for(int j = 0;j<notifications.size();j++){
                            Log.d(TAG,"time="+notifications.get(j).getNotify_time().toString());
                        }
                        Log.d(TAG, "排序结果:" + JSON.toJSONString(notifications));
                        int i = notifications.size();
                        int j = 0;
                        int o = 0;
                        Log.d(TAG,"AFTER i:"+i);
                        queryDatabase();
                        for (Notification update : notifications){
                            if (!querynotify.isEmpty()){
                                for (Notification saved : querynotify){
                                    Log.d(TAG,"savedID:"+saved.getUnique_id()+"  "+"updateID:"+update.getUnique_id());
                                    if (!saved.getUnique_id().equals(update.getUnique_id())){
                                        updated.add(update);
                                        Log.d(TAG,"存入的Update项:"+ JSON.toJSONString(update)+"i:"+i);
                                    }else {
                                        i--;
                                        Log.d(TAG,"i="+i);
                                    }
                                    o++;
                                }
                            }else {
                                Log.d(TAG,"存入的Update项:"+ JSON.toJSONString(update)+"i:"+i);
                                updated.add(update);
                            }
                            j++;
                        }

                        Log.d(TAG,"外层循环："+j+" 内层循环："+o+" 新增："+i);
                        if (i==0) {
                            Toast.makeText(ContextHolder.getContext(),"当前内容已为最新",Toast.LENGTH_SHORT).show();

                        }else {
                            unreadListView.setAdapter(null);
                            Db_Notification.getInstance(ContextHolder.getContext()).saveNoteLists(updated);
                            info = "更新完之后:";
                            queryNotificationsList();
                            Toast.makeText(getContext(),"更新"+i+"条内容",Toast.LENGTH_SHORT).show();
                        }
                        i = 0;
                        notifications.clear();
                        querynotify.clear();
                        unreadSwipeLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                unreadSwipeLayout.setRefreshing(false);
                            }
                        });

                    }else {
                        unreadSwipeLayout.setRefreshing(false);
                        ToastUtil.show(getContext(), "更新失败,请联网重试");}
                }catch (JSONException e) {
                    String err = String.valueOf(e);
                    Toast.makeText(getContext(), "未成功同步,错误" + err, Toast.LENGTH_LONG).show();
                }

            }
        };
        
    }

    public static Date string2Date(String ymd){
        Date d=new Date();
        try{
            d= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(ymd);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            d = sdf.parse(ymd);
            Log.d(TAG,"convert time:"+d.toString());
        }
        catch(Exception e){
            System.out.println(e);
        }
        return d;
    }

    {}

    public void onRefresh()
    {
        Ini_Post_Notification();
    }

}
