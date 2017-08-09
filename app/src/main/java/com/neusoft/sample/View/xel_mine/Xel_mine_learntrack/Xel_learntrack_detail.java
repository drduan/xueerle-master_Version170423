package com.neusoft.sample.View.xel_mine.Xel_mine_learntrack;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.ncapdevi.sample.R;
import com.neusoft.sample.Ctrl.wenchengcheng.ExitApplication;
import com.neusoft.sample.View.AlwaysMarqueeTextView;
import com.neusoft.sample.View.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Xel_learntrack_detail extends BaseActivity {

    private AlwaysMarqueeTextView main_title;
    private SimpleAdapter dataSetter = null;
    //private learnTrackDetailAdapter detailAdapter = null;
    private ListView detail_list;
    List<Xel_learnTrack_bean> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xel_learntrack_detail);
        ExitApplication.getInstance().addActivity(this);
        initView();
        initData();
    }

    private void initData() {
        list = (List<Xel_learnTrack_bean>)getIntent().getSerializableExtra("list");
        String title = getIntent().getStringExtra("title");
        List<Map<String, String>> datas = handleJsonToString(list);
        setDataDisplay(title,datas);
    }

    private List<Map<String, String>> handleJsonToString(List<Xel_learnTrack_bean> list) {
        List<Map<String,String>> analyzedDatas = new ArrayList<>();
        for (int i = 0;i<list.size();i++) {
            Map<String,String> map = new HashMap<>();
            StringBuffer sb = new StringBuffer();
            sb.append(i+1).append(".");
            sb.append(list.get(i).getJg_datetime().getMonth()+1).append("月").append(list.get(i).getJg_datetime().getDay()+1).append("日");
            String test_Group_Number = list.get(i).getTest_group_number();
            sb.append("第").append(test_Group_Number.substring(5, 7)).append("章").append(test_Group_Number.substring(7,9)).append("节").append(test_Group_Number.substring(9,11)).append("组");
            sb.append(" 对").append(list.get(i).getNumber_success()).append("题").append(" 错").append(list.get(i).getNumber_error()).append("题");
            map.put("detail", String.valueOf(sb));
            map.put("score",list.get(i).getScore()+"分");
            analyzedDatas.add(map);
        }
        return analyzedDatas;
    }

    private void initView() {
        main_title = (AlwaysMarqueeTextView) findViewById(R.id.learnTrack_detail_maintitle);
        ImageButton back = (ImageButton) findViewById(R.id.learnTrack_detail_back);
        detail_list = (ListView) findViewById(R.id.learnTrack_ListView);
        back.setOnClickListener(new onBackPressedListener());
    }

    private void setDataDisplay(String title, List<Map<String, String>> datas) {
        main_title.setText(title);
        dataSetter = new SimpleAdapter(this,datas, R.layout.xel_learntrack_item,new String[]{"detail","score"},new int[]{R.id.learntrack_detail, R.id.learntrack_score});
        //detailAdapter = new learnTrackDetailAdapter(this,datas,R.layout.xel_learntrack_item);
        detail_list.setDividerHeight(3);
        detail_list.setAdapter(dataSetter);
    }

    private class onBackPressedListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    }
}
