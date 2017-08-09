package com.neusoft.sample.View.xel_mine.MyHomeWork;

/**
 * 老师查看自己发布的作业实体
 */
public class HomeWork {

	private String 		home_word_id;			//家庭作业id
	private String 		classno;				//班级编号
	private String 		subject;				//科目
	private String 		homework;				//作业内容
	private String 		jobperson;				//发布人
	private String 		date1;					// 作业时间
	private String 		date2;					//发布日期
	public String getHome_word_id() {
		return home_word_id;
	}
	public void setHome_word_id(String home_word_id) {
		this.home_word_id = home_word_id;
	}
	public String getClassno() {
		return classno;
	}
	public void setClassno(String classno) {
		this.classno = classno;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getHomework() {
		return homework;
	}
	public void setHomework(String homework) {
		this.homework = homework;
	}
	public String getJobperson() {
		return jobperson;
	}
	public void setJobperson(String jobperson) {
		this.jobperson = jobperson;
	}
	public String getDate1() {
		return date1;
	}
	public void setDate1(String date1) {
		this.date1 = date1;
	}
	public String getDate2() {
		return date2;
	}
	public void setDate2(String date2) {
		this.date2 = date2;
	}
	
	
	
	
	@Override
	public String toString() {
		return "HomeWork [home_word_id=" + home_word_id + ", classno=" + classno + ", subject=" + subject
				+ ", homework=" + homework + ", jobperson=" + jobperson + ", date1=" + date1 + ", date2=" + date2 + "]";
	}
	
	
}
