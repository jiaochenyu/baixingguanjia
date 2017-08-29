package com.linkhand.baixingguanjia.base;

/**
 * Created by JCY on 2017/6/19.
 * 说明：
 */

public class ConnectUrl {
//    public static String REQUESTURL = "http://192.168.1.172/wuye/server/";
//    public static String REQUESTURL_IMAGE = "http://192.168.1.172/wuye";

    /**
     * 远程服务器
     **/
    public static String REQUESTURL_IMAGE = "http://wuye.177678.top";
    public static String REQUESTURL = "http://wuye.177678.top/server/";
    /**
     * 本机
     **/
//    public static String REQUESTURL = "http://192.168.1.172/server/";
    //    public static String REQUESTURL = "http://192.168.1.100/server/";
    //领航
//    public static String REQUESTURL_IMAGE = "http://192.168.1.151";
//    public static String REQUESTURL = "http://192.168.1.151/server/";

    public static String LOGIN_SENDPHONECODE = REQUESTURL + "Login/sendPhoneCode"; // 短信验证码
    public static String LOGIN_REGISTER = REQUESTURL + "Login/Loginzhu"; // 注册
    public static String LOGIN_MIMA = REQUESTURL + "Login/Loginmima"; // 密码登陆
    public static String LOGIN_LOGINCODE = REQUESTURL + "Login/Logincode "; // 动态码登陆
    public static String LOGIN_LOGINZHAO = REQUESTURL + "Login/Loginzhao"; // 密码找回


    //地区四级联动接口  地区接口   “省、 市、 区、 小区”
    public static String PUBLIC_DIQU = REQUESTURL + "Publics/DiQu"; //

    //地区二级联动联动接口   区、 小区”
    public static String PUBLIC_DINGWEI = REQUESTURL + "Publics/DingWei"; //

    /**
     * 二手车模块
     */
    public static String PUBLIC_CAR_LIST = REQUESTURL + "Car/Car"; //  列表接口
    public static String PUBLIC_CAR_DETAIL = REQUESTURL + "Publics/GoodsList"; //  详情
    public static String PUBLIC_CAR_LIANXI = REQUESTURL + "Car/LianXi"; //  联系发布人接口（获取发布人信息）
    public static String PUBLIC_CAR_TYPE = REQUESTURL + "Publics/CarType"; //  二手车类型接口

    public static String PUBLIC_CAR_RELEASE = REQUESTURL + "Car/Release"; //  二手车发布


    /**
     * 收藏接口
     */
    public static String PUBLIC_COLLECT = REQUESTURL + "Publics/Collect"; //  收藏

//    public static String PUBLIC_IS_COLLECT = REQUESTURL + "Publics/Collection"; //  判断当前用户是否收藏该物品
//    public static String PUBLIC_CANCEL_COLLECT = REQUESTURL + "Publics/Cancel"; //  取消收藏接口

    /**
     * 图片上传
     */
    public static String PUBLIC_UPLOAD_IMAGE = REQUESTURL + "Publics/Upload"; //  图片上传

    /***个人中心****/
    public static String USER_INFO_UPDATE = REQUESTURL + "User/UserUpdate"; //  个人信息修改
    public static String USER_INFO_ADD_XIAOQU = REQUESTURL + "User/AddCell"; //  用户添加小区接口
    public static String USER_INFO_UPDATE_XIAOQU = REQUESTURL + "User/UpdateCell"; //  用户修改小区接口


    /**
     * 首页
     */
    public static String PRODUCT_INDEX = REQUESTURL + "product/index"; //  首页
    public static String PRODUCT_YUGAO = REQUESTURL + "product/yugao"; //  预告
    public static String PRODUCT_GOODS_DETILES = REQUESTURL + "product/detiles"; //  商品详情页面接口
    public static String PRODUCT_INDEX_RELEASE = REQUESTURL + "Publics/indexRelease"; //  首页发布里模块接口


