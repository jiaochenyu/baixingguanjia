package com.linkhand.baixingguanjia.ui.activity.sort;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.linkhand.baixingguanjia.entity.Areas;
import com.linkhand.baixingguanjia.ui.adapter.AreaListViewAdapter;
import com.linkhand.baixingguanjia.utils.RegexUtils;
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
import butterknife.OnClick;

public class MyAreasActivity extends BaseActivity {

    private static final int HTTP_REQUEST = 1;
    @Bind(R.id.tabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;
    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.more_iv)
    ImageView mMoreIV;
    @Bind(R.id.feed_back)
    TextView mFeedBackTV;
    @Bind(R.id.listview)
    PullToRefreshListView mListview;

    AreaListViewAdapter mAdapter;
    List<Areas> mList;

//    private String[] titles = {"小区资讯", "反馈平台"};
//    private List<Fragment> fragments;
//    MyFragmentPagerAdapter adapter;

    Dialog mDialog;
    @Bind(R.id.null_bg)
    RelativeLayout mNullBg;
    private int pageFlag = 1;
    private RequestQueue mQueue = NoHttp.newRequestQueue();
    String backContent; //反馈内容
    String backContact; //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_areas);
        ButterKnife.bind(this);
        initView();
        initDialog();
        initData();
        httpGetList();
        initListener();
    }

    private void initListener() {
        mListview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageFlag = 1;
                httpGetList();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                httpGetList();
            }
        });
    }


    private void initView() {
        mTitle.setText(R.string.myAreaseActivity);
        initRefreshListView(mListview);
    }

    private void initDialog() {

        mDialog = new Dialog(MyAreasActivity.this, R.style.Dialog);
        mDialog.setContentView(R.layout.dialog_area_feed_back);

        TextView submit = (TextView) mDialog.findViewById(R.id.submit);
        final EditText contentET = (EditText) mDialog.findViewById(R.id.content_edit);
        final EditText phoneET = (EditText) mDialog.findViewById(R.id.phone_edit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //弹出框点击事件
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
//                    contentET.getText().toString();
                    backContent = contentET.getText().toString();
                    backContact = phoneET.getText().toString();
                    if (TextUtils.isEmpty(backContent)) {
                        showToast(R.string.toast_feedback_content);
                        return;
                    } else if (TextUtils.isEmpty(backContent)) {
                        showToast(R.string.toast_right_phone);
                        return;
                    } else if (!RegexUtils.isMobileExact(backContact)) {
                        showToast(R.string.inputrightphone);
                        return;
                    }
                    httpFeedback();
                }
            }
        });
    }

    private void initData() {
        mList = new ArrayList<>();
        mAdapter = new AreaListViewAdapter(this, R.layout.item_area_info, mList);
        mListview.setAdapter(mAdapter);
    }

    //网络请求
    public void httpGetList() {
        Request<JSONObject> mRequest = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_MYNEIGHBORHOOD, RequestMethod.POST);
        mRequest.add("page", pageFlag);
        mRequest.add("userid", MyApplication.getUser().getUserid());

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
                                Areas areas = gson.fromJson(jsonObject1.toString(), Areas.class);
                                mList.add(areas);
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
                mAdapter.notifyDataSetChanged();
                pageFlag++;
                if (!adjustList(mList)) {
                    mNullBg.setVisibility(View.VISIBLE);
                } else {
                    mNullBg.setVisibility(View.GONE);
                }
//                refresh();
            }
        });

    }

    //网络请求
    public void httpFeedback() {
        Request<JSONObject> mRequest = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_FEEDBACK, RequestMethod.POST);
        mRequest.add("userid", MyApplication.getUser().getUserid());
        mRequest.add("xiaoquid", MyApplication.getUser().getXiaoqu());
        mRequest.add("content", backContent);
        mRequest.add("phone", backContact);

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
                            showToast("反馈成功");
                        } else {
                            showToast("反馈失败，请稍后再试");
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
            }
        });

    }

    @OnClick({R.id.back, R.id.more_iv, R.id.feed_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.more_iv:
                break;
            case R.id.feed_back:
                mDialog.show();
                break;
        }
    }


}
