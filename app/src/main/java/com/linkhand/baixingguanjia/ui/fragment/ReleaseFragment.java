package com.linkhand.baixingguanjia.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseFragment;
import com.linkhand.baixingguanjia.base.ConnectUrl;
import com.linkhand.baixingguanjia.base.MyApplication;
import com.linkhand.baixingguanjia.entity.ReleaseIndex;
import com.linkhand.baixingguanjia.ui.activity.release.CommonReleaseActivity;
import com.linkhand.baixingguanjia.ui.activity.release.EducationReleaseActivity;
import com.linkhand.baixingguanjia.ui.activity.release.HouseKeepReleaseActivity;
import com.linkhand.baixingguanjia.ui.activity.release.HousePropertyReleaseActivity;
import com.linkhand.baixingguanjia.ui.activity.release.IdleGoodsReleaseActivity;
import com.linkhand.baixingguanjia.ui.activity.release.PublicWelfareReleaseActivity;
import com.linkhand.baixingguanjia.ui.activity.release.RecruitReleaseActivity;
import com.linkhand.baixingguanjia.ui.activity.release.SecondCarReleaseActivity;
import com.linkhand.baixingguanjia.utils.ImageUtils;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JCY on 2017/6/14.
 * 说明：
 */

public class ReleaseFragment extends BaseFragment {
    private static final int REQUEST_WHAT1 = 1
            ;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.option)
    TextView mOptionTV;
    @Bind(R.id.gridView)
    GridView mGridView;
    private View mView;
    String[] titles = {"房产", "闲置物品", "二手车", "家政", "教育", "招聘", "寻人", "寻物", "善行"};
    int[] icons = {R.drawable.fangchan, R.drawable.xianzhi, R.drawable.che, R.drawable.jiazheng,
            R.drawable.jiaoyu, R.drawable.zhaopin, R.drawable.xunren, R.drawable.xunwu, R.drawable.shanxing};

//    private ReleaseGridViewAdapter mAdapter;
    private List<ReleaseIndex> mList;

    CommonAdapter mAdapter;
    private RequestQueue mQueue = NoHttp.newRequestQueue();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_release, null);
        ButterKnife.bind(this, mView);
        initView();
        initData();
        initListener();
        return mView;
    }

    public void onResume() {
        super.onResume();
        setStatusBarColor(R.color.colorSystemBlue);
    }


    private void initView() {
    }

    private void initData() {
        mList = new ArrayList<>();
//        Collections.addAll(mList, titles);
        mAdapter = new MyAdapter(getActivity(), R.layout.item_release_gridview, mList);
        mGridView.setAdapter(mAdapter);
        getData();
    }

    private void initListener() {
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (MyApplication.getUser() == null) {
                    showToast(R.string.pleaseLogin);
                    return;
                }
                switch (position) {
                    case 0:
//                        startActivity(new Intent(getActivity(), CommonReleaseActivity.class));
                        startActivity(new Intent(getActivity(), HousePropertyReleaseActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(), IdleGoodsReleaseActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getActivity(), SecondCarReleaseActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(getActivity(), HouseKeepReleaseActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(getActivity(), EducationReleaseActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(getActivity(), RecruitReleaseActivity.class));
                        break;
                    case 6:
                        Bundle bundle2 = new Bundle();
                        bundle2.putInt("sortFlag", 1);
                        go(PublicWelfareReleaseActivity.class, bundle2);
                        break;
                    case 7:
                        Bundle bundle1 = new Bundle();
                        bundle1.putInt("sortFlag", 2);
                        go(PublicWelfareReleaseActivity.class, bundle1);
                        break;
                    case 8:
                        Bundle bundle = new Bundle();
                        bundle.putInt("sortFlag", 3);
                        go(PublicWelfareReleaseActivity.class, bundle);
                        break;
                    default:
                        Bundle bundleC  = new Bundle();
                        bundleC.putString("goods_type",mList.get(position).getId()); //模块id
                        go(CommonReleaseActivity.class,bundleC);
                        break;


                }
            }
        });
    }

    private void getData() {
        for (int i = 0; i < icons.length; i++) {
            ReleaseIndex releaseIndex = new ReleaseIndex(titles[i],icons[i]);
            mList.add(releaseIndex);
        }
        mAdapter.notifyDataSetChanged();
        httpGetList();
    }
    private void httpGetList() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.PRODUCT_INDEX_RELEASE, RequestMethod.POST);

        mQueue.add(REQUEST_WHAT1, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == REQUEST_WHAT1) {
                    String resultCode = null;
                    Gson gson = new Gson();
                    try {
                        JSONObject jsonObject = response.get();
                        resultCode = jsonObject.getString("code");
                        if (resultCode.equals("200")) {
                           JSONArray array = jsonObject.getJSONArray("data");
                            for (int i = 0; i < array.length(); i++) {
                                ReleaseIndex index = gson.fromJson(array.get(i).toString(),ReleaseIndex.class);
                                mList.add(index);
                                mAdapter.notifyDataSetChanged();
                            }
                        } else {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<JSONObject> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }


    @OnClick(R.id.option)
    public void onViewClicked() {

    }


    class  MyAdapter extends CommonAdapter{

        public MyAdapter(Context context, int layoutId, List datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, Object item, int position) {
            TextView textView = holder.getView(R.id.relese_iconName);
            ImageView imageView = holder.getView(R.id.relese_icon);
            textView.setText(mList.get(position).getName());
            if ( mList.get(position).getIcons()!=0){
                ImageUtils.setDefaultImage(imageView, ConnectUrl.REQUESTURL_IMAGE+mList.get(position).getTubiao(),mList.get(position).getIcons());
            }else {
                ImageUtils.setDefaultImage(imageView, ConnectUrl.REQUESTURL_IMAGE+mList.get(position).getTubiao(),R.drawable.shanxing);
            }
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void onHiddenChanged(boolean hidden) {
// TODO Auto-generated method stub
        super.onHiddenChanged(hidden);
        if (hidden) {// 不在最前端界面显示

        } else {// 重新显示到最前端中
            setStatusBarColor(R.color.colorSystemBlue);
        }
    }


}
