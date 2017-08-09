package com.neusoft.sample.Ctrl.wenchengcheng;

/**
 * Created by AstroBoy on 2017/1/8.
 */

public class Class_bean {

    private String	classNum;
    private String	className;
    private String	peopleNum;
    private String	peopleRegister;
    private String	peopleBuy;

    public Class_bean() {
    }

    public String getClassNum() {
        return classNum;
    }

    public Class_bean setClassNum(String classNum) {
        this.classNum = classNum;
        return this;
    }

    public String getClassName() {
        return className;
    }

    public Class_bean setClassName(String className) {
        this.className = className;
        return this;
    }

    public String getPeopleNum() {
        return peopleNum;
    }

    public Class_bean setPeopleNum(String peopleNum) {
        this.peopleNum = peopleNum;
        return this;
    }

    public String getPeopleRegister() {
        return peopleRegister;
    }

    public Class_bean setPeopleRegister(String peopleRegister) {
        this.peopleRegister = peopleRegister;
        return this;
    }

    public String getPeopleBuy() {
        return peopleBuy;
    }

    public Class_bean setPeopleBuy(String peopleBuy) {
        this.peopleBuy = peopleBuy;
        return this;
    }

    @Override
    public String toString() {
        return "Class_bean{" +
                "classNum='" + classNum + '\'' +
                ", className='" + className + '\'' +
                ", peopleNum='" + peopleNum + '\'' +
                ", peopleRegister='" + peopleRegister + '\'' +
                ", peopleBuy='" + peopleBuy + '\'' +
                '}';
    }

}
