package com.linkhand.baixingguanjia.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JCY on 2017/8/23.
 * 说明： 通用模块
 */

public class Common implements Serializable{


    /**
     * common_id : 2
     * userid : 1
     * contact : 李四
     * phone : 123456
     * title : 公共发布标题11
     * message : 公共发布摘要
     * model : 公共发布内容
     * add_time : 2017-07-13
     * goods_type : 16
     * examine : 1
     * delete : 0
     * randnum : 83148193
     * sheng : 1
     * shi : 2
     * qu : 3
     * xiaoqu : 5
     * address : null
     * logo_url : null
     * offline_time : null
     * image_url : [""]
     * offline : null
     *   "mokuainame": "通用",
     *   "time": "2017-07-13"
     */

    private String common_id;
    private String userid;
    private String contact;
    private String phone;
    private String title;
    private String message;
    private String model;
    private String add_time;
    private String goods_type;
    private String examine;
    private String delete;
    private String randnum;
    private String sheng;
    private String shi;
    private String qu;
    private String xiaoqu;
    private String address;
    private String logo_url;
    private String  offline_time;
    private int offline;
    private List<String> image_url;
    private String mokuainame;
    private String time;

    public String getCommon_id() {
        return common_id;
    }

    public void setCommon_id(String common_id) {
        this.common_id = common_id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(String goods_type) {
        this.goods_type = goods_type;
    }

    public String getExamine() {
        return examine;
    }

    public void setExamine(String examine) {
        this.examine = examine;
    }

    public String getDelete() {
        return delete;
    }

    public void setDelete(String delete) {
        this.delete = delete;
    }

    public String getRandnum() {
        return randnum;
    }

    public void setRandnum(String randnum) {
        this.randnum = randnum;
    }

    public String getSheng() {
        return sheng;
    }

    public void setSheng(String sheng) {
        this.sheng = sheng;
    }

    public String getShi() {
        return shi;
    }

    public void setShi(String shi) {
        this.shi = shi;
    }

    public String getQu() {
        return qu;
    }

    public void setQu(String qu) {
        this.qu = qu;
    }

    public String getXiaoqu() {
        return xiaoqu;
    }

    public void setXiaoqu(String xiaoqu) {
        this.xiaoqu = xiaoqu;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getOffline_time() {
        return offline_time;
    }

    public void setOffline_time(String offline_time) {
        this.offline_time = offline_time;
    }

    public int getOffline() {
        return offline;
    }

    public void setOffline(int offline) {
        this.offline = offline;
    }

    public List<String> getImage_url() {
        return image_url;
    }

    public void setImage_url(List<String> image_url) {
        this.image_url = image_url;
    }

    public String getMokuainame() {
        return mokuainame;
    }

    public void setMokuainame(String mokuainame) {
        this.mokuainame = mokuainame;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
