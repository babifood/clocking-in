package com.seeyon.apps.a8x.clocked.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.seeyon.apps.a8x.clocked.entity.JiaBanAccount;
import com.seeyon.apps.a8x.common.exception.DAOExcuteException;
import com.seeyon.apps.a8x.common.jdbc.DAOContext;
import com.seeyon.apps.a8x.common.jdbc.JdbcDAO;
import com.seeyon.apps.a8x.common.jdbc.JdbcDaoUtil;
import com.seeyon.apps.a8x.common.jdbc.RowGetter;
import com.seeyon.apps.a8x.common.parameter.SqlParameter;

public class JiaBanAccountReadDao extends JdbcDAO implements RowGetter<JiaBanAccount> {

	public JiaBanAccountReadDao() {
		super();
	}

	public JiaBanAccountReadDao(DAOContext context) {
		super(context);
	}
	
	public List<JiaBanAccount> loadByBlankOut() throws DAOExcuteException {
		// 按失效时间排序
		// Select sql
		StringBuffer tmpSelectSQL = new StringBuffer(500);
		tmpSelectSQL.append("select ");
		tmpSelectSQL.append("ID,MEMBERID,SHENGXIAODATE,SHIXIAODATE,JIABANTIMELENGTH");
		tmpSelectSQL.append(",DIKOUTIMELENGTH,SHENGYUTIMELENGTH,ZUOFEITIMELENGTH");
		tmpSelectSQL.append(" from");
		tmpSelectSQL.append(" A8XJIABANACCOUNT where SHIXIAODATE<=to_date(?, 'yyyy-mm-dd') and SHENGYUTIMELENGTH>0 and ZUOFEITIMELENGTH=0 order by SHIXIAODATE ASC");
		String x = new java.sql.Date(System.currentTimeMillis()).toString();
		return super.load(tmpSelectSQL.toString(), this, new SqlParameter(x));
	}

	public List<JiaBanAccount> loadByMemberID(long theMemberID) throws DAOExcuteException {
		// 按失效时间排序
		// Select sql
		StringBuffer tmpSelectSQL = new StringBuffer(500);
		tmpSelectSQL.append("select ");
		tmpSelectSQL.append("ID,MEMBERID,SHENGXIAODATE,SHIXIAODATE,JIABANTIMELENGTH");
		tmpSelectSQL.append(",DIKOUTIMELENGTH,SHENGYUTIMELENGTH,ZUOFEITIMELENGTH");
		tmpSelectSQL.append(" from");
		tmpSelectSQL.append(" A8XJIABANACCOUNT where MEMBERID=? and SHENGYUTIMELENGTH>0 order by SHENGXIAODATE ASC");
		return super.load(tmpSelectSQL.toString(), this, new SqlParameter(String.valueOf(theMemberID)));
	}

	public double getTotalShengYuTimeLengthByMemberID(long theMemberID) throws DAOExcuteException {
		String sql = "select sum(SHENGYUTIMELENGTH) from A8XJIABANACCOUNT where MEMBERID=? and SHENGYUTIMELENGTH>0";
		return JdbcDaoUtil.findAsDouble(context, sql, new SqlParameter(String.valueOf(theMemberID)));
	}

	/**
	 * 获取ResultSet中的数据或Cache中数据
	 * 
	 * @param rs
	 *            ResultSet
	 * @throws SQLException
	 * @return IValueObject
	 */
	public JiaBanAccount convertResultSet(ResultSet rs) throws SQLException {
		int index = 0;
		JiaBanAccount tmpModel = new JiaBanAccount();
		// id;
		index++;
		tmpModel.setId(rs.getLong(index));
		// 姓名;
		index++;
		tmpModel.setMemberID(rs.getString(index));
		// 生效日期;
		index++;
		tmpModel.setShengXiaoDate(rs.getDate(index));
		// 失效日期;
		index++;
		tmpModel.setShiXiaoDate(rs.getDate(index));
		// 时长;
		index++;
		tmpModel.setJiaBanTimeLength(rs.getDouble(index));
		// 用于调休时长;
		index++;
		tmpModel.setDiKouTimeLength(rs.getDouble(index));
		// 剩余时长;
		index++;
		tmpModel.setShengYuTimeLength(rs.getDouble(index));
		
		index++;
		tmpModel.setZuoFeiTimeLength(rs.getDouble(index));
		return tmpModel;
	}

}
