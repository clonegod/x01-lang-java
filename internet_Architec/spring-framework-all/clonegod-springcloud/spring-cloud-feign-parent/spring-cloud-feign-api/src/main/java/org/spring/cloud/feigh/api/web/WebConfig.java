package org.spring.cloud.feigh.api.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 通用拦截器
 * 	作用是产生一条日志，触发zipkin客户端将日志上报到zipkin server.
 * 	person-client和person-service通过@Import的导入到spring环境中。
 * 	 
 *
 */
public class WebConfig extends WebMvcConfigurerAdapter {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new HandlerInterceptorAdapter() {
			@Override
			public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
					ModelAndView modelAndView) throws Exception {
				super.postHandle(request, response, handler, modelAndView);
				// 打印日志，触发日志上报到zipkin服务器
				logger.info("request URI={}", request.getRequestURI());
			}
		});
	}
	
}
