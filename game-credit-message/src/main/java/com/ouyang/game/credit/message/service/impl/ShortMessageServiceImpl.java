package com.ouyang.game.credit.message.service.impl;

import java.io.IOException;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.ouyang.game.credit.message.service.ShortMessageService;
import com.ouyang.game.pojo.message.model.ModelMessage;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ShortMessageServiceImpl implements ShortMessageService{
	private static final int msgAppId = 1400158024;
	private static final String msgAppKey="02c029d0be58864f89df71fbab9c87ae";
	
	
	@Override
	public boolean sendModelMessage(ModelMessage modelMessage) {
		log.debug("发送短信开始，传入参数:{}",modelMessage);
		 SmsSingleSender ssender = new SmsSingleSender(msgAppId, msgAppKey);
		 try {
			SmsSingleSenderResult smsSingleSenderResult = ssender.sendWithParam(modelMessage.getNationCode(),modelMessage.getPhoneNumber(), modelMessage.getTemplateId(), modelMessage.getParams(), modelMessage.getSign(), modelMessage.getExtend(), modelMessage.getExt());
			int result = smsSingleSenderResult.result;
			if(0==result){
				return true;
			}else{
				log.debug("发送短信失败，发送短信返回的结果：{}",JSON.toJSONString(smsSingleSenderResult));
				return false;
			}
		 } catch (JSONException e) {
			e.printStackTrace();
			return false;
		} catch (HTTPException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	
}
