package com.seeyon.apps.a8x.exchange.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.seeyon.apps.a8x.common.domain.QueryCondition;
import com.seeyon.apps.a8x.common.domain.QueryResult;
import com.seeyon.apps.a8x.common.exception.DAOExcuteException;
import com.seeyon.apps.a8x.common.jdbc.DAOContext;
import com.seeyon.apps.a8x.common.jdbc.JdbcDAO;
import com.seeyon.apps.a8x.common.jdbc.JdbcDaoUtil;
import com.seeyon.apps.a8x.common.jdbc.RowGetter;
import com.seeyon.apps.a8x.common.jdbc.RowSetter;
import com.seeyon.apps.a8x.common.parameter.IParameter;
import com.seeyon.apps.a8x.common.parameter.SqlParameter;
import com.seeyon.apps.a8x.exchange.entity.ExchangeLog;

public class ExchangeLogDao extends JdbcDAO implements RowGetter<ExchangeLog>, RowSetter<ExchangeLog> {

	public ExchangeLogDao() {
		super();
	}

	public ExchangeLogDao(DAOContext context) {
		super(context);
	}

	public int updateByFormRecordId(ExchangeLog theModel) throws DAOExcuteException {
		String sql = "update A8X_EXCHANGE_LOG set operate_count=operate_count+1,operate_flag=?,result=?,modify_id=?,modify_date=?,sort_id=?,step=?,form_code=? where form_record_id=?";
		IParameter[] params = new IParameter[8];
		params[0] = new SqlParameter(theModel.getOperateFlag());
		params[1] = new SqlParameter(theModel.getResult());
		params[2] = new SqlParameter(theModel.getModify_id());
		params[3] = new SqlParameter(theModel.getModify_date());
		params[4] = new SqlParameter(System.currentTimeMillis());
		params[5] = new SqlParameter(theModel.getStep());
		params[6] = new SqlParameter(theModel.getFormCode());
		params[7] = new SqlParameter(theModel.getFormRecordId());

		return JdbcDaoUtil.singleExecute(context, sql, params);
	}

	public boolean isExisted(long form_record_id) throws DAOExcuteException {
		String sql = "select count(*) from A8X_EXCHANGE_LOG where form_record_id=?";
		return JdbcDaoUtil.hasDataExistedByCount(context, sql, new SqlParameter(form_record_id));
	}

	public int create(ExchangeLog theModel) throws DAOExcuteException {
		return super.create(theModel, InsertSQL, this);
	}

	public int update(ExchangeLog theModel) throws DAOExcuteException {
		return super.update(theModel, UpdateByKeySQL, this);
	}

	public int delete(Long theKey) throws DAOExcuteException {
		IParameter tmpParameter = null;
		// id;
		tmpParameter = new SqlParameter(theKey);
		return super.delete(DeleteByKeySQL, tmpParameter);
	}

