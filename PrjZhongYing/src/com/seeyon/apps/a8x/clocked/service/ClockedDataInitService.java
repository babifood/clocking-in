package com.seeyon.apps.a8x.clocked.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.seeyon.apps.a8x.clocked.dao.ClockedResultCreateDao;
import com.seeyon.apps.a8x.clocked.dao.ClockedResultLoadDao;
import com.seeyon.apps.a8x.clocked.dao.PersonDao;
import com.seeyon.apps.a8x.clocked.entity.ClockedResult;
import com.seeyon.apps.a8x.clocked.entity.Person;
import com.seeyon.apps.a8x.clocked.rule.ClockedYesNoRule;
import com.seeyon.apps.a8x.clocked.util.ClockedUtil;
import com.seeyon.apps.a8x.common.exception.ProcessingException;
import com.seeyon.apps.a8x.common.jdbc.DAOContext;
import com.seeyon.apps.a8x.common.jdbc.JdbcWorkUnit;
import com.seeyon.apps.a8x.common.jdbc.JdbcWorker;
import com.seeyon.apps.a8x.common.util.UtilDateTime;
import com.seeyon.apps.a8x.oa.A8xFormDataUpdater;
import com.seeyon.apps.a8x.oa.dao.A8xContentAllDao;
import com.seeyon.apps.a8x.oa.entity.A8xContentAll;
import com.seeyon.apps.a8x.oa.entity.A8xMasterDataRow;
import com.seeyon.apps.a8x.oa.msg.KMessageAgent;
import com.seeyon.apps.a8x.oa.msg.KMessageBean;
import com.seeyon.apps.a8x.oa.wrapper.A8xFormDataWrapper;
import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.form.bean.FormBean;
import com.seeyon.ctp.form.service.FormManager;

/**
 * 考勤数据初始化
 * 1:需要参与考勤的人员名单
 * 2：标准作息时间
 * 3：考勤方式（普通考勤/移动考勤）
 * 4：写入考勤结果表
 * 
 */
public class ClockedDataInitService implements ClockedService {

	private A8xFormDataWrapper sourceWrapper;

	private int year = 0;
	private int month = 0;

	private FormBean formBean;

	private List<ClockedResult> dataList;

	private List<ClockedResult> hisDataList;

	private List<Person> personList;

	private String message;

	public ClockedDataInitService(A8xFormDataWrapper sourceWrapper) {
		this.sourceWrapper = sourceWrapper;
		Calendar tempCal = Calendar.getInstance();
		tempCal.setTime(new java.util.Date(System.currentTimeMillis()));
		year = sourceWrapper.getMasterDataMap2().get("年度") == null ? tempCal.get(Calendar.YEAR) : Integer.parseInt(sourceWrapper.getMasterDataMap2().get("年度").toString());
		month = sourceWrapper.getMasterDataMap2().get("月份") == null ? (tempCal.get(Calendar.MONTH) + 1) : Integer.parseInt(sourceWrapper.getMasterDataMap2().get("月份").toString());
	}

	public void execute() {
		//System.out.println("数据初始化>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		boolean flag = true;
		try {
			FormManager formManager = (FormManager) AppContext.getBean("formManager");
			formBean = formManager.getFormByFormCode("KQRESULT");
			if (formBean == null) {
				flag = false;
				message = "模板编号:[KQRESULT]表单,不存在...";
				return;
			}
			// 获取需要考勤的人员名单
			personList = loadNeedClockedPersons();

			int daySize = UtilDateTime.getDaySize(year, month);
			int persronSize = personList == null ? 0 : personList.size();
			dataList = new ArrayList<ClockedResult>(daySize * persronSize);
			ClockedYesNoRule clockedYesNoRule = null;
			if (persronSize > 0) {
				clockedYesNoRule = new ClockedYesNoRule(year, month);
			}

			Person tmpPerson = null;
			for (int day = 1; day <= daySize; day++) {
				for (int k = 0; k < persronSize; k++) {
					tmpPerson = personList.get(k);
					dataList.add(ClockedUtil.markClockedYesNo(tmpPerson, getDate(day), clockedYesNoRule));
				}
			}

			// 获取历史数据
			hisDataList = loadAllHistoryDatas(year, month);

			// 保存数据
			save(dataList, hisDataList);

			// 发送消息
			KMessageBean msg = new KMessageBean();
			msg.setSenddate(new java.util.Date(System.currentTimeMillis()));
			msg.setContent("考勤数据初始化完毕...");
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
			dataRowMap.add("状态", "初始化成功");
			dataRowMap.add("执行情况", "考勤数据初始化成功....");
		} else {
			dataRowMap.add("状态", "初始化失败");
			dataRowMap.add("执行情况", "考勤数据初始化失败....\n" + message);
		}
		A8xFormDataUpdater updater = new A8xFormDataUpdater(sourceWrapper.getFormBean());
		updater.updateMasterTableByKey(sourceWrapper.getFormDataMasterBean().getId(), dataRowMap);
	}

