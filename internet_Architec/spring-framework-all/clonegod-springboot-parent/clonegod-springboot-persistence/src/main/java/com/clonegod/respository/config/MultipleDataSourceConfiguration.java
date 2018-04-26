package com.clonegod.respository.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

@Configuration
public class MultipleDataSourceConfiguration {
	
	@Autowired
    private Environment env;
	
	@Bean
	@Primary
	public DataSource masterDataSource() {
		DataSource dataSource = DataSourceBuilder
				.create()
				.url(env.getProperty("spring.datasource.master.url"))
				.driverClassName(env.getProperty("spring.datasource.master.driverClassName"))
				.username(env.getProperty("spring.datasource.master.username"))
				.password(env.getProperty("spring.datasource.master.password"))
				.build();
		return dataSource;
	}
	
	
	@Bean
	public DataSource slaveDataSource() {
		DataSource dataSource = DataSourceBuilder
				.create()
				.url(env.getProperty("spring.datasource.slave.url"))
				.driverClassName(env.getProperty("spring.datasource.slave.driverClassName"))
				.username(env.getProperty("spring.datasource.slave.username"))
				.password(env.getProperty("spring.datasource.slave.password"))
				.build();
		return dataSource;
	}
	
	
}
