package com.neusoft.sample.Ctrl.yangkangkang;


import android.os.Build;
import android.util.Log;

import java.util.Comparator;

/**
 * 听写答案
 */
public class TingXieAnswer implements Comparable<TingXieAnswer> {

	public  String itemNo;     		//听写答案序号
	public  String speed;      		//速度
	public  String tingXieOrder;	//听写顺序号
	public  String tingXieContent;  //听写内容
	public  String tingXieExplain1; //听写解释1
	public  String tingXieExplain2; //听写解释2
	public  String studyOrder;	    //学习顺序号
	
	
	
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	public String getTingXieOrder() {
		return tingXieOrder;
	}
	public void setTingXieOrder(String tingXieOrder) {
		this.tingXieOrder = tingXieOrder;
	}
	public String getTingXieContent() {
		return tingXieContent;
	}
	public void setTingXieContent(String tingXieContent) {
		this.tingXieContent = tingXieContent;
	}
	public String getTingXieExplain1() {
		return tingXieExplain1;
	}
	public void setTingXieExplain1(String tingXieExplain1) {
		this.tingXieExplain1 = tingXieExplain1;
	}
	public String getTingXieExplain2() {
		return tingXieExplain2;
	}
	public void setTingXieExplain2(String tingXieExplain2) {
		this.tingXieExplain2 = tingXieExplain2;
	}
	public String getStudyOrder() {
		return studyOrder;
	}
	public void setStudyOrder(String studyOrder) {
		this.studyOrder = studyOrder;
	}
	
	public    String []  get_tingxie(){
		String []aa = new String[]{tingXieOrder,tingXieContent,tingXieExplain1,tingXieExplain2};
		return aa;
	}


	@Override
	public int compareTo(TingXieAnswer another) {
		String aa = another.getItemNo();
		Log.d("@@", "aah" + aa);
		Log.d("@@", " aa" + aa + "tingXieOrder" + another.getItemNo() + "tingXieContent" + another.getTingXieContent() + another.getStudyOrder());
		Log.d("@@", "aa.length" + aa.length());
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			return Integer.compare(Integer.valueOf(itemNo), Integer.valueOf(aa));
		}
		return 0;
	}

}
