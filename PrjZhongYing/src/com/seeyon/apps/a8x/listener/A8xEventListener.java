package com.seeyon.apps.a8x.listener;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.task.TaskExecutor;

import www.seeyon.com.utils.StringUtil;

import com.seeyon.apps.a8x.exchange.task.ExchangeTask;
import com.seeyon.apps.a8x.exchange.task.ExchangeTaskContext;
import com.seeyon.apps.a8x.exchange.task.FormExchangeRunnable;
import com.seeyon.apps.a8x.exchange.task.FormExchangeTaskFactory;
import com.seeyon.apps.collaboration.event.CollaborationFinishEvent;
import com.seeyon.apps.collaboration.event.CollaborationStartEvent;
import com.seeyon.apps.collaboration.manager.ColManager;
import com.seeyon.apps.collaboration.po.ColSummary;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.util.annotation.ListenEvent;
import com.seeyon.ctp.workflow.event.WorkflowEventResult;

/*
 * 表单流程监听器,只配置一个.
 */
public class A8xEventListener {
	private static final Log log = LogFactory.getLog(A8xEventListener.class);
	// 任务执行器
	private TaskExecutor a8xTaskExecutor;

	private ColManager colManager;

	/**
	 * 表单发起事件监听
	 */
	@ListenEvent(event = CollaborationStartEvent.class)
	public void onCollaborationStart(CollaborationStartEvent event) throws BusinessException{
		WorkflowEventResult workflowEventResult = new WorkflowEventResult();
		workflowEventResult.setAlertMessage("欢迎发起流程");
	}
	/**
	 * 表单结束事件监听
	 */
	@ListenEvent(event = CollaborationFinishEvent.class)
	public void onCollaborationFinish(CollaborationFinishEvent event) throws BusinessException {
		String tmpTempleteNumber = "";
		try {
			tmpTempleteNumber = event.getTemplateCode();
			// 转为大写
			tmpTempleteNumber = tmpTempleteNumber == null ? "" : tmpTempleteNumber.trim().toUpperCase();
		} catch (BusinessException e1) {
			log.info("错误:获取模板编号错误." + e1.getLocalizedMessage());
			return;
		}
		if (StringUtil.isEmpty(tmpTempleteNumber)) {
			// /log.info("错误:模板编号没有设置.");
			return;
		}

		long summaryId = event.getSummaryId();
		ColSummary tmpColSummary = null;
		// 通过id查找对应的ColSummary
		try {
			tmpColSummary = colManager.getColSummaryById(summaryId);
		} catch (BusinessException e) {
			log.error("错误:获取ColSummary实例失败，summaryId：" + summaryId);
			return;
		}
		if (tmpColSummary == null) {
			log.error("错误:ColSummary实例为空，summaryId：" + summaryId);
			return;
		}
		// 创建数据交换任务
		ExchangeTaskContext context = new ExchangeTaskContext(tmpColSummary.getFormRecordid(), tmpColSummary.getId(), tmpColSummary.getFormAppid(), tmpTempleteNumber, tmpColSummary.getSubject());
		List<ExchangeTask> tmpExchanger = FormExchangeTaskFactory.create(context);
		if (tmpExchanger != null && (!tmpExchanger.isEmpty())) {
			// 在线程池中执行任务
			FormExchangeRunnable task = new FormExchangeRunnable(tmpExchanger);
			a8xTaskExecutor.execute(task);
		}
	}

	public ColManager getColManager() {
		return colManager;
	}

	public void setColManager(ColManager colManager) {
		this.colManager = colManager;
	}

	public TaskExecutor getA8xTaskExecutor() {
		return a8xTaskExecutor;
	}

	public void setA8xTaskExecutor(TaskExecutor a8xTaskExecutor) {
		this.a8xTaskExecutor = a8xTaskExecutor;
	}

}
