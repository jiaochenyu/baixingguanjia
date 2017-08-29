package com.linkhand.baixingguanjia.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import com.linkhand.baixingguanjia.customView.CommonPromptDialog;
import com.linkhand.baixingguanjia.entity.EventFlag;
import com.linkhand.baixingguanjia.entity.Message;
import com.linkhand.baixingguanjia.ui.activity.detail.MessageDetailActivity;
import com.linkhand.baixingguanjia.ui.adapter.MessageAdapter;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.linkhand.baixingguanjia.R.id.position;

public class MessageActivity extends BaseActivity {


    private static final int HTTP_REQUEST_GET_LIST = 1;
    private static final int HTTP_REQUEST_MESSAGE = 2;
    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.header_layout)
    RelativeLayout mHeaderLayout;
    @Bind(R.id.listview)
    PullToRefreshListView mListview;

    MessageAdapter mAdapter;
    List<Message> mList;
    @Bind(R.id.null_bg)
    RelativeLayout mNullBg;
    private RequestQueue mQueue = NoHttp.newRequestQueue();
    CommonPromptDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        initView();
        initData();
        httpGetList();
        initListener();
    }

    private void initView() {
        mHeaderLayout.setBackgroundColor(getResources().getColor(R.color.blueTopic));
        mTitle.setText("消息");
        mTitle.setTextColor(getResources().getColor(R.color.colorWhite));
        mBack.setImageDrawable(getResources().getDrawable(R.drawable.icon_left_arrwo_white));
        initRefreshListView(mListview);
        mListview.setMode(PullToRefreshBase.Mode.PULL_FROM_START); //只允许下拉刷新
    }

    private void initData() {
        mList = new ArrayList<>();
        mAdapter = new MessageAdapter(this, R.layout.item_message, mList);
        mListview.setAdapter(mAdapter);

    }

    private void initListener() {
        //上拉加载下拉刷新监听
        mListview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //重新加载
                httpGetList();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
            }
        });
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mList.get(position - 1).getState().equals("1")) {//1未读 2已读
                    httpSetChangeMessage(position - 1, "");
                    EventBus.getDefault().post(new EventFlag("updateMessageCount", ""));
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("message", mList.get(position - 1));
                go(MessageDetailActivity.class, bundle);
                mList.get(position - 1).setState("2");
                mAdapter.notifyDataSetChanged();
            }
        });
        mListview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showDeleteDialog(position - 1);
                return true;
            }
        });
    }

    private void showDeleteDialog(final int pos) {
        mDialog = new CommonPromptDialog(this, R.style.Dialog);
        mDialog.setMessage(getStrgRes(R.string.deleteMessage));
        mDialog.setOptionOneClickListener(getStrgRes(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mDialog.dismiss();
            }
        });
        mDialog.setOptionTwoClickListener(getStrgRes(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mDialog.dismiss();
                httpSetChangeMessage(pos, "delete");
                finish();
            }
        });

    }


    //网络请求
    public void httpGetList() {
        Request<JSONObject> mRequest = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_MYNEWS_LIST, RequestMethod.POST);
        mRequest.add("userid", MyApplication.getUser().getUserid());
        Log.d("参数", mRequest.getParamKeyValues().values().toString());
        mQueue.add(HTTP_REQUEST_GET_LIST, mRequest, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
                showLoading();
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == HTTP_REQUEST_GET_LIST) {// 根据what判断是哪个请求的返回，这样就可以用一个OnResponseListener来接受多个请求的结果。
                    int responseCode = response.getHeaders().getResponseCode();// 服务器响应码。
                    JSONObject jsonObject = response.get();
                    try {
                        if (jsonObject.getInt("code") == 200) {
                            JSONArray array = jsonObject.getJSONArray("data");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jsonObject1 = array.getJSONObject(i);
                                Gson gson = new Gson();
                                Message message = gson.fromJson(jsonObject1.toString(), Message.class);
                                mList.add(message);
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
                if (!adjustList(mList)) {
                    mNullBg.setVisibility(View.VISIBLE);
                } else {
                    mNullBg.setVisibility(View.GONE);
                }
//                refresh();
            }
        });


    }

    //改变消息状态
    public void httpSetChangeMessage(final int pos, final String flag) {
        Request<JSONObject> mRequest = null;
        if (flag.equals("delete")) {
            mRequest = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_DELETE_MYNEWS, RequestMethod.POST);
        } else {
            mRequest = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_MYNEWS_CHANGE, RequestMethod.POST);
        }
        mRequest.add("id", mList.get(pos).getId());
        Log.d("参数", mRequest.getParamKeyValues().values().toString());
        mQueue.add(HTTP_REQUEST_MESSAGE, mRequest, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
                showLoading();
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == HTTP_REQUEST_MESSAGE) {
                    JSONObject jsonObject = response.get();
                    try {
                        if (flag.equals("delete")) {
                            if (jsonObject.getString("code").equals("200")) {
                                mList.remove(pos);
                                mAdapter.notifyDataSetChanged();
                                if (mList.get(pos).getState().equals("1")) {//1未读 2已读
                                    EventBus.getDefault().post(new EventFlag("updateMessageCount", ""));
                                }
                            }
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
            }
        });


    }


    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }


}
