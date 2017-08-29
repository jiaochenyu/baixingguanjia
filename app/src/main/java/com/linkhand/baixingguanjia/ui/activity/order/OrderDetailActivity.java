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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.base.MyApplication;
import com.linkhand.baixingguanjia.entity.EventFlag;
import com.linkhand.baixingguanjia.entity.Order;
import com.linkhand.baixingguanjia.listener.PayResult;
import com.linkhand.baixingguanjia.utils.ImageUtils;
import com.linkhand.bxgj.lib.utils.DateTimeUtils;
import com.linkhand.bxgj.lib.utils.DecimalUtils;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailActivity extends BaseActivity {
    private static final int REQUEST_OPERATION = 1;
    private static final int SDK_PAY_FLAG = 2;
    int orderType = 0;
    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.more_iv)
    ImageView mMoreIV;
    @Bind(R.id.button1)
    TextView mButton1;
    @Bind(R.id.button2)
    TextView mButton2;
    @Bind(R.id.code_layout)
    LinearLayout mCodeLayout;
    @Bind(R.id.close_time)
    TextView mCloseTimeTV;
    Order mOrder;
    @Bind(R.id.image_good)
    ImageView mImage;
    @Bind(R.id.good_name)
    TextView mGoodName;
    @Bind(R.id.type_tv)
    TextView mTypeTv;
    @Bind(R.id.price1)
    TextView mPrice;
    @Bind(R.id.goods_num)
    TextView mGoodsNum;
    @Bind(R.id.total_money)
    TextView mTotalMoney;
    @Bind(R.id.time)
    TextView mTime;
    @Bind(R.id.address)
    TextView mAddress;


    private Dialog mPayDialog;
    private Dialog mReturnDialog;
    private Dialog mPingjiaDialog;

    private String mReturnReasonStr = ""; //退货理由
    private String mPingjiaStr = ""; //评价
    int position;
    private RequestQueue mRequestQueue = NoHttp.newRequestQueue();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_payment);
        ButterKnife.bind(this);
        initData();
        initView();

    }


    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras != null) {
            mOrder = (Order) extras.getSerializable("order");
            position = extras.getInt("position");
        }
    }

    private void initData() {
        switch (mOrder.getOrder_status()) {
            case 0:
                //待付款
                initTypeView1();
                break;
            case 1:
                //待提货
                initTypeView2();
                break;
            case 2:
                //已提货
                initTypeView3();
                break;
        }
    }

    private void initView() {
        ImageUtils.setDefaultNormalImage(mImage, ConnectUrl.REQUESTURL_IMAGE + mOrder.getOriginal_img(), R.drawable.default_pic_show);
        mGoodName.setText(mOrder.getGoods_name());
        mTypeTv.setText(mOrder.getSpec_key_name());
        mPrice.setText("¥ " + mOrder.getGoods_price());
        mGoodsNum.setText("x" + mOrder.getGoods_num());
        mTotalMoney.setText(mOrder.getTotal_amount());
        mTime.setText(DateTimeUtils.format(mOrder.getAdd_time() * 1000L));
    }


    private void initTypeView1() {
        mCloseTimeTV.setVisibility(View.GONE);
        mButton1.setVisibility(View.VISIBLE);
        mButton1.setText("取消订单");
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(OrderDetailActivity.this, "取消订单", Toast.LENGTH_SHORT).show();
                httpOrderOperation("cancel");
            }
        });

        mButton2.setText("付款");
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(OrderDetailActivity.this, "付款", Toast.LENGTH_SHORT).show();
                showPayDialog();
            }
        });
        mCodeLayout.setVisibility(View.GONE);
    }

    private void initTypeView2() {
        mButton1.setVisibility(View.VISIBLE);
        mButton1.setVisibility(View.GONE);

        mButton2.setText("退款");
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(OrderDetailActivity.this, "退款", Toast.LENGTH_SHORT).show();
                showReturnDialog();
            }
        });
        mCodeLayout.setVisibility(View.VISIBLE);
        mCloseTimeTV.setVisibility(View.GONE);

    }

    private void initTypeView3() {
        mButton1.setText("退货");
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(OrderDetailActivity.this, "退货", Toast.LENGTH_SHORT).show();
                showReturnDialog();
            }
        });

        mButton2.setText("评价");
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OrderDetailActivity.this, "评价", Toast.LENGTH_SHORT).show();
            }
        });
        mCodeLayout.setVisibility(View.VISIBLE);
        mCloseTimeTV.setVisibility(View.GONE);
    }


    private void showReturnDialog() {
        mReturnDialog = new Dialog(this, R.style.Dialog);
        mReturnDialog.setContentView(R.layout.dialog_appointment_evaluate);

        TextView submit = (TextView) mReturnDialog.findViewById(R.id.submit);
        final EditText contentET = (EditText) mReturnDialog.findViewById(R.id.content_edit);
        contentET.setHint("请输入退货原因");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(contentET.getText().toString())) {
                    showToast("请输入退货原因");
                    return;
                }
                mReturnReasonStr = contentET.getText().toString();
                httpOrderOperation("return");
                //
            }
        });
        mReturnDialog.show();
    }

    private void showPingjiaDialog(final int position) {
        mPingjiaDialog = new Dialog(this, R.style.Dialog);
        mPingjiaDialog.setContentView(R.layout.dialog_appointment_evaluate);

        TextView submit = (TextView) mPingjiaDialog.findViewById(R.id.submit);
        final EditText contentET = (EditText) mPingjiaDialog.findViewById(R.id.content_edit);
        contentET.setHint("请输入评价内容");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(contentET.getText().toString())) {
                    showToast("请输入评价内容");
                    return;
                }
                mPingjiaStr = contentET.getText().toString();
