package com.linkhand.baixingguanjia.ui.activity.sort;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
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
import com.linkhand.baixingguanjia.entity.Common;
import com.linkhand.baixingguanjia.entity.Sheng;
import com.linkhand.baixingguanjia.ui.activity.detail.CommonDetailActivity;
import com.linkhand.baixingguanjia.utils.ImageUtils;
import com.linkhand.baixingguanjia.utils.SPUtils;
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

public class CommonListActivity extends BaseActivity {

    private static final int HTTP_REQUEST = 1;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.listview)
    PullToRefreshListView mListview;
    @Bind(R.id.null_bg)
    RelativeLayout mNullBg;
    @Bind(R.id.tiaojian_edit)
    EditText mTiaojianEdit;
    @Bind(R.id.right_text)
    TextView mLocation;


    CommonAdapter mAdapter;
    String modelId = "";

    List<Common> mList;
    private RequestQueue mQueue = NoHttp.newRequestQueue();
    private int pageFlag = 1;
    String tiaojian = "";
    private Sheng mSheng;
    private String shengId;
    private String shiId;
    private String quId;
    private String xiaoquId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_list);
        ButterKnife.bind(this);
        initView();
        initData();
        httpGetList();
        initListener();
    }

    private void initView() {
        mTitle.setText(R.string.list);
        mLocation.setVisibility(View.VISIBLE);
    }

    private void initData() {
        mList = new ArrayList<>();
        mAdapter = new MyAdapter(this, R.layout.item_common_info, mList);
        mListview.setAdapter(mAdapter);

        mSheng = (Sheng) SPUtils.get(CommonListActivity.this, "UserLocation", Sheng.class);
        if (mSheng != null) {
            shengId = mSheng.getId();
            shiId = mSheng.getShi().getId();
            mLocation.setText(mSheng.getShi().getName());
            if (mSheng.getQu() != null) {
                quId = mSheng.getQu().getId();
            }
            if (mSheng.getXiaoqu() != null) {
                xiaoquId = mSheng.getXiaoqu().getId();
            }

        } else {
            Sheng sheng = (Sheng) SPUtils.get(CommonListActivity.this, "DiQu", Sheng.class);
            shengId = sheng.getId();
            shiId = sheng.getShiList().get(0).getId();
            mLocation.setText(sheng.getShi().getName());
        }
    }

    private void initListener() {
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("goods_type", mList.get(position - 1).getGoods_type());
                bundle.putString("goodsid", mList.get(position - 1).getCommon_id() + "");
                go(CommonDetailActivity.class, bundle);
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

        mTiaojianEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 当按了搜索之后关闭软键盘
                    ((InputMethodManager) mTiaojianEdit.getContext().getSystemService(
                            Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                            CommonListActivity.this.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    tiaojian = mTiaojianEdit.getText().toString();
                    pageFlag = 1;
                    httpGetList();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras != null) {
            modelId = extras.getString("id");
        }
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }

    //网络请求
    public void httpGetList() {
        Request<JSONObject> mRequest = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_COMMON_LIST, RequestMethod.POST);
        mRequest.add("page", pageFlag);
        mRequest.add("mkid", modelId);
        mRequest.add("tiaojian", tiaojian);
        mRequest.add("sheng", shengId);
        mRequest.add("shi", shiId);
        mRequest.add("qu", "");
        mRequest.add("xiaoqu", "");
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
                                Common common = gson.fromJson(jsonObject1.toString(), Common.class);
                                mList.add(common);
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

    class MyAdapter extends CommonAdapter {

        public MyAdapter(Context context, int layoutId, List datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, Object item, int position) {
            TextView title = holder.getView(R.id.title);
            TextView time = holder.getView(R.id.time);
            TextView location = holder.getView(R.id.location);
            ImageView imageView = holder.getView(R.id.image);
            Common common = mList.get(0);
            title.setText(common.getTitle());
            time.setText(common.getAdd_time());
            location.setText(common.getMessage());
            ImageUtils.setDefaultImage(imageView, ConnectUrl.REQUESTURL_IMAGE, R.drawable.default_pic_show);

        }
    }

}
