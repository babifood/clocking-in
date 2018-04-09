package com.seeyon.apps.a8x.common.dhtmlxgrid;

import java.math.BigDecimal;
import java.text.Format;

public class ValueSum {

	private Format formater;
	protected BigDecimal value = new BigDecimal(0d);
	protected int size;

	public ValueSum() {

	}

	public Format getFormater() {
		return formater;
	}

	public void setFormater(Format formater) {
		this.formater = formater;
	}

	public void sum(BigDecimal aValue) {
		value = value.add(aValue);
		size++;
	}

	public BigDecimal getValue() {
		return value;
	}
}
