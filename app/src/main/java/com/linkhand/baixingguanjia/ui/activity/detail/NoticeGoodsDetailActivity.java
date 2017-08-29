package com.linkhand.baixingguanjia.ui.activity.detail;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
import com.linkhand.baixingguanjia.customView.GoodsInfoSelectlTagListDialog;
import com.linkhand.baixingguanjia.customView.GradationScrollView;
import com.linkhand.baixingguanjia.customView.NoScrollListView;
import com.linkhand.baixingguanjia.entity.Goods;
import com.linkhand.baixingguanjia.entity.GoodsTag;
import com.linkhand.baixingguanjia.entity.GoodsTagsGuige;
import com.linkhand.baixingguanjia.entity.Picture;
import com.linkhand.baixingguanjia.entity.SerializableMap;
import com.linkhand.baixingguanjia.ui.activity.LoginActivity;
import com.linkhand.baixingguanjia.ui.activity.order.ConfirmOrderActivity;
import com.linkhand.baixingguanjia.ui.adapter.HotGoodsDetailAdapter;
import com.linkhand.baixingguanjia.utils.NetworkImageHolderView;
import com.linkhand.bxgj.lib.utils.DateTimeUtils;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;

import static com.linkhand.baixingguanjia.R.id.price;

public class HotGoodsDetailActivity extends BaseActivity implements GradationScrollView.ScrollViewListener, OnItemClickListener, ViewPager.OnPageChangeListener {

    private static final int REQUEST_WHAT = 0;
    private static final int HTTP_REQUEST = 1;
    @Bind(R.id.iv_good_detai_img)
    ConvenientBanner mBanner;
    @Bind(R.id.nlv_good_detial_imgs)
    NoScrollListView mNoScrollListView;
    @Bind(R.id.g_name)
    TextView mGoodsNameTV;
    @Bind(R.id.sv_container)
    ScrollView mScrollContainer;
    @Bind(R.id.format_layout)
    RelativeLayout mFormatLayout;
    @Bind(R.id.evaluate_layout)
    RelativeLayout mEvaluateLayout;
    @Bind(R.id.CountdownView)
    CountdownView mCountdownView;
    @Bind(R.id.kucun_tv)
    TextView mKucunTV; //库存
    @Bind(R.id.changjia)
    TextView mChangjiaTV;
    @Bind(R.id.chandi)
    TextView mChandiTV;
    @Bind(R.id.fuzeren)
    TextView mFuzerenTV;
    @Bind(R.id.youxiaoqi)
    TextView mYouxiaoqiTV;
    @Bind(price)
    TextView mPriceTV;  // 价格
    @Bind(R.id.num)
    TextView mSoldNumTV; //已经售出
    @Bind(R.id.yuanjia_price)
    TextView mYuanjiaPrice; //原价
    @Bind(R.id.collect_iv)
    ImageView mCollectIV;
    @Bind(R.id.collect_tv)
    TextView mCollectTV;
    @Bind(R.id.collect_layout)
    LinearLayout mCollectLayout;
    @Bind(R.id.guige_text_show)
    TextView mShowGuigeTV;


    private List<String> mPictureList;
    private HotGoodsDetailAdapter mAdapter;
    private List<Picture> mGoodsPicList;

    private List<GoodsTag> mGoodsTagList;

    private int buyNum = 1; //购买数量

    private long start_time = 1499392800;
    private long end_time = 1499574070 * 1000L;

    Goods mGoods;

    String goodsId;
    private RequestQueue mQueue = NoHttp.newRequestQueue();
    GoodsInfoSelectlTagListDialog mDialog2 = null;

    Map<Integer, GoodsTag.Guige> mSelectGuigeMap;  //选择的商品规格

    private boolean isCollect = false; //是否收藏

    private CommonPromptDialog mCommonDialog;

    List<GoodsTagsGuige> mGTGList; //商品规格"gui_price": [{"key": "1_5_9","price": "2.00","store_count": 2}
    String feilei = "";
    float mPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_goods_detail);
        ButterKnife.bind(this);
        initData();
        initView();
        httpGetDetiles();
        initDialog();
