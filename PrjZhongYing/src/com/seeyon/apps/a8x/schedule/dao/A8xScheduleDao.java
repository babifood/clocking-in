package com.seeyon.apps.a8x.schedule.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import com.seeyon.apps.a8x.common.exception.DAOExcuteException;
import com.seeyon.apps.a8x.common.jdbc.DAOContext;
import com.seeyon.apps.a8x.common.jdbc.JdbcDAO;
import com.seeyon.apps.a8x.common.jdbc.RowGetter;
import com.seeyon.apps.a8x.common.jdbc.RowSetter;
import com.seeyon.apps.a8x.common.parameter.IParameter;
import com.seeyon.apps.a8x.common.parameter.SqlParameter;
import com.seeyon.apps.a8x.schedule.model.A8xSchedule;

public class A8xScheduleDao extends JdbcDAO implements RowGetter<A8xSchedule>, RowSetter<A8xSchedule> {

	public A8xScheduleDao() {
		super();
	}

	public A8xScheduleDao(DAOContext context) {
		super(context);
	}

	public int create(A8xSchedule theModel) throws DAOExcuteException {
		return super.create(theModel, InsertSQL, this);
	}

	public int update(A8xSchedule theModel) throws DAOExcuteException {
		return super.update(theModel, UpdateByKeySQL, this);
	}

	public A8xSchedule read(String theKey) throws DAOExcuteException {
		IParameter tmpParameter = null;
		// 定时器标识;
		tmpParameter = new SqlParameter(theKey);
		return super.read(FindByKeySQL, this, tmpParameter);
	}

	public List<A8xSchedule> loadALL() throws DAOExcuteException {
		return super.load(SelectSQL, this);
	}

	/**
	 * 获取ResultSet中的数据或Cache中数据
	 * 
	 * @param rs
	 *            ResultSet
	 * @throws SQLException
	 * @return IValueObject
	 */
	public A8xSchedule convertResultSet(ResultSet rs) throws SQLException {
		int index = 0;
		A8xSchedule tmpModel = new A8xSchedule();
		// 定时器标识;
		index++;
		tmpModel.setSchedule_id(rs.getString(index));
		// 定时器名称;
		index++;
		tmpModel.setSchedule_name(rs.getString(index));
		// 定时器描述;
		index++;
		tmpModel.setSchedule_desc(rs.getString(index));
		// 是否启用;
		index++;
		String s = rs.getString(index) == null ? "N" : rs.getString(index).trim().toUpperCase();
		tmpModel.setEnable(s.equals("Y"));
		// 运行编码;
		index++;
		tmpModel.setQuartz_code(rs.getString(index));
		// 运行规则;
		index++;
		tmpModel.setQuartz_rule(rs.getString(index));
		// 任务标识;
		index++;
		tmpModel.setFlow_id(rs.getString(index));
		// 任务名称;
		index++;
		tmpModel.setFlow_name(rs.getString(index));
		// 创建时间;
		index++;
		tmpModel.setCreate_time(rs.getTimestamp(index));
		return tmpModel;
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
	public int fillStatement(PreparedStatement pstmt, A8xSchedule theModel) throws SQLException {
		A8xSchedule tmpModel = theModel;
		String temp = null;
		int index = 0;
		// 定时器标识;
		index++;
		temp = tmpModel.getSchedule_id();
		temp = temp == null ? "" : temp.trim();
		pstmt.setString(index, temp);
		// 定时器名称;
		index++;
		temp = tmpModel.getSchedule_name();
		temp = temp == null ? "" : temp.trim();
		pstmt.setString(index, temp);
		// 定时器描述;
		index++;
		temp = tmpModel.getSchedule_desc();
		temp = temp == null ? "" : temp.trim();
		pstmt.setString(index, temp);
		// 是否启用;
		index++;
		temp = tmpModel.isEnable() == true ? "Y" : "N";
		temp = temp == null ? "" : temp.trim();
		pstmt.setString(index, temp);
		// 运行编码;
		index++;
		temp = tmpModel.getQuartz_code();
		temp = temp == null ? "" : temp.trim();
		pstmt.setString(index, temp);
		// 运行规则;
		index++;
		temp = tmpModel.getQuartz_rule();
		temp = temp == null ? "" : temp.trim();
		pstmt.setString(index, temp);
		// 任务标识;
		index++;
		temp = tmpModel.getFlow_id();
		temp = temp == null ? "" : temp.trim();
		pstmt.setString(index, temp);
		// 任务名称;
		index++;
		temp = tmpModel.getFlow_name();
		temp = temp == null ? "" : temp.trim();
		pstmt.setString(index, temp);

		// 创建时间;
		index++;
		pstmt.setTimestamp(index, new Timestamp(System.currentTimeMillis()));

		if (actionType == UPDATE) {
			index++;
			// 定时器标识;
			pstmt.setString(index, tmpModel.getSchedule_id());
		}
		return index;
	}

	private final static String SelectSQL;
	private final static String FindByKeySQL;
	private final static String InsertSQL;
	private final static String UpdateByKeySQL;
	static {
		// Select sql
		StringBuffer tmpSelectSQL = new StringBuffer(500);
		tmpSelectSQL.append("select ");
		tmpSelectSQL.append("schedule_id,schedule_name,schedule_desc,is_enable,quartz_code");
		tmpSelectSQL.append(",quartz_rule,flow_id,flow_name,create_time");
		tmpSelectSQL.append(" from");
		tmpSelectSQL.append(" A8X_SCHEDULE");

		SelectSQL = tmpSelectSQL.toString();

		// Find By Key SQL
		tmpSelectSQL.append(" where schedule_id=?");
		FindByKeySQL = tmpSelectSQL.toString();

		// Insert SQL
		StringBuffer tmpInsertSQL = new StringBuffer(500);
		tmpInsertSQL.append("insert into A8X_SCHEDULE(");
		tmpInsertSQL.append("schedule_id,schedule_name,schedule_desc,is_enable,quartz_code");
		tmpInsertSQL.append(",quartz_rule,flow_id,flow_name,create_time");
		tmpInsertSQL.append(") values (");
		tmpInsertSQL.append("?,?,?,?,?");
		tmpInsertSQL.append(",?,?,?,?");
		tmpInsertSQL.append(")");
		InsertSQL = tmpInsertSQL.toString();

		// Update By Key SQL
		StringBuffer tmpUpdateByKeySQL = new StringBuffer(500);
		tmpUpdateByKeySQL.append("update A8X_SCHEDULE set ");
		tmpUpdateByKeySQL.append("schedule_id=?,schedule_name=?,schedule_desc=?,is_enable=?,quartz_code=?");
		tmpUpdateByKeySQL.append(",quartz_rule=?,flow_id=?,flow_name=?,create_time=?");
		tmpUpdateByKeySQL.append(" where schedule_id=?");
		UpdateByKeySQL = tmpUpdateByKeySQL.toString();

	}
}