package com.linkhand.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.linkhand.R;
import com.linkhand.activity.my.MyOrderActivity;
import com.linkhand.activity.my.UserInfoActivity;
import com.linkhand.base.BaseFragment;
import com.linkhand.base.ConnectUrl;
import com.linkhand.kits.GlideCircleTransform;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JCY on 2017/6/14.
 * 说明：
 */

public class MyFragment extends BaseFragment {
    @Bind(R.id.location_layout)
    LinearLayout mSettingLayout;
    @Bind(R.id.message)
    LinearLayout mMessageLayout;
    @Bind(R.id.header_iv)
    ImageView mHeaderIV;
    @Bind(R.id.name)
    TextView mNameTV;
    @Bind(R.id.order)
    TextView mOrder;
    @Bind(R.id.show_order)
    TextView mShowOrder;
    @Bind(R.id.image)
    ImageView mImage;
    @Bind(R.id.order_layout)
    RelativeLayout mOrderLayout;
    @Bind(R.id.payment_layout)
    LinearLayout mPaymentLayout;
    @Bind(R.id.take_layout)
    LinearLayout mTakeLayout;
    @Bind(R.id.back_layout)
    LinearLayout mBackLayout;
    @Bind(R.id.info_layout)
    LinearLayout mInfoLayout;
    @Bind(R.id.true_layout)
    LinearLayout mTrueLayout;
    @Bind(R.id.wuye_layout)
    LinearLayout mWuyeLayout;
    @Bind(R.id.need_layout)
    LinearLayout mNeedLayout;
    @Bind(R.id.like_layout)
    LinearLayout mLikeLayout;
    @Bind(R.id.collect_layout)
    LinearLayout mCollectLayout;
    @Bind(R.id.pingjia_layout)
    LinearLayout mPingjiaLayout;
    @Bind(R.id.history_layout)
    LinearLayout mHistoryLayout;
    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_my, null);
        ButterKnife.bind(this, mView);
        setStatusBarColor(R.color.colorSystemBlack);
        initView();
        initListener();
        return mView;
    }


    private void initView() {
        Glide.with(this)
                .load(ConnectUrl.testUrl)
                .placeholder(R.mipmap.ic_launcher)
                .fitCenter()
                .transform(new GlideCircleTransform(getActivity()))
                .into(mHeaderIV);
    }

    private void initListener() {

    }



    @OnClick({R.id.location_layout, R.id.message, R.id.header_iv, R.id.order_layout, R.id.payment_layout, R.id.take_layout, R.id.back_layout, R.id.info_layout, R.id.true_layout, R.id.wuye_layout, R.id.need_layout, R.id.like_layout, R.id.collect_layout, R.id.pingjia_layout, R.id.history_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.location_layout:
                break;
            case R.id.message:
                break;
            case R.id.header_iv:
                startActivity(new Intent(getActivity(), UserInfoActivity.class));
                break;
            case R.id.order_layout:
                startActivity(new Intent(getActivity(), MyOrderActivity.class));
                break;
            case R.id.payment_layout:
                break;
            case R.id.take_layout:
                break;
            case R.id.back_layout:
                break;
            case R.id.info_layout:
                break;
            case R.id.true_layout:
                break;
            case R.id.wuye_layout:
                break;
            case R.id.need_layout:
                break;
            case R.id.like_layout:
                break;
            case R.id.collect_layout:
                break;
            case R.id.pingjia_layout:
                break;
            case R.id.history_layout:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
