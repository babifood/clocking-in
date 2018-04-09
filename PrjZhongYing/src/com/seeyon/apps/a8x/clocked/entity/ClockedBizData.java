package com.seeyon.apps.a8x.clocked.entity;

import java.io.Serializable;

/*
 * formmain_0346-实体
 */
public class ClockedBizData implements Serializable {
	private static final long serialVersionUID = 836283390499835651L;

	// id
	private long id;
	// 姓名
	private String memberID;
	// 年度
	private int year;
	// 月度
	private int month;
	// 单位机构
	private String organ;
	// 部门
	private String dept;
	// 工号
	private String workNum;
	// 日期
	private java.sql.Timestamp createTime;
	// 业务单据日期
	private java.sql.Date billDate;
	// 业务单据号
	private String billNum;
	// 业务单据类型
	private String billType;
	// 时长
	private double timeLength;
	// 开始时间
	private java.sql.Timestamp beginTime;
	// 结束时间
	private java.sql.Timestamp endTime;
	// 是否有效
	private String validFlag;
	// 业务性质1ID
	private String bizFlag1ID;
	// 业务性质2ID
	private String bizFlag2ID;
	// 业务性质3ID
	private String bizFlag3ID;
	// 业务性质1
	private String bizFlag1;
	// 业务性质2
	private String bizFlag2;
	// 业务性质3
	private String bizFlag3;
	// 协同ID
	private long summaryID;
	// 源单ID1
	private long sourceData1ID;
	// 源单ID2
	private long sourceData2ID;

	/**
	 * Default Constructor
	 */
	public ClockedBizData() {
		// do nothing
	}

	// Bean methods......
	public long getId() {
		return this.id;
	}

	public void setId(long theId) {
		this.id = theId;
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

	public int getMonth() {
		return this.month;
	}

	public void setMonth(int theMonth) {
		this.month = theMonth;
	}

	public String getOrgan() {
		return this.organ;
	}

	public void setOrgan(String theOrgan) {
		this.organ = theOrgan;
	}

	public String getDept() {
		return this.dept;
	}

	public void setDept(String theDept) {
		this.dept = theDept;
	}

	public String getWorkNum() {
		return this.workNum;
	}

	public void setWorkNum(String theWorkNum) {
		this.workNum = theWorkNum;
	}

	public java.sql.Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(java.sql.Timestamp theCreateTime) {
		this.createTime = theCreateTime;
	}

	public java.sql.Date getBillDate() {
		return this.billDate;
	}

	public void setBillDate(java.sql.Date theBillDate) {
		this.billDate = theBillDate;
	}

	public String getBillNum() {
		return this.billNum;
	}

	public void setBillNum(String theBillNum) {
		this.billNum = theBillNum;
	}

	public String getBillType() {
		return this.billType;
	}

	public void setBillType(String theBillType) {
		this.billType = theBillType;
	}

	public double getTimeLength() {
		return this.timeLength;
	}

	public void setTimeLength(double theTimeLength) {
		this.timeLength = theTimeLength;
	}

	public java.sql.Timestamp getBeginTime() {
		return this.beginTime;
	}

	public void setBeginTime(java.sql.Timestamp theBeginTime) {
		this.beginTime = theBeginTime;
	}

	public java.sql.Timestamp getEndTime() {
		return this.endTime;
	}

	public void setEndTime(java.sql.Timestamp theEndTime) {
		this.endTime = theEndTime;
	}

	public String getValidFlag() {
		return this.validFlag;
	}

	public void setValidFlag(String theValidFlag) {
		this.validFlag = theValidFlag;
	}

	public String getBizFlag1ID() {
		return this.bizFlag1ID;
	}

	public void setBizFlag1ID(String theBizFlag1ID) {
		this.bizFlag1ID = theBizFlag1ID;
	}

	public String getBizFlag2ID() {
		return this.bizFlag2ID;
	}

	public void setBizFlag2ID(String theBizFlag2ID) {
		this.bizFlag2ID = theBizFlag2ID;
	}

	public String getBizFlag3ID() {
		return this.bizFlag3ID;
	}

	public void setBizFlag3ID(String theBizFlag3ID) {
		this.bizFlag3ID = theBizFlag3ID;
	}

	public String getBizFlag1() {
		return this.bizFlag1;
	}

	public void setBizFlag1(String theBizFlag1) {
		this.bizFlag1 = theBizFlag1;
	}

	public String getBizFlag2() {
		return this.bizFlag2;
	}

	public void setBizFlag2(String theBizFlag2) {
		this.bizFlag2 = theBizFlag2;
	}

	public String getBizFlag3() {
		return this.bizFlag3;
	}

	public void setBizFlag3(String theBizFlag3) {
		this.bizFlag3 = theBizFlag3;
	}

	public long getSummaryID() {
		return this.summaryID;
	}

	public void setSummaryID(long theSummaryID) {
		this.summaryID = theSummaryID;
	}

	public long getSourceData1ID() {
		return this.sourceData1ID;
	}

	public void setSourceData1ID(long theSourceData1ID) {
		this.sourceData1ID = theSourceData1ID;
	}

	public long getSourceData2ID() {
		return this.sourceData2ID;
	}

	public void setSourceData2ID(long theSourceData2ID) {
		this.sourceData2ID = theSourceData2ID;
	}

	public String toString() {
		StringBuffer returnString = new StringBuffer();
		returnString.append("ClockedBizData[");
		// Set member attributes
		returnString.append("id = " + this.id + ";\n");
		returnString.append("memberID = " + this.memberID + ";\n");
		returnString.append("year = " + this.year + ";\n");
		returnString.append("month = " + this.month + ";\n");
		returnString.append("organ = " + this.organ + ";\n");
		returnString.append("dept = " + this.dept + ";\n");
		returnString.append("workNum = " + this.workNum + ";\n");
		returnString.append("createTime = " + this.createTime + ";\n");
		returnString.append("billDate = " + this.billDate + ";\n");
		returnString.append("billNum = " + this.billNum + ";\n");
		returnString.append("billType = " + this.billType + ";\n");
		returnString.append("timeLength = " + this.timeLength + ";\n");
		returnString.append("beginTime = " + this.beginTime + ";\n");
		returnString.append("endTime = " + this.endTime + ";\n");
		returnString.append("validFlag = " + this.validFlag + ";\n");
		returnString.append("bizFlag1ID = " + this.bizFlag1ID + ";\n");
		returnString.append("bizFlag2ID = " + this.bizFlag2ID + ";\n");
		returnString.append("bizFlag3ID = " + this.bizFlag3ID + ";\n");
		returnString.append("bizFlag1 = " + this.bizFlag1 + ";\n");
		returnString.append("bizFlag2 = " + this.bizFlag2 + ";\n");
		returnString.append("bizFlag3 = " + this.bizFlag3 + ";\n");
		returnString.append("summaryID = " + this.summaryID + ";\n");
		returnString.append("sourceData1ID = " + this.sourceData1ID + ";\n");
		returnString.append("sourceData2ID = " + this.sourceData2ID + ";\n");
		returnString.append("]\n");
		return returnString.toString();
	}
}