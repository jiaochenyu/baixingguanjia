package com.linkhand.baixingguanjia.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseFragment;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.base.MyApplication;
import com.linkhand.baixingguanjia.entity.Appointment;
import com.linkhand.baixingguanjia.ui.activity.PassiveAppointmentDetailActivity;
import com.linkhand.baixingguanjia.ui.activity.detail.EducationDetailActivity;
import com.linkhand.baixingguanjia.ui.activity.detail.HousePropertyDetailActivity;
import com.linkhand.baixingguanjia.ui.activity.detail.HousekeepingDetailActivity;
import com.linkhand.baixingguanjia.ui.activity.detail.IdleGoodsDetailActivity;
import com.linkhand.baixingguanjia.ui.activity.detail.PublicWelfareDetailActivity;
import com.linkhand.baixingguanjia.ui.activity.detail.RecruitDetailActivity;
import com.linkhand.baixingguanjia.ui.activity.detail.SecondhandCarDetail2Activity;
import com.linkhand.baixingguanjia.ui.adapter.my.MyAppointmentListViewAdapter;
import com.linkhand.baixingguanjia.ui.adapter.my.MyPassiveAppointmentListViewAdapter;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JCY on 2017/6/27.
 * 说明：  我的预约
 */

public class MyAppointmentFragment extends BaseFragment {
    private static final String TAG = "MyAppointmentFragment_info";
    private final static int REQUEST = 0;
    View view;
    int type;
    @Bind(R.id.listview)
    PullToRefreshListView mListview;
    @Bind(R.id.null_bg)
    RelativeLayout mNullBg;

    List<Appointment> mList;
    MyAppointmentListViewAdapter mAdapter;  //我预约的
    MyPassiveAppointmentListViewAdapter mPassiveAdapter; //预约我的
    private int pageFlag;
    private RequestQueue mRequestQueue = NoHttp.newRequestQueue();
    Dialog mDialog;// 评论界面

    public MyAppointmentFragment(int type) {
        this.type = type;
        switch (type) {
            case 1:
                Log.d(TAG, "MyAppointmentFragment: 我预约的");
                break;
            case 2:
                Log.d(TAG, "MyAppointmentFragment: 预约我的");
                break;


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_appoinment, container, false);
        ButterKnife.bind(this, view);
        initView();
        initData();
        showLoading();
        httpGetList();
        initListener();
        return view;
    }

    private void initView() {

    }

    private void initData() {
        mList = new ArrayList<>();
        if (type == 1) {
            mAdapter = new MyAppointmentListViewAdapter(getActivity(), R.layout.item_my_appointment, mList);
            mListview.setAdapter(mAdapter);
        } else {
            mPassiveAdapter = new MyPassiveAppointmentListViewAdapter(getActivity(), R.layout.item_my_appointment_passive, mList);
            mListview.setAdapter(mPassiveAdapter);
        }

    }

