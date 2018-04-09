package com.seeyon.apps.a8x.schedule.manager;

import java.util.List;
import java.util.Map;

import com.seeyon.apps.a8x.schedule.model.A8xSchedule;
import com.seeyon.ctp.util.FlipInfo;

public abstract interface A8xScheduleManager {
	public abstract FlipInfo findScheduleList(FlipInfo paramFlipInfo, Map<String, Object> paramMap);
	
	public List<A8xSchedule> loadALL();
}