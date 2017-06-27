package com.linkhand.kits;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by JCY on 2017/6/16.
 * 说明：
 */

public  class MyCommonAdapter extends CommonAdapter{
    public MyCommonAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, Object o, int position) {

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    protected void convert(MyHolder holder, Object o, int position) {

    }

}
