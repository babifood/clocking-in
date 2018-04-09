package com.seeyon.apps.a8x.schedule.controller;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import com.seeyon.apps.a8x.schedule.A8xQuartzManager;
import com.seeyon.apps.a8x.schedule.manager.A8xConfigService;
import com.seeyon.apps.a8x.schedule.manager.A8xScheduleManager;
import com.seeyon.apps.a8x.schedule.model.A8xSchedule;
import com.seeyon.ctp.common.controller.BaseController;
import com.seeyon.ctp.util.FlipInfo;
import com.seeyon.ctp.util.annotation.NeedlessCheckLogin;

public class A8xScheduleController extends BaseController {
	private static final Log log = LogFactory.getLog(A8xScheduleController.class);
	private static final A8xConfigService configService = A8xConfigService.getInstance();
	private A8xScheduleManager a8xScheduleManager;

	@NeedlessCheckLogin
	public ModelAndView scheduleFrame(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView view = new ModelAndView("plugin/a8x/schedule/scheduleFrame");
		FlipInfo fi = this.a8xScheduleManager.findScheduleList(new FlipInfo(), new HashMap<String, Object>());
		request.setAttribute("ffscheduleTable", fi);		
		return view;
	}

	@NeedlessCheckLogin
	public ModelAndView showScheduleDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView view = new ModelAndView("plugin/a8x/schedule/scheduleDetail");
		String flowId = request.getParameter("id");
		A8xSchedule bean = configService.getScheduleByID(flowId);
		view.addObject("bean", bean);
		view.addObject("retFixed", Integer.valueOf(getISFixed(bean.getQuartz_code())));
		return view;
	}

	@NeedlessCheckLogin
	public ModelAndView showScheduleUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView view = new ModelAndView("plugin/a8x/schedule/scheduleUpdate");
		String flowId = request.getParameter("id");
		if ("-1".equals(flowId)) {
			A8xSchedule bean = new A8xSchedule();
			bean.setQuartz_code("0.1.1");
			bean.setEnable(false);
			bean.setSchedule_id("-1");
			view.addObject("bean", bean);
			view.addObject("retFixed", Integer.valueOf(getISFixed(bean.getQuartz_code())));
		} else {
			A8xSchedule bean = configService.getScheduleByID(flowId);
			view.addObject("bean", bean);
			view.addObject("retFixed", Integer.valueOf(getISFixed(bean.getQuartz_code())));
		}
		return view;
	}

	@NeedlessCheckLogin
	public ModelAndView scheduleUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView view = new ModelAndView("plugin/a8x/schedule/scheduleDetail");
		A8xSchedule bean = new A8xSchedule();
		parseRequest(request, bean);
		try {
			boolean flag = false;
			bean.setEnable("1".equals(request.getParameter("isEnable")));
			if ("-1".equals(bean.getSchedule_id())) {
				flag = configService.createSchedule(bean);
			} else {
				flag = configService.updateSchedule(bean);
			}
			if (flag == false) {
				bean = configService.getScheduleByID(bean.getSchedule_id());
			}
			view.addObject("bean", bean);
			view.addObject("retFixed", Integer.valueOf(getISFixed(bean.getQuartz_code())));
			A8xQuartzManager.getInstance().refresh();
		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} catch (Throwable e) {
			log.error("引擎刷新上下文异常" + e.getLocalizedMessage());

		}
		// view.addObject("retMsg", retMsg);
		return view;
	}

	private int getISFixed(String dateCode) {
		if ((dateCode != null) && ("1".equalsIgnoreCase(dateCode.substring(0, 1)))) {
			return 1;
		}
		return 0;
	}

	@SuppressWarnings("rawtypes")
	public static Object parseRequest(HttpServletRequest request, Object bean) {
		Enumeration enums = request.getParameterNames();
		while (enums.hasMoreElements()) {
			Object obj = enums.nextElement();
			try {
				Class cls = PropertyUtils.getPropertyType(bean, obj.toString());
				if (cls != null) {
					Object beanValue = ConvertUtils.convert(request.getParameter(obj.toString()), cls);
					PropertyUtils.setProperty(bean, obj.toString(), beanValue);
				}
			} catch (Exception e) {
				log.error("转换" + bean + ":" + obj.toString() + " 时出错" + e.getMessage(), e);
			}
		}
		return bean;
	}

	public A8xScheduleManager getA8xScheduleManager() {
		return this.a8xScheduleManager;
	}

	public void setA8xScheduleManager(A8xScheduleManager a8xScheduleManager) {
		this.a8xScheduleManager = a8xScheduleManager;
	}
}