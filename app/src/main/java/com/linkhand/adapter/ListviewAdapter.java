package com.linkhand.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.linkhand.R;

/**
 * Created by JCY on 2017/6/13.
 * 说明：
 */

public class ListviewAdapter extends BaseAdapter {
    private Context mContext;
    private String[] titls;
    private LayoutInflater inflater;
    public ListviewAdapter(Context context, String[] titls) {
        mContext = context;
        this.titls = titls;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return titls.length;
    }

    @Override
    public Object getItem(int position) {
        return titls[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_listview,parent,false);
            holder.name = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(titls[position]);
        return convertView;
    }

    private class ViewHolder{
        private TextView name;
    }
}
