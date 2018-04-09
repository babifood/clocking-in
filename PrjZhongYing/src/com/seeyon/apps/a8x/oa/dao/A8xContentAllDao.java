package com.seeyon.apps.a8x.oa.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.seeyon.apps.a8x.common.domain.QueryCondition;
import com.seeyon.apps.a8x.common.domain.QueryResult;
import com.seeyon.apps.a8x.common.exception.DAOExcuteException;
import com.seeyon.apps.a8x.common.jdbc.DAOContext;
import com.seeyon.apps.a8x.common.jdbc.JdbcDAO;
import com.seeyon.apps.a8x.common.jdbc.RowGetter;
import com.seeyon.apps.a8x.common.jdbc.RowSetter;
import com.seeyon.apps.a8x.common.parameter.IParameter;
import com.seeyon.apps.a8x.common.parameter.SqlParameter;
import com.seeyon.apps.a8x.oa.entity.A8xContentAll;

public class A8xContentAllDao extends JdbcDAO implements RowGetter<A8xContentAll>, RowSetter<A8xContentAll> {

	public A8xContentAllDao() {
		super();
	}

	public A8xContentAllDao(DAOContext context) {
		super(context);
	}

	public int create(A8xContentAll theModel) throws DAOExcuteException {
		return super.create(theModel, InsertSQL, this);
	}

	public void create(List<A8xContentAll> theModelList) throws DAOExcuteException {
		super.create(theModelList, InsertSQL, this);
	}

	public int update(A8xContentAll theModel) throws DAOExcuteException {
		return super.update(theModel, UpdateByKeySQL, this);
	}

	public int delete(Long theKey) throws DAOExcuteException {
		IParameter tmpParameter = null;
		// id;
		tmpParameter = new SqlParameter(theKey);
		return super.delete(DeleteByKeySQL, tmpParameter);
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
	public A8xContentAll convertResultSet(ResultSet rs) throws SQLException {
		int index = 0;
		A8xContentAll tmpModel = new A8xContentAll();
		// id;
		index++;
		tmpModel.setId(rs.getLong(index));
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
		// module_type;
		index++;
		tmpModel.setModule_type(rs.getInt(index));
		// module_id;
		index++;
		tmpModel.setModule_id(rs.getLong(index));
		// module_template_id;
		index++;
		tmpModel.setModule_template_id(rs.getLong(index));
		// content_type;
		index++;
		tmpModel.setContent_type(rs.getInt(index));
		// content;
		index++;
		tmpModel.setContent(rs.getString(index));
		// content_data_id;
		index++;
		tmpModel.setContent_data_id(rs.getLong(index));
		// content_template_id;
		index++;
		tmpModel.setContent_template_id(rs.getLong(index));
		// title;
		index++;
		tmpModel.setTitle(rs.getString(index));
		// sort;
		index++;
		tmpModel.setSort(rs.getInt(index));
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
	public int fillStatement(PreparedStatement pstmt, A8xContentAll theModel) throws SQLException {
		A8xContentAll tmpModel = theModel;
		String temp = null;
		int index = 0;
		// id;
		index++;
		pstmt.setLong(index, tmpModel.getId());
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
		// module_type;
		index++;
		pstmt.setInt(index, tmpModel.getModule_type());
		// module_id;
		index++;
		pstmt.setLong(index, tmpModel.getModule_id());
		// module_template_id;
		index++;
		pstmt.setLong(index, tmpModel.getModule_template_id());
		// content_type;
		index++;
		pstmt.setInt(index, tmpModel.getContent_type());
		// content;
		index++;
		temp = tmpModel.getContent();
		temp = temp == null ? "" : temp.trim();
		pstmt.setString(index, temp);
		// content_data_id;
		index++;
		pstmt.setLong(index, tmpModel.getContent_data_id());
		// content_template_id;
		index++;
		pstmt.setLong(index, tmpModel.getContent_template_id());
		// title;
		index++;
		temp = tmpModel.getTitle();
		temp = temp == null ? "" : temp.trim();
		pstmt.setString(index, temp);
		// sort;
		index++;
		pstmt.setInt(index, tmpModel.getSort());
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
	private final static String InsertSQL;
	private final static String UpdateByKeySQL;
	private final static String DeleteByKeySQL = "delete from CTP_CONTENT_ALL where id=?";
	static {
		// Select sql
		StringBuffer tmpSelectSQL = new StringBuffer(500);
		tmpSelectSQL.append("select ");
		tmpSelectSQL.append("id,create_id,create_date,modify_id,modify_date");
		tmpSelectSQL.append(",module_type,module_id,module_template_id,content_type,content");
		tmpSelectSQL.append(",content_data_id,content_template_id,title,sort");
		tmpSelectSQL.append(" from");
		tmpSelectSQL.append(" CTP_CONTENT_ALL");

		SelectSQL = tmpSelectSQL.toString();

		// Insert SQL
		StringBuffer tmpInsertSQL = new StringBuffer(500);
		tmpInsertSQL.append("insert into CTP_CONTENT_ALL(");
		tmpInsertSQL.append("id,create_id,create_date,modify_id,modify_date");
		tmpInsertSQL.append(",module_type,module_id,module_template_id,content_type,content");
		tmpInsertSQL.append(",content_data_id,content_template_id,title,sort");
		tmpInsertSQL.append(") values (");
		tmpInsertSQL.append("?,?,?,?,?");
		tmpInsertSQL.append(",?,?,?,?,?");
		tmpInsertSQL.append(",?,?,?,?");
		tmpInsertSQL.append(")");
		InsertSQL = tmpInsertSQL.toString();

		// Update By Key SQL
		StringBuffer tmpUpdateByKeySQL = new StringBuffer(500);
		tmpUpdateByKeySQL.append("update CTP_CONTENT_ALL set ");
		tmpUpdateByKeySQL.append("id=?,create_id=?,create_date=?,modify_id=?,modify_date=?");
		tmpUpdateByKeySQL.append(",module_type=?,module_id=?,module_template_id=?,content_type=?,content=?");
		tmpUpdateByKeySQL.append(",content_data_id=?,content_template_id=?,title=?,sort=?");
		tmpUpdateByKeySQL.append(" where id=?");
		UpdateByKeySQL = tmpUpdateByKeySQL.toString();

	}
}