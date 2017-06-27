package com.linkhand.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkhand.R;
import com.linkhand.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView mBackIV;
    @Bind(R.id.title)
    TextView mTitleTV;
    @Bind(R.id.right_text)
    TextView mLoginTV;
    @Bind(R.id.yanzheng_num)
    EditText mYanzhengNumET;
    @Bind(R.id.yanzheng)
    TextView mYanzhengTV;
    @Bind(R.id.username)
    EditText mUsernameET;
    @Bind(R.id.password)
    EditText mPasswordET;
    @Bind(R.id.confirm_password)
    EditText mConfirmPasswordET;
    @Bind(R.id.register)
    TextView mRegisterTV;

    boolean flag = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initView();
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
                //验证
                flag = !flag;
                if (flag) {
//                    mYanzhengTV.setFocusable(true);
                    mYanzhengTV.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_textview_corner_line_red));
                    mYanzhengTV.setTextColor(getResources().getColor(R.color.colorTopic));
                } else {
//                    mYanzhengTV.setFocusable(false);
                    mYanzhengTV.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_textview_corner_red));
                    mYanzhengTV.setTextColor(getResources().getColor(R.color.colorWhite));
                }
                break;
            case R.id.register:

                break;
        }
    }
}
