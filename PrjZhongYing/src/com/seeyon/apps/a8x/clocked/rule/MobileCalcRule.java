package com.seeyon.apps.a8x.clocked.rule;

import java.util.List;

import com.seeyon.apps.a8x.clocked.entity.ClockedBizData;
import com.seeyon.apps.a8x.clocked.entity.ClockedResult;
import com.seeyon.apps.a8x.common.util.UtilDateTime;
import com.seeyon.ctp.common.exceptions.BusinessException;

/**
 * 移动-考勤业务数据--加班/出差/请假/异动计算规则
 */
public class MobileCalcRule {

	public MobileCalcRule() {

	}

	public void attach(ClockedResult theResult, List<ClockedBizData> theClockedBizDataList) throws BusinessException {
		if (theResult.getClockDate().toString().equals("2016-08-11") && theResult.getWorkNum().equals("100177")) {
			System.out.println();
		}

		if (theClockedBizDataList != null) {
			int size = theClockedBizDataList.size();
			for (int i = 0; i < size; i++) {
				attachOne(theResult, theClockedBizDataList.get(i));
			}
		}

		calculate(theResult);
	}

	/*
	 * 对最终的数据进行计算
	 */
	private void calculate(ClockedResult theResult) {
		if (theResult.getClockDate().toString().equals("2016-08-11") && theResult.getWorkNum().equals("100177")) {
			System.out.println();
		}

		// 对不需要上班的日期，进行处理
		if (theResult.getClockFlag() < 1) {
			theResult.setEventBeginTime(null);
			theResult.setEventEndTime(null);
			theResult.setBeginTime("");
			theResult.setEndTime("");

			theResult.setYiDong(0);
			theResult.setChuCha(0);
			theResult.setQingJia(0);
			theResult.setShiJia(0);
			theResult.setBingJia(0);
			theResult.setHunJia(0);
			theResult.setSangJia(0);
			theResult.setNianJia(0);
			theResult.setOtherQingJia(0);
			

			theResult.setChiDao(0);
			theResult.setZaoTui(0);
			theResult.setKuangGong(0);

			// 对于加班的数据，不能清空,比如周末加班
			// theResult.setJiaBan(0);
			if (theResult.getClockBeginTime() == null || theResult.getClockEndTime() == null) {
				theResult.setDaKaTimeLength(0);
			}
			
			// 餐补个数
			ClockedBaseRule.calcCanBu(theResult);
		} else {
			// 计算工作时长
			double workHours = 0;
			if (theResult.getYiDong() <= 0 && theResult.getQingJia() <= 0) {
				if (theResult.getClockBeginTime() != null && theResult.getClockEndTime() != null) {
					workHours = WorkHourRule.getMobileWorkHoursBySameDay(theResult.getClockBeginTime(), theResult.getClockEndTime(), theResult,false);
				}
			} else {
				workHours = theResult.getDaKaTimeLength() + theResult.getYiDong();
				if (workHours > theResult.getStandWorkTimeLength()) {
					workHours = theResult.getStandWorkTimeLength();
					workHours = workHours - theResult.getQingJia();
					workHours = workHours < 0 ? 0 : workHours;
				}
			}
			theResult.setWorkHours(workHours);
			// 计算最终有效时长
			double finalHours = 0;
			finalHours = workHours + theResult.getQingJia();
			if (finalHours > theResult.getStandWorkTimeLength()) {
				finalHours = theResult.getStandWorkTimeLength();
			}
			// 缺勤时长
			theResult.setQueQin(theResult.getStandWorkTimeLength() - finalHours);

			// 没有缺勤
			if (theResult.getQueQin() <= 0) {
				theResult.setChiDao(0);
				theResult.setZaoTui(0);
				theResult.setKuangGong(0);
			} else {
				theResult.setChiDao(0);
				theResult.setZaoTui(0);
				theResult.setKuangGong(1);
			}
			// 餐补个数
			ClockedBaseRule.calcCanBu(theResult);
		}
	}

	/*
	 * 适配
	 */
	private void attachOne(ClockedResult theResult, ClockedBizData theClockedBizData) throws BusinessException {
		if (theClockedBizData.getBeginTime() == null || theClockedBizData.getEndTime() == null) {
			throw new BusinessException("业务单据:" + theClockedBizData.getBillNum() + "单据类型:" + theClockedBizData.getBillType() + ",业务发生时间数据不合法,存在NULL值情况");
		}
		// 考勤日在业务发生日之前&&考勤日在业务发生日之后，退出
		if (!UtilDateTime.betweenByDay(theResult.getClockDate(), theClockedBizData.getBeginTime(), theClockedBizData.getEndTime())) {
			return;
		}
		// 单据类型
		String billType = theClockedBizData.getBillType();
		billType = billType == null ? "" : billType.trim();
		if (billType.equals("加班申请单")) {
			attachOneJiaBan(theResult, theClockedBizData);
		} else if (billType.equals("考勤异动单")) {
			attachOneYiDong(theResult, theClockedBizData);
		} else if (billType.equals("移动请假单")) {
			attachOneQingJia(theResult, theClockedBizData);
		} else {
			// do nothing
		}
	}

	/*
	 * 计算异动
	 */
	private void attachOneYiDong(ClockedResult theResult, ClockedBizData theClockedBizData) {
		// 如果原始打卡时长为正常
		if (theResult.getDaKaTimeLength() >= theResult.getStandWorkTimeLength()) {
			return;
		}
		double value = WorkHourRule.getMobileWorkHoursBySameDay_8Hour(theClockedBizData.getBeginTime(), theClockedBizData.getEndTime(), theResult);
		theResult.setYiDong(theResult.getYiDong() + value);
		if (theResult.getYiDong() > theResult.getStandWorkTimeLength()) {
			theResult.setYiDong(theResult.getStandWorkTimeLength());
		}
	}

	/*
	 * 计算请假
	 */
	private void attachOneQingJia(ClockedResult theResult, ClockedBizData theClockedBizData) {
		double value = theClockedBizData.getTimeLength();
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
	 * 计算加班
	 */
	private void attachOneJiaBan(ClockedResult theResult, ClockedBizData theClockedBizData) {
		if (UtilDateTime.getDaysBetween(theResult.getClockDate(), theClockedBizData.getBeginTime()) == 0) {
			if (theResult.getClockFlag() >= 1 && theClockedBizData.getBeginTime().getTime() >= theResult.getStandBeginTime().getTime() && theClockedBizData.getEndTime().getTime() <= theResult.getStandEndTime().getTime()) {
				return;
			}
			theResult.setJiaBan(theResult.getJiaBan() + theClockedBizData.getTimeLength());
		}
	}

}
