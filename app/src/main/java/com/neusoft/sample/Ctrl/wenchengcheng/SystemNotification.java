package com.neusoft.sample.Ctrl.wenchengcheng;


/**
 * 系统通知公告的实体
 */
public class SystemNotification {

	private String 		notification_id;			//通知id
	private String 		titlerNo;					//通知编号
	private String 		title;						//通知标题
	private String 		chapterSequence;			//通知内容
	private String 		chapterSequenceName;		//通知发布人
	private String 		send_time;						//发布时间
	
	
	public String getNotification_id() {
		return notification_id;
	}
	public void setNotification_id(String notification_id) {
		this.notification_id = notification_id;
	}
	public String getTitlerNo() {
		return titlerNo;
	}
	public void setTitlerNo(String titlerNo) {
		this.titlerNo = titlerNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getChapterSequence() {
		return chapterSequence;
	}
	public void setChapterSequence(String chapterSequence) {
		this.chapterSequence = chapterSequence;
	}
	public String getChapterSequenceName() {
		return chapterSequenceName;
	}
	public void setChapterSequenceName(String chapterSequenceName) {
		this.chapterSequenceName = chapterSequenceName;
	}
	public String getSend_time() {
		return send_time;
	}
	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}
	
}
