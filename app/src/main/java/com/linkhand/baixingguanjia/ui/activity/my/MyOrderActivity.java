package com.linkhand.baixingguanjia.ui.activity.my;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.entity.EventFlag;
import com.linkhand.baixingguanjia.listener.MyOnTabSelectedListener;
import com.linkhand.baixingguanjia.ui.adapter.MyFragmentPagerAdapter;
import com.linkhand.baixingguanjia.ui.fragment.OrderFragment;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyOrderActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.more_iv)
    ImageView mMore;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;

    private String[] titles = {"全部", "待付款", "带自提", "已提货"};
    private List<Fragment> fragments;

    LayoutInflater inflater;
    MyFragmentPagerAdapter adapter;
    private PopupWindow popupWindow;

    private Dialog mDialog;
    boolean isPaySuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        ButterKnife.bind(this);
        inflater = LayoutInflater.from(this);
        initView();
        initData();
        if (isPaySuccess) {
            showPaySuccessDialog();
        }
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras != null) {
            isPaySuccess = extras.getBoolean("isSuccess", false);
        }
    }

    private void initView() {
        initDialog();

    }

    private void initData() {
        fragments = new ArrayList<>();
        //0：未付款1：待自提2已提货）
        fragments.add(new OrderFragment(-1));
        fragments.add(new OrderFragment(0));
        fragments.add(new OrderFragment(1));
        fragments.add(new OrderFragment(2));

        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
        tabLayout.setupWithViewPager(viewPager);
        //防止点击的时候出现中间的条目
        tabLayout.setOnTabSelectedListener(new MyOnTabSelectedListener(viewPager));
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

    }


    private void initDialog() {

        mDialog = new Dialog(MyOrderActivity.this, R.style.Dialog);
        mDialog.setContentView(R.layout.dialog_pay_success);

        TextView ok = (TextView) mDialog.findViewById(R.id.btn_ok);
        TextView numTV = (TextView) mDialog.findViewById(R.id.num);
        numTV.setText("2017062211536");
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //弹出框点击事件
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
            }
        });
    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Subscribe
    public void onEvent(EventFlag eventFlag) {
        if (eventFlag.getFlag().equals("paySuccess")) {
            showPaySuccessDialog();
        }

    }

    // 购买成功
    private void showPaySuccessDialog() {
        final Dialog mDialog = new Dialog(MyOrderActivity.this, R.style.Dialog);
        mDialog.setContentView(R.layout.dialog_promt_pay_success);
        TextView info = (TextView) mDialog.findViewById(R.id.info);
        String htmlInfo = "感谢您的光顾，赠与您：" + "<img src=\"" + R.drawable.icon_gold + "\"/>" + "黄金5两~";
        info.setText(Html.fromHtml(htmlInfo));
        TextView know = (TextView) mDialog.findViewById(R.id.yes);
        know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //弹出框点击事件
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
            }
        });
        mDialog.show();
    }

    @OnClick({R.id.back, R.id.more_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.more_iv:
                mDialog.show();
                break;
        }
    }
}
