package com.neusoft.sample.Ctrl.yangkangkang;

public class XiaoXueTuoZhanGuShiCi implements Comparable<XiaoXueTuoZhanGuShiCi>{
	private String itemNo;    //小学拓展古诗词题目项编号
	private String poetry_name;//诗词名称
	private String writer;    //作者
	private String appreciate;//赏析
	private boolean isartistic; //是否有意境图 0无  1有
	private boolean poetic_voice; //是否有诗词语音   0无  1有
	private boolean appreciation_voice;//是否有赏析语音   0无  1有
	private boolean isNull;   //是否空行   0空  1非空
	private boolean isFamous; //是否是名句  0不是  1是
	private String word1;     //字1
	private String word2;     //字2
	private String word3;     //字3
	private String word4;     //字4
	private String word5;     //字5
	private String word6;     //字6
	private String word7;     //字7
	private String word8;     //字8
	private String word9;     //字9
	private String word10;    //字10
	private String word11;    //字11
	private String word12;    //字12
	private String pin1;      //拼音1
	private String pin2;      //拼音2
	private String pin3;      //拼音3
	private String pin4;      //拼音4
	private String pin5;      //拼音5
	private String pin6;      //拼音6
	private String pin7;      //拼音7
	private String pin8;      //拼音8
	private String pin9;      //拼音9
	private String pin10;     //拼音10
	private String pin11;     //拼音11
	private String pin12;     //拼音12
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public String getPoetry_name() {
		return poetry_name;
	}
	public void setPoetry_name(String poetry_name) {
		this.poetry_name = poetry_name;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getAppreciate() {
		return appreciate;
	}
	public void setAppreciate(String appreciate) {
		this.appreciate = appreciate;
	}
	public boolean isIsartistic() {
		return isartistic;
	}
	public void setIsartistic(boolean isartistic) {
		this.isartistic = isartistic;
	}
	public boolean isPoetic_voice() {
		return poetic_voice;
	}
	public void setPoetic_voice(boolean poetic_voice) {
		this.poetic_voice = poetic_voice;
	}
	public boolean isAppreciation_voice() {
		return appreciation_voice;
	}
	public void setAppreciation_voice(boolean appreciation_voice) {
		this.appreciation_voice = appreciation_voice;
	}
	public boolean isNull() {
		return isNull;
	}
	public void setNull(boolean isNull) {
		this.isNull = isNull;
	}
	public boolean isFamous() {
		return isFamous;
	}
	public void setFamous(boolean isFamous) {
		this.isFamous = isFamous;
	}
	public String getWord1() {
		return word1;
	}
	public void setWord1(String word1) {
		this.word1 = word1;
	}
	public String getWord2() {
		return word2;
	}
	public void setWord2(String word2) {
		this.word2 = word2;
	}
	public String getWord3() {
		return word3;
	}
	public void setWord3(String word3) {
		this.word3 = word3;
	}
	public String getWord4() {
		return word4;
	}
	public void setWord4(String word4) {
		this.word4 = word4;
	}
	public String getWord5() {
		return word5;
	}
	public void setWord5(String word5) {
		this.word5 = word5;
	}
	public String getWord6() {
		return word6;
	}
	public void setWord6(String word6) {
		this.word6 = word6;
	}
	public String getWord7() {
		return word7;
	}
	public void setWord7(String word7) {
		this.word7 = word7;
	}
	public String getWord8() {
		return word8;
	}
	public void setWord8(String word8) {
		this.word8 = word8;
	}
	public String getWord9() {
		return word9;
	}
	public void setWord9(String word9) {
		this.word9 = word9;
	}
	public String getWord10() {
		return word10;
	}
	public void setWord10(String word10) {
		this.word10 = word10;
	}
	public String getWord11() {
		return word11;
	}
	public void setWord11(String word11) {
		this.word11 = word11;
	}
	public String getWord12() {
		return word12;
	}
	public void setWord12(String word12) {
		this.word12 = word12;
	}
	public String getPin1() {
		return pin1;
	}
	public void setPin1(String pin1) {
		this.pin1 = pin1;
	}
	public String getPin2() {
		return pin2;
	}
	public void setPin2(String pin2) {
		this.pin2 = pin2;
	}
	public String getPin3() {
		return pin3;
	}
	public void setPin3(String pin3) {
		this.pin3 = pin3;
	}
	public String getPin4() {
		return pin4;
	}
	public void setPin4(String pin4) {
		this.pin4 = pin4;
	}
	public String getPin5() {
		return pin5;
	}
	public void setPin5(String pin5) {
		this.pin5 = pin5;
	}
	public String getPin6() {
		return pin6;
	}
	public void setPin6(String pin6) {
		this.pin6 = pin6;
	}
	public String getPin7() {
		return pin7;
	}
	public void setPin7(String pin7) {
		this.pin7 = pin7;
	}
	public String getPin8() {
		return pin8;
	}
	public void setPin8(String pin8) {
		this.pin8 = pin8;
	}
	public String getPin9() {
		return pin9;
	}
	public void setPin9(String pin9) {
		this.pin9 = pin9;
	}
	public String getPin10() {
		return pin10;
	}
	public void setPin10(String pin10) {
		this.pin10 = pin10;
	}
	public String getPin11() {
		return pin11;
	}
	public void setPin11(String pin11) {
		this.pin11 = pin11;
	}
	public String getPin12() {
		return pin12;
	}
	public void setPin12(String pin12) {
		this.pin12 = pin12;
	}



	////////实体排序
	@Override
	public int compareTo(XiaoXueTuoZhanGuShiCi another) {
		//从下表为7开始往后
	if (Integer.valueOf(itemNo.substring(7))<Integer.valueOf(another.itemNo.substring(7)))return -1;
		else if (Integer.valueOf(itemNo.substring(7))==Integer.valueOf(another.itemNo.substring(7)))
		return 0;
		else return 1;
	}
}
