package com.linkhand.baixingguanjia.ui.activity.my;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linkhand.baixingguanjia.R;
import com.linkhand.baixingguanjia.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;




public class FaceApproveActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.go)
    TextView mGoTV;
    @Bind(R.id.text1)
    TextView mText1TV;

    EditText mNameET;
    EditText mCardET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_approve);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mText1TV.setText(Html
                .fromHtml(descString(), getImageGetterInstance(), null));
        mBack.setImageDrawable(getResources().getDrawable(R.mipmap.icon_back_gray));
        mTitle.setText("人脸认证");
        mTitle.setTextColor(getResources().getColor(R.color.blackText));
        LinearLayout line1 = (LinearLayout) findViewById(R.id.name_layout);
        LinearLayout line2 = (LinearLayout) findViewById(R.id.card);
        LinearLayout grayline = (LinearLayout) line2.findViewById(R.id.line);
        grayline.setVisibility(View.GONE);
        ((TextView) line1.findViewById(R.id.text_left)).setText("姓名");
        ((TextView) line2.findViewById(R.id.text_left)).setText("身份证");
        mNameET = (EditText) line1.findViewById(R.id.edittext_right);
        mNameET.setText("请输入姓名");
        mCardET = (EditText) line2.findViewById(R.id.edittext_right);
        mCardET.setText("请输入身份证号");

    }

    @OnClick({R.id.back, R.id.go})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();

                break;
            case R.id.go:
                break;
        }
    }


    /**
     * 字符串
     *
     * @return
     */
    private String descString() {
        return "<img src='" + R.mipmap.icon_dian
                + "'/>"
                + getResources().getString(R.string.string2)
                + "<br/>"
                + "<img src='" + R.mipmap.icon_dian
                + "'/>"
                + getResources().getString(R.string.string3)
                + "";
    }


    /**
     * ImageGetter用于text图文混排
     *
     * @return
     */
    public Html.ImageGetter getImageGetterInstance() {
        Html.ImageGetter imgGetter = new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
//                int fontH = (int) (getResources().getDimension(
//                        R.dimen.textSizeMedium) * 1.5);
                int fontH = (int) (getResources().getDimension(
                        R.dimen.x14));
                int id = Integer.parseInt(source);
                Drawable d = getResources().getDrawable(id);
                int height = fontH;
                int width = (int) ((float) d.getIntrinsicWidth() / (float) d
                        .getIntrinsicHeight()) * fontH;
                if (width == 0) {
                    width = d.getIntrinsicWidth();
                }
                d.setBounds(0, 0, width, height);
                return d;
            }
        };
        return imgGetter;
    }
}
