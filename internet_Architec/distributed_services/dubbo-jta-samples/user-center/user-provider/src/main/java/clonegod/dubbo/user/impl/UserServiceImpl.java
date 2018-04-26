package clonegod.dubbo.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clonegod.dubbo.user.api.IUserService;
import clonegod.dubbo.user.dao.UserDao;
import clonegod.dubbo.user.dto.DebitRequest;
import clonegod.dubbo.user.dto.DebitResponse;
import clonegod.dubbo.user.dto.UserLoginRequest;
import clonegod.dubbo.user.dto.UserLoginResponse;
import clonegod.dubbo.user.validator.UserValidator;

@Service("userSerivce")
public class UserServiceImpl implements IUserService {
	
	@Autowired
	UserDao userDao;
	
	@Override
	public UserLoginResponse login(UserLoginRequest request) {
		System.out.println("接收到用户登录请求：" + request);
		
		// 注：参数校验可使用dubbo中提供的参数校验来实现
		if(! UserValidator.checkUserLoginRequest(request)) {
			return UserLoginResponse.fail("000001", "请求参数校验失败");
		}
		
		if("root".equals(request.getUsername()) && "root".equals(request.getPassword())) {
			return UserLoginResponse.success();
		}
		
		return UserLoginResponse.fail("000001", "登录失败，用户名或密码错误");
	}


	@Override
	public DebitResponse debit(DebitRequest request) {
		DebitResponse response=new DebitResponse();
        userDao.updateUser(request);
        response.setCode("000000");
        response.setMemo("成功");
        return response;
	}

}
