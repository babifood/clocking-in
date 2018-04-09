package com.seeyon.apps.a8x.common.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface RowSetter<T> {

	public int fillStatement(PreparedStatement theStmt, T theVO) throws SQLException;

}
