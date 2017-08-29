package com.linkhand.baixingguanjia.ui.activity.my;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.base.MyApplication;
import com.linkhand.baixingguanjia.entity.EventFlag;
import com.linkhand.baixingguanjia.entity.Sheng;
import com.linkhand.baixingguanjia.entity.User;
import com.linkhand.baixingguanjia.utils.JSONUtils;
import com.linkhand.baixingguanjia.utils.SPUtils;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.picker.AddressPicker;
import cn.qqtheme.framework.util.LogUtils;

/**
 * 添加小区
 */

public class AddCommunityActivity extends BaseActivity {

    private static final int REQUEST_CODE = 0;
    private static final int REQUEST_CODE2 = 1;
    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.title)
    TextView mTitleTV;
    @Bind(R.id.save)
    TextView mSaveTV;

    TextView mNameTV; //小区名称
    EditText mperiodsET; //期数
    EditText mGroupET; //组团数
    EditText mBuildET; //楼栋号
    EditText mUnitET; //单元号
    EditText mFloorET; //楼层号
    EditText mRoomET; //房间号
    String nameStr, qishuStr, groupStr, buildStr, unitStr, floorStr, roomStr;
    String shengid, shiid, quid, xiaoquid;

    ArrayList<String> mAddCellArray = new ArrayList<>();
    Sheng mSheng;
    private RequestQueue mQueue = NoHttp.newRequestQueue();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_community);
        ButterKnife.bind(this);
        initView();
        initViewData();
        initData();
    }


    private void initData() {
        mSheng = (Sheng) SPUtils.get(this, "DiQu", Sheng.class);

        shengid = MyApplication.getUser().getSheng();
        shiid = MyApplication.getUser().getShi();
        quid = MyApplication.getUser().getQu();
        xiaoquid = MyApplication.getUser().getXiaoqu();
    }

    private void initView() {
        if (TextUtils.isEmpty(MyApplication.getUser().getQu_id())) {
            mTitleTV.setText(R.string.addMyCell);
        } else {
            mTitleTV.setText(R.string.updateMyCell);
        }
        RelativeLayout mLayout0 = (RelativeLayout) findViewById(R.id.header_layout);
        RelativeLayout mLayout1 = (RelativeLayout) findViewById(R.id.layout1);
        LinearLayout mLayout2 = (LinearLayout) findViewById(R.id.layout2);
        LinearLayout mLayout3 = (LinearLayout) findViewById(R.id.layout3);
        LinearLayout mLayout4 = (LinearLayout) findViewById(R.id.layout4);
        LinearLayout mLayout5 = (LinearLayout) findViewById(R.id.layout5);
        LinearLayout mLayout6 = (LinearLayout) findViewById(R.id.layout6);
        LinearLayout mLayout7 = (LinearLayout) findViewById(R.id.layout7);
        mLayout0.setBackground(getResources().getDrawable(R.color.colorSystemBlack));
        TextView title = ((TextView) mLayout0.findViewById(R.id.title));
        title.setTextColor(getResources().getColor(R.color.colorWhite));

        ((TextView) mLayout1.findViewById(R.id.text_left)).setText("小区名称");
        ((TextView) mLayout2.findViewById(R.id.text_left)).setText("期数");
        ((TextView) mLayout3.findViewById(R.id.text_left)).setText("组团数");
        ((TextView) mLayout4.findViewById(R.id.text_left)).setText("楼栋号");
        ((TextView) mLayout5.findViewById(R.id.text_left)).setText("单元号");
        ((TextView) mLayout6.findViewById(R.id.text_left)).setText("楼层号");
        ((TextView) mLayout7.findViewById(R.id.text_left)).setText("房间号");

        mNameTV = (TextView) mLayout1.findViewById(R.id.text_right);
        mperiodsET = (EditText) mLayout2.findViewById(R.id.edittext_right);
        mGroupET = (EditText) mLayout3.findViewById(R.id.edittext_right);
        mBuildET = (EditText) mLayout4.findViewById(R.id.edittext_right);
        mUnitET = (EditText) mLayout5.findViewById(R.id.edittext_right);
        mFloorET = (EditText) mLayout6.findViewById(R.id.edittext_right);
        mRoomET = (EditText) mLayout7.findViewById(R.id.edittext_right);
        mNameTV.setHint("请选择小区");
        mperiodsET.setHint("请输入期数");
        mGroupET.setHint("请输入组团数");
        mBuildET.setHint("请输入楼栋号");
        mUnitET.setHint("请输入单元号");
        mFloorET.setHint("请输入楼层号");
        mRoomET.setHint("请输入房间号");

        mperiodsET.setInputType(InputType.TYPE_CLASS_NUMBER);
        mGroupET.setInputType(InputType.TYPE_CLASS_NUMBER);
        mBuildET.setInputType(InputType.TYPE_CLASS_NUMBER);
        mUnitET.setInputType(InputType.TYPE_CLASS_NUMBER);
        mFloorET.setInputType(InputType.TYPE_CLASS_NUMBER);
        mRoomET.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    private void initViewData() {
        User user = MyApplication.getUser();
        mNameTV.setText(user.getXiaoquname() == null ? "" : user.getXiaoquname());
        mperiodsET.setText(user.getQishu() == null ? "" : user.getQishu());
        mGroupET.setText(user.getZutuan() == null ? "" : user.getZutuan());
        mBuildET.setText(user.getLouhao() == null ? "" : user.getLouhao());
        mUnitET.setText(user.getDanyuan() == null ? "" : user.getDanyuan());
        mFloorET.setText(user.getLouceng() == null ? "" : user.getLouceng());
        mRoomET.setText(user.getFangjian() == null ? "" : user.getFangjian());
    }

    @OnClick({R.id.back, R.id.save, R.id.layout1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.layout1:
                //选择小区
                onAddress2Picker();
                break;
            case R.id.save:
                adjugeData();
                break;
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
            picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
                @Override
                public void onAddressPicked(Province province, City city, County county) {
                    mNameTV.setText(county.getName());
                    shengid = mSheng.getId();
                    shiid = (String) province.getId();
                    quid = (String) city.getId();
                    xiaoquid = (String) county.getId();
                }
            });
            picker.show();
        } catch (Exception e) {
            showToast(LogUtils.toStackTraceString(e));
        }
    }

    private void adjugeData() {
        //nameStr,qishuStr,groupStr,buildStr,unitStr,floorStr,roomStr;
        nameStr = mNameTV.getText().toString();
        qishuStr = mperiodsET.getText().toString();
        groupStr = mGroupET.getText().toString();
        buildStr = mBuildET.getText().toString();
        unitStr = mUnitET.getText().toString();
        floorStr = mFloorET.getText().toString();
        roomStr = mRoomET.getText().toString();
        if (TextUtils.isEmpty(nameStr)) {
            showToast(R.string.toastSelectXiaoqu);
            return;
        }
        //判断是更新还是添加。
        if (TextUtils.isEmpty(MyApplication.getUser().getQu_id())) {
            httpAddCell();
        } else {
            httpUpdateCell();
        }


    }

    /**
     * 添加小区
     */
    private void httpAddCell() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.USER_INFO_ADD_XIAOQU, RequestMethod.POST);
        request.add("data[userid]", MyApplication.getUser().getUserid());
        request.add("data[title]", qishuStr); //期数
        request.add("data[content]", groupStr); //团数
        request.add("data[phone]", buildStr); // 楼栋号
        request.add("data[address]", unitStr); //单元号
        request.add("data[creator]", floorStr); //楼层号
        request.add("data[tel]", roomStr); //房间号
        request.add("data[sheng]", shengid); //
        request.add("data[shi]", shiid);
        request.add("data[qu]", quid);
        request.add("data[xiaoqu]", xiaoquid);

        mQueue.add(REQUEST_CODE, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
                showLoading();
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == REQUEST_CODE) {
                    JSONObject jsonObject = response.get();
                    try {
                        if (jsonObject.getString("code").equals("200")) {
                            Gson gson = new Gson();
                            User user = gson.fromJson(jsonObject.get("data").toString(), User.class);
                            SPUtils.put(AddCommunityActivity.this, "userInfo", user);
                            EventBus.getDefault().post(new EventFlag("updateCell", user));
                            showToast(R.string.uploadsuccess);
                        } else {
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
                hideLoading();
            }

            @Override
            public void onFinish(int what) {
                hideLoading();
            }
        });

    }

    private void httpUpdateCell() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.USER_INFO_UPDATE_XIAOQU, RequestMethod.POST);
        request.add("data[qu_id]", MyApplication.getUser().getQu_id());
        request.add("data[userid]", MyApplication.getUser().getUserid());
        request.add("data[title]", qishuStr); //期数
        request.add("data[content]", groupStr); //团数
        request.add("data[phone]", buildStr); // 楼栋号
        request.add("data[address]", unitStr); //单元号
        request.add("data[creator]", floorStr); //楼层号
        request.add("data[tel]", roomStr); //房间号
        request.add("data[sheng]", shengid); //
        request.add("data[shi]", shiid);
        request.add("data[qu]", quid);
        request.add("data[xiaoqu]", xiaoquid);

        mQueue.add(REQUEST_CODE2, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
                showLoading();
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == REQUEST_CODE2) {
                    JSONObject jsonObject = response.get();
                    try {
                        if (jsonObject.getString("code").equals("200")) {
                            Gson gson = new Gson();
                            User user = gson.fromJson(jsonObject.get("data").toString(), User.class);
                            SPUtils.put(AddCommunityActivity.this, "userInfo", user);
                            EventBus.getDefault().post(new EventFlag("updateCell", user));
                            showToast(R.string.uploadsuccess);
                        } else {
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
                hideLoading();
            }

            @Override
            public void onFinish(int what) {
                hideLoading();
            }
        });

    }


}
