package clonegod.dubbo.user.api;

import clonegod.dubbo.user.dto.DebitRequest;
import clonegod.dubbo.user.dto.DebitResponse;
import clonegod.dubbo.user.dto.UserLoginRequest;
import clonegod.dubbo.user.dto.UserLoginResponse;

public interface IUserService {

    /**
     * 登录
     * @param request
     * @return
     */
    UserLoginResponse login(UserLoginRequest request);
    
    /**
     * 更新用户余额
     */
    DebitResponse debit(DebitRequest request);
}
