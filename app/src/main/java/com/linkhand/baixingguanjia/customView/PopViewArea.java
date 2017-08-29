package com.linkhand.baixingguanjia.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.customView.adapter.PopListTextAdapter;
import com.linkhand.baixingguanjia.entity.Qu;
import com.linkhand.baixingguanjia.entity.Sheng;
import com.linkhand.baixingguanjia.entity.Xiaoqu;
import com.linkhand.baixingguanjia.listener.PopViewBaseActionListener;
import com.linkhand.baixingguanjia.utils.JSONUtils;
import com.linkhand.baixingguanjia.utils.SPUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 地区选择
 *
 * @author linmz
 */
public class PopViewArea extends LinearLayout implements PopViewBaseActionListener {

    private ListView lvRegion, lvPlate;
    private List<Qu> groups = new ArrayList<Qu>();
    private SparseArray<LinkedList<Xiaoqu>> children = new SparseArray<LinkedList<Xiaoqu>>();
    private LinkedList<Xiaoqu> childrenItem = new LinkedList<Xiaoqu>();
    private PopListTextAdapter plateListViewAdapter;
    private PopListTextAdapter earaListViewAdapter;
    private int tEaraPosition = 0;  //区
    private int tBlockPosition = 0; //小区
    private String showString = "不限";
    private OnSelectListener mOnSelectListener;
    Sheng sheng ;


    public PopViewArea(Context context) {
        super(context);
        init(context);
    }

    public PopViewArea(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void updateShowText(String showArea, String showBlock) {
        if (showArea == null || showBlock == null) {
            return;
        }
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i).equals(showArea)) {
                earaListViewAdapter.setSelectedPosition(i);
                childrenItem.clear();
                if (i < children.size()) {
                    childrenItem.addAll(children.get(i));
                }
                tEaraPosition = i;
                break;
            }
        }
        for (int j = 0; j < childrenItem.size(); j++) {
            if (childrenItem.get(j).getName().replace("不限", "").equals(showBlock.trim())) {
                plateListViewAdapter.setSelectedPosition(j);
                tBlockPosition = j;
                break;
            }
        }
        setDefaultSelect();
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_area_popupwindow, this, true);
        lvRegion = (ListView) findViewById(R.id.lvRegion);
        lvPlate = (ListView) findViewById(R.id.lvPlate);



        sheng = (Sheng) SPUtils.get(context, "DiQu", new TypeToken<Sheng>() {
        }.getType());
        Map<String, Object> map = JSONUtils.getDiqu(sheng.getShiList().get(0).getQuList());
        groups = (ArrayList<Qu>) map.get("groups");
        children = (SparseArray<LinkedList<Xiaoqu>>) map.get("children");

        earaListViewAdapter = new PopListTextAdapter(groups, context,
                R.color.colorWhite,
                R.drawable.pop_list_choose_eara_item_selector, 1);
        earaListViewAdapter.setTextSize(13);
        earaListViewAdapter.setTextColor(context.getResources().getColor(R.color.blackText));
        earaListViewAdapter.setSelectedPositionNoNotify(tEaraPosition);
        lvRegion.setAdapter(earaListViewAdapter);
        earaListViewAdapter
                .setOnItemClickListener(new PopListTextAdapter.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {
                        if (position < children.size()) {
                            childrenItem.clear();
                            childrenItem.addAll(children.get(position));
                            Log.e("地区接口参数", "onItemClick: "+childrenItem.size() );
                            plateListViewAdapter.notifyDataSetChanged();
                        }
                        if (position == 0) {
                            showString = groups.get(position).getName();
                            if (mOnSelectListener != null) {
                                mOnSelectListener.getValue(sheng,-1,-1,showString);
                            }
                        }
                        tEaraPosition = position;

                    }
                });
        if (tEaraPosition < children.size())
            childrenItem.addAll(children.get(tEaraPosition));
        plateListViewAdapter = new PopListTextAdapter( childrenItem,context, 0,
                R.drawable.pop_list_choose_plate_item_selector, 2);
        plateListViewAdapter.setTextSize(13);
        plateListViewAdapter.setTextColor(context.getResources().getColor(R.color.blackText));
        plateListViewAdapter.setSelectedPositionNoNotify(tBlockPosition);
        lvPlate.setAdapter(plateListViewAdapter);
        plateListViewAdapter
                .setOnItemClickListener(new PopListTextAdapter.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, final int position) {

                        showString = childrenItem.get(position).getName();
                        if (mOnSelectListener != null) {

                            mOnSelectListener.getValue(sheng,tEaraPosition-1,position-1,showString);
                        }

                    }
                });
        if (tBlockPosition < childrenItem.size())
            showString = childrenItem.get(tBlockPosition).getName();
        if (showString.contains("不限")) {
            showString = showString.replace("不限", "");
        }
        setDefaultSelect();

    }

    public void setDefaultSelect() {
        lvRegion.setSelection(tEaraPosition);
        lvPlate.setSelection(tBlockPosition);
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }

    public interface OnSelectListener {
        /***
         *
         * @param s
         * @param quPos  区的位置
         * @param xiaoquPos 小区的位置
         * @param showText 显示内容
         */
        public void getValue(Sheng s,int quPos,int xiaoquPos,String showText);
    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }

}
