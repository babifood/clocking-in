package com.seeyon.apps.a8x.clocked.check;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.seeyon.apps.a8x.clocked.dao.QingJiaBillDao;
import com.seeyon.apps.a8x.clocked.entity.ClockedBizData;
import com.seeyon.apps.a8x.clocked.rule.ClockedBaseRule;
import com.seeyon.apps.a8x.clocked.rule.ClockedBizDataResolveRule;
import com.seeyon.apps.a8x.common.exception.ProcessingException;
import com.seeyon.apps.a8x.common.jdbc.DAOContext;
import com.seeyon.apps.a8x.common.jdbc.JdbcWorkUnit;
import com.seeyon.apps.a8x.common.jdbc.JdbcWorker;
import com.seeyon.apps.a8x.common.util.UtilString;
import com.seeyon.ctp.common.exceptions.BusinessException;

public class A8xOAFunction {

	private static final Log log = LogFactory.getLog(A8xOAFunction.class);

	public static String getQianJiaTimeLength(Object[] values) {
		// selfFunction( '请假时长计算' , {流程编号} , {申请人} , {请假类别} , {请假开始时间} ,
		// {请假结束时间} )
		// return
		// com.seeyon.apps.a8x.clocked.check.A8xOAFunction.getQianJiaTimeLength(param)
		if (values == null || values.length < 1) {
			return "-1";
		}

		double value = 0;

		ClockedBizData clockedBizData = new ClockedBizData();
		String billNum = values[0] == null ? "" : values[0].toString().trim();
		// "0.0"
		if (billNum.equals("0.0") || billNum.equals("0")) {
			billNum = "";
		}
		clockedBizData.setBillNum(billNum);
		// 如果不是发起节点，流水号有值，从数据库中读取。
		if (clockedBizData.getBillNum().length() > 0) {
			ClockedBizData hisClockedBizData = null;
			try {
				hisClockedBizData = read(clockedBizData.getBillNum());
				if (hisClockedBizData != null) {
					value = hisClockedBizData.getTimeLength();
					if (value > 0) {
						return String.valueOf(value);
					}
				}
			} catch (ProcessingException e) {
				log.equals(e);
			}
		}

		// Member|5841959881753904313
		String member = values[1] == null ? "" : values[1].toString().trim();
		if (member.length() > 0) {
			String[] x = UtilString.tokenize(member, "|");
			if (x.length == 2) {
				clockedBizData.setMemberID(x[1]);
			}
		} else {
			return "-1";
		}
		// 调休 6
		// 事假 0
		// 病假 1
		// 陪产假 2
		// 婚假 3
		// 丧假 4
		// 产假 5
		// 年假 7
		// 培训假 8
		// 其他 9
		String type = values[2] == null ? "-1" : values[2].toString().trim();
		if (type.length() == 0) {
			return "-1";
		}
		int flag = (int) Double.parseDouble(type);
		clockedBizData.setBizFlag1ID("" + flag);
		clockedBizData.setBizFlag1("");
		if (flag == 0) {
			clockedBizData.setBizFlag1("事假");
		} else if (flag == 1) {
			clockedBizData.setBizFlag1("病假");
		} else if (flag == 2) {
			clockedBizData.setBizFlag1("陪产假");
		} else if (flag == 3) {
			clockedBizData.setBizFlag1("婚假");
		} else if (flag == 4) {
			clockedBizData.setBizFlag1("丧假");
		} else if (flag == 5) {
			clockedBizData.setBizFlag1("产假");
		} else if (flag == 6) {
			clockedBizData.setBizFlag1("调休");
		} else if (flag == 7) {
			clockedBizData.setBizFlag1("年假");
		} else if (flag == 8) {
			clockedBizData.setBizFlag1("培训假");
		} else if (flag == 9) {
			clockedBizData.setBizFlag1("其他");
		}

		String date = values[3] == null ? "" : values[3].toString().trim();
		if (date.length() > 8) {
			clockedBizData.setBeginTime(java.sql.Timestamp.valueOf(date + ":00.0"));
		} else {
			return "-1";
		}

		date = values[4] == null ? "" : values[4].toString().trim();
		if (date.length() > 8) {
			clockedBizData.setEndTime(java.sql.Timestamp.valueOf(date + ":00.0"));
		} else {
			return "-1";
		}
		try {
			ClockedBizDataResolveRule tmpRule = new ClockedBizDataResolveRule(clockedBizData);
			value = tmpRule.getQianJiaTimeLength();
		} catch (BusinessException e) {
			value = -1;
			log.equals(e);
		}
		return String.valueOf(value);

	}

