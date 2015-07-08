package me.jp.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import me.jp.indicator.TabsIndicator;


public class MainActivity extends AppCompatActivity {
    ViewPager mViewPager;
    TabsIndicator mTabsIndicator;
    private String[] mTitles = new String[]{"Tab1", "Tab2", "Tab3", "Tab4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), mTitles));
        mViewPager.setOffscreenPageLimit(mTitles.length);
        mTabsIndicator = (TabsIndicator) findViewById(R.id.indicator);
        mTabsIndicator.setViewPager(0, mViewPager);
        mTabsIndicator.setAnimationWithTabChange(true);

    }


    public class MyPagerAdapter extends FragmentPagerAdapter {
        private String[] mTitles;

        public MyPagerAdapter(FragmentManager fm, String[] titles) {
            super(fm);
            mTitles = titles;
        }

        @Override
        public int getCount() {
            return mTitles.length;
        }

        @Override
        public Fragment getItem(int position) {
            return PageFragment.newInstance(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }
}
