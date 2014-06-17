package com.misino.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

@Configuration
@PropertySource("classpath:global.properties")
public class ServiceConfig
{
    @Resource
    Environment env;
}
