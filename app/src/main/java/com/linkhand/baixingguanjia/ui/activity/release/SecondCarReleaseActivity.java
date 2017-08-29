package com.linkhand.baixingguanjia.ui.activity.release;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.base.Constants;
import com.linkhand.baixingguanjia.base.MyApplication;
import com.linkhand.baixingguanjia.customView.CommonPromptDialog;
import com.linkhand.baixingguanjia.entity.Car;
import com.linkhand.baixingguanjia.entity.CommonType;
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
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.picker.AddressPicker;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.util.LogUtils;

import static com.linkhand.baixingguanjia.R.id.sort_layout;


public class SecondCarReleaseActivity extends BaseActivity {
    private static final int LIMIT_PIC_NUM = 8;
    private static final int HTTP_REQUEST = 0;
    private static final int REQUEST_WHAT_DETILES = 2;
    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.submit)
    TextView mSubmit;
    @Bind(R.id.text_upload)
    TextView mUploadTV;
    @Bind(R.id.image_df)
    ImageView mAddBtn;
    @Bind(R.id.pic_bg)
    LinearLayout mPicBg;
    @Bind(R.id.gridview)
    GridView mGridView;
    @Bind(R.id.radiogroup)
    RadioGroup mRadiogroup;
    @Bind(R.id.store_layout)
    RelativeLayout mStoreLayout;
    @Bind(R.id.company_name)
    TextView mStoreName;

    LinearLayout typeLL; //类别
    LinearLayout titleLL; //标题
    LinearLayout creatorLL; //首次上牌时间
    LinearLayout mileageLL; //行驶里程
    LinearLayout contactLL; //联系人
    LinearLayout phoneLL; //手机号
    LinearLayout addressLL; //看车地址

    TextView mTypeTV;
    EditText mTitleET;
    @Bind(R.id.edittext_price)
    EditText mPriceET;
    @Bind(R.id.describe)
    TextView mDescribeET;
    TextView mCreatorTV;
    @Bind(R.id.edittext_licheng)
    EditText mMileageET;
    EditText mContactET;
    EditText mPhoneET;
    TextView mAddressTV;
    String typeStr, titleStr, desStr, creStr, contStr, phoneStr, addStr, locationStr; //locationStr 自动定位
    String categoryStr = "4";//商家 4商家 5个人
    String quId;
    String xiaoquId;
    String company = "";
    double price, mileage;


    ReleaseSelectPicAdapter mAdapter;
    //选择的本地图片地址
    public ArrayList<String> imgPaths;

    private int picPosition;

    RequestQueue mQueue = NoHttp.newRequestQueue();
    List<String> imageUrls;
    CommonType mCommonType;
    Sheng mSheng;

    CommonPromptDialog mSuccessDialog;
    CommonPromptDialog mBackDialog;


    Car mCar;
    String goods_type = "";
    String goodsid = "";
    String shengId;
    String shiId;
    String flag = "";  //判断是更新 还是新发布；
    int position = -1; // 更新我的发布
    List<String> mPictureList;  //存的是从网络加载过来图片集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_car_release);
        ButterKnife.bind(this);
        initView();
        initData();
        initYesDialog();
        initBackDialog();
        initListener();
        initSelectPicView();
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
        mTitle.setText(R.string.carFabu);
        typeLL = (LinearLayout) findViewById(R.id.sort_layout);
        titleLL = (LinearLayout) findViewById(R.id.title_layout);
