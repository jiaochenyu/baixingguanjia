package com.linkhand.baixingguanjia.ui.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.base.Constants;
import com.linkhand.baixingguanjia.base.MyApplication;
import com.linkhand.baixingguanjia.utils.ImageUtils;
import com.linkhand.baixingguanjia.utils.MediaItem;
import com.linkhand.baixingguanjia.utils.MultiSelectPhotoGridActivity;
import com.yanzhenjie.nohttp.NoHttp;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.senab.photoview.PhotoView;


/**
 * Created by timor.fan on 2016/10/18.
 * *项目名：baixinguanjia
 * 类描述：头像预览界面
 */
public class PreviewActivity extends BaseActivity {
    @Bind(R.id.right_text)
    TextView updateTxt;
    @Bind(R.id.ap_head_img)
    PhotoView headImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_preview);
        ButterKnife.bind(this);
        updateTxt.setVisibility(View.VISIBLE);
        updateTxt.setText(getResources().getString(R.string.update));
        if (MyApplication.getUser() != null && !TextUtils.isEmpty(MyApplication.getUser().avatarAddr) ) {
            ImageUtils.setImage(headImg, MyApplication.getUser().avatarAddr);
        }

    }

//    @Override
//    protected void initViewsAndEvents() {
//        updateTxt.setVisibility(View.VISIBLE);
//        updateTxt.setText(getResources().getString(R.string.update));
//        if (!TextUtils.isEmpty(MyApplication.getUser().avatarAddr)) {
//            ImageUtils.setImage(headImg, MyApplication.getUser().avatarAddr);
//        }
//    }

//    @Override
//    protected int getContentViewLayoutID() {
//        return R.layout.activity_head_preview;
//    }

    @OnClick(R.id.right_text)
    void updateHead() {
        Bundle bundle = new Bundle();
        bundle.putInt("selected", 8);
        goForResult(MultiSelectPhotoGridActivity.class, bundle, Constants.Codes.ALBUM);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == Constants.Codes.ALBUM) {
            Bundle extras = data.getExtras();
            if (extras == null) {
                return;
            }
            ArrayList<MediaItem> selectedPics = extras.getParcelableArrayList(Constants.Keys.IMAGES);
            showLoading();
            final String path = ImageUtils.getCompressImg(selectedPics.get(0).getImagePath(), PreviewActivity.this);
            //图片上传
            ImageUtils.setImage(headImg,path);

        }
    }

    @Override
    protected void onDestroy() {
        NoHttp.getRequestQueueInstance().cancelAll();
        super.onDestroy();
    }
}
