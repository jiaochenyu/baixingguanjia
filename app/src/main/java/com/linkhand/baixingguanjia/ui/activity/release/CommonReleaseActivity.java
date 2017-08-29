package com.linkhand.baixingguanjia.ui.activity.release;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.base.Constants;
import com.linkhand.baixingguanjia.base.MyApplication;
import com.linkhand.baixingguanjia.customView.CommonPromptDialog;
import com.linkhand.baixingguanjia.entity.Common;
import com.linkhand.baixingguanjia.entity.EventFlag;
import com.linkhand.baixingguanjia.entity.Sheng;
import com.linkhand.baixingguanjia.entity.User;
import com.linkhand.baixingguanjia.ui.activity.my.PayCheckActivity;
import com.linkhand.baixingguanjia.ui.adapter.ReleaseSelectPicAdapter;
import com.linkhand.baixingguanjia.utils.JSONUtils;
import com.linkhand.baixingguanjia.utils.MultiPhotoViewActivity;
import com.linkhand.baixingguanjia.utils.RegexUtils;
import com.linkhand.baixingguanjia.utils.SPUtils;
import com.yanzhenjie.nohttp.Binary;
import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;
import com.yongchun.library.view.ImageSelectorActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.picker.AddressPicker;
import cn.qqtheme.framework.util.LogUtils;

public class CommonReleaseActivity extends BaseActivity {

