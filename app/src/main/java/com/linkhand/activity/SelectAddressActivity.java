package com.linkhand.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.linkhand.R;
import com.linkhand.adapter.SelectAddressAdapter;
import com.linkhand.base.BaseActivity;
import com.linkhand.entity.Address;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectAddressActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.listview)
    ListView mListview;
    @Bind(R.id.submit)
    TextView mSubmit;

    SelectAddressAdapter mAdapter;
    List<Address> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        mList = new ArrayList<>();
        mAdapter = new SelectAddressAdapter(this, R.layout.item_select_address, mList);
        mListview.setAdapter(mAdapter);
        getData();
    }

    private void getData() {
        mList.add(new Address(true, getString(R.string.string9), getString(R.string.string10), getString(R.string.string11)));
        mList.add(new Address(false, getString(R.string.string9), getString(R.string.string10), getString(R.string.string11)));
        mList.add(new Address(false, getString(R.string.string9), getString(R.string.string10), getString(R.string.string11)));
        mList.add(new Address(false, getString(R.string.string9), getString(R.string.string10), getString(R.string.string11)));
        mAdapter.notifyDataSetChanged();
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
