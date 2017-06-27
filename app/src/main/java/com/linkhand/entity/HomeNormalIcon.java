package com.linkhand.entity;

import java.io.Serializable;

/**
 * Created by JCY on 2017/6/16.
 * 说明：
 */

public class HomeNormalIcon implements Serializable {
    String iconUrl;
    String iconName;
    int icon;

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
}
