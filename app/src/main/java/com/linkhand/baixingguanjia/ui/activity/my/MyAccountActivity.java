package com.linkhand.baixingguanjia.ui.activity.my;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.base.MyApplication;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyCountActivity extends BaseActivity {
    String viewFlag = "";
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.right_text)
    TextView mMingxiTV;
    @Bind(R.id.account)
    TextView mAccountTV;
    @Bind(R.id.layout_pay_silver)
    LinearLayout mLayoutPaySilver;
    @Bind(R.id.layout_pay_gold)
    LinearLayout mLayoutPayGold;
    @Bind(R.id.type_image)
    ImageView mTypeIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_count);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mMingxiTV.setVisibility(View.VISIBLE);
        mMingxiTV.setText(R.string.mingxi);
        if (viewFlag.equals("silver")) {
            mTitle.setText(R.string.silver);
            mTypeIV.setImageDrawable(getResources().getDrawable(R.drawable.icon_sliver_stars));
            mLayoutPaySilver.setVisibility(View.VISIBLE);
            mAccountTV.setText(MyApplication.getUser().getUser_money() + "");
        } else {
            mTitle.setText(R.string.gold);
            mTypeIV.setImageDrawable(getResources().getDrawable(R.drawable.icon_gold_stars));
            mLayoutPayGold.setVisibility(View.VISIBLE);
            mAccountTV.setText(MyApplication.getUser().getUser_gold() + "");
        }
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras != null) {
            viewFlag = extras.getString("viewFlag");
        }
    }

    @OnClick({R.id.back, R.id.right_text, R.id.account, R.id.layout_pay_silver, R.id.layout_pay_gold})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.right_text:
                go(MyCountDetailActivity.class);
                break;
            case R.id.account:

                break;
            case R.id.layout_pay_silver://充值

                break;
            case R.id.layout_pay_gold:
                break;
        }
    }
}
