package com.linkhand.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.linkhand.R;
import com.linkhand.activity.order.OrderDetailActivity;
import com.linkhand.adapter.MyOrderAdapter;
import com.linkhand.entity.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {
    private static final String TAG = "info";
    View view;
    private int type;
    private MyOrderAdapter adapter;
    private List<Order> dataList;
    private ListView listView;

    public OrderFragment(int type) {
        this.type = type;
        switch (type) {
            case 1:
                Log.d(TAG, "OrderFragment: 全部");
                break;
            case 2:
                Log.d(TAG, "OrderFragment: 代付款");
                break;
            case 3:
                Log.d(TAG, "OrderFragment: 待提货");
                break;
            case 4:
                Log.d(TAG, "OrderFragment: 已提货");
                break;

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order, container, false);
        initView();
        initData();
        initListener();
        return view;
    }


    private void initData() {
        dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Order order = new Order();
            if (type == 1) {
                order.setOrderType(i % 3 + 1);
            } else {
                order.setOrderType(type - 1);
            }
            dataList.add(order);

        }

        adapter = new MyOrderAdapter(getActivity(), R.layout.item_my_order, dataList);
        listView.setAdapter(adapter);
    }

    private void initView() {
        listView = (ListView) view.findViewById(R.id.listview);
    }

    private void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                intent.putExtra("orderType", dataList.get(position).getOrderType());
                startActivity(intent);
            }
        });
    }
}
