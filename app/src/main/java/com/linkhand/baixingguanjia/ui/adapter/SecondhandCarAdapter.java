package com.linkhand.baixingguanjia.ui.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.entity.Car;
import com.linkhand.baixingguanjia.utils.ImageUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by JCY on 2017/6/22.
 * 说明：
 */

public class SecondhandCarAdapter extends CommonAdapter {
    private Context mContext;
    private List<Car> mList;

    public SecondhandCarAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
        this.mContext = context;
        this.mList = datas;
    }

    @Override
    protected void convert(ViewHolder holder, Object item, int position) {
        ImageView imageView = holder.getView(R.id.image_good);
        TextView titleTV = holder.getView(R.id.good_name);
        TextView distanceTV = holder.getView(R.id.type_tv); //距离
        TextView scaleTV = holder.getView(R.id.price1); //价格
        TextView timeTV = holder.getView(R.id.time); //价格

        ImageUtils.setDefaultImage(imageView,mList.get(position).getLogo_url(),R.drawable.icon_car_default);
        titleTV.setText(mList.get(position).getTitle());
        distanceTV.setText(mList.get(position).getCreator()+"年-"+mList.get(position).getDistance()+"万公里");
        scaleTV.setText("¥ "+mList.get(position).getPrice()+"万");
        timeTV.setText(mList.get(position).getTime());
    }
}
