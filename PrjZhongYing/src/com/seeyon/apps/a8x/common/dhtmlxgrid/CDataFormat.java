package com.seeyon.apps.a8x.common.dhtmlxgrid;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

public class CDataFormat extends Format {
	/**
	 * 
	 */
	private static final long serialVersionUID = 210129710218335332L;
	public static final CDataFormat formater = new CDataFormat();

	public CDataFormat() {
		super();
	}

	public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
		if (obj == null)
			return (new StringBuffer());
		String message = (String) obj;
		StringBuffer result = new StringBuffer(message.length() + 50);
		result.append("<![CDATA[");
		result.append(message);
		result.append("]]>");
		return result;
	}

	public Object parseObject(String source, ParsePosition pos) {
		return null;
	}

}
