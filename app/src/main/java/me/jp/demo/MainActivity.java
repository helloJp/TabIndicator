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


    ViewPager mViewPager2;
    TabsIndicator mTabsIndicator2;
    ViewPager mViewPager3;
    TabsIndicator mTabsIndicator3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }


    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), mTitles));
        mViewPager.setOffscreenPageLimit(mTitles.length);
        mTabsIndicator = (TabsIndicator) findViewById(R.id.indicator);
        mTabsIndicator.setViewPager(0, mViewPager, true);

        mViewPager2 = (ViewPager) findViewById(R.id.viewpager2);
        mViewPager2.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), mTitles));
        mViewPager2.setOffscreenPageLimit(mTitles.length);
        mTabsIndicator2 = (TabsIndicator) findViewById(R.id.indicator2);
        mTabsIndicator2.addTabIcon(R.mipmap.tab1_n, R.mipmap.tab1_p);
        mTabsIndicator2.addTabIcon(R.mipmap.tab2_n, R.mipmap.tab2_p);
        mTabsIndicator2.addTabIcon(R.mipmap.tab3_n, R.mipmap.tab3_p);
//        mTabsIndicator2.addTabIcon(R.mipmap.tab4_n, R.mipmap.tab4_p);
        mTabsIndicator2.setViewPager(0, mViewPager2, true);

        mViewPager3 = (ViewPager) findViewById(R.id.viewpager3);
        mViewPager3.setAdapter(new MyPagerAdapter2(getSupportFragmentManager(), mTitles));
        mViewPager3.setOffscreenPageLimit(mTitles.length);
        mTabsIndicator3 = (TabsIndicator) findViewById(R.id.indicator3);
        mTabsIndicator3.addTabIcon(R.mipmap.tab1_n, R.mipmap.tab1_p);
        mTabsIndicator3.addTabIcon(R.mipmap.tab2_n, R.mipmap.tab2_p);
        mTabsIndicator3.addTabIcon(R.mipmap.tab3_n, R.mipmap.tab3_p);
        mTabsIndicator3.addTabIcon(R.mipmap.tab4_n, R.mipmap.tab4_p);
        mTabsIndicator3.setViewPager(0, mViewPager3);

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


    public class MyPagerAdapter2 extends FragmentPagerAdapter {
        private String[] mTitles;

        public MyPagerAdapter2(FragmentManager fm, String[] titles) {
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

    }
}
