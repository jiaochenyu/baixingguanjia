package com.linkhand.baixingguanjia.ui.activity.release;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.entity.CommonType;
import com.linkhand.baixingguanjia.entity.EventFlag;
import com.linkhand.baixingguanjia.utils.SPUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommonSelectedTypeActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.listview)
    ListView mListview;
    CommonAdapter mAdapter;
    List<CommonType> mList;
    CommonType mCommonType;
    String flag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_release_type);
        ButterKnife.bind(this);
        initData();
        initAdapter();
        initListener();
    }


    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras != null) {
            mCommonType = (CommonType) extras.getSerializable("type");
            flag = extras.getString("flag");
        }
    }

    private void initData() {
        if (flag.equals("cartype")) {
            mList = (List<CommonType>) SPUtils.get(this, "CarType", new TypeToken<List<CommonType>>() {
            }.getType());
        } else if (flag.equals("idletype")) {
            mList = (List<CommonType>) SPUtils.get(this, "IdleGoodsType", new TypeToken<List<CommonType>>() {
            }.getType());
        } else if (flag.equals("keepingtype")) {
            mList = (List<CommonType>) SPUtils.get(this, "HouseKeepingType", new TypeToken<List<CommonType>>() {
            }.getType());
        } else if (flag.equals("educationtype")) {
            mList = (List<CommonType>) SPUtils.get(this, "EducationType", new TypeToken<List<CommonType>>() {
            }.getType());
        } else if (flag.equals("educationObjecttype")) {
            mList = (List<CommonType>) SPUtils.get(this, "EducationObjectType", new TypeToken<List<CommonType>>() {
            }.getType());
        }else if (flag.equals("positiontype")) {
            mList = (List<CommonType>) SPUtils.get(this, "RecruitPosition", new TypeToken<List<CommonType>>() {
            }.getType());
        }else if (flag.equals("industrytype")) {
            mList = (List<CommonType>) SPUtils.get(this, "RecruitType", new TypeToken<List<CommonType>>() {
            }.getType());
        }


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
                EventBus.getDefault().post(new EventFlag(flag, mList.get(position)));
                finish();
            }
        });
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
