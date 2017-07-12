package com.linkhand.baixingguanjia.utils;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.TextView;

import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * Created by JCY on 2017/6/16.
 * 说明：
 */

public class MyHolder extends ViewHolder {
    public MyHolder(Context context, View itemView) {
        super(context, itemView);
    }

    public ViewHolder setText(int viewId, SpannableStringBuilder text)
    {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }
}
