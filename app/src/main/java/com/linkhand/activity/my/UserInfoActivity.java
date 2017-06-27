package com.linkhand.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.linkhand.R;
import com.linkhand.base.BaseActivity;
import com.linkhand.base.ConnectUrl;
import com.linkhand.kits.GlideCircleTransform;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserInfoActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.address_layout)
    RelativeLayout mAddressLayout;
    @Bind(R.id.statu)
    TextView mStatuTV;
    @Bind(R.id.face_layout)
    RelativeLayout mFaceLayout;

    ImageView mHeaderIV;
    TextView mNickTV;
    TextView mUsernameTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        initView();
        initData();
    }



    private void initView() {
        LinearLayout mLayout1 = (LinearLayout) findViewById(R.id.header_layout);
        LinearLayout mLayout2 = (LinearLayout) findViewById(R.id.nick_layout);
        LinearLayout mLayout3 = (LinearLayout) findViewById(R.id.username_layout);
        LinearLayout mLayout4 = (LinearLayout) findViewById(R.id.password_layout);
        TextView mTextRight1 = (TextView) mLayout1.findViewById(R.id.text_right);
        TextView mTextLeft1 = (TextView) mLayout1.findViewById(R.id.text_left);
        mHeaderIV = (ImageView) mLayout1.findViewById(R.id.image);
        mHeaderIV.setVisibility(View.VISIBLE);
        mTextLeft1.setText("头像");
        mTextRight1.setVisibility(View.GONE);

        TextView mTextRight2 = (TextView) mLayout2.findViewById(R.id.text_right);
        TextView mTextLeft2 = (TextView) mLayout2.findViewById(R.id.text_left);
        mTextRight2.setText("鲁诺本");
        mTextLeft2.setText("昵称");

        TextView mTextRight3 = (TextView) mLayout3.findViewById(R.id.text_right);
        TextView mTextLeft3 = (TextView) mLayout3.findViewById(R.id.text_left);
        mTextRight3.setText("helloworld");
        mTextLeft3.setText("用户名");

        TextView mTextRight4 = (TextView) mLayout4.findViewById(R.id.text_right);
        TextView mTextLeft4 = (TextView) mLayout4.findViewById(R.id.text_left);
        mTextRight4.setVisibility(View.GONE);
        mTextLeft4.setText("修改密码");


    }

    private void initData() {
        Glide.with(this)
                .load(ConnectUrl.testUrl)
                .placeholder(R.mipmap.ic_launcher)
                .fitCenter()
                .transform(new GlideCircleTransform(this))
                .into(mHeaderIV);
    }


    @OnClick({R.id.back, R.id.address_layout, R.id.face_layout, R.id.header_layout, R.id.nick_layout, R.id.username_layout, R.id.password_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.address_layout:
                startActivity(new Intent(UserInfoActivity.this,AddCommunityActivity.class));
                break;
            case R.id.face_layout:
                startActivity(new Intent(UserInfoActivity.this,FaceApproveActivity.class));
                break;
            case R.id.header_layout:

                break;
            case R.id.nick_layout:

                break;
            case R.id.username_layout:

                break;
            case R.id.password_layout:

                break;
        }
    }
}
