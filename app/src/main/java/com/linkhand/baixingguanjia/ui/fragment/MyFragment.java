package com.linkhand.baixingguanjia.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseFragment;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.base.MyApplication;
import com.linkhand.baixingguanjia.entity.EventFlag;
import com.linkhand.baixingguanjia.ui.activity.LoginActivity;
import com.linkhand.baixingguanjia.ui.activity.MessageActivity;
import com.linkhand.baixingguanjia.ui.activity.SettingActivity;
import com.linkhand.baixingguanjia.ui.activity.my.MyAppointmentActivity;
import com.linkhand.baixingguanjia.ui.activity.my.MyCollectActivity;
import com.linkhand.baixingguanjia.ui.activity.my.MyCommentActivity;
import com.linkhand.baixingguanjia.ui.activity.my.MyAccountActivity;
import com.linkhand.baixingguanjia.ui.activity.my.MyFeedBackActivity;
import com.linkhand.baixingguanjia.ui.activity.my.MyOrderActivity;
import com.linkhand.baixingguanjia.ui.activity.my.MyReleaseActivity;
import com.linkhand.baixingguanjia.ui.activity.my.UserInfoActivity;
import com.linkhand.baixingguanjia.utils.ImageUtils;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.linkhand.baixingguanjia.R.id.back_layout;
import static com.linkhand.baixingguanjia.R.id.header_image_layout;
import static com.linkhand.baixingguanjia.R.id.header_iv;
import static com.linkhand.baixingguanjia.base.ConnectUrl.REQUESTURL_IMAGE;

/**
 * Created by JCY on 2017/6/14.
 * 说明：
 */

public class MyFragment extends BaseFragment {
    private static final int REQUEST_MESSAGE = 1;
    @Bind(R.id.location_layout)
    LinearLayout mSettingLayout;
    @Bind(R.id.message)
    RelativeLayout mMessageLayout;
    @Bind(header_iv)
    ImageView mHeaderIV;
    @Bind(R.id.name)
    TextView mNameTV;
    @Bind(R.id.order)
    TextView mOrder;
    @Bind(R.id.show_order)
    TextView mShowOrder;
    @Bind(R.id.image)
    ImageView mImage;
    @Bind(R.id.order_layout)
    RelativeLayout mOrderLayout;
    @Bind(R.id.payment_layout)
    LinearLayout mPaymentLayout;
    @Bind(R.id.take_layout)
    LinearLayout mTakeLayout;
    @Bind(back_layout)
    LinearLayout mBackLayout;
    @Bind(R.id.setting)
    TextView mSetting;
    @Bind(header_image_layout)
    LinearLayout headerLayout;
    @Bind(R.id.message_num)
    TextView mMessageNumTV;
    @Bind(R.id.silver)
    TextView mSilverTV;
    @Bind(R.id.gold)
    TextView mGoldTV;


    private View mView;
    private RequestQueue mQueue = NoHttp.newRequestQueue();

