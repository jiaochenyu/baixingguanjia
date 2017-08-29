package com.linkhand.baixingguanjia.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.entity.HomeNormalIcon;
import com.linkhand.baixingguanjia.utils.ImageUtils;
import com.linkhand.bxgj.lib.pagegridview.PageGridView;

import java.util.List;

/**
 * Created by JCY on 2017/7/27.
 * 说明：
 */

public class HomePageGridviewAdapter extends PageGridView.PagingAdapter<HomePageGridviewAdapter.MyVH> implements PageGridView.OnItemClickListener {
    Context mContext;
    List<HomeNormalIcon> mList;
    int width;

    public HomePageGridviewAdapter(Context context, List<HomeNormalIcon> list) {
        this.mContext = context;
        this.mList = list;
        width = context.getResources().getDisplayMetrics().widthPixels / 4;
    }

    @Override
    public MyVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_gridview_normal_item, parent, false);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = width;
        params.width = width;
        view.setLayoutParams(params);
        return new MyVH(view);
    }

    @Override
    public void onBindViewHolder(MyVH holder, int position) {
        if (mList.get(position) != null) {
            holder.mNameTV.setVisibility(View.VISIBLE);
            holder.mIconIV.setVisibility(View.VISIBLE);

            holder.mNameTV.setText(mList.get(position).getName());
            if (mList.get(position).getName().contains("房")) {
                ImageUtils.setDefaultNormalImage(holder.mIconIV, mList.get(position).getTubiao(), R.drawable.icon_house_property_orange);
            } else if (mList.get(position).getName().contains("车")) {
                ImageUtils.setDefaultNormalImage(holder.mIconIV, mList.get(position).getTubiao(), R.drawable.icon_car_blue);
            } else if (mList.get(position).getName().contains("物")) {
                ImageUtils.setDefaultNormalImage(holder.mIconIV, mList.get(position).getTubiao(), R.drawable.icon_idle_green);
            } else if (mList.get(position).getName().contains("聘")) {
                ImageUtils.setDefaultNormalImage(holder.mIconIV, mList.get(position).getTubiao(), R.drawable.icon_recruit_orang);
            } else if (mList.get(position).getName().contains("育")) {
                ImageUtils.setDefaultNormalImage(holder.mIconIV, mList.get(position).getTubiao(), R.drawable.icon_education_blue);
            } else if (mList.get(position).getName().contains("车")) {
                ImageUtils.setDefaultNormalImage(holder.mIconIV, mList.get(position).getTubiao(), R.drawable.icon_car_blue);
            } else if (mList.get(position).getName().contains("益")) {
                ImageUtils.setDefaultNormalImage(holder.mIconIV, mList.get(position).getTubiao(), R.drawable.icon_public_welfare);
            } else {
                ImageUtils.setDefaultNormalImage(holder.mIconIV, mList.get(position).getTubiao(), R.drawable.icon_notice_red);
            }
        } else {
            holder.mNameTV.setVisibility(View.GONE);
            holder.mIconIV.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public List getData() {
        return mList;
    }

    @Override
    public Object getEmpty() {
        return null;
    }

    @Override
    public void onItemClick(PageGridView pageGridView, int position) {

    }

    public static class MyVH extends RecyclerView.ViewHolder {
        TextView mNameTV;
        ImageView mIconIV;

        public MyVH(View itemView) {
            super(itemView);
            mNameTV = (TextView) itemView.findViewById(R.id.iconName);
            mIconIV = (ImageView) itemView.findViewById(R.id.icon);
        }
    }
}
