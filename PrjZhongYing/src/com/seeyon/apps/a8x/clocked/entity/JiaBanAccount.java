package com.seeyon.apps.a8x.clocked.entity;

/*
 * 员工加班账表-实体
 */
public class JiaBanAccount extends JiaBanAccountBase {

	private static final long serialVersionUID = 5731567004183612272L;

	public JiaBanAccount() {

	}

	// 本次抵扣数
	private double writedownValue;

	public double getWritedownValue() {
		return writedownValue;
	}

	public void setWritedownValue(double writedownValue) {
		this.writedownValue = writedownValue;
	}

}