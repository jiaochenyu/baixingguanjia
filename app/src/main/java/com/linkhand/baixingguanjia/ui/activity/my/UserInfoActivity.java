package com.linkhand.baixingguanjia.ui.activity.my;

import android.app.Dialog;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
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
import com.linkhand.baixingguanjia.entity.User;
import com.linkhand.baixingguanjia.ui.activity.MainActivity;
import com.linkhand.baixingguanjia.utils.ImageUtils;
import com.linkhand.baixingguanjia.utils.SPUtils;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;


public class UserInfoActivity extends BaseActivity {
    private static final int REQUEST_CODE = 0;

    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.address_layout)
    RelativeLayout mAddressLayout;
    @Bind(R.id.statu)
    TextView mStatuTV;
    @Bind(R.id.face_layout)
    RelativeLayout mFaceLayout;
    @Bind(R.id.my_cell)
    TextView mMyCellTV;
    ImageView mHeaderIV;
    TextView mNickTV;
    TextView mUsernameTV;
    String imagePath = "";
    String nickName = "";
    String myCell = "";

    RequestQueue mQueue = NoHttp.newRequestQueue();
    Dialog mDialog;
    EditText mDialogEdit; //修改昵称

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        initView();
        initData();
        initDialog();
    }


    private void initView() {
        LinearLayout mLayout1 = (LinearLayout) findViewById(R.id.touxiang_layout);
        LinearLayout mLayout2 = (LinearLayout) findViewById(R.id.nick_layout);
        LinearLayout mLayout3 = (LinearLayout) findViewById(R.id.username_layout);
        LinearLayout mLayout4 = (LinearLayout) findViewById(R.id.password_layout);
        TextView mTextRight1 = (TextView) mLayout1.findViewById(R.id.text_right);
        TextView mTextLeft1 = (TextView) mLayout1.findViewById(R.id.text_left);
        mHeaderIV = (ImageView) mLayout1.findViewById(R.id.image);
        mHeaderIV.setVisibility(View.VISIBLE);
        mTextLeft1.setText("头像");
        mTextRight1.setVisibility(View.GONE);

        mNickTV = (TextView) mLayout2.findViewById(R.id.text_right);
        TextView mTextLeft2 = (TextView) mLayout2.findViewById(R.id.text_left);
        mNickTV.setText(MyApplication.getUser().getNickname());
        mTextLeft2.setText("昵称");

        TextView mTextRight3 = (TextView) mLayout3.findViewById(R.id.text_right);
        TextView mTextLeft3 = (TextView) mLayout3.findViewById(R.id.text_left);
        mTextRight3.setText("");
        mTextLeft3.setText("用户名");

        TextView mTextRight4 = (TextView) mLayout4.findViewById(R.id.text_right);
        TextView mTextLeft4 = (TextView) mLayout4.findViewById(R.id.text_left);
        mTextRight4.setVisibility(View.GONE);
        mTextLeft4.setText("修改密码");

        if (TextUtils.isEmpty(MyApplication.getUser().getXiaoquname())) {
            mMyCellTV.setText(R.string.addMyCell);
        } else {
            mMyCellTV.setText(MyApplication.getUser().getXiaoquname());
        }


    }

    private void initData() {
        imagePath = MyApplication.getUser().head_url == null ? "" : MyApplication.getUser().head_url;
        ImageUtils.setCircleImage(mHeaderIV, R.drawable.icon_default_header, ConnectUrl.REQUESTURL_IMAGE + imagePath);
        nickName = MyApplication.getUser().getNickname();
    }

    private void initDialog() {
        mDialog = new Dialog(UserInfoActivity.this, R.style.Dialog);
        mDialog.setContentView(R.layout.dialog_common_edittext);
        TextView mDialogMessage = (TextView) mDialog.findViewById(R.id.dialog_message);
        mDialogMessage.setText(R.string.updateNickname);
        mDialogEdit = (EditText) mDialog.findViewById(R.id.edit);
        TextView mNotextview = (TextView) mDialog.findViewById(R.id.notextview);
        TextView mYestextview = (TextView) mDialog.findViewById(R.id.yestextview);
        mDialogEdit.setText(nickName);
        mDialogEdit.setMaxEms(15);
        mNotextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //弹出框点击事件
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
//                    contentET.getText().toString();
                }
            }
        });
        mYestextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = mDialogEdit.getText().toString();
                if (TextUtils.isEmpty(temp)) {
                    showToast(R.string.pleasePutnickname);
                } else {
                    nickName = temp;
                    httpUploadUserInfo();
                    if (mDialog != null && mDialog.isShowing()) {
                        mDialog.dismiss();
                    }
                }


            }
        });
    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Subscribe
    public void onEvent(EventFlag eventFlag) {
        if (eventFlag.getFlag().equals("uploadHeaderSuccess")) {
            imagePath = eventFlag.getObject().toString();
            ImageUtils.setCircleImage(mHeaderIV, R.drawable.icon_default_header, ConnectUrl.REQUESTURL_IMAGE + imagePath);
//            httpUploadUserInfo();
        }
        if (eventFlag.getFlag().equals("updateCell")) {
            mMyCellTV.setText(MyApplication.getUser().getXiaoquname());
            jPushMethod();
        }

    }
    private void jPushMethod() {
        JPushInterface.init(this);
        JPushInterface.resumePush(this);
        String id = MyApplication.getUser().getUserid();
        JPushInterface.setAlias(this, id, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {

            }
        });
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(UserInfoActivity.this);
//        builder.statusBarDrawable = R.mipmap.logos;
//        builder.notificationDefaults = Notification.DEFAULT_VIBRATE;//震动
        builder.notificationFlags = Notification.FLAG_SHOW_LIGHTS; //闪烁灯
        JPushInterface.setPushNotificationBuilder(1, builder);
        Set<String> sets = new HashSet<>();
        sets.add(MyApplication.getUser().getXiaoqu());
        JPushInterface.setTags(this, sets, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
            }
        });
    }


    @OnClick({R.id.back, R.id.address_layout, R.id.nick_layout, R.id.username_layout, R.id.password_layout, R.id.touxiang_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.address_layout:
                startActivity(new Intent(UserInfoActivity.this, AddCommunityActivity.class));


                break;

            case R.id.nick_layout:
                mDialog.show();
                mDialogEdit.setText(nickName);
                break;
            case R.id.username_layout:

                break;
            case R.id.password_layout:

                break;
            case R.id.touxiang_layout:
                go(PreviewActivity.class);
                break;
        }
    }

    private void httpUploadUserInfo() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.USER_INFO_UPDATE, RequestMethod.POST);
        request.add("userid", MyApplication.getUser().getUserid());
        request.add("head_url", imagePath);
        request.add("nickname", nickName);
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
                            SPUtils.put(UserInfoActivity.this, "userInfo", user);
                            mNickTV.setText(nickName);
                            EventBus.getDefault().post(new EventFlag("nickName", nickName));
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
