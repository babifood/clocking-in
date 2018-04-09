package com.seeyon.apps.a8x.clocked.entity;

import java.io.Serializable;

/*
 * 移动打卡记录-实体
 */
public class MobileDaKaRecord2 implements Serializable {

	private static final long serialVersionUID = -5882135939173925888L;
	
	private MobileDaKaLog begin;
	private MobileDaKaLog end;

	/**
	 * Default Constructor
	 */
	public MobileDaKaRecord2() {
		// do nothing
	}

	public MobileDaKaLog getBegin() {
		return begin;
	}

	public void setBegin(MobileDaKaLog begin) {
		this.begin = begin;
	}

	public MobileDaKaLog getEnd() {
		return end;
	}

	public void setEnd(MobileDaKaLog end) {
		this.end = end;
	}

}