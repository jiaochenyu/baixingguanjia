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


public class ForgetPasswordActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.yanzheng_num)
    EditText mYanzhengNumET;
    @Bind(R.id.yanzheng)
    TextView mYanzhengTV;
    @Bind(R.id.username)
    EditText mPhoneET;
    @Bind(R.id.password)
    EditText mPasswordET;
    @Bind(R.id.go)
    TextView mGoTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.back, R.id.yanzheng_num, R.id.yanzheng, R.id.go})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.yanzheng_num:
                break;
            case R.id.yanzheng:
                break;
            case R.id.go:

                break;
        }
    }
}
