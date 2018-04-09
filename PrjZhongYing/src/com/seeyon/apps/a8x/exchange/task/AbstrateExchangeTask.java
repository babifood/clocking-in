package com.seeyon.apps.a8x.exchange.task;

import java.sql.Date;
import java.sql.Timestamp;

import org.apache.commons.logging.Log;

import com.seeyon.apps.a8x.common.exception.ProcessingException;
import com.seeyon.apps.a8x.common.util.UtilString;
import com.seeyon.apps.a8x.exchange.delegate.ExchangeLogDelegate;
import com.seeyon.apps.a8x.exchange.entity.ExchangeLog;
import com.seeyon.apps.a8x.oa.A8xFormDataReader;

/*
 * 集成交换任务接口,该接口具体实现类,在线程执行.
 */
public abstract class AbstrateExchangeTask implements ExchangeTask {

	// 处理成功
	public static final String FLAG_Y = "Y";
	// 处理失败
	public static final String FLAG_N = "N";
	// 待处理
	public static final String FLAG_W = "-";

	
	protected ExchangeTaskContext context;

	// 消息记录
	private String a8xMessage = "";

	// 表单数据读取器
	protected A8xFormDataReader formDataReader;

	public void start() {

	}

	public void end() {

	}

	public AbstrateExchangeTask(ExchangeTaskContext context_) {
		this.context = context_;
		formDataReader = new A8xFormDataReader(true);
	}

	public String getMessage() {
		return a8xMessage;
	}

	public boolean isSuccess() {
		if (a8xMessage == null || "".equals(a8xMessage)) {
			return true;
		} else {
			return false;
		}
	}

	public final void logError(String errorInfo) {
		a8xMessage = errorInfo;
	}

	/*
	 * 记录错误
	 */
	protected void logError(Log log, String errorInfo) {
		a8xMessage = errorInfo;
		log.error(errorInfo);
	}

	
	protected void writeWaitingExchageLog(String aModuleType, String aModuleName, String aTemplateCode, Date aFormDate, String aFormCode, String aFormInfo, String aStep) {
		String flag = "-";
		this.writeExchageLog(flag, aModuleType, aModuleName, aTemplateCode, aFormDate, aFormCode, aFormInfo, aStep);
	}
	
	// 记录操作日志
	protected void writeExchageLog(boolean isSuccess, String aModuleType, String aModuleName, String aTemplateCode, Date aFormDate, String aFormCode, String aFormInfo, String aStep) {
		String flag = "-";
		if (isSuccess) {
			flag = "Y";
		} else {
			flag = "N";
		}
		this.writeExchageLog(flag, aModuleType, aModuleName, aTemplateCode, aFormDate, aFormCode, aFormInfo, aStep);
	}

	// 记录操作日志
	protected void writeExchageLog(String aFlag, String aModuleType, String aModuleName, String aTemplateCode, Date aFormDate, String aFormCode, String aFormInfo, String aStep) {
		ExchangeLog exchageLog = new ExchangeLog();
		String tmpMessage = null;
		// id
		exchageLog.setIdIfNew();
		// 模块类型
		exchageLog.setModuleType(aModuleType);
		// 模块名称
		exchageLog.setModuleName(aModuleName);
		// 模板编号
		exchageLog.setTempleteCode(aTemplateCode);
		// 表单实体
		exchageLog.setFormRecordId(context.getFormRecordId());
		exchageLog.setSummaryId(context.getSummaryId());
		exchageLog.setFormAppId(context.getFormAppId());
		// 表单编码
		exchageLog.setFormCode(aFormCode);
		// 表单信息
		exchageLog.setFormInfo(aFormInfo);
		// 表单日期
		exchageLog.setFormDate(aFormDate);
		// 执行标志
		if ("Y".equalsIgnoreCase(aFlag)) {
			// 成功
			exchageLog.setOperateFlag("Y");
			tmpMessage = "成功...";
		} else if ("N".equalsIgnoreCase(aFlag)) {
			tmpMessage = a8xMessage;
			exchageLog.setOperateFlag("N");
		} else if ("X".equalsIgnoreCase(aFlag)) {
			tmpMessage = "***节点事件,无须手工干预***\n" + a8xMessage;
			exchageLog.setOperateFlag("X");
		} else {
			exchageLog.setOperateFlag("-");
			tmpMessage = "处理中...";
		}

		// 执行结果
		if (tmpMessage != null && UtilString.lengthString(tmpMessage) > 2000) {
			tmpMessage = UtilString.cutString(tmpMessage, 2000);
		}
		exchageLog.setResult(tmpMessage);
		// 步骤
		exchageLog.setStep(aStep);
		// 执行次数
		exchageLog.setOperateCount(0);
		// 排序
		exchageLog.setSort(System.currentTimeMillis());
		// create_id
		// exchageLog.setCreate_id();
		// create_date
		exchageLog.setCreate_date(new Timestamp(System.currentTimeMillis()));
		// modify_id
		// exchageLog.setModify_id();
		// modify_date
		exchageLog.setModify_date(new Timestamp(System.currentTimeMillis()));
		try {
			if (ExchangeLogDelegate.isExisted(context.getFormRecordId())) {
				ExchangeLogDelegate.updateByFormRecordId(exchageLog);
			} else {
				ExchangeLogDelegate.create(exchageLog);
			}
		} catch (ProcessingException e) {
			e.printStackTrace();
		}
	}

	protected void sleepx(int times) {
		try {
			Thread.sleep(times);
		} catch (InterruptedException e) {

		}
	}

}
