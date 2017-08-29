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
import com.linkhand.baixingguanjia.customView.adapter.PopListTextAdapter;
import com.linkhand.baixingguanjia.listener.PopViewBaseActionListener;

import java.util.List;


public class PopViewListAndEdit extends LinearLayout implements PopViewBaseActionListener {

	private Context mContext;
	private ListView mListView;
	private PopListTextAdapter adapter;
	private List<String> items ;//= new String[] {"不限","出售","出租","求购","求租"};//显示字段
	private List<String> itemsVaule ;//= new String[] { "1", "2", "3", "4", "5"};//隐藏id
	private String showText;
	private OnSelectListener mOnSelectListener;
	private int showWhichUp=1;
	private TextView  tvPriceReset, tvPriceFinish,tvYuanTV;
	private EditText etLowPrice, etHighPrice;
	private String lowPrice, highPrice;
	int type = 2; //2买房 1 租房
	public PopViewListAndEdit(Context context, List<String> items, List<String> itemsVaule, int showWhichUp) {
		super(context);
		this.items = items;
		this.itemsVaule = itemsVaule;
		this.showWhichUp = showWhichUp;
		this.mContext = context;
		init();
	}

	public PopViewListAndEdit(Context context, AttributeSet attrs,
                              List<String> items, List<String> itemsVaule, int showWhichUp){
		super(context, attrs);
		this.items = items;
		this.itemsVaule = itemsVaule;
		this.showWhichUp = showWhichUp;
		this.mContext = context;
		init();
	}
	public void setData(List<String> data,List<String> values,int type){
		this.items = data;
		this.itemsVaule = values;
		this.type = type;
		init();
	}
	
	public void init(){

		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_list_popuwindow_price, this, true);
		mListView = (ListView) findViewById(R.id.list);

		if(items!=null&&itemsVaule!=null){
			adapter = new PopListTextAdapter(mContext, items, R.color.colorWhite, R.color.colorWhite,0);
			adapter.setTextSize(14);
			adapter.setTextColor(mContext.getResources()
					.getColor(R.color.blackText));
			mListView.setAdapter(adapter);
			adapter.setOnItemClickListener(new PopListTextAdapter.OnItemClickListener() {
				
				@Override
				public void onItemClick(View view, int position) {
					if (mOnSelectListener != null) {
						showText = items.get(position);
						mOnSelectListener.getValue(itemsVaule.get(position), items.get(position));
					}				
				}
			});
		}
		// 设置价格界面
		etLowPrice = (EditText) findViewById(R.id.etLowPrice);
		etHighPrice = (EditText) findViewById(R.id.etHighPrice);
		tvYuanTV = (TextView) findViewById(R.id.yuan);
		if (type == 2){
			tvYuanTV.setText("万元");
		}else {
			tvYuanTV.setText("元");
		}
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
				mOnSelectListener.getEditeValue(lowPrice,highPrice);
			}
		});
	}
	
	/**
	 * 默认选择项显示
	 * @param showText
	 */
	public void setDefaultShowText(String showText){
		this.showText = showText;
	}
	
	public void setOnSelectListener(OnSelectListener onSelectListener) {
		mOnSelectListener = onSelectListener;
	}

	public interface OnSelectListener {
		public void getValue(String high, String low);
		public void getEditeValue(String high,String low);
	}


	@Override
	public void hide() {

	}

	@Override
	public void show() {

	}

}
