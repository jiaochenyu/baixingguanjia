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
     */

    public int userid;
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
    public String avatarAddr;

    public User() {
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
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

    public String getAvatarAddr() {
        return avatarAddr;
    }

    public void setAvatarAddr(String avatarAddr) {
        this.avatarAddr = avatarAddr;
    }
}
