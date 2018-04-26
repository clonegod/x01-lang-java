package com.clonegod.aop;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import com.clonegod.exceptions.BusinessException;
import com.google.common.base.Throwables;

import lombok.extern.slf4j.Slf4j;

/**
 * spring Mvc 对controller层抛出的异常进行统一拦截处理。
 * >>> 可用于在Controller层统一处理service层抛出的异常。
 * 
 * This is quite useful when you want to deal with business exceptions at the controller level.
 * You can do that with error pages, but you might end up with a single error controller dealing with too many things.
 */

// You can configure the @ControllerAdvice annotation to only apply to specific packages, Controllers extending a specific interface or annotated with a specific annotation
// Target all Controllers within specific packages
@RestControllerAdvice(basePackages="com.clonegod.controller") /** 指定拦截范围：拦截controller层指定package下抛出的异常 */
@Slf4j
public class RestControllerAdvisor {
	
	// You can declare an @ExceptionHandler within a Controller and specify the type of Exception you'd like to handle
	@ExceptionHandler(BusinessException.class)
    public Object handleException(HttpServletRequest request, 
    		HttpServletResponse response, 
    		HandlerMethod handlerMethod, 
    		Throwable t) throws Exception {
		
		Map<String, Object> errors = new HashMap<>();
		errors.put("handler", handlerMethod.getResolvedFromHandlerMethod().toString());
		errors.put("error", Throwables.getStackTraceAsString(t));
		log.warn("\n=======================");
		log.warn("exception: {}", errors);
		
		// 返回给客户端的错误信息
		errors.put("error", t.getMessage());
		return errors;
    }
	
}
