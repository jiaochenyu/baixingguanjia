package com.linkhand.baixingguanjia.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseFragment;
import com.linkhand.baixingguanjia.entity.HomeNormalIcon;
import com.linkhand.baixingguanjia.entity.HomePopularIcon;
import com.linkhand.baixingguanjia.entity.HomeType;
import com.linkhand.baixingguanjia.ui.activity.MessageActivity;
import com.linkhand.baixingguanjia.ui.activity.goods.HotGoodsDetailActivity;
import com.linkhand.baixingguanjia.ui.activity.goods.NoticeGoodsDetailActivity;
import com.linkhand.baixingguanjia.ui.adapter.HomeRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JCY on 2017/6/14.
 * 说明：
 */

public class HomeFragment extends BaseFragment {
    @Bind(R.id.location)
    TextView mLocationTV;
    @Bind(R.id.location_layout)
    LinearLayout mLocationLayout;
    @Bind(R.id.message)
    LinearLayout mMessageLayout;
    @Bind(R.id.home_recyclerview)
    RecyclerView mRecyclerView;
    HomeRecyclerViewAdapter mAdapter;
    private View mView;

    private List<HomeType> mList;

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 101:
//                    Toast.makeText(getActivity(), "handler", Toast.LENGTH_SHORT).show();
                    if (msg.obj != null) {
                        mLocationTV.setText(msg.obj.toString());
                    }

            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, mView);
        setStatusBarColor(R.color.colorSystemBlue);
        initView();
        initData();
        initListener();
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
//        setStatusBarColor(R.color.colorSystemBlue);
    }

    private void initView() {
        //初始化recyclerview
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void initData() {
        mList = new ArrayList<>();
        initDataView();
        mAdapter = new HomeRecyclerViewAdapter(getActivity(), mList);
        mRecyclerView.setAdapter(mAdapter);
        getData();

    }


    private void initListener() {
        mAdapter.setOnItemClickListener(new HomeRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mList.get(position).getType() == 1) {
                    startActivity(new Intent(getActivity(), HotGoodsDetailActivity.class));
                } else if (mList.get(position).getType() == 2) {
                    startActivity(new Intent(getActivity(), NoticeGoodsDetailActivity.class));
                }
            }
        });

    }


    @OnClick({R.id.location_layout, R.id.message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.location_layout:
                break;
            case R.id.message:
                startActivity(new Intent(getActivity(), MessageActivity.class));
                break;
        }
    }

    /**
     * 获取数据
     */
    private void getData() {


        HomeType homeType1 = new HomeType();
        homeType1.setType(1);
        homeType1.setContent("236件已售 仅剩16小时");

        HomeType homeType2 = new HomeType();
        homeType2.setType(2);
        homeType2.setContent("明日10点开抢");

        mList.add(0, homeType1);
        mList.add(1, homeType2);

        mAdapter.notifyDataSetChanged();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    public void initDataView() {

        HomeType homeType1 = new HomeType();
        homeType1.setType(1);
        homeType1.setContent("236件已售 仅剩16小时");

        HomeType homeType2 = new HomeType();
        homeType2.setType(2);
        homeType2.setContent("明日10点开抢");

        List<HomeNormalIcon> normalIconList = new ArrayList<>();
        HomeNormalIcon homeNormalIcon1 = new HomeNormalIcon(R.drawable.icon_notice_red, "小区通知");
        HomeNormalIcon homeNormalIcon2 = new HomeNormalIcon(R.drawable.icon_house_property_orange, "房产");
        HomeNormalIcon homeNormalIcon3 = new HomeNormalIcon(R.drawable.icon_car_blue, "二手车");
        HomeNormalIcon homeNormalIcon4 = new HomeNormalIcon(R.drawable.icon_house_keep, "家政");
        HomeNormalIcon homeNormalIcon5 = new HomeNormalIcon(R.drawable.icon_education_blue, "教育");
        HomeNormalIcon homeNormalIcon6 = new HomeNormalIcon(R.drawable.icon_idle_green, "闲置物品");
        HomeNormalIcon homeNormalIcon7 = new HomeNormalIcon(R.drawable.icon_recruit_orang, "招聘");
        HomeNormalIcon homeNormalIcon8 = new HomeNormalIcon(R.drawable.icon_public_welfare, "公益");
        normalIconList.add(homeNormalIcon1);
        normalIconList.add(homeNormalIcon2);
        normalIconList.add(homeNormalIcon3);
        normalIconList.add(homeNormalIcon4);
        normalIconList.add(homeNormalIcon5);
        normalIconList.add(homeNormalIcon6);
        normalIconList.add(homeNormalIcon7);
        normalIconList.add(homeNormalIcon8);
        HomeType homeType3 = new HomeType();
        homeType3.setType(3);
        homeType3.setNormalIconList(normalIconList);
        mList.add(homeType3);

        List<HomePopularIcon> popularIconList = new ArrayList<>();
        for (int i = 0; i <= 7; i++) {
            HomePopularIcon homePopularIcon = new HomePopularIcon("", "二手车");
            popularIconList.add(homePopularIcon);
        }
        HomeType homeType4 = new HomeType();
        homeType4.setType(4);
        homeType4.setPopularIconList(popularIconList);
        mList.add(homeType4);
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
