package com.linkhand.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.linkhand.R;
import com.linkhand.base.BaseFragment;

/**
 * Created by JCY on 2017/6/23.
 * 说明： 我的小区  信息反馈平台
 */

public class AreaFeedBackFragment extends BaseFragment {
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_area_feed_back, container, false);
        initView();
        initData();
        initListener();
        return view;
    }

    private void initView() {

    }

    private void initData() {

    }

    private void initListener() {

    }
}
