package com.seeyon.apps.a8x.common.exception;

public class DAOTransactionException extends Exception {

	private static final long serialVersionUID = 8854203432302382756L;

	public DAOTransactionException() {
		super();
	}

	public DAOTransactionException(String message) {
		super(message);
	}

	public DAOTransactionException(Throwable exception) {
		super(exception);
	}
}
