package com.linkhand.entity;

import java.io.Serializable;

/**
 * Created by JCY on 2017/6/16.
 * 说明：
 */

public class Release implements Serializable {
    private String icon; //标题名称
    private String iconUrl;

    public Release() {
    }

    public Release(String icon, String iconUrl) {
        this.icon = icon;
        this.iconUrl = iconUrl;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
