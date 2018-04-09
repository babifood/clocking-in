package com.seeyon.apps.a8x.clocked.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.seeyon.apps.a8x.clocked.dao.ClockedRecordDao;
import com.seeyon.apps.a8x.clocked.dao.ClockedRecordUpdateDao;
import com.seeyon.apps.a8x.clocked.dao.PersonDao;
import com.seeyon.apps.a8x.clocked.entity.OfficeDaKaRecord;
import com.seeyon.apps.a8x.clocked.entity.Person;
import com.seeyon.apps.a8x.common.exception.ProcessingException;
import com.seeyon.apps.a8x.common.jdbc.DAOContext;
import com.seeyon.apps.a8x.common.jdbc.JdbcWorkUnit;
import com.seeyon.apps.a8x.common.jdbc.JdbcWorker;
import com.seeyon.apps.a8x.common.util.UtilString;
import com.seeyon.apps.a8x.oa.A8xFormDataUpdater;
import com.seeyon.apps.a8x.oa.entity.A8xMasterDataRow;
import com.seeyon.apps.a8x.oa.msg.KMessageAgent;
import com.seeyon.apps.a8x.oa.msg.KMessageBean;
import com.seeyon.apps.a8x.oa.wrapper.A8xFormDataWrapper;
import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.common.exceptions.BusinessException;

/**
 * 补充完善-打卡记录
 * 
 */
public class DaKaDataAdjustService implements ClockedService {

	private A8xFormDataWrapper sourceWrapper;

	private int year = 2016;
	private int month = 5;

	// private FormBean formBean;

	private List<OfficeDaKaRecord> dataList;

	private List<Person> personList;

	private String message;

	public DaKaDataAdjustService(A8xFormDataWrapper sourceWrapper) {
		this.sourceWrapper = sourceWrapper;
		Calendar tempCal = Calendar.getInstance();
		tempCal.setTime(new java.util.Date(System.currentTimeMillis()));
		year = sourceWrapper.getMasterDataMap2().get("年度") == null ? tempCal.get(Calendar.YEAR) : Integer.parseInt(sourceWrapper.getMasterDataMap2().get("年度").toString());
		month = sourceWrapper.getMasterDataMap2().get("月份") == null ? (tempCal.get(Calendar.MONTH) + 1) : Integer.parseInt(sourceWrapper.getMasterDataMap2().get("月份").toString());
	}

	public void execute() {
		boolean flag = true;
		try {
			personList = loadNeedClockedPersons();

			dataList = loadClockedRecordDatas(year, month);

			int persronSize = personList == null ? 0 : personList.size();
			if (persronSize < 0) {
				return;
			}
			Map<String, Person> personMap = new HashMap<String, Person>(persronSize + 1);
			Person tmpPerson = null;
			for (int i = 0; i < persronSize; i++) {
				tmpPerson = personList.get(i);
				personMap.put(tmpPerson.getCardName(), tmpPerson);
			}

			int size = dataList == null ? 0 : dataList.size();
			OfficeDaKaRecord tmpClockedRecord = null;
			for (int i = 0; i < size; i++) {
				tmpClockedRecord = dataList.get(i);
				tmpPerson = personMap.get(tmpClockedRecord.getCardName());
				if (tmpClockedRecord.getStartTime() != null) {
					tmpClockedRecord.setStartTime(UtilString.replace(tmpClockedRecord.getStartTime().trim(), "1899-12-31", "").trim());
				}

				if (tmpClockedRecord.getEndTime() != null) {
					tmpClockedRecord.setEndTime(UtilString.replace(tmpClockedRecord.getEndTime().trim(), "1899-12-31", "").trim());
				}

				if (tmpPerson != null) {
					tmpClockedRecord.setMemberID(tmpPerson.getMemberID());
					tmpClockedRecord.setWorkNum(tmpPerson.getWorkNum());
					tmpClockedRecord.setDept(tmpPerson.getDept());
					tmpClockedRecord.setOrgan(tmpPerson.getOrgan());
					tmpClockedRecord.setYear(year);
					tmpClockedRecord.setMonth(month);
				}

			}

			// 保存数据
			save(dataList);

			// 发送消息
			KMessageBean msg = new KMessageBean();
			msg.setSenddate(new java.util.Date(System.currentTimeMillis()));
			msg.setContent("打卡记录补充完毕...");
			KMessageAgent agent = new KMessageAgent();
			agent.sendMessage(msg, AppContext.currentUserId());
		} catch (Exception e) {
			flag = false;
			message = e.getLocalizedMessage();
		} finally {
			updateExecStatus(flag);
		}

	}

	public void updateExecStatus(boolean flag) {
		A8xMasterDataRow dataRowMap = new A8xMasterDataRow();
		if (flag) {
			dataRowMap.add("状态", "打卡记录补充成功");
			dataRowMap.add("执行情况", "打卡记录补充成功....");
		} else {
			dataRowMap.add("状态", "打卡记录补充失败");
			dataRowMap.add("执行情况", "打卡记录补充失败....\n" + message);
		}
		A8xFormDataUpdater updater = new A8xFormDataUpdater(sourceWrapper.getFormBean());
		updater.updateMasterTableByKey(sourceWrapper.getFormDataMasterBean().getId(), dataRowMap);

	}

	public void destory() {
		if (dataList != null) {
			dataList.clear();
			dataList = null;
		}

		if (personList != null) {
			personList.clear();
			personList = null;
		}

	}

	public boolean save(final List<OfficeDaKaRecord> saveDataList) throws BusinessException {
		try {
			JdbcWorker.wrapInTransaction(new JdbcWorkUnit<Integer>() {
				public Integer execute(DAOContext daoContext) throws ProcessingException {
					ClockedRecordUpdateDao dao = new ClockedRecordUpdateDao(daoContext);
					dao.update(saveDataList);
					return 1;
				}
			});

			return true;
		} catch (ProcessingException e) {
			throw new BusinessException(e.getLocalizedMessage());
		}
	}

	private List<Person> loadNeedClockedPersons() throws ProcessingException {
		return JdbcWorker.wrapNotInTransaction(new JdbcWorkUnit<List<Person>>() {
			public List<Person> execute(DAOContext daoContext) throws ProcessingException {
				PersonDao dao = new PersonDao(daoContext);
				return dao.load();
			}
		});
	}

	private List<OfficeDaKaRecord> loadClockedRecordDatas(final int year, final int month) throws ProcessingException {
		return JdbcWorker.wrapNotInTransaction(new JdbcWorkUnit<List<OfficeDaKaRecord>>() {
			public List<OfficeDaKaRecord> execute(DAOContext daoContext) throws ProcessingException {
				ClockedRecordDao dao = new ClockedRecordDao(daoContext);
				return dao.loadForDaKaAdjust(year, month);
			}
		});
	}

}
