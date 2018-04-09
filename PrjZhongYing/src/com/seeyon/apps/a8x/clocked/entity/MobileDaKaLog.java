package com.seeyon.apps.a8x.clocked.entity;

import java.io.Serializable;

/*
 * 移动打卡记录-实体
 */
public class MobileDaKaLog implements Serializable {
	private static final long serialVersionUID = 8605537256494746509L;

	// 人员
	private String memberId;
	// 打卡时间
	private java.sql.Timestamp clockTime;
	// 打卡日期字符
	private String clockDate;
	// 打卡地点类型
	private String locationTypeId;

	/**
	 * Default Constructor
	 */
	public MobileDaKaLog() {
		// do nothing
	}

	// Bean methods......
	public String getMemberId() {
		return this.memberId;
	}

	public void setMemberId(String theMemberId) {
		this.memberId = theMemberId;
	}

	public java.sql.Timestamp getClockTime() {
		return this.clockTime;
	}

	public void setClockTime(java.sql.Timestamp theClockTime) {
		this.clockTime = theClockTime;
	}

	public String getClockDate() {
		return this.clockDate;
	}

	public void setClockDate(String theClockDate) {
		this.clockDate = theClockDate;
	}

	public String getLocationTypeId() {
		return this.locationTypeId;
	}

	public void setLocationTypeId(String theLocationTypeId) {
		this.locationTypeId = theLocationTypeId;
	}
	
	public String getLocation() {
		if("7031085782734490657".equals(locationTypeId)){
			return "门店";
		}
		if("1603086988708757755".equals(locationTypeId)){
			return "公司";
		}
		return "";
	}

	public String toString() {
		StringBuffer returnString = new StringBuffer();
		returnString.append("MobileDaKaRecord[");
		// Set member attributes
		returnString.append("memberId = " + this.memberId + ";\n");
		returnString.append("clockTime = " + this.clockTime + ";\n");
		returnString.append("clockDate = " + this.clockDate + ";\n");
		returnString.append("locationTypeId = " + this.locationTypeId + ";\n");
		returnString.append("]\n");
		return returnString.toString();
	}
}