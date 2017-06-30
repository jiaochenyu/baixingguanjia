package com.linkhand.baixingguanjia.entity;

/**
 * Created by JCY on 2017/6/16.
 * 说明：预告信息
 */

public class Notice {
    private String content;
    private String imageUrl;
    private String date;

    public Notice() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
