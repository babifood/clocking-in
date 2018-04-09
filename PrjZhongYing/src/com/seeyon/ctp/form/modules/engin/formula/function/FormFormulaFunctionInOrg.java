package com.seeyon.ctp.form.modules.engin.formula.function;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.seeyon.apps.a8x.clocked.service.KaoQinDirectorMgr;
import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.common.i18n.ResourceUtil;
import com.seeyon.ctp.form.bean.FormBean;
import com.seeyon.ctp.form.bean.FormFieldBean;
import com.seeyon.ctp.form.bean.FormFieldComBean;
import com.seeyon.ctp.form.bean.FormFormulaBean;
import com.seeyon.ctp.form.bean.FormFormulaBean.FormulaBaseBean;
import com.seeyon.ctp.form.bean.FormQueryWhereClause;
import com.seeyon.ctp.form.modules.engin.formula.FormFormulaBaseFunction;
import com.seeyon.ctp.form.modules.engin.formula.FormulaEnums;
import com.seeyon.ctp.form.modules.engin.formula.FormulaFunctionUitl;
import com.seeyon.ctp.util.SQLWildcardUtil;
import com.seeyon.v3x.util.Strings;

public class FormFormulaFunctionInOrg extends FormFormulaBaseFunction {
	public String getId() {
		return "inORG";
	}

	public String getI18nName() {
		return "所属机构";
	}

	public boolean canUse() {
		return false;
	}

	public String getComponentType() {
		return "componentType_condition";
	}

	public String getFormulaType() {
		return null;
	}

	public String getConditionType() {
		return "conditionType_all";
	}

	public FormulaEnums.FunctionType getFunctionType() {
		return FormulaEnums.FunctionType.CONDITIONFUNCTION;
	}

	public boolean validate(String exp, FormBean formBean, FormBean oFormBean, String formulaComponentType, String formulaType) throws BusinessException {
		FormulaFunctionUitl.checkComma(exp);
		String fieldName = exp.substring(0, exp.indexOf(",")).trim();
		String value = exp.substring(exp.indexOf(",") + 1);
		if (FormulaFunctionUitl.isSpecilFormFieldType(fieldName, formBean, oFormBean, true, true, true, new FormFieldComBean.FormFieldComEnum[] { FormFieldComBean.FormFieldComEnum.EXTEND_PROJECT })) {
			throw new BusinessException("请用 extend 按钮设置字段 " + fieldName + " 的条件式！");
		}
		if (oFormBean != null) {
			if (!FormulaFunctionUitl.isFormField(fieldName, null, oFormBean, false)) {
				throw new BusinessException(ResourceUtil.getString("form.formula.validate.first.is.bformfield"));
			}
			if ((!FormulaFunctionUitl.isFormField(value, formBean, null, false)) && (!FormulaFunctionUitl.isUserField(value, formBean, false)))
				FormulaFunctionUitl.isSimpleString(value, true);
		} else {
			if ((!fieldName.startsWith("{")) || (!fieldName.endsWith("}"))) {
				throw new BusinessException(ResourceUtil.getString("form.formula.validate.first.is.formfield"));
			}
			FormulaFunctionUitl.isFormField(fieldName, formBean, oFormBean, true);
			if (!FormulaFunctionUitl.isUserField(value, formBean, false)) {
				FormulaFunctionUitl.isSimpleString(value, true);
			}
		}
		return true;
	}

	public String procFormulaStrForSQL(String sql) {
		String[] params = sql.split(",");
		String group1 = getId();
		String group2 = params[0];
		String group3 = params[1].trim();
		if ("null".equals(group3)) {
			return group2 + " is null";
		}
		if ((group3.startsWith("'")) && (group3.endsWith("'"))) {
			group3 = group3.toLowerCase();
			group3 = group3.substring(1, group3.length() - 1);
			group3 = SQLWildcardUtil.escape(group3);
			group3 = "'%" + group3 + "%'";
		} else if (group3.startsWith("[f.")) {
			group3 = group3.toLowerCase();

			group3 = group3.replace("'", "");
		}
		return group2 + " " + (group1.equals(FormulaEnums.ConditionSymbol.not_like.getKey()) ? "not like" : group1) + " " + group3;
	}

