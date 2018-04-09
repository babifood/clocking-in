package com.seeyon.apps.a8x.clocked.rule;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.seeyon.apps.a8x.clocked.entity.ClockedResult;
import com.seeyon.apps.a8x.clocked.util.ClockedUtil;
import com.seeyon.apps.a8x.common.util.UtilDateTime;

/**
 * 工作时间--小时数--计算公式
 * 
 */
public final class WorkHourRule {

	private WorkHourRule() {

	}

	/*
	 * 设置标准工作时间
	 */
	public static void setStandWorkTime(ClockedResult tmpResult) {
		String week = UtilDateTime.getWeekOfDate(tmpResult.getClockDate());
		tmpResult.setWeek(week);
		if ((week.indexOf("六") != -1) || (week.indexOf("日") != -1)) {
			tmpResult.setWeekEnd(true);
			tmpResult.setClockFlag(ClockedYesNoRule.Clock_Flag_NOT_WORK);
			tmpResult.setBeginTime("09:00");
			tmpResult.setEndTime("16:00");
			tmpResult.setStandWorkTimeLength(6);
		} else {
			tmpResult.setClockFlag(ClockedYesNoRule.Clock_Flag_WORK_NORMAL);
			String organ = ClockedUtil.getOrgan(tmpResult.getOrgan()).getPath();
			//巴比商学院、财务管理中心、采购管理中心、华东团餐事业部、基础项目建设部、
			//人力资源中心、上海生产中心、信息管理中心、华东团餐事业部、华南营运总部
			//人事行政部、巴比商学院华南分院、巴比商城
			if (organ.equals("0000000100020011")||organ.equals("0000000100020005")
					||organ.equals("0000000100020004")||organ.equals("0000000100020014")
					||organ.equals("0000000100020010")||organ.equals("0000000100020006")
					||organ.equals("0000000100020013")||organ.equals("0000000100020007")
					||organ.equals("0000000100120002")||organ.equals("0000000100120001")
					||organ.equals("0000000100120005")||organ.equals("0000000100120004")
					||organ.equals("0000000100020012")) {
				tmpResult.setBeginTime("09:00");
				tmpResult.setEndTime("18:00");
				tmpResult.setStandWorkTimeLength(8);
			} else {
				tmpResult.setBeginTime("08:30");
				tmpResult.setEndTime("17:30");
				tmpResult.setStandWorkTimeLength(8);
			}
		}
		tmpResult.setYear(UtilDateTime.getYear(tmpResult.getClockDate()));
		tmpResult.setMonth(UtilDateTime.getMonth(tmpResult.getClockDate()));
	}

	public static void setStandWorkTimeLength(ClockedResult theResult) {
		if ("09:00".equals(theResult.getBeginTime())
				&& "16:00".equals(theResult.getEndTime())) {
			theResult.setStandWorkTimeLength(6);
		} else if ("09:00".equals(theResult.getBeginTime())
				&& "18:00".equals(theResult.getEndTime())) {
			theResult.setStandWorkTimeLength(8);
		} else if ("08:30".equals(theResult.getBeginTime())
				&& "17:30".equals(theResult.getEndTime())) {
			theResult.setStandWorkTimeLength(8);
		} else {
			theResult.setStandWorkTimeLength(0);
		}
	}

	/**
	 * 计算办公室打卡--某一天的工作时长
	 * 
	 * @param theBeginTime
	 *            开始时间
	 * @param theEndTime
	 *            结束时间
	 * @param theWorkBeginTimeStr
	 *            上班作业时间-字符，比如08:30
	 * @param theWorkEndTimeStr
	 *            下班作业时间-字符，比如17:30
	 * @return
	 */
	public static double getOfficeWorkHoursBySameDay(Timestamp theBeginTime,
			Timestamp theEndTime, ClockedResult theClockedResult) {
		double value = 0.0d;
		Timestamp tmpBegin = getTimestampWithSecond0(theBeginTime);
		Timestamp tmpEnd = getTimestampWithSecond0(theEndTime);
		// 此天的标准工作时间
		Timestamp standBeginTime = theClockedResult.getStandBeginTime();
		Timestamp standEndTime = theClockedResult.getStandEndTime();
		// ======================正常范围=============================
		if (tmpBegin.getTime() <= standBeginTime.getTime()
				&& tmpEnd.getTime() >= standEndTime.getTime()) {
			int[] x = UtilDateTime
					.getTimeInterval(standBeginTime, standEndTime);
			value = x[1];
			// 存在休息情况
			if (theClockedResult.getXiuBeginTime() != null) {
				value = value - 1;
			}
			return value;
		}
		// ======================处理迟到或早退现象=============================
		// 中间休息时段
		Timestamp xiuBeginTime = theClockedResult.getXiuBeginTime();
		Timestamp xiuEndTime = theClockedResult.getXiuEndTime();
		if (tmpBegin.getTime() <= standBeginTime.getTime()) {
			tmpBegin = standBeginTime;
		} else {
			// 开始时间 在 12：00-13：00之间
			if (xiuBeginTime != null && xiuEndTime != null
					&& tmpBegin.getTime() >= xiuBeginTime.getTime()
					&& tmpBegin.getTime() <= xiuEndTime.getTime()) {
				tmpBegin = xiuEndTime;
			}
		}
		if (tmpEnd.getTime() >= standEndTime.getTime()) {
			tmpEnd = standEndTime;
		} else {
			// 结束时间 在 12：00-13：00之间
			if (xiuBeginTime != null && xiuEndTime != null
					&& tmpEnd.getTime() >= xiuBeginTime.getTime()
					&& tmpEnd.getTime() <= xiuEndTime.getTime()) {
				tmpEnd = xiuBeginTime;
			}
		}
		value = getWorkTimeLength(tmpBegin, tmpEnd);
		// 打卡开始时间发生在中途休息前，并且打卡结束时间发生在中途休息后，则扣除中间休息的一小时
		if (xiuBeginTime != null && xiuEndTime != null
				&& tmpBegin.getTime() <= xiuBeginTime.getTime()
				&& tmpEnd.getTime() >= xiuEndTime.getTime()) {
			value = value - 1;
		}
		return value;
	}

