package com.linkhand.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkhand.MainActivity;
import com.linkhand.R;
import com.linkhand.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.back, R.id.right_text, R.id.login, R.id.login_by_phone, R.id.forgetpassword})
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
        }
    }
}
