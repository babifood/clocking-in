package com.seeyon.apps.a8x.common.jdbc;

import java.util.List;

import com.seeyon.apps.a8x.common.domain.QueryCondition;
import com.seeyon.apps.a8x.common.domain.QueryResult;
import com.seeyon.apps.a8x.common.exception.DAOExcuteException;
import com.seeyon.apps.a8x.common.parameter.IParameter;

public class JdbcDAO {
	public static final int FETCH_SIZE = JdbcDaoUtil.FETCH_SIZE;

	public static final int INSERT = JdbcDaoUtil.INSERT;

	public static final int UPDATE = JdbcDaoUtil.UPDATE;

	public static final int DELETE = JdbcDaoUtil.DELETE;

	public static final int READ = JdbcDaoUtil.READ;

	public static final int QUERY = JdbcDaoUtil.QUERY;

	public final static int EXECUTE = JdbcDaoUtil.EXECUTE;

	protected DAOContext context;
	// 执行事件的类别
	protected int actionType;

	public JdbcDAO() {
		super();
	}

	public JdbcDAO(DAOContext context) {
		this.context = context;
	}

	public DAOContext getContext() {
		return context;
	}

	public void setContext(DAOContext context) {
		this.context = context;
	}

	protected <T> T read(String theSQL, RowGetter<T> theRsGetter, IParameter... theParams) throws DAOExcuteException {
		actionType = JdbcDaoUtil.READ;
		return JdbcDaoUtil.findAsValueObject(context, theSQL, theRsGetter, theParams);
	}

	protected <T> List<T> load(String theSQL, RowGetter<T> theRsGetter, IParameter... theParams) throws DAOExcuteException {
		actionType = JdbcDaoUtil.QUERY;
		return JdbcDaoUtil.findAsList(context, theSQL, theRsGetter, theParams);
	}

	protected <T> List<T> load(String theSQL, RowGetter<T> theRsGetter, QueryCondition theCondition) throws DAOExcuteException {
		actionType = JdbcDaoUtil.QUERY;
		return JdbcDaoUtil.findAsList(context, theSQL, theCondition, theRsGetter);
	}

	protected <T> QueryResult query(String theSQL, QueryCondition theCondition, RowGetter<T> theRsGetter) throws DAOExcuteException {
		actionType = JdbcDaoUtil.QUERY;
		return JdbcDaoUtil.query(context, theSQL, theCondition, theRsGetter);
	}

	protected <T> int create(T theEntity, String theSQL, RowSetter<T> theStmtSetter) throws DAOExcuteException {
		actionType = JdbcDaoUtil.INSERT;
		return JdbcDaoUtil.singleExecute(context, theSQL, theEntity, theStmtSetter);
	}

	protected <T> int[] create(List<T> theEntityList, String theSQL, RowSetter<T> theStmtSetter) throws DAOExcuteException {
		actionType = JdbcDaoUtil.INSERT;
		return JdbcDaoUtil.batchExecute(context, theSQL, theEntityList, theStmtSetter);
	}

	protected <T> int update(T theEntity, String theSQL, RowSetter<T> theStmtSetter) throws DAOExcuteException {
		actionType = JdbcDaoUtil.UPDATE;
		return JdbcDaoUtil.singleExecute(context, theSQL, theEntity, theStmtSetter);
	}

	protected <T> int[] update(List<T> theEntityList, String theSQL, RowSetter<T> theStmtSetter) throws DAOExcuteException {
		actionType = JdbcDaoUtil.UPDATE;
		return JdbcDaoUtil.batchExecute(context, theSQL, theEntityList, theStmtSetter);
	}

	protected <T> int delete(String theSQL, IParameter... theParams) throws DAOExcuteException {
		actionType = JdbcDaoUtil.DELETE;
		return JdbcDaoUtil.singleExecute(context, theSQL, theParams);
	}
}
