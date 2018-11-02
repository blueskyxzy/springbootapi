package com.xzy.springbootapi.admin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by xzy on 18/11/2  .
 */
@Configuration
@ImportResource(locations = {"classpath:config.quartz.xml"})
public class QuartzConfig {
}
