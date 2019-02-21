package com.ouyang.game.credit.message; 

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/** 
 * @author 作者 ouyq 
 * @date 创建时间：2019年2月21日 上午10:52:14 
 * 类/接口说明 游戏征信
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableEurekaClient
public class MessageApplication {
	public static void main(String[] args) {
		SpringApplication.run(MessageApplication.class, args);
	}
}
 