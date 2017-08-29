package com.linkhand.baixingguanjia.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.MyApplication;
import com.linkhand.baixingguanjia.customView.NoScrollGridView;
import com.linkhand.baixingguanjia.entity.EventFlag;
import com.linkhand.baixingguanjia.entity.HomeNormalIcon;
import com.linkhand.baixingguanjia.entity.HomeType;
import com.linkhand.baixingguanjia.ui.activity.sort.CommonListActivity;
import com.linkhand.baixingguanjia.ui.activity.sort.EducationActivity;
import com.linkhand.baixingguanjia.ui.activity.sort.HousePropertyActivity;
import com.linkhand.baixingguanjia.ui.activity.sort.HousekeepingActivity;
import com.linkhand.baixingguanjia.ui.activity.sort.IdleGoodsActivity;
import com.linkhand.baixingguanjia.ui.activity.sort.MyAreasActivity;
import com.linkhand.baixingguanjia.ui.activity.sort.PublicWelfareActivity;
import com.linkhand.baixingguanjia.ui.activity.sort.RecruitActivity;
import com.linkhand.baixingguanjia.ui.activity.sort.SecondhandCarActivity;
import com.linkhand.baixingguanjia.utils.ImageUtils;
import com.linkhand.baixingguanjia.utils.SpannableStringUtils;
import com.linkhand.bxgj.lib.pagegridview.MyPageIndicator;
import com.linkhand.bxgj.lib.pagegridview.PageGridView;
import com.linkhand.bxgj.lib.utils.DateTimeUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.iwgang.countdownview.CountdownView;

