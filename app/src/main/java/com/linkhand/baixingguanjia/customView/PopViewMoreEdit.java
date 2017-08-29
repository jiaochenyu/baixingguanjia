package com.linkhand.baixingguanjia.customView;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.listener.PopViewBaseActionListener;

/**
 * 更多
 * @author linmz
 *
 */
public class PopViewMoreEdit extends LinearLayout implements PopViewBaseActionListener {

	private Context mContext;
	private View rlHighPrice, rlLowPrice;
	private TextView  tvPriceReset,
			tvPriceFinish;
	private ListView lvHouseTypes;
	private EditText etLowPrice, etHighPrice;
	private OnSelectListener mOnSelectListener;
	private String[] houseType = { "一居", "二居", "三居", "四居", "五居" };
	private String lowPrice, highPrice;

	public PopViewMoreEdit(Context context) {
		super(context);
		init(context);
	}

	public PopViewMoreEdit(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public void init(Context context) {
		mContext = context;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_more_popuwindow_price, this, true);


		// 设置价格界面
		rlHighPrice = findViewById(R.id.rlHighPrice);
		rlLowPrice = findViewById(R.id.rlLowPrice);
		etLowPrice = (EditText) findViewById(R.id.etLowPrice);
		etHighPrice = (EditText) findViewById(R.id.etHighPrice);
		etLowPrice.setFocusable(true);
		etLowPrice.setFocusableInTouchMode(true);
		etHighPrice.setFocusable(true);
		etHighPrice.setFocusableInTouchMode(true);
		etLowPrice.requestFocus();
		etHighPrice.requestFocus();

		tvPriceReset = (TextView) findViewById(R.id.tvPriceReset);
		tvPriceFinish = (TextView) findViewById(R.id.tvPriceFinish);
		initListener();

	}

	private void initListener() {
		tvPriceReset.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				etLowPrice.setText("");
				etHighPrice.setText("");
			}
		});
		tvPriceFinish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				lowPrice = etLowPrice.getText().toString().trim();
				highPrice = etHighPrice.getText().toString().trim();
				if (!TextUtils.isEmpty(lowPrice)
						&& !TextUtils.isEmpty(highPrice)) {
				}
				mOnSelectListener.getValue(lowPrice,highPrice);
			}
		});
	}




	public void setOnSelectListener(OnSelectListener onSelectListener) {
		mOnSelectListener = onSelectListener;
	}

	public interface OnSelectListener {
		public void getValue(String distance, String showText);
	}

	@Override
	public void hide() {

	}

	@Override
	public void show() {

	}

}
