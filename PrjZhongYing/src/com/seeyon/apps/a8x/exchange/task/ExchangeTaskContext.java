package com.seeyon.apps.a8x.exchange.task;

import java.io.Serializable;

/*
 * 集成交换任务上下文
 */
public class ExchangeTaskContext implements Serializable {

	private static final long serialVersionUID = -6632837577218786573L;

	// 表单模板编号
	private String formTmpleteCode;
	// 表单实体ID
	private long formRecordId;
	// summaryId
	private long summaryId;
	// formId
	private long formAppId;
	// 协同标题
	private String subject;

	// 是否再次执行,容错标志
	private boolean isRedo;

	// 任务阶段步骤,容错标志
	private String step;

	public ExchangeTaskContext(long aFormRecordId, long aSummaryId, long aFormAppId, String aFormTmpleteCode, String subject) {
		this.formRecordId = aFormRecordId;
		this.summaryId = aSummaryId;
		this.formAppId = aFormAppId;
		this.formTmpleteCode = aFormTmpleteCode;
		this.subject = subject;
	}

	public String getFormTmpleteCode() {
		return formTmpleteCode;
	}

	public long getFormRecordId() {
		return formRecordId;
	}

	public long getSummaryId() {
		return summaryId;
	}

	public long getFormAppId() {
		return formAppId;
	}

	public String getSubject() {
		return subject;
	}

	public boolean isRedo() {
		return isRedo;
	}

	public void setRedo(boolean isRedo) {
		this.isRedo = isRedo;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

}
