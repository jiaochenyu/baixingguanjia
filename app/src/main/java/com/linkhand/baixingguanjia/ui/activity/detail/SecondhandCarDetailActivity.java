package com.linkhand.baixingguanjia.ui.activity.detail;

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
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.base.MyApplication;
import com.linkhand.baixingguanjia.customView.CommonPromptDialog;
import com.linkhand.baixingguanjia.customView.NoScrollListView;
import com.linkhand.baixingguanjia.entity.Car;
import com.linkhand.baixingguanjia.entity.Picture;
import com.linkhand.baixingguanjia.ui.activity.LoginActivity;
import com.linkhand.baixingguanjia.ui.activity.car.AddUserInfoActivity;
import com.linkhand.baixingguanjia.ui.adapter.SecondhandCarDetailAdapter;
import com.linkhand.baixingguanjia.utils.NetworkImageHolderView;
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

/**
 * 停用
 */
public class SecondhandCarDetailActivity extends BaseActivity implements OnItemClickListener, ViewPager.OnPageChangeListener {
    private static final int HTTP_REQUEST = 0;

    @Bind(R.id.iv_car_detai_img)
    ConvenientBanner mBanner;
    @Bind(R.id.nlv_car_detial_imgs)
    NoScrollListView mListView;

    SecondhandCarDetailAdapter mAdapter;
    @Bind(R.id.tv_car_detail_buy)
    TextView mContactTV;
    @Bind(R.id.pic_position)
    TextView mPicPositionTV; //
    @Bind(R.id.c_name)
    TextView mCarNameTV;
    @Bind(R.id.release_time)
    TextView mReleaseTimeTV;
    @Bind(R.id.share_iv)
    ImageView mShareIV;
    @Bind(R.id.price)
    TextView mPriceTV;
    @Bind(R.id.location)
    TextView mLocationTV;
    @Bind(R.id.shangpai_time)
    TextView mShangpaiTimeTV;
    @Bind(R.id.iv_good_detai_back)
    ImageView mBack;
    @Bind(R.id.iv_good_detai_collect_select)
    ImageView mCollectIV;
    @Bind(R.id.collect_tv)
    TextView mCollectTV;
    @Bind(R.id.mileage)
    TextView mMileageTV; //公里数
    @Bind(R.id.ll_good_detail_collect)
    LinearLayout mCollectLL;
    private List<String> mPictureList;
    private List<Picture> mGoodsPicList;
    private Car mCar;
    private boolean isCollect; //是否收藏
    private RequestQueue mRequestQueue = NoHttp.newRequestQueue();
    private CommonPromptDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);
        ButterKnife.bind(this);
        initView();
        initData();
        initDialog();
    }


    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras != null) {
            mCar = (Car) extras.getSerializable("car");
            mPictureList = new ArrayList<>();
            for (int i = 0; i < mCar.getPic().size(); i++) {
                mPictureList.add(mCar.getPic().get(i).getUrl());
            }
        }
    }

    private void initView() {
        mPicPositionTV.setText(1 + "/" + mPictureList.size());
        mCarNameTV.setText(mCar.getTitle());
        mReleaseTimeTV.setText(mCar.getTime()+"发布");
        mLocationTV.setText(mCar.getCounty());
        mShangpaiTimeTV.setText(mCar.getCreator()+"年");
        mMileageTV.setText(mCar.getDistance() + "万公里");
        mPriceTV.setText(mCar.getPrice() + "万元");

    }

    private void initData() {
        mGoodsPicList = new ArrayList<>();
        mAdapter = new SecondhandCarDetailAdapter(this, R.layout.item_hot_good_detail_imgs, mGoodsPicList);
        mListView.setAdapter(mAdapter);
        initBanner();
        if (MyApplication.getUser() != null && MyApplication.getUser().getUserid() != null) {
            httpCollect(true);
        }
//        getData();
    }

    private void initDialog() {
        mDialog = new CommonPromptDialog(mContext,R.style.goods_info_dialog);
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
        mPicPositionTV.setText(position%mPictureList.size()+1 + "/" + mPictureList.size());
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
        request.add("goodsid", mCar.getCarid());
        request.add("userid", MyApplication.getUser().getUserid());
        request.add("goodstype", mCar.getGoods_type());
        if (!var) {
            request.add("boolean", isCollect ? "t" : "f");
        }
        Log.d("收藏",request.getParamKeyValues().values().toString());

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
                        if (jsonObject.getString("code").equals("206") ) {
                            //已收藏
                            isCollect = true;

                            if (jsonObject.getString("code1").equals("208") ) {
                                isCollect = false;
                            }
                        } else if (jsonObject.getString("code") .equals("207") ) {
                            //未收藏
                            isCollect = false;

                            if (jsonObject.getString("code1").equals("200")  ) {
                                isCollect = true;
                            }
                        }
                        if (var){
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


    private void getData() {
        mGoodsPicList.add(new Picture("https://img.alicdn.com/imgextra/i4/714288429/TB2dLhGaVXXXXbNXXXXXXXXXXXX-714288429.jpg"));
        mGoodsPicList.add(new Picture("https://img.alicdn.com/imgextra/i3/726966853/TB2vhJ6lXXXXXbJXXXXXXXXXXXX_!!726966853.jpg"));
        mGoodsPicList.add(new Picture("https://img.alicdn.com/imgextra/i4/2081314055/TB2FoTQbVXXXXbuXpXXXXXXXXXX-2081314055.png"));
        mAdapter.notifyDataSetChanged();

        mPictureList.add("");
        mPictureList.add("");
        mPictureList.add("");
        mPictureList.add("");
        mBanner.notifyDataSetChanged();


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
                Bundle bundle = new Bundle();
                bundle.putString("type","car");
                bundle.putSerializable("car",mCar);
                go(AddUserInfoActivity.class,bundle);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRequestQueue.cancelAll();
    }
}
