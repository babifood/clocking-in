package com.seeyon.apps.a8x.exchange.task;

import java.sql.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.seeyon.apps.a8x.clocked.dao.JiaBanAccountCreateDao;
import com.seeyon.apps.a8x.clocked.entity.JiaBanAccount;
import com.seeyon.apps.a8x.clocked.rule.ClockedBaseRule;
import com.seeyon.apps.a8x.clocked.service.KaoQinDirectorMgr;
import com.seeyon.apps.a8x.common.exception.ProcessingException;
import com.seeyon.apps.a8x.common.jdbc.DAOContext;
import com.seeyon.apps.a8x.common.jdbc.JdbcWorkUnit;
import com.seeyon.apps.a8x.common.jdbc.JdbcWorker;
import com.seeyon.apps.a8x.common.util.UtilDateTime;
import com.seeyon.apps.a8x.oa.dao.A8xContentAllDao;
import com.seeyon.apps.a8x.oa.delegate.A8xMemberDelegate;
import com.seeyon.apps.a8x.oa.entity.A8xContentAll;
import com.seeyon.apps.a8x.oa.entity.A8xMember;
import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.form.bean.FormBean;
import com.seeyon.ctp.form.service.FormManager;

/*
 * 加班申请单-集成交换任务
 */

public final class T02JiaBanAccountTask extends AbstrateExchangeTask {

	private static Log log = LogFactory.getLog(T02JiaBanAccountTask.class);

	private Map<String, Object> valuesMap;

	JiaBanAccount tmpJiaBanAccount;

	public T02JiaBanAccountTask(ExchangeTaskContext context) {
		super(context);

	}

	/*
	 * 开始
	 */
	public void start() {

	}

	public void execute() {
		long x = System.currentTimeMillis();
		log.info("[加班申请单]开始执行....");
		if (context.isRedo()) {
			String step = context.getStep();
			if (!"JIABANACCOUNT".equals(step)) {
				return;
			}
		}
		writeWaitingExchageLog("YGJB", "加班申请单", context.getFormTmpleteCode(), null, null, null, "JIABANACCOUNT");
		// 读取表单数据
		loadFormData();
		step2();
		step3();
		long times_ = (System.currentTimeMillis() - x) / 1000;
		log.info("[加班申请单]执行结束....,共耗时:" + times_);
	}

	/*
	 * 构造接口数据
	 */
	private void step2() {
		// 上一步如果执行不成功,退出
		if (isSuccess() == false) {
			return;
		}

		tmpJiaBanAccount = new JiaBanAccount();

		// 初始化
		tmpJiaBanAccount.initCreateFieldValue();
		// 姓名
		tmpJiaBanAccount.setMemberID(valuesMap.get("申请人") == null ? "" : valuesMap.get("申请人").toString());
		// 工号
		// dataMap.setWorkNum(xxx.getWorkNum());
		// 部门
		tmpJiaBanAccount.setDeptID(valuesMap.get("申请人部门") == null ? "" : valuesMap.get("申请人部门").toString());
		// 单位机构
		A8xMember tmpA8xMember = A8xMemberDelegate.read(tmpJiaBanAccount.getMemberID());
		if (tmpA8xMember == null) {
			logError("获取申请人所属机构失败");
			return;
		}

		Long organID = KaoQinDirectorMgr.getMe().getOrganID(tmpA8xMember.getPath());
		if(organID==null){
			logError("获取申请人所属机构失败");
			return;
		}
		tmpJiaBanAccount.setOrganID(organID.toString());

		// 业务单据号
		tmpJiaBanAccount.setBillNum(valuesMap.get("流程编号") == null ? "" : valuesMap.get("流程编号").toString());
		// 申请日期
		java.util.Date tempDate = null;
		if (valuesMap.get("申请日期") != null) {
			tempDate = (java.util.Date) valuesMap.get("申请日期");
		}
		tmpJiaBanAccount.setBillDate(tempDate == null ? null : new java.sql.Date(tempDate.getTime()));
		// 加班类型
		tmpJiaBanAccount.setBillType(valuesMap.get("加班类别一级枚举") == null ? "" : valuesMap.get("加班类别一级枚举").toString());

		// 时长
		String temp = valuesMap.get("人事核定时长") == null ? "0" : valuesMap.get("人事核定时长").toString().trim();
		tmpJiaBanAccount.setJiaBanTimeLength(Double.parseDouble(temp));
		// 系数
		tmpJiaBanAccount.setRatio(1);
		// 偏差时长
		tmpJiaBanAccount.setOffset(0);
		// 用于调休时长
		tmpJiaBanAccount.setDiKouTimeLength(0);
		// 用于计费时长
		String billType2 = "";
		
		
		
		billType2 = valuesMap.get("调休或计加班费CODE") == null ? "" : valuesMap.get("调休或计加班费CODE").toString();
		if(!"1".equals(billType2)){
			billType2 = valuesMap.get("加班类别二级枚举") == null ? "" : valuesMap.get("加班类别二级枚举").toString();
		}
		if ("1".equals(billType2)) {
			tmpJiaBanAccount.setJiFeiTimeLength(tmpJiaBanAccount.getJiaBanTimeLength());
		}

		// 生效日期
		if (valuesMap.get("员工提交实际加班时间左") != null) {
			tempDate = (java.util.Date) valuesMap.get("员工提交实际加班时间左");
			tmpJiaBanAccount.setShengXiaoDate(new java.sql.Date(tempDate.getTime()));
		}

		temp = valuesMap.get("指定调休日期日期选择器休过期作废") == null ? "" : valuesMap.get("指定调休日期日期选择器休过期作废").toString().trim();
		if (temp.equals("1")) {
			// 失效日期
			tempDate = (java.util.Date) valuesMap.get("指定调休日期");
			if (tempDate != null) {
				tmpJiaBanAccount.setShiXiaoDate(UtilDateTime.getDayEndByDay(tempDate, 1));
			}
		} else {
			tmpJiaBanAccount.setShiXiaoDate(ClockedBaseRule.getJiaBanShiXiaoDate(tmpJiaBanAccount.getShengXiaoDate(), tmpJiaBanAccount.getOrganID()));
		}
		// 作废时长
		tmpJiaBanAccount.setZuoFeiTimeLength(0);
		// 剩余时长
		tmpJiaBanAccount.setShengYuTimeLength(tmpJiaBanAccount.getJiaBanTimeLength() - tmpJiaBanAccount.getJiFeiTimeLength());
	}

