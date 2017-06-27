package com.linkhand.kits;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

/**
 * Created by JCY on 2017/6/16.
 * 说明： 富文本工具类
 * 改变text 颜色
 */

public class SpannableStringUtils {

    //236件已售 仅剩16小时
    public SpannableStringBuilder setSpann(String var, String b, String e, int color) {
        SpannableStringBuilder spannableStringBuilder1 = new SpannableStringBuilder(var);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(color);
        int start = var.indexOf(b);
        int end = var.indexOf(e) + 1;
        spannableStringBuilder1.setSpan(foregroundColorSpan, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return spannableStringBuilder1;
    }

    public SpannableStringBuilder setSpann(String var, int b, int e, int color) {
        SpannableStringBuilder spannableStringBuilder1 = new SpannableStringBuilder(var);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(color);
//        int start = var.indexOf(b);
//        int end = var.indexOf(e) + 1;
        spannableStringBuilder1.setSpan(foregroundColorSpan, b, e, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return spannableStringBuilder1;
    }


//    //设置字体大小
//    textview3 = (TextView) findViewById(R.id.text3);
//    SpannableStringBuilder spannableStringBuilder3 = new SpannableStringBuilder("Android");
//    AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(30);
//spannableStringBuilder3.setSpan(absoluteSizeSpan, 0, 3, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//textview3.setText(spannableStringBuilder3);


////设置字体(加粗斜体)
//    textview4 = (TextView) findViewById(R.id.text4);
//    SpannableStringBuilder spannableStringBuilder4 = new SpannableStringBuilder("Android");
//    StyleSpan styleSpan = new StyleSpan(Typeface.BOLD_ITALIC);
//spannableStringBuilder4.setSpan(styleSpan, 0, 3, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//textview4.setText(spannableStringBuilder4);

//    设置下滑线
//    textview5 = (TextView) findViewById(R.id.text5);
//    SpannableStringBuilder spannableStringBuilder5 = new SpannableStringBuilder("Android");
//    UnderlineSpan underlineSpan = new UnderlineSpan();
//spannableStringBuilder5.setSpan(underlineSpan, 0, 3, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//textview5.setText(spannableStringBuilder5);


//    //设置多种样式
//    textview7 = (TextView) findViewById(R.id.text7);
//    SpannableStringBuilder spannableStringBuilder7 = new SpannableStringBuilder("Android");
//spannableStringBuilder7.setSpan(foregroundColorSpan, 0, 3, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//spannableStringBuilder7.setSpan(backgroundColorSpan, 0, 3, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//spannableStringBuilder7.setSpan(underlineSpan, 0, 3, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//spannableStringBuilder7.setSpan(absoluteSizeSpan, 3, 6, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//spannableStringBuilder7.setSpan(strikethroughSpan, 3, 6, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//textview7.setText(spannableStringBuilder7);


// 点击事件
//    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("Android");
//
//spannableStringBuilder.setSpan(
//        new ClickableSpan() {
//        @Override
//        public void onClick(View widget) {
//            //do something
//        }
//
//        @Override
//        public void updateDrawState(TextPaint ds) {
//            //设置一些样式
//            //ds.setUnderlineText(false);
//            //ds.setColor(color);
//        }
//    }, startIndex, endIndex,
//    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//);
}
