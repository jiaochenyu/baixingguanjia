package com.linkhand.baixingguanjia.ui.activity.detail;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.customView.GoodsInfoSelectDialog;
import com.linkhand.baixingguanjia.customView.GradationScrollView;
import com.linkhand.baixingguanjia.customView.NoScrollListView;
import com.linkhand.baixingguanjia.entity.Picture;
import com.linkhand.baixingguanjia.entity.Tag;
import com.linkhand.baixingguanjia.utils.NetworkImageHolderView;
import com.linkhand.baixingguanjia.ui.adapter.HotGoodsDetailAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NoticeGoodsDetailActivityOld extends BaseActivity implements GradationScrollView.ScrollViewListener, OnItemClickListener, ViewPager.OnPageChangeListener {

    @Bind(R.id.iv_good_detai_img)
    ConvenientBanner mBanner;
    @Bind(R.id.nlv_good_detial_imgs)
    NoScrollListView mNoScrollListView;
    @Bind(R.id.sv_container)
    ScrollView mScrollContainer;
    @Bind(R.id.format_layout)
    RelativeLayout mFormatLayout;
    @Bind(R.id.evaluate_layout)
    RelativeLayout mEvaluateLayout;
    private List<String> mPictureList;
    private HotGoodsDetailAdapter mAdapter;
    private List<Picture> mGoodsPicList;
    private GoodsInfoSelectDialog mDialog;
    private List<Tag> mTagList;

    private int byNum = 1; //购买数量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_goods_detail);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {

    }

    private void initData() {
        mPictureList = new ArrayList<>();
        mGoodsPicList = new ArrayList<>();
        mTagList = new ArrayList<>();
        mAdapter = new HotGoodsDetailAdapter(this, R.layout.item_hot_good_detail_imgs, mGoodsPicList);
        mNoScrollListView.setAdapter(mAdapter);


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
    public void onScrollChanged(GradationScrollView scrollView, int x, int y, int oldx, int oldy) {

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

        mTagList.add(new Tag("S",false));
        mTagList.add(new Tag("M",false));
        mTagList.add(new Tag("L",false));
        mTagList.add(new Tag("X",false));
        mTagList.add(new Tag("XL",false));
        mTagList.add(new Tag("XXL",false));

    }



    @OnClick({R.id.format_layout, R.id.evaluate_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.format_layout:
                showDialog();
                break;
            case R.id.evaluate_layout:
                break;
        }
    }

    private void showDialog() {
        final GoodsInfoSelectDialog.Builder builder = new GoodsInfoSelectDialog.Builder(this);
        builder.setTags(mTagList);
        builder.setAddOnClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.setSelectNum(++byNum);
            }
        });
        builder.setMinOnClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (byNum > 1){
                    builder.setSelectNum(--byNum);
                }

            }
        });

        builder.setBuyNowOnClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        mDialog = builder.create();
        Window window = mDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.mypopwindow_anim_style);
        window.setAttributes(lp);

        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = window.getAttributes();
        p.height = (int) (d.getHeight() * 0.8);
        p.width = d.getWidth();
        window.setAttributes(p);
        mDialog.show();

    }
}
