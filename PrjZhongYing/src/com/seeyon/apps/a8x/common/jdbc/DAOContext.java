package com.seeyon.apps.a8x.common.jdbc;

import java.sql.Connection;

import com.seeyon.apps.a8x.common.exception.DAOTransactionException;

public interface DAOContext {

	public void beginTransaction() throws DAOTransactionException;

	public void commitTransaction() throws DAOTransactionException;

	public void rollbackTransaction();

	public void destory();

	public Connection getConnection();

}