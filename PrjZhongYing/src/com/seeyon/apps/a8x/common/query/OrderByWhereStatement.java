package com.seeyon.apps.a8x.common.query;

import java.io.Serializable;

public class OrderByWhereStatement implements Serializable {

	private static final long serialVersionUID = -733616853906258217L;
	private String statement;

	public OrderByWhereStatement() {
		super();
	}

	public OrderByWhereStatement(String theOrderByStatement) {
		super();
		if (theOrderByStatement == null) {
			throw new IllegalArgumentException("OrderByWhereStatement->statement is illegal argument");
		}
		String tmp = theOrderByStatement.trim().toLowerCase();
		if (tmp.startsWith("and ") || tmp.startsWith("or ") || tmp.startsWith("where ") || tmp.startsWith("order ")) {
			throw new IllegalArgumentException("OrderByWhereStatement->statement is illegal argument");
		}
		this.statement = theOrderByStatement;
	}

	public String getOrderByStatement() {
		return Constants.ORDER_BY_FLAG + statement;
	}
}
