package com.linkhand.baixingguanjia.base;

public class Constants {

    public static class RespCode {
        public static final int TOKEN_INVALID = 302;
        public static final int UPDATE = 102;
        public static final int MUST_UPDATE = 103;
    }

    public static class Keys {
        public static final String MESSAGEREMIND = "MESSAGEREMIND";
        public static final String DISTURB = "DISTURB";
        public static final String LANGUAGE = "LANGUAGE";
        public static final String USER = "USER";
        public static final String RONG = "RONG";
        public static final String PHONE = "PHONE";
        public static final String CROP = "CROP";
        public static final String IMAGE_ITEMS = "KEY_IMAGE_ITEMS";
        public static final String TYPE = "TYPE";
        public static final String IMAGES = "IMAGES";
        public static final String CONTACTS = "CONTACTS";
        public static final String LABEL = "LABEL";
        public static final String INTENT_WEB_KEY = "INTENT_WEB_KEY";
        public static final String KEY_RING_TONE = "ringtone";
        public static final String KEY_NEW_MAIL_VIBRATE = "newmailvibrate";
        public static final String QRCODE_VALUE = "QRCODE_VALUE";
    }

    public static class ThirdParty {
        public static final String WECHAT_ID = "wx57dbe72111b4b295";
        public static final String WECHAT_APPSECRET = "f6673d5524b0998d0cf79dcb0076779c";

        public static final String SINA_WEIBO_APPKEY = "2933916621";
        public static final String SINA_WEIBO_APPSECRET = "52438a9cc416a136cc911d916e5d7885";

        public static final String QQ_APPID = "1105505769";
        public static final String QQ_APPKEY = "SgWfECbax7isVlzC";


        public static final String MOB_APPKEY = "14d9856e11a4e";
        public static final String MOB_APPSECRET = "1b7d9ebd49635c46ca24aa32569c75e4";

        public static final String OL_PACKAGE = "com.ymw.officeanywhere";
        public static final String OL_CKL = "com.bap.activity.WelcomeActivity";

        public static final String ZL_PACKAGE = "com.example.zhiliaohaoidian";
        public static final String ZL_CKL = "com.example.zhiliao.activity.LoginByUserActivity";

//        public static final String OL_PACKAGE="com.ymw.officeanywhere";
//        public static final String OL_CKL="com.bap.activity.MainActivity";
//
//        public static final String ZL_PACKAGE="com.example.zhiliaohaoidian";
//        public static final String ZL_CKL="com.example.zhiliao.MainActivity";

    }


    public static class Codes {

        public static final int ADDLABEL = 0x0001;
        public static final int ADDMEMBER = 0x0002;
        public static final int EDITLABEL = 0x0003;
        public static final int BINDPHONE = 0x0004;
        public static final int RONG = 0x0005;
        public static final int ADDFRIEND = 0x0006;
        public static final int ALBUM = 0x0007;
        public static final int VEIDO = 0x0047;
        public static final int CAMERA = 0x0008;
        public static final int LOCATION = 0x0009;
        public static final int SEE = 0x0010;
        public static final int REMIND = 0x0011;
        public static final int PARTSEE = 0x0012;
        public static final int PARTNOTSEE = 0x0013;
        public static final int ADD = 0x0014;
        public static final int UPDATE = 0x0015;
        public static final int QRCODE = 0x0016;
        public static final int WEB_ACTIVITY = 0x0017;
        public static final int LOCAL_CONTACTS = 0x0018;
        public static final int CP = 0x0019;
        public static final int EDIT = 0x0030;
        public static final int RECORD_AUDIO = 0x0031;
        public static final int READ_EXTERNAL_STORAGE = 0x0032;


        public static final int REQUEST_PREVIEW = 0x020;

        // 更多应用先关标记
        public static final int UNDOWNLOAD = 0x021;
        public static final int DOWNLOADED = 0x022;
        //三个显示优先级
        public static final int FIRSTSHOW = 20;//首页显示，已添加
        public static final int SECONDSHOW = 10;//更多应用中显示，已添加
        public static final int THIRDSHOW = 0x025;//已下载未添加
        public static final int FOURSHOW = 0x026;//未下载未添加

    }


    public static class Mode {
    }

    public static class Gender {
    }

    public static class Urls {
//        外网地址,ll
        public static final String API_URL = "http://61.155.169.45:8081/CzfService/";
//        //图片链接地址
        public static final String PIC_URL = "http://61.155.169.45:8081/SZCZF/";

//        //内网地址
//        public static final String API_URL = "http://192.168.0.179:8080/CZF/";
////        图片链接地址
//        public static final String PIC_URL = "http://192.168.0.179:8080/CZF/";


