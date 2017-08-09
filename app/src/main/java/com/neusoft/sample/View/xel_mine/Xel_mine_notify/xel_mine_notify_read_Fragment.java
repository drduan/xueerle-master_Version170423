package com.neusoft.sample.View.xel_mine.Xel_mine_notify;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.ncapdevi.sample.R;
import com.neusoft.sample.App;
import com.neusoft.sample.Ctrl.Db_Notification;
import com.neusoft.sample.Ctrl.wenchengcheng.ExitApplication;
import com.neusoft.sample.Ctrl.wenchengcheng.NotifyRecyclerView;
import com.neusoft.sample.GreenDao.Notification;
import com.neusoft.sample.Model.Consant_stringg;
import com.neusoft.sample.Model.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */

public class xel_mine_notify_read_Fragment extends Fragment {
    private String userID;
    public ListView readListView;
    public TextView noContent;
    private static final String TAG = "READ_FRAG";
    public List<Notification> querynotifyread = new ArrayList<>();
    public List<Map<Object,String>> listInfo = new ArrayList<Map<Object,String>>();// 定义显示的内容包装
    public NotifyRecyclerView listInfoAdapter;
    public List<Notification> querynotify = new ArrayList<>();

    @Override
    public void onResume() {
        super.onResume();
        Intent in = getActivity().getIntent();
        String req = String.valueOf(in.getExtras().get("requestCode"));
        if (req.equals("0")) {
            queryNotificationsList();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ExitApplication.getInstance().addActivity(getActivity());
        Boolean bool = Consant_stringg.is_internet(getActivity());
        if (!bool) {
            Toast.makeText(getActivity(), "请检查您的网络设置!", Toast.LENGTH_SHORT).show();
        }
        View v = inflater.inflate(R.layout.xel_mine_notify_read, container, false);
        noContent = (TextView) v.findViewById(R.id.read_notify_noList);
        userID = App.newInstance().GetSharePrefrence_kejiezu(getContext());
        queryDatabase();
        readListView = (ListView) v.findViewById(R.id.read_notify_listview);
        readListView.setDividerHeight(0);
        queryNotificationsList();



        return v;
    }

    private void queryDatabase() {
        querynotify = Db_Notification.getInstance(getContext()).loadAllNote();
    }

    private void queryNotificationsList() {
        queryDatabase();
        for (Notification c : querynotify){
            Log.d(TAG,"当前循环的列表项："+ JSON.toJSONString(c));
            if (c.getNotify_check_id().equals(userID)&&c.getIs_read().equals("1")) {
                HashMap<Object, String> map = new HashMap<Object, String>();
                map.put("id", c.getNotification_id());
                map.put("hh:mm", c.getNotify_time().getHours() + ":" + c.getNotify_time().getMinutes());
                String yymmdd = Constant.getyy_mm_dd(c.getNotify_time());
                map.put("yy-mm-dd", yymmdd);
                map.put("title", c.getTitle());
                map.put("content", c.getContent());
                Log.d(TAG, "map内的值:" + map);
                listInfo.add(map);
            }
        }
        if (listInfo.isEmpty()){
            readListView.setVisibility(View.GONE);
            noContent.setVisibility(View.VISIBLE);
        }else {
            int layoutres = R.layout.xel_mine_notify_item;
            listInfoAdapter = new NotifyRecyclerView(getContext(), listInfo, layoutres);
            readListView.setAdapter(listInfoAdapter);
            querynotify.clear();
        }
    }


}
