package com.linkhand.activity.sort;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.linkhand.R;
import com.linkhand.adapter.HousekeepingListViewAdapter;
import com.linkhand.base.BaseActivity;
import com.linkhand.entity.Housekeeping;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HousekeepingActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.ll_search)
    LinearLayout mSearch;
    @Bind(R.id.menu_layout)
    LinearLayout mMenuLayout;
    @Bind(R.id.listview)
    PullToRefreshListView mListview;
    HousekeepingListViewAdapter mAdapter;
    List<Housekeeping> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_housekeeping);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {

    }

    private void initData() {
        mList = new ArrayList<>();
        mAdapter = new HousekeepingListViewAdapter(HousekeepingActivity.this, R.layout.item_house_keeping_info, mList);
        mListview.setAdapter(mAdapter);
        getData();
    }

    private void getData() {
        for (int i = 0; i < 4; i++) {
            mList.add(new Housekeeping());
        }
        mAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.back, R.id.ll_search, R.id.menu_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                break;
            case R.id.ll_search:
                break;
            case R.id.menu_layout:
                break;
        }
    }
}
