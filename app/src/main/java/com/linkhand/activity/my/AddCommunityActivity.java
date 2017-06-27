package com.linkhand.activity.my;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linkhand.R;
import com.linkhand.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 添加小区
 */

public class AddCommunityActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.title)
    TextView mTitleTV;
    @Bind(R.id.save)
    TextView mSaveTV;

    EditText mNameET; //小区名称
    EditText mperiodsET; //期数
    EditText mGroupET; //组团数
    EditText mBuildET; //楼栋号
    EditText mUnitET; //单元号
    EditText mFloorET; //楼层号
    EditText mRoomET; //房间号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_community);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTitleTV.setText("添加我的小区");
        RelativeLayout mLayout0 = (RelativeLayout) findViewById(R.id.header_layout);
        LinearLayout mLayout1 = (LinearLayout) findViewById(R.id.layout1);
        LinearLayout mLayout2 = (LinearLayout) findViewById(R.id.layout2);
        LinearLayout mLayout3 = (LinearLayout) findViewById(R.id.layout3);
        LinearLayout mLayout4 = (LinearLayout) findViewById(R.id.layout4);
        LinearLayout mLayout5 = (LinearLayout) findViewById(R.id.layout5);
        LinearLayout mLayout6 = (LinearLayout) findViewById(R.id.layout6);
        LinearLayout mLayout7 = (LinearLayout) findViewById(R.id.layout7);
        mLayout0.setBackground(getResources().getDrawable(R.color.colorSystemBlack));
        TextView title = ((TextView) mLayout0.findViewById(R.id.title));
        title.setTextColor(getResources().getColor(R.color.colorWhite));
        title.setText("添加我的小区");
        ((TextView) mLayout1.findViewById(R.id.text_left)).setText("小区名称");
        ((TextView) mLayout2.findViewById(R.id.text_left)).setText("期数");
        ((TextView) mLayout3.findViewById(R.id.text_left)).setText("组团数");
        ((TextView) mLayout4.findViewById(R.id.text_left)).setText("楼栋号");
        ((TextView) mLayout5.findViewById(R.id.text_left)).setText("单元号");
        ((TextView) mLayout6.findViewById(R.id.text_left)).setText("楼层号");
        ((TextView) mLayout7.findViewById(R.id.text_left)).setText("房间号");
        ((EditText) mLayout1.findViewById(R.id.edittext_right)).setHint("请输入小区名称");
        ((EditText) mLayout2.findViewById(R.id.edittext_right)).setHint("请输入期数");
        ((EditText) mLayout3.findViewById(R.id.edittext_right)).setHint("请输入组团数");
        ((EditText) mLayout4.findViewById(R.id.edittext_right)).setHint("请输入楼栋号");
        ((EditText) mLayout5.findViewById(R.id.edittext_right)).setHint("请输入单元号");
        ((EditText) mLayout6.findViewById(R.id.edittext_right)).setHint("请输入楼层号");
        ((EditText) mLayout7.findViewById(R.id.edittext_right)).setHint("请输入房间号");

    }

    @OnClick({R.id.back, R.id.save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.save:

                break;
        }
    }
}
