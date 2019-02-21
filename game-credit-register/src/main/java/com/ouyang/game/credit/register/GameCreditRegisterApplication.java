package com.ouyang.game.credit.register; 

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/** 
 * @author 作者 ouyq 
 * @date 创建时间：2019年2月19日 下午3:24:11 
 * 类/接口说明 游戏征信系统注册中心
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableEurekaServer
public class GameCreditRegisterApplication {
	public static void main(String[] args) {
		SpringApplication.run(GameCreditRegisterApplication.class, args);
	}
}
 