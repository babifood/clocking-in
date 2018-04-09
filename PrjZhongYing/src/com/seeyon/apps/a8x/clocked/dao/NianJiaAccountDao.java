package com.seeyon.apps.a8x.clocked.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.seeyon.apps.a8x.clocked.entity.NianJiaAccount;
import com.seeyon.apps.a8x.common.exception.DAOExcuteException;
import com.seeyon.apps.a8x.common.jdbc.DAOContext;
import com.seeyon.apps.a8x.common.jdbc.JdbcDAO;
import com.seeyon.apps.a8x.common.jdbc.JdbcDaoUtil;
import com.seeyon.apps.a8x.common.jdbc.RowGetter;
import com.seeyon.apps.a8x.common.parameter.IParameter;
import com.seeyon.apps.a8x.common.parameter.SqlParameter;

public class NianJiaAccountDao extends JdbcDAO implements RowGetter<NianJiaAccount> {

	public NianJiaAccountDao() {
		super();
	}

	public NianJiaAccountDao(DAOContext context) {
		super(context);
	}
	
	public NianJiaAccount loadByMemberID(long theMemberID, int year) throws DAOExcuteException {
		StringBuffer tmpSelectSQL = new StringBuffer(500);
		tmpSelectSQL.append("select ");
		tmpSelectSQL.append("ID,MEMBERID,YEAR,DEPTID,ORGANID");
		tmpSelectSQL.append(",WORKNUM,PRESHIXIAODATE,PRETIMELENGTH,SHIXIAODATE,TIMELENGTH");
		tmpSelectSQL.append(",DIKOUTIMELENGTH,ZUOFEITIMELENGTH,SHENGYUTIMELENGTH,REMARK");
		tmpSelectSQL.append(" from");
		tmpSelectSQL.append(" A8XNIANJIAACCOUNT where MEMBERID=? and YEAR=? order by YEAR desc");

		IParameter[] theParams = new IParameter[2];
		theParams[0] = new SqlParameter(String.valueOf(theMemberID));
		theParams[1] = new SqlParameter(year);
		return JdbcDaoUtil.findAsValueObject(context, tmpSelectSQL.toString(), this, theParams);
	}
	

	public List<NianJiaAccount> load(long theMemberID, int year) throws DAOExcuteException {
		StringBuffer tmpSelectSQL = new StringBuffer(500);
		tmpSelectSQL.append("select ");
		tmpSelectSQL.append("ID,MEMBERID,YEAR,DEPTID,ORGANID");
		tmpSelectSQL.append(",WORKNUM,PRESHIXIAODATE,PRETIMELENGTH,SHIXIAODATE,TIMELENGTH");
		tmpSelectSQL.append(",DIKOUTIMELENGTH,ZUOFEITIMELENGTH,SHENGYUTIMELENGTH,REMARK");
		tmpSelectSQL.append(" from");
		tmpSelectSQL.append(" A8XNIANJIAACCOUNT where MEMBERID=? and YEAR>=? order by YEAR desc");

		IParameter[] theParams = new IParameter[2];
		theParams[0] = new SqlParameter(String.valueOf(theMemberID));
		theParams[1] = new SqlParameter(year);
		return super.load(tmpSelectSQL.toString(), this, theParams);
	}
	
	public List<NianJiaAccount> loadByBlankOut() throws DAOExcuteException {
		StringBuffer tmpSelectSQL = new StringBuffer(500);
		tmpSelectSQL.append("select ");
		tmpSelectSQL.append("ID,MEMBERID,YEAR,DEPTID,ORGANID");
		tmpSelectSQL.append(",WORKNUM,PRESHIXIAODATE,PRETIMELENGTH,SHIXIAODATE,TIMELENGTH");
		tmpSelectSQL.append(",DIKOUTIMELENGTH,ZUOFEITIMELENGTH,SHENGYUTIMELENGTH,REMARK");
		tmpSelectSQL.append(" from");
		tmpSelectSQL.append(" A8XNIANJIAACCOUNT where PRESHIXIAODATE<=to_date(?, 'yyyy-mm-dd') and PRETIMELENGTH>DIKOUTIMELENGTH and ZUOFEITIMELENGTH=0 order by SHIXIAODATE ASC");
		String x = new java.sql.Date(System.currentTimeMillis()).toString();
		return super.load(tmpSelectSQL.toString(), this, new SqlParameter(x));
	}
	
	
	public void updateDiKou(long theKey,double theVaue) throws DAOExcuteException{
		String sql = "update A8XNIANJIAACCOUNT set DIKOUTIMELENGTH=DIKOUTIMELENGTH+?,SHENGYUTIMELENGTH=SHENGYUTIMELENGTH-? where id=?";
		IParameter[] theParams = new IParameter[3];
		theParams[0] = new SqlParameter(theVaue);
		theParams[1] = new SqlParameter(theVaue);
		theParams[2] = new SqlParameter(theKey);
		JdbcDaoUtil.singleExecute(context, sql, theParams);
	}

	/**
	 * 获取ResultSet中的数据或Cache中数据
	 * 
	 * @param rs
	 *            ResultSet
	 * @throws SQLException
	 * @return IValueObject
	 */
	public NianJiaAccount convertResultSet(ResultSet rs) throws SQLException {
		int index = 0;
		NianJiaAccount tmpModel = new NianJiaAccount();
		// id;
		index++;
		tmpModel.setId(rs.getLong(index));
		// 姓名;
		index++;
		tmpModel.setMemberID(rs.getString(index));
		// 年度;
		index++;
		tmpModel.setYear(rs.getInt(index));
		// 部门;
		index++;
		tmpModel.setDeptID(rs.getString(index));
		// 单位机构;
		index++;
		tmpModel.setOrganID(rs.getString(index));
		// 工号;
		index++;
		tmpModel.setWorkNum(rs.getString(index));
		// 上年失效日期;
		index++;
		tmpModel.setPreShiXiaoDate(rs.getDate(index));
		// 上年结余数;
		index++;
		tmpModel.setPreTimeLength(rs.getDouble(index));
		// 本年失效日期;
		index++;
		tmpModel.setShiXiaoDate(rs.getDate(index));
		// 本年年假数;
		index++;
		tmpModel.setTimeLength(rs.getDouble(index));
		// 调休抵扣数;
		index++;
		tmpModel.setDiKouTimeLength(rs.getDouble(index));
		// 自动作废数;
		index++;
		tmpModel.setZuoFeiTimeLength(rs.getDouble(index));
		// 剩余年假数;
		index++;
		tmpModel.setShengYuTimeLength(rs.getDouble(index));
		// 备注;
		index++;
		tmpModel.setRemark(rs.getString(index));
		return tmpModel;
	}

}
