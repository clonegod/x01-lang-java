package com.clonegod.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clonegod.api.User;
import com.clonegod.exceptions.BusinessException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ErrorController {
    /**
     * 异常将被AppExceptionHandler拦截处理
     * 
     */
    @GetMapping("/npe")
    public User npe() {
    	throw new BusinessException("故意抛一个异常！");
    }
 
    /**
     * 这个handler负责处理MyWebMvcConfig 中 registerErrorPages 注册的404错误码
     */
    @GetMapping("/404.html")
    public void handler404(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	log.warn("Bad Request 404: " + request.getRequestURI());
    	
    	// 解决中文乱码
    	response.setCharacterEncoding("UTF-8"); 
    	response.setHeader("Content-Type", "text/html; charset=utf-8");
    	
    	PrintWriter writer = response.getWriter();
    	writer.write("clonegod website: 页面不存在！");
    	writer.flush();
    	writer.close();
    }
}
