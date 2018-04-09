package com.seeyon.apps.a8x.common.dhtmlxgrid;

import java.io.Serializable;
import java.text.Format;

public class DhtmlxUserData implements Serializable {

	private static final long serialVersionUID = -2125899481875358114L;
	private String name;
	private String attribute;
	private String format;
	private String auth;
	private String description;
	private String columnID;

	public DhtmlxUserData() {

	}

	public DhtmlxUserData(String name, String attribute) {
		super();
		this.name = name;
		this.attribute = attribute;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format_) {
		this.format = format_;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getColumnID() {
		return columnID;
	}

	public void setColumnID(String columnID) {
		this.columnID = columnID;
	}

	public Format getFormater() {
		return FormatterCache.getFormatter(format);
	}
}