	public static double getQingJiaHoursBySameDay(Timestamp theBeginTime,
			Timestamp theEndTime, ClockedResult theClockedResult) {
		double value = 0.0d;
		Timestamp tmpBegin = getTimestampWithSecond0(theBeginTime);
		Timestamp tmpEnd = getTimestampWithSecond0(theEndTime);
		// 此天的标准工作时间
		Timestamp standBeginTime = theClockedResult.getStandBeginTime();
		Timestamp standEndTime = theClockedResult.getStandEndTime();
		// ======================正常范围=============================
		if (tmpBegin.getTime() <= standBeginTime.getTime()
				&& tmpEnd.getTime() >= standEndTime.getTime()) {
			int[] x = UtilDateTime
					.getTimeInterval(standBeginTime, standEndTime);
			value = x[1];
			// 存在休息情况
			if (theClockedResult.getXiuBeginTime() != null) {
				value = value - 1;
			}
			return value;
		}
		// ======================处理迟到或早退现象=============================
		// 中间休息时段
		Timestamp xiuBeginTime = theClockedResult.getXiuBeginTime();
		Timestamp xiuEndTime = theClockedResult.getXiuEndTime();
		if (tmpBegin.getTime() <= standBeginTime.getTime()) {
			tmpBegin = standBeginTime;
		} else {
			// 开始时间 在 12：00-13：00之间
			if (xiuBeginTime != null && xiuEndTime != null
					&& tmpBegin.getTime() >= xiuBeginTime.getTime()
					&& tmpBegin.getTime() <= xiuEndTime.getTime()) {
				tmpBegin = xiuEndTime;
			}
		}
		if (tmpEnd.getTime() >= standEndTime.getTime()) {
			tmpEnd = standEndTime;
		} else {
			// 结束时间 在 12：00-13：00之间
			if (xiuBeginTime != null && xiuEndTime != null
					&& tmpEnd.getTime() >= xiuBeginTime.getTime()
					&& tmpEnd.getTime() <= xiuEndTime.getTime()) {
				tmpEnd = xiuBeginTime;
			}
		}
		// 计算时间长度
		value = getQingJiaTimeLength(tmpBegin, tmpEnd);

		// 打卡开始时间发生在中途休息前，并且打卡结束时间发生在中途休息后，则扣除中间休息的一小时
		if (xiuBeginTime != null && xiuEndTime != null
				&& tmpBegin.getTime() <= xiuBeginTime.getTime()
				&& tmpEnd.getTime() >= xiuEndTime.getTime()) {
			value = value - 1;
		}

		return value;
	}

