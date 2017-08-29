package com.linkhand.baixingguanjia.ui.activity;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.base.MyApplication;
import com.linkhand.baixingguanjia.customView.adapter.PopListTextAdapter;
import com.linkhand.baixingguanjia.entity.Address;
import com.linkhand.baixingguanjia.entity.EventFlag;
import com.linkhand.baixingguanjia.entity.Qu;
import com.linkhand.baixingguanjia.entity.Sheng;
import com.linkhand.baixingguanjia.entity.Shi;
import com.linkhand.baixingguanjia.entity.Xiaoqu;
import com.linkhand.baixingguanjia.ui.adapter.SelectAddressAdapter;
import com.linkhand.baixingguanjia.utils.JSONUtils;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SelectAddressActivity extends BaseActivity {
    private static final int HTTP_REQUEST = 0;
    private static final int HTTP_REQUEST2 = 2;

    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.listview)
    PullToRefreshListView mListview;
    @Bind(R.id.submit)
    TextView mSubmit;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyListener();
    SelectAddressAdapter mAdapter;
    List<Address> mList;
    Address mSelectedAdd; //选中的地址
    RequestQueue mQueue = NoHttp.newRequestQueue();
    //经纬度
    LatLng mLatLng;
    double jingdu;
    double weidu;
    @Bind(R.id.header_layout)
    RelativeLayout mHeaderLayout;
    @Bind(R.id.province)
    TextView mShengTV;
    @Bind(R.id.province_layout)
    RelativeLayout mShengLayout;
    @Bind(R.id.city)
    TextView mShiTV;
    @Bind(R.id.city_layout)
    RelativeLayout mShiLayout;
    @Bind(R.id.area)
    TextView mQuTV;
    @Bind(R.id.area_layout)
    RelativeLayout mQuLayout;
    @Bind(R.id.layout_area)
    LinearLayout mLayoutArea;
    @Bind(R.id.tiaojian)
    EditText mTiaojianET;
    @Bind(R.id.search_layout)
    RelativeLayout mSearchLayout;
    @Bind(R.id.edit_search_layout)
    RelativeLayout mEditSearchLayout;
    @Bind(R.id.cha)
    ImageView mChaIV;
    private int pageFlag = 1;
    String mtiaojianStr;
    String shengId;
    String shiId;
    String quId;
    boolean viewFlag = false;  //true 显示搜索

    List<Sheng> mShengs;
    PopupWindow locationPop;
    private ListView mLocationListview;


    private SparseArray<LinkedList<Shi>> mShis = new SparseArray<LinkedList<Shi>>();
    private SparseArray<SparseArray<LinkedList<Qu>>> mQus = new SparseArray<>();
    private SparseArray<SparseArray<SparseArray<LinkedList<Xiaoqu>>>> mXiaoqus = new SparseArray<>();
    private LinkedList<Shi> shiItem = new LinkedList<Shi>();
    private LinkedList<Qu> quItem = new LinkedList<Qu>();
    private PopListTextAdapter mShengAdapter;
    private PopListTextAdapter mShiAdapter;
    private PopListTextAdapter mQuAdapter;
    private int tShengPosition = 0;  //省
    private int tShiPosition = 0; //市
    private int tQuPosition = 0; //区

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);
        ButterKnife.bind(this);
        initData();
        initview();
        initListener();
        initBDLocation();
    }


    private void initview() {
        mTitle.setTextColor(getResources().getColor(R.color.blackText));
        mBack.setImageDrawable(getResources().getDrawable(R.mipmap.icon_back_black));
        mHeaderLayout.setBackground(getResources().getDrawable(R.color.colorWhite));
        mTitle.setText(R.string.selectAddressTitle);
        initRefreshListView(mListview);
        mListview.setMode(PullToRefreshBase.Mode.BOTH);
    }

    private void initData() {
        mShengs = (List<Sheng>) SPUtils.get(this, "Location", new TypeToken<List<Sheng>>() {
        }.getType());
        if (mShengs != null && mShengs.size() > 0) {
            Map<String, Object> map = JSONUtils.getDiquAll(mShengs);
            mShis = (SparseArray<LinkedList<Shi>>) map.get("shis");
            mQus = (SparseArray<SparseArray<LinkedList<Qu>>>) map.get("qus");
        }
        mList = new ArrayList<>();
        //38.0488378098,114.5207820418
        mLatLng = new LatLng(38.0488378098, 114.5207820418);
        mAdapter = new SelectAddressAdapter(this, R.layout.item_select_address, mList, mSelectedAdd, mLatLng);
        mListview.setAdapter(mAdapter);
        httpGetList();
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras != null) {
            mSelectedAdd = (Address) extras.getSerializable("address");
        }
    }

    private void initListener() {
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectedAdd = mList.get(position - 1);
                mAdapter.setSelectedAdd(mSelectedAdd);
                mAdapter.notifyDataSetChanged();
                showToast("点击了" + position);
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
        mTiaojianET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(mTiaojianET.getText())) {
                    mChaIV.setVisibility(View.VISIBLE);
                } else {
                    mChaIV.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void initBDLocation() {
        myListener = new MyListener();
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(0);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要

        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps

        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果

        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集

        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }


    //网络请求
    public void httpGetList() {
        Request<JSONObject> mRequest = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_PROUCT_GET_ADDRESS_LIST, RequestMethod.POST);
        mRequest.add("page", pageFlag);
        mRequest.add("num", 10);
        mRequest.add("user_id", MyApplication.getUser().getUserid());
        mRequest.add("pickup_name", mtiaojianStr);
        mRequest.add("province_id", shengId);
        mRequest.add("city_id", shiId);
        mRequest.add("district_id", quId);
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
                            JSONArray array = jsonObject.getJSONArray("list");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jsonObject1 = array.getJSONObject(i);
                                Gson gson = new Gson();
                                Address address = gson.fromJson(jsonObject1.toString(), Address.class);
                                mList.add(address);
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
            }
        });
    }


    //网络请求 修改默认地址
    public void httpUpdateAddress() {
        Request<JSONObject> mRequest = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_PROUCT_UPDATE_DEF_ADDRESS, RequestMethod.POST);
        mRequest.add("pickup_id", mSelectedAdd.getPickup_id());
        mRequest.add("user_id", MyApplication.getUser().getUserid());
        mRequest.add("edit", 1);
        Log.d("参数", mRequest.getParamKeyValues().values().toString());
        mQueue.add(HTTP_REQUEST2, mRequest, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
                showLoading(true);
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == HTTP_REQUEST2) {// 根据what判断是哪个请求的返回，这样就可以用一个OnResponseListener来接受多个请求的结果。
                    int responseCode = response.getHeaders().getResponseCode();// 服务器响应码。
                    JSONObject jsonObject = response.get();
                    try {

                        if (jsonObject.getInt("code") == 200) {
//                            showToast(R.string.selectAddressSucc);
                            EventBus.getDefault().post(new EventFlag("selectedAddress", mSelectedAdd));
                            finish();
                        } else {
//                            showToast(R.string.selectAddressFailed);
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
                mAdapter.notifyDataSetChanged();
            }
        });


    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Subscribe
    public void onEvent(EventFlag eventFlag) {
        if (eventFlag.getFlag().equals("addNotify")) {
            mAdapter.setLatLng1(mLatLng);
            mAdapter.notifyDataSetChanged();
        }

    }


    /**
     * 百度地图
     */
    private class MyListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location != null) {
//                MyApplication.setLocation(location);
                location.getLatitude(); //纬度
                location.getLongitude(); //经度
                mLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                EventBus.getDefault().post(new EventFlag("addNotify", ""));
            }

            //

        }

    }

    /**************地区筛选**********/
    private void showPop(int flag) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.from(this).inflate(R.layout.view_list_popuwindow_sidebar, null);
        mLocationListview = (ListView) view.findViewById(R.id.listview1);
