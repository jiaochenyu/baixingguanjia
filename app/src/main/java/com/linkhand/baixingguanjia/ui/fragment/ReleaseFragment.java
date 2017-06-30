package com.linkhand.baixingguanjia.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseFragment;
import com.linkhand.baixingguanjia.ui.activity.release.EducationReleaseActivity;
import com.linkhand.baixingguanjia.ui.activity.release.HouseKeepReleaseActivity;
import com.linkhand.baixingguanjia.ui.activity.release.HousePropertyReleaseActivity;
import com.linkhand.baixingguanjia.ui.activity.release.IdleGoodsReleaseActivity;
import com.linkhand.baixingguanjia.ui.activity.release.PublicWelfareReleaseActivity;
import com.linkhand.baixingguanjia.ui.activity.release.RecruitReleaseActivity;
import com.linkhand.baixingguanjia.ui.activity.release.SecondCarReleaseActivity;
import com.linkhand.baixingguanjia.ui.adapter.ReleaseGridViewAdapter;

import java.util.ArrayList;
import java.util.Collections;
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
    String[] titles = {"房产", "闲置物品", "二手车", "家政", "教育", "招聘", "寻人", "寻物", "善行"};
    int[] icons = {R.drawable.fangchan, R.drawable.xianzhi, R.drawable.che, R.drawable.jiazheng,
            R.drawable.jiaoyu, R.drawable.zhaopin, R.drawable.xunren, R.drawable.xunwu, R.drawable.shanxing};

    private ReleaseGridViewAdapter mAdapter;
    private List<String> mList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_release, null);
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
    }

    private void initData() {
        mList = new ArrayList<>();
        Collections.addAll(mList, titles);
        mAdapter = new ReleaseGridViewAdapter(getActivity(), R.layout.item_release_gridview, mList,icons);
        mGridView.setAdapter(mAdapter);
    }


    private void initListener() {
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
//                        startActivity(new Intent(getActivity(), CommonReleaseActivity.class));
                        startActivity(new Intent(getActivity(),HousePropertyReleaseActivity.class));
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



    @OnClick(R.id.option)
    public void onViewClicked() {
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
