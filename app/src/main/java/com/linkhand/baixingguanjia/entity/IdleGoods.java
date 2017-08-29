package com.linkhand.baixingguanjia.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JCY on 2017/6/23.
 * 说明： 闲置物品
 */

public class IdleGoods implements Serializable {

    /**
     * county : 裕华区
     * village : 华山商务
     * idleid : 1
     * usersid : 1
     * category : 4
     * title : 闲置物品
     * content : 闲置物品
     * buy_price : 500
     * sales_price : 300
     * contact : 张三
     * phone : 123456
     * classify : 13
     * sheng : 1
     * shi : 2
     * qu : 3
     * xiaoqu : 4
     * add_time : 1500603568
     * logo_url : null
     * examine : 1
     * delete : 0
     * randnum : null
     * goods_type : 3
     * address : null
     * image_url : ["闲置物品"]
     * time : 2017-07-21
     */

    private String county;
    private String village;
    private int idleid;
    private int usersid;
    private String category;
    private String title;
    private String content;
    private String buy_price;
    private String sales_price;
    private String contact;
    private String phone;
    private String classify;
    private String sheng;
    private String shi;
    private String qu;
    private String xiaoqu;
    private String add_time;
    private String logo_url;
    private String examine;
    private String delete;
    private String randnum;
    private String goods_type;
    private String address;
    private String time;
    private List<String> image_url;
    private String company;
    private String offline;
    private String leibie;
    private String several; //几成新

    public String getOffline() {
        return offline;
    }

    public void setOffline(String offline) {
        this.offline = offline;
    }

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

    public int getIdleid() {
        return idleid;
    }

    public void setIdleid(int idleid) {
        this.idleid = idleid;
    }

    public int getUsersid() {
        return usersid;
    }

    public void setUsersid(int usersid) {
        this.usersid = usersid;
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

    public String getBuy_price() {
        return buy_price;
    }

    public void setBuy_price(String buy_price) {
        this.buy_price = buy_price;
    }

    public String getSales_price() {
        return sales_price;
    }

    public void setSales_price(String sales_price) {
        this.sales_price = sales_price;
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

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
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

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLeibie() {
        return leibie;
    }

    public void setLeibie(String leibie) {
        this.leibie = leibie;
    }

    public String getSeveral() {
        return several;
    }

    public void setSeveral(String several) {
        this.several = several;
    }
}
