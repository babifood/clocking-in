package com.seeyon.apps.a8x.oa.entity;

import java.io.Serializable;


/*
 * -实体
 */
public class A8xMember implements Serializable {
	private static final long serialVersionUID = 6882968115338168262L;

	// id
	private long id;
	// name
	private String name;
	// code
	private String code;
	// deptid
	private long deptid;
	// deptname
	private String deptname;
	
	// path
	private String path;
	// loginname
	private String loginName;

	/**
	 * Default Constructor
	 */
	public A8xMember() {
		// do nothing
	}

	// Bean methods......
	public long getId() {
		return this.id;
	}

	public void setId(long theId) {
		this.id = theId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String theName) {
		this.name = theName;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String theCode) {
		this.code = theCode;
	}

	public long getDeptid() {
		return this.deptid;
	}

	public void setDeptid(long theDeptid) {
		this.deptid = theDeptid;
	}

	public String getDeptname() {
		return this.deptname;
	}

	public void setDeptname(String theDeptname) {
		this.deptname = theDeptname;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String thePath) {
		this.path = thePath;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String toString() {
		StringBuffer returnString = new StringBuffer();
		returnString.append("A8xMember[");
		// Set member attributes
		returnString.append("id = " + this.id + ";\n");
		returnString.append("name = " + this.name + ";\n");
		returnString.append("code = " + this.code + ";\n");
		returnString.append("deptid = " + this.deptid + ";\n");
		returnString.append("deptname = " + this.deptname + ";\n");
		returnString.append("path = " + this.path + ";\n");
		returnString.append("loginName = " + this.loginName + ";\n");
		returnString.append("]\n");
		return returnString.toString();
	}
}