package com.seeyon.apps.a8x.oa.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.seeyon.apps.a8x.common.exception.DAOExcuteException;
import com.seeyon.apps.a8x.common.jdbc.DAOContext;
import com.seeyon.apps.a8x.common.jdbc.JdbcDaoUtil;
import com.seeyon.apps.a8x.common.jdbc.JdbcFieldType;
import com.seeyon.apps.a8x.common.jdbc.JdbcTableMeta;
import com.seeyon.apps.a8x.common.jdbc.RowSetter;
import com.seeyon.apps.a8x.common.parameter.IParameter;
import com.seeyon.apps.a8x.oa.entity.A8xMasterDataRow;
import com.seeyon.ctp.form.bean.FormBean;
import com.seeyon.ctp.form.bean.FormFieldBean;
import com.seeyon.ctp.form.bean.FormTableBean;
import com.seeyon.ctp.form.util.Enums.FieldType;
import com.seeyon.ctp.util.Datetimes;

/*
 * 针对OA表单的DAO 工具类
 */
public final class A8xFormDaoUtil {

	private A8xFormDaoUtil() {

	}

	/**
	 * 根据表单的唯一约束数据项，获取表单的ID值
	 * 
	 * @param formBean
	 * @param validDataMap
	 *            [控件名-数据值]
	 * @return
	 * @throws DAOExcuteException
	 */
	public static Long getMasterDataId(DAOContext daoContext, FormBean formBean, Map<String, String> validDataMap) throws DAOExcuteException {
		FormTableBean masterTableBean = formBean.getMasterTableBean();
		String whereSql = "";
		List<Object> params = new ArrayList<Object>();
		for (String displayName : validDataMap.keySet()) {
			FormFieldBean fieldBean = masterTableBean.getFieldBeanByDisplay(displayName);
			if (fieldBean != null) {
				String value = validDataMap.get(displayName);
				if (FieldType.DECIMAL.name().equals(fieldBean.getFieldType())) {
					params.add(new BigDecimal(value));
				} else if (FieldType.DATETIME.name().equals(fieldBean.getFieldType())) {
					params.add(Datetimes.parseDatetimeWithoutSecond(value));
				} else if (FieldType.TIMESTAMP.name().equals(fieldBean.getFieldType())) {
					params.add(Datetimes.parseDate(value));
				} else {
					params.add(value);
				}
				if (whereSql.length() > 0) {
					whereSql += " and ";
				}
				whereSql = whereSql + fieldBean.getName() + "=?";
			}
		}
		if (StringUtils.isBlank(whereSql.toString())) {
			return null;
		}

		// 查询数据库
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Long result = null;
		String sql = "select id from " + masterTableBean.getTableName() + " where " + whereSql;
		try {
			pstmt = daoContext.getConnection().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			if (params != null) {
				int size = params.size();
				// 设置查询参数
				for (int i = 1; i <= size; i++) {
					setPreparedStatementObject(pstmt, i, params.get(i - 1));
				}
			}
			// 执行查询
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getLong(1);
			}
		} catch (SQLException e) {
			throw new DAOExcuteException(JdbcDaoUtil.READ, e);
		} finally {
			closeResultSet(rs);
			closeStatement(pstmt);
		}
		return result;

	}

	public static Map<String, Object> findAsMap(DAOContext context, String theSQL, IParameter... theParams) throws DAOExcuteException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Map<String, Object> result = null;
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
			JdbcTableMeta tmpMetaModel = getMetaDataType(rs.getMetaData());
			if (rs.next()) {
				result = convertToMap(rs, tmpMetaModel);
			}
		} catch (SQLException e) {
			throw new DAOExcuteException(JdbcDaoUtil.READ, e);
		} finally {
			JdbcDaoUtil.closeResultSet(rs);
			JdbcDaoUtil.closeStatement(pstmt);
		}
		return result;
	}

	public static List<Map<String, Object>> findAsList(DAOContext context, String theSQL, IParameter... theParams) throws DAOExcuteException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Map<String, Object>> tmpList = null;
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
			JdbcTableMeta tmpMetaModel = getMetaDataType(rs.getMetaData());
			rs.setFetchSize(100);
			while (rs.next()) {
				if (tmpList == null) {
					tmpList = new ArrayList<Map<String, Object>>(10);
				}
				tmpList.add(convertToMap(rs, tmpMetaModel));
			}
		} catch (SQLException e) {
			throw new DAOExcuteException(JdbcDaoUtil.READ, e);
		} finally {
			JdbcDaoUtil.closeResultSet(rs);
			JdbcDaoUtil.closeStatement(pstmt);
		}
		return tmpList;
	}

	private static JdbcTableMeta getMetaDataType(ResultSetMetaData meta) throws SQLException {
		JdbcTableMeta temp = null;

		int size = meta.getColumnCount();
		temp = new JdbcTableMeta(size);
		JdbcFieldType tmpType = null;
		for (int i = 1; i <= size; i++) {
			tmpType = new JdbcFieldType();
			tmpType.setJdbcType(meta.getColumnType(i));
			tmpType.setTypeName(meta.getColumnTypeName(i));

			temp.add(meta.getColumnName(i), tmpType);
		}

		return temp;
	}

	public static Map<String, Object> findAsMap(DAOContext context, String theSQL, List<Object> theParams) throws DAOExcuteException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Map<String, Object> result = null;
		try {
			pstmt = context.getConnection().prepareStatement(theSQL, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			if (theParams != null) {
				// 设置查询参数
				int size = theParams.size();
				for (int i = 0; i < size; i++) {
					setPreparedStatementObject(pstmt, i + 1, theParams.get(i));
				}
			}
			// 执行查询
			rs = pstmt.executeQuery();
			JdbcTableMeta tmpMetaModel = getMetaDataType(rs.getMetaData());
			if (rs.next()) {
				result = convertToMap(rs, tmpMetaModel);
			}
		} catch (SQLException e) {
			throw new DAOExcuteException(JdbcDaoUtil.READ, e);
		} finally {
			JdbcDaoUtil.closeResultSet(rs);
			JdbcDaoUtil.closeStatement(pstmt);
		}
		return result;
	}

	public static List<Map<String, Object>> findAsList(DAOContext context, String theSQL, List<Object> theParams) throws DAOExcuteException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Map<String, Object>> tmpList = null;
		try {
			pstmt = context.getConnection().prepareStatement(theSQL, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			if (theParams != null) {
				// 设置查询参数
				int size = theParams.size();
				for (int i = 0; i < size; i++) {
					setPreparedStatementObject(pstmt, i + 1, theParams.get(i));
				}
			}
			// 执行查询
			rs = pstmt.executeQuery();
			JdbcTableMeta tmpMetaModel = getMetaDataType(rs.getMetaData());
			rs.setFetchSize(100);
			while (rs.next()) {
				if (tmpList == null) {
					tmpList = new ArrayList<Map<String, Object>>(10);
				}
				tmpList.add(convertToMap(rs, tmpMetaModel));
			}
		} catch (SQLException e) {
			throw new DAOExcuteException(JdbcDaoUtil.READ, e);
		} finally {
			JdbcDaoUtil.closeResultSet(rs);
			JdbcDaoUtil.closeStatement(pstmt);
		}
		return tmpList;
	}

	private static Map<String, Object> convertToMap(ResultSet rs, JdbcTableMeta meta) throws SQLException {
		int size = meta.size();
		Map<String, Object> map = new HashMap<String, Object>(size + 5);
		for (int i = 0; i < size; i++) {
			map.put(meta.getFieldNameLowerCase(i), meta.getType(i).get(rs, i + 1));
		}
		return map;
	}

	public static int singleExecute(DAOContext context, String theSQL, List<Object> theParams) throws DAOExcuteException {
		int rowCount = -1;
		PreparedStatement stmt = null;
		try {
			stmt = context.getConnection().prepareStatement(theSQL);

			int size = theParams.size();
			for (int i = 0; i < size; i++) {
				setPreparedStatementObject(stmt, i + 1, theParams.get(i));
			}

			stmt.executeUpdate();
		} catch (Exception e) {
			// System.out.println(theModel);
			throw new DAOExcuteException(e.getLocalizedMessage());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
			}
			stmt = null;
		}
		return rowCount;
	}

	private static void setPreparedStatementObject(PreparedStatement pst, int index, Object obj) throws SQLException {
		Object paramObj = obj;
		if (obj != null && obj instanceof java.util.Date && !(obj instanceof java.sql.Date)) {
			paramObj = new java.sql.Timestamp(((java.util.Date) obj).getTime());
		}
		pst.setObject(index, paramObj);
	}

	private static void closeStatement(Statement statement) {
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e) {
		}
		statement = null;
	}

	private static void closeResultSet(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {

		}
		rs = null;
	}

	public static class A8xDataRowMapSetter implements RowSetter<A8xMasterDataRow> {

		private List<String> fieldNameList;

		public A8xDataRowMapSetter(List<String> fieldNameList) {
			this.fieldNameList = fieldNameList;
		}

		public int fillStatement(PreparedStatement pstmt, A8xMasterDataRow theModel) throws SQLException {
			int index = 0;
			int size = fieldNameList.size();
			for (int i = 0; i < size; i++) {
				setPreparedStatementObject(pstmt, i + 1, theModel.get(fieldNameList.get(i)));
			}
			return index;
		}
	}

}
