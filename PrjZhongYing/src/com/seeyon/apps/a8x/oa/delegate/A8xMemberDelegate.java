package com.seeyon.apps.a8x.oa.delegate;

import com.seeyon.apps.a8x.common.exception.ProcessingException;
import com.seeyon.apps.a8x.common.jdbc.DAOContext;
import com.seeyon.apps.a8x.common.jdbc.JdbcWorkUnit;
import com.seeyon.apps.a8x.common.jdbc.JdbcWorker;
import com.seeyon.apps.a8x.oa.dao.A8xMemberDao;
import com.seeyon.apps.a8x.oa.entity.A8xMember;

public class A8xMemberDelegate {

	
	public static A8xMember read(final String theKey) {
		A8xMember temp = null;
		try {
			temp = JdbcWorker.wrapNotInTransaction(new JdbcWorkUnit<A8xMember>() {
				public A8xMember execute(DAOContext daoContext) throws ProcessingException {
					A8xMemberDao dao = new A8xMemberDao(daoContext);
					return dao.read(Long.parseLong(theKey));
				}
			});
		} catch (Exception e) {
			throw new java.lang.IllegalStateException("人员:["+ theKey +"]不存在..."+e.getLocalizedMessage());
		}
		
		if(temp == null){
			throw new java.lang.IllegalStateException("人员:["+ theKey +"]不存在...");
		}
		
		return temp;
	}
}
