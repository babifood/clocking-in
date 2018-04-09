package com.seeyon.apps.a8x.common.element;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Types;

public class TimeElement implements IElement<Time> {

	private static final long serialVersionUID = 8254297033002969459L;

	private Time value;

	public TimeElement() {

	}

	public TimeElement(java.sql.Time time) {
		this.value = time;
	}

	public Time getValue() {
		return value;
	}

	public void setValue(Time value) {
		this.value = value;
	}

	public String getName() {
		return "time";
	}

	public Class<java.sql.Time> getClass_() {
		return Time.class;
	}

	public int getSqlType() {
		return Types.TIME;
	}

	public Time get(ResultSet rs, String name) throws SQLException {
		return rs.getTime(name);
	}

	public Time get(ResultSet rs, int index) throws SQLException {
		return rs.getTime(index);
	}

	public IElement<java.sql.Time> getElement(ResultSet rs, int index) throws SQLException {
		return new TimeElement(rs.getTime(index));
	}

	public void set(PreparedStatement st, int index, Time value) throws SQLException {
		if (value == null) {
			st.setNull(index, getSqlType());
		} else {
			st.setTime(index, value);
		}
	}

	public String toString() {
		return value == null ? "" : value.toString();
	}
}
