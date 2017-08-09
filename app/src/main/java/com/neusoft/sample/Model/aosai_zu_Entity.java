package com.neusoft.sample.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wangyujie on 2016/9/5.
 */
public class aosai_zu_Entity implements Parcelable,Comparable<aosai_zu_Entity>{
    private String choiceA;//答案A
    private String choiceB;//答案B
    private String choiceC;//答案C
    private String choiceD;//答案D

    private String itemNo;//奥赛题目项编号

    private String rightAnswer;	//正确答案

    private String stem;//题干

    private String summary;//技巧总结

    private boolean usedAnaSumPDF;//解析总结是否用PDF   0 无  1有

    private boolean usedStemPDF;//题干是否用PDF   0 无  1有

    private String xiangXiJiexi;//试题解析

    private boolean isStemPic;        	//题干是否有图片
    private String stemPicName;     	//题干图片名称

    protected aosai_zu_Entity(Parcel in) {
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
    }

    public static final Creator<aosai_zu_Entity> CREATOR = new Creator<aosai_zu_Entity>() {
        @Override
        public aosai_zu_Entity createFromParcel(Parcel in) {
            return new aosai_zu_Entity(in);
        }

        @Override
        public aosai_zu_Entity[] newArray(int size) {
            return new aosai_zu_Entity[size];
        }
    };

    public boolean isUsedAnaSumPDF() {
        return usedAnaSumPDF;
    }

    public boolean isUsedStemPDF() {
        return usedStemPDF;
    }

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



    public aosai_zu_Entity(){

     }





    public void setChoiceA(String choiceA){
        this.choiceA = choiceA;
    }
    public String getChoiceA(){
        return this.choiceA;
    }
    public void setChoiceB(String choiceB){
        this.choiceB = choiceB;
    }
    public String getChoiceB(){
        return this.choiceB;
    }
    public void setChoiceC(String choiceC){
        this.choiceC = choiceC;
    }
    public String getChoiceC(){
        return this.choiceC;
    }
    public void setChoiceD(String choiceD){
        this.choiceD = choiceD;
    }
    public String getChoiceD(){
        return this.choiceD;
    }
    public void setItemNo(String itemNo){
        this.itemNo = itemNo;
    }
    public String getItemNo(){
        return this.itemNo;
    }
    public void setRightAnswer(String rightAnswer){
        this.rightAnswer = rightAnswer;
    }
    public String getRightAnswer(){
        return this.rightAnswer;
    }
    public void setStem(String stem){
        this.stem = stem;
    }
    public String getStem(){
        return this.stem;
    }
    public void setSummary(String summary){
        this.summary = summary;
    }
    public String getSummary(){
        return this.summary;
    }
    public void setUsedAnaSumPDF(boolean usedAnaSumPDF){
        this.usedAnaSumPDF = usedAnaSumPDF;
    }
    public boolean getUsedAnaSumPDF(){
        return this.usedAnaSumPDF;
    }
    public void setUsedStemPDF(boolean usedStemPDF){
        this.usedStemPDF = usedStemPDF;
    }
    public boolean getUsedStemPDF(){
        return this.usedStemPDF;
    }
    public void setXiangXiJiexi(String xiangXiJiexi){
        this.xiangXiJiexi = xiangXiJiexi;
    }
    public String getXiangXiJiexi(){
        return this.xiangXiJiexi;
    }

    @Override
    public int describeContents() {
        return 0;
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
    }

    @Override
    public int compareTo(aosai_zu_Entity another) {
        return itemNo.compareTo(another.getItemNo());
    }
}
