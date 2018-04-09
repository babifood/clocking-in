package com.seeyon.apps.a8x.common.query;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import com.seeyon.apps.a8x.common.element.DateElement;
import com.seeyon.apps.a8x.common.element.DoubleElement;
import com.seeyon.apps.a8x.common.element.FloatElement;
import com.seeyon.apps.a8x.common.element.IntegerElement;
import com.seeyon.apps.a8x.common.element.LongElement;
import com.seeyon.apps.a8x.common.element.StringElement;
import com.seeyon.apps.a8x.common.element.TimeElement;
import com.seeyon.apps.a8x.common.element.TimestampElement;
import com.seeyon.apps.a8x.common.parameter.IParameter;
import com.seeyon.apps.a8x.common.parameter.SqlParameter;

public final class ParameterFactory {

	public static IParameter buildParameter(String theValue, String theType, String theParamterName) {
		IParameter tmp = null;
		if (theType.equalsIgnoreCase("str")) {
			tmp = new SqlParameter(theParamterName, new StringElement(theValue));
		} else if (theType.equalsIgnoreCase("int")) {
			tmp = new SqlParameter(theParamterName, new IntegerElement(Integer.parseInt(theValue)));
		} else if (theType.equalsIgnoreCase("float")) {
			tmp = new SqlParameter(theParamterName, new FloatElement(Float.parseFloat(theValue)));
		} else if (theType.equalsIgnoreCase("double")) {
			tmp = new SqlParameter(theParamterName, new DoubleElement(Double.parseDouble(theValue)));
		} else if (theType.equalsIgnoreCase("long")) {
			tmp = new SqlParameter(theParamterName, new LongElement(Long.parseLong(theValue)));
		} else if (theType.equalsIgnoreCase("date")) {
			tmp = new SqlParameter(theParamterName, new DateElement(Date.valueOf(theValue)));
		} else if (theType.equalsIgnoreCase("oracle_date")) {
			tmp = new SqlParameter(theParamterName, new StringElement(theValue));
		} else if (theType.equalsIgnoreCase("timestamp")) {
			tmp = new SqlParameter(theParamterName, new TimestampElement(Timestamp.valueOf(theValue)));
		} else if (theType.equalsIgnoreCase("time")) {
			tmp = new SqlParameter(theParamterName, new TimeElement(Time.valueOf(theValue)));
		} else if (theType.equalsIgnoreCase("timestamp1")) {
			tmp = new SqlParameter(theParamterName, new TimestampElement(Timestamp.valueOf(theValue + " 0:0:0")));
		} else if (theType.equalsIgnoreCase("timestamp2")) {
			tmp = new SqlParameter(theParamterName, new TimestampElement(Timestamp.valueOf(theValue + " 24:0:0")));
		} else {
			throw new IllegalArgumentException("[Type=" + theType + "] unsupported");
		}
		return tmp;
	}
}
