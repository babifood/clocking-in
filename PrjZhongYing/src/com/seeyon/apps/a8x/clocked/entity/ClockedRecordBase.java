package com.seeyon.apps.a8x.clocked.entity;

import java.io.Serializable;

/*
 * 员工打卡记录-实体
 */
public class ClockedRecordBase implements Serializable {
	private static final long serialVersionUID = 1320516018807719880L;

	// id
	private long id;
	// 审核状态
	private int state;
	// 姓名
	private String memberID;
	// 打卡开始时间
	private java.sql.Date clockDate;
	// 上班时间
	private String startTime;
	// 上班时间
	private String endTime;
	// 部门
	private String dept;
	// 单位机构
	private String organ;
	// 工号
	private String workNum;
	// 考勤机姓名
	private String cardName;
	// 年度
	private int year;
	// 月份
	private int month;
	// 说明
	private String remark;

	/**
	 * Default Constructor
	 */
	public ClockedRecordBase() {
		// do nothing
	}

	// Bean methods......
	public long getId() {
		return this.id;
	}

	public void setId(long theId) {
		this.id = theId;
	}

	public int getState() {
		return this.state;
	}

	public void setState(int theState) {
		this.state = theState;
	}

	public String getMemberID() {
		return this.memberID;
	}

	public void setMemberID(String theMemberID) {
		this.memberID = theMemberID;
	}

	public java.sql.Date getClockDate() {
		return this.clockDate;
	}

	public void setClockDate(java.sql.Date theClockDate) {
		this.clockDate = theClockDate;
	}

	public String getStartTime() {
		return this.startTime;
	}

	public void setStartTime(String theStartTime) {
		this.startTime = theStartTime;
	}

	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(String theEndTime) {
		this.endTime = theEndTime;
	}

	public String getDept() {
		return this.dept;
	}

	public void setDept(String theDept) {
		this.dept = theDept;
	}

	public String getOrgan() {
		return this.organ;
	}

	public void setOrgan(String theOrgan) {
		this.organ = theOrgan;
	}

	public String getWorkNum() {
		return this.workNum;
	}

	public void setWorkNum(String theWorkNum) {
		this.workNum = theWorkNum;
	}

	public String getCardName() {
		return this.cardName;
	}

	public void setCardName(String theCardName) {
		this.cardName = theCardName;
	}

	public int getYear() {
		return this.year;
	}

	public void setYear(int theYear) {
		this.year = theYear;
	}

	public int getMonth() {
		return this.month;
	}

	public void setMonth(int theMonth) {
		this.month = theMonth;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String theRemark) {
		this.remark = theRemark;
	}

	public String toString() {
		StringBuffer returnString = new StringBuffer();
		returnString.append("ClockedRecord[");
		// Set member attributes
		returnString.append("id = " + this.id + ";\n");
		returnString.append("state = " + this.state + ";\n");
		returnString.append("memberID = " + this.memberID + ";\n");
		returnString.append("clockDate = " + this.clockDate + ";\n");
		returnString.append("startTime = " + this.startTime + ";\n");
		returnString.append("endTime = " + this.endTime + ";\n");
		returnString.append("dept = " + this.dept + ";\n");
		returnString.append("organ = " + this.organ + ";\n");
		returnString.append("workNum = " + this.workNum + ";\n");
		returnString.append("cardName = " + this.cardName + ";\n");
		returnString.append("year = " + this.year + ";\n");
		returnString.append("month = " + this.month + ";\n");
		returnString.append("remark = " + this.remark + ";\n");
		returnString.append("]\n");
		return returnString.toString();
	}
}