	/**
	 * 用于表单函数
	 * 
	 * @param theBeginTime
	 * @param theEndTime
	 * @return
	 */
	public static double getQingJiaHoursBySameDay4Function(
			Timestamp theBeginTime, Timestamp theEndTime) {
		if (theEndTime.getTime() < theBeginTime.getTime()) {
			return -1;
		}

		int day = UtilDateTime.getDaysBetween(theBeginTime, theEndTime);
		if (day != 0) {
			return -1;
		}

		double value = 0.0d;

		Timestamp tmpBegin = getTimestampWithSecond0(theBeginTime);
		Timestamp tmpEnd = getTimestampWithSecond0(theEndTime);

		// 中间休息时段
		String strDate = theBeginTime.toString().substring(0, 10);
		Timestamp xiuBeginTime = Timestamp.valueOf(strDate + " 12:00:00.0");
		Timestamp xiuEndTime = Timestamp.valueOf(strDate + " 13:00:00.0");
		// 开始时间 在 12：00-13：00之间
		if (xiuBeginTime != null && xiuEndTime != null
				&& tmpBegin.getTime() >= xiuBeginTime.getTime()
				&& tmpBegin.getTime() <= xiuEndTime.getTime()) {
			tmpBegin = xiuEndTime;
		}

		// 结束时间 在 12：00-13：00之间
		if (xiuBeginTime != null && xiuEndTime != null
				&& tmpEnd.getTime() >= xiuBeginTime.getTime()
				&& tmpEnd.getTime() <= xiuEndTime.getTime()) {
			tmpEnd = xiuBeginTime;
		}
		// 计算时间长度
		value = getQingJiaTimeLength(tmpBegin, tmpEnd);

		// 打卡开始时间发生在中途休息前，并且打卡结束时间发生在中途休息后，则扣除中间休息的一小时
		if (xiuBeginTime != null && xiuEndTime != null
				&& tmpBegin.getTime() <= xiuBeginTime.getTime()
				&& tmpEnd.getTime() >= xiuEndTime.getTime()) {
			value = value - 1;
		}

		return value;
	}

