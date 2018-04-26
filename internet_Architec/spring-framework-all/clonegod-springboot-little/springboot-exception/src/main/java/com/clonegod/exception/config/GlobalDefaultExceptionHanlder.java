package com.clonegod.exception.config;

import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.clonegod.exception.ex.PageViewException;
import com.clonegod.exception.ex.RestApiException;
import com.google.common.collect.Maps;

/**
 * 基于不同类型的异常，分别进行处理.
 * 	- 对于REST请求，需要返回string
 * 	- 对于页面请求，需要返回ModelAndView
 *
 */
@ControllerAdvice
@Order(999)
public class GlobalDefaultExceptionHanlder {
	
	Logger logger = Logger.getLogger(GlobalDefaultExceptionHanlder.class.getName());
	
	/**
	 * 专门处理REST API 的异常
	 * 
	 * @param request
	 * @param ex
	 * @return
	 */
    @ExceptionHandler(RestApiException.class)
    @ResponseBody
    public Entry<String, String> handleRestControllerException(HttpServletRequest request, Throwable ex) {
    	logger.log(Level.SEVERE, "Global REST Controller Exception Handler~", ex);
    	return Maps.immutableEntry("message", "服务繁忙，请稍后再试！");
    }
    
    /**
     * 专门处理返回页面类型的异常
     * 
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(PageViewException.class)
    public ModelAndView handleControllerException(HttpServletRequest request, Throwable ex) {
    	logger.log(Level.SEVERE, "Global Controller Exception Handler~", ex);
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName("404");
    	return mav;
    }
}
