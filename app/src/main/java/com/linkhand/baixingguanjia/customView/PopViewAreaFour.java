package com.linkhand.baixingguanjia.customView;

import android.content.Context;
import android.util.AttributeSet;
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
import com.linkhand.baixingguanjia.entity.Shi;
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
public class PopViewAreaFour extends LinearLayout implements PopViewBaseActionListener {

    private ListView lvSheng, lvShi, lvQu, lvXiaoqu;
    private List<Sheng> shengs = new ArrayList<Sheng>();
    private SparseArray<LinkedList<Shi>> shis = new SparseArray<LinkedList<Shi>>();
    private SparseArray<SparseArray<LinkedList<Qu>>> qus = new SparseArray<>();
    private SparseArray<SparseArray<SparseArray<LinkedList<Xiaoqu>>>> xiaoqus = new SparseArray<>();


    private LinkedList<Shi> shiItem = new LinkedList<Shi>();
    private LinkedList<Qu> quItem = new LinkedList<Qu>();
    private LinkedList<Xiaoqu> xiaoquItem = new LinkedList<Xiaoqu>();

    private PopListTextAdapter shengListViewAdapter;
    private PopListTextAdapter shiListViewAdapter;
    private PopListTextAdapter quListViewAdapter;
    private PopListTextAdapter xiaoquListViewAdapter;
    private int tShengPosition = 0;  //省
    private int tShiPosition = 0; //市
    private int tQuPosition = 0; //区
    private int tXiaoquPosition = 0; //小区
    private String showString = "不限";
    private OnSelectListener mOnSelectListener;


    public PopViewAreaFour(Context context) {
        super(context);
        init(context);
    }

    public PopViewAreaFour(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_area_four_popupwindow, this, true);
        lvSheng = (ListView) findViewById(R.id.lvSheng);
        lvShi = (ListView) findViewById(R.id.lvShi);
        lvQu = (ListView) findViewById(R.id.lvQu);
        lvXiaoqu = (ListView) findViewById(R.id.lvXiaoqu);


        shengs = (List<Sheng>) SPUtils.get(context, "Location", new TypeToken<List<Sheng>>() {
        }.getType());
        if (shengs!=null&& shengs.size()>0){
            Map<String, Object> map = JSONUtils.getDiquAll(shengs);
            shis = (SparseArray<LinkedList<Shi>>) map.get("shis");
            qus = (SparseArray<SparseArray<LinkedList<Qu>>>) map.get("qus");
            xiaoqus = (SparseArray<SparseArray<SparseArray<LinkedList<Xiaoqu>>>>) map.get("xiaoqus");
        }


