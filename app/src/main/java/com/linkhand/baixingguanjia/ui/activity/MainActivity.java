package com.linkhand.baixingguanjia.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.base.BaseAppManager;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.entity.Sheng;
import com.linkhand.baixingguanjia.ui.adapter.ListviewAdapter;
import com.linkhand.baixingguanjia.ui.fragment.HomeFragment;
import com.linkhand.baixingguanjia.ui.fragment.MyFragment;
import com.linkhand.baixingguanjia.ui.fragment.NoticeFragment;
import com.linkhand.baixingguanjia.ui.fragment.ReleaseFragment;
import com.linkhand.baixingguanjia.ui.service.HttpService;
import com.linkhand.baixingguanjia.utils.JSONUtils;
import com.linkhand.baixingguanjia.utils.SPUtils;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private final static int REQUEST = 0;
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

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyListener();
    public Handler mHandler;
    public static String city = "";

    RequestQueue mQueue = NoHttp.newRequestQueue();

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
        BaseAppManager.getInstance().clearBackActivities();
        initView();
        initData();
        initLocation();
        //获取地区 并且保存到偏好设置里
        httpGetDiqu();
        initHttpService();

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

        mLocationClient = new LocationClient(getApplicationContext());

        mLocationClient.registerLocationListener(myListener);

        mHandler = homeFragment.mHandler;
    }


    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系

        int span = 1000;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要

        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps

        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果

        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集

        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    private void initHttpService() {
        Intent intent = new Intent(MainActivity.this, HttpService.class);
        startService(intent);
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
//                homeFragment = new HomeFragment();
//                fragmentTransaction.add(R.id.frameLayout, homeFragment);
                break;
            //预告
            case NOTICE:
                if (noticeFragment == null) {
                    noticeFragment = new NoticeFragment();
                    fragmentTransaction.add(R.id.frameLayout, noticeFragment);
                } else {
                    fragmentTransaction.show(noticeFragment);
                }
//                noticeFragment = new NoticeFragment();
//                fragmentTransaction.add(R.id.frameLayout, noticeFragment);
                break;
            //发布
            case RELEASE:
                if (releaseFragment == null) {
                    releaseFragment = new ReleaseFragment();
                    fragmentTransaction.add(R.id.frameLayout, releaseFragment);
                } else {
                    fragmentTransaction.show(releaseFragment);
                }
//                releaseFragment = new ReleaseFragment();
//                fragmentTransaction.add(R.id.frameLayout, releaseFragment);
                break;
            //用户中心
            case USER:
                if (userFragment == null) {
                    userFragment = new MyFragment();
                    fragmentTransaction.add(R.id.frameLayout, userFragment);
                } else {
                    fragmentTransaction.show(userFragment);
                }
//                userFragment = new MyFragment();
//                fragmentTransaction.add(R.id.frameLayout, userFragment);
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

    private class MyListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            city = location.getCity();
            mHandler.sendMessage(mHandler.obtainMessage(101, city));
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

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


    /**
     * 获取地区并且 保存到偏好设置里
     */
    private void httpGetDiqu() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_DINGWEI, RequestMethod.POST);
        request.add("sheng","河北省");
        request.add("shi","石家庄市");
        mQueue.add(REQUEST, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
                showLoading();
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == REQUEST) {
                    String resultCode = null;
                    try {
                        JSONObject jsonObject = response.get();
                        resultCode = jsonObject.getString("code");
                        if (resultCode.equals("200")) {
                            JSONObject json = jsonObject.getJSONObject("data");
                            Sheng sheng  = JSONUtils.getLocationData(json);
                            SPUtils.put(MainActivity.this, "DiQu", sheng);

                        } else {
                            showToast("获取接口地区失败");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<JSONObject> response) {
                hideLoading();
            }

            @Override
            public void onFinish(int what) {
                hideLoading();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }
}
