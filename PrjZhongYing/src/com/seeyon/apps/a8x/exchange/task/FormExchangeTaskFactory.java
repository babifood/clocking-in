package com.seeyon.apps.a8x.exchange.task;

import java.util.ArrayList;
import java.util.List;

final public class FormExchangeTaskFactory {

	private FormExchangeTaskFactory() {

	}

	public static List<ExchangeTask> create(ExchangeTaskContext context) {
		String code = context.getFormTmpleteCode().toUpperCase();
		List<ExchangeTask> tmpList = new ArrayList<ExchangeTask>(2);
		if (code.startsWith("KQYD")) {
			// 考勤异动
			tmpList.add(new T01KaoQinYinDongTask(context));
		} else if (code.startsWith("YGJB")) {
			// 员工加班申请单
			tmpList.add(new T02JiaBanTask(context));
			tmpList.add(new T02JiaBanAccountTask(context));
		} else if (code.startsWith("YGCC")) {
			// 出差申请单
			tmpList.add(new T03ChuChaTask(context));
		} else if (code.startsWith("YGQJ")) {
			// 请假
			tmpList.add(new T04QingJiaTask(context));
			tmpList.add(new T04QingJiaAccountTask(context));
		} 
		else if (code.startsWith("YDQJ")) {
			// 请假
			tmpList.add(new T05QingJiaMobileTask(context));
			tmpList.add(new T05QingJiaAccountMobileTask(context));
		}
		else {

		}
		return tmpList;
	}

}
