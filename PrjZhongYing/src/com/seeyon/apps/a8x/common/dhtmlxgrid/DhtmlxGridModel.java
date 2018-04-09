package com.seeyon.apps.a8x.common.dhtmlxgrid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DhtmlxGridModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6082577370092131427L;
	private List<DhtmlxGridField> items;
	// 用户自定义的属性
	private List<DhtmlxUserData> userDatas;
	// 全局自定义的属性
	private List<DhtmlxUserData> globalUserDatas;

	private String headers;
	private String widths;
	private String types;
	private String aligns;
	private String sorts;
	private String colors;
	private String formats;
	private String hiddens;
	private String attributes;
	private String footers = "";
	private String stats = "";
	private String columnids = "";

	public DhtmlxGridModel(int size) {
		items = new ArrayList<DhtmlxGridField>(size);
	}

	public List<DhtmlxGridField> getItems() {
		return items;
	}

	public List<DhtmlxUserData> getUserDatas() {
		return userDatas;
	}

	public List<DhtmlxUserData> getGlobalUserDatas() {
		return globalUserDatas;
	}

	public String toString() {
		return items.toString();
	}

	public void add(DhtmlxGridField theDhtmlxGridHeader) {
		items.add(theDhtmlxGridHeader);
	}

	public void addGlobal(DhtmlxUserData theDhtmlxUserData) {
		if (globalUserDatas == null) {
			globalUserDatas = new ArrayList<DhtmlxUserData>(10);
		}
		globalUserDatas.add(theDhtmlxUserData);
	}

	public void add(DhtmlxUserData theDhtmlxUserData) {
		if (userDatas == null) {
			userDatas = new ArrayList<DhtmlxUserData>(10);
		}
		userDatas.add(theDhtmlxUserData);
	}

	private void checkNull() {

		DhtmlxGridField tmpDhtmlxGridField = null;
		int size = items.size();
		int flag = 0;
		boolean hasFooter = false;
		if (headers == null) {
			headers = "";
			widths = "";
			types = "";
			aligns = "";
			sorts = "";
			colors = "";
			formats = "";
			hiddens = "";
			attributes = "";
			footers = "";
			stats = "";
			columnids = "";
			for (int i = 0; i < size; i++) {
				if (flag > 0) {
					headers += ",";
					widths += ",";
					types += ",";
					aligns += ",";
					sorts += ",";
					colors += ",";
					formats += ",";
					hiddens += ",";
					attributes += ",";
					footers += ",";
					stats += ",";
					columnids += ",";
				}
				tmpDhtmlxGridField = items.get(i);
				headers += tmpDhtmlxGridField.getDescription();
				widths += tmpDhtmlxGridField.getWidth();
				types += tmpDhtmlxGridField.getType();
				aligns += tmpDhtmlxGridField.getAlign();
				sorts += tmpDhtmlxGridField.getSort();
				colors += tmpDhtmlxGridField.getColor();// #d5f1ff
				formats += tmpDhtmlxGridField.getFormat();
				hiddens += tmpDhtmlxGridField.getHidden();
				attributes += tmpDhtmlxGridField.getAttribute();
				columnids += tmpDhtmlxGridField.getColumnID();

				stats += tmpDhtmlxGridField.getStat();
				if ("#cspan".equals(tmpDhtmlxGridField.getFooter())) {
					footers += "#cspan";
					hasFooter = true;
				} else if (tmpDhtmlxGridField.getFooter() != null && !"".equals(tmpDhtmlxGridField.getFooter())) {
					footers += "<div id='" + tmpDhtmlxGridField.getFooter() + "'>&nbsp;</div>";
					hasFooter = true;
				} else {
					footers += "";
				}
				flag++;
			}
			if (!hasFooter) {
				footers = "";
			}
		}
	}

	public String getHeaders() {
		checkNull();
		return headers;
	}

	public String getWidths() {
		checkNull();
		return widths;
	}

	public String getColumnids() {
		checkNull();
		return columnids;
	}

	public String getTypes() {
		checkNull();
		return types;
	}

	public String getAligns() {
		checkNull();
		return aligns;
	}

	public String getSorts() {
		checkNull();
		return sorts;
	}

	public String getColors() {
		checkNull();
		return colors;
	}

	public String getFormats() {
		checkNull();
		return formats;
	}

	public String getHiddens() {
		checkNull();
		return hiddens;
	}

	public String getAttributes() {
		checkNull();
		return attributes;
	}

	public String getFooters() {
		checkNull();
		return footers;
	}

	public String getStats() {
		checkNull();
		return stats;
	}
}