/**
 * Created by JCY on 2017/6/15.
 * 说明：
 */

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM_TYPE_ONE = 1;
    public static final int ITEM_TYPE_TWO = 2;
    public static final int ITEM_TYPE_THREE = 3;
    public static final int ITEM_TYPE_FOUR = 4;
    public static final int ITEM_TYPE_FIVE = 5;

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
        } else if (viewType == ITEM_TYPE_FIVE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_pagegridview, parent, false);
            ItemFiveViewHolder itemFiveViewHolder = new ItemFiveViewHolder(view);
            return itemFiveViewHolder;
        }
        return null;
    }

    /**
     * @param holder
     * @param position 逻辑处理
     */
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemOneViewHolder) {
            final ItemOneViewHolder itemHolder = (ItemOneViewHolder) holder;
            itemHolder.mNumTV.setText(mTypeList.get(position).getGoods().getBuy_num() + "");
            if (DateTimeUtils.compareTime(mTypeList.get(position).getGoods().getEnd_time() * 1000L) > 0) {
                itemHolder.mCountdownView2.setVisibility(View.VISIBLE);
                itemHolder.mCountdownView2.start(DateTimeUtils.compareTime(mTypeList.get(position).getGoods().getEnd_time() * 1000L));
                itemHolder.mCountdownView2.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
                    @Override
                    public void onEnd(CountdownView cv) {
//                        showToast("倒计时结束");
//                        itemHolder.mNotFinishRL.setVisibility(View.GONE);
//                        itemHolder.mFinishRL.setVisibility(View.VISIBLE);
                        EventBus.getDefault().post(new EventFlag("HotCountDownFinish", ""));
                    }
                });
            } else {
//                itemHolder.mNotFinishRL.setVisibility(View.GONE);
                itemHolder.mFinishRL.setVisibility(View.VISIBLE);
            }
            if (mTypeList.get(position).getGoods().getPicList() == null) {
                ImageUtils.setDefaultNormalImage(itemHolder.mImageView, "", R.drawable.hot_image);
            } else {
                ImageUtils.setDefaultNormalImage(itemHolder.mImageView, mTypeList.get(position).getGoods().getPicList().get(0), R.drawable.hot_image);
            }


        } else if (holder instanceof ItemTwoViewHolder) {
            ItemTwoViewHolder itemHolder = (ItemTwoViewHolder) holder;
            //获取专题的名字
//            itemHolder.mDescribeTV.setText(mTypeList.get(position).getGoods().getCurrentTime() + mContext.getString(R.string.noticeGoodsShow));
            if (DateTimeUtils.compareTime(mTypeList.get(position).getGoods().getStart_time() * 1000L) < 0) {
                itemHolder.mNullBg.setVisibility(View.VISIBLE);
            } else {
                String content = DateTimeUtils.formatMothHour(mTypeList.get(position).getGoods().getStart_time() * 1000L);
                if (!content.contains("结束")) {
                    mTypeList.get(position).getGoods().setContent(content);
                    String var = mTypeList.get(position).getGoods().getContent();
                    itemHolder.mDescribeTV.setText(new SpannableStringUtils().setSpann(
                            var
                            , var.indexOf("日") + 1
                            , var.indexOf("开")
                            , mContext.getResources().getColor(R.color.colorTopic)
                    ));
                }
            }
            if (mTypeList.get(position).getGoods().getGoods_id() == null) {
                itemHolder.mNullBg.setVisibility(View.VISIBLE);
            }
            if (mTypeList.get(position).getGoods().getPicList() == null) {
                ImageUtils.setDefaultNormalImage(itemHolder.mImageView, "", R.drawable.notice_image);
            } else {
                ImageUtils.setDefaultNormalImage(itemHolder.mImageView, mTypeList.get(position).getGoods().getPicList().get(0), R.drawable.notice_image);
            }

        } else if (holder instanceof ItemThreeViewHolder) {
            ItemThreeViewHolder itemHolder = (ItemThreeViewHolder) holder;
            final List<HomeNormalIcon> normalIcons = mTypeList.get(position).getNormalIconList();
            HomeGridViewNormalAdapter adapter = new HomeGridViewNormalAdapter(mContext, normalIcons);

            itemHolder.mGridView.setAdapter(adapter);
            itemHolder.mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                    if (normalIcons.get(pos) != null) {
//                        Toast.makeText(mContext, normalIcons.get(pos).getId(), Toast.LENGTH_SHORT).show();
                        switch (normalIcons.get(pos).getId()) {
                            case "0":
                                mContext.startActivity(new Intent(mContext, MyAreasActivity.class));
                                break;
                            case "1":
                                mContext.startActivity(new Intent(mContext, HousePropertyActivity.class));
                                break;
                            case "2":
                                mContext.startActivity(new Intent(mContext, HousekeepingActivity.class));
                                break;
                            case "3":
                                mContext.startActivity(new Intent(mContext, IdleGoodsActivity.class));
                                break;
                            case "4":
//                                mContext.startActivity(new Intent(mContext, EducationActivity.class));
                                break;
                            case "5":
                                mContext.startActivity(new Intent(mContext, RecruitActivity.class));
                                break;
                            case "6":
                                mContext.startActivity(new Intent(mContext, EducationActivity.class));
                                break;
                            case "7":
                                mContext.startActivity(new Intent(mContext, SecondhandCarActivity.class));
                                break;
                            case "8":
                                mContext.startActivity(new Intent(mContext, PublicWelfareActivity.class));
                                break;
                        }
                    }
                }
            });

        } else if (holder instanceof ItemFourViewHolder) {
            ItemFourViewHolder itemHolder = (ItemFourViewHolder) holder;
            HomeGridViewPopularAdapter adapter = new HomeGridViewPopularAdapter(mContext, mTypeList.get(position).getNormalIconList());
            itemHolder.mGridView.setAdapter(adapter);
            itemHolder.mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mContext.startActivity(new Intent(mContext, SecondhandCarActivity.class));
                }
            });

        } else if (holder instanceof ItemFiveViewHolder) {
            ItemFiveViewHolder itemHolder = (ItemFiveViewHolder) holder;
            final List<HomeNormalIcon> normalIcons = mTypeList.get(position).getNormalIconList();
            HomePageGridviewAdapter adapter = new HomePageGridviewAdapter(mContext, normalIcons);
            itemHolder.mPageGridView.setAdapter(adapter);
            itemHolder.mPageGridView.setOnItemClickListener(adapter);
            itemHolder.mPageGridView.setPageIndicator(itemHolder.pageIndicator);

            itemHolder.mPageGridView.setOnItemClickListener(new PageGridView.OnItemClickListener() {
                @Override
                public void onItemClick(PageGridView pageGridView, int pos) {
                    if (normalIcons.get(pos) != null) {
                        Intent intent = null;
                        int id = Integer.parseInt(normalIcons.get(pos).getId());
                        if (id > 8){
                            //通用
                            Intent intent1 = new Intent(mContext, CommonListActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("id",id+"");
                            intent1.putExtras(bundle);
                            mContext.startActivity(intent1);
                        }
                        switch (normalIcons.get(pos).getId()) {
                            case "0":
                                if (MyApplication.getUser()==null){
                                    Toast.makeText(mContext, "请登录", Toast.LENGTH_SHORT).show();
                                    return;
                                }else if (MyApplication.getUser().getQu_id() == null){
                                    Toast.makeText(mContext, "请完善我的小区", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                mContext.startActivity(new Intent(mContext, MyAreasActivity.class));
                                break;
                            case "1":
                                mContext.startActivity(new Intent(mContext, HousePropertyActivity.class));
                                break;
                            case "2":
                                mContext.startActivity(new Intent(mContext, HousekeepingActivity.class));
                                break;
                            case "3":
                                mContext.startActivity(new Intent(mContext, IdleGoodsActivity.class));
                                break;
                            case "4":
//                                mContext.startActivity(new Intent(mContext, EducationActivity.class));
                                break;
                            case "5":
                                mContext.startActivity(new Intent(mContext, RecruitActivity.class));
                                break;
                            case "6":
                                mContext.startActivity(new Intent(mContext, EducationActivity.class));
                                break;
                            case "7":
                                mContext.startActivity(new Intent(mContext, SecondhandCarActivity.class));
                                break;
                            case "8":
                                mContext.startActivity(new Intent(mContext, PublicWelfareActivity.class));
                                break;
                            default:
                                //通用
                                break;
                        }
                    }
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
        TextView mNumTV;
        CountdownView mCountdownView;
        CountdownView mCountdownView2;
        RelativeLayout mNotFinishRL;
        RelativeLayout mFinishRL;


        public ItemOneViewHolder(View itemView) {
            super(itemView);
            mDescribeTV = (TextView) itemView.findViewById(R.id.describe);
            mImageView = (ImageView) itemView.findViewById(R.id.image);
            mNumTV = (TextView) itemView.findViewById(R.id.num);
            mCountdownView = (CountdownView) itemView.findViewById(R.id.countdownView);
            mCountdownView2 = (CountdownView) itemView.findViewById(R.id.countdownView2);
            mNotFinishRL = (RelativeLayout) itemView.findViewById(R.id.not_finish);
            mFinishRL = (RelativeLayout) itemView.findViewById(R.id.finish_event);
        }
    }

    public class ItemTwoViewHolder extends RecyclerView.ViewHolder {
        //预告
        TextView mDescribeTV;
        ImageView mImageView;
        RelativeLayout mNullBg;

        public ItemTwoViewHolder(View itemView) {
            super(itemView);
            mDescribeTV = (TextView) itemView.findViewById(R.id.describe);
            mImageView = (ImageView) itemView.findViewById(R.id.image);
            mNullBg = (RelativeLayout) itemView.findViewById(R.id.finish_event);
        }
    }

    public class ItemThreeViewHolder extends RecyclerView.ViewHolder {
        //常用
        NoScrollGridView mGridView;

        public ItemThreeViewHolder(View itemView) {
            super(itemView);
            mGridView = (NoScrollGridView) itemView.findViewById(R.id.gridView);
//            mGridView.setOverScrollMode(View.OVER_SCROLL_NEVER);
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

    public class ItemFiveViewHolder extends RecyclerView.ViewHolder {

        PageGridView mPageGridView;
        MyPageIndicator pageIndicator;

        public ItemFiveViewHolder(View itemView) {
            super(itemView);
            mPageGridView = (PageGridView) itemView.findViewById(R.id.pagingGridView);
            pageIndicator = (MyPageIndicator) itemView.findViewById(R.id.pageindicator);

        }
    }

}
