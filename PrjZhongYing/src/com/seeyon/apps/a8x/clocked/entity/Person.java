package com.seeyon.apps.a8x.clocked.entity;

import java.io.Serializable;

/*
 * 员工考勤名单-实体
 */
public class Person implements Serializable {
	private static final long serialVersionUID = 6426433397486997446L;

	// id
	private long id;
	// 修改时间
	private java.sql.Timestamp modify_date;
	// 考勤卡号
	private String cardNum;
	// 员工ID
	private String memberID;
	// 工号
	private String workNum;
	// 部门
	private String dept;
	// 考勤机姓名
	private String cardName;
	// 单位机构
	private String organ;
	// 是否生效
	private String flag;
	// 姓名
	private String memberName;
	// 部门名称
	private String dept_name;
	// 部门路径
	private String dept_path;
	// 考勤类型名称
	private String typeName;
	// 考勤类型编码
	private String typeCode;

	/**
	 * Default Constructor
	 */
	public Person() {
		// do nothing
	}

	// Bean methods......
	public long getId() {
		return this.id;
	}

	public void setId(long theId) {
		this.id = theId;
	}

	public java.sql.Timestamp getModify_date() {
		return this.modify_date;
	}

	public void setModify_date(java.sql.Timestamp theModify_date) {
		this.modify_date = theModify_date;
	}

	public String getCardNum() {
		return this.cardNum;
	}

	public void setCardNum(String theCardNum) {
		this.cardNum = theCardNum;
	}

	public String getMemberID() {
		return this.memberID;
	}

	public void setMemberID(String theMemberID) {
		this.memberID = theMemberID;
	}

	public String getWorkNum() {
		return this.workNum;
	}

	public void setWorkNum(String theWorkNum) {
		this.workNum = theWorkNum;
	}

	public String getDept() {
		return this.dept;
	}

	public void setDept(String theDept) {
		this.dept = theDept;
	}

	public String getCardName() {
		return this.cardName;
	}

	public void setCardName(String theCardName) {
		this.cardName = theCardName;
	}

	public String getOrgan() {
		return this.organ;
	}

	public void setOrgan(String theOrgan) {
		this.organ = theOrgan;
	}

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String theFlag) {
		this.flag = theFlag;
	}

	public String getMemberName() {
		return this.memberName;
	}

	public void setMemberName(String theMemberName) {
		this.memberName = theMemberName;
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

	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String theTypeName) {
		this.typeName = theTypeName;
	}

	public String getTypeCode() {
		return this.typeCode;
	}

	public void setTypeCode(String theTypeCode) {
		this.typeCode = theTypeCode;
	}

	public String toString() {
		StringBuffer returnString = new StringBuffer();
		returnString.append("Person[");
		// Set member attributes
		returnString.append("id = " + this.id + ";\n");
		returnString.append("modify_date = " + this.modify_date + ";\n");
		returnString.append("cardNum = " + this.cardNum + ";\n");
		returnString.append("memberID = " + this.memberID + ";\n");
		returnString.append("workNum = " + this.workNum + ";\n");
		returnString.append("dept = " + this.dept + ";\n");
		returnString.append("cardName = " + this.cardName + ";\n");
		returnString.append("organ = " + this.organ + ";\n");
		returnString.append("flag = " + this.flag + ";\n");
		returnString.append("memberName = " + this.memberName + ";\n");
		returnString.append("dept_name = " + this.dept_name + ";\n");
		returnString.append("dept_path = " + this.dept_path + ";\n");
		returnString.append("typeName = " + this.typeName + ";\n");
		returnString.append("typeCode = " + this.typeCode + ";\n");
		returnString.append("]\n");
		return returnString.toString();
	}
}