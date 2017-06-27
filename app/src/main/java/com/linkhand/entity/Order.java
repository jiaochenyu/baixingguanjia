package com.linkhand.entity;

/**
 * Created by JCY on 2017/6/22.
 * 说明：
 */

public class Order {
    private  String id;
    private Goods mGoods;
    /**
     * 订单的状态
     * 1. 待付款
     * 2. 待自提
     * 3. 已提货
     */
    private int orderType;
    private int totalNum;//总数量
    private String remarks ; //备注

    public Order() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Goods getGoods() {
        return mGoods;
    }

    public void setGoods(Goods goods) {
        mGoods = goods;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
