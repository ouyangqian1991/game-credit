package com.ouyang.game.credit.dnf; 

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

import tk.mybatis.spring.annotation.MapperScan;

/** 
 * @author 作者 ouyq 
 * @date 创建时间：2019年2月21日 下午5:30:48 
 * 类/接口说明 
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableHystrix
@MapperScan(value="com.ouyang.game.credit.dnf.mapper")
public class DnfApplication {
	public static void main(String[] args) {
		SpringApplication.run(DnfApplication.class, args);
	}
}
 