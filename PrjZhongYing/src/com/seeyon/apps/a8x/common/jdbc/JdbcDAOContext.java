package com.seeyon.apps.a8x.common.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import com.seeyon.apps.a8x.common.exception.DAOTransactionException;

public class JdbcDAOContext implements DAOContext {

	private Connection connection;

	private boolean isInTransaction;

	public JdbcDAOContext(Connection connection) {
		this.connection = connection;
	}

	public void beginTransaction() throws DAOTransactionException {
		if (!isInTransaction) {
			try {
				if (getConnection().getAutoCommit()) {
					connection.setAutoCommit(false);

				}
				isInTransaction = true;
			} catch (SQLException e) {
				throw new DAOTransactionException(e);
			}
		}
	}

	public void commitTransaction() throws DAOTransactionException {
		if (isInTransaction) {
			try {
				connection.commit();
			} catch (SQLException e) {
				throw new DAOTransactionException(e);
			}
			try {
				connection.setAutoCommit(true);
				isInTransaction = false;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void rollbackTransaction() {
		if (isInTransaction && connection != null) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				connection.setAutoCommit(true);
				isInTransaction = false;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public void destory() {
		try {
			if (connection != null && connection.getAutoCommit() == false) {
				connection.setAutoCommit(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		connection = null;
	}

	protected void resetAutoCommit() {
		try {
			if (!connection.getAutoCommit()) {
				connection.setAutoCommit(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}