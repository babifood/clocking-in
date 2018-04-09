package com.seeyon.apps.a8x.common.element;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class IntegerElement implements IElement<Integer> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6701007752769139340L;

	private static final Integer ZERO = Integer.valueOf(0);

	private Integer value;

	public IntegerElement() {

	}

	public IntegerElement(Integer i) {
		this.value = i;
	}

	public IntegerElement(int i) {
		this.value = Integer.valueOf(i);
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Object getDefaultValue() {
		return ZERO;
	}

	public String getName() {
		return "integer";
	}

	public Class<Integer> getClass_() {
		return Integer.class;
	}

	public int getSqlType() {
		return Types.INTEGER;
	}

	public Integer get(ResultSet rs, String name) throws SQLException {
		return Integer.valueOf((rs.getInt(name)));
	}

	public Integer get(ResultSet rs, int index) throws SQLException {
		return Integer.valueOf((rs.getInt(index)));
	}

	public IElement<Integer> getElement(ResultSet rs, int index) throws SQLException {
		return new IntegerElement(rs.getInt(index));
	}

	public void set(PreparedStatement st, int index, Integer value) throws SQLException {
		if (value == null) {
			st.setNull(index, getSqlType());
		} else {
			st.setInt(index, value.intValue());
		}
	}

	public void set(PreparedStatement st, int index, int value) throws SQLException {
		st.setInt(index, value);
	}

	public String toString() {
		return value == null ? "" : value.toString();
	}
}
