package com.linkhand.baixingguanjia.ui.activity.detail;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.base.MyApplication;
import com.linkhand.baixingguanjia.customView.CommonPromptDialog;
import com.linkhand.baixingguanjia.entity.EventFlag;
import com.linkhand.baixingguanjia.entity.IdleGoods;
import com.linkhand.baixingguanjia.entity.Picture;
import com.linkhand.baixingguanjia.ui.activity.LoginActivity;
import com.linkhand.baixingguanjia.ui.activity.my.MyAppointmentActivity;
import com.linkhand.baixingguanjia.utils.NetworkImageHolderView;
import com.linkhand.bxgj.lib.utils.DateTimeUtils;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IdleGoodsDetailActivity extends BaseActivity implements OnItemClickListener, ViewPager.OnPageChangeListener {
    //    private static final int HTTP_REQUEST = 0;
//    private static final int REQUEST_WHAT = 1;
    private static final int HTTP_REQUEST_IS_AAP = 2; //是否预约
    private static final int HTTP_REQUEST_COLLECT = 3; //是否收藏
    private static final int REQUEST_WHAT_DETILES = 4; // 详情页
    private static final int HTTP_REQUEST_APP = 5;//预约
    @Bind(R.id.iv_detai_img)
    ConvenientBanner mBanner;
    @Bind(R.id.tv_car_detail_buy)
    TextView mGoTV;
    @Bind(R.id.name)
    TextView mHouseNameTV;
    @Bind(R.id.creatortime)
    TextView mReleaseTimeTV;
    @Bind(R.id.share_iv)
    ImageView mShareIV;
    @Bind(R.id.price)
    TextView mPriceTV;


    @Bind(R.id.iv_good_detai_back)
    ImageView mBack;
    @Bind(R.id.iv_good_detai_collect_select)
    ImageView mCollectIV;
    @Bind(R.id.collect_tv)
    TextView mCollectTV;
    @Bind(R.id.ll_good_detail_collect)
    LinearLayout mCollectLL;
    @Bind(R.id.store_type)
    TextView mStoreTypeTV; //商家 /用户
    @Bind(R.id.content)
    TextView mContentTV; //描述
    @Bind(R.id.chakanquanbu)
    TextView mChakanTV; //查看全部
    @Bind(R.id.contact_people)
    TextView mContactTV;
    @Bind(R.id.address)
    TextView mAddressTV;
    @Bind(R.id.phone)
    TextView mPhoneTV;
    @Bind(R.id.fuwu_type)
    TextView mFuwuTypeTV; //服务类型
    @Bind(R.id.buy_price)
    TextView mBuyPriceTV;
    @Bind(R.id.idle_layout)
    LinearLayout mIdleLayout;


    private List<String> mPictureList;
    private List<Picture> mGoodsPicList;
    private IdleGoods mIdleGoods;
    private boolean isCollect; //是否收藏
    private RequestQueue mRequestQueue = NoHttp.newRequestQueue();
    private CommonPromptDialog mDialog;
    private Dialog mOkDialog;
    private String goods_type = "";
    private String goodsid = "";
    private RequestQueue mQueue = NoHttp.newRequestQueue();
    private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_detail);
        ButterKnife.bind(this);
        initView();
        initData();
        initBanner();
        initDialog();
        initListener();
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras != null) {
            mIdleGoods = (IdleGoods) extras.getSerializable("idleGoods");
            if (mIdleGoods != null) {
                if (adjustList(mIdleGoods.getImage_url())) {
                    mPictureList = mIdleGoods.getImage_url();
                } else {
                    mPictureList = new ArrayList<>();
                }
                position = extras.getInt("position", -1);
            } else {
                goods_type = extras.getString("goods_type", "");
                goodsid = extras.getString("goodsid", "");
            }

        }
    }

    private void initView() {
        mIdleLayout.setVisibility(View.VISIBLE);
        mChakanTV.setVisibility(View.GONE);
        if (mIdleGoods == null) {
            httpGetDetiles();
            if (MyApplication.getUser() != null && MyApplication.getUser().getUserid() != null) {
                httpIsAppoinment();
            }
        } else {
            setViewData();
        }

    }

    private void initData() {
        mGoodsPicList = new ArrayList<>();
        if (MyApplication.getUser() != null && MyApplication.getUser().getUserid() != null) {
            httpCollect(true);
        }
    }

    private void initDialog() {
        mDialog = new CommonPromptDialog(mContext, R.style.goods_info_dialog);
        mDialog.setMessage(getStrgRes(R.string.notLogin));
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
                go(LoginActivity.class);
            }
        });

        mOkDialog = new Dialog(this, R.style.goods_info_dialog);
        mOkDialog.setContentView(R.layout.dialog_appointment_success);
        TextView okBtn = (TextView) mOkDialog.findViewById(R.id.btn_ok);
        TextView noBtn = (TextView) mOkDialog.findViewById(R.id.btn_no);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOkDialog.dismiss();
                goAndFinish(MyAppointmentActivity.class);
            }
        });
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1下线2不下线
                if (mIdleGoods.getOffline().equals("1")) {
                    if (position != -1) {
                        EventBus.getDefault().post(new EventFlag("offlineIdle", position));
                    }
                }
                finish();
            }
        });

    }

    private void initListener() {

    }


    private void initBanner() {
        if (!adjustList(mPictureList)) {
            mPictureList = new ArrayList<>();
        }
        mBanner.setPages(new CBViewHolderCreator() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, mPictureList)
                .setPageIndicator(new int[]{R.drawable.circle_grey, R.drawable.circle_blue})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnPageChangeListener(this).setOnItemClickListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onItemClick(int position) {

    }

    /**
     * 收藏接口
     *
     * @param var 判断是第获取 true:是否收藏  false 取消/收藏
     */
    private void httpCollect(final boolean var) {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_COLLECT, RequestMethod.POST);
        if (mIdleGoods == null) {
            request.add("goodsid", goodsid);
            request.add("goodstype", goods_type);
        } else {
            request.add("goodsid", mIdleGoods.getIdleid());
            request.add("goodstype", mIdleGoods.getGoods_type());
        }
        request.add("userid", MyApplication.getUser().getUserid());
        if (!var) {
            request.add("boolean", isCollect ? "t" : "f");
        }
        Log.d("收藏", request.getParamKeyValues().values().toString());

        mRequestQueue.add(HTTP_REQUEST_COLLECT, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {

            }

            //  code：206已收藏 207未收藏
            //  code1：200收藏成功 204收藏失败 205收藏数量达到上限 208取消收藏成功 209取消收藏失败
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == HTTP_REQUEST_COLLECT) {// 根据what判断是哪个请求的返回，这样就可以用一个OnResponseListener来接受多个请求的结果。
                    int responseCode = response.getHeaders().getResponseCode();// 服务器响应码。
                    JSONObject jsonObject = response.get();
                    try {
                        if (jsonObject.getString("code").equals("206")) {
                            //已收藏
                            isCollect = true;

                            if (jsonObject.getString("code1").equals("208")) {
                                isCollect = false;
                            }
                        } else if (jsonObject.getString("code").equals("207")) {
                            //未收藏
                            isCollect = false;

                            if (jsonObject.getString("code1").equals("200")) {
                                isCollect = true;
                            }
                        }
                        if (var) {
                            changeCollectView();
                        }

//
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

            }
        });
    }

    /**
     * 改变收藏样式
     */
    private void changeCollectView() {
        if (isCollect) {
            mCollectIV.setImageDrawable(getResources().getDrawable(R.drawable.shoucang));
            mCollectTV.setText(R.string.alreadyCollect);
        } else {
            mCollectIV.setImageDrawable(getResources().getDrawable(R.drawable.icon_like_df));
            mCollectTV.setText(R.string.isCollect);
        }
    }


    private void httpGetDetiles() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_SERVICE_DTEAIL, RequestMethod.POST);
        request.add("goodsid", goodsid);
        request.add("goods_type", goods_type);
        mQueue.add(REQUEST_WHAT_DETILES, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
                showLoading(false);
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == REQUEST_WHAT_DETILES) {
                    String resultCode = null;
                    Log.e("tag", response.get().toString());
                    try {
                        Gson gson = new Gson();
                        JSONObject jsonObject = response.get();
                        resultCode = jsonObject.getString("code");
                        if (resultCode.equals("200")) {
                            mIdleGoods = gson.fromJson(jsonObject.getJSONObject("data").toString(), IdleGoods.class);
                        }
                        setViewData();
                        mPictureList = mIdleGoods.getImage_url();
                        if (adjustList(mPictureList)) {
                            mBanner.notifyDataSetChanged();
                        }
                        if (MyApplication.getUser() != null && MyApplication.getUser().getUserid() != null) {
                            httpIsAppoinment();
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
                if (what == REQUEST_WHAT_DETILES) {
//                    initListener();

                    hideLoading();

                }

            }
        });

    }

    private void setViewData() {
        mBuyPriceTV.setText(mIdleGoods.getBuy_price() + "元");
        mHouseNameTV.setText(mIdleGoods.getTitle());
        if (mIdleGoods.getCategory().equals("4")) {
            mStoreTypeTV.setText(R.string.store);
        } else if (mIdleGoods.getCategory().equals("5")) {
            mStoreTypeTV.setText(R.string.geren);
        }
        mReleaseTimeTV.setText(DateTimeUtils.formatdian(mIdleGoods.getAdd_time()));// 1出租2出售【默认2】
        mContentTV.setText(mIdleGoods.getContent());
        mPriceTV.setText(mIdleGoods.getSales_price() + "元");
        mPhoneTV.setText("******");
        mFuwuTypeTV.setText(R.string.idlegoodsType);
        if (mIdleGoods.getCategory().equals("4")) {
            mStoreTypeTV.setText(R.string.store);
        } else if (mIdleGoods.getCategory().equals("5")) {
            mStoreTypeTV.setText(R.string.geren);
        }

        mAddressTV.setText(mIdleGoods.getAddress());
        mContactTV.setText(mIdleGoods.getContact());
    }


    /**
     * 是否预约
     */
    private void httpIsAppoinment() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_IS_APPOINTMENT, RequestMethod.POST);
