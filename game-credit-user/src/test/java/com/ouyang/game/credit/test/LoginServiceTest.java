package com.ouyang.game.credit.test; 

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ouyang.game.credit.user.GameCreditUserApplication;
import com.ouyang.game.credit.user.service.LoginService;
import com.ouyang.game.pojo.commons.BasicResponse;
import com.ouyang.game.pojo.user.business.LoginDTO;

/** 
 * @author 作者 ouyq 
 * @date 创建时间：2019年2月20日 下午3:38:32 
 * 类/接口说明 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=GameCreditUserApplication.class)
public class LoginServiceTest {
	@Autowired
	private LoginService loginService;
	
	@Test
	public void loginOrRegistTest(){
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setMobile("17601281792");
		loginDTO.setVerificationCode("123456");
		BasicResponse<String> loginOrRegist = loginService.loginOrRegist(loginDTO);
		System.out.println("loginOrRegist:"+loginOrRegist);
	}
}
 