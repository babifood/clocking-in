package com.seeyon.apps.a8x.oa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.seeyon.apps.a8x.oa.dao.A8xFormDataReadDao;
import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.common.ctpenumnew.manager.EnumManager;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.common.po.ctpenumnew.CtpEnumItem;
import com.seeyon.ctp.form.bean.FormBean;
import com.seeyon.ctp.form.bean.FormFieldBean;
import com.seeyon.ctp.form.service.FormCacheManager;
import com.seeyon.ctp.form.service.FormManager;

public class A8xFormDataReader {

	// 数据ID
	private Long formRecordId;

	// 表单定义
	private FormBean formBean;

	// 表单-主表数据
	private Map<String, Object> masterData;

	// 表单-子表数据
	private Map<Integer, List<Map<String, Object>>> childDataMap;

	// 枚举字段Map,记录枚举字段的控件名称，不是数据库字段名
	private Map<String, Boolean> enumFieldMap;

	// 是否读取枚举数据
	private boolean isReadEnumData;

	private EnumManager enumManager;

	public A8xFormDataReader() {

	}

	public A8xFormDataReader(boolean isReadEnumData) {
		this.isReadEnumData = isReadEnumData;
	}

	public FormBean getFormBean() {
		return formBean;
	}

	public Long getFormRecordId() {
		return formRecordId;
	}

	/**
	 * 
	 * @param formId
	 *            表单定义的唯一标识
	 * @param formRecordId
	 *            表单数据Key
	 * @return
	 */
	public boolean loadFormData(long formId, long formRecordId) {
		if (formBean == null) {
			// 获取表单定义
			FormCacheManager formCacheManager = (FormCacheManager) AppContext.getBean("formCacheManager");
			formBean = formCacheManager.getForm(formId);
		}
		if (formBean == null) {
			return false;
		}
		return loadFormData(formBean, formRecordId);
	}

	/**
	 * 本方法适用于无流程表单
	 * 
	 * @param formCode
	 *            表单的模板编号
	 * @param formRecordId
	 *            表单数据Key
	 * @return
	 */
	public boolean loadFormData(String formCode, long formRecordId) {
		if (formBean == null) {
			try {
				// 获取表单定义
				FormManager formManager = (FormManager) AppContext.getBean("formManager");
				formBean = formManager.getFormByFormCode(formCode);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (formBean == null) {
			return false;
		}
		return loadFormData(formBean, formRecordId);
	}

	public boolean loadFormData(FormBean theFormBean, long theFormRecordId) {
		this.formRecordId = theFormRecordId;
		this.formBean = theFormBean;
		if (formBean == null) {
			return false;
		}
		boolean flag = true;
		// 查询表单的业务数据
		A8xFormDataReadDao formDataDAO = new A8xFormDataReadDao(formBean, formRecordId);
		try {
			masterData = formDataDAO.loadMasterTableData();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 获取主表数据Map 数据库字段名与值对照Map
	 */
	public Map<String, Object> getMasterDataMap() {
		return masterData;
	}

	/**
	 * 获取重复表数据List 数据库字段名与值对照Map
	 */
	public List<Map<String, Object>> getChildDataList(int index) {
		if (formBean == null) {
			return null;
		}

		List<Map<String, Object>> tmpList = null;
		if (childDataMap != null) {
			tmpList = childDataMap.get(index);
		} else {
			childDataMap = new HashMap<Integer, List<Map<String, Object>>>(3);
		}

		// 第一次取子表数据
		if (tmpList == null) {
			A8xFormDataReadDao formDataDAO = new A8xFormDataReadDao(formBean, formRecordId);
			try {
				tmpList = formDataDAO.getChildTableData(index);
			} catch (Exception e) {

			}
		}

		if (tmpList != null && !tmpList.isEmpty()) {
			childDataMap.put(index, tmpList);
		}
		return tmpList;
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
	 * 中文控件名与值对照Map
	 */
	public Map<String, Object> getMasterDataMap2() {
		if (masterData != null) {
			return convertRowData2Map(masterData);
		} else {
			return null;
		}
	}

	/**
	 * 获取重复表数据List 中文控件名与值对照Map
	 * 
	 * @param theSubTableName
	 *            子表名称
	 * @return
	 */
	public List<Map<String, Object>> getChildDataList2(int index) {
		List<Map<String, Object>> tmpList = getChildDataList(index);
		if (tmpList == null || tmpList.isEmpty()) {
			return null;
		}
		List<Map<String, Object>> newList = null;
		int size = tmpList.size();
		newList = new ArrayList<Map<String, Object>>(size);
		for (int i = 0; i < size; i++) {
			newList.add(convertRowData2Map(tmpList.get(i)));
		}
		return newList;
	}

	private Map<String, Object> convertRowData2Map(Map<String, Object> theRowData) {
		if (theRowData == null) {
			return null;
		}

		// 初始化枚举字段
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
						//System.out.println(tmpFormFieldBean.getDisplay());
						enumFieldMap.put(tmpFormFieldBean.getDisplay(), Boolean.TRUE);
					}
				}
			}
		}
	}

	private CtpEnumItem getCtpEnumItem(Long itemId) {
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
