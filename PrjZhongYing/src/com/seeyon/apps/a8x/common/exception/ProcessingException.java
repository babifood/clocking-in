package com.seeyon.apps.a8x.common.exception;

public class ProcessingException extends Exception {

	private static final long serialVersionUID = 565294042152651318L;

	public ProcessingException() {
		super();
	}

	public ProcessingException(Throwable exception) {
		super(exception);
	}

	public ProcessingException(String exceptionMsg) {
		super(exceptionMsg);
	}

	public static ProcessingException createProcessException(Throwable exception) {
		ProcessingException value = null;
		if (exception instanceof ProcessingException) {
			value = (ProcessingException) exception;
		} else {
			value = new ProcessingException(exception.getLocalizedMessage());
		}
		return value;
	}

	public static ProcessingException createProcessException(String exception) {
		ProcessingException value = new ProcessingException(exception);
		return value;
	}
}
