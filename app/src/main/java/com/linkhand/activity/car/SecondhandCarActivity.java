package com.linkhand.activity.car;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.linkhand.R;
import com.linkhand.adapter.SecondhandCarAdapter;
import com.linkhand.entity.Car;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SecondhandCarActivity extends AppCompatActivity {

    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.menu_layout)
    LinearLayout mMenuLayout;
    @Bind(R.id.listview)
    PullToRefreshListView mListview;

    private List<Car> mList;
    private SecondhandCarAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondhand_car);
        ButterKnife.bind(this);
        initData();
        initListener();
    }


    private void initData() {
        mList = new ArrayList<>();
        mAdapter = new SecondhandCarAdapter(this, R.layout.item_car_info, mList);
        mListview.setAdapter(mAdapter);
        getData();
    }

    private void getData() {
        mList.add(new Car());
        mList.add(new Car());
        mList.add(new Car());
        mList.add(new Car());
        mList.add(new Car());
        mList.add(new Car());
        mAdapter.notifyDataSetChanged();
    }

    private void initListener() {
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(SecondhandCarActivity.this, SecondhandCarDetailActivity.class));
            }
        });
    }


    @OnClick({R.id.back, R.id.menu_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                break;
            case R.id.menu_layout:
                break;
        }
    }


}
