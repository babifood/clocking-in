package com.seeyon.apps.a8x;


import org.quartz.SchedulerException;

import com.seeyon.apps.a8x.schedule.A8xQuartzManager;
import com.seeyon.ctp.common.AbstractSystemInitializer;

public class A8xInitializer extends AbstractSystemInitializer {

    public void destroy() {
        System.out.println("销毁A8x模块");
    }

    public void initialize() {
    	//加载配置文件信息
    	A8xConfig.initA8xConfig();
    	
    	//启动定时器
    	try {
			A8xQuartzManager.getInstance().refresh();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        System.out.println("初始化A8x模块");
    }
    
}