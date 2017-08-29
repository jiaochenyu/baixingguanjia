package com.linkhand.baixingguanjia.entity;

import java.io.Serializable;

/**
 * Created by JCY on 2017/6/22.
 * 说明：
 */

public class Order implements Serializable {
    private String id;
    private Goods mGoods;
    /**
     * 订单的状态
     * 1. 待付款
     * 2. 待自提
     * 3. 已提货
     */
    private int orderType;
    private int totalNum;//总数量
    private String remarks; //备注
    /**
     * order_id : 19
     * order_sn : 201707221157421
     * user_id : 1
     * admin_id : null
     * order_status : 2
     * shipping_status : 1
     * pay_status : 1
     * consignee :
     * country : 0
     * province : 1
     * city : 2
     * district : 33
     * twon : 0
     * address : 是地方吃v
     * zipcode :
     * mobile : 12345678987
     * email :
     * shipping_code :
     * shipping_name :
     * pay_code : 533
     * pay_name : 122
     * invoice_title :
     * goods_price : 0.00
     * shipping_price : 0.00
     * user_money : 0.00
     * coupon_price : 0.00
     * integral : 0
     * integral_money : 0.00
     * order_amount : 1.00
     * total_amount : 1.00
     * add_time : 1500695862
     * shipping_time : 1500877458
     * confirm_time : 1501142982
     * pay_time : 1500877424
     * order_prom_type : 0
     * order_prom_id : 0
     * order_prom_amount : 0.00
     * discount : 0.00
     * user_note :
     * admin_note :
     * parent_sn : null
     * is_distribut : 0
     * paid_money : 0.00
     * rec_id : 19
     * goods_id : 8
     * goods_name : 沈亚星
     * goods_sn : TP0000008
     * goods_num : 0
     * market_price : 0.00
     * cost_price : 0.00
     * member_goods_price : 0.00
     * give_integral : 0
     * spec_key : 2_4
     * spec_key_name : 大小:中 颜色:红色
     * bar_code :
     * is_comment : 0
     * prom_type : 0
     * prom_id : 0
     * is_send : 1
     * delivery_id : 1
     * sku :
     * original_img:
     */

    private String order_id;
    private String order_sn;
    private int user_id;
    private String admin_id;
    private int order_status;
    private String shipping_status;
    private String pay_status;
    private String consignee;
    private String country;
    private String province;
    private String city;
    private String district;
    private String twon;
    private String address;
    private String zipcode;
    private String mobile;
    private String email;
    private String shipping_code;
    private String shipping_name;
    private String pay_code;
    private String pay_name;
    private String invoice_title;
    private double goods_price;
    private String shipping_price;
    private String user_money;
    private String coupon_price;
    private int integral;
    private String integral_money;
    private float order_amount;
    private String total_amount;
    private long add_time;
    private long shipping_time;
    private long confirm_time;
    private long pay_time;
    private String order_prom_type;
    private String order_prom_id;
    private String order_prom_amount;
    private String discount;
    private String user_note;
    private String admin_note;
    private String parent_sn;
    private String is_distribut;
    private String paid_money;
    private String rec_id;
    private String goods_id;
    private String goods_name;
    private String goods_sn;
    private int goods_num;
    private String market_price;
    private String cost_price;
    private String member_goods_price;
    private int give_integral;
    private String spec_key;
    private String spec_key_name;
    private String bar_code;
    private int is_comment;
    private int prom_type;
    private int prom_id;
    private int is_send;
    private int delivery_id;
    private String sku;
    private String original_img;


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

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public String getShipping_status() {
        return shipping_status;
    }

    public void setShipping_status(String shipping_status) {
        this.shipping_status = shipping_status;
    }

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTwon() {
        return twon;
    }

