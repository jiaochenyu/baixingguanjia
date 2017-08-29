package com.linkhand.baixingguanjia.entity;

import java.io.Serializable;

/**
 * Created by JCY on 2017/7/11.
 * 说明： 获取类型 二手车类型  家政类型 等等。。。。。
 */

public class CommonType implements Serializable {

    /**
     * id : 40
     * name : 微型车
     */

    private String id;
    private String name;

    public CommonType() {

    }

    public CommonType(String id, String name) {
        this.id = id;
        this.name = name;
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
}
