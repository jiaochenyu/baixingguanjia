package com.linkhand.baixingguanjia.entity;

import java.io.Serializable;

/**
 * Created by JCY on 2017/6/16.
 * 说明：
 */

public class HomePopularIcon implements Serializable {
    String iconUrl;
    String iconName;

    public HomePopularIcon() {
    }

    public HomePopularIcon(String iconUrl, String iconName) {
        this.iconUrl = iconUrl;
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
}
