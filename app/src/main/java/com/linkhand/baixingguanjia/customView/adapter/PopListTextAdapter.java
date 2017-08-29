package com.linkhand.baixingguanjia.customView.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.entity.Qu;
import com.linkhand.baixingguanjia.entity.Sheng;
import com.linkhand.baixingguanjia.entity.Shi;
import com.linkhand.baixingguanjia.entity.Xiaoqu;

import java.util.List;

import static com.linkhand.baixingguanjia.R.id.position;

public class PopListTextAdapter extends BaseAdapter {

    private Context mContext;
    private List<?> mListData;
    private List<String> mArrayData;
    private int selectedDrawble;
    private int normalDrawbleId;
    private int selectedPos = -1;
    private String selectedText = "";
    private OnClickListener onClickListener;
    private OnItemClickListener mOnItemClickListener;
    private float textSize;
    private int type = 0;//1: 显示选中状态  2ַ  不显示   3

    /**
     * @param context
     * @param listData
     * @param sId      //
     * @param nId      //
     */
    public PopListTextAdapter(List<?> listData, Context context, int sId, int nId, int type) {
        mContext = context;
        mListData = listData;
        this.type = type;
        if (sId != 0) {
            selectedDrawble = sId;
        }
        normalDrawbleId = nId;
        init();
    }


    public PopListTextAdapter(Context context, List<String> arrayData, int sId, int nId, int type) {
        mContext = context;
        mArrayData = arrayData;
        this.type = type;
        if (sId != 0) {
            selectedDrawble = sId;
        }
        normalDrawbleId = nId;
        init();
    }

