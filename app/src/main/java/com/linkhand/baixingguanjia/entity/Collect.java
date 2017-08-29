package com.linkhand.baixingguanjia.entity;

/**
 * Created by JCY on 2017/6/28.
 * 说明：反馈
 */

public class Collect {
    private String content;
    int type;
    /**
     * id : 12
     * goods_type : 7
     * title : 微型大大大车
     * logo_url : public/uploads/images/20170717\c90d8c04050900d0ba74b474ae8c7f39.jpg
     * delete : 0 // 0 正常 1下线 2删除
      * quname : 裕华区
     * xiaoquname : 测试小区
     * modular : 二手车
     * add_time : 2017-08-01
     */

    private String id;
    private String goods_type;
    private String title;
    private String logo_url;
    private String delete;
    private String quname;
    private String xiaoquname;
    private String modular;
    private String add_time;


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


    public String getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(String goods_type) {
        this.goods_type = goods_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getDelete() {
        return delete;
    }

    public void setDelete(String delete) {
        this.delete = delete;
    }

    public String getQuname() {
        return quname;
    }

    public void setQuname(String quname) {
        this.quname = quname;
    }

    public String getXiaoquname() {
        return xiaoquname;
    }

    public void setXiaoquname(String xiaoquname) {
        this.xiaoquname = xiaoquname;
    }

    public String getModular() {
        return modular;
    }

    public void setModular(String modular) {
        this.modular = modular;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }
}
