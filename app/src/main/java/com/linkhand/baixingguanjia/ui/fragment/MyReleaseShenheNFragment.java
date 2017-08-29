package com.linkhand.baixingguanjia.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseFragment;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.base.MyApplication;
import com.linkhand.baixingguanjia.entity.EventFlag;
import com.linkhand.baixingguanjia.entity.Release;
import com.linkhand.baixingguanjia.ui.adapter.my.MyReleaseListViewAdapter;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JCY on 2017/6/27.
 * 说明：  我的发布 待审核
 */

public class MyReleaseShenheNFragment extends BaseFragment {
    private static final String TAG = "MyReleaseFragment_info";
    private final static int REQUEST = 0;
    View view;
    int type;
    //    private int pageFlag = 1;
    @Bind(R.id.listview)
    PullToRefreshListView mListview;
    @Bind(R.id.null_bg)
    RelativeLayout mNullBg;

    List<Release> mList;
    MyReleaseListViewAdapter mAdapter;
    RequestQueue mRequestQueue = NoHttp.newRequestQueue();

    public MyReleaseShenheNFragment(int type) {
        this.type = type;
        switch (type) {
            case 1:
                Log.d(TAG, "MyReleaseNowFragment: 发布中");
                break;
            case 2:
                Log.d(TAG, "MyReleaseNowFragment: 已下线");
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
        mListview.setMode(PullToRefreshBase.Mode.PULL_FROM_START); //只允许下拉刷新
//        initCancelDialog();
    }

    private void initData() {
        mList = new ArrayList<>();
        mAdapter = new MyReleaseListViewAdapter(getActivity(), R.layout.item_my_release, mList, 1);
        mListview.setAdapter(mAdapter);
    }

    private void initListener() {
        //上拉加载下拉刷新监听
//        mListview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
//            @Override
//            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//                //重新加载
//                pageFlag = 1;
//                httpGetList();
//            }
//
//            @Override
//            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//                httpGetList();
//            }
//        });

        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Release release = mList.get(position - 1);
                //跳转界面
                Bundle bundle = new Bundle();
                bundle.putString("flag", "my"); // 需要调用接口获取数据
                bundle.putString("type", release.getGoods_type());
                bundle.putString("id", release.getId());
            }
        });


    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Subscribe
    public void onEvent(EventFlag eventFlag) {
        if (eventFlag.getFlag().equals("fabu")) {
//            pageFlag = 1;
            httpGetList();
        }
        if (eventFlag.getFlag().contains("update")){
            httpGetList();
        }

    }

    private void httpGetList() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_MY_RELEASE_LIST, RequestMethod.POST);
        Gson gson = new Gson();
        request.add("userid", MyApplication.getUser().getUserid());
        request.add("delete", 0);
        request.add("examine", 0);
//        request.add("page", pageFlag);
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
//                        if (pageFlag == 1) {
//                            mList.clear();
//                        }
                        JSONObject jsonObject = response.get();
                        resultCode = jsonObject.getString("code");
                        if (resultCode.equals("200")) {
                            JSONArray array = jsonObject.getJSONArray("data");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jsonObject1 = array.getJSONObject(i);
                                Release release = gson.fromJson(jsonObject1.toString(), Release.class);
                                mList.add(release);
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
                mAdapter.notifyDataSetChanged();
//                pageFlag++;
                if (!adjustList(mList)) {
                    mNullBg.setVisibility(View.VISIBLE);
                } else {
                    mNullBg.setVisibility(View.GONE);
                }
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
