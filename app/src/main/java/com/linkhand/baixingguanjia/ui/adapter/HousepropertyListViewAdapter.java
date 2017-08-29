package com.linkhand.baixingguanjia.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.entity.HouseProperty;
import com.linkhand.baixingguanjia.utils.ImageUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by JCY on 2017/6/20.
 * 说明：
 */

public class HousepropertyListViewAdapter extends CommonAdapter {
    private Context mContext;
    private List<HouseProperty> mList;
    int width;

    public HousepropertyListViewAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
        this.mContext = context;
        this.mList = datas;
    }

    @Override
    protected void convert(ViewHolder holder, Object item, int position) {
        ImageView imageView = holder.getView(R.id.image);
        TextView titleTV = holder.getView(R.id.title);
        TextView contentTV = holder.getView(R.id.type_tv); //说明
        TextView scaleTV = holder.getView(R.id.price1); //价格
        TextView locationTV = holder.getView(R.id.location); //地点

        if (TextUtils.isEmpty(mList.get(position).getLogo_url())) {
            ImageUtils.setDefaultImage(imageView, "", R.drawable.default_pic_show);
        } else {
            ImageUtils.setDefaultImage(imageView, ConnectUrl.REQUESTURL_IMAGE + mList.get(position).getLogo_url(), R.drawable.default_pic_show);
        }
        titleTV.setText(mList.get(position).getTitle());
        contentTV.setText(mList.get(position).getContent() +" "+ mList.get(position).getArea() + "平");
        if(mList.get(position).getHost_type().equals("2")){
            scaleTV.setText("¥ " + mList.get(position).getPrice() + "万/套");
        }else {
            scaleTV.setText("¥ " + mList.get(position).getPrice() + "元/月");
        }

        locationTV.setText(mList.get(position).getQuname() + "-" + mList.get(position).getXiaoquname() + "");
    }


}
