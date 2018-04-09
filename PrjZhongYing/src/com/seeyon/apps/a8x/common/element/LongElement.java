package com.seeyon.apps.a8x.common.element;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class LongElement implements IElement<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5237548797939896729L;
	private static final String LONG_FORMAT = "#,###";
	public static final DecimalFormat FORMAT = (DecimalFormat) NumberFormat.getNumberInstance();
	static {
		FORMAT.applyPattern(LONG_FORMAT);
	}
	private static final Long ZERO = Long.valueOf(0);
	private Long value;

	public LongElement() {

	}

	public LongElement(Long i) {
		this.value = i;
	}

	public LongElement(long i) {
		this.value = Long.valueOf(i);
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	public Object getDefaultValue() {
		return ZERO;
	}

	public int getSqlType() {
		return Types.BIGINT;
	}

	public String getName() {
		return "long";
	}

	public Class<Long> getClass_() {
		return Long.class;
	}

	public Long get(ResultSet rs, String name) throws SQLException {
		return Long.valueOf((rs.getLong(name)));
	}

	public Long get(ResultSet rs, int index) throws SQLException {
		return Long.valueOf((rs.getLong(index)));
	}

	public IElement<Long> getElement(ResultSet rs, int index) throws SQLException {
		return new LongElement(rs.getLong(index));
	}

	public void set(PreparedStatement st, int index, Long value) throws SQLException {
		if (value == null) {
			st.setNull(index, getSqlType());
		} else {
			st.setLong(index, value.longValue());
		}
	}

	public void set(PreparedStatement st, int index, long value) throws SQLException {
		st.setLong(index, value);
	}

	public String toString() {
		return value == null ? "" : value.toString();
	}
}
