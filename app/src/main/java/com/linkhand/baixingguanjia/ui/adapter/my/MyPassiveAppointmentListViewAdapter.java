package com.linkhand.baixingguanjia.ui.adapter.my;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.entity.Appointment;
import com.linkhand.baixingguanjia.utils.ImageUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by JCY on 2017/6/27.
 * 说明：  预约 listview适配器
 */

public class MyPassiveAppointmentListViewAdapter extends CommonAdapter {
    List<Appointment> mList;
    Context mContext;


    public MyPassiveAppointmentListViewAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
        this.mContext = context;
        this.mList = datas;


    }


    @Override
    protected void convert(ViewHolder holder, Object item, int position) {
        ImageView image = holder.getView(R.id.image);
        TextView title = holder.getView(R.id.name);
        TextView phone = holder.getView(R.id.phone);
        TextView type = holder.getView(R.id.type);
        TextView time = holder.getView(R.id.time);
        title.setText(mList.get(position).getTitle());
        phone.setText("电话" + " " + mList.get(position).getPhone());
        type.setText(mList.get(position).getType());
        time.setText(mList.get(position).getAdd_time());
        if (!TextUtils.isEmpty(mList.get(position).getLogo_url())) {
            ImageUtils.setCircleImage(image, R.drawable.default_pic_show, ConnectUrl.REQUESTURL_IMAGE + mList.get(position).getLogo_url());
        }else {
            ImageUtils.setCircleDefImage(image, ConnectUrl.REQUESTURL_IMAGE + mList.get(position).getLogo_url());
        }

    }
}
