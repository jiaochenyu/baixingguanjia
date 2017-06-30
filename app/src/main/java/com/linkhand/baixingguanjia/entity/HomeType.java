package com.linkhand.baixingguanjia.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JCY on 2017/6/16.
 * 说明：
 * type 数据类型
 * 1：显示热卖
 * 2：显示预告
 * 3：显示常用标题
 * 4：显示二手汽车等等图标
 */

public class HomeType implements Serializable {
    int type; // 1 2 3 4
    String imageUrl;
    String content; //内容
    List<HomeNormalIcon> mNormalIconList; // 图标
    List<HomePopularIcon> mPopularIconList; // 图标
    public HomeType() {
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<HomeNormalIcon> getNormalIconList() {
        return mNormalIconList;
    }

    public void setNormalIconList(List<HomeNormalIcon> normalIconList) {
        mNormalIconList = normalIconList;
    }

    public List<HomePopularIcon> getPopularIconList() {
        return mPopularIconList;
    }

    public void setPopularIconList(List<HomePopularIcon> popularIconList) {
        mPopularIconList = popularIconList;
    }
}
