package com.neusoft.sample.View.AoSaiTest;

import android.os.Parcel;
import android.os.Parcelable;

public class Aosai_Question implements Parcelable,Comparable<Aosai_Question>{
	private String choiceA;

	private String choiceB;

	private String choiceC;

	private String choiceD;

	private String itemNo;

	private String rightAnswer;

	private String stem;

	private String summary;

	private boolean usedAnaSumPDF;

	private boolean usedStemPDF;

	private String xiangXiJiexi;
	private boolean isStemPic;        	//题干是否有图片
	private String stemPicName;     	//题干图片名称


	protected Aosai_Question(Parcel in) {
		choiceA = in.readString();
		choiceB = in.readString();
		choiceC = in.readString();
		choiceD = in.readString();
		itemNo = in.readString();
		rightAnswer = in.readString();
		stem = in.readString();
		summary = in.readString();
		usedAnaSumPDF = in.readByte() != 0;
		usedStemPDF = in.readByte() != 0;
		xiangXiJiexi = in.readString();
		isStemPic = in.readByte() != 0;
		stemPicName = in.readString();
		myChoice = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(choiceA);
		dest.writeString(choiceB);
		dest.writeString(choiceC);
		dest.writeString(choiceD);
		dest.writeString(itemNo);
		dest.writeString(rightAnswer);
		dest.writeString(stem);
		dest.writeString(summary);
		dest.writeByte((byte) (usedAnaSumPDF ? 1 : 0));
		dest.writeByte((byte) (usedStemPDF ? 1 : 0));
		dest.writeString(xiangXiJiexi);
		dest.writeByte((byte) (isStemPic ? 1 : 0));
		dest.writeString(stemPicName);
		dest.writeString(myChoice);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<Aosai_Question> CREATOR = new Creator<Aosai_Question>() {
		@Override
		public Aosai_Question createFromParcel(Parcel in) {
			return new Aosai_Question(in);
		}

		@Override
		public Aosai_Question[] newArray(int size) {
			return new Aosai_Question[size];
		}
	};

	public boolean getIsStemPic() {
		return isStemPic;
	}
	public void setStemPic(boolean stemPic) {
		isStemPic = stemPic;
	}



	public String getStemPicName() {
		return stemPicName;
	}

	public void setStemPicName(String stemPicName) {
		this.stemPicName = stemPicName;
	}

	public Aosai_Question(){

 }

	public String getChoiceA() {
		return choiceA;
	}

	public void setChoiceA(String choiceA) {
		this.choiceA = choiceA;
	}

	public String getChoiceB() {
		return choiceB;
	}

	public void setChoiceB(String choiceB) {
		this.choiceB = choiceB;
	}

	public String getChoiceC() {
		return choiceC;
	}

	public void setChoiceC(String choiceC) {
		this.choiceC = choiceC;
	}

	public String getChoiceD() {
		return choiceD;
	}

	public void setChoiceD(String choiceD) {
		this.choiceD = choiceD;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getRightAnswer() {
		return rightAnswer;
	}

	public void setRightAnswer(String rightAnswer) {
		this.rightAnswer = rightAnswer;
	}

	public String getStem() {
		return stem;
	}

	public void setStem(String stem) {
		this.stem = stem;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public boolean isUsedAnaSumPDF() {
		return usedAnaSumPDF;
	}

	public void setUsedAnaSumPDF(boolean usedAnaSumPDF) {
		this.usedAnaSumPDF = usedAnaSumPDF;
	}

	public boolean isUsedStemPDF() {
		return usedStemPDF;
	}

	public void setUsedStemPDF(boolean usedStemPDF) {
		this.usedStemPDF = usedStemPDF;
	}

	public String getXiangXiJiexi() {
		return xiangXiJiexi;
	}

	public void setXiangXiJiexi(String xiangXiJiexi) {
		this.xiangXiJiexi = xiangXiJiexi;
	}

	public String  getMyChoice() {
		return myChoice;
	}

	public void setMyChoice(String myChoice) {
		this.myChoice = myChoice;
	}

	private String myChoice;

	public boolean judege(){

        //my choice 是一个选项
		return rightAnswer.equals(myChoice);

	}




	@Override
	public int compareTo(Aosai_Question another) {
		if (Integer.valueOf(itemNo.substring(9)).intValue()<Integer.valueOf(another.itemNo.substring(9)).intValue())return -1;
		else  if (Integer.valueOf(itemNo.substring(9)).intValue()==Integer.valueOf(another.itemNo.substring(9)).intValue())return 0;
		else return 1;
	}
}