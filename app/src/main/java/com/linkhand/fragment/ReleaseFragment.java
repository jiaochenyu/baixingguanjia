package com.linkhand.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.linkhand.R;
import com.linkhand.activity.release.EducationReleaseActivity;
import com.linkhand.activity.release.HouseKeepReleaseActivity;
import com.linkhand.activity.release.HousePropertyReleaseActivity;
import com.linkhand.activity.release.IdleGoodsReleaseActivity;
import com.linkhand.activity.release.PublicWelfareReleaseActivity;
import com.linkhand.activity.release.RecruitReleaseActivity;
import com.linkhand.activity.release.SecondCarReleaseActivity;
import com.linkhand.adapter.ReleaseGridViewAdapter;
import com.linkhand.base.BaseFragment;
import com.linkhand.entity.Release;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JCY on 2017/6/14.
 * 说明：
 */

public class ReleaseFragment extends BaseFragment {
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.option)
    TextView mOptionTV;
    @Bind(R.id.gridView)
    GridView mGridView;
    private View mView;

    private ReleaseGridViewAdapter mAdapter;
    private List<Release> mList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_release, null);
        ButterKnife.bind(this, mView);
        setStatusBarColor(R.color.colorSystemBlue);
        initView();
        initData();
        initListener();
        return mView;
    }


    private void initView() {
    }

    private void initData() {
        mList = new ArrayList<>();
        mAdapter = new ReleaseGridViewAdapter(getActivity(), R.layout.item_release_gridview, mList);
        mGridView.setAdapter((ListAdapter) mAdapter);
        getData();

    }


    private void initListener() {
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
//                        startActivity(new Intent(getActivity(), CommonReleaseActivity.class));
                        startActivity(new Intent(getActivity(), HousePropertyReleaseActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(), IdleGoodsReleaseActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getActivity(), SecondCarReleaseActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(getActivity(), HouseKeepReleaseActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(getActivity(), EducationReleaseActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(getActivity(), RecruitReleaseActivity.class));
                        break;
                    case 6:
                        startActivity(new Intent(getActivity(), PublicWelfareReleaseActivity.class));
                        break;
                    case 7:
                        startActivity(new Intent(getActivity(), PublicWelfareReleaseActivity.class));
                        break;
                    case 8:
                        startActivity(new Intent(getActivity(), PublicWelfareReleaseActivity.class));
                        break;

                }
            }
        });
    }

    private void getData() {
        for (int i = 0; i < 9; i++) {
            mList.add(new Release("小区通知", ""));
        }
        mAdapter.notifyDataSetChanged();

    }


    @OnClick(R.id.option)
    public void onViewClicked() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
