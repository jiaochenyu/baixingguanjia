package com.linkhand.baixingguanjia.ui.activity.my;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.base.MyApplication;
import com.linkhand.baixingguanjia.entity.EventFlag;
import com.linkhand.baixingguanjia.entity.User;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PropertyPayActivity extends BaseActivity {

    private static final int HTTP_REQUEST = 1;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.edit)
    EditText mEdit;
    @Bind(R.id.user_gold)
    TextView mUserGoldTV;
    @Bind(R.id.value_price)
    TextView mValuePriceTV;
    int count;
    private RequestQueue mQueue = NoHttp.newRequestQueue();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_pay);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        mTitle.setText(R.string.dikouwuyefei);
        mUserGoldTV.setText(MyApplication.getUser().getUser_gold() + "两");

    }

    private void initData() {

    }

    private void initListener() {
        mEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    count = Integer.parseInt(s.toString());
                    if (count > 0) {
                        float f = count / 100f;
                        mValuePriceTV.setText("¥" + DecimalUtils.decimalFormat(f));
                    }

                }

            }
        });

    }

    @OnClick({R.id.back, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.submit:
                if (count > MyApplication.getUser().getUser_gold()) {
                    showToast(R.string.huangjinbuzu);
                    return;
                }
                httpPay();
                break;
        }
    }

    /**
     * 抵扣
     */
    public void httpPay() {
        Request<JSONObject> mRequest = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_GOLD_PAY, RequestMethod.POST);

        mRequest.add("userid", MyApplication.getUser().getUserid());
//        mRequest.add("money", count / 100f); //抵扣金额
        mRequest.add("money", 0.01);
        mRequest.add("user_gold", count);
        Log.d("参数", mRequest.getParamKeyValues().values().toString());
        mQueue.add(HTTP_REQUEST, mRequest, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
                showLoading();
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                JSONObject jsonObject = response.get();
                if (what == HTTP_REQUEST) {//
                    try {
                        String code = jsonObject.getString("code");
                        if (code.equals("200")) {
                            User user = MyApplication.getUser();
                            user.setUser_gold(jsonObject.getInt("data"));
                            MyApplication.setUser(user);
                            EventBus.getDefault().post(new EventFlag("updateGold",""));
                            mUserGoldTV.setText(MyApplication.getUser().getUser_gold() + "两");
                        }
                        showToast(jsonObject.getString("info"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                Log.e("result1", jsonObject.toString());
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

}
