package com.seeyon.apps.a8x.common.dhtmlxgrid;

import java.io.Writer;

public final class DhtmlxErrorXml {
	private DhtmlxErrorXml() {

	}

	public static void toExceptionXml(Writer writer, Exception e) {
		try {
			writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
			writer.write("<rows>");
			writer.write("<userdata name=\"error\">");
			writer.write(e.getLocalizedMessage());
			writer.write("</userdata>");
			writer.write("</rows>");
		} catch (Exception ew) {
			ew.printStackTrace();
		}
	}

	public static void toExceptionXml(Writer writer, String msg) {
		try {
			writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
			writer.write("<rows>");
			writer.write("<userdata name=\"error\">");
			writer.write(msg);
			writer.write("</userdata>");
			writer.write("</rows>");
		} catch (Exception ew) {
			ew.printStackTrace();
		}
	}
}
