package com.seeyon.apps.a8x.oa.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.seeyon.ctp.form.bean.FormBean;
import com.seeyon.ctp.form.bean.FormFieldBean;
import com.seeyon.ctp.form.bean.FormTableBean;
import com.seeyon.ctp.form.util.Enums.FieldType;
import com.seeyon.ctp.util.Datetimes;

public final class A8xSQLBuilder {

	private A8xSQLBuilder() {

	}

	public static A8xSelectSqlInfo buildMasterTableSelectSql(FormBean formBean, List<String> theDisplayList, Map<String, String> paramMap) {
		if (theDisplayList == null || theDisplayList.isEmpty()) {
			throw new java.lang.RuntimeException("控件名-List为空");
		}

		List<FormFieldBean> tmpFieldList = formBean.getMasterTableBean().getFields();
		Map<String, FormFieldBean> map = new HashMap<String, FormFieldBean>(tmpFieldList.size());
		for (FormFieldBean tmpFormFieldBean : tmpFieldList) {
			map.put(tmpFormFieldBean.getDisplay(), tmpFormFieldBean);
		}

		String theTablename = formBean.getMasterTableBean().getTableName();
		FormTableBean masterTableBean = formBean.getMasterTableBean();

		String whereSql = "";
		List<Object> params = new ArrayList<Object>();
		for (String displayName : paramMap.keySet()) {
			FormFieldBean fieldBean = masterTableBean.getFieldBeanByDisplay(displayName);
			if (fieldBean == null) {
				throw new java.lang.RuntimeException("控件名" + displayName + "在表单中不存在");
			}

			String value = paramMap.get(displayName);
			if (FieldType.DECIMAL.name().equals(fieldBean.getFieldType())) {
				params.add(new BigDecimal(value));
			} else if (FieldType.DATETIME.name().equals(fieldBean.getFieldType())) {
				params.add(Datetimes.parseDatetimeWithoutSecond(value));
			} else if (FieldType.TIMESTAMP.name().equals(fieldBean.getFieldType())) {
				params.add(Datetimes.parseDate(value));
			} else {
				params.add(value);
			}
			if (whereSql.length() > 0) {
				whereSql += " and ";
			}
			whereSql = whereSql + fieldBean.getName() + "=?";

		}
		if (StringUtils.isBlank(whereSql.toString())) {
			throw new java.lang.RuntimeException("查询条件为NULL...");
		}

		int size = theDisplayList == null ? 0 : theDisplayList.size();

		String sql2 = "id";
		String tempDisplay = null;
		FormFieldBean tmpFormFieldBean = null;
		for (int i = 0; i < size; i++) {
			tempDisplay = theDisplayList.get(i);
			tmpFormFieldBean = map.get(tempDisplay);
			if (tmpFormFieldBean == null) {
				throw new java.lang.RuntimeException("控件名" + tempDisplay + "在表单中不存在");
			}
			if (tmpFormFieldBean.getName().toLowerCase().startsWith("field")) {
				sql2 = sql2 + "," + tmpFormFieldBean.getName();
			}
		}
		String sql = "select " + sql2 + " form " + theTablename + " where " + whereSql;

		return new A8xSelectSqlInfo(sql, params);

	}

	public static A8xSqlInfo buildMasterTableInsertSql(FormBean formBean) {
		String sql = null;
		List<String> fieldNameList = null;

		String theTablename = formBean.getMasterTableBean().getTableName();
		List<FormFieldBean> tmpFieldList = formBean.getMasterTableBean().getFields();

		int size = tmpFieldList == null ? 0 : tmpFieldList.size();
		fieldNameList = new ArrayList<String>(size + 13);

		String sql1 = "id,state,start_member_id,start_date,approve_member_id,approve_date,finishedflag,ratifyflag,ratify_member_id,ratify_date,sort,modify_member_id,modify_date";

		fieldNameList.add("id");
		fieldNameList.add("state");
		fieldNameList.add("start_member_id");
		fieldNameList.add("start_date");
		fieldNameList.add("approve_member_id");
		fieldNameList.add("approve_date");
		fieldNameList.add("finishedflag");
		fieldNameList.add("ratifyflag");
		fieldNameList.add("ratify_member_id");
		fieldNameList.add("ratify_date");
		fieldNameList.add("sort");
		fieldNameList.add("modify_member_id");
		fieldNameList.add("modify_date");

		String sql2 = "?,?,?,?,?,?,?,?,?,?,?,?,?";
		for (FormFieldBean tmpFormFieldBean : tmpFieldList) {
			sql1 = sql1 + "," + tmpFormFieldBean.getName();
			sql2 = sql2 + ",?";
			fieldNameList.add(tmpFormFieldBean.getName());
		}
		sql = "insert into " + theTablename + " (" + sql1 + ") values (" + sql2 + ")";

		return new A8xSqlInfo(sql, fieldNameList);

	}

