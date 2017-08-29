package com.linkhand.baixingguanjia.ui.activity.release;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.entity.EventFlag;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecruitSalaryActivity extends BaseActivity {
    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.listview)
    ListView mListview;
    CommonAdapter mAdapter;
    String var1 = "1000元/月以下";
    String var2 = "1000-2000元/月";
    String var3 = "2001-4000元/月";
    String var4 = "4001-6000元/月";
    String var5 = "6001-8000元/月";
    String var6 = "8001-10000元/月";
    String var7 = "10001-20000元/月";
    String var8 = "20000元/月以上";
    String var9 = "面议";
    List<String> mList = Arrays.asList(new String[]{var1, var2, var3, var4, var5, var6, var7, var8, var9});
    String flag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_release_type);
        ButterKnife.bind(this);
        mTitle.setText("选择薪资");
        initAdapter();
        initListener();
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras != null) {
            flag = extras.getString("selected");
        }
    }

    private void initAdapter() {
        mAdapter = new CommonAdapter(this, R.layout.item_car_type, mList) {
            @Override
            protected void convert(ViewHolder holder, Object item, int position) {
                TextView textView = holder.getView(R.id.textView);
                textView.setText(mList.get(position));
                if (flag != null) {
                    if (flag.equals(mList.get(position))) {
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
                EventBus.getDefault().post(new EventFlag("selectedSalary", mList.get(position)));
                finish();
            }
        });
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }

}
