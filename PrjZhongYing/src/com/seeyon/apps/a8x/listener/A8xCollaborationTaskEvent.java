package com.seeyon.apps.a8x.listener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.seeyon.apps.a8x.clocked.check.QingJiaChecker;
import com.seeyon.apps.a8x.clocked.entity.ClockedBizData;
import com.seeyon.apps.a8x.clocked.entity.ClockedResult;
import com.seeyon.apps.a8x.clocked.entity.Person;
import com.seeyon.apps.a8x.clocked.rule.ClockedYesNoRule;
import com.seeyon.apps.a8x.clocked.service.KaoQinDirectorMgr;
import com.seeyon.apps.a8x.clocked.util.ClockedUtil;
import com.seeyon.apps.a8x.common.exception.ProcessingException;
import com.seeyon.apps.a8x.common.util.UtilDateTime;
import com.seeyon.apps.a8x.oa.util.XmlRowManyUtils;
import com.seeyon.apps.a8x.oa.util.XmlRowOneUtils;
import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.common.ctpenumnew.manager.EnumManager;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.common.po.ctpenumnew.CtpEnumItem;
import com.seeyon.ctp.form.modules.event.CollaborationAbstractTaskEvent;
/**
 * 表单发起监听事件
 * @author BABIFOOD
 *
 */
public class A8xCollaborationTaskEvent extends CollaborationAbstractTaskEvent {


	public A8xCollaborationTaskEvent() {
		super();
	}

	public int getId() {
		return getLabel().hashCode();
	}

	public int sort() {
		return 1;
	}

	public String getLabel() {
		return "A8X协同任务事件";
	}

