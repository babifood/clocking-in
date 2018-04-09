package com.seeyon.apps.a8x.common.dhtmlxgrid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.Writer;

public class UtilHtml {

	public static String getContent(String content) {
		StringBuffer sb = new StringBuffer();
		StringReader sr = new StringReader(content);
		BufferedReader reader = new BufferedReader(sr);
		try {
			String s = reader.readLine();
			if (s != null)
				sb.append(encode(s));
			while ((s = reader.readLine()) != null) {
				sb.append("<br>");
				sb.append(encode(s));
			}
		} catch (Exception e) {
			return content;
		}
		return sb.toString();
	}

	public static void printContent(String content, Writer out) throws IOException {
		StringReader sr = new StringReader(content);
		BufferedReader reader = new BufferedReader(sr);
		String s = null;
		while ((s = reader.readLine()) != null) {
			out.write(encode(s));
			out.write("<br>");
		}
	}

	public static String encode(String SrcStr) {
		char[] charA = SrcStr.toCharArray();
		StringBuffer EncodeStr = new StringBuffer("");

		for (int i = 0; i < charA.length; i++) {
			boolean FindChar = false;
			if (charA[i] == '&') {
				FindChar = true;
				EncodeStr.append("&amp;");
			}
			if (charA[i] == '<') {
				FindChar = true;
				EncodeStr.append("&lt;");
			}
			if (charA[i] == '>') {
				FindChar = true;
				EncodeStr.append("&gt;");
			}
			if (charA[i] == ' ') {
				FindChar = true;
				EncodeStr.append("&nbsp;&nbsp;");
			}
			if (!FindChar)
				EncodeStr.append(charA[i]);
		}
		return EncodeStr.toString();
	}
}
