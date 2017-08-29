package com.linkhand.baixingguanjia.entity;

/**
 * Created by JCY on 2017/7/4.
 * 说明：
 */

public class User {

    /**
     * userid : 5
     * username :
     * email :
     * phone : 13091617887
     * nickname : null
     * sex : 0
     * password : e10adc3949ba59abbe56e057f20f883e
     * home_address : 0
     * community_id : 0
     * user_type : null
     * modifier : null
     * deletedby : null
     * Insertdate : 2017-07-04 17:03:26
     * modifydate : null
     * deletedate : null
     * "total_amount": null, //消费额度
     * "user_money": "1000.00", //  白银  用于发布
     * "user_gold": "55.00", // 黄金
     */


    public String userid;
    public String username;
    public String email;
    public String phone;
    public String nickname;
    public int sex;
    public String password;
    public int home_address;
    public int community_id;
    public String user_type;
    public String modifier;
    public String deletedby;
    public String Insertdate;
    public String modifydate;
    public String deletedate;
    public String head_url;
    public int num;
    /**
     * qu_id : r
     * qishu : r
     * zutuan : r
     * louhao : r
     * danyuan : r
     * louceng : r
     * fangjian : r
     * xiaoqu : r
     */
    //我的小区
    private String qu_id;
    private String qishu; //期数
    private String zutuan; //组团数
    private String louhao; // 楼号
    private String danyuan; //单元
    private String louceng; //楼层
    private String fangjian; //房间号
    /**
     * sheng : r
     * shi : r
     * qu : r
     * xiaoqu : r
     * xiaoquname : r
     */

    private String sheng; //省市区id
    private String shi;
    private String qu;
    private String xiaoqu;
    private String xiaoquname; //我的小区名称
    /**
     * total_amount : null
     * user_money : 1000.00
     * user_gold : 55.00
     */

    private double total_amount;
    private int user_money;
    private int user_gold;


    public User() {
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getHome_address() {
        return home_address;
    }

    public void setHome_address(int home_address) {
        this.home_address = home_address;
    }

    public int getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(int community_id) {
        this.community_id = community_id;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getDeletedby() {
        return deletedby;
    }

    public void setDeletedby(String deletedby) {
        this.deletedby = deletedby;
    }

    public String getInsertdate() {
        return Insertdate;
    }

    public void setInsertdate(String insertdate) {
        Insertdate = insertdate;
    }

    public String getModifydate() {
        return modifydate;
    }

    public void setModifydate(String modifydate) {
        this.modifydate = modifydate;
    }

    public String getDeletedate() {
        return deletedate;
    }

    public void setDeletedate(String deletedate) {
        this.deletedate = deletedate;
    }

    public String getHead_url() {
        return head_url;
    }

    public void setHead_url(String head_url) {
        this.head_url = head_url;
    }

    public String getQu_id() {
        return qu_id;
    }

    public void setQu_id(String qu_id) {
        this.qu_id = qu_id;
    }

    public String getQishu() {
        return qishu;
    }

    public void setQishu(String qishu) {
        this.qishu = qishu;
    }

    public String getZutuan() {
        return zutuan;
    }

    public void setZutuan(String zutuan) {
        this.zutuan = zutuan;
    }

    public String getLouhao() {
        return louhao;
    }

    public void setLouhao(String louhao) {
        this.louhao = louhao;
    }

    public String getDanyuan() {
        return danyuan;
    }

    public void setDanyuan(String danyuan) {
        this.danyuan = danyuan;
    }

    public String getLouceng() {
        return louceng;
    }

    public void setLouceng(String louceng) {
        this.louceng = louceng;
    }

    public String getFangjian() {
        return fangjian;
    }

    public void setFangjian(String fangjian) {
        this.fangjian = fangjian;
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

    public String getXiaoquname() {
        return xiaoquname;
    }

    public void setXiaoquname(String xiaoquname) {
        this.xiaoquname = xiaoquname;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public int getUser_money() {
        return user_money;
    }

    public void setUser_money(int user_money) {
        this.user_money = user_money;
    }

    public int getUser_gold() {
        return user_gold;
    }

    public void setUser_gold(int user_gold) {
        this.user_gold = user_gold;
    }
}
