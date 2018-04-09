package com.seeyon.apps.a8x.clocked.entity;

import java.sql.Timestamp;

import com.seeyon.apps.a8x.clocked.rule.WorkHourRule;
import com.seeyon.apps.a8x.common.util.UtilDateTime;
import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.util.UUIDLong;

/*
 * 考勤结果-实体
 */
public class ClockedResult extends ClockedResultBase {

	private static final long serialVersionUID = 1582695253462971352L;

	// 最终开始计时时间
	private java.sql.Timestamp finalBeginTime;
	// 最终结束计时时间
	private java.sql.Timestamp finalEndTime;

	// 姓名
	private String memberName;
	// 部门名称
	private String dept_name;
	// 部门路径
	private String dept_path;

	// 标准上班时间
	private java.sql.Timestamp standBeginTime;
	// 标准下班时间
	private java.sql.Timestamp standEndTime;
	
	// 中间休息时段
	Timestamp xiuBeginTime = null;

	Timestamp xiuEndTime = null;

	private boolean isWeekEnd;
	private boolean isHoliday;
	private boolean isMarkByPerson;
	private boolean isMarkByDept;
	private boolean isMarkByOrg;

	public ClockedResult() {

	}

	public java.sql.Timestamp getFinalBeginTime() {
		finalBeginTime = getClockBeginTime() == null ? getEventBeginTime() : getClockBeginTime();
		if (getClockBeginTime() != null && getEventBeginTime() != null) {
			if (getClockBeginTime().after(getEventBeginTime())) {
				finalBeginTime = getEventBeginTime();
			}
		}
		return finalBeginTime;
	}

	public java.sql.Timestamp getFinalEndTime() {
		finalEndTime = getClockEndTime() == null ? getEventEndTime() : getClockEndTime();
		if (getClockEndTime() != null && getEventEndTime() != null) {
			if (getClockEndTime().before(getEventEndTime())) {
				finalEndTime = getEventEndTime();
			}
		}
		return finalEndTime;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getDept_path() {
		return dept_path;
	}

	public void setDept_path(String dept_path) {
		this.dept_path = dept_path;
	}

	public java.sql.Timestamp getStandBeginTime() {
		if (standBeginTime == null) {
			standBeginTime = WorkHourRule.getStandBeginTime(this.getClockDate(), this.getBeginTime());
		}
		return standBeginTime;
	}

	public java.sql.Timestamp getStandEndTime() {
		if (standEndTime == null) {
			standEndTime = WorkHourRule.getStandEndTime(this.getClockDate(), this.getBeginTime(), this.getEndTime());
		}
		return standEndTime;
	}

	// 考勤标准的结束时间是否需要延时1天
	public boolean isDelayOneDay() {
		if (getStandEndTime() == null || getStandBeginTime() == null) {
			return false;
		}
		return UtilDateTime.getDaysBetween(getStandBeginTime(), getStandEndTime()) == 1;
	}



	public boolean isMarkByPerson() {
		return isMarkByPerson;
	}

	public void setMarkByPerson(boolean isMarkByPerson) {
		this.isMarkByPerson = isMarkByPerson;
	}

	public boolean isMarkByDept() {
		return isMarkByDept;
	}

	public void setMarkByDept(boolean isMarkByDept) {
		this.isMarkByDept = isMarkByDept;
	}

	public boolean isMarkByOrg() {
		return isMarkByOrg;
	}

	public void setMarkByOrg(boolean isMarkByOrg) {
		this.isMarkByOrg = isMarkByOrg;
	}

	public boolean isHoliday() {
		return isHoliday;
	}

	public void setHoliday(boolean isHoliday) {
		this.isHoliday = isHoliday;
	}

	public boolean isWeekEnd() {
		return isWeekEnd;
	}

	public void setWeekEnd(boolean isWeekEnd) {
		this.isWeekEnd = isWeekEnd;
	}

	public Timestamp getXiuBeginTime() {
		if (xiuBeginTime == null) {
			checkXiuXiTime();
		}
		return xiuBeginTime;
	}

	public Timestamp getXiuEndTime() {
		if (xiuEndTime == null) {
			checkXiuXiTime();
		}
		return xiuEndTime;
	}

	private void checkXiuXiTime() {
		String theWorkBeginTimeStr = this.getBeginTime();
		String theWorkEndTimeStr = this.getEndTime();
		String dateString = this.getClockDate().toString();
		//int standWorkTime[] = UtilDateTime.getTimeInterval(standBeginTime, standEndTime);
		// 标准工作时长计算》8小时，应该有中间休息时段，如果没有，抛出异常
		// if (standWorkTime[1] >= 6) {
		if (theWorkBeginTimeStr.indexOf("8:30") != -1 && theWorkEndTimeStr.indexOf("17:30") != -1) {
			xiuBeginTime = Timestamp.valueOf(dateString + " 12:00:00.0");
			xiuEndTime = Timestamp.valueOf(dateString + " 13:00:00.0");
		} else if (theWorkBeginTimeStr.indexOf("9:00") != -1 && theWorkEndTimeStr.indexOf("18:00") != -1) {
			xiuBeginTime = Timestamp.valueOf(dateString + " 12:00:00.0");
			xiuEndTime = Timestamp.valueOf(dateString + " 13:00:00.0");
		} else if (theWorkBeginTimeStr.indexOf("9:00") != -1 && theWorkEndTimeStr.indexOf("16:00") != -1) {
			xiuBeginTime = Timestamp.valueOf(dateString + " 12:00:00.0");
			xiuEndTime = Timestamp.valueOf(dateString + " 13:00:00.0");
		} 
		if (xiuBeginTime == null || xiuEndTime == null) {
			// throw new IllegalArgumentException("上/下班作业时间不支持,[" +
			// theWorkBeginTimeStr + "-" + theWorkEndTimeStr +
			// "],无法获取中间休息时段数据!!!");
			System.out.println("上/下班作业时间不支持,[" + theWorkBeginTimeStr + "-" + theWorkEndTimeStr + "],无法获取中间休息时段数据!!!");
		}
		// }
	}

	public void initCreateFieldValue() {
		setId(UUIDLong.longUUID());
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		setState(1);

		this.setStart_member_id(AppContext.currentUserId());
		this.setStart_date(currentTime);

		this.setApprove_member_id(AppContext.currentUserId());
		this.setApprove_date(currentTime);

		this.setFinishedflag(1);
		this.setRatifyflag(0);
		this.setRatify_member_id(-1);
		// setSort(0);

		this.setModify_member_id(AppContext.currentUserId());
		this.setModify_date(currentTime);
	}

}