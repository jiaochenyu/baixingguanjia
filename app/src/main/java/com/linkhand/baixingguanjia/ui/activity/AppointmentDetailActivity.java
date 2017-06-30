package com.linkhand.baixingguanjia.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 预约详情
 * 我预约的
 */

public class AppointmentDetailActivity extends BaseActivity {


    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.chakanquanbu)
    TextView mChakanquanbu;
    @Bind(R.id.shouqi)
    TextView mShouqi;
    LinearLayout bottomLayout;
    ScrollView topLayout;
    @Bind(R.id.scrollview)
    ScrollView mScrollview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_detail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTitle.setText("预约详情");
        bottomLayout = (LinearLayout) findViewById(R.id.detail_bottom_layout);
        topLayout = (ScrollView) findViewById(R.id.detail_top_layout);
    }

    @OnClick({R.id.back, R.id.chakanquanbu, R.id.shouqi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                break;
            case R.id.chakanquanbu:
                //控件显示的动画
//                Animation mShowAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
//                        Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF
//                        , -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
//                mShowAnim.setDuration(500);
//                bottomLayout.startAnimation(mShowAnim);
                bottomLayout.setVisibility(View.VISIBLE);
                // scrollview滑动到指定位置
                mScrollview.scrollTo(0, topLayout.getMeasuredHeight()+ topLayout.getMeasuredHeight()/2);


                break;
            case R.id.shouqi:
//                Animation hiddenAmin = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
//                        Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF
//                        , 0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
//                hiddenAmin.setDuration(500);
//                bottomLayout.startAnimation(hiddenAmin);
                bottomLayout.setVisibility(View.GONE);
                break;
        }
    }


}
