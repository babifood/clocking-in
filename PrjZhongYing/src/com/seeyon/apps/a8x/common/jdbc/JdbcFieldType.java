package com.seeyon.apps.a8x.common.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 数据字段的类型
 */
public class JdbcFieldType implements java.io.Serializable {

	private static final long serialVersionUID = 3177305999575411053L;

	private int jdbcType;

	private String typeName;

	public JdbcFieldType() {

	}

	public int getJdbcType() {
		return jdbcType;
	}

	public void setJdbcType(int jdbcType) {
		this.jdbcType = jdbcType;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Override
	public String toString() {
		return "TType [jdbcType=" + jdbcType + ", typeName=" + typeName + "]";
	}

	public String valueOf(Object theValue) {
		String temp = null;
		if (theValue != null) {
			temp = theValue.toString().trim();
		}
		return temp;
	}

	public Object get(ResultSet rs, int index) throws SQLException {
		Object tmp = null;
		if (jdbcType == java.sql.Types.DATE) {
			tmp = rs.getDate(index);
		} else if (jdbcType == java.sql.Types.TIMESTAMP) {
			tmp = rs.getTimestamp(index);
		} else if (jdbcType == java.sql.Types.TIME) {
			tmp = rs.getTime(index);
		} else {
			tmp = rs.getObject(index);
		}
		return tmp;
	}

	public void set(PreparedStatement st, int index, Object value) throws SQLException {
		if (value == null) {
			st.setNull(index, jdbcType);
		} else {
			st.setObject(index, value);
		}
	}

}