	public static String getQianJiaTimeLength2(Object[] values) {
		// selfFunction( '请假时长计算' , {流程编号} , {申请人} , {请假类别} , {请假开始时间} ,
		// {请假结束时间} )
		// return
		// com.seeyon.apps.a8x.clocked.check.A8xOAFunction.getQianJiaTimeLength(param)
		if (values == null || values.length < 1) {
			return "-1";
		}

		double value = 0;

		ClockedBizData clockedBizData = new ClockedBizData();
		String billNum = values[0] == null ? "" : values[0].toString().trim();
		// "0.0"
		if (billNum.equals("0.0") || billNum.equals("0")) {
			billNum = "";
		}
		clockedBizData.setBillNum(billNum);
		// 如果不是发起节点，流水号有值，从数据库中读取。
		if (clockedBizData.getBillNum().length() > 0) {

		}

		// Member|5841959881753904313
		String member = values[1] == null ? "" : values[1].toString().trim();
		if (member.length() > 0) {
			String[] x = UtilString.tokenize(member, "|");
			if (x.length == 2) {
				clockedBizData.setMemberID(x[1]);
			}
		} else {
			return "-1";
		}
		// 调休 6
		// 事假 0
		// 病假 1
		// 陪产假 2
		// 婚假 3
		// 丧假 4
		// 产假 5
		// 年假 7
		// 培训假 8
		// 其他 9
		String type = values[2] == null ? "-1" : values[2].toString().trim();
		if (type.length() == 0) {
			return "-1";
		}
		int flag = (int) Double.parseDouble(type);
		clockedBizData.setBizFlag1ID("" + flag);
		clockedBizData.setBizFlag1("");
		if (flag == 0) {
			clockedBizData.setBizFlag1("事假");
		} else if (flag == 1) {
			clockedBizData.setBizFlag1("病假");
		} else if (flag == 2) {
			clockedBizData.setBizFlag1("陪产假");
		} else if (flag == 3) {
			clockedBizData.setBizFlag1("婚假");
		} else if (flag == 4) {
			clockedBizData.setBizFlag1("丧假");
		} else if (flag == 5) {
			clockedBizData.setBizFlag1("产假");
		} else if (flag == 6) {
			clockedBizData.setBizFlag1("调休");
		} else if (flag == 7) {
			clockedBizData.setBizFlag1("年假");
		} else if (flag == 8) {
			clockedBizData.setBizFlag1("培训假");
		} else if (flag == 9) {
			clockedBizData.setBizFlag1("其他");
		}

		String date = values[3] == null ? "" : values[3].toString().trim();
		if (date.length() > 8) {
			clockedBizData.setBeginTime(java.sql.Timestamp.valueOf(date + ":00.0"));
		} else {
			return "-1";
		}

		date = values[4] == null ? "" : values[4].toString().trim();
		if (date.length() > 8) {
			clockedBizData.setEndTime(java.sql.Timestamp.valueOf(date + ":00.0"));
		} else {
			return "-1";
		}
		try {
			value = ClockedBaseRule.calcQingJiaValue_Mobile(clockedBizData);
			if(value >= 8){
				value = 8;
			}
		} catch (Exception e) {
			value = -1;
			log.equals(e);
		}
		return String.valueOf(value);

	}
	
	

	public static String getCanUseJiaValue(Object[] values) {
		if (values == null || values.length < 1) {
			return "-1";
		}
		if (values[0] == null) {
			return "-1";
		}
		if ("人事核定".equals(values[0])) {
			return "-1";
		} else {
			return "-1";
		}
	}

	public static ClockedBizData read(final String theQinJiaBillCode) throws ProcessingException {
		return JdbcWorker.wrapNotInTransaction(new JdbcWorkUnit<ClockedBizData>() {
			public ClockedBizData execute(DAOContext daoContext) throws ProcessingException {
				QingJiaBillDao dao = new QingJiaBillDao(daoContext);
				return dao.readByOffice(theQinJiaBillCode);
			}
		});
	}

}
