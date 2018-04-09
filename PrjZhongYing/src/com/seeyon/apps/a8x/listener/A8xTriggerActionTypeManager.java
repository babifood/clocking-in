package com.seeyon.apps.a8x.listener;

import com.seeyon.apps.a8x.clocked.service.ClockeGatherService;
import com.seeyon.apps.a8x.clocked.service.ClockedDataInitService;
import com.seeyon.apps.a8x.clocked.service.ClockedService;
import com.seeyon.apps.a8x.clocked.service.DaKaDataAdjustService;
import com.seeyon.apps.a8x.clocked.service.KaoQinDirectorMgr;
import com.seeyon.apps.a8x.oa.wrapper.A8xFormDataWrapper;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.form.modules.trigger.FormTriggerActionTypeManager;
import com.seeyon.ctp.form.modules.trigger.TriggerActionContext;

public class A8xTriggerActionTypeManager extends FormTriggerActionTypeManager {

	public void execute(TriggerActionContext context) throws BusinessException {

		if (context.getFormBean().getFormName().indexOf("机构信息") != -1) {
			//重新刷新考勤专员与机构的对应关系  
			KaoQinDirectorMgr.getMe().refresh();
			return;
		}

		A8xFormDataWrapper wrapper = new A8xFormDataWrapper(context.getFormBean(), context.getMasterBean(), true);


		String state = wrapper.getMasterDataMap2().get("状态") == null ? "" : wrapper.getMasterDataMap2().get("状态").toString();

		ClockedService clockedService = null;
		try {
			if (state.indexOf("初始化") != -1) {
				clockedService = new ClockedDataInitService(wrapper);
				clockedService.execute();
			} else if (state.indexOf("归集") != -1) {
				clockedService = new ClockeGatherService(wrapper);
				clockedService.execute();
			} else if (state.indexOf("打卡记录") != -1) {
				clockedService = new DaKaDataAdjustService(wrapper);
				clockedService.execute();
			} else {
				
			}
		} finally {
			if (clockedService != null) {
				clockedService.destory();
			}
		}

	}
}
