package com.seeyon.apps.a8x.oa.entity;

import java.sql.Timestamp;
import java.util.Map;

import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.form.util.Enums;

public class A8xMasterDataRow extends A8xDataRow {

	private static final long serialVersionUID = -8187619071948421600L;

	private Long id;

	private int state;
	private long startMemberId;
	private java.sql.Timestamp startDate;
	private long approveMemberId;
	private java.sql.Timestamp approveDate;
	private int finishedFlag;
	private int sort;
	private int ratifyFlag;
	private long ratifyMemberId;
	private java.sql.Timestamp ratifyDate;
	private long modifyMemberId;
	private java.sql.Timestamp modifyDate;

	public A8xMasterDataRow(int size) {
		super(size);
	}

	public A8xMasterDataRow() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		addDataItem(Enums.MasterTableField.id.getKey(), id);
		this.id = id;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		addDataItem(Enums.MasterTableField.state.getKey(), state);
		this.state = state;
	}

	public long getStartMemberId() {
		return startMemberId;
	}

	public void setStartMemberId(long startMemberId) {
		addDataItem(Enums.MasterTableField.start_member_id.getKey(), startMemberId);
		this.startMemberId = startMemberId;
	}

	public java.sql.Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(java.sql.Timestamp startDate) {
		addDataItem(Enums.MasterTableField.start_date.getKey(), startDate);
		this.startDate = startDate;
	}

	public long getApproveMemberId() {
		return approveMemberId;
	}

	public void setApproveMemberId(long approveMemberId) {
		addDataItem(Enums.MasterTableField.approve_member_id.getKey(), approveMemberId);
		this.approveMemberId = approveMemberId;
	}

	public java.sql.Timestamp getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(java.sql.Timestamp approveDate) {
		addDataItem(Enums.MasterTableField.approve_date.getKey(), approveDate);
		this.approveDate = approveDate;
	}

	public int getFinishedFlag() {
		return finishedFlag;
	}

	public void setFinishedFlag(int finishedFlag) {
		addDataItem(Enums.MasterTableField.finishedflag.getKey(), finishedFlag);
		this.finishedFlag = finishedFlag;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		addDataItem(Enums.MasterTableField.sort.getKey(), sort);
		this.sort = sort;
	}

	public int getRatifyFlag() {
		return ratifyFlag;
	}

	public void setRatifyFlag(int ratifyFlag) {
		addDataItem(Enums.MasterTableField.ratifyflag.getKey(), ratifyFlag);
		this.ratifyFlag = ratifyFlag;
	}

	public long getRatifyMemberId() {
		return ratifyMemberId;
	}

	public void setRatifyMemberId(long ratifyMemberId) {
		addDataItem(Enums.MasterTableField.ratify_member_id.getKey(), ratifyMemberId);
		this.ratifyMemberId = ratifyMemberId;
	}

	public java.sql.Timestamp getRatifyDate() {
		return ratifyDate;
	}

	public void setRatifyDate(java.sql.Timestamp ratifyDate) {
		addDataItem(Enums.MasterTableField.ratify_date.getKey(), ratifyDate);
		this.ratifyDate = ratifyDate;
	}

	public long getModifyMemberId() {
		return modifyMemberId;
	}

	public void setModifyMemberId(long modifyMemberId) {
		addDataItem(Enums.MasterTableField.modify_member_id.getKey(), modifyMemberId);
		this.modifyMemberId = modifyMemberId;
	}

	public java.sql.Timestamp getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(java.sql.Timestamp modifyDate) {
		addDataItem(Enums.MasterTableField.modify_date.getKey(), modifyDate);
		this.modifyDate = modifyDate;
	}

	public void putAll(Map<? extends String, ? extends Object> t) {
		Object temp = t.get(Enums.MasterTableField.id.getKey());
		if (temp != null) {
			setId(Long.parseLong(temp.toString()));
		}

		temp = t.get(Enums.MasterTableField.state.getKey());
		if (temp != null) {
			setState(Integer.parseInt(temp.toString()));
		}

		temp = t.get(Enums.MasterTableField.start_member_id.getKey());
		if (temp != null) {
			setStartMemberId(Long.parseLong(temp.toString()));
		}

		temp = t.get(Enums.MasterTableField.start_date.getKey());
		if (temp != null) {
			if (temp instanceof java.sql.Timestamp) {
				setStartDate((Timestamp) temp);
			} else {
				setStartDate(new java.sql.Timestamp(((java.util.Date) temp).getTime()));
			}
		}

		temp = t.get(Enums.MasterTableField.approve_member_id.getKey());
		if (temp != null) {
			setApproveMemberId(Long.parseLong(temp.toString()));
		}

		temp = t.get(Enums.MasterTableField.approve_date.getKey());
		if (temp != null) {
			if (temp instanceof java.sql.Timestamp) {
				setApproveDate((Timestamp) temp);
			} else {
				setApproveDate(new java.sql.Timestamp(((java.util.Date) temp).getTime()));
			}
		}

		temp = t.get(Enums.MasterTableField.finishedflag.getKey());
		if (temp != null) {
			setFinishedFlag(Integer.parseInt(temp.toString()));
		}

		temp = t.get(Enums.MasterTableField.ratifyflag.getKey());
		if (temp != null) {
			setRatifyFlag(Integer.parseInt(temp.toString()));
		}

		temp = t.get(Enums.MasterTableField.ratify_member_id.getKey());
		if (temp != null) {
			setRatifyMemberId(Long.parseLong(temp.toString()));
		}

		temp = t.get(Enums.MasterTableField.ratify_date.getKey());
		if (temp != null) {
			if (temp instanceof java.sql.Timestamp) {
				setRatifyDate((Timestamp) temp);
			} else {
				setRatifyDate(new java.sql.Timestamp(((java.util.Date) temp).getTime()));
			}
		}

		temp = t.get(Enums.MasterTableField.sort.getKey());
		if (temp != null) {
			setSort(Integer.parseInt(temp.toString()));
		}

		temp = t.get(Enums.MasterTableField.modify_member_id.getKey());
		if (temp != null) {
			setModifyMemberId(Long.parseLong(temp.toString()));
		}

		temp = t.get(Enums.MasterTableField.modify_date.getKey());
		if (temp != null) {
			if (temp instanceof java.sql.Timestamp) {
				setModifyDate((Timestamp) temp);
			} else {
				setModifyDate(new java.sql.Timestamp(((java.util.Date) temp).getTime()));
			}
		}

		super.putAll(t);
	}

	public void initCreateFieldValue() {
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		Object temp = get(Enums.MasterTableField.state.getKey());
		setState(temp == null ? 0 : Integer.parseInt(temp.toString()));

		temp = get(Enums.MasterTableField.start_member_id.getKey());
		setStartMemberId(temp == null ? AppContext.currentUserId() : Long.parseLong(temp.toString()));

		temp = get(Enums.MasterTableField.start_date.getKey());
		setStartDate(temp == null ? currentTime : (Timestamp) temp);

		temp = get(Enums.MasterTableField.approve_member_id.getKey());
		setApproveMemberId(temp == null ? 0L : Long.parseLong(temp.toString()));

		temp = get(Enums.MasterTableField.approve_date.getKey());
		setApproveDate(temp == null ? currentTime : (Timestamp) temp);

		temp = get(Enums.MasterTableField.finishedflag.getKey());
		setFinishedFlag(temp == null ? 0 : Integer.parseInt(temp.toString()));

		temp = get(Enums.MasterTableField.ratifyflag.getKey());
		setRatifyFlag(temp == null ? 0 : Integer.parseInt(temp.toString()));

		temp = get(Enums.MasterTableField.ratify_member_id.getKey());
		setRatifyMemberId(temp == null ? 0L : Long.parseLong(temp.toString()));

		temp = get(Enums.MasterTableField.ratify_date.getKey());
		if (temp != null) {
			setRatifyDate((Timestamp) temp);
		}

		temp = get(Enums.MasterTableField.sort.getKey());
		setSort(temp == null ? 0 : Integer.parseInt(temp.toString()));

		temp = get(Enums.MasterTableField.modify_member_id.getKey());
		setModifyMemberId(temp == null ? AppContext.currentUserId() : Long.parseLong(temp.toString()));

		temp = get(Enums.MasterTableField.modify_date.getKey());
		setModifyDate(temp == null ? currentTime : (Timestamp) temp);
	}

	public void initModifyFieldValue() {
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());

		Object temp = get(Enums.MasterTableField.modify_member_id.getKey());
		setModifyMemberId(temp == null ? AppContext.currentUserId() : Long.parseLong(temp.toString()));

		temp = get(Enums.MasterTableField.modify_date.getKey());
		setModifyDate(temp == null ? currentTime : (Timestamp) temp);
	}
}
