package com.linkhand.baixingguanjia.receiver;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.linkhand.baixingguanjia.base.BaseAppManager;
import com.linkhand.baixingguanjia.base.MyApplication;
import com.linkhand.baixingguanjia.entity.EventFlag;
import com.linkhand.baixingguanjia.ui.activity.LoginActivity;
import com.linkhand.baixingguanjia.ui.activity.MessageActivity;
import com.linkhand.baixingguanjia.utils.JPushUtils;
import com.linkhand.baixingguanjia.utils.SPUtils;
import com.linkhand.bxgj.lib.utils.ApplicationUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 JPushReceiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class JPushReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        //Log.d(TAG, "[JPushReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[JPushReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[JPushReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            /**
             * 用户被举报强制下线处理
             */
            offlineUser(bundle.getString(JPushInterface.EXTRA_EXTRA), context);


        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[JPushReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_MESSAGE);

            sentCount(bundle.getString(JPushInterface.EXTRA_EXTRA));


            //*****************插入数据到数据库
//			initdata(context,bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE),bundle.getString(JPushInterface.EXTRA_ALERT));


        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[JPushReceiver] 用户点击打开了通知");

            //打开自定义的Activity
            Intent i = new Intent(context, MessageActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(i);

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[JPushReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[JPushReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            Log.d(TAG, "[JPushReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    /**
     * 用户被举报下线处理
     */
    private void offlineUser(String var, final Context context) {
        try {
            JSONObject json = new JSONObject(var);
            String code = json.getString("code");
            String info = json.getString("data");
            if (!code.equals("200")) {
                return;
            }
            //先判断程序是在后台还是在前台  true后台 false前台
            boolean flag = ApplicationUtils.isApplicationBroughtToBackground(context);
            if (flag) {
                SPUtils.put(context, "userInfo", null);
                MyApplication.setUser(null);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("提示");
                builder.setMessage("您因涉嫌："+info+"问题，已被平台禁用！");
                builder.setCancelable(false);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BaseAppManager.getInstance().clear();
                        SPUtils.put(context, "userInfo", null);
                        MyApplication.setUser(null);
                        JPushUtils.jPushMethod(context, "", null);
                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });

                AlertDialog alterDialog = builder.create();
                alterDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                alterDialog.show();
//                BaseAppManager.getInstance().clear();
//                SPUtils.put(context, "userInfo", null);
//                MyApplication.setUser(null);
//                JPushUtils.jPushMethod(context, "", null);
//                Intent intent = new Intent(context, LoginActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //发送未读数量
    private void sentCount(String var) {
        try {
            JSONObject jsonObject = new JSONObject(var);
            int count = jsonObject.getInt("count");
            EventBus.getDefault().post(new EventFlag("jpushMessageCount", count));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