	public void destory() {
		if (dataList != null) {
			dataList.clear();
			dataList = null;
		}

		if (hisDataList != null) {
			hisDataList.clear();
			hisDataList = null;
		}

		if (personList != null) {
			personList.clear();
			personList = null;
		}

		if (formBean != null) {
			formBean = null;
		}
	}

	public boolean save(List<ClockedResult> saveDataList, List<ClockedResult> hisDataList) throws Exception {
		int size = saveDataList == null ? 0 : saveDataList.size();
		if (size == 0) {
			return false;
		}

		List<ClockedResult> createList = new ArrayList<ClockedResult>(size);
		List<ClockedResult> updateList = new ArrayList<ClockedResult>(size);

		ClockedResult tmpNewValue = null;
		ClockedResult tmpHisValue = null;
		for (int i = 0; i < size; i++) {
			tmpHisValue = null;
			tmpNewValue = saveDataList.get(i);
			tmpHisValue = getValueOfHistoryData(tmpNewValue, hisDataList);
			if (tmpHisValue != null) {
				// 数据修改时候，把历史数据和新数据进行合并
				tmpNewValue.setId(tmpHisValue.getId());

				tmpNewValue.setState(1);
				tmpNewValue.setStart_member_id(tmpHisValue.getStart_member_id());
				tmpNewValue.setStart_date(tmpHisValue.getStart_date());

				tmpNewValue.setApprove_member_id(tmpHisValue.getApprove_member_id());
				tmpNewValue.setApprove_date(tmpHisValue.getApprove_date());

				tmpNewValue.setFinishedflag(1);
				tmpNewValue.setRatifyflag(0);
				tmpNewValue.setRatify_member_id(-1);
				// setSort(0);

				Timestamp currentTime = new Timestamp(System.currentTimeMillis());
				tmpNewValue.setModify_member_id(AppContext.currentUserId());
				tmpNewValue.setModify_date(currentTime);

				updateList.add(tmpNewValue);
			} else {
				// 对需要新创建的数据进行补充表内置的关键数据项
				tmpNewValue.initCreateFieldValue();
				createList.add(tmpNewValue);
			}
		}
		saveOrUpdateFormData(createList, updateList);
		return true;
	}

	private ClockedResult getValueOfHistoryData(ClockedResult theSaveDate, List<ClockedResult> hisDataList) {
		if (hisDataList == null) {
			return null;
		}
		ClockedResult tmpMap = null;
		int size = hisDataList == null ? 0 : hisDataList.size();
		boolean flag = false;
		for (int i = 0; i < size; i++) {
			flag = false;
			tmpMap = hisDataList.get(i);
			if (tmpMap.getMemberID().equals(theSaveDate.getMemberID())) {
				if (tmpMap.getClockDate().equals(theSaveDate.getClockDate())) {
					flag = true;
					break;
				}
			}
		}
		if (flag) {
			return tmpMap;
		} else {
			return null;
		}
	}

	private void saveOrUpdateFormData(final List<ClockedResult> newList, final List<ClockedResult> updateList) throws Exception {
		JdbcWorker.wrapInTransaction(new JdbcWorkUnit<Integer>() {
			public Integer execute(DAOContext daoContext) throws ProcessingException {
				ClockedResultCreateDao dao = new ClockedResultCreateDao(daoContext);
				if (newList != null && !newList.isEmpty()) {
					A8xContentAllDao contentDao = new A8xContentAllDao(daoContext);
					int size = newList.size();
					List<A8xContentAll> contentList = new ArrayList<A8xContentAll>(size);
					ClockedResult tmpResult = null;
					A8xContentAll content = null;
					for (int i = 0; i < size; i++) {
						tmpResult = newList.get(i);
						content = new A8xContentAll();
						content.copy4New(formBean, AppContext.currentUserId(), tmpResult.getId());
						contentList.add(content);
					}
					dao.create(newList);
					contentDao.create(contentList);
				}
				if (updateList != null && !updateList.isEmpty()) {
					dao.update(updateList);
				}
				return 1;
			}
		});
	}

	private java.sql.Date getDate(int day) {
		String temp = null;
		temp = String.valueOf(year);

		if (month < 10) {
			temp = temp + "-0" + month;
		} else {
			temp = temp + "-" + month;
		}

		if (day < 10) {
			temp = temp + "-0" + day;
		} else {
			temp = temp + "-" + day;
		}
		return java.sql.Date.valueOf(temp);
	}

	private List<Person> loadNeedClockedPersons() throws ProcessingException {
		return JdbcWorker.wrapNotInTransaction(new JdbcWorkUnit<List<Person>>() {
			public List<Person> execute(DAOContext daoContext) throws ProcessingException {
				PersonDao dao = new PersonDao(daoContext);
				return dao.load();
			}
		});
	}

	private List<ClockedResult> loadAllHistoryDatas(final int year, final int month) throws ProcessingException {
		return JdbcWorker.wrapNotInTransaction(new JdbcWorkUnit<List<ClockedResult>>() {
			public List<ClockedResult> execute(DAOContext daoContext) throws ProcessingException {
				ClockedResultLoadDao dao = new ClockedResultLoadDao(daoContext);
				return dao.loadAll(year, month);
			}
		});
	}
}
