package com.linkhand.bxgj.lib.utils;

import java.text.DecimalFormat;

/**
 * Created by JCY on 2017/8/15.
 * 说明：
 */

public class DecimalUtils {
    /**
     * 单位转换
     *
     * @return
     */
    public static String unitConversion(double v) {
        if (v > 1000) {
            DecimalFormat df = new DecimalFormat("#.00");
            return df.format(v / 1000) + "km";
        } else {
            DecimalFormat df = new DecimalFormat("#.00");
            return df.format(v) + "m";
        }
    }

    /**
     * 格式转换
     *
     * @param v
     * @return
     */
    public static String decimalFormat(float v) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p = decimalFormat.format(v);//format 返回的是字符串
        return p;
    }

    public static String decimalFormat(double v) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p = decimalFormat.format(v);//format 返回的是字符串
        return p;
    }
}
