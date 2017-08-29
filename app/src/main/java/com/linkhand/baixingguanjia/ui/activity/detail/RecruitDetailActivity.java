package com.linkhand.baixingguanjia.ui.activity.detail;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.base.MyApplication;
import com.linkhand.baixingguanjia.customView.CommonPromptDialog;
import com.linkhand.baixingguanjia.entity.Education;
import com.linkhand.baixingguanjia.entity.Picture;
import com.linkhand.baixingguanjia.ui.activity.LoginActivity;
import com.linkhand.baixingguanjia.utils.NetworkImageHolderView;
import com.linkhand.bxgj.lib.utils.DateTimeUtils;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EducationDetailActivity extends BaseActivity implements OnItemClickListener, ViewPager.OnPageChangeListener {
    private static final int HTTP_REQUEST = 0;
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
    @Bind(R.id.rmb)
    TextView mRmbTV;

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
    @Bind(R.id.education_object)
    TextView mObjectTV;
    @Bind(R.id.education_layout)
    LinearLayout mEducationLayout;
    @Bind(R.id.company)
    TextView mCompanyTV;
    @Bind(R.id.company_layout)
    RelativeLayout mCompanyLayout;


    private List<String> mPictureList;
    private List<Picture> mGoodsPicList;
    private Education mEducation;
    private boolean isCollect; //是否收藏
    private RequestQueue mRequestQueue = NoHttp.newRequestQueue();
    private CommonPromptDialog mDialog;
    private Dialog mOkDialog;

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
            mEducation = (Education) extras.getSerializable("education");
            if (adjustList(mEducation.getImage_url())) {
                mPictureList = mEducation.getImage_url();
            } else {
                mPictureList = new ArrayList<>();
            }
        }
    }

    private void initView() {
        mEducationLayout.setVisibility(View.VISIBLE);
        mObjectTV.setText(mEducation.getObject());

        mHouseNameTV.setText(mEducation.getTitle());
        if (mEducation.getCategory().equals("4")) {
            mStoreTypeTV.setText(R.string.store);
        } else if (mEducation.getCategory().equals("5")) {
            mStoreTypeTV.setText(R.string.geren);
        }
        mReleaseTimeTV.setText(DateTimeUtils.formatdian(mEducation.getAdd_time()));// 1出租2出售【默认2】
        mContentTV.setText(mEducation.getContent());
        mPriceTV.setVisibility(View.GONE);
        mRmbTV.setVisibility(View.GONE);
        mPhoneTV.setText("******");
        mFuwuTypeTV.setText(R.string.housekeepingType);
        if (mEducation.getCategory().equals("4")) {
            mStoreTypeTV.setText(R.string.store);
            mCompanyLayout.setVisibility(View.VISIBLE);
            mCompanyTV.setText(mEducation.getCompany());
        } else if (mEducation.getCategory().equals("5")) {
            mStoreTypeTV.setText(R.string.geren);
            mCompanyLayout.setVisibility(View.VISIBLE);
        }
        mChakanTV.setVisibility(View.GONE);
        mAddressTV.setText(mEducation.getCounty() + mEducation.getVillage());
        mContactTV.setText(mEducation.getCreator());

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
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOkDialog.dismiss();
            }
        });

    }

    private void initListener() {

    }


    private void initBanner() {
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
        request.add("goodsid", mEducation.getFamily_id());
        request.add("userid", MyApplication.getUser().getUserid());
        request.add("goodstype", mEducation.getGoods_type());
        if (!var) {
            request.add("boolean", isCollect ? "t" : "f");
        }
        Log.d("收藏", request.getParamKeyValues().values().toString());

        mRequestQueue.add(HTTP_REQUEST, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {

            }

            //  code：206已收藏 207未收藏
            //  code1：200收藏成功 204收藏失败 205收藏数量达到上限 208取消收藏成功 209取消收藏失败
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == HTTP_REQUEST) {// 根据what判断是哪个请求的返回，这样就可以用一个OnResponseListener来接受多个请求的结果。
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
//                Bundle bundle = new Bundle();
//                bundle.putString("type", "car");
//                bundle.putSerializable("car", mCar);
//                go(AddUserInfoActivity.class, bundle);
                mOkDialog.show();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRequestQueue.cancelAll();
    }
}
