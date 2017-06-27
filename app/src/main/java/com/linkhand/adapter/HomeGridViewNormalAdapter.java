package com.linkhand.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.linkhand.R;
import com.linkhand.entity.HomeNormalIcon;

import java.util.List;

/**
 * Created by JCY on 2017/6/16.
 * 说明：
 */

public class HomeGridViewNormalAdapter extends BaseAdapter {

    Context mContext;
    List<HomeNormalIcon> mList;

    LayoutInflater mInflater;

    public HomeGridViewNormalAdapter(Context context, List<HomeNormalIcon> list) {
        mList = list;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = mInflater.inflate(R.layout.item_home_gridview_normal_item, parent, false);
            holder.mIconIV = (ImageView) convertView.findViewById(R.id.icon);
            holder.mNameTV = (TextView) convertView.findViewById(R.id.iconName);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.mNameTV.setText(mList.get(position).getIconName());
        Glide.with(mContext)
                .load(mList.get(position).getIcon())
                .placeholder(mList.get(position).getIcon())
                .into(holder.mIconIV);


        return convertView;
    }

    private class Holder {
        TextView mNameTV;
        ImageView mIconIV;
    }
}
