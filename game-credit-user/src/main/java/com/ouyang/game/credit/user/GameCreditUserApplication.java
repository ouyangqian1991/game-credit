package com.ouyang.game.credit.user; 

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import tk.mybatis.spring.annotation.MapperScan;

/** 
 * @author 作者 ouyq 
 * @date 创建时间：2019年2月19日 下午4:09:49 
 * 类/接口说明 游戏征信用户模块启动类
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@MapperScan(basePackages = {"com.ouyang.game.credit.user.mapper"})
public class GameCreditUserApplication {
 public static void main(String[] args) {
	SpringApplication.run(GameCreditUserApplication.class, args);
}
}
 