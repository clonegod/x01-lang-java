package com.clonegod.validation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

public class ValidationInterceptor implements HandlerInterceptor {
	
	/**
	 * 参数合法性检查
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		System.err.println("可以通过HandlerInterceptor，从request中取出请求参数，再对参数进行校验。"
				+ "如果验证失败，直接返回false。不用再调用handlerMethod ");
		
		return true;
	}
	
}
