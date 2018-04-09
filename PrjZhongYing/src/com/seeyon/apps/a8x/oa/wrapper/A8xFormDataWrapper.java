package com.seeyon.apps.a8x.oa.wrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.common.ctpenumnew.manager.EnumManager;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.common.po.ctpenumnew.CtpEnumItem;
import com.seeyon.ctp.form.bean.FormBean;
import com.seeyon.ctp.form.bean.FormDataMasterBean;
import com.seeyon.ctp.form.bean.FormDataSubBean;
import com.seeyon.ctp.form.bean.FormFieldBean;
import com.seeyon.ctp.form.bean.FormTableBean;

public class A8xFormDataWrapper {

	// 表单定义
	private FormBean formBean;

	// 表单数据
	private FormDataMasterBean masterBean;

	// 是否读取枚举数据
	private boolean isReadEnumData = false;

	// 枚举字段Map,记录枚举字段的控件名称，不是数据库字段名
	private Map<String, Boolean> enumFieldMap;

	private EnumManager enumManager;

	public A8xFormDataWrapper(FormBean formBean, FormDataMasterBean masterBean) {
		this(formBean, masterBean, false);
	}

	public A8xFormDataWrapper(FormBean formBean, FormDataMasterBean masterBean, boolean isReadEnumData) {
		this.formBean = formBean;
		this.masterBean = masterBean;
		this.isReadEnumData = isReadEnumData;
	}

	public FormBean getFormBean() {
		return formBean;
	}

	public FormDataMasterBean getFormDataMasterBean() {
		return masterBean;
	}

	/**
	 * 获取主表-名称
	 */
	public String getMasterTableName() {
		String temp = null;
		if (formBean != null) {
			temp = formBean.getMasterTableBean().getTableName();
		}
		return temp;
	}

	/**
	 * 控件名-字段名对照
	 */
	public Map<String, String> getFieldDisplayMap() {
		Map<String, String> tmp = null;
		if (formBean != null) {
			tmp = formBean.getAllFieldDisplayMap();
		} else {
			tmp = new HashMap<String, String>(1);
		}
		return tmp;
	}

	/**
	 * 字段名-控件名-对照
	 */
	public Map<String, String> getFieldNameMap() {
		Map<String, String> tmp = null;
		if (formBean != null) {
			tmp = formBean.getAllFieldNameMap();
		} else {
			tmp = new HashMap<String, String>(1);
		}
		return tmp;
	}

	/**
	 * 数据库字段名与值对照Map
	 */
	public Map<String, Object> getMasterDataMap1() {
		Map<String, Object> tmp = null;
		if (masterBean != null) {
			tmp = masterBean.getRowData();
		}
		return tmp;
	}

	/**
	 * 获取重复表数据List 数据库字段名与值对照Map
	 */
	public List<Map<String, Object>> getChildDataList(int index) {
		List<FormTableBean> tmpFormTableBeanList = formBean.getSubTableBean();
		int size = tmpFormTableBeanList == null ? -1 : tmpFormTableBeanList.size();
		if (index >= size) {
			return null;
		}
		String childTablename = tmpFormTableBeanList.get(index).getTableName();
		if (childTablename == null) {
			return null;
		}
		List<Map<String, Object>> tmpList = null;
		List<FormDataSubBean> subList = masterBean.getSubData(childTablename);
		if (subList != null) {
			size = subList.size();
			tmpList = new ArrayList<Map<String, Object>>(size);
			FormDataSubBean tmpFormDataSubBean = null;
			for (int i = 0; i < size; i++) {
				tmpFormDataSubBean = subList.get(i);
				tmpList.add(tmpFormDataSubBean.getRowData());
			}
		}
		return tmpList;
	}

	/**
	 * 中文控件名与值对照Map
	 */
	public Map<String, Object> getMasterDataMap2() {
		return convertRowData2Map(getMasterDataMap1());
	}

