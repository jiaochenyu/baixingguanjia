package com.linkhand.baixingguanjia.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseFragment;

/**
 * Created by JCY on 2017/7/26.
 * 说明：
 */

public class HousePropertyRentFragment extends BaseFragment {
    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, null);
        return mView;
    }
}
