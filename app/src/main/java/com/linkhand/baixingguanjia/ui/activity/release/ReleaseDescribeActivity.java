package com.linkhand.baixingguanjia.ui.activity.release;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.entity.EventFlag;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ReleaseDescribeActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.right_text)
    TextView mSaveTV;
    @Bind(R.id.edit)
    EditText mEdit;
    @Bind(R.id.limit_text)
    TextView mLimitText;

    String flag = "";  //carDescribe 二手车
    String content = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_describe);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras != null) {
            flag = extras.getString("flag");
            content = extras.getString("content");
        }
    }

    private void initView() {
        mTitle.setText(R.string.miaoshu);
        mSaveTV.setText(R.string.complete);
        mEdit.setText(content);
    }

    private void initData() {

    }

    @OnClick({R.id.back, R.id.right_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.right_text:
                //点击保存
                content = mEdit.getText().toString();
                if (TextUtils.isEmpty(content)){
                    showToast(R.string.toastMiaoshu);
                    return;
                }
                EventBus.getDefault().post(new EventFlag(flag,content));
                finish();
                break;
        }
    }
}
