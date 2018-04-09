package com.seeyon.apps.a8x.common.element;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class ByteElement implements IElement<Byte> {

	private static final long serialVersionUID = -7053193111164067858L;
	private static final Byte ZERO = Byte.valueOf((byte) 0);

	public Serializable getDefaultValue() {
		return ZERO;
	}

	private Byte value;

	public ByteElement() {

	}

	public ByteElement(Byte value) {
		this.value = value;
	}

	public ByteElement(byte value_) {
		this.value = Byte.valueOf(value_);
	}

	public Byte getValue() {
		return value;
	}

	public void setValue(Byte value_) {
		this.value = value_;
	}


	public Class<Byte> getPrimitiveClass() {
		return byte.class;
	}

	public Class<Byte> getClass_() {
		return Byte.class;
	}

	public int getSqlType() {
		return Types.TINYINT;
	}

	public String getName() {
		return "byte";
	}

	public Byte get(ResultSet rs, String name) throws SQLException {
		return Byte.valueOf(rs.getByte(name));
	}

	public Byte get(ResultSet rs, int index) throws SQLException {
		return Byte.valueOf((rs.getByte(index)));
	}

	public IElement<Byte> getElement(ResultSet rs, int index) throws SQLException {
		return new ByteElement(rs.getByte(index));
	}

	public void set(PreparedStatement st, int index, Byte value) throws SQLException {
		if (value == null) {
			st.setNull(index, getSqlType());
		} else {
			st.setByte(index, value.byteValue());
		}
	}

	public void set(PreparedStatement st, int index, byte value) throws SQLException {
		st.setByte(index, value);
	}

	public int hashCode() {
		return "Type.Byte".hashCode();
	}

	public String toString() {
		return value == null ? "" : value.toString();
	}
}
