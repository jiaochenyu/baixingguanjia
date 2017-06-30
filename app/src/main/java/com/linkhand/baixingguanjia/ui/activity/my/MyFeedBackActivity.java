package com.linkhand.baixingguanjia.ui.activity.my;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.entity.FeedBack;
import com.linkhand.baixingguanjia.ui.adapter.my.MyFeedBackAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
/*
*
* 我的反馈
 */

public class MyFeedBackActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.listview)
    ListView mListview;
    List<FeedBack> mList;

    MyFeedBackAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_feed_back);
        ButterKnife.bind(this);
        initView();
        initData();
    }


    private void initView() {
        mTitle.setText("我的反馈");
    }

    private void initData() {
        mList = new ArrayList<>();
        mAdapter = new MyFeedBackAdapter(this,R.layout.item_my_feed_back,mList);
        mListview.setAdapter(mAdapter);
        getData();
    }

    private void getData() {
        mList.add(new FeedBack());
        mList.add(new FeedBack());
        mList.add(new FeedBack());
        mList.add(new FeedBack());
        mList.add(new FeedBack());
        mAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }

    
}
