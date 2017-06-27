package com.linkhand.entity;

import java.io.Serializable;

/**
 * Created by JCY on 2017/6/26.
 * 说明：
 */

public class Message implements Serializable {
    private String iconUrl;
    private String title;
    private String content;

    public Message() {
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
