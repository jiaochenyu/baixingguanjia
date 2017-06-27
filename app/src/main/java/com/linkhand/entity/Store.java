package com.linkhand.entity;

/**
 * 商家
 */
public class Store {
    private String id;
    private String name;//公司名
    private String logo;//公司logo
    private boolean checked;//是否已经选中
//    private List<Goods> goodsList;//商品名称
    private int num; // 数量
    private String distributionType;// 配送方式
    private String freightRisk; //运费险
    private String words; //留言
    private int totalNum;//总数量
    private double totalPrice;//总价
    /**
     * 为了调用自适配器的方法而写的引用
     */
//    private ShoppingCartItemAdapter adapter;
    /**
     * 订单的状态
     * 1. 待付款
     * 2. 待发货
     * 3. 待收货
     * 4. 待评价
     * 5.
     */
    private int orderType;

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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

//    public List<Goods> getGoodsList() {
//        return goodsList;
//    }

//    public void setGoodsList(List<Goods> goodsList) {
//        this.goodsList = goodsList;
//    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getDistributionType() {
        return distributionType;
    }

    public void setDistributionType(String distributionType) {
        this.distributionType = distributionType;
    }

    public String getFreightRisk() {
        return freightRisk;
    }

    public void setFreightRisk(String freightRisk) {
        this.freightRisk = freightRisk;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

//    public ShoppingCartItemAdapter getAdapter() {
//        return adapter;
//    }

//    public void setAdapter(ShoppingCartItemAdapter adapter) {
//        this.adapter = adapter;
//    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }
}
