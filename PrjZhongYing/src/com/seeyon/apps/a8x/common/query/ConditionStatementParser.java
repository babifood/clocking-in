package com.seeyon.apps.a8x.common.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.seeyon.apps.a8x.common.domain.QueryCondition;

public final class ConditionStatementParser {
	
	private ConditionStatementParser() {

	}

	public static QueryCondition createQueryCondition(String theQryLogicRule, Map<String, String> theParamName_valueMapping, Map<String, QueryFieldDef> theParamName_QueryFieldMapping) {
		return createSimpleQueryCondition(theQryLogicRule, theParamName_valueMapping, theParamName_QueryFieldMapping);
	}

	// 创建简单的查询,比如："f1 and f2 and f3 and f4"
	private static QueryCondition createSimpleQueryCondition(String theQryLogicRule, Map<String, String> theParamName_valueMapping, Map<String, QueryFieldDef> theParamName_QueryFieldMapping) {
		// 获取SQL-Rule
		String tokens[] = filerSimpleQryLogicRule(theQryLogicRule, theParamName_valueMapping);
		if (tokens == null) {
			return null;
		}
		// 创建条件对象
		QueryCondition condition = new QueryCondition();
		// 参数SQL 语句
		StringBuffer buf = new StringBuffer(tokens.length * 20);
		String token = null;
		for (int i = 0; i < tokens.length; i++) {
			token = tokens[i];
			if (i == 0) {
				if (token.equals("and") || token.equals("or")) {
					continue;
				}
			}
			if (token.equals("and")) {
				buf.append(" and ");
			} else if (token.equals("or")) {
				buf.append(" or ");
			} else {
				QueryFieldDef tmpQueryFieldDef = theParamName_QueryFieldMapping.get(token);
				buf.append(tmpQueryFieldDef.getSQLStatement());
				String tmpValue = theParamName_valueMapping.get(token);
				condition.addParameter(tmpQueryFieldDef.getParameter(tmpValue));
			}
		}
		condition.addSqlWhereStatement(buf.toString());
		return condition;
	}

	private static String[] filerSimpleQryLogicRule(String theQryLogicRule, Map<String, String> theParamName_valueMapping) {
		String tokens[] = getTokens(theQryLogicRule);
		int emptyCount = 0;
		for (int i = 0; i < tokens.length; i++) {
			String token = tokens[i];
			if (!token.equals("and") && !token.equals("or") && !token.equals(")") && !token.equals("(")) {
				String value = theParamName_valueMapping.get(token);
				if (value == null || value.length() == 0) {
					tokens[i] = "";
					emptyCount++;
					if ((i - 1) > 0) {
						tokens[i - 1] = "";
						emptyCount++;
					}
				}
			}
		}
		if (emptyCount == tokens.length) {
			return null;
		}
		if (emptyCount == 0) {
			return tokens;
		}
		String newTokens[] = new String[tokens.length - emptyCount];
		int k = 0;
		for (int i = 0; i < tokens.length; i++) {
			String token = tokens[i];
			if (token.length() > 0) {
				newTokens[k] = tokens[i];
				k++;
			}

		}
		return newTokens;
	}

	protected static String[] getTokens(String s) {
		List<String> parsedTokens = new ArrayList<String>();
		int start = 0;
		String tmp = null;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '(') {
				parsedTokens.add("(");
				start = i + 1;
			} else if (s.charAt(i) == ')') {
				if (start < i) {
					tmp = s.substring(start, i).trim();
					if (tmp.length() > 0) {
						parsedTokens.add(tmp);
					}
				}
				parsedTokens.add(")");
				start = i + 1;
			} else if (s.charAt(i) == ' ') {
				tmp = s.substring(start, i).trim();
				if (tmp.length() > 0) {
					if (tmp.equalsIgnoreCase("and")) {
						parsedTokens.add("and");
					} else if (tmp.equalsIgnoreCase("or")) {
						parsedTokens.add("or");
					} else {
						parsedTokens.add(tmp);
					}
				}
				start = i + 1;
			}
		}
		if (start < s.length()) {
			tmp = s.substring(start).trim();
			if (tmp.length() > 0) {
				parsedTokens.add(tmp);
			}
		}
		return parsedTokens.toArray(new String[parsedTokens.size()]);
	}
}