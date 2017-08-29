package com.linkhand.baixingguanjia.ui.activity;

import android.app.Notification;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.base.MyApplication;
import com.linkhand.baixingguanjia.utils.SPUtils;

import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class SettingActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.sign_out)
    TextView mSignOutTV;
    @Bind(R.id.title)
    TextView mTitle;
    LinearLayout mUpdateLayout;
    LinearLayout mAboutUsLayout;
    LinearLayout mPhoneLyaout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTitle.setText(R.string.setting);
        mUpdateLayout = (LinearLayout) findViewById(R.id.update_version);
        mAboutUsLayout = (LinearLayout) findViewById(R.id.about_us);
        mPhoneLyaout = (LinearLayout) findViewById(R.id.phone);
        ((TextView) mUpdateLayout.findViewById(R.id.text_left)).setText(R.string.version_update);
        ((TextView) mAboutUsLayout.findViewById(R.id.text_left)).setText(R.string.about_us);
        ((TextView) mPhoneLyaout.findViewById(R.id.text_left)).setText(R.string.hot_phone);
        TextView phoneTV = (TextView) mPhoneLyaout.findViewById(R.id.text_right2);
        phoneTV.setVisibility(View.VISIBLE);
        phoneTV.setText(R.string.hot_phone_num);

    }

    private void jPushMethod() {
        JPushInterface.init(this);
        JPushInterface.resumePush(this);
        JPushInterface.setAlias(this, "", new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {

            }
        });
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(this);
//        builder.statusBarDrawable = R.mipmap.logos;
//        builder.notificationDefaults = Notification.DEFAULT_VIBRATE;//震动
        builder.notificationFlags = Notification.FLAG_SHOW_LIGHTS; //闪烁灯
        JPushInterface.setPushNotificationBuilder(1, builder);
        JPushInterface.setTags(this, null, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
            }
        });
    }


    @OnClick({R.id.back, R.id.sign_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.sign_out:
                SPUtils.put(SettingActivity.this, "userInfo", null);
                MyApplication.setUser(null);
                jPushMethod();
                goAndFinish(LoginActivity.class);
                break;
        }
    }
}
