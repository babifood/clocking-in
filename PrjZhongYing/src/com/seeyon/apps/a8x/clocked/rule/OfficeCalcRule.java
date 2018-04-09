package com.seeyon.apps.a8x.clocked.rule;

import java.sql.Timestamp;
import java.util.List;

import com.seeyon.apps.a8x.clocked.entity.ClockedBizData;
import com.seeyon.apps.a8x.clocked.entity.ClockedResult;
import com.seeyon.apps.a8x.common.util.UtilDateTime;
import com.seeyon.ctp.common.exceptions.BusinessException;

/**
 * 办公室-考勤业务数据--加班/出差/请假/异动计算规则
 */
public class OfficeCalcRule {

	private Timestamp yiDongBegin;
	private Timestamp yiDongEnd;

	private Timestamp qingJiaBegin;
	private Timestamp qingJiaEnd;

	private Timestamp chuChaiBegin;
	private Timestamp chuChaiEnd;

	public OfficeCalcRule() {

	}

	public void attach(ClockedResult theResult, List<ClockedBizData> theClockedBizDataList) throws BusinessException {
		if (theClockedBizDataList != null) {
			int size = theClockedBizDataList.size();
			for (int i = 0; i < size; i++) {
				attachOne(theResult, theClockedBizDataList.get(i));
			}
		}

		Timestamp tmpBegin = getMin(yiDongBegin, qingJiaBegin);
		tmpBegin = getMin(tmpBegin, chuChaiBegin);

		Timestamp tmpEnd = getMax(yiDongEnd, qingJiaEnd);
		tmpEnd = getMax(tmpEnd, chuChaiEnd);

		theResult.setEventBeginTime(tmpBegin);
		theResult.setEventEndTime(tmpEnd);

		calculate(theResult);
	}

	/*
	 * 对最终的数据进行计算
	 */
	private void calculate(ClockedResult theResult) {
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
			// 计算异动时间
			if (yiDongBegin != null && yiDongEnd != null) {
				double yiDongHours = WorkHourRule.getOfficeWorkHoursBySameDay(yiDongBegin, yiDongEnd, theResult);
				theResult.setYiDong(yiDongHours);
			}
			// 计算最终有效时长
			double finalHours = 0;
			if (theResult.getFinalBeginTime() != null && theResult.getFinalEndTime() != null) {
				finalHours = WorkHourRule.getOfficeWorkHoursBySameDay(theResult.getFinalBeginTime(), theResult.getFinalEndTime(), theResult);
				// if(theResult.getNianJia() > finalHours){
				// finalHours = theResult.getNianJia();
				// }
			}
			// 缺勤时长
			theResult.setQueQin(theResult.getStandWorkTimeLength() - finalHours);
			// 实际工作时长
			if (theResult.getStandWorkTimeLength() - theResult.getQingJia() - theResult.getQueQin() < 0) {
				theResult.setWorkHours(0);
			} else {
				theResult.setWorkHours(theResult.getStandWorkTimeLength() - theResult.getQingJia() - theResult.getQueQin());
			}
			// 没有缺勤
			if (theResult.getQueQin() <= 0) {
				theResult.setChiDao(0);
				theResult.setZaoTui(0);
				theResult.setKuangGong(0);
			} else {
				if (theResult.getFinalBeginTime() == null || theResult.getFinalEndTime() == null) {
					theResult.setKuangGong(1);
				} else {
					// 迟到
					long chiDaoValue = 0;
					if (theResult.getFinalBeginTime().after(theResult.getStandBeginTime())) {
						chiDaoValue = UtilDateTime.getMinuteBetween(theResult.getStandBeginTime(), theResult.getFinalBeginTime());
					}
					// 早退
					long zaoTuiValue = 0;
					if (theResult.getFinalEndTime().before(theResult.getStandEndTime())) {
						zaoTuiValue = UtilDateTime.getMinuteBetween(theResult.getFinalEndTime(), theResult.getStandEndTime());
					}
					if (chiDaoValue > 15 || zaoTuiValue > 15) {
						theResult.setKuangGong(1);
					} else {
						if (chiDaoValue > 0) {
							theResult.setChiDao(1);
							//缺勤置0
							theResult.setQueQin(0);
						}
						if (zaoTuiValue > 0) {
							theResult.setZaoTui(1);
							//缺勤置0
							theResult.setQueQin(0);
						}
					}
				}
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
		}
		if (billType.equals("考勤异动单")) {
			attachOneYiDong(theResult, theClockedBizData);
		} else if (billType.equals("请假申请单")) {
			attachOneQingJia(theResult, theClockedBizData);
		} else if (billType.equals("出差申请单")) {
			attachOneChuChai(theResult, theClockedBizData);
		} else {
			// do nothing
		}
	}

