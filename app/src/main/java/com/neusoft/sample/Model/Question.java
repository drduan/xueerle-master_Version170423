package com.neusoft.sample.Model;

public class Question {
	private long _id;
	private String item_no;
	private String stem;
	private Boolean has_stem_pic;
	private String picName;
	private Boolean hasStemSound;
	private String soundName;
	private String choice1,choice2,choice3,choice4;
	private String theRight;

	public Boolean getHas_stem_pic() {
		return has_stem_pic;
	}

	public String getSoundName() {
		return soundName;
	}

	public void setSoundName(String soundName) {
		this.soundName = soundName;
	}

	public Boolean getHasStemSound() {
		return hasStemSound;
	}

	public void setHasStemSound(Boolean hasStemSound) {
		this.hasStemSound = hasStemSound;
	}

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public Question(long _id, String item_no , String stem, Boolean has_stem_pic ,String picName,Boolean hasStemSound,String soundName, String choice1, String choice2, String choice3, String choice4, String theRight) {
        super();

        this._id = _id;
        this.item_no = item_no;
        this.stem = stem;
        this.has_stem_pic = has_stem_pic;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.choice4 = choice4;
        this.theRight = theRight;
		this.picName=picName;
		this.hasStemSound=hasStemSound;
		this.soundName=soundName;
    }



    public long get_id() {
		return _id;
	}

	public void set_id(long _id) {
		this._id = _id;
	}

	public String getItem_no() {
		return item_no;
	}

	public void setItem_no(String item_no) {
		this.item_no = item_no;
	}

	public String getStem() {
		return stem;
	}

	public void setStem(String stem) {
		this.stem = stem;
	}

	public Boolean isHas_stem_pic() {
		return has_stem_pic;
	}

	public void setHas_stem_pic(Boolean  has_stem_pic) {
		this.has_stem_pic = has_stem_pic;
	}

	public String getChoice1() {
		return choice1;
	}

	public void setChoice1(String choice1) {
		this.choice1 = choice1;
	}

	public String getChoice2() {
		return choice2;
	}

	public void setChoice2(String choice2) {
		this.choice2 = choice2;
	}

	public String getChoice3() {
		return choice3;
	}

	public void setChoice3(String choice3) {
		this.choice3 = choice3;
	}

	public String getChoice4() {
		return choice4;
	}

	public void setChoice4(String choice4) {
		this.choice4 = choice4;
	}

	public String getTheRight() {
		return theRight;
	}

	public void setTheRight(String theRight) {
		this.theRight = theRight;
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
		return theRight.equals(myChoice);

	}


}