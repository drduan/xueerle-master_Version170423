package com.neusoft.sample.View.Adapter;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ncapdevi.sample.R;

import java.util.List;

/**
 * Created by wangyujie on 2016/11/4.
 */
public class Cuoti_spinner_item_Adapter extends BaseAdapter {

    private Context context;
    List<SpannableStringBuilder> error_test_kousuan_ke_list;
    public Cuoti_spinner_item_Adapter(Context context, List<SpannableStringBuilder> error_test_kousuan_ke_list) {

        this.context=context;
        this.error_test_kousuan_ke_list=error_test_kousuan_ke_list;

    }

    @Override
    public int getCount() {
        return error_test_kousuan_ke_list.size();
    }

    @Override
    public Object getItem(int position) {
        return error_test_kousuan_ke_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return error_test_kousuan_ke_list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= LayoutInflater.from(context).inflate(R.layout.cuoti_spinner_item_adapter_layout,null);
        TextView title= (TextView) view.findViewById(R.id.cuoti_item_title);
        TextView nub= (TextView) view.findViewById(R.id.cuoti_nub);

        LinearLayout IsZero= (LinearLayout) view.findViewById(R.id.isZero);
//        error_test_kousuan_ke_list.get(position).sub
        Log.d("error_test",error_test_kousuan_ke_list.get(position).toString()+"*");
        if (error_test_kousuan_ke_list.get(position).toString().contains("&")) {
            int titlesnub = error_test_kousuan_ke_list.get(position).toString().indexOf("&");
            title.setText(error_test_kousuan_ke_list.get(position).toString().substring(0,titlesnub-1));
            nub.setText(error_test_kousuan_ke_list.get(position).toString().substring(titlesnub+1));
        }
        else {
            title.setText(error_test_kousuan_ke_list.get(position).toString());
            IsZero.setVisibility(View.INVISIBLE);
        }

        return view;
    }
}
