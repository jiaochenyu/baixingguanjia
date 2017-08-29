package com.linkhand.baixingguanjia.ui.adapter.my;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.entity.Order;
import com.linkhand.baixingguanjia.utils.ImageUtils;
import com.linkhand.bxgj.lib.utils.DecimalUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by JCY on 2017/6/22.
 * 说明：
 */

public class MyOrderAdapter extends CommonAdapter {
    private Context mContext;
    private List<Order> mList;
    private OrderOperationListerner mOrderOperationListerner;


    public MyOrderAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
        this.mContext = context;
        this.mList = datas;
    }

    public void setOrderOperationListerner(OrderOperationListerner orderOperationListerner) {
        mOrderOperationListerner = orderOperationListerner;
    }

    @Override
    protected void convert(ViewHolder viewHolder, Object item, int position) {
        ImageView image = viewHolder.getView(R.id.image_good);
        TextView name = viewHolder.getView(R.id.good_name);
        TextView type = viewHolder.getView(R.id.type_tv);
        TextView price = viewHolder.getView(R.id.price1);//¥
        TextView num = viewHolder.getView(R.id.goods_num);
        ImageUtils.setDefaultNormalImage(image, ConnectUrl.REQUESTURL_IMAGE + mList.get(position).getOriginal_img(), R.drawable.default_pic_show);
        name.setText(mList.get(position).getGoods_name());
        type.setText(mList.get(position).getSpec_key_name());
        price.setText("¥ " + DecimalUtils.decimalFormat(mList.get(position).getGoods_price()));
        num.setText("x" + mList.get(position).getGoods_num());


        Log.d("pay_status", "convert: " + mList.get(position).getOrder_status() + "position" + position);

        switch (mList.get(position).getOrder_status()) {
            case 0:
                // 代付款
                initTypeView1(viewHolder, position);
                break;
            case 1:
                // 待自提
                initTypeView2(viewHolder, position);
                break;
            case 2:
                // 已提货
                initTypeView3(viewHolder, position);
                break;
            case 3:
                //关闭
                initTypeView4(viewHolder);
                break;
        }


    }


    private void initTypeView1(ViewHolder holder, final int position) {
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
//                Toast.makeText(mContext, "取消订单", Toast.LENGTH_SHORT).show();
                mOrderOperationListerner.onCancelListener(position);
            }
        });

        button2.setText("付款");
        button2.setTextColor(mContext.getResources().getColor(R.color.redTopic));
        button2.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.background_textview_big_corner_line_red));
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, "付款", Toast.LENGTH_SHORT).show();
                mOrderOperationListerner.onPayListener(position);
            }
        });
    }

    private void initTypeView2(ViewHolder holder, final int position) {
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
//                Toast.makeText(mContext, "退款", Toast.LENGTH_SHORT).show();
                mOrderOperationListerner.onReturnListener(position);
            }
        });
    }

    private void initTypeView3(ViewHolder holder, final int position) {
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
//                Toast.makeText(mContext, "退货", Toast.LENGTH_SHORT).show();
                mOrderOperationListerner.onReturnListener(position);
            }
        });

        button2.setText("评价");
        button2.setTextColor(mContext.getResources().getColor(R.color.redTopic));
        button2.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.background_textview_big_corner_line_red));
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "评价", Toast.LENGTH_SHORT).show();
                mOrderOperationListerner.onEvaluateListener(position);
            }
        });
    }

    private void initTypeView4(ViewHolder holder) {
        TextView typeTV = holder.getView(R.id.type);
        TextView button1 = holder.getView(R.id.button1);
        TextView button2 = holder.getView(R.id.button2);
        typeTV.setText("交易关闭");
        typeTV.setTextColor(mContext.getResources().getColor(R.color.blackText));

        button1.setVisibility(View.GONE);
        button2.setVisibility(View.GONE);
    }

    //
    public interface OrderOperationListerner {
        public void onCancelListener(int position);

        public void onPayListener(int position);

        public void onReturnListener(int position);

        public void onEvaluateListener(int position);

    }
}
