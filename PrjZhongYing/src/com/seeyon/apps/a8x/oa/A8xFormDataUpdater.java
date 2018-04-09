package com.seeyon.apps.a8x.oa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.seeyon.apps.a8x.common.exception.ProcessingException;
import com.seeyon.apps.a8x.common.jdbc.DAOContext;
import com.seeyon.apps.a8x.common.jdbc.JdbcDaoUtil;
import com.seeyon.apps.a8x.common.jdbc.JdbcWorkUnit;
import com.seeyon.apps.a8x.common.jdbc.JdbcWorker;
import com.seeyon.apps.a8x.oa.dao.A8xFormDaoUtil;
import com.seeyon.apps.a8x.oa.dao.A8xSQLBuilder;
import com.seeyon.apps.a8x.oa.entity.A8xMasterDataRow;
import com.seeyon.ctp.form.bean.FormBean;
import com.seeyon.ctp.form.bean.FormFieldBean;
import com.seeyon.ctp.form.bean.FormTableBean;

public class A8xFormDataUpdater {

	private static final Log log = LogFactory.getLog(A8xFormDataUpdater.class);

	// 表单定义
	private FormBean formBean;

	public A8xFormDataUpdater(FormBean formBean) {
		this.formBean = formBean;
	}

	public FormBean getFormBean() {
		return formBean;
	}

	public boolean updateMasterTableByKey(Long dataKey, A8xMasterDataRow theDataRow) {
		if (dataKey == null) {
			return false;
		}
		List<String> displayList = new ArrayList<String>(theDataRow.size());
		Set<String> sets = theDataRow.keySet();
		for (String s : sets) {
			displayList.add(s);
		}
		final A8xSQLBuilder.A8xSqlInfo sqlInfo = A8xSQLBuilder.buildMasterTableUpdateSql(formBean,displayList);

		// 将【控件名-值】Map 转换为【字段名-值】Map
		final A8xMasterDataRow newData = new A8xMasterDataRow(theDataRow.size() + 10);
		FormTableBean masterTableBean = formBean.getMasterTableBean();
		Iterator<Entry<String, Object>> iter = theDataRow.entrySet().iterator();
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
		// 设置修改者和修改时间
		newData.initModifyFieldValue();
		// 设置数据关键字
		newData.setId(dataKey);

		// 执行数据库更新操作
		boolean temp = false;
		try {
			temp = JdbcWorker.wrapNotInTransaction(new JdbcWorkUnit<Boolean>() {
				public Boolean execute(DAOContext daoContext) throws ProcessingException {
					int flag = JdbcDaoUtil.singleExecute(daoContext, sqlInfo.getSql(), newData, new A8xFormDaoUtil.A8xDataRowMapSetter(sqlInfo.getFieldNameList()));
					return (flag >= 1);
				}
			});
		} catch (ProcessingException e) {
			temp = false;
			log.error(e);
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

}
