package com.seeyon.apps.a8x;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*
 * A8x插件配置相关参数
 */
public final class A8xConfig {

	private static Log log = LogFactory.getLog(A8xConfig.class);

	private A8xConfig() {

	}

	public static void initA8xConfig() {
		log.info("加载A8x插件配置成功...   ");
	}

}
