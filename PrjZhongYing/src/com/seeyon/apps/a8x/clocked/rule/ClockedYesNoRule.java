package com.seeyon.apps.a8x.clocked.rule;

import java.util.List;

import com.seeyon.apps.a8x.clocked.dao.HolidayDao;
import com.seeyon.apps.a8x.clocked.dao.WorkCalendarDao;
import com.seeyon.apps.a8x.clocked.entity.ClockedResult;
import com.seeyon.apps.a8x.clocked.entity.Holiday;
import com.seeyon.apps.a8x.clocked.entity.WorkCalendar;
import com.seeyon.apps.a8x.common.exception.ProcessingException;
import com.seeyon.apps.a8x.common.jdbc.DAOContext;
import com.seeyon.apps.a8x.common.jdbc.JdbcWorkUnit;
import com.seeyon.apps.a8x.common.jdbc.JdbcWorker;
import com.seeyon.apps.a8x.common.util.UtilDateTime;
import com.seeyon.apps.a8x.common.util.UtilString;
import com.seeyon.ctp.common.exceptions.BusinessException;

/**
 * 判断某人某天是否需要上班-规则
 * 
 */
public class ClockedYesNoRule {
	// 不需要考勤
	public static final int Clock_Flag_NOT_WORK = 0;
	// 工作日
	public static final int Clock_Flag_WORK_NORMAL = 1;
	// 休息日上班
	public static final int Clock_Flag_WEEK_END_YES = 2;
	// 节假日上班---不会发生，按加班处理
	public static final int Clock_Flag_HOLIDAY_YES = 3;

	// 节假日数据集
	private List<Holiday> holidayList;

	// 特殊排班日数据集
	private List<WorkCalendar> workTimeList;

	private int year;
	private int month;

	public ClockedYesNoRule(int year, int month) throws BusinessException {
		this.year = year;
		this.month = month;
		try {
			holidayList = loadHolidayDatas(year);
		} catch (ProcessingException e1) {
			throw new BusinessException("读取假日数据失败." + e1.getLocalizedMessage());
		}

		try {
			workTimeList = loadWorkCalendarDatas(year, month);
		} catch (ProcessingException e1) {
			throw new BusinessException("读取特殊排班数据失败." + e1.getLocalizedMessage());
		}
	}

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public void markClockedYesNo(ClockedResult theResult) {
		int size = holidayList == null ? 0 : holidayList.size();
		Holiday tmpHoliday = null;
		for (int i = 0; i < size; i++) {
			tmpHoliday = holidayList.get(i);
			// 在假日内
			if (UtilDateTime.betweenByDay(theResult.getClockDate(), tmpHoliday.getBeginDate(), tmpHoliday.getEndDate())) {
				// 考勤标志
				theResult.setClockFlag(Clock_Flag_NOT_WORK);
				theResult.setHoliday(true);
			}
		}

		size = workTimeList == null ? 0 : workTimeList.size();
		WorkCalendar tmpWorkCalendar = null;
		String tmpOrgan = null;
		for (int i = 0; i < size; i++) {
			tmpWorkCalendar = workTimeList.get(i);
			tmpOrgan = theResult.getOrgan();
			tmpOrgan = tmpOrgan == null ? "" : tmpOrgan.trim();
			if (tmpOrgan.equals(tmpWorkCalendar.getOrgan()) && UtilDateTime.getDaysBetween(theResult.getClockDate(), tmpWorkCalendar.getWorkDate()) == 0) {
				markByWorkCalendar(theResult, tmpWorkCalendar);
			}
		}
	}

	private void markByWorkCalendar(ClockedResult theResult, WorkCalendar tmpWorkCalendar) {
		// 数据策略
		String dataPolicy = tmpWorkCalendar.getDataflag_name() == null ? "" : tmpWorkCalendar.getDataflag_name().trim();
		// 按人
		if (dataPolicy.indexOf("人员") != -1) {
			if (tmpWorkCalendar.getMember().indexOf(theResult.getMemberID()) != -1) {
				// 人员优先级
				theResult.setMarkByPerson(true);
				markClockFlag(theResult, tmpWorkCalendar);
			}
		} else if (dataPolicy.indexOf("部门") != -1) {
			if (theResult.isMarkByPerson()) {
				// 如果按人员已设置过，退出
				return;
			}
			if (theResult.getDept_path().startsWith(tmpWorkCalendar.getDept_path())) {
				// 部门优先级
				theResult.setMarkByDept(true);
				markClockFlag(theResult, tmpWorkCalendar);
			}
		} else if (dataPolicy.indexOf("机构") != -1) {
			if (theResult.isMarkByPerson()) {
				// 如果按人员已设置过，退出
				return;
			}
			if (theResult.isMarkByDept()) {
				// 按部门设置过，退出
				return;
			}
			theResult.setMarkByOrg(true);
			markClockFlag(theResult, tmpWorkCalendar);
		} else {
			return;
		}
	}

	private void markClockFlag(ClockedResult theResult, WorkCalendar tmpWorkCalendar) {
		if (isNeedClocked(tmpWorkCalendar)) {
			if (theResult.isHoliday()) {
				theResult.setClockFlag(Clock_Flag_HOLIDAY_YES);
			} else if (theResult.isWeekEnd()) {
				theResult.setClockFlag(Clock_Flag_WEEK_END_YES);
			} else {
				theResult.setClockFlag(Clock_Flag_WORK_NORMAL);
			}
			String[] x = UtilString.tokenize(tmpWorkCalendar.getWorkgroup_name(), "~");
			if (x != null && x.length >= 2) {
				theResult.setBeginTime(x[0]);
				theResult.setEndTime(x[1]);
				WorkHourRule.setStandWorkTimeLength(theResult);
			}
		} else {
			theResult.setClockFlag(Clock_Flag_NOT_WORK);
			// theResult.setBeginTime("");
			// theResult.setEndTime("");
			// theResult.setStandWorkTimeLength(0);
		}
	}

	private boolean isNeedClocked(WorkCalendar tmpWorkCalendar) {
		String flag = tmpWorkCalendar.getWorkFlag() == null ? "" : tmpWorkCalendar.getWorkFlag().trim();
		if ("1".equals(flag)) {
			return true;
		} else {
			return false;
		}
	}

	private List<Holiday> loadHolidayDatas(final int year) throws ProcessingException {
		return JdbcWorker.wrapNotInTransaction(new JdbcWorkUnit<List<Holiday>>() {
			public List<Holiday> execute(DAOContext daoContext) throws ProcessingException {
				HolidayDao dao = new HolidayDao(daoContext);
				return dao.load(year);
			}
		});
	}

	private List<WorkCalendar> loadWorkCalendarDatas(final int year, final int month) throws ProcessingException {
		return JdbcWorker.wrapNotInTransaction(new JdbcWorkUnit<List<WorkCalendar>>() {
			public List<WorkCalendar> execute(DAOContext daoContext) throws ProcessingException {
				WorkCalendarDao dao = new WorkCalendarDao(daoContext);
				return dao.load(year, month);
			}
		});
	}

}
