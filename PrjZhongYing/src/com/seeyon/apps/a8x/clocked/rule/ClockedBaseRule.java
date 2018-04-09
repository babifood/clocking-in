package com.seeyon.apps.a8x.clocked.rule;

import java.sql.Timestamp;

import com.seeyon.apps.a8x.clocked.entity.ClockedBizData;
import com.seeyon.apps.a8x.clocked.entity.ClockedResult;
import com.seeyon.apps.a8x.clocked.entity.Person;
import com.seeyon.apps.a8x.clocked.service.KaoQinDirectorMgr;
import com.seeyon.apps.a8x.clocked.util.ClockedUtil;
import com.seeyon.apps.a8x.common.exception.ProcessingException;
import com.seeyon.apps.a8x.common.util.UtilDateTime;
import com.seeyon.ctp.organization.bo.V3xOrgDepartment;

/*
 * 考勤基本规则
 */
public class ClockedBaseRule {

	private ClockedBaseRule() {

	}

	/*
	 * 获取加班失效日期
	 */
	public static java.sql.Date getJiaBanShiXiaoDate(java.sql.Date shengXiaoDate, String orgID) {
		java.sql.Date temp = null;
		V3xOrgDepartment tmpOrg = ClockedUtil.getOrgan(orgID);
		String name = tmpOrg.getName() == null ? "" : tmpOrg.getName().trim();
		if (name.indexOf("上海生产中心") != -1 || name.indexOf("华东团餐") != -1 || name.indexOf("广州生产中心") != -1) {
			temp = UtilDateTime.getDayEndByDay(shengXiaoDate, 30 * 6);
		} else {
			temp = java.sql.Date.valueOf("2099-01-01");
		}
		return temp;
	}

	/*
	 * 计算餐补
	 */
	public static void calcCanBu(ClockedResult theResult) {
		if (theResult.getClockFlag() < 1) {
			theResult.setCanBu(0);
			if (theResult.getJiaBan() >= 4.5) {
				String organ = ClockedUtil.getOrgan(theResult.getOrgan()).getName();
				if (organ.indexOf("集团") != -1 || organ.indexOf("营运") != -1) {
					theResult.setCanBu(1);
				}
			}
		} else {
			// 餐补个数
			if (theResult.getStandWorkTimeLength() == 8 && theResult.getWorkHours() >= 4.5d) {
				theResult.setCanBu(1);
			} else if (theResult.getStandWorkTimeLength() == 6 && theResult.getWorkHours() >= 3.5d) {
				theResult.setCanBu(1);
			} else {
				theResult.setCanBu(0);
			}
		}

	}
	
	public static void seteQingJiaValue(ClockedResult theResult,ClockedBizData theClockedBizData,double value) {
		if ("年假".equals(theClockedBizData.getBizFlag1())) {
			theResult.setNianJia(theResult.getNianJia() + value);
		} else if ("事假".equals(theClockedBizData.getBizFlag1())) {
			theResult.setShiJia(theResult.getShiJia() + value);
		} else if ("病假".equals(theClockedBizData.getBizFlag1())) {
			theResult.setBingJia(theResult.getBingJia() + value);
		} else if ("婚假".equals(theClockedBizData.getBizFlag1())) {
			theResult.setHunJia(theResult.getHunJia() + value);
		} else if ("丧假".equals(theClockedBizData.getBizFlag1())) {
			theResult.setSangJia(theResult.getSangJia() + value);
		} else if ("培训假".equals(theClockedBizData.getBizFlag1())) {
			theResult.setPeiXunJia(theResult.getPeiXunJia() + value);
		} else if ("产假".equals(theClockedBizData.getBizFlag1())) {
			theResult.setChanJia(theResult.getChanJia() + value);
		} else if ("陪产假".equals(theClockedBizData.getBizFlag1())) {
			theResult.setPeiChanJia(theResult.getPeiChanJia() + value);
		} else if ("调休".equals(theClockedBizData.getBizFlag1())) {
			theResult.setTiaoXiu(theResult.getTiaoXiu() + value);
		} else {
			theResult.setOtherQingJia(theResult.getOtherQingJia() + value);
		}
		// 设置请假时数
		theResult.setQingJia(theResult.getQingJia() + value);
	}

