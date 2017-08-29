package com.linkhand.bxgj.lib.widget.widget.picker;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;

import java.util.Date;

import cn.qqtheme.framework.picker.DatePicker;

/**
 * Created by JCY on 2017/7/17.
 * 说明：
 */

public class DatePickerView {


    //日期选择器
    public static void  onYearMonthPicker(Context context) {

        DatePicker picker = new DatePicker((Activity) context, DatePicker.YEAR_MONTH);
        picker.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL);
        picker.setWidth((int) (picker.getScreenWidthPixels() * 0.6));
        picker.setRangeStart(1990, 10, 14);
        picker.setRangeEnd(2020, 11, 11);
        Date date = new Date();
        int year = date.getYear();
        int month = date.getMonth();

        picker.setSelectedItem(year, month);
        final String[] dateStr = {""};
        picker.setOnDatePickListener(new DatePicker.OnYearMonthPickListener() {
            @Override
            public void onDatePicked(String year, String month) {

                dateStr[0] = year + "年" + month + "月";
            }
        });
        picker.show();

    }
}
