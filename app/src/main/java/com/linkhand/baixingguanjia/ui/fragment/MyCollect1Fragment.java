package com.linkhand.baixingguanjia.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseFragment;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.base.MyApplication;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JCY on 2017/6/27.
 * 说明：  我的收藏
 */

public class MyCollectFragment extends BaseFragment {
    private static final String TAG = "info";
    private final static int REQUEST = 0;

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

    public MyCollectFragment(int type) {
        this.type = type;
        switch (type) {
            case 1:
                Log.d(TAG, "MyCollectFragment: 商品类");
                break;
            case 2:
                Log.d(TAG, "MyCollectFragment: 服务类");
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
        initListener();

        return view;
    }

    private void initView() {

    }

    private void initData() {
        if (type == 1){
            mGoodsList = new ArrayList<>();
            mAdapter = new MyCollectListViewAdapter(getActivity(), R.layout.item_my_collect, mServiceList);
            mListview.setAdapter(mAdapter);
        }else if (type == 2){
            mServiceList = new ArrayList<>();
            mAdapter2 = new MyCollectListView2Adapter(getActivity(), R.layout.item_my_collect, mGoodsList);
            mListview.setAdapter(mAdapter2);
        }


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
                        JSONObject jsonObject = response.get();
                        resultCode = jsonObject.getString("code");
                        if (resultCode.equals("200")) {
                            if (type == 1) {
                                Goods goods = gson.fromJson(jsonObject.getJSONObject("data").toString(),Goods.class);
                                mGoodsList.add()
                            } else if (type == 2) {
                                Collect collect = gson.fromJson(jsonObject.getJSONObject("data").toString(), Collect.class);
                                mServiceList.add(collect);
                            }


                        } else if (resultCode.equals("201")) {
                            Toast.makeText(getActivity(), "账号或密码有误", Toast.LENGTH_SHORT).show();
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

    private void initListener() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}