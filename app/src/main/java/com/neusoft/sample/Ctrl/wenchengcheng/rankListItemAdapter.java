package com.neusoft.sample.Ctrl.wenchengcheng;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.ncapdevi.sample.R;
import com.neusoft.sample.Ctrl.Db_RankListService;
import com.neusoft.sample.GreenDao.Ranklist;
import com.neusoft.sample.GreenDao.User;
import com.neusoft.sample.Model.Constant;
import com.neusoft.sample.Model.Post_area_rankList;
import com.neusoft.sample.View.Fragment.xel_mine_fragment;
import com.neusoft.sample.View.xel_mine.Xel_mine_ranklist_detail;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by AstroBoy on 2016/9/12.
 */
public class rankListItemAdapter extends BaseAdapter {

    public static final String TAG = "ranklistitemadapter";
    private LayoutInflater mInflater;
    private Context context;
    private List<Map<Object, String>> listItem;
    String cityNub, gradeNub, year, month, monthNub, uniqueID, city, grade;
    Map<Object, String> querymap = null;
    private User cUserInfo = null;
    String success;
    JSONObject json = null;
    Handler handler;
    Intent intent;

    public rankListItemAdapter(Context context, List<Map<Object, String>> listItem) {
        Log.d(TAG, "构造方法");
        this.mInflater = LayoutInflater.from(context);
        this.listItem = listItem;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Object getItem(int position) {
        return listItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = mInflater.inflate(R.layout.xel_mine_ranklist_item, null);
        TextView dateItem = (TextView) v.findViewById(R.id.textView3);
        cUserInfo = xel_mine_fragment.tUserInfo;
        city = cUserInfo.getCity();
        grade = cUserInfo.getGrade();
        cityNub = cUserInfo.getCity_nub();
        gradeNub = cUserInfo.getGrade_nub();
        gradeNub = gradeNub.substring(gradeNub.length() - 4, gradeNub.length() - 2);
        year = String.valueOf(Constant.getNowDate().getYear() + 1900);
        for (int i = 1; i <= listItem.size(); i++) {
            dateItem.setText(listItem.get(position).get("subTitle"));
            dateItem.setTag(i);
        }
        dateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int monthInt = position + 1;
                month = String.valueOf(monthInt);
                if (month.length() < 2) {
                    monthNub = "0" + month;
                } else {
                    monthNub = month;
                }
                uniqueID = year + monthNub + cityNub + gradeNub;
                Log.d(TAG, "POSITION:" + monthInt + " " + "uniqueID:" + uniqueID);
                Log.d(TAG, "queryData:" + "cityNub:" + cityNub + " " + "gradeNub:" + gradeNub + " " + "monthNub:" + monthNub + " " + "year:" + year);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.d(TAG, "queryData:" + "cityNub:" + cityNub + " " + "gradeNub:" + gradeNub + " " + "monthNub:" + monthNub + " " + "year:" + year);
                            String originArray = Post_area_rankList.getStringCha(cityNub, gradeNub, monthNub, year);
                            Log.d(TAG, "RANKLIST result:" + originArray);
                            Message msg = new Message();
                            Bundle bundle = new Bundle();
                            bundle.putString("response", originArray);
                            msg.setData(bundle);
                            handler.sendMessage(msg);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                getHandleMsg();
            }

        });
        return v;
    }

    private void getHandleMsg() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String response = msg.getData().getString("response");
                Log.d(TAG, "response==" + response);
                try {
                    json = new JSONObject(response);
                    success = json.getString("success");
                    if (success.equals("200")) {
                        Log.d(TAG, "Server Response:" + success);
                        List<Ranklist> ranklists = JSON.parseArray(json.get("data").toString(), Ranklist.class);
                        for (Ranklist ranklist : ranklists) {
                            ranklist.setUnique_id(uniqueID);
                            Log.d(TAG, "uni_id" + uniqueID);
                        }
                        Collections.sort(ranklists);
                        Log.d(TAG, "排序结果:" + JSON.toJSONString(ranklists));
                        Db_RankListService.getInstance(context).saveNoteLists(ranklists);
                        Log.d(TAG, "已走过Toast");
                    } else if (success.equals("100")) {
                        Log.d(TAG, "Server Response:" + success);
                    } else {
                        Log.d(TAG, "Server Response:" + success);
                    }
                    JumpToDetail(success);
                } catch (JSONException e1) {
                    String err = String.valueOf(e1);
                    e1.printStackTrace();
                }
            }
        };
    }

    private void JumpToDetail(String success) {
        Log.d(TAG, "status:" + success);
        this.success = success;
        if (this.success.equals("200")) {
            intent = new Intent(context, Xel_mine_ranklist_detail.class);
            intent.putExtra("city", city);
            intent.putExtra("year", year);
            intent.putExtra("month", month);
            intent.putExtra("grade",gradeNub);
            intent.putExtra("queryCode", uniqueID);
            context.startActivity(intent);
        }else {
            Toast.makeText(context,"当前月份/地区未发布排行榜",Toast.LENGTH_SHORT).show();
        }
    }
}
