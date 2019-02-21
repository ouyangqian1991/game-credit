package com.ouyang.game.credit.user.service; 

import com.ouyang.game.pojo.user.model.UserInfo;

/** 
 * @author 作者 ouyq 
 * @date 创建时间：2019年2月19日 下午5:07:47 
 * 类/接口说明 
 */
public interface UserService {
	/**
	 * 根据用户id查询用户信息
	 * @param userId 用户id
	 * @return
	 */
	UserInfo getUserInfoById(String userId);
	
	/**
	 * 根据用户手机号查询用户信息
	 * @param mobil 手机号码
	 * @return
	 */
	UserInfo getUserInfoByMobile(String mobile);
	
	/**
	 * 新增一个用户到数据库
	 * @param userInfo
	 * @return 插入的条数
	 */
	int addUserInfo(UserInfo userInfo);
	
	/**
	 * 修改用户数据，根据主键
	 * @param userInfo 装有修改条件的类
	 * @return 修改条数
	 */
	int updateUserInfo(UserInfo userInfo);
}
 