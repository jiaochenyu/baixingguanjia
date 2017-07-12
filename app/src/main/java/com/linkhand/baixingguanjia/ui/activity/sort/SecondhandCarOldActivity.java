package com.linkhand.baixingguanjia.ui.activity.sort;


import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.entity.Car;
import com.linkhand.baixingguanjia.entity.Qu;
import com.linkhand.baixingguanjia.entity.Sheng;
import com.linkhand.baixingguanjia.entity.Shi;
import com.linkhand.baixingguanjia.entity.Xiaoqu;
import com.linkhand.baixingguanjia.ui.activity.car.SecondhandCarDetailActivity;
import com.linkhand.baixingguanjia.ui.adapter.PopListviewAdapter;
import com.linkhand.baixingguanjia.ui.adapter.SecondhandCarAdapter;
import com.linkhand.baixingguanjia.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 四级联动的 popupwindow。
 */
public class SecondhandCarOldActivity extends BaseActivity {
    private final static int SHENG = 1; //省
    private final static int SHI = 2; //市
    private final static int QU = 3; //区
    private final static int XIAOQU = 4;//小区
    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.menu_layout)
    LinearLayout mMenuLayout;
    @Bind(R.id.listview)
    PullToRefreshListView mListview;
    @Bind(R.id.location)
    TextView mLocationTV;
    @Bind(R.id.location_layout)
    RelativeLayout mLocationLayout;
    @Bind(R.id.type)
    TextView mTypeTV;
    @Bind(R.id.type_layout)
    RelativeLayout mTypeLayout;
    @Bind(R.id.price)
    TextView mPriceTV;
    @Bind(R.id.price_layout)
    RelativeLayout mPriceLayout;
    @Bind(R.id.store)
    TextView mStoreTV;
    @Bind(R.id.store_layout)
    RelativeLayout mStoreLayout;
    View mPopViewBG;
    @Bind(R.id.line)
    View grayline;

    List<Sheng> mShengList;
    List<Shi> mShiList;
    List<Qu> mQuList;
    List<Xiaoqu> mXiaoquList;
    ListView mPopListView1;
    ListView mPopListView2;
    ListView mPopListView3;
    ListView mPopListView4;
    PopupWindow locationPop;
    PopListviewAdapter mPopListviewAdapter1;
    PopListviewAdapter mPopListviewAdapter2;
    PopListviewAdapter mPopListviewAdapter3;
    PopListviewAdapter mPopListviewAdapter4;

    private List<Car> mList;
    private SecondhandCarAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondhand_car_old);
        ButterKnife.bind(this);
        initData();
        initPop();
        initListener();
    }


    private void initData() {
        mList = new ArrayList<>();
        mAdapter = new SecondhandCarAdapter(this, R.layout.item_car_info, mList);
        mListview.setAdapter(mAdapter);
        getData();
    }


    private void getData() {
        mList.add(new Car());
        mList.add(new Car());
        mList.add(new Car());
        mList.add(new Car());
        mList.add(new Car());
        mList.add(new Car());
        mAdapter.notifyDataSetChanged();
    }

    private void initPop() {
        mShengList = new ArrayList<>();
        mShiList = new ArrayList<>();
        mQuList = new ArrayList<>();
        mXiaoquList = new ArrayList<>();

        mShengList = (List<Sheng>) SPUtils.get(this, "DiQu", new TypeToken<List<Sheng>>() {
        }.getType());
//        mShiList = mShengList.get(0).getShiList();
//        mQuList = mShiList.get(0).getQuList();
//        mXiaoquList = mQuList.get(0).getXiaoquList();
        if (mShengList == null) {
            mShengList = new ArrayList<>();
        }
        mPopListviewAdapter1 = new PopListviewAdapter(this, R.layout.item_pop_listview, mShengList, SHENG);
        mPopListviewAdapter2 = new PopListviewAdapter(this, R.layout.item_pop_listview, mShiList, SHI);
        mPopListviewAdapter3 = new PopListviewAdapter(this, R.layout.item_pop_listview, mQuList, QU);
        mPopListviewAdapter4 = new PopListviewAdapter(this, R.layout.item_pop_listview, mXiaoquList, XIAOQU);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.from(this).inflate(R.layout.pop_location_four_listview, null);
        locationPop = new PopupWindow();
        locationPop.setContentView(view);
        locationPop.setAnimationStyle(R.style.menu_popwindow_anim_style);
        locationPop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        locationPop.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        locationPop.setOutsideTouchable(true);
        locationPop.setFocusable(true);
        locationPop.setBackgroundDrawable(new BitmapDrawable());
        mPopListView1 = (ListView) view.findViewById(R.id.first_listview);
        mPopListView2 = (ListView) view.findViewById(R.id.second_listview);
        mPopListView3 = (ListView) view.findViewById(R.id.third_listview);
        mPopListView4 = (ListView) view.findViewById(R.id.fourth_listview);
        mPopViewBG = view.findViewById(R.id.pop_bg);
        mPopListView1.setAdapter(mPopListviewAdapter1);
        mPopListView2.setAdapter(mPopListviewAdapter2);
        mPopListView3.setAdapter(mPopListviewAdapter3);
        mPopListView4.setAdapter(mPopListviewAdapter4);
    }


    private void initListener() {
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                go(SecondhandCarDetailActivity.class);
            }
        });
        locationPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
        mPopViewBG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationPop.dismiss();
            }
        });

        /**
         * 四级联动 点击事件逻辑处理
         * //省
         */

        mPopListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击省改变市 区 小区
                mShiList.clear();
                mShiList.addAll(mShengList.get(position).getShiList());
                Log.e("mShiList", "onItemClick: " + mShiList.get(0).getName());
                mPopListviewAdapter2.notifyDataSetChanged();
                if (adjustList(mShiList)) {
                    mQuList.clear();
                    mQuList.addAll(mShiList.get(0).getQuList());
                    mPopListviewAdapter3.notifyDataSetChanged();
                    if (adjustList(mQuList)) {
                        mXiaoquList.clear();
                        mXiaoquList.addAll(mQuList.get(0).getXiaoquList());
                        mPopListviewAdapter4.notifyDataSetChanged();
                    }
                }

            }
        });
        //市
        mPopListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mQuList.clear();
                mQuList.addAll(mShiList.get(position).getQuList());
                mPopListviewAdapter3.notifyDataSetChanged();
                if (adjustList(mQuList)) {
                    mXiaoquList.clear();
                    mXiaoquList.addAll(mQuList.get(0).getXiaoquList());
                    mPopListviewAdapter4.notifyDataSetChanged();
                }
            }
        });
        //区
        mPopListView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mXiaoquList.clear();
                mXiaoquList.addAll(mQuList.get(position).getXiaoquList());
                mPopListviewAdapter4.notifyDataSetChanged();
            }
        });

    }


    @OnClick({R.id.back, R.id.menu_layout, R.id.location_layout, R.id.type_layout, R.id.price_layout, R.id.store_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                break;
            case R.id.menu_layout:
                break;
            case R.id.location_layout:
                //选择地区
                setMenuLayout(1);
                locationPop.showAsDropDown(grayline);
                break;
            case R.id.type_layout:
                //类型选择
                setMenuLayout(2);

                break;
            case R.id.price_layout:
                //选择价钱
                setMenuLayout(3);

                break;
            case R.id.store_layout:
                //选择商家
                setMenuLayout(4);

                break;
        }
    }


    /**
     * 设置状态
     *
     * @param menuFlag
     */
    private void setMenuLayout(int menuFlag) {
        switch (menuFlag) {
            case 1:
                mLocationTV.setTextColor(getResources().getColor(R.color.blueTopic));
                mTypeTV.setTextColor(getResources().getColor(R.color.blackText));
                mPriceTV.setTextColor(getResources().getColor(R.color.blackText));
                mStoreTV.setTextColor(getResources().getColor(R.color.blackText));
                break;
            case 2:
                mLocationTV.setTextColor(getResources().getColor(R.color.blackText));
                mTypeTV.setTextColor(getResources().getColor(R.color.blueTopic));
                mPriceTV.setTextColor(getResources().getColor(R.color.blackText));
                mStoreTV.setTextColor(getResources().getColor(R.color.blackText));
                break;
            case 3:
                mLocationTV.setTextColor(getResources().getColor(R.color.blackText));
                mTypeTV.setTextColor(getResources().getColor(R.color.blackText));
                mPriceTV.setTextColor(getResources().getColor(R.color.blueTopic));
                mStoreTV.setTextColor(getResources().getColor(R.color.blackText));
                break;
            case 4:
                mLocationTV.setTextColor(getResources().getColor(R.color.blackText));
                mTypeTV.setTextColor(getResources().getColor(R.color.blackText));
                mPriceTV.setTextColor(getResources().getColor(R.color.blackText));
                mStoreTV.setTextColor(getResources().getColor(R.color.blueTopic));
                break;
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            if (locationPop != null && locationPop.isShowing()) {
                locationPop.dismiss();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
