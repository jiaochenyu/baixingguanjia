package com.linkhand.baixingguanjia.ui.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.entity.Areas;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by JCY on 2017/6/20.
 * 说明：
 */

public class AreaListViewAdapter extends CommonAdapter {
    private Context mContext;
    private List<Areas> mList;
    int width;

    public AreaListViewAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
        this.mContext = context;
        this.mList = datas;
    }

    @Override
    protected void convert(ViewHolder holder, Object item, int position) {
        ImageView imageView = holder.getView(R.id.image_good);
        TextView title = holder.getView(R.id.title);
        TextView type = holder.getView(R.id.type_tv);
        TextView time = holder.getView(R.id.time);
        Areas areas = mList.get(position);
        if (areas.getCategory() == 0) {//0系统消息1活动消息
            title.setText(R.string.system_message);
            imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.xitongxiaoxi));
        } else {
            title.setText(R.string.action_message);
            imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.huodongxiaoxi));
        }

        time.setText(areas.getAdd_time().split(" ")[0]);


    }


}
