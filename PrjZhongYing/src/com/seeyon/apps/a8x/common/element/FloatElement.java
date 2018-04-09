package com.seeyon.apps.a8x.common.element;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class FloatElement implements IElement<Float> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5738513125019479611L;
	private static final Float ZERO = new Float(0.0);
	private Float value;

	public FloatElement() {

	}

	public FloatElement(Float i) {
		this.value = i;
	}

	public FloatElement(float i) {
		this.value = new Float(i);
	}

	public Float getValue() {
		return value;
	}

	public void setValue(Float value) {
		this.value = value;
	}

	public Object getDefaultValue() {
		return ZERO;
	}

	public String getName() {
		return "float";
	}

	public Class<Float> getClass_() {
		return Float.class;
	}

	public int getSqlType() {
		return Types.FLOAT;
	}

	public Float get(ResultSet rs, String name) throws SQLException {
		return new Float(rs.getFloat(name));
	}

	public Float get(ResultSet rs, int index) throws SQLException {
		return new Float(rs.getFloat(index));
	}

	public IElement<Float> getElement(ResultSet rs, int index) throws SQLException {
		return new FloatElement(rs.getFloat(index));
	}

	public void set(PreparedStatement st, int index, Float value) throws SQLException {
		if (value == null) {
			st.setNull(index, getSqlType());
		} else {
			st.setFloat(index, value.floatValue());
		}
	}

	public void set(PreparedStatement st, int index, float value) throws SQLException {
		st.setFloat(index, value);
	}

	public String toString() {
		return value == null ? "" : value.toString();
	}
}
