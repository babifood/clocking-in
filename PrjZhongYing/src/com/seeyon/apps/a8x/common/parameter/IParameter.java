package com.seeyon.apps.a8x.common.parameter;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface IParameter extends Serializable {

	abstract int fillPreparedStatement(PreparedStatement pstmt, int index) throws SQLException;

	abstract public Object getValue();

	abstract public String getName();

}