	public String translateFormula(String formula, FormBean formBean) {
		String[] params = formula.split(",");
		String group2 = params[0];
		String group3 = params[1].trim();
		return group2 + " 需要包含 " + Strings.toHTML(group3);
	}

	public FormQueryWhereClause procFormulaStrForWhereClauseSQL(boolean isNeedTableName, FormBean formBean, FormBean relationFormBean, FormFormulaBean.FormulaFunctionBean formulaFunctionBean, Map<String, FormulaEnums.FormulaVar> formulaVarMap) {
		List<FormulaBaseBean> list = formulaFunctionBean.getList();
		boolean isFirstField = list.get(1) instanceof FormFormulaBean.FormulaDataFieldBean;
		boolean isThirdField = list.get(3) instanceof FormFormulaBean.FormulaDataFieldBean;

		FormFormulaBean.FormulaBaseBean param0 = null;
		if (isFirstField)
			param0 = (FormFormulaBean.FormulaDataFieldBean) list.get(1);
		else {
			param0 = (FormFormulaBean.FormulaValueBean) list.get(1);
		}
		FormFormulaBean.FormulaBaseBean param2 = null;
		if (isThirdField)
			param2 = (FormFormulaBean.FormulaDataFieldBean) list.get(3);
		else {
			param2 = (FormFormulaBean.FormulaValueBean) list.get(3);
		}
		String group1 = "in";
		String group2 = "";
		String group3 = "";
		if (isFirstField) {
			group2 = ((FormFormulaBean.FormulaDataFieldBean) param0).getValue();
			String myTableName = getFieldOwnerTableName(isNeedTableName, formBean, group2);
			String fieldName = group2;
			FormFieldBean formFieldBean = null;
			if (fieldName.startsWith("b")) {
				int positionIndex = group2.indexOf(".");
				fieldName = group2.substring(positionIndex + 1);
				formFieldBean = relationFormBean.getFieldBeanByName(fieldName);
			} else {
				formFieldBean = formBean.getFieldBeanByName(fieldName);
			}

			FormFieldBean realformFieldBean = formFieldBean;
			if (formFieldBean != null) {
				try {
					realformFieldBean = formFieldBean.findRealFieldBean();
				} catch (BusinessException localBusinessException1) {
					realformFieldBean = formFieldBean;
				}
			}
			group2 = myTableName + group2;
			group2 = FormulaFunctionUitl.getFieldSqlForNull(group2, realformFieldBean);
		} else {
			group2 = ((FormFormulaBean.FormulaValueBean) param0).getValue();
		}
		if (isThirdField) {
			group3 = ((FormFormulaBean.FormulaDataFieldBean) param2).getValue();
			String myTableName = getFieldOwnerTableName(isNeedTableName, formBean, group3);

			String fieldName = group3;
			FormFieldBean formFieldBean = null;
			if (fieldName.startsWith("b")) {
				int positionIndex = group2.indexOf(".");
				fieldName = group2.substring(positionIndex + 1);
				formFieldBean = formBean.getFieldBeanByName(fieldName);
			} else {
				formFieldBean = formBean.getFieldBeanByName(fieldName);
			}
			FormFieldBean realformFieldBean = formFieldBean;
			if (formFieldBean != null) {
				try {
					realformFieldBean = formFieldBean.findRealFieldBean();
				} catch (BusinessException localBusinessException2) {
					realformFieldBean = formFieldBean;
				}
			}
			group3 = myTableName + group3;
			group3 = FormulaFunctionUitl.getFieldSqlForNull(group3, realformFieldBean);
		} else {
			group3 = ((FormFormulaBean.FormulaValueBean) param2).getValue();
		}

		String sql = "";
		List<Object> params = new ArrayList<Object>(1);
		sql = group2 + " " + group1 + " (" + KaoQinDirectorMgr.getMe().getSqlString(AppContext.currentUserId()) + ")";

		FormQueryWhereClause whereClause = new FormQueryWhereClause();
		whereClause.setQueryParams(params);
		whereClause.setAllSqlClause(sql);
		return whereClause;
	}
}