package com.linkhand.baixingguanjia.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseFragment;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.base.MyApplication;
import com.linkhand.baixingguanjia.customView.ExpandTabView;
import com.linkhand.baixingguanjia.customView.PopViewAreaFour;
import com.linkhand.baixingguanjia.entity.EventFlag;
import com.linkhand.baixingguanjia.entity.Goods;
import com.linkhand.baixingguanjia.entity.HomeNormalIcon;
import com.linkhand.baixingguanjia.entity.HomePopularIcon;
import com.linkhand.baixingguanjia.entity.HomeType;
import com.linkhand.baixingguanjia.entity.Qu;
import com.linkhand.baixingguanjia.entity.Sheng;
import com.linkhand.baixingguanjia.entity.Shi;
import com.linkhand.baixingguanjia.entity.Xiaoqu;
import com.linkhand.baixingguanjia.ui.activity.HomeAreaSearchActivity;
import com.linkhand.baixingguanjia.ui.activity.LoginActivity;
import com.linkhand.baixingguanjia.ui.activity.MessageActivity;
import com.linkhand.baixingguanjia.ui.activity.detail.HotGoodsDetailActivity;
import com.linkhand.baixingguanjia.ui.activity.detail.NoticeGoodsDetailActivity;
import com.linkhand.baixingguanjia.ui.adapter.HomeRecyclerViewAdapter;
import com.linkhand.baixingguanjia.ui.service.HttpService;
import com.linkhand.baixingguanjia.utils.SPUtils;
import com.linkhand.bxgj.lib.utils.DateTimeUtils;
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
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JCY on 2017/6/14.
 * 说明：
 */

public class HomeFragment extends BaseFragment {
    private static final int REQUEST_WHAT = 2;
    @Bind(R.id.location)
    TextView mLocationTV;
    @Bind(R.id.location_layout)
    LinearLayout mLocationLayout;
    @Bind(R.id.layout)
    LinearLayout mHeaderLayout;
    @Bind(R.id.message)
    RelativeLayout mMessageLayout;
    @Bind(R.id.home_recyclerview)
    RecyclerView mRecyclerView;
    @Bind(R.id.expandtabTab)
    ExpandTabView expandTabView;
    @Bind(R.id.message_num)
    TextView mMessageNumTV;
    @Bind(R.id.v_bg)
    View v_bg; //popup背景色
    @Bind(R.id.layout_swipe_refresh)
    SwipeRefreshLayout mRefreshLayout;

    HomeRecyclerViewAdapter mAdapter;
    private View mView;
    PopViewAreaFour mPopViewArea;
    PopupWindow mPopupWindow;

    private List<HomeType> mList;
    private Sheng userSheng; //用户存储的省市区信息


    RequestQueue mQueue = NoHttp.newRequestQueue();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, mView);
        setStatusBarColor(R.color.colorSystemBlue);
        initData();
        httpGetIndex();
        initView();
