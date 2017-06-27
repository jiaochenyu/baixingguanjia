package com.linkhand.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linkhand.R;
import com.linkhand.activity.SelectAddressActivity;
import com.linkhand.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfirmOrderActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.address_ry)
    LinearLayout mAddressLayout;
    @Bind(R.id.submit)
    TextView mSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.address_ry, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.address_ry:
                startActivity(new Intent(ConfirmOrderActivity.this, SelectAddressActivity.class));
                break;
            case R.id.submit:
                break;
        }
    }
}