	/*
	 * 计算异动
	 */
	private void attachOneYiDong(ClockedResult theResult, ClockedBizData theClockedBizData) {
		// 上班和下班都打卡了,并且上下班打卡时间都正常
		if (theResult.getClockBeginTime() != null && theResult.getClockEndTime() != null && theResult.getStandBeginTime().getTime() >= theResult.getClockBeginTime().getTime() && theResult.getStandEndTime().getTime() <= theResult.getClockEndTime().getTime()) {
			return;
		}

		Timestamp[] temp = ClockedBaseRule.calcActualTime(theResult, theClockedBizData);
		// 如果异动开始时间 》 下班时间
		//2017-5-17李志强注释了这段代码
//		if (temp[0].getTime() > theResult.getStandEndTime().getTime()) {
//			return;
//		}
		// 如果异动结束时间 《 上班时间
		//2017-5-17李志强注释了这段代码
//		if (temp[1].getTime() < theResult.getStandBeginTime().getTime()) {
//			return;
//		}
		// 上班和下班都打卡了,异动时间在打卡时间之间
		if (theResult.getClockBeginTime() != null && theResult.getClockEndTime() != null && temp[0].getTime() >= theResult.getClockBeginTime().getTime() && temp[1].getTime() <= theResult.getClockEndTime().getTime()) {
			return;
		}

		if (yiDongBegin == null) {
			yiDongBegin = temp[0];
		} else {
			if (yiDongBegin.getTime() > temp[0].getTime()) {
				yiDongBegin = temp[0];
			}
		}
		if (yiDongEnd == null) {
			yiDongEnd = temp[1];
		} else {
			if (yiDongEnd.getTime() < temp[1].getTime()) {
				yiDongEnd = temp[1];
			}
		}
	}

	/*
	 * 计算请假
	 */
	private void attachOneQingJia(ClockedResult theResult, ClockedBizData theClockedBizData) {
		Timestamp[] temp = ClockedBaseRule.calcActualTime(theResult, theClockedBizData);
		// 如果请假开始时间 》 下班时间
		if (temp[0].getTime() >= theResult.getStandEndTime().getTime()) {
			return;
		}
		// 如果请假结束时间 《 上班时间
		if (temp[1].getTime() <= theResult.getStandBeginTime().getTime()) {
			return;
		}
		if (qingJiaBegin == null) {
			qingJiaBegin = temp[0];
		} else {
			if (qingJiaBegin.getTime() > temp[0].getTime()) {
				qingJiaBegin = temp[0];
			}
		}
		if (qingJiaEnd == null) {
			qingJiaEnd = temp[1];
		} else {
			if (qingJiaEnd.getTime() < temp[1].getTime()) {
				qingJiaEnd = temp[1];
			}
		}
		ClockedBaseRule.calcOneQingJiaByOffice(theResult, theClockedBizData);
	}

	/*
	 * 计算出差
	 */
	private void attachOneChuChai(ClockedResult theResult, ClockedBizData theClockedBizData) {
		Timestamp[] temp = ClockedBaseRule.calcActualTime(theResult, theClockedBizData);
		// 如果出差开始时间 》 下班时间
		if (temp[0].getTime() >= theResult.getStandEndTime().getTime()) {
			return;
		}
		// 如果出差结束时间 《 上班时间
		if (temp[1].getTime() <= theResult.getStandBeginTime().getTime()) {
			return;
		}
		// 上班和下班都打卡了
		if (theResult.getClockBeginTime() != null && theResult.getClockEndTime() != null && temp[0].getTime() >= theResult.getClockBeginTime().getTime() && temp[1].getTime() <= theResult.getClockEndTime().getTime()) {
			return;
		}
		if (chuChaiBegin == null) {
			chuChaiBegin = temp[0];
		} else {
			if (chuChaiBegin.getTime() > temp[0].getTime()) {
				chuChaiBegin = temp[0];
			}
		}
		if (chuChaiEnd == null) {
			chuChaiEnd = temp[1];
		} else {
			if (chuChaiEnd.getTime() < temp[1].getTime()) {
				chuChaiEnd = temp[1];
			}
		}
		double value = WorkHourRule.getOfficeWorkHoursBySameDay(temp[0], temp[1], theResult);
		theResult.setChuCha(theResult.getChuCha() + value);
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

	private Timestamp getMin(Timestamp x1, Timestamp x2) {
		if (x1 == null) {
			return x2;
		} else {
			if (x2 == null) {
				return x1;
			} else {
				if (x1.before(x2)) {
					return x1;
				} else {
					return x2;
				}
			}
		}
	}

	private Timestamp getMax(Timestamp x1, Timestamp x2) {
		if (x1 == null) {
			return x2;
		} else {
			if (x2 == null) {
				return x1;
			} else {
				if (x1.before(x2)) {
					return x2;
				} else {
					return x1;
				}
			}
		}
	}
}
