package com.linkhand.baixingguanjia.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.entity.Recruit;
import com.linkhand.baixingguanjia.utils.ImageUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by JCY on 2017/6/20.
 * 说明：
 */

public class RecruitListViewAdapter extends CommonAdapter {
    private Context mContext;
    private List<Recruit> mList;
    int width;

    public RecruitListViewAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
        this.mContext = context;
        this.mList = datas;
    }

    @Override
    protected void convert(ViewHolder holder, Object item, int position) {
        ImageView imageView = holder.getView(R.id.image);
        TextView titleTV = holder.getView(R.id.title);
        TextView locationTV = holder.getView(R.id.location); //距离
        TextView scaleTV = holder.getView(R.id.price1); //价格
        TextView timeTV = holder.getView(R.id.time); //价格

        if (TextUtils.isEmpty(mList.get(position).getLogo_url())) {
            ImageUtils.setDefaultImage(imageView, "", R.drawable.default_pic_show);
        } else {
            ImageUtils.setDefaultImage(imageView, ConnectUrl.REQUESTURL_IMAGE + mList.get(position).getLogo_url(), R.drawable.default_pic_show);
        }


        String fuli = mList.get(position).getWelfare();
        String showFuli = "";
        if (!TextUtils.isEmpty(fuli)) {
            if (fuli.contains("|||")) {
                String[] str = fuli.split("|||");
//                for (String s : str) {
//                }
                showFuli = str[0] + "-" + str[1];
            } else {
                showFuli = fuli;
            }
        }
        titleTV.setText(mList.get(position).getTitle() + "-" + showFuli);
        locationTV.setText(mList.get(position).getCompany());
        scaleTV.setText("¥ " + mList.get(position).getDescriptions() + "元/月");
        timeTV.setText(mList.get(position).getTime());
    }


}
