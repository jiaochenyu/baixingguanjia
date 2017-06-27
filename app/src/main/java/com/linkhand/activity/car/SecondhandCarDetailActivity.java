package com.linkhand.activity.car;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.linkhand.R;
import com.linkhand.adapter.SecondhandCarDetailAdapter;
import com.linkhand.base.BaseActivity;
import com.linkhand.customView.NoScrollListView;
import com.linkhand.entity.Picture;
import com.linkhand.kits.NetworkImageHolderView;

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
    private List<String> mPictureList;
    private List<Picture> mGoodsPicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {

    }

    private void initData() {
        mPictureList = new ArrayList<>();
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
}
