package com.seeyon.apps.a8x;

import org.apache.log4j.Logger;

import com.seeyon.ctp.common.plugin.PluginDefinition;
import com.seeyon.ctp.common.plugin.PluginInitializer;

public class A8xPluginInitializer implements PluginInitializer {

	@Override
	public boolean isAllowStartup(PluginDefinition aPlugin, Logger aLogger) {
		return true;
//		if ("1".equals(aPlugin.getPluginProperty("a8x.enabled"))) {
//			return true;
//		} else {
//			return false;
//		}

	}

}
