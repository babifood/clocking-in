package com.seeyon.apps.a8x.common.dhtmlxgrid;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

public class PercentFormat extends Format {

	private static final long serialVersionUID = -3353640682640726509L;

	public static final PercentFormat formater = new PercentFormat();

	public PercentFormat() {
		super();
	}

	public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
		StringBuffer tmpBufer = new StringBuffer(10);
		if (obj == null) {
			tmpBufer.append("0%");
		} else {
			Double tmp = (Double) obj;
			tmpBufer.append(FormatterCache.getFormatter("##0.##").format(tmp.doubleValue() * 100) + "%");
		}
		return tmpBufer;
	}

	public Object parseObject(String source, ParsePosition pos) {
		return null;
	}

}
