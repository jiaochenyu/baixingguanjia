package com.linkhand.baixingguanjia.entity;

import java.io.Serializable;

/**
 * Created by JCY on 2017/7/24.
 * 说明： 商品规格
 */

public class GoodsTagsGuige implements Serializable {

    /**
     * key : 1_5_9    id：颜色 分类 大小
     * price : 2.00
     * store_count : 2
     */

    private String key;
    private float price;
    private int store_count;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getStore_count() {
        return store_count;
    }

    public void setStore_count(int store_count) {
        this.store_count = store_count;
    }
}
