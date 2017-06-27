package com.linkhand.adapter;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.TextView;

import com.linkhand.R;
import com.linkhand.entity.Address;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by JCY on 2017/6/21.
 * 说明：
 */

public class SelectAddressAdapter extends CommonAdapter {
    private Context mContext;
    private List<Address> mList;

    public SelectAddressAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
        this.mContext = context;
        this.mList = datas;
    }

    @Override
    protected void convert(ViewHolder viewHolder, Object item, int position) {
        CheckBox checkBox = viewHolder.getView(R.id.checkbox);
        TextView shortTV = viewHolder.getView(R.id.home_address);
        TextView longTV = viewHolder.getView(R.id.address);
        TextView phoneTV = viewHolder.getView(R.id.phone);
        checkBox.setChecked(mList.get(position).isFlag());
        shortTV.setText(mList.get(position).getShortAddress());
        longTV.setText(mList.get(position).getLongAddress());
        phoneTV.setText(mList.get(position).getPhone());
    }
}
