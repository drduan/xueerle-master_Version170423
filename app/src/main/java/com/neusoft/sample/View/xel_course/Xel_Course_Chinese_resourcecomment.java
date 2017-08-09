package com.neusoft.sample.View.xel_course;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.ncapdevi.sample.R;
import com.neusoft.sample.View.Fragment.xel_error_fragment;

import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Administrator on 2016/6/25.
 */
public class Xel_Course_Chinese_resourcecomment extends FragmentActivity implements View.OnClickListener {

    private HorizontalScrollView horizontalScrollView;
    private LinearLayout layout;
  private List list;
    private String ID="";

//    @Override
//    protected void onStart() {
//        super.onStart();
//        list=new ArrayList();
//        SQLLoad sqlLoad=new SQLLoad();
//        SQLiteDatabase db =sqlLoad.openDatabase(this);
//        Cursor cursor = db.rawQuery("select * from feed ", null);
//
//        for(int i=0;i<cursor.getCount();i++){
//            cursor.moveToNext ();
//            ID  = cursor.getString(cursor.getColumnIndex("rowid"));
//            list.add(ID);
//        }
//        Log.d("SQL", list.toString());
//        cursor.close();
//        db.close();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xel_course_chinese_resourcecomment);
        Fragment fragment = new Xel_Course_Chinese_resourcecomment_fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

        initView();
//        initEvent();
        /**
         * test 从数据库中获取内容
         */

    }

    private void initView() {
        horizontalScrollView= (HorizontalScrollView) findViewById(R.id.horizontalsv);
        horizontalScrollView.setFillViewport(true);
        layout= (LinearLayout) findViewById(R.id.container_frag);
        //TODO
        /**
         * 从服务器中获取内容后
         * 把题号加载到button上面
         */


        for (int i=0;i<30;i++)
        {
            View view=getLayoutInflater().inflate(R.layout.custombtn_in,null);
            Button button= (Button) view.findViewById(R.id.question);
            button.setId(i);
            button.setText("MY");
            // 为item设置点击事件
//            button.setOnClickListener(this);
            layout.addView(view);

        }
    }


    @Override
    public void onClick(View v) {

        int id = v.getId();
// 获取item绑定的数据
        String content = (String) v.getTag();
        for (int i = 0; i < 30; i++) {
            Button button= (Button) v.findViewById(id);
//            final Drawable drawable = this.getDrawable(R.drawable.red_btn_change);
            Drawable drawable= ContextCompat.getDrawable(this, R.drawable.red_btn_change);
            button.setBackground(drawable);

            if (i == id) {
                //TODO
                /**
                 * fragment跳转页面
                 * 注意这里是从服务器中获取内容
                 * 然后加载到上面
                 */
                Fragment fragment = new xel_error_fragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

            }
        }
    }
    public void break_1(View view){
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
