package com.linkhand.baixingguanjia.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录验证页面
 */
public class LoginByPhoneActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.yanzheng_num)
    EditText mYanzhengNumET;
    @Bind(R.id.yanzheng)
    TextView mYanzhengTV;
    @Bind(R.id.username)
    EditText mPhoneTV;
    @Bind(R.id.login)
    TextView mLoginTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_by_phone);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.yanzheng, R.id.username, R.id.login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.yanzheng:
                break;
            case R.id.username:
                break;
            case R.id.login:
                break;
        }
    }
}
