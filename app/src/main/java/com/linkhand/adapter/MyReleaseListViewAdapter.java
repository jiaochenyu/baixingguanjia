package com.linkhand.adapter;

import android.content.Context;

import com.linkhand.entity.Release;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by JCY on 2017/6/27.
 * 说明：  我发布的 listview适配器
 */

public class MyReleaseListViewAdapter extends CommonAdapter {
    List<Release> mList;
    Context mContext;

    public MyReleaseListViewAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
        this.mContext = context;
        this.mList = datas;
    }

    @Override
    protected void convert(ViewHolder viewHolder, Object item, int position) {
            
    }
}
