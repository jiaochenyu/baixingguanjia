package com.linkhand.baixingguanjia.ui.adapter.my;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.entity.Collect;
import com.linkhand.baixingguanjia.utils.ImageUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by JCY on 2017/6/27.
 * 说明：  我发布的 listview适配器
 */

public class MyCollectListView2Adapter extends CommonAdapter {
    List<Collect> mList;
    Context mContext;
    CancelClick mCancelClick;

    public MyCollectListView2Adapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
        this.mContext = context;
        this.mList = datas;
    }

    public void setCancelClick(CancelClick cancelClick) {
        mCancelClick = cancelClick;
    }

    @Override
    protected void convert(ViewHolder holder, Object item, final int position) {
        ImageView imageView = holder.getView(R.id.image);
        TextView title = holder.getView(R.id.name);
        TextView time = holder.getView(R.id.time);
        TextView location = holder.getView(R.id.location);
        TextView type = holder.getView(R.id.type);
        TextView cancel = holder.getView(R.id.cancel);

        ImageUtils.setDefaultNormalImage(imageView, ConnectUrl.REQUESTURL_IMAGE+mList.get(position).getLogo_url(), R.drawable.default_pic_show);
        title.setText(mList.get(position).getTitle());
        time.setText(mList.get(position).getAdd_time());
        location.setText(mList.get(position).getQuname() + " " + mList.get(position).getXiaoquname());
        type.setText(mList.get(position).getModular());

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCancelClick.cancelClick(position);
            }
        });
    }

    public interface  CancelClick{
        void cancelClick(int position);
    }
}
