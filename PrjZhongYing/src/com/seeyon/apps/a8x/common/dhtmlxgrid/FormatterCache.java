package com.seeyon.apps.a8x.common.dhtmlxgrid;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public final class FormatterCache {

	public final static Map<String, Format> Map_Formatter = new HashMap<String, Format>(12);
	static {
		Format tmpFormat = null;
		String style = "#,##0.00";
		tmpFormat = NumberFormat.getNumberInstance();
		((DecimalFormat) tmpFormat).applyPattern(style);
		Map_Formatter.put(style, tmpFormat);

		style = "#,##0.##";
		tmpFormat = NumberFormat.getNumberInstance();
		((DecimalFormat) tmpFormat).applyPattern(style);
		Map_Formatter.put(style, tmpFormat);

		style = "##0.##";
		tmpFormat = NumberFormat.getNumberInstance();
		((DecimalFormat) tmpFormat).applyPattern(style);
		Map_Formatter.put(style, tmpFormat);

		style = "%";
		tmpFormat = new PercentFormat();
		Map_Formatter.put(style, tmpFormat);

		style = "html";
		tmpFormat = new HtmlFormat();
		Map_Formatter.put(style, tmpFormat);

		style = "cdata";
		tmpFormat = new CDataFormat();
		Map_Formatter.put(style, tmpFormat);
	}

	private FormatterCache() {

	}

	public static Format getFormatter(String theStyle) {
		Format tmpFormat = null;
		if (theStyle != null && !"".equals(theStyle)) {
			tmpFormat = Map_Formatter.get(theStyle);
			if (tmpFormat == null) {
				if (theStyle.indexOf("%") != -1) {
					tmpFormat = new PercentFormat();
					Map_Formatter.put(theStyle, tmpFormat);
				} else if (theStyle.indexOf("#") != -1) {
					tmpFormat = NumberFormat.getNumberInstance();
					((DecimalFormat) tmpFormat).applyPattern(theStyle);
					Map_Formatter.put(theStyle, tmpFormat);
				} else if (theStyle.indexOf("-") != -1 || theStyle.indexOf(":") != -1) {
					tmpFormat = new SimpleDateFormat(theStyle);
					((SimpleDateFormat) tmpFormat).applyPattern(theStyle);
				} else {
					throw new IllegalStateException(theStyle + " 格式无效");
				}
			}
		}
		return tmpFormat;
	}

}
