package com.seeyon.apps.a8x.clocked.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.seeyon.apps.a8x.clocked.entity.OfficeDaKaRecord;
import com.seeyon.apps.a8x.common.domain.QueryCondition;
import com.seeyon.apps.a8x.common.exception.DAOExcuteException;
import com.seeyon.apps.a8x.common.jdbc.DAOContext;
import com.seeyon.apps.a8x.common.jdbc.JdbcDAO;
import com.seeyon.apps.a8x.common.jdbc.JdbcDaoUtil;
import com.seeyon.apps.a8x.common.jdbc.RowGetter;
import com.seeyon.apps.a8x.common.parameter.IParameter;
import com.seeyon.apps.a8x.common.parameter.SqlParameter;
import com.seeyon.apps.a8x.common.util.UtilDateTime;

public class ClockedRecordDao extends JdbcDAO implements RowGetter<OfficeDaKaRecord> {

	public ClockedRecordDao() {
		super();
	}

	public ClockedRecordDao(DAOContext context) {
		super(context);
	}

	/**
	 * 读取数据---打卡数据完善
	 * 
	 * @param year
	 * @param month
	 * @return
	 * @throws DAOExcuteException
	 */
	public List<OfficeDaKaRecord> loadForDaKaAdjust(int year, int month) throws DAOExcuteException {
		String sql = getSelectSQL(null) + " where CLOCKDATE >= to_date(?, 'yyyy-mm-dd') and CLOCKDATE <= to_date(?, 'yyyy-mm-dd')";
		actionType = JdbcDaoUtil.QUERY;
		IParameter[] tmp = new IParameter[2];
		tmp[0] = new SqlParameter((new java.sql.Date(UtilDateTime.getMonthStart(year, month).getTime())).toString());
		tmp[1] = new SqlParameter((new java.sql.Date(UtilDateTime.getMonthEnd(year, month).getTime())).toString());
		return JdbcDaoUtil.findAsList(context, sql, this, tmp);
	}

	/**
	 * 读取数据--考勤数据归集
	 * 
	 * @param year
	 * @param month
	 * @return
	 * @throws DAOExcuteException
	 */
	public List<OfficeDaKaRecord> loadForClockeGather(int year, int month) throws DAOExcuteException {
		String sql = getSelectSQL(null) + " where YEAR=? and MONTH=?";
		actionType = JdbcDaoUtil.QUERY;
		IParameter[] tmp = new IParameter[2];

		tmp[0] = new SqlParameter(year);
		tmp[1] = new SqlParameter(month);
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
	public OfficeDaKaRecord convertResultSet(ResultSet rs) throws SQLException {
		int index = 0;
		OfficeDaKaRecord tmpModel = new OfficeDaKaRecord();
		// id;
		index++;
		tmpModel.setId(rs.getLong(index));
		// 审核状态;
		index++;
		tmpModel.setState(rs.getInt(index));
		// 姓名;
		index++;
		tmpModel.setMemberID(rs.getString(index));
		// 打卡开始时间;
		index++;
		tmpModel.setClockDate(rs.getDate(index));
		// 上班时间;
		index++;
		tmpModel.setStartTime(rs.getString(index));
		// 上班时间;
		index++;
		tmpModel.setEndTime(rs.getString(index));
		// 部门;
		index++;
		tmpModel.setDept(rs.getString(index));
		// 单位机构;
		index++;
		tmpModel.setOrgan(rs.getString(index));
		// 工号;
		index++;
		tmpModel.setWorkNum(rs.getString(index));
		// 考勤机姓名;
		index++;
		tmpModel.setCardName(rs.getString(index));
		// 年度;
		index++;
		tmpModel.setYear(rs.getInt(index));
		// 月份;
		index++;
		tmpModel.setMonth(rs.getInt(index));
		// 说明;
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
		tmpSelectSQL.append("ID,STATE,MEMBERID,CLOCKDATE,STARTTIME");
		tmpSelectSQL.append(",ENDTIME,DEPT,ORGAN,WORKNUM,CARDNAME");
		tmpSelectSQL.append(",YEAR,MONTH,REMARK");
		tmpSelectSQL.append(" from");
		tmpSelectSQL.append(" A8XCLOCKEDRECORD");
		if (condition != null) {
			tmpSelectSQL.append(condition.getWhereConditionString());
		}
		return tmpSelectSQL.toString();
	}

}