package com.linkhand.baixingguanjia.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.utils.RegexUtils;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ForgetPasswordActivity extends BaseActivity {
    private static final int COUNTDOWN = 0x100;

    private static final int REQUEST = 0;
    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.yanzheng_num)
    EditText mYanzhengNumET;
    @Bind(R.id.yanzheng)
    TextView mYanzhengTV;
    @Bind(R.id.phone)
    EditText mPhoneET;
    @Bind(R.id.password)
    EditText mPasswordET;
    @Bind(R.id.go)
    TextView mGoTV;


    private String phone;
    private String code;
    private String password;

    private RequestQueue mRequestQueue = NoHttp.newRequestQueue();
    private String serverPhoneCode;

    Runnable mRunnable;
    int timeCount;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case COUNTDOWN:
                    timeCount--;
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        mRunnable = new Runnable() {
            @Override
            public void run() {
                mYanzhengTV.setText(getResources().getString(R.string.requestVerification) + "(" + String.format("%ds", timeCount) + ")");
                if (timeCount < 0) {
                    reset();
                    return;
                }
                mHandler.sendEmptyMessage(COUNTDOWN);
                mYanzhengTV.postDelayed(this, 1000);
            }
        };
    }


    // 开始倒计时
    private void startCountdown() {
        timeCount = 60;
        mYanzhengTV.setClickable(false);
        mYanzhengTV.post(mRunnable);
        mYanzhengTV.setTextColor(getResources().getColor(R.color.gray8e8e));
    }

    //重置倒计时
    private void reset() {
        timeCount = 0;
        mYanzhengTV.setClickable(true);
        mYanzhengTV.setTextColor(getResources().getColor(R.color.blueTopic));
        mYanzhengTV.setText(getResources().getString(R.string.sendVerification));
    }


    @OnClick({R.id.back, R.id.yanzheng, R.id.go})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.yanzheng:
                dataJudgeCode();
                break;
            case R.id.go:
                dataJudgeReset();
                break;
        }
    }

    private void dataJudgeReset() {
        phone = mPhoneET.getText().toString();
        code = mYanzhengNumET.getText().toString();
        password = mPasswordET.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(ForgetPasswordActivity.this, "电话号码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!RegexUtils.isMobileExact(phone)) {
            Toast.makeText(ForgetPasswordActivity.this, "请输入正确的电话号码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(code)) {
            Toast.makeText(ForgetPasswordActivity.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(ForgetPasswordActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(ForgetPasswordActivity.this, "密码大于6位", Toast.LENGTH_SHORT).show();
            return;
        }
        httpUpdatePass();

    }


    private void dataJudgeCode() {
        phone = mPhoneET.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(ForgetPasswordActivity.this, "电话号码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!RegexUtils.isMobileExact(phone)) {
            Toast.makeText(ForgetPasswordActivity.this, "请输入正确的电话号码", Toast.LENGTH_SHORT).show();
            return;
        }
        startCountdown();
        httpGetCode();
    }


    //获取验证码
    private void httpGetCode() {
        mRequestQueue = NoHttp.newRequestQueue();
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.LOGIN_SENDPHONECODE, RequestMethod.POST);
        Gson gson = new Gson();
        request.add("phone", phone);
        request.add("product", code);
        mRequestQueue.add(REQUEST, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == REQUEST) {
                    String resultCode = null;
                    try {
                        resultCode = response.get().getString("code");
                        String info = response.get().getString("info");
                        if (resultCode.equals("200")) {
                            Gson gson = new Gson();
//                        MyApplication.setUser(gson.fromJson(jsonObject.getString("user"), User.class));
                            Toast.makeText(ForgetPasswordActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                            //发送验证码按安按钮显示控制
                            serverPhoneCode = response.get().getString("phonecode");

                        } else if (resultCode.equals("202")) {
                            showToast(R.string.sendfail);
                            reset();
                        } else {
                            showToast(R.string.sendfail);
                            reset();
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


    /***
     * 注册
     */
    private void httpUpdatePass() {
        mRequestQueue = NoHttp.newRequestQueue();
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.LOGIN_LOGINZHAO, RequestMethod.POST);
        Gson gson = new Gson();
        request.add("phone", phone);
        request.add("code", code);
        request.add("password", password);
        mRequestQueue.add(REQUEST, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
                showLoading();
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == REQUEST) {
                    try {
                        // 返回值含义：207手机号未注册；204手机号有变化；208重置密码失败；200重置密码成功
                        int resultCode = Integer.parseInt(response.get().getString("code"));
                        switch (resultCode) {
                            case 200:
                                showToast(R.string.successUpdatePass);
                                finish();
                                startActivity(new Intent(ForgetPasswordActivity.this, LoginActivity.class));
                                break;
                            case 204:
                                showToast(R.string.updatePassFail);
                                break;
                            case 207:
                                showToast(R.string.phoneNotRegister);
                                break;
                            case 209:
                                showToast(R.string.codeerror);
                                break;
                            default:
                                showToast(R.string.updatePassFail);
                                break;
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
}
