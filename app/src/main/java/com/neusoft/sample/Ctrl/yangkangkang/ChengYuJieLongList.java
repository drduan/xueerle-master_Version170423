package com.neusoft.sample.Ctrl.yangkangkang;

public class ChengYuJieLongList implements Comparable<ChengYuJieLongList> {
	
	private String itemNo;     //成语接龙编号
	private String cheng1;     //成语1
	private String cheng2;     //成语2
	private String cheng3;     //成语3
	private String cheng4;     //成语4
	private String cheng5;     //成语5
	private String cheng6;     //成语6
	private String cheng7;     //成语7
	private String cheng8;     //成语8
	private String cheng9;     //成语9
	private String cheng10;    //成语10
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public String getCheng1() {
		return cheng1;
	}
	public void setCheng1(String cheng1) {
		this.cheng1 = cheng1;
	}
	public String getCheng2() {
		return cheng2;
	}
	public void setCheng2(String cheng2) {
		this.cheng2 = cheng2;
	}
	public String getCheng3() {
		return cheng3;
	}
	public void setCheng3(String cheng3) {
		this.cheng3 = cheng3;
	}
	public String getCheng4() {
		return cheng4;
	}
	public void setCheng4(String cheng4) {
		this.cheng4 = cheng4;
	}
	public String getCheng5() {
		return cheng5;
	}
	public void setCheng5(String cheng5) {
		this.cheng5 = cheng5;
	}
	public String getCheng6() {
		return cheng6;
	}
	public void setCheng6(String cheng6) {
		this.cheng6 = cheng6;
	}
	public String getCheng7() {
		return cheng7;
	}
	public void setCheng7(String cheng7) {
		this.cheng7 = cheng7;
	}
	public String getCheng8() {
		return cheng8;
	}
	public void setCheng8(String cheng8) {
		this.cheng8 = cheng8;
	}
	public String getCheng9() {
		return cheng9;
	}
	public void setCheng9(String cheng9) {
		this.cheng9 = cheng9;
	}
	public String getCheng10() {
		return cheng10;
	}
	public void setCheng10(String cheng10) {
		this.cheng10 = cheng10;
	}

	@Override
	public int compareTo(ChengYuJieLongList another) {
		//从下表为7开始往后
		if (Integer.valueOf(itemNo.substring(7))<Integer.valueOf(another.itemNo.substring(7)))return -1;
		else if (Integer.valueOf(itemNo.substring(7))==Integer.valueOf(another.itemNo.substring(7)))
			return 0;
		else return 1;
	}
}
