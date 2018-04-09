package com.seeyon.apps.a8x.listener;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.seeyon.apps.a8x.clocked.check.QingJiaChecker;
import com.seeyon.apps.collaboration.manager.ColManager;
import com.seeyon.apps.collaboration.po.ColSummary;
import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.common.po.template.CtpTemplate;
import com.seeyon.ctp.common.template.manager.TemplateManager;
import com.seeyon.ctp.workflow.event.AbstractWorkflowEvent;
import com.seeyon.ctp.workflow.event.WorkflowEventData;
import com.seeyon.ctp.workflow.event.WorkflowEventResult;

public class A8xWorkflowEventImpl extends AbstractWorkflowEvent {

	private static final Log log = LogFactory.getLog(A8xWorkflowEventImpl.class);

	public String getId() {
		return String.valueOf(getLabel().hashCode());
	}

	public String getLabel() {
		return "A8流程节点监听器";
	}

	public int sort() {
		return 1;
	}

	@Override
	public WorkflowEventResult onBeforeFinishWorkitem(WorkflowEventData data) {
		return doBeforeFinishWorkitem(data.getSummaryId());
	}

	private WorkflowEventResult doBeforeFinishWorkitem(long colSummaryId) {
		WorkflowEventResult workflowEventResult = new WorkflowEventResult();
		ColSummary tmpColSummary = null;
		try {
			ColManager colManager = (ColManager) AppContext.getBean("colManager");
			tmpColSummary = colManager.getColSummaryById(Long.valueOf(colSummaryId));
		} catch (Exception e1) {
			workflowEventResult.setAlertMessage("获取协同表单资料失败:\n" + e1.getLocalizedMessage());
			return workflowEventResult;
		}
		if (tmpColSummary == null) {
			workflowEventResult.setAlertMessage("错误:协同表单资料为空!!!\n");
			return workflowEventResult;
		}

		String tmpTempleteNumber = null;
		try {
			tmpTempleteNumber = getA8FormCode(tmpColSummary);
		} catch (Exception e1) {
			workflowEventResult.setAlertMessage("获取表单模板编号信息失败:\n" + e1.getLocalizedMessage());
			return workflowEventResult;
		}
		if (StringUtils.isBlank(tmpTempleteNumber)) {
			log.info("表单模板编号为空..");
			return null;
		}
		tmpTempleteNumber = tmpTempleteNumber.toUpperCase().trim();
		//员工请假
		if (tmpTempleteNumber.startsWith("YGQJ")) {
			QingJiaChecker tmpQingJiaChecker = new QingJiaChecker();
			try {
				boolean flag = tmpQingJiaChecker.checkOnFinish(tmpColSummary.getFormRecordid(),false);
				if (!flag) {
					workflowEventResult.setAlertMessage("执行[请假账表校验]任务失败:\n" + tmpQingJiaChecker.getMessage());
					return workflowEventResult;
				}
			} catch (Exception e) {
				workflowEventResult.setAlertMessage("执行[请假账表校验]任务失败:\n" + e.getLocalizedMessage());
				return workflowEventResult;
			}
		}
		//督导请假单
		if (tmpTempleteNumber.startsWith("YDQJ")) {
			QingJiaChecker tmpQingJiaChecker = new QingJiaChecker();
			try {
				boolean flag = tmpQingJiaChecker.checkOnFinish(tmpColSummary.getFormRecordid(),true);
				if (!flag) {
					workflowEventResult.setAlertMessage("执行[请假账表校验]任务失败:\n" + tmpQingJiaChecker.getMessage());
					return workflowEventResult;
				}
			} catch (Exception e) {
				workflowEventResult.setAlertMessage("执行[请假账表校验]任务失败:\n" + e.getLocalizedMessage());
				return workflowEventResult;
			}
		}
		
		return null;
	}

	private String getA8FormCode(ColSummary colSummary) throws Exception {
		if (colSummary == null) {
			return null;
		}
		Long templetId = colSummary.getTempleteId();
		if (templetId == null) {
			return null;
		}
		TemplateManager templateManager = (TemplateManager) AppContext.getBean("templateManager");
		CtpTemplate templete = templateManager.getCtpTemplate(templetId);
		if (templete == null) {
			return null;
		}
		String a8FormCode = templete.getTempleteNumber();
		if (StringUtils.isBlank(a8FormCode)) {
			return null;
		}
		return a8FormCode;
	}

}
