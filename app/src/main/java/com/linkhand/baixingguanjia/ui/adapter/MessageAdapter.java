package com.linkhand.baixingguanjia.ui.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.entity.Message;
import com.linkhand.baixingguanjia.utils.ImageUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by JCY on 2017/6/26.
 * 说明：
 */

public class MessageAdapter extends CommonAdapter {
    private List<Message> mList;
    private Context mContext;

    public MessageAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
        this.mContext = context;
        this.mList = datas;
    }

    @Override
    protected void convert(ViewHolder holder, Object item, int position) {
        ImageView imageView = holder.getView(R.id.image);
        TextView title = holder.getView(R.id.title);
        TextView content = holder.getView(R.id.content);
        TextView time = holder.getView(R.id.time);
        TextView state = holder.getView(R.id.state);
        if (mList.get(position).getTitle().contains("系统")) {
            ImageUtils.setDefaultNormalImage(imageView, "", R.drawable.icon_bell_blue);
        } else if (mList.get(position).getTitle().contains("反馈")) {
            ImageUtils.setDefaultNormalImage(imageView, "", R.drawable.icon_bell_blue);
        }
        if (mList.get(position).getState().equals("1")) {//1未读 2已读
            state.setText("未读");
        } else {
            state.setText("已读");
        }
        title.setText(mList.get(position).getTitle());
        content.setText(mList.get(position).getContent());
        time.setText(mList.get(position).getAdd_time());

    }
}
