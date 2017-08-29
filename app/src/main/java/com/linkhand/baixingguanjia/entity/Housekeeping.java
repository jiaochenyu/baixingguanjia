package com.linkhand.baixingguanjia.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JCY on 2017/6/23.
 * 说明： 家政
 */

public class Housekeeping implements Serializable {

    /**
     * county : 裕华区
     * village : 华山商务
     * husbandryid : 2
     * userid : 1
     * category : 5
     * title : 保洁
     * content : 勤劳的小保洁一枚
     * phone : 123456789
     * creator : 李四
     * logo_url : null
     * add_time : 1499396680
     * deletedate : null
     * sheng : 1
     * shi : 2
     * qu : 3
     * xiaoqu : 4
     * examine : 1
     * delete : 0
     * randnum : null
     * goods_type : 2
     * type : 54
     * address : null
     * image_url : ["图片链接2","图片链接2","图片链接2"]
     * time : 2017-07-07
     * offline
     */

    private String county;
    private String village;
    private int husbandryid;
    private int userid;
    private String category;
    private String title;
    private String content;
    private String phone;
    private String creator;
    private String logo_url;
    private String add_time;
    private String deletedate;
    private String sheng;
    private String shi;
    private String qu;
    private String xiaoqu;
    private String examine;
    private String delete;
    private String randnum;
    private String goods_type;
    private String type;
    private String address;
    private String time;
    private List<String> image_url;
    private String company;
    private String offline;
    private String leibie;

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public int getHusbandryid() {
        return husbandryid;
    }

    public void setHusbandryid(int husbandryid) {
        this.husbandryid = husbandryid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getDeletedate() {
        return deletedate;
    }

    public void setDeletedate(String deletedate) {
        this.deletedate = deletedate;
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

    public String getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(String goods_type) {
        this.goods_type = goods_type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<String> getImage_url() {
        return image_url;
    }

    public void setImage_url(List<String> image_url) {
        this.image_url = image_url;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getOffline() {
        return offline;
    }

    public void setOffline(String offline) {
        this.offline = offline;
    }

    public String getLeibie() {
        return leibie;
    }

    public void setLeibie(String leibie) {
        this.leibie = leibie;
    }
}
