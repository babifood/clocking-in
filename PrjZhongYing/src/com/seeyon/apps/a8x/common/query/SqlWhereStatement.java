package com.seeyon.apps.a8x.common.query;

import java.io.Serializable;

public class SqlWhereStatement implements Serializable {

	private static final long serialVersionUID = 4336113989112065619L;

	private String prepend;

	private String statement;

	public SqlWhereStatement() {
		super();
	}

	public SqlWhereStatement(String statement) {
		this(null, statement);
	}

	public SqlWhereStatement(String prepend, String statement) {
		super();
		if (statement == null) {
			throw new IllegalArgumentException("SqlWhereStatement->statement is illegal argument");
		}
		String tmp = statement.trim().toLowerCase();
		if (tmp.startsWith("and ") || tmp.startsWith("or ") || tmp.startsWith("where ")) {
			throw new IllegalArgumentException("SqlWhereStatement->statement is illegal argument");
		}
		this.prepend = prepend;
		this.statement = statement;
	}

	public String getPrepend() {
		return prepend;
	}

	public void setPrepend(String prepend) {
		this.prepend = prepend;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}
}
