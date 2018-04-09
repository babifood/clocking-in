package com.seeyon.apps.a8x.common.jdbc.page;

import java.sql.Connection;
import java.util.List;

import com.seeyon.apps.a8x.common.jdbc.RowGetter;
import com.seeyon.apps.a8x.common.parameter.IParameter;

public final class DivPageQueryerFactory {

	@SuppressWarnings("rawtypes")
	public static IDivPageQueryer create(Connection dbConn, String sql, List<IParameter> paramters, int pageNo, int pageSize, RowGetter converter) {
		IDivPageQueryer divPageQueryer = null;
		divPageQueryer = new OracleDivPageQueryer(dbConn, sql, paramters, pageNo, pageSize, converter);
		return divPageQueryer;
	}
}
