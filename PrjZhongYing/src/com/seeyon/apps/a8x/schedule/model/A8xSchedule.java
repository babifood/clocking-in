package com.seeyon.apps.a8x.schedule.model;

import java.io.Serializable;

public class A8xSchedule implements Serializable {
	private static final long serialVersionUID = 5752723181291786031L;

	// 定时器标识
	private String schedule_id;
	// 定时器名称
	private String schedule_name;
	// 定时器描述
	private String schedule_desc;
	// 是否启用
	private boolean isEnable;
	// 运行编码
	private String quartz_code;
	// 运行规则
	private String quartz_rule;
	// 任务标识
	private String flow_id;
	// 任务名称
	private String flow_name;
	// 创建时间
	private java.sql.Timestamp create_time;

	public String getQuartz_code() {
		return this.quartz_code;
	}

	public void setQuartz_code(String quartz_code) {
		this.quartz_code = quartz_code;
	}

	public String getFlow_id() {
		return this.flow_id;
	}

	public void setFlow_id(String flow_id) {
		this.flow_id = flow_id;
	}

	public String getFlow_name() {
		return this.flow_name;
	}

	public void setFlow_name(String flow_name) {
		this.flow_name = flow_name;
	}

	public boolean isEnable() {
		return this.isEnable;
	}

	public void setEnable(boolean enable) {
		this.isEnable = enable;
	}

	public String getSchedule_id() {
		return this.schedule_id;
	}

	public void setSchedule_id(String schedule_id) {
		this.schedule_id = schedule_id;
	}

	public String getSchedule_name() {
		return this.schedule_name;
	}

	public void setSchedule_name(String schedule_name) {
		this.schedule_name = schedule_name;
	}

	public String getSchedule_desc() {
		return this.schedule_desc;
	}

	public void setSchedule_desc(String schedule_desc) {
		this.schedule_desc = schedule_desc;
	}

	public java.sql.Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(java.sql.Timestamp create_time) {
		this.create_time = create_time;
	}

	public String getQuartz_rule() {
		return quartz_rule;
	}

	public void setQuartz_rule(String quartz_rule) {
		this.quartz_rule = quartz_rule;
	}

	/**
	 * 生成定时器规则
	 */
	public void generateScheduleRule() {
		String cron = "";
		String quartzCode = getQuartz_code();
		if (quartzCode != null) {
			String[] arr = quartzCode.split(",");
			if (arr.length > 1) {
				if ("0".equalsIgnoreCase(arr[0])) {
					int count = Integer.parseInt(arr[1]);
					int qty = Integer.parseInt(arr[2]);
					if (qty == 1)
						cron = "0 0/" + count + " * * * ? ";
					else if (qty == 2)
						cron = "0 0 0/" + count + " * * ? ";
					else if (qty == 3)
						cron = "0 0 0/" + count * 24 + " * * ? ";
					else if (qty == 4)
						cron = "0 0 0/" + count * 24 * 7 + " * * ? ";
					else if (qty == 5)
						cron = "0 0 0 1 1/" + count + " ? ";
				} else {
					String[] weekArr = { "MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN" };
					if ("3".equalsIgnoreCase(arr[1])) {
						cron = "0 " + arr[4] + " " + arr[3] + " " + arr[2] + " * ?";
					} else if ("2".equalsIgnoreCase(arr[1])) {
						int weekInt = Integer.parseInt(arr[2]);
						cron = "0 " + arr[4] + " " + arr[3] + " ? * " + weekArr[(weekInt - 1)];
					} else if ("1".equalsIgnoreCase(arr[1])) {
						cron = "0 " + arr[4] + " " + arr[3] + " * * ?";
					}
				}
			}
		}
		this.setQuartz_rule(cron);
	}

}