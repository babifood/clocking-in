package com.seeyon.apps.a8x.common.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.seeyon.apps.a8x.common.parameter.IParameter;
import com.seeyon.apps.a8x.common.query.OrderByWhereStatement;
import com.seeyon.apps.a8x.common.query.SqlWhereStatement;

public class QueryCondition implements Serializable {

	private static final long serialVersionUID = -1153834179900546743L;

	// 查询的页数
	private int pageNo = 1;

	// 页面留览的大小
	private int pageSize = -1;

	// 查询条件片断SQL语句列表
	private List<SqlWhereStatement> sqlWhereStatementList = null;

	// 查询参数列表
	private List<IParameter> parameterList = null;

	// SQL排序-条件
	private OrderByWhereStatement orderByWhereStatement = null;

	// 前缀符号(where/and/or)
	private String prepend = null;

	public QueryCondition() {
		super();
	}

	public void markPrependAND() {
		prepend = "and";
	}

	public void markPrependOR() {
		prepend = "or";
	}

	public void markPrependWhere() {
		prepend = "where";
	}

	public String getWhereConditionString() {
		StringBuffer buffer = new StringBuffer(200);
		buffer.append(getSqlWhereStatementString());
		if (orderByWhereStatement != null) {
			buffer.append(" ");
			buffer.append(orderByWhereStatement.getOrderByStatement());
		}
		return buffer.toString();
	}

	public String getSqlOrderStatementString() {
		if (orderByWhereStatement != null) {
			return " " + orderByWhereStatement.getOrderByStatement();
		}
		return "";
	}

	public String getSqlWhereStatementString() {
		StringBuffer buffer = new StringBuffer(200);
		// 添加用户定义的SQL条件片断
		if (sqlWhereStatementList != null && sqlWhereStatementList.isEmpty() == false) {
			if (prepend == null) {
				markPrependWhere();
			}
			buffer.append(" ");
			buffer.append(prepend);
			buffer.append(" ");

			SqlWhereStatement tmpSqlWhereStatement = sqlWhereStatementList.get(0);
			buffer.append(tmpSqlWhereStatement.getStatement());
			int x = sqlWhereStatementList.size();
			for (int i = 1; i < x; i++) {
				tmpSqlWhereStatement = sqlWhereStatementList.get(i);
				if (tmpSqlWhereStatement.getPrepend() == null) {
					buffer.append(" and ");
				} else {
					buffer.append(" ");
					buffer.append(tmpSqlWhereStatement.getPrepend());
					buffer.append(" ");
				}
				buffer.append(tmpSqlWhereStatement.getStatement());
			}
		}
		return buffer.toString();
	}

	public List<IParameter> getParameterList() {
		return parameterList;
	}

	public void setPrepend(String prepend_) {
		this.prepend = prepend_;
	}

	public void setOrderByWhereStatement(OrderByWhereStatement value) {
		if (value == null) {
			throw new IllegalArgumentException("setStaticOrderByCondition method paramter is null");
		}
		this.orderByWhereStatement = value;
	}

	public void addSqlWhereStatement(String thePrepend, String theSqlWhere) {
		getSafeSqlWhereStatementList().add(new SqlWhereStatement(thePrepend, theSqlWhere));
	}

	public void addSqlWhereStatement(String theSqlWhere) {
		getSafeSqlWhereStatementList().add(new SqlWhereStatement(theSqlWhere));
	}

	public void addParameter(IParameter parameter) {
		getSafeParameterList().add(parameter);
	}

	// 辅助方法
	private List<IParameter> getSafeParameterList() {
		if (parameterList == null) {
			parameterList = new ArrayList<IParameter>();
		}
		return parameterList;
	}

	// 辅助方法
	private List<SqlWhereStatement> getSafeSqlWhereStatementList() {
		if (sqlWhereStatementList == null) {
			sqlWhereStatementList = new ArrayList<SqlWhereStatement>();
		}
		return sqlWhereStatementList;
	}

	public boolean isDisplayByPage() {
		return pageSize > 0 ? true : false;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}