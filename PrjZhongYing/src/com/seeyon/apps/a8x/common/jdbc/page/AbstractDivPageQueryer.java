package com.seeyon.apps.a8x.common.jdbc.page;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.seeyon.apps.a8x.common.domain.QueryResult;
import com.seeyon.apps.a8x.common.exception.DAOExcuteException;
import com.seeyon.apps.a8x.common.jdbc.JdbcDaoUtil;
import com.seeyon.apps.a8x.common.jdbc.RowGetter;
import com.seeyon.apps.a8x.common.parameter.IParameter;

abstract public class AbstractDivPageQueryer implements IDivPageQueryer {

	protected Connection dbConnection;

	protected String sql;

	protected List<IParameter> paramters;

	protected int pageNo;

	protected int pageSize;

	@SuppressWarnings("rawtypes")
	protected RowGetter converter;

	@SuppressWarnings("rawtypes")
	public AbstractDivPageQueryer(Connection dbConnection, String sql, List<IParameter> paramters, int pageNo, int pageSize, RowGetter converter) {
		this.dbConnection = dbConnection;
		this.sql = sql;
		this.paramters = paramters;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.converter = converter;
	}
	
	public abstract String getSelectCountSQL4GroupBy();
	
	public String getSelectCountSQL() {
		String rSql = sql.trim();
		String tmpSql = sql.trim().toLowerCase();
		if (tmpSql.indexOf("group by") != -1 || tmpSql.indexOf(" distinct ") != -1) {
			return getSelectCountSQL4GroupBy();
		}
		
		String tSql = "select count(*) as totalsize from ";
		String from = null;
		String where = null;
		int indexFrom = tmpSql.indexOf("from ");
		int indexWhere = tmpSql.indexOf("where ");
		int indexOrder = tmpSql.indexOf("order by ");
		if (indexWhere > -1) {
			from = rSql.substring(indexFrom + 5, indexWhere);
			if (indexOrder > -1) {
				where = rSql.substring(indexWhere + 6, indexOrder);
			} else {
				where = rSql.substring(indexWhere + 6);
			}
		} else {
			if (indexOrder > -1) {
				from = rSql.substring(indexFrom + 5, indexOrder);
			} else {
				from = rSql.substring(indexFrom + 5);
			}
		}
		tSql += from;
		if (where != null) {
			tSql = tSql + " where " + where;
		}
		return tSql;
	}

	protected int queryTotalSize() throws DAOExcuteException {
		int value = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = dbConnection.prepareStatement(getSelectCountSQL(), java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			int size = 0;
			if (paramters != null) {
				size = paramters.size();
			}
			IParameter tmpParam = null;
			for (int i = 0; i < size; i++) {
				tmpParam = paramters.get(i);
				tmpParam.fillPreparedStatement(pstmt, i + 1);
			}
			// 执行查询
			rs = pstmt.executeQuery();
			if (rs.next()) {
				value = rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new DAOExcuteException(JdbcDaoUtil.QUERY, e);
		} finally {
			JdbcDaoUtil.close(pstmt, rs);
		}
		return value;
	}

	public QueryResult query() throws DAOExcuteException {
		QueryResult tmp = null;
		int totalSize = queryTotalSize(); // 总记录数
		if (totalSize > 0) {
			List<Object> tmpList = queryDatas();
			tmp = new QueryResult(tmpList, totalSize, pageNo, pageSize);
		}
		return tmp;
	}

	protected abstract List<Object> queryDatas() throws DAOExcuteException;
}