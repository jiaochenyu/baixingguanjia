package com.linkhand.baixingguanjia.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.entity.Notice;
import com.linkhand.baixingguanjia.utils.SpannableStringUtils;
import com.linkhand.bxgj.lib.utils.DateTimeUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by JCY on 2017/6/16.
 * 说明：
 */

public class NoticeListViewAdapter extends CommonAdapter {
    private Context mContext;
    private List<Notice> mList;

    public NoticeListViewAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
        this.mContext = context;
        this.mList = datas;
    }


    @Override
    protected void convert(ViewHolder holder, Object o, int position) {
        LinearLayout linearLayout = holder.getView(R.id.line);
        if (position == mList.size() - 1) {
            linearLayout.setVisibility(View.VISIBLE);
        }
        TextView mTimeTV = holder.getView(R.id.time);
        ImageView mImageIV = holder.getView(R.id.image);
        TextView mContent = holder.getView(R.id.describe);

        String time = DateTimeUtils.formatMoth(mList.get(position).getStart_time() * 1000L);
        mTimeTV.setText(time);

        String content = DateTimeUtils.formatMothHour(mList.get(position).getStart_time() * 1000L);
        if (!content.contains("结束")) {
            mList.get(position).setContent(content);
            String var = mList.get(position).getContent();
            mContent.setText(new SpannableStringUtils().setSpann(
                    var
                    , var.indexOf("日") + 1
                    , var.indexOf("开")
                    , mContext.getResources().getColor(R.color.colorTopic)
            ));
        }
        Glide.with(mContext)
                .load(ConnectUrl.REQUESTURL_IMAGE + mList.get(position).getOriginal_img())
                .placeholder(R.drawable.notice_image)
                .into(mImageIV);
    }
}
