package com.linkhand.baixingguanjia.ui.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.entity.CommonType;
import com.linkhand.baixingguanjia.entity.Sheng;
import com.linkhand.baixingguanjia.entity.Shi;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JCY on 2017/7/11.
 * 说明：启动一个服务用来加载数据比如 地区， 搜索类型，分类等等。。。。
 */

public class HttpService extends IntentService {
    private static final int REQUEST = 0;
    private static final int REQUEST_HOUSE_KEEPING = 3;
    private static final int REQUEST_HOUSE_EDUCATION = 4;
    private static final int REQUEST_WHAT = 2;
    private static final int REQUEST_POSTION_TYPE = 5;
    private RequestQueue mRequestQueue = NoHttp.newRequestQueue();
    Handler mHandler;
    Sheng mSheng;


    public HttpService() {
        super("httpservice");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        mSheng = (Sheng) intent.getSerializableExtra("sheng");
        if (mSheng != null) {

        } else {
            mSheng = new Sheng();
            mSheng.setName("河北省");
            Shi shi = new Shi();
            shi.setName("石家庄市");
            mSheng.setName("河北省");
            mSheng.setShi(shi);
        }
        httpGetDiqu();
        httpGetCarType();
        httpGetHouseKeepingType();
        httpGetIdleGooodsType();
        httpGetEducationType();
        httpGetEducationObject();
        httpGetRecruitType();
        httpGetRecruitPosition();
    }

