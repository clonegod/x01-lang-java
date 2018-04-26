package clonegod.dubbo.user.validator;

import org.apache.commons.lang3.StringUtils;

import clonegod.dubbo.user.dto.UserLoginRequest;

public class UserValidator {
	
	/**
	 * 校验用户登录参数是否合法
	 * 
	 * @param request
	 * @return true，合法；false，非法
	 */
	public static boolean checkUserLoginRequest(UserLoginRequest request) {
		
		return StringUtils.isNotBlank(request.getUsername()) && StringUtils.isNotBlank(request.getPassword());
	}
	
}
