package com.seeyon.apps.a8x.common.dhtmlxgrid;

import java.util.Hashtable;

public class DhtmlxGridMgr {

	private Hashtable<String, DhtmlxGridModel> cache;

	private DhtmlxGridMgr() {
		cache = new Hashtable<String, DhtmlxGridModel>(200);
	}

	private static class LazyHolder {
		private static final DhtmlxGridMgr INSTANCE = new DhtmlxGridMgr();
	}

	public static DhtmlxGridMgr getMe() {
		return LazyHolder.INSTANCE;

	}
	
	public void clearCahce() {
		cache.clear();
	}

	public DhtmlxGridModel getDhtmlxGridModel(String theName) {
		DhtmlxGridModel tmpModel = null;
		DhtmlxGridXmlDAO dao = null;
		tmpModel = cache.get(theName);
		if (tmpModel == null) {
			dao = new DhtmlxGridXmlDAO(theName.trim());
			try {
				dao.doParse();
				tmpModel = dao.getModel();
				if (tmpModel == null) {
					throw new IllegalStateException(theName + ".xml 文件不存在...");
				}
				cache.put(theName.trim(), tmpModel);
			} catch (Exception e) {
				throw new IllegalStateException(theName + ".xml 读取时错误...");
			}
		}
		return tmpModel;
	}

	public String toString() {
		return "DhtmlxgridMgr";
	}
}
