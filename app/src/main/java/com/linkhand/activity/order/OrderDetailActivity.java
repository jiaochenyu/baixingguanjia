package com.linkhand.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.linkhand.R;
import com.linkhand.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailActivity extends BaseActivity {
    int orderType = 0;
    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.more_iv)
    ImageView mMoreIV;
    @Bind(R.id.button1)
    TextView mButton1;
    @Bind(R.id.button2)
    TextView mButton2;
    @Bind(R.id.code_layout)
    LinearLayout mCodeLayout;
    @Bind(R.id.close_time)
    TextView mCloseTimeTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_payment);
        ButterKnife.bind(this);
        ininData();
    }

    private void ininData() {
        Intent intent = getIntent();
        orderType = intent.getIntExtra("orderType", 0);
        switch (orderType) {
            case 1:
                //待付款
                initTypeView1();
                break;
            case 2:
                //待提货
                initTypeView2();
                break;
            case 3:
                //已提货
                initTypeView3();
                break;
        }
    }


    private void initTypeView1() {
        mButton1.setVisibility(View.VISIBLE);
        mButton1.setText("取消订单");
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OrderDetailActivity.this, "取消订单", Toast.LENGTH_SHORT).show();
            }
        });

        mButton2.setText("付款");
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OrderDetailActivity.this, "付款", Toast.LENGTH_SHORT).show();
            }
        });
        mCodeLayout.setVisibility(View.GONE);
    }

    private void initTypeView2() {
        mButton1.setVisibility(View.VISIBLE);
        mButton1.setVisibility(View.GONE);

        mButton2.setText("退款");
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OrderDetailActivity.this, "退款", Toast.LENGTH_SHORT).show();
            }
        });
        mCodeLayout.setVisibility(View.VISIBLE);
        mCloseTimeTV.setVisibility(View.GONE);
    }

    private void initTypeView3() {
        mButton1.setText("退货");
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OrderDetailActivity.this, "退货", Toast.LENGTH_SHORT).show();
            }
        });

        mButton2.setText("评价");
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OrderDetailActivity.this, "评价", Toast.LENGTH_SHORT).show();
            }
        });
        mCodeLayout.setVisibility(View.VISIBLE);
        mCloseTimeTV.setVisibility(View.GONE);
    }

    @OnClick({R.id.back, R.id.more_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.more_iv:
                break;

        }
    }
}
