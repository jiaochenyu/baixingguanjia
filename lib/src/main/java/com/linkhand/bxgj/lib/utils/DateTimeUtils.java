package com.linkhand.bxgj.lib.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tiangongyipin on 16/3/7.
 */
public class DateTimeUtils {

    /**
     * 返回当前年月日 时分秒
     */
    public static String getDateTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String str = simpleDateFormat.format(date);
        return str;
    }

    public static Date parse(String dateStr, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        //判断是时间
        if (dateStr == null) {
            return null;
        }
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String format(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static String format(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return sdf.format(date);
    }
    public static String format(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }


    /**
     * 两个时间点的间隔时长（分钟）
     *
     * @param before 开始时间
     * @param after  结束时间
     * @return 两个时间点的间隔时长（分钟）
     */
    public static long compareMin(Date before, Date after) {
        if (before == null || after == null) {
            return 0l;
        }
        long dif = 0;
        if (after.getTime() >= before.getTime()) {
            dif = after.getTime() - before.getTime();
        } else if (after.getTime() < before.getTime()) {
            dif = after.getTime() + 86400000 - before.getTime();
        }
        dif = Math.abs(dif);
        return dif / 60000;
    }

    /**
     * 活动倒计时时间
     * 计算方式 结束时间 - 当前网络时间/当前时间
     */
    public static long compareTime(long endtime) {
        long time = 0;
        Date date = new Date();
        long time1 = date.getTime();
        time = endtime - time1;
        return time;
    }

    /**
     * 获取网络时间
     *
     * @return
     */
    public static Date getNetCurrentTime() {
        Date date = null;
        try {
            URL url = null;//取得资源对象
            url = new URL("http://www.bjtime.cn");
            URLConnection uc = url.openConnection();//生成连接对象
            uc.connect(); //发出连接
            long ld = uc.getDate(); //取得网站日期时间
            date = new Date(ld); //转换为标准时间对象
            //分别取得时间中的小时，分钟和秒，并输出
//            System.out.print(date.getHours() + "时" + date.getMinutes() + "分" + date.getSeconds() + "秒");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return date;
    }


}