	public ExchangeLog read(Long theKey) throws DAOExcuteException {
		IParameter tmpParameter = null;
		// id;
		tmpParameter = new SqlParameter(theKey);
		return super.read(FindByKeySQL, this, tmpParameter);
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
	public ExchangeLog convertResultSet(ResultSet rs) throws SQLException {
		int index = 0;
		ExchangeLog tmpModel = new ExchangeLog();
		// id;
		index++;
		tmpModel.setId(rs.getLong(index));
		// 模块类型;
		index++;
		tmpModel.setModuleType(rs.getString(index));
		// 模块名称;
		index++;
		tmpModel.setModuleName(rs.getString(index));
		// 模板编号;
		index++;
		tmpModel.setTempleteCode(rs.getString(index));
		// 表单实体ID;
		index++;
		tmpModel.setFormRecordId(rs.getLong(index));
		// summaryId;
		index++;
		tmpModel.setSummaryId(rs.getLong(index));
		// formAppId;
		index++;
		tmpModel.setFormAppId(rs.getLong(index));
		// 表单编号;
		index++;
		tmpModel.setFormCode(rs.getString(index));
		// 表单日期;
		index++;
		tmpModel.setFormDate(rs.getDate(index));
		// 表单简要;
		index++;
		tmpModel.setFormInfo(rs.getString(index));
		// 执行状况;
		index++;
		tmpModel.setOperateFlag(rs.getString(index));
		// 执行结果;
		index++;
		tmpModel.setResult(rs.getString(index));
		// 步骤说明;
		index++;
		tmpModel.setStep(rs.getString(index));
		// 执行次数;
		index++;
		tmpModel.setOperateCount(rs.getInt(index));
		// 处理结果;
		index++;
		tmpModel.setAction(rs.getString(index));
		// 处理结果标志;
		index++;
		tmpModel.setActionFlag(rs.getInt(index));
		// 排序;
		index++;
		tmpModel.setSort(rs.getLong(index));
		// create_id;
		index++;
		tmpModel.setCreate_id(rs.getLong(index));
		// create_date;
		index++;
		tmpModel.setCreate_date(rs.getTimestamp(index));
		// modify_id;
		index++;
		tmpModel.setModify_id(rs.getLong(index));
		// modify_date;
		index++;
		tmpModel.setModify_date(rs.getTimestamp(index));
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
	public int fillStatement(PreparedStatement pstmt, ExchangeLog theModel) throws SQLException {
		ExchangeLog tmpModel = theModel;
		String temp = null;
		int index = 0;
		if (actionType == INSERT) {
			// id;
			index++;
			pstmt.setLong(index, tmpModel.getId());
		}
		// 模块类型;
		index++;
		temp = tmpModel.getModuleType();
		temp = temp == null ? "" : temp.trim();
		pstmt.setString(index, temp);
		// 模块名称;
		index++;
		temp = tmpModel.getModuleName();
		temp = temp == null ? "" : temp.trim();
		pstmt.setString(index, temp);
		// 模板编号;
		index++;
		temp = tmpModel.getTempleteCode();
		temp = temp == null ? "" : temp.trim();
		pstmt.setString(index, temp);
		// 表单实体ID;
		index++;
		pstmt.setLong(index, tmpModel.getFormRecordId());
		// summaryId;
		index++;
		pstmt.setLong(index, tmpModel.getSummaryId());
		// formAppId;
		index++;
		pstmt.setLong(index, tmpModel.getFormAppId());
		// 表单编号;
		index++;
		temp = tmpModel.getFormCode();
		temp = temp == null ? "" : temp.trim();
		pstmt.setString(index, temp);
		// 表单日期;
		index++;
		if (tmpModel.getFormDate() == null) {
			pstmt.setNull(index, java.sql.Types.DATE);
		} else {
			pstmt.setDate(index, tmpModel.getFormDate());
		}
		// 表单简要;
		index++;
		temp = tmpModel.getFormInfo();
		temp = temp == null ? "" : temp.trim();
		pstmt.setString(index, temp);
		// 执行状况;
		index++;
		temp = tmpModel.getOperateFlag();
		temp = temp == null ? "" : temp.trim();
		pstmt.setString(index, temp);
		// 执行结果;
		index++;
		temp = tmpModel.getResult();
		temp = temp == null ? "" : temp.trim();
		pstmt.setString(index, temp);
		// 步骤说明;
		index++;
		temp = tmpModel.getStep();
		temp = temp == null ? "" : temp.trim();
		pstmt.setString(index, temp);
		// 执行次数;
		index++;
		pstmt.setInt(index, tmpModel.getOperateCount());
		// 处理结果;
		index++;
		temp = tmpModel.getAction();
		temp = temp == null ? "" : temp.trim();
		pstmt.setString(index, temp);
		// 处理结果标志;
		index++;
		pstmt.setInt(index, tmpModel.getActionFlag());
		// 排序;
		index++;
		pstmt.setLong(index, tmpModel.getSort());
		if (actionType == INSERT) {
			// create_id;
			index++;
			pstmt.setLong(index, tmpModel.getCreate_id());
			// create_date;
			index++;
			if (tmpModel.getCreate_date() == null) {
				pstmt.setNull(index, java.sql.Types.NULL);
			} else {
				pstmt.setTimestamp(index, tmpModel.getCreate_date());
			}
		}
		// modify_id;
		index++;
		pstmt.setLong(index, tmpModel.getModify_id());
		// modify_date;
		index++;
		if (tmpModel.getModify_date() == null) {
			pstmt.setNull(index, java.sql.Types.NULL);
		} else {
			pstmt.setTimestamp(index, tmpModel.getModify_date());
		}
		if (actionType == UPDATE) {
			index++;
			// id;
			pstmt.setLong(index, tmpModel.getId());
		}
		return index;
	}

	/**
	 * 获取查询的SQL语句
	 * 
	 * @param condition
	 *            QueryCondition
	 * @return String
	 */
	private String getSelectSQL(QueryCondition condition) {
		String tmpSQL = SelectSQL;
		if (condition != null) {
			String tmp = condition.getWhereConditionString();
			tmpSQL += tmp;
		}
		return tmpSQL;
	}

	private final static String SelectSQL;
	private final static String FindByKeySQL;
	private final static String InsertSQL;
	private final static String UpdateByKeySQL;
	private final static String DeleteByKeySQL = "delete from A8X_EXCHANGE_LOG where id=?";
	static {
		// Select sql
		StringBuffer tmpSelectSQL = new StringBuffer(500);
		tmpSelectSQL.append("select ");
		tmpSelectSQL.append("id,module_type,module_name,templete_code,form_record_id");
		tmpSelectSQL.append(",summaryid,formappid,form_code,form_date,form_info");
		tmpSelectSQL.append(",operate_flag,result,step,operate_count,action");
		tmpSelectSQL.append(",action_flag,sort_id,create_id,create_date,modify_id");
		tmpSelectSQL.append(",modify_date");
		tmpSelectSQL.append(" from");
		tmpSelectSQL.append(" A8X_EXCHANGE_LOG");
		SelectSQL = tmpSelectSQL.toString();

		// Find By Key SQL
		tmpSelectSQL.append(" where id=?");
		FindByKeySQL = tmpSelectSQL.toString();

		// Insert SQL
		StringBuffer tmpInsertSQL = new StringBuffer(500);
		tmpInsertSQL.append("insert into A8X_EXCHANGE_LOG(");
		tmpInsertSQL.append("id,module_type,module_name,templete_code,form_record_id");
		tmpInsertSQL.append(",summaryid,formappid,form_code,form_date,form_info");
		tmpInsertSQL.append(",operate_flag,result,step,operate_count,action");
		tmpInsertSQL.append(",action_flag,sort_id,create_id,create_date,modify_id");
		tmpInsertSQL.append(",modify_date");
		tmpInsertSQL.append(") values (");
		tmpInsertSQL.append("?,?,?,?,?");
		tmpInsertSQL.append(",?,?,?,?,?");
		tmpInsertSQL.append(",?,?,?,?,?");
		tmpInsertSQL.append(",?,?,?,?,?");
		tmpInsertSQL.append(",?");
		tmpInsertSQL.append(")");
		InsertSQL = tmpInsertSQL.toString();

		// Update By Key SQL
		StringBuffer tmpUpdateByKeySQL = new StringBuffer(500);
		tmpUpdateByKeySQL.append("update A8X_EXCHANGE_LOG set ");
		tmpUpdateByKeySQL.append("module_type=?,module_name=?,templete_code=?,form_record_id=?");
		tmpUpdateByKeySQL.append(",summaryid=?,formappid=?,form_code=?,form_date=?,form_info=?");
		tmpUpdateByKeySQL.append(",operate_flag=?,result=?,step=?,operate_count=?,action=?");
		tmpUpdateByKeySQL.append(",action_flag=?,sort_id=?,modify_id=?,modify_date=?");
		tmpUpdateByKeySQL.append(" where id=?");
		UpdateByKeySQL = tmpUpdateByKeySQL.toString();
	}
}