//        initListener();
    }


    private void initView() {

    }

    private void initData() {
        mGTGList = new ArrayList<>();
        mPictureList = new ArrayList<>();
        mGoodsPicList = new ArrayList<>();
        mGoodsTagList = new ArrayList<>();
        mSelectGuigeMap = new TreeMap<>();

        mAdapter = new HotGoodsDetailAdapter(this, R.layout.item_hot_good_detail_imgs, mGoodsPicList);
        mNoScrollListView.setAdapter(mAdapter);


        //开启倒计时

//        mCountdownView.start(end_time);
//        for (int time = 0; time < 1000; time++) {
//            mCountdownView.updateShow(time);
//        }

        initBanner();
//        getData();

    }

    private void initDialog() {
        mCommonDialog = new CommonPromptDialog(mContext, R.style.goods_info_dialog);
        mCommonDialog.setMessage(getStrgRes(R.string.notLogin));
        mCommonDialog.setOptionOneClickListener(getStrgRes(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mCommonDialog.dismiss();
            }
        });
        mCommonDialog.setOptionTwoClickListener(getStrgRes(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mCommonDialog.dismiss();
                go(LoginActivity.class);
            }
        });


    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras != null) {
            goodsId = extras.getString("goodsId");
        }
    }

    private void initListener() {
        mCountdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                showToast("倒计时结束");
            }
        });
        mFormatLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGuigeDialog();
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
    public void onScrollChanged(GradationScrollView scrollView, int x, int y, int oldx, int oldy) {

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


    private void getData() {
        mGoodsPicList.add(new Picture("https://img.alicdn.com/imgextra/i4/714288429/TB2dLhGaVXXXXbNXXXXXXXXXXXX-714288429.jpg"));
        mGoodsPicList.add(new Picture("https://img.alicdn.com/imgextra/i3/726966853/TB2vhJ6lXXXXXbJXXXXXXXXXXXX_!!726966853.jpg"));
        mGoodsPicList.add(new Picture("https://img.alicdn.com/imgextra/i4/2081314055/TB2FoTQbVXXXXbuXpXXXXXXXXXX-2081314055.png"));
        mAdapter.notifyDataSetChanged();


    }


    @OnClick({R.id.format_layout, R.id.evaluate_layout, R.id.collect_layout, R.id.tv_good_detail_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.format_layout:

                break;
            case R.id.evaluate_layout:
                break;
            case R.id.collect_layout:
                //收藏
                if (MyApplication.getUser() != null && MyApplication.getUser().getUserid() != null) {
                    isCollect = !isCollect;
                    changeCollectView();
                    httpCollect(false);
                } else {
                    mCommonDialog.show();
                }
                break;
            case R.id.tv_good_detail_buy:
                if (MyApplication.getUser() != null && MyApplication.getUser().getUserid() != null) {
                    if (mSelectGuigeMap.size() != mGoodsTagList.size()) {
                        showToast(R.string.selectGuigeToast);
                        return;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("goods",mGoods);
                    bundle.putSerializable("guigemap",new SerializableMap(mSelectGuigeMap));
                    bundle.putInt("goodnum",buyNum);
                    bundle.putFloat("price",mPrice);
                    go(ConfirmOrderActivity.class,bundle);
                } else {
                    mCommonDialog.show();
                }


                break;

        }
    }


    private void httpGetDetiles() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.PRODUCT_GOODS_DETILES, RequestMethod.POST);
        request.add("goods_id", goodsId);
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
                        if (resultCode.equals("200")) {
                            mGoods = gson.fromJson(jsonObject.getJSONObject("date").toString(), Goods.class);
                            String o = jsonObject.getJSONObject("date").get("image_url").toString();
                            List<String> picList = new ArrayList<String>();
                            if (!o.equals("null")) {
                                JSONArray picArray = jsonObject.getJSONObject("date").getJSONArray("image_url");
                                for (int i = 0; i < picArray.length(); i++) {
                                    picList.add(picArray.getString(i));
                                }
                            }
                            mGoods.setPicList(picList);
                            JSONArray tagsArray = jsonObject.getJSONObject("date").getJSONArray("guige1");
                            List<GoodsTag.Guige> guiges = new ArrayList<GoodsTag.Guige>();
                            GoodsTag goodsTag = new GoodsTag();
                            int flag = 0; //
                            String temp = "";
                            for (int i = 0; i < tagsArray.length(); i++) {
                                JSONObject tagsJson = tagsArray.getJSONObject(i);
                                if (!temp.equals(tagsJson.getString("name"))) {
                                    if (i != 0) {
                                        goodsTag.setGuiges(guiges);
                                        mGoodsTagList.add(goodsTag);
                                        guiges = new ArrayList<GoodsTag.Guige>();
                                        goodsTag = new GoodsTag();
                                    }

                                }
                                guiges.add(gson.fromJson(tagsJson.toString(), GoodsTag.Guige.class));
                                if (i == tagsArray.length() - 1) {
                                    goodsTag.setGuiges(guiges);
                                    mGoodsTagList.add(goodsTag);
                                }
                                temp = tagsJson.getString("name");
                            }
//                            Log.e("商品标签",mGoodsTagList.size()+"");
                            for (GoodsTag s : mGoodsTagList) {
                                System.out.println(s);
                                Log.e("商品标签", s.toString());
                            }
                            JSONArray guipriceArray = jsonObject.getJSONArray("gui_price");
                            for (int i = 0; i < guipriceArray.length(); i++) {
                                GoodsTagsGuige tagsGuige = gson.fromJson(guipriceArray.getJSONObject(i).toString(), GoodsTagsGuige.class);
                                mGTGList.add(tagsGuige);
                            }

                            JSONArray imageUrlArray = jsonObject.getJSONObject("date").getJSONArray("image_url");
                            for (int i = 0; i < imageUrlArray.length(); i++) {
                                String picUrl = imageUrlArray.getJSONObject(i).getString("image_url");
                                mPictureList.add(ConnectUrl.REQUESTURL_IMAGE + picUrl);
                            }
                            setViewData();
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
//                    initListener();
                    mAdapter.notifyDataSetChanged();
                    hideLoading();

                }

            }
        });


    }

    /**
     * 为布局设置数据
     */
    private void setViewData() {
        if (MyApplication.getUser() != null && MyApplication.getUser().getUserid() != null) {
            httpCollect(true);
        }
        mGoodsNameTV.setText(mGoods.getGoods_name());
        mChangjiaTV.setText(mGoods.getChangjia());
        mChandiTV.setText(mGoods.getChandi());
        mFuzerenTV.setText(mGoods.getFuzeren());
        mYouxiaoqiTV.setText(DateTimeUtils.formatdian(mGoods.getIn_time()) + "~" + DateTimeUtils.formatdian(mGoods.getOut_time()));
        mCountdownView.start(DateTimeUtils.compareTime(mGoods.getEnd_time() * 1000L));
        mPriceTV.setText(mGoods.getPrice() + "");
        mSoldNumTV.setText("已售" + mGoods.getBuy_num() + "");
        mYuanjiaPrice.setText(mGoods.getMarket_price() + "");
        mKucunTV.setText("库存" + mGoods.getStore_count() + "");
        initListener();
        mBanner.notifyDataSetChanged();
    }

    private void httpCollect(final boolean var) {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_COLLECT, RequestMethod.POST);
        request.add("goodsid", goodsId);
        request.add("userid", MyApplication.getUser().getUserid());
        request.add("goodstype", mGoods.getType());
        if (!var) {
            request.add("boolean", isCollect ? "t" : "f");
        }
        Log.d("收藏", request.getParamKeyValues().values().toString());

        mQueue.add(HTTP_REQUEST, request, new OnResponseListener<JSONObject>() {
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


    private void showGuigeDialog() {
        mDialog2 = new GoodsInfoSelectlTagListDialog(HotGoodsDetailActivity.this, R.style.goods_info_dialog, mGoodsTagList);
//        mDialog2.setTags(mGoodsTagList);

        mDialog2.setSelectFenlei(feilei);
//        for (GoodsTag.Guige v : mSelectGuigeMap.values()) {
//            feilei += " " + v.getItem();
//        }
        if (mSelectGuigeMap.size() == mGoodsTagList.size()) {
            changeTagsDialogView();
        } else {
            mDialog2.setKucun(mGoods.getStore_count());
            mDialog2.setPrice(mGoods.getPrice());
        }


//        buyNum = mGoods.getStore_count();

        mDialog2.setAddOnClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (buyNum < mGoods.getStore_count()) {
                    mDialog2.setSelectNum(++buyNum);
                }
            }
        });
        mDialog2.setMinOnClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (buyNum > 1 && buyNum <= mGoods.getStore_count()) {
                    mDialog2.setSelectNum(--buyNum);
                }

            }
        });

        mDialog2.setBuyNowOnClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int flag = 0;
                for (GoodsTag.Guige v : mSelectGuigeMap.values()) {
                    Log.e("规格：", "onClick: " + v.toString());
                    flag++;
                }
                if (TextUtils.isEmpty(feilei)) {
                    mShowGuigeTV.setText("选择商品规格");
                } else
                    mShowGuigeTV.setText("已选规格:" + feilei);

                mDialog2.dismiss();
            }
        });
        mDialog2.setItemClickListener(new GoodsInfoSelectlTagListDialog.OnItemClickListener() {
            @Override
            public void onItemClick(int position, int i) {
                if (i >= 0) {
                    mGoodsTagList.get(position).getGuiges().get(i).setFlag(true);
                    mSelectGuigeMap.put(position, mGoodsTagList.get(position).getGuiges().get(i));
                    setSeclectFalseOther(position, i);

                } else {
                    setSeclectFalse(position);
                    mSelectGuigeMap.remove(position);
                }

                feilei = "";
                for (GoodsTag.Guige v : mSelectGuigeMap.values()) {
                    feilei += " " + v.getItem();
                }

                mDialog2.setSelectFenlei(feilei);
                //选择标签改变对应的价钱和库存
                if (mSelectGuigeMap.size() == mGoodsTagList.size()) {
                    changeTagsDialogView();
                } else {
                    mDialog2.setKucun(mGoods.getStore_count());
                    mDialog2.setPrice(mGoods.getPrice());
                }


            }
        });
        Window window = mDialog2.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.mypopwindow_anim_style);
        window.setAttributes(lp);

        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = window.getAttributes();
        p.height = (int) (d.getHeight() * 0.8);
        p.width = d.getWidth();
        window.setAttributes(p);
        mDialog2.show();
    }

    /***
     *    //选择标签改变对应的价钱和库存
     */
    private void changeTagsDialogView() {
        String tagsIdKey[] = mGTGList.get(0).getKey().split("_");
        String keyJoin = "";
        for (GoodsTag.Guige v : mSelectGuigeMap.values()) {
            keyJoin += v.getId() + "_";
        }
        keyJoin = keyJoin.substring(0, keyJoin.length() - 1);
        for (GoodsTagsGuige g : mGTGList) {
            if (g.getKey().contains(keyJoin)) {
                mDialog2.setKucun(g.getStore_count());
                mDialog2.setPrice(g.getPrice());
                mPrice = g.getPrice();
                break;
            }
        }

    }

    private void setSeclectFalseOther(int position, int i) {
        for (int j = 0; j < mGoodsTagList.get(position).getGuiges().size(); j++) {
            if (i != j) {
                mGoodsTagList.get(position).getGuiges().get(j).setFlag(false);
            }

        }
    }

    //给商品规格全部设置为未选中
    private void setSeclectFalse(int position) {
        for (int j = 0; j < mGoodsTagList.get(position).getGuiges().size(); j++) {
            mGoodsTagList.get(position).getGuiges().get(j).setFlag(false);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mQueue.cancelAll();
    }
}
