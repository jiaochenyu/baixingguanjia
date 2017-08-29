/*
 * Copyright 2015 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.linkhand.baixingguanjia.customView;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.entity.GoodsTag;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created in Oct 23, 2015 1:19:04 PM.
 *
 * @author Yan Zhenjie.
 */
public class GoodsInfoSelectlTagListDialog extends Dialog {
    private Context mContext;

    private ImageView mImageView; //logo
    private ImageView mCloseImageView; //关闭
    private TextView mTextView1, mTextView2, mTextView3; // 减号 显示 加号
    private TextView mAddCartTextView, mBuyNowTextView2; // 加入购物车，立即购买
    private TextView mPriceTV, mKucunTV, mSelectTV; // 加入购物车，立即购买
    private TextView mCountPriceTV; //总价
    private NoScrollListView mNoScrollListView;
    private OnClickListener addOnClickListener;
    private OnClickListener minOnClickListener;
    private OnClickListener closeOnClickListener;
    private OnClickListener tagsOnClickListener;
    private OnClickListener buyNowOnClickListener;
    private OnItemClickListener mItemClickListener;

    private float price;
    private int kucun;
    private String selectFenlei;
    private int selectNum;
    List<GoodsTag> mGoodsTags;
    GuigeAdapter mAdapter;
    LayoutInflater inflater;
    Map<Integer, Set<Integer>> selectedMap = new HashMap<Integer, Set<Integer>>();

    public GoodsInfoSelectlTagListDialog(@NonNull Context context, @StyleRes int themeResId, List<GoodsTag> list) {
        super(context, themeResId);
        this.mContext = context;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mGoodsTags = list;
        initView();
        initAdapter();
        initListener();

    }


    private void initView() {
        setContentView(R.layout.dialog_goods_info_select);
        mImageView = (ImageView) findViewById(R.id.logo);
        mCloseImageView = (ImageView) findViewById(R.id.close); //关闭
        mTextView1 = (TextView) findViewById(R.id.less);
        mTextView2 = (TextView) findViewById(R.id.num);
        mTextView3 = (TextView) findViewById(R.id.more);
        mAddCartTextView = (TextView) findViewById(R.id.add_car);
        mBuyNowTextView2 = (TextView) findViewById(R.id.buy_now);
        mPriceTV = (TextView) findViewById(R.id.money);
        mKucunTV = (TextView) findViewById(R.id.kucun);
        mSelectTV = (TextView) findViewById(R.id.fenlei);
        mNoScrollListView = (NoScrollListView) findViewById(R.id.listview);
        mCountPriceTV = (TextView) findViewById(R.id.count_money);
    }

    private void initAdapter() {
//        mGoodsTags = new ArrayList<>();
        mAdapter = new GuigeAdapter(mContext, R.layout.item_dialog_goods_info_select_tag, mGoodsTags);
        mNoScrollListView.setAdapter(mAdapter);
    }

    private void initListener() {
        mCloseImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mTextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minOnClickListener.onClick(GoodsInfoSelectlTagListDialog.this, BUTTON_POSITIVE);
            }
        });
        mTextView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOnClickListener.onClick(GoodsInfoSelectlTagListDialog.this, BUTTON_POSITIVE);
            }
        });

        mAddCartTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        mBuyNowTextView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyNowOnClickListener.onClick(GoodsInfoSelectlTagListDialog.this, BUTTON_POSITIVE);
            }
        });
        mNoScrollListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

    public void setSelectedMap(Map<Integer, Set<Integer>> selectedMap) {
        this.selectedMap = selectedMap;

    }

    public void setTags(List<GoodsTag> tags) {
        this.mGoodsTags = tags;
        mAdapter.notifyDataSetChanged();
    }

    public void setPrice(float price) {
        this.price = price;
        mPriceTV.setText("¥ " + price);

    }
    public void setImage(String url){

    }

    public void setKucun(int kucun) {
        this.kucun = kucun;
        mKucunTV.setText("库存" + kucun + "件");
    }

    public void setSelectFenlei(String selectFenlei) {
        this.selectFenlei = selectFenlei;
        mSelectTV.setText("选择" + selectFenlei);

    }

    public void setSelectNum(int selectNum) {
        this.selectNum = selectNum;
        mTextView2.setText(selectNum + "");
        setCountPrice(selectNum * price);
    }

    public void setCountPrice(float price) {
        mCountPriceTV.setText("¥ " + price);
    }

    public void setAddOnClickListener(OnClickListener dialogOnClickListener) {
        this.addOnClickListener = dialogOnClickListener;

    }

    public void setMinOnClickListener(OnClickListener dialogOnClickListener) {
        this.minOnClickListener = dialogOnClickListener;

    }

    public void setCloseOnClickListener(OnClickListener dialogOnClickListener) {
        this.closeOnClickListener = dialogOnClickListener;

    }

    public void setBuyNowOnClickListener(OnClickListener dialogOnClickListener) {
        this.buyNowOnClickListener = dialogOnClickListener;

    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }


    private class GuigeAdapter extends CommonAdapter {

        public GuigeAdapter(Context context, int layoutId, List datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(final ViewHolder viewHolder, final Object item, final int position) {

            ((TextView) viewHolder.getView(R.id.text_flag_name)).setText(mGoodsTags.get(position).getGuiges().get(0).getName());
            final TagFlowLayout mFlowLayout = viewHolder.getView(R.id.goods_tags);
            TagAdapter<GoodsTag.Guige> tagAdapter = new TagAdapter<GoodsTag.Guige>(mGoodsTags.get(position).getGuiges()) {
                @Override
                public View getView(FlowLayout parent, int position, GoodsTag.Guige guige) {
                    TextView tv = (TextView) inflater.inflate(R.layout.item_viewgroup_textview, parent, false);
                    tv.setText(guige.getItem());
                    return tv;
                }
            };
            mFlowLayout.setAdapter(tagAdapter);

            for (int i = 0; i < mGoodsTags.get(position).getGuiges().size(); i++) {
                if (mGoodsTags.get(position).getGuiges().get(i).isFlag()) {
//                    mFlowLayout.getChildAt(i).setSelected(true);
                    Set<Integer> integers = new HashSet<>();
                    integers.add(i);
                    selectedMap.put(position, integers);
                    tagAdapter.setSelectedList(selectedMap.get(position));
//                    tagAdapter.setSelectedList(integers);
                } else {

                }

            }

            //重置状态
//            tagAdapter.setSelectedList(selectedMap.get(viewHolder.getItemPosition()));
//            tagAdapter.setSelectedList(selectedMap.get(position));

            mFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                @Override
                public void onSelected(Set<Integer> selectPosSet) {
//                    selectedMap.put(viewHolder.getItemPosition(), selectPosSet);
//                    selectedMap.put(position, selectPosSet);
                    int selectPosition = -1;
                    for (int item : mFlowLayout.getSelectedList()) {
                        selectPosition = item;
                    }
                    Toast.makeText(mContext, position + "位置" + selectPosition + "被选中", Toast.LENGTH_SHORT).show();
//                    Log.e("选中", "onSelected: "+ mGoodsTags.get(position).getGuiges().get(Integer.parseInt(mFlowLayout.getSelectedList().toString())));
                    mItemClickListener.onItemClick(position, selectPosition);

                }
            });


        }
    }


    //定义监听接口
    public interface OnItemClickListener {

        void onItemClick(int position, int i);
    }


}
