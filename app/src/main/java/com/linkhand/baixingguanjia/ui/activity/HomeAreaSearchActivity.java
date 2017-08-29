package com.linkhand.baixingguanjia.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.gson.reflect.TypeToken;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.base.MyApplication;
import com.linkhand.baixingguanjia.customView.SideBar;
import com.linkhand.baixingguanjia.customView.adapter.PopListTextAdapter;
import com.linkhand.baixingguanjia.entity.EventFlag;
import com.linkhand.baixingguanjia.entity.Qu;
import com.linkhand.baixingguanjia.entity.Sheng;
import com.linkhand.baixingguanjia.entity.Shi;
import com.linkhand.baixingguanjia.entity.Xiaoqu;
import com.linkhand.baixingguanjia.ui.service.HttpService;
import com.linkhand.baixingguanjia.utils.JSONUtils;
import com.linkhand.baixingguanjia.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeAreaSearchActivity extends BaseActivity {

    @Bind(R.id.back1)
    ImageView mBack;
    @Bind(R.id.title1)
    TextView mTitle;
    @Bind(R.id.edit)
    EditText mEdit;
    @Bind(R.id.city)
    TextView mCity;
    @Bind(R.id.sheng_tv)
    TextView mShengTv;
    @Bind(R.id.sheng_layout)
    RelativeLayout mShengLayout;
    @Bind(R.id.shi_tv)
    TextView mShiTv;
    @Bind(R.id.shi_layout)
    RelativeLayout mShiLayout;
    @Bind(R.id.qu_tv)
    TextView mQuTv;
    @Bind(R.id.qu_layout)
    RelativeLayout mQuLayout;
    @Bind(R.id.xiaoqu_tv)
    TextView mXiaoquTv;
    @Bind(R.id.xiaoqu_layout)
    RelativeLayout mXiaoquLayout;
    @Bind(R.id.submit)
    TextView mSubmitTV;
    @Bind(R.id.listview1)
    ListView mListview;
    //    @Bind(R.id.showletter)
    TextView mShowletter;
    //    @Bind(R.id.sidebar)
    SideBar mSidebar;

    List<Sheng> mShengs;
    PopupWindow locationPop;
    PopupWindow locationPop2;
    ListView mListView;
    @Bind(R.id.layout_1)
    LinearLayout mLayout1;
    @Bind(R.id.layout_2)
    LinearLayout mLayout2;
    private SparseArray<LinkedList<Shi>> mShis = new SparseArray<LinkedList<Shi>>();
    private SparseArray<SparseArray<LinkedList<Qu>>> mQus = new SparseArray<>();
    private SparseArray<SparseArray<SparseArray<LinkedList<Xiaoqu>>>> mXiaoqus = new SparseArray<>();
    private LinkedList<Shi> shiItem = new LinkedList<Shi>();
    private LinkedList<Qu> quItem = new LinkedList<Qu>();
    private LinkedList<Xiaoqu> xiaoquItem = new LinkedList<Xiaoqu>();
    private PopListTextAdapter mShengAdapter;
    private PopListTextAdapter mShiAdapter;
    private PopListTextAdapter mQuAdapter;
    private PopListTextAdapter mXiaoquAdapter;
    private int tShengPosition = 0;  //省
    private int tShiPosition = 0; //市
    private int tQuPosition = 0; //区
    private int tXiaoquPosition = 0; //小区

    private Sheng userSheng; //用户存储的省市区信息


    public LocationClient mLocationClient = null;
    public BDLocationListener myListener;

    private Sheng updateSheng; //更新用户选择的地址

    String mTitleStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_area_search);
        ButterKnife.bind(this);
        initView();
        initData();
        initBDLocation();