    private void init() {
        onClickListener = new OnClickListener() {

            @Override
            public void onClick(View view) {
                selectedPos = (Integer) view.getTag(position);
                setSelectedPosition(selectedPos);
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, selectedPos);
                }
            }
        };
    }

    /**
     * 设置选中位置
     *
     * @param pos
     */
    public void setSelectedPosition(int pos) {
        if (mListData != null && pos < mListData.size()) {
            selectedPos = pos;
            if (mListData.get(pos) instanceof Qu) {
                selectedText = ((Qu) mListData.get(pos)).getName();
            } else if (mListData.get(pos) instanceof Xiaoqu) {
                selectedText = ((Xiaoqu) mListData.get(pos)).getName();

            } else if (mListData.get(pos) instanceof Sheng) {
                selectedText = ((Sheng) mListData.get(pos)).getName();
            } else if (mListData.get(pos) instanceof Shi) {
                selectedText = ((Shi) mListData.get(pos)).getName();
            }

            notifyDataSetChanged();
        } else if (mArrayData != null && pos < mArrayData.size()) {
            selectedPos = pos;
            selectedText = mArrayData.get(pos);
            notifyDataSetChanged();
        }
    }

    /**
     * 设置谁被选中
     * @param o
     * @param name
     */
    public void setSelectedPosition(Object o, String name) {
        if (o instanceof Qu) {
            for (int i = 0; i < mListData.size(); i++) {
                if (((Qu) mListData.get(i)).getName().equals(name)){
                    selectedPos = i;
                    selectedText  = name;
                }
            }
        }
        if (o instanceof Xiaoqu) {
            for (int i = 0; i < mListData.size(); i++) {
                if (((Xiaoqu) mListData.get(i)).getName().equals(name)){
                    selectedPos = i;
                    selectedText  = name;
                }
            }
        }
        if (o instanceof Shi) {
            for (int i = 0; i < mListData.size(); i++) {
                if (((Shi) mListData.get(i)).getName().equals(name)){
                    selectedPos = i;
                    selectedText  = name;
                }
            }
        }
        if (o instanceof Sheng) {
            for (int i = 0; i < mListData.size(); i++) {
                if (((Sheng) mListData.get(i)).getName().equals(name)){
                    selectedPos = i;
                    selectedText  = name;
                }
            }
        }
        notifyDataSetChanged();

    }

    /**
     * 设置谁被选中
     */
    public void setSelectedPositionNoNotify(int pos) {
        selectedPos = pos;
        if (mListData != null && pos < mListData.size()) {
            if (mListData.get(pos) instanceof Qu) {
                selectedText = ((Qu) mListData.get(pos)).getName();
            } else if (mListData.get(pos) instanceof Xiaoqu) {
                selectedText = ((Xiaoqu) mListData.get(pos)).getName();
            } else if (mListData.get(pos) instanceof Sheng) {
                selectedText = ((Sheng) mListData.get(pos)).getName();
            } else if (mListData.get(pos) instanceof Shi) {
                selectedText = ((Shi) mListData.get(pos)).getName();
            }

//            selectedText = mListData.get(pos);
        } else if (mArrayData != null && pos < mArrayData.size()) {
            selectedText = mArrayData.get(pos);
        }
    }

    /**
     * 获取选中位置
     */
    public int getSelectedPosition() {
        if (mArrayData != null && selectedPos < mArrayData.size()) {
            return selectedPos;
        }
        if (mListData != null && selectedPos < mListData.size()) {
            return selectedPos;
        }

        return -1;
    }

    /**
     * 设置文字大小
     */
    public void setTextSize(float tSize) {
        textSize = tSize;
    }

    int colorRes;

    public void setTextColor(int res) {
        colorRes = res;
    }

    @Override
    public int getCount() {
        if (mListData != null) {
            return mListData.size();
        } else if (mArrayData != null) {
            return mArrayData.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mListData != null) {
            return mListData.get(position);
        } else if (mArrayData != null) {
            return mArrayData.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.view_area_popuwindow_item, parent, false);
            holder.tvContent = (TextView) convertView.findViewById(R.id.tvContent);
            holder.vLine = convertView.findViewById(R.id.vLine);
            holder.vDivider = convertView.findViewById(R.id.vDivider);
            holder.vBottomDivider = convertView.findViewById(R.id.vBottomDivider);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        convertView.setTag(R.id.position, position);
        String mString = "";
        if (mListData != null) {
            if (position < mListData.size()) {
                if (mListData.get(position) instanceof Qu) {
                    mString = ((Qu) mListData.get(position)).getName();
                } else if (mListData.get(position) instanceof Xiaoqu) {
                    mString = ((Xiaoqu) mListData.get(position)).getName();
                } else if (mListData.get(position) instanceof Sheng) {
                    mString = ((Sheng) mListData.get(position)).getName();
                } else if (mListData.get(position) instanceof Shi) {
                    mString = ((Shi) mListData.get(position)).getName();
                }
//                mString = mListData.get(position);
            }
            holder.vDivider.setVisibility(View.VISIBLE);
        } else if (mArrayData != null) {
            if (position < mArrayData.size()) {
                mString = mArrayData.get(position);
            }
            holder.vLine.setVisibility(View.GONE);
            holder.vDivider.setVisibility(View.GONE);
        }

        if (mString.contains("不限"))
            holder.tvContent.setText("不限");
        else
            holder.tvContent.setText(mString);
        if (textSize != 0) {
            holder.tvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        }
        if (colorRes != 0) {
            holder.tvContent.setTextColor(colorRes);
        }
        if (selectedText != null && selectedText.equals(mString)) {
            if (type == 1) {
                holder.vLine.setVisibility(View.VISIBLE);
            } else {
                holder.vLine.setVisibility(View.GONE);
            }
            convertView.setBackgroundResource(selectedDrawble);
        } else {
            holder.vLine.setVisibility(View.GONE);
            convertView.setBackgroundResource(normalDrawbleId);
        }
        if (type == 3 && mArrayData != null
                && mArrayData.size() > 0 && (position == mArrayData.size() - 1)) {
            holder.vBottomDivider.setVisibility(View.VISIBLE);
        } else {
            holder.vBottomDivider.setVisibility(View.GONE);
        }
        convertView.setOnClickListener(onClickListener);
        return convertView;
    }

    private class ViewHolder {
        TextView tvContent;
        View vLine, vDivider, vBottomDivider;
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        mOnItemClickListener = l;
    }

    /**
     *
     */
    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

}
