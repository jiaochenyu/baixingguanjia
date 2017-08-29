package com.linkhand.baixingguanjia.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.linkhand.baixingguanjia.widget.popup.LoadingPopup;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by jcy on 2016/12/19.
 */
public abstract class BaseActivity extends BaseAppCompatActivity {
    private static final String TAG = "BaseActivity";
    protected LoadingPopup loadingPop;
    private int currentPercent;
    private boolean canSideOut;
    private Toast toast = null;

    private Handler myHandler = new Handler();

    private Runnable mLoadingRunnable = new Runnable() {

        @Override
        public void run() {
            if (loadingPop == null) {
                loadingPop = new LoadingPopup(BaseActivity.this);
            }
            if (canSideOut) {
                loadingPop.pickPhotoView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            } else {
                loadingPop.pickPhotoView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadingPop.dismiss();
                    }
                });
            }
            loadingPop.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        }
    };
    private Runnable mHideLoadingRunnable = new Runnable() {
        @Override
        public void run() {
            if (loadingPop != null) {
                loadingPop.dismiss();
            }
        }
    };

    private Runnable mShowPercentRunnable = new Runnable() {
        @Override
        public void run() {
            if (loadingPop != null) {
                loadingPop.percentLyt.setVisibility(View.VISIBLE);
                loadingPop.loadLyt.setVisibility(View.GONE);
                loadingPop.percentBar.setProgress(currentPercent);
            }
        }
    };


//    @Override
//    public void setContentView(@LayoutRes int layoutResID) {
//        super.setContentView(layoutResID);
//    }

    // @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        if (isBindEventBusHere()) {
//            EventBus.getDefault().register(this);
//        }
//        super.onCreate(savedInstanceState);
//
//
//    }

    @Override
    protected void initSubscription() {

    }

    @Override
    protected void setCustomTitle(CharSequence title) {

    }

    @Override
    protected boolean hasTitlebar() {
        return false;
    }

    @Override
    protected void onNavigateClick() {

    }

    @Override
    protected boolean hasSoft() {
        return false;
    }

    @Override
    protected void onEditOutSideClick() {

    }

    @Override
    protected boolean isOverridePendingTransition() {
        return false;
    }

    @Override
    protected void onNetworkDisconnect() {

    }

    @Override
    protected void onNetworkConnected() {

    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return 0;
    }

    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected TransitionMode getTransitionMode() {
        return null;
    }

    //***********隐藏软键盘*************
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                View v = getCurrentFocus();
                if (isShouldHideInput(v, ev)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        Log.e("隐藏软键盘", "是的");
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }

        }
        return super.dispatchTouchEvent(ev);
    }

    //***********隐藏软键盘*************
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


    public synchronized void showLoading() {
        Log.e(TAG, ">>>>>>  showLoading");
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                myHandler.post(mLoadingRunnable);
            }
        });
    }

    public void showLoading(boolean canSideOut) {
        this.canSideOut = canSideOut;
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                myHandler.post(mLoadingRunnable);
            }
        });
    }

    public synchronized void hideLoading() {
        // Log.e(TAG, ">>>>>>  hideLoading");
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                myHandler.post(mHideLoadingRunnable);
            }
        });
    }

    public synchronized void showPercentLoading(int percent) {
        currentPercent = percent;
        // Log.e(TAG, ">>>>>>  hideLoading");
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                myHandler.post(mShowPercentRunnable);
            }
        });
    }


    public void showToast(String msg) {
        if (null != msg) {
            if (toast == null) {
                toast = Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
            } else {
                toast.setText(msg);
            }
            toast.show();
        }
    }

    public void showToast(int msg) {
        if (toast == null) {
            toast = Toast.makeText(BaseActivity.this, getString(msg), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(msg);
        }
        toast.show();

    }

    // 判断list是否不为空且有值
    public boolean adjustList(List<?> list) {
        if (list != null && list.size() > 0 && (!list.isEmpty())) {
            return true;
        } else {
            return false;
        }
    }

    public String getStrgRes(int res){
        return getResources().getString(res);
    }

    //PullToRefreshListView 自定义头部尾部加载信息
    public void initRefreshListView(PullToRefreshListView mListview) {
        ILoadingLayout startLables = mListview.getLoadingLayoutProxy(true,false);
        startLables.setPullLabel("下拉刷新");
        startLables.setRefreshingLabel("玩命刷新中...");
        startLables.setReleaseLabel("放开刷新");
        ILoadingLayout endLables = mListview.getLoadingLayoutProxy(false,true);
        endLables.setPullLabel("上拉加载");
        endLables.setRefreshingLabel("玩命加载中...");
        endLables.setReleaseLabel("放开加载");
    }

    // 图片选择器 横向滑动的GridView
    public void horizontal_layout(List<?> mList, GridView mGridView) {
        int size = mList.size();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int allWidth = (int) (70 * size * density + 5 * density * (size - 1)); // 宽度加间距
        int itemWidth = (int) (70 * density);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(allWidth,
                LinearLayout.LayoutParams.MATCH_PARENT);
        mGridView.setLayoutParams(params);
        mGridView.setColumnWidth(itemWidth);
        mGridView.setHorizontalSpacing((int) (5 * density));
        mGridView.setStretchMode(GridView.NO_STRETCH);
        mGridView.setNumColumns(size);
    }


    @Override
    protected void onDestroy() {
        if (isBindEventBusHere()) {
            EventBus.getDefault().unregister(this);
        }
        hideLoading();
        toast = null;
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (toast != null) {
            toast.cancel();
        }
    }


    /**
     * 充值
     * @param info
     * @param clazz
     */
     public  void showBaseDialog(String info, final Class<? extends Activity> clazz){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage(info);
        builder.setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
             go(clazz);
            }
        });
        AlertDialog alterDialog = builder.create();
        alterDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alterDialog.show();
    }


}
