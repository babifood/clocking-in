package com.seeyon.apps.a8x.clocked.rule;

import java.util.ArrayList;
import java.util.List;

import com.seeyon.apps.a8x.clocked.entity.ClockedBizData;
import com.seeyon.apps.a8x.clocked.entity.ClockedResult;
import com.seeyon.apps.a8x.clocked.entity.Person;
import com.seeyon.apps.a8x.clocked.util.ClockedUtil;
import com.seeyon.apps.a8x.common.exception.ProcessingException;
import com.seeyon.apps.a8x.common.util.UtilDateTime;
import com.seeyon.ctp.common.exceptions.BusinessException;

/*
 * 加班/出差/请假/异动-业务数据分解规则
 */
public class ClockedBizDataResolveRule {

	private ClockedBizData clockedBizData;

	public ClockedBizDataResolveRule(ClockedBizData theClockedBizData) {
		this.clockedBizData = theClockedBizData;
	}

	/*
	 * 分解到每一天
	 */
	public List<ClockedResult> resolve() throws BusinessException {
		Person person = null;
		try {
			person = ClockedUtil.loadPerson(Long.parseLong(clockedBizData.getMemberID()));
		} catch (ProcessingException e) {
			throw new BusinessException("无法获取人员..." + e.getLocalizedMessage());
		}

		if (person == null) {
			throw new BusinessException("该人员不在考勤名单中...");
		}

		int day = UtilDateTime.getDaysBetween(clockedBizData.getBeginTime(), clockedBizData.getEndTime());
		List<ClockedResult> tmpList = new ArrayList<ClockedResult>(day);
		ClockedResult tmpClockedResult = null;
		java.sql.Date tmpDate = null;

		ClockedYesNoRule clockedYesNoRule = null;
		if (day == 0) {
			tmpDate = new java.sql.Date(clockedBizData.getBeginTime().getTime());
			// 考勤规则
			clockedYesNoRule = new ClockedYesNoRule(UtilDateTime.getYear(tmpDate), UtilDateTime.getMonth(tmpDate));
			tmpClockedResult = ClockedUtil.markClockedYesNo(person, tmpDate, clockedYesNoRule);
			tmpList.add(tmpClockedResult);

		} else {
			int year = 0;
			int month = 0;

			for (int i = 0; i <= day; i++) {
				tmpDate = UtilDateTime.getDayEndByDay(clockedBizData.getBeginTime(), i);
				year = UtilDateTime.getYear(tmpDate);
				month = UtilDateTime.getMonth(tmpDate);
				if (clockedYesNoRule == null) {
					// 考勤规则
					clockedYesNoRule = new ClockedYesNoRule(year, month);
				} else {
					if (year != clockedYesNoRule.getYear()) {
						// 考勤规则
						clockedYesNoRule = new ClockedYesNoRule(year, month);
					} else {
						if (month != clockedYesNoRule.getMonth()) {
							// 考勤规则
							clockedYesNoRule = new ClockedYesNoRule(year, month);
						}
					}
				}
				tmpClockedResult = ClockedUtil.markClockedYesNo(person, tmpDate, clockedYesNoRule);
				tmpList.add(tmpClockedResult);
			}
		}
		return tmpList;
	}

	/*
	 * 分解后的请假数据
	 */
	public List<ClockedResult> resolveQinJiaDatas() throws BusinessException {
		List<ClockedResult> tmpList = resolve();
		int size = tmpList == null ? 0 : tmpList.size();
		if (size == 0) {
			return null;
		}

		List<ClockedResult> tmpNewList = new ArrayList<ClockedResult>(size);
		ClockedResult tmpClockedResult = null;
		for (int i = 0; i < size; i++) {
			tmpClockedResult = tmpList.get(i);
			if (tmpClockedResult.getClockFlag() > 0) {
				ClockedBaseRule.calcOneQingJiaByOffice(tmpClockedResult, clockedBizData);
				if (tmpClockedResult.getQingJia() > 0) {
					tmpNewList.add(tmpClockedResult);
				}
			}
		}
		return tmpNewList;
	}
	
	public  double getQianJiaTimeLength() throws BusinessException {
		List<ClockedResult> tmpList =resolveQinJiaDatas();
		if (tmpList == null || tmpList.isEmpty()) {
			return 0;
		}

		double value = 0;
		for (ClockedResult temp : tmpList) {
			value += temp.getQingJia();
		}
		return value;

	}

}
