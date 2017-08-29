package com.linkhand.baixingguanjia.ui.activity.car;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.entity.Car;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * *********************停用****************
 */
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

    private String type;
    private Car mCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_info);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras != null) {
            type = extras.getString("type");
            if (type.equals("car")) {
                mCar = (Car) extras.getSerializable("car");
            }

        }
    }

    private void initView() {
        LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.name_layout);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.phone_layout);
        LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.address_layout);
        mTitle.setText("填写信息");
        ((TextView) linearLayout1.findViewById(R.id.text_left)).setText("姓名");
        ((TextView) linearLayout2.findViewById(R.id.text_left)).setText("电话");
        ((TextView) linearLayout3.findViewById(R.id.text_left)).setText("家庭住址");
        ((TextView) linearLayout1.findViewById(R.id.text_left)).setTextColor(getResources().getColor(R.color.grayText));
        ((TextView) linearLayout2.findViewById(R.id.text_left)).setTextColor(getResources().getColor(R.color.grayText));
        ((TextView) linearLayout3.findViewById(R.id.text_left)).setTextColor(getResources().getColor(R.color.grayText));
        mNameET = (EditText) linearLayout1.findViewById(R.id.edittext_right);
        mNameET.setKeyListener(null);
        mPhoneET = (EditText) linearLayout2.findViewById(R.id.edittext_right);
        mPhoneET.setKeyListener(null);
        mAddressET = (EditText) linearLayout3.findViewById(R.id.edittext_right);
        mAddressET.setKeyListener(null);


    }

    private void initData() {
        if (type.equals("car")){
            mNameET.setText(mCar.getContact());
            mPhoneET.setText(mCar.getPhone());
            mAddressET.setText(mCar.getCounty()+""+mCar.getVillage());
//            mSubmit.setVisibility(View.GONE);
        }


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
