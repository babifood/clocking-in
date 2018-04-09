package com.seeyon.apps.a8x.oa.manager;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.seeyon.apps.a8x.common.exception.ProcessingException;
import com.seeyon.apps.a8x.common.jdbc.DAOContext;
import com.seeyon.apps.a8x.common.jdbc.JdbcWorkUnit;
import com.seeyon.apps.a8x.common.jdbc.JdbcWorker;
import com.seeyon.apps.a8x.common.parameter.SqlParameter;
import com.seeyon.apps.a8x.oa.dao.A8xContentAllDao;
import com.seeyon.apps.a8x.oa.dao.A8xDiBiaoDao;
import com.seeyon.apps.a8x.oa.dao.A8xFormDaoUtil;
import com.seeyon.apps.a8x.oa.entity.A8xContentAll;
import com.seeyon.apps.a8x.oa.entity.A8xMasterDataRow;
import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.common.ctpenumnew.manager.EnumManager;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.common.po.ctpenumnew.CtpEnumBean;
import com.seeyon.ctp.common.po.ctpenumnew.CtpEnumItem;
import com.seeyon.ctp.form.bean.FormBean;
import com.seeyon.ctp.form.bean.FormFieldBean;
import com.seeyon.ctp.form.bean.FormTableBean;
import com.seeyon.ctp.form.modules.serialNumber.SerialNumberManager;
import com.seeyon.ctp.form.service.FormManager;
import com.seeyon.ctp.form.util.Enums;
import com.seeyon.ctp.util.UUIDLong;

/**
 * 适用没有重表，只有主表的情况
 * 
 */
public class A8xDiBiaoSingleService {

	private FormManager formManager;
	private SerialNumberManager serialNumberManager;
	private EnumManager enumManagerNew;

	public A8xDiBiaoSingleService() {

	}

	public FormManager getFormManager() {
		return formManager;
	}

	public void setFormManager(FormManager formManager) {
		this.formManager = formManager;
	}

	public SerialNumberManager getSerialNumberManager() {
		return serialNumberManager;
	}

	public void setSerialNumberManager(SerialNumberManager serialNumberManager) {
		this.serialNumberManager = serialNumberManager;
	}

	public EnumManager getEnumManagerNew() {
		return enumManagerNew;
	}

	public void setEnumManagerNew(EnumManager enumManagerNew) {
		this.enumManagerNew = enumManagerNew;
	}

	public Long saveFormData(String templateCode, Map<String, Object> data, String[] valiFieldAry) throws BusinessException {
		FormBean formBean = formManager.getFormByFormCode(templateCode);
		if (formBean == null) {
			throw new BusinessException("模板编号:[" + templateCode + "]表单,不存在...");
		}
		Long oldKey = ckeckDataExist(formBean, data, valiFieldAry);
		Long masterId = null;
		// 表单数据
		try {
			A8xMasterDataRow masterData = buildFormDataMasterBean(data, formBean, oldKey);
			A8xContentAll content = null;
			if (oldKey == null) {
				masterData.setId(UUIDLong.longUUID());
				masterId = masterData.getId();
				content = new A8xContentAll();
				content.copy4New(formBean, AppContext.currentUserId(), masterId);
			}
			// 保存或更新数据
			saveOrUpdateFormData(formBean, masterData, content);
			masterId = masterData.getId();
		} catch (Exception e1) {
			throw new BusinessException("保存数据失败..." + e1.getLocalizedMessage());
		}

		return masterId;
	}

	private A8xMasterDataRow buildFormDataMasterBean(Map<String, Object> data, FormBean formBean, Long oldKey) throws BusinessException {
		A8xMasterDataRow newData = new A8xMasterDataRow();
		newData.setState(1);

		if (oldKey == null) {
			newData.initCreateFieldValue();
		} else {
			// 先读取表的原始数据
			Map<String, Object> oldData = selectDataByMasterId(oldKey, formBean.getMasterTableBean().getTableName());
			newData.putAll(oldData);
		}

		// 数据转换
		FormTableBean masterTableBean = formBean.getMasterTableBean();
		Iterator<Entry<String, Object>> iter = data.entrySet().iterator();
		Entry<String, Object> entry = null;
		Object value = null;
		// 控件名称
		String displayName = null;
		while (iter.hasNext()) {
			entry = iter.next();
			displayName = entry.getKey();
			value = entry.getValue();
			if (value != null) {
				FormFieldBean fieldBean = masterTableBean.getFieldBeanByDisplay(displayName);
				if (fieldBean != null) {
					newData.put(fieldBean.getName(), value);
				}
			}
		}
		return newData;
	}

