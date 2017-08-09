package com.neusoft.sample.Ctrl;


import android.os.Parcel;
import android.os.Parcelable;


/**
 *	优秀作文,好书推荐,读后感 实体
 */
public class Composition implements Parcelable {
	
	private String 		grade;				//年级编号
	private String		term;				//学期
	private String      lx;					//类型
	private String		lxNo;				//类型顺序号
	private String		pic;				//图片名
	private String		chapterSequence;	//HTML文件名
	private String		title1;				//标题1
	private String		title2;				//标题2
	private String		title3;				//标题3
	private String		userNumber;			//用户号
    public Composition(){

	}
	public Composition(String grade, String term, String lx, String lxNo, String pic, String chapterSequence, String title1, String title2, String title3, String userNumber) {
		this.grade = grade;
		this.term = term;
		this.lx = lx;
		this.lxNo = lxNo;
		this.pic = pic;
		this.chapterSequence = chapterSequence;
		this.title1 = title1;
		this.title2 = title2;
		this.title3 = title3;
		this.userNumber = userNumber;
	}

	public Composition(Parcel in) {
		grade = in.readString();
		term = in.readString();
		lx = in.readString();
		lxNo = in.readString();
		pic = in.readString();
		chapterSequence = in.readString();
		title1 = in.readString();
		title2 = in.readString();
		title3 = in.readString();
		userNumber = in.readString();
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(grade);
		dest.writeString(term);
		dest.writeString(lx);
		dest.writeString(lxNo);
		dest.writeString(pic);
		dest.writeString(chapterSequence);
		dest.writeString(title1);
		dest.writeString(title2);
		dest.writeString(title3);
		dest.writeString(userNumber);
	}

	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getLx() {
		return lx;
	}
	public void setLx(String lx) {
		this.lx = lx;
	}
	public String getLxNo() {
		return lxNo;
	}
	public void setLxNo(String lxNo) {
		this.lxNo = lxNo;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getChapterSequence() {
		return chapterSequence;
	}
	public void setChapterSequence(String chapterSequence) {
		this.chapterSequence = chapterSequence;
	}
	public String getTitle1() {
		return title1;
	}
	public void setTitle1(String title1) {
		this.title1 = title1;
	}
	public String getTitle2() {
		return title2;
	}
	public void setTitle2(String title2) {
		this.title2 = title2;
	}
	public String getTitle3() {
		return title3;
	}
	public void setTitle3(String title3) {
		this.title3 = title3;
	}
	
	public String getUserNumber() {
		return userNumber;
	}
	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}


	@Override
	public int describeContents() {
		return 0;
	}


	public static final Creator<Composition> CREATOR = new Creator<Composition>() {
		@Override
		public Composition createFromParcel(Parcel in) {
			return new Composition(in);
		}

		@Override
		public Composition[] newArray(int size) {
			return new Composition[size];
		}
	};
}
