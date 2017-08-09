package com.neusoft.sample.Ctrl.wenchengcheng;

import java.util.Date;

/**
 * 龙虎榜和用户表的vo
 */
public class LongHuUser {
	//integral积分
	private String integral_id;    		//龙虎榜排名id
	private String user_id;        		//用户id
	private Integer integral_number; 	//积分
	private Date month;                 //月份
	private String text;                //备注
	
	//用户t_ser
	private String real_name;    // 姓名
	private String email;        // email
	private String class_number; // 班级编号
	private String mobile;       //手机号
	private String password;     //密码
	private String role;         //角色    1 ：学生     2 :家长     0 ：老师
	private String city_number;  //城市编号 
	private Date indate;         //产品有效期
	
	
	
	private String user_icon_url;        //用户头像图片
	private String gender;   			 //性别
	private String qq_number; 			 //qq号码
	private String weixin_number; 		 //微信号码
	private String motto;   			 //个性签名
	private String recipient;  			 //收货人
	private String address;  			 //详细地址
	private String phone;   			 //手机号
	private String zip_code; 			 //邮编地址
	public String getIntegral_id() {
		return integral_id;
	}
	public void setIntegral_id(String integral_id) {
		this.integral_id = integral_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public Integer getIntegral_number() {
		return integral_number;
	}
	public void setIntegral_number(Integer integral_number) {
		this.integral_number = integral_number;
	}
	public Date getMonth() {
		return month;
	}
	public void setMonth(Date month) {
		this.month = month;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getReal_name() {
		return real_name;
	}
	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getClass_number() {
		return class_number;
	}
	public void setClass_number(String class_number) {
		this.class_number = class_number;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getCity_number() {
		return city_number;
	}
	public void setCity_number(String city_number) {
		this.city_number = city_number;
	}
	public Date getIndate() {
		return indate;
	}
	public void setIndate(Date indate) {
		this.indate = indate;
	}
	public String getUser_icon_url() {
		return user_icon_url;
	}
	public void setUser_icon_url(String user_icon_url) {
		this.user_icon_url = user_icon_url;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getQq_number() {
		return qq_number;
	}
	public void setQq_number(String qq_number) {
		this.qq_number = qq_number;
	}
	public String getWeixin_number() {
		return weixin_number;
	}
	public void setWeixin_number(String weixin_number) {
		this.weixin_number = weixin_number;
	}
	public String getMotto() {
		return motto;
	}
	public void setMotto(String motto) {
		this.motto = motto;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getZip_code() {
		return zip_code;
	}
	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}
	@Override
	public String toString() {
		return "LongHuUser [integral_id=" + integral_id + ", user_id=" + user_id + ", integral_number="
				+ integral_number + ", month=" + month + ", text=" + text + ", real_name=" + real_name + ", email="
				+ email + ", class_number=" + class_number + ", mobile=" + mobile + ", password=" + password + ", role="
				+ role + ", city_number=" + city_number + ", indate=" + indate + ", user_icon_url=" + user_icon_url
				+ ", gender=" + gender + ", qq_number=" + qq_number + ", weixin_number=" + weixin_number + ", motto="
				+ motto + ", recipient=" + recipient + ", address=" + address + ", phone=" + phone + ", zip_code="
				+ zip_code + "]";
	}
	
	
	
	
}
