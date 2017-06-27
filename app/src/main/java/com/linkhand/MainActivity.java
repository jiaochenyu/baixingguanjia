package com.linkhand;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linkhand.adapter.ListviewAdapter;
import com.linkhand.base.BaseActivity;
import com.linkhand.fragment.HomeFragment;
import com.linkhand.fragment.MyFragment;
import com.linkhand.fragment.NoticeFragment;
import com.linkhand.fragment.ReleaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    ListviewAdapter hListViewAdapter;
    @Bind(R.id.frameLayout)
    FrameLayout mFrameLayout;


    private FragmentManager fragmentManager;

    private LinearLayout linearLayout1;
    private LinearLayout linearLayout2;
    private LinearLayout linearLayout3;
    private LinearLayout linearLayout4;

    private HomeFragment homeFragment;
    private NoticeFragment noticeFragment;
    private ReleaseFragment releaseFragment;
    private MyFragment userFragment;
    private static final int HOME = 1;
    private static final int NOTICE = 2;
    private static final int RELEASE = 3;
    private static final int USER = 4;

    public Handler mHandler;

    private String[] name = {"首页", "预告", "发布", "我的"};
    private int[] image = {
            R.drawable.icon_home_gray,
            R.drawable.icon_home_blue,
            R.drawable.icon_clock_gray,
            R.drawable.icon_clock_blue,
            R.drawable.icon_release_gray,
            R.drawable.icon_release_blue,
            R.drawable.icon_my_gray,
            R.drawable.icon_my_blue
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initData();

    }

    private void initView() {
        linearLayout1 = (LinearLayout) findViewById(R.id.linearlayout1);
        linearLayout2 = (LinearLayout) findViewById(R.id.linearlayout2);
        linearLayout3 = (LinearLayout) findViewById(R.id.linearlayout3);
        linearLayout4 = (LinearLayout) findViewById(R.id.linearlayout4);

        linearLayout1.setOnClickListener(this);
        linearLayout2.setOnClickListener(this);
        linearLayout3.setOnClickListener(this);
        linearLayout4.setOnClickListener(this);
    }

    private void initData() {
        fragmentManager = getSupportFragmentManager();

        showFragment(HOME);

        mHandler = homeFragment.mHandler;
        mHandler.sendEmptyMessage(101);
    }

    @OnClick(R.id.frameLayout)
    public void onViewClicked() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearlayout1:
                showFragment(HOME);
                break;
            case R.id.linearlayout2:
                showFragment(NOTICE);
                break;
            case R.id.linearlayout3:
                showFragment(RELEASE);
                break;
            case R.id.linearlayout4:
                showFragment(USER);
                break;
        }
    }

    private void showFragment(int index) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //隐藏所有的Fragment
        hideFragment(fragmentTransaction);
        setDefaultBottom();
        setName(index);
        //显示指定的Fragment

        switch (index) {
            //首页
            case HOME:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    fragmentTransaction.add(R.id.frameLayout, homeFragment);
                } else {
                    fragmentTransaction.show(homeFragment);
                }
                break;
            //预告
            case NOTICE:
                if (noticeFragment == null) {
                    noticeFragment = new NoticeFragment();
                    fragmentTransaction.add(R.id.frameLayout, noticeFragment);
                } else {
                    fragmentTransaction.show(noticeFragment);
                }
                break;
            //发布
            case RELEASE:
                if (releaseFragment == null) {
                    releaseFragment = new ReleaseFragment();
                    fragmentTransaction.add(R.id.frameLayout, releaseFragment);
                } else {
                    fragmentTransaction.show(releaseFragment);
                }
                break;
            //用户中心
            case USER:
                if (userFragment == null) {
                    userFragment = new MyFragment();
                    fragmentTransaction.add(R.id.frameLayout, userFragment);
                } else {
                    fragmentTransaction.show(userFragment);
                }
                break;
        }

        fragmentTransaction.commit();
    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (homeFragment != null) {
            fragmentTransaction.hide(homeFragment);
        }
        if (noticeFragment != null) {
            fragmentTransaction.hide(noticeFragment);
        }
        if (releaseFragment != null) {
            fragmentTransaction.hide(releaseFragment);
        }
        if (userFragment != null) {
            fragmentTransaction.hide(userFragment);
        }
    }


    private void setName(int index) {
        TextView textView;
        ImageView imageView;
        switch (index) {
            case HOME:
                textView = (TextView) linearLayout1.findViewById(R.id.textview_icon);
                imageView = (ImageView) linearLayout1.findViewById(R.id.imageview);
                textView.setText(name[0]);
                textView.setTextColor(getResources().getColor(R.color.blueTopic));
                imageView.setImageDrawable(getResources().getDrawable(image[1]));
                break;
            case NOTICE:
                textView = (TextView) linearLayout2.findViewById(R.id.textview_icon);
                imageView = (ImageView) linearLayout2.findViewById(R.id.imageview);
                textView.setTextColor(getResources().getColor(R.color.blueTopic));
                textView.setText(name[1]);
                imageView.setImageDrawable(getResources().getDrawable(image[3]));
                break;
            case RELEASE:
                textView = (TextView) linearLayout3.findViewById(R.id.textview_icon);
                imageView = (ImageView) linearLayout3.findViewById(R.id.imageview);
                textView.setText(name[2]);
                textView.setTextColor(getResources().getColor(R.color.blueTopic));
                imageView.setImageDrawable(getResources().getDrawable(image[5]));
                break;
            case USER:
                textView = (TextView) linearLayout4.findViewById(R.id.textview_icon);
                imageView = (ImageView) linearLayout4.findViewById(R.id.imageview);
                textView.setText(name[3]);
                textView.setTextColor(getResources().getColor(R.color.blueTopic));
                imageView.setImageDrawable(getResources().getDrawable(image[7]));
                break;
        }
    }


    private void setDefaultBottom() {
        TextView textView = (TextView) linearLayout1.findViewById(R.id.textview_icon);
        ImageView imageView = (ImageView) linearLayout1.findViewById(R.id.imageview);
        textView.setTextColor(getResources().getColor(R.color.grayText));
        textView.setText(name[0]);
        imageView.setImageDrawable(getResources().getDrawable(image[0]));

        textView = (TextView) linearLayout2.findViewById(R.id.textview_icon);
        imageView = (ImageView) linearLayout2.findViewById(R.id.imageview);
        textView.setTextColor(getResources().getColor(R.color.grayText));
        textView.setText(name[1]);
        imageView.setImageDrawable(getResources().getDrawable(image[2]));

        textView = (TextView) linearLayout3.findViewById(R.id.textview_icon);
        imageView = (ImageView) linearLayout3.findViewById(R.id.imageview);
        textView.setText(name[2]);
        textView.setTextColor(getResources().getColor(R.color.grayText));
        imageView.setImageDrawable(getResources().getDrawable(image[4]));

        textView = (TextView) linearLayout4.findViewById(R.id.textview_icon);
        imageView = (ImageView) linearLayout4.findViewById(R.id.imageview);
        textView.setText(name[3]);
        textView.setTextColor(getResources().getColor(R.color.grayText));
        imageView.setImageDrawable(getResources().getDrawable(image[6]));
    }
}
