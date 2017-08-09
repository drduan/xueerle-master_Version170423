package com.neusoft.sample.View.xel_mine.Xel_mine_learntrack;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ncapdevi.sample.R;
import com.neusoft.sample.Ctrl.wenchengcheng.ExitApplication;
import com.neusoft.sample.View.BaseActivity;
import com.neusoft.sample.View.Fragment.xel_mine_fragment;

import java.util.List;
import java.util.Map;

public class Xel_mine_learntrack extends BaseActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ImageButton learnTrack_back;
    public static List<Map<Object, String>> dateSet = new Xel_learntrack_dataHandler().getNowSemesterDateList();
    public static String currentUserName = xel_mine_fragment.cUserInfo.getMobile();
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xel_mine_learntrack);
        ExitApplication.getInstance().addActivity(this);
        init();
        initData();
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    private void init() {
        learnTrack_back = (ImageButton) findViewById(R.id.track_back);
        learnTrack_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initData() {

    }

    public static String[] getDateString(){
        String[] temp = new String[dateSet.size()];
        int i = 0;
        for (Map<Object, String> m:dateSet){
            temp[i] = m.get("year") + "年" + m.get("month") + "月";
            i++;
        }
        return temp;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_xel_mine_learntrack, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_xel_mine_learntrack, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private FragmentManager fm;

        Fragment[] fragments = {Xel_learntrack_math_fragment.newInstance(0),
                Xel_learntrack_chinese_Fragment.newInstance(1),
                Xel_learntrack_english_Fragment.newInstance(2)
        };

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fm = fm;


        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        mSectionsPagerAdapter.fm.getFragments().clear();
    }
}
