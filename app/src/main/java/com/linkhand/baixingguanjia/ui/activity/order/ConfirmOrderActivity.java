package com.linkhand.baixingguanjia.ui.activity.order;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.base.BaseAppManager;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.base.MyApplication;
import com.linkhand.baixingguanjia.entity.Address;
import com.linkhand.baixingguanjia.entity.EventFlag;
import com.linkhand.baixingguanjia.entity.Goods;
import com.linkhand.baixingguanjia.entity.GoodsTag;
import com.linkhand.baixingguanjia.entity.SerializableMap;
import com.linkhand.baixingguanjia.listener.PayResult;
import com.linkhand.baixingguanjia.ui.activity.SelectAddressActivity;
import com.linkhand.baixingguanjia.ui.activity.detail.HotGoodsDetailActivity;
import com.linkhand.baixingguanjia.ui.activity.my.MyOrderActivity;
import com.linkhand.baixingguanjia.utils.ImageUtils;
import com.linkhand.bxgj.lib.utils.DecimalUtils;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.linkhand.baixingguanjia.R.id.submit;

public class ConfirmOrderActivity extends BaseActivity {
    private static final int REQUEST_WHAT = 0;
    private static final int REQUEST_WHAT_ALIPAY = 1;
    private static final int SDK_PAY_FLAG = 2;

    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.header_layout)
    RelativeLayout mHeaderLayout;
    private RequestQueue mQueue = NoHttp.newRequestQueue();

    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.address_ry)
    LinearLayout mAddressLayout;
    @Bind(submit)
    TextView mSubmit;
    int payNum = 1;
    Goods mGoods;
    Map<Integer, GoodsTag.Guige> mGuigeMap;
    @Bind(R.id.address)
    TextView mAddressTV;
    @Bind(R.id.image_good)
    ImageView mImageGoodIV;
    @Bind(R.id.good_name)
    TextView mGoodNameTV;
    @Bind(R.id.type_tv)
    TextView mContentTV;
    @Bind(R.id.price1)
    TextView mPriceTV;
    @Bind(R.id.less)
    TextView mLessTV;
    @Bind(R.id.more)
    TextView mMoreTV;
    @Bind(R.id.goods_num)
    TextView mGoodsNumTV;
    @Bind(R.id.num)
    TextView mNumTV;
    @Bind(R.id.price)
    TextView mTotalPriceTV;

    Address mAddress;
    int storeNum; //库存
    String attr_id = "";
    float price;
    float totalPrcie;
    String eventId = "" ;
    Dialog mPayDialog;
    String fenlei = "";
    private String orderSnStr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        ButterKnife.bind(this);
        initView();
        initData();
        httpDefautAddress();
        initListener();
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras != null) {
            payNum = extras.getInt("goodnum", 1);
            mGoods = (Goods) extras.getSerializable("goods");
            mGuigeMap = ((SerializableMap) extras.getSerializable("guigemap")).getGuigeMap();
            storeNum = extras.getInt("kuncun", 1);
            price = extras.getFloat("price", 0);
            eventId = extras.getString("eventId");
        }
    }

    private void initView() {
        mTitle.setTextColor(getResources().getColor(R.color.blackText));
        mBack.setImageDrawable(getResources().getDrawable(R.mipmap.icon_back_black));
        mHeaderLayout.setBackground(getResources().getDrawable(R.color.colorWhite));
        mTitle.setText(R.string.confirmOrderTitle);
        ImageUtils.setDefaultNormalImage(mImageGoodIV, ConnectUrl.REQUESTURL_IMAGE + mGoods.getOriginal_img(), R.drawable.default_pic_show);
        mGoodNameTV.setText(mGoods.getGoods_name());

        for (GoodsTag.Guige item : mGuigeMap.values()) {
            fenlei += item.getItem();
            attr_id += item.getItem_id() + "_";
        }
        attr_id = attr_id.substring(0, attr_id.length() - 1);
        mContentTV.setText("规格：" + fenlei);
        mPriceTV.setText("¥ " + DecimalUtils.decimalFormat(price));
        mGoodsNumTV.setText("x" + payNum + "");
        mNumTV.setText(payNum + "");
        totalPrcie = payNum * price;
        mTotalPriceTV.setText(DecimalUtils.decimalFormat(totalPrcie));

    }

    private void initData() {

    }

    private void initListener() {

    }


    private void httpDefautAddress() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_PROUCT_GET_DEF_ADDRESS, RequestMethod.POST);
        request.add("user_id", MyApplication.getUser().getUserid());
