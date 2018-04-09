package com.seeyon.apps.a8x.clocked.entity;

import java.io.Serializable;

/*
 * 初始年假信息-实体
 */
public class NianJiaAccount implements Serializable {
	private static final long serialVersionUID = 7754220263052101813L;

	// id
	private long id;
	// 审核状态
	private int state;
	// 发起人
	private long start_member_id;
	// 发起时间
	private java.sql.Timestamp start_date;
	// 审核人
	private long approve_member_id;
	// 审核时间
	private java.sql.Timestamp approve_date;
	// 流程状态
	private int finishedflag;
	// 核定状态
	private int ratifyflag;
	// 核定人
	private long ratify_member_id;
	// 核定时间
	private java.sql.Timestamp ratify_date;
	// 姓名
	private String memberID;
	// 年度
	private int year;
	// 部门
	private String deptID;
	// 单位机构
	private String organID;
	// 工号
	private String workNum;
	// 上年失效日期
	private java.sql.Date preShiXiaoDate;
	// 上年结余数
	private double preTimeLength;
	// 本年失效日期
	private java.sql.Date shiXiaoDate;
	// 本年年假数
	private double timeLength;
	// 调休抵扣数
	private double diKouTimeLength;
	// 自动作废数
	private double zuoFeiTimeLength;
	// 剩余年假数
	private double shengYuTimeLength;
	// 备注
	private String remark;

	/**
	 * Default Constructor
	 */
	public NianJiaAccount() {
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

	public long getStart_member_id() {
		return this.start_member_id;
	}

	public void setStart_member_id(long theStart_member_id) {
		this.start_member_id = theStart_member_id;
	}

	public java.sql.Timestamp getStart_date() {
		return this.start_date;
	}

	public void setStart_date(java.sql.Timestamp theStart_date) {
		this.start_date = theStart_date;
	}

	public long getApprove_member_id() {
		return this.approve_member_id;
	}

	public void setApprove_member_id(long theApprove_member_id) {
		this.approve_member_id = theApprove_member_id;
	}

	public java.sql.Timestamp getApprove_date() {
		return this.approve_date;
	}

	public void setApprove_date(java.sql.Timestamp theApprove_date) {
		this.approve_date = theApprove_date;
	}

	public int getFinishedflag() {
		return this.finishedflag;
	}

	public void setFinishedflag(int theFinishedflag) {
		this.finishedflag = theFinishedflag;
	}

	public int getRatifyflag() {
		return this.ratifyflag;
	}

	public void setRatifyflag(int theRatifyflag) {
		this.ratifyflag = theRatifyflag;
	}

	public long getRatify_member_id() {
		return this.ratify_member_id;
	}

	public void setRatify_member_id(long theRatify_member_id) {
		this.ratify_member_id = theRatify_member_id;
	}

	public java.sql.Timestamp getRatify_date() {
		return this.ratify_date;
	}

	public void setRatify_date(java.sql.Timestamp theRatify_date) {
		this.ratify_date = theRatify_date;
	}

	public String getMemberID() {
		return this.memberID;
	}

	public void setMemberID(String theMemberID) {
		this.memberID = theMemberID;
	}

	public int getYear() {
		return this.year;
	}

	public void setYear(int theYear) {
		this.year = theYear;
	}

	public String getDeptID() {
		return this.deptID;
	}

	public void setDeptID(String theDeptID) {
		this.deptID = theDeptID;
	}

	public String getOrganID() {
		return this.organID;
	}

	public void setOrganID(String theOrganID) {
		this.organID = theOrganID;
	}

	public String getWorkNum() {
		return this.workNum;
	}

	public void setWorkNum(String theWorkNum) {
		this.workNum = theWorkNum;
	}

	public java.sql.Date getPreShiXiaoDate() {
		return this.preShiXiaoDate;
	}

	public void setPreShiXiaoDate(java.sql.Date thePreShiXiaoDate) {
		this.preShiXiaoDate = thePreShiXiaoDate;
	}

	public double getPreTimeLength() {
		return this.preTimeLength;
	}

	public void setPreTimeLength(double thePreTimeLength) {
		this.preTimeLength = thePreTimeLength;
	}

	public java.sql.Date getShiXiaoDate() {
		return this.shiXiaoDate;
	}

	public void setShiXiaoDate(java.sql.Date theShiXiaoDate) {
		this.shiXiaoDate = theShiXiaoDate;
	}

	public double getTimeLength() {
		return this.timeLength;
	}

	public void setTimeLength(double theTimeLength) {
		this.timeLength = theTimeLength;
	}

	public double getDiKouTimeLength() {
		return this.diKouTimeLength;
	}

	public void setDiKouTimeLength(double theDiKouTimeLength) {
		this.diKouTimeLength = theDiKouTimeLength;
	}

	public double getZuoFeiTimeLength() {
		return this.zuoFeiTimeLength;
	}

	public void setZuoFeiTimeLength(double theZuoFeiTimeLength) {
		this.zuoFeiTimeLength = theZuoFeiTimeLength;
	}

	public double getShengYuTimeLength() {
		return this.shengYuTimeLength;
	}

	public void setShengYuTimeLength(double theShengYuTimeLength) {
		this.shengYuTimeLength = theShengYuTimeLength;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String theRemark) {
		this.remark = theRemark;
	}

	public String toString() {
		StringBuffer returnString = new StringBuffer();
		returnString.append("NianJiaAccount[");
		// Set member attributes
		returnString.append("id = " + this.id + ";\n");
		returnString.append("state = " + this.state + ";\n");
		returnString.append("start_member_id = " + this.start_member_id + ";\n");
		returnString.append("start_date = " + this.start_date + ";\n");
		returnString.append("approve_member_id = " + this.approve_member_id + ";\n");
		returnString.append("approve_date = " + this.approve_date + ";\n");
		returnString.append("finishedflag = " + this.finishedflag + ";\n");
		returnString.append("ratifyflag = " + this.ratifyflag + ";\n");
		returnString.append("ratify_member_id = " + this.ratify_member_id + ";\n");
		returnString.append("ratify_date = " + this.ratify_date + ";\n");
		returnString.append("memberID = " + this.memberID + ";\n");
		returnString.append("year = " + this.year + ";\n");
		returnString.append("deptID = " + this.deptID + ";\n");
		returnString.append("organID = " + this.organID + ";\n");
		returnString.append("workNum = " + this.workNum + ";\n");
		returnString.append("preShiXiaoDate = " + this.preShiXiaoDate + ";\n");
		returnString.append("preTimeLength = " + this.preTimeLength + ";\n");
		returnString.append("shiXiaoDate = " + this.shiXiaoDate + ";\n");
		returnString.append("timeLength = " + this.timeLength + ";\n");
		returnString.append("diKouTimeLength = " + this.diKouTimeLength + ";\n");
		returnString.append("zuoFeiTimeLength = " + this.zuoFeiTimeLength + ";\n");
		returnString.append("shengYuTimeLength = " + this.shengYuTimeLength + ";\n");
		returnString.append("remark = " + this.remark + ";\n");
		returnString.append("]\n");
		return returnString.toString();
	}

	// 本次抵扣数
	private double writedownValue;

	public double getWritedownValue() {
		return writedownValue;
	}

	public void setWritedownValue(double writedownValue) {
		this.writedownValue = writedownValue;
	}
}