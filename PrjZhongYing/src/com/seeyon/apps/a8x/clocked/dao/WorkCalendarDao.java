package com.seeyon.apps.a8x.clocked.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.seeyon.apps.a8x.clocked.entity.WorkCalendar;
import com.seeyon.apps.a8x.common.domain.QueryCondition;
import com.seeyon.apps.a8x.common.exception.DAOExcuteException;
import com.seeyon.apps.a8x.common.jdbc.DAOContext;
import com.seeyon.apps.a8x.common.jdbc.JdbcDAO;
import com.seeyon.apps.a8x.common.jdbc.JdbcDaoUtil;
import com.seeyon.apps.a8x.common.jdbc.RowGetter;
import com.seeyon.apps.a8x.common.parameter.IParameter;
import com.seeyon.apps.a8x.common.parameter.SqlParameter;
import com.seeyon.apps.a8x.common.util.UtilDateTime;

public class WorkCalendarDao extends JdbcDAO implements RowGetter<WorkCalendar> {

	public WorkCalendarDao() {
		super();
	}

	public WorkCalendarDao(DAOContext context) {
		super(context);
	}

	public List<WorkCalendar> load(int year, int month) throws DAOExcuteException {
		String sql = getSelectSQL(null);
		sql += " where WORKDATE >= to_date(?, 'yyyy-mm-dd') and WORKDATE <= to_date(?, 'yyyy-mm-dd') order by WORKDATE";

		String begin = (new java.sql.Date(UtilDateTime.getMonthStart(year, month).getTime())).toString();
		String end = (new java.sql.Date(UtilDateTime.getMonthEnd(year, month).getTime())).toString();

		IParameter[] tmp = new IParameter[2];
		tmp[0] = new SqlParameter(begin);
		tmp[1] = new SqlParameter(end);

		return JdbcDaoUtil.findAsList(context, sql, this, tmp);
	}

	/**
	 * 获取ResultSet中的数据或Cache中数据
	 * 
	 * @param rs
	 *            ResultSet
	 * @throws SQLException
	 * @return IValueObject
	 */
	public WorkCalendar convertResultSet(ResultSet rs) throws SQLException {
		int index = 0;
		WorkCalendar tmpModel = new WorkCalendar();
		// id;
		index++;
		tmpModel.setId(rs.getLong(index));
		// 日期;
		index++;
		tmpModel.setWorkDate(rs.getDate(index));
		// 班次;
		index++;
		tmpModel.setWorkGroup(rs.getString(index));
		// 是否考勤;
		index++;
		tmpModel.setWorkFlag(rs.getString(index));
		// 数据策略;
		index++;
		tmpModel.setDataFlag(rs.getString(index));
		// 机构;
		index++;
		tmpModel.setOrgan(rs.getString(index));
		// 部门;
		index++;
		tmpModel.setDept(rs.getString(index));
		// 员工;
		index++;
		tmpModel.setMember(rs.getString(index));
		// 日期类型;
		index++;
		tmpModel.setDateType(rs.getString(index));
		// 创建日期;
		index++;
		tmpModel.setCreateTime(rs.getTimestamp(index));
		// 创建者;
		index++;
		tmpModel.setCreator(rs.getString(index));
		// 班次NAME;
		index++;
		tmpModel.setWorkgroup_name(rs.getString(index));
		// 班次CODE;
		index++;
		tmpModel.setWorkgroup_code(rs.getString(index));
		// 数据策略NAME;
		index++;
		tmpModel.setDataflag_name(rs.getString(index));
		// 数据策略CODE;
		index++;
		tmpModel.setDataflag_code(rs.getString(index));
		// 日期类型NAME;
		index++;
		tmpModel.setDatetype_name(rs.getString(index));
		// 日期类型CODE;
		index++;
		tmpModel.setDatetype_code(rs.getString(index));
		// 机构名称;
		index++;
		tmpModel.setOrgan_name(rs.getString(index));
		// 机构路径;
		index++;
		tmpModel.setOrgan_path(rs.getString(index));
		// 部门名称;
		index++;
		tmpModel.setDept_name(rs.getString(index));
		// 部门路径;
		index++;
		tmpModel.setDept_path(rs.getString(index));
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
		tmpSelectSQL.append("ID,WORKDATE,WORKGROUP,WORKFLAG,DATAFLAGID");
		tmpSelectSQL.append(",ORGAN,DEPT,MEMBER,DATETYPE,CREATETIME");
		tmpSelectSQL.append(",CREATOR,WORKGROUP_NAME,WORKGROUP_CODE,DATAFLAG_NAME,DATAFLAG_CODE");
		tmpSelectSQL.append(",DATETYPE_NAME,DATETYPE_CODE,ORGAN_NAME,ORGAN_PATH,DEPT_NAME");
		tmpSelectSQL.append(",DEPT_PATH");
		tmpSelectSQL.append(" from");
		tmpSelectSQL.append(" A8XWORKCALENDAR");
		if (condition != null) {
			tmpSelectSQL.append(condition.getWhereConditionString());
		}
		return tmpSelectSQL.toString();
	}

}