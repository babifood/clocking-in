package com.seeyon.apps.a8x.clocked.entity;

import java.io.Serializable;

/*
 * 考勤结果-实体
 */
public class ClockedResultBase implements Serializable {
	private static final long serialVersionUID = 3274652133733320835L;
	// id
	private long id;
	// 审核状态
	private int state;
	// 发起人
	private long start_member_id;
	// 发起时间
	private java.sql.Timestamp start_date;
	// 审核人
	private long approve_member_id;
	// 审核时间
	private java.sql.Timestamp approve_date;
	// 流程状态
	private int finishedflag;
	// 核定状态
	private int ratifyflag;
	// 核定人
	private long ratify_member_id;
	// 核定时间
	private java.sql.Timestamp ratify_date;
	// 修改者标识
	private long modify_member_id;
	// 修改日期
	private java.sql.Timestamp modify_date;
	// 年度
	private int year;
	// 月份
	private int month;
	// 姓名
	private String memberID;
	// 工号
	private String workNum;
	// 部门
	private String dept;
	// 单位机构
	private String organ;
	// 日期
	private java.sql.Date clockDate;
	// 星期
	private String week;
	// 考勤标志
	private int clockFlag;
	// 迟到
	private int chiDao;
	// 早退
	private int zaoTui;
	// 旷工
	private int kuangGong;
	// 打卡原始时长
	private double daKaTimeLength;
	// 工作时长
	private double workHours;
	// 缺勤小时数
	private double queQin;
	// 异动小时数
	private double yiDong;
	// 出差小时数
	private double chuCha;
	// 请假小时数
	private double qingJia;
	// 事假
	private double shiJia;
	// 年假
	private double nianJia;
	// 婚假
	private double hunJia;
	// 丧假
	private double sangJia;
	// 病假
	private double bingJia;
	// 其它请假
	private double otherQingJia;
	// 培训假
	private double peiXunJia;
	// 产假
	private double chanJia;
	// 陪产假
	private double peiChanJia;
	// 调休
	private double tiaoXiu;
	// 加班小时数
	private double jiaBan;
	// 餐补个数
	private int canBu;
	// 标准上班时间
	private String beginTime;
	// 标准下班时间
	private String endTime;
	// 打卡起始时间
	private java.sql.Timestamp clockBeginTime;
	// 打卡结束时间
	private java.sql.Timestamp clockEndTime;
	// 业务开始时间
	private java.sql.Timestamp eventBeginTime;
	// 业务结束时间
	private java.sql.Timestamp eventEndTime;

	// 打卡地点
	private String daKaLocation;
	// 考勤方式
	private String daKaType;

	// 标准工作时长
	double standWorkTimeLength;

	/**
	 * Default Constructor
	 */
	public ClockedResultBase() {
		// do nothing
	}

	// Bean methods......
	public long getId() {
		return this.id;
	}

	public void setId(long theId) {
		this.id = theId;
	}

	public int getState() {
		return this.state;
	}

	public void setState(int theState) {
		this.state = theState;
	}

	public long getStart_member_id() {
		return this.start_member_id;
	}

	public void setStart_member_id(long theStart_member_id) {
		this.start_member_id = theStart_member_id;
	}

	public java.sql.Timestamp getStart_date() {
		return this.start_date;
	}

	public void setStart_date(java.sql.Timestamp theStart_date) {
		this.start_date = theStart_date;
	}

	public long getApprove_member_id() {
		return this.approve_member_id;
	}

	public void setApprove_member_id(long theApprove_member_id) {
		this.approve_member_id = theApprove_member_id;
	}

	public java.sql.Timestamp getApprove_date() {
		return this.approve_date;
	}

	public void setApprove_date(java.sql.Timestamp theApprove_date) {
		this.approve_date = theApprove_date;
	}

	public int getFinishedflag() {
		return this.finishedflag;
	}

	public void setFinishedflag(int theFinishedflag) {
		this.finishedflag = theFinishedflag;
	}

	public int getRatifyflag() {
		return this.ratifyflag;
	}

	public void setRatifyflag(int theRatifyflag) {
		this.ratifyflag = theRatifyflag;
	}

	public long getRatify_member_id() {
		return this.ratify_member_id;
	}

	public void setRatify_member_id(long theRatify_member_id) {
		this.ratify_member_id = theRatify_member_id;
	}

	public java.sql.Timestamp getRatify_date() {
		return this.ratify_date;
	}

	public void setRatify_date(java.sql.Timestamp theRatify_date) {
		this.ratify_date = theRatify_date;
	}

	public long getModify_member_id() {
		return this.modify_member_id;
	}

	public void setModify_member_id(long theModify_member_id) {
		this.modify_member_id = theModify_member_id;
	}

	public java.sql.Timestamp getModify_date() {
		return this.modify_date;
	}

	public void setModify_date(java.sql.Timestamp theModify_date) {
		this.modify_date = theModify_date;
	}

	public int getYear() {
		return this.year;
	}

