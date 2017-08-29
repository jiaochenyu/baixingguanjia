package com.linkhand.baixingguanjia.entity;

import java.io.Serializable;

/**
 * Created by JCY on 2017/6/21.
 * 说明：
 */

public class Address implements Serializable{
    private boolean flag = false;
    private String  shortAddress;
    private String  longAddress;
    private String phone;
    /**
     * pickup_id : 2
     * pickup_name : 糊糊
     * pickup_address : 是地方吃v
     * pickup_phone : 12345678987
     * pickup_contact : 水电费
     * province_id : 1
     * city_id : 2
     * district_id : 33
     * suppliersid : 1
     * is_moren : 1
     * jing : 1
     * wei : 1
     * province_name : 河北省
     * city_name : 石家庄市
     * district_name : 元氏县
     */

    private String pickup_id;
    private String pickup_name;
    private String pickup_address;
    private String pickup_phone;
    private String pickup_contact;
    private String province_id;
    private String city_id;
    private String district_id;
    private String suppliersid;
    private String is_moren;
    private double jing;
    private double wei;
    private String province_name;
    private String city_name;
    private String district_name;
    /**
     * shengid : 1
     * shengname : 河北省
     * shiid : 2
     * shiname : 石家庄市
     * quid : 3
     * quname : 裕华区
     * xiaoquid : 18
     * xiaoquname : 裕华高速口
     * username : 张三
     * street : 裕华路东二环交口
     */

    private String shengid;
    private String shengname;
    private String shiid;
    private String shiname;
    private String quid;
    private String quname;
    private String xiaoquid;
    private String xiaoquname;
    private String username;
    private String street;


    public Address() {
    }

    public Address(boolean flag, String shortAddress, String longAddress, String phone) {
        this.flag = flag;
        this.shortAddress = shortAddress;
        this.longAddress = longAddress;
        this.phone = phone;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getShortAddress() {
        return shortAddress;
    }

    public void setShortAddress(String shortAddress) {
        this.shortAddress = shortAddress;
    }

    public String getLongAddress() {
        return longAddress;
    }

    public void setLongAddress(String longAddress) {
        this.longAddress = longAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPickup_id() {
        return pickup_id;
    }

    public void setPickup_id(String pickup_id) {
        this.pickup_id = pickup_id;
    }

    public String getPickup_name() {
        return pickup_name;
    }

    public void setPickup_name(String pickup_name) {
        this.pickup_name = pickup_name;
    }

    public String getPickup_address() {
        return pickup_address;
    }

    public void setPickup_address(String pickup_address) {
        this.pickup_address = pickup_address;
    }

    public String getPickup_phone() {
        return pickup_phone;
    }

    public void setPickup_phone(String pickup_phone) {
        this.pickup_phone = pickup_phone;
    }

    public String getPickup_contact() {
        return pickup_contact;
    }

    public void setPickup_contact(String pickup_contact) {
        this.pickup_contact = pickup_contact;
    }

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
    }

    public String getSuppliersid() {
        return suppliersid;
    }

    public void setSuppliersid(String suppliersid) {
        this.suppliersid = suppliersid;
    }

    public String getIs_moren() {
        return is_moren;
    }

    public void setIs_moren(String is_moren) {
        this.is_moren = is_moren;
    }

    public double getJing() {
        return jing;
    }

    public void setJing(double jing) {
        this.jing = jing;
    }

    public double getWei() {
        return wei;
    }

    public void setWei(double wei) {
        this.wei = wei;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public String getShengid() {
        return shengid;
    }

    public void setShengid(String shengid) {
        this.shengid = shengid;
    }

    public String getShengname() {
        return shengname;
    }

    public void setShengname(String shengname) {
        this.shengname = shengname;
    }

    public String getShiid() {
        return shiid;
    }

    public void setShiid(String shiid) {
        this.shiid = shiid;
    }

    public String getShiname() {
        return shiname;
    }

    public void setShiname(String shiname) {
        this.shiname = shiname;
    }

    public String getQuid() {
        return quid;
    }

    public void setQuid(String quid) {
        this.quid = quid;
    }

    public String getQuname() {
        return quname;
    }

    public void setQuname(String quname) {
        this.quname = quname;
    }

    public String getXiaoquid() {
        return xiaoquid;
    }

    public void setXiaoquid(String xiaoquid) {
        this.xiaoquid = xiaoquid;
    }

    public String getXiaoquname() {
        return xiaoquname;
    }

    public void setXiaoquname(String xiaoquname) {
        this.xiaoquname = xiaoquname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
