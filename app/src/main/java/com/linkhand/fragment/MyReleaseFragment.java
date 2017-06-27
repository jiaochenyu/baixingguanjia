package com.linkhand.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.linkhand.R;
import com.linkhand.adapter.MyReleaseListViewAdapter;
import com.linkhand.base.BaseFragment;
import com.linkhand.entity.Release;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JCY on 2017/6/27.
 * 说明：  我的发布
 */

public class MyReleaseFragment extends BaseFragment {
    private static final String TAG = "MyReleaseFragment_info";

    View view;
    int type;
    @Bind(R.id.listview)
    ListView mListview;
    List<Release> mList;
    MyReleaseListViewAdapter mAdapter;

    public MyReleaseFragment(int type) {
        this.type = type;
        switch (type) {
            case 1:
                Log.d(TAG, "MyReleaseFragment: 发布中");
                break;
            case 2:
                Log.d(TAG, "MyReleaseFragment: 已下线");
                break;


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_release, container, false);
        ButterKnife.bind(this, view);
        initView();
        initData();
        initListener();

        return view;
    }

    private void initView() {

    }

    private void initData() {
        mList = new ArrayList<>();
        mAdapter = new MyReleaseListViewAdapter(getActivity(), R.layout.item_my_release, mList);
        mListview.setAdapter(mAdapter);
        getData();
    }

    private void getData() {
        mList.add(new Release());
        mList.add(new Release());
        mList.add(new Release());
        mList.add(new Release());
        mList.add(new Release());
        mAdapter.notifyDataSetChanged();
    }

    private void initListener() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
