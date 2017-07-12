package com.linkhand.baixingguanjia.widget.popup;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.widget.progress.CircleProgressBar;




/**
 * Created by tiangongyipin on 16/2/22.
 */
public class LoadingPopup extends PopupWindow {

    private Context mContext;

    public RelativeLayout loadLyt;
    public RelativeLayout percentLyt;
    public View pickPhotoView;
    public CircleProgressBar percentBar;

    public LoadingPopup(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    private void init() {
        pickPhotoView = LayoutInflater.from(mContext).inflate(R.layout.popup_loading, null);
        loadLyt= (RelativeLayout) pickPhotoView.findViewById(R.id.loading_lyt);
        percentLyt= (RelativeLayout) pickPhotoView.findViewById(R.id.percent_lyt);
        percentBar= (CircleProgressBar) pickPhotoView.findViewById(R.id.percent_bar);
//        pickPhotoView.getBackground().setAlpha(150);
        setContentView(pickPhotoView);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(false);
//        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//        setBackgroundDrawable(new ColorDrawable(0));
        setBackgroundDrawable(new BitmapDrawable());

    }

}
