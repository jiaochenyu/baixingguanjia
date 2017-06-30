package com.linkhand.baixingguanjia.ui.activity.sort;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.entity.HouseProperty;
import com.linkhand.baixingguanjia.ui.adapter.HousepropertyListViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HousePropertyActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.search_text_layout)
    LinearLayout mSearchLayout;  //隐藏显示
    @Bind(R.id.menu_layout)
    LinearLayout mMenuLayout;
    @Bind(R.id.listview)
    PullToRefreshListView mListview;
    HousepropertyListViewAdapter mAdapter;
    List<HouseProperty> mList;
    @Bind(R.id.radiogroup)
    RadioGroup mRadioGroup;
    @Bind(R.id.select_layout)
    LinearLayout mSelectLayout;
    @Bind(R.id.search_layout)
    LinearLayout mSearch;  // 搜索点击
    @Bind(R.id.location)
    TextView mLocation;
    @Bind(R.id.location_layout)
    RelativeLayout mLocationLayout;
    @Bind(R.id.type)
    TextView mType;
    @Bind(R.id.type_layout)
    RelativeLayout mTypeLayout;
    @Bind(R.id.price)
    TextView mPrice;
    @Bind(R.id.price_layout)
    RelativeLayout mPriceLayout;
    @Bind(R.id.store)
    TextView mStore;
    @Bind(R.id.store_layout)
    RelativeLayout mStoreLayout;

    boolean viewFlag = false; //搜索输入框显示和隐藏
    @Bind(R.id.sell)
    RadioButton mSell;
    @Bind(R.id.rent)
    RadioButton mRent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_property);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }

    private void initListener() {

    }

    private void initView() {
        mMenuLayout.setBackground(getResources().getDrawable(R.color.grayF9F));
    }

    private void initData() {
        mList = new ArrayList<>();
        mAdapter = new HousepropertyListViewAdapter(HousePropertyActivity.this, R.layout.item_house_property_info, mList);
        mListview.setAdapter(mAdapter);
        getData();
    }

    private void getData() {
        for (int i = 0; i < 4; i++) {
            mList.add(new HouseProperty());
        }
        mAdapter.notifyDataSetChanged();
    }


    @OnClick({R.id.back, R.id.search_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.search_layout:
                viewFlag = !viewFlag;
                if (viewFlag) {
                    mSearchLayout.setVisibility(View.VISIBLE);
                    mSelectLayout.setVisibility(View.GONE);
                } else {
                    mSearchLayout.setVisibility(View.GONE);
                    mSelectLayout.setVisibility(View.VISIBLE);
                }
                break;


        }
    }


}
