package com.seeyon.apps.a8x.common.jdbc;

import com.seeyon.apps.a8x.common.exception.ProcessingException;

public interface JdbcWorkUnit<T> {
	T execute(DAOContext context) throws ProcessingException;
}
