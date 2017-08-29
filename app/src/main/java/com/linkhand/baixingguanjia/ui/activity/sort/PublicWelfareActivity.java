package com.linkhand.baixingguanjia.ui.activity.sort;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.listener.MyOnTabSelectedListener;
import com.linkhand.baixingguanjia.ui.adapter.MyFragmentPagerAdapter;
import com.linkhand.baixingguanjia.ui.fragment.PublicWelfareFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PublicWelfareActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.show_layout)
    LinearLayout mLayoutShow;
    @Bind(R.id.cha)
    ImageView mChaIV;
    @Bind(R.id.edit_layout)
    LinearLayout mLayoutEdit;
    @Bind(R.id.search_text)
    TextView mSearchTV;
    @Bind(R.id.edit)
    EditText mEditText;

    @Bind(R.id.tabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.viewPager)
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
        initListener();
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
        mViewPager.setOffscreenPageLimit(3);
//        mViewPager.setOffscreenPageLimit(6);
        mTabLayout.setupWithViewPager(mViewPager);
        //防止点击的时候出现中间的条目
        mTabLayout.setOnTabSelectedListener(new MyOnTabSelectedListener(mViewPager));
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    private void initListener() {
        // 搜索输入框事件监听
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(mEditText.getText())) {
                    mChaIV.setVisibility(View.VISIBLE);
                    mSearchTV.setText(getStrgRes(R.string.search));
                } else {
                    mChaIV.setVisibility(View.GONE);
                    mSearchTV.setText(getStrgRes(R.string.cancel));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.back, R.id.show_layout, R.id.cha, R.id.search_text,})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.show_layout:
                mLayoutShow.setVisibility(View.GONE);
                mLayoutEdit.setVisibility(View.VISIBLE);
                mSearchTV.setVisibility(View.VISIBLE);
                break;
            case R.id.cha:
                mEditText.setText("");
                break;
            case R.id.search_text:
                if (mSearchTV.getText().equals(getStrgRes(R.string.cancel))) {
                    mLayoutShow.setVisibility(View.VISIBLE);
                    mLayoutEdit.setVisibility(View.GONE);
                    mSearchTV.setVisibility(View.GONE);
                    mEditText.setText("");
                }
                ((PublicWelfareFragment) fragments.get(mTabLayout.getSelectedTabPosition())).setTiaojian(mEditText.getText().toString());
                break;
        }
    }
}
