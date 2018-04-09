package com.seeyon.apps.a8x.clocked.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.seeyon.apps.a8x.clocked.entity.JiaBanAccount;
import com.seeyon.apps.a8x.common.exception.DAOExcuteException;
import com.seeyon.apps.a8x.common.jdbc.DAOContext;
import com.seeyon.apps.a8x.common.jdbc.JdbcDAO;
import com.seeyon.apps.a8x.common.jdbc.RowSetter;

/*
 * 员工加班账表-DAO
 */
public class JiaBanAccountCreateDao extends JdbcDAO implements RowSetter<JiaBanAccount> {

	public JiaBanAccountCreateDao() {
		super();
	}

	public JiaBanAccountCreateDao(DAOContext context) {
		super(context);
	}

	public int create(JiaBanAccount theModel) throws DAOExcuteException {
		StringBuffer tmpInsertSQL = new StringBuffer(500);
		tmpInsertSQL.append("insert into A8XJIABANACCOUNT(");
		tmpInsertSQL.append("ID,STATE,START_MEMBER_ID,START_DATE,APPROVE_MEMBER_ID");
		tmpInsertSQL.append(",APPROVE_DATE,FINISHEDFLAG,RATIFYFLAG,RATIFY_MEMBER_ID,RATIFY_DATE");
		tmpInsertSQL.append(",SORT,MODIFY_MEMBER_ID,MODIFY_DATE,MEMBERID,WORKNUM");
		tmpInsertSQL.append(",DEPTID,ORGANID,BILLNUM,BILLDATE,BILLTYPE");
		tmpInsertSQL.append(",JIABANTIMELENGTH,RATIO,OFFSET,DIKOUTIMELENGTH,JIFEITIMELENGTH");
		tmpInsertSQL.append(",SHENGXIAODATE,SHIXIAODATE,ZUOFEITIMELENGTH,SHENGYUTIMELENGTH");
		tmpInsertSQL.append(") values (");
		tmpInsertSQL.append("?,?,?,?,?");
		tmpInsertSQL.append(",?,?,?,?,?");
		tmpInsertSQL.append(",?,?,?,?,?");
		tmpInsertSQL.append(",?,?,?,?,?");
		tmpInsertSQL.append(",?,?,?,?,?");
		tmpInsertSQL.append(",?,?,?,?");
		tmpInsertSQL.append(")");

		return super.create(theModel, tmpInsertSQL.toString(), this);
	}

