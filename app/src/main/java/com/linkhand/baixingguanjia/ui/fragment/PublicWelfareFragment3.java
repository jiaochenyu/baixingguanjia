package com.linkhand.baixingguanjia.ui.fragment;

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
import com.linkhand.baixingguanjia.base.BaseLazyFragment;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.entity.PublicWelfare;
import com.linkhand.baixingguanjia.ui.activity.detail.PublicWelfareDetailActivity;
import com.linkhand.baixingguanjia.ui.adapter.PublicWelfareAdapter;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PublicWelfareFragment2 extends BaseLazyFragment {
    private static final int HTTP_REQUEST = 0;
    private boolean isPrepared;
    RequestQueue mQueue = NoHttp.newRequestQueue();
    @Bind(R.id.null_bg)
    RelativeLayout mNullBg;
    private int pageFlag = 1;

    private static final String TAG = "info";
    View view;
    @Bind(R.id.listview)
    PullToRefreshListView mListview;
    private int type;
    private PublicWelfareAdapter adapter;
    private List<PublicWelfare> dataList;
    private String tiaojian;

    public PublicWelfareFragment2(int type) {
        this.type = type;
        switch (type) {
            case 1:
                Log.d(TAG, "PublicWelfareFragment: 寻人");
                break;
            case 2:
                Log.d(TAG, "PublicWelfareFragment: 寻物");
                break;
            case 3:
                Log.d(TAG, "PublicWelfareFragment: 善行");
                break;

        }
    }

    public void setTiaojian(String tiaojian) {
        this.tiaojian = tiaojian;
        showLoading();
        pageFlag = 1;
        httpGetList();
    }

    /**
     * 懒加载
     */
    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        initView();
        initData();
        showLoading();
        httpGetList();
        initListener();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_public_welfare, container, false);
        ButterKnife.bind(this, view);
        isPrepared = true;
        lazyLoad();
        return view;
    }

    private void initView() {
//        mListview = (PullToRefreshListView) view.findViewById(R.id.listview);
        initRefreshListView(mListview);
    }

    private void initData() {
        dataList = new ArrayList<>();
        adapter = new PublicWelfareAdapter(getActivity(), R.layout.item_public_welfare_info, dataList);
        mListview.setAdapter(adapter);
    }


    private void initListener() {
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("welfare", (Serializable) dataList.get(position - 1));
                go(PublicWelfareDetailActivity.class, bundle);
            }
        });
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
    }


    //网络请求
    public void httpGetList() {
        Request<JSONObject> mRequest = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_WELFARE_LIST, RequestMethod.POST);
        mRequest.add("page", pageFlag);
        mRequest.add("tiaojian", tiaojian);
        mRequest.add("category", type);

        Log.d("参数", mRequest.getParamKeyValues().values().toString());
        mQueue.add(HTTP_REQUEST, mRequest, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == HTTP_REQUEST) {// 根据what判断是哪个请求的返回，这样就可以用一个OnResponseListener来接受多个请求的结果。
                    int responseCode = response.getHeaders().getResponseCode();// 服务器响应码。
                    JSONObject jsonObject = response.get();
                    try {
                        if (pageFlag == 1) {
                            dataList.clear();
                        }
                        if (jsonObject.getInt("code") == 200) {
                            JSONArray array = jsonObject.getJSONArray("data");
                            if (array == null || array.length() == 0) {

                            }
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jsonObject1 = array.getJSONObject(i);
                                Gson gson = new Gson();
                                PublicWelfare publicWelfare = gson.fromJson(jsonObject1.toString(), PublicWelfare.class);
                                dataList.add(publicWelfare);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.e("result1", jsonObject.toString());
                }
            }

            @Override
            public void onFailed(int what, Response<JSONObject> response) {

            }

            @Override
            public void onFinish(int what) {
                hideLoading();
                mListview.onRefreshComplete();
                adapter.notifyDataSetChanged();
                if (isHidden()) {

                } else {
                    pageFlag++;
                }

                if (!adjustList(dataList)) {
                    mNullBg.setVisibility(View.VISIBLE);
                } else {
                    mNullBg.setVisibility(View.GONE);
                }
//                refresh();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
