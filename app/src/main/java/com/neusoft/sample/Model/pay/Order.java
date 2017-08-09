package com.neusoft.sample.Model.pay;

/**
 * 产品订单
 */
public class Order {

	private String order_id;              //订单ID
	private String order_price;           //订单价格
	private String good_no ;              //商品编号
	private String good_name ;            //商品名称
	private String good_price ;           //商品单价
	private String good_number ;          //商品数量
	private String good_type ;            //商品类型  
	private String order_create_time ;    //订单创建时间
	private String order_pay_time ;       //订单支付时间
	private String notify_url ;           //回调地址
	private String  pay_status;           //支付状态     支付中，支付成功，支付失败。   
	private String  user_id;              //用户ID
	private String  trade_no ;            //支付宝交易号	String(64)	是	支付宝交易凭证号	2013112011001004330000121536
	
	
	public Order(){}


	public String getOrder_id() {
		return order_id;
	}


	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}


	public String getOrder_price() {
		return order_price;
	}


	public void setOrder_price(String order_price) {
		this.order_price = order_price;
	}


	public String getGood_no() {
		return good_no;
	}


	public void setGood_no(String good_no) {
		this.good_no = good_no;
	}


	public String getGood_name() {
		return good_name;
	}


	public void setGood_name(String good_name) {
		this.good_name = good_name;
	}


	public String getGood_price() {
		return good_price;
	}


	public void setGood_price(String good_price) {
		this.good_price = good_price;
	}


	public String getGood_number() {
		return good_number;
	}


	public void setGood_number(String good_number) {
		this.good_number = good_number;
	}


	public String getGood_type() {
		return good_type;
	}


	public void setGood_type(String good_type) {
		this.good_type = good_type;
	}


	public String getOrder_create_time() {
		return order_create_time;
	}


	public void setOrder_create_time(String order_create_time) {
		this.order_create_time = order_create_time;
	}


	public String getOrder_pay_time() {
		return order_pay_time;
	}


	public void setOrder_pay_time(String order_pay_time) {
		this.order_pay_time = order_pay_time;
	}


	public String getNotify_url() {
		return notify_url;
	}


	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}


	public String getPay_status() {
		return pay_status;
	}


	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}


	public String getUser_id() {
		return user_id;
	}


	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}


	public String getTrade_no() {
		return trade_no;
	}


	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}


	@Override
	public String toString() {
		return "Order [order_id=" + order_id + ", order_price=" + order_price + ", good_no=" + good_no + ", good_name="
				+ good_name + ", good_price=" + good_price + ", good_number=" + good_number + ", good_type=" + good_type
				+ ", order_create_time=" + order_create_time + ", order_pay_time=" + order_pay_time + ", notify_url="
				+ notify_url + ", pay_status=" + pay_status + ", user_id=" + user_id + ", trade_no=" + trade_no + "]";
	}


	
	

}
