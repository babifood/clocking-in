package com.seeyon.apps.a8x.common.query;

import java.io.Serializable;

import com.seeyon.apps.a8x.common.parameter.IParameter;

public class QueryFieldDef implements Serializable {

	private static final long serialVersionUID = -306500318450054064L;

	// 参数类型
	private String type;

	// 查询逻辑字段名称
	private String logicFieldName;

	// 条件操作(= > >= < <= like)
	private String operator;

	// 参数标识名称,用于hibernate按名称标识查询,比如tbl.field=:paramter1
	private String paramterName;

	private boolean isByName;

	public QueryFieldDef(String fieldLogicName, String operator, String paramterName) {
		this.logicFieldName = fieldLogicName;
		this.paramterName = paramterName;
		this.operator = operator;
	}

	public QueryFieldDef(String fieldLogicName, String operator, String type, String paramterName) {
		this.logicFieldName = fieldLogicName;
		this.paramterName = paramterName;
		this.type = type;
		this.operator = operator;
	}

	public boolean isByName() {
		return isByName;
	}

	public void setByName(boolean isByName) {
		this.isByName = isByName;
	}

	public String getLogicFieldName() {
		return logicFieldName;
	}

	public void setLogicFieldName(String logicFieldName) {
		this.logicFieldName = logicFieldName;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getParamterName() {
		return paramterName;
	}

	public void setParamterName(String paramterName) {
		this.paramterName = paramterName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSQLStatement() {
		if (isByName) {
			return getNameSQLStatement();
		} else {
			return getIndexSQLStatement();
		}
	}

	private String getNameSQLStatement() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(logicFieldName);
		buffer.append(" ");
		if (Constants.OP_LEFT_LIKE.equalsIgnoreCase(operator) || Constants.OP_RIGHT_LIKE.equalsIgnoreCase(operator)) {
			buffer.append(Constants.OP_LIKE);
		} else {
			buffer.append(operator);
		}
		buffer.append(" :");
		buffer.append(paramterName);
		return buffer.toString();
	}

	private String getIndexSQLStatement() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(logicFieldName);
		buffer.append(" ");
		if (Constants.OP_LEFT_LIKE.equalsIgnoreCase(operator) || Constants.OP_RIGHT_LIKE.equalsIgnoreCase(operator)) {
			buffer.append(Constants.OP_LIKE);
		} else {
			buffer.append(operator);
		}

		if (type.equals("oracle_date")) {
			buffer.append(" to_date(?, 'yyyy-mm-dd')");
		} else {
			buffer.append(" ?");
		}

		return buffer.toString();
	}

	private String getValue_(String theValue) {
		if ("NONE".equalsIgnoreCase(theValue)) {
			return "";
		}
		if (Constants.OP_LEFT_LIKE.equalsIgnoreCase(operator)) {
			theValue = "%" + theValue;
		} else if (Constants.OP_RIGHT_LIKE.equalsIgnoreCase(operator)) {
			theValue = theValue + "%";
		} else if (Constants.OP_LIKE.equalsIgnoreCase(operator)) {
			theValue = "%" + theValue + "%";
		}
		return theValue;
	}

	public IParameter getParameter(String theValue) {
		if (isByName) {
			return ParameterFactory.buildParameter(getValue_(theValue), type, paramterName);
		} else {
			return ParameterFactory.buildParameter(getValue_(theValue), type, null);
		}
	}
}
