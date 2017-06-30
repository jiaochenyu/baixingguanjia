package com.linkhand.baixingguanjia.entity;

/**
 * Created by JCY on 2017/6/28.
 * 说明：预约
 */

public class Appointment {
    private String id;
    private String content;
    private String title;
    int type; //类型 家政、房产
    private String date;

    public Appointment() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
