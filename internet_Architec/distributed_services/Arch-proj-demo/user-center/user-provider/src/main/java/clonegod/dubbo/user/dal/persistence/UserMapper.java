package clonegod.dubbo.user.dal.persistence;

import clonegod.dubbo.user.dal.entity.User;

public interface UserMapper {

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    User getUserByUserName(String username);

    /**
     * 根据uid获取用户信息
     * @param uid
     * @return
     */
    User getUserByUid(Integer uid);

    /**
     * 添加用户
     * @param user
     * @return
     */
    int insertSelective(User user);
}
