package com.linkhand.baixingguanjia.base;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by jcy on 2017/6/12.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
