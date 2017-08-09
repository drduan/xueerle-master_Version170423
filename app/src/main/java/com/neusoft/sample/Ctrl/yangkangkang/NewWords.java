package com.neusoft.sample.Ctrl.yangkangkang;

public class NewWords implements Comparable<NewWords>{    // 生字学习
	
	private  String  itemNo ;   // 语文课本生字知识项编号        ps:教材编号（5）+章（2）b
	private  String  zhangName; //章名
	private  int  sequence ;    // 学习顺序号
	private  String  word ;     // 生字
	private  String  wordSpell ; // 生字拼音
	private  String  wordGroup1 ;    // 生字词组1
	private  String  wordGroup2 ;    // 生字词组2
	private  String  wordGroup3 ;    // 生字词组3
	private  String  wordGroup4 ;    // 生字词组4
	private  String  poem ;          // 古诗词
	private  String  poemOrigin ;    // 古诗词出处
	private  boolean hasOutFill ;    // 是否有轮廓描红文件          0否，1是
	private  boolean hasWordSound;   // 是否有字词语音                  0否，1是
	
	
	public  NewWords(){}  // 构造方法

	

	public String getZhangName() {
		return zhangName;
	}



	public void setZhangName(String zhangName) {
		this.zhangName = zhangName;
	}



	public String getItemNo() {
		return itemNo;
	}


	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}


	public int getSequence() {
		return sequence;
	}


	public void setSequence(int sequence) {
		this.sequence = sequence;
	}


	public String getWord() {
		return word;
	}


	public void setWord(String word) {
		this.word = word;
	}


	public String getWordSpell() {
		return wordSpell;
	}


	public void setWordSpell(String wordSpell) {
		this.wordSpell = wordSpell;
	}


	public String getWordGroup1() {
		return wordGroup1;
	}


	public void setWordGroup1(String wordGroup1) {
		this.wordGroup1 = wordGroup1;
	}


	public String getWordGroup2() {
		return wordGroup2;
	}


	public void setWordGroup2(String wordGroup2) {
		this.wordGroup2 = wordGroup2;
	}


	public String getWordGroup3() {
		return wordGroup3;
	}


	public void setWordGroup3(String wordGroup3) {
		this.wordGroup3 = wordGroup3;
	}


	public String getWordGroup4() {
		return wordGroup4;
	}


	public void setWordGroup4(String wordGroup4) {
		this.wordGroup4 = wordGroup4;
	}


	public String getPoem() {
		return poem;
	}


	public void setPoem(String poem) {
		this.poem = poem;
	}


	public String getPoemOrigin() {
		return poemOrigin;
	}


	public void setPoemOrigin(String poemOrigin) {
		this.poemOrigin = poemOrigin;
	}


	public boolean isHasOutFill() {
		return hasOutFill;
	}


	public void setHasOutFill(boolean hasOutFill) {
		this.hasOutFill = hasOutFill;
	}


	public boolean isHasWordSound() {
		return hasWordSound;
	}


	public void setHasWordSound(boolean hasWordSound) {
		this.hasWordSound = hasWordSound;
	}

	@Override
	public int compareTo(NewWords another) {
		if (Integer.valueOf(sequence)<Integer.valueOf(another.sequence))return -1;
		else if (Integer.valueOf(sequence)==Integer.valueOf(another.sequence))
			return 0;
		else return 1;
	}
}
