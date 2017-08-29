package com.linkhand.baixingguanjia.ui.activity.release;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.entity.EventFlag;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecruitFuliActivity extends BaseActivity {
    private static final int HTTP_QEQUEST = 1;

    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.right_text)
    TextView mSave;
    @Bind(R.id.flowlayout)
    TagFlowLayout mFlowLayout;
    @Bind(R.id.welfare_type)
    EditText mWelfareType;
    @Bind(R.id.yes)
    TextView mYes;


    RequestQueue mQueue = NoHttp.newRequestQueue();
    private int mWhat;
    LayoutInflater mInflater;
    String[] var = new String[]{};
    TagAdapter<String> mAdapter;
    List<String> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruit_fuli);
        ButterKnife.bind(this);
        initView();
        initData();
        initAdapter();
        httpFuli();
    }


    private void initData() {
        mList = new ArrayList<>();
        mInflater = LayoutInflater.from(this);

    }

    private void initView() {
        mTitle.setText("选择福利");
        mSave.setText("完成");
        mSave.setVisibility(View.VISIBLE);
    }

    private void initAdapter() {
        mAdapter = new TagAdapter<String>(mList) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.fuli_textview,
                        mFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        };
        mFlowLayout.setAdapter(mAdapter);
    }

    private void httpFuli() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.PUBLIC_RECRUIT_SELECT_FULI, RequestMethod.POST);


        mQueue.add(HTTP_QEQUEST, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
                showLoading(true);
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == HTTP_QEQUEST) {
                    JSONObject jsonObject = response.get();
                    try {
                        if (jsonObject.getInt("code") == 200) {
                            JSONArray jsonArray = new JSONArray();
                            jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                mList.add(jsonObject1.getString("name"));
                            }
                            mAdapter.notifyDataChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("上传返回结果", response.get().toString());
                }
            }

            @Override
            public void onFailed(int what, Response<JSONObject> response) {

            }

            @Override
            public void onFinish(int what) {
                hideLoading();
            }
        });
    }


    @OnClick({R.id.back, R.id.yes, R.id.right_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.yes:
                String fuli = mWelfareType.getText().toString();
                if (!TextUtils.isEmpty(fuli)) {
                    if (!fuli.contains(",") && !fuli.contains("/")) {
                        mList.add(fuli);
                        mAdapter.notifyDataChanged();
                    }
                }
                break;
            case R.id.right_text:
                mFlowLayout.getSelectedList();
                int selectPosition = -1;
                String selectfuli = "";
                for (int item : mFlowLayout.getSelectedList()) {
                    selectfuli += mList.get(item) + "|||";
                }
                selectfuli = selectfuli.substring(0, selectfuli.length() - 3);
                EventBus.getDefault().post(new EventFlag("selectedFuli", selectfuli));
                finish();
                break;
        }
    }
}
