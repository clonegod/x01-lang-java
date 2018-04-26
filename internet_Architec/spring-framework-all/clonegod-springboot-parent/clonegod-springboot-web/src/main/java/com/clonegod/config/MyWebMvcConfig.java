package com.clonegod.config;

import java.util.List;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.clonegod.interceptors.MyInterceptor;
import com.clonegod.message.convertor.UserPropertiesHttpMessageConvertor;
import com.clonegod.validation.ValidationInterceptor;

@Configuration
public class MyWebMvcConfig  implements WebMvcConfigurer, ErrorPageRegistrar {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new MyInterceptor()).addPathPatterns("/user/**");
		registry.addInterceptor(new ValidationInterceptor()).addPathPatterns("/validation/**");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // to serve static .html pages...
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
	}

	@Override
	public void registerErrorPages(ErrorPageRegistry registry) {
		// 客户端请求如果发生404异常，则路由请求到path=/404.html的handler
		registry.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404.html"));		
	}

	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		// If no converters are added, a default list of converters is registered. 
		// 如果没有加入任何converter，则spring会默认加入一批converter到converter集合中
		// 如果加入新的converter，则spring不会加入默认的那些converter
		// 在org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport.getMessageConverters()中，
		// 如果发现converters为空，则会调用addDefaultHttpMessageConverters(this.messageConverters);
		// 否则，直接返回添加了converter的集合。
		WebMvcConfigurer.super.configureMessageConverters(converters);
	}

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		// allow default converters to be registered and then insert a custom converter through this method.
		// spring提供的扩展点：保留spring默认提供的converter，通过此扩展方法加入自定义的converter到converter集合中
		converters.add(new UserPropertiesHttpMessageConvertor());
	}

	
}