	private int saveOrUpdateFormData(final FormBean formBean, final A8xMasterDataRow a8xDataRowMap, final A8xContentAll content) throws BusinessException {
		try {
			return JdbcWorker.wrapInTransaction(new JdbcWorkUnit<Integer>() {
				public Integer execute(DAOContext daoContext) throws ProcessingException {
					if (content != null) {
						// 创建CTP_CONTENT_ALL表记录
						A8xContentAllDao contentDao = new A8xContentAllDao(daoContext);
						contentDao.create(content);
					}
					A8xDiBiaoDao dao = new A8xDiBiaoDao(daoContext);
					dao.setFormBean(formBean);
					return dao.insertOrUpdateMasterData(a8xDataRowMap);
				}
			});
		} catch (ProcessingException e) {
			throw new BusinessException(e.getLocalizedMessage());
		}

	}

	/**
	 * 获取枚举ID
	 * 
	 * @param id
	 * @param value
	 * @param showValue
	 * @return
	 * @throws BusinessException
	 */
	public String getCtpEnumId(Long id, String value, String showValue) throws BusinessException {
		long tempID = -1l;
		try {
			tempID = Long.parseLong(value);
		} catch (Exception e) {

		}
		if (Math.abs(tempID) > 10000000) {
			return String.valueOf(tempID);
		}

		CtpEnumItem tmpCtpEnumItem = null;
		if (value != null) {
			tmpCtpEnumItem = getItemByValue(id, value);
		}
		if (tmpCtpEnumItem == null) {
			if (showValue != null) {
				tmpCtpEnumItem = getItemByShowValue(id, showValue);
			}
		}
		if (tmpCtpEnumItem != null) {
			return String.valueOf(tmpCtpEnumItem.getId());
		}
		return null;
	}

	private CtpEnumItem getItemByShowValue(Long id, String showValue) throws BusinessException {
		CtpEnumItem returnValue = null;
		CtpEnumBean ceb = enumManagerNew.getEnum(id);
		List<CtpEnumItem> list = ceb.getItems();
		if (null != list) {
			for (CtpEnumItem cei : list) {
				if (showValue.equals(cei.getShowvalue())) {
					returnValue = cei;
					break;
				}
			}
		}
		return returnValue;
	}

	private CtpEnumItem getItemByValue(Long id, String value) throws BusinessException {
		CtpEnumItem returnValue = null;
		CtpEnumBean ceb = enumManagerNew.getEnum(id);
		List<CtpEnumItem> list = ceb.getItems();
		if (null != list) {
			for (CtpEnumItem cei : list) {
				if (value.equals(cei.getEnumvalue())) {
					returnValue = cei;
					break;
				}
			}
		}
		return returnValue;
	}

	private Long ckeckDataExist(FormBean formBean, Map<String, Object> data, String[] validFieldAry) throws BusinessException {
		Long key = null;
		if (validFieldAry != null && validFieldAry.length > 0) {
			Map<String, String> valiDataMap = new LinkedHashMap<String, String>();
			for (String field : validFieldAry) {
				if (StringUtils.isNotBlank(field)) {
					Object tmp = data.get(field);
					if (tmp != null) {
						valiDataMap.put(field, tmp.toString());
					}
				}
			}
			try {
				key = getMasterDataId(formBean, valiDataMap);
			} catch (SQLException e) {
				throw new BusinessException("判断数据唯一性错误.." + e.getLocalizedMessage());
			}
		}
		return key;
	}

	private Long getMasterDataId(final FormBean formBean, final Map<String, String> validDataMap) throws BusinessException, SQLException {
		try {
			return JdbcWorker.wrapNotInTransaction(new JdbcWorkUnit<Long>() {
				public Long execute(DAOContext daoContext) throws ProcessingException {
					return A8xFormDaoUtil.getMasterDataId(daoContext,formBean, validDataMap);
				}
			});
		} catch (ProcessingException e) {
			return null;
		}
	}

	public Map<String, Object> selectDataByMasterId(final long id, String theTablename) throws BusinessException {

		final String sql = "select * from " + theTablename + " where " + Enums.SubTableField.id.getKey() + "=? order by sort asc";
		try {
			return JdbcWorker.wrapNotInTransaction(new JdbcWorkUnit<Map<String, Object>>() {
				public Map<String, Object> execute(DAOContext daoContext) throws ProcessingException {
					return A8xFormDaoUtil.findAsMap(daoContext, sql, new SqlParameter(id));
				}
			});
		} catch (ProcessingException e) {
			throw new BusinessException(e.getLocalizedMessage());
		}

	}

}