	/*
	 * 执行WebService接口
	 */
	private void step3() {
		if (isSuccess() == false) {
			return;
		}
		try {
			FormManager formManager = (FormManager) AppContext.getBean("formManager");
			FormBean formBean = formManager.getFormByFormCode("JIABANACCOUNT");
			if (formBean == null) {
				throw new BusinessException("模板编号:[JIABANACCOUNT]表单,不存在...");
			}
			A8xContentAll content = new A8xContentAll();
			content.copy4New(formBean, AppContext.currentUserId(), tmpJiaBanAccount.getId());
			create(tmpJiaBanAccount, content);
		} catch (Exception e) {
			logError("生成数据失败." + e.getLocalizedMessage());
		}
	}

	public void end() {
		if (context.isRedo()) {
			String step = context.getStep();
			if (!"JIABANACCOUNT".equals(step)) {
				return;
			}
		}

		// 记录执行情况
		Date tmpFormDate = new java.sql.Date(System.currentTimeMillis());
		// 业务表单流水号
		String tmpFormCode = valuesMap.get("流程编号") == null ? "" : valuesMap.get("流程编号").toString();
		// 业务表单流程标题
		String tmpFormInfo = context.getSubject();
		// 记录执行日志
		if (this.isSuccess()) {
			writeExchageLog("Y", "YGJB", "加班申请单", context.getFormTmpleteCode(), tmpFormDate, tmpFormCode, tmpFormInfo, "JIABANACCOUNT");
		} else {
			writeExchageLog("N", "YGJB", "加班申请单", context.getFormTmpleteCode(), tmpFormDate, tmpFormCode, tmpFormInfo, "JIABANACCOUNT");
		}
		if (valuesMap != null) {
			valuesMap.clear();
			valuesMap = null;
		}
	}

	/*
	 * 读取OA表单信息
	 */
	private boolean loadFormData() {
		log.info("start:获取[加班申请单]数据.");
		// 待定5秒
		// sleepx(5000);
		boolean flag = loadFormData2();
		if (valuesMap == null) {
			// 再待定3秒
			sleepx(3000);
			flag = loadFormData2();
		}
		if (valuesMap == null) {
			// 再待定2秒
			sleepx(2000);
			flag = loadFormData2();
		}
		if (valuesMap == null) {
			// 再待定10秒
			sleepx(10000);
			flag = loadFormData2();
		}
		if (valuesMap == null) {
			logError("读取表单数据失败.");
		}
		log.info("end:获取[加班申请单]数据");
		return flag;
	}

	/*
	 * 读取OA表单信息
	 */
	private boolean loadFormData2() {
		log.info("start:获取表单数据.");
		boolean flag = formDataReader.loadFormData(context.getFormAppId(), context.getFormRecordId());
		if (flag) {
			valuesMap = formDataReader.getMasterDataMap2();
		}
		log.info("end:获取表单数据");
		return flag;
	}

	public boolean create(final JiaBanAccount theModel, final A8xContentAll theContentAll) throws ProcessingException {
		return JdbcWorker.wrapInTransaction(new JdbcWorkUnit<Boolean>() {
			public Boolean execute(DAOContext daoContext) throws ProcessingException {
				JiaBanAccountCreateDao dao = new JiaBanAccountCreateDao(daoContext);
				int i = dao.create(theModel);

				A8xContentAllDao contentDao = new A8xContentAllDao(daoContext);
				contentDao.create(theContentAll);
				return i == 1;
			}
		});
	}
}
