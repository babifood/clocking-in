package com.seeyon.apps.a8x.common.element;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

public class TimestampElement implements IElement<Timestamp> {

	private static final long serialVersionUID = -7553598816008173887L;

	private Timestamp value;

	public TimestampElement() {

	}

	public TimestampElement(Timestamp timestamp) {
		this.value = timestamp;
	}

	public Timestamp getValue() {
		return value;
	}

	public void setValue(Timestamp value) {
		this.value = value;
	}

	public String getName() {
		return "timestamp";
	}

	public int getSqlType() {
		return Types.TIMESTAMP;
	}

	public Class<java.sql.Timestamp> getClass_() {
		return Timestamp.class;
	}

	public Timestamp get(ResultSet rs, String name) throws SQLException {
		return rs.getTimestamp(name);
	}

	public Timestamp get(ResultSet rs, int index) throws SQLException {
		return rs.getTimestamp(index);
	}

	public IElement<java.sql.Timestamp> getElement(ResultSet rs, int index) throws SQLException {
		return new TimestampElement(rs.getTimestamp(index));
	}

	public void set(PreparedStatement st, int index, Timestamp value) throws SQLException {
		if (value == null) {
			st.setNull(index, getSqlType());
		} else {
			st.setTimestamp(index, value);
		}
	}

	public String toString() {
		return value == null ? "" : value.toString();
	}
}
