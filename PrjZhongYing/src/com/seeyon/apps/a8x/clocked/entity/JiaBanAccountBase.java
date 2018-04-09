package com.seeyon.apps.a8x.clocked.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.util.UUIDLong;

/*
 * 员工加班账表-实体
 */
public class JiaBanAccountBase implements Serializable {
	private static final long serialVersionUID = 7999586694532386556L;

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
	// 排序号
	private int sort;
	// 修改者标识
	private long modify_member_id;
	// 修改日期
	private java.sql.Timestamp modify_date;
	// 姓名
	private String memberID;
	// 工号
	private String workNum;
	// 部门
	private String deptID;
	// 单位机构
	private String organID;
	// 业务单据号
	private String billNum;
	// 申请日期
	private java.sql.Date billDate;
	// 加班类型
	private String billType;
	// 时长
	private double jiaBanTimeLength;
	// 系数
	private double ratio;
	// 偏差时长
	private double offset;
	// 用于调休时长
	private double diKouTimeLength;
	// 用于计费时长
	private double jiFeiTimeLength;
	// 生效日期
	private java.sql.Date shengXiaoDate;
	// 失效日期
	private java.sql.Date shiXiaoDate;
	// 作废时长
	private double zuoFeiTimeLength;
	// 剩余时长
	private double shengYuTimeLength;

	/**
	 * Default Constructor
	 */
	public JiaBanAccountBase() {
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

	public int getSort() {
		return this.sort;
	}

	public void setSort(int theSort) {
		this.sort = theSort;
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

	public String getDeptID() {
		return this.deptID;
	}

	public void setDeptID(String theDeptID) {
		this.deptID = theDeptID;
	}

	public String getOrganID() {
		return this.organID;
	}

	public void setOrganID(String theOrganID) {
		this.organID = theOrganID;
	}

	public String getBillNum() {
		return this.billNum;
	}

	public void setBillNum(String theBillNum) {
		this.billNum = theBillNum;
	}

	public java.sql.Date getBillDate() {
		return this.billDate;
	}

	public void setBillDate(java.sql.Date theBillDate) {
		this.billDate = theBillDate;
	}

	public String getBillType() {
		return this.billType;
	}

	public void setBillType(String theBillType) {
		this.billType = theBillType;
	}

	public double getJiaBanTimeLength() {
		return this.jiaBanTimeLength;
	}

	public void setJiaBanTimeLength(double theJiaBanTimeLength) {
		this.jiaBanTimeLength = theJiaBanTimeLength;
	}

	public double getRatio() {
		return this.ratio;
	}

	public void setRatio(double theRatio) {
		this.ratio = theRatio;
	}

	public double getOffset() {
		return this.offset;
	}

	public void setOffset(double theOffset) {
		this.offset = theOffset;
	}

	public double getDiKouTimeLength() {
		return this.diKouTimeLength;
	}

	public void setDiKouTimeLength(double theDiKouTimeLength) {
		this.diKouTimeLength = theDiKouTimeLength;
	}

	public double getJiFeiTimeLength() {
		return this.jiFeiTimeLength;
	}

	public void setJiFeiTimeLength(double theJiFeiTimeLength) {
		this.jiFeiTimeLength = theJiFeiTimeLength;
	}

	public java.sql.Date getShengXiaoDate() {
		return this.shengXiaoDate;
	}

	public void setShengXiaoDate(java.sql.Date theShengXiaoDate) {
		this.shengXiaoDate = theShengXiaoDate;
	}

	public java.sql.Date getShiXiaoDate() {
		return this.shiXiaoDate;
	}

	public void setShiXiaoDate(java.sql.Date theShiXiaoDate) {
		this.shiXiaoDate = theShiXiaoDate;
	}

	public double getZuoFeiTimeLength() {
		return this.zuoFeiTimeLength;
	}

	public void setZuoFeiTimeLength(double theZuoFeiTimeLength) {
		this.zuoFeiTimeLength = theZuoFeiTimeLength;
	}

	public double getShengYuTimeLength() {
		return this.shengYuTimeLength;
	}

	public void setShengYuTimeLength(double theShengYuTimeLength) {
		this.shengYuTimeLength = theShengYuTimeLength;
	}

	public String toString() {
		StringBuffer returnString = new StringBuffer();
		returnString.append("JiaBanAccount[");
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
		returnString.append("sort = " + this.sort + ";\n");
		returnString.append("modify_member_id = " + this.modify_member_id + ";\n");
		returnString.append("modify_date = " + this.modify_date + ";\n");
		returnString.append("memberID = " + this.memberID + ";\n");
		returnString.append("workNum = " + this.workNum + ";\n");
		returnString.append("deptID = " + this.deptID + ";\n");
		returnString.append("organID = " + this.organID + ";\n");
		returnString.append("billNum = " + this.billNum + ";\n");
		returnString.append("billDate = " + this.billDate + ";\n");
		returnString.append("billType = " + this.billType + ";\n");
		returnString.append("jiaBanTimeLength = " + this.jiaBanTimeLength + ";\n");
		returnString.append("ratio = " + this.ratio + ";\n");
		returnString.append("offset = " + this.offset + ";\n");
		returnString.append("diKouTimeLength = " + this.diKouTimeLength + ";\n");
		returnString.append("jiFeiTimeLength = " + this.jiFeiTimeLength + ";\n");
		returnString.append("shengXiaoDate = " + this.shengXiaoDate + ";\n");
		returnString.append("shiXiaoDate = " + this.shiXiaoDate + ";\n");
		returnString.append("zuoFeiTimeLength = " + this.zuoFeiTimeLength + ";\n");
		returnString.append("shengYuTimeLength = " + this.shengYuTimeLength + ";\n");
		returnString.append("]\n");
		return returnString.toString();
	}
	
	
	public void initCreateFieldValue() {
		setId(UUIDLong.longUUID());
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		setState(1);

		this.setStart_member_id(AppContext.currentUserId());
		this.setStart_date(currentTime);

		this.setApprove_member_id(AppContext.currentUserId());
		this.setApprove_date(currentTime);

		this.setFinishedflag(1);
		this.setRatifyflag(0);
		this.setRatify_member_id(-1);
		// setSort(0);

		this.setModify_member_id(AppContext.currentUserId());
		this.setModify_date(currentTime);
	}
}