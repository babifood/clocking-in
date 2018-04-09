package com.seeyon.apps.a8x.clocked.rule;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.seeyon.apps.a8x.clocked.dao.NianJiaAccountDao;
import com.seeyon.apps.a8x.clocked.entity.NianJiaAccount;
import com.seeyon.apps.a8x.common.exception.ProcessingException;
import com.seeyon.apps.a8x.common.jdbc.DAOContext;
import com.seeyon.apps.a8x.common.jdbc.JdbcWorkUnit;
import com.seeyon.apps.a8x.common.jdbc.JdbcWorker;
import com.seeyon.apps.a8x.common.util.UtilDateTime;

public class NianJiaWriteDownRule {

	public NianJiaWriteDownRule() {

	}

	public boolean check(long theMemberId, double theQianJiaTimeLength, java.util.Date theBillDate) throws Exception {
		int year = UtilDateTime.getYear(theBillDate);
		int month = UtilDateTime.getMonth(theBillDate);
		NianJiaAccount tmpNianJiaAccount = loadByMemberID(theMemberId, year);
		if (tmpNianJiaAccount == null) {
			return false;
		} else {
			// 年假数/12*申请当月月份
			double x = tmpNianJiaAccount.getTimeLength() * month;
			// System.out.println("年假剩余数"+x);
			x = x / 12+tmpNianJiaAccount.getPreTimeLength();
			BigDecimal tmpBigDecimal = new BigDecimal(x);
			tmpBigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
			x = tmpBigDecimal.doubleValue();
			// System.out.println("本月可用数数"+x + ";请假数" +
			// tmpClockedResult.getQingJia());
			if (theQianJiaTimeLength > x) {
				return false;
			}
		}
		return true;
	}

	public void execute(long theMemberId, double theQianJiaTimeLength, java.util.Date theBillDate) throws Exception {
		List<NianJiaAccount> nianJiaAccounts = new ArrayList<NianJiaAccount>(10);
		int year = UtilDateTime.getYear(theBillDate);
		NianJiaAccount tmpNianJiaAccount = loadByMemberID(theMemberId, year);
		if (tmpNianJiaAccount != null) {
			tmpNianJiaAccount.setWritedownValue(theQianJiaTimeLength);
			nianJiaAccounts.add(tmpNianJiaAccount);
		} else {
			throw new java.lang.Exception("员工[" + theMemberId + "], " + year + "年度,年假账信息没有维护!!");
		}

		if (!nianJiaAccounts.isEmpty()) {
			writeDown(nianJiaAccounts);
		}
	}

	private static NianJiaAccount loadByMemberID(final long theMemeberID, final int year) throws ProcessingException {
		return JdbcWorker.wrapNotInTransaction(new JdbcWorkUnit<NianJiaAccount>() {
			public NianJiaAccount execute(DAOContext daoContext) throws ProcessingException {
				NianJiaAccountDao dao = new NianJiaAccountDao(daoContext);
				return dao.loadByMemberID(theMemeberID, year);
			}
		});
	}

	private static void writeDown(final List<NianJiaAccount> theList) throws ProcessingException {
		JdbcWorker.wrapInTransaction(new JdbcWorkUnit<Boolean>() {
			public Boolean execute(DAOContext daoContext) throws ProcessingException {
				NianJiaAccountDao dao = new NianJiaAccountDao(daoContext);
				int size = theList == null ? 0 : theList.size();
				NianJiaAccount tmpNianJiaAccount = null;
				for (int i = 0; i < size; i++) {
					tmpNianJiaAccount = theList.get(i);
					dao.updateDiKou(tmpNianJiaAccount.getId(), tmpNianJiaAccount.getWritedownValue());
				}
				return true;
			}
		});
	}

}
