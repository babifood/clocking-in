package com.seeyon.apps.a8x.common.element;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class DateElement implements IElement<Date> {

	private static final long serialVersionUID = 3722512112859355051L;
	private Date value;

	public DateElement() {

	}

	public DateElement(java.sql.Date date) {
		this.value = date;
	}

	public DateElement(java.util.Date date) {
		this.value = (date == null) ? null : new java.sql.Date(date.getTime());
	}

	public DateElement(java.util.Calendar date) {
		this.value = (date == null) ? null : new java.sql.Date(date.getTime().getTime());
	}

	public Date getValue() {
		return value;
	}

	public void setValue(Date value) {
		this.value = value;
	}

	public String getName() {
		return "date";
	}

	public Class<java.sql.Date> getClass_() {
		return java.sql.Date.class;
	}

	public int getSqlType() {
		return Types.DATE;
	}

	public java.sql.Date get(ResultSet rs, String name) throws SQLException {
		return rs.getDate(name);
	}

	public java.sql.Date get(ResultSet rs, int index) throws SQLException {
		return rs.getDate(index);
	}

	public IElement<java.sql.Date> getElement(ResultSet rs, int index) throws SQLException {
		return new DateElement(rs.getDate(index));
	}

	public void set(PreparedStatement st, int index, java.sql.Date value) throws SQLException {
		if (value == null) {
			st.setNull(index, getSqlType());
		} else {
			st.setDate(index, value);
		}
	}

	public String format(java.util.Date value) {
		// todo
		return "";
	}

	public String format(java.sql.Date value) {
		// todo
		return "";
	}

	public String format(Object value) {
		// todo
		return "";
	}

	public String toString() {
		return value == null ? "" : value.toString();
	}
}
