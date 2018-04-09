package com.seeyon.apps.a8x.clocked.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.seeyon.apps.a8x.clocked.entity.Holiday;
import com.seeyon.apps.a8x.common.domain.QueryCondition;
import com.seeyon.apps.a8x.common.exception.DAOExcuteException;
import com.seeyon.apps.a8x.common.jdbc.DAOContext;
import com.seeyon.apps.a8x.common.jdbc.JdbcDAO;
import com.seeyon.apps.a8x.common.jdbc.JdbcDaoUtil;
import com.seeyon.apps.a8x.common.jdbc.RowGetter;
import com.seeyon.apps.a8x.common.parameter.IParameter;
import com.seeyon.apps.a8x.common.parameter.SqlParameter;

public class HolidayDao extends JdbcDAO implements RowGetter<Holiday> {

	public HolidayDao() {
		super();
	}

	public HolidayDao(DAOContext context) {
		super(context);
	}

	public List<Holiday> load(int year) throws DAOExcuteException {
		String sql = getSelectSQL(null);
		sql += " where (BEGINDATE >= to_date(?, 'yyyy-mm-dd') or ENDDATE < to_date(?, 'yyyy-mm-dd')) order by BEGINDATE";

		IParameter[] params = new IParameter[2];
		params[0] = new SqlParameter(String.valueOf(year)+"-01-01");
		params[1] = new SqlParameter(String.valueOf(year+1)+"-01-01");

		return JdbcDaoUtil.findAsList(context, sql, this, params);
	}

	/**
	 * 获取ResultSet中的数据或Cache中数据
	 * 
	 * @param rs
	 *            ResultSet
	 * @throws SQLException
	 * @return IValueObject
	 */
	public Holiday convertResultSet(ResultSet rs) throws SQLException {
		int index = 0;
		Holiday tmpModel = new Holiday();
		// id;
		index++;
		tmpModel.setId(rs.getLong(index));
		// 年度;
		index++;
		tmpModel.setYear(rs.getInt(index));
		// 节日类型;
		index++;
		tmpModel.setTypeID(rs.getString(index));
		// 开始日期;
		index++;
		tmpModel.setBeginDate(rs.getDate(index));
		// 结束日期;
		index++;
		tmpModel.setEndDate(rs.getDate(index));
		// 天数;
		index++;
		tmpModel.setDays(rs.getDouble(index));
		// 备注;
		index++;
		tmpModel.setRemark(rs.getString(index));
		return tmpModel;
	}

	/**
	 * 获取查询的SQL语句
	 * 
	 * @param condition
	 *            QueryCondition
	 * @return String
	 */
	private String getSelectSQL(QueryCondition condition) {
		StringBuffer tmpSelectSQL = new StringBuffer(500);
		tmpSelectSQL.append("select ");
		tmpSelectSQL.append("ID,YEAR,TYPEID,BEGINDATE,ENDDATE");
		tmpSelectSQL.append(",DAYS,REMARK");
		tmpSelectSQL.append(" from");
		tmpSelectSQL.append(" A8XHOLIDAY");
		if (condition != null) {
			tmpSelectSQL.append(condition.getWhereConditionString());
		}
		return tmpSelectSQL.toString();
	}

}