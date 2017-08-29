package com.linkhand.bxgj.lib.utils;

import android.app.ActivityManager;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;

import java.util.List;
import java.util.Set;

/**
 * Created by JCY on 2017/8/10.
 * 说明：
 */

public class ApplicationUtils {

    //判断程序在前台还是后台
    public static boolean isApplicationBroughtToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true; //后台
            }
        }
        return false; //前台
    }


}