	/**
	 * 将时间置秒/毫秒数为0
	 */
	public static Timestamp getTimestampWithSecond0(java.sql.Timestamp stamp) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(stamp);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return new Timestamp(cal.getTime().getTime());
	}

	public static java.sql.Timestamp getStandBeginTime(
			java.sql.Date theClockDate, String theBeginTimeStr) {
		java.sql.Timestamp standBeginTime = null;
		String s = theClockDate.toString() + " " + theBeginTimeStr + ":0.0";
		standBeginTime = java.sql.Timestamp.valueOf(s);
		return standBeginTime;
	}

	public static java.sql.Timestamp getStandEndTime(
			java.sql.Date theClockDate, String theBeginTimeStr,
			String theEndTimeStr) {
		java.sql.Timestamp standEndTime = null;
		String s = theClockDate.toString() + " " + theEndTimeStr + ":0.0";
		standEndTime = java.sql.Timestamp.valueOf(s);

		java.sql.Timestamp standBeginTime = null;
		s = theClockDate.toString() + " " + theBeginTimeStr + ":0.0";
		standBeginTime = java.sql.Timestamp.valueOf(s);

		if (standBeginTime.after(standEndTime)) {
			standEndTime = getTimeDelayOneDay(standEndTime, 1);
		}
		return standEndTime;
	}

	public static java.sql.Timestamp getTimeDelayOneDay(java.util.Date stamp,
			int daysLater) {
		Calendar tempCal = Calendar.getInstance();
		tempCal.setTime(new java.util.Date(stamp.getTime()));
		tempCal.set(tempCal.get(Calendar.YEAR), tempCal.get(Calendar.MONTH),
				tempCal.get(Calendar.DAY_OF_MONTH), tempCal
						.get(Calendar.HOUR_OF_DAY), tempCal
						.get(Calendar.MINUTE), 0);
		tempCal.add(Calendar.DAY_OF_MONTH, daysLater);
		java.sql.Timestamp retStamp = new java.sql.Timestamp(tempCal.getTime()
				.getTime());
		retStamp.setNanos(0);
		return retStamp;
	}

	public static boolean isKuaTianOnEndTime(ClockedResult theResult,
			java.util.Date theBizBeginTime, java.util.Date theBizEndTime) {
		boolean flag = false;
		if (theResult.isDelayOneDay()) {
			if (theBizEndTime.getTime() > getTimeDelayOneDay(
					theResult.getStandEndTime(), 1).getTime()) {
				flag = true;
			}
		} else {
			flag = (UtilDateTime.getDaysBetween(theBizBeginTime, theBizEndTime)) > 0;
		}
		return flag;
	}

	public static boolean isKuaTianOnBeginTime(ClockedResult theResult,
			java.util.Date theBizBeginTime, java.util.Date theBizEndTime) {
		boolean flag = false;
		if (theResult.isDelayOneDay()) {
			if (theResult.getStandBeginTime().getTime() > getTimeDelayOneDay(
					theBizBeginTime, 1).getTime()) {
				flag = true;
			}
		} else {
			flag = (UtilDateTime.getDaysBetween(theBizBeginTime, theBizEndTime)) > 0;
		}
		return flag;
	}

	/**
	 * 计算移动打卡--某一天的工作时长
	 * 
	 * @param theBeginTime
	 *            开始时间
	 * @param theEndTime
	 *            结束时间
	 * @param theWorkBeginTimeStr
	 *            上班作业时间-字符，比如08:30
	 * @param theWorkEndTimeStr
	 *            下班作业时间-字符，比如17:30
	 * @return
	 */
	public static double getMobileWorkHoursBySameDay(Timestamp theBeginTime,
			Timestamp theEndTime, ClockedResult theClockedResult,
			boolean isYuanShi) {
		double value = 0.0d;
		// 打卡地点
		String daKaLocation = theClockedResult.getDaKaLocation();

		Timestamp tmpBegin = getTimestampWithSecond0(theBeginTime);
		Timestamp tmpEnd = getTimestampWithSecond0(theEndTime);

		if ("门店-门店".equals(daKaLocation)) {
			double standWorkTimeLength = theClockedResult
					.getStandWorkTimeLength();
			value = getWorkTimeLength(tmpBegin, tmpEnd);
			if (standWorkTimeLength == 6) {
				// 小周 6小时工作制
				if (value >= 4.5d) {
					if (isYuanShi) {
						return value;
					}
					value = 6d;
				} else {
					value = getMobileWorkHoursBySameDay_8Hour(tmpBegin, tmpEnd,
							theClockedResult);
				}
			} else if (standWorkTimeLength == 8) {
				// 8小时工作制
				if (value >= 6d) {
					if (isYuanShi) {
						return value;
					}
					value = 8d;
				} else {
					value = getMobileWorkHoursBySameDay_8Hour(tmpBegin, tmpEnd,
							theClockedResult);
				}
			}
		} else {
			value = getMobileWorkHoursBySameDay_8Hour(tmpBegin, tmpEnd,
					theClockedResult);
		}
		return value;
	}

	public static double getMobileWorkHoursBySameDay_8Hour(
			Timestamp theBeginTime, Timestamp theEndTime,
			ClockedResult theClockedResult) {
		double value = 0.0d;
		Timestamp tmpBegin = getTimestampWithSecond0(theBeginTime);
		Timestamp tmpEnd = getTimestampWithSecond0(theEndTime);

		// 中间休息时段
		Timestamp xiuBeginTime = theClockedResult.getXiuBeginTime();
		Timestamp xiuEndTime = theClockedResult.getXiuEndTime();

		// 开始时间 在 12：00-13：00之间
		if (xiuBeginTime != null && xiuEndTime != null
				&& tmpBegin.getTime() >= xiuBeginTime.getTime()
				&& tmpBegin.getTime() <= xiuEndTime.getTime()) {
			// 结束时间 在 12：00-13：00之间
			if (xiuBeginTime != null && xiuEndTime != null
					&& tmpEnd.getTime() >= xiuBeginTime.getTime()
					&& tmpEnd.getTime() <= xiuEndTime.getTime()) {
				return 0;
			}
		}

		// 开始时间 在 12：00-13：00之间
		if (xiuBeginTime != null && xiuEndTime != null
				&& tmpBegin.getTime() >= xiuBeginTime.getTime()
				&& tmpBegin.getTime() <= xiuEndTime.getTime()) {
			tmpBegin = xiuEndTime;
		}

		// 结束时间 在 12：00-13：00之间
		if (xiuBeginTime != null && xiuEndTime != null
				&& tmpEnd.getTime() >= xiuBeginTime.getTime()
				&& tmpEnd.getTime() <= xiuEndTime.getTime()) {
			tmpEnd = xiuBeginTime;
		}

		value = getWorkTimeLength(tmpBegin, tmpEnd);

		// 打卡开始时间发生在中途休息前，并且打卡结束时间发生在中途休息后，则扣除中间休息的一小时
		if (xiuBeginTime != null && xiuEndTime != null
				&& tmpBegin.getTime() <= xiuBeginTime.getTime()
				&& tmpEnd.getTime() >= xiuEndTime.getTime()) {
			value = value - 1;
		}

		if (value > 8d) {
			value = 8d;
		}

		if (value < 0) {
			value = 0;
		}

		return value;
	}

	private static double getWorkTimeLength(Timestamp tmpBegin, Timestamp tmpEnd) {
		double value = 0d;
		int[] x = UtilDateTime.getTimeInterval(tmpBegin, tmpEnd);
		if (x[2] >= 30) {
			value = x[1] + 0.5;
		} else {
			value = x[1];
		}
		return value;
	}

	private static double getQingJiaTimeLength(Timestamp tmpBegin,
			Timestamp tmpEnd) {
		double value = 0d;
		int[] x = UtilDateTime.getTimeInterval(tmpBegin, tmpEnd);
		if (x[2] > 30) {
			value = x[1] + 1;
		} else if ((x[2] <= 30) && (x[2] > 0)) {
			value = x[1] + 0.5d;
		} else {
			value = x[1];
		}
		return value;
	}

}