	public void setYear(int theYear) {
		this.year = theYear;
	}

	public int getMonth() {
		return this.month;
	}

	public void setMonth(int theMonth) {
		this.month = theMonth;
	}

	public String getMemberID() {
		return this.memberID;
	}

	public void setMemberID(String theMemberID) {
		this.memberID = theMemberID;
	}

	public String getWorkNum() {
		return this.workNum;
	}

	public void setWorkNum(String theWorkNum) {
		this.workNum = theWorkNum;
	}

	public String getDept() {
		return this.dept;
	}

	public void setDept(String theDept) {
		this.dept = theDept;
	}

	public String getOrgan() {
		return this.organ;
	}

	public void setOrgan(String theOrgan) {
		this.organ = theOrgan;
	}

	public java.sql.Date getClockDate() {
		return this.clockDate;
	}

	public void setClockDate(java.sql.Date theClockDate) {
		this.clockDate = theClockDate;
	}

	public String getWeek() {
		return this.week;
	}

	public void setWeek(String theWeek) {
		this.week = theWeek;
	}

	public int getClockFlag() {
		return this.clockFlag;
	}

	public void setClockFlag(int theClockFlag) {
		this.clockFlag = theClockFlag;
	}

	public int getChiDao() {
		return this.chiDao;
	}

	public void setChiDao(int theChiDao) {
		this.chiDao = theChiDao;
	}

	public int getZaoTui() {
		return this.zaoTui;
	}

	public void setZaoTui(int theZaoTui) {
		this.zaoTui = theZaoTui;
	}

	public int getKuangGong() {
		return this.kuangGong;
	}

	public void setKuangGong(int theKuangGong) {
		this.kuangGong = theKuangGong;
	}

	public double getDaKaTimeLength() {
		return this.daKaTimeLength;
	}

	public void setDaKaTimeLength(double theDaKaTimeLength) {
		this.daKaTimeLength = theDaKaTimeLength;
	}

	public double getWorkHours() {
		return this.workHours;
	}

	public void setWorkHours(double theWorkHours) {
		this.workHours = theWorkHours;
	}

	public double getQueQin() {
		return this.queQin;
	}

	public void setQueQin(double theQueQin) {
		this.queQin = theQueQin;
	}

	public double getYiDong() {
		return this.yiDong;
	}

	public void setYiDong(double theYiDong) {
		this.yiDong = theYiDong;
	}

	public double getChuCha() {
		return this.chuCha;
	}

	public void setChuCha(double theChuCha) {
		this.chuCha = theChuCha;
	}

	public double getQingJia() {
		return this.qingJia;
	}

	public void setQingJia(double theQingJia) {
		this.qingJia = theQingJia;
	}

	public double getShiJia() {
		return this.shiJia;
	}

	public void setShiJia(double theShiJia) {
		this.shiJia = theShiJia;
	}

	public double getNianJia() {
		return this.nianJia;
	}

	public void setNianJia(double theNianJia) {
		this.nianJia = theNianJia;
	}

	public double getHunJia() {
		return this.hunJia;
	}

	public void setHunJia(double theHunJia) {
		this.hunJia = theHunJia;
	}

	public double getSangJia() {
		return this.sangJia;
	}

	public void setSangJia(double theSangJia) {
		this.sangJia = theSangJia;
	}

	public double getBingJia() {
		return this.bingJia;
	}

	public void setBingJia(double theBingJia) {
		this.bingJia = theBingJia;
	}

	public double getOtherQingJia() {
		return this.otherQingJia;
	}

	public void setOtherQingJia(double theOtherQingJia) {
		this.otherQingJia = theOtherQingJia;
	}

	public double getPeiXunJia() {
		return this.peiXunJia;
	}

	public void setPeiXunJia(double thePeiXunJia) {
		this.peiXunJia = thePeiXunJia;
	}

	public double getChanJia() {
		return this.chanJia;
	}

	public void setChanJia(double theChanJia) {
		this.chanJia = theChanJia;
	}

	public double getPeiChanJia() {
		return this.peiChanJia;
	}

	public void setPeiChanJia(double thePeiChanJia) {
		this.peiChanJia = thePeiChanJia;
	}

	public double getTiaoXiu() {
		return this.tiaoXiu;
	}

	public void setTiaoXiu(double theTiaoXiu) {
		this.tiaoXiu = theTiaoXiu;
	}

	public double getJiaBan() {
		return this.jiaBan;
	}

	public void setJiaBan(double theJiaBan) {
		this.jiaBan = theJiaBan;
	}

	public int getCanBu() {
		return this.canBu;
	}

	public void setCanBu(int theCanBu) {
		this.canBu = theCanBu;
	}

	public String getBeginTime() {
		return this.beginTime;
	}

	public void setBeginTime(String theBeginTime) {
		this.beginTime = theBeginTime;
	}

	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(String theEndTime) {
		this.endTime = theEndTime;
	}

