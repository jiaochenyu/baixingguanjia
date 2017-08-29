package com.linkhand.baixingguanjia.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseFragment;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.base.MyApplication;
import com.linkhand.baixingguanjia.entity.EventFlag;
import com.linkhand.baixingguanjia.entity.Notice;
import com.linkhand.baixingguanjia.entity.Sheng;
import com.linkhand.baixingguanjia.ui.activity.HomeAreaSearchActivity;
import com.linkhand.baixingguanjia.ui.activity.LoginActivity;
import com.linkhand.baixingguanjia.ui.activity.MessageActivity;
import com.linkhand.baixingguanjia.ui.activity.detail.NoticeGoodsDetailActivity;
import com.linkhand.baixingguanjia.ui.adapter.NoticeListViewAdapter;
import com.linkhand.baixingguanjia.utils.SPUtils;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JCY on 2017/6/14.
 * 说明：
 */

public class NoticeFragment extends BaseFragment {
    private static final int REQUEST_MESSAGE = 1;
    private static final int REQUEST_GET_LIST = 2;
    @Bind(R.id.location)
    TextView mLocationTV;
    @Bind(R.id.imageView)
    ImageView mLocationIV;
    @Bind(R.id.location_layout)
    LinearLayout mLocationLayout;
    @Bind(R.id.message)
    RelativeLayout mMessageLayout;
    @Bind(R.id.listview)
    PullToRefreshListView mListview;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.message_num)
    TextView mMessageNumTV;
    @Bind(R.id.null_bg)
    RelativeLayout mNullBg;
    private View mView;
    private List<Notice> mList;
    private NoticeListViewAdapter mAdapter;
    private Sheng userSheng; //用户存储的省市区信息
    private RequestQueue mQueue = NoHttp.newRequestQueue();
    int pageFlag = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_notice, null);
        ButterKnife.bind(this, mView);
        initData();
        initView();
        initListener();
        httpMessageCount();
        return mView;
    }

    private void initData() {
        userSheng = (Sheng) SPUtils.get(getActivity(), "UserLocation", Sheng.class);
        mList = new ArrayList<>();
        mAdapter = new NoticeListViewAdapter(getActivity(), R.layout.item_notice_recyler, mList);
        mListview.setAdapter(mAdapter);
        httpGetList();
    }

    private void initView() {
        mTitle.setText("预告");
        if (userSheng == null && MyApplication.getLocation() != null) {
            Log.d("tag", "initView: " + MyApplication.getLocation().getCity());
            mLocationTV.setText(MyApplication.getLocation().getCity());
        } else if (userSheng != null) {
            mLocationTV.setText(userSheng.getShi().getName());
            if (userSheng.getQu() != null) {
                mLocationTV.setText(userSheng.getQu().getName());
            }
            if (userSheng.getXiaoqu() != null) {
                mLocationTV.setText(userSheng.getXiaoqu().getName());
            }
        } else {
            mLocationTV.setText("石家庄市");
        }
        initRefreshListView(mListview);
        mListview.setMode(PullToRefreshBase.Mode.BOTH);
//        mListview.setMode(PullToRefreshBase.Mode.PULL_FROM_START); //只允许下拉刷新
    }


    private void initListener() {
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
                Bundle bundle = new Bundle();
                bundle.putString("goodsId", mList.get(position - 1).getGoods_id());
                go(NoticeGoodsDetailActivity.class, bundle);
            }
        });

    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Subscribe
    public void onEvent(EventFlag eventFlag) {
        if (eventFlag.getFlag().equals("updateLocation")) {
            userSheng = (Sheng) SPUtils.get(getActivity(), "UserLocation", Sheng.class);
            if (userSheng == null && MyApplication.getLocation() != null) {
                mLocationTV.setText(MyApplication.getLocation().getCity());
            } else if (userSheng != null) {
                mLocationTV.setText(userSheng.getShi().getName());
                if (userSheng.getQu() != null) {
                    mLocationTV.setText(userSheng.getQu().getName());
                }
                if (userSheng.getXiaoqu() != null) {
                    mLocationTV.setText(userSheng.getXiaoqu().getName());
                }
            } else if (userSheng == null && MyApplication.getLocation() == null) {
                mLocationTV.setText("石家庄市");
            }
        }
        if (eventFlag.getFlag().equals("jpushMessageCount")) {
            int count = eventFlag.getPosition();
            if (count <= 0) {
                mMessageNumTV.setVisibility(View.GONE);
            } else if (count > 0 && count <= 99) {
                mMessageNumTV.setVisibility(View.VISIBLE);
                mMessageNumTV.setText(count + "");
            } else {
                mMessageNumTV.setVisibility(View.VISIBLE);
                mMessageNumTV.setText("99+");
            }
        }
        if (eventFlag.getFlag().equals("updateMessageCount")) {
            int count = Integer.parseInt(mMessageNumTV.getText().toString());
            count--;
            if (count <= 0) {
                mMessageNumTV.setVisibility(View.GONE);
            } else if (count > 0 && count <= 99) {
                mMessageNumTV.setVisibility(View.VISIBLE);
                mMessageNumTV.setText(count + "");
            } else {
                mMessageNumTV.setVisibility(View.VISIBLE);
                mMessageNumTV.setText("99+");
            }
        }

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

    private void httpGetList() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.PRODUCT_YUGAO, RequestMethod.POST);
        request.add("page", pageFlag);
        request.add("num", 8);
        mQueue.add(REQUEST_GET_LIST, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
                showLoading();
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == REQUEST_GET_LIST) {
                    try {
                        if (pageFlag == 1) {
                            mList.clear();
                        }
                        Gson gson = new Gson();
                        String code = response.get().getString("code");
                        if (code.equals("200")) {
                            JSONArray jsonArray = response.get().getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                Notice notice = gson.fromJson(obj.toString(), Notice.class);
                                mList.add(notice);
                            }
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
                hideLoading();
                pageFlag++;
                mListview.onRefreshComplete();
                mAdapter.notifyDataSetChanged();
                if (!adjustList(mList)) {
                    mNullBg.setVisibility(View.VISIBLE);
                } else {
                    mNullBg.setVisibility(View.GONE);
                }
            }
        });
    }

    @OnClick({R.id.location_layout, R.id.message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.location_layout:
                Bundle bundle = new Bundle();
                bundle.putString("selectLocation", mLocationTV.getText().toString());
                go(HomeAreaSearchActivity.class, bundle);
                break;
            case R.id.message:
                if (MyApplication.getUser() != null) {
                    go(MessageActivity.class);
                } else {
                    go(LoginActivity.class);
                }
                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void onHiddenChanged(boolean hidden) {
// TODO Auto-generated method stub
        super.onHiddenChanged(hidden);
        if (hidden) {// 不在最前端界面显示

        } else {// 重新显示到最前端中
            setStatusBarColor(R.color.colorSystemBlue);
        }
    }
}
