package com.linkhand.baixingguanjia.ui.adapter.my;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.entity.Appointment;
import com.linkhand.baixingguanjia.utils.ImageUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;


/**
 * Created by JCY on 2017/6/27.
 * 说明：  预约 listview适配器   我预约的
 */

public class MyAppointmentListViewAdapter extends CommonAdapter {
    List<Appointment> mList;
    Context mContext;

    EvaluateOnClick mEvaluateOnClick;

    public MyAppointmentListViewAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
        this.mContext = context;
        this.mList = datas;
    }

    public void setEvaluateOnClick(EvaluateOnClick evaluateOnClick) {
        mEvaluateOnClick = evaluateOnClick;
    }


    @Override
    protected void convert(ViewHolder holder, Object item, final int position) {
        ImageView image = holder.getView(R.id.image);
        TextView title = holder.getView(R.id.name);
        TextView content = holder.getView(R.id.location);
        TextView type = holder.getView(R.id.type);
        TextView time = holder.getView(R.id.time);
        title.setText(mList.get(position).getTitle());
        content.setText(mList.get(position).getContent());
        type.setText(mList.get(position).getType());
        time.setText(mList.get(position).getAdd_time());
        TextView evaluate = holder.getView(R.id.pingjia);
        //comment 1 已经评论 // 2 为评论
        if (mList.get(position).getComment().equals("1")){
            evaluate.setVisibility(View.GONE);
        }else {
            evaluate.setVisibility(View.VISIBLE);
        }
        evaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEvaluateOnClick.onClick(position);
            }
        });
        if (!TextUtils.isEmpty(mList.get(position).getLogo_url())) {
            ImageUtils.setDefaultNormalImage(image, ConnectUrl.REQUESTURL_IMAGE + mList.get(position).getLogo_url(), R.drawable.default_pic_show);
        }
    }

    public interface EvaluateOnClick {
        void onClick(int position);
    }
}