        //默认头像地址
        public static final String DEFAULT_AVATAT = "avatar_default.png";
        //版本升级地址
        public static String VERSION_DOWNLOAD = API_URL + "app-release.apk";
        //哦了下载地址
        public static final String OL_DOWNLOAD = API_URL + "officeOnline.apk";
        //知了下载地址
        public static final String ZL_DOWNLOAD = API_URL + "studyOnline.apk";

        /**
         * 客户端文件夹结构：CZF/files/，
         * 下包括video、photo（采用CZF拍照的照片都会保存到该目录下，不会删除）、logs、temp（临时文件夹）。
         * 录音和压缩的图片均属于临时文件，每次重新APP进程，都会清空temp文件夹。
         */
        //客户端存放根路径。
        public static final String CZF_ROOT_URL = "CZF/files/";
        //客户端缓存文件路径。下次启动【APP进程】时会删除所有该文件夹下所有文件。不要在该文件夹放需要长久的文件。
        public static final String CZF_TEMP_URL = CZF_ROOT_URL + "temp/";


    }

    //
    //FIXME:临时的文件上传，后期删除。
    public static class TempNoHttpUtils {
//        //外网地址
       public static final String API_URL = "http://61.155.169.45:8081/CzfService/";
        //内网地址
//       public static final String API_URL = "http://192.168.0.179:8080/CZF/";
        public static final String API_uploadFiles = API_URL + "fileManagerAction_filesUpload";


        //定义what值，类似于定义API,用户返回结果使用
        public static final int website_addProductPic = 0x001;
        public static final int website_addResultPic = 0x002;

    }

    public static class CaptchaType {

        public static final String REGISTER = "register";
        public static final String FORGET = "forget";
        public static final String BIND = "bind";
    }

    //快速发布相关
    public static class PublishTask {
        public static final String TASK_TYPE = "TASK_TYPE";//判断是定向任务还是悬赏任务；默认0：悬赏任务；1：定向任务
        public static final String PUBLISH_TYPE = "PUBLISH_TYPE";
        //标记何种发布方式：0：发布悬赏任务；1：发布定向任务；2：发布呼叫服务；3：发布通知公告。默认：0
        public static final int PUBLISH_TYPE_0 = 0;
        public static final int PUBLISH_TYPE_1 = 1;
        public static final int PUBLISH_TYPE_2 = 2;
        public static final int PUBLISH_TYPE_3 = 3;

        //图片预览跳转到相册的结果码
        public static final int PICVIEW2MULITPIC_KEY = 4;//确定是图片预览到添加图片页面

        public static final String CALL2ORGAN_KEY = "CALL2ORGAN_KEY";//确定是呼叫服务唤出的服务对象
        public static final String CALL2VIDEO_KEY = "CALL2VIDEO_KEY";//确定是呼叫服务唤出的添加视频
        public static final String CALL2SUMMARY_KEY = "CALL2SUMMARY_KEY";//确定是呼叫服务唤出的添加描述

        public static final String NOTIC2TOPIC_KEY = "NOTIC2TOPIC_KEY";//确定是通知公告唤出的通知主题
        public static final String NOTIC2DETAIL_KEY = "NOTIC2DETAIL_KEY";//确定是通知公告唤出的详细内容
        public static final String NOTIC2ORGAN_KEY = "NOTIC2ORGAN_KEY";//确定是通知公告唤出的选择对象，隐藏下面三个按钮

        public static final String PIC_KEY = "PIC_KEY";//key
        public static final String PICVIEW2MULITSELECT_KEY = "PICVIEW2MULITSELECT_KEY";//确定是图片预览到相册
        public static final String PICVIEW2ADDPIC_KEY = "PICVIEW2MULITSELECT_KEY";//确定是图片预览到添加图片页面


        //投票相关
        // 添加投票内容对应的偏好设置的key
        public static final String ADD_VOTE_KEY = "ADD_VOTE_KEY";
        //添加题目
        public static final String ADD_TITLE_KEY = "ADD_TITLE_KEY";


        //以下开始标记快速发布多个帧布局，主要用于内容的空与否的判断
        public static final int TASK_SUMMARY_FLAG = 0x001;
        public static final int ADD_PIC_FLAG = 0x002;
        public static final int ADD_VOICE_FLAG = 0x003;
        public static final int ADD_VIDEO_FLAG = 0x004;
        public static final int ADD_MONEY_FLAG = 0x005;
        public static final int ADD_ORGAN_FLAG = 0x006;
        public static final int ADD_SIGN_FLAG = 0x007;
        public static final int ADD_STAR_FLAG = 0x008;
        public static final int ADD_VOTE_FLAG = 0x009;
    }

    // 我的草稿相关
    public static class MyDraft {
        //跳转相关
        public static final String MINE2MYDRAFT_KEY = "MINE2MYDRAFT_KEY";//从我的页面跳转到我的草稿
        public static final int MINE2MYDRAFT_FLAG = 0x010;//从我的页面跳转到我的草稿
    }

