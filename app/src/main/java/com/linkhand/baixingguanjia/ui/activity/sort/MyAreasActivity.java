package com.linkhand.baixingguanjia.ui.activity.sort;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.entity.Areas;
import com.linkhand.baixingguanjia.ui.adapter.AreaListViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyAreasActivity extends BaseActivity {

    @Bind(R.id.tabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;
    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.more_iv)
    ImageView mMoreIV;
    @Bind(R.id.feed_back)
    TextView mFeedBackTV;
    @Bind(R.id.listview)
    PullToRefreshListView mListview;

    AreaListViewAdapter mAdapter;
    List<Areas> mList;

//    private String[] titles = {"小区资讯", "反馈平台"};
//    private List<Fragment> fragments;
//    MyFragmentPagerAdapter adapter;

    Dialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_areas);
        ButterKnife.bind(this);
        initView();
        initDialog();
        initData();
    }


    private void initView() {
        mTitle.setText("我的小区");
    }

    private void initDialog() {

        mDialog = new Dialog(MyAreasActivity.this, R.style.Dialog);
        mDialog.setContentView(R.layout.dialog_area_feed_back);

        TextView submit = (TextView) mDialog.findViewById(R.id.submit);
        final EditText contentET = (EditText) mDialog.findViewById(R.id.content_edit);
        final EditText phoneET = (EditText) mDialog.findViewById(R.id.phone_edit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //弹出框点击事件
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
//                    contentET.getText().toString();
                }
            }
        });
    }

    private void initData() {
//        fragments = new ArrayList<>();
//        fragments.add(new AreaInformationFragment());
//        fragments.add(new AreaFeedBackFragment());
//        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments, titles);
//        mViewPager.setAdapter(adapter);
//        mTabLayout.setupWithViewPager(mViewPager);
//        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mList = new ArrayList<>();
        mAdapter = new AreaListViewAdapter(this, R.layout.item_area_info, mList);
        mListview.setAdapter(mAdapter);
        getData();
    }

    private void getData() {
        for (int i = 0; i < 4; i++) {
            mList.add(new Areas());
        }
        mAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.back, R.id.more_iv, R.id.feed_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.more_iv:
                break;
            case R.id.feed_back:
                mDialog.show();
                break;
        }
    }


}
