package com.linkhand.baixingguanjia.ui.adapter.my;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.entity.Comment;
import com.linkhand.baixingguanjia.utils.ImageUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by JCY on 2017/6/27.
 * 说明：  我发布的 listview适配器
 */

public class MyCommentListView2Adapter extends CommonAdapter {
    List<Comment> mList;
    Context mContext;
    CancelClick mCancelClick;

    public MyCommentListView2Adapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
        this.mContext = context;
        this.mList = datas;
    }

    public void setCancelClick(CancelClick cancelClick) {
        mCancelClick = cancelClick;
    }

    @Override
    protected void convert(ViewHolder holder, Object item, final int position) {
        TextView uname = holder.getView(R.id.user_name);
        TextView time = holder.getView(R.id.time);
        TextView content = holder.getView(R.id.content);

        ImageView imageView = holder.getView(R.id.image);
        TextView title = holder.getView(R.id.name);
        TextView location = holder.getView(R.id.location);
        TextView type = holder.getView(R.id.type);


        ImageUtils.setDefaultNormalImage(imageView, ConnectUrl.REQUESTURL_IMAGE+mList.get(position).getLogo_url(), R.drawable.default_pic_show);
        title.setText(mList.get(position).getTitle());
        time.setText(mList.get(position).getAdd_time());
        uname.setText(mList.get(position).getUsername());
        content.setText(mList.get(position).getContent());
        location.setText(mList.get(position).getQu()+mList.get(position).getXiaoqu());
        type.setText(mList.get(position).getModular());
    }

    public interface  CancelClick{
        void cancelClick(int position);
    }
}
