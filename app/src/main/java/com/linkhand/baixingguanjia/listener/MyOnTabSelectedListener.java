package com.linkhand.baixingguanjia.listener;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

/**
 * Created by Administrator on 2016/11/7.
 */
public class MyOnTabSelectedListener implements TabLayout.OnTabSelectedListener {

    private final ViewPager mViewPager;

    public MyOnTabSelectedListener(ViewPager viewPager) {
        mViewPager = viewPager;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mViewPager.setCurrentItem(tab.getPosition(),false);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
