package com.seeyon.apps.a8x.schedule.manager;

import java.sql.Timestamp;
import java.util.List;

import com.seeyon.apps.a8x.common.exception.ProcessingException;
import com.seeyon.apps.a8x.common.jdbc.DAOContext;
import com.seeyon.apps.a8x.common.jdbc.JdbcWorkUnit;
import com.seeyon.apps.a8x.common.jdbc.JdbcWorker;
import com.seeyon.apps.a8x.schedule.dao.A8xScheduleDao;
import com.seeyon.apps.a8x.schedule.model.A8xSchedule;
import com.seeyon.ctp.util.UUIDLong;

public class A8xConfigService {

	private static A8xConfigService fls = new A8xConfigService();

	public static A8xConfigService getInstance() {
		if (fls == null) {
			fls = new A8xConfigService();
		}
		return fls;
	}

	public List<A8xSchedule> getAllScheduleList() {
		List<A8xSchedule> tmpList = null;
		try {
			tmpList = JdbcWorker.wrapNotInTransaction(new JdbcWorkUnit<List<A8xSchedule>>() {
				public List<A8xSchedule> execute(DAOContext daoContext) throws ProcessingException {
					A8xScheduleDao dao = new A8xScheduleDao(daoContext);
					return dao.loadALL();
				}
			});
		} catch (ProcessingException e) {

		}
		if (tmpList == null) {
			tmpList = new java.util.ArrayList<A8xSchedule>();
		}
		return tmpList;
	}
	
	public boolean createSchedule(final A8xSchedule sdBean) {
		// 生成定时器规则
		sdBean.setSchedule_id(String.valueOf(UUIDLong.absLongUUID()));
		sdBean.generateScheduleRule();
		sdBean.setCreate_time(new Timestamp(System.currentTimeMillis()));
		int x = 0;
		try {
			x = JdbcWorker.wrapNotInTransaction(new JdbcWorkUnit<Integer>() {
				public Integer execute(DAOContext daoContext) throws ProcessingException {
					A8xScheduleDao dao = null;
					dao = new A8xScheduleDao(daoContext);
					int flag = dao.create(sdBean);
					return flag;
				}
			});
		} catch (ProcessingException e) {
			e.printStackTrace();
		}
		return x == 1;
	}

	public boolean updateSchedule(final A8xSchedule sdBean) {
		// 生成定时器规则
		sdBean.generateScheduleRule();
		int x = 0;
		try {
			x = JdbcWorker.wrapNotInTransaction(new JdbcWorkUnit<Integer>() {
				public Integer execute(DAOContext daoContext) throws ProcessingException {
					A8xScheduleDao dao = null;
					dao = new A8xScheduleDao(daoContext);
					int flag = dao.update(sdBean);
					return flag;
				}
			});
		} catch (ProcessingException e) {
			e.printStackTrace();
		}
		return x == 1;
	}

	public A8xSchedule getScheduleByID(final String theKey) {
		try {
			return JdbcWorker.wrapNotInTransaction(new JdbcWorkUnit<A8xSchedule>() {
				public A8xSchedule execute(DAOContext daoContext) throws ProcessingException {
					A8xScheduleDao dao = new A8xScheduleDao(daoContext);
					return dao.read(theKey);
				}
			});
		} catch (ProcessingException e) {
			return null;
		}
	}

}
