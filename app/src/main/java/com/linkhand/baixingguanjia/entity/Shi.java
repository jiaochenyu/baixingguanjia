package com.linkhand.baixingguanjia.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JCY on 2017/7/8.
 * 说明：
 */

public class Shi implements Serializable {

    /**
     * id : 1
     * name : 石家庄市
     * fu_id : 0
     */

    private String id;
    private String name;
    private String fu_id;

    private List<Qu> quList;

    public Shi() {
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

    public List<Qu> getQuList() {
        return quList;
    }

    public void setQuList(List<Qu> quList) {
        this.quList = quList;
    }
}
