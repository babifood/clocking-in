package com.seeyon.apps.a8x.oa.msg;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.common.usermessage.MessageContent;
import com.seeyon.ctp.common.usermessage.MessageReceiver;
import com.seeyon.ctp.common.usermessage.UserMessageManager;
import com.seeyon.ctp.util.Strings;

public class KMessageAgent {

	private static final Log log = LogFactory.getLog(KMessageAgent.class);

	private UserMessageManager userMessageManager;

	public KMessageAgent() {
		userMessageManager = (UserMessageManager) AppContext.getBean("userMessageManager");
	}

	/**
	 * 功能：发送消息
	 */
	public void sendMessage(KMessageBean msg, Long userId) {
		String title = msg.getTitle();
		String messageContent = msg.getContent();
		String msgType = msg.getMsgtype();
		Date sendDate = msg.getSenddate();

		try {
			if (sendDate == null) {
				sendDate = new Date();
			}
			String content = "";
			if (Strings.isNotBlank(title)) {
				content += title;
			}
			if (Strings.isNotBlank(msgType)) {
				content += "  " + msgType;
			}
			if (Strings.isNotBlank(messageContent)) {
				content += "  " + messageContent;
			}
			if (Strings.isNotBlank(msg.getSender())) {
				content += "  " + msg.getSender();
			}
			Collection<MessageReceiver> receivers = null;
			Collection<Long> receiverIds = new HashSet<Long>();
			receiverIds.add(userId);

			receivers = MessageReceiver.get(null, receiverIds);
			this.userMessageManager.sendSystemMessage(MessageContent.get(content), 102, -1, sendDate, receivers);
		} catch (Exception e) {
			log.error("KMessageAgent执行错误", e);
		}
	}

	/**
	 * 功能：发送消息
	 */
	public void sendMessage(KMessageBean msg, Long[] userIds) {
		String title = msg.getTitle();
		String messageContent = msg.getContent();
		String msgType = msg.getMsgtype();
		Date sendDate = msg.getSenddate();
		try {
			if (sendDate == null) {
				sendDate = new Date();
			}
			// 消息标题-“主题+类型+发送人”
			String content = "";
			if (Strings.isNotBlank(title)) {
				content += title;
			}
			if (Strings.isNotBlank(msgType)) {
				content += "  " + msgType;
			}
			if (Strings.isNotBlank(messageContent)) {
				content += "  " + messageContent;
			}
			if (Strings.isNotBlank(msg.getSender())) {
				content += "  " + msg.getSender();
			}
			Collection<MessageReceiver> receivers = null;
			Collection<Long> receiverIds = new HashSet<Long>();
			for (int i = 0; i < userIds.length; i++) {
				receiverIds.add(userIds[i]);
			}

			receivers = MessageReceiver.get(null, receiverIds);
			this.userMessageManager.sendSystemMessage(MessageContent.get(content), 0, -1, sendDate, receivers);
		} catch (Exception e) {
			log.error("KMessageAgent执行错误", e);
		}
	}

}
