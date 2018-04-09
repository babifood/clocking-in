package com.seeyon.apps.a8x.common.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.seeyon.apps.a8x.common.domain.QueryCondition;
import com.seeyon.apps.a8x.common.domain.QueryResult;
import com.seeyon.apps.a8x.common.exception.DAOExcuteException;
import com.seeyon.apps.a8x.common.jdbc.page.DivPageQueryerFactory;
import com.seeyon.apps.a8x.common.jdbc.page.IDivPageQueryer;
import com.seeyon.apps.a8x.common.parameter.IParameter;

public final class JdbcDaoUtil {

	public static final int FETCH_SIZE = 100;

	public static final int INSERT = 1;

	public static final int UPDATE = 2;

	public static final int DELETE = 3;

	public static final int READ = 4;

	public static final int QUERY = 5;

	public final static int EXECUTE = 6;

	// 关闭Statement and ResultSet
	public static void close(Statement statement, ResultSet rs) {
		closeResultSet(rs);
		closeStatement(statement);
	}

	// 关闭连接
	public static void closeConnection(Connection con) {
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {

		}
		con = null;
	}

	public static void closeStatement(Statement statement) {
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e) {
		}
		statement = null;
	}

	public static void closeResultSet(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
		}
		rs = null;
	}

	// ======================常用JDBC操作=======================================
	private static DAOExcuteException createDAOExcuteException(String theSQL, SQLException e) {
		String tmp = theSQL.toLowerCase().trim();
		DAOExcuteException tmpDAOExcuteException = null;
		if (tmp.startsWith("insert")) {
			tmpDAOExcuteException = new DAOExcuteException(INSERT, e);
		} else if (tmp.startsWith("update")) {
			tmpDAOExcuteException = new DAOExcuteException(UPDATE, e);
		} else if (tmp.startsWith("delete")) {
			tmpDAOExcuteException = new DAOExcuteException(DELETE, e);
		} else {
			tmpDAOExcuteException = new DAOExcuteException(EXECUTE, e);
		}
		return tmpDAOExcuteException;
	}

	public static String findAsString(DAOContext context, String theSQL, IParameter... theParams) throws DAOExcuteException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		try {
			pstmt = context.getConnection().prepareStatement(theSQL, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			if (theParams != null) {
				// 设置查询参数
				for (int i = 0; i < theParams.length; i++) {
					theParams[i].fillPreparedStatement(pstmt, i + 1);
				}
			}
			// 执行查询
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getString(1);
			}
		} catch (SQLException e) {
			throw new DAOExcuteException(READ, e);
		} finally {
			closeResultSet(rs);
			closeStatement(pstmt);
		}
		return result;
	}

	public static String findAsString(DAOContext context, String theSQL, List<IParameter> theParams) throws DAOExcuteException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		try {
			pstmt = context.getConnection().prepareStatement(theSQL, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			if (theParams != null) {
				// 设置查询参数
				int size = theParams.size();
				for (int i = 0; i < size; i++) {
					theParams.get(i).fillPreparedStatement(pstmt, i + 1);
				}
			}
			// 执行查询
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getString(1);
			}
		} catch (SQLException e) {
			throw new DAOExcuteException(READ, e);
		} finally {
			closeResultSet(rs);
			closeStatement(pstmt);
		}
		return result;
	}

	public static int findAsInt(DAOContext context, String theSQL, IParameter... theParams) throws DAOExcuteException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = Integer.MIN_VALUE;
		try {
			pstmt = context.getConnection().prepareStatement(theSQL, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			if (theParams != null) {
				// 设置查询参数
				for (int i = 0; i < theParams.length; i++) {
					theParams[i].fillPreparedStatement(pstmt, i + 1);
				}
			}
			// 执行查询
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new DAOExcuteException(READ, e);
		} finally {
			closeResultSet(rs);
			closeStatement(pstmt);
		}
		return result;
	}

	public static int findAsInt(DAOContext context, String theSQL, List<IParameter> theParams) throws DAOExcuteException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = Integer.MIN_VALUE;
		try {
			pstmt = context.getConnection().prepareStatement(theSQL, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			if (theParams != null) {
				// 设置查询参数
				int size = theParams.size();
				for (int i = 0; i < size; i++) {
					theParams.get(i).fillPreparedStatement(pstmt, i + 1);
				}
			}
			// 执行查询
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new DAOExcuteException(READ, e);
		} finally {
			closeResultSet(rs);
			closeStatement(pstmt);
		}
		return result;
	}

	public static double findAsDouble(DAOContext context, String theSQL, IParameter... theParams) throws DAOExcuteException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		double result = Double.MIN_VALUE;
		try {
			pstmt = context.getConnection().prepareStatement(theSQL, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			if (theParams != null) {
				// 设置查询参数
				for (int i = 0; i < theParams.length; i++) {
					theParams[i].fillPreparedStatement(pstmt, i + 1);
				}
			}
			// 执行查询
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getDouble(1);
			}
		} catch (SQLException e) {
			throw new DAOExcuteException(READ, e);
		} finally {
			closeResultSet(rs);
			closeStatement(pstmt);
		}
		return result;
	}

	public static double findAsDouble(DAOContext context, String theSQL, List<IParameter> theParams) throws DAOExcuteException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		double result = Double.MIN_VALUE;
		try {
			pstmt = context.getConnection().prepareStatement(theSQL, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			if (theParams != null) {
				// 设置查询参数
				int size = theParams.size();
				for (int i = 0; i < size; i++) {
					theParams.get(i).fillPreparedStatement(pstmt, i + 1);
				}
			}
			// 执行查询
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getDouble(1);
			}
		} catch (SQLException e) {
			throw new DAOExcuteException(READ, e);
		} finally {
			closeResultSet(rs);
			closeStatement(pstmt);
		}
		return result;
	}

	public static Long findAsLong(DAOContext context, String theSQL, IParameter... theParams) throws DAOExcuteException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Long result = null;
		try {
			pstmt = context.getConnection().prepareStatement(theSQL, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			if (theParams != null) {
				// 设置查询参数
				for (int i = 0; i < theParams.length; i++) {
					theParams[i].fillPreparedStatement(pstmt, i + 1);
				}
			}
			// 执行查询
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getLong(1);
			}
		} catch (SQLException e) {
			throw new DAOExcuteException(READ, e);
		} finally {
			closeResultSet(rs);
			closeStatement(pstmt);
		}
		return result;
	}

	public static <T> T findAsValueObject(DAOContext context, String theSQL, RowGetter<T> theRsGetter, IParameter... theParams) throws DAOExcuteException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		T result = null;
		try {
			pstmt = context.getConnection().prepareStatement(theSQL, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			if (theParams != null) {
				// 设置查询参数
				for (int i = 0; i < theParams.length; i++) {
					theParams[i].fillPreparedStatement(pstmt, i + 1);
				}
			}
			// 执行查询
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = theRsGetter.convertResultSet(rs);
			}
		} catch (SQLException e) {
			throw new DAOExcuteException(READ, e);
		} finally {
			closeResultSet(rs);
			closeStatement(pstmt);
		}
		return result;
	}

	public static <T> T findAsValueObject(DAOContext context, String theSQL, RowGetter<T> theRsGetter, List<IParameter> theParams) throws DAOExcuteException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		T result = null;
		try {
			pstmt = context.getConnection().prepareStatement(theSQL, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			if (theParams != null) {
				// 设置查询参数
				int size = theParams.size();
				for (int i = 0; i < size; i++) {
					theParams.get(i).fillPreparedStatement(pstmt, i + 1);
				}
			}
			// 执行查询
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = theRsGetter.convertResultSet(rs);
			}
		} catch (SQLException e) {
			throw new DAOExcuteException(READ, e);
		} finally {
			closeResultSet(rs);
			closeStatement(pstmt);
		}
		return result;
	}

	public static <T> List<T> findAsList(DAOContext context, String theSQL, RowGetter<T> theRsGetter, IParameter... theParams) throws DAOExcuteException {
		return findAsList(context, theSQL, theRsGetter, FETCH_SIZE, theParams);
	}

	public static <T> List<T> findAsList(DAOContext context, String theSQL, RowGetter<T> theRsGetter, List<IParameter> theParams) throws DAOExcuteException {
		return findAsList(context, theSQL, theRsGetter, FETCH_SIZE, theParams);
	}

	public static <T> List<T> findAsList(DAOContext context, String theSQL, RowGetter<T> theRsGetter, int theFetchSize, IParameter... theParams) throws DAOExcuteException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<T> tmpList = null;
		try {
			pstmt = context.getConnection().prepareStatement(theSQL, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			if (theParams != null) {
				// 设置查询参数
				for (int i = 0; i < theParams.length; i++) {
					theParams[i].fillPreparedStatement(pstmt, i + 1);
				}
			}
			// 执行查询
			rs = pstmt.executeQuery();

			if (theFetchSize > 0) {
				try {
					rs.setFetchSize(theFetchSize);
				} catch (SQLException ee) {

				}
			}
			while (rs.next()) {
				if (tmpList == null) {
					tmpList = new ArrayList<T>(10);
				}
				tmpList.add(theRsGetter.convertResultSet(rs));
			}
		} catch (SQLException e) {
			throw new DAOExcuteException(READ, e);
		} finally {
			closeResultSet(rs);
			closeStatement(pstmt);
		}
		return tmpList;
	}

	public static <T> List<T> findAsList(DAOContext context, String theSQL, RowGetter<T> theRsGetter, int theFetchSize, List<IParameter> theParams) throws DAOExcuteException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<T> tmpList = null;
		try {
			pstmt = context.getConnection().prepareStatement(theSQL, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			if (theParams != null) {
				// 设置查询参数
				int size = theParams.size();
				for (int i = 0; i < size; i++) {
					theParams.get(i).fillPreparedStatement(pstmt, i + 1);
				}
			}
			// 执行查询
			rs = pstmt.executeQuery();
			if (theFetchSize > 0) {
				try {
					rs.setFetchSize(theFetchSize);
				} catch (SQLException ee) {

				}
			}
			while (rs.next()) {
				if (tmpList == null) {
					tmpList = new ArrayList<T>(10);
				}
				tmpList.add(theRsGetter.convertResultSet(rs));
			}
		} catch (SQLException e) {
			throw new DAOExcuteException(QUERY, e);
		} finally {
			closeResultSet(rs);
			closeStatement(pstmt);
		}
		return tmpList;
	}

	public static <T> List<T> findAsList(DAOContext context, String theQuerySQL, QueryCondition theCondition, RowGetter<T> theRsGetter) throws DAOExcuteException {
		List<T> tmpList = null;
		if (theCondition != null && theCondition.getParameterList() != null) {
			tmpList = findAsList(context, theQuerySQL, theRsGetter, theCondition.getParameterList());
		} else {
			tmpList = findAsList(context, theQuerySQL, theRsGetter);
		}
		return tmpList;
	}

	public static List<String> findAsStringList(DAOContext context, String theSQL, IParameter... theParams) throws DAOExcuteException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> tmpList = null;
		try {
			pstmt = context.getConnection().prepareStatement(theSQL, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			if (theParams != null) {
				// 设置查询参数
				int size = theParams.length;
				for (int i = 0; i < size; i++) {
					theParams[i].fillPreparedStatement(pstmt, i + 1);
				}
			}
			// 执行查询
			rs = pstmt.executeQuery();
			try {
				rs.setFetchSize(50);
			} catch (SQLException ee) {

			}

			while (rs.next()) {
				if (tmpList == null) {
					tmpList = new ArrayList<String>(10);
				}
				tmpList.add(rs.getString(1));
			}
		} catch (SQLException e) {
			throw new DAOExcuteException(QUERY, e);
		} finally {
			closeResultSet(rs);
			closeStatement(pstmt);
		}
		return tmpList;
	}

	public static boolean hasDataExisted(DAOContext context, String theSQL, IParameter... theParams) throws DAOExcuteException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;
		try {
			pstmt = context.getConnection().prepareStatement(theSQL);
			if (theParams != null) {
				// 设置查询参数
				for (int i = 0; i < theParams.length; i++) {
					theParams[i].fillPreparedStatement(pstmt, i + 1);
				}
			}
			// 执行查询
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = true;
			}
		} catch (SQLException e) {
			throw new DAOExcuteException(QUERY, e);
		} finally {
			closeResultSet(rs);
			closeStatement(pstmt);
		}
		return result;
	}

	public static boolean hasDataExistedByCount(DAOContext context, String theSQL, IParameter... theParams) throws DAOExcuteException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;
		try {
			pstmt = context.getConnection().prepareStatement(theSQL);
			if (theParams != null) {
				// 设置查询参数
				for (int i = 0; i < theParams.length; i++) {
					theParams[i].fillPreparedStatement(pstmt, i + 1);
				}
			}
			// 执行查询
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getInt(1) > 0) {
					result = true;
				}
			}
		} catch (SQLException e) {
			throw new DAOExcuteException(QUERY, e);
		} finally {
			closeResultSet(rs);
			closeStatement(pstmt);
		}
		return result;
	}

	public static boolean hasDataExistedByCount(DAOContext context, String theSQL, List<IParameter> theParams) throws DAOExcuteException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;
		try {
			pstmt = context.getConnection().prepareStatement(theSQL, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			if (theParams != null) {
				int size = theParams.size();
				// 设置查询参数
				for (int i = 0; i < size; i++) {
					theParams.get(i).fillPreparedStatement(pstmt, i + 1);
				}
			}
			// 执行查询
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getInt(1) > 0) {
					result = true;
				}
			}
		} catch (SQLException e) {
			throw new DAOExcuteException(QUERY, e);
		} finally {
			closeResultSet(rs);
			closeStatement(pstmt);
		}
		return result;
	}

	public static <T> int singleExecute(DAOContext context, String theSQL, T theModel, RowSetter<T> theStmtSetter) throws DAOExcuteException {
		int rowCount = -1;
		PreparedStatement stmt = null;
		try {
			stmt = context.getConnection().prepareStatement(theSQL);
			theStmtSetter.fillStatement(stmt, theModel);
			rowCount = stmt.executeUpdate();
		} catch (SQLException e) {
			// System.out.println(theModel);
			throw createDAOExcuteException(theSQL, e);
		} finally {
			closeStatement(stmt);
		}
		return rowCount;
	}

	public static int singleExecute(DAOContext context, String theSQL, IParameter... theParams) throws DAOExcuteException {
		int rowCount = -1;
		PreparedStatement stmt = null;
		try {
			stmt = context.getConnection().prepareStatement(theSQL);
			if (theParams != null) {
				// 设置查询参数
				for (int i = 0; i < theParams.length; i++) {
					theParams[i].fillPreparedStatement(stmt, i + 1);
				}
			}
			rowCount = stmt.executeUpdate();
		} catch (SQLException e) {
			throw createDAOExcuteException(theSQL, e);
		} finally {
			closeStatement(stmt);
		}
		return rowCount;
	}

	public static int singleExecute(DAOContext context, String theSQL, List<IParameter> theParams) throws DAOExcuteException {
		int rowCount = -1;
		PreparedStatement stmt = null;
		try {
			stmt = context.getConnection().prepareStatement(theSQL);
			if (theParams != null) {
				// 设置查询参数
				int size = theParams.size();
				for (int i = 0; i < size; i++) {
					theParams.get(i).fillPreparedStatement(stmt, i + 1);
				}
			}
			rowCount = stmt.executeUpdate();
		} catch (SQLException e) {
			throw createDAOExcuteException(theSQL, e);
		} finally {
			closeStatement(stmt);
		}
		return rowCount;
	}

	public static <T> int[] batchExecute(DAOContext context, String theSQL, List<T> theModelList, RowSetter<T> theStmtSetter) throws DAOExcuteException {
		PreparedStatement stmt = null;
		try {
			stmt = context.getConnection().prepareStatement(theSQL);
			int voListSize = theModelList.size();
			for (int k = 0; k < voListSize; k++) {
				theStmtSetter.fillStatement(stmt, theModelList.get(k));
				stmt.addBatch();
			}
			return stmt.executeBatch();
		} catch (SQLException e) {
			throw createDAOExcuteException(theSQL, e);
		} finally {
			closeStatement(stmt);
		}
	}

	public static Map<String, Double> findAsMapStringDouble(DAOContext context, String theSQL, IParameter... theParams) throws DAOExcuteException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Map<String, Double> result = new HashMap<String, Double>(1000);
		String key = null;
		try {
			pstmt = context.getConnection().prepareStatement(theSQL, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			if (theParams != null) {
				// 设置查询参数
				for (int i = 0; i < theParams.length; i++) {
					theParams[i].fillPreparedStatement(pstmt, i + 1);
				}
			}
			// 执行查询
			rs = pstmt.executeQuery();
			while (rs.next()) {
				key = rs.getString(1);
				key = key == null ? "" : key.trim();
				result.put(key, rs.getDouble(2));
			}
		} catch (SQLException e) {
			throw new DAOExcuteException(READ, e);
		} finally {
			closeResultSet(rs);
			closeStatement(pstmt);
		}
		return result;
	}

	public static Map<String, String> findAsMapStringString(DAOContext context, String theSQL, IParameter... theParams) throws DAOExcuteException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Map<String, String> result = new HashMap<String, String>(1000);
		String key = null;
		String value = null;
		try {
			pstmt = context.getConnection().prepareStatement(theSQL, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			if (theParams != null) {
				// 设置查询参数
				for (int i = 0; i < theParams.length; i++) {
					theParams[i].fillPreparedStatement(pstmt, i + 1);
				}
			}
			// 执行查询
			rs = pstmt.executeQuery();
			while (rs.next()) {
				key = rs.getString(1);
				key = key == null ? "" : key.trim();

				value = rs.getString(2);
				value = value == null ? "" : value.trim();
				result.put(key, value);
			}
		} catch (SQLException e) {
			throw new DAOExcuteException(READ, e);
		} finally {
			closeResultSet(rs);
			closeStatement(pstmt);
		}
		return result;
	}

	public static Map<String, Long> findAsMapStringLong(DAOContext context, String theSQL, IParameter... theParams) throws DAOExcuteException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Map<String, Long> result = new HashMap<String, Long>(1000);
		String key = null;
		try {
			pstmt = context.getConnection().prepareStatement(theSQL, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			if (theParams != null) {
				// 设置查询参数
				for (int i = 0; i < theParams.length; i++) {
					theParams[i].fillPreparedStatement(pstmt, i + 1);
				}
			}
			// 执行查询
			rs = pstmt.executeQuery();
			while (rs.next()) {
				key = rs.getString(1);
				key = key == null ? "" : key.trim();
				result.put(key, rs.getLong(2));
			}
		} catch (SQLException e) {
			throw new DAOExcuteException(READ, e);
		} finally {
			closeResultSet(rs);
			closeStatement(pstmt);
		}
		return result;
	}

	public static <T> QueryResult query(DAOContext context, String theSQL, QueryCondition theCondition, RowGetter<T> theRsGetter) throws DAOExcuteException {
		QueryResult tmp = null;
		boolean isDisplayByPage = false;
		List<IParameter> tmpParamList = null;
		if (theCondition != null) {
			isDisplayByPage = theCondition.isDisplayByPage();
			tmpParamList = theCondition.getParameterList();
		}
		if (isDisplayByPage) {

			tmpParamList = theCondition.getParameterList();
			int pageSize = theCondition.getPageSize();
			int pageNo = theCondition.getPageNo();
			IDivPageQueryer agent = DivPageQueryerFactory.create(context.getConnection(), theSQL, tmpParamList, pageNo, pageSize, theRsGetter);
			tmp = agent.query();

		} else {
			List<T> tmpList = findAsList(context, theSQL, theRsGetter, tmpParamList);
			if (tmpList != null) {
				tmp = new QueryResult(tmpList);
			}
		}
		return tmp;

	}

}
