package com.linkhand.entity;

import java.util.List;

/**
 * Created by JCY on 2017/6/22.
 * 说明： 商品信息
 */

public class Goods {
    private String id ;
    private String name;
    private float price;
    private List<String> picList;


    public Goods() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public List<String> getPicList() {
        return picList;
    }

    public void setPicList(List<String> picList) {
        this.picList = picList;
    }


}
