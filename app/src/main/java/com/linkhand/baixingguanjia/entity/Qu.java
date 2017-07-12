package com.linkhand.baixingguanjia.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JCY on 2017/7/8.
 * 说明：
 */

public class Qu implements Serializable {

    /**
     * id : 1
     * name : 裕华区
     * fu_id : 0
     */

    private String id;
    private String name;
    private String fu_id;

    private List<Xiaoqu> xiaoquList;

    public Qu() {
    }


    public Qu(String name) {
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

    public String getFu_id() {
        return fu_id;
    }

    public void setFu_id(String fu_id) {
        this.fu_id = fu_id;
    }

    public List<Xiaoqu> getXiaoquList() {
        return xiaoquList;
    }

    public void setXiaoquList(List<Xiaoqu> xiaoquList) {
        this.xiaoquList = xiaoquList;
    }
}