	/*
	 * 计算一天内的请假--普通考勤
	 */
	public static void calcOneQingJiaByOffice(ClockedResult theResult, ClockedBizData theClockedBizData) {
		Timestamp[] temp = calcActualTime(theResult, theClockedBizData);
		// 如果请假开始时间 》 下班时间
		if (temp[0].getTime() >= theResult.getStandEndTime().getTime()) {
			return;
		}
		// 如果请假结束时间 《 上班时间
		if (temp[1].getTime() <= theResult.getStandBeginTime().getTime()) {
			return;
		}

		double value = WorkHourRule.getQingJiaHoursBySameDay(temp[0], temp[1], theResult);
		if ("年假".equals(theClockedBizData.getBizFlag1())) {
			String organ = ClockedUtil.getOrgan(theResult.getOrgan()).getName();
			// 华东团餐事业部,上海生产中心,广州生产中心 按0.5小时扣，其他按4小时
			if (organ.indexOf("华东团餐事业部") != -1 || organ.indexOf("上海生产中心") != -1 || organ.indexOf("广州生产中心") != -1) {
				if (value <= 1) {
					value = 1;
				} else if (value > 1 && value <= 2) {
					value = 2;
				} else if (value > 2 && value <= 3) {
					value = 3;
				} else if (value > 3 && value <= 4) {
					value = 4;
				} else if (value > 4 && value <= 5) {
					value = 5;
				} else if (value > 5 && value <= 6) {
					value = 6;
				} else if (value > 6 && value <= 7) {
					value = 7;
				} else if (value > 7 && value <= 8) {
					value = 8;
				} else {

				}
			} else {
				if (value <= 4) {
					value = 4;
				} else {
					value = 8;
				}
			}
			theResult.setNianJia(theResult.getNianJia() + value);
		} else if ("事假".equals(theClockedBizData.getBizFlag1())) {
			theResult.setShiJia(theResult.getShiJia() + value);
		} else if ("病假".equals(theClockedBizData.getBizFlag1())) {
			theResult.setBingJia(theResult.getBingJia() + value);
		} else if ("婚假".equals(theClockedBizData.getBizFlag1())) {
			theResult.setHunJia(theResult.getHunJia() + value);
		} else if ("丧假".equals(theClockedBizData.getBizFlag1())) {
			theResult.setSangJia(theResult.getSangJia() + value);
		} else if ("培训假".equals(theClockedBizData.getBizFlag1())) {
			theResult.setPeiXunJia(theResult.getPeiXunJia() + value);
		} else if ("产假".equals(theClockedBizData.getBizFlag1())) {
			theResult.setChanJia(theResult.getChanJia() + value);
		} else if ("陪产假".equals(theClockedBizData.getBizFlag1())) {
			theResult.setPeiChanJia(theResult.getPeiChanJia() + value);
		} else if ("调休".equals(theClockedBizData.getBizFlag1())) {
			theResult.setTiaoXiu(theResult.getTiaoXiu() + value);
		} else {
			theResult.setOtherQingJia(theResult.getOtherQingJia() + value);
		}
		// 设置请假时数
		theResult.setQingJia(theResult.getQingJia() + value);
	}
	
	

	/**
	 * 计算实际的业务时间--只适合异动单/出差单/请假三种业务单据
	 * 
	 * @param theResult
	 * @param theClockedBizData
	 */
	public static Timestamp[] calcActualTime(ClockedResult theResult, ClockedBizData theClockedBizData) {
		Timestamp begin = null;
		Timestamp end = null;
		Timestamp bizBeginTime = WorkHourRule.getTimestampWithSecond0(theClockedBizData.getBeginTime());
		Timestamp bizEndTime = WorkHourRule.getTimestampWithSecond0(theClockedBizData.getEndTime());
		java.sql.Date clockDate = theResult.getClockDate();
		// 判断是否跨天

		begin = bizBeginTime;
		end = bizEndTime;
		if (WorkHourRule.isKuaTianOnBeginTime(theResult, bizBeginTime, bizEndTime)) {
			if (UtilDateTime.getDaysBetween(bizBeginTime, clockDate) > 0) {
				// 跨天
				if (bizBeginTime.before(theResult.getStandBeginTime())) {
					begin = (theResult.getStandBeginTime());
				}
			}
		} else {
			begin = (bizBeginTime);
		}
		if (WorkHourRule.isKuaTianOnEndTime(theResult, bizBeginTime, bizEndTime)) {
			if (UtilDateTime.getDaysBetween(clockDate, bizEndTime) > 0) {
				// 跨天
				if (bizEndTime.after(theResult.getStandEndTime())) {
					end = theResult.getStandEndTime();
				}
			}
		} else {
			end = (bizEndTime);
		}
		return new Timestamp[] { begin, end };
	}

	/*
	 * 计算一天内的请假---移动---用于界面-函数
	 */
	public static double calcQingJiaValue_Mobile(ClockedBizData theClockedBizData) {
		// 如果请假开始时间 》 下班时间
		if (theClockedBizData.getBeginTime().getTime() >= theClockedBizData.getEndTime().getTime()) {
			return -1;
		}

		int day = UtilDateTime.getDaysBetween(theClockedBizData.getBeginTime(), theClockedBizData.getEndTime());
		if (day != 0) {
			return -1;
		}

		double value = WorkHourRule.getQingJiaHoursBySameDay4Function(theClockedBizData.getBeginTime(), theClockedBizData.getEndTime());
		if ("年假".equals(theClockedBizData.getBizFlag1())) {
			Person person = null;
			try {
				person = ClockedUtil.loadPerson(Long.parseLong(theClockedBizData.getMemberID()));
			} catch (ProcessingException e) {
				// throw new BusinessException("无法获取人员..." +
				// e.getLocalizedMessage());
				return -1;
			}
			Long organId = KaoQinDirectorMgr.getMe().getOrganID(person.getDept_path());

			String organ = null;
			if (organId != null) {
				organ = ClockedUtil.getOrgan(organId.toString()).getName();
			}
			if (organ == null) {
				return -1;
			}

			// 华东团餐事业部,上海生产中心,广州生产中心 按0.5小时扣，其他按4小时
			if (organ.indexOf("华东团餐事业部") != -1 || organ.indexOf("上海生产中心") != -1 || organ.indexOf("广州生产中心") != -1) {
				if (value <= 1) {
					value = 1;
				} else if (value > 1 && value <= 2) {
					value = 2;
				} else if (value > 2 && value <= 3) {
					value = 3;
				} else if (value > 3 && value <= 4) {
					value = 4;
				} else if (value > 4 && value <= 5) {
					value = 5;
				} else if (value > 5 && value <= 6) {
					value = 6;
				} else if (value > 6 && value <= 7) {
					value = 7;
				} else if (value > 7 && value <= 8) {
					value = 8;
				} else {

				}
			} else {
				if (value <= 4) {
					value = 4;
				} else {
					value = 8;
				}
			}

		}
		return value;
	}
}
