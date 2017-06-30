package com.linkhand.baixingguanjia.ui.adapter.my;

import android.content.Context;

import com.linkhand.baixingguanjia.entity.FeedBack;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by JCY on 2017/6/28.
 * 说明：
 */

public class MyFeedBackAdapter extends CommonAdapter {
    List<FeedBack> mList;
    Context mContext;

    public MyFeedBackAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
        this.mList = datas;
        this.mContext = context;
    }

    @Override
    protected void convert(ViewHolder viewHolder, Object item, int position) {

    }
}
