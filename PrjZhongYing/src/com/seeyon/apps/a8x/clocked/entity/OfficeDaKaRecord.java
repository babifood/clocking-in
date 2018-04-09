package com.seeyon.apps.a8x.clocked.entity;

import com.seeyon.apps.a8x.clocked.rule.WorkHourRule;

/*
 * 员工打卡记录-实体
 */
public class OfficeDaKaRecord extends ClockedRecordBase {

	private static final long serialVersionUID = 1320516018807719880L;

	private java.sql.Timestamp daKaBeginTime;

	private java.sql.Timestamp daKaEndTime;

	public java.sql.Timestamp getDaKaBeginTime() {
		String temp = this.getStartTime() == null ? "" : this.getStartTime().trim();
		if (temp.equals("")) {
			daKaBeginTime = null;
		} else {
			if (daKaBeginTime == null) {
				if (temp.length() <= 5) {// hh:mm
					daKaBeginTime = java.sql.Timestamp.valueOf(this.getClockDate().toString() + " " + temp + ":00.0");
				} else {
					daKaBeginTime = java.sql.Timestamp.valueOf(this.getClockDate().toString() + " " + temp + ".0");
				}
			}
		}
		return daKaBeginTime;
	}

//	public java.sql.Timestamp getDaKaEndTime() {
//		return getDaKaEndTime(false);
//	}

	public java.sql.Timestamp getDaKaEndTime(boolean isDelayOneDay) {
		String temp = this.getEndTime() == null ? "" : this.getEndTime().trim();
		if (temp.equals("")) {
			daKaEndTime = null;
		} else {
			if (daKaEndTime == null) {
				if (temp.length() <= 5) {// hh:mm
					daKaEndTime = java.sql.Timestamp.valueOf(this.getClockDate().toString() + " " + temp + ":00.0");
				} else {
					daKaEndTime = java.sql.Timestamp.valueOf(this.getClockDate().toString() + " " + temp + ".0");
				}
				if (isDelayOneDay) {
					daKaEndTime = WorkHourRule.getTimeDelayOneDay(daKaEndTime, 1);
				}
			}
		}
		return daKaEndTime;
	}
}