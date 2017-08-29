package com.linkhand.baixingguanjia.ui.activity.my;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import com.linkhand.baixingguanjia.entity.FeedBack;
import com.linkhand.baixingguanjia.ui.adapter.my.MyFeedBackAdapter;
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
/*
*
* 我的反馈
 */

public class MyFeedBackActivity extends BaseActivity {
    private static final int HTTP_REQUEST = 0;
    private static final int HTTP_REQUEST2 = 2;
    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.listview)
    PullToRefreshListView mListview;
    List<FeedBack> mList;

    MyFeedBackAdapter mAdapter;
    @Bind(R.id.null_bg)
    RelativeLayout mNullBg;

    private int pageFlag = 1;
    private RequestQueue mQueue = NoHttp.newRequestQueue();
    private Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_feed_back);
        ButterKnife.bind(this);
        initView();
        initData();
        showLoading();
        httpGetList();
        initListener();
    }


    private void initView() {
        mTitle.setText("我的反馈");
        initRefreshListView(mListview);
    }

    private void initData() {
        mList = new ArrayList<>();
        mAdapter = new MyFeedBackAdapter(this, R.layout.item_my_feed_back, mList);
        mListview.setAdapter(mAdapter);
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

        mAdapter.setFeedBackOnClick(new MyFeedBackAdapter.FeedBackOnClick() {
            @Override
            public void onclick(int pos) {
                //再次反馈反馈
                httpFeedbackAgain(mList.get(pos).getId());
            }
        });
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDialog(mList.get(position - 1).getContent());
            }
        });


    }


    //网络请求
    public void httpGetList() {
        Request<JSONObject> mRequest = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_MY_FEEDBACK, RequestMethod.POST);
        mRequest.add("page", pageFlag);
        mRequest.add("userid", MyApplication.getUser().getUserid());

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
                            mList.clear();
                        }
                        if (jsonObject.getInt("code") == 200) {
                            JSONArray array = jsonObject.getJSONArray("data");
                            if (array == null || array.length() == 0) {

                            }
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jsonObject1 = array.getJSONObject(i);
                                Gson gson = new Gson();
                                FeedBack feedBack = gson.fromJson(jsonObject1.toString(), FeedBack.class);
                                mList.add(feedBack);
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

    /**
     * 再次反馈
     */
    public void httpFeedbackAgain(String id) {
        Request<JSONObject> mRequest = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_MY_FEEDBACK_AGAIN, RequestMethod.POST);
        mRequest.add("id", id);
        Log.d("参数", mRequest.getParamKeyValues().values().toString());
        mQueue.add(HTTP_REQUEST2, mRequest, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
                showLoading();
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == HTTP_REQUEST2) {// 根据what判断是哪个请求的返回，这样就可以用一个OnResponseListener来接受多个请求的结果。
                    int responseCode = response.getHeaders().getResponseCode();// 服务器响应码。
                    JSONObject jsonObject = response.get();
                    try {
                        if (pageFlag == 1) {
                            mList.clear();
                        }
                        if (jsonObject.getInt("code") == 200) {
                            showToast(R.string.feedbackSuccess);
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

    private void showDialog(String content) {
        mDialog = new Dialog(MyFeedBackActivity.this, R.style.Dialog);
        mDialog.setContentView(R.layout.dialog_area_feed_back);
        TextView submit = (TextView) mDialog.findViewById(R.id.submit);
        final EditText contentET = (EditText) mDialog.findViewById(R.id.content_edit);
        final EditText phoneET = (EditText) mDialog.findViewById(R.id.phone_edit);
        phoneET.setVisibility(View.GONE);
        submit.setVisibility(View.GONE);
        contentET.setHint("");
        contentET.setText(content);
        contentET.setFocusable(false);
        contentET.setEnabled(false);
        mDialog.show();

    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }


}
