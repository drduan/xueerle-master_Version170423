package com.neusoft.sample.Ctrl.yangkangkang;

/**
 * 英语语法学习
 */
public class EnglishYuFaStudy {
	private String itemNo;
	private String zhangName;//章名
	private String englishYuFa;//英语语法名称
	private String englishYuFaNo;//英语语法编号
	
	
	
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public String getZhangName() {
		return zhangName;
	}
	public void setZhangName(String zhangName) {
		this.zhangName = zhangName;
	}
	public String getEnglishYuFa() {
		return englishYuFa;
	}
	public void setEnglishYuFa(String englishYuFa) {
		this.englishYuFa = englishYuFa;
	}
	public String getEnglishYuFaNo() {
		return englishYuFaNo;
	}
	public void setEnglishYuFaNo(String englishYuFaNo) {
		this.englishYuFaNo = englishYuFaNo;
	}
	
	
	@Override
	public String toString() {
		return "EnglishYuFaStudy [itemNo=" + itemNo + ", zhangName=" + zhangName + ", EnglishYuFa=" + englishYuFa
				+ ", EnglishYuFaNo=" + englishYuFaNo + "]";
	}
	
}
