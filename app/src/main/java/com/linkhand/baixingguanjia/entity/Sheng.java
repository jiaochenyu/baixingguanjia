package com.linkhand.baixingguanjia.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JCY on 2017/7/8.
 * 说明：
 */

public class Sheng implements Serializable {

    /**
     * id : 1
     * name : 河北省
     * fu_id : 0
     */

    private String id;
    private String name;
    private String fu_id;
    private List<Shi> shiList;
    private Shi Shi;
    private Qu qu;
    private Xiaoqu xiaoqu;

    public Sheng() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFu_id() {
        return fu_id;
    }

    public void setFu_id(String fu_id) {
        this.fu_id = fu_id;
    }

    public List<Shi> getShiList() {
        return shiList;
    }

    public void setShiList(List<Shi> shiList) {
        this.shiList = shiList;
    }

    public com.linkhand.baixingguanjia.entity.Shi getShi() {
        return Shi;
    }

    public void setShi(com.linkhand.baixingguanjia.entity.Shi shi) {
        Shi = shi;
    }

    public Qu getQu() {
        return qu;
    }

    public void setQu(Qu qu) {
        this.qu = qu;
    }

    public Xiaoqu getXiaoqu() {
        return xiaoqu;
    }

    public void setXiaoqu(Xiaoqu xiaoqu) {
        this.xiaoqu = xiaoqu;
    }

}
