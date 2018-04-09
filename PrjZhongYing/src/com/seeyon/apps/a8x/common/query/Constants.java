package com.seeyon.apps.a8x.common.query;

public final class Constants {
	// 默认的分页大小
	public static final int DEFAULT_DIV_PAGE_SIZE = 50;

	// 使用按?-参数,比如field1=?
	public static final int QRY_TYPE_BY_INDEX = 1;

	// 使用按名字-参数,比如field1=:param1
	public static final int QRY_TYPE_BY_NAME = 2;

	public static final String PAGE_SIZE = "pagesize";

	public static final String PAGE_NO = "pageno";

	public static final String PREPEND = "prepend";

	public static final String QRY_TYPE = "qryType";

	public static final String QRY_NAME_FLAG = "qryName";

	public static final String QRY_STATIC_SQL = "qryStaticSql";

	public static final String QRY_PARAM_NAMES_FLAG = "qryParamNames";

	public static final String QRY_LOGIC_FIELDS_FLAG = "qryLogicFields";

	public static final String QRY_FIELD_TYPES_FLAG = "qryFieldTypes";

	public static final String QRY_OPERATORS_FLAG = "qryOperators";

	public static final String QRY_LOGIC_Rule_FLAG = "qryLogicRule";

	public static final String QRY_SORT_FIELDS_FLAG = "qrySortFields";

	public static final String QRY_SORT_TYPE_FLAG = "qrySortType";

	public static final String OP_EQUAL = "=";

	public static final String OP_NO_EQUAL = "!=";

	public static final String OP_LIKE = "like";

	public static final String OP_LEFT_LIKE = "llike";

	public static final String OP_RIGHT_LIKE = "rlike";

	public static final String OP_LOWER = "<";

	public static final String OP_UPPER = ">";

	public static final String OP_LOWER_EQUAL = "<=";

	public static final String OP_UPPER_EQUAL = ">=";

	public static final String OR = "or";

	public static final String AND = "and";

	public static final String ASC = "ASC";

	public static final String DESC = "DESC";

	public static final String ORDER_BY_FLAG = " order by ";

	public static final String WHERE_FLAG = "where";
}
