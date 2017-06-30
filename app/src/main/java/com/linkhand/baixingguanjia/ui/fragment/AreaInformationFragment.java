package com.linkhand.baixingguanjia.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseFragment;
import com.linkhand.baixingguanjia.entity.Areas;
import com.linkhand.baixingguanjia.ui.adapter.AreaListViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JCY on 2017/6/23.
 * 说明：
 */

public class AreaInformationFragment extends BaseFragment {
    View view;
    @Bind(R.id.listview)
    PullToRefreshListView mListview;
    AreaListViewAdapter mAdapter;
    List<Areas> mList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_area_information, container, false);
        initView();
        initData();
        initListener();
        ButterKnife.bind(this, view);
        return view;
    }

    private void initView() {

    }

    private void initData() {
        mList = new ArrayList<>();
        mAdapter = new AreaListViewAdapter(getActivity(),R.layout.item_area_info,mList);
        mListview.setAdapter(mAdapter);
        getdata();
    }

    private void getdata() {
        mList.add(new Areas());
        mList.add(new Areas());
        mList.add(new Areas());
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
