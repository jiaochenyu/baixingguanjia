package com.linkhand.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.linkhand.R;
import com.linkhand.adapter.PublicWelfareAdapter;
import com.linkhand.entity.PublicWelfare;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PublicWelfareFragment extends Fragment {
    private static final String TAG = "info";
    View view;
    @Bind(R.id.listview)
    PullToRefreshListView mListview;
    private int type;
    private PublicWelfareAdapter adapter;
    private List<PublicWelfare> dataList;

    public PublicWelfareFragment(int type) {
        this.type = type;
        switch (type) {
            case 1:
                Log.d(TAG, "PublicWelfareFragment: 寻人");
                break;
            case 2:
                Log.d(TAG, "PublicWelfareFragment: 寻物");
                break;
            case 3:
                Log.d(TAG, "PublicWelfareFragment: 善行");
                break;

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_public_welfare, container, false);
        initView();
        initData();
        initListener();
        ButterKnife.bind(this, view);
        return view;
    }
    private void initView() {
        mListview = (PullToRefreshListView) view.findViewById(R.id.listview);
    }

    private void initData() {
        dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            PublicWelfare pw = new PublicWelfare();
            if (type == 1) {
                pw.setType(i % 2 + 1);
            } else {
                pw.setType(type - 1);
            }
            dataList.add(pw);

        }

        adapter = new PublicWelfareAdapter (getActivity(), R.layout.item_public_welfare_info, dataList);
        mListview.setAdapter(adapter);
    }



    private void initListener() {
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