    // 我的网站相关
    public static class MyWebSite {
        //个人资料修改
        public static final String PROFILE2EDIT_KEY = "PROFILE2EDIT_KEY";//从个人资料跳转到修改界面的key
        public static final int EDIT_BRIEF_FLAG = 0x011;//修改简介
        public static final int EDIT_SCPOE_FLAG = 0x012;//修改经营范围
        public static final int EDIT_PHONE_FLAG = 0x013;//修改电话

        //添加产品类别
        public static final String PRODUCTTYPE2EDIT_KEY = "PRODUCTTYPE2EDIT_KEY";//从编辑产品类别跳转到修改界面的key
        public static final int PRODUCTTYPE_ADD_FLAG = 0x014;//add
        public static final int PRODUCTTYPE_UPDATE_FLAG = 0x015;//update
        public static final String PRODUCTTYPE_DATA_KEY = "PRODUCTTYPE_DATA_KEY";//获取数据的key
        public static final int PRODUCT2UPDATE_REQCODE = 0x016;//reslut请求码
        public static final int ADD2PRODUCT_REQCODE = 0x017;//获取reslut结果码
        public static final int UPDATE2PRODUCT_REQCODE = 0x018;//获取reslut结果码
        //修改相关
        public static final String INTENT_KEY = "INTENT_KEY";//INTENT_KEY
        public static final int UPDATE_FLAG = 0x019;//获取reslut结果码
        // 该部分的偏好设置
        // 添加产品类别的key
        public static final String ADD_PRODUCTTYPE_KEY = "ADD_PRODUCTTYPE_KEY";
        //标记：成果展示（与产品相区别）
        public static final int RELUSTSHOW_FLAG = 0x020;//
        public static final String RELUSTSHOW_KEY = "RELUSTSHOW_KEY";//RELUSTSHOW_KEY
        //标记：添加分享
        public static final int ADDSHARE_FLAG = 0x022;//

        //**案例相关**//
        //标记：案例展示
        public static final int CASESHOW_FLAG = 0x021;//
        public static final String CASESHOW_KEY = "CASESHOW_KEY";//CASESHOW_KE
        //案例修改
        public static final int CSAE_UPDATE_FLAG = 0x023;//
        //标记是哪一个项目
        public static final int CSAE_TEXT_FLAG = 10;//
        public static final int CSAE_PIC_FLAG = 20;//
        public static final int CSAE_VIDEO_FLAG = 30;//
        public static final int CSAE_LINK_FLAG = 40;//
        //about update
        public static final String INTENT_DATA = "INTENT_DATA";//INTENT_KEY
        public static final String INTENT_CASE_UPDATE = "INTENT_CASE_UPDATE";//INTENT_KEY
        public static final int INTENT_UPDATE_KEY = 0x028;//


        //**以下标记区分荣誉资历、产品介绍、成果展示、案例展示**//
        public static final int ADD_PRODUCT_KEY = 0x029;//
        public static final int ADD_RESULT_KEY = 0x030;//
        public static final int ADD_CASE_KEY = 0x031;//
        public static final int ADD_HONOR_KEY = 0x032;//

        public static final int UPDATE_PRODUCT_KEY = 0x033;//
        public static final int UPDATE_RESULT_KEY = 0x034;//
        public static final int UPDATE_CASE_KEY = 0x035;//
        public static final int UPDATE_HONOR_KEY = 0x036;//

        public static final int IS_PRODUCT_KEY = 0x037;//
        public static final int IS_RESULT_KEY = 0x038;//
        public static final int IS_CASE_KEY = 0x039;//
        public static final int IS_HONOR_KEY = 0x040;//


        public static final int GUEST = 0x041;//标记是访客
        public static final String GUEST_INTENT = "GUEST_INTENT";//标记是访客
    }

    public static class ChatManager {
        //定义消息类型
        public static final String MSGTYPE_TEXT_RIGHT = "MSGTYPE_TEXT_RIGHT";//文字
        public static final String MSGTYPE_ICON_RIGHT = "MSGTYPE_ICON_RIGHT";//表情
        public static final String MSGTYPE_PIC_RIGHT = "MSGTYPE_PIC_RIGHT";//图片
        public static final String MSGTYPE_VOICE_RIGHT = "MSGTYPE_VOICE_RIGHT";//语音
        public static final String MSGTYPE_VEDIO_RIGHT = "MSGTYPE_VEDIO_RIGHT";//视频
        public static final String MSGTYPE_MYRELEASE_RIGHT = "MSGTYPE_MYRELEASE_RIGHT";//我的发布
        public static final String MSGTYPE_MYPARTIN_RIGHT = "MSGTYPE_MYPARTIN_RIGHT";//我的参与
        public static final String MSGTYPE_MYFOCUS_RIGHT = "MSGTYPE_MYFOCUS_RIGHT";//我怕的关注

