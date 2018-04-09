package com.seeyon.apps.a8x.clocked.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.seeyon.apps.a8x.clocked.entity.ClockedBizData;
import com.seeyon.apps.a8x.common.exception.DAOExcuteException;
import com.seeyon.apps.a8x.common.jdbc.DAOContext;
import com.seeyon.apps.a8x.common.jdbc.JdbcDAO;
import com.seeyon.apps.a8x.common.jdbc.JdbcDaoUtil;
import com.seeyon.apps.a8x.common.jdbc.RowGetter;
import com.seeyon.apps.a8x.common.parameter.IParameter;
import com.seeyon.apps.a8x.common.parameter.SqlParameter;

public class QingJiaBillDao extends JdbcDAO implements RowGetter<ClockedBizData> {

	public QingJiaBillDao() {
		super();
	}

	public QingJiaBillDao(DAOContext context) {
		super(context);
	}

	

	public ClockedBizData readByOffice(String theBillCode) throws DAOExcuteException {
		// name="field0003" display="流程编号"
		// name="field0014" display="请假时长"
		String sql = "select field0001 as membmerID,field0012 as beginTime,field0013 as endTime,field0010 as type,field0003 as billNum,field0014 as timeLength,ctp_enum_item.showvalue ";
		sql += " from formmain_0138,ctp_enum_item where formmain_0138.field0010=ctp_enum_item.id";
		sql += " and formmain_0138.state=1 and formmain_0138.field0003=?";
		return super.read(sql, this, new SqlParameter(theBillCode));
	}

	public List<ClockedBizData> load(long theMemberID, java.sql.Timestamp theBegin, java.sql.Timestamp theEnd) throws DAOExcuteException {
		String sql = "select field0001 as membmerID,field0012 as beginTime,field0013 as endTime,field0010 as type,field0003 as billNum,field0014 as timeLength,ctp_enum_item.showvalue ";
		sql += " from formmain_0138,ctp_enum_item where formmain_0138.field0010=ctp_enum_item.id";
		sql += " and formmain_0138.state=1 and field0001=? and ((to_date(to_char(field0012,'yyyy-mm-dd'),'yyyy-mm-dd') <= to_date(?, 'yyyy-mm-dd') and to_date(to_char(field0013,'yyyy-mm-dd'),'yyyy-mm-dd') >= to_date(?, 'yyyy-mm-dd')) or (to_date(to_char(field0012,'yyyy-mm-dd'),'yyyy-mm-dd') <= to_date(?, 'yyyy-mm-dd') and  to_date(to_char(field0013,'yyyy-mm-dd'),'yyyy-mm-dd') >= to_date(?, 'yyyy-mm-dd')) ) order by field0012";

		IParameter[] params = new IParameter[5];
		params[0] = new SqlParameter(String.valueOf(theMemberID));

		params[1] = new SqlParameter(theEnd.toString().substring(0, 10));
		params[2] = new SqlParameter(theBegin.toString().substring(0, 10));

		params[3] = new SqlParameter(theEnd.toString().substring(0, 10));
		params[4] = new SqlParameter(theBegin.toString().substring(0, 10));

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
	public ClockedBizData convertResultSet(ResultSet rs) throws SQLException {
		int index = 0;
		ClockedBizData tmpModel = new ClockedBizData();

		// 姓名;
		index++;
		tmpModel.setMemberID(rs.getString(index));
		// 开始日期;
		index++;
		tmpModel.setBeginTime(rs.getTimestamp(index));
		// 结束日期;
		index++;
		tmpModel.setEndTime(rs.getTimestamp(index));
		// 请假类别ID
		index++;
		tmpModel.setBizFlag1ID(rs.getString(index));
		// 单据号
		index++;
		tmpModel.setBillNum(rs.getString(index));
		// 时长
		index++;
		tmpModel.setTimeLength(rs.getDouble(index));

		// 请假类别
		index++;
		tmpModel.setBillType(rs.getString(index));
		
		return tmpModel;
	}
	
	public ClockedBizData readByOffice(long theRecordId) throws DAOExcuteException {
		String sql = "select field0001 as membmerID,field0012 as beginTime,field0013 as endTime,field0010 as type,field0003 as billNum,field0014 as timeLength,ctp_enum_item.showvalue ";
		sql += " from formmain_0138,ctp_enum_item where formmain_0138.field0010=ctp_enum_item.id";
		sql += " and formmain_0138.state=1 and formmain_0138.id=?";
		return super.read(sql, this, new SqlParameter(theRecordId));
	}
	
	//开发formmain_1568
	//formmain_1596 需修改 
	public ClockedBizData readByMobile(long theRecordId) throws DAOExcuteException {
		String sql = "select field0001 as membmerID,field0002 as beginTime,field0002 as endTime,field0010 as type,field0003 as billNum,field0014 as timeLength,ctp_enum_item.showvalue ";
		sql += " from formmain_1596,ctp_enum_item where formmain_1596.field0010=ctp_enum_item.id";
		sql += " and formmain_1596.state=1 and formmain_1596.id=?";
		return super.read(sql, this, new SqlParameter(theRecordId));
	}

	public static void main(String args[]) {
		String sql = "select field0001 as membmerID,field0002 as beginTime,field0002 as endTime,field0010 as type,field0003 as billNum,field0014 as timeLength,ctp_enum_item.showvalue ";
		sql += " from formmain_0138,ctp_enum_item where formmain_0138.field0010=ctp_enum_item.id";
		sql += " and formmain_0138.state=1 and formmain_0138.id=?";
		System.out.println(sql);
	}

}
