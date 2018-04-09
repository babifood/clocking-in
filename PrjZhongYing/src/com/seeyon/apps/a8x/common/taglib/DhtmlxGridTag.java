package com.seeyon.apps.a8x.common.taglib;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.seeyon.apps.a8x.common.dhtmlxgrid.DhtmlxGridMgr;
import com.seeyon.apps.a8x.common.dhtmlxgrid.DhtmlxGridModel;
import com.seeyon.apps.a8x.common.dhtmlxgrid.DhtmlxUserData;

public class DhtmlxGridTag extends TagSupport {

	private static final long serialVersionUID = -6338990206672730714L;

	private String varGrid;
	private String idGrid;
	private String name;

	private boolean multiselect = false;
	private boolean enableTooltips = false;
	private boolean sort = true;
	private boolean colors = false;
	private boolean enableColumnMove = false;
	private boolean enableSmartRendering = true;
	private boolean enableMultiline = false;

	private boolean edit = false;
	private int splitAt = 0;

	public void setVarGrid(String varGrid) {
		this.varGrid = varGrid;
	}

	public void setIdGrid(String idGrid) {
		this.idGrid = idGrid;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMultiselect(boolean multiselect) {
		this.multiselect = multiselect;
	}

	public void setEnableTooltips(boolean enableTooltips) {
		this.enableTooltips = enableTooltips;
	}

	public void setSort(boolean sort) {
		this.sort = sort;
	}

	public void setColors(boolean colors) {
		this.colors = colors;
	}

	public void setEnableColumnMove(boolean enableColumnMove) {
		this.enableColumnMove = enableColumnMove;
	}

	public void setEnableSmartRendering(boolean enableSmartRendering) {
		this.enableSmartRendering = enableSmartRendering;
	}

	public boolean isEnableMultiline() {
		return enableMultiline;
	}

	public void setEnableMultiline(boolean enableMultiline) {
		this.enableMultiline = enableMultiline;
	}

	public void setSplitAt(int splitAt) {
		this.splitAt = splitAt;
	}

	public boolean isEdit() {
		return edit;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}

	public boolean isEnableSmartRendering() {
		return enableSmartRendering;
	}

	public int doStartTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			DhtmlxGridMgr tmpDhtmlxgridMgr = DhtmlxGridMgr.getMe();
			DhtmlxGridModel tmpModel = tmpDhtmlxgridMgr.getDhtmlxGridModel(name);
			if (tmpModel != null) {
				HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
				out.print(varGrid + "=new dhtmlXGridObject('" + idGrid + "');\n");
				out.print(varGrid + ".setImagePath('" + request.getContextPath() + "/apps_res/a8x/dhtmlx/imgs/');\n");

				out.print(varGrid + ".setHeader('" + tmpModel.getHeaders() + "');\n");
				out.print(varGrid + ".setInitWidths('" + tmpModel.getWidths() + "');\n");
				out.print(varGrid + ".setColTypes('" + tmpModel.getTypes() + "');\n");
				out.print(varGrid + ".setColAlign('" + tmpModel.getAligns() + "');\n");
				if (edit) {
					out.print(varGrid + ".setColumnIds('" + tmpModel.getColumnids() + "');\n");
				}
				if (sort) {
					out.print(varGrid + ".setColSorting('" + tmpModel.getSorts() + "');\n");
				}
				if (colors) {
					out.print(varGrid + ".setColumnColor('" + tmpModel.getColors() + "');\n");
				} else {
					out.print(varGrid + ".setColumnColor('#efefef');\n");
				}
				if (!"".equals(tmpModel.getFooters())) {
					out.print(varGrid + ".attachFooter(\"" + tmpModel.getFooters() + "\");\n");
				}

				if (enableColumnMove) {
					out.print(varGrid + ".enableColumnMove(" + enableColumnMove + ")\n");
				}
				if (enableMultiline) {
					out.print(varGrid + ".enableMultiline(" + enableMultiline + ")\n");
				}
				if (edit) {
					List<DhtmlxUserData> tmpList = tmpModel.getUserDatas();
					if (tmpList != null) {
						int size = tmpList.size();
						DhtmlxUserData tmp = null;
						for (int i = 0; i < size; i++) {
							tmp = tmpList.get(i);
							if (!"".equals(tmp.getColumnID())) {
								out.print(varGrid + ".setUserData4ColumnID(\"" + tmp.getName() + "\",\"" + tmp.getColumnID() + "\");\n");
							}
						}
					}
				}

			} else {
				throw new JspException(varGrid + "不存在....");
			}
		} catch (Throwable e) {
			throw new JspException(e);
		}
		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			// out.print(varGrid + ".enableAlterCss(\"even\",\"uneven\");\n");
			out.print(varGrid + ".setMultiselect('" + multiselect + "');\n");
			out.print(varGrid + ".enableTooltips('" + enableTooltips + "');\n");

			out.print(varGrid + ".setSkin(\"dhx_web\");\n");
			out.print(varGrid + ".init();\n");
			if (enableSmartRendering) {
				out.print(varGrid + ".enableSmartRendering(" + enableSmartRendering + ");\n");
			}
			if (splitAt > 0) {
				out.print(varGrid + ".splitAt(" + splitAt + ");");
			}
		} catch (Throwable e) {
			throw new JspException(e);
		}
		return (EVAL_PAGE);
	}
}