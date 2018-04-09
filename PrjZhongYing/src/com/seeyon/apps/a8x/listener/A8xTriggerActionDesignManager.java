package com.seeyon.apps.a8x.listener;

import com.seeyon.ctp.form.modules.trigger.FormTriggerActionDesignManager;
import com.seeyon.ctp.form.util.Enums.FormType;

public class A8xTriggerActionDesignManager extends FormTriggerActionDesignManager {
	public String getId() {
		return "ClockedRecordTrigger";
	}

	public String geti18nName() {
		return "考勤数据处理";
	}

	public String configPageURL() {
		return null;
	}

	public boolean canUse4FormType(FormType type) {
		return true;
	}

	public boolean canUse() {
		return true;
	}

	public String getActionTypeManagerName() {
		return "a8xTriggerActionTypeManager";
	}
}