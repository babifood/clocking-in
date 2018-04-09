package com.seeyon.apps.a8x.common.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class DhtmlxGridEventTag extends TagSupport {

	private static final long serialVersionUID = 5023574144740018111L;

	private String varGrid;
	private String eventName;
	private String eventMethod;

	public void setVarGrid(String varGrid) {
		this.varGrid = varGrid;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public void setEventMethod(String eventMethod) {
		this.eventMethod = eventMethod;
	}

	public int doEndTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			out.print(varGrid + ".attachEvent('" + eventName + "'," + eventMethod + ");\n");
		} catch (Throwable e) {
			throw new JspException(e);
		}
		return (EVAL_PAGE);
	}
}