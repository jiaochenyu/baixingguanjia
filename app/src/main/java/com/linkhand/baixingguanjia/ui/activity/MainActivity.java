package com.linkhand.baixingguanjia.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.base.BaseAppManager;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.base.MyApplication;
import com.linkhand.baixingguanjia.entity.EventFlag;
import com.linkhand.baixingguanjia.entity.Sheng;
import com.linkhand.baixingguanjia.ui.activity.my.AddCommunityActivity;
import com.linkhand.baixingguanjia.ui.fragment.HomeFragment;
import com.linkhand.baixingguanjia.ui.fragment.MyFragment;
import com.linkhand.baixingguanjia.ui.fragment.NoticeFragment;
import com.linkhand.baixingguanjia.ui.fragment.ReleaseFragment;
import com.linkhand.baixingguanjia.ui.service.HttpService;
import com.linkhand.baixingguanjia.utils.JPushUtils;
import com.linkhand.baixingguanjia.utils.JSONUtils;
import com.linkhand.baixingguanjia.utils.SPUtils;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private final static int REQUEST = 0;
    private final static int REQUEST_MESSAGE = 1;
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
    Sheng mSheng; //获取 用户选择的省市区信息


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

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyListener();

    private boolean isRegister = false; //判断是否是从注册页面跳转过来

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        BaseAppManager.getInstance().clearBackActivities();
        initView();
        initData();
        //获取地区 并且保存到偏好设置里
        httpGetDiqu();
        initHttpService();
//        jPushMethod();
        httpMessageCount();
        perfectAreaInfo(); //完善用户小区信息
        if (isRegister) {
            showGiveSilverDialog();
        }
    }


    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras != null) {
            isRegister = extras.getBoolean("isRegister", false);
        }
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
        mSheng = (Sheng) SPUtils.get(MainActivity.this, "UserLocation", Sheng.class);
        fragmentManager = getSupportFragmentManager();
        showFragment(HOME);
        if (MyApplication.getUser() != null) {
            Set<String> sets = new HashSet<>();
            sets.add(MyApplication.getUser().getXiaoqu());
            JPushUtils.jPushMethod(this, MyApplication.getUser().getUserid(), sets);
        }
    }


    private void initHttpService() {
        Intent intent = new Intent(MainActivity.this, HttpService.class);
        startService(intent);
    }

    //完善用户小区信息
    private void perfectAreaInfo() {
        if (MyApplication.getUser() != null) {//用户已登录
            if (TextUtils.isEmpty(MyApplication.getUser().getQu_id())) {
                showToast(R.string.perfectInfo);
                go(AddCommunityActivity.class);
            }
        }
    }

    // 注册成功 给白银1000
    private void showGiveSilverDialog() {
        final Dialog mDialog = new Dialog(MainActivity.this, R.style.Dialog);
        mDialog.setContentView(R.layout.dialog_promt_give_silver);
        TextView know = (TextView) mDialog.findViewById(R.id.know);
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
        if (mSheng == null) {
            if (MyApplication.getLocation() == null) {
                request.add("sheng", "河北省");
                request.add("shi", "石家庄市");
            } else {
                request.add("sheng", MyApplication.getLocation().getProvince() == null ? "河北省" : MyApplication.getLocation().getProvince());
                request.add("shi", MyApplication.getLocation().getCity() == null ? "石家庄市" : MyApplication.getLocation().getCity());
            }

        } else {
            request.add("sheng", mSheng.getName());
            request.add("shi", mSheng.getShi().getName());
        }
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
                            Sheng sheng = JSONUtils.getLocationData(json);
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

    private void httpMessageCount() {
        if (MyApplication.getUser() == null) {
            return;
        }
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_MYNEWS_COUNT, RequestMethod.POST);
        request.add("userid", MyApplication.getUser().getUserid());
        mQueue.add(REQUEST_MESSAGE, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == REQUEST_MESSAGE) {
                    String resultCode = null;
                    try {
                        JSONObject jsonObject = response.get();
                        resultCode = jsonObject.getString("code");
                        if (resultCode.equals("200")) {
                            int count = jsonObject.getInt("data");
                            EventBus.getDefault().post(new EventFlag("jpushMessageCount", count));
                        } else {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<JSONObject> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    private class MyListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location != null) {
                MyApplication.setLocation(location);
//                    httpGetDiqu();
//                httpGetLcoationAll();
//                EventBus.getDefault().post(new EventFlag(""));
            }

            //

        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mQueue.cancelAll();
//        BaseAppManager.getInstance().clear();
    }
}
