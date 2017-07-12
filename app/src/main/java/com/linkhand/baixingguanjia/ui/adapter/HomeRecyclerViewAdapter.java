package com.linkhand.baixingguanjia.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.customView.NoScrollGridView;
import com.linkhand.baixingguanjia.entity.HomeType;
import com.linkhand.baixingguanjia.utils.SpannableStringUtils;
import com.linkhand.baixingguanjia.ui.activity.sort.EducationActivity;
import com.linkhand.baixingguanjia.ui.activity.sort.HousePropertyActivity;
import com.linkhand.baixingguanjia.ui.activity.sort.HousekeepingActivity;
import com.linkhand.baixingguanjia.ui.activity.sort.IdleGoodsActivity;
import com.linkhand.baixingguanjia.ui.activity.sort.MyAreasActivity;
import com.linkhand.baixingguanjia.ui.activity.sort.PublicWelfareActivity;
import com.linkhand.baixingguanjia.ui.activity.sort.RecruitActivity;
import com.linkhand.baixingguanjia.ui.activity.sort.SecondhandCarActivity;

import java.util.List;

/**
 * Created by JCY on 2017/6/15.
 * 说明：
 */

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM_TYPE_ONE = 1;
    public static final int ITEM_TYPE_TWO = 2;
    public static final int ITEM_TYPE_THREE = 3;
    public static final int ITEM_TYPE_FOUR = 4;

    List<HomeType> mTypeList;
    Context mContext;

    private OnItemClickListener mOnItemClickListener = null;

    //定义监听接口
    public interface OnItemClickListener {

        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public HomeRecyclerViewAdapter(Context context, List<HomeType> typeList) {
        this.mTypeList = typeList;
        mContext = context;
    }

    @Override
    public int getItemCount() {
        return mTypeList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mTypeList.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_ONE) {
            //绑定活动头部的视图
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_hot, parent, false);
            ItemOneViewHolder itemOneViewHolder = new ItemOneViewHolder(view);
            return itemOneViewHolder;
        } else if (viewType == ITEM_TYPE_TWO) {
            //绑定活动的详细信息
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_notice, parent, false);
            ItemTwoViewHolder itemTwoViewHolder = new ItemTwoViewHolder(view);
            return itemTwoViewHolder;
        } else if (viewType == ITEM_TYPE_THREE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_gridview_normal, parent, false);
            ItemThreeViewHolder itemThreeViewHolder = new ItemThreeViewHolder(view);
            return itemThreeViewHolder;

        } else if (viewType == ITEM_TYPE_FOUR) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_gridview_popular, parent, false);
            ItemFourViewHolder itemFourViewHolder = new ItemFourViewHolder(view);
            return itemFourViewHolder;
        }
        return null;
    }

    /**
     * @param holder
     * @param position 逻辑处理
     */
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemOneViewHolder) {
            ItemOneViewHolder itemHolder = (ItemOneViewHolder) holder;
            //获取专题的名字
//            itemHolder.mDescribeTV.setText(mTypeList.get(position).getContent());
            String var = mTypeList.get(position).getContent();
            itemHolder.mDescribeTV.setText(new SpannableStringUtils().setSpann(
                    var
                    , var.indexOf("件")
                    , var.indexOf("剩") + 1
                    , mContext.getResources().getColor(R.color.blackText)));
            Glide.with(mContext)
                    .load(mTypeList.get(position).getImageUrl())
                    .placeholder(R.drawable.hot_image)
                    .into(itemHolder.mImageView);

        } else if (holder instanceof ItemTwoViewHolder) {
            ItemTwoViewHolder itemHolder = (ItemTwoViewHolder) holder;
            //获取专题的名字
            itemHolder.mDescribeTV.setText(mTypeList.get(position).getContent());
            Glide.with(mContext)
                    .load(mTypeList.get(position).getImageUrl())
                    .placeholder(R.drawable.notice_image)
                    .into(itemHolder.mImageView);

        } else if (holder instanceof ItemThreeViewHolder) {
            ItemThreeViewHolder itemHolder = (ItemThreeViewHolder) holder;
            HomeGridViewNormalAdapter adapter = new HomeGridViewNormalAdapter(mContext, mTypeList.get(position).getNormalIconList());
            itemHolder.mGridView.setAdapter(adapter);
            itemHolder.mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0:
                            mContext.startActivity(new Intent(mContext, MyAreasActivity.class));
                            break;
                        case 1:
                            mContext.startActivity(new Intent(mContext, HousePropertyActivity.class));
                            break;
                        case 2:
                            mContext.startActivity(new Intent(mContext, SecondhandCarActivity.class));
                            break;
                        case 3:
                            mContext.startActivity(new Intent(mContext, HousekeepingActivity.class));
                            break;
                        case 4:
                            mContext.startActivity(new Intent(mContext, EducationActivity.class));
                            break;
                        case 5:
                            mContext.startActivity(new Intent(mContext, IdleGoodsActivity.class));
                            break;
                        case 6:
                            mContext.startActivity(new Intent(mContext, RecruitActivity.class));
                            break;
                        case 7:
                            mContext.startActivity(new Intent(mContext, PublicWelfareActivity.class));
                            break;
                    }
                }
            });

        } else if (holder instanceof ItemFourViewHolder) {
            ItemFourViewHolder itemHolder = (ItemFourViewHolder) holder;
            HomeGridViewPopularAdapter adapter = new HomeGridViewPopularAdapter(mContext, mTypeList.get(position).getPopularIconList());
            itemHolder.mGridView.setAdapter(adapter);
            itemHolder.mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mContext.startActivity(new Intent(mContext, SecondhandCarActivity.class));
                }
            });

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(holder.itemView, holder.getLayoutPosition());
            }
        });

    }


    public class ItemOneViewHolder extends RecyclerView.ViewHolder {
        //热卖
        TextView mDescribeTV;
        ImageView mImageView;

        public ItemOneViewHolder(View itemView) {
            super(itemView);
            mDescribeTV = (TextView) itemView.findViewById(R.id.describe);
            mImageView = (ImageView) itemView.findViewById(R.id.image);

        }
    }

    public class ItemTwoViewHolder extends RecyclerView.ViewHolder {
        //预告
        TextView mDescribeTV;
        ImageView mImageView;

        public ItemTwoViewHolder(View itemView) {
            super(itemView);
            mDescribeTV = (TextView) itemView.findViewById(R.id.describe);
            mImageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }

    public class ItemThreeViewHolder extends RecyclerView.ViewHolder {
        //常用
       NoScrollGridView mGridView;

        public ItemThreeViewHolder(View itemView) {
            super(itemView);
            mGridView = (NoScrollGridView) itemView.findViewById(R.id.gridView);
            mGridView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
    }

    public class ItemFourViewHolder extends RecyclerView.ViewHolder {
        //二手车
       NoScrollGridView mGridView;

        public ItemFourViewHolder(View itemView) {
            super(itemView);
            mGridView = (NoScrollGridView) itemView.findViewById(R.id.gridView);
            mGridView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
    }


}
