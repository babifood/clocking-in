package com.seeyon.apps.a8x.exchange.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.seeyon.apps.a8x.common.dhtmlxgrid.DhtmlxErrorXml;
import com.seeyon.apps.a8x.common.dhtmlxgrid.DhtmlxGridMgr;
import com.seeyon.apps.a8x.common.dhtmlxgrid.DhtmlxGridXml;
import com.seeyon.apps.a8x.common.domain.QueryCondition;
import com.seeyon.apps.a8x.common.domain.QueryResult;
import com.seeyon.apps.a8x.common.query.QueryConditionFactory;
import com.seeyon.apps.a8x.exchange.delegate.ExchangeLogDelegate;
import com.seeyon.apps.a8x.exchange.task.ExchangeTask;
import com.seeyon.apps.a8x.exchange.task.ExchangeTaskContext;
import com.seeyon.apps.a8x.exchange.task.FormExchangeTaskFactory;
import com.seeyon.apps.collaboration.manager.ColManager;
import com.seeyon.apps.collaboration.po.ColSummary;
import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.common.controller.BaseController;
import com.seeyon.ctp.common.exceptions.BusinessException;

public class ExchangeController extends BaseController {

	public ModelAndView dorepeat(HttpServletRequest request, HttpServletResponse response) {
		String templeteCode = request.getParameter("templeteCode");
		templeteCode = templeteCode == null ? "" : templeteCode.trim();
		long formRecordId = Long.valueOf(request.getParameter("formRecordId"));
		long summaryId = Long.valueOf(request.getParameter("summaryId"));
		long formAppId = Long.valueOf(request.getParameter("formAppId"));
		String step = request.getParameter("step");
		step = step == null ? "" : step.trim();

		String info = "";

		ColManager colManager = (ColManager) AppContext.getBean("colManager");
		ColSummary tmpColSummary = null;
		String subject = null;
		// 通过id查找对应的ColSummary
		try {
			tmpColSummary = colManager.getColSummaryById(summaryId);
			if (tmpColSummary != null) {
				subject = tmpColSummary.getSubject();
			}
		} catch (BusinessException e) {

		}
		String msg = "";
		ExchangeTaskContext context = new ExchangeTaskContext(formRecordId, summaryId, formAppId, templeteCode, subject);
		List<ExchangeTask> exchangers = FormExchangeTaskFactory.create(context);
		if (exchangers != null && (!exchangers.isEmpty())) {
			for (ExchangeTask exchanger : exchangers) {
				try {
					// 再次执行为True
					context.setRedo(true);
					context.setStep(step);
					// /开发执行交换任务
					exchanger.start();
					exchanger.execute();
				} catch (Exception e) {
					exchanger.logError("再次执行失败:" + e.getLocalizedMessage());
				} finally {
					exchanger.end();
				}
				msg = exchanger.getMessage();
			}

		}
		info = "执行完毕:\n" + msg;

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer;
		try {
			writer = response.getWriter();
			writer.println(info);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView("plugin/a8x/exchange/ExchangeLogList");
	}

	public ModelAndView datagrid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryResult result = null;
		DhtmlxGridXml xmler = null;
		String grid = request.getParameter("grid");
		QueryCondition condition = QueryConditionFactory.createQueryCondition(request);
		response.setContentType("text/xml");
		response.setCharacterEncoding("UTF-8");
		try {
			result = ExchangeLogDelegate.query(condition);
			if (grid == null || "".equals(grid)) {
				xmler = new DhtmlxGridXml("ExchangeLogGrid", result);
			} else {
				xmler = new DhtmlxGridXml(grid, result);
			}
			xmler.toXml(response.getWriter());
		} catch (Exception e) {
			try {
				DhtmlxErrorXml.toExceptionXml(response.getWriter(), e);
			} catch (Exception ee) {

			}
		}
		return null;
	}

	public ModelAndView excel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryResult result = null;
		DhtmlxGridXml xmler = null;
		String grid = request.getParameter("grid");
		QueryCondition condition = QueryConditionFactory.createQueryCondition(request);

		try {
			result = ExchangeLogDelegate.query(condition);
			if (grid == null || "".equals(grid)) {
				xmler = new DhtmlxGridXml("ExchangeLogGrid", result);
			} else {
				xmler = new DhtmlxGridXml(grid, result);
			}

			response.setContentType("application/msexecl");
			String fileName = new String("集成执行情况".getBytes("gb2312"), "iso-8859-1") + new java.sql.Date(System.currentTimeMillis()) + ".xls";
			response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
			response.setCharacterEncoding("utf-8");
			xmler.toHtmlExcel(response.getWriter());

		} catch (Exception e) {
			try {
				DhtmlxErrorXml.toExceptionXml(response.getWriter(), e);
			} catch (Exception ee) {

			}
		}
		return null;
	}

	public ModelAndView clearGridChache(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DhtmlxGridMgr.getMe().clearCahce();
		return null;
	}

	public ModelAndView sync_prf_by_record(HttpServletRequest request, HttpServletResponse response) {
		long summaryId = Long.valueOf(request.getParameter("id"));

		String info = "";

		ColManager colManager = (ColManager) AppContext.getBean("colManager");
		ColSummary tmpColSummary = null;
		String subject = null;
		// 通过id查找对应的ColSummary
		try {
			tmpColSummary = colManager.getColSummaryById(summaryId);
			if (tmpColSummary != null) {
				subject = tmpColSummary.getSubject();
			}
		} catch (BusinessException e) {

		}
		String msg = "";
		ExchangeTaskContext context = new ExchangeTaskContext(tmpColSummary.getFormRecordid(), summaryId, tmpColSummary.getFormAppid(), "DGFK", subject);
		List<ExchangeTask> exchangers = FormExchangeTaskFactory.create(context);
		if (exchangers != null && (!exchangers.isEmpty())) {
			for (ExchangeTask exchanger : exchangers) {
				try {
					// 再次执行为True
					context.setRedo(true);
					context.setStep("");
					// /开发执行交换任务
					exchanger.start();
					exchanger.execute();
				} catch (Exception e) {
					exchanger.logError("再次执行失败:" + e.getLocalizedMessage());
				} finally {
					exchanger.end();
				}
				msg = exchanger.getMessage();
			}
		}
		info = "执行完毕:\n" + msg;

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer;
		try {
			writer = response.getWriter();
			writer.println(info);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

}
