package com.seeyon.apps.a8x.clocked.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.seeyon.apps.a8x.clocked.dao.ClockedBizDataDao;
import com.seeyon.apps.a8x.clocked.dao.ClockedRecordDao;
import com.seeyon.apps.a8x.clocked.dao.ClockedResultLoadDao;
import com.seeyon.apps.a8x.clocked.dao.ClockedResultUpdateDao;
import com.seeyon.apps.a8x.clocked.dao.MobileDaKaRecordDao;
import com.seeyon.apps.a8x.clocked.entity.ClockedBizData;
import com.seeyon.apps.a8x.clocked.entity.ClockedResult;
import com.seeyon.apps.a8x.clocked.entity.MobileDaKaRecord2;
import com.seeyon.apps.a8x.clocked.entity.OfficeDaKaRecord;
import com.seeyon.apps.a8x.clocked.rule.MobileCalcRule;
import com.seeyon.apps.a8x.clocked.rule.MobileDaKaRule;
import com.seeyon.apps.a8x.clocked.rule.OfficeCalcRule;
import com.seeyon.apps.a8x.clocked.rule.OfficeDaKaRule;
import com.seeyon.apps.a8x.common.exception.ProcessingException;
import com.seeyon.apps.a8x.common.jdbc.DAOContext;
import com.seeyon.apps.a8x.common.jdbc.JdbcWorkUnit;
import com.seeyon.apps.a8x.common.jdbc.JdbcWorker;
import com.seeyon.apps.a8x.oa.A8xFormDataUpdater;
import com.seeyon.apps.a8x.oa.entity.A8xMasterDataRow;
import com.seeyon.apps.a8x.oa.msg.KMessageAgent;
import com.seeyon.apps.a8x.oa.msg.KMessageBean;
import com.seeyon.apps.a8x.oa.wrapper.A8xFormDataWrapper;
import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.common.exceptions.BusinessException;

/**
 * 考勤数据归集服务
 * 
 * 1：适匹打卡数据
 * 2：适匹业务数据（异动/出差/请假/加班)
 * 3: 计算工作时长
 * 4：计算迟到/早退/矿工
 * 5：计算餐补个数
 * 6：回写到考勤结果表
 */
public class ClockeGatherService implements ClockedService {

	private static final Log log = LogFactory.getLog(ClockeGatherService.class);

	private A8xFormDataWrapper sourceWrapper;

	private int year = 0;

	private int month = 0;

	private List<OfficeDaKaRecord> officeDaKaList;

	private Map<String, MobileDaKaRecord2> mobileDaKaMap;

	private List<ClockedResult> clockedResultList;

	private Map<String, List<ClockedBizData>> clockedBizDataMap;

	private String message;

	public ClockeGatherService(A8xFormDataWrapper sourceWrapper) {
		this.sourceWrapper = sourceWrapper;
		Calendar tempCal = Calendar.getInstance();
		tempCal.setTime(new java.util.Date(System.currentTimeMillis()));
		year = sourceWrapper.getMasterDataMap2().get("年度") == null ? tempCal.get(Calendar.YEAR) : Integer.parseInt(sourceWrapper.getMasterDataMap2().get("年度").toString());
		month = sourceWrapper.getMasterDataMap2().get("月份") == null ? (tempCal.get(Calendar.MONTH) + 1) : Integer.parseInt(sourceWrapper.getMasterDataMap2().get("月份").toString());
	}

	public void execute() {
		boolean flag = true;
		log.info("====================开始数据归集================");
		try {
			log.info("1 载入数据");
			load();

			log.info("2 根据打卡记录，计算相关数据");
			attachWithDaKa();

			log.info("3 根据业务数据，计算相关数据");			
			attachWithBizData();

			log.info("4 保存数据");
			update(clockedResultList);

			sendMsg("考勤数据归集完毕...");

		} catch (Exception e) {
			flag = false;
			sendMsg("考勤数据归集失败...\n" + e.getLocalizedMessage());
			message = e.getLocalizedMessage();
		} finally {
			log.info("====================结束数据归集================");
			updateExecStatus(flag);
		}
	}