	/**
	 * 适配执行参数
	 * 
	 * @param pstmt
	 *            PreparedStatement
	 * @param theModel
	 *            IValueObject
	 * @throws SQLException
	 * @return int
	 */
	public int fillStatement(PreparedStatement pstmt, JiaBanAccount theModel) throws SQLException {
		JiaBanAccount tmpModel = theModel;
		String temp = null;
		int index = 0;
		if (actionType == INSERT) {
			// id;
			index++;
			pstmt.setLong(index, tmpModel.getId());
		}
		if (actionType == INSERT) {
			// 审核状态;
			index++;
			pstmt.setInt(index, tmpModel.getState());
		}
		if (actionType == INSERT) {
			// 发起人;
			index++;
			pstmt.setLong(index, tmpModel.getStart_member_id());
		}
		if (actionType == INSERT) {
			// 发起时间;
			index++;
			if (tmpModel.getStart_date() == null) {
				pstmt.setNull(index, java.sql.Types.NULL);
			} else {
				pstmt.setTimestamp(index, tmpModel.getStart_date());
			}
		}
		if (actionType == INSERT) {
			// 审核人;
			index++;
			pstmt.setLong(index, tmpModel.getApprove_member_id());
		}
		if (actionType == INSERT) {
			// 审核时间;
			index++;
			if (tmpModel.getApprove_date() == null) {
				pstmt.setNull(index, java.sql.Types.NULL);
			} else {
				pstmt.setTimestamp(index, tmpModel.getApprove_date());
			}
		}
		if (actionType == INSERT) {
			// 流程状态;
			index++;
			pstmt.setInt(index, tmpModel.getFinishedflag());
		}
		if (actionType == INSERT) {
			// 核定状态;
			index++;
			pstmt.setInt(index, tmpModel.getRatifyflag());
		}
		if (actionType == INSERT) {
			// 核定人;
			index++;
			pstmt.setLong(index, tmpModel.getRatify_member_id());
		}
		if (actionType == INSERT) {
			// 核定时间;
			index++;
			if (tmpModel.getRatify_date() == null) {
				pstmt.setNull(index, java.sql.Types.NULL);
			} else {
				pstmt.setTimestamp(index, tmpModel.getRatify_date());
			}
		}
		if (actionType == INSERT) {
			// 排序号;
			index++;
			pstmt.setInt(index, tmpModel.getSort());
		}
		// 修改者标识;
		index++;
		pstmt.setLong(index, tmpModel.getModify_member_id());
		// 修改日期;
		index++;
		if (tmpModel.getModify_date() == null) {
			pstmt.setNull(index, java.sql.Types.NULL);
		} else {
			pstmt.setTimestamp(index, tmpModel.getModify_date());
		}
		// 姓名;
		index++;
		if (tmpModel.getMemberID() == null) {
			pstmt.setNull(index, java.sql.Types.NULL);
		} else {
			temp = tmpModel.getMemberID();
			temp = temp == null ? "" : temp.trim();
			pstmt.setString(index, temp);
		}
		// 工号;
		index++;
		if (tmpModel.getWorkNum() == null) {
			pstmt.setNull(index, java.sql.Types.NULL);
		} else {
			temp = tmpModel.getWorkNum();
			temp = temp == null ? "" : temp.trim();
			pstmt.setString(index, temp);
		}
		// 部门;
		index++;
		if (tmpModel.getDeptID() == null) {
			pstmt.setNull(index, java.sql.Types.NULL);
		} else {
			temp = tmpModel.getDeptID();
			temp = temp == null ? "" : temp.trim();
			pstmt.setString(index, temp);
		}
		// 单位机构;
		index++;
		if (tmpModel.getOrganID() == null) {
			pstmt.setNull(index, java.sql.Types.NULL);
		} else {
			temp = tmpModel.getOrganID();
			temp = temp == null ? "" : temp.trim();
			pstmt.setString(index, temp);
		}
		// 业务单据号;
		index++;
		if (tmpModel.getBillNum() == null) {
			pstmt.setNull(index, java.sql.Types.NULL);
		} else {
			temp = tmpModel.getBillNum();
			temp = temp == null ? "" : temp.trim();
			pstmt.setString(index, temp);
		}
		// 申请日期;
		index++;
		if (tmpModel.getBillDate() == null) {
			pstmt.setNull(index, java.sql.Types.DATE);
		} else {
			pstmt.setDate(index, tmpModel.getBillDate());
		}
		// 加班类型;
		index++;
		if (tmpModel.getBillType() == null) {
			pstmt.setNull(index, java.sql.Types.NULL);
		} else {
			temp = tmpModel.getBillType();
			temp = temp == null ? "" : temp.trim();
			pstmt.setString(index, temp);
		}
		// 时长;
		index++;
		pstmt.setDouble(index, tmpModel.getJiaBanTimeLength());
		// 系数;
		index++;
		pstmt.setDouble(index, tmpModel.getRatio());
		// 偏差时长;
		index++;
		pstmt.setDouble(index, tmpModel.getOffset());
		// 用于调休时长;
		index++;
		pstmt.setDouble(index, tmpModel.getDiKouTimeLength());
		// 用于计费时长;
		index++;
		pstmt.setDouble(index, tmpModel.getJiFeiTimeLength());
		// 生效日期;
		index++;
		if (tmpModel.getShengXiaoDate() == null) {
			pstmt.setNull(index, java.sql.Types.DATE);
		} else {
			pstmt.setDate(index, tmpModel.getShengXiaoDate());
		}
		// 失效日期;
		index++;
		if (tmpModel.getShiXiaoDate() == null) {
			pstmt.setNull(index, java.sql.Types.DATE);
		} else {
			pstmt.setDate(index, tmpModel.getShiXiaoDate());
		}
		// 作废时长;
		index++;
		pstmt.setDouble(index, tmpModel.getZuoFeiTimeLength());
		// 剩余时长;
		index++;
		pstmt.setDouble(index, tmpModel.getShengYuTimeLength());
		if (actionType == UPDATE) {
			index++;
			// id;
			pstmt.setLong(index, tmpModel.getId());
		}
		return index;
	}

}