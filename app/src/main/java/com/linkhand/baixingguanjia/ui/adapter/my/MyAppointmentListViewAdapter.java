package com.linkhand.baixingguanjia.ui.adapter.my;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.entity.Appointment;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by JCY on 2017/6/27.
 * 说明：  预约 listview适配器
 */

public class MyAppointmentListViewAdapter extends CommonAdapter {
    List<Appointment> mList;
    Context mContext;
    Dialog mDialog;

    public MyAppointmentListViewAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
        this.mContext = context;
        this.mList = datas;
        this.mDialog = new Dialog(mContext);
        initDialog();
    }

    private void initDialog() {

        mDialog = new Dialog(mContext, R.style.Dialog);
        mDialog.setContentView(R.layout.dialog_appointment_evaluate);

        TextView submit = (TextView) mDialog.findViewById(R.id.submit);
        final EditText contentET = (EditText) mDialog.findViewById(R.id.content_edit);
        final EditText phoneET = (EditText) mDialog.findViewById(R.id.phone_edit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //弹出框点击事件
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
//                    contentET.getText().toString();
                }
            }
        });
    }
    @Override
    protected void convert(ViewHolder viewHolder, Object item, int position) {
        TextView evaluate = viewHolder.getView(R.id.pingjia);
        evaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.show();
            }
        });
    }
}