        //省
        shengListViewAdapter = new PopListTextAdapter(shengs, context,
                R.color.colorWhite,
                R.drawable.pop_list_choose_eara_item_selector, 1);
        shengListViewAdapter.setTextSize(13);
        shengListViewAdapter.setTextColor(context.getResources().getColor(R.color.blackText));
        shengListViewAdapter.setSelectedPositionNoNotify(tShengPosition);
        lvSheng.setAdapter(shengListViewAdapter);
        shengListViewAdapter
                .setOnItemClickListener(new PopListTextAdapter.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {
                        if (position < shis.size()) {
                            shiItem.clear();
                            shiItem.addAll(shis.get(position));
                            shiListViewAdapter.notifyDataSetChanged();

                            quItem.clear();
                            quItem.addAll(qus.get(position).get(0));
                            quListViewAdapter.notifyDataSetChanged();

                            xiaoquItem.clear();
                            xiaoquItem.addAll(xiaoqus.get(position).get(0).get(0));
                            xiaoquListViewAdapter.notifyDataSetChanged();

                        }
//
                        tShengPosition = position;

                    }
                });
        //市
        if (tShengPosition < shis.size())
            shiItem.addAll(shis.get(tShengPosition));
        shiListViewAdapter = new PopListTextAdapter(shiItem, context, 0,
                R.drawable.pop_list_choose_plate_item_selector, 2);
        shiListViewAdapter.setTextSize(13);
        shiListViewAdapter.setTextColor(context.getResources().getColor(R.color.blackText));
        shiListViewAdapter.setSelectedPositionNoNotify(tShiPosition);
        lvShi.setAdapter(shiListViewAdapter);
        shiListViewAdapter
                .setOnItemClickListener(new PopListTextAdapter.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, final int position) {

//                        showString = shiItem.get(position).getName();
                        quItem.clear();
                        quItem.addAll(qus.get(tShengPosition).get(position));
                        quListViewAdapter.notifyDataSetChanged();

                        xiaoquItem.clear();
                        xiaoquItem.addAll(xiaoqus.get(tShengPosition).get(position).get(0));
                        xiaoquListViewAdapter.notifyDataSetChanged();

                        tShiPosition = position;
                    }
                });

        ///区
        if (tShiPosition < qus.size())
            quItem.addAll(qus.get(tShengPosition).get(tShiPosition));
        quListViewAdapter = new PopListTextAdapter(quItem, context, 0,
                R.drawable.pop_list_choose_plate_item_selector, 3);
        quListViewAdapter.setTextSize(13);
        quListViewAdapter.setTextColor(context.getResources().getColor(R.color.blackText));
        quListViewAdapter.setSelectedPositionNoNotify(tQuPosition);
        lvQu.setAdapter(quListViewAdapter);
        quListViewAdapter
                .setOnItemClickListener(new PopListTextAdapter.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, final int position) {

                        showString = quItem.get(position).getName();

                        xiaoquItem.clear();
                        xiaoquItem.addAll(xiaoqus.get(tShengPosition).get(tShiPosition).get(position));
                        xiaoquListViewAdapter.notifyDataSetChanged();

                        //全城
                        if (position == 0) {
                            showString = shis.get(tShengPosition).get(tShiPosition).getName();
                            Sheng sheng = shengs.get(tShengPosition);
                            if (mOnSelectListener != null) {
                                mOnSelectListener.getValue(sheng,sheng.getShiList().get(tShiPosition),null,null,showString);
                            }
                        }

                        tQuPosition = position;

                    }
                });
        ///小区
        if (tQuPosition < xiaoqus.size())
            xiaoquItem.addAll(xiaoqus.get(tShengPosition).get(tShiPosition).get(tQuPosition));
        xiaoquListViewAdapter = new PopListTextAdapter(xiaoquItem, context, 0,
                R.drawable.pop_list_choose_plate_item_selector, 2);
        xiaoquListViewAdapter.setTextSize(13);
        xiaoquListViewAdapter.setTextColor(context.getResources().getColor(R.color.blackText));
        xiaoquListViewAdapter.setSelectedPositionNoNotify(tXiaoquPosition);
        lvXiaoqu.setAdapter(xiaoquListViewAdapter);
        xiaoquListViewAdapter
                .setOnItemClickListener(new PopListTextAdapter.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, final int position) {
                        Sheng sheng = shengs.get(tShengPosition);
                        showString = xiaoquItem.get(position).getName();
                        if (mOnSelectListener != null) {
                            //全区
                            if(position == 0){
                                showString = quItem.get(tQuPosition).getName();
                                mOnSelectListener.getValue(sheng,sheng.getShiList().get(tShiPosition),sheng.getShiList().get(tShiPosition).getQuList().get(tQuPosition-1),null,showString);

                            }else {
                                mOnSelectListener.getValue(sheng,sheng.getShiList().get(tShiPosition),sheng.getShiList().get(tShiPosition).getQuList().get(tQuPosition),sheng.getShiList().get(tShiPosition).getQuList().get(tQuPosition-1).getXiaoquList().get(position-1),showString);
                            }

                        }


                    }
                });


        if (tShiPosition < shiItem.size())
            showString = shiItem.get(tShiPosition).getName();
        if (showString.contains("不限")) {
            showString = showString.replace("不限", "");
        }
        setDefaultSelect();

    }

    public void setDefaultSelect() {
        lvSheng.setSelection(tShengPosition);
        lvShi.setSelection(tShiPosition);
        lvQu.setSelection(tQuPosition);
        lvXiaoqu.setSelection(tXiaoquPosition);
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }

    public interface OnSelectListener {
        /***
         *
         * @param s
         * @param shi
         * @param qu
         * @param xiaoqu
         * @param showText
         */
        public void getValue(Sheng s,Shi shi, Qu qu, Xiaoqu xiaoqu, String showText);
    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }

}
