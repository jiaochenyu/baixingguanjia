package com.linkhand.baixingguanjia.ui.adapter.my;

import android.content.Context;
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

public class MyCommentListView1Adapter extends CommonAdapter {
    List<Goods> mList;
    Context mContext;


    public MyCommentListView1Adapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
        this.mContext = context;
        this.mList = datas;
    }

    @Override
    protected void convert(ViewHolder holder, Object item, final int position) {
        TextView uname = holder.getView(R.id.user_name);
        TextView time = holder.getView(R.id.time);
        TextView content = holder.getView(R.id.content);
        ImageView imageView = holder.getView(R.id.image_good);
        TextView gname = holder.getView(R.id.good_name);
        TextView gtype = holder.getView(R.id.type_tv); //选择的规格尺寸
        TextView price = holder.getView(R.id.price1); //价格 ¥
        TextView gnum = holder.getView(R.id.goods_num); //数量

        ImageUtils.setDefaultNormalImage(imageView, ConnectUrl.REQUESTURL_IMAGE+mList.get(position).getOriginal_img(),R.drawable.default_pic_show);
        time.setText(mList.get(position).getAdd_time());
        uname.setText(mList.get(position).getNickname());
        content.setText(mList.get(position).getContent());
        gname.setText(mList.get(position).getGoods_name());
        gtype.setText(mList.get(position).getSpec_key_name());
        price.setText("¥"+mList.get(position).getGoods_price());
        gnum.setText("x"+mList.get(position).getGoods_num());
    }


}
