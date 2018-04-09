package com.seeyon.apps.a8x.common.jdbc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据表结构模型
 */
public class JdbcTableMeta implements java.io.Serializable {

	private static final long serialVersionUID = -5392213637467009781L;

	private List<String> fieldnameList;

	private Map<String, JdbcFieldType> map;

	public JdbcTableMeta(int count) {
		fieldnameList = new ArrayList<String>(count);
		map = new HashMap<String, JdbcFieldType>(count);
	}

	public void add(String theFieldName, JdbcFieldType theType) {
		fieldnameList.add(theFieldName);

		map.put(theFieldName.toLowerCase(), theType);
	}

	public JdbcFieldType getType(int index) {
		JdbcFieldType tmp = null;
		String name = fieldnameList.get(index);
		if (name != null) {
			tmp = map.get(name.toLowerCase());
		}
		return tmp;
	}

	public JdbcFieldType getType(String theName) {
		return map.get(theName.toLowerCase());
	}

	public String getFieldName(int index) {
		String temp = fieldnameList.get(index);
		return temp;
	}

	public String getFieldNameLowerCase(int index) {
		String temp = fieldnameList.get(index);
		return temp.toLowerCase();
	}

	public int size() {
		return fieldnameList == null ? 0 : fieldnameList.size();
	}
}
