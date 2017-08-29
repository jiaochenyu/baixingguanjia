package com.linkhand.baixingguanjia.ui.adapter.my;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.entity.Release;
import com.linkhand.baixingguanjia.utils.ImageUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by JCY on 2017/6/27.
 * 说明：  我发布的 listview适配器
 */

public class MyReleaseListViewAdapter extends CommonAdapter {
    List<Release> mList;
    Context mContext;
    int flag  ;  //判断发布类型 1未审核 2审核未通过 3 发布中， 4下线
    DeleteOnclick mDeleteOnclick;
    OfflineOnclick mOfflineOnclick;
    ReleaseOnclick mReleaseOnclick;
    EditeOnclick mEditeOnclick;

    public MyReleaseListViewAdapter(Context context, int layoutId, List datas,int flags) {
        super(context, layoutId, datas);
        this.mContext = context;
        this.mList = datas;
        this.flag = flags;
    }

    public void setDeleteOnclick(DeleteOnclick deleteOnclick) {
        mDeleteOnclick = deleteOnclick;
    }

    public void setOfflineOnclick(OfflineOnclick offlineOnclick) {
        mOfflineOnclick = offlineOnclick;
    }

    public void setReleaseOnclick(ReleaseOnclick releaseOnclick) {
        mReleaseOnclick = releaseOnclick;
    }

    public void setEditeOnclick(EditeOnclick editeOnclick) {
        mEditeOnclick = editeOnclick;
    }

    @Override
    protected void convert(ViewHolder holder, Object item, final int position) {
        TextView title = holder.getView(R.id.name);
        TextView time = holder.getView(R.id.time);
        TextView location = holder.getView(R.id.location);
        TextView type = holder.getView(R.id.type);
        TextView fabu = holder.getView(R.id.fabu);
        TextView state = holder.getView(R.id.state);
        TextView offline_btn = holder.getView(R.id.offline_btn);
        TextView delete_btn = holder.getView(R.id.delete_btn);
        TextView edit_btn = holder.getView(R.id.edit_btn);
        ImageView image = holder.getView(R.id.image);
        ImageUtils.setDefaultNormalImage(image, ConnectUrl.REQUESTURL_IMAGE+mList.get(position).getLogo_url(),R.drawable.default_pic_show);
        title.setText(mList.get(position).getTitle());
        time.setText(mList.get(position).getAdd_time());
        location.setText(mList.get(position).getQuname()+""+mList.get(position).getXiaoquname());
        type.setText(mList.get(position).getModular());
        type.setText(mList.get(position).getModular());
        if (flag == 1){//待审核
            state.setText("待审核");
        }else if (flag == 2){ //2审核未通过
            state.setText("审核未通过");
            delete_btn.setVisibility(View.VISIBLE);
            edit_btn.setVisibility(View.VISIBLE);
        }else if (flag == 3){//3 发布中， 4下线
            state.setText("发布中");
            offline_btn.setVisibility(View.VISIBLE);
            edit_btn.setVisibility(View.VISIBLE);
        } else if (flag == 4) { //下线
            state.setText("下线");
            fabu.setVisibility(View.VISIBLE);
            delete_btn.setVisibility(View.VISIBLE);
            edit_btn.setVisibility(View.VISIBLE);
        }

        fabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mReleaseOnclick.releaseOnClick(position);
            }
        });
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDeleteOnclick.deleteOnClick(position);
            }
        });
        offline_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOfflineOnclick.offlineOnClick(position);
            }
        });
        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditeOnclick.editOnClick(position);
            }
        });

    }

    public interface DeleteOnclick{
        void  deleteOnClick(int position);
    }
    public interface  EditeOnclick{
        void editOnClick(int position);
    }
    public interface  ReleaseOnclick{
        void releaseOnClick(int position);
    }
    public interface  OfflineOnclick{
        void offlineOnClick(int position);
    }
}
