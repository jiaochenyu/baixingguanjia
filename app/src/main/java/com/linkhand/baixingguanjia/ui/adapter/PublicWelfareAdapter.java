package com.linkhand.baixingguanjia.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.entity.PublicWelfare;
import com.linkhand.baixingguanjia.utils.ImageUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by JCY on 2017/6/22.
 * 说明：
 */

public class PublicWelfareAdapter extends CommonAdapter {
    private Context mContext;
    private List<PublicWelfare> mList;

    public PublicWelfareAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
        this.mContext = context;
        this.mList = datas;
    }

    @Override
    protected void convert(ViewHolder holder, Object item, int position) {
        ImageView imageView = holder.getView(R.id.image);
        TextView titleTV = holder.getView(R.id.title);
        TextView locationTV = holder.getView(R.id.location); //距离
        TextView timeTV = holder.getView(R.id.time); //价格
        if (TextUtils.isEmpty(mList.get(position).getLogo_url())) {
            ImageUtils.setDefaultImage(imageView, "", R.drawable.default_pic_show);
        } else {
            ImageUtils.setDefaultImage(imageView, ConnectUrl.REQUESTURL_IMAGE + mList.get(position).getLogo_url(), R.drawable.default_pic_show);
        }
        titleTV.setText(mList.get(position).getTitle());
        locationTV.setText(mList.get(position).getContent());
        timeTV.setText(mList.get(position).getTime());
    }

}
