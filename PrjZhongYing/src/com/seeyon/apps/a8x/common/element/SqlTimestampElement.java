package com.seeyon.apps.a8x.common.element;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;

public class SqlTimestampElement implements IElement<Timestamp> {

	private static final long serialVersionUID = -4198126094181847433L;
	public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final SimpleDateFormat FORMAT = new SimpleDateFormat(TIMESTAMP_FORMAT);

	private Timestamp value;

	public SqlTimestampElement() {

	}

	public SqlTimestampElement(Timestamp timestamp) {
		this.value = timestamp;
	}

	public Timestamp getValue() {
		return value;
	}

	public void setValue(Timestamp value) {
		this.value = value;
	}

	public String getFormatValue() {
		String tmp = null;
		if (value != null) {
			tmp = format(value);
		} else {
			tmp = "";
		}
		return tmp;
	}

	public String getName() {
		return "timestamp";
	}

	public int getSqlType() {
		return Types.TIMESTAMP;
	}

	public Class<Timestamp> getClass_() {
		return Timestamp.class;
	}

	public String format(Object value) {
		return format((Timestamp) value);
	}

	public String format(Timestamp value) {
		String tmp = null;
		if (value != null) {
			tmp = FORMAT.format(value);
		} else {
			tmp = "";
		}
		return tmp;
	}

	public Timestamp get(ResultSet rs, String name) throws SQLException {
		return rs.getTimestamp(name);
	}

	public Timestamp get(ResultSet rs, int index) throws SQLException {
		return rs.getTimestamp(index);
	}

	public IElement<Timestamp> getElement(ResultSet rs, int index) throws SQLException {
		return new SqlTimestampElement(rs.getTimestamp(index));
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
