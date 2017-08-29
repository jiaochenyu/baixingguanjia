package com.linkhand.baixingguanjia.ui.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.base.Constants;
import com.linkhand.baixingguanjia.base.MyApplication;
import com.linkhand.baixingguanjia.entity.EventFlag;
import com.linkhand.baixingguanjia.entity.User;
import com.linkhand.baixingguanjia.utils.ImageUtils;
import com.linkhand.baixingguanjia.utils.SPUtils;
import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;
import com.yongchun.library.view.ImageSelectorActivity;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
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
    private static final int HTTP_REQUEST = 0;
    private static final int REQUEST_CODE = 1;
    @Bind(R.id.right_text)
    TextView updateTxt;
    @Bind(R.id.ap_head_img)
    PhotoView headImg;
    @Bind(R.id.title)
    TextView mTitle;
    String picNetUrl;

    RequestQueue mQueue = NoHttp.newRequestQueue();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_preview);
        ButterKnife.bind(this);
        initView();


    }

    private void initView() {
        mTitle.setText(R.string.myHeader);
        updateTxt.setVisibility(View.VISIBLE);
        updateTxt.setText(getResources().getString(R.string.update));
        if (MyApplication.getUser() != null && !TextUtils.isEmpty(MyApplication.getUser().getHead_url())) {
            ImageUtils.setImage(headImg, ConnectUrl.REQUESTURL_IMAGE+MyApplication.getUser().getHead_url());
        } else {
            ImageUtils.setImage(headImg, "");
        }
    }


    @OnClick(R.id.right_text)
    public void updateHead() {
        Intent intent = new Intent(PreviewActivity.this, ImageSelectorActivity.class);
        intent.putExtra(ImageSelectorActivity.EXTRA_SELECT_MODE, ImageSelectorActivity.MODE_SINGLE);
        intent.putExtra(ImageSelectorActivity.EXTRA_ENABLE_CROP, true);
        intent.putExtra(ImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        startActivityForResult(intent, Constants.Codes.ALBUM);
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
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
            ArrayList<String> selectedPics = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT);
            showLoading();
            final String path = ImageUtils.getCompressImg(selectedPics.get(0), PreviewActivity.this);
            //图片上传
            ImageUtils.setImage(headImg, path);
            httpUpload(path);


        }
    }

    /**
     *
     */
    private void httpUpload(String path) {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_UPLOAD_IMAGE, RequestMethod.POST);
        if (new File(path).exists()) {
            request.add("image[]", new FileBinary(new File(path)));
        } else {
            showToast(R.string.selectUploadTishi);
            return;
        }

        mQueue.add(HTTP_REQUEST, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
                showLoading(false);
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == HTTP_REQUEST) {// 根据what判断是哪个请求的返回，这样就可以用一个OnResponseListener来接受多个请求的结果。
                    int responseCode = response.getHeaders().getResponseCode();// 服务器响应码。
                    JSONObject jsonObject = response.get();
                    try {
                        if (jsonObject.getInt("code") == 200) {
                            JSONArray array = jsonObject.getJSONArray("data");
                            for (int i = 0; i < array.length(); i++) {
                                picNetUrl = array.getString(i);
                            }
                            httpUploadUserInfo();
                        } else {
                            showToast(R.string.uploadFail);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.e("result1", jsonObject.toString());
                }
            }

            @Override
            public void onFailed(int what, Response<JSONObject> response) {
                showToast(R.string.uploadFail);
                hideLoading();

            }

            @Override
            public void onFinish(int what) {
                hideLoading();
            }
        });
    }

    private void httpUploadUserInfo() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.USER_INFO_UPDATE, RequestMethod.POST);
        request.add("userid", MyApplication.getUser().getUserid());
        request.add("head_url", picNetUrl);
        request.add("nickname", MyApplication.getUser().getNickname());
        mQueue.add(REQUEST_CODE, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == REQUEST_CODE) {
                    JSONObject jsonObject = response.get();
                    try {
                        if (jsonObject.getString("code").equals("200")||jsonObject.getString("code").equals("203")){
                            Gson gson = new Gson();
                            User user = gson.fromJson(jsonObject.get("data").toString(),User.class);
                            SPUtils.put(PreviewActivity.this,"userInfo",user);
                            EventBus.getDefault().post(new EventFlag("uploadHeaderSuccess", picNetUrl));
                            showToast(R.string.uploadsuccess);
                        }else {
                            showToast(R.string.uploadFail);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.e("返回数据", "onSucceed: " + response.get().toString());
                }

            }

            @Override
            public void onFailed(int what, Response<JSONObject> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });

    }


    @Override
    protected void onDestroy() {
        NoHttp.getRequestQueueInstance().cancelAll();
        super.onDestroy();
    }

}
