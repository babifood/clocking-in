package com.seeyon.apps.a8x.oa.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class A8xDataRow implements Map<String, Object>, Serializable {

	private static final long serialVersionUID = -8187619071948421600L;

	private Map<String, Object> dataMap;

	public A8xDataRow(int size) {
		this.dataMap = new HashMap<String, Object>(size);
	}

	public A8xDataRow() {
		this.dataMap = new HashMap<String, Object>(30);
	}

	public Object remove(String key) {
		return this.dataMap.remove(key);
	}

	public void add(String key, int value) {
		addDataItem(key, Integer.valueOf(value));
	}

	public void add(String key, long value) {
		addDataItem(key, Long.valueOf(value));
	}

	public void add(String key, short value) {
		addDataItem(key, Short.valueOf(value));
	}

	public void add(String key, byte value) {
		addDataItem(key, Byte.valueOf(value));
	}

	public void add(String key, float value) {
		addDataItem(key, Float.valueOf(value));
	}

	public void add(String key, double value) {
		addDataItem(key, Double.valueOf(value));
	}

	public void add(String key, boolean value) {
		addDataItem(key, Boolean.valueOf(value));
	}

	public void add(String key, char value) {
		addDataItem(key, Character.valueOf(value));
	}

	public void add(String key, String value) {
		addDataItem(key, value);
	}

	public void add(String key, java.sql.Date value) {
		addDataItem(key, value);
	}

	public void add(String key, java.sql.Timestamp value) {
		addDataItem(key, value);
	}

	protected void addDataItem(String key, Object value) {
		this.dataMap.put(key, value);
	}

	public int size() {
		return this.dataMap.size();
	}

	public void clear() {
		this.dataMap.clear();
	}

	public boolean containsKey(Object key) {
		return this.dataMap.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return this.dataMap.containsValue(value);
	}

	public Set<Map.Entry<String, Object>> entrySet() {
		return this.dataMap.entrySet();
	}

	public Object get(Object key) {
		Object result = null;
		result = this.dataMap.get(key);
		return result;
	}

	public boolean isEmpty() {
		return this.dataMap.isEmpty();
	}

	public Set<String> keySet() {
		return this.dataMap.keySet();
	}

	public Object put(String key, Object value) {
		addDataItem(key, value);
		return this.dataMap;
	}

	public void putAll(Map<? extends String, ? extends Object> t) {
		this.dataMap.putAll(t);
	}

	public Object remove(Object key) {
		return this.dataMap.remove(key);
	}

	public Collection<Object> values() {
		return this.dataMap.values();
	}

	public String toString() {
		return dataMap == null ? "" : dataMap.toString();
	}
}
