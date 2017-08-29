package com.linkhand.baixingguanjia.ui.activity.my;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.base.MyApplication;
import com.linkhand.baixingguanjia.entity.DealDetail;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyAccountDetailActivity extends BaseActivity {

    private static final int HTTP_REQUEST = 1;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.listview)
    PullToRefreshListView mListview;
    @Bind(R.id.null_bg)
    RelativeLayout mNullBg;
    List<DealDetail> mList;
    CommonAdapter mAdatper;
    String viewFlag = "";
    private RequestQueue mQueue = NoHttp.newRequestQueue();
    private int pageFlag = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_count_detail);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras != null) {
            viewFlag = extras.getString("viewFlag");
        }
    }

    private void initView() {
        if (viewFlag.equals("gold")) {
            mTitle.setText(R.string.goldDealTitle);
        } else {
            mTitle.setText(R.string.silverDealTitle);
        }
        initRefreshListView(mListview);

    }

    private void initData() {
        mList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mList.add(new DealDetail());
        }
        mAdatper = new MyAdatper(this, R.layout.item_dealdetail, mList);
        mListview.setAdapter(mAdatper);
//        httpGetList();
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
    }


    //网络请求
    public void httpGetList() {
        Request<JSONObject> mRequest = null;
        if (viewFlag.equals("gold")) {
            mRequest = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_GOLD_DETAIL_LIST, RequestMethod.POST);
        } else {
            mRequest = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_SILVER_DETAIL_LIST, RequestMethod.POST);
        }

        mRequest.add("userid", MyApplication.getUser().getUserid());
        mRequest.add("page ", pageFlag);
        Log.d("参数", mRequest.getParamKeyValues().values().toString());
        mQueue.add(HTTP_REQUEST, mRequest, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
                showLoading();
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == HTTP_REQUEST) {// 根据what判断是哪个请求的返回，这样就可以用一个OnResponseListener来接受多个请求的结果。
                    int responseCode = response.getHeaders().getResponseCode();// 服务器响应码。
                    JSONObject jsonObject = response.get();
                    try {
                        if (pageFlag == 1) {
                            mList.clear();
                        }
                        if (jsonObject.getInt("code") == 200) {
                            JSONArray array = jsonObject.getJSONArray("data");
                            if (array == null || array.length() == 0) {

                            }
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jsonObject1 = array.getJSONObject(i);
                                Gson gson = new Gson();
                                DealDetail detail = gson.fromJson(jsonObject1.toString(), DealDetail.class);
                                mList.add(detail);
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
                mAdatper.notifyDataSetChanged();
                pageFlag++;
                if (!adjustList(mList)) {
                    mNullBg.setVisibility(View.VISIBLE);
                } else {
                    mNullBg.setVisibility(View.GONE);
                }
            }
        });


    }


    @OnClick({R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;

        }
    }

    class MyAdatper extends CommonAdapter {
        public MyAdatper(Context context, int layoutId, List datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, Object item, int position) {
            TextView title = holder.getView(R.id.title);
            TextView balance = holder.getView(R.id.balance);
            TextView time = holder.getView(R.id.time);
            TextView num = holder.getView(R.id.num);
            DealDetail detail = mList.get(position);
            title.setText(detail.getPay_name());
            balance.setText("余额" + detail.getBalance() + "两");
            time.setText(detail.getPay_time());
            num.setText(detail.getDetailed());

        }
    }
}
