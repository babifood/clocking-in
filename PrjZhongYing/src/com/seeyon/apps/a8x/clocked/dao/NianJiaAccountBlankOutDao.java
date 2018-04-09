package com.seeyon.apps.a8x.clocked.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.seeyon.apps.a8x.clocked.entity.NianJiaAccount;
import com.seeyon.apps.a8x.common.exception.DAOExcuteException;
import com.seeyon.apps.a8x.common.jdbc.DAOContext;
import com.seeyon.apps.a8x.common.jdbc.JdbcDAO;
import com.seeyon.apps.a8x.common.jdbc.RowSetter;

/**
 * 年假账--作废处理
 *
 */
public class NianJiaAccountBlankOutDao extends JdbcDAO implements RowSetter<NianJiaAccount> {

	public NianJiaAccountBlankOutDao() {
		super();
	}

	public NianJiaAccountBlankOutDao(DAOContext context) {
		super(context);
	}

	public void update(List<NianJiaAccount> theList) throws DAOExcuteException {
		StringBuffer tmpUpdateByKeySQL = new StringBuffer(500);
		tmpUpdateByKeySQL.append("update A8XNIANJIAACCOUNT set ");
		tmpUpdateByKeySQL.append("ZUOFEITIMELENGTH=ZUOFEITIMELENGTH+?,SHENGYUTIMELENGTH=SHENGYUTIMELENGTH-?");
		tmpUpdateByKeySQL.append(" where id=?");
		super.update(theList, tmpUpdateByKeySQL.toString(), this);
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
	public int fillStatement(PreparedStatement pstmt, NianJiaAccount theModel) throws SQLException {
		NianJiaAccount tmpModel = theModel;
		int index = 0;
		// 本次作废数;
		index++;
		pstmt.setDouble(index, tmpModel.getWritedownValue());
		// 本次作废数;
		index++;
		pstmt.setDouble(index, tmpModel.getWritedownValue());

		index++;
		// id;
		pstmt.setLong(index, tmpModel.getId());
		return index;
	}

}