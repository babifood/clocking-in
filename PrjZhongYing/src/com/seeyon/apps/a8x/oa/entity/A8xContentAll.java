package com.seeyon.apps.a8x.oa.entity;

import java.sql.Timestamp;

import www.seeyon.com.utils.UUIDUtil;

import com.seeyon.ctp.common.ModuleType;
import com.seeyon.ctp.form.bean.FormBean;

public class A8xContentAll implements java.io.Serializable {

	private static final long serialVersionUID = 1600566135417095984L;

	// id
	private long id;
	// create_id
	private long create_id;
	// create_date
	private java.sql.Timestamp create_date;
	// modify_id
	private long modify_id;
	// modify_date
	private java.sql.Timestamp modify_date;
	// module_type
	private int module_type;
	// module_id
	private long module_id;
	// module_template_id
	private long module_template_id;
	// content_type
	private int content_type;
	// content
	private String content;
	// content_data_id
	private long content_data_id;
	// content_template_id
	private long content_template_id;
	// title
	private String title;
	// sort
	private int sort;

	/**
	 * Default Constructor
	 */
	public A8xContentAll() {
		// do nothing
	}

	// Bean methods......
	public long getId() {
		return this.id;
	}

	public void setId(long theId) {
		this.id = theId;
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

	public int getModule_type() {
		return this.module_type;
	}

	public void setModule_type(int theModule_type) {
		this.module_type = theModule_type;
	}

	public long getModule_id() {
		return this.module_id;
	}

	public void setModule_id(long theModule_id) {
		this.module_id = theModule_id;
	}

	public long getModule_template_id() {
		return this.module_template_id;
	}

	public void setModule_template_id(long theModule_template_id) {
		this.module_template_id = theModule_template_id;
	}

	public int getContent_type() {
		return this.content_type;
	}

	public void setContent_type(int theContent_type) {
		this.content_type = theContent_type;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String theContent) {
		this.content = theContent;
	}

	public long getContent_data_id() {
		return this.content_data_id;
	}

	public void setContent_data_id(long theContent_data_id) {
		this.content_data_id = theContent_data_id;
	}

	public long getContent_template_id() {
		return this.content_template_id;
	}

	public void setContent_template_id(long theContent_template_id) {
		this.content_template_id = theContent_template_id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String theTitle) {
		this.title = theTitle;
	}

	public int getSort() {
		return this.sort;
	}

	public void setSort(int theSort) {
		this.sort = theSort;
	}

	public A8xContentAll copy4New(FormBean formBean, long userID, long masterId) {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		this.setModule_type(ModuleType.unflowInfo.getKey());
		this.setContent_type(20);
		this.setCreate_id(userID);
		this.setCreate_date(now);
		this.setModify_date(now);
		this.setModify_id(userID);
		this.setSort(0);
		this.setId(UUIDUtil.getUUIDLong());
		// content.setStatus(MainbodyStatus.STATUS_POST_SAVE);
		this.setTitle(formBean.getFormName());
		this.setModule_id(masterId);
		this.setModule_template_id(formBean.getId());
		this.setContent_template_id(formBean.getId());
		this.setContent_data_id(masterId);
		return this;
	}

	public String toString() {
		StringBuffer returnString = new StringBuffer();
		returnString.append("CtpContentAll[");
		// Set member attributes
		returnString.append("id = " + this.id + ";\n");
		returnString.append("create_id = " + this.create_id + ";\n");
		returnString.append("create_date = " + this.create_date + ";\n");
		returnString.append("modify_id = " + this.modify_id + ";\n");
		returnString.append("modify_date = " + this.modify_date + ";\n");
		returnString.append("module_type = " + this.module_type + ";\n");
		returnString.append("module_id = " + this.module_id + ";\n");
		returnString.append("module_template_id = " + this.module_template_id + ";\n");
		returnString.append("content_type = " + this.content_type + ";\n");
		returnString.append("content = " + this.content + ";\n");
		returnString.append("content_data_id = " + this.content_data_id + ";\n");
		returnString.append("content_template_id = " + this.content_template_id + ";\n");
		returnString.append("title = " + this.title + ";\n");
		returnString.append("sort = " + this.sort + ";\n");
		returnString.append("]\n");
		return returnString.toString();
	}
}