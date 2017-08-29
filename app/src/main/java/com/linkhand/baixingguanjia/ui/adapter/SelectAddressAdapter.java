package com.linkhand.baixingguanjia.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.entity.Address;
import com.linkhand.bxgj.lib.utils.DecimalUtils;
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
    private Address mSelectedAdd;
    LatLng mLatLng1;
    LatLng mLatLng2;

    public SelectAddressAdapter(Context context, int layoutId, List datas, Address mSelected, LatLng latLng1) {
        super(context, layoutId, datas);
        this.mContext = context;
        this.mList = datas;
        this.mSelectedAdd = mSelected;
        this.mLatLng1 = latLng1;
    }

    public void setSelectedAdd(Address selectedAdd) {
        mSelectedAdd = selectedAdd;
    }

    public void setLatLng1(LatLng latLng) {
        mLatLng1 = latLng;
    }

    @Override
    protected void convert(ViewHolder viewHolder, Object item, int position) {
        CheckBox checkBox = viewHolder.getView(R.id.checkbox);
        TextView shortTV = viewHolder.getView(R.id.home_address);
        TextView longTV = viewHolder.getView(R.id.address);
        TextView phoneTV = viewHolder.getView(R.id.phone);
        TextView distanceTV = viewHolder.getView(R.id.range);
        checkBox.setChecked(mList.get(position).isFlag());
        shortTV.setText(mList.get(position).getPickup_name());
        longTV.setText(mList.get(position).getPickup_address());
        phoneTV.setText(mList.get(position).getPickup_phone());
        Log.d("mLatLng1", mLatLng1.longitude + "  " + mLatLng1.latitude);
        double distance = DistanceUtil.getDistance(mLatLng1, new LatLng(mList.get(position).getWei(), mList.get(position).getJing()));
        distanceTV.setText(DecimalUtils.unitConversion(distance));
        if (mSelectedAdd != null) {
            if (mList.get(position).getPickup_id().equals(mSelectedAdd.getPickup_id())) {
                mList.get(position).setFlag(true);
            } else {
                mList.get(position).setFlag(false);
            }
            notifyDataSetChanged();
        }
        if (mList.get(position).isFlag()) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }

    }
}
