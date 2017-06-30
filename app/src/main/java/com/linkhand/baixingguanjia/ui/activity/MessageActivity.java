package com.linkhand.baixingguanjia.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.entity.Message;
import com.linkhand.baixingguanjia.ui.adapter.MessageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.header_layout)
    RelativeLayout mHeaderLayout;
    @Bind(R.id.listview)
    ListView mListview;

    MessageAdapter mAdapter;
    List<Message> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        mHeaderLayout.setBackgroundColor(getResources().getColor(R.color.blueTopic));
        mTitle.setText("消息");
        mTitle.setTextColor(getResources().getColor(R.color.colorWhite));
        mBack.setImageDrawable(getResources().getDrawable(R.drawable.icon_left_arrwo_white));

    }

    private void initData() {
        mList = new ArrayList<>();
        mAdapter = new MessageAdapter(this, R.layout.item_message, mList);
        mListview.setAdapter(mAdapter);
        getData();
    }

    private void getData() {
        for (int i = 0; i < 5; i++) {
            mList.add(new Message());
        }
        mAdapter.notifyDataSetChanged();
    }

    private void initListener() {

    }

    @OnClick(R.id.back)
    public void onViewClicked() {
    }

    
}
