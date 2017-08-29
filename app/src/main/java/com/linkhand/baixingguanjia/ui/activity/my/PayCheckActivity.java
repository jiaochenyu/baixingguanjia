package com.linkhand.baixingguanjia.ui.activity.my;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.base.MyApplication;
import com.linkhand.baixingguanjia.entity.EventFlag;
import com.linkhand.baixingguanjia.entity.User;
import com.linkhand.baixingguanjia.listener.PayResult;
import com.linkhand.baixingguanjia.utils.SPUtils;
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

public class PayCheckActivity extends BaseActivity {

    private static final int SDK_PAY_FLAG = 1;
    private static final int REQUEST_WHAT_ALIPAY = 2;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.item)
    LinearLayout mItemLayout;
    @Bind(R.id.submit_pay)
    TextView mSubmitPayTV;
    boolean flagItem = false;
    @Bind(R.id.pay_radiogroup)
    RadioGroup mPayRadiogroup;
    private RequestQueue mQueue = NoHttp.newRequestQueue();
    private int payway = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_check);
        ButterKnife.bind(this);
        initView();
        initListener();
    }


    private void initView() {
        mTitle.setText(R.string.pay);
        String priceHtml = "确认支付" + "<big>¥" + "1.00" + "</big>";
        mSubmitPayTV.setText(Html.fromHtml(priceHtml));
    }

    private void initListener() {
        mPayRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.alipayPay:
                        payway = 1;
                        break;
                    case R.id.weChatPay:
                        payway = 2;
                        break;
                }
            }
        });
    }

    @OnClick({R.id.back, R.id.item, R.id.submit_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.item:
                flagItem = !flagItem;
                if (flagItem) {
                    mItemLayout.setBackground(getResources().getDrawable(R.drawable.background_corner_line_blue));
                    mSubmitPayTV.setBackground(getResources().getDrawable(R.drawable.background_textview_big_corner_blue));
                } else {
                    mItemLayout.setBackground(getResources().getDrawable(R.drawable.background_textview_6dp_corner_line_gray));
                    mSubmitPayTV.setBackground(getResources().getDrawable(R.drawable.background_textview_big_corner_gray));
                }
                break;
            case R.id.submit_pay:
                if (flagItem) {
                    if (payway == 1) {
                        httpAlipay();
                    } else {

                    }
                }
                break;
        }
    }


    private void httpAlipay() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_PAY_ALIPAY_SILVER, RequestMethod.POST);
        request.add("user_id", MyApplication.getUser().getUserid());
        request.add("account", 0.01);
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
                PayTask alipay = new PayTask(PayCheckActivity.this);
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
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(PayCheckActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        User user = MyApplication.getUser();
                        user.setUser_money(user.getUser_money() + 100);
                        SPUtils.put(PayCheckActivity.this, "userInfo", user);
                        MyApplication.setUser(user);
                        EventBus.getDefault().post(new EventFlag("paySilver", ""));
                        finish();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(PayCheckActivity.this, "您取消了支付", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }

    };


}