    /**
     * 房产
     */
    public static String PUBLIC_HOUSE_LIST = REQUESTURL + "House/House"; //  房产列表
    public static String PUBLIC_HOUSE_RELEASE = REQUESTURL + "House/Release"; //  发布

    /**
     * 家政
     */
    public static String PUBLIC_HOUSEKEEEPING_TYPE = REQUESTURL + "Publics/HousekeepingType"; //  家政类型接口
    public static String PUBLIC_HOUSEKEEEPING_LIST = REQUESTURL + "Housekeeping/Housekeeping"; //  家政列表
    public static String PUBLIC_HOUSEKEEEPING_RELEASE = REQUESTURL + "Housekeeping/Release"; //  家政发布

    /**
     * 招聘
     */
    public static String PUBLIC_RECRUIT_INDUSTRY_TYPE = REQUESTURL + "Publics/IndustryType"; //  行业分类接口
    public static String PUBLIC_RECRUIT_POISTION_TYPE = REQUESTURL + "Publics/PositionType"; //  职位分类接口
    public static String PUBLIC_RECRUIT_LIST = REQUESTURL + "Recruit/Recruit"; //  列表
    public static String PUBLIC_RECRUIT_RELEASE = REQUESTURL + "Recruit/Release"; //  发布
    public static String PUBLIC_RECRUIT_SELECT_FULI = REQUESTURL + "Publics/Welfare"; //  发布选择福利
    /**
     * 教育
     */
    public static String PUBLIC_EDUCATION_SORT_TYPE = REQUESTURL + "Publics/TutorType"; //  家教分类接口
    public static String PUBLIC_EDUCATION_OBJECT_TYPE = REQUESTURL + "Publics/ObjectType"; //  家教对象接口
    public static String PUBLIC_EDUCATION_LIST = REQUESTURL + "Tutor/Tutor"; //  教育列表接口
    public static String PUBLIC_EDUCATION_RELEASE = REQUESTURL + "Tutor/Release"; //  教育列表接口
    /**
     * /**
     * 闲置物品
     */
    public static String PUBLIC_IDLE_GOODS_TYPE = REQUESTURL + "Publics/GoodsType"; //  闲置物品分类接口
    public static String PUBLIC_IDLE_GOODS_LIST = REQUESTURL + "Goods/Goods"; //  闲置物品列表
    public static String PUBLIC_IDLE_GOODS_RELEASE = REQUESTURL + "Goods/Release"; //  闲置物品列表

    /**
     * 公益
     */
    public static String PUBLIC_WELFARE_LIST = REQUESTURL + "Welfare/Welfare"; //
    public static String PUBLIC_WELFARE_RELEASE = REQUESTURL + "Welfare/Release"; //


    /**
     * 购买流程
     */
    public static String PUBLIC_PROUCT_SUBMIT_ORDER = REQUESTURL + "product/purchase"; //下单
    public static String PUBLIC_PROUCT_NOTICE_REMIND = REQUESTURL + "product/remind"; //预告 提醒接口
    public static String PUBLIC_PROUCT_GET_DEF_ADDRESS = REQUESTURL + "GoodsOrder/Address"; //获取默认地址
    public static String PUBLIC_PROUCT_UPDATE_DEF_ADDRESS = REQUESTURL + "GoodsOrder/SaveAddress"; //修改默认地址
    public static String PUBLIC_PROUCT_GET_ADDRESS_LIST = REQUESTURL + "GoodsOrder/SelectAddress"; //获取地址列表
    public static String PUBLIC_PROUCT_ALIPAY = REQUESTURL + "Pay/alipayAppPay"; //支付宝付款