    private void initListener() {

        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (type == 1) {
                    Appointment appointment = mList.get(position);
                    if (appointment.getDelete().equals("2")) {
                        showToast(R.string.toast_service_delete);
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putString("flag", "my"); // 需要调用接口获取数据
                        bundle.putString("goods_type", appointment.getGoods_type());
                        bundle.putString("goodsid", appointment.getGoodsid());
                        goActivityDetail(appointment.getGoods_type(), bundle);
                    }
//                    startActivity(new Intent(getActivity(), AppointmentDetailActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), PassiveAppointmentDetailActivity.class));
                }
            }
        });
        //上拉加载下拉刷新监听
        mListview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //重新加载
                pageFlag = 1;
                httpGetList();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                httpGetList();
            }
        });
        if (type == 1) {
            mAdapter.setEvaluateOnClick(new MyAppointmentListViewAdapter.EvaluateOnClick() {
                @Override
                public void onClick(int position) {
                    showDialog(position);
                }
            });
        }

    }


    private void httpGetList() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_MY_APPOINTMENT_LIST, RequestMethod.POST);
        request.add("userid", MyApplication.getUser().getUserid());
        request.add("bespoke", type);
        request.add("page", pageFlag);
        mRequestQueue.add(REQUEST, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == REQUEST) {
                    String resultCode = null;
                    Gson gson = new Gson();
                    try {
                        if (pageFlag == 1) {
                            mList.clear();
                        }
                        JSONObject jsonObject = response.get();
                        resultCode = jsonObject.getString("code");
                        if (resultCode.equals("200")) {
                            JSONArray array = jsonObject.getJSONArray("data");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jsonObject1 = array.getJSONObject(i);
                                Appointment appointment = gson.fromJson(jsonObject1.toString(), Appointment.class);
                                mList.add(appointment);
                            }

                        } else if (resultCode.equals("201")) {
//                            Toast.makeText(getActivity(), "账号或密码有误", Toast.LENGTH_SHORT).show();
                        }
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
                hideLoading();
                mListview.onRefreshComplete();
                if (type == 1) {
                    mAdapter.notifyDataSetChanged();
                } else {
                    mPassiveAdapter.notifyDataSetChanged();
                }
                pageFlag++;
                if (!adjustList(mList)) {
                    mNullBg.setVisibility(View.VISIBLE);
                } else {
                    mNullBg.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 评价
     */
    private void httpComment(final int pos, String content) {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_MY_COMMENT, RequestMethod.POST);
        request.add("userid", MyApplication.getUser().getUserid());
        request.add("content", content);
        request.add("goodsid", mList.get(pos).getGoodsid());
        request.add("goodstype", mList.get(pos).getGoods_type());
        mRequestQueue.add(REQUEST, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
                showLoading();
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == REQUEST) {
                    String resultCode = null;
                    Gson gson = new Gson();
                    try {
                        JSONObject jsonObject = response.get();
                        resultCode = jsonObject.getString("code");
                        if (resultCode.equals("200")) {
                            showToast(R.string.comment_success);
                            mDialog.dismiss();
                            for (int i = 0; i < mList.size(); i++) {
                                if (mList.get(i).getGoodsid().equals(mList.get(pos).getGoodsid())) {
                                    mList.get(i).setComment("1");
                                }
                            }
//                            mList.get(pos).setComment("1");
                            mAdapter.notifyDataSetChanged();
                        } else {
                            showToast(R.string.comment_fail);
                        }
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
                hideLoading();
            }
        });
    }

    private void showDialog(final int position) {
        mDialog = new Dialog(getActivity(), R.style.Dialog);
        mDialog.setContentView(R.layout.dialog_appointment_evaluate);

        TextView submit = (TextView) mDialog.findViewById(R.id.submit);
        final EditText contentET = (EditText) mDialog.findViewById(R.id.content_edit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(contentET.getText().toString())) {
                    Toast.makeText(getActivity(), "请输入评价内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                httpComment(position, contentET.getText().toString());
                //
            }
        });
        mDialog.show();
    }

    public void goActivityDetail(String type, Bundle bundle) {
        //1=>二手房产 2=>家政 3=>闲置物品 5=>招聘 6=>教育 7=>二手车 8=>公益
        if (type.equals("1")) {
            go(HousePropertyDetailActivity.class, bundle);
        } else if (type.equals("2")) {
            go(HousekeepingDetailActivity.class, bundle);
        } else if (type.equals("3")) {
            go(IdleGoodsDetailActivity.class, bundle);
        } else if (type.equals("5")) {
            go(RecruitDetailActivity.class, bundle);
        } else if (type.equals("6")) {
            go(EducationDetailActivity.class, bundle);
        } else if (type.equals("7")) {
            go(SecondhandCarDetail2Activity.class, bundle);
        } else if (type.equals("8")) {
            go(PublicWelfareDetailActivity.class, bundle);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
