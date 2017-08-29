package com.linkhand.baixingguanjia.ui.activity.sort;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.customView.ExpandTabView;
import com.linkhand.baixingguanjia.customView.PopViewAreaSidebar;
import com.linkhand.baixingguanjia.customView.PopViewList;
import com.linkhand.baixingguanjia.customView.PopViewListAndEdit;
import com.linkhand.baixingguanjia.entity.EventFlag;
import com.linkhand.baixingguanjia.entity.HouseProperty;
import com.linkhand.baixingguanjia.entity.Sheng;
import com.linkhand.baixingguanjia.ui.activity.detail.HousePropertyDetailActivity;
import com.linkhand.baixingguanjia.ui.adapter.HousepropertyListViewAdapter;
import com.linkhand.baixingguanjia.utils.SPUtils;
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
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class HousePropertyActivity extends BaseActivity {

    private static final int HTTP_REQUEST = 0;
    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.search_text_layout)
    LinearLayout mSearchLayout;  //隐藏显示
    @Bind(R.id.listview)
    PullToRefreshListView mListview;
    @Bind(R.id.radiogroup)
    RadioGroup mRadioGroup;
    @Bind(R.id.select_layout)
    LinearLayout mSelectLayout;
    @Bind(R.id.search_layout)
    LinearLayout mSearch;  // 搜索点击
    @Bind(R.id.tiaojian)
    EditText mTiaojianET;
    @Bind(R.id.sell)
    RadioButton mSell;
    @Bind(R.id.rent)
    RadioButton mRent;
    @Bind(R.id.expandtabTab)
    ExpandTabView mExpandtabTab;
    @Bind(R.id.null_bg)
    RelativeLayout mNullBg;
    @Bind(R.id.v_bg)
    View mVBg;

    boolean viewFlag = false; //搜索输入框显示和隐藏
    @Bind(R.id.cha)
    ImageView mCha;
    private PopViewAreaSidebar viewArea;
    private PopViewList viewType, viewStore;
    private PopViewListAndEdit viewSales;
    List<HouseProperty> mList;
    HousepropertyListViewAdapter mAdapter;

    private ArrayList<View> mViewArray = new ArrayList<View>();

    private List<String> salesItems = Arrays.asList(new String[]{"价格不限", "50万以下", "50-100万", "100-150万", "150-200万", "200-300万", "300万以上"});
    private List<String> salesItemsVaule = Arrays.asList(new String[]{"n-n", "0-50", "50-100", "100-150", "150-200", "200-300", "300-n"});
    private List<String> salesItemsRent = Arrays.asList(new String[]{"价格不限", "1000 元/月以下", "1000-1500 元/月", "1500-2000 元/月", "2000-3000 元/月", "3000 元/月以上"});
    private List<String> salesItemsRentVaule = Arrays.asList(new String[]{"n-n", "0-100", "1000-1500", "1500-2000", "2000-3000", "3000-n"});
    private List<String> typeItems = Arrays.asList(new String[]{"价格不限", "1室", "2室", "3室", "4室"});
    private List<String> typeItemsVaule = Arrays.asList(new String[]{"n", "1", "2", "3", "4"});
    private List<String> storeItems = Arrays.asList(new String[]{"发布人不限", "个人", "商家"});
    private List<String> storeItemsVaule = Arrays.asList(new String[]{"n", "5", "4"});
    ArrayList<String> mTextArray;

    RequestQueue mQueue = NoHttp.newRequestQueue();
    private int pageFlag = 1;

    /**
     * 搜索条件
     **/
    String tiaojian;
    String shengId;
    String shiId;
    String quId;
    String xiaoquId;
    String woshi; //房子类型
    String categoryId; //发布人
    String minPublisher; //百万以上筛选：（minPublisher：min； maxPublisher：100)
    String maxPublisher; //百万以上筛选：（minPublisher：min； maxPublisher：100)
    String houseType = "2"; //租房 还是 买房1出租2出售【默认2】

    Sheng mSheng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_property);
        ButterKnife.bind(this);
        initView();
        initData();
        httpGetList();
        initListener();
    }


    private void initView() {
//        mTiaojianET.requestFocus();
        viewArea = new PopViewAreaSidebar(this);
        viewType = new PopViewList(this, typeItems, typeItemsVaule, 2);
        viewSales = new PopViewListAndEdit(this, salesItems, salesItemsVaule, 3);
        viewStore = new PopViewList(this, storeItems, storeItemsVaule, 4);
    }

    private void initData() {
        mList = new ArrayList<>();
        mAdapter = new HousepropertyListViewAdapter(HousePropertyActivity.this, R.layout.item_house_property_info, mList);
        mListview.setAdapter(mAdapter);
        /**
         * 初始化条件选择view的数据
         */
        mViewArray.add(viewArea);
        mViewArray.add(viewType);
        mViewArray.add(viewSales);
        mViewArray.add(viewStore);
        mTextArray = new ArrayList<String>();
        mTextArray.add(getStrgRes(R.string.allCity));
        mTextArray.add(getStrgRes(R.string.sortType));
        mTextArray.add(getStrgRes(R.string.sortScale));
        mTextArray.add(getStrgRes(R.string.sortStore));


        mSheng = (Sheng) SPUtils.get(HousePropertyActivity.this, "UserLocation", Sheng.class);
        if (mSheng != null) {
            shengId = mSheng.getId();
            shiId = mSheng.getShi().getId();
//            mTextArray.set(0,mSheng.getShi().getName());
            if (mSheng.getQu() != null) {
                quId = mSheng.getQu().getId();
                mTextArray.set(0, mSheng.getQu().getName());
            }

            if (mSheng.getXiaoqu() != null) {
                xiaoquId = mSheng.getXiaoqu().getId();
                mTextArray.set(0, mSheng.getXiaoqu().getName());
            }

        } else {
            Sheng sheng = (Sheng) SPUtils.get(HousePropertyActivity.this, "DiQu", Sheng.class);
            shengId = sheng.getId();
            shiId = sheng.getShiList().get(0).getId();
        }

        mExpandtabTab.setValue(mTextArray, mViewArray, mVBg);
        initRefreshListView(mListview);
    }

    private void initListener() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                mVBg.setVisibility(View.GONE);
                switch (checkedId) {
                    case R.id.sell:
                        houseType = "2";
                        viewSales.setData(salesItems, salesItemsVaule, 2);
                        onRefresh(viewSales, "价格");
                        maxPublisher = "";
                        minPublisher = "";
                        pageFlag = 1;
                        httpGetList();
                        break;
                    case R.id.rent:
                        houseType = "1";
                        viewSales.setData(salesItemsRent, salesItemsRentVaule, 1);
                        onRefresh(viewSales, "价格");
                        pageFlag = 1;
                        maxPublisher = "";
                        minPublisher = "";
                        httpGetList();
                        break;
                }
            }
        });
        // 搜索输入框事件监听
        mTiaojianET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(mTiaojianET.getText())) {
                    mCha.setVisibility(View.VISIBLE);
                } else {
                    mCha.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //* 选择小区
        viewArea.setOnSelectListener(new PopViewAreaSidebar.OnSelectListener() {

            @Override
            public void getValue(Sheng s, int quPos, int xiaoquPos, String showText) {
                shengId = s.getId();
                Log.d("参数", "getValue:  quPos" + quPos + " xiaoquPos" + xiaoquPos);
                shiId = s.getShiList().get(0).getId();
                if (quPos == -1) {
                    //quPos ==1 代表选择了全城
                    xiaoquId = "n";
                    quId = "n";
                } else {
                    //xiaoquPos ==1 代表选择了全区
                    quId = s.getShiList().get(0).getQuList().get(quPos).getId();
                    xiaoquId = (xiaoquPos == -1) ? "n" : s.getShiList().get(0).getQuList().get(quPos).getXiaoquList().get(xiaoquPos).getId();
                }
                mVBg.setVisibility(View.GONE);
                onRefresh(viewArea, showText);
                pageFlag = 1;
                showLoading();
                httpGetList();
            }


        });
        //* 选择类型
        viewType.setOnSelectListener(new PopViewList.OnSelectListener() {

            @Override
            public void getValue(String value, String showText) {
                woshi = value;
                mVBg.setVisibility(View.GONE);
                onRefresh(viewType, showText);
                pageFlag = 1;
                showLoading();
                httpGetList();

            }
        });
        //* 选择价钱
        viewSales.setOnSelectListener(new PopViewListAndEdit.OnSelectListener() {

            @Override
            public void getValue(String value, String showText) {
//                int price = Integer.parseInt(value);
                saleType(value);
                mVBg.setVisibility(View.GONE);
                onRefresh(viewSales, showText);
                pageFlag = 1;
                showLoading();
                httpGetList();
            }

            @Override
            public void getEditeValue(String high, String low) {
                maxPublisher = high;
                minPublisher = low;
                mVBg.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(high) && !TextUtils.isEmpty(low)) {
                    onRefresh(viewSales, high + "-" + low);
                } else if (TextUtils.isEmpty(high) && TextUtils.isEmpty(low)) {
                    onRefresh(viewSales, "价格");
                } else
                    onRefresh(viewSales, high + low);
                pageFlag = 1;
                showLoading();
                httpGetList();
            }
        });
        //选择商家
        viewStore.setOnSelectListener(new PopViewList.OnSelectListener() {

            @Override
            public void getValue(String value, String showText) {
                mVBg.setVisibility(View.GONE);
                onRefresh(viewStore, showText);
                categoryId = value;
                pageFlag = 1;
                showLoading();
                httpGetList();

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

        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("house", mList.get(position - 1));
                bundle.putInt("position", position - 1);
                go(HousePropertyDetailActivity.class, bundle);
            }
        });

    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }
    @Subscribe
    public void onEvent(EventFlag eventFlag) {
        if (eventFlag.getFlag().equals("offlineHouse")) {
            int position = (Integer) eventFlag.getObject();
            mList.remove(position);
            mAdapter.notifyDataSetChanged();
        }

    }
    /**
     * 更新自定义ToggleButton上的文字
     *
     * @param view
     * @param showText
     */
    private void onRefresh(View view, String showText) {

        mExpandtabTab.onPressBack();
        int position = getPositon(view);
        if (position >= 0 && !mExpandtabTab.getTitle(position).equals(showText)) {
            mExpandtabTab.setTitle(showText, position);
        }

    }

    private int getPositon(View tView) {
        for (int i = 0; i < mViewArray.size(); i++) {
            if (mViewArray.get(i) == tView) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 价钱筛选
     */
    private void saleType(String price) {
        minPublisher = price.split("-")[0];
        maxPublisher = price.split("-")[1];
    }


    //网络请求
    public void httpGetList() {
        Request<JSONObject> mRequest = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_HOUSE_LIST, RequestMethod.POST);
        mRequest.add("page", pageFlag);
        mRequest.add("tiaojian", tiaojian);
        mRequest.add("sheng", shengId);
        mRequest.add("chengshi", shiId);
        mRequest.add("qu", quId);
        mRequest.add("xiaoqu", xiaoquId);
        mRequest.add("shi", woshi);
        mRequest.add("category", categoryId);
        mRequest.add("minPublisher", minPublisher);
        mRequest.add("maxPublisher", maxPublisher);
        mRequest.add("host_type", houseType);
        Log.d("参数", mRequest.getParamKeyValues().values().toString());
//        mRequest.setConnectTimeout(300*1000);
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
                                HouseProperty house = gson.fromJson(jsonObject1.toString(), HouseProperty.class);
                                mList.add(house);
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


    @OnClick({R.id.back, R.id.search_layout, R.id.cha})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.cha:
                mTiaojianET.setText("");
                break;
            case R.id.search_layout:
                mVBg.setVisibility(View.GONE);
                viewFlag = !viewFlag;
                if (TextUtils.isEmpty(mTiaojianET.getText().toString())) {
                    if (viewFlag) {
                        mSearchLayout.setVisibility(View.VISIBLE);
                        mSelectLayout.setVisibility(View.GONE);
                    } else {
                        mSearchLayout.setVisibility(View.GONE);
                        mSelectLayout.setVisibility(View.VISIBLE);
                        tiaojian = "";
                        pageFlag = 1;
                        httpGetList();
                    }

                } else {
                    tiaojian = mTiaojianET.getText().toString();
                    pageFlag = 1;
                    httpGetList();
                }

                break;


        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mQueue.cancelAll();
    }
}
