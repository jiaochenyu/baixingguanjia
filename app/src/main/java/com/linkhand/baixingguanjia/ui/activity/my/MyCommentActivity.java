package com.linkhand.baixingguanjia.ui.activity.my;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.ui.adapter.MyFragmentPagerAdapter;
import com.linkhand.baixingguanjia.ui.fragment.MyComment1Fragment;
import com.linkhand.baixingguanjia.ui.fragment.MyComment2Fragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 我的评价
 */
public class MyCommentActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.tabLayout)
    XTabLayout mTabLayout;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;
    private String[] titles = {"商品类", "服务类"};
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
        mTitle.setText("我的评价");

    }

    private void initData() {
        fragments = new ArrayList<>();
        fragments.add(new MyComment1Fragment(1));
        fragments.add(new MyComment2Fragment(2));

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
        finish();
    }
}
