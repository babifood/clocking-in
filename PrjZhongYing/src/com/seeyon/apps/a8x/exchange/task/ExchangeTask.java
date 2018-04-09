package com.seeyon.apps.a8x.exchange.task;

/*
 * 集成交换任务接口,该接口具体实现类,在线程执行.
 */
public interface ExchangeTask {

	/**
	 * 开始
	 */
	public void start();

	/**
	 * 执行
	 */
	public void execute();

	/**
	 * 结束
	 */
	public void end();

	/**
	 * 获取处理情况
	 * 
	 * @return
	 */
	public String getMessage();
	
	public boolean isSuccess();

	public void logError(String errorInfo);

}
