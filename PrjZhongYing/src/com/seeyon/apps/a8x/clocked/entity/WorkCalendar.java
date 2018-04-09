package com.seeyon.apps.a8x.clocked.entity;

import java.io.Serializable;

/*
 * 特殊排班日信息-实体
 */
public class WorkCalendar implements Serializable {
	private static final long serialVersionUID = 5669021399879535253L;

	// id
	private long id;
	// 日期
	private java.sql.Date workDate;
	// 班次
	private String workGroup;
	// 是否考勤
	private String workFlag;
	// 数据策略
	private String dataFlag;
	// 机构
	private String organ;
	// 部门
	private String dept;
	// 员工
	private String member;
	// 日期类型
	private String dateType;
	// 创建日期
	private java.sql.Timestamp createTime;
	// 创建者
	private String creator;
	// 班次NAME
	private String workgroup_name;
	// 班次CODE
	private String workgroup_code;
	// 数据策略NAME
	private String dataflag_name;
	// 数据策略CODE
	private String dataflag_code;
	// 日期类型NAME
	private String datetype_name;
	// 日期类型CODE
	private String datetype_code;
	// 机构名称
	private String organ_name;
	// 机构路径
	private String organ_path;
	// 部门名称
	private String dept_name;
	// 部门路径
	private String dept_path;

	/**
	 * Default Constructor
	 */
	public WorkCalendar() {
		// do nothing
	}

	// Bean methods......
	public long getId() {
		return this.id;
	}

	public void setId(long theId) {
		this.id = theId;
	}

	public java.sql.Date getWorkDate() {
		return this.workDate;
	}

	public void setWorkDate(java.sql.Date theWorkDate) {
		this.workDate = theWorkDate;
	}

	public String getWorkGroup() {
		return this.workGroup;
	}

	public void setWorkGroup(String theWorkGroup) {
		this.workGroup = theWorkGroup;
	}

	public String getWorkFlag() {
		return this.workFlag;
	}

	public void setWorkFlag(String theWorkFlag) {
		this.workFlag = theWorkFlag;
	}

	public String getDataFlag() {
		return this.dataFlag;
	}

	public void setDataFlag(String theDataFlag) {
		this.dataFlag = theDataFlag;
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

	public String getMember() {
		return this.member;
	}

	public void setMember(String theMember) {
		this.member = theMember;
	}

	public String getDateType() {
		return this.dateType;
	}

	public void setDateType(String theDateType) {
		this.dateType = theDateType;
	}

	public java.sql.Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(java.sql.Timestamp theCreateTime) {
		this.createTime = theCreateTime;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String theCreator) {
		this.creator = theCreator;
	}

	public String getWorkgroup_name() {
		return this.workgroup_name;
	}

	public void setWorkgroup_name(String theWorkgroup_name) {
		this.workgroup_name = theWorkgroup_name;
	}

	public String getWorkgroup_code() {
		return this.workgroup_code;
	}

	public void setWorkgroup_code(String theWorkgroup_code) {
		this.workgroup_code = theWorkgroup_code;
	}

	public String getDataflag_name() {
		return this.dataflag_name;
	}

	public void setDataflag_name(String theDataflag_name) {
		this.dataflag_name = theDataflag_name;
	}

	public String getDataflag_code() {
		return this.dataflag_code;
	}

	public void setDataflag_code(String theDataflag_code) {
		this.dataflag_code = theDataflag_code;
	}

	public String getDatetype_name() {
		return this.datetype_name;
	}

	public void setDatetype_name(String theDatetype_name) {
		this.datetype_name = theDatetype_name;
	}

	public String getDatetype_code() {
		return this.datetype_code;
	}

	public void setDatetype_code(String theDatetype_code) {
		this.datetype_code = theDatetype_code;
	}

	public String getOrgan_name() {
		return this.organ_name;
	}

	public void setOrgan_name(String theOrgan_name) {
		this.organ_name = theOrgan_name;
	}

	public String getOrgan_path() {
		return this.organ_path;
	}

	public void setOrgan_path(String theOrgan_path) {
		this.organ_path = theOrgan_path;
	}

	public String getDept_name() {
		return this.dept_name;
	}

	public void setDept_name(String theDept_name) {
		this.dept_name = theDept_name;
	}

	public String getDept_path() {
		return this.dept_path;
	}

	public void setDept_path(String theDept_path) {
		this.dept_path = theDept_path;
	}

	public String toString() {
		StringBuffer returnString = new StringBuffer();
		returnString.append("WorkCalendar[");
		// Set member attributes
		returnString.append("id = " + this.id + ";\n");
		returnString.append("workDate = " + this.workDate + ";\n");
		returnString.append("workGroup = " + this.workGroup + ";\n");
		returnString.append("workFlag = " + this.workFlag + ";\n");
		returnString.append("dataFlag = " + this.dataFlag + ";\n");
		returnString.append("organ = " + this.organ + ";\n");
		returnString.append("dept = " + this.dept + ";\n");
		returnString.append("member = " + this.member + ";\n");
		returnString.append("dateType = " + this.dateType + ";\n");
		returnString.append("createTime = " + this.createTime + ";\n");
		returnString.append("creator = " + this.creator + ";\n");
		returnString.append("workgroup_name = " + this.workgroup_name + ";\n");
		returnString.append("workgroup_code = " + this.workgroup_code + ";\n");
		returnString.append("dataflag_name = " + this.dataflag_name + ";\n");
		returnString.append("dataflag_code = " + this.dataflag_code + ";\n");
		returnString.append("datetype_name = " + this.datetype_name + ";\n");
		returnString.append("datetype_code = " + this.datetype_code + ";\n");
		returnString.append("organ_name = " + this.organ_name + ";\n");
		returnString.append("organ_path = " + this.organ_path + ";\n");
		returnString.append("dept_name = " + this.dept_name + ";\n");
		returnString.append("dept_path = " + this.dept_path + ";\n");
		returnString.append("]\n");
		return returnString.toString();
	}
}