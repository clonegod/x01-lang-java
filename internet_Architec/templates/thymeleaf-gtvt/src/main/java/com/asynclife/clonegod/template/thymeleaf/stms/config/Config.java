package com.asynclife.clonegod.template.thymeleaf.stms.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

import com.asynclife.clonegod.template.thymeleaf.stms.web.conversion.DateFormatter;
import com.asynclife.clonegod.template.thymeleaf.stms.web.conversion.VarietyFormatter;

@Configuration
public class Config {
	
	@SuppressWarnings("all")
	@Bean
	public FormattingConversionServiceFactoryBean customerFormater() {
		FormattingConversionServiceFactoryBean formater = 
				new FormattingConversionServiceFactoryBean();
		
		Set set = new HashSet();
		set.add(new DateFormatter());
		set.add(new VarietyFormatter());
		
		formater.setFormatters(set);
		
		return formater;
	}
	
	
}
