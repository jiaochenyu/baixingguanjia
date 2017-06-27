package com.linkhand.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linkhand.R;
import com.linkhand.adapter.NoticeRecyclerViewAdapter;
import com.linkhand.base.BaseFragment;
import com.linkhand.entity.Notice;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JCY on 2017/6/14.
 * 说明：
 */

public class NoticeFragment extends BaseFragment {
    @Bind(R.id.location)
    TextView mLocationTV;
    @Bind(R.id.imageView)
    ImageView mLocationIV;
    @Bind(R.id.location_layout)
    LinearLayout mLocationLayout;
    @Bind(R.id.message)
    LinearLayout mMessageLayout;
    @Bind(R.id.recylerview)
    RecyclerView mRecylerview;
    private View mView;
    private List<Notice> mList;
    private NoticeRecyclerViewAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_notice, null);
        ButterKnife.bind(this, mView);
        initView();
        initData();
        initListener();

        return mView;
    }
    public void onResume() {
        super.onResume();
        setStatusBarColor(R.color.colorSystemBlue);
    }

    private void initView() {
        mRecylerview.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void initData() {
        mList = new ArrayList<>();
        mAdapter = new NoticeRecyclerViewAdapter(getActivity(), R.layout.item_notice_recyler, mList);
        mRecylerview.setAdapter(mAdapter);
        getData();
    }


    private void initListener() {

    }


    @OnClick({R.id.location_layout, R.id.message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.location_layout:
                break;
            case R.id.message:
                break;
        }
    }

    private void getData() {
        Notice notice = new Notice();
        notice.setContent("明日6点开抢");
        notice.setDate("6月6日");
        notice.setImageUrl("");
        mList.add(notice);
        mList.add(notice);
        mList.add(notice);
        mAdapter.notifyDataSetChanged();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    public void onHiddenChanged(boolean hidden) {
// TODO Auto-generated method stub
        super.onHiddenChanged(hidden);
        if (hidden) {// 不在最前端界面显示

        } else {// 重新显示到最前端中
            setStatusBarColor(R.color.colorSystemBlue);
        }
    }
}
