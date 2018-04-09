package com.seeyon.apps.a8x.common.dhtmlxgrid;

import java.math.BigDecimal;

public class ValueAvg extends ValueSum {
	public ValueAvg() {
		super();
	}

	public BigDecimal getValue() {
		return value.divide(new BigDecimal(size));
	}
}
