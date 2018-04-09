package com.seeyon.apps.a8x.oa.dao;

import java.util.List;
import java.util.Map;

import com.seeyon.apps.a8x.common.exception.ProcessingException;
import com.seeyon.apps.a8x.common.jdbc.DAOContext;
import com.seeyon.apps.a8x.common.jdbc.JdbcWorkUnit;
import com.seeyon.apps.a8x.common.jdbc.JdbcWorker;
import com.seeyon.apps.a8x.common.parameter.SqlParameter;
import com.seeyon.apps.a8x.oa.util.A8xDiBiaoUtil;
import com.seeyon.ctp.form.bean.FormBean;
import com.seeyon.ctp.form.bean.FormTableBean;

public class A8xFormDataReadDao {

	private Long formRecordId;

	// 表单定义
	private FormBean formBean;

	public A8xFormDataReadDao(FormBean formBean, Long formRecordId) {
		this.formBean = formBean;
		this.formRecordId = formRecordId;
	}

	public Map<String, Object> loadMasterTableData() throws ProcessingException {
		final String sql = "select * from " + formBean.getMasterTableBean().getTableName() + " where ID=?";
		return JdbcWorker.wrapNotInTransaction(new JdbcWorkUnit<Map<String, Object>>() {
			public Map<String, Object> execute(DAOContext daoContext) throws ProcessingException {

				return A8xFormDaoUtil.findAsMap(daoContext, sql, new SqlParameter(A8xFormDataReadDao.this.formRecordId));
			}
		});
	}

	public List<Map<String, Object>> getChildTableData(String theChildTableName) throws ProcessingException {
		final String sql = "select * from " + theChildTableName + " where formmain_id=?";
		return JdbcWorker.wrapNotInTransaction(new JdbcWorkUnit<List<Map<String, Object>>>() {
			public List<Map<String, Object>> execute(DAOContext daoContext) throws ProcessingException {
				return A8xFormDaoUtil.findAsList(daoContext, sql, new SqlParameter(A8xFormDataReadDao.this.formRecordId));
			}
		});
	}



	public List<Map<String, Object>> getChildTableDataByDisplayName(String theDisplayname) throws ProcessingException {
		String tmpTablename = A8xDiBiaoUtil.getTableName(theDisplayname, formBean);
		if (tmpTablename != null) {
			return getChildTableData(tmpTablename);
		}
		return null;

	}

	public List<Map<String, Object>> getChildTableData(int index) throws ProcessingException {
		String theChildTableName = null;
		List<FormTableBean> tmpList = formBean.getSubTableBean();
		if (tmpList != null && tmpList.size() > 0) {
			if (tmpList.size() < (index - 1)) {
				return null;
			}
			theChildTableName = tmpList.get(index).getTableName();
			final String sql = "select * from " + theChildTableName + " where formmain_id=?";
			return JdbcWorker.wrapNotInTransaction(new JdbcWorkUnit<List<Map<String, Object>>>() {
				public List<Map<String, Object>> execute(DAOContext daoContext) throws ProcessingException {
					return A8xFormDaoUtil.findAsList(daoContext, sql, new SqlParameter(A8xFormDataReadDao.this.formRecordId));
				}
			});
		}
		return null;

	}

}
