package com.seeyon.apps.a8x.common.jdbc;

import java.sql.Connection;

import com.seeyon.apps.a8x.common.exception.ProcessingException;

public class JdbcWorker {

	/*
	 * 获取缺省（A8数据库）的JDBC DAO上下文
	 */
	public static JdbcDAOContext getDAOContext() {
		JdbcDAOContext tmpJdbcDAOContext = null;
		Connection connection = null;
		
		connection = JdbcConnectionLocator.getInstance().getConnection("java:comp/env/jdbc/ctpDataSource");
		tmpJdbcDAOContext = new JdbcDAOContext(connection);
		return tmpJdbcDAOContext;
	}

	public static <T> T wrapInTransaction(JdbcWorkUnit<T> c) throws ProcessingException {
		T temp = null;
		DAOContext context = null;
		try {
			context = getDAOContext();
		} catch (Exception ex) {
			throw ProcessingException.createProcessException(ex);
		}
		try {
			context.beginTransaction();
			temp = c.execute(context);
			context.commitTransaction();
			return temp;
		} catch (Exception ex) {
			context.rollbackTransaction();
			throw ProcessingException.createProcessException(ex);
		} finally {
			context.destory();
		}
	}

	public static <T> T wrapNotInTransaction(JdbcWorkUnit<T> c) throws ProcessingException {
		T temp = null;
		DAOContext context = null;
		try {
			context = getDAOContext();
		} catch (Exception ex) {
			throw ProcessingException.createProcessException(ex);
		}
		try {
			// System.out.println("连接属性>>>>>" +
			// context.getConnection().getAutoCommit());
			temp = c.execute(context);
			return temp;
		} catch (Exception ex) {
			throw ProcessingException.createProcessException(ex);
		} finally {
			context.destory();
		}
	}

}