	public static A8xSqlInfo buildMasterTableInsertSql(FormBean formBean, List<String> theDisplayList) {
		if (theDisplayList == null || theDisplayList.isEmpty()) {
			throw new java.lang.RuntimeException("控件名-List为空");
		}
		String sql = null;
		List<String> fieldNameList = null;
		String theTablename = formBean.getMasterTableBean().getTableName();
		List<FormFieldBean> tmpFieldList = formBean.getMasterTableBean().getFields();
		Map<String, FormFieldBean> map = new HashMap<String, FormFieldBean>(tmpFieldList.size());
		for (FormFieldBean tmpFormFieldBean : tmpFieldList) {
			map.put(tmpFormFieldBean.getDisplay(), tmpFormFieldBean);
		}
		int size = theDisplayList == null ? 0 : theDisplayList.size();
		fieldNameList = new ArrayList<String>(size + 13);

		String sql1 = "id,state,start_member_id,start_date,approve_member_id,approve_date,finishedflag,ratifyflag,ratify_member_id,ratify_date,sort,modify_member_id,modify_date";

		fieldNameList.add("id");
		fieldNameList.add("state");
		fieldNameList.add("start_member_id");
		fieldNameList.add("start_date");
		fieldNameList.add("approve_member_id");
		fieldNameList.add("approve_date");
		fieldNameList.add("finishedflag");
		fieldNameList.add("ratifyflag");
		fieldNameList.add("ratify_member_id");
		fieldNameList.add("ratify_date");
		fieldNameList.add("sort");
		fieldNameList.add("modify_member_id");
		fieldNameList.add("modify_date");

		String sql2 = "?,?,?,?,?,?,?,?,?,?,?,?,?";

		String tempDisplay = null;
		FormFieldBean tmpFormFieldBean = null;
		for (int i = 0; i < size; i++) {
			tempDisplay = theDisplayList.get(i);
			tmpFormFieldBean = map.get(tempDisplay);

			if (tmpFormFieldBean == null) {
				throw new java.lang.RuntimeException("控件名" + tempDisplay + "在表单中不存在");
			}
			sql1 = sql1 + "," + tmpFormFieldBean.getName();
			sql2 = sql2 + ",?";
			fieldNameList.add(tmpFormFieldBean.getName());
		}
		sql = "insert into " + theTablename + " (" + sql1 + ") values (" + sql2 + ")";

		return new A8xSqlInfo(sql, fieldNameList);
	}

	public static A8xSqlInfo buildMasterTableUpdateSql(FormBean formBean, List<String> theDisplayList) {
		if (theDisplayList == null || theDisplayList.isEmpty()) {
			throw new java.lang.RuntimeException("控件名-List为空");
		}

		String sql = null;
		List<String> fieldNameList = null;

		List<FormFieldBean> tmpFieldList = formBean.getMasterTableBean().getFields();
		Map<String, FormFieldBean> map = new HashMap<String, FormFieldBean>(tmpFieldList.size());
		for (FormFieldBean tmpFormFieldBean : tmpFieldList) {
			map.put(tmpFormFieldBean.getDisplay(), tmpFormFieldBean);
		}

		String theTablename = formBean.getMasterTableBean().getTableName();

		int size = theDisplayList == null ? 0 : theDisplayList.size();
		fieldNameList = new ArrayList<String>(size + 13);

		fieldNameList.add("modify_member_id");
		fieldNameList.add("modify_date");
		String sql2 = "modify_member_id=?,modify_date=?";

		String tempDisplay = null;
		FormFieldBean tmpFormFieldBean = null;
		for (int i = 0; i < size; i++) {
			tempDisplay = theDisplayList.get(i);
			tmpFormFieldBean = map.get(tempDisplay);
			if (tmpFormFieldBean == null) {
				throw new java.lang.RuntimeException("控件名" + tempDisplay + "在表单中不存在");
			}
			if (tmpFormFieldBean.getName().toLowerCase().startsWith("field")) {
				sql2 = sql2 + "," + tmpFormFieldBean.getName() + "=?";
				fieldNameList.add(tmpFormFieldBean.getName());
			}
		}
		sql = "update " + theTablename + " set " + sql2 + " where id=?";
		fieldNameList.add("id");

		return new A8xSqlInfo(sql, fieldNameList);
	}

	public static A8xSqlInfo buildMasterTableUpdateSql(FormBean formBean) {
		String sql = null;
		List<String> fieldNameList = null;

		String theTablename = formBean.getMasterTableBean().getTableName();
		List<FormFieldBean> tmpFieldList = formBean.getMasterTableBean().getFields();
		int size = tmpFieldList == null ? 0 : tmpFieldList.size();
		fieldNameList = new ArrayList<String>(size + 13);
		fieldNameList.add("modify_member_id");
		fieldNameList.add("modify_date");
		String sql2 = "modify_member_id=?,modify_date=?";
		for (FormFieldBean tmpFormFieldBean : tmpFieldList) {
			sql2 = sql2 + "," + tmpFormFieldBean.getName() + "=?";
			fieldNameList.add(tmpFormFieldBean.getName());
		}
		sql = "update " + theTablename + " set " + sql2 + " where id=?";
		fieldNameList.add("id");

		return new A8xSqlInfo(sql, fieldNameList);
	}

	public static class A8xSqlInfo {

		private String sql;

		private List<String> fieldNameList;

		public A8xSqlInfo(String sql, List<String> fieldNameList) {
			this.sql = sql;
			this.fieldNameList = fieldNameList;
		}

		public String getSql() {
			return sql;
		}

		public List<String> getFieldNameList() {
			return fieldNameList;
		}

	}

	public static class A8xSelectSqlInfo {

		private String sql;

		private List<Object> paramValueList;

		public A8xSelectSqlInfo(String sql, List<Object> paramValueList) {
			this.sql = sql;
			this.paramValueList = paramValueList;
		}

		public String getSql() {
			return sql;
		}

		public List<Object> getParamValueList() {
			return paramValueList;
		}

	}

}
