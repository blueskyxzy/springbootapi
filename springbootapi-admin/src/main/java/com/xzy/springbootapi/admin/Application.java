package com.xzy.springbootapi.admin;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement // 如果mybatis中service实现类中加入事务注解，需要此处添加该注解
//@MapperScan(basePackages = "com.xzy.springbootapi.domain.mapper") // 扫描的是mapper.xml中namespace指向值的包位置
public class Application{

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