//                httpOrderOperation(position, "pingjia");
                //
            }
        });
        mPingjiaDialog.show();
    }

    private void showPayDialog() {
        mPayDialog = new Dialog(this, R.style.Dialog);
        mPayDialog.setContentView(R.layout.dialog_select_payway);
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
        String p = DecimalUtils.decimalFormat(mOrder.getOrder_amount());
        String priceHtml = "确认支付" + "<big>¥" + p + "</big>";
        submit.setText(Html.fromHtml(priceHtml));

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //弹出框点击事件
                if (payway[0] == 1) {
                    httpOrderOperation("alipay");
                } else {

                }
                if (mPayDialog != null && mPayDialog.isShowing()) {
                    mPayDialog.dismiss();
//                    contentET.getText().toString();
                }

            }
        });
        mPayDialog.show();
    }


    /**
     * 订单操作
     *
     * @param
     * @param flag 什么操作
     */
    private void httpOrderOperation(final String flag) {
        Request<JSONObject> request = null;
        if (flag.equals("cancel")) {
            request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_CANCEL_ORDER, RequestMethod.POST);
            request.add("order_sn", mOrder.getOrder_sn());
        } else if (flag.equals("alipay")) {
            request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_PROUCT_ALIPAY, RequestMethod.POST);
            request.add("order_sn", mOrder.getOrder_sn());
            request.add("order_amount", 0.01);
//            request.add("order_amount", mOrder.getOrder_amount());
        } else if (flag.equals("return")) {
            request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_RETURN_ORDER, RequestMethod.POST);
            request.add("reason", mReturnReasonStr);
            request.add("spec_key", mOrder.getSpec_key());
            request.add("goods_id", mOrder.getGoods_id());
            request.add("user_id", MyApplication.getUser().getUserid());
        } else if (flag.equals("pingjia")) {
//            request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_RETURN_ORDER, RequestMethod.POST);
//            request.add("reason", mReturnReasonStr);
//            request.add("spec_key", mOrder.getSpec_key());
//            request.add("goods_id", mOrder.getGoods_id());
//            request.add("user_id", MyApplication.getUser().getUserid());
        }

        mRequestQueue.add(REQUEST_OPERATION, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
                showLoading();
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == REQUEST_OPERATION) {
                    String resultCode = null;
                    Gson gson = new Gson();
                    try {
                        JSONObject jsonObject = response.get();
                        resultCode = jsonObject.getString("code");
                        if (resultCode.equals("200")) {
                            if (flag.equals("cancel")) {
                                EventBus.getDefault().post(new EventFlag("updateOrder", ""));
                            }
                            if (flag.equals("alipay")) {
                                goAlipay(jsonObject.getString("data"));
                            }
                            if (flag.equals("return")) {
                                showToast("申请成功，请等待处理结果！");
                            }


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
                hideLoading();
            }
        });
    }

    private void goAlipay(final String orderInfo) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(OrderDetailActivity.this);
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
//                        showToast("支付成功");
                        EventBus.getDefault().post(new EventFlag("updateOrder", ""));
                        showPaySuccessDialog();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showToast("支付失败");
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    // 购买成功
    private void showPaySuccessDialog() {
        final Dialog mDialog = new Dialog(OrderDetailActivity.this, R.style.Dialog);
        mDialog.setCancelable(false);
        mDialog.setContentView(R.layout.dialog_promt_pay_success);
        TextView info = (TextView) mDialog.findViewById(R.id.info);
        String htmlInfo = "感谢您的光顾，赠与您：" + "<img src=\"" + R.drawable.icon_gold + "\"/>" + "黄金5两~";
        info.setText(Html.fromHtml(htmlInfo));
        TextView know = (TextView) mDialog.findViewById(R.id.yes);
        mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    finish();
                }
                return false;
            }
        });
        know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //弹出框点击事件
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                finish();
            }
        });
        mDialog.show();
    }

    @OnClick({R.id.back, R.id.more_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.more_iv:
                break;

        }
    }
}