    /**
     * 获取地区并且 保存到偏好设置里
     */
    private void httpGetDiqu() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_DINGWEI, RequestMethod.POST);
        request.add("sheng", mSheng.getName());
        request.add("shi", mSheng.getShi().getName());
        mRequestQueue.add(REQUEST_WHAT, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == REQUEST_WHAT) {
                    String resultCode = null;
                    try {
                        JSONObject jsonObject = response.get();
                        resultCode = jsonObject.getString("code");
                        if (resultCode.equals("200")) {
                            JSONObject json = jsonObject.getJSONObject("data");
                            Sheng sheng = JSONUtils.getLocationData(json);
                            SPUtils.put(HttpService.this, "DiQu", sheng);

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
                Sheng sheng = (Sheng) SPUtils.get(HttpService.this, "DiQu", new TypeToken<Sheng>() {
                }.getType());
                if (sheng == null) {
                    httpGetDiqu();
                }
            }
        });
    }


    /**
     * 获取二手车类型接口
     */
    private void httpGetCarType() {
//        Log.e("mlist_service", "onSucceed: 执行了么？");
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
                            List<CommonType> mlist = new ArrayList<CommonType>();
                            for (int i = 0; i < array.length(); i++) {
                                CommonType commonType = new CommonType();
                                commonType.setId(array.getJSONObject(i).getString("id"));
                                commonType.setName(array.getJSONObject(i).getString("name"));
                                mlist.add(commonType);
                                Log.e("mlist_service", "onSucceed: " + commonType.getName());
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
                List<CommonType> mTList = (List<CommonType>) SPUtils.get(HttpService.this, "CarType", new TypeToken<List<CommonType>>() {
                }.getType());
                if (mTList == null) {
                    httpGetCarType();
                }
            }
        });
    }


    /**
     * 获取家政类型接口
     */
    private void httpGetHouseKeepingType() {
//        Log.e("mlist_service", "onSucceed: 执行了么？");
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_HOUSEKEEEPING_TYPE, RequestMethod.GET);
        mRequestQueue.add(REQUEST_HOUSE_KEEPING, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == REQUEST_HOUSE_KEEPING) {
                    String resultCode = null;
                    Gson gson = new Gson();
                    try {
                        JSONObject jsonObject = response.get();
                        resultCode = jsonObject.getString("code");
                        if (resultCode.equals("200")) {
                            JSONArray array = jsonObject.getJSONArray("data");
                            List<CommonType> mlist = new ArrayList<CommonType>();
                            for (int i = 0; i < array.length(); i++) {
                                CommonType commonType = new CommonType();
                                commonType.setId(array.getJSONObject(i).getString("id"));
                                commonType.setName(array.getJSONObject(i).getString("name"));
                                mlist.add(commonType);
                                Log.e("mlist_service", "onSucceed: " + commonType.getName());
                            }
                            SPUtils.put(HttpService.this, "HouseKeepingType", mlist);

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
                List<CommonType> mTList = (List<CommonType>) SPUtils.get(HttpService.this, "HouseKeepingType", new TypeToken<List<CommonType>>() {
                }.getType());
                if (mTList == null) {
                    httpGetHouseKeepingType();
                }
            }
        });
    }

    /**
     * 闲置物品分类接口
     */
    private void httpGetIdleGooodsType() {
//        Log.e("mlist_service", "onSucceed: 执行了么？");
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_IDLE_GOODS_TYPE, RequestMethod.GET);
        mRequestQueue.add(REQUEST_HOUSE_KEEPING, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == REQUEST_HOUSE_KEEPING) {
                    String resultCode = null;
                    Gson gson = new Gson();
                    try {
                        JSONObject jsonObject = response.get();
                        resultCode = jsonObject.getString("code");
                        if (resultCode.equals("200")) {
                            JSONArray array = jsonObject.getJSONArray("data");
                            List<CommonType> mlist = new ArrayList<CommonType>();
                            for (int i = 0; i < array.length(); i++) {
                                CommonType commonType = new CommonType();
                                commonType.setId(array.getJSONObject(i).getString("id"));
                                commonType.setName(array.getJSONObject(i).getString("name"));
                                mlist.add(commonType);
                                Log.e("mlist_service", "onSucceed: " + commonType.getName());
                            }
                            SPUtils.put(HttpService.this, "IdleGoodsType", mlist);

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
            }

            @Override
            public void onFinish(int what) {
                List<CommonType> mTList = (List<CommonType>) SPUtils.get(HttpService.this, "IdleGoodsType", new TypeToken<List<CommonType>>() {
                }.getType());
                if (mTList == null) {
                    httpGetIdleGooodsType();
                }
            }
        });
    }

    /**
     * 家教类别接口
     */
    private void httpGetEducationType() {
//        Log.e("mlist_service", "onSucceed: 执行了么？");
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_EDUCATION_SORT_TYPE, RequestMethod.GET);
        mRequestQueue.add(REQUEST_HOUSE_EDUCATION, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == REQUEST_HOUSE_EDUCATION) {
                    String resultCode = null;
                    Gson gson = new Gson();
                    try {
                        JSONObject jsonObject = response.get();
                        resultCode = jsonObject.getString("code");
                        if (resultCode.equals("200")) {
                            JSONArray array = jsonObject.getJSONArray("data");
                            List<CommonType> mlist = new ArrayList<CommonType>();
                            for (int i = 0; i < array.length(); i++) {
                                CommonType commonType = new CommonType();
                                commonType.setId(array.getJSONObject(i).getString("id"));
                                commonType.setName(array.getJSONObject(i).getString("name"));
                                mlist.add(commonType);
                                Log.e("mlist_service", "onSucceed: " + commonType.getName());
                            }
                            SPUtils.put(HttpService.this, "EducationType", mlist);

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
                List<CommonType> mTList = (List<CommonType>) SPUtils.get(HttpService.this, "EducationType", new TypeToken<List<CommonType>>() {
                }.getType());
                if (mTList == null) {
                    httpGetEducationType();
                }
            }
        });
    }

    /**
     * 家教对象 接口
     */
    private void httpGetEducationObject() {
//        Log.e("mlist_service", "onSucceed: 执行了么？");
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_EDUCATION_OBJECT_TYPE, RequestMethod.GET);
        mRequestQueue.add(REQUEST_HOUSE_KEEPING, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == REQUEST_HOUSE_KEEPING) {
                    String resultCode = null;
                    Gson gson = new Gson();
                    try {
                        JSONObject jsonObject = response.get();
                        resultCode = jsonObject.getString("code");
                        if (resultCode.equals("200")) {
                            JSONArray array = jsonObject.getJSONArray("data");
                            List<CommonType> mlist = new ArrayList<CommonType>();
                            for (int i = 0; i < array.length(); i++) {
                                CommonType commonType = new CommonType();
                                commonType.setId(array.getJSONObject(i).getString("id"));
                                commonType.setName(array.getJSONObject(i).getString("name"));
                                mlist.add(commonType);
                                Log.e("mlist_service", "onSucceed: " + commonType.getName());
                            }
                            SPUtils.put(HttpService.this, "EducationObjectType", mlist);

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
                List<CommonType> mTList = (List<CommonType>) SPUtils.get(HttpService.this, "EducationObjectType", new TypeToken<List<CommonType>>() {
                }.getType());
                if (mTList == null) {
                    httpGetEducationObject();
                }
            }
        });
    }

    /**
     * 招聘 行业 接口
     */
    private void httpGetRecruitType() {
//        Log.e("mlist_service", "onSucceed: 执行了么？");
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_RECRUIT_INDUSTRY_TYPE, RequestMethod.GET);
        mRequestQueue.add(REQUEST_HOUSE_KEEPING, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == REQUEST_HOUSE_KEEPING) {
                    String resultCode = null;
                    Gson gson = new Gson();
                    try {
                        JSONObject jsonObject = response.get();
                        resultCode = jsonObject.getString("code");
                        if (resultCode.equals("200")) {
                            JSONArray array = jsonObject.getJSONArray("data");
                            List<CommonType> mlist = new ArrayList<CommonType>();
                            for (int i = 0; i < array.length(); i++) {
                                CommonType commonType = new CommonType();
                                commonType.setId(array.getJSONObject(i).getString("id"));
                                commonType.setName(array.getJSONObject(i).getString("name"));
                                mlist.add(commonType);
                                Log.e("mlist_service", "onSucceed: " + commonType.getName());
                            }
                            SPUtils.put(HttpService.this, "RecruitType", mlist);

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
                List<CommonType> mTList = (List<CommonType>) SPUtils.get(HttpService.this, "RecruitType", new TypeToken<List<CommonType>>() {
                }.getType());
                if (mTList == null) {
                    httpGetRecruitType();
                }


            }
        });
    }

    /**
     * 招聘 职位 接口
     */
    private void httpGetRecruitPosition() {
//        Log.e("mlist_service", "onSucceed: 执行了么？");
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_RECRUIT_POISTION_TYPE, RequestMethod.GET);
        mRequestQueue.add(REQUEST_POSTION_TYPE, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == REQUEST_POSTION_TYPE) {
                    String resultCode = null;
                    Gson gson = new Gson();
                    try {
                        JSONObject jsonObject = response.get();
                        resultCode = jsonObject.getString("code");
                        if (resultCode.equals("200")) {
                            JSONArray array = jsonObject.getJSONArray("data");
                            List<CommonType> mlist = new ArrayList<CommonType>();
                            for (int i = 0; i < array.length(); i++) {
                                CommonType commonType = new CommonType();
                                commonType.setId(array.getJSONObject(i).getString("id"));
                                commonType.setName(array.getJSONObject(i).getString("name"));
                                mlist.add(commonType);
                                Log.e("mlist_service", "onSucceed:RecruitPosition " + commonType.getName());
                            }
                            SPUtils.put(HttpService.this, "RecruitPosition", mlist);

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
            }

            @Override
            public void onFinish(int what) {
                List<CommonType> mTList = (List<CommonType>) SPUtils.get(HttpService.this, "RecruitPosition", new TypeToken<List<CommonType>>() {
                }.getType());
                if (mTList == null) {
                    httpGetRecruitPosition();
                }
            }
        });
    }
}
