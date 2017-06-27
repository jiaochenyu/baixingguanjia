package com.linkhand.activity.car;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linkhand.R;
import com.linkhand.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddUserInfoActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.edit)
    EditText mNeedEdit;
    @Bind(R.id.submit)
    TextView mSubmit;

    EditText mNameET;
    EditText mPhoneET;
    EditText mAddressET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_info);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        LinearLayout linearLayout1  = (LinearLayout) findViewById(R.id.name_layout);
        LinearLayout linearLayout2  = (LinearLayout) findViewById(R.id.phone_layout);
        LinearLayout linearLayout3  = (LinearLayout) findViewById(R.id.address_layout);
        mTitle.setText("填写信息");
        ((TextView)linearLayout1.findViewById(R.id.text_left)).setText("姓名");
        ((TextView)linearLayout2.findViewById(R.id.text_left)).setText("电话");
        ((TextView)linearLayout3.findViewById(R.id.text_left)).setText("家庭住址");
        ((TextView)linearLayout1.findViewById(R.id.text_left)).setTextColor(getResources().getColor(R.color.grayText));
        ((TextView)linearLayout2.findViewById(R.id.text_left)).setTextColor(getResources().getColor(R.color.grayText));
        ((TextView)linearLayout3.findViewById(R.id.text_left)).setTextColor(getResources().getColor(R.color.grayText));
        mNameET  = (EditText) linearLayout1.findViewById(R.id.edittext_right);
        mPhoneET  = (EditText) linearLayout2.findViewById(R.id.edittext_right);
        mAddressET  = (EditText) linearLayout3.findViewById(R.id.edittext_right);
    }

    private void initData() {

    }

    @OnClick({R.id.back, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.submit:
                break;
        }
    }
}
