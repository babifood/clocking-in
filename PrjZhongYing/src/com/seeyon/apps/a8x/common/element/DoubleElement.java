package com.seeyon.apps.a8x.common.element;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class DoubleElement implements IElement<Double> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5174380029282723446L;

	private static final Double ZERO = new Double(0);

	private Double value;

	public DoubleElement() {

	}

	public DoubleElement(Double i) {
		this.value = i;
	}

	public DoubleElement(double i) {
		this.value = new Double(i);
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Object getDefaultValue() {
		return ZERO;
	}

	public String getName() {
		return "double";
	}

	public Class<Double> getClass_() {
		return Double.class;
	}

	public int getSqlType() {
		return Types.DOUBLE;
	}

	public Double get(ResultSet rs, String name) throws SQLException {
		return new Double(rs.getDouble(name));
	}

	public Double get(ResultSet rs, int index) throws SQLException {
		return new Double(rs.getDouble(index));
	}

	public IElement<Double> getElement(ResultSet rs, int index) throws SQLException {
		return new DoubleElement(rs.getDouble(index));
	}

	public void set(PreparedStatement st, int index, Double value) throws SQLException {
		if (value == null) {
			st.setNull(index, getSqlType());
		} else {
			st.setDouble(index, value.doubleValue());
		}
	}

	public void set(PreparedStatement st, int index, double value) throws SQLException {
		st.setDouble(index, value);
	}

	public String toString() {
		return value == null ? "" : value.toString();
	}
}
