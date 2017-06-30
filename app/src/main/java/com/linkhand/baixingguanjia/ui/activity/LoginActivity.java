package com.linkhand.baixingguanjia.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
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
    private String scope;

    private UserInfo userInfo; //qq

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initData();

    }

    private void initData() {

    }

    @OnClick({R.id.back, R.id.right_text, R.id.login, R.id.login_by_phone, R.id.forgetpassword, R.id.wechat, R.id.qq})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                break;
            case R.id.right_text:
                Intent intent1 = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent1);
                break;
            case R.id.login:
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
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

                break;
            case R.id.qq:
                showLoading();
                loginQQ();
                break;
        }
    }


    public void loginQQ() {
        mTencent = Tencent.createInstance(QQ_APP_ID, LoginActivity.this);
        scope = "all";
        if (!mTencent.isSessionValid()) {
            mTencent.login(LoginActivity.this,scope,loginListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_LOGIN) {
            hideLoading();
            if (resultCode == -1) {
                Tencent.handleResultData(data, new QQUiListener() );
            }
        }
    }


    /**
     * QQ第三方登录回调借口
     */
    private class QQUiListener implements IUiListener {

        @Override
        public void onComplete(Object o) {
            Log.d("qq", "onComplete: "+o.toString());

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
