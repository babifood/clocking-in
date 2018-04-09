package com.seeyon.apps.a8x.clocked.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.seeyon.apps.a8x.clocked.entity.MobileDaKaLog;
import com.seeyon.apps.a8x.clocked.entity.MobileDaKaRecord2;
import com.seeyon.apps.a8x.common.domain.QueryCondition;
import com.seeyon.apps.a8x.common.domain.QueryResult;
import com.seeyon.apps.a8x.common.exception.DAOExcuteException;
import com.seeyon.apps.a8x.common.jdbc.DAOContext;
import com.seeyon.apps.a8x.common.jdbc.JdbcDAO;
import com.seeyon.apps.a8x.common.jdbc.JdbcDaoUtil;
import com.seeyon.apps.a8x.common.jdbc.RowGetter;
import com.seeyon.apps.a8x.common.parameter.SqlParameter;

public class MobileDaKaRecordDao extends JdbcDAO implements RowGetter<MobileDaKaLog> {

	public MobileDaKaRecordDao() {
		super();
	}

	public MobileDaKaRecordDao(DAOContext context) {
		super(context);
	}

	/**
	 * 读取数据--考勤数据归集
	 * 
	 * @param year
	 * @param month
	 * @return
	 * @throws DAOExcuteException
	 */
	public Map<String, MobileDaKaRecord2> loadForClockeGather(int year, int month) throws DAOExcuteException {
		String sql = getSelectSQL(null) + " where to_char(CLOCKTIME, 'yyyy-mm')=? order by MEMBERID,CLOCKTIME";
		actionType = JdbcDaoUtil.QUERY;
		String s = String.valueOf(year);
		if (month < 10) {
			s = s + "-0" + month;
		} else {
			s = s + "-" + month;
		}
		List<MobileDaKaLog> tmpList = JdbcDaoUtil.findAsList(context, sql, this, new SqlParameter(s));
		int size = tmpList == null ? 0 : tmpList.size();

		MobileDaKaRecord2 tmpMobileDaKaRecord2 = null;
		MobileDaKaLog tmpLog = null;

		Map<String, MobileDaKaRecord2> map = new HashMap<String, MobileDaKaRecord2>(size);
		String key = null;
		for (int i = 0; i < size; i++) {
			tmpLog = tmpList.get(i);
			key = tmpLog.getMemberId() + tmpLog.getClockDate();
			tmpMobileDaKaRecord2 = map.get(key);
			if (tmpMobileDaKaRecord2 == null) {
				tmpMobileDaKaRecord2 = new MobileDaKaRecord2();
				tmpMobileDaKaRecord2.setBegin(tmpLog);
				map.put(key, tmpMobileDaKaRecord2);
			} else {
				tmpMobileDaKaRecord2.setEnd(tmpLog);
			}
		}
		return map;
	}

	public QueryResult query(QueryCondition theCondition) throws DAOExcuteException {
		return super.query(getSelectSQL(theCondition), theCondition, this);
	}

	/**
	 * 获取ResultSet中的数据或Cache中数据
	 * 
	 * @param rs
	 *            ResultSet
	 * @throws SQLException
	 * @return IValueObject
	 */
	public MobileDaKaLog convertResultSet(ResultSet rs) throws SQLException {
		int index = 0;
		MobileDaKaLog tmpModel = new MobileDaKaLog();
		// 人员;
		index++;
		tmpModel.setMemberId(rs.getString(index));
		// 打卡时间;
		index++;
		tmpModel.setClockTime(rs.getTimestamp(index));
		// 打卡日期字符;
		index++;
		tmpModel.setClockDate(rs.getString(index));
		// 打卡地点类型;
		index++;
		tmpModel.setLocationTypeId(rs.getString(index));
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
		tmpSelectSQL.append("MEMBERID,CLOCKTIME,CLOCKDATE,LOCATIONTYPEID");
		tmpSelectSQL.append(" from");
		tmpSelectSQL.append(" A8XMOBILE_CLOCKED_RECORD_FINAL");
		if (condition != null) {
			tmpSelectSQL.append(condition.getWhereConditionString());
		}
		return tmpSelectSQL.toString();
	}

}