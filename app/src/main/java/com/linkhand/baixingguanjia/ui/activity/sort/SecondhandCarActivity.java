package com.linkhand.baixingguanjia.ui.activity.sort;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.customView.ExpandTabView;
import com.linkhand.baixingguanjia.customView.PopViewAreaSidebar;
import com.linkhand.baixingguanjia.customView.PopViewList;
import com.linkhand.baixingguanjia.entity.Car;
import com.linkhand.baixingguanjia.entity.CommonType;
import com.linkhand.baixingguanjia.entity.EventFlag;
import com.linkhand.baixingguanjia.entity.Picture;
import com.linkhand.baixingguanjia.entity.Sheng;
import com.linkhand.baixingguanjia.ui.activity.detail.SecondhandCarDetail2Activity;
import com.linkhand.baixingguanjia.ui.adapter.SecondhandCarAdapter;
import com.linkhand.baixingguanjia.utils.JSONUtils;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SecondhandCarActivity extends BaseActivity {
    private final static int SHENG = 1; //省
    private final static int SHI = 2; //市
    private final static int QU = 3; //区
    private final static int XIAOQU = 4;//小区
    private static final int HTTP_REQUEST = 0;
    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.menu_layout)
    LinearLayout mMenuLayout;
    @Bind(R.id.listview)
    PullToRefreshListView mListview;


    @Bind(R.id.line)
    View grayline;
    @Bind(R.id.expandtabTab)
    ExpandTabView expandTabView;
    @Bind(R.id.v_bg)
    View v_bg; //popup背景色
    @Bind(R.id.show_layout)
    LinearLayout mLayoutShow;
    @Bind(R.id.cha)
    ImageView mChaIV;
    @Bind(R.id.edit_layout)
    LinearLayout mLayoutEdit;
    @Bind(R.id.search_text)
    TextView mSearchTV;
    @Bind(R.id.null_bg)
    RelativeLayout mNullBg;
    @Bind(R.id.edit)
    EditText mEditText;
    @Bind(R.id.layout)
    LinearLayout mLayout;
    private PopViewAreaSidebar viewArea;
    private PopViewList viewType, viewSales, viewStore;
    private ArrayList<View> mViewArray = new ArrayList<View>();

    private List<String> salesItems = Arrays.asList(new String[]{"价格不限", "10万以下", "10万-30万", "30万-50万", "50万-100万", "100万以上"});
    private List<String> salesItemsVaule = Arrays.asList(new String[]{"n-n", "0-10", "10-30", "30-50", "50-100", "min-100"});
    private List<String> typeItems = new ArrayList<>();
    private List<String> typeItemsVaule = new ArrayList<>();
    private List<String> storeItems = Arrays.asList(new String[]{"发布人不限", "个人", "商家"});
    private List<String> storeItemsVaule = Arrays.asList(new String[]{"n", "5", "4"});

    private List<Car> mList;
    private SecondhandCarAdapter mAdapter;

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
    String typeId; //汽车类型
    String categoryId; //发布人
    String minPublisher; //百万以上筛选：（minPublisher：min； maxPublisher：100)
    String maxPublisher; //百万以上筛选：（minPublisher：min； maxPublisher：100)

    Sheng mSheng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondhand_car);
        ButterKnife.bind(this);
        initView();
        initData();
        showLoading();
        httpGetCarList();
        initListener();
    }


    private void initView() {
        List<CommonType> mTList = (List<CommonType>) SPUtils.get(this, "CarType", new TypeToken<List<CommonType>>() {
        }.getType());
        Map<String, List<String>> map = JSONUtils.getStrArray(mTList);
        typeItems = map.get("items");
        typeItemsVaule = map.get("itemsValue");
        viewArea = new PopViewAreaSidebar(this);
        viewType = new PopViewList(this, typeItems, typeItemsVaule, 2);
        viewSales = new PopViewList(this, salesItems, salesItemsVaule, 3);
        viewStore = new PopViewList(this, storeItems, storeItemsVaule, 4);
        initRefreshListView(mListview);
    }


    private void initData() {
        /**
         * 初始化条件选择view的数据
         */
        mViewArray.add(viewArea);
        mViewArray.add(viewType);
        mViewArray.add(viewSales);
        mViewArray.add(viewStore);
        ArrayList<String> mTextArray = new ArrayList<String>();
        mTextArray.add(getStrgRes(R.string.allCity));
        mTextArray.add(getStrgRes(R.string.sortType));
        mTextArray.add(getStrgRes(R.string.sortScale));
        mTextArray.add(getStrgRes(R.string.sortStore));


        mSheng = (Sheng) SPUtils.get(SecondhandCarActivity.this, "UserLocation", Sheng.class);
        if (mSheng != null) {
            shengId = mSheng.getId();
            shiId = mSheng.getShi().getId();
//            mTextArray.set(0,mSheng.getShi().getName());
            if (mSheng.getQu() != null){
                quId = mSheng.getQu().getId();
                mTextArray.set(0,mSheng.getQu().getName());
            }

            if (mSheng.getXiaoqu() != null){
                xiaoquId = mSheng.getXiaoqu().getId();
                mTextArray.set(0,mSheng.getXiaoqu().getName());
            }

        }else {
            Sheng sheng = (Sheng) SPUtils.get(SecondhandCarActivity.this, "DiQu", Sheng.class);
            shengId = sheng.getId();
            shiId = sheng.getShiList().get(0).getId();
        }

        expandTabView.setValue(mTextArray, mViewArray, v_bg);


        mList = new ArrayList<>();
        mAdapter = new SecondhandCarAdapter(this, R.layout.item_car_info, mList);
        mListview.setAdapter(mAdapter);
    }


    private void initListener() {
        // 搜索输入框事件监听
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(mEditText.getText())) {
                    mChaIV.setVisibility(View.VISIBLE);
                    mSearchTV.setText(getStrgRes(R.string.search));
                } else {
                    mChaIV.setVisibility(View.GONE);
                    mSearchTV.setText(getStrgRes(R.string.cancel));
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
                v_bg.setVisibility(View.GONE);
                onRefresh(viewArea, showText);
                pageFlag = 1;
                showLoading();
                httpGetCarList();
            }


        });
        //* 选择类型
        viewType.setOnSelectListener(new PopViewList.OnSelectListener() {

            @Override
            public void getValue(String value, String showText) {
                typeId = value;
                v_bg.setVisibility(View.GONE);
                onRefresh(viewType, showText);
                pageFlag = 1;
                showLoading();
                httpGetCarList();

            }
        });
        //* 选择价钱
        viewSales.setOnSelectListener(new PopViewList.OnSelectListener() {

            @Override
            public void getValue(String value, String showText) {
                saleType(value);
                v_bg.setVisibility(View.GONE);
                onRefresh(viewSales, showText);
                pageFlag = 1;
                showLoading();
                httpGetCarList();
            }
        });
        //选择商家
        viewStore.setOnSelectListener(new PopViewList.OnSelectListener() {

            @Override
            public void getValue(String value, String showText) {
                v_bg.setVisibility(View.GONE);
                onRefresh(viewStore, showText);
                categoryId = value;
                pageFlag = 1;
                showLoading();
                httpGetCarList();

            }
        });


        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("car", (Serializable) mList.get(position - 1));
                bundle.putInt("position", position - 1);
                go(SecondhandCarDetail2Activity.class, bundle);
            }
        });


        //上拉加载下拉刷新监听
        mListview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //重新加载
                pageFlag = 1;
                httpGetCarList();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                httpGetCarList();
            }
        });

    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }
    @Subscribe
    public void onEvent(EventFlag eventFlag) {
        if (eventFlag.getFlag().equals("offlineCar")) {
            int position = (Integer) eventFlag.getObject();
            mList.remove(position);
            mAdapter.notifyDataSetChanged();
        }

    }


    //网络请求
    public void httpGetCarList() {
        Request<JSONObject> mRequest = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_CAR_LIST, RequestMethod.POST);
        mRequest.add("page", pageFlag);
        mRequest.add("tiaojian", tiaojian);
        mRequest.add("sheng", shengId);
        mRequest.add("shi", shiId);
        mRequest.add("qu", quId);
        mRequest.add("xiaoqu", xiaoquId);
        mRequest.add("type", typeId);
        mRequest.add("category", categoryId);
        mRequest.add("minPublisher", minPublisher);
        mRequest.add("maxPublisher", maxPublisher);
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
                                Car car = gson.fromJson(jsonObject1.toString(), Car.class);
