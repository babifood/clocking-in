package com.seeyon.apps.a8x.clocked.rule;

import java.util.List;

import com.seeyon.apps.a8x.clocked.dao.JiaBanAccountReadDao;
import com.seeyon.apps.a8x.clocked.dao.JiaBanAccountUpdateDao;
import com.seeyon.apps.a8x.clocked.entity.JiaBanAccount;
import com.seeyon.apps.a8x.common.exception.ProcessingException;
import com.seeyon.apps.a8x.common.jdbc.DAOContext;
import com.seeyon.apps.a8x.common.jdbc.JdbcWorkUnit;
import com.seeyon.apps.a8x.common.jdbc.JdbcWorker;

public class JiaBanWriteDownRule {

	private List<JiaBanAccount> jiaBanAccounts;

	public JiaBanWriteDownRule() {

	}

	public boolean check(long theMemberId,double theQianJiaTimeLength) throws Exception {
		double theValue = theQianJiaTimeLength;
		// 读取加班账数据
		jiaBanAccounts = loadByMemberID(theMemberId);
		int size = jiaBanAccounts == null ? 0 : jiaBanAccounts.size();
		JiaBanAccount tmpJiaBanAccount = null;
		double sum = 0;
		for (int i = 0; i < size; i++) {
			tmpJiaBanAccount = jiaBanAccounts.get(i);
			sum += tmpJiaBanAccount.getShengYuTimeLength();
		}
		// 加班数不够
		if (sum < theValue) {
			return false;
		} else {
			return true;
		}
	}

	public void execute(long theMemberId,double theQianJiaTimeLength) throws Exception {
		double theValue = theQianJiaTimeLength;
		// 读取加班账数据
		jiaBanAccounts = loadByMemberID(theMemberId);
		int size = jiaBanAccounts == null ? 0 : jiaBanAccounts.size();
		JiaBanAccount tmpJiaBanAccount = null;
		double sum = 0;
		for (int i = 0; i < size; i++) {
			tmpJiaBanAccount = jiaBanAccounts.get(i);
			sum += tmpJiaBanAccount.getShengYuTimeLength();
		}

		// 加班数不够
		if (sum < theValue) {
			// 前期已做数据校验，这种情况不会发生
		}

		double need = theValue;
		double value = 0;
		for (int i = 0; i < size; i++) {
			tmpJiaBanAccount = jiaBanAccounts.get(i);
			if (tmpJiaBanAccount.getShengYuTimeLength() >= need) {
				tmpJiaBanAccount.setWritedownValue(need);
				break;
			} else {
				tmpJiaBanAccount.setWritedownValue(tmpJiaBanAccount.getShengYuTimeLength());
				value += tmpJiaBanAccount.getShengYuTimeLength();
				need = theValue - value;
			}
		}
		// 加班数不够
		if (sum < theValue) {

		}
		// 扣减
		writeDown(jiaBanAccounts);
	}



	private static List<JiaBanAccount> loadByMemberID(final long theMemeberID) throws ProcessingException {
		return JdbcWorker.wrapNotInTransaction(new JdbcWorkUnit<List<JiaBanAccount>>() {
			public List<JiaBanAccount> execute(DAOContext daoContext) throws ProcessingException {
				JiaBanAccountReadDao dao = new JiaBanAccountReadDao(daoContext);
				return dao.loadByMemberID(theMemeberID);
			}
		});
	}

	private static void writeDown(final List<JiaBanAccount> theList) throws ProcessingException {
		JdbcWorker.wrapInTransaction(new JdbcWorkUnit<Boolean>() {
			public Boolean execute(DAOContext daoContext) throws ProcessingException {
				JiaBanAccountUpdateDao dao = new JiaBanAccountUpdateDao(daoContext);
				dao.update(theList);
				return true;
			}
		});
	}

}
