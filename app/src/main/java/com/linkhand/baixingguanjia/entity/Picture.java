package com.linkhand.baixingguanjia.entity;

import java.io.Serializable;

/**
 * Created by JCY on 2017/6/20.
 * 说明：
 */

public class Picture implements Serializable {
    private String url;


    public Picture() {
    }

    public Picture(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
