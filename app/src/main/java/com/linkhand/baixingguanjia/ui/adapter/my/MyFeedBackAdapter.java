package com.linkhand.baixingguanjia.ui.adapter.my;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.entity.FeedBack;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by JCY on 2017/6/28.
 * 说明：
 */

public class MyFeedBackAdapter extends CommonAdapter {
    List<FeedBack> mList;
    Context mContext;
    FeedBackOnClick mFeedBackOnClick;

    public void setFeedBackOnClick(FeedBackOnClick feedBackOnClick) {
        mFeedBackOnClick = feedBackOnClick;
    }

    public MyFeedBackAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
        this.mList = datas;
        this.mContext = context;
    }

    @Override
    protected void convert(ViewHolder holder, Object item, final int position) {
        TextView content = holder.getView(R.id.content);
        TextView time = holder.getView(R.id.time);
        content.setText(mList.get(position).getContent());
        time.setText(mList.get(position).getAdd_time());
        TextView feedbackBtn = holder.getView(R.id.feedback_btn);
        feedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFeedBackOnClick.onclick(position);
            }
        });
    }

    public interface FeedBackOnClick {
        void onclick(int pos);
    }
}
