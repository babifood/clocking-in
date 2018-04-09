package com.seeyon.apps.a8x.common.jdbc.page;

import com.seeyon.apps.a8x.common.domain.QueryResult;
import com.seeyon.apps.a8x.common.exception.DAOExcuteException;

public interface IDivPageQueryer {

	public QueryResult query() throws DAOExcuteException;

	public String getLimitSelectSQL();

	public String getSelectCountSQL();

}