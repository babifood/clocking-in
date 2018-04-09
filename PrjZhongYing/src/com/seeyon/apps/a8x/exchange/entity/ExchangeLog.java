package com.seeyon.apps.a8x.exchange.entity;

import com.seeyon.ctp.util.UUIDLong;

public class ExchangeLog implements java.io.Serializable {
	private static final long serialVersionUID = 1092299320278075109L;
	// id
	private long id;
	// 模块类型
	private String moduleType;
	// 模块名称
	private String moduleName;
	// 模板编号
	private String templeteCode;
	// 表单实体ID
	private long formRecordId;
	// summaryId
	private long summaryId;
	// formAppId
	private long formAppId;
	// 表单编号
	private String formCode;
	// 表单日期
	private java.sql.Date formDate;
	// 表单简要
	private String formInfo;
	// 执行状况
	private String operateFlag;
	// 执行结果
	private String result;
	// 步骤说明
	private String step;
	// 执行次数
	private int operateCount;
	// 处理结果
	private String action;
	// 处理结果标志
	private int actionFlag;
	// 排序
	private long sort;
	// create_id
	private long create_id;
	// create_date
	private java.sql.Timestamp create_date;
	// modify_id
	private long modify_id;
	// modify_date
	private java.sql.Timestamp modify_date;

	/**
	 * Default Constructor
	 */
	public ExchangeLog() {
		// do nothing
	}

	public void setIdIfNew() {
		if (this.getId() == 0) {
			setId(UUIDLong.longUUID());
		}
	}

	// Bean methods......
	public long getId() {
		return this.id;
	}

	public void setId(long theId) {
		this.id = theId;
	}

	public String getModuleType() {
		return this.moduleType;
	}

	public void setModuleType(String theModuleType) {
		this.moduleType = theModuleType;
	}

	public String getModuleName() {
		return this.moduleName;
	}

	public void setModuleName(String theModuleName) {
		this.moduleName = theModuleName;
	}

	public String getTempleteCode() {
		return this.templeteCode;
	}

	public void setTempleteCode(String theTempleteCode) {
		this.templeteCode = theTempleteCode;
	}

	public long getFormRecordId() {
		return this.formRecordId;
	}

	public void setFormRecordId(long theFormRecordId) {
		this.formRecordId = theFormRecordId;
	}

	public long getSummaryId() {
		return this.summaryId;
	}

	public void setSummaryId(long theSummaryId) {
		this.summaryId = theSummaryId;
	}

	public long getFormAppId() {
		return this.formAppId;
	}

	public void setFormAppId(long theFormAppId) {
		this.formAppId = theFormAppId;
	}

	public String getFormCode() {
		return this.formCode;
	}

	public void setFormCode(String theFormCode) {
		this.formCode = theFormCode;
	}

	public java.sql.Date getFormDate() {
		return this.formDate;
	}

	public void setFormDate(java.sql.Date theFormDate) {
		this.formDate = theFormDate;
	}

	public String getFormInfo() {
		return this.formInfo;
	}

	public void setFormInfo(String theFormInfo) {
		this.formInfo = theFormInfo;
	}

	public String getOperateFlag() {
		return this.operateFlag;
	}

	public void setOperateFlag(String theOperateFlag) {
		this.operateFlag = theOperateFlag;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String theResult) {
		this.result = theResult;
	}

	public String getStep() {
		return this.step;
	}

	public void setStep(String theStep) {
		this.step = theStep;
	}

	public int getOperateCount() {
		return this.operateCount;
	}

	public void setOperateCount(int theOperateCount) {
		this.operateCount = theOperateCount;
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String theAction) {
		this.action = theAction;
	}

	public int getActionFlag() {
		return this.actionFlag;
	}

	public void setActionFlag(int theActionFlag) {
		this.actionFlag = theActionFlag;
	}

	public long getSort() {
		return this.sort;
	}

	public void setSort(long theSort) {
		this.sort = theSort;
	}

	public long getCreate_id() {
		return this.create_id;
	}

	public void setCreate_id(long theCreate_id) {
		this.create_id = theCreate_id;
	}

	public java.sql.Timestamp getCreate_date() {
		return this.create_date;
	}

	public void setCreate_date(java.sql.Timestamp theCreate_date) {
		this.create_date = theCreate_date;
	}

	public long getModify_id() {
		return this.modify_id;
	}

	public void setModify_id(long theModify_id) {
		this.modify_id = theModify_id;
	}

	public java.sql.Timestamp getModify_date() {
		return this.modify_date;
	}

	public void setModify_date(java.sql.Timestamp theModify_date) {
		this.modify_date = theModify_date;
	}

	public String toString() {
		StringBuffer returnString = new StringBuffer();
		returnString.append("ExchangeLog[");
		// Set member attributes
		returnString.append("id = " + this.id + ";\n");
		returnString.append("moduleType = " + this.moduleType + ";\n");
		returnString.append("moduleName = " + this.moduleName + ";\n");
		returnString.append("templeteCode = " + this.templeteCode + ";\n");
		returnString.append("formRecordId = " + this.formRecordId + ";\n");
		returnString.append("summaryId = " + this.summaryId + ";\n");
		returnString.append("formAppId = " + this.formAppId + ";\n");
		returnString.append("formCode = " + this.formCode + ";\n");
		returnString.append("formDate = " + this.formDate + ";\n");
		returnString.append("formInfo = " + this.formInfo + ";\n");
		returnString.append("operateFlag = " + this.operateFlag + ";\n");
		returnString.append("result = " + this.result + ";\n");
		returnString.append("step = " + this.step + ";\n");
		returnString.append("operateCount = " + this.operateCount + ";\n");
		returnString.append("action = " + this.action + ";\n");
		returnString.append("actionFlag = " + this.actionFlag + ";\n");
		returnString.append("sort = " + this.sort + ";\n");
		returnString.append("create_id = " + this.create_id + ";\n");
		returnString.append("create_date = " + this.create_date + ";\n");
		returnString.append("modify_id = " + this.modify_id + ";\n");
		returnString.append("modify_date = " + this.modify_date + ";\n");
		returnString.append("]\n");
		return returnString.toString();
	}
}