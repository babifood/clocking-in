package com.seeyon.apps.a8x.exchange.task;

import java.sql.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.seeyon.apps.a8x.clocked.rule.JiaBanWriteDownRule;
import com.seeyon.apps.a8x.clocked.rule.NianJiaWriteDownRule;

/*
 * 请假申请单-集成交换任务
 */

public final class T04QingJiaAccountTask extends AbstrateExchangeTask {

	private static Log log = LogFactory.getLog(T04QingJiaAccountTask.class);

	private Map<String, Object> valuesMap;

	private String billTypeName = "";
	private long memberId;
	private double qingJiaTimeLength;
	private java.util.Date billDate;

	public T04QingJiaAccountTask(ExchangeTaskContext context) {
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
			if (!"QINGJIATASK".equals(step)) {
				return;
			}
		}

		// 读取表单数据
		loadFormData();
		// 判断是否调休或年假
		if (!isFlag()) {
			return;
		}

		writeWaitingExchageLog("YGQJ", "请假申请单", context.getFormTmpleteCode(), null, null, null, "QINGJIATASK");
		long x = System.currentTimeMillis();
		log.info("[请假申请单]开始执行....");

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

		if (valuesMap.get("申请人") == null || "".equals(valuesMap.get("申请人").toString().trim())) {
			logError("申请人为空.");
			return;
		}

		if (valuesMap.get("请假时长") == null || "".equals(valuesMap.get("请假时长").toString().trim())) {
			logError("请假时长为空.");
			return;
		}

		if (valuesMap.get("请假类别") == null || "".equals(valuesMap.get("请假类别").toString().trim())) {
			logError("请假类别为空.");
			return;
		}

		billDate = (java.util.Date) valuesMap.get("请假开始时间");
		if (billDate == null) {
			billDate = (java.util.Date) valuesMap.get("请假结束时间");
		}
		if (billDate == null) {
			logError("请假时间为空.");
			return;
		}

		billTypeName = valuesMap.get("请假类别").toString().trim();

		String temp = valuesMap.get("申请人").toString().trim();
		memberId = Long.valueOf(temp);

		temp = valuesMap.get("请假时长").toString().trim();
		qingJiaTimeLength = Double.valueOf(temp);

	}

	/*
	 * 执行扣减
	 */
	private void step3() {
		if (isSuccess() == false) {
			return;
		}
		if (billTypeName.equals("调休")) {
			JiaBanWriteDownRule rule = new JiaBanWriteDownRule();
			try {
				rule.execute(memberId, qingJiaTimeLength);
			} catch (Exception e) {
				logError("加班抵扣失败." + e.getLocalizedMessage());
			}
		} else if (billTypeName.equals("年假")) {
			NianJiaWriteDownRule rule = new NianJiaWriteDownRule();
			try {
				rule.execute(memberId, qingJiaTimeLength, billDate);
			} catch (Exception e) {
				logError("年假抵扣失败." + e.getLocalizedMessage());
			}
		} else {

		}
	}

	public void end() {
		if (context.isRedo()) {
			String step = context.getStep();
			if (!"QINGJIATASK".equals(step)) {
				return;
			}
		}

		if (!isFlag()) {
			return;
		}

		// 记录执行情况
		Date tmpFormDate = new java.sql.Date(System.currentTimeMillis());
		// 业务表单流水号
		String tmpFormCode = valuesMap.get("流程编号") == null ? "" : valuesMap.get("流程编号").toString();
		// 业务表单流程标题
		String tmpFormInfo = context.getSubject();
		// 记录执行日志
		if (this.isSuccess()) {
			writeExchageLog("Y", "YGQJ", "请假申请单", context.getFormTmpleteCode(), tmpFormDate, tmpFormCode, tmpFormInfo, "QINGJIATASK");
		} else {
			writeExchageLog("N", "YGQJ", "请假申请单", context.getFormTmpleteCode(), tmpFormDate, tmpFormCode, tmpFormInfo, "QINGJIATASK");
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
		else {
			billTypeName = valuesMap.get("请假类别") == null ? "" : valuesMap.get("请假类别").toString().trim();
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

	private boolean isFlag() {
		if (billTypeName.equals("调休")) {
			return true;
		}
		if (billTypeName.equals("年假")) {
			return true;
		}
		return false;
	}
}
