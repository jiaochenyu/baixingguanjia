package com.linkhand.baixingguanjia.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.entity.PublicWelfare;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by JCY on 2017/6/22.
 * 说明：
 */

public class PublicWelfareAdapter extends CommonAdapter {
    private Context mContext;
    private List<PublicWelfare> mList;

    public PublicWelfareAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
        this.mContext = context;
        this.mList = datas;
    }

    @Override
    protected void convert(ViewHolder viewHolder, Object item, int position) {
//        View view = viewHolder.getView(R.id.bottom_line);
//        if (position == mList.size() - 1) {
//            view.setVisibility(View.VISIBLE);
//        }
        switch (mList.get(position).getType()) {
            case 1:
                // 寻人
//                initTypeView1(viewHolder);
                break;
            case 2:
                // 寻物
//                initTypeView2(viewHolder);
                break;
            case 3:
                // 善行
//                initTypeView3(viewHolder);
                break;
        }


    }


    private void initTypeView1(ViewHolder holder) {
        TextView typeTV = holder.getView(R.id.type);
        TextView button1 = holder.getView(R.id.button1);
        TextView button2 = holder.getView(R.id.button2);
        typeTV.setText("待付款");
        typeTV.setTextColor(mContext.getResources().getColor(R.color.redTopic));

        button1.setVisibility(View.VISIBLE);
        button1.setText("取消订单");
        button1.setTextColor(mContext.getResources().getColor(R.color.blackText));
        button1.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.background_textview_big_corner_line_gray));
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "取消订单", Toast.LENGTH_SHORT).show();
            }
        });

        button2.setText("付款");
        button2.setTextColor(mContext.getResources().getColor(R.color.redTopic));
        button2.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.background_textview_big_corner_line_red));
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "付款", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initTypeView2(ViewHolder holder) {
        TextView typeTV = holder.getView(R.id.type);
        TextView button1 = holder.getView(R.id.button1);
        TextView button2 = holder.getView(R.id.button2);
        typeTV.setText("待自提");
        typeTV.setTextColor(mContext.getResources().getColor(R.color.redTopic));


        button1.setVisibility(View.GONE);

        button2.setText("退款");
        button2.setTextColor(mContext.getResources().getColor(R.color.redTopic));
        button2.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.background_textview_big_corner_line_red));
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "退款", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initTypeView3(ViewHolder holder) {
        TextView typeTV = holder.getView(R.id.type);
        TextView button1 = holder.getView(R.id.button1);
        TextView button2 = holder.getView(R.id.button2);
        typeTV.setText("已提货");
        typeTV.setTextColor(mContext.getResources().getColor(R.color.blackText));

        button1.setVisibility(View.VISIBLE);
        button1.setText("退货");
        button1.setTextColor(mContext.getResources().getColor(R.color.blackText));
        button1.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.background_textview_big_corner_line_gray));
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "退货", Toast.LENGTH_SHORT).show();
            }
        });

        button2.setText("评价");
        button2.setTextColor(mContext.getResources().getColor(R.color.redTopic));
        button2.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.background_textview_big_corner_line_red));
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "评价", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
