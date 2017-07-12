package com.linkhand.baixingguanjia.base;

/**
 * Created by JCY on 2017/6/19.
 * 说明：
 */

public class ConnectUrl {
    public static String testUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497862893995&di=041ae93088cea10444a795276789497a&imgtype=0&src=http%3A%2F%2Fcdn.duitang.com%2Fuploads%2Fitem%2F201603%2F17%2F20160317185030_wyefK.thumb.224_0.jpeg";
    public static String REQUESTURL = "http://192.168.1.172/wuye/server/";
//    public static String REQUESTURL = "http://wuye.177678.top/server/";


    public static String LOGIN_SENDPHONECODE = REQUESTURL + "Login/sendPhoneCode"; // 短信验证码
    public static String LOGIN_REGISTER = REQUESTURL + "Login/Loginzhu"; // 注册
    public static String LOGIN_MIMA = REQUESTURL + "Login/Loginmima"; // 密码登陆
    public static String LOGIN_LOGINCODE = REQUESTURL + "Login/Logincode "; // 动态码登陆
    public static String LOGIN_LOGINZHAO = REQUESTURL + "Login/Loginzhao"; // 密码找回


    //地区四级联动接口  地区接口   “省、 市、 区、 小区”
    public static String PUBLIC_DIQU = REQUESTURL + "Publics/DiQu"; //  停用

    //地区二级联动联动接口   区、 小区”
    public static String PUBLIC_DINGWEI = REQUESTURL + "Publics/DingWei"; //

    /**
     * 二手车模块
     */
    public static String PUBLIC_CAR_LIST = REQUESTURL + "Car/Car"; //  列表接口
    public static String PUBLIC_CAR_DETAIL = REQUESTURL + "Publics/GoodsList"; //  详情
    public static String PUBLIC_CAR_LIANXI = REQUESTURL + "Car/LianXi"; //  联系发布人接口（获取发布人信息）
    public static String PUBLIC_CAR_TYPE = REQUESTURL + "Publics/CarType"; //  二手车类型接口






}
