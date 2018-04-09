package com.seeyon.apps.a8x.common.element;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class StringElement implements IElement<String> {
	private static final long serialVersionUID = 1860285610955837289L;
	private String value;

	public StringElement() {
	}

	public StringElement(String s) {
		this.value = s;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return "string";
	}

	public Class<String> getClass_() {
		return String.class;
	}

	public int getSqlType() {
		return Types.VARCHAR;
	}

	public String get(ResultSet rs, String name) throws SQLException {
		return rs.getString(name);
	}

	public String get(ResultSet rs, int index) throws SQLException {
		return rs.getString(index);
	}

	public IElement<String> getElement(ResultSet rs, int index) throws SQLException {
		return new StringElement(rs.getString(index));
	}

	public void set(PreparedStatement st, int index, String value) throws SQLException {
		if (value == null) {
			st.setNull(index, getSqlType());
		} else {
			st.setString(index, value.trim());
		}
	}

	public String toString() {
		if (value == null) {
			return null;
		} else {
			return value.toString();
		}
	}
}
