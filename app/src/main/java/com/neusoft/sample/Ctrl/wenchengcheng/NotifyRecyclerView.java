package com.neusoft.sample.Ctrl.wenchengcheng;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ncapdevi.sample.R;
import com.neusoft.sample.View.xel_mine.Xel_mine_notify.xel_mine_notify_detail;

import java.util.List;
import java.util.Map;

/**
 * Created by AstroBoy on 2016/10/7.
 */

public class NotifyRecyclerView extends BaseAdapter {

    public static final String TAG = "NotifyRecyclerView";
    private LayoutInflater mInflater;
    private Context context;
    private List<Map<Object, String>> listInfo;
    private int layoutres;
    LinearLayout notify_oc;
    View v;
    TextView notify_item_hour_min, notify_item_date, notify_item_title, notify_item_content;

    public NotifyRecyclerView(Context context, List<Map<Object, String>> listInfo, int layoutres) {
        Log.d(TAG, "构造方法");
        this.mInflater = LayoutInflater.from(context);
        this.listInfo = listInfo;
        this.context = context;
        this.layoutres = layoutres;
    }

    @Override
    public int getCount() {
        return listInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return listInfo.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        v = mInflater.inflate(layoutres, null);

        notify_oc = (LinearLayout) v.findViewById(R.id.listItem_layout);

        notify_item_hour_min = (TextView) v.findViewById(R.id.notify_item_hour_min);
        notify_item_date = (TextView) v.findViewById(R.id.notify_item_date);
        notify_item_title = (TextView) v.findViewById(R.id.notify_item_title);
        notify_item_content = (TextView) v.findViewById(R.id.notify_item_content);

        for (int i = 1; i <= listInfo.size(); i++) {
            notify_oc.setTag(listInfo.get(position).get("id"));
            notify_item_hour_min.setText(isNullConvert(listInfo.get(position).get("hh:mm")));
            notify_item_date.setText(isNullConvert(listInfo.get(position).get("yy-mm-dd")));
            notify_item_title.setText(isNullConvert(listInfo.get(position).get("title")));
            notify_item_content.setText(isNullConvert(listInfo.get(position).get("content")));
        }

        notify_oc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ocID = String.valueOf(notify_oc.getTag());
                listInfo.remove(position);
                Log.d(TAG, "当前点击的通知ID:" + ocID);
                Intent in = new Intent(context, xel_mine_notify_detail.class);
                in.putExtra("NotificationID",ocID);
                context.startActivity(in);
            }
        });

        return v;

    }

    private String isNullConvert(String content) {
        String inValidContent = "无内容";
        if (content == null) {
            return inValidContent;
        } else {
            return content;
        }
    }
}
