package com.linkhand.baixingguanjia.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JCY on 2017/6/23.
 * 说明： icon_public_welfare
 */

public class PublicWelfare implements Serializable {
    public int type ;
    /**
     * gyid : 5
     * users_id : 1
     * title : 测试寻人启事
     * phone : null
     * content : 测试寻人启事
     * add_time : 1499671739
     * category : 1
     * creator : 测试人员
     * logo_url : null
     * sheng : 1
     * shi : 2
     * qu : 3
     * xiaoqu : 4
     * goods_type : 8
     * examine : 0
     * delete : 0
     * randnum : 47314453
     * address : null
     * image_url : [""]
     * time : 2017-07-10
     *
     */

    /**
     * 1 寻人
     * 2 寻物
     * 3 善行
     */

    private String gyid;
    private String users_id;
    private String title;
    private String phone;
    private String content;
    private String add_time;
    private String category;
    private String creator;
    private String logo_url;
    private String sheng;
    private String shi;
    private String qu;
    private String xiaoqu;
    private String goods_type;
    private String examine;
    private String delete;
    private String randnum;
    private String address;
    private String time;
    private List<String> image_url;
    private String quname;
    private String xiaoquname;

    public String getOffline() {
        return offline;
    }

    public void setOffline(String offline) {
        this.offline = offline;
    }

    private String offline;




    public PublicWelfare() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getGyid() {
        return gyid;
    }

    public void setGyid(String gyid) {
        this.gyid = gyid;
    }

    public String getUsers_id() {
        return users_id;
    }

    public void setUsers_id(String users_id) {
        this.users_id = users_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getSheng() {
        return sheng;
    }

    public void setSheng(String sheng) {
        this.sheng = sheng;
    }

    public String getShi() {
        return shi;
    }

    public void setShi(String shi) {
        this.shi = shi;
    }

    public String getQu() {
        return qu;
    }

    public void setQu(String qu) {
        this.qu = qu;
    }

    public String getXiaoqu() {
        return xiaoqu;
    }

    public void setXiaoqu(String xiaoqu) {
        this.xiaoqu = xiaoqu;
    }

    public String getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(String goods_type) {
        this.goods_type = goods_type;
    }

    public String getExamine() {
        return examine;
    }

    public void setExamine(String examine) {
        this.examine = examine;
    }

    public String getDelete() {
        return delete;
    }

    public void setDelete(String delete) {
        this.delete = delete;
    }

    public String getRandnum() {
        return randnum;
    }

    public void setRandnum(String randnum) {
        this.randnum = randnum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<String> getImage_url() {
        return image_url;
    }

    public void setImage_url(List<String> image_url) {
        this.image_url = image_url;
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
}
