package com.linkhand.baixingguanjia.utils;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;
import com.linkhand.baixingguanjia.base.Constants;
import com.linkhand.baixingguanjia.base.adapter.BasicAdapter;
import com.linkhand.baixingguanjia.base.adapter.ViewHolder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 打开相册工具类，包括拍照，选择照片等。使用方式下面的程序入口有注释
 */
// FIXME: 2016/11/18 在htc手机上拍照会随机【随机】出现拿不到地址。
public class MultiSelectPhotoGridActivity extends BaseActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static final String TAG2 = "MultiSelectPhotivity2";
    @Bind(R.id.amspg_tv_nums)
    TextView numsTxt;
    @Bind(R.id.amspg_tv_txt)
    TextView txtTxt;
    private int max = 9;
    //private static String text_tips = "已选 <font color=\"#d9534f\">%d</font> 张";
    @Bind(R.id.tips)
    TextView tips;
    @Bind(R.id.btn_send)
    LinearLayout btnSend;
    @Bind(R.id.photo_grid)
    GridView photoGrid;
    private AsyncTask<Void, Void, List<MediaItem>> mPicLoadTask;
    private BasicAdapter<MediaItem> picAdapter;
    private List<MediaItem> selectedPics = new ArrayList<MediaItem>();
    private String photoUrl;//拍照的图片路径
    private String page;//用于判断图片选择完成后进入哪个页面

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_select_photo_grid);
        ButterKnife.bind(this);
        initViewsAndEventss();
    }


    private void initViewsAndEventss() {
        //添加图片过来的
        showSelectedNumber(0);
        int selected = getIntent().getExtras().getInt("selected");
        final int maxC = getIntent().getExtras().getInt("max");
        if (maxC != 0)
            max = maxC;
        max = max - selected;
        //开始grid适配器
        picAdapter = new BasicAdapter<MediaItem>(this, R.layout.item_photo_grid_checkable) {
            @Override
            protected void render(final ViewHolder holder, final MediaItem item, final int position) {
                //如果是第一张图片，设置为拍照图片，并且隐藏选择按钮
                if (position == 0) {
                    holder.gone(R.id.item_checkbox);//隐藏选择框
                    ImageView imageView = holder.getSubView(R.id.item_photo_grid);//更换为拍照按钮
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.login_paitu_def));
                } else {
                    holder.visible(R.id.item_checkbox);
                    if (item.isChoose == 0) {
                        holder.setBackgroundImage(R.id.item_checkbox, R.drawable.xiangjijiaojuan_dagou);
                    } else {
                        holder.setBackgroundImage(R.id.item_checkbox, R.drawable.xiangjijiaojuan_dagoubg);
                    }
                    holder.setImagePhoto(R.id.item_photo_grid/*, R.drawable.img_default*/, String.format("file://%s", item.getImagePath()));
                }


                //选择图片
                holder.onClick(R.id.item_checkbox, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.isChoose == 0) {
                            item.isChoose = 1;
                            selectedPics.remove(item);
                            holder.setBackgroundImage(R.id.item_checkbox, R.drawable.xiangjijiaojuan_dagoubg);
                        } else {
                            //这里进行只有一张图片的判断
                            if (max == 1) {
                                //重置
                                List<MediaItem> dataList = picAdapter.getDataList();
                                for (MediaItem mediaItem : dataList) {
                                    if (mediaItem.isChoose == 0) {
                                        mediaItem.isChoose = 1;
                                        selectedPics.remove(mediaItem);
                                    }
                                }

                                //重新设置当前按钮
                                item.isChoose = 0;
                                selectedPics.add(item);
                                holder.setBackgroundImage(R.id.item_checkbox, R.drawable.xiangjijiaojuan_dagou);
                                showSelectedNumber(selectedPics.size());
                                notifyDataSetChanged();
                                return;
                            }


                            if (selectedPics.size() >= max) {
                                showToast(String.format("您最多只能选择%d张照片", max));
                            } else {
                                item.isChoose = 0;
                                selectedPics.add(item);
                                holder.setBackgroundImage(R.id.item_checkbox, R.drawable.xiangjijiaojuan_dagou);
                            }
                        }
                        showSelectedNumber(selectedPics.size());
                    }
                });

                //预览以及拍照
                ImageView checkbox2 = holder.getSubView(R.id.item_photo_grid);
                checkbox2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //进行拍照
                        if (position == 0) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (ContextCompat.checkSelfPermission(MultiSelectPhotoGridActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                    //申请WRITE_EXTERNAL_STORAGE权限
                                    ActivityCompat.requestPermissions(MultiSelectPhotoGridActivity.this, new String[]{Manifest.permission.CAMERA}, Constants.Codes.CAMERA);
                                } else {
                                    openCamera();
                                }
                            } else {
                                openCamera();
                            }
                        } else {
                            //开始预览相关逻辑
                            //去除第一张
                            //开始预览
                            ArrayList<MediaItem> imageItems = new ArrayList<>();
                            for (int i = 0; i < picAdapter.getDataList().size(); i++) {
                                if (i != 0) {
                                    imageItems.add(picAdapter.getDataList().get(i));
                                }
                            }
                            Intent intent = new Intent(MultiSelectPhotoGridActivity.this, CommonPhotoViewActivity.class);
                            intent.putExtra(Constants.Keys.IMAGE_ITEMS, imageItems);//传递所有数据
                            intent.putExtra("position", position - 1);//确定当前是哪张图片
                            intent.putExtra("max", max);//确定上次已经选择了多少张
                            startActivityForResult(intent, Constants.Codes.REQUEST_PREVIEW);
                        }

                    }

                });
            }
        };


        //设置适配器
        photoGrid.setAdapter(picAdapter);

        //授权判断
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(MultiSelectPhotoGridActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //申请WRITE_EXTERNAL_STORAGE权限
                ActivityCompat.requestPermissions(MultiSelectPhotoGridActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constants.Codes.ALBUM);
            } else {
                loadAlbums();
            }
        } else {
            loadAlbums();
        }

    }

    private void loadAlbums() {
        //开始加载图片
        mPicLoadTask = new AsyncTask<Void, Void, List<MediaItem>>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                ImagePickerHelper.getInstance().init(mContext);
                MediaItem btnCamera = new MediaItem();
                picAdapter.getDataList().add(btnCamera);//这个是第一个按钮，拍照按钮
            }

            @Override
            protected List<MediaItem> doInBackground(Void... params) {
                return ImagePickerHelper.getInstance().getMediaItemList();
            }

            @Override
            protected void onPostExecute(List<MediaItem> imageItems) {
                if (imageItems != null && imageItems.size() > 0) {
                    picAdapter.getDataList().addAll(imageItems);
                    picAdapter.notifyDataSetChanged();
                }
            }
        };

        mPicLoadTask.execute();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.Codes.ALBUM:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    loadAlbums();
                } else {
                    // Permission Denied
                    showToast("没有权限，已退出");
                    finish();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void getBundleExtras(Bundle extras) {
        page = extras.getString("type");
    }

    /**
     * 程序入口：这个会把选择的图片返回给调用者。
     * 使用方式：在onActivityResult(...)中调用:  Bundle extras = data.getExtras();   ArrayList<MediaItem> selectedPics = extras.getParcelableArrayList(Constants.Keys.IMAGES);
     */
    @OnClick(R.id.btn_send)
    void send() {
        Intent intent = new Intent();
        Bundle extras = new Bundle();


        //从源头上压缩图片，其它调用者不用再考虑压缩问题。特别重要的一步，压缩图片。
        selectedPics = LHFileUtils.compressPhotosMediaItem(selectedPics, this);


        extras.putParcelableArrayList(Constants.Keys.IMAGES, (ArrayList<? extends Parcelable>) selectedPics);
        extras.putInt("type", 10);
        intent.putExtras(extras);
        //额外的
//        if (!StringUtils.isEmpty(page) && page.equals("dynamic")) {
//            extras.putSerializable("dynamic", getIntent().getExtras().getSerializable("dynamic"));
//            goAndFinish(PostDynamicsActivity.class, extras);
//            return;
//        }
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * 程序入口：第一个if这个会把拍照的照片返回给调用者。另外一个预览功能比较负责，内部处理，不用考虑。
     * 使用方式：在onActivityResult(...)中调用:  Bundle extras = data.getExtras();   ArrayList<MediaItem> selectedPics = extras.getParcelableArrayList(Constants.Keys.IMAGES);
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if (data == null) return;
        //这个是返回拍照的图片
        if (requestCode == Constants.Codes.CAMERA && resultCode == RESULT_OK) {
            //这里立刻扫描我们的包，更新相册
//            Intent scan = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//            Uri uri = Uri.fromFile(CzfFileUtils.getPhotoDir(mContext));
//            scan.setData(uri);
//            mContext.sendBroadcast(scan);

            //及时加入多媒体库
            new SingleMediaScanner(mContext, new File(photoUrl));

            Intent intent = new Intent();
            Bundle extras = new Bundle();
            selectedPics.clear();
            MediaItem imageItem = new MediaItem();
            imageItem.setImagePath(photoUrl);
            selectedPics.add(imageItem);

            //从源头上压缩图片，其它调用者不用再考虑压缩问题。特别重要的一步，压缩图片。
            selectedPics = LHFileUtils.compressPhotosMediaItem(selectedPics, this);


            extras.putParcelableArrayList(Constants.Keys.IMAGES, (ArrayList<? extends Parcelable>) selectedPics);
            extras.putInt("type", 10);
            intent.putExtras(extras);
            //额外的
//            if (!StringUtils.isEmpty(page) && page.equals("dynamic")) {
//                extras.putSerializable("dynamic", getIntent().getExtras().getSerializable("dynamic"));
//                goAndFinish(PostDynamicsActivity.class, extras);
//                return;
//            }
            setResult(RESULT_OK, intent);
            finish();
            return;
        }

        //预览后点击【完成】返回的
        if (requestCode == Constants.Codes.REQUEST_PREVIEW && resultCode == RESULT_OK) {
            //额外的
//            if (!StringUtils.isEmpty(page) && page.equals("dynamic")) {
//                List<MediaItem> imageItems = (ArrayList<MediaItem>) data.getSerializableExtra(Constants.Keys.IMAGES);
//                Bundle bundle = new Bundle();
//
//                //从源头上压缩图片，其它调用者不用再考虑压缩问题。特别重要的一步，压缩图片。
//                imageItems = LHFileUtils.compressPhotosMediaItem(imageItems, this);
//
//                bundle.putParcelableArrayList(Constants.Keys.IMAGES, (ArrayList<? extends Parcelable>) imageItems);
//                bundle.putInt("type", 10);
//                bundle.putSerializable("dynamic", getIntent().getExtras().getSerializable("dynamic"));
//                goAndFinish(PostDynamicsActivity.class, bundle);
//                return;
//            }
            setResult(RESULT_OK, data);
            finish();
            return;
        }
        //直接按返回键的，也就是要更新这个页面的
        if (requestCode == Constants.Codes.REQUEST_PREVIEW && resultCode == Constants.PublishTask.PICVIEW2MULITPIC_KEY) {
            //图片【预览】返回的，比较特别
            if (data.getStringExtra(Constants.PublishTask.PIC_KEY) != null) {
                if (data.getStringExtra(Constants.PublishTask.PIC_KEY).equals(Constants.PublishTask.PICVIEW2MULITSELECT_KEY)) {
                    //首先判断数据来源是相册预览还是已选择照片预览
                    boolean isPreview = data.getBooleanExtra("key", false);
                    if (isPreview) {
                        //逻辑：首先获取数据，然后拿获取的数据更新selectedPics，同时更新相册数据。
                        ArrayList<MediaItem> imageItems = (ArrayList<MediaItem>) data.getSerializableExtra(Constants.Keys.IMAGES);
                        selectedPics.clear();//清空数据
                        //遍历判断相册，确定已选择的以及未选择的
                        //TODO:这里可以考虑优化，没有找到合适的方法，但这里明显比较耗时
                        for (int j = 0; j < picAdapter.getDataList().size(); j++) {
                            //首先将所有数据状态重置为未选择
                            picAdapter.getDataList().get(j).isChoose = 1;
                            for (int i = 0; i < imageItems.size(); i++) {
                                //处理selectedPics
                                if (imageItems.get(i).isChoose == 0 && j == 0) {
                                    selectedPics.add(imageItems.get(i));
                                }
                                if (imageItems.get(i).isChoose == 0) {
                                    //处理相册：如果id相同，重置为true
                                    if (imageItems.get(i).getImageId().equals(picAdapter.getDataList().get(j).getImageId())) {
                                        picAdapter.getDataList().get(j).isChoose = 0;
                                    }
                                }
                            }
                        }
                        showSelectedNumber(selectedPics.size());
                        picAdapter.notifyDataSetChanged();
                    } else {
                        //预览相册后返回的
                        picAdapter.getDataList().clear();//首先清空
                        picAdapter.getDataList().add(new MediaItem());//首张拍照图片
                        ArrayList<MediaItem> imageItems = (ArrayList<MediaItem>) data.getSerializableExtra(Constants.Keys.IMAGES);
                        picAdapter.getDataList().addAll(imageItems);
                        picAdapter.notifyDataSetChanged();
                        //更新选择的数据
                        int nums = data.getIntExtra("selected", 0);//已选择的张数
                        showSelectedNumber(nums);
                        //清空原数据
                        selectedPics.clear();
                        for (MediaItem imageItem : imageItems) {
                            if (imageItem.isChoose == 0) selectedPics.add(imageItem);
                        }
                    }
                }
            } else {
                showToast("param error!");
            }
            return;
        }
    }

    //拍照
    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoUrl = LHFileUtils.createPhotoPath(this);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(photoUrl)));
        startActivityForResult(intent, Constants.Codes.CAMERA);
    }

    private void showSelectedNumber(int num) {

        if (num <= 0) {
            btnSend.setEnabled(false);
            txtTxt.setTextColor(Color.argb(0x7d, 0x23, 0x97, 0xf3));
            tips.setTextColor(Color.rgb(0xd2, 0xd2, 0xd2));
            numsTxt.setVisibility(View.GONE);
        } else {
            btnSend.setEnabled(true);

            numsTxt.setVisibility(View.VISIBLE);
            numsTxt.setText(num + "");

            txtTxt.setTextColor(Color.rgb(0x23, 0x97, 0xf3));
            txtTxt.setText(getResources().getText(R.string.complete));

            tips.setTextColor(Color.rgb(0x3a, 0x44, 0x4e));
            //开始预览用户选择的照片
            tips.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //开始预览
                    ArrayList<MediaItem> imageItemArrayList = (ArrayList<MediaItem>) (selectedPics);
                    Intent intent = new Intent(MultiSelectPhotoGridActivity.this, CommonPhotoViewActivity.class);
                    intent.putExtra(Constants.Keys.IMAGE_ITEMS, imageItemArrayList);//传递所有数据
                    intent.putExtra("position", 0);//确定当前是哪张图片
                    intent.putExtra("key", true);//添加标记，标记是预览页面
                    startActivityForResult(intent, Constants.Codes.REQUEST_PREVIEW);
                }
            });
        }
    }

//    @Override
//    protected int getContentViewLayoutID() {
//        return R.layout.activity_multi_select_photo_grid;
//    }


}
