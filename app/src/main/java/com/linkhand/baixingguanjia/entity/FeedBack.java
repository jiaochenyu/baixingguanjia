package com.linkhand.baixingguanjia.entity;

/**
 * Created by JCY on 2017/6/28.
 * 说明：反馈
 */

public class FeedBack {


    private String id;
    private String content; //内容
    /**
     * id : 1
     * userid : 1
     * xiaoqu_id : 4
     * phone : 123456789
     * add_time : 2017-07-28
     * num : 3
     * delete : 0
     */


    private int userid;
    private String xiaoqu_id;
    private String phone;
    private String add_time;
    private String num;
    private String delete;

    public FeedBack() {
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





    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getXiaoqu_id() {
        return xiaoqu_id;
    }

    public void setXiaoqu_id(String xiaoqu_id) {
        this.xiaoqu_id = xiaoqu_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getDelete() {
        return delete;
    }

    public void setDelete(String delete) {
        this.delete = delete;
    }
}
