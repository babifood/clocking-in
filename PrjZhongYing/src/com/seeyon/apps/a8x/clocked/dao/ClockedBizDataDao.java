package com.seeyon.apps.a8x.clocked.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.seeyon.apps.a8x.clocked.entity.ClockedBizData;
import com.seeyon.apps.a8x.common.domain.QueryCondition;
import com.seeyon.apps.a8x.common.exception.DAOExcuteException;
import com.seeyon.apps.a8x.common.jdbc.DAOContext;
import com.seeyon.apps.a8x.common.jdbc.JdbcDAO;
import com.seeyon.apps.a8x.common.jdbc.JdbcDaoUtil;
import com.seeyon.apps.a8x.common.jdbc.RowGetter;
import com.seeyon.apps.a8x.common.parameter.IParameter;
import com.seeyon.apps.a8x.common.parameter.SqlParameter;
import com.seeyon.apps.a8x.common.util.UtilDateTime;

public class ClockedBizDataDao extends JdbcDAO implements RowGetter<ClockedBizData> {

	public ClockedBizDataDao() {
		super();
	}

	public ClockedBizDataDao(DAOContext context) {
		super(context);
	}
	
	/**
	 * 载入流程表单的业务数据----按人员排序
	 * 
	 * @param year
	 * @param month
	 * @return
	 * @throws DAOExcuteException
	 */
	public List<ClockedBizData> loadByMemberID(int year, int month,long memberId) throws DAOExcuteException {
		String sql = getSelectSQL(null) + " where YEAR=? and MONTH=? and MEMBERID=? order by BILLTYPE";
		actionType = JdbcDaoUtil.QUERY;
		IParameter[] tmp = new IParameter[3];

		tmp[0] = new SqlParameter(year);
		tmp[1] = new SqlParameter(month);
		tmp[2] = new SqlParameter(memberId);
		return JdbcDaoUtil.findAsList(context, sql, this, tmp);
	}
	

	/**
	 * 载入流程表单的业务数据----按人员排序
	 * 
	 * @param year
	 * @param month
	 * @return
	 * @throws DAOExcuteException
	 */
	public List<ClockedBizData> load(int year, int month) throws DAOExcuteException {
		String begin = UtilDateTime.getMonthStartSqlDate(year, month).toString()+" 00:00:00";
		String end = UtilDateTime.getMonthEndSqlDate(year, month).toString()+" 23:59:59";
		
		String sql = getSelectSQL(null) + " where (BEGINTIME >= to_date(?, 'yyyy-mm-dd hh24:mi:ss') and BEGINTIME <= to_date(?, 'yyyy-mm-dd hh24:mi:ss'))  or (ENDTIME >= to_date(?, 'yyyy-mm-dd hh24:mi:ss') and ENDTIME <= to_date(?, 'yyyy-mm-dd hh24:mi:ss'))  order by MEMBERID";
		actionType = JdbcDaoUtil.QUERY;
		IParameter[] tmp = new IParameter[4];
		tmp[0] = new SqlParameter(begin);
		tmp[1] = new SqlParameter(end);
		tmp[2] = new SqlParameter(begin);
		tmp[3] = new SqlParameter(end);
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
	public ClockedBizData convertResultSet(ResultSet rs) throws SQLException {
		int index = 0;
		ClockedBizData tmpModel = new ClockedBizData();
		// id;
		index++;
		tmpModel.setId(rs.getLong(index));
		// 姓名;
		index++;
		tmpModel.setMemberID(rs.getString(index));
		// 年度;
		index++;
		tmpModel.setYear(rs.getInt(index));
		// 月度;
		index++;
		tmpModel.setMonth(rs.getInt(index));
		// 单位机构;
		index++;
		tmpModel.setOrgan(rs.getString(index));
		// 部门;
		index++;
		tmpModel.setDept(rs.getString(index));
		// 工号;
		index++;
		tmpModel.setWorkNum(rs.getString(index));
		// 日期;
		index++;
		tmpModel.setCreateTime(rs.getTimestamp(index));
		// 业务单据日期;
		index++;
		tmpModel.setBillDate(rs.getDate(index));
		// 业务单据号;
		index++;
		tmpModel.setBillNum(rs.getString(index));
		// 业务单据类型;
		index++;
		tmpModel.setBillType(rs.getString(index));
		// 时长;
		index++;
		tmpModel.setTimeLength(rs.getDouble(index));
		// 开始时间;
		index++;
		tmpModel.setBeginTime(rs.getTimestamp(index));
		// 结束时间;
		index++;
		tmpModel.setEndTime(rs.getTimestamp(index));
		// 是否有效;
		index++;
		tmpModel.setValidFlag(rs.getString(index));
		// 业务性质1ID;
		index++;
		tmpModel.setBizFlag1ID(rs.getString(index));
		// 业务性质2ID;
		index++;
		tmpModel.setBizFlag2ID(rs.getString(index));
		// 业务性质3ID;
		index++;
		tmpModel.setBizFlag3ID(rs.getString(index));
		// 业务性质1;
		index++;
		tmpModel.setBizFlag1(rs.getString(index));
		// 业务性质2;
		index++;
		tmpModel.setBizFlag2(rs.getString(index));
		// 业务性质3;
		index++;
		tmpModel.setBizFlag3(rs.getString(index));
		// 协同ID;
		index++;
		tmpModel.setSummaryID(rs.getLong(index));
		// 源单ID1;
		index++;
		tmpModel.setSourceData1ID(rs.getLong(index));
		// 源单ID2;
		index++;
		tmpModel.setSourceData2ID(rs.getLong(index));
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
		tmpSelectSQL.append("ID,MEMBERID,YEAR,MONTH,ORGAN");
		tmpSelectSQL.append(",DEPT,WORKNUM,CREATETIME,BILLDATE,BILLNUM");
		tmpSelectSQL.append(",BILLTYPE,TIMELENGTH,BEGINTIME,ENDTIME,VALIDFLAG");
		tmpSelectSQL.append(",BIZFLAG1ID,BIZFLAG2ID,BIZFLAG3ID,BIZFLAG1,BIZFLAG2");
		tmpSelectSQL.append(",BIZFLAG3,SUMMARYID,SOURCEDATA1ID,SOURCEDATA2ID");
		tmpSelectSQL.append(" from");
		tmpSelectSQL.append(" A8XCLOCKEDBIZDATA");
		if (condition != null) {
			tmpSelectSQL.append(condition.getWhereConditionString());
		}
		return tmpSelectSQL.toString();
	}

}