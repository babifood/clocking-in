package com.seeyon.apps.a8x.clocked.check;

import java.util.ArrayList;
import java.util.List;

import com.seeyon.apps.a8x.clocked.dao.QingJiaBillDao;
import com.seeyon.apps.a8x.clocked.entity.ClockedBizData;
import com.seeyon.apps.a8x.clocked.entity.ClockedResult;
import com.seeyon.apps.a8x.clocked.rule.ClockedBizDataResolveRule;
import com.seeyon.apps.a8x.clocked.rule.JiaBanWriteDownRule;
import com.seeyon.apps.a8x.clocked.rule.NianJiaWriteDownRule;
import com.seeyon.apps.a8x.common.exception.ProcessingException;
import com.seeyon.apps.a8x.common.jdbc.DAOContext;
import com.seeyon.apps.a8x.common.jdbc.JdbcWorkUnit;
import com.seeyon.apps.a8x.common.jdbc.JdbcWorker;

public class QingJiaChecker {

	private String message = "";

	public QingJiaChecker() {

	}

	public String getMessage() {
		return message;
	}

	/*
	 * 判断一天内请假不能超过标准工作时长
	 */
	public boolean checkOnStart(ClockedBizData clockedBizData) throws Exception {
		ClockedBizDataResolveRule rule = new ClockedBizDataResolveRule(clockedBizData);
		List<ClockedResult> tmpList = rule.resolveQinJiaDatas();
		if (tmpList == null || tmpList.isEmpty()) {
			message = "请假时间不合法,不是有效的请假时段.";
			return false;
		}
		// 获取历史请假数据
		List<ClockedResult> tmpHisList = new ArrayList<ClockedResult>(10);
		List<ClockedBizData> tempHisDatas = loadByMemberID(Long.valueOf(clockedBizData.getMemberID()), clockedBizData.getBeginTime(), clockedBizData.getEndTime());
		if (tempHisDatas != null && tempHisDatas.size() > 0) {
			int len = tempHisDatas.size();
			for (int k = 0; k < len; k++) {
				rule = new ClockedBizDataResolveRule(tempHisDatas.get(k));
				tmpHisList.addAll(rule.resolveQinJiaDatas());
			}
		}
		ClockedResult tmpClockedResult = null;
		if (tmpHisList.size() > 0) {
			int size = tmpList.size();
			for (int i = 0; i < size; i++) {
				tmpClockedResult = tmpList.get(i);
				merge(tmpClockedResult, tmpHisList);
			}
		}
		int size = tmpList.size();
		for (int i = 0; i < size; i++) {
			tmpClockedResult = tmpList.get(i);
			if (tmpClockedResult.getQingJia() > tmpClockedResult.getStandWorkTimeLength()) {
				if (message.length() > 0) {
					message += ";  \n";
				}
				message += tmpClockedResult.getClockDate() + "请假时间累计超过标准工作时长" + tmpClockedResult.getStandWorkTimeLength();
			}
		}
		if (message.length() > 0) {
			return false;
		}

		return true;
	}

	private void merge(ClockedResult tmpClockedResult, List<ClockedResult> tmpHisList) {
		int size = tmpHisList.size();
		ClockedResult tmpHis = null;
		for (int i = 0; i < size; i++) {
			tmpHis = tmpHisList.get(i);
			if (tmpHis.getClockDate().toString().equals(tmpClockedResult.getClockDate().toString())) {
				tmpClockedResult.setQingJia(tmpClockedResult.getQingJia() + tmpHis.getQingJia());
			}
		}
	}

	private static List<ClockedBizData> loadByMemberID(final long theMemberID, final java.sql.Timestamp theBegin, final java.sql.Timestamp theEnd) throws ProcessingException {
		return JdbcWorker.wrapNotInTransaction(new JdbcWorkUnit<List<ClockedBizData>>() {
			public List<ClockedBizData> execute(DAOContext daoContext) throws ProcessingException {
				QingJiaBillDao dao = new QingJiaBillDao(daoContext);
				return dao.load(theMemberID, theBegin, theEnd);
			}
		});
	}

	/**
	 * 普通考勤的业务数据
	 */
	public ClockedBizData readByOffice(final long theRecordId) throws ProcessingException {
		return JdbcWorker.wrapNotInTransaction(new JdbcWorkUnit<ClockedBizData>() {
			public ClockedBizData execute(DAOContext daoContext) throws ProcessingException {
				QingJiaBillDao dao = new QingJiaBillDao(daoContext);
				return dao.readByOffice(theRecordId);
			}
		});
	}

	/**
	 * 移动考勤的业务数据
	 */
	public ClockedBizData readByMobile(final long theRecordId) throws ProcessingException {
		return JdbcWorker.wrapNotInTransaction(new JdbcWorkUnit<ClockedBizData>() {
			public ClockedBizData execute(DAOContext daoContext) throws ProcessingException {
				QingJiaBillDao dao = new QingJiaBillDao(daoContext);
				return dao.readByMobile(theRecordId);
			}
		});
	}

	/*
	 * 请假单-人事核定-最后判断加班账是否够？
	 */
	public boolean checkOnFinish(long theRecordId, boolean isMobileType) throws Exception {
		ClockedBizData clockedBizData = null;
		if (isMobileType) {
			clockedBizData = readByMobile(theRecordId);
		} else {
			clockedBizData = readByOffice(theRecordId);
		}
		if (clockedBizData == null) {
			message = "读取表单数据失败.";
			return false;
		}

		String typeName = clockedBizData.getBillType();
		String[] temps = null;
		if ("调休".equals(typeName)) {
			temps = checkTiaoXiu(Long.parseLong(clockedBizData.getMemberID()), clockedBizData.getTimeLength());
		} else if ("年假".equals(typeName)) {
			temps = checkNianJia(Long.parseLong(clockedBizData.getMemberID()), clockedBizData.getTimeLength(), clockedBizData.getBeginTime());
		}
		if (temps != null) {
			message = temps[1];
			return false;
		}
		return true;
	}

	public String[] checkNianJia(long theMemberId, double theQianJiaTimeLength, java.util.Date theBillDate) {
		boolean flag = false;
		NianJiaWriteDownRule rule = new NianJiaWriteDownRule();
		try {
			flag = rule.check(theMemberId, theQianJiaTimeLength, theBillDate);
		} catch (Exception e) {
			return new String[] { "1", "系统异常..." + e.getLocalizedMessage() };
		}
		if (flag == false) {
			return new String[] { "1", "超过额定可休年假时长...." };
		}
		return null;
	}

	public String[] checkTiaoXiu(long theMemberId, double theQianJiaTimeLength) {
		boolean flag = false;
		JiaBanWriteDownRule rule = new JiaBanWriteDownRule();
		try {
			flag = rule.check(theMemberId, theQianJiaTimeLength);
		} catch (Exception e) {
			return new String[] { "1", "系统异常..." + e.getLocalizedMessage() };
		}
		if (flag == false) {
			return new String[] { "1", "可调休数的加班数不够...." };
		}
		return null;
	}

}
