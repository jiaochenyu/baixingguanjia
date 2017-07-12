package com.linkhand.baixingguanjia.ui.adapter;

import android.content.Context;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.entity.Qu;
import com.linkhand.baixingguanjia.entity.Sheng;
import com.linkhand.baixingguanjia.entity.Shi;
import com.linkhand.baixingguanjia.entity.Xiaoqu;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by JCY on 2017/7/7.
 * 说明：
 */

public class PopListviewAdapter extends CommonAdapter {
    private Context mContext;
    private List<Sheng> mList1;
    private List<Shi> mList2;
    private List<Qu> mList3;
    private List<Xiaoqu> mList4;
    private List<Object> mList;
    private int type;

    public PopListviewAdapter(Context context, int layoutId, List datas, int type) {
        super(context, layoutId, datas);
        this.mContext = context;
        this.type = type;
        this.mList = datas;
    }

    @Override
    protected void convert(ViewHolder viewHolder, Object item, int position) {
        TextView text = viewHolder.getView(R.id.location);
        if (mList.get(position) instanceof Sheng) {

            text.setText(((Sheng) mList.get(position)).getName());

        } else if (mList.get(position) instanceof Shi) {

            text.setText(((Shi) mList.get(position)).getName());

        } else if (mList.get(position) instanceof Qu) {

            text.setText(((Qu) mList.get(position)).getName());

        } else if (mList.get(position) instanceof Xiaoqu) {

            text.setText(((Xiaoqu) mList.get(position)).getName());

        }

    }
}
