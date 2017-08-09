package com.neusoft.sample.View.xel_mine.Xel_mine_learntrack;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.alibaba.fastjson.JSON;
import com.ncapdevi.sample.R;
import com.neusoft.sample.Model.Constant;
import com.neusoft.sample.Model.Post_Learntrack;
import com.neusoft.sample.Model.mutils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.neusoft.sample.View.BaseFragment.ARGS_INSTANCE;
import static com.neusoft.sample.View.xel_mine.Xel_mine_learntrack.Xel_mine_learntrack.currentUserName;
import static com.neusoft.sample.View.xel_mine.Xel_mine_learntrack.Xel_mine_learntrack.dateSet;
import static com.neusoft.sample.View.xel_mine.Xel_mine_learntrack.Xel_mine_learntrack.getDateString;

/**
 * A simple {@link Fragment} subclass.
 */
public class Xel_learntrack_english_Fragment extends Fragment {

    private final static String TAG = "Learn_track-English";

    private List<Map<Object, String>> date = dateSet;
    private List<Map<Object, String>> type = new Xel_learntrack_dataHandler().getSuitableSubjectList("english");
    private List<Xel_learnTrack_bean> responseContent = new ArrayList<>();
    private String UserName = currentUserName;
    private Spinner year_month;
    private Spinner testType;
    private LinearLayout go;
    private ProgressDialog dialog;
    private Handler mHandler;
    private JSONObject json = null;
    private String success;
    private String detailTitle = "英语>学习轨迹>";

    @Override
    public void onResume() {
        super.onResume();
        detailTitle = "英语>学习轨迹>";
        success = "";
        responseContent.clear();
    }


    public Xel_learntrack_english_Fragment() {
        // Required empty public constructor
    }

    public static Xel_learntrack_english_Fragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        Xel_learntrack_english_Fragment fragment = new Xel_learntrack_english_Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.xel_learntrack_english_fragment, container, false);
        initView(v);
        initData();
        return v;
    }

    private void initView(View v) {
        year_month = (Spinner) v.findViewById(R.id.learnTrack_year_month);
        testType = (Spinner) v.findViewById(R.id.learnTrack_type);
        go = (LinearLayout) v.findViewById(R.id.learnTrack_go);
    }

    private void initProgressDialog() {
        dialog = ProgressDialog.show(getActivity(), "Loading...", "正在加载数据，请稍后!");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
    }

    private void initData() {
        ArrayAdapter<String> yyyy_mm = new ArrayAdapter<>(getContext(), R.layout.xel_spiner_learntrack_view, getDateString());
        year_month.setAdapter(yyyy_mm);
        ArrayAdapter<String> type = new ArrayAdapter<>(getContext(), R.layout.xel_spiner_learntrack_view, getTypeName());
        testType.setAdapter(type);
        go.setOnClickListener(new Xel_learntrack_english_Fragment.pushInfosToPostThread());
    }

    private String[] getTypeName() {
        String[] temp = new String[type.size()];
        int i = 0;
        for (Map t : type) {
            temp[i] = t.get("name").toString();
            i++;
        }
        Log.d(TAG, "GET_TYPE_NAME:" + Arrays.toString(temp));
        return temp;
    }

    private class pushInfosToPostThread implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            initProgressDialog();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String selectDate = date.get(year_month.getSelectedItemPosition()).get("yyyy-mm");
                    String selectTypeCode = type.get(testType.getSelectedItemPosition()).get("code");
                    String[] selectedDate = getDateString();
                    detailTitle = "英语>学习轨迹>";
                    detailTitle = detailTitle + selectedDate[year_month.getSelectedItemPosition()] + ">" + type.get(testType.getSelectedItemPosition()).get("name");
                    Log.d(TAG, "date" + selectDate + "type" + selectTypeCode + "UserName" + UserName);
                    String result = null;
                    try {
                        result = Post_Learntrack.getStringCha(Constant.post_learntrack, selectTypeCode, selectDate, UserName);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d(TAG, "CatchException:" + e);
                    }
                    Log.d(TAG, "result:" + result);
                    Message msg = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("response", result);
                    msg.setData(bundle);
                    mHandler.sendMessage(msg);
                }
            }).start();
            dialog.dismiss();
            mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    String response = msg.getData().getString("response");
                    Log.d(TAG, "response==" + response);
                    if (response != null) {
                        try {
                            JSONArray data = new JSONArray();
                            json = new JSONObject(response);
                            success = json.getString("success");
                            if (success.equals("200")) {
                                data = json.getJSONArray("data");
                            }
                            Log.d(TAG, "JsonData" + data);
                            Log.d(TAG,"SUCCESS"+success);
                            switch (success) {
                                case "200":
                                    if (data == null || data.length() == 0) {
                                        ToastUtil.show(getActivity(), "没有这个月的数据~");
                                    } else {
                                        responseContent = JSON.parseArray(String.valueOf(data), Xel_learnTrack_bean.class);
                                        System.out.println(responseContent);
                                        JumpToDetail(responseContent, detailTitle);
                                    }
                                    break;
                                case "100":
                                    ToastUtil.show(getActivity(), json.getString("error"));
                                    break;
                                default:
                                    ToastUtil.show(getActivity(), "没有这个月的数据~");
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        ToastUtil.show(getActivity(), "请求数据失败,请联网重试~");
                    }
                }
            };
        }
    }

    private void JumpToDetail(List<Xel_learnTrack_bean> lists, String title) {
        Intent in = new Intent(getActivity(), Xel_learntrack_detail.class);
        in.putExtra("list", (Serializable) lists);
        in.putExtra("title", title);
        startActivity(in);
    }
}