	/**
	 * 获取重复表数据List 中文控件名与值对照Map
	 * 
	 * @param theSubTableName
	 *            子表名称
	 * @return
	 */
	public List<Map<String, Object>> getChildDataList2(int index) {
		List<FormTableBean> tmpFormTableBeanList = formBean.getSubTableBean();
		int size = tmpFormTableBeanList == null ? -1 : tmpFormTableBeanList.size();
		if (index >= size) {
			return null;
		}
		String childTablename = tmpFormTableBeanList.get(index).getTableName();
		List<Map<String, Object>> tmpList = null;
		List<FormDataSubBean> subList = masterBean.getSubData(childTablename);
		if (subList != null) {
			size = subList.size();
			tmpList = new ArrayList<Map<String, Object>>(size);
			FormDataSubBean tmpFormDataSubBean = null;
			for (int i = 0; i < size; i++) {
				tmpFormDataSubBean = subList.get(i);
				tmpList.add(convertRowData2Map(tmpFormDataSubBean.getRowData()));
			}
		}
		return tmpList;
	}

	private Map<String, Object> convertRowData2Map(Map<String, Object> theRowData) {
		if (theRowData == null) {
			return null;
		}

		// 检查枚举情况
		markEnumField();

		Map<String, Object> tmp = new HashMap<String, Object>(theRowData.size());
		Map<String, String> tmpFieldNameMap = getFieldNameMap();
		Object value = null;
		String displayName = null;
		CtpEnumItem tmpCtpEnumItem = null;
		for (String key : theRowData.keySet()) {
			value = theRowData.get(key);
			// 如果值为null，不管
			if (value == null) {
				continue;
			}
			// 根据数据库字段名获取表单的控件名称，一般来讲控件名称不会为NULL
			displayName = tmpFieldNameMap.get(key);
			if (displayName == null) {
				tmp.put(key, value);
				continue;
			}
			// 先记录,保证建立映射
			tmp.put(displayName, value);
			// 如果是枚举字段，进一步处理，获取枚举的ID 和 Code
			if (enumFieldMap.get(displayName) != null) {
				Long x = null;
				try {
					x = Long.parseLong(value.toString());
				} catch (Exception e) {

				}
				if (x != null) {
					tmpCtpEnumItem = getCtpEnumItem(x);
					if (tmpCtpEnumItem != null) {
						// 记录枚举的ID值
						tmp.put(displayName + "ID", tmpCtpEnumItem.getId());
						// 记录枚举的显示值
						String showValue = tmpCtpEnumItem.getShowvalue();
						tmp.put(displayName, showValue == null ? "" : showValue.trim());
						// 记录枚举的code
						String enumValue = tmpCtpEnumItem.getEnumvalue();
						tmp.put(displayName + "CODE", enumValue == null ? "" : enumValue.trim());
					}
				}
			}

		}
		return tmp;
	}

	/**
	 * 获取表单的枚举字段
	 */
	private void markEnumField() {
		if (isReadEnumData) {
			// 获取表单元数据结构信息
			if (enumFieldMap == null) {
				List<FormFieldBean> tmpList = formBean.getAllFieldBeans();
				int size = tmpList == null ? 0 : tmpList.size();
				enumFieldMap = new HashMap<String, Boolean>(size + 1);
				FormFieldBean tmpFormFieldBean = null;
				for (int i = 0; i < size; i++) {
					tmpFormFieldBean = tmpList.get(i);
					if (tmpFormFieldBean.getEnumId() != 0L) {
						enumFieldMap.put(tmpFormFieldBean.getDisplay(), Boolean.TRUE);
					}
				}
			}
		}
	}

	protected CtpEnumItem getCtpEnumItem(Long itemId) {
		CtpEnumItem tmpCtpEnumItem = null;
		try {
			tmpCtpEnumItem = getSafeEnumManager().getCacheEnumItem(itemId);
		} catch (BusinessException e) {
			// e.printStackTrace();
		}
		return tmpCtpEnumItem;
	}

	private EnumManager getSafeEnumManager() {
		if (enumManager == null) {
			enumManager = (EnumManager) AppContext.getBean("enumManagerNew");
		}
		return enumManager;
	}

}
