package com.linkhand.baixingguanjia.utils;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.base.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * 该类提供预览、选择操作、数量检测等功能。
 */

/**
 * 该类仅供相册工具类使用，如果只是预览图片请使用MulitPhotoViewActivity类。
 */

public class CommonPhotoViewActivity extends BaseActivity implements ViewPager.OnPageChangeListener, PhotoViewAttacher.OnPhotoTapListener {

    @Bind(R.id.gallery)
    ViewPager gallery;
    //    @Bind(R.id.index_text)
//    TextView indexText;
    @Bind(R.id.actionbar_back)
    RelativeLayout backBtn;
    @Bind(R.id.acpv_tv_nums)
    TextView numsTxt;
    @Bind(R.id.btn_send)
    LinearLayout sendBtn;
    @Bind(R.id.acpv_tv_txt)
    TextView wanchengTxt;
    @Bind(R.id.acpv_iv_checkbox)
    ImageView mCheckBox;
    private int index;
    private int nums = 0;//选择的照片的张数
    private ArrayList<MediaItem> imageUrls;
    private List<PhotoView> imgViews;
    private int currentPosition;
    private int max = 9;//最多选择的照片数
    private boolean isPreview = false;

    @Override
    protected void getBundleExtras(Bundle extras) {
        imageUrls = (ArrayList<MediaItem>) extras.get(Constants.Keys.IMAGE_ITEMS);
        currentPosition = extras.getInt("position");
        max = extras.getInt("max");
        isPreview = extras.getBoolean("key", false);//是否是预览
        index = currentPosition;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_photo_view);
        ButterKnife.bind(this);
        initViewsAndEventss();
    }


    private void initViewsAndEventss() {

        if (imageUrls == null || imageUrls.size() == 0) {
            finish();
        }


        //遍历判断已经选择了多少张
        for (MediaItem imageItem : imageUrls
                ) {
            if (imageItem.isChoose == 0) {
                nums++;
            }
        }
        setNums();//set1 nums

        //确定当前页是否被选择
        if (imageUrls.get(currentPosition).isChoose == 0) {
            mCheckBox.setImageDrawable(getResources().getDrawable(R.drawable.xiangjijiaojuan_dagou));
        } else {
            mCheckBox.setImageDrawable(getResources().getDrawable(R.drawable.xiangjijiaojuan_dagoubg));
        }

        //点击后，集合中数据点击状态的变化
        mCheckBox.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             if (imageUrls.get(index).isChoose == 0) {
                                                 imageUrls.get(index).isChoose = 1;
                                                 mCheckBox.setImageDrawable(getResources().getDrawable(R.drawable.xiangjijiaojuan_dagoubg));
                                                 nums--;
                                             } else {
                                                 if (nums >= max) {
                                                     showToast(String.format("您最多只能选择%d张照片", max));
                                                 } else {
                                                     imageUrls.get(index).isChoose = 0;
                                                     mCheckBox.setImageDrawable(getResources().getDrawable(R.drawable.xiangjijiaojuan_dagou));
                                                     nums++;
                                                 }
                                             }
                                             setNums();
                                         }
                                     }

        );


        //   indexText.setText(String.format("%d/%d", 1, imageUrls.size()));
        imgViews = new ArrayList<>(imageUrls.size());
        for (MediaItem imageItem : imageUrls) {
            PhotoView img = new PhotoView(this);
            img.setScaleType(ImageView.ScaleType.FIT_CENTER);
//            img.setOnPhotoTapListener(this);
            img.setEnabled(true);
            Glide.with(CommonPhotoViewActivity.this).load(imageItem.getImagePath()).placeholder(R.drawable.fabuxuanshang_tupian_def).error(R.drawable.fabuxuanshang_tupian_def).into(img);
            imgViews.add(img);
        }

        gallery.setAdapter(new

                                   PagerAdapter() {
                                       @Override
                                       public int getCount() {
                                           return imageUrls == null ? 0 : imageUrls.size();
                                       }

                                       @Override
                                       public boolean isViewFromObject(View view, Object object) {
                                           return view == object;
                                       }

                                       @Override
                                       public void destroyItem(ViewGroup container, int position, Object object) {
                                           container.removeView((View) object);
                                       }

                                       @Override
                                       public Object instantiateItem(ViewGroup container, int position) {

                                           PhotoView view = imgViews.get(position);
                                           container.addView(view);
                                           return view;
                                       }
                                   }

        );
        gallery.addOnPageChangeListener(this);
        if (currentPosition == -1)

        {
            //   indexText.setVisibility(View.GONE);
        } else

        {
            //   indexText.setVisibility(View.VISIBLE);
            gallery.setCurrentItem(currentPosition);
            mCheckBox.setImageDrawable(imageUrls.get(currentPosition).isChoose == 0 ? getResources().getDrawable(R.drawable.xiangjijiaojuan_dagou) : getResources().getDrawable(R.drawable.xiangjijiaojuan_dagoubg));

        }

    }

    private void setNums() {
        //show nums
        if (nums == 0) {
            sendBtn.setClickable(false);
            numsTxt.setVisibility(View.GONE);
            wanchengTxt.setTextColor(Color.argb(0x7d, 0x23, 0x97, 0xf3));
        } else {
            sendBtn.setClickable(true);
            numsTxt.setVisibility(View.VISIBLE);
            numsTxt.setText(nums + "");
            wanchengTxt.setTextColor(Color.rgb(0x23, 0x97, 0xf3));
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //  indexText.setText(String.format("%d/%d", position + 1, imgViews.size()));
        index = position;
        if (imageUrls.get(position).isChoose == 0) {
            mCheckBox.setImageDrawable(getResources().getDrawable(R.drawable.xiangjijiaojuan_dagou));

        } else {
            mCheckBox.setImageDrawable(getResources().getDrawable(R.drawable.xiangjijiaojuan_dagoubg));

        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

//    @Override
//    protected int getContentViewLayoutID() {
//        return R.layout.activity_common_photo_view;
//    }

    @Override
    public void onPhotoTap(View view, float x, float y) {
        //直接到结果页面
        Intent intent = new Intent();
        intent.putExtra(Constants.Keys.IMAGES, imageUrls);
        intent.putExtra("selected", nums);
        intent.putExtra(Constants.PublishTask.PIC_KEY, Constants.PublishTask.PICVIEW2MULITSELECT_KEY);
        intent.putExtra("key", isPreview);//额外的标记，标记是否是预览
        setResult(Constants.PublishTask.PICVIEW2MULITPIC_KEY, intent);


        finish();
    }


    @OnClick(R.id.actionbar_back)
    public void onClick() {
        //直接到上一个页面
        Intent intent = new Intent();
        intent.putExtra(Constants.Keys.IMAGES, imageUrls);
        intent.putExtra("selected", nums);
        intent.putExtra(Constants.PublishTask.PIC_KEY, Constants.PublishTask.PICVIEW2MULITSELECT_KEY);
        intent.putExtra("key", isPreview);//额外的标记，标记是否是预览
        setResult(Constants.PublishTask.PICVIEW2MULITPIC_KEY, intent);
        finish();
    }

    @OnClick(R.id.btn_send)
    public void send() {
        //直接到结果页面
        ArrayList<MediaItem> imageItemArrayList = new ArrayList<>();
        for (MediaItem imageItem : imageUrls) {
            if (imageItem.isChoose == 0) imageItemArrayList.add(imageItem);
        }

        Intent intent = new Intent();
        intent.putExtra(Constants.Keys.IMAGES, imageItemArrayList);
        setResult(RESULT_OK, intent);
        finish();
    }

    //返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            //直接到结果页面
            Intent intent = new Intent();
            intent.putExtra(Constants.Keys.IMAGES, imageUrls);
            intent.putExtra("selected", nums);
            intent.putExtra(Constants.PublishTask.PIC_KEY, Constants.PublishTask.PICVIEW2MULITSELECT_KEY);
            intent.putExtra("key", isPreview);//额外的标记，标记是否是预览
            setResult(Constants.PublishTask.PICVIEW2MULITPIC_KEY, intent);
            finish();
        }
        return false;
    }

}
