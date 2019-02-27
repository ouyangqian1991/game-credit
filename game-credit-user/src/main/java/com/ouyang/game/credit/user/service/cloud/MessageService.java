package com.ouyang.game.credit.user.service.cloud; 

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ouyang.game.credit.user.service.hystrix.MessageServiceHystrix;
import com.ouyang.game.pojo.message.model.ModelMessage;

/** 
 * @author 作者 ouyq 
 * @date 创建时间：2019年2月21日 下午1:28:40 
 * 类/接口说明 使用cloud的调用外部的Service
 */
@FeignClient(value="game-credit-message",fallback=MessageServiceHystrix.class)
public interface MessageService {
	@RequestMapping(value="/message/sendMessage")
	String sendModelMessage(@RequestBody ModelMessage modelMessage);
}
 