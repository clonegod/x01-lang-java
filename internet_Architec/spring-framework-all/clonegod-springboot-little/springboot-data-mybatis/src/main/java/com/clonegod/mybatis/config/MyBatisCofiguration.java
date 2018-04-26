package com.clonegod.mybatis.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.pagehelper.PageHelper;

@Configuration
public class MyBatisCofiguration {

	/**
	 * 发布PageHelper Bean 到Spring容器
	 * 
	 * 注意：
	 * 	该配置仅在PageHelper 4.x 中生效，可以实现分页。
	 * 	如果使用PageHelper 5.x版本，发布Bean的方式配置PageHelper是无效的，无法分页。具体配置方法，查阅最新文档。
	 * 
	 * @return
	 */
	@Bean
	public PageHelper pageHelper() {
		PageHelper pageHelper = new PageHelper();
		Properties props = new Properties();
		props.setProperty("offsetAsPageNum", "true");
		props.setProperty("rowBoundsWithCount", "true");
		props.setProperty("reasonable", "true");
		pageHelper.setProperties(props);
		return pageHelper;
	}
	
}
