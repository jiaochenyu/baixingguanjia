package com.linkhand.baixingguanjia.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseFragment;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.base.MyApplication;
import com.linkhand.baixingguanjia.customView.CommonPromptDialog;
import com.linkhand.baixingguanjia.entity.Collect;
import com.linkhand.baixingguanjia.entity.Goods;
import com.linkhand.baixingguanjia.ui.adapter.my.MyCollectListView2Adapter;
import com.linkhand.baixingguanjia.ui.adapter.my.MyCollectListViewAdapter;
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

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JCY on 2017/6/27.
 * 说明：  我的收藏  服务
 */

public class MyCollect2Fragment extends BaseFragment {
    private static final String TAG = "info";
    private final static int REQUEST = 0;
    private static final int HTTP_REQUEST = 1;

    View view;
    int type;
    int pageFlag = 1;
    @Bind(R.id.listview)
    PullToRefreshListView mListview;
    List<Collect> mServiceList;
    List<Goods> mGoodsList;
    MyCollectListViewAdapter mAdapter; // 商品
    MyCollectListView2Adapter mAdapter2; //服务
    RequestQueue mRequestQueue = NoHttp.newRequestQueue();
    @Bind(R.id.null_bg)
    RelativeLayout mNullBg;
    CommonPromptDialog mCancelDialog;
    int cancelPosition;

    public MyCollect2Fragment(int type) {
        this.type = type;
        switch (type) {
            case 1:
                Log.d(TAG, "MyCollect1Fragment: 商品类");
                break;
            case 2:
                Log.d(TAG, "MyCollect1Fragment: 服务类");
                break;


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_release, container, false);
        ButterKnife.bind(this, view);
        initView();
        initData();
        showLoading();
        httpGetList();
        initListener();

        return view;
    }

    private void initView() {

        initRefreshListView(mListview);
        initCancelDialog();
    }

    private void initData() {
        if (type == 1) {
            mGoodsList = new ArrayList<>();
            mAdapter = new MyCollectListViewAdapter(getActivity(), R.layout.item_my_collect, mGoodsList);
            mListview.setAdapter(mAdapter);
        } else if (type == 2) {
            mServiceList = new ArrayList<>();
            mAdapter2 = new MyCollectListView2Adapter(getActivity(), R.layout.item_my_collect2, mServiceList);
            mListview.setAdapter(mAdapter2);
        }


    }

    private void initCancelDialog() {
        mCancelDialog = new CommonPromptDialog(getActivity(), R.style.goods_info_dialog);
        mCancelDialog.setMessage(getStrgRes(R.string.cancelCollect));
        mCancelDialog.setOptionOneClickListener(getStrgRes(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mCancelDialog.dismiss();
            }
        });
        mCancelDialog.setOptionTwoClickListener(getStrgRes(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mCancelDialog.dismiss();
                mServiceList.remove(cancelPosition);
                mAdapter2.notifyDataSetChanged();
                if (!adjustList(mServiceList)) {
                    mNullBg.setVisibility(View.VISIBLE);
                } else {
                    mNullBg.setVisibility(View.GONE);
                }
                httpCancelCollect();
            }
        });
    }

    private void httpGetList() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_MY_COLLECT_LIST, RequestMethod.POST);
        Gson gson = new Gson();
        request.add("userid", MyApplication.getUser().getUserid());
        request.add("type", type);
        request.add("page", pageFlag);
        mRequestQueue.add(REQUEST, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
                showLoading();
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == REQUEST) {
                    String resultCode = null;
                    Gson gson = new Gson();
                    try {
                        if (pageFlag == 1) {
                            mServiceList.clear();
                        }
                        JSONObject jsonObject = response.get();
                        resultCode = jsonObject.getString("code");
                        if (resultCode.equals("200")) {
                            JSONArray array = jsonObject.getJSONArray("data");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jsonObject1 = array.getJSONObject(i);
                                Collect collect = gson.fromJson(jsonObject1.toString(), Collect.class);
                                mServiceList.add(collect);
                            }

                        } else if (resultCode.equals("201")) {
//                            Toast.makeText(getActivity(), "账号或密码有误", Toast.LENGTH_SHORT).show();
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
                mListview.onRefreshComplete();
                mAdapter2.notifyDataSetChanged();
                pageFlag++;
                if (!adjustList(mServiceList)) {
                    mNullBg.setVisibility(View.VISIBLE);
                } else {
                    mNullBg.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initListener() {
        //上拉加载下拉刷新监听
        mListview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //重新加载
                pageFlag = 1;
                httpGetList();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                httpGetList();
            }
        });

        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Collect collect = mServiceList.get(position - 1);
                if (collect.getDelete().equals("1") || collect.getDelete().equals("2")) {
                    showToast(R.string.collectXiaxian);
                    return;
                }
                //跳转界面
                Bundle bundle = new Bundle();
                bundle.putString("flag", "my"); // 需要调用接口获取数据
                bundle.putString("type", collect.getGoods_type());
                bundle.putString("id", collect.getId());

            }
        });

        mAdapter2.setCancelClick(new MyCollectListView2Adapter.CancelClick() {
            @Override
            public void cancelClick(int position) {
                showToast("点击了取消" + position);
//                httpCancelCollect(position);
                cancelPosition = position;
                mCancelDialog.show();
            }
        });
    }

    private void httpCancelCollect() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_COLLECT, RequestMethod.POST);
        request.add("goodsid", mServiceList.get(cancelPosition).getId());
        request.add("userid", MyApplication.getUser().getUserid());
        request.add("goodstype", mServiceList.get(cancelPosition).getGoods_type());
        request.add("boolean", "f");

        Log.d("收藏", request.getParamKeyValues().values().toString());

        mRequestQueue.add(HTTP_REQUEST, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {

            }

            //  code：206已收藏 207未收藏
            //  code1：200收藏成功 204收藏失败 205收藏数量达到上限 208取消收藏成功 209取消收藏失败
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == HTTP_REQUEST) {// 根据what判断是哪个请求的返回，这样就可以用一个OnResponseListener来接受多个请求的结果。
                    int responseCode = response.getHeaders().getResponseCode();// 服务器响应码。
                    JSONObject jsonObject = response.get();
                    try {
                        if (jsonObject.getString("code").equals("206")) {
                            //已收藏


                            if (jsonObject.getString("code1").equals("208")) {

                            }
                        } else if (jsonObject.getString("code").equals("207")) {
                            //未收藏


                            if (jsonObject.getString("code1").equals("200")) {

                            }
                        }


//
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
