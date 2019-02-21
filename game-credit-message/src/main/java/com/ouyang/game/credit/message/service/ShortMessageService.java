package com.ouyang.game.credit.message.service;

import com.ouyang.game.pojo.message.model.ModelMessage;




public interface ShortMessageService {
	
	
	/**
	 * 发送单个模版消息
	 * @param modelMessage
	 * @return
	 */
	public boolean sendModelMessage(ModelMessage modelMessage);
}
