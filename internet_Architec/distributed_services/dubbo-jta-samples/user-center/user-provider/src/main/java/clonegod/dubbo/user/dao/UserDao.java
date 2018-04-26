package clonegod.dubbo.user.dao;

import clonegod.dubbo.user.dto.DebitRequest;

public interface UserDao {

    void updateUser(DebitRequest debitReq);
}
