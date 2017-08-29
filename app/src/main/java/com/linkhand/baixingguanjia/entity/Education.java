package com.linkhand.baixingguanjia.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JCY on 2017/6/23.
 * 说明： 家政
 */

public class Education implements Serializable {

    /**
     * county : 裕华区
     * village : 华山商务
     * family_id : 2
     * userid : 1
     * category : 5
     * company : null
     * object : 70
     * title : 初中生家教
     * content : 中学生数学家教一枚
     * creator : 李四
     * phone : 123456
     * add_time : 1499396680
     * type : 61
     * logo_url : null
     * sheng : 1
     * shi : 2
     * qu : 3
     * xiaoqu : 4
     * examine : 1
     * delete : 0
     * randnum : null
     * goods_type : 6
     * address : null
     * image_url : ["图片链接2","图片链接2","图片链接2"]
     * time : 2017-07-07
     * offline:1
     * "leibie": "语文",
     "duixiang": "小学生"
     */

    private String county;
    private String village;
    private String family_id;
    private String userid;
    private String category;
    private String company;
    private String object;
    private String title;
    private String content;
    private String creator;
    private String phone;
    private String add_time;
    private int type;
    private String logo_url;
    private String sheng;
    private String shi;
    private String qu;
    private String xiaoqu;
    private String examine;
    private String delete;
    private String randnum;
    private String goods_type;
    private String address;
    private String time;
    private String offline; //是否下线
    private List<String> image_url;
    /**
     * leibie : 语文
     * duixiang : 小学生
     */

    private String leibie;
    private String duixiang;


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

    public String getFamily_id() {
        return family_id;
    }

    public void setFamily_id(String family_id) {
        this.family_id = family_id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
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

    public String getDuixiang() {
        return duixiang;
    }

    public void setDuixiang(String duixiang) {
        this.duixiang = duixiang;
    }
}
