package com.neusoft.sample.Ctrl;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.neusoft.sample.View.xel_mine.Xel_mine_Youxiuzuowen_PDFActivity;

import java.util.List;
import java.util.Map;

/**
 * Created by AstroBoy on 2016/9/17.
 */
public class CompositionItemAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context context;
    private List<Map<Object, String>> listInfo;
    TextView title;
    public CompositionItemAdapter(Context context, List<Map<Object, String>> listInfo) {
        this.mInflater = LayoutInflater.from(context);
        this.listInfo = listInfo;
        this.context = context;
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
        View v = mInflater.inflate(android.R.layout.simple_list_item_1, null);
        Log.d("listInfo", JSON.toJSONString(listInfo));
       final String LXno =listInfo.get(position).get("pic");
        title = (TextView) v.findViewById(android.R.id.text1);
        for (int i = 1; i <= listInfo.size(); i++) {
            title.setText(listInfo.get(position).get("title1")+"("+listInfo.get(position).get("title3")+")");
        }
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Xel_mine_Youxiuzuowen_PDFActivity.class);
                intent.putExtra("pic",LXno);
                intent.putExtra("title1",listInfo.get(position).get("title1"));
                context.startActivity(intent);


            }
        });
        return v;
    }
}
