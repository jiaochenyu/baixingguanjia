package com.linkhand.baixingguanjia.ui.activity.detail;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.base.Constants;
import com.linkhand.baixingguanjia.base.MyApplication;
import com.linkhand.baixingguanjia.ui.adapter.ReleaseSelectPicAdapter;
import com.linkhand.baixingguanjia.utils.MultiPhotoViewActivity;
import com.linkhand.baixingguanjia.utils.RegexUtils;
import com.yanzhenjie.nohttp.Binary;
import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;
import com.yongchun.library.view.ImageSelectorActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReportActivity extends BaseActivity {

    private static final int LIMIT_PIC_NUM = 5;
    private static final int HTTP_REQUEST_IMAGE = 2;
    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.header_layout)
    RelativeLayout mHeaderLayout;
    @Bind(R.id.flowlayout)
    TagFlowLayout mFlowlayout;
    @Bind(R.id.edittext_result)
    EditText mEdittextResult;
    @Bind(R.id.gridview)
    GridView mGridview;
    @Bind(R.id.horizontalscrollView)
    HorizontalScrollView mHorizontalscrollView;
    @Bind(R.id.phone_edit)
    EditText mPhoneEdit;
    LayoutInflater mInflater;
    TagAdapter<String> mTagAdapter;
    String[] tag = {"电话虚假（如空号、无人接听）", "涉嫌违法", "诈骗（如提前收取各类费用）", "中介冒充个人", "虚假（如服务、价格等虚假）", "公司信息虚假"};
    ReleaseSelectPicAdapter mPicAdapter;
    //选择的本地图片地址
    public ArrayList<String> imgPaths;
    int picPosition;
    @Bind(R.id.submit)
    TextView mSubmit;

    RequestQueue mQueue = NoHttp.newRequestQueue();
    String imageUrls = "";

    String reason = "";
    String phoneStr;
    String goodsid = "";
    String goodstype = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);
        initView();
        initData();
        initTagAdapter();
        initListener();

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras!=null){
            goodsid = extras.getString("goodsid","");
            goodstype = extras.getString("goodstype","");
        }
    }

    private void initView() {
        mHeaderLayout.setBackground(getDrawable(R.color.grayE7E7));
        mTitle.setText("用户举报");
    }

    private void initData() {
        imgPaths = new ArrayList<>();
        imgPaths.add("");
        mPicAdapter = new ReleaseSelectPicAdapter(this, R.layout.item_photo, imgPaths);
        mGridview.setAdapter(mPicAdapter);
        horizontal_layout(imgPaths, mGridview);
    }

    private void initTagAdapter() {
        mInflater = LayoutInflater.from(this);
        mTagAdapter = new TagAdapter<String>(tag) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.report_textview,
                        mFlowlayout, false);
                tv.setText(s);
                return tv;
            }
        };
        mFlowlayout.setAdapter(mTagAdapter);
    }

    private void initListener() {
        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                // TODO Auto-generated method stub
                if (imgPaths.get(position).equals("")) {
                    // 调用 相册 拍照
                    picPosition = position;
                    selectorImage();

                } else if (!imgPaths.get(position).equals("")) {
                    // 放大图

                    ArrayList<String> picString = new ArrayList<String>();
                    for (int i = 0; i < imgPaths.size(); i++) {
                        if (!imgPaths.get(i).equals("")) {
                            picString.add(imgPaths.get(i));
                        }
                    }
                    //开始预览
                    Intent intent = new Intent(ReportActivity.this, MultiPhotoViewActivity.class);
                    intent.putExtra(Constants.Keys.IMAGE_ITEMS, picString);//传递所有数据
                    intent.putExtra("position", position);//确定当前是哪张图片
                    startActivity(intent);
                }

            }

        });

        mPicAdapter.setImagesDataChanged(new ReleaseSelectPicAdapter.ImagesDataChanged() {
            @Override
            public void imagesChanged() {

            }
        });
    }

    private void selectorImage() {
        int count = 0;
        if (imgPaths.size() == 0) {
            count = LIMIT_PIC_NUM;
        } else {
            for (int i = 0; i < imgPaths.size(); i++) {
                if (imgPaths.get(i).equals("")) {
                    count = LIMIT_PIC_NUM - imgPaths.size() + 1;
                    break;
                }
                if (i == imgPaths.size()) {
                    count = LIMIT_PIC_NUM - imgPaths.size();
                }
            }
        }

        Intent intent = new Intent(ReportActivity.this, ImageSelectorActivity.class);
        intent.putExtra(ImageSelectorActivity.EXTRA_MAX_SELECT_NUM, count);
        intent.putExtra(ImageSelectorActivity.EXTRA_SELECT_MODE, ImageSelectorActivity.MODE_MULTIPLE);
        intent.putExtra(ImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        intent.putExtra(ImageSelectorActivity.EXTRA_ENABLE_PREVIEW, true);
        intent.putExtra(ImageSelectorActivity.EXTRA_ENABLE_CROP, false);
        startActivityForResult(intent, Constants.Codes.ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.Codes.ALBUM) {
                ArrayList<String> mSelectPath = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT);

                if (mSelectPath != null && mSelectPath.size() != 0) {
                    if (imgPaths.get(imgPaths.size() - 1).equals(""))
                        imgPaths.remove(imgPaths.size() - 1);
                }
                for (int i = 0; i < mSelectPath.size(); i++) {
//                    mList.set(picPosition + i, new Pic(mSelectPath.get(i)));
                    imgPaths.add(mSelectPath.get(i));
                }
                if (imgPaths.size() == LIMIT_PIC_NUM) {

                } else if (picPosition < LIMIT_PIC_NUM) {
                    imgPaths.add("");
                }
                mPicAdapter.notifyDataSetChanged();
                horizontal_layout(imgPaths, mGridview);
            }

        }
    }

    /**
     * *上传图片
     */
    private void httpUploadImage() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_UPLOAD_IMAGE, RequestMethod.POST);
        List<Binary> fileList = new ArrayList<>();
        for (int i = 0; i < imgPaths.size(); i++) {
            if (new File(imgPaths.get(i)).exists()) {
                fileList.add(new FileBinary(new File(imgPaths.get(i))));
                request.add("image[]", new FileBinary(new File(imgPaths.get(i))));
            }

        }
        if (!adjustList(fileList)) {
            httpUploadInfo();
            return;
        }


