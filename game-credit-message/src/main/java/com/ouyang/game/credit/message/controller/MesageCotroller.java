package com.ouyang.game.credit.message.controller; 

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ouyang.game.credit.message.service.ShortMessageService;
import com.ouyang.game.num.commons.ErrorCodeEnum;
import com.ouyang.game.pojo.message.model.ModelMessage;

/** 
 * @author 作者 ouyq 
 * @date 创建时间：2019年2月21日 下午1:35:37 
 * 类/接口说明 短信的消息服务
 */
@RestController
@Slf4j
@RequestMapping(value="/message")
public class MesageCotroller {
	@Autowired
	private ShortMessageService shortMessageService;
	@RequestMapping("/sendMessage")
	public String sendMessage(@RequestBody ModelMessage modelMessage){
		log.debug("发送短信方法开始执行，传入参数:{}",modelMessage);
		boolean sendModelMessage = shortMessageService.sendModelMessage(modelMessage);
		if(sendModelMessage){
			return ErrorCodeEnum.SUCCESS.getErrorCode();
		}else{
			log.warn("发送短信失败");
			return ErrorCodeEnum.SMS_SEND_ERROR.getErrorCode();
		}
	}
}
 