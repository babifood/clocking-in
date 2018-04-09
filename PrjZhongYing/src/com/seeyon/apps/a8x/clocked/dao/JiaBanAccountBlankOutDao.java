package com.seeyon.apps.a8x.clocked.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.seeyon.apps.a8x.clocked.entity.JiaBanAccount;
import com.seeyon.apps.a8x.common.exception.DAOExcuteException;
import com.seeyon.apps.a8x.common.jdbc.DAOContext;
import com.seeyon.apps.a8x.common.jdbc.JdbcDAO;
import com.seeyon.apps.a8x.common.jdbc.RowSetter;

/**
 * 加班账--作废处理
 *
 */
public class JiaBanAccountBlankOutDao extends JdbcDAO implements RowSetter<JiaBanAccount> {
	

	public JiaBanAccountBlankOutDao() {
		super();
	}

	public JiaBanAccountBlankOutDao(DAOContext context) {
		super(context);
	}

	public int update(JiaBanAccount theModel) throws DAOExcuteException {
		// Update By Key SQL
		StringBuffer tmpUpdateByKeySQL = new StringBuffer(500);
		tmpUpdateByKeySQL.append("update A8XJIABANACCOUNT set ");
		tmpUpdateByKeySQL.append("DIKOUTIMELENGTH=ZUOFEITIMELENGTH+?,SHENGYUTIMELENGTH=SHENGYUTIMELENGTH-?");
		tmpUpdateByKeySQL.append(" where id=?");
		return super.update(theModel, tmpUpdateByKeySQL.toString(), this);
	}

	public void update(List<JiaBanAccount> theList) throws DAOExcuteException {
		// Update By Key SQL
		StringBuffer tmpUpdateByKeySQL = new StringBuffer(500);
		tmpUpdateByKeySQL.append("update A8XJIABANACCOUNT set ");
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
	public int fillStatement(PreparedStatement pstmt, JiaBanAccount theModel) throws SQLException {
		JiaBanAccount tmpModel = theModel;
		int index = 0;
		// 作废时长;
		index++;
		pstmt.setDouble(index, tmpModel.getWritedownValue());
		// 剩余时长;
		index++;
		pstmt.setDouble(index, tmpModel.getWritedownValue());

		index++;
		// id;
		pstmt.setLong(index, tmpModel.getId());
		return index;
	}
}
