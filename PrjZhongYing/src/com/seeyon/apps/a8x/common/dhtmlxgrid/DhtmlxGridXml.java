package com.seeyon.apps.a8x.common.dhtmlxgrid;

import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import com.seeyon.apps.a8x.common.domain.QueryResult;

public class DhtmlxGridXml {
	private String gridName;
	private QueryResult result;
	private List<?> valueList;
	private Object globalValue;

	private int pageNo = 0;

	private int pageSize = 0;

	private int totalSize = 0;

	private int pageCount = 0;

	private HashMap<String, ValueSum> map;

	private String errors = "";

	public DhtmlxGridXml(String theGridName, QueryResult theResult) {
		this.gridName = theGridName;
		this.result = theResult;
		if (result != null) {
			this.valueList = result.getDatas();
			if (result.isDisplayByPage()) {
				totalSize = result.getTotalSize();
				pageSize = result.getPageSize();
				pageNo = result.getPageNo();
				pageCount = result.getPageCount();
			} else {
				totalSize = valueList == null ? 0 : valueList.size();
				pageSize = totalSize;
				pageNo = 1;
				pageCount = 1;
			}
		}
	}

	public DhtmlxGridXml(String gridName, QueryResult result, Object globalValue) {
		this(gridName, result);
		this.globalValue = globalValue;
	}

	public DhtmlxGridXml(String gridName, List<?> valueList) {
		this.gridName = gridName;
		this.valueList = valueList;
		totalSize = valueList == null ? 0 : valueList.size();
		pageSize = totalSize;
		pageNo = 1;
		pageCount = 1;
	}

	public DhtmlxGridXml(String gridName, List<?> valueList, Object globalValue) {
		this(gridName, valueList);
		this.globalValue = globalValue;
	}

	public HashMap<String, ValueSum> getMapModel() {
		if (map == null) {
			map = new HashMap<String, ValueSum>(20);
		}
		return map;
	}

	private Object getPropertyValue(Object theValue, String theField) {
		Object temp = null;
		try {
			temp = PropertyUtils.getProperty(theValue, theField);
		} catch (NoSuchMethodException e) {
			if (errors.length() > 0) {
				errors += "\n";
				errors += e.getLocalizedMessage();
			} else {
				errors = e.getLocalizedMessage();
			}
		} catch (IllegalAccessException e) {
			if (errors.length() > 0) {
				errors += "\n";
				errors += e.getLocalizedMessage();
			} else {
				errors = e.getLocalizedMessage();
			}
		} catch (InvocationTargetException e) {
			if (errors.length() > 0) {
				errors += "\n";
				errors += e.getLocalizedMessage();
			} else {
				errors = e.getLocalizedMessage();
			}
		} catch (Exception e) {

		}
		return temp;
	}

	private void write(Writer writer, String s) {
		try {
			writer.write(s);
		} catch (Exception e) {

		}
	}

