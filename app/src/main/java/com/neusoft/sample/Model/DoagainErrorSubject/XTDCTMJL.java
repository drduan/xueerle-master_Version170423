package com.neusoft.sample.Model.DoagainErrorSubject;

import java.util.Date;

/**
 * 习题测试记录和系统测试题目表的vo
 */
public class XTDCTMJL {


	/**用户答错题目项**/
	private  String  xtdctm_id ;      // 用户答错题目项 ID
	private  String  examination_number ;           // 口算题卡题目项编号
	private  int     ismastered  ;                  // 是否后来做对过
	private  String  test_group_number; // 测试组编号
	private  String  user_id;           // 用户ID

	/**用户错题测试记录**/
	private  int  xtcsjl_id ;    // 用户重做错题测试记录
	private  String  answer ;
	private  Date  jl_datetime ;          // 做题时间
	
	/******表名*****/
	private  String  tableName ;  // 教材识别码

	
	
	
	public String getXtdctm_id() {
		return xtdctm_id;
	}

	public void setXtdctm_id(String xtdctm_id) {
		this.xtdctm_id = xtdctm_id;
	}

	public String getExamination_number() {
		return examination_number;
	}

	public void setExamination_number(String examination_number) {
		this.examination_number = examination_number;
	}

	public int getIsmastered() {
		return ismastered;
	}

	public void setIsmastered(int ismastered) {
		this.ismastered = ismastered;
	}

	public String getTest_group_number() {
		return test_group_number;
	}

	public void setTest_group_number(String test_group_number) {
		this.test_group_number = test_group_number;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public int getXtcsjl_id() {
		return xtcsjl_id;
	}

	public void setXtcsjl_id(int xtcsjl_id) {
		this.xtcsjl_id = xtcsjl_id;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Date getJl_datetime() {
		return jl_datetime;
	}

	public void setJl_datetime(Date jl_datetime) {
		this.jl_datetime = jl_datetime;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public String toString() {
		return "XTDCTMJL [xtdctm_id=" + xtdctm_id + ", examination_number=" + examination_number + ", ismastered="
				+ ismastered + ", test_group_number=" + test_group_number + ", user_id=" + user_id + ", xtcsjl_id="
				+ xtcsjl_id + ", answer=" + answer + ", jl_datetime=" + jl_datetime + ", tableName=" + tableName + "]";
	}
	
	
	
}
