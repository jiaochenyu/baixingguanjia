package com.linkhand.baixingguanjia.base;

import android.app.Application;

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
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        InitializationConfig config = InitializationConfig.newBuilder(this).connectionTimeout(300*1000).build();
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
        User user = (User) SPUtils.get(MyApplication.getInst(), "userInfor", new TypeToken<User>(){}.getType());
        return user;
    }

    public static void setUser(User user) {
        mUser = user;
    }
}
