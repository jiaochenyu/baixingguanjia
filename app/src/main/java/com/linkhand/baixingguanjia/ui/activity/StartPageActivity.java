package com.linkhand.baixingguanjia.ui.activity;

import android.os.Bundle;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.reflect.TypeToken;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.base.MyApplication;
import com.linkhand.baixingguanjia.entity.Sheng;
import com.linkhand.baixingguanjia.utils.JSONUtils;
import com.linkhand.baixingguanjia.utils.SPUtils;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


/**
 * 启动页用来请求数据 缓存数据
 * 一共进行了三次网络请求
 * 1.定位
 * 2.请求 四级联动数据
 * 3.根据省市 获取二级联动数据
 */

public class StartPageActivity extends BaseActivity {
    private final int REQUEST_WHAT1 = 1;
    private final int REQUEST_WHAT2 = 2;
    private final int REQUEST_WHAT3 = 3;
    RequestQueue mQueue = NoHttp.newRequestQueue();
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyListener();
    BDLocation bdLocation;
    Sheng mSheng; //获取 用户选择的省市区信息
    String shengname = "";
    String cityname = "";
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_start_page);
        initData();
        httpAutoOffline();
        initBDLocation();
        //加载首页数据
//        httpGetIndex();
        httpGetLcoationAll();
//        httpGetDiqu();


    }


    private void initData() {
        mSheng = (Sheng) SPUtils.get(StartPageActivity.this, "UserLocation", Sheng.class);
    }


    private void initBDLocation() {
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系

        int span = 1000;
        option.setScanSpan(0);
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

    /**
     * 自动下线
     */

    private void httpAutoOffline() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_AUTO_OFFLINE, RequestMethod.POST);
        mQueue.add(REQUEST_WHAT3, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == REQUEST_WHAT3) {

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

    /**
     * 获取省市区
     */
    private void httpGetLcoationAll() {

        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_DIQU, RequestMethod.POST);

        mQueue.add(REQUEST_WHAT1, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == REQUEST_WHAT1) {
                    String resultCode = null;
                    try {
                        JSONObject jsonObject = response.get();
                        resultCode = jsonObject.getString("code");
                        if (resultCode.equals("200")) {
                            JSONArray json = jsonObject.getJSONArray("data");

                            List<Sheng> shengs = JSONUtils.getLocationDataAll(json);
                            SPUtils.put(StartPageActivity.this, "Location", shengs);
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
                if (MyApplication.getLocation() == null) {

                }
                if (what == REQUEST_WHAT1) {
                    List<Sheng> shengs = (List<Sheng>) SPUtils.get(StartPageActivity.this, "Location", new TypeToken<List<Sheng>>() {
                    }.getType());
                    if (shengs == null) {
                        httpGetLcoationAll();
                    } else {
                        goAndFinish(MainActivity.class);
                    }
                }

            }
        });

    }

    /**
     * 获取地区并且 保存到偏好设置里
     */
    private void httpGetDiqu() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_DINGWEI, RequestMethod.POST);
        if (mSheng == null) {
            request.add("sheng", MyApplication.getLocation().getProvince());
            request.add("shi", MyApplication.getLocation().getCity());
        } else {
            request.add("sheng", mSheng.getName());
            request.add("shi", mSheng.getShi().getName());
        }

        mQueue.add(REQUEST_WHAT2, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == REQUEST_WHAT2) {
                    String resultCode = null;
                    try {
                        JSONObject jsonObject = response.get();
                        resultCode = jsonObject.getString("code");
                        if (resultCode.equals("200")) {
                            JSONObject json = jsonObject.getJSONObject("data");
                            Sheng sheng = JSONUtils.getLocationData(json);
                            SPUtils.put(StartPageActivity.this, "DiQu", sheng);
                        } else {
//                            showToast("获取接口地区失败");
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
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }
}
