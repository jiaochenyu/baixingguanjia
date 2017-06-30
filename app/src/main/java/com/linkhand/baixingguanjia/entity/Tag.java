package com.linkhand.baixingguanjia.entity;

/**
 * Created by JCY on 2017/6/21.
 * 说明： 选择商品信息的标签 比如  尺码S M  L X Xl XXl
 */

public class Tag {
    private String name;
    private boolean flag = false;

    public Tag() {
    }

    public Tag(String name, boolean flag) {
        this.name = name;
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
