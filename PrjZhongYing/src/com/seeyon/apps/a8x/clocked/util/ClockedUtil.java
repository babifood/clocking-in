package com.seeyon.apps.a8x.clocked.util;

import com.seeyon.apps.a8x.clocked.dao.PersonDao;
import com.seeyon.apps.a8x.clocked.entity.ClockedResult;
import com.seeyon.apps.a8x.clocked.entity.Person;
import com.seeyon.apps.a8x.clocked.rule.ClockedYesNoRule;
import com.seeyon.apps.a8x.clocked.rule.WorkHourRule;
import com.seeyon.apps.a8x.clocked.service.KaoQinDirectorMgr;
import com.seeyon.apps.a8x.common.exception.ProcessingException;
import com.seeyon.apps.a8x.common.jdbc.DAOContext;
import com.seeyon.apps.a8x.common.jdbc.JdbcWorkUnit;
import com.seeyon.apps.a8x.common.jdbc.JdbcWorker;
import com.seeyon.apps.a8x.common.util.UtilDateTime;
import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.organization.bo.V3xOrgDepartment;
import com.seeyon.ctp.organization.manager.OrgManager;

public final class ClockedUtil {

	private ClockedUtil() {
		
	}
	
	/*
	 * 获取机构名称
	 */
	public static V3xOrgDepartment getOrgan(String theKey) {
		V3xOrgDepartment tmpDept = null;
		try {
			OrgManager orgManager = (OrgManager) AppContext.getBean("orgManager");
			tmpDept = orgManager.getDepartmentById(Long.parseLong(theKey));
		} catch (Exception e) {
			throw new java.lang.IllegalStateException("考勤机构[" + theKey + "]不存在..." + e.getLocalizedMessage());
		}
		if (tmpDept == null) {
			throw new java.lang.IllegalStateException("考勤机构[" + theKey + "]不存在...");
		}
		return tmpDept;
	}
	
	/*
	 * 根据人员ID获取考勤人员信息
	 */
	public static Person loadPerson(final long theMemeberID) throws ProcessingException {
		return JdbcWorker.wrapNotInTransaction(new JdbcWorkUnit<Person>() {
			public Person execute(DAOContext daoContext) throws ProcessingException {
				PersonDao dao = new PersonDao(daoContext);
				return dao.getByMememberID(theMemeberID);
			}
		});
	}
	
	/*
	 * 标识某人在某一天的考勤规则
	 */
	public static ClockedResult markClockedYesNo(Person thePerson,java.sql.Date theDate,ClockedYesNoRule clockedYesNoRule) throws BusinessException {
		ClockedResult tmpResult = new ClockedResult();
		tmpResult.setMemberID(thePerson.getMemberID());
		tmpResult.setMemberName(thePerson.getMemberName());
		tmpResult.setDept_name(thePerson.getDept_name());
		tmpResult.setDept_path(thePerson.getDept_path());
		tmpResult.setWorkNum(thePerson.getWorkNum());
		// 设置部门
		tmpResult.setDept(thePerson.getDept());
		// 设置机构
		tmpResult.setOrgan(KaoQinDirectorMgr.getMe().getOrganID(thePerson.getDept_path()).toString());
		// 设置考勤日期
		tmpResult.setClockDate(theDate);
		tmpResult.setYear(UtilDateTime.getYear(theDate));
		tmpResult.setMonth(UtilDateTime.getMonth(theDate));
		//设置考勤方式
		tmpResult.setDaKaType(thePerson.getTypeName());

		// 设置标准作息时间
		WorkHourRule.setStandWorkTime(tmpResult);
		// 标志是否上班规则
		clockedYesNoRule.markClockedYesNo(tmpResult);

		return tmpResult;

	}
	
	
}
