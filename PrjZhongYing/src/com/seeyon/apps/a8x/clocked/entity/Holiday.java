package com.seeyon.apps.a8x.clocked.entity;

import java.io.Serializable;

/*
 * 国家法定节日-实体
 */
public class Holiday implements Serializable {
	private static final long serialVersionUID = 4267529921271775900L;

	// id
	private long id;
	// 年度
	private int year;
	// 节日类型
	private String typeID;
	// 开始日期
	private java.sql.Date beginDate;
	// 结束日期
	private java.sql.Date endDate;
	// 天数
	private double days;
	// 备注
	private String remark;

	/**
	 * Default Constructor
	 */
	public Holiday() {
		// do nothing
	}

	// Bean methods......
	public long getId() {
		return this.id;
	}

	public void setId(long theId) {
		this.id = theId;
	}

	public int getYear() {
		return this.year;
	}

	public void setYear(int theYear) {
		this.year = theYear;
	}

	public String getTypeID() {
		return this.typeID;
	}

	public void setTypeID(String theTypeID) {
		this.typeID = theTypeID;
	}

	public java.sql.Date getBeginDate() {
		return this.beginDate;
	}

	public void setBeginDate(java.sql.Date theBeginDate) {
		this.beginDate = theBeginDate;
	}

	public java.sql.Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(java.sql.Date theEndDate) {
		this.endDate = theEndDate;
	}

	public double getDays() {
		return this.days;
	}

	public void setDays(double theDays) {
		this.days = theDays;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String theRemark) {
		this.remark = theRemark;
	}

	public String toString() {
		StringBuffer returnString = new StringBuffer();
		returnString.append("Holiday[");
		// Set member attributes
		returnString.append("id = " + this.id + ";\n");
		returnString.append("year = " + this.year + ";\n");
		returnString.append("typeID = " + this.typeID + ";\n");
		returnString.append("beginDate = " + this.beginDate + ";\n");
		returnString.append("endDate = " + this.endDate + ";\n");
		returnString.append("days = " + this.days + ";\n");
		returnString.append("remark = " + this.remark + ";\n");
		returnString.append("]\n");
		return returnString.toString();
	}
}