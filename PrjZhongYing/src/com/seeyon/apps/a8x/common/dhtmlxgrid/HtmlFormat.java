package com.seeyon.apps.a8x.common.dhtmlxgrid;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

public class HtmlFormat extends Format {
	private static final long serialVersionUID = -5949812487271701630L;
	public static final HtmlFormat formater = new HtmlFormat();

	public HtmlFormat() {
		super();
	}

	public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
		String message = (String) obj;
		if (message == null)
			return (null);

		char content[] = new char[message.length()];
		message.getChars(0, message.length(), content, 0);
		StringBuffer result = new StringBuffer(content.length + 50);
		result.append("<![CDATA[");
		for (int i = 0; i < content.length; i++) {
			switch (content[i]) {
			case '\r':
				result.append(" ");
				break;
			case '\t':
				result.append("   ");
				break;
			case '\n':
				result.append("&lt;br&gt;");
				break;
			case '<':
				result.append("&lt;");
				break;
			case '>':
				result.append("&gt;");
				break;
			case '&':
				result.append("&amp;");
				break;
			case '"':
				result.append("&quot;");
				break;
			default:
				result.append(content[i]);
			}
		}
		result.append("]]>");
		return result;
	}

	public Object parseObject(String source, ParsePosition pos) {
		return null;
	}

}
