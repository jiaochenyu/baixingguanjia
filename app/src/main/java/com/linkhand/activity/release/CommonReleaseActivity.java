package com.linkhand.activity.release;

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

public class CommonReleaseActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.text)
    TextView mTextView;

    EditText mTitleET;
    EditText mAbstractET;
    @Bind(R.id.content_edit)
    EditText mContentET;
    @Bind(R.id.submit)
    TextView mSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_release);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        mTextView.getBackground().setAlpha(50); //背景透明度
        mTitle.setText("发布");
        LinearLayout layout1 = (LinearLayout) findViewById(R.id.title_layout);
        LinearLayout layout2 = (LinearLayout) findViewById(R.id.abstract_layout);
        ((TextView)layout1.findViewById(R.id.text_left)).setText("标题");
        ((TextView)layout2.findViewById(R.id.text_left)).setText("摘要");
        mTitleET = (EditText) layout1.findViewById(R.id.edittext_right);
        mTitleET = (EditText) layout1.findViewById(R.id.edittext_right);
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
