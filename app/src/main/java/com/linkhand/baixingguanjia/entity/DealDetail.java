package com.linkhand.baixingguanjia.entity;

import java.io.Serializable;

/**
 * Created by JCY on 2017/8/18.
 * 说明：
 */

public class DealDetail implements Serializable {
    private String title;
    private String account;
    private String time;
    private String dealData; //交易数量
    /**
     * id : 5
     * userid : 8
     * pay_name : 发布二手房产
     * pay_time : 2017-08-21
     * balance : 99
     *  "detailed": "-1"
     */

    private String id;
    private String userid;
    private String pay_name;
    private String pay_time;
    private int balance;
    private String  detailed;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDealData() {
        return dealData;
    }

    public void setDealData(String dealData) {
        this.dealData = dealData;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPay_name() {
        return pay_name;
    }

    public void setPay_name(String pay_name) {
        this.pay_name = pay_name;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getDetailed() {
        return detailed;
    }

    public void setDetailed(String detailed) {
        this.detailed = detailed;
    }
}