        public static final String MSGTYPE_TEXT_LEFT = "MSGTYPE_TEXT_LEFT";//文字
        public static final String MSGTYPE_ICON_LEFT = "MSGTYPE_ICON_LEFT";//表情
        public static final String MSGTYPE_PIC_LEFT = "MSGTYPE_PIC_LEFT";//图片
        public static final String MSGTYPE_VOICE_LEFT = "MSGTYPE_VOICE_LEFT";//语音
        public static final String MSGTYPE_VEDIO_LEFT = "MSGTYPE_VEDIO_LEFT";//视频
        public static final String MSGTYPE_MYRELEASE_LEFT = "MSGTYPE_MYRELEASE_LEFT";//我的发布
        public static final String MSGTYPE_MYPARTIN_LEFT = "MSGTYPE_MYPARTIN_LEFT";//我的参与
        public static final String MSGTYPE_MYFOCUS_LEFT = "MSGTYPE_MYFOCUS_LEFT";//我怕的关注

        //图文消息相关
        public static final String MSGTYPE_TUWEN_SINGLE = "MSGTYPE_TUWEN_SINGLE";//单图文
        public static final String MSGTYPE_TUWEN_MULIT = "MSGTYPE_TUWEN_MULIT";//多图文
    }

    public static class FileManager {
        public final static String rootPath = "D:/CZF";
        public final static String rootAccessPath = "/files";// 访问，这是根路径.不要项目名称
        // 图片缩略图前缀
        public final static String thumb_pre = "thumb_";
    }


    public static class PageRequest {
        public final static int TOTAL = 10;
    }

    public static class DeleteAction {
        //针对exper为academy的成员
        public final static String DELETE_ACTIVITY_5_1 = "deleteAcademy5_1";
        public final static String DELETE_ACTIVITY_6_1 = "deleteAcademy6_1";
        public final static String DELETE_ACTIVITY_11_1 = "deleteAcademy11_1";
        public final static String DELETE_ACTIVITY_13_1 = "deleteAcademy13_1";
        public final static String DELETE_ACTIVITY_15_1 = "deleteAcademy15_1";


        public final static String DELETE_ACTIVITY = "deleteAcademy";
        public final static String DELETE_ACTIVITY_5 = "deleteAcademy5";
        public final static String DELETE_ACTIVITY_6 = "deleteAcademy6";
        public final static String DELETE_ACTIVITY_11 = "deleteAcademy11";
        public final static String DELETE_ACTIVITY_13 = "deleteAcademy13";
        public final static String DELETE_ACTIVITY_15 = "deleteAcademy15";


        public final static String DELETE_EXPERT = "deleteExpertGroup";
        public final static String DELETE_EXPERT_3 = "deleteExpertGroup3";
        public final static String DELETE_EXPERT_4 = "deleteExpertGroup4";
        public final static String DELETE_EXPERT_10 = "deleteExpertGroup10";
        public final static String DELETE_EXPERT_12 = "deleteExpertGroup12";
        public final static String DELETE_EXPERT_14 = "deleteExpertGroup14";
    }

    public static class ExitAction {
        public final static String EXIT_ACADEMY = "exitAcademyPar";
        public final static String EXIT_ACADEMY_5 = "exitAcademyPar5";
        public final static String EXIT_ACADEMY_6 = "exitAcademyPar6";

        public final static String EXIT_EXPERT = "exitExpertGr";
        public final static String EXIT_EXPERT_3 = "exitExpertGr3";
        public final static String EXIT_EXPERT_4 = "exitExpertGr4";
    }

    public static class UpdateAction {
        public final static String UPDATE_EXPERT_MEMBER = "updateExpertMember";
        public final static String UPDATE_ACADEMY_MEMBER = "updateAcademyMember";
        public final static String UPDATE_ACADEMY_DES = "updateAcademyDes";
    }

    public static class OtherAction {
        public final static String CREATE_EXPERT = "createExpertGroup";
        public final static String CREATE_ACADEMY = "createAcademy";
        public final static String CREATE_EXPERT_RESULT = "expertCreateResult";
    }


    public static class ShareUrl{
        public final static String REWARD_SHARE_URL = Urls.API_URL+"offerReward.html?";
        public final static String ORIENT_SHARE_URL = Urls.API_URL+"orientationTask.html?";
        public final static String SERVICE_SHARE_URL = Urls.API_URL+"callService.html?";
        public final static String NOTIFY_SHARE_URL = Urls.API_URL+"noticeShare.html?";
        public final static String CZFURL ="http://www.czfnet.com";

    }
}
