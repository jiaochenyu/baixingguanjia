package com.linkhand.baixingguanjia.ui.activity.release;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
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
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.customView.SideBar;
import com.linkhand.baixingguanjia.customView.adapter.PopListTextAdapter;
import com.linkhand.baixingguanjia.entity.EventFlag;
import com.linkhand.baixingguanjia.entity.Qu;
import com.linkhand.baixingguanjia.entity.Sheng;
import com.linkhand.baixingguanjia.entity.Xiaoqu;
import com.linkhand.baixingguanjia.utils.JSONUtils;
import com.linkhand.baixingguanjia.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ReleaseAreaSearchActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.title)
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

    Sheng mSheng;
    PopupWindow locationPop;
    PopupWindow locationPop2;
    ListView mListView;
    @Bind(R.id.layout_1)
    LinearLayout mLayout1;
    @Bind(R.id.layout_2)
    LinearLayout mLayout2;
    @Bind(R.id.home_header_layout)
    LinearLayout mHomeHeaderLayout;
    @Bind(R.id.commom_layout)
    LinearLayout mCommomLayout;
    private List<Qu> mQus = new ArrayList<Qu>();
    private SparseArray<LinkedList<Xiaoqu>> mXiaoqus = new SparseArray<LinkedList<Xiaoqu>>();
    private LinkedList<Xiaoqu> xiaoquItem = new LinkedList<Xiaoqu>();
    private PopListTextAdapter mQuAdapter;
    private PopListTextAdapter mXiaoquAdapter;
    private int tQuPosition = 0; //区
    private int tXiaoquPosition = 0; //小区


    public LocationClient mLocationClient = null;
    public BDLocationListener myListener;

    private Sheng updateSheng; //更新用户选择的地址

    String mTitleStr;

    String acFlag = "";//判断是哪个activity传过来的
    Sheng eventSheng; // 传递的参数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_area_search);
        ButterKnife.bind(this);
        initView();
        initData();
        initBDLocation();

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras != null) {
            acFlag = extras.getString("acFlag");
        }
    }

    private void initView() {
        mSubmitTV.setText(R.string.sure_select);
        mCommomLayout.setVisibility(View.VISIBLE);
        mHomeHeaderLayout.setVisibility(View.GONE);
        mLayout1.setVisibility(View.GONE);
        mTitle.setText(R.string.select_address);

    }

    private void initData() {
        eventSheng = new Sheng();
        mSheng = (Sheng) SPUtils.get(this, "DiQu", Sheng.class);
        Map<String, Object> map = JSONUtils.getDiqu2(mSheng.getShiList().get(0).getQuList());
        mQus = (ArrayList<Qu>) map.get("groups");
        mXiaoqus = (SparseArray<LinkedList<Xiaoqu>>) map.get("children");

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
                break;
            case 2:
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

    private void initQuAdatper() {
        mQuAdapter = new PopListTextAdapter(mQus, this, 0,
                R.drawable.pop_list_choose_plate_item_selector, 1);
        mQuAdapter.setTextSize(13);
        mQuAdapter.setTextColor(getResources().getColor(R.color.blackText));
        mQuAdapter.setSelectedPositionNoNotify(tQuPosition);
//        mListview.setAdapter(mQuAdapter);
        mQuAdapter
                .setOnItemClickListener(new PopListTextAdapter.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, final int position) {
                        eventSheng.setQu(mQus.get(position));
                        eventSheng.setXiaoqu(null);
                        mQuTv.setText(mQus.get(position).getName());
                        locationPop.dismiss();
                        tQuPosition = position;

                        mXiaoquTv.setText("小区");
                        xiaoquItem.clear();

                    }
                });

    }


    private void initXiaoquAdatper() {
//        if (tQuPosition >= 0) {  //position 0
//            xiaoquItem.clear();
//            xiaoquItem.addAll(mXiaoqus.get(tQuPosition));
//            showToast(xiaoquItem.size() + " " + xiaoquItem.get(0).getName());
//            sort();
//        }
        xiaoquItem.addAll(mXiaoqus.get(tQuPosition));
        sort();
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
//                        mXiaoquTv.setText(mXiaoqus.get(tShengPosition).get(tShiPosition).get(tQuPosition).get(position).getName());
                        eventSheng.setXiaoqu(xiaoquItem.get(position));
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
        if (Build.VERSION.SDK_INT >= 24) {
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
                    showPop(4);
                    popUtils(mLayout2);
//                    locationPop.showAsDropDown(mLayout2);
                break;
            case R.id.submit:
                if (eventSheng.getQu() == null) {
                    showToast("请选择区");
                    return;
                }
                if (eventSheng.getXiaoqu() == null) {
                    showToast("请选择小区");
                    return;
                }
                EventBus.getDefault().post(new EventFlag(acFlag, eventSheng));
                finish();
                break;
        }
    }
}