    public void setTwon(String twon) {
        this.twon = twon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getShipping_code() {
        return shipping_code;
    }

    public void setShipping_code(String shipping_code) {
        this.shipping_code = shipping_code;
    }

    public String getShipping_name() {
        return shipping_name;
    }

    public void setShipping_name(String shipping_name) {
        this.shipping_name = shipping_name;
    }

    public String getPay_code() {
        return pay_code;
    }

    public void setPay_code(String pay_code) {
        this.pay_code = pay_code;
    }

    public String getPay_name() {
        return pay_name;
    }

    public void setPay_name(String pay_name) {
        this.pay_name = pay_name;
    }

    public String getInvoice_title() {
        return invoice_title;
    }

    public void setInvoice_title(String invoice_title) {
        this.invoice_title = invoice_title;
    }

    public double getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(double goods_price) {
        this.goods_price = goods_price;
    }

    public String getShipping_price() {
        return shipping_price;
    }

    public void setShipping_price(String shipping_price) {
        this.shipping_price = shipping_price;
    }

    public String getUser_money() {
        return user_money;
    }

    public void setUser_money(String user_money) {
        this.user_money = user_money;
    }

    public String getCoupon_price() {
        return coupon_price;
    }

    public void setCoupon_price(String coupon_price) {
        this.coupon_price = coupon_price;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getIntegral_money() {
        return integral_money;
    }

    public void setIntegral_money(String integral_money) {
        this.integral_money = integral_money;
    }

    public float getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(float order_amount) {
        this.order_amount = order_amount;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public long getAdd_time() {
        return add_time;
    }

    public void setAdd_time(long add_time) {
        this.add_time = add_time;
    }

    public long getShipping_time() {
        return shipping_time;
    }

    public void setShipping_time(long shipping_time) {
        this.shipping_time = shipping_time;
    }

    public long getConfirm_time() {
        return confirm_time;
    }

    public void setConfirm_time(long confirm_time) {
        this.confirm_time = confirm_time;
    }

    public long getPay_time() {
        return pay_time;
    }

    public void setPay_time(long pay_time) {
        this.pay_time = pay_time;
    }

    public String getOrder_prom_type() {
        return order_prom_type;
    }

    public void setOrder_prom_type(String order_prom_type) {
        this.order_prom_type = order_prom_type;
    }

    public String getOrder_prom_id() {
        return order_prom_id;
    }

    public void setOrder_prom_id(String order_prom_id) {
        this.order_prom_id = order_prom_id;
    }

    public String getOrder_prom_amount() {
        return order_prom_amount;
    }

    public void setOrder_prom_amount(String order_prom_amount) {
        this.order_prom_amount = order_prom_amount;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getUser_note() {
        return user_note;
    }

    public void setUser_note(String user_note) {
        this.user_note = user_note;
    }

    public String getAdmin_note() {
        return admin_note;
    }

    public void setAdmin_note(String admin_note) {
        this.admin_note = admin_note;
    }

    public String getParent_sn() {
        return parent_sn;
    }

    public void setParent_sn(String parent_sn) {
        this.parent_sn = parent_sn;
    }

    public String getIs_distribut() {
        return is_distribut;
    }

    public void setIs_distribut(String is_distribut) {
        this.is_distribut = is_distribut;
    }

    public String getPaid_money() {
        return paid_money;
    }

    public void setPaid_money(String paid_money) {
        this.paid_money = paid_money;
    }

    public String getRec_id() {
        return rec_id;
    }

    public void setRec_id(String rec_id) {
        this.rec_id = rec_id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_sn() {
        return goods_sn;
    }

    public void setGoods_sn(String goods_sn) {
        this.goods_sn = goods_sn;
    }

    public int getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(int goods_num) {
        this.goods_num = goods_num;
    }

    public String getMarket_price() {
        return market_price;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
    }

    public String getCost_price() {
        return cost_price;
    }

    public void setCost_price(String cost_price) {
        this.cost_price = cost_price;
    }

    public String getMember_goods_price() {
        return member_goods_price;
    }

    public void setMember_goods_price(String member_goods_price) {
        this.member_goods_price = member_goods_price;
    }

    public int getGive_integral() {
        return give_integral;
    }

    public void setGive_integral(int give_integral) {
        this.give_integral = give_integral;
    }

    public String getSpec_key() {
        return spec_key;
    }

    public void setSpec_key(String spec_key) {
        this.spec_key = spec_key;
    }

    public String getSpec_key_name() {
        return spec_key_name;
    }

    public void setSpec_key_name(String spec_key_name) {
        this.spec_key_name = spec_key_name;
    }

    public String getBar_code() {
        return bar_code;
    }

    public void setBar_code(String bar_code) {
        this.bar_code = bar_code;
    }

    public int getIs_comment() {
        return is_comment;
    }

    public void setIs_comment(int is_comment) {
        this.is_comment = is_comment;
    }

    public int getProm_type() {
        return prom_type;
    }

    public void setProm_type(int prom_type) {
        this.prom_type = prom_type;
    }

    public int getProm_id() {
        return prom_id;
    }

    public void setProm_id(int prom_id) {
        this.prom_id = prom_id;
    }

    public int getIs_send() {
        return is_send;
    }

    public void setIs_send(int is_send) {
        this.is_send = is_send;
    }

    public int getDelivery_id() {
        return delivery_id;
    }

    public void setDelivery_id(int delivery_id) {
        this.delivery_id = delivery_id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getOriginal_img() {
        return original_img;
    }

    public void setOriginal_img(String original_img) {
        this.original_img = original_img;
    }
}
