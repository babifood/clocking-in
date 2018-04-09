package com.seeyon.apps.a8x.clocked.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.seeyon.apps.a8x.clocked.entity.Person;
import com.seeyon.apps.a8x.common.exception.DAOExcuteException;
import com.seeyon.apps.a8x.common.jdbc.DAOContext;
import com.seeyon.apps.a8x.common.jdbc.JdbcDAO;
import com.seeyon.apps.a8x.common.jdbc.RowGetter;
import com.seeyon.apps.a8x.common.parameter.SqlParameter;

public class PersonDao extends JdbcDAO implements RowGetter<Person> {

	public PersonDao() {
		super();
	}

	public PersonDao(DAOContext context) {
		super(context);
	}

	public Person getByMememberID(long theMemeberID) throws DAOExcuteException {
		StringBuffer tmpSelectSQL = new StringBuffer(500);
		tmpSelectSQL.append("select ");
		tmpSelectSQL.append("ID,MODIFY_DATE,CARDNUM,MEMBERID,WORKNUM");
		tmpSelectSQL.append(",DEPT,CARDNAME,ORGAN,FLAG,MEMBERNAME");
		tmpSelectSQL.append(",DEPT_NAME,DEPT_PATH,TYPENAME,TYPECODE");
		tmpSelectSQL.append(" from");
		tmpSelectSQL.append(" A8XPERSON");
		tmpSelectSQL.append(" where FLAG='1' and MEMBERID=?");
		return super.read(tmpSelectSQL.toString(), this, new SqlParameter(String.valueOf(theMemeberID)));
	}

	public List<Person> load() throws DAOExcuteException {

		StringBuffer tmpSelectSQL = new StringBuffer(500);
		tmpSelectSQL.append("select ");
		tmpSelectSQL.append("ID,MODIFY_DATE,CARDNUM,MEMBERID,WORKNUM");
		tmpSelectSQL.append(",DEPT,CARDNAME,ORGAN,FLAG,MEMBERNAME");
		tmpSelectSQL.append(",DEPT_NAME,DEPT_PATH,TYPENAME,TYPECODE");
		tmpSelectSQL.append(" from");
		tmpSelectSQL.append(" A8XPERSON");
		tmpSelectSQL.append(" where FLAG='1'");
		return super.load(tmpSelectSQL.toString(), this);
	}

	/**
	 * 获取ResultSet中的数据或Cache中数据
	 * 
	 * @param rs
	 *            ResultSet
	 * @throws SQLException
	 * @return IValueObject
	 */
	public Person convertResultSet(ResultSet rs) throws SQLException {
		int index = 0;
		Person tmpModel = new Person();
		// id;
		index++;
		tmpModel.setId(rs.getLong(index));
		// 修改时间;
		index++;
		tmpModel.setModify_date(rs.getTimestamp(index));
		// 考勤卡号;
		index++;
		tmpModel.setCardNum(rs.getString(index));
		// 员工ID;
		index++;
		tmpModel.setMemberID(rs.getString(index));
		// 工号;
		index++;
		tmpModel.setWorkNum(rs.getString(index));
		// 部门;
		index++;
		tmpModel.setDept(rs.getString(index));
		// 考勤机姓名;
		index++;
		tmpModel.setCardName(rs.getString(index));
		// 单位机构;
		index++;
		tmpModel.setOrgan(rs.getString(index));
		// 是否生效;
		index++;
		tmpModel.setFlag(rs.getString(index));
		// 姓名;
		index++;
		tmpModel.setMemberName(rs.getString(index));
		// 部门名称;
		index++;
		tmpModel.setDept_name(rs.getString(index));
		// 部门路径;
		index++;
		tmpModel.setDept_path(rs.getString(index));
		// 考勤类型名称;
		index++;
		tmpModel.setTypeName(rs.getString(index));
		// 考勤类型编码;
		index++;
		tmpModel.setTypeCode(rs.getString(index));
		return tmpModel;
	}

}