//        initPop();
        initListener();
        return mView;
    }


    @Override
    public void onResume() {
        super.onResume();
//        setStatusBarColor(R.color.colorSystemBlue);
    }

    private void initView() {
        if (userSheng == null && MyApplication.getLocation() != null) {
            if (TextUtils.isEmpty(MyApplication.getLocation().getCity())) {
                mLocationTV.setText("石家庄市");
            } else {
                mLocationTV.setText(MyApplication.getLocation().getCity());
            }
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

    }

    private void initData() {
        //初始化recyclerview
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        userSheng = (Sheng) SPUtils.get(getActivity(), "UserLocation", Sheng.class);
        mList = new ArrayList<>();
//        mList = (List<HomeType>) SPUtils.get(getActivity(),"homeList", new TypeToken<List<HomeType>>(){}.getType());
//        initDataView();
        mAdapter = new HomeRecyclerViewAdapter(getActivity(), mList);
        mRecyclerView.setAdapter(mAdapter);
//        getData();

    }


    private void initListener() {
        mAdapter.setOnItemClickListener(new HomeRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mList.get(position).getType() == 1) {
                    if (mList.get(position).getGoods().getGoods_id() == null || DateTimeUtils.compareTime(mList.get(position).getGoods().getEnd_time() * 1000L) <= 0) {
                        showToast("活动已结束");
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putString("eventId",mList.get(position).getGoods().getId());//活动id
                        bundle.putString("goodsId", mList.get(position).getGoods().getGoods_id());
                        go(HotGoodsDetailActivity.class, bundle);
                    }

                } else if (mList.get(position).getType() == 2) {
                    if (mList.get(position).getGoods().getGoods_id() == null || DateTimeUtils.compareTime(mList.get(position).getGoods().getStart_time() * 1000L) <= 0) {
                        showToast("敬请期待");
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putString("goodsId", mList.get(position).getGoods().getGoods_id());
                        go(NoticeGoodsDetailActivity.class, bundle);
                    }
                }
            }
        });
        v_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                v_bg.setVisibility(View.GONE);
            }
        });
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                httpGetIndex();
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

        if (eventFlag.getFlag().equals("HotCountDownFinish")) {
            httpGetIndex();
        }


    }

    private void initPop() {
        mPopViewArea = new PopViewAreaFour(getActivity());
        mPopupWindow = new PopupWindow(mPopViewArea);
        mPopupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
        mPopupWindow.setFocusable(false);
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//        PopUIHelper.setPopupWindowTouchModal(mPopupWindow, false);
//        mPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.transparent));

        mPopViewArea.setOnSelectListener(new PopViewAreaFour.OnSelectListener() {
            @Override
            public void getValue(Sheng s, Shi shi, Qu qu, Xiaoqu xiaoqu, String showText) {
                Log.d("参数", "getValue:  quPos" + " xiaoquPos");
                s.setShi(shi);
                s.setQu(qu);
                s.setXiaoqu(xiaoqu);
                SPUtils.put(getActivity(), "UserLocation", s);
                Intent intent = new Intent(getActivity(), HttpService.class);
                intent.putExtra("sheng", s);
                getActivity().startService(intent);
                mPopupWindow.dismiss();
                v_bg.setVisibility(View.GONE);
                mLocationTV.setText(showText);

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
//                showPop();
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

    private void showPop() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            v_bg.setVisibility(View.GONE);
        } else if (mPopupWindow != null && !mPopupWindow.isShowing()) {
//            mPopupWindow.showAsDropDown(mLocationLayout,0,0);
            v_bg.setVisibility(View.VISIBLE);
            mPopupWindow.setContentView(mPopViewArea);
            mPopupWindow.showAsDropDown(mHeaderLayout, 0, 0);
        }
    }


    /**
     * 获取数据
     */
    private void getData() {


        HomeType homeType1 = new HomeType();
        homeType1.setType(1);
        homeType1.setContent("236件已售 仅剩16小时");

        HomeType homeType2 = new HomeType();
        homeType2.setType(2);
        homeType2.setContent("明日10点开抢");

        mList.add(0, homeType1);
        mList.add(1, homeType2);

        mAdapter.notifyDataSetChanged();

    }

    /**
     * 加载首页数据
     */

    private void httpGetIndex() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.PRODUCT_INDEX, RequestMethod.POST);

        mQueue.add(REQUEST_WHAT, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
                showLoading(false);
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == REQUEST_WHAT) {
                    String resultCode = null;
                    Log.e("tag", response.get().toString());
                    try {
                        Gson gson = new Gson();
                        JSONObject jsonObject = response.get();
                        resultCode = jsonObject.getString("code");
                        if (resultCode.equals("200") || resultCode.equals("202")) {
                            List<HomeType> homeList = new ArrayList<HomeType>();
                            HomeType homeType1 = new HomeType();
                            HomeType homeType2 = new HomeType();
//                            HomeType homeType3 = new HomeType();
                            homeType1.setType(1);
                            homeType2.setType(2);
//                            homeType3.setType(3);


                            String o = jsonObject.get("data").toString();
                            if (o != null && !o.equals("null")) {
                                JSONArray array = jsonObject.getJSONArray("data");
                                for (int i = 0; i < array.length(); i++) {
                                    Goods goods = gson.fromJson(array.getJSONObject(i).toString(), Goods.class);
                                    List<String> pictures = new ArrayList<String>();
                                    pictures.add(goods.getOriginal_img());
                                    goods.setPicList(pictures);
                                    if (i == 0) {
                                        homeType1.setGoods(goods);
                                    } else if (i == 1) {
                                        homeType2.setGoods(goods);
                                    }
                                }
                                if (array.length() == 0) {
                                    homeType1.setGoods(new Goods());
                                    homeType2.setGoods(new Goods());
                                }
                                if (array.length() == 1) {
                                    homeType2.setGoods(new Goods());
                                }
                            } else {
                                homeType1.setGoods(new Goods());
                                homeType2.setGoods(new Goods());
                            }

//                            Goods goods1;
//                            String o1 = jsonObject.get("data").toString();
//                            if (o1 != null && !o1.equals("null")) {
//                                JSONObject jsonData1 = jsonObject.getJSONObject("data");
//                                goods1 = gson.fromJson(jsonData1.getJSONObject("dafe").toString(), Goods.class);
//                                List<String> pictures = new ArrayList<String>();
////                                JSONArray imageArray = jsonData1.getJSONObject("dafe").getJSONArray("original_img");
////                                for (int i = 0; i < imageArray.length(); i++) {
////                                    pictures.add(imageArray.getString(i));
////                                }
//                                pictures.add(jsonData1.getJSONObject("dafe").getString("original_img"));
//                                goods1.setPicList(pictures);
//                            } else {
//                                goods1 = new Goods();
//                            }
//                            homeType1.setGoods(goods1);
//
//
//                            Goods goods2;
//                            String o2 =  jsonObject.get("data1").toString();
//                            if (o2 != null && !o2.equals("null")) {
//                                JSONObject jsonData2 = jsonObject.getJSONObject("data1");
//                                goods2 = gson.fromJson(jsonData2.getJSONObject("dafee").toString(), Goods.class);
//                                List<String> pictures = new ArrayList<String>();
////                                JSONArray imageArray = jsonData2.getJSONObject("dafe").getJSONArray("original_img");
////                                for (int i = 0; i < imageArray.length(); i++) {
////                                    pictures.add(imageArray.getString(i));
////                                }
//                                pictures.add(jsonData2.getJSONObject("dafee").getString("original_img"));
//                                goods2.setPicList(pictures);
//                            } else {
//                                goods2 = new Goods();
//                            }
//                            homeType2.setGoods(goods2);
                            JSONArray listArray = jsonObject.getJSONArray("list");
                            List<HomeNormalIcon> normalList = new ArrayList<HomeNormalIcon>();
                            for (int i = 0; i < listArray.length(); i++) {
                                HomeNormalIcon homeNormalIcon = gson.fromJson(listArray.getJSONObject(i).toString(), HomeNormalIcon.class);
                                normalList.add(homeNormalIcon);
                            }
                            HomeNormalIcon homeNormalIcon = new HomeNormalIcon();
                            homeNormalIcon.setName("我的小区");
                            homeNormalIcon.setTubiao("");
                            homeNormalIcon.setId("0");
                            normalList.add(0, homeNormalIcon);
//                            homeType3.setNormalIconList(normalList);
                            homeList.add(homeType1);
                            homeList.add(homeType2);
//                            homeList.add(homeType3);

                            HomeType homeType5 = new HomeType();
                            homeType5.setType(5);
                            homeType5.setNormalIconList(normalList);
                            homeList.add(homeType5);

                            mList.clear();
                            mList.addAll(homeList);
                        } else {

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<JSONObject> response) {
                hideLoading();
            }

            @Override
            public void onFinish(int what) {
                if (what == REQUEST_WHAT) {
                    mAdapter.notifyDataSetChanged();
                    mRefreshLayout.setRefreshing(false);
                    hideLoading();
                }

            }
        });


    }


    public void initDataView() {

        HomeType homeType1 = new HomeType();
        homeType1.setType(1);
        homeType1.setContent("236件已售 仅剩16小时");

        HomeType homeType2 = new HomeType();
        homeType2.setType(2);
        homeType2.setContent("明日10点开抢");

        List<HomeNormalIcon> normalIconList = new ArrayList<>();
        HomeNormalIcon homeNormalIcon1 = new HomeNormalIcon(R.drawable.icon_notice_red, "小区通知");
        HomeNormalIcon homeNormalIcon2 = new HomeNormalIcon(R.drawable.icon_house_property_orange, "房产");
        HomeNormalIcon homeNormalIcon3 = new HomeNormalIcon(R.drawable.icon_car_blue, "二手车");
        HomeNormalIcon homeNormalIcon4 = new HomeNormalIcon(R.drawable.icon_house_keep, "家政");
        HomeNormalIcon homeNormalIcon5 = new HomeNormalIcon(R.drawable.icon_education_blue, "教育");
        HomeNormalIcon homeNormalIcon6 = new HomeNormalIcon(R.drawable.icon_idle_green, "闲置物品");
        HomeNormalIcon homeNormalIcon7 = new HomeNormalIcon(R.drawable.icon_recruit_orang, "招聘");
        HomeNormalIcon homeNormalIcon8 = new HomeNormalIcon(R.drawable.icon_public_welfare, "公益");
        normalIconList.add(homeNormalIcon1);
        normalIconList.add(homeNormalIcon2);
        normalIconList.add(homeNormalIcon3);
        normalIconList.add(homeNormalIcon4);
        normalIconList.add(homeNormalIcon5);
        normalIconList.add(homeNormalIcon6);
        normalIconList.add(homeNormalIcon7);
        normalIconList.add(homeNormalIcon8);
        HomeType homeType3 = new HomeType();
        homeType3.setType(3);
        homeType3.setNormalIconList(normalIconList);
        mList.add(homeType3);

        List<HomePopularIcon> popularIconList = new ArrayList<>();
        for (int i = 0; i <= 7; i++) {
            HomePopularIcon homePopularIcon = new HomePopularIcon("", "二手车");
            popularIconList.add(homePopularIcon);
        }
        HomeType homeType4 = new HomeType();
        homeType4.setType(4);
        homeType4.setPopularIconList(popularIconList);
        mList.add(homeType4);
    }


    public void onHiddenChanged(boolean hidden) {
// TODO Auto-generated method stub
        super.onHiddenChanged(hidden);
        if (hidden) {// 不在最前端界面显示
            if (mPopupWindow != null && mPopupWindow.isShowing()) {
                mPopupWindow.dismiss();
                v_bg.setVisibility(View.GONE);
            }
        } else {// 重新显示到最前端中
            setStatusBarColor(R.color.colorSystemBlue);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
