package com.seeyon.apps.a8x.common.query;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.seeyon.apps.a8x.common.domain.QueryCondition;
import com.seeyon.apps.a8x.common.util.UtilString;

public class QueryConditionFactory {

	public static QueryCondition createQueryCondition(HttpServletRequest request) {
		// 前缀符号(where and or)
		String prepend = request.getParameter(Constants.PREPEND);
		// 指定无参数SQL 条件
		String staticSql = request.getParameter(Constants.QRY_STATIC_SQL);
		staticSql = staticSql == null ? "" : staticSql.trim();
		// 参数名称
		String tmpParamNames = request.getParameter(Constants.QRY_PARAM_NAMES_FLAG);

		// 逻辑字段
		String tmpLogicFields = request.getParameter(Constants.QRY_LOGIC_FIELDS_FLAG);

		// 类型
		String tmpFieldTypes = request.getParameter(Constants.QRY_FIELD_TYPES_FLAG);

		// 操作
		String tmpOperator = request.getParameter(Constants.QRY_OPERATORS_FLAG);

		// 条件规则
		String tmpQryLogicRule = request.getParameter(Constants.QRY_LOGIC_Rule_FLAG);

		// 排列的字段
		String sSortFields = request.getParameter(Constants.QRY_SORT_FIELDS_FLAG);

		// 排列方式升或降
		String sSortType = request.getParameter(Constants.QRY_SORT_TYPE_FLAG);

		// 换页请求
		String sPageNo = request.getParameter(Constants.PAGE_NO);
		String sPageSize = request.getParameter(Constants.PAGE_SIZE);

		// 查询参数途径,按名称或索引
		int flag = 0;
		String sFlag = request.getParameter(Constants.QRY_TYPE);
		if (sFlag == null) {
			flag = Constants.QRY_TYPE_BY_INDEX;
		} else {
			flag = Integer.parseInt(sFlag);
		}
		if (flag == 0) {
			flag = Constants.QRY_TYPE_BY_INDEX;
		}

		// 查询条件定义
		QueryConditionDef tmpQueryConditionDef = new QueryConditionDef(flag);
		tmpQueryConditionDef.setPrepend(prepend);
		tmpQueryConditionDef.setPageNo(sPageNo == null ? 0 : Integer.parseInt(sPageNo));
		tmpQueryConditionDef.setPageSize(sPageSize == null ? 0 : Integer.parseInt(sPageSize));
		Map<String, String> paramName_valueMapping = null;
		QueryCondition tmpQueryCondition = null;
		if (tmpParamNames != null) {
			String sParamName[] = UtilString.tokenize(tmpParamNames, "|");
			String sFieldType[] = UtilString.tokenize(tmpFieldTypes, "|");
			// 参数条件值
			paramName_valueMapping = new HashMap<String, String>((int) (sParamName.length / .75f) + 1);
			for (int i = 0; i < sParamName.length; i++) {
				String tmpValue = request.getParameter(sParamName[i]);
				if (tmpValue != null && tmpValue.length() > 0 && tmpValue.trim().length() == 0 && sFieldType[i].equals("str")) {
					paramName_valueMapping.put(sParamName[i], "NONE");
				} else {
					tmpValue = tmpValue == null ? "" : tmpValue.trim();
					if (tmpValue != null && tmpValue.length() != 0) {
						paramName_valueMapping.put(sParamName[i], tmpValue);
					}
				}
			}
			tmpQueryConditionDef.setQryParamNames(tmpParamNames);
			tmpQueryConditionDef.setQryLogicFields(tmpLogicFields);
			tmpQueryConditionDef.setQryFieldTypes(tmpFieldTypes);
			tmpQueryConditionDef.setQryOperators(tmpOperator);
			tmpQueryConditionDef.setQryLogicRule(tmpQryLogicRule);
		}

		tmpQueryConditionDef.setQrySortFields(sSortFields);
		tmpQueryConditionDef.setQrySortType(sSortType);

		tmpQueryCondition = tmpQueryConditionDef.createQueryCondition(paramName_valueMapping);
		if (tmpQueryCondition == null) {
			tmpQueryCondition = new QueryCondition();
		}
		if (!"".equals(staticSql)) {
			tmpQueryCondition.addSqlWhereStatement(staticSql);
		}
		return tmpQueryCondition;
	}
}