//        describeLL = (LinearLayout) findViewById(R.id.abstract_layout);
        creatorLL = (LinearLayout) findViewById(R.id.time_layout);
        mileageLL = (LinearLayout) findViewById(R.id.mileage_layout);
        contactLL = (LinearLayout) findViewById(R.id.contacts_layout);
        phoneLL = (LinearLayout) findViewById(R.id.phone_layout);
        addressLL = (LinearLayout) findViewById(R.id.location_layout);
        ((TextView) typeLL.findViewById(R.id.text_left)).setText(R.string.selectType);
        ((TextView) titleLL.findViewById(R.id.text_left)).setText(R.string.title);
        TextView textView = ((TextView) creatorLL.findViewById(R.id.text_left));
        textView.setText(R.string.firstTime);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(params);
        ((TextView) mileageLL.findViewById(R.id.text_left)).setText(R.string.licheng);
        ((TextView) mileageLL.findViewById(R.id.text_right)).setText(R.string.wangongli);
        ((TextView) contactLL.findViewById(R.id.text_left)).setText(R.string.lianxiren);
        ((TextView) phoneLL.findViewById(R.id.text_left)).setText(R.string.phone);
        ((TextView) addressLL.findViewById(R.id.text_left)).setText(R.string.carAddress);

        mTypeTV = (TextView) typeLL.findViewById(R.id.text_right2);
        mTypeTV.setVisibility(View.VISIBLE);
        mTitleET = (EditText) titleLL.findViewById(R.id.edittext_right);
        mTitleET.setHint(R.string.hintEdittextCartitle);
        mTitleET.setGravity(Gravity.LEFT);
        mCreatorTV = (TextView) creatorLL.findViewById(R.id.text_right2);
        mCreatorTV.setVisibility(View.VISIBLE);
        mContactET = (EditText) contactLL.findViewById(R.id.edittext_right);
        mPhoneET = (EditText) phoneLL.findViewById(R.id.edittext_right);
        mPhoneET.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        mPhoneET.setHint(R.string.hintEdittextPhoneSetting);
        mPhoneET.setMaxEms(11);
        mAddressTV = (TextView) addressLL.findViewById(R.id.text_right2);
        mAddressTV.setVisibility(View.VISIBLE);
    }

    private void initData() {
        imgPaths = new ArrayList<>();
        imageUrls = new ArrayList<>();
        mSheng = (Sheng) SPUtils.get(this, "DiQu", Sheng.class);
        imgPaths.add("");
        mAdapter = new ReleaseSelectPicAdapter(this, R.layout.item_photo, imgPaths);
        mGridView.setAdapter(mAdapter);
//        horizontal_layout(imgPaths, mGridView);
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


    private void initListener() {
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

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
                    Intent intent = new Intent(SecondCarReleaseActivity.this, MultiPhotoViewActivity.class);
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

        mRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.business://商家
                        categoryStr = "4";
                        mStoreLayout.setVisibility(View.VISIBLE);
                        break;
                    case R.id.personal: //个人
                        categoryStr = "5";
                        mStoreLayout.setVisibility(View.GONE);
                        break;
                }

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
                            mCar = gson.fromJson(jsonObject.getJSONObject("data").toString(), Car.class);
                        }
                        mPictureList = mCar.getImage_url();
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

    /**
     * 绑定数据
     */
    private void setViewData() {

        if (mCar.getCategory().equals("4")) {
            ((RadioButton) mRadiogroup.getChildAt(0)).setChecked(true);
        } else if (mCar.getCategory().equals("5")) {
            ((RadioButton) mRadiogroup.getChildAt(1)).setChecked(true);
        }
        categoryStr = mCar.getCategory();
        mCommonType = new CommonType(mCar.getType(), mCar.getLeibie());


        mTypeTV.setText(mCar.getLeibie());
        mTitleET.setText(mCar.getTitle());
        mDescribeET.setText(mCar.getContent());
        mCreatorTV.setText(mCar.getCreator());
        mContactET.setText(mCar.getContact());
        mPhoneET.setText(mCar.getPhone());
        mAddressTV.setText(mCar.getQuname() + mCar.getXiaoquname()); //
        shengId = mCar.getSheng();
        shiId = mCar.getShi();
        quId = mCar.getQu();
        xiaoquId = mCar.getXiaoqu();
        mStoreName.setText(mCar.getCompany());
        mMileageET.setText(mCar.getDistance()+"");
        imgPaths.clear();
        for (int i = 0; i < mPictureList.size(); i++) {
            imgPaths.add(ConnectUrl.REQUESTURL_IMAGE + mPictureList.get(i));
        }
        if (!adjustList(imgPaths) || imgPaths.size() < LIMIT_PIC_NUM) {
            imgPaths.add("");
        }
        mAdapter.notifyDataSetChanged();
//        horizontal_layout(imgPaths, mGridView);
        initSelectPicView();
    }


    @OnClick({R.id.back, R.id.submit, R.id.image_df, sort_layout, R.id.time_layout, R.id.location_layout, R.id.abstract_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                mBackDialog.show();
                break;
            case R.id.submit:
                if (MyApplication.getUser().getUser_money()<=0){
                    showBaseDialog("白银不足", PayCheckActivity.class);
                }else {
                    showPaySuccessDialog();
                }
                break;
            case R.id.image_df:
                selectorImage();
                break;
            case sort_layout:
                Bundle bundle = new Bundle();
                bundle.putSerializable("type", mCommonType);
                bundle.putString("flag", "cartype");
                go(CommonSelectedTypeActivity.class, bundle);
                break;
            case R.id.time_layout:
                onYearMonthPicker();
                break;
            case R.id.location_layout:
                Bundle bundleP = new Bundle();
                bundleP.putString("acFlag", "CarRelease");
                go(ReleaseAreaSearchActivity.class, bundleP);
//                onAddress2Picker();
                break;
            case R.id.abstract_layout:
                Bundle bundle1 = new Bundle();
                bundle1.putString("flag", "carDescribe");
                bundle1.putString("content", mDescribeET.getText().toString());
                go(ReleaseDescribeActivity.class, bundle1);
                break;

        }


    }

    /**
     * 数据判断
     */
    private void judgeData() {
        typeStr = mTypeTV.getText().toString();
        titleStr = mTitleET.getText().toString();
        desStr = mDescribeET.getText().toString();
        creStr = mCreatorTV.getText().toString();
        contStr = mContactET.getText().toString();
        phoneStr = mPhoneET.getText().toString();
        addStr = mAddressTV.getText().toString();
        if (TextUtils.isEmpty(mPriceET.getText().toString())) {
            price = 0;
        } else {
            price = Double.parseDouble(mPriceET.getText().toString());
        }
        if (TextUtils.isEmpty(mMileageET.getText().toString())) {
            mileage = 0;
        } else {
            mileage = Double.parseDouble(mMileageET.getText().toString());
        }
        company = mStoreName.getText().toString();
        if (TextUtils.isEmpty(typeStr)) {
            showToast(R.string.toasttype);
            return;
        }
        if (TextUtils.isEmpty(titleStr)) {
            showToast(R.string.toastTitle);
            return;
        }
        if (TextUtils.isEmpty(desStr)) {
            showToast(R.string.toastMiaoshu);
            return;
        }
        if (price <= 0) {
            showToast(R.string.toastprice);
            return;
        }
        if (TextUtils.isEmpty(creStr)) {
            showToast(R.string.toastFirstTime);
            return;
        }
        if (mileage <= 0) {
            showToast(R.string.toastLicheng);
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
        if (TextUtils.isEmpty(addStr)) {
            showToast(R.string.toastCarAddress);
            return;
        }
        if (categoryStr.equals("4")) {
            //商家
            if (TextUtils.isEmpty(company)) {
                showToast(R.string.toastStoreName);
                return;
            }
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
                Log.d("image", "image" + i + "  " + imgPaths.get(i));
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
//                httpUploadInfo();

            }
        });

    }

    private void httpUploadInfo() {
        Request<JSONObject> request = null;
        if (!flag.contains("update")) {
            request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_CAR_RELEASE, RequestMethod.POST);
            request.add("data[sheng]", mSheng.getId()); //河北省 石家庄 裕华 华山商务
            request.add("data[shi]", mSheng.getShiList().get(0).getId());
        } else {
            request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_MY_RELEASE_UPDATE, RequestMethod.POST);
            request.add("goodsid", mCar.getCarid());
            request.add("goodstype", mCar.getGoods_type());
            request.add("userid", MyApplication.getUser().getUserid());
            request.add("data[sheng]", shengId); //河北省 石家庄 裕华 华山商务
            request.add("data[shi]", shiId);
        }
        request.add("data[usersid]", MyApplication.getUser().getUserid());
        request.add("data[category]", categoryStr);//类型4商家5个人
        request.add("data[company]", company);
        request.add("data[type]", mCommonType.getId());
        request.add("data[title]", titleStr);
        request.add("data[distance]", mileage);
        request.add("data[content]", desStr);
        request.add("data[contact]", contStr);
        request.add("data[creator]", creStr);

        request.add("data[phone]", phoneStr);
        request.add("data[price]", price);
        request.add("data[sheng]", mSheng.getId()); //河北省 石家庄 裕华 华山商务
        request.add("data[shi]", mSheng.getShiList().get(0).getId());
        request.add("data[qu]", quId);
        request.add("data[xiaoqu]", xiaoquId);
        request.add("data[address]", MyApplication.getLocation().getStreet());
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
                            user.setUser_money(user.getUser_money()-1);
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

    private void emptyView() {
        mTypeTV.setText("");
        mTitleET.setText("");
        mDescribeET.setText("");
        mCreatorTV.setText("");
        mContactET.setText("");
        mPhoneET.setText("");
        mAddressTV.setText("");
        imgPaths.clear();
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

        Intent intent = new Intent(SecondCarReleaseActivity.this, ImageSelectorActivity.class);
        intent.putExtra(ImageSelectorActivity.EXTRA_MAX_SELECT_NUM, count);
        intent.putExtra(ImageSelectorActivity.EXTRA_SELECT_MODE, ImageSelectorActivity.MODE_MULTIPLE);
        intent.putExtra(ImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        intent.putExtra(ImageSelectorActivity.EXTRA_ENABLE_PREVIEW, true);
        intent.putExtra(ImageSelectorActivity.EXTRA_ENABLE_CROP, false);
        startActivityForResult(intent, Constants.Codes.ALBUM);
    }

    /**
     * 设置图片选择布局
     */
    private void initSelectPicView() {
        if (imgPaths.size() == 1 && imgPaths.get(0).equals("")) {
            mPicBg.setBackground(getResources().getDrawable(R.drawable.icon_release_car_bg));
            mGridView.setVisibility(View.GONE);
            mAddBtn.setVisibility(View.VISIBLE);
            mUploadTV.setVisibility(View.VISIBLE);
        } else if (imgPaths.size() == 0) {
            mPicBg.setBackground(getResources().getDrawable(R.drawable.icon_release_car_bg));
            mGridView.setVisibility(View.GONE);
            mAddBtn.setVisibility(View.VISIBLE);
            mUploadTV.setVisibility(View.VISIBLE);
        } else {
            mPicBg.setBackground(getResources().getDrawable(R.color.white));
            mGridView.setVisibility(View.VISIBLE);
            mAddBtn.setVisibility(View.GONE);
            mUploadTV.setVisibility(View.GONE);
        }
    }

    //日期选择器
    public void onYearMonthPicker() {

        DatePicker picker = new DatePicker(this, DatePicker.YEAR_MONTH);
        picker.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL);
        picker.setWidth((int) (picker.getScreenWidthPixels() * 0.5));
//        picker.setAnimationStyle(R.style.goods_info_dialog);

        Calendar c = Calendar.getInstance();//
        int year = c.get(Calendar.YEAR);// 获取当前年份
        int month = c.get(Calendar.MONTH) + 1;// 获取当前月份
//        mDay = c.get(Calendar.DAY_OF_MONTH); // 获取当日期

        picker.setRangeStart(2000, 1);
        picker.setRangeEnd(year, month);
        picker.setSelectedItem(year, month);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthPickListener() {
            @Override
            public void onDatePicked(String year, String month) {
                mCreatorTV.setText(year + "年" + "-" + month + "月");
            }
        });
        picker.show();

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

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Subscribe
    public void onEvent(EventFlag eventFlag) {
        if (eventFlag.getFlag().equals("cartype")) {
            mCommonType = (CommonType) eventFlag.getObject();
            mTypeTV.setText(mCommonType.getName());
        } else if (eventFlag.getFlag().equals("carDescribe")) {
            mDescribeET.setText((String) eventFlag.getObject());
        }
        if (eventFlag.getFlag().equals("CarRelease")) {
            Sheng s = (Sheng) eventFlag.getObject();
            quId = s.getQu().getId();
            xiaoquId = s.getXiaoqu().getId();
            mAddressTV.setText(s.getQu().getName() + s.getXiaoqu().getName());
        }


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
//                horizontal_layout(imgPaths, mGridView);
            }

        }
    }
    // 购买成功
    private void showPaySuccessDialog() {
        final Dialog mDialog = new Dialog(SecondCarReleaseActivity.this, R.style.Dialog);
        mDialog.setContentView(R.layout.dialog_promt_pay_silver);
        TextView info = (TextView) mDialog.findViewById(R.id.info);
        info.setText("您的白银还剩余"+ MyApplication.getUser().getUser_money()+"两~");
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
    public void onBackPressed() {
        mBackDialog.show();
    }
}
