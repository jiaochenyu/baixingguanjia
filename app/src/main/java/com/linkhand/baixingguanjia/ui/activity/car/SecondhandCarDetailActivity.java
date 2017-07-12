package com.linkhand.baixingguanjia.ui.activity.car;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.customView.NoScrollListView;
import com.linkhand.baixingguanjia.entity.Car;
import com.linkhand.baixingguanjia.entity.Picture;
import com.linkhand.baixingguanjia.ui.adapter.SecondhandCarDetailAdapter;
import com.linkhand.baixingguanjia.utils.NetworkImageHolderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SecondhandCarDetailActivity extends BaseActivity implements OnItemClickListener, ViewPager.OnPageChangeListener {

    @Bind(R.id.iv_car_detai_img)
    ConvenientBanner mBanner;
    @Bind(R.id.nlv_car_detial_imgs)
    NoScrollListView mListView;

    SecondhandCarDetailAdapter mAdapter;
    @Bind(R.id.tv_car_detail_buy)
    TextView mContactTV;
    @Bind(R.id.pic_position)
    TextView mPicPositionTV;
    @Bind(R.id.c_name)
    TextView mCarNameTV;
    @Bind(R.id.release_time)
    TextView mReleaseTimeTV;
    @Bind(R.id.share_iv)
    ImageView mShareIV;
    @Bind(R.id.price)
    TextView mPriceTV;
    @Bind(R.id.location)
    TextView mLocationTV;
    @Bind(R.id.shangpai_time)
    TextView mShangpaiTimeTV;
    @Bind(R.id.iv_good_detai_back)
    ImageView mBack;
    @Bind(R.id.iv_good_detai_collect_select)
    ImageView mCollectIV;
    @Bind(R.id.ll_good_detail_collect)
    LinearLayout mCollectLL;
    private List<String> mPictureList;
    private List<Picture> mGoodsPicList;
    private Car mCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras != null) {
            mCar = (Car) extras.getSerializable("car");
            mPictureList = new ArrayList<>();
            for (int i = 0; i < mCar.getPic().size(); i++) {
                mPictureList.add(mCar.getPic().get(i).getUrl());
            }
        }
    }

    private void initView() {

    }

    private void initData() {
        mGoodsPicList = new ArrayList<>();
        mAdapter = new SecondhandCarDetailAdapter(this, R.layout.item_hot_good_detail_imgs, mGoodsPicList);
        mListView.setAdapter(mAdapter);
        initBanner();
        getData();
    }

    private void initBanner() {
        mBanner.setPages(new CBViewHolderCreator() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, mPictureList)
                .setPageIndicator(new int[]{R.drawable.circle_grey, R.drawable.circle_white})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnPageChangeListener(this).setOnItemClickListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onItemClick(int position) {

    }


    private void getData() {
        mGoodsPicList.add(new Picture("https://img.alicdn.com/imgextra/i4/714288429/TB2dLhGaVXXXXbNXXXXXXXXXXXX-714288429.jpg"));
        mGoodsPicList.add(new Picture("https://img.alicdn.com/imgextra/i3/726966853/TB2vhJ6lXXXXXbJXXXXXXXXXXXX_!!726966853.jpg"));
        mGoodsPicList.add(new Picture("https://img.alicdn.com/imgextra/i4/2081314055/TB2FoTQbVXXXXbuXpXXXXXXXXXX-2081314055.png"));
        mAdapter.notifyDataSetChanged();

        mPictureList.add("");
        mPictureList.add("");
        mPictureList.add("");
        mPictureList.add("");
        mBanner.notifyDataSetChanged();


    }

    @OnClick(R.id.tv_car_detail_buy)
    public void onViewClicked() {
        startActivity(new Intent(SecondhandCarDetailActivity.this, AddUserInfoActivity.class));
    }

    @OnClick({R.id.share_iv, R.id.price, R.id.iv_good_detai_back, R.id.tv_car_detail_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.share_iv:
                break;
            case R.id.price:
                break;
            case R.id.iv_good_detai_back:
                break;
            case R.id.tv_car_detail_buy:
                break;
        }
    }
}
