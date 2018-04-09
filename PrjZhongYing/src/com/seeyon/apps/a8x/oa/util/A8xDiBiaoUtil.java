package com.seeyon.apps.a8x.oa.util;

import java.util.List;

import com.seeyon.ctp.form.bean.FormBean;
import com.seeyon.ctp.form.bean.FormFieldBean;
import com.seeyon.ctp.form.bean.FormTableBean;

public class A8xDiBiaoUtil {

	private A8xDiBiaoUtil() {

	}

	/**
	 * 根据infopath文件的控件名，获取表名，主要用于查找重复表的表名
	 * 
	 * @param theDisplayName
	 * @param formBean
	 * @return
	 */
	public static String getTableName(String theDisplayName, FormBean formBean) {
		FormFieldBean tmpFormFieldBean = formBean.getMasterTableBean().getFieldBeanByDisplay(theDisplayName);
		if (tmpFormFieldBean != null) {
			return formBean.getMasterTableBean().getTableName();
		}
		List<FormTableBean> tmpList = formBean.getSubTableBean();
		int size = tmpList == null ? 0 : tmpList.size();
		FormTableBean tmpFormTableBean = null;
		for (int i = 0; i < size; i++) {
			tmpFormTableBean = tmpList.get(i);
			if (tmpFormTableBean.getFieldBeanByDisplay(theDisplayName) != null) {
				return tmpFormTableBean.getTableName();
			}
		}

		return null;

	}
}
