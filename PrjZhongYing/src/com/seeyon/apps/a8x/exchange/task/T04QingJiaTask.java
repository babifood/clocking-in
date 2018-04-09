package com.seeyon.apps.a8x.exchange.task;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.seeyon.apps.a8x.common.util.UtilDateTime;
import com.seeyon.apps.a8x.oa.manager.A8xDiBiaoSingleService;
import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.common.exceptions.BusinessException;

/*
 * 请假申请单-集成交换任务
 */

public final class T04QingJiaTask extends AbstrateExchangeTask {

	private static Log log = LogFactory.getLog(T04QingJiaTask.class);

	private Map<String, Object> valuesMap;

	private Map<String, Object> dataMap;

	public T04QingJiaTask(ExchangeTaskContext context) {
		super(context);

	}

	/*
	 * 开始
	 */
	public void start() {
		
	}

	public void execute() {
		if (context.isRedo()) {
			String step = context.getStep();
			if (!"BIZEVENT".equals(step)) {
				return;
			}
		}
		writeWaitingExchageLog("YGQJ", "请假申请单", context.getFormTmpleteCode(), null, null, null, "BIZEVENT");
		
		long x = System.currentTimeMillis();
		log.info("[请假申请单]开始执行....");

		// 读取表单数据
		loadFormData();
		step2();
		step3();
		long times_ = (System.currentTimeMillis() - x) / 1000;
		log.info("[请假申请单]执行结束....,共耗时:" + times_);
	}

	/*
	 * 构造接口数据
	 */
	private void step2() {
		// 上一步如果执行不成功,退出
		if (isSuccess() == false) {
			return;
		}
		dataMap = new HashMap<String, Object>();

		

		dataMap.put("源单ID1", valuesMap.get("id"));
		dataMap.put("源单ID2", valuesMap.get("id"));
		dataMap.put("协同ID", context.getSummaryId());
		dataMap.put("业务单据类型", "请假申请单");

		java.util.Date billDate = (java.util.Date) valuesMap.get("申请日期");
		if(billDate==null){
			billDate = (java.util.Date) valuesMap.get("请假开始时间");
		}
		dataMap.put("年度", UtilDateTime.getYear(billDate));
		dataMap.put("月度", UtilDateTime.getMonth(billDate));

		dataMap.put("姓名", valuesMap.get("申请人"));
		dataMap.put("单位机构", valuesMap.get("申请人所属机构"));
		dataMap.put("部门", valuesMap.get("申请人部门"));
		dataMap.put("工号",valuesMap.get("申请人工号"));
		dataMap.put("日期", valuesMap.get("创建日期"));

		dataMap.put("业务单据日期", valuesMap.get("申请日期"));
		dataMap.put("业务单据号", valuesMap.get("流程编号"));

		dataMap.put("业务性质1", valuesMap.get("请假类别"));
		dataMap.put("业务性质1ID", valuesMap.get("请假类别ID"));

		// dataMap.put("业务性质2",valuesMap.get(""));
		// dataMap.put("业务性质2ID",valuesMap.get(""));
		// dataMap.put("业务性质3",valuesMap.get(""));
		// dataMap.put("业务性质3ID",valuesMap.get(""));
		
		dataMap.put("开始时间", valuesMap.get("请假开始时间"));
		dataMap.put("结束时间", valuesMap.get("请假结束时间"));
		dataMap.put("时长", valuesMap.get("请假时长"));

		dataMap.put("是否有效", "1");
	}

	/*
	 * 执行WebService接口
	 */
	private void step3() {
		if (isSuccess() == false) {
			return;
		}
		A8xDiBiaoSingleService diBiaoManager = (A8xDiBiaoSingleService) AppContext.getBean("diBiaoSingleService");
		try {
			diBiaoManager.saveFormData("KQEVENTDATA", dataMap,  new String[] { "源单ID1" });
		} catch (BusinessException e) {
			logError("生成数据失败." + e.getLocalizedMessage());
		}
	}

	public void end() {
		if (context.isRedo()) {
			String step = context.getStep();
			if (!"BIZEVENT".equals(step)) {
				return;
			}
		}
		// 记录执行情况
		Date tmpFormDate = new java.sql.Date(System.currentTimeMillis());
		// 业务表单流水号
		String tmpFormCode = valuesMap.get("流程编号") == null ? "" : valuesMap.get("流程编号").toString();
		// 业务表单流程标题
		String tmpFormInfo = context.getSubject();
		// 记录执行日志
		if (this.isSuccess()) {
			writeExchageLog("Y", "YGQJ", "请假申请单", context.getFormTmpleteCode(), tmpFormDate, tmpFormCode, tmpFormInfo, "BIZEVENT");
		} else {
			writeExchageLog("N", "YGQJ", "请假申请单", context.getFormTmpleteCode(), tmpFormDate, tmpFormCode, tmpFormInfo, "BIZEVENT");
		}
		if (valuesMap != null) {
			valuesMap.clear();
			valuesMap = null;
		}
	}

	/*
	 * 读取OA表单信息
	 */
	private boolean loadFormData() {
		log.info("start:获取[请假申请单]数据.");
		// 待定5秒
		// sleepx(5000);
		boolean flag = loadFormData2();
		if (valuesMap == null) {
			// 再待定3秒
			sleepx(3000);
			flag = loadFormData2();
		}
		if (valuesMap == null) {
			// 再待定2秒
			sleepx(2000);
			flag = loadFormData2();
		}
		if (valuesMap == null) {
			// 再待定10秒
			sleepx(10000);
			flag = loadFormData2();
		}
		if (valuesMap == null) {
			logError("读取表单数据失败.");
		}
		log.info("end:获取[请假申请单]数据");
		return flag;
	}

	/*
	 * 读取OA表单信息
	 */
	private boolean loadFormData2() {
		log.info("start:获取表单数据.");
		boolean flag = formDataReader.loadFormData(context.getFormAppId(), context.getFormRecordId());
		if (flag) {
			valuesMap = formDataReader.getMasterDataMap2();
		}
		log.info("end:获取表单数据");
		return flag;
	}
}
