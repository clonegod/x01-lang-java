package com.clonegod.profile.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
/** list the properties classes to register in the @EnableConfigurationProperties annotation */
@EnableConfigurationProperties(FooProperties.class)
public class MyConfiguration {
}