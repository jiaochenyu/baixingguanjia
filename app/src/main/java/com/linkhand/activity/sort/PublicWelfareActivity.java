package com.linkhand.activity.sort;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkhand.R;
import com.linkhand.adapter.MyFragmentPagerAdapter;
import com.linkhand.base.BaseActivity;
import com.linkhand.base.MyOnTabSelectedListener;
import com.linkhand.fragment.PublicWelfareFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.linkhand.R.id.tabLayout;
import static com.linkhand.R.id.viewPager;

public class PublicWelfareActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.more_iv)
    ImageView mMoreIv;
    @Bind(tabLayout)
    TabLayout mTabLayout;
    @Bind(viewPager)
    ViewPager mViewPager;
    MyFragmentPagerAdapter adapter;
    private String[] titles = {"寻人", "寻物", "善行"};
    private List<Fragment> fragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_welfare);
        ButterKnife.bind(this);
        initView();
        initData();
    }
    private void initView() {

    }

    private void initData() {
        fragments = new ArrayList<>();
        fragments.add(new PublicWelfareFragment(1));
        fragments.add(new PublicWelfareFragment(2));
        fragments.add(new PublicWelfareFragment(3));

        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        //防止点击的时候出现中间的条目
        mTabLayout.setOnTabSelectedListener(new MyOnTabSelectedListener(mViewPager));
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
    }
    @OnClick({R.id.back, R.id.more_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                break;
            case R.id.more_iv:
                break;
        }
    }
}
