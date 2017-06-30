package com.linkhand.baixingguanjia.entity;

/**
 * Created by JCY on 2017/6/21.
 * 说明：
 */

public class Address {
    private boolean flag;
    private String  shortAddress;
    private String  longAddress;
    private String phone;


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
}
