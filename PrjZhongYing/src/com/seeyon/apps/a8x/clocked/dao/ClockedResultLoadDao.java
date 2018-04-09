package com.seeyon.apps.a8x.clocked.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.seeyon.apps.a8x.clocked.entity.ClockedResult;
import com.seeyon.apps.a8x.common.exception.DAOExcuteException;
import com.seeyon.apps.a8x.common.jdbc.DAOContext;
import com.seeyon.apps.a8x.common.jdbc.JdbcDAO;
import com.seeyon.apps.a8x.common.jdbc.JdbcDaoUtil;
import com.seeyon.apps.a8x.common.jdbc.RowGetter;
import com.seeyon.apps.a8x.common.parameter.IParameter;
import com.seeyon.apps.a8x.common.parameter.SqlParameter;

/**
 * 
 * 用于考勤初始化和考勤归集
 * <p>
 * 
 * 
 */
public class ClockedResultLoadDao extends JdbcDAO implements RowGetter<ClockedResult> {

	public ClockedResultLoadDao() {
		super();
	}

	public ClockedResultLoadDao(DAOContext context) {
		super(context);
	}

	/**
	 * 
	 * @param year
	 * @param month
	 * @return
	 * @throws DAOExcuteException
	 */
	public List<ClockedResult> loadAll(int year, int month) throws DAOExcuteException {
		StringBuffer tmpSelectSQL = new StringBuffer(500);
		tmpSelectSQL.append("select ");
		tmpSelectSQL.append("A8XCLOCKEDRESULT.ID,A8XCLOCKEDRESULT.MODIFY_DATE,YEAR,MONTH,MEMBERID");
		tmpSelectSQL.append(",WORKNUM,DEPT,ORGAN,CLOCKDATE,WEEK");
		tmpSelectSQL.append(",CLOCKFLAG,BEGINTIME,ENDTIME,DAKALOCATION,DAKATYPE,STANDWORKTIMELENGTH");
		tmpSelectSQL.append(" from");
		tmpSelectSQL.append(" A8XCLOCKEDRESULT");

		String sql = tmpSelectSQL.toString() + " where YEAR=? and MONTH=?";
		actionType = JdbcDaoUtil.QUERY;
		IParameter[] tmp = new IParameter[2];
		tmp[0] = new SqlParameter(year);
		tmp[1] = new SqlParameter(month);
		return JdbcDaoUtil.findAsList(context, sql, this, tmp);
	}

	/**
	 * 
	 * @param year
	 * @param month
	 * @return
	 * @throws DAOExcuteException
	 */
	public List<ClockedResult> loadOnToday(int year, int month) throws DAOExcuteException {

		StringBuffer tmpSelectSQL = new StringBuffer(500);
		tmpSelectSQL.append("select ");
		tmpSelectSQL.append("A8XCLOCKEDRESULT.ID,A8XCLOCKEDRESULT.MODIFY_DATE,YEAR,MONTH,MEMBERID");
		tmpSelectSQL.append(",WORKNUM,DEPT,ORGAN,CLOCKDATE,WEEK");
		tmpSelectSQL.append(",CLOCKFLAG,BEGINTIME,ENDTIME,DAKALOCATION,DAKATYPE,STANDWORKTIMELENGTH");
		tmpSelectSQL.append(" from");
		tmpSelectSQL.append(" A8XCLOCKEDRESULT");

		String sql = tmpSelectSQL.toString() + " where YEAR=? and MONTH=?";
		
		actionType = JdbcDaoUtil.QUERY;
		IParameter[] tmp = new IParameter[2];
		tmp[0] = new SqlParameter(year);
		tmp[1] = new SqlParameter(month);
		// tmp[2] = new SqlParameter(new
		// java.sql.Date(System.currentTimeMillis()).toString());
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
	public ClockedResult convertResultSet(ResultSet rs) throws SQLException {
		int index = 0;
		ClockedResult tmpModel = new ClockedResult();
		// id;
		index++;
		tmpModel.setId(rs.getLong(index));
		// 修改日期;
		index++;
		tmpModel.setModify_date(rs.getTimestamp(index));
		// 年度;
		index++;
		tmpModel.setYear(rs.getInt(index));
		// 月份;
		index++;
		tmpModel.setMonth(rs.getInt(index));
		// 姓名;
		index++;
		tmpModel.setMemberID(rs.getString(index));
		// 工号;
		index++;
		tmpModel.setWorkNum(rs.getString(index));
		// 部门;
		index++;
		tmpModel.setDept(rs.getString(index));
		// 单位机构;
		index++;
		tmpModel.setOrgan(rs.getString(index));
		// 日期;
		index++;
		tmpModel.setClockDate(rs.getDate(index));
		// 星期;
		index++;
		tmpModel.setWeek(rs.getString(index));
		// 考勤标志;
		index++;
		tmpModel.setClockFlag(rs.getInt(index));
		// 标准上班时间;
		index++;
		tmpModel.setBeginTime(rs.getString(index));
		// 标准下班时间;
		index++;
		tmpModel.setEndTime(rs.getString(index));

		// 打卡地点;
		index++;
		tmpModel.setDaKaLocation(rs.getString(index));

		// 打卡方式;
		index++;
		tmpModel.setDaKaType(rs.getString(index));
		
		//标准工作时长
		index++;
		tmpModel.setStandWorkTimeLength(rs.getDouble(index));
		
		return tmpModel;
	}

}