package com.seeyon.apps.a8x.common.dhtmlxgrid;

import java.io.Serializable;
import java.text.Format;

public class DhtmlxGridField implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1178805959899942036L;
	public final String STAT_SUM = "sum";
	public final String STAT_AVG = "avg";
	public final String STAT_GET = "get";

	private String description;
	private String width;
	private String type;
	private String align;
	private String sort;
	private String color;
	private String format;
	private String hidden;
	private String attribute;
	private String columnID;

	private String auth;
	private String footer = "";

	private String stat = "";

	private String excelStyle = "";

	public DhtmlxGridField() {

	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getHidden() {
		return hidden;
	}

	public void setHidden(String hidden) {
		this.hidden = hidden;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public String getExcelStyle() {
		return excelStyle;
	}

	public void setExcelStyle(String excelStyle) {
		this.excelStyle = excelStyle;
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

	public String toString() {
		StringBuffer returnString = new StringBuffer();
		returnString.append("DhtmlxGridHeader[");
		// Set member attributes
		returnString.append("description = " + this.description + ";\n");
		returnString.append("width = " + this.width + ";\n");
		returnString.append("type = " + this.type + ";\n");
		returnString.append("align = " + this.align + ";\n");
		returnString.append("sort = " + this.sort + ";\n");
		returnString.append("color = " + this.color + ";\n");
		returnString.append("format = " + this.format + ";\n");
		returnString.append("hidden = " + this.hidden + ";\n");
		returnString.append("auth = " + this.auth + ";\n");
		returnString.append("]\n");
		return returnString.toString();
	}
}