	private void write_header(Writer writer) {
		write(writer, "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		write(writer, "<rows>");
		write(writer, "<userdata name=\"pageCount\">");
		write(writer, String.valueOf(pageCount));
		write(writer, "</userdata>");
		write(writer, "<userdata name=\"pageSize\">");
		write(writer, String.valueOf(pageSize));
		write(writer, "</userdata>");
		write(writer, "<userdata name=\"currentPage\">");
		write(writer, String.valueOf(pageNo));
		write(writer, "</userdata>");
		write(writer, "<userdata name=\"totalCount\">");
		write(writer, String.valueOf(totalSize));
		write(writer, "</userdata>");
	}

	private void write_globalValue(Writer writer, DhtmlxGridModel tmpModel) {
		List<DhtmlxUserData> tmpGlobalList = tmpModel.getGlobalUserDatas();
		int userDataSize = tmpGlobalList == null ? 0 : tmpGlobalList.size();
		DhtmlxUserData tmpUserData = null;
		Object tmpObject = null;
		for (int m = 0; m < userDataSize; m++) {
			tmpUserData = tmpGlobalList.get(m);
			write(writer, "<userdata name=\"");
			write(writer, tmpUserData.getName());
			write(writer, "\">");
			tmpObject = getPropertyValue(globalValue, tmpUserData.getAttribute());
			if (tmpObject != null) {
				if ("".equals(tmpUserData.getFormat())) {
					write(writer, tmpObject.toString());
				} else {
					write(writer, tmpUserData.getFormater().format(tmpObject));
				}
			}
			write(writer, "</userdata>");
		}
	}

	private void write_rowUserData(Writer writer, DhtmlxUserData tmpUserData, Object theValue) {
		Object tmpObject = null;
		write(writer, "<userdata name=\"");
		write(writer, tmpUserData.getName());
		write(writer, "\">");
		tmpObject = getPropertyValue(theValue, tmpUserData.getAttribute());
		write(writer, tmpObject == null ? "" : tmpObject.toString());
		write(writer, "</userdata>");
	}

	private void write_cell1(Writer writer, DhtmlxGridField tmpField, int i) {
		write(writer, "<cell>");
		if (tmpField.getDescription().equals("*")) {
			write(writer, String.valueOf(i + 1));
		} 
		else if (tmpField.getDescription().equals("-")) {
			write(writer, "0");
		} 
		else {
			write(writer, "0");
		}
		write(writer, "</cell>");
	}

	private void write_cell2(Writer writer, DhtmlxGridField tmpField, Object theValue) {
		Object tmpObject = null;
		tmpObject = getPropertyValue(theValue, tmpField.getAttribute());
		if (tmpObject == null) {
			write(writer, "<cell></cell>");
		} else {
			if ("".equals(tmpField.getFormat())) {
				write(writer, "<cell>");
				write(writer, tmpObject.toString());
				write(writer, "</cell>");
			} else {
				write(writer, "<cell>");
				write(writer, tmpField.getFormater().format(tmpObject));
				write(writer, "</cell>");
			}
		}
	}

	private void write_globalfooter(Writer writer) {
		if (map != null && !getMapModel().isEmpty()) {
			Iterator<String> it = getMapModel().keySet().iterator();
			String itName = null;
			ValueSum tmpValueSum = null;
			while (it.hasNext()) {
				itName = it.next();
				tmpValueSum = getMapModel().get(itName);
				write(writer, "<userdata name=\"");
				write(writer, itName);
				write(writer, "\">");
				write(writer, tmpValueSum.getFormater().format(tmpValueSum.getValue()));

				write(writer, "</userdata>");
			}
		}
	}

	private void footer(DhtmlxGridField tmpField, Object theValue) {
		BigDecimal tmpBigDecimal = null;
		Object tmpObject = null;
		if (!"".equals(tmpField.getStat())) {
			tmpObject = getPropertyValue(theValue, tmpField.getAttribute());
			if (tmpObject != null) {
				tmpBigDecimal = new BigDecimal(tmpObject.toString());
			}
			if ("sum".equals(tmpField.getStat())) {
				ValueSum tmValueSum = getMapModel().get(tmpField.getFooter());
				if (tmValueSum == null) {
					tmValueSum = new ValueSum();
					if (tmpField.getFormater() == null) {
						tmValueSum.setFormater(FormatterCache.getFormatter("#,##0.00##"));
					} else {
						tmValueSum.setFormater(tmpField.getFormater());
					}
					getMapModel().put(tmpField.getFooter(), tmValueSum);
				}
				tmValueSum.sum(tmpBigDecimal);
			}
			if ("avg".equals(tmpField.getStat())) {
				ValueAvg tmpValueAvg = (ValueAvg) getMapModel().get(tmpField.getFooter());
				if (tmpValueAvg == null) {
					tmpValueAvg = new ValueAvg();
					if (tmpField.getFormater() == null) {
						tmpValueAvg.setFormater(FormatterCache.getFormatter("#,##0.00##"));
					} else {
						tmpValueAvg.setFormater(tmpField.getFormater());
					}
					getMapModel().put(tmpField.getFooter(), tmpValueAvg);
				}
				tmpValueAvg.sum(tmpBigDecimal);
			}
		}
	}

	private void write_row(Writer writer, DhtmlxGridModel tmpModel, int i) {
		List<DhtmlxGridField> tmpList = tmpModel.getItems();
		List<DhtmlxUserData> tmpUserDataList = tmpModel.getUserDatas();
		int fieldsSize = tmpList.size();
		int userDataSize = tmpUserDataList == null ? 0 : tmpUserDataList.size();
		DhtmlxGridField tmpField = null;
		DhtmlxUserData tmpUserData = null;
		write(writer, "<row id=\"");
		write(writer, String.valueOf(i + 1));
		write(writer, "\">");
		for (int m = 0; m < userDataSize; m++) {
			tmpUserData = tmpUserDataList.get(m);
			write_rowUserData(writer, tmpUserData, valueList.get(i));
		}
		for (int k = 0; k < fieldsSize; k++) {
			tmpField = tmpList.get(k);
			if ("".equals(tmpField.getAttribute())) {
				write_cell1(writer, tmpField, i);
			} else {
				write_cell2(writer, tmpField, valueList.get(i));
				// 计算
				footer(tmpField, valueList.get(i));
			}
		}
		write(writer, "</row>\n");
	}

	public void toXml(Writer writer) {
		// long start = System.currentTimeMillis();
		if (valueList == null) {
			toEmptyXml(writer);
			return;
		}
		DhtmlxGridMgr tmpDhtmlxgridMgr = DhtmlxGridMgr.getMe();
		DhtmlxGridModel tmpModel = tmpDhtmlxgridMgr.getDhtmlxGridModel(gridName);

		int size = valueList.size();
		// header
		write_header(writer);
		if (globalValue != null) {
			write_globalValue(writer, tmpModel);
		}
		if ("".equals(errors)) {
			for (int i = 0; i < size; i++) {
				write_row(writer, tmpModel, i);
				if (!"".equals(errors)) {
					break;
				}
			}
		}
		if ("".equals(errors)) {
			write_globalfooter(writer);
		}
		if (!"".equals(errors)) {
			write(writer, "<userdata name=\"error\">");
			write(writer, "<![CDATA[");
			write(writer, errors);
			write(writer, "]]>");
			write(writer, "</userdata>");
		}
		write(writer, "</rows>");
		// System.out.println((System.currentTimeMillis() - start)+"豪秒");
	}

	private void toEmptyXml(Writer writer) {
		write(writer, "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		write(writer, "<rows>");
		write(writer, "<userdata name=\"pageCount\">");
		write(writer, String.valueOf(pageCount));
		write(writer, "</userdata>");
		write(writer, "<userdata name=\"pageSize\">");
		write(writer, String.valueOf(pageSize));
		write(writer, "</userdata>");
		write(writer, "<userdata name=\"currentPage\">");
		write(writer, String.valueOf(pageNo));
		write(writer, "</userdata>");
		write(writer, "<userdata name=\"totalCount\">");
		write(writer, String.valueOf(totalSize));
		write(writer, "</userdata>");
		write(writer, "</rows>");
	}

	public void toHtmlExcel(Writer writer) {
		if (valueList == null) {
			toEmptyXml(writer);
			return;
		}
		// long start = System.currentTimeMillis();
		try {

			Object tmpObject = null;

			DhtmlxGridMgr tmpDhtmlxgridMgr = DhtmlxGridMgr.getMe();
			DhtmlxGridModel tmpModel = tmpDhtmlxgridMgr.getDhtmlxGridModel(gridName);
			List<DhtmlxGridField> tmpList = tmpModel.getItems();
			int fieldsSize = tmpList.size();

			int size = valueList.size();
			DhtmlxGridField tmpField = null;

			write(writer, "<?xml version=\"1.0\"?>");
			write(writer, "<?mso-application progid=\"Excel.Sheet\"?>");
			write(writer, "<Workbook xmlns=\"urn:schemas-microsoft-com:office:spreadsheet\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:x=\"urn:schemas-microsoft-com:office:excel\" xmlns:ss=\"urn:schemas-microsoft-com:office:spreadsheet\" xmlns:html=\"http://www.w3.org/TR/REC-html40\">");
			write(writer, "<DocumentProperties xmlns=\"urn:schemas-microsoft-com:office:office\">");
			write(writer, "<LastAuthor>chenkq</LastAuthor>");
			write(writer, "<Version>12.00</Version>");
			write(writer, "</DocumentProperties>");
			write(writer, "<ExcelWorkbook xmlns=\"urn:schemas-microsoft-com:office:excel\">");
			write(writer, "<WindowHeight>10005</WindowHeight>");
			write(writer, "<WindowWidth>10005</WindowWidth>");
			write(writer, "<WindowTopX>120</WindowTopX>");
			write(writer, "<WindowTopY>135</WindowTopY>");
			write(writer, "<ProtectStructure>False</ProtectStructure>");
			write(writer, "<ProtectWindows>False</ProtectWindows>");
			write(writer, "</ExcelWorkbook>");
			write(writer, "<Styles>");
			write(writer, "<Style ss:ID=\"Default\" ss:Name=\"Normal\">");
			write(writer, "<Alignment ss:Vertical=\"Center\" />");
			write(writer, "<Borders />");
			write(writer, "<Font ss:FontName=\"宋体\" x:CharSet=\"134\" ss:Size=\"12\" />");
			write(writer, "<Interior />");
			write(writer, "<NumberFormat />");
			write(writer, "<Protection />");
			write(writer, "</Style>");
			write(writer, "<Style ss:ID=\"s62\">");
			write(writer, "<Font ss:FontName=\"宋体\" x:CharSet=\"134\" />");
			write(writer, "</Style>");
			write(writer, "<Style ss:ID=\"s63\">");
			write(writer, "<Alignment ss:Horizontal=\"Center\" ss:Vertical=\"Center\" ss:WrapText=\"1\" />");
			write(writer, "<Font ss:FontName=\"宋体\" x:CharSet=\"134\" />");
			write(writer, "</Style>");
			write(writer, "<Style ss:ID=\"s64\">");
			write(writer, "<Alignment ss:Vertical=\"Center\" ss:WrapText=\"1\" />");
			write(writer, "<Font ss:FontName=\"宋体\" x:CharSet=\"134\" />");
			write(writer, "</Style>");
			write(writer, "<Style ss:ID=\"s65\">");
			write(writer, "<Alignment ss:Horizontal=\"Center\" ss:Vertical=\"Center\" ss:WrapText=\"1\" />");
			write(writer, "<Borders>");
			write(writer, "<Border ss:Position=\"Bottom\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\" />");
			write(writer, "<Border ss:Position=\"Left\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\" />");
			write(writer, "<Border ss:Position=\"Right\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\" />");
			write(writer, "<Border ss:Position=\"Top\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\" />");
			write(writer, "</Borders>");
			write(writer, "<Font ss:FontName=\"宋体\" x:CharSet=\"134\" ss:Bold=\"1\" />");
			write(writer, "<Interior ss:Color=\"#99CCFF\" ss:Pattern=\"Solid\" />");
			write(writer, "<NumberFormat />");
			write(writer, "<Protection />");
			write(writer, "</Style>");
			write(writer, "<Style ss:ID=\"s66\">");
			write(writer, "<Alignment ss:Horizontal=\"Left\" ss:Vertical=\"Center\" ss:WrapText=\"1\" />");
			write(writer, "<Borders>");
			write(writer, "<Border ss:Position=\"Bottom\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\" />");
			write(writer, "<Border ss:Position=\"Left\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\" />");
			write(writer, "<Border ss:Position=\"Right\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\" />");
			write(writer, "<Border ss:Position=\"Top\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\" />");
			write(writer, "</Borders>");
			write(writer, "<Font ss:FontName=\"宋体\" x:CharSet=\"134\" />");
			write(writer, "</Style>");
			write(writer, "<Style ss:ID=\"s67\">");
			write(writer, "<Alignment ss:Horizontal=\"Right\" ss:Vertical=\"Center\" ss:WrapText=\"1\" />");
			write(writer, "<Borders>");
			write(writer, "<Border ss:Position=\"Bottom\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\" />");
			write(writer, "<Border ss:Position=\"Left\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\" />");
			write(writer, "<Border ss:Position=\"Right\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\" />");
			write(writer, "<Border ss:Position=\"Top\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\" />");
			write(writer, "</Borders>");
			write(writer, "<Font ss:FontName=\"宋体\" x:CharSet=\"134\" />");
			write(writer, "<NumberFormat ss:Format=\"0.00_ \" />");
			write(writer, "</Style>");
			write(writer, "<Style ss:ID=\"s68\">");
			write(writer, "<Alignment ss:Horizontal=\"Left\" ss:Vertical=\"Center\" ss:WrapText=\"1\" />");
			write(writer, "<Borders>");
			write(writer, "<Border ss:Position=\"Bottom\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\" />");
			write(writer, "<Border ss:Position=\"Left\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\" />");
			write(writer, "<Border ss:Position=\"Right\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\" />");
			write(writer, "<Border ss:Position=\"Top\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\" />");
			write(writer, "</Borders>");
			write(writer, "<Font ss:FontName=\"宋体\" x:CharSet=\"134\" />");
			write(writer, "<NumberFormat ss:Format=\"@\" />");
			write(writer, "</Style>");
			write(writer, "</Styles>");
			write(writer, "<Worksheet ss:Name=\"数据\">");
			write(writer, "<Table ss:ExpandedColumnCount=\"" + (fieldsSize) + "\" ss:ExpandedRowCount=\"" + (size + 1) + "\" x:FullColumns=\"1\" x:FullRows=\"1\" ss:StyleID=\"s62\" ss:DefaultColumnWidth=\"54\" ss:DefaultRowHeight=\"20\">");

			write(writer, "<Column ss:StyleID=\"s62\" ss:AutoFitWidth=\"0\" ss:Width=\"26.25\"/>");
			for (int k = 1; k < fieldsSize; k++) {
				tmpField = tmpList.get(k);
				write(writer, "<Column ss:StyleID=\"s62\" ss:Width=\"" + tmpField.getWidth() + "\"/>");

			}

			write(writer, "<Row ss:AutoFitHeight=\"0\">");
			for (int k = 0; k < fieldsSize; k++) {
				tmpField = tmpList.get(k);
				write(writer, "<Cell ss:StyleID=\"s65\">");
				write(writer, "<Data ss:Type=\"String\">");
				write(writer, tmpField.getDescription());
				write(writer, "</Data>");
				write(writer, "</Cell>");
			}
			write(writer, "</Row>\n");
			for (int i = 0; i < size; i++) {
				write(writer, "<Row ss:AutoFitHeight=\"0\">");
				for (int k = 0; k < fieldsSize; k++) {
					if (k == 0) {
						write(writer, "<Cell ss:StyleID=\"s66\">");
						write(writer, "<Data ss:Type=\"Number\">");
						write(writer, String.valueOf(i + 1));
						write(writer, "</Data>");
						write(writer, "</Cell>");
						continue;
					}

					tmpField = tmpList.get(k);
					tmpObject = getPropertyValue(valueList.get(i), tmpField.getAttribute());

					if (!"".equals(tmpField.getExcelStyle())) {
						write(writer, "<Cell ss:StyleID=\"s68\">");
						write(writer, "<Data ss:Type=\"String\">");
					} else {
						if ("int".equals(tmpField.getSort())) {
							write(writer, "<Cell ss:StyleID=\"s67\">");
							write(writer, "<Data ss:Type=\"Number\">");
						} else {
							write(writer, "<Cell ss:StyleID=\"s66\">");
							write(writer, "<Data ss:Type=\"String\">");
						}
					}
					if (tmpObject == null) {
						write(writer, "");
					} else {
						if ("".equals(tmpField.getFormat())) {
							write(writer, tmpObject.toString());
						} else {
							write(writer, tmpField.getFormater().format(tmpObject));
						}
					}
					write(writer, "</Data>");
					write(writer, "</Cell>");
				}
				write(writer, "</Row>\n");
			}
			write(writer, "</Table>");
			write(writer, "<WorksheetOptions xmlns=\"urn:schemas-microsoft-com:office:excel\">");
			write(writer, "<Unsynced />");
			write(writer, "<Selected />");
			write(writer, "<ProtectObjects>False</ProtectObjects>");
			write(writer, "<ProtectScenarios>False</ProtectScenarios>");
			write(writer, "</WorksheetOptions>");
			write(writer, "</Worksheet>");
			write(writer, "</Workbook>");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		// System.out.println((System.currentTimeMillis() - start)+"豪秒");
	}

	public void toHtmlExcel1(Writer writer) {
		if (valueList == null) {
			toEmptyXml(writer);
			return;
		}
		// long start = System.currentTimeMillis();
		try {
			DhtmlxUserData tmpUserData = null;
			Object tmpObject = null;

			DhtmlxGridMgr tmpDhtmlxgridMgr = DhtmlxGridMgr.getMe();
			DhtmlxGridModel tmpModel = tmpDhtmlxgridMgr.getDhtmlxGridModel(gridName);
			List<DhtmlxGridField> tmpList = tmpModel.getItems();
			int fieldsSize = tmpList.size();

			List<DhtmlxUserData> tmpGlobalList = tmpModel.getGlobalUserDatas();
			int globalDataSize = tmpGlobalList == null ? 0 : tmpGlobalList.size();

			int size = valueList.size();
			DhtmlxGridField tmpField = null;
			int columns = 0;
			write(writer, "<head>");
			write(writer, "<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\">");
			write(writer, "<style>");
			write(writer, "table,td {font-family:宋体;font-size:10pt;}");
			write(writer, ".str {mso-number-format:\"\\@\"}");
			write(writer, "</stype>");
			write(writer, "</head>");
			write(writer, "<table border='1' cellspacing='0' cellpading='0'>");
			write(writer, "<tr>");
			for (int k = 0; k < fieldsSize; k++) {
				tmpField = tmpList.get(k);
				write(writer, "<td ");
				// write(writer,tmpField.getWidth());
				write(writer, " align='center' nowrap height=24 style='background:silver;font-weight:700'>");
				write(writer, tmpField.getDescription());
				write(writer, "</td>");
				columns++;
			}
			write(writer, "</tr>\n");
			for (int i = 0; i < size; i++) {
				write(writer, "<tr>");
				for (int k = 0; k < fieldsSize; k++) {
					tmpField = tmpList.get(k);

					if ("".equals(tmpField.getAttribute())) {
						write(writer, "<td nowrap height=22>");
						write(writer, String.valueOf(i + 1));
						write(writer, "</td>");
					} else {
						tmpObject = getPropertyValue(valueList.get(i), tmpField.getAttribute());
						// <td class='cls-data-td-detail'
						// style='mso-number-format:\@' width='100px'
						// bgcolor='#ffffff' align='left'>10001</td>
						write(writer, "<td ");
						if (!"".equals(tmpField.getExcelStyle())) {
							write(writer, " style='");
							write(writer, tmpField.getExcelStyle());
							write(writer, "'");
						}
						write(writer, " nowrap>");
						if (tmpObject == null) {
							write(writer, "");
						} else {
							if ("".equals(tmpField.getFormat())) {
								write(writer, tmpObject.toString());
							} else {
								write(writer, tmpField.getFormater().format(tmpObject));
							}
						}
						write(writer, "</td>");
					}
				}
				write(writer, "</tr>\n");
			}
			if (globalValue != null) {
				write(writer, "<tr><td colspan='" + columns + "' style='background:#CCFFFF;'>");
				for (int m = 0; m < globalDataSize; m++) {
					tmpUserData = tmpGlobalList.get(m);

					if (!tmpUserData.getAuth().equals("")) {
						tmpObject = getPropertyValue(globalValue, tmpUserData.getAttribute());
					} else {
						tmpObject = getPropertyValue(globalValue, tmpUserData.getAttribute());
					}
					if (tmpObject != null) {
						write(writer, "[");
						write(writer, tmpUserData.getDescription());
						write(writer, ":");
						if ("".equals(tmpUserData.getFormat())) {
							write(writer, tmpObject.toString());
						} else {
							write(writer, tmpUserData.getFormater().format(tmpObject));
						}
						write(writer, "]&nbsp;&nbsp;");
					}

				}
				write(writer, "</td></tr>");
			}
			write(writer, "</table>");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		// System.out.println((System.currentTimeMillis() - start)+"豪秒");
	}

}