//        if (mEducation == null) {
//            request.add("goodsid", goodsid);
//            request.add("goodstype", goods_type);
//        } else { }
        request.add("goodsid", mIdleGoods.getIdleid());
        request.add("goodstype", mIdleGoods.getGoods_type());
        request.add("userid", MyApplication.getUser().getUserid());

        Log.d("是否预约", request.getParamKeyValues().values().toString());

        mRequestQueue.add(HTTP_REQUEST_IS_AAP, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {

            }

            //  code：206已收藏 207未收藏
            //  code1：200收藏成功 204收藏失败 205收藏数量达到上限 208取消收藏成功 209取消收藏失败
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == HTTP_REQUEST_IS_AAP) {// 根据what判断是哪个请求的返回，这样就可以用一个OnResponseListener来接受多个请求的结果。
                    int responseCode = response.getHeaders().getResponseCode();// 服务器响应码。
                    JSONObject jsonObject = response.get();
                    try {
                        if (jsonObject.getString("code").equals("200")) {
                            //已预约
                            mPhoneTV.setText(mIdleGoods.getPhone());
                        } else if (jsonObject.getString("code").equals("216")) {
                            mPhoneTV.setText("******");
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

            }
        });
    }

    /**
     * 预约
     */
    private void httpAppointment() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_SERVICE_APPOINTMENT, RequestMethod.POST);
        request.add("goodsid", mIdleGoods.getIdleid());
        request.add("goodstype", mIdleGoods.getGoods_type());
        request.add("offline", mIdleGoods.getOffline());
        request.add("userid", MyApplication.getUser().getUserid());
        Log.d("预约", request.getParamKeyValues().values().toString());

        mRequestQueue.add(HTTP_REQUEST_APP, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
                showLoading();
            }

            //  code：206已收藏 207未收藏
            //  code1：200收藏成功 204收藏失败 205收藏数量达到上限 208取消收藏成功 209取消收藏失败
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == HTTP_REQUEST_APP) {// 根据what判断是哪个请求的返回，这样就可以用一个OnResponseListener来接受多个请求的结果。
                    int responseCode = response.getHeaders().getResponseCode();// 服务器响应码。
                    JSONObject jsonObject = response.get();
                    try {
                        if (jsonObject.getString("code").equals("200")) {
                            mOkDialog.show();
                        } else if (jsonObject.getString("code").equals("212")) {
                            showToast(R.string.toast_not_appoinment_yourself);
                        } else if (jsonObject.getString("code").equals("213")) {
                            showToast(R.string.yuyueFail);
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


    @OnClick({R.id.share_iv, R.id.ll_good_detail_collect, R.id.iv_good_detai_back, R.id.tv_car_detail_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.share_iv:

                break;
            case R.id.ll_good_detail_collect:
                //收藏
                if (MyApplication.getUser() != null && MyApplication.getUser().getUserid() != null) {
                    isCollect = !isCollect;
                    changeCollectView();
                    httpCollect(false);
                } else {
                    mDialog.show();
                }

                break;
            case R.id.iv_good_detai_back:
                finish();
                break;
            case R.id.tv_car_detail_buy:
                if (mIdleGoods != null) {
                    httpAppointment();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRequestQueue.cancelAll();
    }
}
