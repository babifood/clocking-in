package com.seeyon.apps.a8x.schedule.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.seeyon.apps.a8x.common.exception.ProcessingException;
import com.seeyon.apps.a8x.common.jdbc.DAOContext;
import com.seeyon.apps.a8x.common.jdbc.JdbcWorkUnit;
import com.seeyon.apps.a8x.common.jdbc.JdbcWorker;
import com.seeyon.apps.a8x.schedule.dao.A8xScheduleDao;
import com.seeyon.apps.a8x.schedule.model.A8xSchedule;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.util.FlipInfo;

public class A8xScheduleManagerImpl implements A8xScheduleManager {
	private static final Log log = LogFactory.getLog(A8xScheduleManagerImpl.class);
	
	private static final A8xConfigService configService = A8xConfigService.getInstance();


	public void update(A8xSchedule sdBean) throws BusinessException {
		configService.updateSchedule(sdBean);
	}

	

	public FlipInfo findScheduleList(FlipInfo fi, Map<String, Object> param) {
		String schedule_name = "";
		String flow_name = "";
		for (Map.Entry<String,Object> entry : param.entrySet()) {
			if (((String) entry.getKey()).equals("schedule_name")) {
				Object tmp = entry.getValue();
				schedule_name = tmp == null ? "" : tmp.toString();
			}
			if (((String) entry.getKey()).equals("flow_name")) {
				Object tmp = entry.getValue();
				flow_name = tmp == null ? "" : tmp.toString();
			}
		}
		List<A8xSchedule> datas = new ArrayList<A8xSchedule>();
		List<A8xSchedule> result = configService.getAllScheduleList();
		List<A8xSchedule> resultList = new ArrayList<A8xSchedule>();
		for (A8xSchedule A8xScheduleBean : result) {
			if ((A8xScheduleBean.getSchedule_name() == null ? "" : A8xScheduleBean.getSchedule_name()).contains(schedule_name)) {
				if ((A8xScheduleBean.getFlow_name() == null ? "" : A8xScheduleBean.getFlow_name()).contains(flow_name))
					resultList.add(A8xScheduleBean);
			}
		}
		for (int i = 0; i < resultList.size(); i++) {
			if ((i >= (fi.getPage() - 1) * fi.getSize()) && (i < fi.getPage() * fi.getSize())) {
				datas.add(resultList.get(i));
			}
		}
		fi.setData(datas);
		fi.setTotal(resultList.size());
		return fi;
	}
	
	public List<A8xSchedule> loadALL() {
		try {
			return JdbcWorker.wrapNotInTransaction(new JdbcWorkUnit<List<A8xSchedule>>() {
				public List<A8xSchedule> execute(DAOContext daoContext) throws ProcessingException {
					A8xScheduleDao dao = new A8xScheduleDao(daoContext);
					return dao.loadALL();
				}
			});
		} catch (ProcessingException e) {
			log.equals(e);
			return null;
		}
	}
}