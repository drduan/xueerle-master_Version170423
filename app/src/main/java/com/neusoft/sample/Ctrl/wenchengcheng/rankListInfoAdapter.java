package com.neusoft.sample.Ctrl.wenchengcheng;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ncapdevi.sample.R;
import com.neusoft.sample.View.AlwaysMarqueeTextView;

import java.util.List;
import java.util.Map;

/**
 * Created by AstroBoy on 2016/9/17.
 */
public class rankListInfoAdapter extends BaseAdapter {
    public static final String TAG = "ranklistinfoadapter";
    private LayoutInflater mInflater;
    private Context context;
    private List<Map<Object, String>> listInfo;
    TextView rank_item,points_item;
    AlwaysMarqueeTextView name_item,school_item,grade_item;
    public rankListInfoAdapter(Context context, List<Map<Object, String>> listInfo) {
        Log.d(TAG, "构造方法");
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = mInflater.inflate(R.layout.xel_ranklist_info_item, null);

        rank_item = (TextView) v.findViewById(R.id.rank_item);
        name_item = (AlwaysMarqueeTextView) v.findViewById(R.id.name_item);
        school_item = (AlwaysMarqueeTextView) v.findViewById(R.id.school_item);
        grade_item = (AlwaysMarqueeTextView) v.findViewById(R.id.grade_item);
        points_item = (TextView) v.findViewById(R.id.points_item);

        for (int i = 1; i <= listInfo.size(); i++) {
            rank_item.setText(String.valueOf(position+1));
            name_item.setText(listInfo.get(position).get("name"));
            school_item.setText(listInfo.get(position).get("school"));
            grade_item.setText(listInfo.get(position).get("class"));
            points_item.setText(listInfo.get(position).get("points"));
            rank_item.setTag(i);
        }

        return v;
    }
}
