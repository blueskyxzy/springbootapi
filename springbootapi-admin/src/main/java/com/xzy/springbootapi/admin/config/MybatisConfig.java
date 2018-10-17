package com.xzy.springbootapi.admin.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xzy on 18/10/16  .
 */

@Configuration
@MapperScan("com.xzy.springbootapi.domain.mapper")
public class MybatisConfig {

}