//        initPop();
//        initAdatper();
//        initListener();
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras != null) {
            mTitleStr = extras.getString("selectLocation", "石家庄");
        }
    }

    private void initView() {
        if (userSheng == null) {
            mTitle.setText("当前定位城市-" + mTitleStr);
        } else {
            mTitle.setText("当前选择地区-" + mTitleStr);
        }
    }

    private void initData() {
        mShengs = (List<Sheng>) SPUtils.get(this, "Location", new TypeToken<List<Sheng>>() {
        }.getType());
        if (mShengs != null && mShengs.size() > 0) {
            Map<String, Object> map = JSONUtils.getDiquAll(mShengs);
            mShis = (SparseArray<LinkedList<Shi>>) map.get("shis");
            mQus = (SparseArray<SparseArray<LinkedList<Qu>>>) map.get("qus");
            mXiaoqus = (SparseArray<SparseArray<SparseArray<LinkedList<Xiaoqu>>>>) map.get("xiaoqus");
        }
        //设置默认  也就是选中的 省市区小区
        userSheng = (Sheng) SPUtils.get(this, "UserLocation", Sheng.class);
        updateSheng = userSheng;
        if (userSheng == null && MyApplication.getLocation() != null) {
//            mShengTv.setText("省");
        } else if (userSheng != null) {
            mShengTv.setText(userSheng.getName());
            mShiTv.setText(userSheng.getShi().getName());
            Sheng flagSheng = null;
            Shi flagShi = null;
            Qu flagQu = null;
            Xiaoqu flagXiaoqu = null;
            for (int i = 0; i < mShengs.size(); i++) {
                if (mShengs.get(i).getName().equals(userSheng.getName())) {
                    tShengPosition = i;
                    flagSheng = mShengs.get(i);
                    break;
                }

            }
            if (flagSheng != null) {
                for (int i = 0; i < flagSheng.getShiList().size(); i++) {
                    if (flagSheng.getShiList().get(i).getName().equals(userSheng.getShi().getName())) {
                        tShiPosition = i;
                        flagShi = flagSheng.getShiList().get(i);
                        break;
                    }
                }
            }
            if (userSheng.getQu() != null) {
                mQuTv.setText(userSheng.getQu().getName());
                if (flagShi != null) {
                    for (int i = 0; i < flagShi.getQuList().size(); i++) {
                        if (flagShi.getQuList().get(i).getName().equals(userSheng.getQu().getName())) {
                            flagQu = flagShi.getQuList().get(i);
                            tQuPosition = i + 1;
                            break;
                        }
                    }
                }
                Log.e("qu", userSheng.getQu().getName() + " " + tQuPosition);
            }
            if (userSheng.getXiaoqu() != null) {
                Log.e("xiaoqu", userSheng.getXiaoqu().getName());
                mXiaoquTv.setText(userSheng.getXiaoqu().getName());
//                if (flagQu != null) {
////                    for (int i = 0; i < flagQu.getXiaoquList().size(); i++) {
////                        if (flagQu.getXiaoquList().get(i).getName().equals(userSheng.getXiaoqu().getName())) {
////                            flagXiaoqu = flagQu.getXiaoquList().get(i);
//////                            tXiaoquPosition = i;
////                            break;
////                        }
////                    }
//                }
            }

        } else if (userSheng == null && MyApplication.getLocation() == null) {
//            mShengTv.setText("省");
        }


    }

    private void initBDLocation() {
        myListener = new MyBDListener();
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


    private void initSidebarListener() {
        mSidebar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                char letter;
                letter = s.toUpperCase().charAt(0);

//                for (int i = 0; i < mXiaoqus.size(); i++) {
                for (int i = 0; i < xiaoquItem.size(); i++) {
//                    Log.e("theLetter", "onTouchingLetterChanged: " + xiaoquItem.get(i).getName());
                    if (xiaoquItem.get(i).getLetter() == letter) {
                        Log.e("onTouchingLetterChanged", "onTouchingLetterChanged: " + i);
                        mListview.setSelection(i);
                        break;
                    }
                }
            }
        });
    }

    private void showPop(int flag) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.from(this).inflate(R.layout.view_list_popuwindow_sidebar, null);
        mListView = (ListView) view.findViewById(R.id.listview1);
        mShowletter = (TextView) view.findViewById(R.id.showletter);
        mSidebar = (SideBar) view.findViewById(R.id.sidebar);
        mSidebar.setTextView(mShowletter);
