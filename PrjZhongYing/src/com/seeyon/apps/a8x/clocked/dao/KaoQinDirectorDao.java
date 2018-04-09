package com.seeyon.apps.a8x.clocked.dao;

import java.util.Map;

import com.seeyon.apps.a8x.common.exception.DAOExcuteException;
import com.seeyon.apps.a8x.common.jdbc.DAOContext;
import com.seeyon.apps.a8x.common.jdbc.JdbcDAO;
import com.seeyon.apps.a8x.common.jdbc.JdbcDaoUtil;

/*
 * 考勤机构与专员
 */
public class KaoQinDirectorDao extends JdbcDAO {

	public KaoQinDirectorDao() {
		super();
	}

	public KaoQinDirectorDao(DAOContext context) {
		super(context);
	}
	
	public Map<String,String> load() throws DAOExcuteException {
		String sql ="select field0001, field0002 from formmain_1226";
		return JdbcDaoUtil.findAsMapStringString(context, sql);
	}
	
	public Map<String,Long> loadDay() throws DAOExcuteException {
		String sql ="select field0001, field0003 from formmain_1226";
		return JdbcDaoUtil.findAsMapStringLong(context, sql);
	}
	
	
}
