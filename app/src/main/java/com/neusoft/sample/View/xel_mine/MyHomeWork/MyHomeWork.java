package com.neusoft.sample.View.xel_mine.MyHomeWork;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.ncapdevi.sample.R;
import com.neusoft.sample.View.BaseActivity;

import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/*
* 学生端我的作业
* */
public class MyHomeWork extends BaseActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private TabLayout tabLayout;
    private ViewPager mViewPager;
    private ImageButton back;
    private static int flag = 0;
    private String data1;
    private Handler datahandler;
    private List<String> stringList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_home_work);
        setDarkStatusIcon(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        mViewPager = (ViewPager) findViewById(R.id.container);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        back = (ImageButton) findViewById(R.id.myHome_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(0);
        tabLayout.setupWithViewPager(mViewPager);




    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onDestroy() {
        finish();
        mSectionsPagerAdapter.fm.getFragments().clear();
        Log.d("onDestroy", "finishing");
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        mSectionsPagerAdapter.fm.getFragments().clear();
        Log.d("onDestroy", "finishing");

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        private FragmentManager fm;

        Fragment[] fragments = {Math_framents_for_homeWork.newInstance(),
                Chiese_framents_for_homeWork.newInstance(),
                English_framents_for_homeWork.newInstance()
        };

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fm = fm;


        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            Log.d("我是数学", "i---" + position);

            return fragments[position];
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            Log.d("我是数学", "i--OOO-" + position);

            return super.instantiateItem(container, position);


        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "数学";
                case 1:
                    return "语文";
                case 2:
                    return "英语";
            }
            return null;
        }
    }
}
