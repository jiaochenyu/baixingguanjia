package com.example.morelistpop;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 更多
 * @author linmz
 *
 */
public class ViewMore extends LinearLayout implements ViewBaseAction {

	private Context mContext;
	private View rlHouseType, rlPrice, rlWeb, lyIndexView,
			lySelectHouseTypeView, lySetPriceView, rlHighPrice, rlLowPrice;
	private TextView tvFilter, tvBack, tvPrice, tvHouseType, tvPriceBack,
			tvPriceFinish;
	private ListView lvHouseTypes;
	private EditText etLowPrice, etHighPrice;
	private TextAdapter selectHoseTypeAdapter;
	private OnSelectListener mOnSelectListener;
	private String[] houseType = { "一居", "二居", "三居", "四居", "五居" };
	private String lowPrice, highPrice;

	public ViewMore(Context context) {
		super(context);
		init(context);
	}

	public ViewMore(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public void init(Context context) {
		mContext = context;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_more_popuwindow, this, true);
		// 主界面
		lyIndexView = findViewById(R.id.lyIndex);
		rlHouseType = findViewById(R.id.rlHouseType);// 居室
		rlPrice = findViewById(R.id.rlPrice);// 价格
		rlWeb = findViewById(R.id.rlWeb);// 网站
		tvFilter = (TextView) findViewById(R.id.tvFilter);// 筛选
		tvHouseType = (TextView) findViewById(R.id.tvHouseType);
		tvPrice = (TextView) findViewById(R.id.tvPrice);

		// 选择居室界面
		lySelectHouseTypeView = findViewById(R.id.lySelectHouseType);
		lvHouseTypes = (ListView) findViewById(R.id.lvHouseTypes);
		tvBack = (TextView) findViewById(R.id.tvBack);

		// 设置价格界面
		lySetPriceView = findViewById(R.id.lySetPrice);
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

		tvPriceBack = (TextView) findViewById(R.id.tvPriceBack);
		tvPriceFinish = (TextView) findViewById(R.id.tvPriceFinish);

		selectHoseTypeAdapter = new TextAdapter(mContext, houseType,
				R.color.white, R.color.white, 3);
		lvHouseTypes.setAdapter(selectHoseTypeAdapter);

		rlHouseType.setOnClickListener(onClickListener);
		rlPrice.setOnClickListener(onClickListener);
		rlWeb.setOnClickListener(onClickListener);
		tvFilter.setOnClickListener(onClickListener);
		tvBack.setOnClickListener(onClickListener);
		tvPriceBack.setOnClickListener(onClickListener);
		tvPriceFinish.setOnClickListener(onClickListener);
		rlHighPrice.setOnClickListener(onClickListener);
		rlLowPrice.setOnClickListener(onClickListener);
		
		selectHoseTypeAdapter.setOnItemClickListener(new TextAdapter.OnItemClickListener() {

			@Override
			public void onItemClick(View view, int position) {

				if (!TextUtils.isEmpty(houseType[position])) {
					tvHouseType.setText(houseType[position]);
					lySelectHouseTypeView.setVisibility(GONE);
					lyIndexView.setVisibility(View.VISIBLE);
				}
			}
		});
	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.rlHouseType:
				lyIndexView.setVisibility(View.GONE);
				lySelectHouseTypeView.setVisibility(View.VISIBLE);
				break;
			case R.id.rlPrice:
				// 点击首页中的价格
				lyIndexView.setVisibility(View.GONE);
				lySetPriceView.setVisibility(View.VISIBLE);

				break;
			case R.id.rlWeb:

				break;
			case R.id.tvFilter:
				// 筛选

				break;
			case R.id.tvBack:
				// 返回
				lySelectHouseTypeView.setVisibility(View.GONE);
				lyIndexView.setVisibility(View.VISIBLE);
				break;
			case R.id.tvPriceBack:
				// 点击价格设置界面中的返回按钮
				lySetPriceView.setVisibility(View.GONE);
				lyIndexView.setVisibility(View.VISIBLE);
				break;
			case R.id.tvPriceFinish:
				// 点击价格设置界面的完成按钮
				lowPrice = etLowPrice.getText().toString().trim();
				highPrice = etHighPrice.getText().toString().trim();
				if (!TextUtils.isEmpty(lowPrice)
						&& !TextUtils.isEmpty(highPrice)) {
					tvPrice.setText(lowPrice + "-" + highPrice);
				}
				lySetPriceView.setVisibility(View.GONE);
				lyIndexView.setVisibility(View.VISIBLE);
				break;
			case R.id.rlHighPrice:

				break;
			case R.id.rlLowPrice:

				break;
			// case R.id.etLowPrice:
			// Use.showPan((Activity)mContext, v);
			// break;
			// case R.id.etHighPrice:
			// Use.showPan((Activity)mContext, v);
			// break;
			default:
				break;
			}

		}
	};

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
		lySelectHouseTypeView.setVisibility(GONE);
		lySetPriceView.setVisibility(View.GONE);
		lyIndexView.setVisibility(View.VISIBLE);
	}

}
