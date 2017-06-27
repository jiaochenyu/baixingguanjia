package com.linkhand.adapter;

import android.content.Context;

import com.linkhand.entity.Car;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by JCY on 2017/6/22.
 * 说明：
 */

public class SecondhandCarAdapter extends CommonAdapter {
    private Context mContext;
    private List<Car> mList;

    public SecondhandCarAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
        this.mContext = context;
        this.mList = datas;
    }

    @Override
    protected void convert(ViewHolder viewHolder, Object item, int position) {

    }
}
