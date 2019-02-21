package com.ouyang.game.credit.user.controller; 


import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ouyang.game.credit.user.service.LoginService;
import com.ouyang.game.num.commons.ErrorCodeEnum;
import com.ouyang.game.pojo.commons.BasicResponse;
import com.ouyang.game.pojo.message.model.ModelMessage;
import com.ouyang.game.pojo.user.business.LoginDTO;
import com.ouyang.game.pojo.user.model.UserInfo;

/** 
 * @author 作者 ouyq 
 * @date 创建时间：2019年2月19日 下午5:20:30 
 * 类/接口说明 登录的控制层
 */
@RestController
@Slf4j
@RequestMapping(value="/user")
public class LoginController {
	@Autowired
	private LoginService loginService;

	
	@RequestMapping(value="/login")
	public BasicResponse<String> loginOrRegist(@RequestBody LoginDTO loginDTO){
		log.debug("LoginController登录/注册方法开始执行，传入参数:{}",loginDTO);
		BasicResponse<String> loginOrRegist = null;
		try {
			loginOrRegist = loginService.loginOrRegist(loginDTO);
		} catch (Exception e) {
			log.error("登录/注册方法发生异常，传入参数：{},异常原因:{}",loginDTO,e);
			loginOrRegist = new BasicResponse<>(ErrorCodeEnum.INTERNAL_SERVER_ERROR.getErrorCode(), ErrorCodeEnum.INTERNAL_SERVER_ERROR.getErrorMsg());
		}
		log.debug("登录注册接口返回的数据:{}",loginOrRegist);
		return loginOrRegist;
	}
	
	@RequestMapping(value="/getUserInfoByToken")
	public BasicResponse<UserInfo> getUserInfoByToken(@RequestHeader("token")String token){
		log.debug("根据token获取用户信息开始执行，传入参数:{}",token);
		BasicResponse<UserInfo> userInfoByToken = null;
		try {
			 userInfoByToken = loginService.getUserInfoByToken(token);
		} catch (Exception e) {
			log.error("根据token获取用户信息发生异常：{}",e);
			userInfoByToken = new BasicResponse<>(ErrorCodeEnum.INTERNAL_SERVER_ERROR.getErrorCode(), ErrorCodeEnum.INTERNAL_SERVER_ERROR.getErrorMsg());
		}
		log.debug("根据token获取用户信息，返回的参数：{}",userInfoByToken);
		return userInfoByToken;
	}
	
	@RequestMapping(value="/sendMessage")
	public BasicResponse<Void> sendMessage(@RequestBody ModelMessage modelMessage){
		log.debug("用户获取验证码开始执行");
		BasicResponse<Void> verificationCode = loginService.getVerificationCode(modelMessage.getNationCode(), modelMessage.getPhoneNumber());
		return verificationCode;
	}
}
 