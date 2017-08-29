package com.linkhand.baixingguanjia.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.base.MyApplication;
import com.linkhand.baixingguanjia.entity.User;
import com.linkhand.baixingguanjia.utils.RegexUtils;
import com.linkhand.baixingguanjia.utils.SPUtils;
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

import static com.linkhand.baixingguanjia.R.id.username;

/**
 * 登录验证页面
 */
public class LoginByPhoneActivity extends BaseActivity {
    private static final int COUNTDOWN = 0x100;

    private static final int REQUEST = 0;
    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.yanzheng_num)
    EditText mYanzhengNumET;
    @Bind(R.id.yanzheng)
    TextView mYanzhengTV;
    @Bind(username)
    EditText mPhoneET;
    @Bind(R.id.login)
    TextView mLoginTV;

    String phone;
    String code;

    boolean flag = true;

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
        setContentView(R.layout.activity_login_by_phone);
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

    @OnClick({R.id.back, R.id.yanzheng, R.id.login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.yanzheng:
                judgeCodeData();
                break;
            case R.id.login:
                judgeLoginData();
                break;
        }
    }

    private void judgeCodeData() {
        phone = mPhoneET.getText().toString();
        Log.d("phone", "judgeCodeData: "+phone);
        if (TextUtils.isEmpty(phone)) {
            showToast(R.string.passnotnull);
            return;
        }
        if (!RegexUtils.isMobileExact(phone)) {
            showToast(R.string.inputrightphone);
            return;
        }
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


    //获取验证码
    private void httpGetCode() {
        mRequestQueue = NoHttp.newRequestQueue();
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.LOGIN_SENDPHONECODE, RequestMethod.POST);
        request.add("phone", phone);
        request.add("product", 2);
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
                            showToast(R.string.sendsuccess);
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


    /**
     * 数据判断
     */
    private void judgeLoginData() {
        phone = mPhoneET.getText().toString();
        code = mYanzhengNumET.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(LoginByPhoneActivity.this, "账号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(LoginByPhoneActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        httpLogin();
    }

    private void httpLogin() {
        mRequestQueue = NoHttp.newRequestQueue();
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.LOGIN_LOGINCODE, RequestMethod.POST);
        request.add("phone", phone);
        request.add("code", code);
        mRequestQueue.add(REQUEST, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
                showLoading();
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == REQUEST) {
                    try {

                        //207手机号未注册；204手机号有变化；209验证码错误；200登陆成功
                        JSONObject jsonObject = response.get();
                        int resultCode = Integer.parseInt(jsonObject.getString("code"));
                        switch (resultCode) {
                            case 200:
                                User user = new User();
                                user = new Gson().fromJson(jsonObject.getJSONObject("data").toString(), User.class);
                                SPUtils.put(LoginByPhoneActivity.this, "userInfo", user);
                                MyApplication.setUser(user);
                                goAndFinish(MainActivity.class);
                                break;
                            case 204:
                                Toast.makeText(LoginByPhoneActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                                break;
                            case 207:
                                Toast.makeText(LoginByPhoneActivity.this, "手机号未注册", Toast.LENGTH_SHORT).show();
                                break;
                            case 209:
                                Toast.makeText(LoginByPhoneActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                                break;
                            case 214:
                                showOffliePop(jsonObject.getString("data"));
//                                Toast.makeText(LoginByPhoneActivity.this, "该账号已被禁用", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(LoginByPhoneActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                                break;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            private void showOffliePop(String info){
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginByPhoneActivity.this);
                builder.setTitle("提示");
                builder.setMessage("该账号因涉嫌："+info+"问题，已被平台禁用！");
                builder.setCancelable(false);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alterDialog = builder.create();
                alterDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                alterDialog.show();
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