    private static final int LIMIT_PIC_NUM = 8;
    private static final int REQUEST_WHAT_DETILES = 1;
    private static final int HTTP_REQUEST = 0;
    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.text_upload)
    TextView mUploadTV;
    @Bind(R.id.image_df)
    ImageView mAddBtn;
    @Bind(R.id.title_edit)
    EditText mTitleET;
    @Bind(R.id.zhaiyao_edit)
    EditText mZhaiyaoET;
    @Bind(R.id.describe)
    TextView mAbstractTV;

    @Bind(R.id.submit)
    TextView mSubmit;

    EditText mPhoneET;
    EditText mContactsET;
    TextView mAddressTV;
    @Bind(R.id.gridview)
    GridView mGridview;
    @Bind(R.id.pic_bg)
    RelativeLayout mPicBg;
    private ArrayList<String> imgPaths;
    private ArrayList<String> imageUrls;
    Sheng mSheng;
    private ReleaseSelectPicAdapter mAdapter;
    private int picPosition;


    String titleStr, desStr, contStr, phoneStr, addStr, zhaiyaoStr;
    private int position;
    private String flag;
    private String goods_type;
    private String goodsid;
    CommonPromptDialog mSuccessDialog;
    CommonPromptDialog mBackDialog;
    private RequestQueue mQueue = NoHttp.newRequestQueue();
    private Common mCommon;
    private List<String> mPictureList;

    String shengId;
    String shiId;
    String quId;
    String xiaoquId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_release);
        ButterKnife.bind(this);
        initView();
        initData();
        initYesDialog();
        initBackDialog();
        initSelectPicView();
        initListener();
        if (!TextUtils.isEmpty(flag)) {
            httpGetDetiles();
        }

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras != null) {

            position = extras.getInt("position", -1);
            flag = extras.getString("flag", "");
            goods_type = extras.getString("goods_type", "");
            goodsid = extras.getString("goods_id", "");
        }
    }

    private void initView() {
        mTitle.setText("发布");
        LinearLayout layout1 = (LinearLayout) findViewById(R.id.contacts_layout);
        LinearLayout layout2 = (LinearLayout) findViewById(R.id.phone_layout);
        LinearLayout layout3 = (LinearLayout) findViewById(R.id.location_layout);
        ((TextView) layout1.findViewById(R.id.text_left)).setText("联系人");
        ((TextView) layout2.findViewById(R.id.text_left)).setText("电话号码");
        ((TextView) layout3.findViewById(R.id.text_left)).setText("地址");
        mPhoneET = (EditText) layout1.findViewById(R.id.edittext_right);
        mPhoneET.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        mPhoneET.setHint(R.string.hintEdittextPhoneSetting);
        mPhoneET.setMaxEms(11);
        mContactsET = (EditText) layout2.findViewById(R.id.edittext_right);
        mAddressTV = (TextView) layout3.findViewById(R.id.text_right2);
        mAddressTV.setVisibility(View.VISIBLE);
    }

    private void initData() {
        imgPaths = new ArrayList<>();
        imageUrls = new ArrayList<>();
        mSheng = (Sheng) SPUtils.get(this, "DiQu", Sheng.class);
        imgPaths.add("");
        mAdapter = new ReleaseSelectPicAdapter(this, R.layout.item_photo, imgPaths);
        mGridview.setAdapter(mAdapter);
    }

    /**
     * 设置图片选择布局
     */
    private void initSelectPicView() {
        if (imgPaths.size() == 1 && imgPaths.get(0).equals("")) {
            mPicBg.setBackgroundColor(getResources().getColor(R.color.blue1D3));
            mGridview.setVisibility(View.GONE);
            mAddBtn.setVisibility(View.VISIBLE);
            mUploadTV.setVisibility(View.VISIBLE);
        } else if (imgPaths.size() == 0) {
            mPicBg.setBackgroundColor(getResources().getColor(R.color.blue1D3));
            mGridview.setVisibility(View.GONE);
            mAddBtn.setVisibility(View.VISIBLE);
            mUploadTV.setVisibility(View.VISIBLE);
        } else {
            mPicBg.setBackgroundColor(getResources().getColor(R.color.white));
            mGridview.setVisibility(View.VISIBLE);
            mAddBtn.setVisibility(View.GONE);
            mUploadTV.setVisibility(View.GONE);
        }
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
                    Intent intent = new Intent(CommonReleaseActivity.this, MultiPhotoViewActivity.class);
                    intent.putExtra(Constants.Keys.IMAGE_ITEMS, picString);//传递所有数据
                    intent.putExtra("position", position);//确定当前是哪张图片
                    startActivity(intent);
                }

            }

        });
        mAdapter.setImagesDataChanged(new ReleaseSelectPicAdapter.ImagesDataChanged() {
            @Override
            public void imagesChanged() {
                initSelectPicView();
            }
        });
    }

    private void initYesDialog() {
        mSuccessDialog = new CommonPromptDialog(mContext, R.style.goods_info_dialog);
        mSuccessDialog.setMessage(getStrgRes(R.string.uploadSuccess));
        mSuccessDialog.setOptionOneClickListener(getStrgRes(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mSuccessDialog.dismiss();
            }
        });
        mSuccessDialog.setOptionTwoClickListener(getStrgRes(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mSuccessDialog.dismiss();
//                go(LoginActivity.class);
                finish();
            }
        });
    }

    private void initBackDialog() {
        mBackDialog = new CommonPromptDialog(mContext, R.style.goods_info_dialog);
        mBackDialog.setMessage(getStrgRes(R.string.uploadBack));
        mBackDialog.setOptionOneClickListener(getStrgRes(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mBackDialog.dismiss();
            }
        });
        mBackDialog.setOptionTwoClickListener(getStrgRes(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mBackDialog.dismiss();
                finish();
            }
        });
    }

    /**
     * 更新操作
     ***/
    private void httpGetDetiles() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_SERVICE_DTEAIL, RequestMethod.POST);
        request.add("goodsid", goodsid);
        request.add("goods_type", goods_type);
        mQueue.add(REQUEST_WHAT_DETILES, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
                showLoading(true);
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == REQUEST_WHAT_DETILES) {
                    String resultCode = null;
                    Log.e("tag", response.get().toString());
                    try {
                        Gson gson = new Gson();
                        JSONObject jsonObject = response.get();
                        resultCode = jsonObject.getString("code");
                        if (resultCode.equals("200")) {
                            mCommon = gson.fromJson(jsonObject.getJSONObject("data").toString(), Common.class);
                        }

                        mPictureList = mCommon.getImage_url();
                        setViewData();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<JSONObject> response) {
                hideLoading();
            }

            @Override
            public void onFinish(int what) {
                if (what == REQUEST_WHAT_DETILES) {
//                    initListener();
                    hideLoading();
                }

            }
        });

    }

    private void setViewData() {
        mAbstractTV.setText(mCommon.getModel());
        mTitleET.setText(mCommon.getTitle());
        mContactsET.setText(mCommon.getContact()); //
        mPhoneET.setText(mCommon.getPhone());
        mPhoneET.setText(mCommon.getPhone());
        shengId = mCommon.getSheng();
        shiId = mCommon.getShi();
        quId = mCommon.getQu();
        xiaoquId = mCommon.getXiaoqu();
        mAddressTV.setText(mCommon.getAddress()); //
        imgPaths.clear();
        for (int i = 0; i < mPictureList.size(); i++) {
            imgPaths.add(ConnectUrl.REQUESTURL_IMAGE + mPictureList.get(i));
        }
        if (!adjustList(imgPaths) || imgPaths.size() < LIMIT_PIC_NUM) {
            imgPaths.add("");
        }
        mAdapter.notifyDataSetChanged();
        initSelectPicView();
    }


    /**
     * 相册选择
     *
     * @param
     */
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

        Intent intent = new Intent(CommonReleaseActivity.this, ImageSelectorActivity.class);
        intent.putExtra(ImageSelectorActivity.EXTRA_MAX_SELECT_NUM, count);
        intent.putExtra(ImageSelectorActivity.EXTRA_SELECT_MODE, ImageSelectorActivity.MODE_MULTIPLE);
        intent.putExtra(ImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        intent.putExtra(ImageSelectorActivity.EXTRA_ENABLE_PREVIEW, true);
        intent.putExtra(ImageSelectorActivity.EXTRA_ENABLE_CROP, false);
        startActivityForResult(intent, Constants.Codes.ALBUM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                initSelectPicView();
                mAdapter.notifyDataSetChanged();
            }

        }
    }

    @OnClick({R.id.back, R.id.submit, R.id.image_df, R.id.abstract_layout, R.id.location_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                mBackDialog.show();
                break;
            case R.id.image_df:
                selectorImage();
                break;
            case R.id.submit:
                if (MyApplication.getUser().getUser_money() <= 0) {
                    showBaseDialog("白银不足", PayCheckActivity.class);
                } else {
                    showPaySuccessDialog();
                }
                break;
            case R.id.abstract_layout:
                Bundle bundle1 = new Bundle();
                bundle1.putString("flag", "commonDescribe");
                bundle1.putString("content", mAbstractTV.getText().toString());
                go(ReleaseDescribeActivity.class, bundle1);
                break;
            case R.id.location_layout:
                Bundle bundleP = new Bundle();
                bundleP.putString("acFlag", "CommonRelease");
                go(ReleaseAreaSearchActivity.class, bundleP);
//                onAddress2Picker();
                break;
        }
    }

    private void judgeData() {
        titleStr = mTitleET.getText().toString(); //标题
        zhaiyaoStr = mZhaiyaoET.getText().toString(); //摘要
        desStr = mAbstractTV.getText().toString();//描述
        contStr = mContactsET.getText().toString();//联系人
        phoneStr = mPhoneET.getText().toString(); //手机号
        addStr = mAddressTV.getText().toString(); //地址
        if (TextUtils.isEmpty(titleStr)) {
            showToast(R.string.toastTitle);
            return;
        }
        if (TextUtils.isEmpty(zhaiyaoStr)) {
            showToast(R.string.toastZhaiyao);
            return;
        }
        if (TextUtils.isEmpty(desStr)) {
            showToast(R.string.toastMiaoshu);
            return;
        }
        if (TextUtils.isEmpty(addStr)) {
            showToast(R.string.toastLoaction);
            return;
        }
        if (TextUtils.isEmpty(contStr)) {
            showToast(R.string.toastLianxiren);
            return;
        }
        if (TextUtils.isEmpty(phoneStr)) {
            showToast(R.string.toastPhone);
            return;
        }
        if (!RegexUtils.isMobileExact(phoneStr)) {
            showToast(R.string.inputrightphone);
            return;
        }
        showLoading();
        httpUploadImage();
    }


    /**
     * 上传图片
     */

    private void httpUploadImage() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_UPLOAD_IMAGE, RequestMethod.POST);
        List<Binary> fileList = new ArrayList<>();
        for (int i = 0; i < imgPaths.size(); i++) {
            if (imgPaths.get(i).contains("http://")) {
                imageUrls.add(imgPaths.get(i).split(ConnectUrl.REQUESTURL_IMAGE)[1]);
            }
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

        mQueue.add(HTTP_REQUEST, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {

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
                                imageUrls.add(array.getString(i));
                            }
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

            }
        });

    }

    private void httpUploadInfo() {
        Request<JSONObject> request = null;
        if (!flag.contains("update")) {
            request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_COMMON_RELEASE, RequestMethod.POST);
            request.add("data[sheng]", mSheng.getId()); //河北省 石家庄 裕华 华山商务
            request.add("data[shi]", mSheng.getShiList().get(0).getId());
        } else {
            request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_MY_RELEASE_UPDATE, RequestMethod.POST);
            request.add("goodsid", mCommon.getCommon_id());
            request.add("goodstype", mCommon.getGoods_type());
            request.add("userid", MyApplication.getUser().getUserid());
            request.add("data[sheng]", shengId); //河北省 石家庄 裕华 华山商务
            request.add("data[shi]", shiId);

        }
        request.add("data[userid]", MyApplication.getUser().getUserid());
        request.add("data[contact]", contStr);
        request.add("data[phone]", phoneStr);
        request.add("data[title]", titleStr);
        request.add("data[model]", desStr);
        request.add("data[message]", zhaiyaoStr);
        request.add("data[goods_type]", goods_type);
        request.add("data[qu]", quId);
        request.add("data[xiaoqu]", xiaoquId);
        if (adjustList(imageUrls)) {
            request.add("data[logo_url]", imageUrls.get(0));
        } else {
            request.add("data[logo_url]", "");
        }
        if (imageUrls.size() == 0) {
            request.add("img[]", "");
        } else {
            for (int i = 0; i < imageUrls.size(); i++) {
                request.add("img[]", imageUrls.get(i));
            }
        }

        mQueue.add(1, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == 1) {
                    try {
                        if (response.get().getInt("code") == 200) {
                            emptyView();
                            mSuccessDialog.show();
                            if (position != -1) {
                                EventBus.getDefault().post(new EventFlag(flag, position));
                            }
                            User user = MyApplication.getUser();
                            user.setUser_money(user.getUser_money() - 1);
                            MyApplication.setUser(user);
                        } else {
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
//                showToast(R.string.uploadFail);
                hideLoading();
            }

            @Override
            public void onFinish(int what) {
                hideLoading();
            }
        });
    }

    /**
     * 清空数据
     */
    private void emptyView() {
        mTitleET.setText(""); //标题
        mZhaiyaoET.setText(""); //摘要
        mAbstractTV.setText(""); //内容
        mPhoneET.setText(""); //标题
        mContactsET.setText("");
        mAddressTV.setText(""); //标题
        imgPaths.clear();
        mAdapter.notifyDataSetChanged();
        initSelectPicView();
    }

    // 提示消耗白银
    private void showPaySuccessDialog() {
        final Dialog mDialog = new Dialog(CommonReleaseActivity.this, R.style.Dialog);
        mDialog.setContentView(R.layout.dialog_promt_pay_silver);
        TextView info = (TextView) mDialog.findViewById(R.id.info);
        info.setText("您的白银还剩余" + MyApplication.getUser().getUser_money() + "两~");
        TextView no = (TextView) mDialog.findViewById(R.id.no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
            }
        });
        TextView yes = (TextView) mDialog.findViewById(R.id.yes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //弹出框点击事件
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                judgeData();
            }
        });
        mDialog.show();
    }


    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Subscribe
    public void onEvent(EventFlag eventFlag) {
        if (eventFlag.getFlag().equals("commonDescribe")) {
            mAbstractTV.setText((String) eventFlag.getObject());
        }
        if (eventFlag.getFlag().equals("CommonRelease")) {
            Sheng s = (Sheng) eventFlag.getObject();
            quId = s.getQu().getId();
            xiaoquId = s.getXiaoqu().getId();
            mAddressTV.setText(s.getQu().getName() + s.getXiaoqu().getName());
        }
    }

    /**
     * 地址选择器 二级联动 区小区
     *
     * @param
     */
    public void onAddress2Picker() {
        try {
            //市 区 小区 Province == 市
            ArrayList<Province> data = JSONUtils.getAddress2PickerData(mSheng);
            AddressPicker picker = new AddressPicker(this, data);
            picker.setShadowVisible(true);
            picker.setHideProvince(true);
            //市 区 小区
//            picker.setSelectedItem(sheng.getShiList().get(0).getName(),sheng.getShiList().get(0).getQuList().get(0).getName(),sheng.getShiList().get(0).getQuList().get(0).getXiaoquList().get(0).getName());
            picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
                @Override
                public void onAddressPicked(Province province, City city, County county) {
//                    showToast("province : " + province + ", city: " + city + ", county: " + county);
                    quId = (String) city.getId();
                    if (county != null) {
                        xiaoquId = (String) county.getId();
                    }
                    mAddressTV.setText(province.getName() + city.getName() + county.getName());
                }
            });
            picker.show();
        } catch (Exception e) {
            showToast(LogUtils.toStackTraceString(e));
        }
    }

}
