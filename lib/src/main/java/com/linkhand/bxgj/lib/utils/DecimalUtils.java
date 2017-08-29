package com.linkhand.bxgj.lib.utils;

import java.text.DecimalFormat;

/**
 * Created by JCY on 2017/8/15.
 * 说明：
 */

public class DistanceUtils {
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
}