//        initSidebarListener();
        switch (flag) {
            case 1:
                initShengAdatper();
                mLocationListview.setAdapter(mShengAdapter);
                break;
            case 2:
                initShiAdatper();
                mLocationListview.setAdapter(mShiAdapter);
                break;
            case 3:
                initQuAdatper();
                mLocationListview.setAdapter(mQuAdapter);
                break;

        }
        locationPop = new PopupWindow();
        locationPop.setContentView(view);
        locationPop.setAnimationStyle(R.style.menu_popwindow_anim_style);
        locationPop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        locationPop.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        locationPop.setOutsideTouchable(true);
        locationPop.setFocusable(true);
        locationPop.setBackgroundDrawable(new BitmapDrawable());
    }

    private void initShengAdatper() {
        mShengAdapter = new PopListTextAdapter(mShengs, this,
                R.color.colorWhite,
                R.drawable.pop_list_choose_eara_item_selector, 1);
        mShengAdapter.setTextSize(13);
        mShengAdapter.setTextColor(getResources().getColor(R.color.blackText));
        mShengAdapter.setSelectedPositionNoNotify(tShengPosition);
//        mListview.setAdapter(mShengAdapter);
        mShengAdapter
                .setOnItemClickListener(new PopListTextAdapter.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {
                        if (position < mShis.size()) {
                            Sheng sheng = mShengs.get(position);
                            mShengTV.setText(sheng.getName());
                            mShiTV.setText(mShis.get(position).get(0).getName());
                            mQuTV.setText("区");
                            quItem.clear();

                            shengId = sheng.getId();
                            shiId = mShis.get(position).get(0).getId();
                            quId = "";
                            pageFlag = 1;
                            httpGetList();
                            locationPop.dismiss();

                        }
                        tShengPosition = position;
                    }
                });

    }

    private void initShiAdatper() {
        if (tShengPosition < mShis.size()) {
            shiItem.clear();
            shiItem.addAll(mShis.get(tShengPosition));
        }

        mShiAdapter = new PopListTextAdapter(shiItem, this, 0,
                R.drawable.pop_list_choose_plate_item_selector, 1);
        mShiAdapter.setTextSize(13);
        mShiAdapter.setTextColor(getResources().getColor(R.color.blackText));
        mShiAdapter.setSelectedPositionNoNotify(tShiPosition);
//        mListview.setAdapter(mShiAdapter);
        mShiAdapter
                .setOnItemClickListener(new PopListTextAdapter.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, final int position) {
                        mShiTV.setText(mShis.get(tShengPosition).get(position).getName());
                        locationPop.dismiss();

                        mQuTV.setText("区");
                        quItem.clear();

                        shiId = mShis.get(tShengPosition).get(position).getId();
                        quId = "";
                        pageFlag = 1;
                        httpGetList();

                        tShiPosition = position;
                    }
                });

    }

    private void initQuAdatper() {
        if (tShiPosition < mQus.size()) {
            quItem.clear();
            quItem.addAll(mQus.get(tShengPosition).get(tShiPosition));
        }

        mQuAdapter = new PopListTextAdapter(quItem, this, 0,
                R.drawable.pop_list_choose_plate_item_selector, 1);
        mQuAdapter.setTextSize(13);
        mQuAdapter.setTextColor(getResources().getColor(R.color.blackText));
        mQuAdapter.setSelectedPositionNoNotify(tQuPosition);
//        mListview.setAdapter(mQuAdapter);
        mQuAdapter
                .setOnItemClickListener(new PopListTextAdapter.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, final int position) {
                        if (position == 0) { //表示选择全城
                            quId = "";
                        } else {
                            quId = mQus.get(tShengPosition).get(tShiPosition).get(position).getId();

                        }
                        pageFlag = 1;
                        httpGetList();
                        mQuTV.setText(mQus.get(tShengPosition).get(tShiPosition).get(position).getName());
                        locationPop.dismiss();
                        tQuPosition = position;
                    }
                });

    }

    private void popUtils(View v) {
        if (android.os.Build.VERSION.SDK_INT >= 24) {
            int[] a = new int[2];
            v.getLocationInWindow(a);
            locationPop.showAtLocation(getWindow().getDecorView(), Gravity.NO_GRAVITY, 0, a[1] + v.getHeight());
        } else {
            locationPop.showAsDropDown(v);
        }
    }

    @OnClick({R.id.back, R.id.submit, R.id.province_layout, R.id.city_layout, R.id.area_layout, R.id.search_layout, R.id.cha})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.submit:
                httpUpdateAddress();
                break;
            case R.id.province_layout:
                showPop(1);
                popUtils(mLayoutArea);
                break;
            case R.id.city_layout:
                if (!TextUtils.isEmpty(shengId)) {
                    showPop(2);
                    popUtils(mLayoutArea);
                }
                break;
            case R.id.area_layout:
                if (!TextUtils.isEmpty(shengId) && !TextUtils.isEmpty(shiId)) {
                    showPop(3);
                    popUtils(mLayoutArea);
//                    locationPop.showAsDropDown(mLayout2);
                }
                break;
            case R.id.search_layout:
                viewFlag = !viewFlag;
                if (TextUtils.isEmpty(mTiaojianET.getText().toString())) {
                    if (viewFlag) {
                        mEditSearchLayout.setVisibility(View.VISIBLE);
                        mLayoutArea.setVisibility(View.GONE);
                    } else {
                        mEditSearchLayout.setVisibility(View.GONE);
                        mLayoutArea.setVisibility(View.VISIBLE);
                        mtiaojianStr = "";
                        pageFlag = 1;
                        httpGetList();
                    }

                } else {
                    mtiaojianStr = mTiaojianET.getText().toString();
                    pageFlag = 1;
                    httpGetList();
                }
                break;
            case R.id.cha:
                mTiaojianET.setText("");
                break;
        }
    }


}
