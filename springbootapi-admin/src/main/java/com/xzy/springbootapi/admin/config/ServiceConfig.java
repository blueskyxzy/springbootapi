package com.xzy.springbootapi.admin.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xzy on 18/10/16  .
 */

@Configuration
@ComponentScan({"com.xzy.springbootapi.admin.controller",
        "com.xzy.springbootapi.service"})
public class ServiceConfig {

}
