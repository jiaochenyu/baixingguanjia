package com.linkhand.baixingguanjia.entity;

/**
 * Created by JCY on 2017/6/28.
 * 说明：反馈
 */

public class Collect {
    private String id;
    private String content;
    int type;

    /**
     * 1 商品类
     * 2 服务类
     */

    public Collect() {
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
