package com.seeyon.apps.a8x.schedule;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.seeyon.apps.a8x.clocked.dao.JiaBanAccountBlankOutDao;
import com.seeyon.apps.a8x.clocked.dao.JiaBanAccountReadDao;
import com.seeyon.apps.a8x.clocked.dao.NianJiaAccountBlankOutDao;
import com.seeyon.apps.a8x.clocked.dao.NianJiaAccountDao;
import com.seeyon.apps.a8x.clocked.entity.JiaBanAccount;
import com.seeyon.apps.a8x.clocked.entity.NianJiaAccount;
import com.seeyon.apps.a8x.common.exception.ProcessingException;
import com.seeyon.apps.a8x.common.jdbc.DAOContext;
import com.seeyon.apps.a8x.common.jdbc.JdbcWorkUnit;
import com.seeyon.apps.a8x.common.jdbc.JdbcWorker;

/**
 * [加班或年假]过期时间检查--定时任务
 */
public class A8xJob1 implements Job {
	private static final Log log = LogFactory.getLog(A8xJob1.class);

	public void execute(JobExecutionContext context) throws JobExecutionException {
		run();
	}

	private void run() {
		log.info("[加班或年假]过期时间检查-开始执行。");
		try {
			blankOutJiaBan();
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		try {
			blankOutNianJia();
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		log.info("[加班或年假]过期时间检查-执行完毕。");
	}

	private void blankOutNianJia() throws ProcessingException {
		List<NianJiaAccount> tmpList = loadNianJia();
		if (tmpList != null) {
			List<NianJiaAccount> newList = new ArrayList<NianJiaAccount>(tmpList.size() + 1);
			for (NianJiaAccount tmp : tmpList) {
				if (tmp.getPreTimeLength() > tmp.getDiKouTimeLength()) {
					tmp.setWritedownValue(tmp.getPreTimeLength()-tmp.getDiKouTimeLength());
					newList.add(tmp);
				}
			}
			if (!newList.isEmpty()) {
				writeDownNianJia(newList);
			}
		}
	}

	private void blankOutJiaBan() throws ProcessingException {
		List<JiaBanAccount> tmpList = loadJiaBan();
		if (tmpList != null) {
			List<JiaBanAccount> newList = new ArrayList<JiaBanAccount>(tmpList.size() + 1);
			for (JiaBanAccount tmp : tmpList) {
				tmp.setWritedownValue(tmp.getShengYuTimeLength());
				newList.add(tmp);
			}
			if (!newList.isEmpty()) {
				writeDownJiaBan(newList);
			}
		}
	}

	private List<JiaBanAccount> loadJiaBan() throws ProcessingException {
		return JdbcWorker.wrapNotInTransaction(new JdbcWorkUnit<List<JiaBanAccount>>() {
			public List<JiaBanAccount> execute(DAOContext daoContext) throws ProcessingException {
				JiaBanAccountReadDao dao = new JiaBanAccountReadDao(daoContext);
				return dao.loadByBlankOut();
			}
		});
	}

	private void writeDownJiaBan(final List<JiaBanAccount> theList) throws ProcessingException {
		JdbcWorker.wrapInTransaction(new JdbcWorkUnit<Boolean>() {
			public Boolean execute(DAOContext daoContext) throws ProcessingException {
				JiaBanAccountBlankOutDao dao = new JiaBanAccountBlankOutDao(daoContext);
				dao.update(theList);
				return true;
			}
		});
	}

	private List<NianJiaAccount> loadNianJia() throws ProcessingException {
		return JdbcWorker.wrapNotInTransaction(new JdbcWorkUnit<List<NianJiaAccount>>() {
			public List<NianJiaAccount> execute(DAOContext daoContext) throws ProcessingException {
				NianJiaAccountDao dao = new NianJiaAccountDao(daoContext);
				return dao.loadByBlankOut();
			}
		});
	}

	private void writeDownNianJia(final List<NianJiaAccount> theList) throws ProcessingException {
		JdbcWorker.wrapInTransaction(new JdbcWorkUnit<Boolean>() {
			public Boolean execute(DAOContext daoContext) throws ProcessingException {
				NianJiaAccountBlankOutDao dao = new NianJiaAccountBlankOutDao(daoContext);
				dao.update(theList);
				return true;
			}
		});
	}

}
