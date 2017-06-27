package com.linkhand.activity.my;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.linkhand.R;
import com.linkhand.adapter.MyFragmentPagerAdapter;
import com.linkhand.base.BaseActivity;
import com.linkhand.base.MyOnTabSelectedListener;
import com.linkhand.fragment.OrderFragment;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        ButterKnife.bind(this);
        inflater = LayoutInflater.from(this);
        initView();
        initData();
    }


    private void initView() {
        initDialog();
    }

    private void initData() {
        fragments = new ArrayList<>();
        fragments.add(new OrderFragment(1));
        fragments.add(new OrderFragment(2));
        fragments.add(new OrderFragment(3));
        fragments.add(new OrderFragment(4));

        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(adapter);
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
