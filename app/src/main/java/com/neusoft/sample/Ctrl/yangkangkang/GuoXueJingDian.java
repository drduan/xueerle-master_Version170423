package com.neusoft.sample.Ctrl.yangkangkang;

/**
 * 国学经典
 */
public class GuoXueJingDian implements Comparable<GuoXueJingDian> {
	private String itemNo; // 国学经典学习项编号
	private boolean isFirst; // 是否段落开头
	private boolean isfamous; // 是否是名句
	private String word1; // 字1
	private String word2; // 字2
	private String word3; // 字3
	private String word4; // 字4
	private String word5; // 字5
	private String word6; // 字6
	private String word7; // 字7
	private String word8; // 字8
	private String word9; // 字9
	private String word10; // 字10
	private String word11; // 字11
	private String word12; // 字12
	private String word13; // 字13
	private String word14; // 字14
	private String word15; // 字15
	private String word16; // 字16
	private String word17; // 字17
	private String word18; // 字18
	private String word19; // 字19
	private String word20; // 字20
	private String word21; // 字21
	private String word22; // 字22
	private String word23; // 字23
	private String word24; // 字24
	private String word25; // 字25
	private String word26; // 字26
	private String pin1; // 拼音1
	private String pin2; // 拼音2
	private String pin3; // 拼音3
	private String pin4; // 拼音4
	private String pin5; // 拼音5
	private String pin6; // 拼音6
	private String pin7; // 拼音7
	private String pin8; // 拼音8
	private String pin9; // 拼音9
	private String pin10; // 拼音10
	private String pin11; // 拼音11
	private String pin12; // 拼音12
	private String pin13; // 拼音13
	private String pin14; // 拼音14
	private String pin15; // 拼音15
	private String pin16; // 拼音16
	private String pin17; // 拼音17
	private String pin18; // 拼音18
	private String pin19; // 拼音19
	private String pin20; // 拼音20
	private String pin21; // 拼音21
	private String pin22; // 拼音22
	private String pin23; // 拼音23
	private String pin24; // 拼音24
	private String pin25; // 拼音25
	private String pin26; // 拼音26
	public String[] getWord() {
		String[] strs;
		strs = new String[]{word1,word2,word3,word4,word5,word6,word7,word8,word9,word10,word11,word12,word13,
				word14,word15,word16,word17,word18,word19,word20,word21,word22,word23,word24,word25,word26};
		return strs;
	}
	public String[] getPin() {
		String[] strs;
		strs = new String[]{pin1,pin2,pin3,pin4,pin5,pin6,pin7,pin8,pin9,pin10,pin11,pin12,pin13,
				pin14,pin15,pin16,pin17,pin18,pin19,pin20,pin21,pin22,pin23,pin24,pin25,pin26};
		return strs;
	}
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public boolean isFirst() {
		return isFirst;
	}
	public void setFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}
	public boolean isIsfamous() {
		return isfamous;
	}
	public void setIsfamous(boolean isfamous) {
		this.isfamous = isfamous;
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
	public String getWord13() {
		return word13;
	}
	public void setWord13(String word13) {
		this.word13 = word13;
	}
	public String getWord14() {
		return word14;
	}
	public void setWord14(String word14) {
		this.word14 = word14;
	}
	public String getWord15() {
		return word15;
	}
	public void setWord15(String word15) {
		this.word15 = word15;
	}
	public String getWord16() {
		return word16;
	}
	public void setWord16(String word16) {
		this.word16 = word16;
	}
	public String getWord17() {
		return word17;
	}
	public void setWord17(String word17) {
		this.word17 = word17;
	}
	public String getWord18() {
		return word18;
	}
	public void setWord18(String word18) {
		this.word18 = word18;
	}
	public String getWord19() {
		return word19;
	}
	public void setWord19(String word19) {
		this.word19 = word19;
	}
	public String getWord20() {
		return word20;
	}
	public void setWord20(String word20) {
		this.word20 = word20;
	}
	public String getWord21() {
		return word21;
	}
	public void setWord21(String word21) {
		this.word21 = word21;
	}
	public String getWord22() {
		return word22;
	}
	public void setWord22(String word22) {
		this.word22 = word22;
	}
	public String getWord23() {
		return word23;
	}
	public void setWord23(String word23) {
		this.word23 = word23;
	}
	public String getWord24() {
		return word24;
	}
	public void setWord24(String word24) {
		this.word24 = word24;
	}
	public String getWord25() {
		return word25;
	}
	public void setWord25(String word25) {
		this.word25 = word25;
	}
	public String getWord26() {
		return word26;
	}
	public void setWord26(String word26) {
		this.word26 = word26;
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
	public String getPin13() {
		return pin13;
	}
	public void setPin13(String pin13) {
		this.pin13 = pin13;
	}
	public String getPin14() {
		return pin14;
	}
	public void setPin14(String pin14) {
		this.pin14 = pin14;
	}
	public String getPin15() {
		return pin15;
	}
	public void setPin15(String pin15) {
		this.pin15 = pin15;
	}
	public String getPin16() {
		return pin16;
	}
	public void setPin16(String pin16) {
		this.pin16 = pin16;
	}
	public String getPin17() {
		return pin17;
	}
	public void setPin17(String pin17) {
		this.pin17 = pin17;
	}
	public String getPin18() {
		return pin18;
	}
	public void setPin18(String pin18) {
		this.pin18 = pin18;
	}
	public String getPin19() {
		return pin19;
	}
	public void setPin19(String pin19) {
		this.pin19 = pin19;
	}
	public String getPin20() {
		return pin20;
	}
	public void setPin20(String pin20) {
		this.pin20 = pin20;
	}
	public String getPin21() {
		return pin21;
	}
	public void setPin21(String pin21) {
		this.pin21 = pin21;
	}
	public String getPin22() {
		return pin22;
	}
	public void setPin22(String pin22) {
		this.pin22 = pin22;
	}
	public String getPin23() {
		return pin23;
	}
	public void setPin23(String pin23) {
		this.pin23 = pin23;
	}
	public String getPin24() {
		return pin24;
	}
	public void setPin24(String pin24) {
		this.pin24 = pin24;
	}
	public String getPin25() {
		return pin25;
	}
	public void setPin25(String pin25) {
		this.pin25 = pin25;
	}
	public String getPin26() {
		return pin26;
	}
	public void setPin26(String pin26) {
		this.pin26 = pin26;
	}

	@Override
	public int compareTo(GuoXueJingDian another) {

		//从下表为7开始往后
		if (Integer.valueOf(itemNo.substring(7))<Integer.valueOf(another.itemNo.substring(7)))return -1;
		else if (Integer.valueOf(itemNo.substring(7))==Integer.valueOf(another.itemNo.substring(7)))
			return 0;
		else return 1;
	}

}
