package com.linkhand.baixingguanjia.ui.adapter;

import android.content.Context;

import com.linkhand.baixingguanjia.entity.Education;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by JCY on 2017/6/20.
 * 说明：
 */

public class EducationListViewAdapter extends CommonAdapter {
    private Context mContext;
    private List<Education> mList;
    int width;

    public EducationListViewAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
        this.mContext = context;
        this.mList = datas;
    }

    @Override
    protected void convert(ViewHolder viewHolder, Object item, int position) {

    }



}