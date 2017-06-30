package com.linkhand.baixingguanjia.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseFragment;
import com.linkhand.baixingguanjia.entity.Appointment;
import com.linkhand.baixingguanjia.ui.activity.AppointmentDetailActivity;
import com.linkhand.baixingguanjia.ui.activity.PassiveAppointmentDetailActivity;
import com.linkhand.baixingguanjia.ui.adapter.my.MyAppointmentListViewAdapter;
import com.linkhand.baixingguanjia.ui.adapter.my.MyPassiveAppointmentListViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JCY on 2017/6/27.
 * 说明：  我的预约
 */

public class MyAppointmentFragment extends BaseFragment {
    private static final String TAG = "MyAppointmentFragment_info";

    View view;
    int type;
    @Bind(R.id.listview)
    ListView mListview;
    List<Appointment> mList;
    MyAppointmentListViewAdapter mAdapter;
    MyPassiveAppointmentListViewAdapter mPassiveAdapter;

    public MyAppointmentFragment(int type) {
        this.type = type;
        switch (type) {
            case 1:
                Log.d(TAG, "MyAppointmentFragment: 我预约的");
                break;
            case 2:
                Log.d(TAG, "MyAppointmentFragment: 预约我的");
                break;


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_appoinment, container, false);
        ButterKnife.bind(this, view);
        initView();
        initData();
        initListener();

        return view;
    }

    private void initView() {

    }

    private void initData() {
        mList = new ArrayList<>();
        if (type == 1){
            mAdapter = new MyAppointmentListViewAdapter(getActivity(), R.layout.item_my_appointment, mList);
            mListview.setAdapter(mAdapter);
        }else {
            mPassiveAdapter = new MyPassiveAppointmentListViewAdapter(getActivity(), R.layout.item_my_appointment_passive, mList);
            mListview.setAdapter(mPassiveAdapter);
        }

        getData();
    }

    private void getData() {
        mList.add(new Appointment());
        mList.add(new Appointment());
        mList.add(new Appointment());
        mList.add(new Appointment());
        mList.add(new Appointment());
        if(type == 1){
            mAdapter.notifyDataSetChanged();
        }else {
            mPassiveAdapter.notifyDataSetChanged();
        }

    }

    private void initListener() {

        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (type == 1){
                    startActivity(new Intent(getActivity(), AppointmentDetailActivity.class));
                }else {
                    startActivity(new Intent(getActivity(), PassiveAppointmentDetailActivity.class));
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
