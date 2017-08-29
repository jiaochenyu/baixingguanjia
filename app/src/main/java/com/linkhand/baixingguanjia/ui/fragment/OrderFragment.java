package com.linkhand.baixingguanjia.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseFragment;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.base.MyApplication;
import com.linkhand.baixingguanjia.entity.EventFlag;
import com.linkhand.baixingguanjia.entity.Order;
import com.linkhand.baixingguanjia.listener.PayResult;
import com.linkhand.baixingguanjia.ui.activity.order.OrderDetailActivity;
import com.linkhand.baixingguanjia.ui.adapter.my.MyOrderAdapter;
import com.linkhand.bxgj.lib.utils.DecimalUtils;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OrderFragment extends BaseFragment {
    private static final String TAG = "info";
    private static final int REQUEST_GET_LIST = 0;
    private static final int REQUEST_OPERATION = 2;
    private static final int SDK_PAY_FLAG = 3;
    View view;
    @Bind(R.id.null_bg)
    RelativeLayout mNullBg;
    private int type;
    private MyOrderAdapter mAdapter;
    private List<Order> mList;
    @Bind(R.id.listview)
    PullToRefreshListView mListView;
    private int pageFlag = 1;
    private RequestQueue mRequestQueue = NoHttp.newRequestQueue();
    private Dialog mPayDialog;
    private Dialog mReturnDialog;
    private Dialog mPingjiaDialog;

    private String mReturnReasonStr = ""; //退货理由
    private String mPingjiaStr = ""; //评价

    public OrderFragment(int type) {
        this.type = type;
        switch (type) {
            case -1:
                Log.d(TAG, "OrderFragment: 全部");
                break;
            case 0:
                Log.d(TAG, "OrderFragment: 代付款");
                break;
            case 1:
                Log.d(TAG, "OrderFragment: 待提货");
                break;
            case 2:
                Log.d(TAG, "OrderFragment: 已提货");
                break;

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order, container, false);
        ButterKnife.bind(this, view);
        initView();
        initData();
        httpGetList();
        initListener();
        return view;
    }


    private void initData() {
        mList = new ArrayList<>();
        mAdapter = new MyOrderAdapter(getActivity(), R.layout.item_my_order, mList);
        mListView.setAdapter(mAdapter);
    }

    private void initView() {
        initRefreshListView(mListView);
        mListView.setMode(PullToRefreshBase.Mode.BOTH);
    }

    private void initListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("order", mList.get(position - 1));
                bundle.putInt("position", position - 1);
                bundle.putInt("type", type);

                go(OrderDetailActivity.class, bundle);
            }
        });
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //重新加载
                pageFlag = 1;
                httpGetList();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                httpGetList();
            }
        });
        mAdapter.setOrderOperationListerner(new MyOrderAdapter.OrderOperationListerner() {
            @Override
            public void onCancelListener(int position) {
                httpOrderOperation(position, "cancel");
            }

            @Override
            public void onPayListener(int position) {
                showPayDialog(position);
            }

            @Override
            public void onReturnListener(int position) {
                showReturnDialog(position);
            }

            @Override
            public void onEvaluateListener(int position) {
                showPingjiaDialog(position);
            }
        });
    }

    private void showReturnDialog(final int position) {
        mReturnDialog = new Dialog(getActivity(), R.style.Dialog);
        mReturnDialog.setContentView(R.layout.dialog_appointment_evaluate);

        TextView submit = (TextView) mReturnDialog.findViewById(R.id.submit);
        final EditText contentET = (EditText) mReturnDialog.findViewById(R.id.content_edit);
        contentET.setHint("请输入退货原因");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(contentET.getText().toString())) {
                    Toast.makeText(getActivity(), "请输入退货原因", Toast.LENGTH_SHORT).show();
                    return;
                }
                mReturnReasonStr = contentET.getText().toString();
                httpOrderOperation(position, "return");
                //
            }
        });
        mReturnDialog.show();
    }

    private void showPingjiaDialog(final int position) {
        mPingjiaDialog = new Dialog(getActivity(), R.style.Dialog);
        mPingjiaDialog.setContentView(R.layout.dialog_appointment_evaluate);

        TextView submit = (TextView) mPingjiaDialog.findViewById(R.id.submit);
        final EditText contentET = (EditText) mPingjiaDialog.findViewById(R.id.content_edit);
        contentET.setHint("请输入评价内容");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(contentET.getText().toString())) {
                    Toast.makeText(getActivity(), "请输入评价内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                mPingjiaStr = contentET.getText().toString();
//                httpOrderOperation(position, "pingjia");
                //
            }
        });
        mPingjiaDialog.show();
    }

    private void showPayDialog(final int pos) {
        mPayDialog = new Dialog(getActivity(), R.style.Dialog);
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
        String p = DecimalUtils.decimalFormat(mList.get(pos).getOrder_amount());
        String priceHtml = "确认支付" + "<big>¥" + p + "</big>";
        submit.setText(Html.fromHtml(priceHtml));

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //弹出框点击事件
                if (payway[0] == 1) {
                    httpOrderOperation(pos, "alipay");
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

    private void httpGetList() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_MY_ORDER, RequestMethod.POST);
        Gson gson = new Gson();
        request.add("user_id", MyApplication.getUser().getUserid());
        request.add("page", pageFlag);
        request.add("num", 10);
        if (type >= 0) {
            request.add("order_status", type);
        }

        mRequestQueue.add(REQUEST_GET_LIST, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
                showLoading();
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == REQUEST_GET_LIST) {
                    String resultCode = null;
                    Gson gson = new Gson();
                    try {
                        if (pageFlag == 1) {
                            mList.clear();
                        }
                        JSONObject jsonObject = response.get();
                        resultCode = jsonObject.getString("code");
                        if (resultCode.equals("200")) {
                            JSONArray array = jsonObject.getJSONArray("data");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jsonObject1 = array.getJSONObject(i);
                                Order order = gson.fromJson(jsonObject1.toString(), Order.class);
                                mList.add(order);
                            }

                        } else if (resultCode.equals("201")) {

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
                mListView.onRefreshComplete();
                mAdapter.notifyDataSetChanged();
                pageFlag++;
                if (!adjustList(mList)) {
                    mNullBg.setVisibility(View.VISIBLE);
                } else {
                    mNullBg.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 订单操作
     *
     * @param position
     * @param flag     什么操作
     */
    private void httpOrderOperation(final int position, final String flag) {
        Request<JSONObject> request = null;
        if (flag.equals("cancel")) {
            request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_CANCEL_ORDER, RequestMethod.POST);
            request.add("order_sn", mList.get(position).getOrder_sn());
        } else if (flag.equals("alipay")) {
            request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_PROUCT_ALIPAY, RequestMethod.POST);
            request.add("order_sn", mList.get(position).getOrder_sn());
            request.add("order_amount", 0.01);
//            request.add("order_amount", mList.get(position).getOrder_amount());
        } else if (flag.equals("return")) {
            request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_RETURN_ORDER, RequestMethod.POST);
            request.add("reason", mReturnReasonStr);
            request.add("spec_key", mList.get(position).getSpec_key());
            request.add("goods_id", mList.get(position).getGoods_id());
            request.add("user_id", MyApplication.getUser().getUserid());
        } else if (flag.equals("pingjia")) {
//            request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_RETURN_ORDER, RequestMethod.POST);
//            request.add("reason", mReturnReasonStr);
//            request.add("spec_key", mList.get(position).getSpec_key());
//            request.add("goods_id", mList.get(position).getGoods_id());
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
                                mList.remove(position);
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
                mAdapter.notifyDataSetChanged();
                if (!adjustList(mList)) {
                    mNullBg.setVisibility(View.VISIBLE);
                } else {
                    mNullBg.setVisibility(View.GONE);
                }
            }
        });
    }

    private void goAlipay(final String orderInfo) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(getActivity());
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
                        Toast.makeText(getActivity(), "支付成功", Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post(new EventFlag("paySuccess", ""));
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(getActivity(), "您取消了支付", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };


    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Subscribe
    public void onEvent(EventFlag eventFlag) {
        if (eventFlag.getFlag().equals("updateOrder")) {
            pageFlag = 1;
            httpGetList();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
