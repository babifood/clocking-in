package com.seeyon.apps.a8x.exchange.task;

import java.util.List;

/*
 * Form集成交换任务执行线程
 */

public class FormExchangeRunnable implements Runnable {

	private List<ExchangeTask> exchangers;

	public FormExchangeRunnable(List<ExchangeTask> aExchangers) {
		this.exchangers = aExchangers;
	}

	public void run() {
		for (ExchangeTask exchanger : exchangers) {
			try {
				exchanger.start();
				exchanger.execute();
				if (!exchanger.isSuccess()) {
					//退出
					return;
				}
			} catch (Exception e) {
				exchanger.logError("执行错误:" + e.getLocalizedMessage());
				//退出
				return;
			} finally {
				exchanger.end();
			}
		}
	}
}
