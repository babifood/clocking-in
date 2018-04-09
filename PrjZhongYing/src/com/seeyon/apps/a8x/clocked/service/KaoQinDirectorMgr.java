package com.seeyon.apps.a8x.clocked.service;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import com.seeyon.apps.a8x.clocked.dao.KaoQinDirectorDao;
import com.seeyon.apps.a8x.common.exception.ProcessingException;
import com.seeyon.apps.a8x.common.jdbc.DAOContext;
import com.seeyon.apps.a8x.common.jdbc.JdbcWorkUnit;
import com.seeyon.apps.a8x.common.jdbc.JdbcWorker;
import com.seeyon.apps.a8x.common.util.UtilString;
import com.seeyon.apps.a8x.oa.delegate.A8xMemberDelegate;
import com.seeyon.apps.a8x.oa.entity.A8xMember;
import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.organization.bo.V3xOrgDepartment;
import com.seeyon.ctp.organization.manager.OrgManager;

/*
 * 考勤机构与专员管理<p>
 * 缓存考勤专员与考勤机构的对应关系
 * 
 */
public final class KaoQinDirectorMgr {

	private Hashtable<String, String> cache;

	private Hashtable<String, Long> orgMap;

	private Map<String, Long> dayMap;

	private KaoQinDirectorMgr() {
		cache = new Hashtable<String, String>(200);
		orgMap = new Hashtable<String, Long>(200);
	}

	private static class LazyHolder {
		private static final KaoQinDirectorMgr INSTANCE = new KaoQinDirectorMgr();
	}

	public static KaoQinDirectorMgr getMe() {
		return LazyHolder.INSTANCE;

	}

	public void clearCahce() {
		cache.clear();
	}

	public String getSqlString(Long theCurrentUserId) {
		if (theCurrentUserId == null) {
			return "'nothing'";
		} else {
			return getSqlString(String.valueOf(theCurrentUserId));
		}

	}

	public String getSqlString(String theCurrentUserId) {
		String sql = null;
		if (cache.isEmpty()) {
			init();
		}
		sql = cache.get(theCurrentUserId);
		if (sql == null) {
			sql = "'nothing'";
		}
		return sql;
	}

	/*
	 * 获取机构ID
	 */
	public Long getOrganID(String theDeptpath) {
		Long id = null;
		if (orgMap.isEmpty()) {
			init();
		}
		Iterator<Map.Entry<String, Long>> iter = orgMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, Long> entry = iter.next();
			if (theDeptpath.startsWith(entry.getKey())) {
				id = entry.getValue();
				break;
			}
		}
		if (id == null) {
			throw new java.lang.IllegalStateException("无法获取部门[" + theDeptpath + "]对应的机构...");
		}
		return id;
	}

	public Long getFinalDayByMemberId(long memberId) {
		A8xMember tmpA8xMember = A8xMemberDelegate.read(String.valueOf(memberId));
		if (tmpA8xMember == null) {
			return null;
		}

		Long organID = getOrganID(tmpA8xMember.getPath());
		if (organID == null) {
			return null;
		}
		if (dayMap == null) {
			try {
				dayMap = loadDay();
			} catch (ProcessingException e) {

			}
		}

		Long value = dayMap.get(organID.toString());
		if (value == null || value.longValue()==0L) {
			try {
				dayMap = loadDay();
			} catch (ProcessingException e) {

			}
		}

		value = dayMap.get(organID.toString());
		if (value == null || value.longValue()==0L) {
			return null;
		}
		return value;

	}

	public void init() {
		try {
			OrgManager orgManager = (OrgManager) AppContext.getBean("orgManager");
			Map<String, String> mapping = load();
			dayMap = loadDay();

			if (mapping != null) {
				Iterator<Map.Entry<String, String>> iter = mapping.entrySet().iterator();
				String key = "";
				String value = "";
				while (iter.hasNext()) {
					Map.Entry<String, String> entry = iter.next();
					key = entry.getKey();

					try {
						V3xOrgDepartment tmpDept = orgManager.getDepartmentById(Long.parseLong(key));
						orgMap.put(tmpDept.getPath(), tmpDept.getId());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					value = entry.getValue();
					if (value != null && value.trim().length() > 0) {
						String x[] = UtilString.tokenize(value, ",");
						String old = null;
						for (int i = 0; i < x.length; i++) {
							old = cache.get(x[i].trim());
							if (old != null) {
								cache.put(x[i].trim(), old + ",'" + key + "'");
							} else {
								cache.put(x[i].trim(), "'" + key + "'");
							}
						}
					}
				}
			}
		} catch (ProcessingException e) {
			e.printStackTrace();
		}
	}

	public void refresh() {
		cache.clear();
		orgMap.clear();
		if (dayMap != null) {
			dayMap.clear();
		}
		init();
	}

	public String toString() {
		return "KaoQinDirectorMgr";
	}

	private Map<String, String> load() throws ProcessingException {
		return JdbcWorker.wrapNotInTransaction(new JdbcWorkUnit<Map<String, String>>() {
			public Map<String, String> execute(DAOContext daoContext) throws ProcessingException {
				KaoQinDirectorDao dao = new KaoQinDirectorDao(daoContext);
				return dao.load();
			}
		});
	}

	private Map<String, Long> loadDay() throws ProcessingException {
		return JdbcWorker.wrapNotInTransaction(new JdbcWorkUnit<Map<String, Long>>() {
			public Map<String, Long> execute(DAOContext daoContext) throws ProcessingException {
				KaoQinDirectorDao dao = new KaoQinDirectorDao(daoContext);
				return dao.loadDay();
			}
		});
	}

}
