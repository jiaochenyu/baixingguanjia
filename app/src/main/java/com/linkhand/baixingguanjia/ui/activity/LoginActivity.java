package com.linkhand.baixingguanjia.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.base.MyApplication;
import com.linkhand.baixingguanjia.entity.User;
import com.linkhand.baixingguanjia.utils.SPUtils;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
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
import cn.sharesdk.onekeyshare.OnekeyShare;

public class LoginActivity extends BaseActivity {
    private final static int REQUEST = 0;

    private static final String QQ_APP_ID = "1106257128";
    @Bind(R.id.back)
    ImageView mBackIV;
    @Bind(R.id.right_text)
    TextView mRegisterButton;
    @Bind(R.id.username)
    EditText mUsernameET;
    @Bind(R.id.password)
    EditText mPasswordET;
    @Bind(R.id.login)
    TextView mLoginTV;
    @Bind(R.id.login_by_phone)
    TextView mLoginByPhoneTV;
    @Bind(R.id.forgetpassword)
    TextView mForgetpasswordTV;
    @Bind(R.id.wechat)
    LinearLayout mWechat;
    @Bind(R.id.qq)
    LinearLayout mQQ;

    Tencent mTencent;
    private IUiListener loginListener;
    private IUiListener userInfoListener;
    private String scope;  //qq

    private UserInfo userInfo; //qq

    private RequestQueue mRequestQueue = NoHttp.newRequestQueue();

    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }



    private void initData() {

    }

    @OnClick({R.id.back, R.id.right_text, R.id.login, R.id.login_by_phone, R.id.forgetpassword, R.id.wechat, R.id.qq})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.right_text:
                Intent intent1 = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent1);
                break;
            case R.id.login:
                judgeData();
                break;
            case R.id.login_by_phone:
                Intent intent2 = new Intent(LoginActivity.this, LoginByPhoneActivity.class);
                startActivity(intent2);
                break;

            case R.id.forgetpassword:
                Intent intent3 = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent3);
                break;
            case R.id.wechat:
//                showShare();
                User user = new User();
                user.setAvatarAddr("222");
                SPUtils.put(LoginActivity.this, "userInfo", user);
                MyApplication.setUser(user);
                goAndFinish(MainActivity.class);
                break;
            case R.id.qq:
                showLoading();
                loginQQ();
                break;
        }
    }

    /**
     * 数据判断
     */
    private void judgeData() {
        username = mUsernameET.getText().toString();
        password = mPasswordET.getText().toString();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(LoginActivity.this, "账号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(LoginActivity.this, "密码大6位", Toast.LENGTH_SHORT).show();
            return;
        }
        httpLogin();
    }

    private void httpLogin() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.LOGIN_MIMA, RequestMethod.POST);
        Gson gson = new Gson();
        request.add("phone", username);
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
                        JSONObject jsonObject = response.get();
                        resultCode = jsonObject.getString("code");
                        if (resultCode.equals("200")) {
                            User user = new User();
                            user = new Gson().fromJson(jsonObject.getJSONObject("data").toString(), User.class);
                            SPUtils.put(LoginActivity.this, "userInfo", user);
                            MyApplication.setUser(user);
                            goAndFinish(MainActivity.class);

                        } else if (resultCode.equals("206")) {
                            Toast.makeText(LoginActivity.this, "账号或密码有误", Toast.LENGTH_SHORT).show();
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

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.app_name));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }


    public void loginQQ() {
        mTencent = Tencent.createInstance(QQ_APP_ID, LoginActivity.this);
        scope = "all";
        if (!mTencent.isSessionValid()) {
            mTencent.login(LoginActivity.this, scope, loginListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_LOGIN) {
            hideLoading();
            if (resultCode == -1) {
                Tencent.handleResultData(data, new QQUiListener());
            }
        }
    }


    /**
     * QQ第三方登录回调借口
     */
    private class QQUiListener implements IUiListener {

        @Override
        public void onComplete(Object o) {
            Log.d("qq", "onComplete: " + o.toString());

        }

        @Override
        public void onError(UiError uiError) {

        }

        @Override
        public void onCancel() {

        }
    }

    @Override
    protected void onDestroy() {
        if (mTencent != null) {
            mTencent.logout(LoginActivity.this);
        }
        super.onDestroy();
    }
}
