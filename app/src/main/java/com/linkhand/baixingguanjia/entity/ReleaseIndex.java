package com.linkhand.baixingguanjia.entity;

import java.io.Serializable;

/**
 * Created by JCY on 2017/8/24.
 * 说明：
 */

public class ReleaseIndex implements Serializable {

    /**
     * id : 16
     * name : 通用
     * tubiao : null
     */

    private String id;
    private String name;
    private String tubiao;
    private int icons;

    public ReleaseIndex() {
    }

    public ReleaseIndex(String name, int icons) {
        this.name = name;
        this.icons = icons;
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

    public String getTubiao() {
        return tubiao;
    }

    public void setTubiao(String tubiao) {
        this.tubiao = tubiao;
    }

    public int getIcons() {
        return icons;
    }

    public void setIcons(int icons) {
        this.icons = icons;
    }
}
