package clonegod.dubbo.user.api;

import clonegod.dubbo.user.dto.UserQueryRequest;
import clonegod.dubbo.user.dto.UserQueryResponse;

public interface IUserQueryService {


    /**
     * 根据用户id来查询用户信息
     * @param request
     * @return
     */
    UserQueryResponse getUserById(UserQueryRequest request);

    /**
     * 根据用户id来查询用户信息
     * @param request
     * @return
     */
    UserQueryResponse getUserByIdWithLimiter(UserQueryRequest request);
}
