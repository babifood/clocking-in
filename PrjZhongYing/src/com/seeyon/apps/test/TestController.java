package com.seeyon.apps.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.seeyon.apps.a8x.clocked.service.KaoQinDirectorMgr;
import com.seeyon.apps.a8x.common.dhtmlxgrid.DhtmlxGridMgr;
import com.seeyon.ctp.common.controller.BaseController;

public class TestController extends BaseController {

	public ModelAndView refreshKaoQinSQL(HttpServletRequest request, HttpServletResponse response) throws Exception {
		KaoQinDirectorMgr.getMe().refresh();

		return null;
	}

	public ModelAndView clearGridChache(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DhtmlxGridMgr.getMe().clearCahce();
		return null;
	}
}
