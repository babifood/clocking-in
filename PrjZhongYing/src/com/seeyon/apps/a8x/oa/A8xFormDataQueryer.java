package com.seeyon.apps.a8x.oa;

import java.util.List;
import java.util.Map;

import com.seeyon.apps.a8x.common.exception.ProcessingException;
import com.seeyon.apps.a8x.common.jdbc.DAOContext;
import com.seeyon.apps.a8x.common.jdbc.JdbcWorkUnit;
import com.seeyon.apps.a8x.common.jdbc.JdbcWorker;
import com.seeyon.apps.a8x.oa.dao.A8xFormDaoUtil;
import com.seeyon.apps.a8x.oa.dao.A8xSQLBuilder;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.form.bean.FormBean;
import com.seeyon.ctp.form.service.FormManager;

public class A8xFormDataQueryer {

	private FormManager formManager;

	public A8xFormDataQueryer() {

	}

	public FormManager getFormManager() {
		return formManager;
	}

	public void setFormManager(FormManager formManager) {
		this.formManager = formManager;
	}

	public Map<String, Object> findAsMap(String templateCode, List<String> theDisplayList, Map<String, String> paramMap) throws BusinessException {
		FormBean formBean = formManager.getFormByFormCode(templateCode);
		if (formBean == null) {
			throw new BusinessException("模板编号:[" + templateCode + "]表单,不存在...");
		}

		final A8xSQLBuilder.A8xSelectSqlInfo sqlInfo = A8xSQLBuilder.buildMasterTableSelectSql(formBean, theDisplayList, paramMap);
		try {
			return JdbcWorker.wrapNotInTransaction(new JdbcWorkUnit<Map<String, Object>>() {
				public Map<String, Object> execute(DAOContext daoContext) throws ProcessingException {
					return A8xFormDaoUtil.findAsMap(daoContext, sqlInfo.getSql(), sqlInfo.getParamValueList());
				}
			});
		} catch (ProcessingException e) {
			throw new BusinessException(e.getLocalizedMessage());
		}
	}

	public List<Map<String, Object>> findAsList(String templateCode, List<String> theDisplayList, Map<String, String> paramMap) throws BusinessException {

		FormBean formBean = formManager.getFormByFormCode(templateCode);
		if (formBean == null) {
			throw new BusinessException("模板编号:[" + templateCode + "]表单,不存在...");
		}

		final A8xSQLBuilder.A8xSelectSqlInfo sqlInfo = A8xSQLBuilder.buildMasterTableSelectSql(formBean, theDisplayList, paramMap);
		try {
			return JdbcWorker.wrapNotInTransaction(new JdbcWorkUnit<List<Map<String, Object>>>() {
				public List<Map<String, Object>> execute(DAOContext daoContext) throws ProcessingException {
					return A8xFormDaoUtil.findAsList(daoContext, sqlInfo.getSql(), sqlInfo.getParamValueList());
				}
			});
		} catch (ProcessingException e) {
			throw new BusinessException(e.getLocalizedMessage());
		}
	}

}
