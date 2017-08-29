package com.linkhand.baixingguanjia.entity;

import java.io.Serializable;

/**
 * Created by jcy on 2017/5/15.
 */

/**
 * flag
 * uploadHeaderSuccess :  个人中心 头像上传成功 PreviewActivity --> UserInfoActivity
 * type : 发布通用类型 二手汽车发布选择汽车类型  CommonSelectedTypeActivity--> SecondCarReleaseActivity。。。。
 * cartype :发布通用类型 二手汽车发布选择汽车类型   CommonSelectedTypeActivity———>SecondCarReleaseActivity
 * idletype :发布通用类型 二手物品发布二手物品类型   CommonSelectedTypeActivity———>IdlegoodsReleaseActivity
 * nickName :个人中心 昵称上传成功  userInfoActivity———>myFragment
 * updateCell :个人中心 更新我的小区或者添加我的小区  AddcommunityActivity———>UserInfoActivity
 * selectedAddress :选择默认收货地址   selectaddaressActivity———>confirmOrderActivity
 * carDescribe :二手车描述   ReleaseDescribeActivity———>SecondCarReleaseActivity
 * selectedSalary : 招聘选择薪资   RecruitSalaryActivity———>RecruitReleaseActivity
 * selectedFuli : 招聘选择福利   RecruitSalaryActivity———>RecruitReleaseActivity
 * offline : 发布的类型下线   MyReleaseNowFragment———>MyReleaseOfflineFragment
 * fabu : 已下线的服务然后重新发布 通知到待审核  MyReleaseOfflineFragment———>MyReleaseNFragment
 * offlineEducation : 下线  教育详情页———>教育列表移除选项
 * offlineKeeping : 下线  家政详情页———>家政列表移除选项
 * offlineHouse : 下线  房产详情页———>房产列表移除选项
 * offlineIdle : 下线  闲置物品详情页———>闲置物品列表移除选项
 * offlineRecruit : 下线  招聘详情页———>招聘列表移除选项
 * offlineCar : 下线  二手车详情页———>二手车列表移除选项
 * offlineWelfare : 下线  公益详情页———>公益列表移除选项
 * jpushMessageCount : Jpushreceiver 消息未读数量  home notice myFragment ;
 * updateMessageCount : Jpushreceiver 消息未读数量减减  home notice myFragment ;
 * HotCountDownFinish : 首页倒计时结束 重新请求数据 ;
 * paySilver : 充值白银成功  ;
 * CommonRelease :   ;
 * EducationRelease :   ;
 * KeepRelease :   ;
 * HouseRelease :   ;
 * IdleRelease :   ;
 * WelfareRelease :   ;
 * RecruitRelease :   ;
 */
public class EventFlag implements Serializable {
    String flag;
    int position;
    Object mObject;

    public EventFlag() {
    }

    public EventFlag(String flag, Object object) {
        this.flag = flag;
        this.mObject = object;
    }

    public EventFlag(String flag, int position) {
        this.flag = flag;
        this.position = position;
    }


    public EventFlag(String flag, Object object, int position) {
        this.flag = flag;
        this.position = position;
        mObject = object;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Object getObject() {
        return mObject;
    }

    public void setObject(Object object) {
        mObject = object;
    }
}
