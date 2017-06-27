package com.linkhand.activity.my;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.linkhand.R;
import com.linkhand.adapter.MyFragmentPagerAdapter;
import com.linkhand.base.BaseActivity;
import com.linkhand.fragment.MyReleaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.linkhand.R.id.tabLayout;
import static com.linkhand.R.id.viewPager;

/**
 * 我的发布
 */
public class MyReleaseActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(tabLayout)
    XTabLayout mTabLayout;
    @Bind(viewPager)
    ViewPager mViewPager;
    private String[] titles = {"发布中", "已下线"};
    private List<Fragment> fragments;
    MyFragmentPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_release);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }

    private void initView() {

    }

    private void initData() {
        fragments = new ArrayList<>();
        fragments.add(new MyReleaseFragment(1));
        fragments.add(new MyReleaseFragment(2));

        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        //防止点击的时候出现中间的条目
//        mTabLayout.setOnTabSelectedListener((XTabLayout.OnTabSelectedListener) new MyOnTabSelectedListener(mViewPager));
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    private void initListener() {

    }

    @OnClick(R.id.back)
    public void onViewClicked() {
    }
}
