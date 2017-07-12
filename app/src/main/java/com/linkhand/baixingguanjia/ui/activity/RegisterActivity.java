package com.linkhand.baixingguanjia.ui.activity;

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

import static android.text.TextUtils.isEmpty;

public class RegisterActivity extends BaseActivity {
    private final static int REQUEST = 0;
    private static final int COUNTDOWN = 0x100;

    @Bind(R.id.back)
    ImageView mBackIV;
    @Bind(R.id.title)
    TextView mTitleTV;
    @Bind(R.id.right_text)
    TextView mLoginTV;
    @Bind(R.id.yanzheng)
    TextView mYanzhengTV;
    @Bind(R.id.password)
    EditText mPasswordET;
    @Bind(R.id.confirm_password)
    EditText mConfirmPasswordET;
    @Bind(R.id.register)
    TextView mRegisterTV;
    @Bind(R.id.phone)
    EditText mPhoneET;
    @Bind(R.id.code)
    EditText mCodeET;

    private RequestQueue mRequestQueue = NoHttp.newRequestQueue();

    String code;
    String phone;
    String password;

    String serverPhoneCode; //后台返回的验证码

    boolean flag = true;

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
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initView();
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

    private void initView() {
        mTitleTV.setText("注册");
        mLoginTV.setText("登录");
    }

    @OnClick({R.id.back, R.id.right_text, R.id.yanzheng, R.id.register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.right_text:
                //登录
                finish();
                break;
            case R.id.yanzheng:

                //获取验证码
                dataJudgeCode();
                break;
            case R.id.register:
                //数据判断
                dataJudgeRegister();
                break;
        }
    }


    private void dataJudgeCode() {
        phone = mPhoneET.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(RegisterActivity.this, "电话号码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!RegexUtils.isMobileExact(phone)) {
            Toast.makeText(RegisterActivity.this, "请输入正确的电话号码", Toast.LENGTH_SHORT).show();
            return;
        }
        //开始倒计时
        startCountdown();
        httpGetCode();
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

    //数据判断
    private void dataJudgeRegister() {
        phone = mPhoneET.getText().toString();
        code = mCodeET.getText().toString();
        password = mPasswordET.getText().toString();
        if (isEmpty(phone)) {
            Toast.makeText(RegisterActivity.this, "电话号码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!RegexUtils.isMobileExact(phone)) {
            Toast.makeText(RegisterActivity.this, "请输入正确的电话号码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(code)) {
            Toast.makeText(RegisterActivity.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(password)) {
            Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            showToast(getResources().getString(R.string.passmore));
            return;
        }
        httpRegister();

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
                            Toast.makeText(RegisterActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
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
    private void httpRegister() {
        mRequestQueue = NoHttp.newRequestQueue();
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.LOGIN_REGISTER, RequestMethod.POST);
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
                    String resultCode = null;
                    try {
                        resultCode = response.get().getString("code");
                        String info = response.get().getString("info");
                        if (resultCode.equals("200")) {
                            showToast(R.string.registerSuccess);
                        } else if (resultCode.equals("202")) {
                            showToast(R.string.registerFail);
                        } else if (resultCode.equals("203")) {
                            showToast(R.string.phoneAlreadyExist);
                        } else {
                            showToast(R.string.registerFail);
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
