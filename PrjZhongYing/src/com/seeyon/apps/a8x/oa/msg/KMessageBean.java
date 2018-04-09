package com.seeyon.apps.a8x.oa.msg;

import java.util.Date;

/**
 * 消息对象
 */
public class KMessageBean {

	/** 消息标题 */
	private String title;

	/** 发送人 */
	private String sender;

	/** 接收人 */
	private String receiver;

	/** 发送日期 */
	private Date senddate;

	/** 消息类型 */
	private String msgtype;

	/** 消息内容 */
	private String content;

	/** 字符串格式的发送日期 */
	private String sendDate;

	public KMessageBean() {

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public Date getSenddate() {
		return senddate;
	}

	public void setSenddate(Date senddate) {
		this.senddate = senddate;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

}