package com.seeyon.apps.a8x.common.exception;

import java.sql.SQLException;

public class DAOExcuteException extends ProcessingException {

	private static final long serialVersionUID = -5070934856598497337L;

	private int type;

	private String error;
	
	public DAOExcuteException(String msg) {
		super(msg);
		
	}

	public DAOExcuteException(int type, SQLException exception) {
		super(exception);
		this.type = type;
		error = exception.getLocalizedMessage();

	}

	public int getType() {
		return type;
	}

	public String getMessage() {
		return error;
	}

}
