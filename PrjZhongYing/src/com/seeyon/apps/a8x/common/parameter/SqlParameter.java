package com.seeyon.apps.a8x.common.parameter;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

import com.seeyon.apps.a8x.common.element.ByteElement;
import com.seeyon.apps.a8x.common.element.DoubleElement;
import com.seeyon.apps.a8x.common.element.FloatElement;
import com.seeyon.apps.a8x.common.element.IElement;
import com.seeyon.apps.a8x.common.element.IntegerElement;
import com.seeyon.apps.a8x.common.element.LongElement;
import com.seeyon.apps.a8x.common.element.SqlDateElement;
import com.seeyon.apps.a8x.common.element.SqlTimeElement;
import com.seeyon.apps.a8x.common.element.SqlTimestampElement;
import com.seeyon.apps.a8x.common.element.StringElement;

public class SqlParameter implements IParameter, Serializable {

	private static final long serialVersionUID = -5541067780510277663L;
	private String name;

	@SuppressWarnings("rawtypes")
	private IElement value;

	@SuppressWarnings("rawtypes")
	public SqlParameter(IElement value) {
		this.value = value;
	}

	@SuppressWarnings("rawtypes")
	public SqlParameter(String name, IElement value) {
		this.name = name;
		this.value = value;
	}

	public SqlParameter(byte value_) {
		value = new ByteElement(value_);
	}

	public SqlParameter(int value_) {
		value = new IntegerElement(value_);
	}

	public SqlParameter(String name_, int value_) {
		this.name = name_;
		value = new IntegerElement(value_);
	}

	public SqlParameter(long value_) {
		value = new LongElement(value_);
	}

	public SqlParameter(float value_) {
		value = new FloatElement(value_);
	}

	public SqlParameter(double value_) {
		value = new DoubleElement(value_);
	}

	public SqlParameter(String value_) {
		value = new StringElement(value_);
	}

	public SqlParameter(String name_, String value_) {
		this.name = name_;
		value = new StringElement(value_);
	}

	public SqlParameter(java.sql.Date value_) {
		value = new SqlDateElement(value_);
	}

	public SqlParameter(Time value_) {
		value = new SqlTimeElement(value_);
	}

	public SqlParameter(Timestamp value_) {
		value = new SqlTimestampElement(value_);
	}

	@SuppressWarnings("unchecked")
	public int fillPreparedStatement(PreparedStatement pstmt, int index) throws SQLException {
		value.set(pstmt, index, value.getValue());
		return index;
	}

	@SuppressWarnings("rawtypes")
	public IElement getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class<?> geClassClass() {
		return value.getClass_();
	}

	public Object getValueValue() {
		return value.getValue();
	}
}