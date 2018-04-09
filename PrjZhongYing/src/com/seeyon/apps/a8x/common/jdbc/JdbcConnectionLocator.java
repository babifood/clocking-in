package com.seeyon.apps.a8x.common.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public final class JdbcConnectionLocator {
	private Map<String, DataSource> cache;
	private InitialContext ic;

	private JdbcConnectionLocator() {
		this.cache = Collections.synchronizedMap(new HashMap<String, DataSource>(5));
	}

	public DataSource getDataSource(String dataSourceName) {
		DataSource dataSource = null;
		if (cache.containsKey(dataSourceName)) {
			dataSource = cache.get(dataSourceName);
		} else {
			try {
				dataSource = (DataSource) getInitialContext().lookup(dataSourceName);
				cache.put(dataSourceName, dataSource);
			} catch (NamingException e) {
				throw new IllegalStateException("无法获取数据源:[" + dataSourceName + "]," + e.getLocalizedMessage());
			}
		}
		return dataSource;
	}

	public void shutdown() {
		try {
			ic.close();
		} catch (Exception e) {

		}
	}

	// 数据库连接服务
	public Connection getConnection(String dataSourceName) {
		Connection conn = null;
		try {
			DataSource tmpDataSource = getDataSource(dataSourceName);
			if (tmpDataSource != null) {
				conn = tmpDataSource.getConnection();
			}
		} catch (SQLException e) {
			throw new IllegalStateException("数据库连接服务错误:数据源[" + dataSourceName + "],原因:" + e.getLocalizedMessage());
		}
		return conn;
	}

	private InitialContext getInitialContext() {
		if (ic == null) {
			try {
				this.ic = new InitialContext();
			} catch (NamingException ne) {
				throw new IllegalStateException("Initial Context Error");
			}
		}
		return ic;
	}

	public static JdbcConnectionLocator getInstance() {
		return LazyHolder.INSTANCE;
	}

	private static class LazyHolder {
		private static final JdbcConnectionLocator INSTANCE = new JdbcConnectionLocator();
	}
}