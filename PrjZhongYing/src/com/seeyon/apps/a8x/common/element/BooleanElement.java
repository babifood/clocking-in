package com.seeyon.apps.a8x.common.element;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class BooleanElement implements IElement<Boolean> {
	private static final long serialVersionUID = -6458497832561007237L;

	private Boolean value;

	public BooleanElement() {

	}

	public BooleanElement(Boolean value) {
		this.value = value;
	}

	public BooleanElement(boolean value_) {
		this.value = Boolean.valueOf(value_);
	}

	public Boolean getValue() {
		return value;
	}

	public void setValue(Boolean value) {
		this.value = value;
	}

	public String getFormatValue() {
		return toString();
	}

	public String getName() {
		return "boolean";
	}

	public Class<Boolean> getPrimitiveClass() {
		return boolean.class;
	}

	public Class<Boolean> getClass_() {
		return Boolean.class;
	}

	public int getSqlType() {
		return Types.BIT;
	}

	public Object getDefaultValue() {
		return Boolean.FALSE;
	}

	public Boolean get(ResultSet rs, String name) throws SQLException {
		return rs.getBoolean(name);
	}

	public Boolean get(ResultSet rs, int index) throws SQLException {
		return rs.getBoolean(index);
	}

	public IElement<Boolean> getElement(ResultSet rs, int index) throws SQLException {
		return new BooleanElement(rs.getBoolean(index));
	}

	public void set(PreparedStatement st, int index, Boolean value) throws SQLException {
		if (value == null) {
			st.setNull(index, getSqlType());
		} else {
			st.setBoolean(index, value.booleanValue());
		}
	}

	public void set(PreparedStatement st, int index, boolean value) throws SQLException {
		st.setBoolean(index, value);
	}

	public String toString() {
		return value == null ? "" : value.toString();
	}
}
