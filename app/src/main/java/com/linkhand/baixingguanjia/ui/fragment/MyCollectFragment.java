package com.linkhand.baixingguanjia.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseFragment;
import com.linkhand.baixingguanjia.entity.Collect;
import com.linkhand.baixingguanjia.ui.adapter.my.MyCollectListViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JCY on 2017/6/27.
 * 说明：  我的收藏
 */

public class MyCollectFragment extends BaseFragment {
    private static final String TAG = "info";

    View view;
    int type;
    @Bind(R.id.listview)
    ListView mListview;
    List<Collect> mList;
    MyCollectListViewAdapter mAdapter;

    public MyCollectFragment(int type) {
        this.type = type;
        switch (type) {
            case 1:
                Log.d(TAG, "MyCollectFragment: 商品类");
                break;
            case 2:
                Log.d(TAG, "MyCollectFragment: 服务类");
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
        mAdapter = new MyCollectListViewAdapter(getActivity(), R.layout.item_my_collect, mList);
        mListview.setAdapter(mAdapter);
        getData();
    }

    private void getData() {
        mList.add(new Collect());
        mList.add(new Collect());
        mList.add(new Collect());
        mList.add(new Collect());
        mList.add(new Collect());
        mList.add(new Collect());
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