    /**
     * 我的模块
     */
    public static String PUBLIC_MY_COLLECT_LIST = REQUESTURL + "User/MyCommodity"; //我的收藏
    public static String PUBLIC_MY_EVALUATE_LIST = REQUESTURL + "User/Evaluate"; //我的评价
    public static String PUBLIC_MY_RELEASE_LIST = REQUESTURL + "User/MyRelease"; //我的发布
    public static String PUBLIC_MY_RELEASE_DELETE = REQUESTURL + "User/Delete"; //我的发布删除
    public static String PUBLIC_MY_RELEASE_NOCHANGE_UPDATE = REQUESTURL + "User/Release"; //我的发布重新发布 （没有修改）
    public static String PUBLIC_MY_RELEASE_UPDATE = REQUESTURL + "User/Edit"; //编辑发布 （修改）
    public static String PUBLIC_MY_RELEASE_OFFLINE = REQUESTURL + "User/Undercarriage"; //下线 （修改）
    public static String PUBLIC_MY_FEEDBACK = REQUESTURL + "User/MyFeedback"; //我的反馈
    public static String PUBLIC_MY_FEEDBACK_AGAIN = REQUESTURL + "Publics/ZaiFeedback"; //再次反馈
    public static String PUBLIC_MY_APPOINTMENT_LIST = REQUESTURL + "User/MyBespoke"; //我的预约
    public static String PUBLIC_MY_COMMENT = REQUESTURL + "User/Comment"; //评价
    public static String PUBLIC_MY_ORDER = REQUESTURL + "GoodsOrder/index"; //我的订单
    public static String PUBLIC_RETURN_ORDER = REQUESTURL + "product/return_order"; //退款
    public static String PUBLIC_CANCEL_ORDER = REQUESTURL + "GoodsOrder/cancelOrder"; //取消订单
//    public static String PUBLIC_CANCEL_ORDER = REQUESTURL + "GoodsOrder/cancelOrder"; //取消订单


    /**
     * 服务详情 不包括商品
     */
    public static String PUBLIC_SERVICE_DTEAIL = REQUESTURL + "Publics/GoodsList"; //服务详情
    public static String PUBLIC_REPORT = REQUESTURL + "User/Report"; //举报

    /**
     * 预约接口
     */
    public static String PUBLIC_SERVICE_APPOINTMENT = REQUESTURL + "Publics/Bespoke"; //预约
    public static String PUBLIC_IS_APPOINTMENT = REQUESTURL + "Publics/sfBespoke"; //判断用户是否预约

    /**
     * 消息
     */
    public static String PUBLIC_MYNEWS_LIST = REQUESTURL + "User/MyNews"; //消息列表
    public static String PUBLIC_MYNEWS_COUNT = REQUESTURL + "User/UnreadMessage"; //未读消息数量
    public static String PUBLIC_MYNEWS_CHANGE = REQUESTURL + "User/ReadNews"; //改变消息状态
    public static String PUBLIC_DELETE_MYNEWS = REQUESTURL + "User/DelNews"; //删除消息

    /**
     * 自动下线
     */
    public static String PUBLIC_AUTO_OFFLINE = REQUESTURL + "Public/Offline"; //下线

    /**
     * 充值
     */
    public static String PUBLIC_PAY_ALIPAY_SILVER = REQUESTURL + "Pay/alipayRechargePay"; //支付宝白银充值
    public static String PUBLIC_SILVER_DETAIL_LIST = REQUESTURL + "User/SilverList"; //白银收支明细
    public static String PUBLIC_GOLD_DETAIL_LIST = REQUESTURL + "User/GoldList"; //黄金收支明细
    public static String PUBLIC_GOLD_PAY = REQUESTURL + "User/wuyefei"; //黄金抵扣物业费
    /**
     * 分类我的小区
     */
    public static String PUBLIC_MYNEIGHBORHOOD = REQUESTURL + "Publics/MyNeighborhood"; //我的小区接口
    public static String PUBLIC_FEEDBACK = REQUESTURL + "Publics/Feedback"; //我的小区反馈接口


    /**
     * 通用
     */

    public static String PUBLIC_COMMON_LIST = REQUESTURL + "Common/Common"; //通用列表
    public static String PUBLIC_COMMON_RELEASE = REQUESTURL + "Common/Release"; //通用发布

}
