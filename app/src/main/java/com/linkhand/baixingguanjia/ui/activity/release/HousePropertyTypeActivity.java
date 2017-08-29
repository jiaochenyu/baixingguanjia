package com.linkhand.baixingguanjia.ui.activity.release;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.entity.CommonType;
import com.linkhand.baixingguanjia.entity.EventFlag;
import com.linkhand.baixingguanjia.utils.StringUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HousePropertyTypeActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.listview)
    ListView mListview;
    CommonAdapter mAdapter;
    List<CommonType> mList;
    CommonType mCommonType;
    @Bind(R.id.shi)
    EditText mShi;
    @Bind(R.id.ting)
    EditText mTing;
    @Bind(R.id.chu)
    EditText mChu;
    @Bind(R.id.wei)
    EditText mWei;
    @Bind(R.id.bottom)
    LinearLayout mBottom;
    @Bind(R.id.right_text)
    TextView mSaveTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_release_type);
        ButterKnife.bind(this);
        initView();
        initData();
        initAdapter();
        initListener();

    }


    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras != null) {
            mCommonType = (CommonType) extras.getSerializable("cartype");
        }
    }

    private void initView() {
        mTitle.setText("选择户型");
        mSaveTV.setText("完成");
        mSaveTV.setVisibility(View.VISIBLE);
        mBottom.setVisibility(View.VISIBLE);
    }

    private void initData() {
        mList = new ArrayList<>();
        if (mCommonType != null) {
            String rgex1 = "(.*?)室";
            String rgex2 = "室(.*?)厅";
            String rgex3 = "厅(.*?)厨";
            String rgex4 = "厨(.*?)卫";
            String shi = StringUtils.getSubUtilSimple(mCommonType.getName(), rgex1);
            String ting = StringUtils.getSubUtilSimple(mCommonType.getName(), rgex2);
            String chu = StringUtils.getSubUtilSimple(mCommonType.getName(), rgex3);
            String wei = StringUtils.getSubUtilSimple(mCommonType.getName(), rgex4);
            mShi.setText(shi);
            mTing.setText(ting);
            mChu.setText(chu);
            mWei.setText(wei);
        }

        getData();
    }

    private void getData() {
        CommonType commonType1 = new CommonType(1 + "", "5室" + "2厅" + "1厨" + "3卫");
        CommonType commonType2 = new CommonType(2 + "", "4室" + "2厅" + "1厨" + "2卫");
        CommonType commonType3 = new CommonType(3 + "", "3室" + "1厅" + "1厨" + "2卫");
        CommonType commonType4 = new CommonType(4 + "", "3室" + "1厅" + "1厨" + "1卫");
        CommonType commonType5 = new CommonType(5 + "", "2室" + "1厅" + "1厨" + "2卫");
        CommonType commonType6 = new CommonType(6 + "", "2室" + "1厅" + "1厨" + "1卫");
        CommonType commonType7 = new CommonType(7 + "", "1室" + "1厅" + "1厨" + "1卫");
        CommonType commonType8 = new CommonType(8 + "", "其他");
        mList.add(commonType1);
        mList.add(commonType2);
        mList.add(commonType3);
        mList.add(commonType4);
        mList.add(commonType5);
        mList.add(commonType6);
        mList.add(commonType7);
        mList.add(commonType8);

    }

    private void initAdapter() {
        mAdapter = new CommonAdapter(this, R.layout.item_car_type, mList) {
            @Override
            protected void convert(ViewHolder holder, Object item, int position) {
                TextView textView = holder.getView(R.id.textView);
                textView.setText(mList.get(position).getName());
                if (mCommonType != null) {
                    if (mList.get(position).getId().equals(mCommonType.getId())) {
                        textView.setTextColor(getResources().getColor(R.color.blueTopic));
                    }
                }
            }
        };
        mListview.setAdapter(mAdapter);
    }

    private void initListener() {
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mList.get(position).getName().equals("其他")) {
                    EventBus.getDefault().post(new EventFlag("housetype", mList.get(position)));
                    finish();
                } else {
                    String rgex1 = "(.*?)室";
                    String rgex2 = "室(.*?)厅";
                    String rgex3 = "厅(.*?)厨";
                    String rgex4 = "厨(.*?)卫";
                    String shi = StringUtils.getSubUtilSimple(mList.get(position).getName(), rgex1);
                    String ting = StringUtils.getSubUtilSimple(mList.get(position).getName(), rgex2);
                    String chu = StringUtils.getSubUtilSimple(mList.get(position).getName(), rgex3);
                    String wei = StringUtils.getSubUtilSimple(mList.get(position).getName(), rgex4);
                    mShi.setText(shi);
                    mTing.setText(ting);
                    mChu.setText(chu);
                    mWei.setText(wei);
                }

            }
        });
    }

    @OnClick({R.id.back, R.id.right_text})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.right_text:
                String shi = mShi.getText().toString();
                String ting = mTing.getText().toString();
                String chu = mChu.getText().toString();
                String wei = mWei.getText().toString();
                CommonType commonType = new CommonType(9 + "", shi + "室" + ting + "厅" + chu + "厨" + wei + "卫");
                EventBus.getDefault().post(new EventFlag("housetype", commonType));
                finish();
                break;
        }

    }


}