	public void destory() {
		if (officeDaKaList != null) {
			officeDaKaList.clear();
			officeDaKaList = null;
		}

		if (clockedResultList != null) {
			clockedResultList.clear();
			clockedResultList = null;
		}

		if (clockedBizDataMap != null) {
			clockedBizDataMap.clear();
			clockedBizDataMap = null;
		}
	}

	private void sendMsg(String theMsg) {
		// 发送消息
		KMessageBean msg = new KMessageBean();
		msg.setSenddate(new java.util.Date(System.currentTimeMillis()));
		msg.setContent(theMsg);
		KMessageAgent agent = new KMessageAgent();
		agent.sendMessage(msg, AppContext.currentUserId());
	}

	// 读取相关数据
	private void load() throws ProcessingException {
		// 办公室打卡记录
		officeDaKaList = loadOfficeDaKaList(year, month);
		// 移动打卡记录
		mobileDaKaMap = loadMobileDaKaList(year, month);

		clockedResultList = loadClockedResults(year, month);
		List<ClockedBizData> clockedBizDataList = loadClockedBizDatas(year, month);
		clockedBizDataMap = new HashMap<String, List<ClockedBizData>>(300);

		if (clockedBizDataList != null && clockedBizDataList.isEmpty() == false) {
			int xsize = clockedBizDataList.size();
			ClockedBizData tmpClockedBizData = null;
			List<ClockedBizData> tmpList = null;
			for (int i = 0; i < xsize; i++) {
				tmpClockedBizData = clockedBizDataList.get(i);
				tmpList = clockedBizDataMap.get(tmpClockedBizData.getMemberID());
				if (tmpList == null) {
					tmpList = new ArrayList<ClockedBizData>(20);
					clockedBizDataMap.put(tmpClockedBizData.getMemberID(), tmpList);
				}
				tmpList.add(tmpClockedBizData);
			}
		}
		if (clockedBizDataList != null) {
			clockedBizDataList.clear();
			clockedBizDataList = null;
		}
	}

	private void attachWithDaKa() {
		ClockedResult tmpClockedResult = null;
		int size = clockedResultList.size();
		String daKaType = null;
		for (int i = 0; i < size; i++) {
			tmpClockedResult = clockedResultList.get(i);
			daKaType = tmpClockedResult.getDaKaType() == null ? "" : tmpClockedResult.getDaKaType().trim();
			if (daKaType.equals("移动考勤")) {
				MobileDaKaRule.attachWithDaKa(tmpClockedResult, mobileDaKaMap);
			} else {
				OfficeDaKaRule.attachWithDaKa(tmpClockedResult, officeDaKaList);
			}
		}
	}

	private void attachWithBizData() throws BusinessException {
		ClockedResult tmpClockedResult = null;
		int size = clockedResultList.size();
		OfficeCalcRule officeCalcRule = null;
		MobileCalcRule mobileCalcRule = null;
		String daKaType = null;
		for (int i = 0; i < size; i++) {
			tmpClockedResult = clockedResultList.get(i);
			daKaType = tmpClockedResult.getDaKaType() == null ? "" : tmpClockedResult.getDaKaType().trim();
			
			if (daKaType.equals("移动考勤")) {
				mobileCalcRule =  new MobileCalcRule();
				tmpClockedResult = clockedResultList.get(i);
				// 按规则进行计算
				mobileCalcRule.attach(tmpClockedResult, clockedBizDataMap.get(tmpClockedResult.getMemberID()));
			} else {
				officeCalcRule = new OfficeCalcRule();
				tmpClockedResult = clockedResultList.get(i);
				// 按规则进行计算
				officeCalcRule.attach(tmpClockedResult, clockedBizDataMap.get(tmpClockedResult.getMemberID()));

			}

		}
	}

