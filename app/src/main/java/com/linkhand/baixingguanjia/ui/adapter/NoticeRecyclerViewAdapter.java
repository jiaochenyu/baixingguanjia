package com.linkhand.baixingguanjia.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.entity.Notice;
import com.linkhand.baixingguanjia.utils.SpannableStringUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by JCY on 2017/6/16.
 * 说明：
 */

public class NoticeRecyclerViewAdapter extends CommonAdapter {
    private Context mContext;
    private List<Notice> mList;

    public NoticeRecyclerViewAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
        this.mContext = context;
        this.mList = datas;
    }


    @Override
    protected void convert(ViewHolder holder, Object o, int position) {
        TextView mTimeTV = holder.getView(R.id.time);
        ImageView mImageIV = holder.getView(R.id.image);
        TextView mContent = holder.getView(R.id.describe);
        mTimeTV.setText(mList.get(position).getDate());

        String var = mList.get(position).getContent();
        mContent.setText(new SpannableStringUtils().setSpann(
                var
                , var.indexOf("日") + 1
                , var.indexOf("开")
                , mContext.getResources().getColor(R.color.colorTopic)
        ));
        Glide.with(mContext)
                .load(mList.get(position).getImageUrl())
                .placeholder(R.drawable.notice_image)
                .into(mImageIV);

        LinearLayout linearLayout = holder.getView(R.id.line);
        if (position == mList.size()-1) {
            linearLayout.setVisibility(View.VISIBLE);
        }
    }
}
