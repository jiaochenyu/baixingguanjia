package com.linkhand.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.linkhand.R;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by JCY on 2017/6/16.
 * 说明：
 */

public class ReleaseGridViewAdapter extends CommonAdapter {
    private Context mContext;
    private List<String> mList;
    private int[] mIcons;
    public ReleaseGridViewAdapter(Context context, int layoutId, List datas,int[] icons) {
        super(context, layoutId, datas);
        this.mContext = context;
        this.mList = datas;
        this.mIcons = icons;
    }

    @Override
    protected void convert(ViewHolder holder, Object o, int position) {
        TextView textView = holder.getView(R.id.relese_iconName);
        ImageView imageView = holder.getView(R.id.relese_icon);
        textView.setText(mList.get(position));
        Glide.with(mContext)
                .load(mIcons[position])
                .into(imageView);

    }

}