//        request.add("is_moren", 1);
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
                            mAddress = gson.fromJson(jsonObject.get("data").toString(), Address.class);

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
                    if (mAddress != null) {
                        mAddressTV.setText("提货地址： " + mAddress.getPickup_name() + mAddress.getPickup_address());
                    }
                    hideLoading();
                }

            }
        });


    }

    /**
     * 下单
     */
    private void httpOrder() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_PROUCT_SUBMIT_ORDER, RequestMethod.POST);
        request.add("goods_id", mGoods.getGoods_id());
        request.add("userid", MyApplication.getUser().getUserid());
        request.add("huodongid", eventId);

        request.add("data[province]", mAddress.getShengid());
        request.add("data[city]", mAddress.getShiid());
        request.add("data[district]", mAddress.getQuid());
        request.add("data[twon]", mAddress.getXiaoquid());
        request.add("data[goods_price]", DecimalUtils.decimalFormat(totalPrcie));

        request.add("data1[goods_num]", payNum);
        request.add("data1[spec_key]", attr_id);
        request.add("data1[spec_key_name]", fenlei);
        Log.d("attr_id", "attr_id: " + attr_id);
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
                        String info = jsonObject.getString("info");
                        if (resultCode.equals("200")) {
                            JSONObject dataJson = jsonObject.getJSONObject("data");
//                            showToast("下单成功");
                            orderSnStr = dataJson.getString("order_sn");
                            showDialog();
                        } else if (resultCode.equals("202")) {
                            showToast(info);
                        } else if (resultCode.equals("201")) {
                            showToast(info);
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
                hideLoading();
            }
        });
    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Subscribe
    public void onEvent(EventFlag eventFlag) {
        if (eventFlag.getFlag().equals("selectedAddress")) {
            mAddress = (Address) eventFlag.getObject();
            mAddressTV.setText("提货地址： " +mAddress.getProvince_name()+mAddress.getCity_name()+mAddress.getDistrict_name()+mAddress.getXiaoquname()+mAddress.getStreet());
        }
    }

    private void showDialog() {
        mPayDialog = new Dialog(ConfirmOrderActivity.this, R.style.Dialog);
        mPayDialog.setContentView(R.layout.dialog_select_payway);
        mPayDialog.setCancelable(false);
        final int[] payway = {1}; //1支付宝, 2 微信
        RadioGroup radioGroup = (RadioGroup) mPayDialog.findViewById(R.id.pay_radiogroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.alipayPay:
                        payway[0] = 1;
                        break;
                    case R.id.weChatPay:
                        payway[0] = 2;
                        break;
                }
            }
        });

        TextView submit = (TextView) mPayDialog.findViewById(R.id.submit_pay);
        String p = DecimalUtils.decimalFormat(totalPrcie);
        String priceHtml = "确认支付" + "<big>¥" + p + "</big>";
        submit.setText(Html.fromHtml(priceHtml));

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //弹出框点击事件

                if (payway[0] == 1) {
                    httpAlipay();
                } else {

                }
                if (mPayDialog != null && mPayDialog.isShowing()) {
                    mPayDialog.dismiss();
//                    contentET.getText().toString();
                }

            }
        });
        mPayDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    Toast.makeText(ConfirmOrderActivity.this, "您取消了支付，请到订单中支付", Toast.LENGTH_SHORT).show();
//                    BaseAppManager.getInstance().finishActivity(HotGoodsDetailActivity.class);
                    goAndFinish(MyOrderActivity.class);
                }
                return false;
            }
        });
        mPayDialog.show();
    }

    private void httpAlipay() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_PROUCT_ALIPAY, RequestMethod.POST);
        request.add("order_sn", orderSnStr);
        request.add("order_amount", 0.01);
//        request.add("order_amount", DecimalUtils.decimalFormat(totalPrcie));
        mQueue.add(REQUEST_WHAT_ALIPAY, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
                showLoading(false);
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == REQUEST_WHAT_ALIPAY) {
                    String resultCode = null;

                    try {
                        JSONObject jsonObject = response.get();
                        resultCode = jsonObject.getString("code");
                        if (resultCode.equals("200")) {
                            String orderInfo = jsonObject.getString("data");
                            goAlipay(orderInfo);
                        } else {
                            showToast("调用支付宝失败");
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
                hideLoading();

            }
        });
    }

    private void goAlipay(final String orderInfo) {

        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(ConfirmOrderActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.d("msp", result.toString());
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
//                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    Log.d("alipay", resultInfo);
                    Log.d("alipaystatus", resultStatus);
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(ConfirmOrderActivity.this, "支付成功，请到订单中查看", Toast.LENGTH_SHORT).show();
                        BaseAppManager.getInstance().finishActivity(HotGoodsDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("isSuccess", true);
                        goAndFinish(MyOrderActivity.class, bundle);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(ConfirmOrderActivity.this, "您取消了支付，请到订单中支付", Toast.LENGTH_SHORT).show();
                        BaseAppManager.getInstance().finishActivity(HotGoodsDetailActivity.class);
                        goAndFinish(MyOrderActivity.class);
                    }
                    break;
                }
                default:
                    break;
            }
        }

    };

    @OnClick({R.id.back, R.id.address_ry, submit, R.id.less, R.id.more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.address_ry:
                if (mAddress == null) {
                    go(SelectAddressActivity.class);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("address", mAddress);
                    go(SelectAddressActivity.class, bundle);
                }
                break;
            case R.id.less:
                if (payNum > 1 && payNum <= storeNum) {
                    payNum--;
                    mNumTV.setText(payNum + "");
                    mGoodsNumTV.setText("x" + payNum + "");
                    totalPrcie = payNum * price;
                    mTotalPriceTV.setText(DecimalUtils.decimalFormat(totalPrcie));
                }
                break;
            case R.id.more:
                if (payNum < storeNum) {
                    payNum++;
                    mNumTV.setText(payNum + "");
                    mGoodsNumTV.setText("x" + payNum + "");
                    totalPrcie = payNum * price;
                    mTotalPriceTV.setText(DecimalUtils.decimalFormat(totalPrcie));
                }
                break;
            case submit:
                httpOrder();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mQueue.cancelAll();
    }
}
