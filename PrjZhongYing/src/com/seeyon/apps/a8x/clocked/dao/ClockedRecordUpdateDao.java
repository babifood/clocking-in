package com.seeyon.apps.a8x.clocked.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.seeyon.apps.a8x.clocked.entity.OfficeDaKaRecord;
import com.seeyon.apps.a8x.common.exception.DAOExcuteException;
import com.seeyon.apps.a8x.common.jdbc.DAOContext;
import com.seeyon.apps.a8x.common.jdbc.JdbcDAO;
import com.seeyon.apps.a8x.common.jdbc.RowSetter;

public class ClockedRecordUpdateDao extends JdbcDAO implements RowSetter<OfficeDaKaRecord> {

	public ClockedRecordUpdateDao() {
		super();
	}

	public ClockedRecordUpdateDao(DAOContext context) {
		super(context);
	}

	public int update(OfficeDaKaRecord theModel) throws DAOExcuteException {
		StringBuffer tmpUpdateByKeySQL = new StringBuffer(500);
		tmpUpdateByKeySQL.append("update A8XCLOCKEDRECORD set ");
		tmpUpdateByKeySQL.append("STATE=?,MEMBERID=?,CLOCKDATE=?,STARTTIME=?,ENDTIME=?");
		tmpUpdateByKeySQL.append(",DEPT=?,ORGAN=?,WORKNUM=?,CARDNAME=?,YEAR=?");
		tmpUpdateByKeySQL.append(",MONTH=?,REMARK=?");
		tmpUpdateByKeySQL.append(" where id=?");

		return super.update(theModel, tmpUpdateByKeySQL.toString(), this);
	}

	public void update(List<OfficeDaKaRecord> theModelList) throws DAOExcuteException {
		StringBuffer tmpUpdateByKeySQL = new StringBuffer(500);
		tmpUpdateByKeySQL.append("update A8XCLOCKEDRECORD set ");
		tmpUpdateByKeySQL.append("STATE=?,MEMBERID=?,CLOCKDATE=?,STARTTIME=?,ENDTIME=?");
		tmpUpdateByKeySQL.append(",DEPT=?,ORGAN=?,WORKNUM=?,CARDNAME=?,YEAR=?");
		tmpUpdateByKeySQL.append(",MONTH=?,REMARK=?");
		tmpUpdateByKeySQL.append(" where id=?");

		super.update(theModelList, tmpUpdateByKeySQL.toString(), this);
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
	public int fillStatement(PreparedStatement pstmt, OfficeDaKaRecord theModel) throws SQLException {
		OfficeDaKaRecord tmpModel = theModel;
		String temp = null;
		int index = 0;
		if (actionType == INSERT) {
			// id;
			index++;
			pstmt.setLong(index, tmpModel.getId());
		}
		// 审核状态;
		index++;
		pstmt.setInt(index, tmpModel.getState());
		// 姓名;
		index++;
		if (tmpModel.getMemberID() == null) {
			pstmt.setNull(index, java.sql.Types.NULL);
		} else {
			temp = tmpModel.getMemberID();
			temp = temp == null ? "" : temp.trim();
			pstmt.setString(index, temp);
		}
		// 打卡开始时间;
		index++;
		if (tmpModel.getClockDate() == null) {
			pstmt.setNull(index, java.sql.Types.DATE);
		} else {
			pstmt.setDate(index, tmpModel.getClockDate());
		}
		// 上班时间;
		index++;
		if (tmpModel.getStartTime() == null) {
			pstmt.setNull(index, java.sql.Types.NULL);
		} else {
			temp = tmpModel.getStartTime();
			temp = temp == null ? "" : temp.trim();
			pstmt.setString(index, temp);
		}
		// 上班时间;
		index++;
		if (tmpModel.getEndTime() == null) {
			pstmt.setNull(index, java.sql.Types.NULL);
		} else {
			temp = tmpModel.getEndTime();
			temp = temp == null ? "" : temp.trim();
			pstmt.setString(index, temp);
		}
		// 部门;
		index++;
		if (tmpModel.getDept() == null) {
			pstmt.setNull(index, java.sql.Types.NULL);
		} else {
			temp = tmpModel.getDept();
			temp = temp == null ? "" : temp.trim();
			pstmt.setString(index, temp);
		}
		// 单位机构;
		index++;
		if (tmpModel.getOrgan() == null) {
			pstmt.setNull(index, java.sql.Types.NULL);
		} else {
			temp = tmpModel.getOrgan();
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
		// 考勤机姓名;
		index++;
		if (tmpModel.getCardName() == null) {
			pstmt.setNull(index, java.sql.Types.NULL);
		} else {
			temp = tmpModel.getCardName();
			temp = temp == null ? "" : temp.trim();
			pstmt.setString(index, temp);
		}
		// 年度;
		index++;
		pstmt.setInt(index, tmpModel.getYear());
		// 月份;
		index++;
		pstmt.setInt(index, tmpModel.getMonth());
		// 说明;
		index++;
		if (tmpModel.getRemark() == null) {
			pstmt.setNull(index, java.sql.Types.NULL);
		} else {
			temp = tmpModel.getRemark();
			temp = temp == null ? "" : temp.trim();
			pstmt.setString(index, temp);
		}
		if (actionType == UPDATE) {
			index++;
			// id;
			pstmt.setLong(index, tmpModel.getId());
		}
		return index;
	}

}
