package com.seeyon.apps.a8x.common.element;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface IElement<T> extends Serializable {

	public T getValue();

	public void setValue(T value);


	public String getName();

	public Class<?> getClass_();

	public int getSqlType();

	public T get(ResultSet rs, String name) throws SQLException;

	public T get(ResultSet rs, int index) throws SQLException;

	public IElement<T> getElement(ResultSet rs, int index) throws SQLException;

	public void set(PreparedStatement st, int index, T value) throws SQLException;

}