//        initSidebarListener();
        switch (flag) {
            case 1:
                mSidebar.setVisibility(View.GONE);
                mShowletter.setVisibility(View.GONE);
                initShengAdatper();
                mListView.setAdapter(mShengAdapter);
                break;
            case 2:
                mSidebar.setVisibility(View.GONE);
                mShowletter.setVisibility(View.GONE);
                initShiAdatper();
                mListView.setAdapter(mShiAdapter);
                break;
            case 3:
                mSidebar.setVisibility(View.GONE);
                mShowletter.setVisibility(View.GONE);
                initQuAdatper();
                mListView.setAdapter(mQuAdapter);
                break;
            case 4:
                mSidebar.setVisibility(View.VISIBLE);
                mShowletter.setVisibility(View.GONE);
                initXiaoquAdatper();
                mListView.setAdapter(mXiaoquAdapter);
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
                            updateSheng = mShengs.get(position);
                            updateSheng.setShi(updateSheng.getShiList().get(0));

                            mShengTv.setText(updateSheng.getName());
                            showToast("" + updateSheng.getName() + "" + mShis.get(position).get(0).getName());
                            mShiTv.setText(mShis.get(position).get(0).getName());

                            mQuTv.setText("区");
                            mXiaoquTv.setText("小区");
                            quItem.clear();
                            xiaoquItem.clear();
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
                        mShiTv.setText(mShis.get(tShengPosition).get(position).getName());
                        locationPop.dismiss();

                        mQuTv.setText("区");
                        mXiaoquTv.setText("小区");
                        quItem.clear();
                        xiaoquItem.clear();

                        updateSheng = mShengs.get(tShengPosition);
                        updateSheng.setShi(updateSheng.getShiList().get(position));

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
                            updateSheng = mShengs.get(tShengPosition);
                            updateSheng.setShi(mShis.get(tShengPosition).get(tShiPosition));
                        } else {
                            updateSheng = mShengs.get(tShengPosition);
                            updateSheng.setShi(mShis.get(tShengPosition).get(tShiPosition));
                            updateSheng.setQu(mQus.get(tShengPosition).get(tShiPosition).get(position));
                        }

                        mQuTv.setText(mQus.get(tShengPosition).get(tShiPosition).get(position).getName());
                        locationPop.dismiss();
                        tQuPosition = position;

                        mXiaoquTv.setText("小区");
                        xiaoquItem.clear();

                    }
                });

    }


    private void initXiaoquAdatper() {
//        if (tQuPosition < mXiaoqus.size() && tQuPosition > 0) {  //position 0 是全城
        if (tQuPosition > 0) {  //position 0 是全城
            xiaoquItem.clear();
            xiaoquItem.addAll(mXiaoqus.get(tShengPosition).get(tShiPosition).get(tQuPosition));
            showToast(xiaoquItem.size() + " " + xiaoquItem.get(0).getName());
            sort();
        }
        mXiaoquAdapter = new PopListTextAdapter(xiaoquItem, this, 0,
                R.drawable.pop_list_choose_plate_item_selector, 1);
        mXiaoquAdapter.setTextSize(13);
        mXiaoquAdapter.setTextColor(getResources().getColor(R.color.blackText));
        mXiaoquAdapter.setSelectedPositionNoNotify(tXiaoquPosition);
//        mListview.setAdapter(mXiaoquAdapter);
        mXiaoquAdapter
                .setOnItemClickListener(new PopListTextAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {
                        if (position == 0) { //表示选择全区
                            updateSheng = mShengs.get(tShengPosition);
                            updateSheng.setShi(mShis.get(tShengPosition).get(tShiPosition));
                            updateSheng.setQu(mQus.get(tShengPosition).get(tShiPosition).get(tQuPosition));
                        } else {
                            updateSheng = mShengs.get(tShengPosition);
                            updateSheng.setShi(mShis.get(tShengPosition).get(tShiPosition));
                            updateSheng.setQu(mQus.get(tShengPosition).get(tShiPosition).get(tQuPosition));
                            updateSheng.setXiaoqu(xiaoquItem.get(position));
                        }
//                        mXiaoquTv.setText(mXiaoqus.get(tShengPosition).get(tShiPosition).get(tQuPosition).get(position).getName());
                        mXiaoquTv.setText(xiaoquItem.get(position).getName());
                        tXiaoquPosition = position;
                        locationPop.dismiss();
                    }
                });

        mSidebar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                char letter;
                letter = s.toUpperCase().charAt(0);
                for (int i = 0; i < xiaoquItem.size(); i++) {
                    Log.e("theLetter", "onTouchingLetterChanged: " + xiaoquItem.get(i).getLetter());
                    if (xiaoquItem.get(i).getLetter() == letter) {
                        Log.e("onTouchingLetterChanged", "onTouchingLetterChanged: " + i);
                        mListView.setSelection(i);
                        break;
                    }
                }
            }
        });

    }

    private void sort() {
        for (int i = 0; i < xiaoquItem.size(); i++) {
            for (int j = i + 1; j < xiaoquItem.size(); j++) {
                if (xiaoquItem.get(j).getLetter() < xiaoquItem.get(i).getLetter()) {
                    Xiaoqu temp = xiaoquItem.get(i);
                    xiaoquItem.set(i, xiaoquItem.get(j));
                    xiaoquItem.set(j, temp);
                }
            }
        }
    }

    private class MyBDListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            EventBus.getDefault().post(new EventFlag("city", location.getCity()));
            Log.d("地图", "定位城市: " + location.getCity());
        }

    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Subscribe
    public void onEvent(EventFlag eventFlag) {
        if (eventFlag.getFlag().equals("city")) {
            mCity.setText((String) eventFlag.getObject());
        }
    }

    /**
     * 解决华为手机7.0以上pop显示问题
     *
     * @param v
     */
    private void popUtils(View v) {
        if (android.os.Build.VERSION.SDK_INT >= 24) {
            int[] a = new int[2];
            v.getLocationInWindow(a);
            locationPop.showAtLocation(getWindow().getDecorView(), Gravity.NO_GRAVITY, 0, a[1] + v.getHeight());
        } else {
            locationPop.showAsDropDown(v);
        }
    }

    @OnClick({R.id.back, R.id.submit, R.id.sheng_layout, R.id.shi_layout, R.id.qu_layout, R.id.xiaoqu_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.sheng_layout:
                showPop(1);
                popUtils(mLayout1);
//                locationPop.showAsDropDown(mLayout1);
                break;
            case R.id.shi_layout:
                showPop(2);
                popUtils(mLayout1);
//                locationPop.showAsDropDown(mLayout1);
                break;
            case R.id.qu_layout:
                showPop(3);
                popUtils(mLayout2);
//                locationPop.showAsDropDown(mLayout2);
                break;
            case R.id.xiaoqu_layout:
                if (tQuPosition > 0) {
                    showPop(4);
                    popUtils(mLayout2);
//                    locationPop.showAsDropDown(mLayout2);
                }
                break;
            case R.id.submit:
//                showToast(updateSheng.getName() + updateSheng.getShi().getName()+updateSheng.getQu().getName()+updateSheng.getXiaoqu().getName());
//                Log.e("地区接口参数", updateSheng.getName() + updateSheng.getShi().getName()+updateSheng.getQu().getName()+updateSheng.getXiaoqu().getName());
                SPUtils.put(HomeAreaSearchActivity.this, "UserLocation", updateSheng);
                Intent intent = new Intent(getApplicationContext(), HttpService.class);
                intent.putExtra("sheng", updateSheng);
                startService(intent);
                EventBus.getDefault().post(new EventFlag("updateLocation", ""));
                finish();
                break;
        }
    }
}
