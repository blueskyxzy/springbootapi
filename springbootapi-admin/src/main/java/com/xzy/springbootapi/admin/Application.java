package com.xzy.springbootapi.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;


@SpringBootApplication
@EnableTransactionManagement // 如果mybatis中service实现类中加入事务注解，需要此处添加该注解
//@MapperScan(basePackages = "com.xzy.springbootapi.domain.mapper") // 扫描的是mapper.xml中namespace指向值的包位置
public class Application{
	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
