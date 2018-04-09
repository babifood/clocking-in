package com.seeyon.apps.a8x.schedule;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.seeyon.apps.a8x.common.exception.ProcessingException;
import com.seeyon.apps.a8x.common.jdbc.DAOContext;
import com.seeyon.apps.a8x.common.jdbc.JdbcWorkUnit;
import com.seeyon.apps.a8x.common.jdbc.JdbcWorker;
import com.seeyon.apps.a8x.schedule.dao.A8xScheduleDao;
import com.seeyon.apps.a8x.schedule.model.A8xSchedule;

public class A8xQuartzManager {
	private static Log log = LogFactory.getLog(A8xQuartzManager.class);
	private static final A8xQuartzManager INSTANCE = new A8xQuartzManager();
	public static final String JOB_GROUP_NAME = "A8X_JOBGROUP";
	public static final String TRIGGER_GROUP_NAME = "A8X_TRIGGER";
	private static Scheduler sd;
	private static List<String> list = new ArrayList<String>();

	public static A8xQuartzManager getInstance() {
		INSTANCE.getScheduleInst();
		return INSTANCE;
	}

	public Scheduler getScheduleInst() {
		return sd;
	}

	public List<String> getJobList() {
		return list;
	}

	public synchronized void flowSchedule() {
		List<A8xSchedule> tmpJobList = null;
		try {
			tmpJobList = JdbcWorker.wrapNotInTransaction(new JdbcWorkUnit<List<A8xSchedule>>() {
				public List<A8xSchedule> execute(DAOContext daoContext) throws ProcessingException {
					A8xScheduleDao dao = new A8xScheduleDao(daoContext);
					return dao.loadALL();
				}
			});
		} catch (ProcessingException e) {
			log.equals(e);
			return;
		}

		if (tmpJobList == null || tmpJobList.isEmpty()) {
			return;
		}

		try {
			for (A8xSchedule tmpA8xSchedule : tmpJobList) {
				if (tmpA8xSchedule.isEnable()) {
					Class<?> clazz = Class.forName(tmpA8xSchedule.getFlow_id()).newInstance().getClass();
					String jobName = "job" + UUID.randomUUID();
					list.add(jobName);
					addJob(jobName, clazz, tmpA8xSchedule.getQuartz_rule(), tmpA8xSchedule.getFlow_name());
				}
			}
		} catch (Exception e) {
			log.equals(e);
		}
	}

	public void addJob(String jobName, Class<?> job, String time, String flowName) throws SchedulerException, ParseException {
		JobDetail jobDetail = new JobDetail(jobName, "A8X_JOBGROUP", job);
		jobDetail.getJobDataMap().put("flowName", flowName);
		CronTrigger trigger = new CronTrigger(jobName, "A8X_TRIGGER");
		trigger.setCronExpression(time);
		sd.scheduleJob(jobDetail, trigger);
		if (!sd.isShutdown()) {
			sd.start();
		}
	}

	public void refresh() throws SchedulerException {
		sd.pauseAll();
		synchronized (list) {
			for (String s : list) {
				sd.deleteJob(s, "A8X_JOBGROUP");
			}
			list.clear();
			getInstance().flowSchedule();
			sd.resumeAll();
		}
	}

	static {
		SchedulerFactory sf = null;
		InputStream fis = A8xQuartzManager.class.getResourceAsStream("/com/seeyon/apps/a8x/schedule/a8x-quartz.properties");
		try {
			Properties properties = new Properties();
			properties.load(fis);
			sf = new StdSchedulerFactory(properties);
			log.info("A8X_定时器初始化完成！");
		} catch (IOException e) {
			log.error("定时器初始化IO异常：" + e.getLocalizedMessage(), e);
		} catch (SchedulerException e) {
			log.error("定时器初始化异常：" + e.getLocalizedMessage(), e);
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException e) {

			}
		}
		if (sf == null)
			sf = new StdSchedulerFactory();
		try {
			sd = sf.getScheduler();
		} catch (SchedulerException e) {
			log.error(e);
		}
	}

	public static void main(String args[]) {
		A8xQuartzManager.getInstance().flowSchedule();
		try {
			Thread.sleep(1000000000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}