//                                Car car = new Car();
//                                car.setPrice(jsonObject1.getDouble("price"));
                                List<Picture> picList = new ArrayList<Picture>();
                                JSONArray picArray = jsonObject1.getJSONArray("image_url");
                                for (int j = 0; j < picArray.length(); j++) {
                                    Picture picture = new Picture(picArray.getString(j));
                                    picList.add(picture);
                                }
                                car.setPic(picList);
                                mList.add(car);
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
                if (!adjustList(mList)){
                    mNullBg.setVisibility(View.VISIBLE);
                }else {
                    mNullBg.setVisibility(View.GONE);
                }
//                refresh();
            }
        });


    }

    //   刷新listview
    private void refresh() {
        //bug,数据量很小时刷新不能终止，延时0.5秒刷新
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mListview.onRefreshComplete();
                mAdapter.notifyDataSetChanged();
                pageFlag++;
            }
        }, 500);

    }


    /**
     * 更新自定义ToggleButton上的文字
     *
     * @param view
     * @param showText
     */
    private void onRefresh(View view, String showText) {

        expandTabView.onPressBack();
        int position = getPositon(view);
        if (position >= 0 && !expandTabView.getTitle(position).equals(showText)) {
            expandTabView.setTitle(showText, position);
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


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {

        if (!expandTabView.onPressBack()) {
            finish();
        }

    }

    @OnClick({R.id.back, R.id.show_layout, R.id.cha, R.id.search_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.show_layout:
                v_bg.setVisibility(View.GONE);
                mLayoutShow.setVisibility(View.GONE);
                mLayoutEdit.setVisibility(View.VISIBLE);
                mSearchTV.setVisibility(View.VISIBLE);
                break;
            case R.id.cha:
                mEditText.setText("");
                break;
            case R.id.search_text:
                v_bg.setVisibility(View.GONE);
                if (mSearchTV.getText().equals(getStrgRes(R.string.cancel))) {
                    mLayoutShow.setVisibility(View.VISIBLE);
                    mLayoutEdit.setVisibility(View.GONE);
                    mSearchTV.setVisibility(View.GONE);
                    mEditText.setText("");
                }
                tiaojian = mEditText.getText().toString();
                showLoading();
                pageFlag = 1;
                httpGetCarList();

                break;
        }
    }
}
