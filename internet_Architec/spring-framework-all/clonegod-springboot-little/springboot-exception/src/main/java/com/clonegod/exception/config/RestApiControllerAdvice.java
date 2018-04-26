package com.clonegod.exception.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.clonegod.exception.ex.RestApiException;
import com.clonegod.exception.web.api.RestApi;

/**
 * 基于某个Controller所在包路径下的所有Controller定制专门的异常处理
 * 	因此，推荐在每个包下，创建一个特殊的无操作标记类或接口，除了被此属性引用之外，没有其他用途。
 */
@ControllerAdvice(basePackageClasses = RestApi.class)
@Order(901)
public class RestApiControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RestApiException.class)
    @ResponseBody
    ResponseEntity<CustomErrorType> handleControllerException(HttpServletRequest request, Throwable ex) {
        HttpStatus status = getStatus(request);
        CustomErrorType msgBody = new CustomErrorType(status.value(), ex);
        return new ResponseEntity<>(msgBody, status);
    }

    /** 
     * 对原始响应状态码进行转化处理 
     * */
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

    public static class CustomErrorType {
    	private int statusCode;
    	private String desc;
    	private String message;
    	
    	public CustomErrorType(int statusCode, Throwable ex) {
    		super();
    		this.statusCode = statusCode;
    		this.desc = "对rest api请求发生的异常进行转换";
    		this.message = "[error] " + ex.getMessage() + "; [cause by] " + ex.getCause().getMessage();
    	}

		public int getStatusCode() {
			return statusCode;
		}

		public void setStatusCode(int statusCode) {
			this.statusCode = statusCode;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

    }
}
