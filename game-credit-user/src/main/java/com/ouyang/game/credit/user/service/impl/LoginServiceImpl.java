package com.ouyang.game.credit.user.service.impl; 

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.ouyang.game.credit.user.service.LoginService;
import com.ouyang.game.credit.user.service.UserService;
import com.ouyang.game.credit.user.service.cloud.MessageService;
import com.ouyang.game.num.commons.ErrorCodeEnum;
import com.ouyang.game.num.commons.StatusEnum;
import com.ouyang.game.pojo.commons.BasicResponse;
import com.ouyang.game.pojo.commons.Constant;
import com.ouyang.game.pojo.message.model.ModelMessage;
import com.ouyang.game.pojo.user.business.LoginDTO;
import com.ouyang.game.pojo.user.business.RegisterUserInfo;
import com.ouyang.game.pojo.user.model.UserInfo;
import com.ouyang.game.utils.ProjectUtil;

/** 
 * @author 作者 ouyq 
 * @date 创建时间：2019年2月19日 下午5:30:15 
 * 类/接口说明 
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService{
	@Autowired
	private UserService userService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Override
	public BasicResponse<String> loginOrRegist(LoginDTO loginDTO) {
		log.debug("LoginService.loginOrRegist方法开始执行，传入参数：{}",loginDTO);
		String mobile = loginDTO.getMobile();
		String token = null;
		boolean verificationCodeCheck = verificationCodeCheck(loginDTO);
		if(verificationCodeCheck){
			log.debug("手机验证码通过，执行登录/注册操作");
			UserInfo userInfoByMobile = userService.getUserInfoByMobile(mobile);
			if(userInfoByMobile == null){
				userInfoByMobile = regist(loginDTO);
			}
			//注册方法执行完成后，返回的用户信息不为空，则说明注册成功，进行登录操作
			if(userInfoByMobile != null){
				token = login(userInfoByMobile);
				return new BasicResponse<String>(token);
			}else{
				log.error("注册用户失败，无法进行登录操作");
				return new BasicResponse<>(ErrorCodeEnum.BACKSTAGE_ADD_FAILED.getErrorCode(), ErrorCodeEnum.BACKSTAGE_ADD_FAILED.getErrorMsg());
			}
	
		}else{
			log.warn("手机验证码未通过");
			return new BasicResponse<>(ErrorCodeEnum.SMS_CODE_ERROR.getErrorCode(), ErrorCodeEnum.SMS_CODE_ERROR.getErrorMsg());
		}
	}
	
	

	@Override
	public BasicResponse<UserInfo> getUserInfoByToken(String token) {
		if(StringUtils.isBlank(token)){
			log.warn("根据token获取用户信息失败，用户传入的token为空");
			return new BasicResponse<>(ErrorCodeEnum.REQUEST_PARAMETER_IS_INCORRECT.getErrorCode(), ErrorCodeEnum.REQUEST_PARAMETER_IS_INCORRECT.getErrorMsg());
		}
		String userInfoStr = redisTemplate.opsForValue().get(Constant.LOGIN_TOKEN+token);
		if(StringUtils.isBlank(userInfoStr)){
			return new BasicResponse<>(ErrorCodeEnum.USER_TOKEN_EXPIRE.getErrorCode(), ErrorCodeEnum.USER_TOKEN_EXPIRE.getErrorMsg());
		}else{
			UserInfo userInfo = JSONObject.parseObject(userInfoStr, UserInfo.class);
			return new BasicResponse<UserInfo>(userInfo);
		}
	}

	
	@Override
	public BasicResponse<Void> getVerificationCode(String internationalCode,String mobile) {
		Map<String, Object> params = buildingParameters(mobile, internationalCode);
		String smsCode = (String)params.get("smsCode");
		String time = (String)params.get("time");
		ArrayList<String> list = new ArrayList<>();
		list.add(smsCode);
		list.add(time);
		ModelMessage modelMessage = new ModelMessage();
		modelMessage.setExt("");
		modelMessage.setExtend("");
		modelMessage.setNationCode(internationalCode);
		modelMessage.setParams(list);
		modelMessage.setPhoneNumber(mobile);
		//根据不同的国际码发送不同的短信模版
		if(Constant.INTERNATIONAL_CODE_CH.equals(internationalCode)){
			modelMessage.setSign("有叻会员");
			modelMessage.setTemplateId(231211);
		}else if(Constant.INTERNATIONAL_CODE_HK.equals(internationalCode) || "853".equals(internationalCode) ||"886".equals(internationalCode)){
			modelMessage.setSign("有叻會員");
			modelMessage.setTemplateId(232046);
		}else{
			modelMessage.setSign("FOSUN Youle");
			modelMessage.setTemplateId(231158);
		}
		log.debug("调用腾讯云平台发送短信Method_smsSend>>>>>>>>>>请求参数：params:{}", JSON.toJSON(modelMessage));

		String sendModelMessage = messageService.sendModelMessage(modelMessage);
		log.debug("调用腾讯云平台发送短信Method_smsSend>>>>>>>>>>响应参数：result:{}", sendModelMessage);

		if (sendModelMessage.equals(ErrorCodeEnum.SUCCESS.getErrorCode())){
			return new BasicResponse<>(ErrorCodeEnum.SUCCESS.getErrorCode(), ErrorCodeEnum.SUCCESS.getErrorMsg());
		}else if(sendModelMessage.equals(ErrorCodeEnum.MESSAGE_SYSTEM_EXCEPTION.getErrorCode())){
			log.error("短信服务模块无法正常使用，系统已降级");
			return new BasicResponse<>(ErrorCodeEnum.MESSAGE_SYSTEM_EXCEPTION.getErrorCode(), ErrorCodeEnum.MESSAGE_SYSTEM_EXCEPTION.getErrorMsg());
		}
		return new BasicResponse<>(ErrorCodeEnum.SMS_SEND_ERROR.getErrorCode(), ErrorCodeEnum.SMS_SEND_ERROR.getErrorMsg());
	}
	
	
	/**
	 * 登录方法
	 * @param userInfoByMobile
	 * @return
	 */
	private  String  login(UserInfo userInfoByMobile) {
		String token  = UUID.randomUUID().toString().replaceAll("-", "");
		redisTemplate.opsForValue().set(Constant.LOGIN_TOKEN+token, JSONObject.toJSONString(userInfoByMobile), Constant.TOKEN_EXPIRY, TimeUnit.MINUTES);
		return token;
	}
	
	/**
	 * 注册方法
	 * @param loginDTO
	 * @return
	 */
	private UserInfo regist(LoginDTO loginDTO) {
		log.debug("注册方法开始执行，传入参数:{}",loginDTO);
		UserInfo userInfo = new UserInfo();
		RegisterUserInfo registUserInfo = loginDTO.getRegistUserInfo();
		if(registUserInfo != null){
			BeanUtils.copyProperties(registUserInfo, userInfo);
		}
		userInfo.setUserId(UUID.randomUUID().toString().replaceAll("-", ""));
		userInfo.setMobile(loginDTO.getMobile());
		userInfo.setStatus(StatusEnum.YES.getValue());
		//插入数据库操作
		int addUserInfo = userService.addUserInfo(userInfo);
		if(addUserInfo > 0){
			return userInfo;
		}else{
			log.error("底层数据库插入失败，返回空的");
			return null;
		}
	}

	/**
	 * 验证码校验方法
	 * @param loginDTO
	 * @return
	 */
	private boolean verificationCodeCheck(LoginDTO loginDTO) {
		log.debug("验证码校验方法开始执行，传入参数:{}",loginDTO);
		String mobile = loginDTO.getMobile();
		String verificationCode = loginDTO.getVerificationCode();
		if(StringUtils.isBlank(mobile) || StringUtils.isBlank(verificationCode)){
			log.info("验证码校验失败，参数不全");
			return false;
		}
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		String sysVerificationCode = opsForValue.get(Constant.VERIFICATION_CODE+mobile);
		if(StringUtils.isNotBlank(sysVerificationCode) && sysVerificationCode.equals(verificationCode)){
			log.debug("验证码校验成功");
			//使用后从redis中删除
			redisTemplate.delete(Constant.VERIFICATION_CODE+mobile);
			return true;
		}else{
			log.info("验证码校验失败，验证码错误");
			return false;
		}
	}
	
	/**
	 * 
	 * @param phone
	 * @param internationalCode
	 * @return
	 */
	private Map<String, Object> buildingParameters(String mobile,String internationalCode) {

		String smsCode = ProjectUtil.genValidationCode();
		Map<String, Object> params = Maps.newHashMap();
		params.put("smsCode", smsCode);
		params.put("time", "2");
		params.put("mobile", mobile);
		redisTemplate.opsForValue().set(Constant.VERIFICATION_CODE + mobile, smsCode, Constant.VERIFICATION_CODE_EXPIRY, TimeUnit.MINUTES);
		return params;
	}


}
 