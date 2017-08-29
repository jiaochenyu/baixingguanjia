package com.linkhand.baixingguanjia.ui.activity.detail;

import android.os.Bundle;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.entity.Message;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.codeboy.android.aligntextview.CBAlignTextView;

public class MessageDetailActivity extends BaseActivity {

    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.textview)
    CBAlignTextView mTextview;
    Message mMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTitle.setText(mMessage.getTitle());
        mTextview.setText(mMessage.getContent());
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras != null) {
            mMessage = (Message) extras.getSerializable("message");
        }
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
