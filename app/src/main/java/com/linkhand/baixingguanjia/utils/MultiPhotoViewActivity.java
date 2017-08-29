package com.linkhand.baixingguanjia.utils;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.base.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * 这是多图片预览工具类，不要使用CommonPhotoViewActivity进行预览。
 */
public class MultiPhotoViewActivity extends BaseActivity implements ViewPager.OnPageChangeListener, PhotoViewAttacher.OnPhotoTapListener {

    @Bind(R.id.gallery)
    ViewPager gallery;
    @Bind(R.id.index_text)
    TextView indexText;
    private ArrayList<String> imageUrls;
    private List<PhotoView> imgViews;
    private int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_photo_view);
        ButterKnife.bind(this);
        if (imageUrls == null || imageUrls.size() == 0) {
            finish();
//            imageUrls = new ArrayList<String>(){
//                {
//                    add("http://appimage2.tiangongyipin.com/1458537785933-qUaIZ");
//                    add("http://appimage2.tiangongyipin.com/1458537789105-NPcLi");
//                    add("http://appimage2.tiangongyipin.com/1458537790882-RKvfL");
//                    add("http://appimage2.tiangongyipin.com/1458537795089-BZvhg");
//                    add("http://appimage2.tiangongyipin.com/1458537797223-FMnOs");
//                }
//            };
        }

        indexText.setText(String.format("%d/%d", 1, imageUrls.size()));
        imgViews = new ArrayList<>(imageUrls.size());
        for (String url : imageUrls) {
            PhotoView img = new PhotoView(this);
            img.setScaleType(ImageView.ScaleType.FIT_CENTER);
            img.setOnPhotoTapListener(this);
            Glide.with(MultiPhotoViewActivity.this).load(url).placeholder(R.drawable.loadpic).error(R.drawable.errorpic).into(img);
            imgViews.add(img);
        }

        gallery.setAdapter(new PagerAdapter() {
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
        });
        gallery.addOnPageChangeListener(this);
        if (currentPosition == -1) {
            indexText.setVisibility(View.GONE);
        } else {
            indexText.setVisibility(View.VISIBLE);
            gallery.setCurrentItem(currentPosition);
        }
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        imageUrls = extras.getStringArrayList(Constants.Keys.IMAGE_ITEMS);
        currentPosition = extras.getInt("position");
    }

//    @Override
//    protected void initViewsAndEvents() {
//
//
//    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        indexText.setText(String.format("%d/%d", position + 1, imgViews.size()));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_multi_photo_view;
    }

    @Override
    public void onPhotoTap(View view, float x, float y) {
        finish();
    }

}
