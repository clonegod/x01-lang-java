package clonegod.dubbo.user.validator;

import org.apache.commons.lang3.StringUtils;

import clonegod.dubbo.user.dto.UserLoginRequest;
import clonegod.dubbo.user.dto.UserRegisterRequest;
import clonegod.dubbo.user.exception.ValidateException;

public class UserValidator {
	
	public static void beforeRegisterValidate(UserRegisterRequest request){
        if(null==request){
            throw new ValidateException("请求对象为空");
        }
        if(StringUtils.isEmpty(request.getUsername())){
            throw new ValidateException("用户名为空");
        }
        if(StringUtils.isEmpty(request.getPassword())){
            throw new ValidateException("密码为空");
        }
        if(StringUtils.isEmpty(request.getMobile())){
            throw new ValidateException("手机号码为空");
        }
    }

	public static void beforeValidate(UserLoginRequest request){
        if(null==request){
            throw new ValidateException("请求对象为空");
        }
        if(StringUtils.isEmpty(request.getUsername())){
            throw new ValidateException("用户名为空");
        }
        if(StringUtils.isEmpty(request.getPassword())){
            throw new ValidateException("密码为空");
        }
    }
	
}
