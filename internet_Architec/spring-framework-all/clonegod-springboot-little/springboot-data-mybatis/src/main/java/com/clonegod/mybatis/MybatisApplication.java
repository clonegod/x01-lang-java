package com.clonegod.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.clonegod.mybatis.mapper.MybatisMapperLocation;

@SpringBootApplication
@MapperScan(basePackageClasses=MybatisMapperLocation.class)
//@MapperScan(basePackages="com.clonegod.mybatis.mapper")
public class MybatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatisApplication.class, args);
	}
}
