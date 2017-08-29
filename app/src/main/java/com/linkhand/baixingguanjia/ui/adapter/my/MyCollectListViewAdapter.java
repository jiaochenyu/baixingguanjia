package com.linkhand.baixingguanjia.ui.adapter.my;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.entity.Goods;
import com.linkhand.baixingguanjia.utils.ImageUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by JCY on 2017/6/27.
 * 说明：  我发布的 listview适配器
 */

public class MyCollectListViewAdapter extends CommonAdapter {
    List<Goods> mList;
    Context mContext;
    CancelClick mCancelClick;

    public void setCancelClick(CancelClick cancelClick) {
        mCancelClick = cancelClick;
    }

    public MyCollectListViewAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
        this.mContext = context;
        this.mList = datas;
    }

    @Override
    protected void convert(ViewHolder holder, Object item, final int position) {
        ImageView imageView = holder.getView(R.id.image);
        TextView time = holder.getView(R.id.time);
        TextView describe = holder.getView(R.id.describe);
        TextView cancel = holder.getView(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCancelClick.cancelClick(position);
            }
        });
        ImageUtils.setDefaultNormalImage(imageView, ConnectUrl.REQUESTURL_IMAGE+mList.get(position).getOriginal_img(),R.drawable.default_pic_show);
//        time.setText();
    }

    public interface CancelClick {
        void cancelClick(int position);
    }
}
