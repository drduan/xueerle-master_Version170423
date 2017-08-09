package com.neusoft.sample.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 单独查询错题的类
 */
public class ErrorSubject_two implements Parcelable,Comparable<ErrorSubject_two>{
	private  String  twoJieGou;
	private  String  itemNo;
	private  String  stem;             //题干
	private  boolean hasStemPic;       //题干是否有图片   0无  1有
	private  String  picName;          //图片名称
	private  boolean hasStemSound;     //题干是否有声音   0无  1有
	private  String  soundName;        //生命名称
	private  String  choiceA;          //答案A	 
	private  String  choiceB;          //答案B	
	private  String  choiceC;          //答案C
	private  String  choiceD;          //答案D
    private  String  rightAnswer ;     //正确答案	
    private  String  xiangXiJiexi;     //详细解析
    private  String  summary;          //技巧总结
	private  String  answer ;

	public ErrorSubject_two() {
	}

	protected ErrorSubject_two(Parcel in) {
		twoJieGou = in.readString();
		itemNo = in.readString();
		stem = in.readString();
		hasStemPic = in.readByte() != 0;
		picName = in.readString();
		hasStemSound = in.readByte() != 0;
		soundName = in.readString();
		choiceA = in.readString();
		choiceB = in.readString();
		choiceC = in.readString();
		choiceD = in.readString();
		rightAnswer = in.readString();
		xiangXiJiexi = in.readString();
		summary = in.readString();
		answer = in.readString();
	}

	public static final Creator<ErrorSubject_two> CREATOR = new Creator<ErrorSubject_two>() {
		@Override
		public ErrorSubject_two createFromParcel(Parcel in) {
			return new ErrorSubject_two(in);
		}

		@Override
		public ErrorSubject_two[] newArray(int size) {
			return new ErrorSubject_two[size];
		}
	};

	public String getTwoJieGou() {
		return twoJieGou;
	}
	public String getStem() {
		return stem;
	}
	public void setStem(String stem) {
		this.stem = stem;
	}
	public boolean isHasStemPic() {
		return hasStemPic;
	}
	public void setHasStemPic(boolean hasStemPic) {
		this.hasStemPic = hasStemPic;
	}
	public String getPicName() {
		return picName;
	}
	public void setPicName(String picName) {
		this.picName = picName;
	}
	public boolean isHasStemSound() {
		return hasStemSound;
	}
	public void setHasStemSound(boolean hasStemSound) {
		this.hasStemSound = hasStemSound;
	}
	public String getSoundName() {
		return soundName;
	}
	public void setSoundName(String soundName) {
		this.soundName = soundName;
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
	public String getRightAnswer() {
		return rightAnswer;
	}
	public void setRightAnswer(String rightAnswer) {
		this.rightAnswer = rightAnswer;
	}
	public String getXiangXiJiexi() {
		return xiangXiJiexi;
	}
	public void setXiangXiJiexi(String xiangXiJiexi) {
		this.xiangXiJiexi = xiangXiJiexi;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public void setTwoJieGou(String twoJieGou) {
		this.twoJieGou = twoJieGou;
	}
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	
	
	
	@Override
	public String toString() {
		return "Subject [twoJieGou=" + twoJieGou + ", itemNo=" + itemNo + ", stem=" + stem + ", hasStemPic="
				+ hasStemPic + ", picName=" + picName + ", hasStemSound=" + hasStemSound + ", soundName=" + soundName
				+ ", choiceA=" + choiceA + ", choiceB=" + choiceB + ", choiceC=" + choiceC + ", choiceD=" + choiceD
				+ ", rightAnswer=" + rightAnswer + ", xiangXiJiexi=" + xiangXiJiexi + ", summary=" + summary + "]";
	}


	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(twoJieGou);
		dest.writeString(itemNo);
		dest.writeString(stem);
		dest.writeByte((byte) (hasStemPic ? 1 : 0));
		dest.writeString(picName);
		dest.writeByte((byte) (hasStemSound ? 1 : 0));
		dest.writeString(soundName);
		dest.writeString(choiceA);
		dest.writeString(choiceB);
		dest.writeString(choiceC);
		dest.writeString(choiceD);
		dest.writeString(rightAnswer);
		dest.writeString(xiangXiJiexi);
		dest.writeString(summary);
		dest.writeString(answer);
	}



	@Override
	public int compareTo(ErrorSubject_two another) {

		if (Integer.valueOf(itemNo.substring(9)).intValue()<Integer.valueOf(another.itemNo.substring(9)).intValue())return -1;
		else  if (Integer.valueOf(itemNo.substring(9)).intValue()==Integer.valueOf(another.itemNo.substring(9)).intValue())return 0;
		else return 1;
	}
}
