package com.linkhand.baixingguanjia.ui.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.entity.CarType;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JCY on 2017/7/11.
 * 说明：启动一个服务用来加载数据比如 地区， 搜索类型，分类等等。。。。
 */

public class HttpService extends IntentService {
    private static final int REQUEST = 0;
    private RequestQueue mRequestQueue = NoHttp.newRequestQueue();


    public HttpService() {
        super("httpservice");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        httpGetCarType();
    }

    /**
     * 获取二手车类型接口
     */
    private void httpGetCarType() {
        Log.e("mlist_service", "onSucceed: 执行了么？");
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_CAR_TYPE, RequestMethod.GET);
        mRequestQueue.add(REQUEST, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == REQUEST) {
                    String resultCode = null;
                    Gson gson = new Gson();
                    try {
                        JSONObject jsonObject = response.get();
                        resultCode = jsonObject.getString("code");
                        if (resultCode.equals("200")) {
                            JSONArray array = jsonObject.getJSONArray("data");
                            List<CarType> mlist = new ArrayList<CarType>();
                            for (int i = 0; i < array.length(); i++) {
                                CarType carType = new CarType();
                                carType.setId(array.getJSONObject(i).getString("id"));
                                carType.setName(array.getJSONObject(i).getString("name"));
                                mlist.add(carType);
                                Log.e("mlist_service", "onSucceed: "+ carType.getName());
                            }
                            SPUtils.put(HttpService.this, "CarType", mlist);

                        } else {
//                            showToast("获取接口地区失败");
//                            httpGetCarType();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<JSONObject> response) {
//                httpGetCarType();
            }

            @Override
            public void onFinish(int what) {
            }
        });
    }
}