//        request.add("image",fileList);
        Log.e("参数", request.getParamKeyValues().values().toString());

        mQueue.add(HTTP_REQUEST_IMAGE, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == HTTP_REQUEST_IMAGE) {// 根据what判断是哪个请求的返回，这样就可以用一个OnResponseListener来接受多个请求的结果。
                    int responseCode = response.getHeaders().getResponseCode();// 服务器响应码。
                    JSONObject jsonObject = response.get();
                    try {
                        if (jsonObject.getInt("code") == 200) {
                            JSONArray array = jsonObject.getJSONArray("data");
                            for (int i = 0; i < array.length(); i++) {
                                imageUrls += array.getString(i) + "|||";
                            }
                            imageUrls = imageUrls.substring(0, imageUrls.length() - 3);
                            httpUploadInfo();
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
//                httpUploadInfo();

            }
        });

    }

    private void httpUploadInfo() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_REPORT, RequestMethod.POST);
        JSONObject jsonObject = new JSONObject();
        request.add("data[goods_id]", goodsid);
        request.add("data[goods_type]",goodstype);
        request.add("data[userid]", MyApplication.getUser().getUserid());
        request.add("data[reason]", reason);
        request.add("data[content]", mEdittextResult.getText().toString());
        request.add("data[image]", imageUrls);
        request.add("data[phone]", phoneStr);
        mQueue.add(1, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == 1) {
                    try {
                        if (response.get().getInt("code") == 200) {
                            showToast(response.get().getString("info"));
                        }else {
                            showToast(response.get().getString("info"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("上传返回结果", response.get().toString());
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

    @OnClick({R.id.back, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.submit:
                for (int item : mFlowlayout.getSelectedList()) {
                    reason += tag[item] + "/";
                }
                if (!TextUtils.isEmpty(reason)) {
                    reason = reason.substring(0, reason.length() - 1);
                    phoneStr = mPhoneEdit.getText().toString();
                    if (!TextUtils.isEmpty(phoneStr)&&!RegexUtils.isMobileExact(phoneStr)) {
                        showToast(R.string.inputrightphone);
                        return;
                    }
                    httpUploadImage();
                } else {
                    showToast(R.string.toast_reprot_reason);
                    return;
                }
                break;
        }
    }
}
