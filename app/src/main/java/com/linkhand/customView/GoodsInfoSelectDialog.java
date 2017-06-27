package com.linkhand.customView;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.linkhand.R;
import com.linkhand.entity.Tag;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by JCY on 2016/7/28.
 */
public class GoodsInfoSelectDialog extends Dialog {

    public GoodsInfoSelectDialog(Context context) {
        super(context);
    }

    public GoodsInfoSelectDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {
        private Context mContext;
        private List<Tag> tags = new ArrayList<>();//分类标签
        private ImageView mImageView; //logo
        private ImageView mCloseImageView; //关闭
        private TextView mTextView1, mTextView2, mTextView3; // 减号 显示 加号
        private TextView mAddCartTextView, mBuyNowTextView2; // 加入购物车，立即购买
        private TextView mPriceTV, mKucunTV, mSelectTV; // 加入购物车，立即购买
        private FlowLayout mColorTagsView, mSizeTagsView;
        private OnClickListener addOnClickListener;
        private OnClickListener minOnClickListener;
        private OnClickListener closeOnClickListener;
        private OnClickListener tagsOnClickListener;
        private OnClickListener addCartOnClickListener;
        private OnClickListener buyNowOnClickListener;

        private float price;
        private int kucun;
        private String selectFenlei;
        private int selectNum;

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder setTags(List<Tag> strings) {
            this.tags = strings;
            return this;
        }

        public Builder setPrice(float price) {
            this.price = price;
            mPriceTV.setText("¥ " + price);
            return this;
        }

        public Builder setKucun(int kucun) {
            this.kucun = kucun;
            mKucunTV.setText("库存" + kucun + "件");
            return this;
        }

        public Builder setSelectFenlei(String selectFenlei) {
            this.selectFenlei = selectFenlei;
            mSelectTV.setText("选择" + selectFenlei);
            return this;
        }

        public Builder setSelectNum(int selectNum) {
            this.selectNum = selectNum;
            mTextView2.setText(selectNum + "");
            return this;
        }

        public Builder setAddOnClickListener(OnClickListener dialogOnClickListener) {
            this.addOnClickListener = dialogOnClickListener;
            return this;
        }

        public Builder setMinOnClickListener(OnClickListener dialogOnClickListener) {
            this.minOnClickListener = dialogOnClickListener;
            return this;
        }

        public Builder setCloseOnClickListener(OnClickListener dialogOnClickListener) {
            this.closeOnClickListener = dialogOnClickListener;
            return this;
        }

        public Builder setBuyNowOnClickListener(OnClickListener dialogOnClickListener) {
            this.buyNowOnClickListener = dialogOnClickListener;
            return this;
        }

        public GoodsInfoSelectDialog create() {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final GoodsInfoSelectDialog dialog = new GoodsInfoSelectDialog(mContext, R.style.goods_info_dialog);
            View layout = inflater.inflate(R.layout.dialog_goods_info_select, null);
            dialog.addContentView(layout, new ActionBar.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            ));

            mImageView = (ImageView) layout.findViewById(R.id.logo);
            mCloseImageView = (ImageView) layout.findViewById(R.id.close); //关闭
            mTextView1 = (TextView) layout.findViewById(R.id.less);
            mTextView2 = (TextView) layout.findViewById(R.id.num);
            mTextView3 = (TextView) layout.findViewById(R.id.more);
            mAddCartTextView = (TextView) layout.findViewById(R.id.add_car);
            mBuyNowTextView2 = (TextView) layout.findViewById(R.id.buy_now);
            mPriceTV = (TextView) layout.findViewById(R.id.money);
            mKucunTV = (TextView) layout.findViewById(R.id.kucun);
            mSelectTV = (TextView) layout.findViewById(R.id.fenlei);
            mColorTagsView = (FlowLayout) layout.findViewById(R.id.color_tags);

            for (int i = 0; i < tags.size(); i++) {
                TextView textView = (TextView) inflater.inflate(R.layout.item_viewgroup_textview, null);
                textView.setText(tags.get(i).getName());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(0, 5, 20, 5);
                textView.setId(i);
                textView.setLayoutParams(lp);
                if (tags.get(i).isFlag()) {
                    textView.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.background_textview_10_corner_red));
                } else {
                    textView.setTextColor(mContext.getResources().getColor(R.color.blackText));
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.background_textview_10_corner_gray));
                }
                mColorTagsView.addView(textView);
            }

            mSizeTagsView = (FlowLayout) layout.findViewById(R.id.size_tags);
            for (int i = 0; i < tags.size(); i++) {
                TextView textView = (TextView) inflater.inflate(R.layout.item_viewgroup_textview, null);
                textView.setText(tags.get(i).getName());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(0, 5, 5, 5);
                textView.setId(i);
                textView.setLayoutParams(lp);
                mSizeTagsView.addView(textView);
            }
//            initTagsView(mViewGroup);

            mCloseImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            mTextView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    minOnClickListener.onClick(dialog, BUTTON_POSITIVE);
                }
            });
            mTextView3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addOnClickListener.onClick(dialog, BUTTON_POSITIVE);
                }
            });

            mAddCartTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addCartOnClickListener.onClick(dialog, BUTTON_POSITIVE);
                }
            });
            mBuyNowTextView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buyNowOnClickListener.onClick(dialog, BUTTON_POSITIVE);
                }
            });

            for (int i = 0; i < tags.size(); i++) {
                mColorTagsView.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, v.getId() + "", Toast.LENGTH_SHORT).show();
//                        v.getId();
//                        if (tags.get(v.getId()).isFlag()) {
//                            tags.get(v.getId()).setFlag(false);
//                        }
                        flowLayoutNotify(v.getId());
                    }
                });
            }

            for (int i = 0; i < tags.size(); i++) {
                mSizeTagsView.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, v.getId() + "", Toast.LENGTH_SHORT).show();
                        v.getId();
                    }
                });
            }

            dialog.setContentView(layout);
            dialog.setCanceledOnTouchOutside(true);//点击屏幕不消失
            dialog.setCancelable(true);
            return dialog;

        }

        public void flowLayoutNotify(int local) {
            boolean flag = tags.get(local).isFlag();
            if (flag) {
                tags.get(local).setFlag(false);
                mColorTagsView.getChildAt(local).setBackground(mContext.getResources().getDrawable(R.drawable.background_textview_10_corner_gray));
                ((TextView) mColorTagsView.getChildAt(local)).setTextColor(mContext.getResources().getColor(R.color.blackText));
            } else {
                tags.get(local).setFlag(true);
                //将其他置为false
                for (int i = 0; i < tags.size(); i++) {
                    if (i!=local){
                        tags.get(i).setFlag(false);
                    }
                    if (tags.get(i).isFlag()) {
                        ((TextView) mColorTagsView.getChildAt(i)).setTextColor(mContext.getResources().getColor(R.color.colorWhite));
                        ((TextView) mColorTagsView.getChildAt(i)).setBackground(mContext.getResources().getDrawable(R.drawable.background_textview_10_corner_red));
                    } else if (tags.get(i).isFlag() == false){
                        ((TextView) mColorTagsView.getChildAt(i)).setTextColor(mContext.getResources().getColor(R.color.blackText));
                        ((TextView) mColorTagsView.getChildAt(i)).setBackground(mContext.getResources().getDrawable(R.drawable.background_textview_10_corner_gray));
                    }
                }
            }


        }


    }


}