	/**
	 * 修改任务执行情况
	 * 
	 * @param flag
	 */
	public void updateExecStatus(boolean flag) {
		A8xMasterDataRow dataRowMap = new A8xMasterDataRow();
		if (flag) {
			dataRowMap.add("状态", "数据归集成功");
			dataRowMap.add("执行情况", "考勤数据归集成功....");
		} else {
			dataRowMap.add("状态", "数据归集失败");
			dataRowMap.add("执行情况", "考勤数据归集失败....\n" + message);
		}
		A8xFormDataUpdater updater = new A8xFormDataUpdater(sourceWrapper.getFormBean());
		updater.updateMasterTableByKey(sourceWrapper.getFormDataMasterBean().getId(), dataRowMap);
	}

	/**
	 * 获取业务流程单据数据
	 * 
	 * @param theYear
	 * @param theMonth
	 * @return
	 * @throws ProcessingException
	 */
	private List<ClockedBizData> loadClockedBizDatas(final int theYear, final int theMonth) throws ProcessingException {
		return JdbcWorker.wrapNotInTransaction(new JdbcWorkUnit<List<ClockedBizData>>() {
			public List<ClockedBizData> execute(DAOContext daoContext) throws ProcessingException {
				ClockedBizDataDao dao = new ClockedBizDataDao(daoContext);
				return dao.load(year, month);
			}
		});
	}

	/**
	 * 获取办公室-打卡记录数据
	 * 
	 * @param theYear
	 * @param theMonth
	 * @return
	 * @throws ProcessingException
	 */
	private List<OfficeDaKaRecord> loadOfficeDaKaList(final int theYear, final int theMonth) throws ProcessingException {
		return JdbcWorker.wrapNotInTransaction(new JdbcWorkUnit<List<OfficeDaKaRecord>>() {
			public List<OfficeDaKaRecord> execute(DAOContext daoContext) throws ProcessingException {
				ClockedRecordDao dao = new ClockedRecordDao(daoContext);
				return dao.loadForClockeGather(year, month);
			}
		});
	}

	/**
	 * 获取移动-打卡记录数据
	 * 
	 * @param theYear
	 * @param theMonth
	 * @return
	 * @throws ProcessingException
	 */
	private Map<String, MobileDaKaRecord2> loadMobileDaKaList(final int theYear, final int theMonth) throws ProcessingException {
		return JdbcWorker.wrapNotInTransaction(new JdbcWorkUnit<Map<String, MobileDaKaRecord2>>() {
			public Map<String, MobileDaKaRecord2> execute(DAOContext daoContext) throws ProcessingException {
				MobileDaKaRecordDao dao = new MobileDaKaRecordDao(daoContext);
				return dao.loadForClockeGather(year, month);
			}
		});
	}

	/**
	 * 获取考勤结果数据
	 * 
	 * @param theYear
	 * @param theMonth
	 * @return
	 * @throws ProcessingException
	 */
	private List<ClockedResult> loadClockedResults(final int theYear, final int theMonth) throws ProcessingException {
		return JdbcWorker.wrapNotInTransaction(new JdbcWorkUnit<List<ClockedResult>>() {
			public List<ClockedResult> execute(DAOContext daoContext) throws ProcessingException {
				ClockedResultLoadDao dao = new ClockedResultLoadDao(daoContext);
				return dao.loadOnToday(year, month);
			}
		});
	}

	private static void update(final List<ClockedResult> theClockedResultList) throws ProcessingException {
		JdbcWorker.wrapInTransaction(new JdbcWorkUnit<Integer>() {
			public Integer execute(DAOContext daoContext) throws ProcessingException {
				ClockedResultUpdateDao dao = new ClockedResultUpdateDao(daoContext);
				dao.update(theClockedResultList);
				return 1;
			}
		});
	}

}
