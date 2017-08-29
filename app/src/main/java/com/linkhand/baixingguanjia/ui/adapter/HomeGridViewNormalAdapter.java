package com.linkhand.baixingguanjia.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.entity.HomeNormalIcon;
import com.linkhand.baixingguanjia.utils.ImageUtils;

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
        holder.mNameTV.setText(mList.get(position).getName());
        if (mList.get(position).getName().contains("房")){
            ImageUtils.setDefaultNormalImage(holder.mIconIV,mList.get(position).getTubiao(),R.drawable.icon_house_property_orange);
        }else if (mList.get(position).getName().contains("车")){
            ImageUtils.setDefaultNormalImage(holder.mIconIV,mList.get(position).getTubiao(),R.drawable.icon_car_blue);
        }else if (mList.get(position).getName().contains("物")){
            ImageUtils.setDefaultNormalImage(holder.mIconIV,mList.get(position).getTubiao(),R.drawable.icon_idle_green);
        }else if (mList.get(position).getName().contains("聘")){
            ImageUtils.setDefaultNormalImage(holder.mIconIV,mList.get(position).getTubiao(),R.drawable.icon_recruit_orang);
        }else if (mList.get(position).getName().contains("育")){
            ImageUtils.setDefaultNormalImage(holder.mIconIV,mList.get(position).getTubiao(),R.drawable.icon_education_blue);
        }else if (mList.get(position).getName().contains("车")){
            ImageUtils.setDefaultNormalImage(holder.mIconIV,mList.get(position).getTubiao(),R.drawable.icon_car_blue);
        }else if (mList.get(position).getName().contains("益")){
            ImageUtils.setDefaultNormalImage(holder.mIconIV,mList.get(position).getTubiao(),R.drawable.icon_public_welfare);
        }else {
            ImageUtils.setDefaultNormalImage(holder.mIconIV,mList.get(position).getTubiao(),R.drawable.icon_notice_red);
        }
//        switch (position) {
//            case 0:
//                ImageUtils.setDefaultNormalImage(holder.mIconIV,mList.get(position).getTubiao(),R.drawable.icon_notice_red);
//                break;
//            case 1:
//                ImageUtils.setDefaultNormalImage(holder.mIconIV,mList.get(position).getTubiao(),R.drawable.icon_house_property_orange);
//                break;
//            case 2:
//                break;
//            case 3:
//                ImageUtils.setDefaultNormalImage(holder.mIconIV,mList.get(position).getTubiao(),R.drawable.icon_house_keep);
//
//                break;
//            case 4:
//                ImageUtils.setDefaultNormalImage(holder.mIconIV,mList.get(position).getTubiao(),R.drawable.icon_education_blue);
//
//                break;
//            case 5:
//                ImageUtils.setDefaultNormalImage(holder.mIconIV,mList.get(position).getTubiao(),R.drawable.icon_idle_green);
//
//                break;
//            case 6:
//                ImageUtils.setDefaultNormalImage(holder.mIconIV,mList.get(position).getTubiao(),R.drawable.icon_recruit_orang);
//                break;
//            case 7:
//                ImageUtils.setDefaultNormalImage(holder.mIconIV,mList.get(position).getTubiao(),R.drawable.icon_public_welfare);
//                break;
//        }
//        Glide.with(mContext)
//                .load(mList.get(position).getIcon())
//                .placeholder(mList.get(position).getIcon())
//                .into(holder.mIconIV);


        return convertView;
    }

    private class Holder {
        TextView mNameTV;
        ImageView mIconIV;
    }
}
