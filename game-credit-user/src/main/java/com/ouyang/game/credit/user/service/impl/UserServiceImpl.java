package com.ouyang.game.credit.user.service.impl; 

import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ouyang.game.credit.user.mapper.UserInfoMapper;
import com.ouyang.game.credit.user.service.UserService;
import com.ouyang.game.pojo.user.model.UserInfo;

/** 
 * @author 作者 ouyq 
 * @date 创建时间：2019年2月19日 下午5:13:34 
 * 类/接口说明 
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService{
	@Autowired
	private UserInfoMapper userInfoMapper;
	@Override
	public UserInfo getUserInfoById(String userId) {
		log.debug("UserService.getUserInfoById方法开始执行，传入参数:{}",userId);
		UserInfo userInfo = null;
		if(StringUtils.isBlank(userId)){
			log.warn("传入的查询为空，不做数据库操作");
			return userInfo;
		}
		userInfo = userInfoMapper.selectByPrimaryKey(userId);
		return userInfo;
	}

	@Override
	public UserInfo getUserInfoByMobile(String mobile) {
		UserInfo userInfo = null;
		if(StringUtils.isBlank(mobile)){
			log.warn("传入的查询为空，不做数据库操作");
			return userInfo;
		}
		UserInfo record = new UserInfo();
		record.setMobile(mobile);
		userInfo = userInfoMapper.selectOne(record);
		return userInfo;
	}

	@Override
	public int addUserInfo(UserInfo userInfo) {
		log.debug("新增用户UserService.addUserInfo方法开始执行，传入参数:{}",userInfo);
		int insertSelective = 0;
		try {
			if(userInfo != null){
				userInfo.setCreateTime(new Date());
				insertSelective = userInfoMapper.insertSelective(userInfo);
			}
		} catch (Exception e) {
			log.error("新增用户发生异常:{}",e);
		}
		log.debug("本次插入用户条数:{}",insertSelective);
		return insertSelective;
	}

	@Override
	public int updateUserInfo(UserInfo userInfo) {
		log.debug("修改用户信息开始传入的参数:{}",userInfo);
		int updateByPrimaryKeySelective = 0;
		try {
			if(userInfo != null){
				userInfo.setUpdateTime(new Date());
				updateByPrimaryKeySelective = userInfoMapper.updateByPrimaryKeySelective(userInfo);
			}
		} catch (Exception e) {
			log.debug("修改用户信息发生异常:{}",e);
		}
		log.debug("修改用户信息受影响条数:{}",updateByPrimaryKeySelective);
		return updateByPrimaryKeySelective;
	}
	
	
}
 