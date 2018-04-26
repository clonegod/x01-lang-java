package clonegod.dubbo.user.api;

import clonegod.dubbo.user.dto.DebitRequest;
import clonegod.dubbo.user.dto.DebitResponse;
import clonegod.dubbo.user.dto.UserLoginRequest;
import clonegod.dubbo.user.dto.UserLoginResponse;
import clonegod.dubbo.user.dto.UserRegisterRequest;
import clonegod.dubbo.user.dto.UserRegisterResponse;

public interface IUserCoreService {

    /**
     * 用户登录操作
     * @param userLoginRequest
     * @return
     */
    UserLoginResponse login(UserLoginRequest userLoginRequest);


    /*
     * 注册
     */
    UserRegisterResponse register(UserRegisterRequest userRegisterRequest);
    
    
    /**
     * 更新用户余额
     */
    DebitResponse debit(DebitRequest request);
}
