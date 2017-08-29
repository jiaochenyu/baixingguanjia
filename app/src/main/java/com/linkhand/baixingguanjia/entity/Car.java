package com.linkhand.baixingguanjia.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JCY on 2017/6/22.
 * 说明：
 * /**
 * carid : 6
 * usersid : 2
 * category : 4
 * title : 奥迪A6
 * content : 五成新A6一辆
 * type : 4
 * creator : 2012
 * distance : 10
 * contact : 测试人
 * phone : 789789
 * Insertdate : 1499396680
 * deletedate :
 * logo_url :
 * price : 15
 * sheng : 23
 * shi : 24
 * qu : 25
 * xiaoqu : 26
 * examine : 1
 * delete : 0
 * randnum : 26870727
 * goods_type : 7
 * county : 测试区B
 * village : 测试小区B
 * time : 1970-01-01
 * <p>
 * img : [{"image_url":"图片链接1"},{"image_url":"图片链接1"},{"image_url":"图片链接1"}]
 */


public class Car implements Serializable {
    private String carid;
    private String usersid;
    private String category;
    private String title;
    private String content;
    private String type;
    private String creator;
    private double distance;
    private String contact;
    private String phone;
    private String Insertdate;
    private String deletedate;
    private String logo_url;
    private double price;
    private String sheng;
    private String shi;
    private String qu;
    private String xiaoqu;
    private String examine;
    private String delete;
    private int randnum;
    private String goods_type;
    private String county;
    private String village;
    private String time;
    private List<Picture> pic;
    private String company;
    private String address;
    private List<String> image_url;
    private String offline;
    private String leibie;
    private String quname;
    private String xiaoquname;

    public Car() {
    }

    public String getOffline() {
        return offline;
    }

    public void setOffline(String offline) {
        this.offline = offline;
    }


    public String getCarid() {
        return carid;
    }

    public void setCarid(String carid) {
        this.carid = carid;
    }

    public String getUsersid() {
        return usersid;
    }

    public void setUsersid(String usersid) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
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

    public String getInsertdate() {
        return Insertdate;
    }

    public void setInsertdate(String Insertdate) {
        this.Insertdate = Insertdate;
    }

    public String getDeletedate() {
        return deletedate;
    }

    public void setDeletedate(String deletedate) {
        this.deletedate = deletedate;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public int getRandnum() {
        return randnum;
    }

    public void setRandnum(int randnum) {
        this.randnum = randnum;
    }

    public String getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(String goods_type) {
        this.goods_type = goods_type;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Picture> getPic() {
        return pic;
    }

    public void setPic(List<Picture> pic) {
        this.pic = pic;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getImage_url() {
        return image_url;
    }

    public void setImage_url(List<String> image_url) {
        this.image_url = image_url;
    }

    public String getLeibie() {
        return leibie;
    }

    public void setLeibie(String leibie) {
        this.leibie = leibie;
    }

    public String getQuname() {
        return quname;
    }

    public void setQuname(String quname) {
        this.quname = quname;
    }

    public String getXiaoquname() {
        return xiaoquname;
    }

    public void setXiaoquname(String xiaoquname) {
        this.xiaoquname = xiaoquname;
    }
}
