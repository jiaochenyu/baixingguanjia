package com.linkhand.baixingguanjia.entity;

import java.io.Serializable;

/**
 * Created by JCY on 2017/6/23.
 * 说明： icon_public_welfare
 */

public class PublicWelfare implements Serializable {
    public int type ;

    /**
     * 1 寻人
     * 2 寻物
     * 3 善行
     */

    public PublicWelfare() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
