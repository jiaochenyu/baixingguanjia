package com.linkhand.baixingguanjia.ui.adapter.my;

import android.content.Context;

import com.linkhand.baixingguanjia.entity.Collect;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by JCY on 2017/6/27.
 * 说明：  我发布的 listview适配器
 */

public class MyCollectListViewAdapter extends CommonAdapter {
    List<Collect> mList;
    Context mContext;

    public MyCollectListViewAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
        this.mContext = context;
        this.mList = datas;
    }

    @Override
    protected void convert(ViewHolder viewHolder, Object item, int position) {
            
    }
}