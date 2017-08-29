package com.linkhand.baixingguanjia.base;

import android.app.Application;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.SDKInitializer;
import com.google.gson.reflect.TypeToken;
import com.linkhand.baixingguanjia.entity.User;
import com.linkhand.baixingguanjia.utils.SPUtils;
import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by jcy on 2017/6/12.
 */
public class MyApplication extends Application {
    private static User mUser;
    private static MyApplication inst;
    private static BDLocation location; //定位区小区


    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        SDKInitializer.initialize(this);//百度

        InitializationConfig config = InitializationConfig.newBuilder(this).connectionTimeout(30 * 1000).readTimeout(30 * 1000).retry(10).build();
        NoHttp.initialize(config);
        Logger.setDebug(true);// 开启NoHttp的调试模式, 配置后可看到请求过程、日志和错误信息。
        Logger.setTag("NoHttpSample");
        inst = MyApplication.this;
    }

    public static MyApplication getInst() {
        return inst;
    }

    public static User getUser() {
//        User user = (User) SPUtils.get(MyApplication.getInst(), "userInfor", User.class);
        User user = (User) SPUtils.get(MyApplication.getInst(), "userInfo", new TypeToken<User>() {
        }.getType());
        return user;
    }

    public static void setUser(User user) {
        SPUtils.put(MyApplication.getInst(),"userInfo",user);
        mUser = user;
    }

    public static BDLocation getLocation() {
        return location;
    }

    public static void setLocation(BDLocation location) {
        MyApplication.location = location;
    }
}
