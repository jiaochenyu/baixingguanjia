package com.linkhand.baixingguanjia.ui.fragment;

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
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseFragment;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.kits.GlideCircleTransform;
import com.linkhand.baixingguanjia.ui.activity.my.MyAppointmentActivity;
import com.linkhand.baixingguanjia.ui.activity.my.MyCollectActivity;
import com.linkhand.baixingguanjia.ui.activity.my.MyFeedBackActivity;
import com.linkhand.baixingguanjia.ui.activity.my.MyReleaseActivity;

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

    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_my, null);
        ButterKnife.bind(this, mView);
        initView();
        initListener();
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        setStatusBarColor(R.color.colorSystemBlack);
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
            setStatusBarColor(R.color.colorSystemBlack);
        }
    }

    @OnClick({R.id.info_layout, R.id.appointment_layout, R.id.fabu_layout, R.id.fankui_layout, R.id.collect_layout, R.id.pingjia_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.info_layout:
                break;
            case R.id.appointment_layout:
                startActivity(new Intent(getActivity(), MyAppointmentActivity.class));
                break;
            case R.id.fabu_layout:
                startActivity(new Intent(getActivity(), MyReleaseActivity.class));
                break;
            case R.id.fankui_layout:
                startActivity(new Intent(getActivity(), MyFeedBackActivity.class));
                break;
            case R.id.collect_layout:
                startActivity(new Intent(getActivity(), MyCollectActivity.class));
                break;
            case R.id.pingjia_layout:
                break;
        }
    }
}
