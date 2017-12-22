package com.ht;

import com.ht.config.Config;
import org.dozer.DozerBeanMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.ht.account.mapper")
public class AccountServiceApplication {

	@Bean
	@ConfigurationProperties(prefix = "config")
	public Config config(){
		return new Config();
	}

	@Bean
	public DozerBeanMapper dozerBeanMapper(){
		return new DozerBeanMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(AccountServiceApplication.class, args);
	}
}