	public String[] onBeforeStart(String xml) throws Exception {
		Map<String, Object> tmpRow = XmlRowOneUtils.toMap(xml);
		if (tmpRow == null) {
			return new String[] { "1", "系统异常...转换XML数据错误!!!" };
		}
		String bill = tmpRow.get("formname") == null ? "" : tmpRow.get("formname").toString().trim();
		if (bill.indexOf("加班申请单") != -1) {
			return execJiaBanBill(tmpRow);
		} else if (bill.indexOf("出差申请单") != -1) {
			return execChuChaBill(tmpRow);
		} else if (bill.indexOf("考勤异动单") != -1) {
			tmpRow = XmlRowManyUtils.toMap(xml);
			return execYidongBill(tmpRow);
		} else if (bill.equals("员工请假申请单")) {
			return execQingJiaBill(tmpRow);
		} else if (bill.equals("督导请假申请单")) {
			return execMobileQingJiaBill(tmpRow);
		}

		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String[] execYidongBill(Map<String, Object> theRow) {
		Map tmpMap = (Map) theRow.get("formson_0191");
		List newList = null;
		if (tmpMap != null) {
			Object tmpValue = tmpMap.get("row");
			if (tmpValue instanceof Map) {
				newList = new ArrayList();
				newList.add((Map) tmpValue);
			} else {
				newList = (List) tmpValue;
			}
		}
		if (newList == null) {
			return null;
		}
		int size = newList.size();

		Map tmpMap2 = (Map) theRow.get("formmain_0190");

		long memberId = 0l;
		if (tmpMap2 != null) {
			Map tmpValue = (Map) tmpMap2.get("row");
			memberId = tmpValue.get("field0001") == null ? 0 : Long.parseLong(tmpValue.get("field0001").toString().trim());

		}

		for (int i = 0; i < size; i++) {
			Map tmpRow = (Map) newList.get(i);
			ClockedBizData clockedBizData = new ClockedBizData();
			// clockedBizData.setMemberID();

			String billDate = null;
			if (tmpRow.get("field0015") != null) {
				billDate = tmpRow.get("field0015").toString();
				if (billDate.length() <= 16) {
					billDate = billDate + ":00.00";
				}
				clockedBizData.setBeginTime(java.sql.Timestamp.valueOf(billDate));
			} else {
				return new String[] { "1", "异动开始时间，不能为空..." };
			}

			if (tmpRow.get("field0019") != null) {
				billDate = tmpRow.get("field0019").toString();
				if (billDate.length() <= 16) {
					billDate = billDate + ":00.00";
				}
				clockedBizData.setEndTime(java.sql.Timestamp.valueOf(billDate));
			} else {
				return new String[] { "1", "异动结束时间，不能为空..." };
			}

			String[] s = checkTime(memberId, clockedBizData.getBeginTime());
			if (s != null) {
				return s;
			}
		}
		return null;
	}

	private String[] execChuChaBill(Map<String, Object> tmpRow) {
		ClockedBizData clockedBizData = new ClockedBizData();
		clockedBizData.setMemberID(tmpRow.get("申请人").toString());
		String billDate = (String) tmpRow.get("预计出差时间左");
		if (billDate != null) {
			if (billDate.length() <= 16) {
				billDate = billDate + ":00.00";
			}
			clockedBizData.setBeginTime(java.sql.Timestamp.valueOf(billDate));
		} else {
			return new String[] { "1", "预计出差开始时间，不能为空..." };
		}
		billDate = (String) tmpRow.get("预计出差时间右");
		if (billDate != null) {
			if (billDate.length() <= 16) {
				billDate = billDate + ":00.00";
			}
			clockedBizData.setEndTime(java.sql.Timestamp.valueOf(billDate));
		} else {
			return new String[] { "1", "预计出差结束时间，不能为空..." };
		}
		return checkTime(Long.valueOf(clockedBizData.getMemberID()), clockedBizData.getBeginTime());

	}

	private String[] execQingJiaBill(Map<String, Object> tmpRow) {

		ClockedBizData clockedBizData = null;
		clockedBizData = new ClockedBizData();
		clockedBizData.setMemberID(tmpRow.get("申请人").toString());

		String billDate = (String) tmpRow.get("请假开始时间");
		if (billDate != null) {
			if (billDate.length() <= 16) {
				billDate = billDate + ":00.00";
			}
			clockedBizData.setBeginTime(java.sql.Timestamp.valueOf(billDate));
		}
		billDate = (String) tmpRow.get("请假结束时间");
		if (billDate != null) {
			if (billDate.length() <= 16) {
				billDate = billDate + ":00.00";
			}
			clockedBizData.setEndTime(java.sql.Timestamp.valueOf(billDate));
		}
		// 判断考勤数据已封存
		String[] s = checkTime(Long.valueOf(clockedBizData.getMemberID()), clockedBizData.getBeginTime());
		if (s != null) {
			return s;
		}

		if (tmpRow.get("申请人") == null) {
			return new String[] { "1", "申请人不能为空!!!" };
		}
		long memberId = Long.parseLong(tmpRow.get("申请人").toString());

		if (tmpRow.get("请假时长") == null) {
			return new String[] { "1", "请假总时长不能为空!!!" };
		}
		double timeLength = Double.parseDouble(tmpRow.get("请假时长").toString());

		// 判断加班账或年假账是否满足
		String typeName = null;
		try {
			EnumManager enumManager = (EnumManager) AppContext.getBean("enumManagerNew");
			String type = (String) tmpRow.get("请假类别");
			if ("调休".equals(typeName) || "年假".equals(typeName)) {
				typeName = type;
			} else {
				CtpEnumItem tmpCtpEnumItem = enumManager.getCacheEnumItem(Long.valueOf(type));
				if (tmpCtpEnumItem != null) {
					typeName = tmpCtpEnumItem.getShowvalue();
				}
			}
		} catch (Exception e) {
			return new String[] { "1", "系统异常..." + e.getLocalizedMessage() };
		}

		clockedBizData.setBillType(typeName);
		clockedBizData.setBizFlag1(typeName);

		// 判断一天内请假不能超过标准工作时长
		QingJiaChecker tmpQingJiaChecker = new QingJiaChecker();
		try {
			boolean flag = tmpQingJiaChecker.checkOnStart(clockedBizData);
			if (!flag) {
				return new String[] { "1", tmpQingJiaChecker.getMessage() };
			}

		} catch (Exception e) {
			return new String[] { "1", "判断一天内请假不能超过标准工作时长发生异常." + e.getLocalizedMessage() };
		}

		if (clockedBizData != null) {
			if ("调休".equals(typeName)) {
				return tmpQingJiaChecker.checkTiaoXiu(memberId, timeLength);
			} else if ("年假".equals(typeName)) {
				return tmpQingJiaChecker.checkNianJia(memberId, timeLength, clockedBizData.getBeginTime());
			}
		}
		return null;
	}

	private String[] execJiaBanBill(Map<String, Object> tmpRow) {
		ClockedBizData clockedBizData = new ClockedBizData();
		clockedBizData.setMemberID(tmpRow.get("申请人").toString());
		String billDate = (String) tmpRow.get("预计加班时间左");
		if (billDate != null) {
			if (billDate.length() <= 16) {
				billDate = billDate + ":00.00";
			}
			clockedBizData.setBeginTime(java.sql.Timestamp.valueOf(billDate));
		} else {
			return new String[] { "1", "预计加班开始时间，不能为空..." };
		}
		billDate = (String) tmpRow.get("预计加班时间右");
		if (billDate != null) {
			if (billDate.length() <= 16) {
				billDate = billDate + ":00.00";
			}
			clockedBizData.setEndTime(java.sql.Timestamp.valueOf(billDate));
		} else {
			return new String[] { "1", "预计加班结束时间，不能为空..." };
		}

		String[] s = checkTime(Long.valueOf(clockedBizData.getMemberID()), clockedBizData.getBeginTime());
		if (s != null) {
			return s;
		}

		java.sql.Date tmpDate = new java.sql.Date(clockedBizData.getBeginTime().getTime());
		ClockedResult tmpClockedResult = null;

		Person person = null;
		try {
			person = ClockedUtil.loadPerson(Long.parseLong(clockedBizData.getMemberID()));
		} catch (ProcessingException e) {
			return new String[] { "1", "无法获取人员..." + e.getLocalizedMessage() };
		}
		if (person == null) {
			return new String[] { "1", "该人员不在考勤名单中..." };
		}

		try {
			// 考勤规则
			ClockedYesNoRule clockedYesNoRule = new ClockedYesNoRule(UtilDateTime.getYear(tmpDate), UtilDateTime.getMonth(tmpDate));
			tmpClockedResult = ClockedUtil.markClockedYesNo(person, tmpDate, clockedYesNoRule);
		} catch (BusinessException e) {
			return new String[] { "1", "系统异常..." + e.getLocalizedMessage() };
		}
		// 对不需要上班的日期，进行处理
		// if (tmpClockedResult.getClockFlag() < 1) {
		// return true;
		// }

		if (tmpClockedResult.getClockFlag() >= 1 && clockedBizData.getBeginTime().getTime() >= tmpClockedResult.getStandBeginTime().getTime() && clockedBizData.getEndTime().getTime() <= tmpClockedResult.getStandEndTime().getTime()) {
			return new String[] { "1", "时间非法，原因：加班时间在正常工作期间内" };
		}
		return null;
	}

	private String[] execMobileQingJiaBill(Map<String, Object> tmpRow) {
		// || "".equals(tmpRow.get("申请日期").toString().trim()
		if (tmpRow.get("申请日期") == null || "".equals(tmpRow.get("申请日期").toString().trim())) {
			return new String[] { "1", "申请日期不能为空!!!" };
		}

		String billDateS = tmpRow.get("申请日期").toString();
		java.util.Date billDate = null;
		if (billDateS.indexOf("CST") != -1) {
			SimpleDateFormat cstFormater = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
			try {
				billDate = cstFormater.parse(billDateS);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			if (billDateS != null) {
				if (billDateS.length() <= 16) {
					billDateS = billDateS + ":00.00";
				}
				billDate = java.sql.Timestamp.valueOf(billDateS);
			}
		}

		if (tmpRow.get("申请人") == null) {
			return new String[] { "1", "申请人不能为空!!!" };
		}
		long memberId = Long.parseLong(tmpRow.get("申请人").toString());

		// 判断考勤数据已封存
		String[] s = checkTime(memberId, billDate);
		if (s != null) {
			return s;
		}

		if (tmpRow.get("请假时长") == null) {
			return new String[] { "1", "请假总时长不能为空!!!" };
		}
		double timeLength = Double.parseDouble(tmpRow.get("请假时长").toString());

		// 判断加班账或年假账是否满足
		String typeName = null;
		try {
			EnumManager enumManager = (EnumManager) AppContext.getBean("enumManagerNew");
			String type = (String) tmpRow.get("请假类别");
			if ("调休".equals(typeName) || "年假".equals(typeName)) {
				typeName = type;
			} else {
				CtpEnumItem tmpCtpEnumItem = enumManager.getCacheEnumItem(Long.valueOf(type));
				if (tmpCtpEnumItem != null) {
					typeName = tmpCtpEnumItem.getShowvalue();
				}
			}
		} catch (Exception e) {
			return new String[] { "1", "系统异常..." + e.getLocalizedMessage() };
		}

		QingJiaChecker tmpQingJiaChecker = new QingJiaChecker();
		if ("调休".equals(typeName)) {
			return tmpQingJiaChecker.checkTiaoXiu(memberId, timeLength);
		} else if ("年假".equals(typeName)) {
			return tmpQingJiaChecker.checkNianJia(memberId, timeLength, billDate);
		}
		return null;
	}

	/**
	 * 封存月份检查
	 * 
	 * @param clockedBizData
	 * @return
	 */
	private String[] checkTime(long memberID, java.util.Date theDate) {
		Calendar tempCal = Calendar.getInstance();
		tempCal.setTime(new java.util.Date(System.currentTimeMillis()));
		int year = tempCal.get(Calendar.YEAR);
		int month = tempCal.get(Calendar.MONTH) + 1;
		int day = tempCal.get(Calendar.DAY_OF_MONTH);

		tempCal.setTime(theDate);
		int yearX = tempCal.get(Calendar.YEAR);
		int monthX = tempCal.get(Calendar.MONTH) + 1;

		String s = "" + yearX + "年" + monthX + "月的考勤数据已封存";

		//KaoQinDirectorMgr.getMe().getFinalDayByMemberId(memberID);
		int DAY_END = 9;
		if (year == yearX) {
			int x = month - monthX;
			if (x >= 2) {
				return new String[] { "1", "单据日期不合法," + s };
			} else if (x == 1) {

				Long finalDay = KaoQinDirectorMgr.getMe().getFinalDayByMemberId(memberID);
				if (finalDay != null) {
					DAY_END = (int) finalDay.longValue();
				}
				if (day >= DAY_END) {
					return new String[] { "1", "单据日期不合法," + s };
				}
			}
		} else if (yearX > year) {
			return new String[] { "1", "单据日期不合法,年份不对!!!" };
		} else {
			if (year - yearX >= 2) {
				return new String[] { "1", "单据日期不合法," + s };
			} else {
				Long finalDay = KaoQinDirectorMgr.getMe().getFinalDayByMemberId(memberID);
				if (finalDay != null) {
					DAY_END = (int) finalDay.longValue();
				}

				if (day >= DAY_END) {
					return new String[] { "1", "单据日期不合法," + s };
				}
			}
		}

		return null;

	}

}
