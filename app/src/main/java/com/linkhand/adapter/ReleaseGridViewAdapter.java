package com.linkhand.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.linkhand.R;
import com.linkhand.entity.Release;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by JCY on 2017/6/16.
 * 说明：
 */

public class ReleaseGridViewAdapter extends CommonAdapter {
    private Context mContext;
    private List<Release> mList;

    public ReleaseGridViewAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
        this.mContext = context;
        this.mList = datas;
    }

    @Override
    protected void convert(ViewHolder holder, Object o, int position) {
        TextView textView = holder.getView(R.id.relese_iconName);
        ImageView imageView = holder.getView(R.id.relese_icon);
        textView.setText(mList.get(position).getIcon());
        Glide.with(mContext)
                .load(mList.get(position).getIconUrl())
                .placeholder(R.mipmap.icon_bell)
                .into(imageView);

    }

}