    @Override
    public void onResume() {
        super.onResume();
        setStatusBarColor(R.color.colorSystemBlack);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_my, null);
        ButterKnife.bind(this, mView);
        setStatusBarColor(R.color.colorSystemBlack);
        initView();
        initListener();
        httpMessageCount();
        return mView;
    }


    private void initView() {
        if (MyApplication.getUser() == null) {
            mNameTV.setText(R.string.pleaseLogin);
            ImageUtils.setCircleImage(mHeaderIV, R.drawable.icon_default_header, "");
        } else {
            mNameTV.setText(MyApplication.getUser().getNickname());
            ImageUtils.setCircleImage(mHeaderIV, R.drawable.icon_default_header, REQUESTURL_IMAGE + MyApplication.getUser().getHead_url());
//            ImageUtils.setCircleImage(mHeaderIV, R.drawable.icon_default_header, ConnectUrl.testUrl);
            mGoldTV.setText(MyApplication.getUser().getUser_gold() + "");
            mSilverTV.setText(MyApplication.getUser().getUser_money() + "");
        }
    }

    private void initListener() {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Subscribe
    public void onEvent(EventFlag eventFlag) {
        if (eventFlag.getFlag().equals("nickName")) {
            mNameTV.setText(MyApplication.getUser().getNickname());
        } else if (eventFlag.getFlag().equals("uploadHeaderSuccess")) {
            ImageUtils.setCircleImage(mHeaderIV, R.drawable.icon_default_header, REQUESTURL_IMAGE + MyApplication.getUser().getHead_url());
        }

        if (eventFlag.getFlag().equals("jpushMessageCount")) {
            int count = eventFlag.getPosition();
            if (count <= 0) {
                mMessageNumTV.setVisibility(View.GONE);
            } else if (count > 0 && count <= 99) {
                mMessageNumTV.setVisibility(View.VISIBLE);
                mMessageNumTV.setText(count + "");
            } else {
                mMessageNumTV.setVisibility(View.VISIBLE);
                mMessageNumTV.setText("99+");
            }
        }
        if (eventFlag.getFlag().equals("updateMessageCount")) {
            int count = Integer.parseInt(mMessageNumTV.getText().toString());
            count--;
            if (count <= 0) {
                mMessageNumTV.setVisibility(View.GONE);
            } else if (count > 0 && count <= 99) {
                mMessageNumTV.setVisibility(View.VISIBLE);
                mMessageNumTV.setText(count + "");
            } else {
                mMessageNumTV.setVisibility(View.VISIBLE);
                mMessageNumTV.setText("99+");
            }
        }
        if (eventFlag.getFlag().equals("paySilver")) {
            mSilverTV.setText(MyApplication.getUser().getUser_money());
        }
        if (eventFlag.getFlag().equals("updateGold")) {
            mGoldTV.setText(MyApplication.getUser().getUser_gold());

        }


    }


    private void httpMessageCount() {
        if (MyApplication.getUser() == null) {
            return;
        }
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_MYNEWS_COUNT, RequestMethod.POST);
        request.add("userid", MyApplication.getUser().getUserid());
        mQueue.add(REQUEST_MESSAGE, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == REQUEST_MESSAGE) {
                    String resultCode = null;
                    try {
                        JSONObject jsonObject = response.get();
                        resultCode = jsonObject.getString("code");
                        if (resultCode.equals("200")) {
                            int count = jsonObject.getInt("data");
                            EventBus.getDefault().post(new EventFlag("jpushMessageCount", count));
                        } else {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void onHiddenChanged(boolean hidden) {
// TODO Auto-generated method stub
        super.onHiddenChanged(hidden);
        if (hidden) {// 不在最前端界面显示

        } else {// 重新显示到最前端中
            setStatusBarColor(R.color.colorSystemBlack);
        }
    }

    @OnClick({R.id.setting, R.id.info_layout, R.id.appointment_layout, R.id.fabu_layout, R.id.fankui_layout, R.id.collect_layout, R.id.pingjia_layout, header_image_layout, R.id.message, R.id.order_layout, R.id.payment_layout, R.id.take_layout, R.id.back_layout, R.id.layout_gold, R.id.layout_silver})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.setting:
//                startActivity(new Intent(getActivity(), SettingActivity.class));
                if (MyApplication.getUser() == null) {
                    go(LoginActivity.class);
                } else {
                    go(SettingActivity.class);
                }
                break;
            case R.id.info_layout:
                break;
            case R.id.appointment_layout:
                if (MyApplication.getUser() == null) {
                    go(LoginActivity.class);
                } else {
                    go(MyAppointmentActivity.class);
                }
                break;
            case R.id.fabu_layout:
                if (MyApplication.getUser() == null) {
                    go(LoginActivity.class);
                } else {
                    go(MyReleaseActivity.class);
                }
                break;
            case R.id.fankui_layout:
                if (MyApplication.getUser() == null) {
                    go(LoginActivity.class);
                } else {
                    go(MyFeedBackActivity.class);
                }
                break;
            case R.id.collect_layout:
                if (MyApplication.getUser() == null) {
                    go(LoginActivity.class);
                } else {
                    go(MyCollectActivity.class);
                }
                break;
            case R.id.pingjia_layout:
                if (MyApplication.getUser() == null) {
                    go(LoginActivity.class);
                } else {
                    go(MyCommentActivity.class);
                }
                break;
            case header_image_layout:
                if (MyApplication.getUser() == null) {
                    go(LoginActivity.class);
                } else {
                    go(UserInfoActivity.class);
                }

                break;
            case R.id.message:
                if (MyApplication.getUser() == null) {
                    go(LoginActivity.class);
                } else {
                    go(MessageActivity.class);
                }
                break;
            case R.id.order_layout:
                if (MyApplication.getUser() == null) {
                    go(LoginActivity.class);
                } else {
                    go(MyOrderActivity.class);
                }
                break;
            case R.id.payment_layout:
                if (MyApplication.getUser() == null) {
                    go(LoginActivity.class);
                } else {

                }
                break;
            case R.id.take_layout:
                if (MyApplication.getUser() == null) {
                    go(LoginActivity.class);
                } else {

                }
                break;
            case R.id.back_layout:
                if (MyApplication.getUser() == null) {
                    go(LoginActivity.class);
                } else {

                }
                break;
            case R.id.layout_silver:
                if (MyApplication.getUser() == null) {
                    go(LoginActivity.class);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("viewFlag", "silver");
                    go(MyAccountActivity.class, bundle);
                }
                break;
            case R.id.layout_gold:
                if (MyApplication.getUser() == null) {
                    go(LoginActivity.class);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("viewFlag", "gold");
                    go(MyAccountActivity.class, bundle);
                }
                break;
        }
    }


}
