package com.linkhand.baixingguanjia.ui.activity.my;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.base.MyApplication;
import com.linkhand.baixingguanjia.entity.EventFlag;

import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的账户金额
 */

public class MyAccountActivity extends BaseActivity {

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
    String viewFlag = "silver";  //gold 、silver

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_count);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        if (extras != null) {
            viewFlag = extras.getString("viewFlag", "silver");
        }
        super.getBundleExtras(extras);
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
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Subscribe
    public void onEvent(EventFlag eventFlag) {
        if (eventFlag.getFlag().equals("paySilver")) {
            if (viewFlag.equals("silver")) {
                mAccountTV.setText(MyApplication.getUser().getUser_money());
            }
        }
        if (eventFlag.getFlag().equals("updateGold")) {
            if (viewFlag.equals("gold")) {
                mAccountTV.setText(MyApplication.getUser().getUser_gold());
            }
        }


    }

    @OnClick({R.id.back, R.id.right_text, R.id.layout_pay_silver, R.id.layout_pay_gold})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.right_text:
                Bundle bundle = new Bundle();
                bundle.putString("viewFlag", viewFlag);
                go(MyAccountDetailActivity.class, bundle);
                break;
            case R.id.layout_pay_silver://充值
                go(PayCheckActivity.class);
                break;
            case R.id.layout_pay_gold://物业费充值
                if (MyApplication.getUser().getUser_gold() != 0) {
                    go(PropertyPayActivity.class);
                } else {
                    showToast(R.string.nogold);
                }
                break;
        }
    }
}
