package com.linkhand.baixingguanjia.ui.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.entity.Picture;
import com.linkhand.baixingguanjia.kits.MyImageLoader;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by JCY on 2017/6/20.
 * 说明：
 */

public class SecondhandCarDetailAdapter extends CommonAdapter {
    private Context mContext;
    private List<Picture> mList;
    int width;

    public SecondhandCarDetailAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
        this.mContext = context;
        this.mList = datas;
        width = getScreenWidth(mContext);
    }

    @Override
    protected void convert(ViewHolder viewHolder, Object item, int position) {
        ImageView imageView = viewHolder.getView(R.id.iv_adapter_good_detail_img);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageView.getLayoutParams();
        params.width = width;
        params.height = width/2;
        imageView.setLayoutParams(params);
        MyImageLoader.getInstance().displayImageCen(mContext,mList.get(position).getUrl(),imageView,width,width/2);
    }


    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }
}
