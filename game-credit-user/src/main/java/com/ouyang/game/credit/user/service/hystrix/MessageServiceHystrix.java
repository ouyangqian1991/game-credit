package com.ouyang.game.credit.user.service.hystrix; 

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import com.ouyang.game.credit.user.service.cloud.MessageService;
import com.ouyang.game.num.commons.ErrorCodeEnum;
import com.ouyang.game.pojo.message.model.ModelMessage;

/** 
 * @author 作者 ouyq 
 * @date 创建时间：2019年2月21日 下午3:01:34 
 * 类/接口说明 消息服务的断路器
 */
@Slf4j
@Component

public class MessageServiceHystrix implements MessageService{

	@Override
	public String sendModelMessage(ModelMessage modelMessage) {
		log.error("sendModelMessage消息cloud服务降级执行，请检查消息模块服务");
		return ErrorCodeEnum.MESSAGE_SYSTEM_EXCEPTION.getErrorCode();
	}

}
 