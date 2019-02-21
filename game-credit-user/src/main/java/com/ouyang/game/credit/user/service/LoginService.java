package com.ouyang.game.credit.user.service; 

import com.ouyang.game.pojo.commons.BasicResponse;
import com.ouyang.game.pojo.user.business.LoginDTO;
import com.ouyang.game.pojo.user.model.UserInfo;

/** 
 * @author 作者 ouyq 
 * @date 创建时间：2019年2月19日 下午5:30:26 
 * 类/接口说明 
 */
public interface LoginService {
	
	/**
	 * 浏览器环境登录/注册方法，用户登录/注册方法
	 * <pre>
	 * 1.根据用户新传入手机号查询是否是有该用户
	 * 	1.1 存在，登录
	 *	1.2 不存在--注册---登录
	 * </pre>
	 * @return 返回登录后产生的token
	 */
	BasicResponse<String> loginOrRegist(LoginDTO loginDTO);
	
	/**
	 * 根据token获取用户信息
	 * @param token
	 * @return
	 */
	BasicResponse<UserInfo> getUserInfoByToken(String token);
	
	/**
	 * 获取手机登录验证码
	 * @param internationalCode
	 * @param mobile
	 * @return
	 */
	BasicResponse<Void> getVerificationCode(String internationalCode,String mobile);
}
 