	public java.sql.Timestamp getClockBeginTime() {
		return this.clockBeginTime;
	}

	public void setClockBeginTime(java.sql.Timestamp theClockBeginTime) {
		this.clockBeginTime = theClockBeginTime;
	}

	public java.sql.Timestamp getClockEndTime() {
		return this.clockEndTime;
	}

	public void setClockEndTime(java.sql.Timestamp theClockEndTime) {
		this.clockEndTime = theClockEndTime;
	}

	public java.sql.Timestamp getEventBeginTime() {
		return this.eventBeginTime;
	}

	public void setEventBeginTime(java.sql.Timestamp theEventBeginTime) {
		this.eventBeginTime = theEventBeginTime;
	}

	public java.sql.Timestamp getEventEndTime() {
		return this.eventEndTime;
	}

	public void setEventEndTime(java.sql.Timestamp theEventEndTime) {
		this.eventEndTime = theEventEndTime;
	}

	public String getDaKaLocation() {
		return daKaLocation;
	}

	public void setDaKaLocation(String daKaLocation) {
		this.daKaLocation = daKaLocation;
	}

	public String getDaKaType() {
		return daKaType;
	}

	public void setDaKaType(String daKaType) {
		this.daKaType = daKaType;
	}

	public double getStandWorkTimeLength() {
		return standWorkTimeLength;
	}

	public void setStandWorkTimeLength(double standWorkTimeLength) {
		this.standWorkTimeLength = standWorkTimeLength;
	}

	public String toString() {
		StringBuffer returnString = new StringBuffer();
		returnString.append("ClockedResult[");
		// Set member attributes
		returnString.append("id = " + this.id + ";\n");
		returnString.append("state = " + this.state + ";\n");
		returnString.append("start_member_id = " + this.start_member_id + ";\n");
		returnString.append("start_date = " + this.start_date + ";\n");
		returnString.append("approve_member_id = " + this.approve_member_id + ";\n");
		returnString.append("approve_date = " + this.approve_date + ";\n");
		returnString.append("finishedflag = " + this.finishedflag + ";\n");
		returnString.append("ratifyflag = " + this.ratifyflag + ";\n");
		returnString.append("ratify_member_id = " + this.ratify_member_id + ";\n");
		returnString.append("ratify_date = " + this.ratify_date + ";\n");
		returnString.append("modify_member_id = " + this.modify_member_id + ";\n");
		returnString.append("modify_date = " + this.modify_date + ";\n");
		returnString.append("year = " + this.year + ";\n");
		returnString.append("month = " + this.month + ";\n");
		returnString.append("memberID = " + this.memberID + ";\n");
		returnString.append("workNum = " + this.workNum + ";\n");
		returnString.append("dept = " + this.dept + ";\n");
		returnString.append("organ = " + this.organ + ";\n");
		returnString.append("clockDate = " + this.clockDate + ";\n");
		returnString.append("week = " + this.week + ";\n");
		returnString.append("clockFlag = " + this.clockFlag + ";\n");
		returnString.append("chiDao = " + this.chiDao + ";\n");
		returnString.append("zaoTui = " + this.zaoTui + ";\n");
		returnString.append("kuangGong = " + this.kuangGong + ";\n");
		returnString.append("daKaTimeLength = " + this.daKaTimeLength + ";\n");
		returnString.append("workHours = " + this.workHours + ";\n");
		returnString.append("queQin = " + this.queQin + ";\n");
		returnString.append("yiDong = " + this.yiDong + ";\n");
		returnString.append("chuCha = " + this.chuCha + ";\n");
		returnString.append("qingJia = " + this.qingJia + ";\n");
		returnString.append("shiJia = " + this.shiJia + ";\n");
		returnString.append("nianJia = " + this.nianJia + ";\n");
		returnString.append("hunJia = " + this.hunJia + ";\n");
		returnString.append("sangJia = " + this.sangJia + ";\n");
		returnString.append("bingJia = " + this.bingJia + ";\n");
		returnString.append("otherQingJia = " + this.otherQingJia + ";\n");
		returnString.append("peiXunJia = " + this.peiXunJia + ";\n");
		returnString.append("chanJia = " + this.chanJia + ";\n");
		returnString.append("peiChanJia = " + this.peiChanJia + ";\n");
		returnString.append("tiaoXiu = " + this.tiaoXiu + ";\n");
		returnString.append("jiaBan = " + this.jiaBan + ";\n");
		returnString.append("canBu = " + this.canBu + ";\n");
		returnString.append("beginTime = " + this.beginTime + ";\n");
		returnString.append("endTime = " + this.endTime + ";\n");
		returnString.append("clockBeginTime = " + this.clockBeginTime + ";\n");
		returnString.append("clockEndTime = " + this.clockEndTime + ";\n");
		returnString.append("eventBeginTime = " + this.eventBeginTime + ";\n");
		returnString.append("eventEndTime = " + this.eventEndTime + ";\n");
		returnString.append("]\n");
		return returnString.toString();
	}
}