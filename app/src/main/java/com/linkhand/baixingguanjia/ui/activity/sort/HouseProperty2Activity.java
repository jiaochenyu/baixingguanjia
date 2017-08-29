package com.linkhand.baixingguanjia.ui.activity.sort;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.ui.fragment.HousePropertyRentFragment;
import com.linkhand.baixingguanjia.ui.fragment.HousePropertySellFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
//*停用

public class HouseProperty2Activity extends BaseActivity {

    private static final int HTTP_REQUEST = 0;
    private static final int SELL = 1;
    private static final int RENT = 2;
    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.search_text_layout)
    LinearLayout mSearchLayout;  //隐藏显示
    @Bind(R.id.listview)
    PullToRefreshListView mListview;
    @Bind(R.id.radiogroup)
    RadioGroup mRadioGroup;
    @Bind(R.id.select_layout)
    LinearLayout mSelectLayout;
    @Bind(R.id.search_layout)
    LinearLayout mSearch;  // 搜索点击
    @Bind(R.id.tiaojian)
    EditText mTiaojianET;
    @Bind(R.id.sell)
    RadioButton mSell;
    @Bind(R.id.rent)
    RadioButton mRent;
    @Bind(R.id.cha)
    ImageView mCha;

    boolean viewFlag = false; //搜索输入框显示和隐藏

    HousePropertyRentFragment rentFragment;
    HousePropertySellFragment sellFragment;


    /**
     * 搜索条件
     **/
    String tiaojian;
    String houseType = "2"; //租房 还是 买房1出租2出售【默认2】
    @Bind(R.id.fragment)
    FrameLayout mFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_property2);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }


    private void initView() {
//        mTiaojianET.requestFocus();
    }

    private void initData() {
        fragmentManager = getSupportFragmentManager();
        showFragment(SELL);

    }

    private void showFragment(int index) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //隐藏所有的Fragment
        hideFragment(fragmentTransaction);

        //显示指定的Fragment
        switch (index) {
            case SELL:
                if (sellFragment == null) {
                    sellFragment = new HousePropertySellFragment();
                    fragmentTransaction.add(R.id.fragment, sellFragment);
                } else {
                    fragmentTransaction.show(sellFragment);
                }
                break;
            case RENT:
                if (rentFragment == null) {
                    rentFragment = new HousePropertyRentFragment();
                    fragmentTransaction.add(R.id.fragment, rentFragment);
                } else {
                    fragmentTransaction.show(rentFragment);
                }
                break;

        }
        fragmentTransaction.commit();
    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (sellFragment != null) {
            fragmentTransaction.hide(sellFragment);
        }
        if (rentFragment != null) {
            fragmentTransaction.hide(rentFragment);
        }
    }

    private void initListener() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.sell:
                        houseType = "2";
                        showFragment(SELL);
                        break;
                    case R.id.rent:
                        houseType = "1";
                        showFragment(RENT);
                        break;
                }
            }
        });
        // 搜索输入框事件监听
        mTiaojianET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(mTiaojianET.getText())) {
                    mCha.setVisibility(View.VISIBLE);
                } else {
                    mCha.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


    @OnClick({R.id.back, R.id.search_layout, R.id.cha})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.cha:
                mTiaojianET.setText("");
                break;
            case R.id.search_layout:
                viewFlag = !viewFlag;
                if (TextUtils.isEmpty(mTiaojianET.getText().toString())) {
                    if (viewFlag) {
                        mSearchLayout.setVisibility(View.VISIBLE);
                        mSelectLayout.setVisibility(View.GONE);
                    } else {
                        mSearchLayout.setVisibility(View.GONE);
                        mSelectLayout.setVisibility(View.VISIBLE);
                    }
                } else {
                    tiaojian = mTiaojianET.getText().toString();
                }

                break;


        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
