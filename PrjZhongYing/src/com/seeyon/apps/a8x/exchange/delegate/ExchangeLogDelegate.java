package com.seeyon.apps.a8x.exchange.delegate;

import com.seeyon.apps.a8x.common.domain.QueryCondition;
import com.seeyon.apps.a8x.common.domain.QueryResult;
import com.seeyon.apps.a8x.common.exception.ProcessingException;
import com.seeyon.apps.a8x.common.jdbc.DAOContext;
import com.seeyon.apps.a8x.common.jdbc.JdbcWorkUnit;
import com.seeyon.apps.a8x.common.jdbc.JdbcWorker;
import com.seeyon.apps.a8x.exchange.dao.ExchangeLogDao;
import com.seeyon.apps.a8x.exchange.entity.ExchangeLog;

public final class ExchangeLogDelegate {

	// private static Log log = LogFactory.getLog(ExchageLogDelegate.class);

	/**
	 * Default Constructor
	 */
	private ExchangeLogDelegate() {

	}

	// 新增一条记录
	public static void create(final ExchangeLog theModel) throws ProcessingException {
		JdbcWorker.wrapInTransaction(new JdbcWorkUnit<Integer>() {
			public Integer execute(DAOContext daoContext) throws ProcessingException {
				ExchangeLogDao dao = null;
				theModel.setIdIfNew();
				dao = new ExchangeLogDao(daoContext);
				return dao.create(theModel);
			}
		});
	}

	// 修改一条记录
	public static void update(final ExchangeLog theModel) throws ProcessingException {
		JdbcWorker.wrapNotInTransaction(new JdbcWorkUnit<Integer>() {
			public Integer execute(DAOContext daoContext) throws ProcessingException {
				ExchangeLogDao dao = null;
				dao = new ExchangeLogDao(daoContext);
				int flag = dao.update(theModel);
				return flag;
			}
		});
	}

	// 按关键字查询
	public static ExchangeLog read(final Long theKey) throws ProcessingException {
		return JdbcWorker.wrapNotInTransaction(new JdbcWorkUnit<ExchangeLog>() {
			public ExchangeLog execute(DAOContext daoContext) throws ProcessingException {
				ExchangeLogDao dao = new ExchangeLogDao(daoContext);
				return dao.read(theKey);
			}
		});
	}

	// 按条件查询
	public static QueryResult query(final QueryCondition theCondition) throws ProcessingException {
		return JdbcWorker.wrapNotInTransaction(new JdbcWorkUnit<QueryResult>() {
			public QueryResult execute(DAOContext daoContext) throws ProcessingException {
				ExchangeLogDao dao = new ExchangeLogDao(daoContext);
				return dao.query(theCondition);
			}
		});
	}

	// 按关键字查询
	public static boolean isExisted(final long form_record_id) throws ProcessingException {
		return JdbcWorker.wrapNotInTransaction(new JdbcWorkUnit<Boolean>() {
			public Boolean execute(DAOContext daoContext) throws ProcessingException {
				ExchangeLogDao dao = new ExchangeLogDao(daoContext);
				return dao.isExisted(form_record_id);
			}
		});
	}

	// 修改一条记录
	public static void updateByFormRecordId(final ExchangeLog theModel) throws ProcessingException {
		JdbcWorker.wrapNotInTransaction(new JdbcWorkUnit<Integer>() {
			public Integer execute(DAOContext daoContext) throws ProcessingException {
				ExchangeLogDao dao = null;
				dao = new ExchangeLogDao(daoContext);
				int flag = dao.updateByFormRecordId(theModel);
				return flag;
			}
		});
	}

}