package com.seeyon.apps.a8x.common.jdbc.page;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.seeyon.apps.a8x.common.exception.DAOExcuteException;
import com.seeyon.apps.a8x.common.jdbc.JdbcDaoUtil;
import com.seeyon.apps.a8x.common.jdbc.RowGetter;
import com.seeyon.apps.a8x.common.parameter.IParameter;

public class OracleDivPageQueryer extends AbstractDivPageQueryer {

	@SuppressWarnings("rawtypes")
	public OracleDivPageQueryer(Connection dbConnection, String sql, List<IParameter> paramters, int pageNo, int pageSize, RowGetter converter) {
		super(dbConnection, sql, paramters, pageNo, pageSize, converter);
	}
	
	public String getSelectCountSQL4GroupBy() {
		return "select count(1) from (" + sql + ")";
	}

	public String getLimitSelectSQL() {
		sql = sql.trim();
		boolean isForUpdate = false;
		if (sql.toLowerCase().endsWith(" for update")) {
			sql = sql.substring(0, sql.length() - 11);
			isForUpdate = true;
		}
		StringBuilder pagingSelect = new StringBuilder(sql.length() + 100);
		if (pageNo > -1 && pageSize > 0) {
			pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
		} else {
			pagingSelect.append("select * from ( ");
		}
		pagingSelect.append(sql);
		pagingSelect.append(" ) row_ ) where rownum_ <= ").append("?").append(" and rownum_ > ").append("?");
		if (isForUpdate) {
			pagingSelect.append(" for update");
		}
		return pagingSelect.toString();
	}

	protected List<Object> queryDatas() throws DAOExcuteException {
		List<Object> tmpList = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String tmpSql = getLimitSelectSQL();
		try {
			pstmt = dbConnection.prepareStatement(tmpSql, java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY);
			int size = 0;
			if (paramters != null) {
				size = paramters.size();
			}
			IParameter tmpParam = null;
			for (int i = 0; i < size; i++) {
				tmpParam = paramters.get(i);
				tmpParam.fillPreparedStatement(pstmt, i + 1);
			}
			// where rownum_ <= ? and rownum_ > ?
			int end = pageNo * pageSize;
			int start = (pageNo - 1) * pageSize;
			if (size == 0) {
				pstmt.setInt(1, end);
				pstmt.setInt(2, start);
			} else {
				pstmt.setInt(size + 1, end);
				pstmt.setInt(size + 2, start);
			}
			// 执行查询
			rs = pstmt.executeQuery();
			if (pageSize > 10000) {
				rs.setFetchSize(100);
				tmpList = new ArrayList<Object>(100);
			} else {
				rs.setFetchSize(pageSize);
				// 构造集合内存空间
				tmpList = new ArrayList<Object>(pageSize);
			}
			while (rs.next()) {
				tmpList.add(converter.convertResultSet(rs));
			}
		} catch (SQLException e) {
			throw new DAOExcuteException(JdbcDaoUtil.QUERY, e);
		} finally {
			JdbcDaoUtil.close(pstmt, rs);
		}
		return tmpList;
	}
}
