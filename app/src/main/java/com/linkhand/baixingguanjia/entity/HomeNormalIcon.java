package com.linkhand.baixingguanjia.entity;

import java.io.Serializable;

/**
 * Created by JCY on 2017/6/16.
 * 说明：
 */

public class HomeNormalIcon implements Serializable {
    String iconUrl;
    String iconName;
    int icon;
    /**
     * id : 1
     * name : 二手房产
     * tubiao : string
     * sort_order : string
     * parient_id : 0
     */

    private String id;
    private String name;
    private String tubiao;
    private String sort_order;
    private String parient_id;


    public HomeNormalIcon() {
    }



    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public HomeNormalIcon(int icon, String iconName) {
        this.icon = icon;
        this.iconName = iconName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
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

    public String getSort_order() {
        return sort_order;
    }

    public void setSort_order(String sort_order) {
        this.sort_order = sort_order;
    }

    public String getParient_id() {
        return parient_id;
    }

    public void setParient_id(String parient_id) {
        this.parient_id = parient_id;
    }
}
