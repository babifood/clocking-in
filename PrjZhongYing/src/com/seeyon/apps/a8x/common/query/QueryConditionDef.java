package com.seeyon.apps.a8x.common.query;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.seeyon.apps.a8x.common.domain.QueryCondition;
import com.seeyon.apps.a8x.common.util.UtilString;

public class QueryConditionDef implements Serializable {
	private static final long serialVersionUID = -8634224540098225531L;

	// 当前页数
	private int pageNo;

	// 每页显示Size
	private int pageSize;

	// 前缀符号(where and or )
	private String prepend;

	// 排列的字段
	private String qrySortFields;

	// 排列方式升或降
	private String qrySortType;

	// 条件规则,比如: f1 and (f2 or f3) and f4
	private String qryLogicRule;

	// 查询参数名称，比如: f1|f2|f2|f4
	private String qryParamNames;

	// 查询的逻辑字段名称,比如tblUser.name|tblUser.type
	private String qryLogicFields;

	// 查询的逻辑字段的类型,其中值参考java.sql.Types定义的常量
	private String qryFieldTypes;

	// 操作,比如=|llike|rlike|like|!=
	private String qryOperators;

	// 使用按?-参数,比如field1=? or 使用按名字-参数,比如field1=:param1
	private int useType;

	public QueryConditionDef(int useType) {
		this.useType = useType;
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

	public String getQryLogicRule() {
		return qryLogicRule;
	}

	public void setQryLogicRule(String qryLogicRule) {
		this.qryLogicRule = qryLogicRule;
	}

	public String getQrySortFields() {
		return qrySortFields;
	}

	public void setQrySortFields(String qrySortFields) {
		this.qrySortFields = qrySortFields;
	}

	public String getQrySortType() {
		return qrySortType;
	}

	public void setQrySortType(String qrySortType) {
		this.qrySortType = qrySortType;
	}

	public String getPrepend() {
		return prepend;
	}

	public void setPrepend(String prepend) {
		this.prepend = prepend;
	}

	public String getQryFieldTypes() {
		return qryFieldTypes;
	}

	public void setQryFieldTypes(String qryFieldTypes) {
		this.qryFieldTypes = qryFieldTypes;
	}

	public String getQryLogicFields() {
		return qryLogicFields;
	}

	public void setQryLogicFields(String qryLogicFields) {
		this.qryLogicFields = qryLogicFields;
	}

	public String getQryOperators() {
		return qryOperators;
	}

	public void setQryOperators(String qryOperators) {
		this.qryOperators = qryOperators;
	}

	public String getQryParamNames() {
		return qryParamNames;
	}

	public void setQryParamNames(String qryParamNames) {
		this.qryParamNames = qryParamNames;
	}

	public QueryCondition createQueryCondition(Map<String, String> theParamValueMaping) {
		// 获取条件
		QueryCondition conditions = parseCriterionCondition(theParamValueMaping);

		// 设置前缀符号(where and or)
		if (conditions != null) {
			conditions.setPrepend(prepend);
		}

		// 排列的字段
		if (qrySortFields != null && qrySortFields.trim().length() > 0) {
			if (conditions == null) {
				conditions = new QueryCondition();
			}
			// 排列方式升或降
			qrySortType = qrySortType == null ? "" : " " + qrySortType;
			conditions.setOrderByWhereStatement(new OrderByWhereStatement(qrySortFields + qrySortType));
		}

		// 换页请求
		if (pageNo > 0) {
			if (conditions == null) {
				conditions = new QueryCondition();
			}
			conditions.setPageNo(pageNo);
			if (pageSize > 0) {
				conditions.setPageSize(pageSize);
			} else {
				conditions.setPageSize(Constants.DEFAULT_DIV_PAGE_SIZE);
			}
		}
		return conditions;
	}

	private QueryCondition parseCriterionCondition(Map<String, String> theParamValueMaping) {
		if (theParamValueMaping == null) {
			return null;
		}

		if (theParamValueMaping.isEmpty()) {
			return null;
		}

		if (qryParamNames == null || "".equals(qryParamNames)) {
			return null;
		}

		// 参数名称
		boolean isEmptyValue = true;
		String sParamName[] = UtilString.tokenize(qryParamNames, "|");
		String tmpValue = null;
		for (int i = 0; i < sParamName.length; i++) {
			tmpValue = theParamValueMaping.get(sParamName[i]);
			if (tmpValue != null && tmpValue.trim().length() != 0) {
				isEmptyValue = false;
				break;
			}
		}
		if (isEmptyValue) {
			return null;
		}

		// 逻辑字段
		String sLogicField[] = UtilString.tokenize(qryLogicFields, "|");
		if (sParamName.length != sLogicField.length) {
			throw new java.lang.IllegalArgumentException("Logic field parameter format error!");
		}

		// 类型
		String sFieldType[] = UtilString.tokenize(qryFieldTypes, "|");
		if (sParamName.length != sFieldType.length) {
			throw new java.lang.IllegalArgumentException("Field type parameter format error!");
		}

		// 操作
		String[] sOperator = null;
		if (qryOperators == null || qryOperators.trim().length() == 0) {
			sOperator = new String[sLogicField.length];
			for (int i = 0; i < sFieldType.length; i++) {
				sOperator[i] = Constants.OP_EQUAL;
			}
		} else {
			sOperator = UtilString.tokenize(qryOperators, "|");
		}
		if (sOperator.length != sLogicField.length) {
			throw new java.lang.IllegalArgumentException("Operator parameter format error!");
		}

		// 构造QueryFieldDef
		Map<String, QueryFieldDef> tmpQueryFieldMapping = new HashMap<String, QueryFieldDef>((int) (sParamName.length / .75f) + 1);
		QueryFieldDef tmpQueryFieldDef = null;
		for (int i = 0; i < sParamName.length; i++) {
			tmpQueryFieldDef = new QueryFieldDef(sLogicField[i], sOperator[i], sFieldType[i], sParamName[i]);
			if (useType == Constants.QRY_TYPE_BY_NAME) {
				tmpQueryFieldDef.setByName(true);
			} else {
				tmpQueryFieldDef.setByName(false);
			}
			tmpQueryFieldMapping.put(sParamName[i], tmpQueryFieldDef);
		}

		// 创建QueryCondition对象
		QueryCondition condition = null;

		// 条件规则
		if (qryLogicRule == null) {
			condition = new QueryCondition();
			StringBuffer buf = new StringBuffer(sParamName.length * 20);
			int flag = 0;
			for (int i = 0; i < sParamName.length; i++) {
				tmpValue = theParamValueMaping.get(sParamName[i]);
				if (tmpValue != null && tmpValue.trim().length() != 0) {
					tmpQueryFieldDef = (QueryFieldDef) tmpQueryFieldMapping.get(sParamName[i]);
					if (flag == 1) {
						buf.append(" and ");
					}
					buf.append(tmpQueryFieldDef.getSQLStatement());
					condition.addParameter(tmpQueryFieldDef.getParameter(tmpValue));
					flag = 1;
				}
			}
			condition.addSqlWhereStatement(buf.toString());
		} else {
			condition = ConditionStatementParser.createQueryCondition(qryLogicRule, theParamValueMaping, tmpQueryFieldMapping);
		}

		return condition;
	}

}
